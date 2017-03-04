package collections.compare.demo.game;

import org.junit.Test;

public class FishUsingEclipseCollectionsTest extends AbstractFishTest
{
    @Test
    public void play_smallSet_Win()
    {
        FishUsingEclipseCollections fish = new FishUsingEclipseCollections(this.numberOfPlayersSmallSet, this.seedForWinSmallSet);
        this.playSmallSetWin(fish);
    }

    @Test
    public void play_smallSet_PondDry()
    {
        FishUsingEclipseCollections fish = new FishUsingEclipseCollections(this.numberOfPlayersSmallSet, this.seedForPondDrySmallSet);
        this.playSmallSetPondDry(fish);
    }
}
