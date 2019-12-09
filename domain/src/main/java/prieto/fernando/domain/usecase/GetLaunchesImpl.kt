package prieto.fernando.domain.usecase

import dagger.Reusable
import javax.inject.Inject

interface GetLaunches {
    fun execute()
}

@Reusable
class GetLaunchesImpl @Inject constructor() : GetLaunches {
    override fun execute() {

    }
}