package com.begner.hdmivolumeosd

import android.animation.Animator
import android.annotation.SuppressLint
import android.view.Gravity
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
    private fun setRotationStartAngle() {
        val horizontalGravity = osdPosition.gravity and Gravity.VERTICAL_GRAVITY_MASK
        outOfScreenPos = 0f
        if (horizontalGravity == Gravity.TOP) outOfScreenPos = 90f
        if (horizontalGravity == Gravity.BOTTOM) outOfScreenPos = -90f
    }

    // Instant in
    @SuppressLint("RtlHardcoded")
    override fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        // verticlal
        calcAnimationDirections(mainView, horizontal = false, animateIn = true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, inAnimationDuration, inAnimationDelay, easingMove))

        // horizontal
        calcAnimationDirections(mainView, horizontal = true, animateIn = true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, inAnimationDuration, inAnimationDelay, easingMove))

        animations.add(createAnimation(mainView, "scaleX", animationScale, 1f, inAnimationDuration, inAnimationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", animationScale, 1f, inAnimationDuration, inAnimationDelay, easingScale))

        setRotationStartAngle()
        val scale = osdView!!.getAnimationSubPart("scale")
        animations.add(createAnimation(scale!!, "rotation", outOfScreenPos*-1, 0f, inRotationDuration, inRotationDelay, inRotationEasing))

        val bar = osdView!!.getAnimationSubPart("bar")
        animations.add(createAnimation(bar!!, "rotation", outOfScreenPos, 0f, inRotationDurationBar, inRotationDelayBar, inRotationEasing))

        return animations
    }


    // Instant out
    override fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        // vertical
        calcAnimationDirections(mainView, horizontal = false, animateIn = false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, outAnimationDuration, outAnimationDelay, easingMove))

        // horizontal
        calcAnimationDirections(mainView, horizontal = true, animateIn = false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, outAnimationDuration, outAnimationDelay, easingMove))

        animations.add(createAnimation(mainView, "scaleX", 1f, animationScale, outAnimationDuration, outAnimationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 1f, animationScale, outAnimationDuration, outAnimationDelay, easingScale))

        setRotationStartAngle()
        val scale = osdView!!.getAnimationSubPart("scale")
        animations.add(createAnimation(scale!!, "rotation", 0f, outOfScreenPos, outRotationDuration, outRotationDelay, outRotationEasing))

        val bar = osdView!!.getAnimationSubPart("bar")
        animations.add(createAnimation(bar!!, "rotation",0f, outOfScreenPos*-1, outRotationDurationBar, outRotationDelayBar, inRotationEasing))

        return animations
    }


}