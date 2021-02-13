package com.begner.hdmivolumeosd

import android.animation.Animator
import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import com.daimajia.easing.Skill

class StyleAnimationTemperatureAppleRadial : StyleAnimation() {

    private val animationDuration = 300f
    private val rotationDuration = 600f
    private val animationDelay = 0f
    private var animationScale = 0.8f
    private val easingScale = Skill.QuadEaseInOut

    private fun setPivot(animateIn: Boolean) {
        mainView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        val horizontalGravity = osdPosition.gravity and Gravity.VERTICAL_GRAVITY_MASK
        if (horizontalGravity == Gravity.TOP) {
            setViewProperty(mainView, "pivotY", 0f)
            if (animateIn) {
                outOfScreenPos = 90f
            }
            else {
                outOfScreenPos = -90f
            }
        }
        if (horizontalGravity == Gravity.BOTTOM) {
            setViewProperty(mainView, "pivotY", mainView.getMeasuredHeight().toFloat())
            if (animateIn) {
                outOfScreenPos = -90f
            }
            else {
                outOfScreenPos = 90f
            }
        }

        val verticalGravity = osdPosition.gravity and Gravity.HORIZONTAL_GRAVITY_MASK
        if (verticalGravity == Gravity.LEFT) {
            setViewProperty(mainView, "pivotX", 0f)
        }
        if (verticalGravity == Gravity.RIGHT) {
            setViewProperty(mainView, "pivotX", mainView.getMeasuredWidth().toFloat())
        }
    }

    // Instant in
    @SuppressLint("RtlHardcoded")
    override public fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.QuadEaseOut

        setPivot(true)
        animations.add(createAnimation(mainView, "rotation", outOfScreenPos, 0f, rotationDuration, animationDelay, easingScale))

        return animations
    }

    // Instant out
    override public fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.QuadEaseIn
        setPivot(false)
        animations.add(createAnimation(mainView, "rotation", 0f, outOfScreenPos, rotationDuration, animationDelay, easingScale))

        return animations
    }



}