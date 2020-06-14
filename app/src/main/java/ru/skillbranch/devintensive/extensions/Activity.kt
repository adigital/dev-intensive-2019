package ru.skillbranch.devintensive.extensions

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

var isKeyboardVisible: Boolean = false

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.isKeyboardOpen(): Boolean {
    return isKeyboardVisible
}

fun AppCompatActivity.isKeyboardClosed(): Boolean {
    return !isKeyboardVisible
}

//fun AppCompatActivity.isKeyboardVisible(): Boolean {
//    val r = Rect()
//
//    window.decorView.getWindowVisibleDisplayFrame(r)
//
//    val height = getDisplaySize().y
//    val diff = height - r.bottom
//
//    return diff != 0
//}

fun AppCompatActivity.isKeyboardShown(rootView: View): Boolean {
    /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
    val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val dm = rootView.resources.displayMetrics
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
    val heightDiff = rootView.bottom - r.bottom
    /* Threshold size: dp to pixels, multiply with display density */
    val isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density
    isKeyboardVisible = isKeyboardShown

    return isKeyboardShown
}