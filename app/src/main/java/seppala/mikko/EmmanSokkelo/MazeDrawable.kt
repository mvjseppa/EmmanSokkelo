package seppala.mikko.EmmanSokkelo

import android.graphics.*
import android.graphics.drawable.Drawable
import android.hardware.camera2.params.BlackLevelPattern
import android.util.Log

class MazeDrawable(private val maze: Maze, private val heroDrawable: Drawable, private val goalDrawable: Drawable) : Drawable() {

    private val wallThickness = 10
    private val cellSize = 100
    private val paint = Paint()
    init { paint.setARGB(255, 0, 0, 0) }


    override fun draw(canvas: Canvas) {

        val drawArea = Rect(0,0,cellSize,cellSize)

        for(x in 0 until maze.size.width)
        {
            drawArea.top=0
            drawArea.bottom=cellSize

            for(y in 0 until maze.size.height)
            {
                drawCell(maze[x,y], canvas, drawArea)

                if(maze.hero.cell === maze[x,y])
                {
                    heroDrawable.bounds = drawArea
                    heroDrawable.draw(canvas)
                }
                else if(maze[x,y].type == MazeCell.Type.GOAL)
                {

                    canvas.save()

                    canvas.translate(drawArea.exactCenterX(), drawArea.exactCenterY())
                    canvas.scale(0.8f, 0.8f)
                    canvas.clipRect(-cellSize/2,-cellSize/2,cellSize/2,cellSize/2)
                    goalDrawable.bounds = canvas.clipBounds

                    canvas.rotate(20.0f)
                    goalDrawable.draw(canvas)
                    
                    canvas.restore()
                }

                drawArea.top += cellSize
                drawArea.bottom += cellSize
            }

            drawArea.left += cellSize
            drawArea.right += cellSize
        }

    }

    private fun drawCell(cell: MazeCell, canvas: Canvas, area: Rect)
    {
        for(wall in cell.getWalls())
        {
            val wallRect = Rect(area)

            when(wall)
            {
                Direction.NORTH -> wallRect.bottom  = wallRect.top + wallThickness
                Direction.WEST  -> wallRect.right   = wallRect.left + wallThickness
                Direction.SOUTH -> wallRect.top     = wallRect.bottom + wallThickness
                Direction.EAST  -> wallRect.left    = wallRect.right + wallThickness
            }

            canvas.drawRect(wallRect, paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        // This method is required
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // This method is required
    }

    override fun getOpacity(): Int {
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        return PixelFormat.OPAQUE
    }
}