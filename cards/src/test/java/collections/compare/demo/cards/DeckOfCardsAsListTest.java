package collections.compare.demo.cards;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javaslang.collection.Stack;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.collector.Collectors2;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeckOfCardsAsListTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DeckOfCardsAsListTest.class);

    private EclipseCollectionsDeckOfCardsAsImmutableList ecDeck1 = new EclipseCollectionsDeckOfCardsAsImmutableList();
    private EclipseCollectionsDeckOfCardsAsReadableList ecDeck2 = new EclipseCollectionsDeckOfCardsAsReadableList();
    private JDK8DeckOfCardsAsList jdkDeck = new JDK8DeckOfCardsAsList();
    private GoogleGuavaDeckOfCardsAsImmutableList ggDeck = new GoogleGuavaDeckOfCardsAsImmutableList();
    private ApacheCommonsDeckOfCardsAsList acDeck = new ApacheCommonsDeckOfCardsAsList();
    private JavaSlangDeckOfCardsAsImmutableList jsDeck = new JavaSlangDeckOfCardsAsImmutableList();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.ecDeck1.getCards(), this.jdkDeck.getCards());
        Assert.assertEquals(this.jdkDeck.getCards(), this.ggDeck.getCards());
        Assert.assertEquals(this.ggDeck.getCards(), this.ecDeck1.getCards());
        Assert.assertEquals(this.ecDeck1.getCards(), this.acDeck.getCards());
        Assert.assertEquals(this.acDeck.getCards(), this.jsDeck.getCards().toJavaList());
        Assert.assertEquals(this.ecDeck2.getCards(), this.ecDeck1.getCards());
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.ecDeck1.diamonds(), this.jdkDeck.diamonds());
        Assert.assertEquals(this.jdkDeck.diamonds(), this.ggDeck.diamonds());
        Assert.assertEquals(this.ggDeck.diamonds(), this.ecDeck1.diamonds());
        Assert.assertEquals(this.ecDeck1.diamonds(), this.acDeck.diamonds());
        Assert.assertEquals(this.acDeck.diamonds(), this.jsDeck.diamonds().toJavaList());
        Assert.assertEquals(this.ecDeck2.diamonds(), this.ecDeck1.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.ecDeck1.hearts(), this.jdkDeck.hearts());
        Assert.assertEquals(this.jdkDeck.hearts(), this.ggDeck.hearts());
        Assert.assertEquals(this.ggDeck.hearts(), this.ecDeck1.hearts());
        Assert.assertEquals(this.ecDeck1.hearts(), this.acDeck.hearts());
        Assert.assertEquals(this.acDeck.hearts(), this.jsDeck.hearts().toJavaList());
        Assert.assertEquals(this.ecDeck2.hearts(), this.ecDeck1.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.ecDeck1.spades(), this.jdkDeck.spades());
        Assert.assertEquals(this.jdkDeck.spades(), this.ggDeck.spades());
        Assert.assertEquals(this.ggDeck.spades(), this.ecDeck1.spades());
        Assert.assertEquals(this.ecDeck1.spades(), this.acDeck.spades());
        Assert.assertEquals(this.acDeck.spades(), this.jsDeck.spades().toJavaList());
        Assert.assertEquals(this.ecDeck2.spades(), this.ecDeck1.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.ecDeck1.clubs(), this.jdkDeck.clubs());
        Assert.assertEquals(this.jdkDeck.clubs(), this.ggDeck.clubs());
        Assert.assertEquals(this.ggDeck.clubs(), this.ecDeck1.clubs());
        Assert.assertEquals(this.ecDeck1.clubs(), this.acDeck.clubs());
        Assert.assertEquals(this.acDeck.clubs(), this.jsDeck.clubs().toJavaList());
        Assert.assertEquals(this.ecDeck2.clubs(), this.ecDeck1.clubs());
    }

    @Test
    public void deal()
    {
        MutableStack<Card> ec1Shuffle = this.ecDeck1.shuffle(new Random(1));
        MutableStack<Card> ec2Shuffle = this.ecDeck2.shuffle(new Random(1));
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffle = this.ggDeck.shuffle(new Random(1));
        Deque<Card> acShuffle = this.acDeck.shuffle(new Random(1));
        Stack<Card> jsShuffle = this.jsDeck.shuffle(new Random(1));

        MutableSet<Card> ec1Hand = this.ecDeck1.deal(ec1Shuffle, 5);
        MutableSet<Card> ec2Hand = this.ecDeck2.deal(ec2Shuffle, 5);
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> ggHand = this.ggDeck.deal(ggShuffle, 5);
        Set<Card> acHand = this.acDeck.deal(acShuffle, 5);
        Set<Card> jsHand = this.jsDeck.deal(jsShuffle, 5)._1().toJavaSet();
        Assert.assertEquals(ec1Hand, jdkHand);
        Assert.assertEquals(jdkHand, ggHand);
        Assert.assertEquals(ggHand, ec1Hand);
        Assert.assertEquals(ec1Hand, acHand);
        Assert.assertEquals(acHand, jsHand);
        Assert.assertEquals(ec1Hand, ec2Hand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        ImmutableList<Set<Card>> ec1Hands = this.ecDeck1.shuffleAndDeal(new Random(1), 5, 5);
        ListIterable<Set<Card>> ec2Hands = this.ecDeck2.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);
        javaslang.collection.List<javaslang.collection.Set<Card>> jsHands =
                this.jsDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(ec1Hands, jdkHands);
        Assert.assertEquals(jdkHands, ggHands);
        Assert.assertEquals(ggHands, ec1Hands);
        Assert.assertEquals(ec1Hands, acHands);
        Assert.assertEquals(acHands.get(0), jsHands.get(0).toJavaSet());
        Assert.assertEquals(acHands.get(1), jsHands.get(1).toJavaSet());
        Assert.assertEquals(acHands.get(2), jsHands.get(2).toJavaSet());
        Assert.assertEquals(acHands.get(3), jsHands.get(3).toJavaSet());
        Assert.assertEquals(acHands.get(4), jsHands.get(4).toJavaSet());
    }

    @Test
    public void cardsBySuit()
    {
        ImmutableListMultimap<Suit, Card> ecCardsBySuit = this.ecDeck1.getCardsBySuit();
        Map<Suit, List<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        com.google.common.collect.ImmutableListMultimap<Suit, Card> ggCardsBySuit = this.ggDeck.getCardsBySuit();
        javaslang.collection.Map<Suit, ? extends javaslang.collection.List<Card>> jsCardsBySuit =
                this.jsDeck.getCardsBySuit();
        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ggCardsBySuit.get(Suit.CLUBS), ecCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jsCardsBySuit.get(Suit.CLUBS).get().toJavaList());
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(13, this.ecDeck1.countsBySuit().occurrencesOf(Suit.CLUBS));
        Assert.assertEquals(13, this.acDeck.countsBySuit().getCount(Suit.CLUBS));
        Assert.assertEquals(13, this.ggDeck.countsBySuit().count(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), this.jdkDeck.countsBySuit().get(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), this.jsDeck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(4, this.ecDeck1.countsByRank().occurrencesOf(Rank.SEVEN));
        Assert.assertEquals(4, this.acDeck.countsByRank().getCount(Rank.EIGHT));
        Assert.assertEquals(4, this.ggDeck.countsByRank().count(Rank.NINE));
        Assert.assertEquals(Long.valueOf(4), this.jdkDeck.countsByRank().get(Rank.TEN));
        Assert.assertEquals(Long.valueOf(4), this.jsDeck.countsByRank().get(Rank.TEN));
    }

    @Test
    public void goodDeals()
    {
        Random random = new Random();
        Predicate<Set<Card>> pair = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 4;
        Supplier<ImmutableList<Set<Card>>> generator = () -> this.ecDeck1.shuffleAndDeal(random, 5, 5);
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
