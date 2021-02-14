package com.begner.hdmivolumeosd

import android.animation.Animator
import com.daimajia.easing.Skill

class StyleAnimationVolumeApple : StyleAnimation() {

    private val animationDuration = 300f
    private val animationDelay = 0f
    private val animationScale = 0.7f
    private val easingScale = Skill.QuadEaseInOut

    // Instant in
    override fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseOut

        calcAnimationDirections(mainView, !osdPosition.isHorizontal, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, animationDuration, animationDelay, easing))

        animations.add(createAnimation(mainView, "scaleX", animationScale, 1f, animationDuration, animationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", animationScale, 1f, animationDuration, animationDelay, easingScale))

        return animations
    }

    // Instant out
    override fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseIn

        calcAnimationDirections(mainView, !osdPosition.isHorizontal, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, animationDuration, animationDelay, easing))

        animations.add(createAnimation(mainView, "scaleX", 1f, animationScale, animationDuration, animationDelay, easingScale))
        animations.add(createAnimation(mainView, "scaleY", 1f, animationScale, animationDuration, animationDelay, easingScale))

        return animations
    }



}