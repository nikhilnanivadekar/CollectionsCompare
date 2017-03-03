package collections.compare.demo.cards;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSetMultimap;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.sortedset.ImmutableSortedSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.collector.Collectors2;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeckOfCardsTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DeckOfCardsTest.class);

    private EclipseCollectionsDeckOfCards ecDeck = new EclipseCollectionsDeckOfCards();
    private JDK8DeckOfCards jdkDeck = new JDK8DeckOfCards();
    private GoogleGuavaDeckOfCards ggDeck = new GoogleGuavaDeckOfCards();
    private ApacheCommonsDeckOfCards acDeck = new ApacheCommonsDeckOfCards();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.ecDeck.getCards(), this.jdkDeck.getCards());
        Assert.assertEquals(this.jdkDeck.getCards(), this.ggDeck.getCards());
        Assert.assertEquals(this.ggDeck.getCards(), this.ecDeck.getCards());
        Assert.assertEquals(this.ecDeck.getCards(), this.acDeck.getCards());
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.ecDeck.diamonds(), this.jdkDeck.diamonds());
        Assert.assertEquals(this.jdkDeck.diamonds(), this.ggDeck.diamonds());
        Assert.assertEquals(this.ggDeck.diamonds(), this.ecDeck.diamonds());
        Assert.assertEquals(this.ecDeck.diamonds(), this.acDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.ecDeck.hearts(), this.jdkDeck.hearts());
        Assert.assertEquals(this.jdkDeck.hearts(), this.ggDeck.hearts());
        Assert.assertEquals(this.ggDeck.hearts(), this.ecDeck.hearts());
        Assert.assertEquals(this.ecDeck.hearts(), this.acDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.ecDeck.spades(), this.jdkDeck.spades());
        Assert.assertEquals(this.jdkDeck.spades(), this.ggDeck.spades());
        Assert.assertEquals(this.ggDeck.spades(), this.ecDeck.spades());
        Assert.assertEquals(this.ecDeck.spades(), this.acDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.ecDeck.clubs(), this.jdkDeck.clubs());
        Assert.assertEquals(this.jdkDeck.clubs(), this.ggDeck.clubs());
        Assert.assertEquals(this.ggDeck.clubs(), this.ecDeck.clubs());
        Assert.assertEquals(this.ecDeck.clubs(), this.acDeck.clubs());
    }

    @Test
    public void deal()
    {
        MutableStack<Card> ecShuffle = this.ecDeck.shuffle(new Random(1));
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffle = this.ggDeck.shuffle(new Random(1));
        Deque<Card> acShuffle = this.acDeck.shuffle(new Random(1));

        MutableSet<Card> ecHand = this.ecDeck.deal(ecShuffle, 5);
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> ggHand = this.ggDeck.deal(ggShuffle, 5);
        Set<Card> acHand = this.acDeck.deal(acShuffle, 5);
        Assert.assertEquals(ecHand, jdkHand);
        Assert.assertEquals(jdkHand, ggHand);
        Assert.assertEquals(ggHand, ecHand);
        Assert.assertEquals(ecHand, acHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        ImmutableList<Set<Card>> ecHands = this.ecDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);

        Assert.assertEquals(ecHands, jdkHands);
        Assert.assertEquals(jdkHands, ggHands);
        Assert.assertEquals(ggHands, ecHands);
        Assert.assertEquals(ecHands, acHands);
    }

    @Test
    public void cardsBySuit()
    {
        ImmutableSortedSetMultimap<Suit, Card> ecCardsBySuit = this.ecDeck.getCardsBySuit();
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        ImmutableSetMultimap<Suit, Card> ggCardsBySuit = this.ggDeck.getCardsBySuit();

        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ggCardsBySuit.get(Suit.CLUBS), ecCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(13, this.ecDeck.countsBySuit().occurrencesOf(Suit.CLUBS));
        Assert.assertEquals(13, this.acDeck.countsBySuit().getCount(Suit.CLUBS));
        Assert.assertEquals(13, this.ggDeck.countsBySuit().count(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), this.jdkDeck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(4, this.ecDeck.countsByRank().occurrencesOf(Rank.SEVEN));
        Assert.assertEquals(4, this.acDeck.countsByRank().getCount(Rank.EIGHT));
        Assert.assertEquals(4, this.ggDeck.countsByRank().count(Rank.NINE));
        Assert.assertEquals(Long.valueOf(4), this.jdkDeck.countsByRank().get(Rank.TEN));
    }

    @Test
    public void goodDeals()
    {
        Random random = new Random();
        Predicate<Set<Card>> pair = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 4;
        Supplier<ImmutableList<Set<Card>>> generator = () -> this.ecDeck.shuffleAndDeal(random, 5, 5);
        Set<Card> pairOrBetter = Stream.generate(generator)
                .filter(hands -> hands.anySatisfy(pair))
                .findFirst()
                .get()
                .detect(pair);
        LOGGER.info(pairOrBetter.toString());
        Predicate<Set<Card>> twoPairs = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 3;
        Set<Card> twoPairsOrBetter = Stream.generate(generator)
                .filter(hands -> hands.anySatisfy(twoPairs))
                .findFirst()
                .get()
                .detect(twoPairs);
        LOGGER.info(twoPairsOrBetter.toString());
        Predicate<Set<Card>> fullHouse = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 2;
        Set<Card> fullHouseOrBetter = Stream.generate(generator)
                .filter(hands -> hands.anySatisfy(fullHouse))
                .findFirst()
                .get()
                .detect(fullHouse);
        LOGGER.info(fullHouseOrBetter.toString());
    }
}
