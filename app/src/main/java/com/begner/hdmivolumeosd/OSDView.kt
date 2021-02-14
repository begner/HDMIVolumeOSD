package com.begner.hdmivolumeosd

import android.animation.Animator
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.widget.FrameLayout


abstract class OSDView(val context: Context, var frameLayout: FrameLayout) {

    private var settingsGlobal  : SettingsGlobal = SettingsGlobal(context)
    lateinit var osdPosition: OSDPosition
    lateinit var view: View
    var backgroundView: View? = null
    lateinit var osdStyle : OSDStyle
    abstract fun addView()
    abstract fun addBackground()
    var isVisible: Boolean = false
    private var closeTimer: CountDownTimer? = null
    private var animatorSet: AnimatorSet? = null

    fun start() {
        addBackground()
        addView()

    }

    private fun animateIn() {
        if (!isVisible) {
            isVisible = true
            animate(true)
        }
    }

    fun animateOut() {
        if (isVisible) {
            isVisible = false
            animate(false)
        }
    }

    fun updated() {
        val duration = settingsGlobal.getDuration().toLong()
        if (closeTimer != null) {
            closeTimer!!.cancel()
        }
        closeTimer = object : CountDownTimer(duration * 1000, duration * 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                animateOut()
            }
        }.start()
        animateIn()
    }

    open fun getAnimationSubPart(name: String): View? {
        return null
    }

    @SuppressLint("RtlHardcoded")
    private fun animate(animateIn: Boolean) {
        val animationLib = osdStyle.animationClass
        animationLib.context = context
        animationLib.mainView = view
        animationLib.osdView = this
        if (backgroundView != null) {
            animationLib.backgroundView = backgroundView
        }
        animationLib.osdPosition = osdPosition

        if (animatorSet != null) {
            animatorSet!!.cancel()
        }
        animatorSet = AnimatorSet()
        val animations: MutableList<Animator>
        if (animateIn) {
            animations = animationLib.animateIn()
            animatorSet!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    view.visibility = View.VISIBLE
                    if (backgroundView != null) {
                        backgroundView!!.visibility = View.VISIBLE
                    }
                }
                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        } else {
            animations = animationLib.animateOut()
            animatorSet!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    view.visibility = View.VISIBLE
                    if (backgroundView != null) {
                        backgroundView!!.visibility = View.VISIBLE
                    }
                }
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                    if (backgroundView != null) {
                        backgroundView!!.visibility = View.GONE
                    }
                }
                override fun onAnimationCancel(animation: Animator) {
                    onAnimationEnd(animation)
                }
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        animatorSet!!.playTogether(animations)
        animatorSet!!.start()
    }


}