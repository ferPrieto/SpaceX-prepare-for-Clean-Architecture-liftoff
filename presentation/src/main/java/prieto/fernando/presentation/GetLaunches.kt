package prieto.fernando.presentation

import io.reactivex.Single
import prieto.fernando.presentation.model.LaunchesUiModel

interface GetLaunches {
    fun execute(): Single<LaunchesUiModel>
}
