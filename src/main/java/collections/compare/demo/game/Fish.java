package collections.compare.demo.game;

public abstract class Fish
{
    protected int getNumberOfCardsPerPlayer(int numberOfPlayers)
    {
        if (numberOfPlayers < 5)
        {
            return 7;
        }
        return 5;
    }
}
