package seppala.mikko.EmmanSokkelo

import android.text.method.Touch.onTouchEvent
import android.view.MotionEvent
import android.widget.Toast
import android.view.GestureDetector
import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.GridView


class GestureDetectGridView : GridView {
    private var gDetector: GestureDetector? = null
    private var flingConfirmed = false
    private var mTouchX: Float = 0.toFloat()
    private var mTouchY: Float = 0.toFloat()

    // Boiler plate view constructors
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    // Sets up gesture detector, moved from your original MainActivity

    private fun init(context: Context) {
        gDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(event: MotionEvent): Boolean {
                return true
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                                 velocityY: Float): Boolean {
                val position = this@GestureDetectGridView.pointToPosition(Math.round(e1.x), Math.round(e1.y))

                if (Math.abs(e1.y - e2.y) > SWIPE_MAX_OFF_PATH) {
                    if (Math.abs(e1.x - e2.x) > SWIPE_MAX_OFF_PATH || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false
                    }
                    if (e1.y - e2.y > SWIPE_MIN_DISTANCE) {
                        Toast.makeText(context, "top at index $position", Toast.LENGTH_SHORT).show()
                    } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE) {
                        Toast.makeText(context, "bottom at index $position", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                        return false
                    }
                    if (e1.x - e2.x > SWIPE_MIN_DISTANCE) {
                        Toast.makeText(context, "left at index $position", Toast.LENGTH_SHORT).show()
                    } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE) {
                        Toast.makeText(context, "right at index $position", Toast.LENGTH_SHORT).show()
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.actionMasked
        gDetector!!.onTouchEvent(ev)
        // Determine whether we need to start intercepting all touch events
        // such that the buttons would no longer receive further touch events
        // Return true if the fling gesture is confirmed
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            flingConfirmed = false
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.x
            mTouchY = ev.y
        } else {
            // short cut just in case
            if (flingConfirmed) {
                return true
            }
            val dX = Math.abs(ev.x - mTouchX)
            val dY = Math.abs(ev.y - mTouchY)
            if (dX > SWIPE_MIN_DISTANCE || dY > SWIPE_MIN_DISTANCE) {
                flingConfirmed = true
                return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return gDetector!!.onTouchEvent(ev)
    }

    companion object {

        private val SWIPE_MIN_DISTANCE = 120
        private val SWIPE_MAX_OFF_PATH = 250
        private val SWIPE_THRESHOLD_VELOCITY = 200
    }

}