package seppala.mikko.EmmanSokkelo

import java.util.*

class MazeGeneratorRandomizedPrim
{
    private var walls = ArrayList<Wall>()
    private val visited = ArrayList<MazeCell>()
    private val rndGen = Random(System.currentTimeMillis())

    private lateinit var maze : Maze

    private fun<E> List<E>.random(random: java.util.Random): E? = if (size > 0) get(random.nextInt(size)) else null

    class Wall(val cell1: MazeCell, val cell2: MazeCell, private val dir: Direction)
    {
        fun connect() { cell1[dir] = cell2 }
    }

    fun generate(size: Size): Maze
    {
        walls.clear()
        visited.clear()

        //1. Start with a grid full of walls.
        maze = Maze(size)

        //2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
        val cellToAdd = maze[0,0]
        var lastAdded = cellToAdd
        visited.add(cellToAdd)
        cellToAdd.getWallDirections().forEach{addToWalls(cellToAdd, it)}

        //3. pick a random wall to process until done
        while(walls.size > 0) { lastAdded = processWall(walls.random(rndGen)!!) ?: lastAdded }

        //4. set goal to last added cell
        lastAdded.type=MazeCell.Type.GOAL
        maze.hero.updateCellsSeen()

        return maze
    }

    private fun processWall(wall : Wall): MazeCell?
    {
        var cellToAdd: MazeCell? = null
        val cell1Visited = visited.contains(wall.cell1)

        if(cell1Visited != visited.contains(wall.cell2)) //If only one of the cells is visited
        {
            cellToAdd = if(cell1Visited) wall.cell2 else wall.cell1

            //Connect the cells of this wall and mark the unvisited cell as part of the maze.
            wall.connect()
            visited.add(cellToAdd)

            //Add the neighboring walls of the cell to the wall list.
            cellToAdd.getWallDirections().forEach{ addToWalls(cellToAdd, it) }
        }

        //3. Remove the wall from the list.
        walls.remove(wall)
        return cellToAdd
    }


    private fun addToWalls(cell: MazeCell, dir: Direction)
    {
        try
        {
            val wall = Wall(cell, maze.getCellNeighbour(cell, dir), dir)
            if(!walls.contains(wall)) walls.add(wall)
        }
        catch (e: ArrayIndexOutOfBoundsException) { /*no neighbour in that direction*/ }
    }
}