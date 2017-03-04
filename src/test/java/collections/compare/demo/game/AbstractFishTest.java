package collections.compare.demo.game;

import org.junit.Assert;

public abstract class AbstractFishTest
{
    protected int numberOfPlayersSmallSet = 4;
    protected long seedForWinSmallSet = 87654321L;
    protected long seedForPondDrySmallSet = 1234567890L;

    protected void playSmallSetWin(Fish game)
    {
        game.deal();

        boolean playFish = true;
        while (playFish)
        {
            for (int i = 1; i <= this.numberOfPlayersSmallSet; i++)
            {
                playFish = game.playTurn(i);
                if (!playFish)
                {
                    Assert.assertEquals(Outcome.WINNER, game.getOutcome());
                    return;
                }
            }
        }
    }

    protected void playSmallSetPondDry(Fish game)
    {
        game.deal();

        boolean playFish = true;
        while (playFish)
        {
            for (int i = 1; i <= this.numberOfPlayersSmallSet; i++)
            {
                playFish = game.playTurn(i);
                if (!playFish)
                {
                    Assert.assertEquals(Outcome.POND_DRY, game.getOutcome());
                    return;
                }
            }
        }
    }
}
