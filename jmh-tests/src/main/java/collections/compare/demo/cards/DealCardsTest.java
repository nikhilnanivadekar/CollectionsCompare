package collections.compare.demo.cards;

import java.util.Deque;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.list.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.list.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.list.JDK8DeckOfCards;
import collections.compare.demo.cards.list.VavrDeckOfCards;
import org.eclipse.collections.api.stack.MutableStack;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class DealCardsTest
{
    @State(Scope.Thread)
    public static class Deck
    {
        public collections.compare.demo.cards.sortedset.ApacheCommonsDeckOfCards apacheCommonsDeckOfCards;
        public ApacheCommonsDeckOfCards apacheCommonsDeckOfCardsAsList;
        public collections.compare.demo.cards.sortedset.EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCards;
        public EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCardsAsImmutableList;
        public EclipseCollectionsDeckOfCardsAsReadableList eclipseCollectionsDeckOfCardsAsReadableList;
        public collections.compare.demo.cards.sortedset.GoogleGuavaDeckOfCards googleGuavaDeckOfCards;
        public GoogleGuavaDeckOfCards googleGuavaDeckOfCardsAsImmutableList;
        public collections.compare.demo.cards.sortedset.JDK8DeckOfCards jdk8DeckOfCards;
        public JDK8DeckOfCards jdk8DeckOfCardsAsList;
        public collections.compare.demo.cards.sortedset.VavrDeckOfCards vavrDeckOfCards;
        public VavrDeckOfCards vavrDeckOfCardsAsImmutableList;

        public Deque<Card> shuffledApacheCommonsDeckOfCards;
        public Deque<Card> shuffledApachedCommonsDeckOfCardsAsList;
        public MutableStack<Card> shuffledEclipseCollectionsDeckOfCards;
        public MutableStack<Card> shuffledEclipseCollectionsDeckOfCardsAsImmutableList;
        public MutableStack<Card> shuffledEclipseCollectionsDeckOfCardsAsReadableList;
        public Deque<Card> shuffledGoogleGuavaDeckOfCards;
        public Deque<Card> shuffledGoogleGuavaDeckOfCardsAsImmutableList;
        public Deque<Card> shuffledJdk8DeckOfCards;
        public Deque<Card> shuffledJdk8DeckOfCardsAsList;
        public io.vavr.collection.List<Card> shuffledJavaslangDeckOfCards;
        public io.vavr.collection.List<Card> shuffledJavaslangDeckOfCardsAsImmutableList;

        @Setup(Level.Invocation)
        public void setup()
        {
            apacheCommonsDeckOfCards = new collections.compare.demo.cards.sortedset.ApacheCommonsDeckOfCards();
            apacheCommonsDeckOfCardsAsList = new ApacheCommonsDeckOfCards();
            eclipseCollectionsDeckOfCards = new collections.compare.demo.cards.sortedset.EclipseCollectionsDeckOfCards();
            eclipseCollectionsDeckOfCardsAsImmutableList = new EclipseCollectionsDeckOfCards();
            eclipseCollectionsDeckOfCardsAsReadableList = new EclipseCollectionsDeckOfCardsAsReadableList();
            googleGuavaDeckOfCards = new collections.compare.demo.cards.sortedset.GoogleGuavaDeckOfCards();
            googleGuavaDeckOfCardsAsImmutableList = new GoogleGuavaDeckOfCards();
            jdk8DeckOfCards = new collections.compare.demo.cards.sortedset.JDK8DeckOfCards();
            jdk8DeckOfCardsAsList = new JDK8DeckOfCards();
            vavrDeckOfCards = new collections.compare.demo.cards.sortedset.VavrDeckOfCards();
            vavrDeckOfCardsAsImmutableList = new VavrDeckOfCards();

            shuffledApacheCommonsDeckOfCards = apacheCommonsDeckOfCards.shuffle(new Random(123456789L));
            shuffledApachedCommonsDeckOfCardsAsList = apacheCommonsDeckOfCardsAsList.shuffle(new Random(123456789L));
            shuffledEclipseCollectionsDeckOfCards = eclipseCollectionsDeckOfCards.shuffle(new Random(123456789L));
            shuffledEclipseCollectionsDeckOfCardsAsImmutableList = eclipseCollectionsDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
            shuffledEclipseCollectionsDeckOfCardsAsReadableList = eclipseCollectionsDeckOfCardsAsReadableList.shuffle(new Random(123456789L));
            shuffledGoogleGuavaDeckOfCards = googleGuavaDeckOfCards.shuffle(new Random(123456789L));
            shuffledGoogleGuavaDeckOfCardsAsImmutableList = googleGuavaDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
            shuffledJdk8DeckOfCards = jdk8DeckOfCards.shuffle(new Random(123456789L));
            shuffledJdk8DeckOfCardsAsList = jdk8DeckOfCardsAsList.shuffle(new Random(123456789L));
            shuffledJavaslangDeckOfCards = vavrDeckOfCards.shuffle(new Random(123456789L));
            shuffledJavaslangDeckOfCardsAsImmutableList = vavrDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
        }
    }

    @Benchmark
    public int dealApache(Deck deck)
    {
        return deck.apacheCommonsDeckOfCards.dealHands(deck.shuffledApacheCommonsDeckOfCards, 5, 5)
                .size();
    }

    @Benchmark
    public int dealApacheUnmodifiable(Deck deck)
    {
        return deck.apacheCommonsDeckOfCardsAsList.dealHands(deck.shuffledApachedCommonsDeckOfCardsAsList, 5, 5)
                .size();
    }

    @Benchmark
    public int dealEC(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCards.dealHands(deck.shuffledEclipseCollectionsDeckOfCards, 5, 5)
                .size();
    }

    @Benchmark
    public int dealECImmutable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.dealHands(deck.shuffledEclipseCollectionsDeckOfCardsAsImmutableList, 5, 5)
                .size();
    }

    @Benchmark
    public int dealECReadable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.dealHands(deck.shuffledEclipseCollectionsDeckOfCardsAsReadableList, 5, 5)
                .size();
    }

    @Benchmark
    public int dealGuava(Deck deck)
    {
        return deck.googleGuavaDeckOfCards.dealHands(deck.shuffledGoogleGuavaDeckOfCards, 5, 5)
                .size();
    }

    @Benchmark
    public int dealGuavaImmutable(Deck deck)
    {
        return deck.googleGuavaDeckOfCardsAsImmutableList.dealHands(deck.shuffledGoogleGuavaDeckOfCardsAsImmutableList, 5, 5)
                .size();
    }

    @Benchmark
    public int dealJDK(Deck deck)
    {
        return deck.jdk8DeckOfCards.dealHands(deck.shuffledJdk8DeckOfCards, 5, 5)
                .size();
    }

    @Benchmark
    public int dealJDKUnmodifiable(Deck deck)
    {
        return deck.jdk8DeckOfCardsAsList.dealHands(deck.shuffledJdk8DeckOfCardsAsList, 5, 5)
                .size();
    }

    @Benchmark
    public int dealJavaslang(Deck deck)
    {
        return deck.vavrDeckOfCards.dealHands(deck.shuffledJavaslangDeckOfCards, 5, 5)
                .size();
    }

    @Benchmark
    public int dealJavaslangImmutable(Deck deck)
    {
        return deck.vavrDeckOfCardsAsImmutableList.dealHands(deck.shuffledJavaslangDeckOfCardsAsImmutableList, 5, 5)
                .size();
    }
}
