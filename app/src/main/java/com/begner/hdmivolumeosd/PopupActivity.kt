package com.begner.hdmivolumeosd

import android.app.Activity
import android.content.Intent
import android.view.View

open class PopupActivity : Activity() {

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

    public fun ActionCancel(view: View?) {
        setResultAndFinish(false)
    }

    override fun onBackPressed() {
        setResultAndFinish(false)
    }

}