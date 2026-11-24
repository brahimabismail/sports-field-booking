package dz.ubma.sportsbooking

import android.annotation.SuppressLint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

/**
 * Adds a show/hide (eye) icon at the end of a password EditText.
 *
 * - Password is HIDDEN by default.
 * - Tapping the icon toggles visible text <-> masked characters.
 * - Only the display transformation changes, so inputType (textPassword)
 *   and the keyboard type are NOT modified.
 * - Cursor position (and selection) and the font are preserved.
 */
@SuppressLint("ClickableViewAccessibility")
fun EditText.setupPasswordToggle(
    iconShow: Int = R.drawable.ic_visibility,       // shown while password is hidden
    iconHide: Int = R.drawable.ic_visibility_off    // shown while password is visible
) {
    var passwordVisible = false // hidden by default

    fun render() {
        // 1) Save cursor/selection and font
        val start = selectionStart
        val end = selectionEnd
        val currentTypeface = typeface

        // 2) Switch only the display transformation
        transformationMethod = if (passwordVisible) {
            HideReturnsTransformationMethod.getInstance()
        } else {
            PasswordTransformationMethod.getInstance()
        }

        // 3) Restore font and cursor/selection
        typeface = currentTypeface
        if (start >= 0 && end >= 0) setSelection(start, end)

        // 4) Update the icon at the end side
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            0, 0, if (passwordVisible) iconHide else iconShow, 0
        )
    }

    fun isOnIcon(event: MotionEvent): Boolean {
        val icon = compoundDrawablesRelative[2] ?: return false
        val slack = 8 * resources.displayMetrics.density // slightly bigger tap area
        val iconWidth = icon.bounds.width()
        return if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            event.x <= paddingStart + iconWidth + slack
        } else {
            event.x >= width - paddingEnd - iconWidth - slack
        }
    }

    render() // initial state: hidden + eye icon

    setOnTouchListener { v, event ->
        when (event.action) {
            // Consume the touch on the icon so it doesn't move the cursor
            MotionEvent.ACTION_DOWN -> isOnIcon(event)
            MotionEvent.ACTION_UP -> {
                if (isOnIcon(event)) {
                    passwordVisible = !passwordVisible
                    render()
                    v.performClick()
                    true
                } else {
                    false
                }
            }
            else -> false
        }
    }
}