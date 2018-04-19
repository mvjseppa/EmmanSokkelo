package seppala.mikko.EmmanSokkelo

/**
 * Created by Mikko on 2018-04-08.
 */
class Maze(val size: Size)
{
    private var goalSet = false

    private var grid = Array(size.height)
    {
        Array(size.width){ MazeCell(MazeCell.Type.NORMAL) }
    }

    init { grid[0][0].type = MazeCell.Type.START }

    val hero = Hero(this[0,0])

    private var wallGrid = Array(size.height * 2 + 1)
    {
        Array(size.width * 2 + 1) { ' ' }
    }

    fun setGoal(p: Point)
    {
        if(!goalSet)
        {
            goalSet = true
            this[p].type = MazeCell.Type.GOAL
        }
    }

    operator fun get(x: Int, y: Int): MazeCell = grid[y][x]
    operator fun get(p: Point) = get(p.x, p.y)

    fun getCoordinatesOf(c: MazeCell) : Point
    {
        val y = grid.indexOfFirst { it.contains(c) }
        val x = grid[y].indexOfFirst{it === c}

        return Point(x,y)
    }

    fun getCellNeighbour(c: MazeCell, dir: Direction) : MazeCell = this[getCoordinatesOf(c) + dir]

    override fun toString(): String
    {
        var y = 1
        for (row in grid)
        {
            var x = 1
            for (cell in row)
            {
                checkWalls(cell, x, y)
                if (cell.type == MazeCell.Type.GOAL) wallGrid[y][x] = '^'
                if (cell.type == MazeCell.Type.START) wallGrid[y][x] = 'v'
                x += 2
            }
            y += 2
        }

        var out = ""
        for (row in wallGrid) {
            out += String(row.toCharArray()) + "\n"
        }

        return out
    }

    private fun checkWalls(cell: MazeCell, x: Int, y: Int)
    {
        if (cell[Direction.NORTH] == null) {
            wallGrid[y - 1][x - 1] = '#'//'┼'
            wallGrid[y - 1][x] = '#'//'─'
            wallGrid[y - 1][x + 1] = '#'//'┼'
        }

        if (cell[Direction.WEST] == null) {
            wallGrid[y - 1][x - 1] = '#'//'┼'
            wallGrid[y][x - 1] = '#'//'│'
            wallGrid[y + 1][x - 1] = '#'//'┼'
        }

        if (cell[Direction.SOUTH] == null) {
            wallGrid[y + 1][x - 1] = '#'//'┼'
            wallGrid[y + 1][x] = '#'//'─'
            wallGrid[y + 1][x + 1] = '#'//'┼'
        }

        if (cell[Direction.EAST] == null) {
            wallGrid[y - 1][x + 1] = '#'//'┼'
            wallGrid[y][x + 1] = '#'//'│'
            wallGrid[y + 1][x + 1] = '#'//'┼'
        }
    }
}