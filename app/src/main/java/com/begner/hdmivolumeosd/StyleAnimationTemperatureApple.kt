package com.begner.hdmivolumeosd

import android.animation.Animator
import com.daimajia.easing.Skill

class StyleAnimationTemperatureApple : StyleAnimation() {

    // Instant in
    override public fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseOut
        val duration = 750f
        val delayElement = 100f
        val easingScale = Skill.QuadEaseInOut

        // verticlal
        calcAnimationDirections(mainView, false, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, duration, delayElement, easing))

        // horizontal
        calcAnimationDirections(mainView, true, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, duration, delayElement, easing))

        animations.add(createAnimation(mainView, "scaleX", 0.7f, 1f, duration, delayElement, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 0.7f, 1f, duration, delayElement, easingScale))

        return animations
    }

    // Instant out
    override public fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseIn
        val duration = 750f
        val delayElement = 0f
        val easingScale = Skill.QuadEaseInOut

        // vertical
        calcAnimationDirections(mainView, false, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, duration, delayElement, easing))

        // horizontal
        calcAnimationDirections(mainView, true, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, duration, delayElement, easing))

        animations.add(createAnimation(mainView, "scaleX", 1f, 0.7f, duration, delayElement, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 1f, 0.7f, duration, delayElement, easingScale))

        return animations
    }



}