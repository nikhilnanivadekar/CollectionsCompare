package collections.compare.demo.cards;

import com.google.common.collect.SortedSetMultimap;
import org.eclipse.collections.api.multimap.sortedset.ImmutableSortedSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.junit.Assert;
import org.junit.Test;

import java.util.Deque;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

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
    public void deals()
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
    public void cardsBySuit()
    {
        ImmutableSortedSetMultimap<Suit, Card> ecCardsBySuit = ecDeck.getCardsBySuit();
        Map<Suit, SortedSet<Card>> jdkCardsBySuit = jdkDeck.getCardsBySuit();
        SortedSetMultimap<Suit, Card> ggCardsBySuit = ggDeck.getCardsBySuit();

        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), ggCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ggCardsBySuit.get(Suit.CLUBS), ecCardsBySuit.get(Suit.CLUBS));
    }

}
