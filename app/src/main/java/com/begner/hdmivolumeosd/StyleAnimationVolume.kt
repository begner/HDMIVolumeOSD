package com.begner.hdmivolumeosd

import android.animation.Animator
import com.daimajia.easing.Skill

class StyleAnimationVolume : StyleAnimation() {

    // Instant in
    override fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseOut
        val duration = 1000f
        val delayElement = 100f
        val delayBackground = 0f

        calcAnimationDirections(mainView, !osdPosition.isHorizontal, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, duration, delayElement, easing))

        if (backgroundView != null) {
            calcAnimationDirections(backgroundView!!, !osdPosition.isHorizontal, true)
            animations.add(createAnimation(backgroundView!!, animationProperty, outOfScreenPos, onScreenPos, duration, delayBackground, easing))
            animations.add(createAnimation(backgroundView!!, "alpha", 0f, 1f, duration, delayBackground, easing))
        }

        return animations
    }

    // Instant out
    override fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseIn
        val duration = 1000f
        val delayElement = 0f
        val delayBackground = 100f

        calcAnimationDirections(mainView, !osdPosition.isHorizontal, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, duration, delayElement, easing))

        if (backgroundView != null) {
            calcAnimationDirections(backgroundView!!, !osdPosition.isHorizontal, false)
            animations.add(createAnimation(backgroundView!!, animationProperty, onScreenPos, outOfScreenPos, duration, delayBackground, easing))
            animations.add(createAnimation(backgroundView!!, "alpha", 1f, 0f, duration, delayBackground, easing))
        }

        return animations
    }



}