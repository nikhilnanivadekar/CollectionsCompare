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
public class CountsByRankTest
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
    public int countsByRankApache(Deck deck)
    {
        return deck.apacheCommonsDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankApacheUnmodifiable(Deck deck)
    {
        return deck.apacheCommonsDeckOfCardsAsList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankEC(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankECImmutable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankECReadable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankGuava(Deck deck)
    {
        return deck.googleGuavaDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankGuavaImmutable(Deck deck)
    {
        return deck.googleGuavaDeckOfCardsAsImmutableList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJDK(Deck deck)
    {
        return deck.jdk8DeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJDKUnmodifiable(Deck deck)
    {
        return deck.jdk8DeckOfCardsAsList.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJavaslang(Deck deck)
    {
        return deck.javaslangDeckOfCards.countsByRank()
                .size();
    }

    @Benchmark
    public int countsByRankJavaslangImmutable(Deck deck)
    {
        return deck.javaslangDeckOfCardsAsImmutableList.countsByRank()
                .size();
    }
}
