package prieto.fernando.spacex.utils

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import org.hamcrest.Description
import org.hamcrest.Matcher

class BottomNavigationMatcher {
    fun withBottomNavItemCheckedStatus(isChecked: Boolean): Matcher<View?> {
        return object :
            BoundedMatcher<View?, BottomNavigationItemView>(BottomNavigationItemView::class.java) {
            var triedMatching = false
            override fun describeTo(description: Description) {
                if (triedMatching) {
                    description.appendText("with BottomNavigationItem check status: $isChecked")
                    description.appendText("But was: $isChecked")
                }
            }

            override fun matchesSafely(item: BottomNavigationItemView): Boolean {
                triedMatching = true
                return item.itemData.isChecked == isChecked
            }
        }
    }
}