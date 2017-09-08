package collections.compare.demo.cards.list;

import java.util.Random;
import java.util.Set;

import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.Rank;
import collections.compare.demo.cards.Suit;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.primitive.IntInterval;

public class EclipseCollectionsDeckOfCardsAsReadableList
{
    private ListIterable<Card> cards;
    private ListMultimap<Suit, Card> cardsBySuit;

    public EclipseCollectionsDeckOfCardsAsReadableList()
    {
        this.cards = Card.lazyCards().toSortedList().asUnmodifiable();
        this.cardsBySuit = this.cards.groupBy(Card::getSuit);
    }

    public MutableStack<Card> shuffle(Random random)
    {
        return this.cards
                .toList()
                .shuffleThis(random)
                .shuffleThis(random)
                .shuffleThis(random)
                .toStack();
    }

    public MutableSet<Card> deal(MutableStack<Card> stack, int count)
    {
        return stack.pop(count).toSet();
    }

    public Card dealOneCard(MutableStack<Card> stack)
    {
        return stack.pop();
    }

    public ListIterable<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        MutableStack<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public ListIterable<Set<Card>> dealHands(MutableStack<Card> shuffled, int hands, int cardsPerHand)
    {
        return IntInterval.oneTo(hands)
                .collect(i -> this.deal(shuffled, cardsPerHand), Lists.mutable.<Set<Card>>empty()).asUnmodifiable();
    }

    public ListIterable<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public ListIterable<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public ListIterable<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public ListIterable<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Bag<Suit> countsBySuit()
    {
        return this.cards.countBy(Card::getSuit);
    }

    public Bag<Rank> countsByRank()
    {
        return this.cards.countBy(Card::getRank);
    }

    public ListIterable<Card> getCards()
    {
        return this.cards;
    }

    public ListMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
