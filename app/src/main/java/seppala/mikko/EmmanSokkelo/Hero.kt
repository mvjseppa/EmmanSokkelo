package seppala.mikko.EmmanSokkelo

class Hero(var cell: MazeCell, var coordinates: Point = Point(0,0))
{
    fun move(direction: Direction)
    {
        cell = cell[direction] ?: cell
        coordinates += direction
    }
}

