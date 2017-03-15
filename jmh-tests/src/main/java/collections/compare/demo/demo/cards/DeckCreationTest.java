package collections.compare.demo.demo.cards;

import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.ApacheCommonsDeckOfCardsAsList;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCardsAsImmutableList;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.GoogleGuavaDeckOfCardsAsImmutableList;
import collections.compare.demo.cards.JDK8DeckOfCards;
import collections.compare.demo.cards.JDK8DeckOfCardsAsList;
import collections.compare.demo.cards.JavaSlangDeckOfCards;
import collections.compare.demo.cards.JavaSlangDeckOfCardsAsImmutableList;
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
public class DeckCreationTest {
    @Benchmark
    public ApacheCommonsDeckOfCards deckApache() {
        return new ApacheCommonsDeckOfCards();
    }

    @Benchmark
    public ApacheCommonsDeckOfCardsAsList deckApacheUnmodifiable() {
        return new ApacheCommonsDeckOfCardsAsList();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCards deckEC() {
        return new EclipseCollectionsDeckOfCards();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCardsAsImmutableList deckECImmutable() {
        return new EclipseCollectionsDeckOfCardsAsImmutableList();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCardsAsReadableList deckECReadable() {
        return new EclipseCollectionsDeckOfCardsAsReadableList();
    }

    @Benchmark
    public GoogleGuavaDeckOfCards deckGuava() {
        return new GoogleGuavaDeckOfCards();
    }

    @Benchmark
    public GoogleGuavaDeckOfCardsAsImmutableList deckGuavaImmutable() {
        return new GoogleGuavaDeckOfCardsAsImmutableList();
    }

    @Benchmark
    public JDK8DeckOfCards deckJDK() {
        return new JDK8DeckOfCards();
    }

    @Benchmark
    public JDK8DeckOfCardsAsList deckJDKUnmodifiable() {
        return new JDK8DeckOfCardsAsList();
    }

    @Benchmark
    public JavaSlangDeckOfCards deckJavaslang() {
        return new JavaSlangDeckOfCards();
    }

    @Benchmark
    public JavaSlangDeckOfCardsAsImmutableList deckJavaslangImmutable() {
        return new JavaSlangDeckOfCardsAsImmutableList();
    }
}
