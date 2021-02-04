package com.begner.hdmivolumeosd

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.opengl.Visibility
import android.view.Gravity
import android.view.View
import com.daimajia.easing.Glider
import com.daimajia.easing.Skill

open class StyleAnimation  {
    public lateinit var mainView: View
    public var backgroundView: View? = null
    public lateinit var context: Context
    public lateinit var osdPosition: OSDPosition
    public var animationProperty: String = "translationX"
    public var onScreenPos: Float = 0f
    public var outOfScreenPos: Float = 0f


    open public fun calcAnimationDirections(animView: View, horizontal: Boolean, animateIn: Boolean) {
        val metrics = context.getResources().getDisplayMetrics()
        animView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        if (!horizontal) {
            val horizontalGravity = osdPosition.gravity and Gravity.VERTICAL_GRAVITY_MASK
            val height = animView.getMeasuredHeight()
            animationProperty = "translationY"

            if (horizontalGravity == Gravity.TOP) {
                outOfScreenPos = 0f - height
            }
            if (horizontalGravity == Gravity.BOTTOM) {
                outOfScreenPos = 0f + height
            }
            onScreenPos = 0f
        }
        else {
            val verticalGravity = osdPosition.gravity and Gravity.HORIZONTAL_GRAVITY_MASK
            val width = animView.getMeasuredWidth()
            animationProperty = "translationX"

            if (verticalGravity == Gravity.LEFT) {
                outOfScreenPos = 0f - width
            }
            if (verticalGravity == Gravity.RIGHT) {
                outOfScreenPos = 0f + width
            }
            onScreenPos = 0f
        }
    }

    // Instant in
    open public fun animateIn(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseOut
        val duration = 500f
        val delayElement = 100f
        val delayBackground = 0f

        // verticlal
        calcAnimationDirections(mainView, false, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, duration, delayElement, easing))

        // horizontal
        calcAnimationDirections(mainView, true, true)
        animations.add(createAnimation(mainView, animationProperty, outOfScreenPos, onScreenPos, duration, delayElement, easing))

        if (backgroundView != null) {
            calcAnimationDirections(backgroundView!!, osdPosition.isHorizontal, true)
            animations.add(createAnimation(backgroundView!!, animationProperty, outOfScreenPos, onScreenPos, duration, delayBackground, easing))
            animations.add(createAnimation(backgroundView!!, "alpha", 0f, 1f, duration, delayBackground, easing))
        }

        return animations
    }

    // Instant out
    open public fun animateOut(): MutableList<Animator> {
        val animations : MutableList<Animator> = ArrayList()

        val easing = Skill.CircEaseIn
        val duration = 500f
        val delayElement = 0f
        val delayBackground = 100f

        // vertical
        calcAnimationDirections(mainView, false, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, duration, delayElement, easing))

        // horizontal
        calcAnimationDirections(mainView, true, false)
        animations.add(createAnimation(mainView, animationProperty, onScreenPos, outOfScreenPos, duration, delayElement, easing))

        if (backgroundView != null) {
            calcAnimationDirections(backgroundView!!, osdPosition.isHorizontal, false)
            animations.add(createAnimation(backgroundView!!, animationProperty, onScreenPos, outOfScreenPos, duration, delayBackground, easing))
            animations.add(createAnimation(backgroundView!!, "alpha", 1f, 0f, duration, delayBackground, easing))
        }

        return animations
    }

    public fun setViewProperty(view: View, propertyName: String, value: Float) {
        when (propertyName) {
            "translationX" -> view.translationX = value
            "translationY" -> view.translationY = value
            "alpha" -> view.alpha = value
            "scaleX" -> view.scaleX = value
            "scaleY" -> view.scaleY = value
        }
    }

    public fun createAnimation(view:View, property: String, start: Float, end: Float, duration: Float, startDelay: Float, easing: Skill) : ValueAnimator {
        setViewProperty(view, property, start)
        val animation = ObjectAnimator.ofFloat(view, property, end)
        animation.setDuration((duration - startDelay).toLong())
        animation.startDelay = startDelay.toLong()

        return Glider.glide(easing, duration, animation)
    }
}