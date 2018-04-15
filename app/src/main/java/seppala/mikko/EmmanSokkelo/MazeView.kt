package seppala.mikko.EmmanSokkelo

import android.content.Context
import android.graphics.Canvas
import android.media.MediaPlayer
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class MazeView : View, HeroEventListener
{
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    inner class MazeViewHandler
    {
        private val heroDrawable = ContextCompat.getDrawable(context, R.drawable.hero_500px)
        private val goalDrawable = ContextCompat.getDrawable(context, R.drawable.flower_door_500px)

        var maze = RandomizedPrimMazeGenerator().generate(Size(10,15))
        val mazeDrawable = MazeDrawable(maze, heroDrawable, goalDrawable)
        var gestureDetector = GestureDetector(context, MazeFlingListener(maze))

        init { maze.hero.registerListener(this@MazeView) }
    }

    private var handler = MazeViewHandler()


    override fun onDraw(canvas: Canvas?)
    {
        if(canvas == null) return
        handler.mazeDrawable.setBounds(0,0, width, height)
        handler.mazeDrawable.draw(canvas)
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        val ret = handler.gestureDetector.onTouchEvent(event)

        this.postInvalidate()
        return ret
    }

    override fun onHeroMove(oldCell:MazeCell, newCell: MazeCell)
    {
        if(newCell.type == MazeCell.Type.GOAL) {
            val mp = MediaPlayer.create(context, R.raw.jeejee)
            mp.seekTo(900)
            mp.start()

            handler = MazeViewHandler()
        }
    }
}