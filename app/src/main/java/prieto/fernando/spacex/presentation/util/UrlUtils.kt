package prieto.fernando.spacex.presentation.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object UrlUtils {
    fun navigateTo(context: Context, pageUrl: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(pageUrl))
    }
}
