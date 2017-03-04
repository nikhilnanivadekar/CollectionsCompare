package collections.compare.demo.game;

import org.junit.Test;

public class FishUsingGoogleGuavaTest extends AbstractFishTest
{
    @Test
    public void play_smallSet_Win()
    {
        FishUsingGoogleGuava fish = new FishUsingGoogleGuava(this.numberOfPlayersSmallSet, this.seedForWinSmallSet);
        this.playSmallSetWin(fish);
    }

    @Test
    public void play_smallSet_PondDry()
    {
        FishUsingGoogleGuava fish = new FishUsingGoogleGuava(this.numberOfPlayersSmallSet, this.seedForPondDrySmallSet);
        this.playSmallSetPondDry(fish);
    }
}
