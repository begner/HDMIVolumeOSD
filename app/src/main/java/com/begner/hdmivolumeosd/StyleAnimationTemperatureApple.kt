package com.begner.hdmivolumeosd

import android.animation.Animator
import com.daimajia.easing.Skill

class StyleAnimationTemperatureApple : StyleAnimation() {

    private val animationDuration = 300f
    private val animationDelay = 0f
    private var animationScale = 0.8f
    private val easingScale = Skill.QuadEaseInOut

    // Instant in
    override fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseOut

        // verticlal
        calcAnimationDirections(mainView, horizontal = false, animateIn = true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, animationDuration, animationDelay, easing))

        // horizontal
        calcAnimationDirections(mainView, horizontal = true, animateIn = true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, animationDuration, animationDelay, easing))

        animations.add(createAnimation(mainView, "scaleX", animationScale, 1f, animationDuration, animationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", animationScale, 1f, animationDuration, animationDelay, easingScale))

        return animations
    }

    // Instant out
    override fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseIn

        // vertical
        calcAnimationDirections(mainView, horizontal = false, animateIn = false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, animationDuration, animationDelay, easing))

        // horizontal
        calcAnimationDirections(mainView, true, animateIn = false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, animationDuration, animationDelay, easing))

        animations.add(createAnimation(mainView, "scaleX", 1f, animationScale, animationDuration, animationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 1f, animationScale, animationDuration, animationDelay, easingScale))

        return animations
    }



}