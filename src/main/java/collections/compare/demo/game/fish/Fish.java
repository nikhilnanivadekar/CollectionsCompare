package collections.compare.demo.game.fish;

import collections.compare.demo.game.Outcome;

public abstract class Fish
{
    protected abstract Outcome getOutcome();

    protected abstract void deal();

    protected abstract boolean playTurn(int playerNumber);

    protected int getNumberOfCardsPerPlayer(int numberOfPlayers)
    {
        if (numberOfPlayers < 5)
        {
            return 7;
        }
        return 5;
    }
}
