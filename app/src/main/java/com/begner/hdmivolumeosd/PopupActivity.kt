package com.begner.hdmivolumeosd

import android.app.Activity
import android.content.Intent
import android.view.View

abstract class PopupActivity : Activity() {

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

}