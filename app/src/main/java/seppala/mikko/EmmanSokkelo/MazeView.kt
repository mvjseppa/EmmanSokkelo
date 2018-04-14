package seppala.mikko.EmmanSokkelo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
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

    private val heroDrawable = ContextCompat.getDrawable(context, R.drawable.hero_500px)
    private val goalDrawable = ContextCompat.getDrawable(context, R.drawable.flower_door_500px)
    private val maze = RandomizedPrimMazeGenerator().generate(Size(10,15))
    private val mazeDrawable = MazeDrawable(maze, heroDrawable, goalDrawable)
    private val paint = Paint()
    private val mazeFlingListener = MazeFlingListener(maze)
    private var gestureDetector = GestureDetector(context, mazeFlingListener)

    init
    {
        maze.hero.registerListener(this)
        paint.setARGB(255, 255, 0, 0)
    }

    override fun onDraw(canvas: Canvas?)
    {
        if(canvas == null) return
        mazeDrawable.setBounds(0,0, width, height)
        mazeDrawable.draw(canvas)
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        val ret = gestureDetector.onTouchEvent(event)

        this.postInvalidate()
        return ret
    }

    override fun onHeroMove(oldCell:MazeCell, newCell: MazeCell)
    {
        if(newCell.type == MazeCell.Type.GOAL) {
            val mp = MediaPlayer.create(context, R.raw.jeejee)
            mp.seekTo(900)
            mp.start()
        }
    }
}