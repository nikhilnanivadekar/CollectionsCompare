package collections.compare.demo.cards;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Multiset;
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
    public org.apache.commons.collections4.Bag<Suit> countsBySuitApache(Deck deck) {
        return deck.apacheCommonsDeckOfCards.countsBySuit();
    }

    @Benchmark
    public org.apache.commons.collections4.Bag<Suit> countsBySuitApacheUnmodifiable(Deck deck) {
        return deck.apacheCommonsDeckOfCardsAsList.countsBySuit();
    }

    @Benchmark
    public Bag<Suit> countsBySuitEC(Deck deck) {
        return deck.eclipseCollectionsDeckOfCards.countsBySuit();
    }

    @Benchmark
    public Bag<Suit> countsBySuitECImmutable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.countsBySuit();
    }

    @Benchmark
    public Bag<Suit> countsBySuitECReadable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.countsBySuit();
    }

    @Benchmark
    public Multiset<Suit> countsBySuitGuava(Deck deck) {
        return deck.googleGuavaDeckOfCards.countsBySuit();
    }

    @Benchmark
    public Multiset<Suit> countsBySuitGuavaImmutable(Deck deck) {
        return deck.googleGuavaDeckOfCardsAsImmutableList.countsBySuit();
    }

    @Benchmark
    public Map<Suit, Long> countsBySuitJDK(Deck deck) {
        return deck.jdk8DeckOfCards.countsBySuit();
    }

    @Benchmark
    public Map<Suit, Long> countsBySuitJDKUnmodifiable(Deck deck) {
        return deck.jdk8DeckOfCardsAsList.countsBySuit();
    }

    @Benchmark
    public Map<Suit, Long> countsBySuitJavaslang(Deck deck) {
        return deck.javaslangDeckOfCards.countsBySuit();
    }

    @Benchmark
    public Map<Suit, Long> countsBySuitJavaslangImmutable(Deck deck) {
        return deck.javaslangDeckOfCardsAsImmutableList.countsBySuit();
    }
}
