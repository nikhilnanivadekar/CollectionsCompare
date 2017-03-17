package collections.compare.demo.cards;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
@Fork(2)
public class DeckOfCardsTest {
    @Benchmark
    public int dealJDK() {
        JDK8DeckOfCards deck = new JDK8DeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands.size();
    }

    @Benchmark
    public int dealEC() {
        EclipseCollectionsDeckOfCards deck = new EclipseCollectionsDeckOfCards();
        ImmutableList<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands.size();
    }

    @Benchmark
    public int dealGuava() {
        GoogleGuavaDeckOfCards deck = new GoogleGuavaDeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands.size();
    }

    @Benchmark
    public int dealApache() {
        ApacheCommonsDeckOfCards deck = new ApacheCommonsDeckOfCards();
        List<Set<Card>> hands = deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands.size();
    }

    @Benchmark
    public int dealJavaslang() {
        JavaSlangDeckOfCards deck = new JavaSlangDeckOfCards();
        javaslang.collection.List<javaslang.collection.Set<Card>> hands =
                deck.shuffleAndDeal(new Random(1), 5, 5);
        return hands.size();
    }
}
