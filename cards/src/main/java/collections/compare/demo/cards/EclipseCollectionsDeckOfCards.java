package collections.compare.demo.cards;

import java.util.Random;
import java.util.Set;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.sortedset.ImmutableSortedSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.list.primitive.IntInterval;

public class EclipseCollectionsDeckOfCards
{
    private ImmutableSortedSet<Card> cards;
    private ImmutableSortedSetMultimap<Suit, Card> cardsBySuit;

    public EclipseCollectionsDeckOfCards()
    {
        this.cards = SortedSets.immutable.with(
                Card.streamCards()
                        .toArray(Card[]::new));
        this.cardsBySuit = this.cards.groupBy(Card::getSuit);
    }

    public MutableStack<Card> shuffle(Random random)
    {
        return this.cards.toList()
                .shuffleThis(random)
                .shuffleThis(random)
                .shuffleThis(random).toStack();
    }

    public MutableSet<Card> deal(MutableStack<Card> stack, int count)
    {
        return stack.pop(count).toSet();
    }

    public Card dealOneCard(MutableStack<Card> stack)
    {
        return stack.pop();
    }

    public ImmutableList<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        MutableStack<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public ImmutableList<Set<Card>> dealHands(
            MutableStack<Card> shuffled,
            int hands,
            int cardsPerHand)
    {
        return IntInterval.oneTo(hands)
                .collect(i -> this.deal(shuffled, cardsPerHand));
    }

    public ImmutableSortedSet<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public ImmutableSortedSet<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public ImmutableSortedSet<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public ImmutableSortedSet<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Bag<Suit> countsBySuit()
    {
        return this.cards.asLazy()
                .collect(Card::getSuit)
                .toBag();
    }

    public Bag<Rank> countsByRank()
    {
        return this.cards.asLazy()
                .collect(Card::getRank)
                .toBag();
    }

    public ImmutableSortedSet<Card> getCards()
    {
        return this.cards;
    }

    public ImmutableSortedSetMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
