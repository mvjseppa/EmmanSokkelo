package seppala.mikko.EmmanSokkelo

import kotlin.properties.Delegates

class Hero(startCell: MazeCell, var coordinates: Point = Point(0,0))
{
    private var listeners = emptyList<HeroEventListener>()

    var cell: MazeCell by Delegates.observable(startCell)
    {
        _, oldCell, newCell ->
        listeners.forEach{it.onHeroMove(oldCell, newCell)}
    }

    private var cellsSeen = mutableSetOf(cell)

    init{updateCellsSeen()}

    fun registerListener(listener: HeroEventListener)
    {
        listeners += listener
    }

    fun move(direction: Direction)
    {
        if(cell[direction] != null)
        {
            cell = cell[direction] ?: cell
            coordinates += direction
            updateCellsSeen()
        }
    }

    fun getCellsSeen() = cellsSeen

    fun updateCellsSeen()
    {
        Direction.values().forEach {
            var seen = cell
            while(seen[it] != null)
            {
                seen = seen[it]!!
                if(!cellsSeen.contains(seen)) cellsSeen.add(seen)
            }

        }
    }
}

