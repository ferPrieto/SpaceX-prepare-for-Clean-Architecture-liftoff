package prieto.fernando.spacex.presentation.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import prieto.fernando.spacex.R
import kotlinx.android.synthetic.main.widget_launch_details.view.widget_launch_details_title as titleView
import kotlinx.android.synthetic.main.widget_launch_details.view.widget_launch_details_value as valueView

class LaunchDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var title: CharSequence
        get() = titleView.text
        set(value) {
            titleView.text = value
        }

    var value: CharSequence
        get() = valueView.text
        set(value) {
            valueView.text = value
        }

    init {
        initializeView(context)

        attrs?.let(::applyAttributes)
    }

    private fun initializeView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.widget_launch_details, this, true)
    }

    private fun applyAttributes(attributes: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.LaunchDetailsView)
        typedArray.setTextIfSet(titleView, R.styleable.LaunchDetailsView_title)
        typedArray.setTextIfSet(valueView, R.styleable.LaunchDetailsView_value)
        typedArray.recycle()
    }

    private fun TypedArray.setTextIfSet(textView: TextView, textViewTextRes: Int) =
        getText(textViewTextRes)?.let(textView::setText)
}
