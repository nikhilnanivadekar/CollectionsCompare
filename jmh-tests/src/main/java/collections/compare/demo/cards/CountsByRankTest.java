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
public class CountsByRankTest {
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
    public int countsByRankApache(Deck deck) {
        return deck.apacheCommonsDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankApacheUnmodifiable(Deck deck) {
        return deck.apacheCommonsDeckOfCardsAsList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankEC(Deck deck) {
        return deck.eclipseCollectionsDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankECImmutable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankECReadable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankGuava(Deck deck) {
        return deck.googleGuavaDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankGuavaImmutable(Deck deck) {
        return deck.googleGuavaDeckOfCardsAsImmutableList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJDK(Deck deck) {
        return deck.jdk8DeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJDKUnmodifiable(Deck deck) {
        return deck.jdk8DeckOfCardsAsList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJavaslang(Deck deck) {
        return deck.javaslangDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJavaslangImmutable(Deck deck) {
        return deck.javaslangDeckOfCardsAsImmutableList.countsByRank()
                .size();
    }
}
