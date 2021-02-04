package com.begner.hdmivolumeosd

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewDebug.ExportedProperty
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat

class LayoutTemperatureIndicator : FrameLayout {

    @ExportedProperty(category = "value")
    public var value: Int = 0
        set(value) {
            field = value
            doOnChange()
        }

    @ExportedProperty(category = "minValue")
    public var minValue: Int = 0
        set(value) {
            field = value
            doOnChange()
        }

    @ExportedProperty(category = "maxValue")
    public var maxValue: Int = 0
        set(value) {
            field = value
            doOnChange()
        }

    @ExportedProperty(category = "backgroundId")
    public var backgroundId: Int = 0
        set(value) {
            field = value
        }

    @ExportedProperty(category = "chartId")
    public var chartId: Int = 0
        set(value) {
            field = value
        }

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    lateinit private var chartCanvas: Canvas
    lateinit private var chartCanvasBitmap: Bitmap

    lateinit private var chartProgress: Drawable
    lateinit private var chartBackground: Drawable

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val metrics = context.getResources().getDisplayMetrics()

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LayoutTemperatureIndicator,
            0, 0).apply {

            try {
                value = getInteger(R.styleable.LayoutTemperatureIndicator_value, 0)
                minValue = getInteger(R.styleable.LayoutTemperatureIndicator_minValue, 0)
                maxValue = getInteger(R.styleable.LayoutTemperatureIndicator_maxValue, 100)
                backgroundId = getResourceId(R.styleable.LayoutTemperatureIndicator_backgroundId, 0)
                chartId = getResourceId(R.styleable.LayoutTemperatureIndicator_chartId, 0)
            } finally {
                recycle()
            }
        }

        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        chartProgress = ResourcesCompat.getDrawable(
            resources,
            chartId,
            null
        )!!

        chartBackground = ResourcesCompat.getDrawable(
            resources,
            backgroundId,
            null
        )!!
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        doOnChange()
    }

    fun doOnChange() {
        if (mWidth < 1 || mHeight < 1) {
            return
        }
        chartCanvasBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        chartCanvas = Canvas(chartCanvasBitmap)
        chartProgress.setBounds(0, 0, mWidth, mHeight)
        chartBackground.setBounds(0, 0, mWidth, mHeight)
    }

    private fun calcAngle() : Float {
        val range = (maxValue - minValue).toDouble()
        val rangeValue = (value - minValue).toDouble()

        val degree = 90.0 / range * rangeValue
        return degree.toFloat()
    }

    private fun calcClipLeftHeight(clipAngle:Float) : Float {
        var angle = clipAngle
        if (angle < 0f) {
            angle = 0.1f
        }
        if (angle >= 90f) {
            angle = 89.9f
        }
        return (mWidth.toDouble() * Math.tan(Math.toRadians(angle.toDouble()))).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val clipAngle = calcAngle()
        val clipPath = Path()
        clipPath.moveTo(mWidth.toFloat(), mHeight.toFloat())
        clipPath.lineTo(0f, mHeight.toFloat())
        clipPath.lineTo(0f, mHeight.toFloat() - calcClipLeftHeight(clipAngle))
        clipPath.lineTo(mWidth.toFloat(), mHeight.toFloat())

        if (canvas != null) {
            chartCanvas.clipPath(clipPath)
            chartProgress.draw(chartCanvas)
            chartBackground.draw(canvas)
            canvas.drawBitmap(chartCanvasBitmap, 0f, 0f, Paint())
            // DRAW CLIP PATH for DEBUG purpose
            /*
            val debugClipPathPaint = Paint()
            debugClipPathPaint.setStyle(Paint.Style.FILL)
            debugClipPathPaint.setColor(Color.TRANSPARENT)
            debugClipPathPaint.setStrokeWidth(3F)
            debugClipPathPaint.setPathEffect(null)
            debugClipPathPaint.setColor(Color.BLACK)
            debugClipPathPaint.setStyle(Paint.Style.STROKE)
            canvas.drawPath(clipPath, debugClipPathPaint)
            */
        }
    }
}
