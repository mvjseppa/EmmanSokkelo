package seppala.mikko.EmmanSokkelo

/**
 * Created by Mikko on 2018-04-08.
 */
class MazeCell(var type: MazeCell.Type)
{
    enum class Type{START, GOAL, NORMAL}

    private var neighbours: Array<MazeCell?> = arrayOf(null,null,null,null)
    var coordinates = Point(0,0)


    operator fun get(dir: Direction): MazeCell?
    {
        return neighbours[dir.ordinal]
    }

    operator fun set(dir: Direction, cell: MazeCell)
    {
        val invDir = dir.invert()
        if(this[dir] == null && cell[invDir] == null)
        {
            neighbours[dir.ordinal] = cell
            cell.neighbours[invDir.ordinal] = this
        }
        else
        {
            throw IllegalAccessException("Neighbour in this direction already exists!")
        }
    }

    fun getWallDirections() = Direction.values().filter { this[it] == null }.toList()

}