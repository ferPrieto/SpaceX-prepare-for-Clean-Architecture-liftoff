const cp = require('child_process');
const fetch = require('node-fetch');

const checks = require('./checks');

const [owner, repo] = process.env.GITHUB_REPOSITORY.split('/');

function getCurrentCommitSha() {
  return cp
    .execSync(`git rev-parse HEAD`)
    .toString()
    .trim();
}

// The SHA provied by GITHUB_SHA is the merge (PR) commit.
// We need to get the current commit sha ourself.
const sha = getCurrentCommitSha();

async function setStatus(context, state, description) {
  return fetch(`https://api.github.com/repos/${owner}/${repo}/statuses/${sha}`, {
    method: 'POST',
    body: JSON.stringify({
      state,
      description,
      context,
    }),
    headers: {
      Authorization: `Bearer ${process.env.GITHUB_TOKEN}`,
      'Content-Type': 'application/json',
    },
  });
}

(async () => {
  console.log(`Starting status checks for commit ${sha}`);

  // Run in parallel
  await Promise.all(
    checks.map(async check => {
      const { name, callback } = check;

      await setStatus(name, 'pending', 'Running check..');

      try {
        const response = await callback();
        await setStatus(name, 'success', response);
      } catch (err) {
        const message = err ? err.message : 'Something went wrong';
        await setStatus(name, 'failure', message);
      }
    }),
  );

  console.log('Finished status checks');
})();
