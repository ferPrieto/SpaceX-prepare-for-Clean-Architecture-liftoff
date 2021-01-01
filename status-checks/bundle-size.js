const path = require('path');
const fg = require('fast-glob');
const gzipSize = require('gzip-size');

const BUILD_DIR = path.join(process.cwd(), 'dist');

const sizes = [
  {
    pattern: '*.js',
    limit: 120 * 1024, // 120kb
    format: formatHashedFileName,
  },
  {
    pattern: '*.css',
    limit: 30 * 1024, // 30kb
    format: formatHashedFileName,
  },
];

function toKb(bytes) {
  return `${Math.round(bytes / 1024)}Kb`;
}

function formatHashedFileName(filePath) {
  const name = filePath.split('.')[0];
  return `${name}${path.extname(filePath)}`;
}

function formatErrorMessage(failed) {
  if (failed.length === 1) {
    const { name, size, diff } = failed[0];
    return `${name}: ${toKb(size)} (+${toKb(diff)} larger than allowed)`;
  }

  const chunkErrors = failed.map(({ name, diff }) => `${name} (+${toKb(diff)})`).join(', ');
  return `${failed.length} files exceed limit: ${chunkErrors}`;
}

async function checkBundleSize() {
  console.log('Starting bundle size check');

  const files = [];

  sizes.forEach(size => {
    const { pattern, limit, format } = size;
    fg.sync(pattern, {
      cwd: BUILD_DIR,
    }).forEach(filePath => {
      files.push({ filePath, limit, format });
    });
  });

  console.log(`Found ${files.length} file(s)`);

  const failed = [];
  const passed = [];

  await Promise.all(
    files.map(async chunk => {
      const { filePath, format, limit } = chunk;
      const name = typeof format === 'function' ? format(filePath) : filePath;
      const size = await gzipSize.file(path.join(BUILD_DIR, filePath));

      console.log(`Measuring ${name} (${toKb(size)})`);

      const diff = size - limit;
      if (size > limit) {
        failed.push({ name, size, limit, diff });
        return;
      }

      passed.push({ name, size, limit, diff });
      console.log(`${name} is under size (${toKb(diff)}). Nice work!`);
    }),
  );

  console.log(`Number of failures: ${failed.length}`);

  if (failed.length) {
    const errorMessage = formatErrorMessage(failed);
    throw new Error(errorMessage);
  }

  const chunkMessages = passed.map(({ name, size }) => `${name} (${toKb(size)})`).join(', ');

  console.log('Finished bundle size check');

  return `Passed! ${chunkMessages}`;
}

module.exports = {
  name: 'Bundle size',
  callback: checkBundleSize,
};
