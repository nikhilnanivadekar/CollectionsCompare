package collections.compare.demo.cards.list;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.Rank;
import collections.compare.demo.cards.Suit;
import io.vavr.Tuple2;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
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

    private EclipseCollectionsDeckOfCards ecDeck1 = new EclipseCollectionsDeckOfCards();
    private EclipseCollectionsDeckOfCardsAsReadableList ecDeck2 = new EclipseCollectionsDeckOfCardsAsReadableList();
    private JDK8DeckOfCards jdkDeck = new JDK8DeckOfCards();
    private GoogleGuavaDeckOfCards ggDeck = new GoogleGuavaDeckOfCards();
    private ApacheCommonsDeckOfCards acDeck = new ApacheCommonsDeckOfCards();
    private VavrDeckOfCards vavrDeck = new VavrDeckOfCards();

    @Test
    public void deckOfCardsMemory()
    {
        System.out.println("EC   (DeckOfCards): "+ ObjectSizeCalculator.getObjectSize(this.ecDeck1));
        System.out.println("JDK  (DeckOfCards): "+ ObjectSizeCalculator.getObjectSize(this.jdkDeck));
        System.out.println("GG   (DeckOfCards): "+ ObjectSizeCalculator.getObjectSize(this.ggDeck));
        System.out.println("AC   (DeckOfCards): "+ ObjectSizeCalculator.getObjectSize(this.acDeck));
        System.out.println("VAVR (DeckOfCards): "+ ObjectSizeCalculator.getObjectSize(this.vavrDeck));
    }

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.ecDeck1.getCards(), this.jdkDeck.getCards());
        Assert.assertEquals(this.jdkDeck.getCards(), this.ggDeck.getCards());
        Assert.assertEquals(this.ggDeck.getCards(), this.ecDeck1.getCards());
        Assert.assertEquals(this.ecDeck1.getCards(), this.acDeck.getCards());
        Assert.assertEquals(this.acDeck.getCards(), this.vavrDeck.getCards().toJavaList());
        Assert.assertEquals(this.ecDeck2.getCards(), this.ecDeck1.getCards());

        System.out.println("EC   (Cards): "+ ObjectSizeCalculator.getObjectSize(this.ecDeck1.getCards()));
        System.out.println("JDK  (Cards): "+ ObjectSizeCalculator.getObjectSize(this.jdkDeck.getCards()));
        System.out.println("GG   (Cards): "+ ObjectSizeCalculator.getObjectSize(this.ggDeck.getCards()));
        System.out.println("AC   (Cards): "+ ObjectSizeCalculator.getObjectSize(this.acDeck.getCards()));
        System.out.println("VAVR (Cards): "+ ObjectSizeCalculator.getObjectSize(this.ecDeck2.getCards()));
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.ecDeck1.diamonds(), this.jdkDeck.diamonds());
        Assert.assertEquals(this.jdkDeck.diamonds(), this.ggDeck.diamonds());
        Assert.assertEquals(this.ggDeck.diamonds(), this.ecDeck1.diamonds());
        Assert.assertEquals(this.ecDeck1.diamonds(), this.acDeck.diamonds());
        Assert.assertEquals(this.acDeck.diamonds(), this.vavrDeck.diamonds().toJavaList());
        Assert.assertEquals(this.ecDeck2.diamonds(), this.ecDeck1.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.ecDeck1.hearts(), this.jdkDeck.hearts());
        Assert.assertEquals(this.jdkDeck.hearts(), this.ggDeck.hearts());
        Assert.assertEquals(this.ggDeck.hearts(), this.ecDeck1.hearts());
        Assert.assertEquals(this.ecDeck1.hearts(), this.acDeck.hearts());
        Assert.assertEquals(this.acDeck.hearts(), this.vavrDeck.hearts().toJavaList());
        Assert.assertEquals(this.ecDeck2.hearts(), this.ecDeck1.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.ecDeck1.spades(), this.jdkDeck.spades());
        Assert.assertEquals(this.jdkDeck.spades(), this.ggDeck.spades());
        Assert.assertEquals(this.ggDeck.spades(), this.ecDeck1.spades());
        Assert.assertEquals(this.ecDeck1.spades(), this.acDeck.spades());
        Assert.assertEquals(this.acDeck.spades(), this.vavrDeck.spades().toJavaList());
        Assert.assertEquals(this.ecDeck2.spades(), this.ecDeck1.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.ecDeck1.clubs(), this.jdkDeck.clubs());
        Assert.assertEquals(this.jdkDeck.clubs(), this.ggDeck.clubs());
        Assert.assertEquals(this.ggDeck.clubs(), this.ecDeck1.clubs());
        Assert.assertEquals(this.ecDeck1.clubs(), this.acDeck.clubs());
        Assert.assertEquals(this.acDeck.clubs(), this.vavrDeck.clubs().toJavaList());
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
        io.vavr.collection.List<Card> vavrShuffle = this.vavrDeck.shuffle(new Random(1));

        MutableSet<Card> ec1Hand = this.ecDeck1.deal(ec1Shuffle, 5);
        MutableSet<Card> ec2Hand = this.ecDeck2.deal(ec2Shuffle, 5);
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);
        Set<Card> ggHand = this.ggDeck.deal(ggShuffle, 5);
        Set<Card> acHand = this.acDeck.deal(acShuffle, 5);
        Tuple2<io.vavr.collection.Set<Card>, ? extends io.vavr.collection.List<Card>> vavrHand = this.vavrDeck.deal(vavrShuffle, 5);
        Set<Card> jsHand = vavrHand._1().toJavaSet();
        Assert.assertEquals(ec1Hand, jdkHand);
        Assert.assertEquals(jdkHand, ggHand);
        Assert.assertEquals(ggHand, ec1Hand);
        Assert.assertEquals(ec1Hand, acHand);
        Assert.assertEquals(acHand, jsHand);
        Assert.assertEquals(ec1Hand, ec2Hand);

        System.out.println("EC   (Shuffle): "+ ObjectSizeCalculator.getObjectSize(ec1Shuffle));
        System.out.println("JDK  (Shuffle): "+ ObjectSizeCalculator.getObjectSize(jdkShuffle));
        System.out.println("GG   (Shuffle): "+ ObjectSizeCalculator.getObjectSize(ggShuffle));
        System.out.println("AC   (Shuffle): "+ ObjectSizeCalculator.getObjectSize(acShuffle));
        System.out.println("VAVR (Shuffle): "+ ObjectSizeCalculator.getObjectSize(vavrShuffle));

        System.out.println("EC   (Hand): "+ ObjectSizeCalculator.getObjectSize(ec1Hand));
        System.out.println("JDK  (Hand): "+ ObjectSizeCalculator.getObjectSize(jdkHand));
        System.out.println("GG   (Hand): "+ ObjectSizeCalculator.getObjectSize(ggHand));
        System.out.println("AC   (Hand): "+ ObjectSizeCalculator.getObjectSize(acHand));
        System.out.println("VAVR (Hand): "+ ObjectSizeCalculator.getObjectSize(vavrHand));
    }

    @Test
    public void shuffleAndDealHands()
    {
        ImmutableList<Set<Card>> ec1Hands = this.ecDeck1.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> acHands = this.acDeck.shuffleAndDeal(new Random(1), 5, 5);
        io.vavr.collection.List<io.vavr.collection.Set<Card>> jsHands =
                this.vavrDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(ec1Hands, jdkHands);
        Assert.assertEquals(jdkHands, ggHands);
        Assert.assertEquals(ggHands, ec1Hands);
        Assert.assertEquals(ec1Hands, acHands);
        Assert.assertEquals(acHands.get(0), jsHands.get(0).toJavaSet());
        Assert.assertEquals(acHands.get(1), jsHands.get(1).toJavaSet());
        Assert.assertEquals(acHands.get(2), jsHands.get(2).toJavaSet());
        Assert.assertEquals(acHands.get(3), jsHands.get(3).toJavaSet());
        Assert.assertEquals(acHands.get(4), jsHands.get(4).toJavaSet());

        System.out.println("EC   (Shuffled Hands): "+ ObjectSizeCalculator.getObjectSize(ec1Hands));
        System.out.println("JDK  (Shuffled Hands): "+ ObjectSizeCalculator.getObjectSize(jdkHands));
        System.out.println("GG   (Shuffled Hands): "+ ObjectSizeCalculator.getObjectSize(ggHands));
        System.out.println("AC   (Shuffled Hands): "+ ObjectSizeCalculator.getObjectSize(acHands));
        System.out.println("VAVR (Shuffled Hands): "+ ObjectSizeCalculator.getObjectSize(jsHands));
    }

    @Test
    public void dealHands()
    {
        MutableStack<Card> ecShuffled = this.ecDeck1.shuffle(new Random(1));
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        Deque<Card> ggShuffled = this.ggDeck.shuffle(new Random(1));
        Deque<Card> acShuffled = this.acDeck.shuffle(new Random(1));
        io.vavr.collection.List<Card> vavrShuffled = this.vavrDeck.shuffle(new Random(1));
        ImmutableList<Set<Card>> ec1Hands = this.ecDeck1.dealHands(ecShuffled, 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);
        List<Set<Card>> ggHands = this.ggDeck.dealHands(ggShuffled, 5, 5);
        List<Set<Card>> acHands = this.acDeck.dealHands(acShuffled, 5, 5);
        io.vavr.collection.List<io.vavr.collection.Set<Card>> jsHands =
                this.vavrDeck.dealHands(vavrShuffled, 5, 5);
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
        io.vavr.collection.Map<Suit, ? extends io.vavr.collection.List<Card>> jsCardsBySuit =
                this.vavrDeck.getCardsBySuit();
        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ggCardsBySuit.get(Suit.CLUBS), ecCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jsCardsBySuit.get(Suit.CLUBS).get().toJavaList());

        System.out.println("EC   (cardsBySuit): "+ ObjectSizeCalculator.getObjectSize(ecCardsBySuit));
        System.out.println("JDK  (cardsBySuit): "+ ObjectSizeCalculator.getObjectSize(jdkCardsBySuit));
        System.out.println("GG   (cardsBySuit): "+ ObjectSizeCalculator.getObjectSize(ggCardsBySuit));
        System.out.println("AC   (cardsBySuit): "+ ObjectSizeCalculator.getObjectSize(jdkCardsBySuit));
        System.out.println("VAVR (cardsBySuit): "+ ObjectSizeCalculator.getObjectSize(jsCardsBySuit));
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(13, this.ecDeck1.countsBySuit().occurrencesOf(Suit.CLUBS));
        Assert.assertEquals(13, this.acDeck.countsBySuit().getCount(Suit.CLUBS));
        Assert.assertEquals(13, this.ggDeck.countsBySuit().count(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), this.jdkDeck.countsBySuit().get(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), this.vavrDeck.countsBySuit().get(Suit.CLUBS));

        System.out.println("EC   (countsBySuit): "+ ObjectSizeCalculator.getObjectSize(this.ecDeck1.countsBySuit()));
        System.out.println("JDK  (countsBySuit): "+ ObjectSizeCalculator.getObjectSize(this.jdkDeck.countsBySuit()));
        System.out.println("GG   (countsBySuit): "+ ObjectSizeCalculator.getObjectSize(this.ggDeck.countsBySuit()));
        System.out.println("AC   (countsBySuit): "+ ObjectSizeCalculator.getObjectSize(this.acDeck.countsBySuit()));
        System.out.println("VAVR (countsBySuit): "+ ObjectSizeCalculator.getObjectSize(this.vavrDeck.countsBySuit()));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(4, this.ecDeck1.countsByRank().occurrencesOf(Rank.SEVEN));
        Assert.assertEquals(4, this.acDeck.countsByRank().getCount(Rank.EIGHT));
        Assert.assertEquals(4, this.ggDeck.countsByRank().count(Rank.NINE));
        Assert.assertEquals(Long.valueOf(4), this.jdkDeck.countsByRank().get(Rank.TEN));
        Assert.assertEquals(Long.valueOf(4), this.vavrDeck.countsByRank().get(Rank.TEN));

        System.out.println("EC   (countsByRank): "+ ObjectSizeCalculator.getObjectSize(this.ecDeck1.countsByRank()));
        System.out.println("JDK  (countsByRank): "+ ObjectSizeCalculator.getObjectSize(this.jdkDeck.countsByRank()));
        System.out.println("GG   (countsByRank): "+ ObjectSizeCalculator.getObjectSize(this.ggDeck.countsByRank()));
        System.out.println("AC   (countsByRank): "+ ObjectSizeCalculator.getObjectSize(this.acDeck.countsByRank()));
        System.out.println("VAVR (countsByRank): "+ ObjectSizeCalculator.getObjectSize(this.vavrDeck.countsByRank()));
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
