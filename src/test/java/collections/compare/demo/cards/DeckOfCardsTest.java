package collections.compare.demo.cards;

import java.net.PortUnreachableException;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSetMultimap;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.sortedset.ImmutableSortedSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

public class DeckOfCardsTest
{
    private EclipseCollectionsDeckOfCards ecDeck = new EclipseCollectionsDeckOfCards();
    private JDK8DeckOfCards jdkDeck = new JDK8DeckOfCards();
    private GoogleGuavaDeckOfCards ggDeck = new GoogleGuavaDeckOfCards();
    private ApacheCommonsDeckOfCards acDeck = new ApacheCommonsDeckOfCards();

    @Test
    public void allCards()
    {
        Assert.assertEquals(ecDeck.getCards(), jdkDeck.getCards());
        Assert.assertEquals(jdkDeck.getCards(), ggDeck.getCards());
        Assert.assertEquals(ggDeck.getCards(), ecDeck.getCards());
        Assert.assertEquals(ecDeck.getCards(), acDeck.getCards());
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(ecDeck.diamonds(), jdkDeck.diamonds());
        Assert.assertEquals(jdkDeck.diamonds(), ggDeck.diamonds());
        Assert.assertEquals(ggDeck.diamonds(), ecDeck.diamonds());
        Assert.assertEquals(ecDeck.diamonds(), acDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(ecDeck.hearts(), jdkDeck.hearts());
        Assert.assertEquals(jdkDeck.hearts(), ggDeck.hearts());
        Assert.assertEquals(ggDeck.hearts(), ecDeck.hearts());
        Assert.assertEquals(ecDeck.hearts(), acDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(ecDeck.spades(), jdkDeck.spades());
        Assert.assertEquals(jdkDeck.spades(), ggDeck.spades());
        Assert.assertEquals(ggDeck.spades(), ecDeck.spades());
        Assert.assertEquals(ecDeck.spades(), acDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(ecDeck.clubs(), jdkDeck.clubs());
        Assert.assertEquals(jdkDeck.clubs(), ggDeck.clubs());
        Assert.assertEquals(ggDeck.clubs(), ecDeck.clubs());
        Assert.assertEquals(ecDeck.clubs(), acDeck.clubs());
    }

    @Test
    public void deal()
    {
        MutableStack<Card> ecShuffle = ecDeck.shuffle(new Random(1));
        Deque<Card> jdkShuffle = jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffle = ggDeck.shuffle(new Random(1));
        Deque<Card> acShuffle = acDeck.shuffle(new Random(1));

        MutableSet<Card> ecHand = ecDeck.deal(ecShuffle, 5);
        Set<Card> jdkHand = jdkDeck.deal(jdkShuffle, 5);
        Set<Card> ggHand = ggDeck.deal(ggShuffle, 5);
        Set<Card> acHand = acDeck.deal(acShuffle, 5);
        Assert.assertEquals(ecHand, jdkHand);
        Assert.assertEquals(jdkHand, ggHand);
        Assert.assertEquals(ggHand, ecHand);
        Assert.assertEquals(ecHand, acHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        ImmutableList<Set<Card>> ecHands = ecDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> ggHands = ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = acDeck.shuffleAndDeal(new Random(1), 5, 5);

        Assert.assertEquals(ecHands, jdkHands);
        Assert.assertEquals(jdkHands, ggHands);
        Assert.assertEquals(ggHands, ecHands);
        Assert.assertEquals(ecHands, acHands);
    }

    @Test
    public void cardsBySuit()
    {
        ImmutableSortedSetMultimap<Suit, Card> ecCardsBySuit = ecDeck.getCardsBySuit();
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = jdkDeck.getCardsBySuit();
        ImmutableSetMultimap<Suit, Card> ggCardsBySuit = ggDeck.getCardsBySuit();

        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ggCardsBySuit.get(Suit.CLUBS), ecCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(13, ecDeck.countsBySuit().occurrencesOf(Suit.CLUBS));
        Assert.assertEquals(13, acDeck.countsBySuit().getCount(Suit.CLUBS));
        Assert.assertEquals(13, ggDeck.countsBySuit().count(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), jdkDeck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(4, ecDeck.countsByRank().occurrencesOf(Rank.SEVEN));
        Assert.assertEquals(4, acDeck.countsByRank().getCount(Rank.EIGHT));
        Assert.assertEquals(4, ggDeck.countsByRank().count(Rank.NINE));
        Assert.assertEquals(Long.valueOf(4), jdkDeck.countsByRank().get(Rank.TEN));
    }

    @Test
    public void goodDeals()
    {
        Random random = new Random();
        Predicate<Set<Card>> pair = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 4;
        Supplier<ImmutableList<Set<Card>>> generator = () -> ecDeck.shuffleAndDeal(random, 5, 5);
        Set<Card> pairOrBetter = Stream.generate(generator)
                .filter(hands ->hands.anySatisfy(pair))
                .findFirst()
                .get()
                .detect(pair);
        System.out.println(pairOrBetter);
        Predicate<Set<Card>> twoPairs = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 3;
        Set<Card> twoPairsOrBetter = Stream.generate(generator)
                .filter(hands ->hands.anySatisfy(twoPairs))
                .findFirst()
                .get()
                .detect(twoPairs);
        System.out.println(twoPairsOrBetter);
        Predicate<Set<Card>> fullHouse = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 2;
        Set<Card> fullHouseOrBetter = Stream.generate(generator)
                .filter(hands ->hands.anySatisfy(fullHouse))
                .findFirst()
                .get()
                .detect(fullHouse);
        System.out.println(fullHouseOrBetter);
    }
}
