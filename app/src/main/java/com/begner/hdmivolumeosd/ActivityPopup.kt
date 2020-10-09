package com.begner.hdmivolumeosd

import android.app.Activity
import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class ActivityPopup : ActivityGlobal() {

    public fun setResultAndFinish(success: Boolean) {
        setResult(success)
        finish()
    }

    public fun setResult(success: Boolean) {
        val returnIntent = Intent()
        // returnIntent.putExtra("result", "canceled")
        if (success) {
            setResult(RESULT_OK, returnIntent)
        }
        else {
            setResult(RESULT_CANCELED, returnIntent)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    public fun ActionCancel(view: View?) {
        setResultAndFinish(false)
    }

    @Suppress("UNUSED_PARAMETER")
    public fun ActionSave(view: View?) {
        save()
        setResultAndFinish(true)
    }

    abstract fun save()

    override fun onBackPressed() {
        setResultAndFinish(false)
    }

    fun HideKeyboardOnFocus(view: View, activity: Activity?) {
        if (!(view is EditText)) {
            view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    HideSoftKeyboard()
                }
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                HideKeyboardOnFocus(innerView, activity)
            }
        }
    }

    fun HideSoftKeyboard() {
        val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0);
    }
}
