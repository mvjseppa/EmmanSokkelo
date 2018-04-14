package seppala.mikko.EmmanSokkelo

import java.util.*

/**
 * Created by Mikko on 2018-04-01.
 */

class RandomizedPrimMazeGenerator
{
    private var walls = ArrayList<Wall>()
    private val visited = ArrayList<MazeCell>()
    private val rndGen = Random(System.currentTimeMillis())

    private fun <E> List<E>.random(random: java.util.Random): E? = if (size > 0) get(random.nextInt(size)) else null

    class Wall(val cell1: MazeCell, val cell2: MazeCell, private val dir: Direction)
    {
        fun connect() { cell1[dir] = cell2 }
    }

    private fun addToWalls(cell: MazeCell, maze: Maze)
    {
        for(dir in cell.getWalls())
        {
            try
            {
                val wall = Wall(cell, maze.getCursorNeighbour(dir), dir)
                if(!walls.contains(wall)) walls.add(wall)
            }
            catch (e: ArrayIndexOutOfBoundsException) { }
        }
    }

    fun generate(size: Size): Maze
    {
        walls.clear()
        visited.clear()

        //1. Start with a grid full of walls.
        val maze = Maze(size)

        //2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
        val cellToAdd = maze[0,0]
        var lastAdded = cellToAdd
        visited.add(cellToAdd)
        addToWalls(cellToAdd, maze)

        //3. process walls until done
        while(walls.size > 0) { lastAdded = processWall(maze) ?: lastAdded }

        //4. set goal to last added cell
        lastAdded.type=MazeCell.Type.GOAL

        maze.hero.updateCellsSeen()

        return maze
    }

    private fun processWall(maze: Maze): MazeCell?
    {
        var cellToAdd: MazeCell? = null

        //1. Pick a random wall from the list.
        val wall = walls.random(rndGen)!!

        //2. If only one of the two cells that the wall divides is visited, then:
        val cell1Found = visited.contains(wall.cell1)
        val cell2Found = visited.contains(wall.cell2)

        if(cell1Found xor cell2Found)
        {
            cellToAdd = if(!cell1Found) wall.cell1 else wall.cell2

            //1. Make the wall a passage and mark the unvisited cell as part of the maze.
            wall.connect()
            visited.add(cellToAdd)
            maze.setCursor(cellToAdd)

            //2. Add the neighboring walls of the cell to the wall list.
            addToWalls(cellToAdd, maze)
        }

        //3. Remove the wall from the list.
        walls.remove(wall)
        return cellToAdd
    }

}