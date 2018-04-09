package seppala.mikko.EmmanSokkelo

/**
 * Created by Mikko on 2018-04-08.
 */
enum class Direction
{
    NORTH   { override fun invert() = SOUTH},
    EAST    { override fun invert() = WEST},
    SOUTH   { override fun invert() = NORTH},
    WEST    { override fun invert() = EAST};

    abstract fun invert(): Direction
    operator fun unaryMinus(): Direction = invert()
}