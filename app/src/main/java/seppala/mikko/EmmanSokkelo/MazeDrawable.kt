package seppala.mikko.EmmanSokkelo

import android.graphics.*
import android.graphics.drawable.Drawable

class MazeDrawable(private val maze: Maze, private val heroDrawable: Drawable, private val goalDrawable: Drawable) : Drawable() {

    private val wallThickness = 10
    private val cellSize = 150
    private val wallPaint = Paint()
    private val floorPaint = Paint()
    init
    {
        wallPaint.setARGB(255, 0, 0, 0)
        floorPaint.setARGB(255, 100, 100, 250)
    }


    override fun draw(canvas: Canvas)
    {
        canvas.save()

        val newX = -cellSize * (maze.hero.coordinates.x + 0.5f) + canvas.width * 0.5f
        val newY = -cellSize * (maze.hero.coordinates.y + 0.5f) + canvas.height * 0.5f

        canvas.translate(newX, newY)

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
                    canvas.scale(1.2f, 1.2f)
                    canvas.clipRect(-cellSize/2,-cellSize/2,cellSize/2,cellSize/2)
                    goalDrawable.bounds = canvas.clipBounds

                    canvas.rotate(0.0f)
                    goalDrawable.draw(canvas)
                    
                    canvas.restore()
                }

                drawArea.top += cellSize
                drawArea.bottom += cellSize
            }

            drawArea.left += cellSize
            drawArea.right += cellSize
        }

        canvas.restore()

    }

    private fun drawCell(cell: MazeCell, canvas: Canvas, area: Rect)
    {
        if(!maze.hero.getCellsSeen().contains(cell)) return

        canvas.drawRect(area, floorPaint)

        for(wall in cell.getWallDirections())
        {
            val wallRect = Rect(area)

            when(wall)
            {
                Direction.NORTH -> wallRect.bottom  = wallRect.top + wallThickness
                Direction.WEST  -> wallRect.right   = wallRect.left + wallThickness
                Direction.SOUTH -> wallRect.top     = wallRect.bottom + wallThickness
                Direction.EAST  -> wallRect.left    = wallRect.right + wallThickness
            }

            canvas.drawRect(wallRect, wallPaint)
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