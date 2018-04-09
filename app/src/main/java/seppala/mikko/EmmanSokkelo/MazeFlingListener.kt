package seppala.mikko.EmmanSokkelo

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import java.lang.Math.abs

class MazeFlingListener(private val maze: Maze) : GestureDetector.SimpleOnGestureListener()
{
    override fun onDown(event: MotionEvent): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                         velocityY: Float): Boolean
    {

        var dir = if(abs(velocityX) > abs(velocityY))
        {
            if(velocityX < 0) Direction.WEST else Direction.EAST
        }
        else
        {
            if (velocityY < 0) Direction.NORTH else Direction.SOUTH
        }

        maze.hero.move(dir)

        Log.d("EmmanSokkelo", "swipe $velocityX, $velocityY")

        return true
    }
}