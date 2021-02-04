package com.begner.hdmivolumeosd

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout


abstract class OSDView(val context: Context, var frameLayout: FrameLayout) {

    lateinit var osdPosition: OSDPosition
    lateinit var view: View
    abstract fun addView()
    abstract fun addBackground()
    var isVisible: Boolean = false
    var closeTimer: CountDownTimer? = null
    var animatorSet: AnimatorSet? = null

    public fun animateIn() {
        if (!isVisible) {
            isVisible = true
            animate(true)
        }
    }

    public fun animateOut() {
        if (isVisible) {
            isVisible = false
            animate(false)
        }
    }

    public fun updated() {
        val duration = 2L
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

    @SuppressLint("RtlHardcoded")
    private fun animate(animateIn: Boolean) {
        val metrics = context.getResources().getDisplayMetrics()
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        var animationProperty = ""
        var outOfScreenPos = 0f
        var onScreenPos = 0f

        if (osdPosition.isHorizontal) {
            val horizontalGravity = osdPosition.gravity and Gravity.VERTICAL_GRAVITY_MASK
            val height = view.getMeasuredHeight()
            animationProperty = "translationY"

            if (horizontalGravity == Gravity.TOP) {
                outOfScreenPos = 0f - height
            }
            if (horizontalGravity == Gravity.BOTTOM) {
                outOfScreenPos = 0f + height
            }
            onScreenPos = 0f

            if (animateIn) {
                view.y = outOfScreenPos
            } else {
                view.y = onScreenPos
            }

        }
        else {
            val verticalGravity = osdPosition.gravity and Gravity.HORIZONTAL_GRAVITY_MASK
            val width = view.getMeasuredWidth()
            animationProperty = "translationX"

            if (verticalGravity == Gravity.LEFT) {
                outOfScreenPos = 0f - width
            }
            if (verticalGravity == Gravity.RIGHT) {
                outOfScreenPos = 0f + width
            }
            onScreenPos = 0f
            if (animateIn) {
                view.x = outOfScreenPos
            } else {
                view.x = onScreenPos
            }
        }

        if (animatorSet != null) {
            animatorSet!!.cancel()
        }
        animatorSet = AnimatorSet()
        val animations: MutableList<Animator> = ArrayList()
        if (animateIn) {
            animations.add(
                ObjectAnimator.ofFloat(view, animationProperty, onScreenPos).setDuration(
                    500
                )
            );
            animatorSet!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        } else {
            animations.add(
                ObjectAnimator.ofFloat(view, animationProperty, outOfScreenPos).setDuration(500)
            );
            animatorSet!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    view.visibility = View.VISIBLE
                }
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
                override fun onAnimationCancel(animation: Animator) {
                    view.visibility = View.GONE
                }
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        animatorSet!!.playTogether(animations)
        animatorSet!!.start()
    }


}