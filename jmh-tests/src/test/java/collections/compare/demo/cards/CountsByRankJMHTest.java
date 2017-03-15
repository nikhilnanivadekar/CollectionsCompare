package collections.compare.demo.cards;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Multiset;
import org.apache.commons.collections4.MultiSet;
import org.eclipse.collections.api.bag.Bag;
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
public class CountsByRankJMHTest {
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
    public MultiSet<Rank> countsByRankApache(Deck deck) {
        return deck.apacheCommonsDeckOfCards.countsByRank();
    }

    @Benchmark
    public MultiSet<Rank> countsByRankApacheUnmodifiable(Deck deck) {
        return deck.apacheCommonsDeckOfCardsAsList.countsByRank();
    }

    @Benchmark
    public Bag<Rank> countsByRankEC(Deck deck) {
        return deck.eclipseCollectionsDeckOfCards.countsByRank();
    }

    @Benchmark
    public Bag<Rank> countsByRankECImmutable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.countsByRank();
    }

    @Benchmark
    public Bag<Rank> countsByRankECReadable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.countsByRank();
    }

    @Benchmark
    public Multiset<Rank> countsByRankGuava(Deck deck) {
        return deck.googleGuavaDeckOfCards.countsByRank();
    }

    @Benchmark
    public Multiset<Rank> countsByRankGuavaImmutable(Deck deck) {
        return deck.googleGuavaDeckOfCardsAsImmutableList.countsByRank();
    }

    @Benchmark
    public Map<Rank, Long> countsByRankJDK(Deck deck) {
        return deck.jdk8DeckOfCards.countsByRank();
    }

    @Benchmark
    public Map<Rank, Long> countsByRankJDKUnmodifiable(Deck deck) {
        return deck.jdk8DeckOfCardsAsList.countsByRank();
    }

    @Benchmark
    public Map<Rank, Long> countsByRankJavaslang(Deck deck) {
        return deck.javaslangDeckOfCards.countsByRank();
    }

    @Benchmark
    public Map<Rank, Long> countsByRankJavaslangImmutable(Deck deck) {
        return deck.javaslangDeckOfCardsAsImmutableList.countsByRank();
    }
}
