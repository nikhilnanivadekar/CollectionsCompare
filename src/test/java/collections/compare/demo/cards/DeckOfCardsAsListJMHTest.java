package collections.compare.demo.cards;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.list.ImmutableList;
import org.junit.Assert;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(4)
public class DeckOfCardsAsListJMHTest
{
    @Benchmark
    public List<Set<Card>> dealJDK()
    {
        JDK8DeckOfCardsAsList deck = new JDK8DeckOfCardsAsList();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public ImmutableList<Set<Card>> dealEC()
    {
        EclipseCollectionsDeckOfCardsAsList deck = new EclipseCollectionsDeckOfCardsAsList();
        ImmutableList<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealGuava()
    {
        GoogleGuavaDeckOfCardsAsList deck = new GoogleGuavaDeckOfCardsAsList();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealApache()
    {
        ApacheCommonsDeckOfCardsAsList deck = new ApacheCommonsDeckOfCardsAsList();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public javaslang.collection.List<javaslang.collection.Set<Card>> dealJavaslang()
    {
        JavaSlangDeckOfCardsAsList deck = new JavaSlangDeckOfCardsAsList();
        javaslang.collection.List<javaslang.collection.Set<Card>> hands =
                deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }
}
