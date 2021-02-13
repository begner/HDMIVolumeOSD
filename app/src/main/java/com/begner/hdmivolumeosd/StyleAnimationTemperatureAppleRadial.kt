package com.begner.hdmivolumeosd

import android.animation.Animator
import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import com.daimajia.easing.Skill

class StyleAnimationTemperatureAppleRadial : StyleAnimation() {

    private val animationScale = 0.8f
    private val easingScale = Skill.QuadEaseInOut

    private val inAnimationDuration = 300f
    private val inAnimationDelay = 0f
    private val inRotationDuration = 600f
    private val inRotationDelay = 0f
    private val inRotationDurationBar = 600f
    private val inRotationDelayBar = 50f
    private val inRotationEasing = Skill.QuadEaseIn

    private val outAnimationDuration = 800f
    private val outAnimationDelay = 600f
    private val outRotationDuration = 600f
    private val outRotationDelay = 300f
    private val outRotationDurationBar = 500f
    private val outRotationDelayBar = 0f
    private val outRotationEasing = Skill.QuadEaseOut

    private val easingMove = Skill.QuadEaseInOut

    @SuppressLint("RtlHardcoded")
    private fun setPivot(view: View, animateIn: Boolean) {
        mainView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        val horizontalGravity = osdPosition.gravity and Gravity.VERTICAL_GRAVITY_MASK
        if (horizontalGravity == Gravity.TOP) {
            setViewProperty(view, "pivotY", 0f)
            if (animateIn) {
                outOfScreenPos = 90f
            }
            else {
                outOfScreenPos = -90f
            }
        }
        if (horizontalGravity == Gravity.BOTTOM) {
            setViewProperty(view, "pivotY", mainView.getMeasuredHeight().toFloat())
            if (animateIn) {
                outOfScreenPos = -90f
            }
            else {
                outOfScreenPos = 90f
            }
        }

        val verticalGravity = osdPosition.gravity and Gravity.HORIZONTAL_GRAVITY_MASK
        if (verticalGravity == Gravity.LEFT) {
            setViewProperty(view, "pivotX", 0f)
        }
        if (verticalGravity == Gravity.RIGHT) {
            setViewProperty(view, "pivotX", mainView.getMeasuredWidth().toFloat())
        }
    }

    // Instant in
    @SuppressLint("RtlHardcoded")
    override public fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        // verticlal
        calcAnimationDirections(mainView, false, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, inAnimationDuration, inAnimationDelay, easingMove))

        // horizontal
        calcAnimationDirections(mainView, true, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, inAnimationDuration, inAnimationDelay, easingMove))

        animations.add(createAnimation(mainView, "scaleX", animationScale, 1f, inAnimationDuration, inAnimationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", animationScale, 1f, inAnimationDuration, inAnimationDelay, easingScale))

        val scale = osdView!!.getAnimationSubPart("scale")
        setPivot(scale!!, true)
        animations.add(createAnimation(scale, "rotation", outOfScreenPos, 0f, inRotationDuration, inRotationDelay, inRotationEasing))

        val bar = osdView!!.getAnimationSubPart("bar")
        setPivot(bar!!, true)
        animations.add(createAnimation(bar, "rotation", outOfScreenPos, 0f, inRotationDurationBar, inRotationDelayBar, inRotationEasing))

        return animations
    }


    // Instant out
    override public fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        // vertical
        calcAnimationDirections(mainView, false, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, outAnimationDuration, outAnimationDelay, easingMove))

        // horizontal
        calcAnimationDirections(mainView, true, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, outAnimationDuration, outAnimationDelay, easingMove))

        animations.add(createAnimation(mainView, "scaleX", 1f, animationScale, outAnimationDuration, outAnimationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 1f, animationScale, outAnimationDuration, outAnimationDelay, easingScale))

        val scale = osdView!!.getAnimationSubPart("scale")
        setPivot(scale!!, false)
        animations.add(createAnimation(scale, "rotation", 0f, outOfScreenPos, outRotationDuration, outRotationDelay, outRotationEasing))

        val bar = osdView!!.getAnimationSubPart("bar")
        setPivot(bar!!, false)
        animations.add(createAnimation(bar, "rotation",0f, outOfScreenPos, outRotationDurationBar, outRotationDelayBar, inRotationEasing))

        return animations
    }


}