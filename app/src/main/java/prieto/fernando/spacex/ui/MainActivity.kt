package prieto.fernando.spacex.ui

import android.os.Bundle
import androidx.navigation.Navigation
import dagger.android.support.DaggerAppCompatActivity
import prieto.fernando.spacex.R

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp()
}
