package seppala.mikko.EmmanSokkelo
import org.junit.Test
import org.junit.Assert.*

class MazeUnitTests {
    @Test
    fun maze_construction() {
        val m = Maze(Size(10,4))

        print(m.toString())
        assertTrue(true)
    }

    @Test
    fun maze_randPrimGeneration()
    {
        val g = RandomizedPrimMazeGenerator()
        val m = g.generate(Size(40, 20))
        print(m.toString())

        assertTrue(true)
    }
}
