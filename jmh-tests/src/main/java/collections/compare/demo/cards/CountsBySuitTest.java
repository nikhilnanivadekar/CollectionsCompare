package collections.compare.demo.cards;

import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.list.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.list.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.list.JDK8DeckOfCards;
import collections.compare.demo.cards.list.VavrDeckOfCards;
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
public class CountsBySuitTest
{
    @State(Scope.Thread)
    public static class Deck
    {
        public collections.compare.demo.cards.sortedset.ApacheCommonsDeckOfCards apacheCommonsDeckOfCards = new collections.compare.demo.cards.sortedset.ApacheCommonsDeckOfCards();
        public ApacheCommonsDeckOfCards apacheCommonsDeckOfCardsAsList = new ApacheCommonsDeckOfCards();
        public collections.compare.demo.cards.sortedset.EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCards = new collections.compare.demo.cards.sortedset.EclipseCollectionsDeckOfCards();
        public EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCardsAsImmutableList = new EclipseCollectionsDeckOfCards();
        public EclipseCollectionsDeckOfCardsAsReadableList eclipseCollectionsDeckOfCardsAsReadableList = new EclipseCollectionsDeckOfCardsAsReadableList();
        public collections.compare.demo.cards.sortedset.GoogleGuavaDeckOfCards googleGuavaDeckOfCards = new collections.compare.demo.cards.sortedset.GoogleGuavaDeckOfCards();
        public GoogleGuavaDeckOfCards googleGuavaDeckOfCardsAsImmutableList = new GoogleGuavaDeckOfCards();
        public collections.compare.demo.cards.sortedset.JDK8DeckOfCards jdk8DeckOfCards = new collections.compare.demo.cards.sortedset.JDK8DeckOfCards();
        public JDK8DeckOfCards jdk8DeckOfCardsAsList = new JDK8DeckOfCards();
        public collections.compare.demo.cards.sortedset.VavrDeckOfCards javaslangDeckOfCards = new collections.compare.demo.cards.sortedset.VavrDeckOfCards();
        public VavrDeckOfCards javaslangDeckOfCardsAsImmutableList = new VavrDeckOfCards();
    }

    @Benchmark
    public int countsBySuitApache(Deck deck)
    {
        return deck.apacheCommonsDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitApacheUnmodifiable(Deck deck)
    {
        return deck.apacheCommonsDeckOfCardsAsList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitEC(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitECImmutable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitECReadable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitGuava(Deck deck)
    {
        return deck.googleGuavaDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitGuavaImmutable(Deck deck)
    {
        return deck.googleGuavaDeckOfCardsAsImmutableList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJDK(Deck deck)
    {
        return deck.jdk8DeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJDKUnmodifiable(Deck deck)
    {
        return deck.jdk8DeckOfCardsAsList.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJavaslang(Deck deck)
    {
        return deck.javaslangDeckOfCards.countsBySuit()
                .size();
    }

    @Benchmark
    public int countsBySuitJavaslangImmutable(Deck deck)
    {
        return deck.javaslangDeckOfCardsAsImmutableList.countsBySuit()
                .size();
    }
}
