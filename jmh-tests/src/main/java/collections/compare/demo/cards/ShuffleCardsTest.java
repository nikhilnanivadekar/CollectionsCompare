package collections.compare.demo.cards;

import java.util.Random;
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
public class ShuffleCardsTest
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
    public int shuffleApache(Deck deck)
    {
        return deck.apacheCommonsDeckOfCards.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleApacheUnmodifiable(Deck deck)
    {
        return deck.apacheCommonsDeckOfCardsAsList.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleEC(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCards.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleECImmutable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleECReadable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleGuava(Deck deck)
    {
        return deck.googleGuavaDeckOfCards.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleGuavaImmutable(Deck deck)
    {
        return deck.googleGuavaDeckOfCardsAsImmutableList.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleJDK(Deck deck)
    {
        return deck.jdk8DeckOfCards.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public int shuffleJDKUnmodifiable(Deck deck)
    {
        return deck.jdk8DeckOfCardsAsList.shuffle(new Random(123456789L))
                .size();
    }

    @Benchmark
    public boolean shuffleJavaslang(Deck deck)
    {
        return deck.javaslangDeckOfCards.shuffle(new Random(123456789L))
                .isEmpty();
    }

    @Benchmark
    public boolean shuffleJavaslangImmutable(Deck deck)
    {
        return deck.javaslangDeckOfCardsAsImmutableList.shuffle(new Random(123456789L))
                .isEmpty();
    }
}
