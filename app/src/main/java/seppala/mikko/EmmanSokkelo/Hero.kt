package seppala.mikko.EmmanSokkelo

class Hero(var cell: MazeCell, var coordinates: Point = Point(0,0))
{
    private var listeners = emptyList<HeroEventListener>()

    fun registerListener(listener: HeroEventListener)
    {
        listeners += listener
    }

    fun move(direction: Direction)
    {
        cell = cell[direction] ?: cell
        coordinates += direction

        if(cell.type == MazeCell.Type.GOAL)
            for(listener in listeners)
                listener.onGoalEvent()

    }
}

