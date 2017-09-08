package collections.compare.demo.cards;

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import collections.compare.demo.cards.list.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.list.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.list.JDK8DeckOfCards;
import collections.compare.demo.cards.list.VavrDeckOfCards;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.SetValuedMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@SuppressWarnings("RedundantCast")
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class GroupByTest
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
    public int groupByApache(Deck deck)
    {
        SetValuedMap<Suit, Card> cbs = MultiMapUtils.newSetValuedHashMap();
        deck.apacheCommonsDeckOfCards.getCards().forEach(card -> cbs.put(card.getSuit(), card));
        return cbs.size();
    }

    @Benchmark
    public int groupByApacheUnmodifiable(Deck deck)
    {
        ListValuedMap<Suit, Card> cbs = MultiMapUtils.newListValuedHashMap();
        deck.apacheCommonsDeckOfCardsAsList.getCards()
                .forEach(card -> cbs.put(card.getSuit(), card));
        return MultiMapUtils.unmodifiableMultiValuedMap(cbs).size();
    }

    @Benchmark
    public int groupByEC(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCards.getCards()
                .groupBy(Card::getSuit)
                .size();
    }

    @Benchmark
    public int groupByECImmutable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.getCards()
                .groupBy(Card::getSuit)
                .size();
    }

    @Benchmark
    public int groupByECReadable(Deck deck)
    {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.getCards()
                .groupBy(Card::getSuit)
                .size();
    }

    @Benchmark
    public int groupByGuava(Deck deck)
    {
        ImmutableSetMultimap.Builder<Suit, Card> builder =
                new ImmutableSetMultimap.Builder<Suit, Card>().orderValuesBy(Comparator.naturalOrder());
        deck.googleGuavaDeckOfCards.getCards()
                .forEach(card -> builder.put(card.getSuit(), card));
        return builder.build().size();
    }

    @Benchmark
    public int groupByGuavaImmutable(Deck deck)
    {
        return Multimaps.index(deck.googleGuavaDeckOfCardsAsImmutableList.getCards(),
                (Function<Card, Suit>) Card::getSuit)
                .size();
    }

    @Benchmark
    public int groupByJDK(Deck deck)
    {
        return Collections.unmodifiableMap(
                deck.jdk8DeckOfCards.getCards()
                        .stream()
                        .collect(Collectors.groupingBy(
                                Card::getSuit,
                                Collectors.mapping(java.util.function.Function.identity(),
                                        Collectors.collectingAndThen(
                                                Collectors.toCollection(TreeSet::new),
                                                Collections::unmodifiableSortedSet)))))
                .size();
    }

    @Benchmark
    public int groupByJDKUnmodifiable(Deck deck)
    {
        return deck.jdk8DeckOfCardsAsList.getCards()
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Card::getSuit,
                                Collectors.mapping(java.util.function.Function.identity(),
                                        Collectors.collectingAndThen(
                                                Collectors.toList(),
                                                Collections::unmodifiableList))),
                        Collections::unmodifiableMap))
                .size();
    }

    @Benchmark
    public int groupByJavaslang(Deck deck)
    {
        return deck.javaslangDeckOfCards.getCards().groupBy(Card::getSuit).size();
    }

    @Benchmark
    public int groupByJavaslangImmutable(Deck deck)
    {
        return deck.javaslangDeckOfCardsAsImmutableList.getCards().groupBy(Card::getSuit).size();
    }
}
