package collections.compare.demo.cards;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.list.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.list.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.list.JDK8DeckOfCards;
import collections.compare.demo.cards.list.VavrDeckOfCards;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
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
@Fork(2)
public class DeckOfCardsAsListTest
{
    @Benchmark
    public List<Set<Card>> dealJDKUnmodifiable()
    {
        JDK8DeckOfCards deck = new JDK8DeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealGuavaImmutable()
    {
        GoogleGuavaDeckOfCards deck = new GoogleGuavaDeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealApacheUnmodifiable()
    {
        ApacheCommonsDeckOfCards deck = new ApacheCommonsDeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public io.vavr.collection.List<io.vavr.collection.Set<Card>> dealJavaslang()
    {
        VavrDeckOfCards deck = new VavrDeckOfCards();
        io.vavr.collection.List<io.vavr.collection.Set<Card>> hands =
                deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public ImmutableList<Set<Card>> dealECImmutable()
    {
        EclipseCollectionsDeckOfCards deck = new EclipseCollectionsDeckOfCards();
        ImmutableList<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public ListIterable<Set<Card>> dealECReadable()
    {
        EclipseCollectionsDeckOfCardsAsReadableList deck = new EclipseCollectionsDeckOfCardsAsReadableList();
        ListIterable<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }
}
