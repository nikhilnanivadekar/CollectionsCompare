package collections.compare.demo.cards;

import java.util.EnumSet;
import java.util.Random;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.multimap.sortedset.ImmutableSortedSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Sets;

public class EclipseCollectionsDeckOfCards
{
    private ImmutableSortedSet<Card> cards;
    private ImmutableSortedSetMultimap<Suit, Card> cardsBySuit;

    public EclipseCollectionsDeckOfCards()
    {
        LazyIterable<Card> cardIterable =
                Sets.cartesianProduct(
                        EnumSet.allOf(Rank.class),
                        EnumSet.allOf(Suit.class))
                .collect(pair -> new Card(pair.getOne(), pair.getTwo()));
        this.cards = cardIterable.toSortedSet().toImmutable();
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
        return this.cards.asLazy().collect(Card::getSuit).toBag();
    }

    public Bag<Rank> countsByRank()
    {
        return this.cards.asLazy().collect(Card::getRank).toBag();
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
