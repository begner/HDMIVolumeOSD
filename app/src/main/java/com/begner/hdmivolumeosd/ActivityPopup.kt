package com.begner.hdmivolumeosd

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import com.google.android.material.tabs.TabLayout

abstract class ActivityPopup : ActivityGlobal(), TabLayout.OnTabSelectedListener {

    lateinit var TabList    : List<TabData>
    lateinit var NavigationTab: TabLayout

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
            view.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val tabIndex = NavigationTab.selectedTabPosition
        TabList.forEachIndexed { index, tabData ->
            if (index == tabIndex) {
                tabData.contentContainer.visibility = ScrollView.VISIBLE
            }
            else {
                tabData.contentContainer.visibility = ScrollView.GONE
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    data class TabData (
        var key: String = "",
        var label: String = "",
        var contentContainer: LinearLayout
    )
}
