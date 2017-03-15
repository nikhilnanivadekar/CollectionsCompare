package collections.compare.demo.demo.cards;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.JDK8DeckOfCards;
import collections.compare.demo.cards.JavaSlangDeckOfCards;
import org.eclipse.collections.api.list.ImmutableList;
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
@Fork(1)
public class DeckOfCardsJMHTest {
    @Benchmark
    public List<Set<Card>> dealJDK() {
        JDK8DeckOfCards deck = new JDK8DeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public ImmutableList<Set<Card>> dealEC() {
        EclipseCollectionsDeckOfCards deck = new EclipseCollectionsDeckOfCards();
        ImmutableList<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealGuava() {
        GoogleGuavaDeckOfCards deck = new GoogleGuavaDeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public List<Set<Card>> dealApache() {
        ApacheCommonsDeckOfCards deck = new ApacheCommonsDeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }

    @Benchmark
    public javaslang.collection.List<javaslang.collection.Set<Card>> dealJavaslang() {
        JavaSlangDeckOfCards deck = new JavaSlangDeckOfCards();
        javaslang.collection.List<javaslang.collection.Set<Card>> hands =
                deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands;
    }
}
