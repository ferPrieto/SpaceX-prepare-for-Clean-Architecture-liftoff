package prieto.fernando.core.di

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
