package collections.compare.demo.game;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FishTest.class);

    private final int numberOfPlayersSmallSet = 4;
    private final long seed1 = 87654321L;
    private final long seed2 = 1234567890L;

    @Test
    public void playSmallSet1()
    {
        FishUsingEclipseCollections ec = new FishUsingEclipseCollections(numberOfPlayersSmallSet, seed1);
        FishUsingGoogleGuava gg = new FishUsingGoogleGuava(numberOfPlayersSmallSet, seed1);

        ec.deal();
        gg.deal();

        Outcome ecOutcome = this.playFish(ec);
        Outcome ggOutcome = this.playFish(gg);
        Assert.assertEquals(ecOutcome, ggOutcome);
    }

    @Test
    public void playSmallSet2()
    {
        FishUsingEclipseCollections ec = new FishUsingEclipseCollections(numberOfPlayersSmallSet, seed2);
        FishUsingGoogleGuava gg = new FishUsingGoogleGuava(numberOfPlayersSmallSet, seed2);

        ec.deal();
        gg.deal();

        Outcome ecOutcome = this.playFish(ec);
        Outcome ggOutcome = this.playFish(gg);
        Assert.assertEquals(ecOutcome, ggOutcome);
    }

    private Outcome playFish(Fish game)
    {
        LOGGER.info("Playing Fish using {}", game.getClass());
        boolean playFish = true;
        while (playFish)
        {
            for (int i = 1; i <= this.numberOfPlayersSmallSet; i++)
            {
                playFish = game.playTurn(i);
                if (!playFish)
                {
                    LOGGER.info("Finished playing Fish using {}. Outcome:{}", game.getClass(), game.getOutcome());
                    return game.getOutcome();
                }
            }
        }
        throw new IllegalStateException("Game did not end properly!");
    }
}