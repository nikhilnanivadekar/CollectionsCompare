package collections.compare.demo.cards;

import java.util.concurrent.TimeUnit;

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
public class CountsBySuitTest {
    @State(Scope.Thread)
    public static class Deck {
        public ApacheCommonsDeckOfCards apacheCommonsDeckOfCards = new ApacheCommonsDeckOfCards();
        public ApacheCommonsDeckOfCardsAsList apacheCommonsDeckOfCardsAsList = new ApacheCommonsDeckOfCardsAsList();
        public EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCards = new EclipseCollectionsDeckOfCards();
        public EclipseCollectionsDeckOfCardsAsImmutableList eclipseCollectionsDeckOfCardsAsImmutableList = new EclipseCollectionsDeckOfCardsAsImmutableList();
        public EclipseCollectionsDeckOfCardsAsReadableList eclipseCollectionsDeckOfCardsAsReadableList = new EclipseCollectionsDeckOfCardsAsReadableList();
        public GoogleGuavaDeckOfCards googleGuavaDeckOfCards = new GoogleGuavaDeckOfCards();
        public GoogleGuavaDeckOfCardsAsImmutableList googleGuavaDeckOfCardsAsImmutableList = new GoogleGuavaDeckOfCardsAsImmutableList();
        public JDK8DeckOfCards jdk8DeckOfCards = new JDK8DeckOfCards();
        public JDK8DeckOfCardsAsList jdk8DeckOfCardsAsList = new JDK8DeckOfCardsAsList();
        public JavaSlangDeckOfCards javaslangDeckOfCards = new JavaSlangDeckOfCards();
        public JavaSlangDeckOfCardsAsImmutableList javaslangDeckOfCardsAsImmutableList = new JavaSlangDeckOfCardsAsImmutableList();
    }

    @Benchmark
    public int countsBySuitApache(Deck deck) {
        return deck.apacheCommonsDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitApacheUnmodifiable(Deck deck) {
        return deck.apacheCommonsDeckOfCardsAsList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitEC(Deck deck) {
        return deck.eclipseCollectionsDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitECImmutable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitECReadable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitGuava(Deck deck) {
        return deck.googleGuavaDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitGuavaImmutable(Deck deck) {
        return deck.googleGuavaDeckOfCardsAsImmutableList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJDK(Deck deck) {
        return deck.jdk8DeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJDKUnmodifiable(Deck deck) {
        return deck.jdk8DeckOfCardsAsList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJavaslang(Deck deck) {
        return deck.javaslangDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJavaslangImmutable(Deck deck) {
        return deck.javaslangDeckOfCardsAsImmutableList.countsBySuit()
                .size();
    }
}
