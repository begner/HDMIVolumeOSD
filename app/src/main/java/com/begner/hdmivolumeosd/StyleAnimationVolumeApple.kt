package com.begner.hdmivolumeosd

import android.animation.Animator
import com.daimajia.easing.Skill

class StyleAnimationVolumeApple : StyleAnimation() {

    // Instant in
    override public fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseOut
        val easingScale = Skill.QuadEaseInOut
        val duration = 500f
        val delayElement = 100f

        calcAnimationDirections(mainView, !osdPosition.isHorizontal, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, duration, delayElement, easing))

        animations.add(createAnimation(mainView, "scaleX", 0.7f, 1f, duration, delayElement, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 0.7f, 1f, duration, delayElement, easingScale))

        return animations
    }

    // Instant out
    override public fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseIn
        val easingScale = Skill.QuadEaseInOut
        val duration = 1000f
        val delayElement = 0f
        val delayBackground = 100f

        calcAnimationDirections(mainView, !osdPosition.isHorizontal, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, duration, delayElement, easing))

        animations.add(createAnimation(mainView, "scaleX", 1f, 0.7f, duration, delayElement, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 1f, 0.7f, duration, delayElement, easingScale))

        return animations
    }



}