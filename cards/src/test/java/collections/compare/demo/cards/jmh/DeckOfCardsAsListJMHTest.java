package collections.compare.demo.cards.jmh;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.ApacheCommonsDeckOfCardsAsList;
import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCardsAsImmutableList;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.GoogleGuavaDeckOfCardsAsImmutableList;
import collections.compare.demo.cards.JDK8DeckOfCardsAsList;
import collections.compare.demo.cards.JavaSlangDeckOfCardsAsImmutableList;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
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
@Fork(2)
public class DeckOfCardsAsListJMHTest {
    @Benchmark
    public List<Set<Card>> dealJDKUnmodifiable() {
        JDK8DeckOfCardsAsList deck = new JDK8DeckOfCardsAsList();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealGuavaImmutable() {
        GoogleGuavaDeckOfCardsAsImmutableList deck = new GoogleGuavaDeckOfCardsAsImmutableList();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealApacheUnmodifiable() {
        ApacheCommonsDeckOfCardsAsList deck = new ApacheCommonsDeckOfCardsAsList();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public javaslang.collection.List<javaslang.collection.Set<Card>> dealJavaslang() {
        JavaSlangDeckOfCardsAsImmutableList deck = new JavaSlangDeckOfCardsAsImmutableList();
        javaslang.collection.List<javaslang.collection.Set<Card>> hands =
                deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public ImmutableList<Set<Card>> dealECImmutable() {
        EclipseCollectionsDeckOfCardsAsImmutableList deck = new EclipseCollectionsDeckOfCardsAsImmutableList();
        ImmutableList<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }

    @Benchmark
    public ListIterable<Set<Card>> dealECReadable() {
        EclipseCollectionsDeckOfCardsAsReadableList deck = new EclipseCollectionsDeckOfCardsAsReadableList();
        ListIterable<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(5, hands.size());
        return hands;
    }
}
