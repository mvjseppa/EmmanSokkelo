package seppala.mikko.EmmanSokkelo

/**
 * Created by Mikko on 2018-04-08.
 */
class Point(var x: Int, var y: Int)
{
    operator fun plus(p :Point) = Point(x + p.x, y + p.y)
    operator fun plus(n: Int) = Point(x+n, y+n)
    operator fun times(n : Int) = Point(x*n, y*n)
    operator fun div(n : Int) = Point(x/n, y/n)

    operator fun unaryMinus() = Point(-x, -y)
    operator fun minus(p :Point) = this + (-p)

    operator fun plus(dir: Direction) : Point
    {
        return when(dir)
        {
            Direction.NORTH -> Point(x, y - 1)
            Direction.WEST  -> Point(x - 1, y)
            Direction.SOUTH -> Point(x, y + 1)
            Direction.EAST  -> Point(x + 1, y)
        }
    }

    operator fun minus(dir: Direction) : Point = this + dir.invert()
}