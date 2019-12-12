package prieto.fernando.presentation

import io.reactivex.Single
import prieto.fernando.presentation.model.LaunchUiModel

interface GetLaunches {
    fun execute(): Single<List<LaunchUiModel>>
}
