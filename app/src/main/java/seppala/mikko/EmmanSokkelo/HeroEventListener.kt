package seppala.mikko.EmmanSokkelo

interface HeroEventListener
{
    fun onHeroMove(oldCell: MazeCell, newCell: MazeCell)
}