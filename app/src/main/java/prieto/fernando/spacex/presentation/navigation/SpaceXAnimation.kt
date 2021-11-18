package prieto.fernando.spacex.presentation.navigation

import prieto.fernando.spacex.R

sealed class SpaceXAnimation(val animId: Int){
    object ErrorConnection: SpaceXAnimation(R.raw.error_conexion)
    object LoadingAnimation: SpaceXAnimation(R.raw.loading_animation)
    object PlanetAnimation: SpaceXAnimation(R.raw.planet_animation)
    object RocketLaunched: SpaceXAnimation(R.raw.rocket_launched)
}
