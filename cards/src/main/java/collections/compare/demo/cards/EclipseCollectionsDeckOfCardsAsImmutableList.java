package collections.compare.demo.cards;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.primitive.IntInterval;

public class EclipseCollectionsDeckOfCardsAsImmutableList {
    private ImmutableList<Card> cards;
    private ImmutableListMultimap<Suit, Card> cardsBySuit;

    public EclipseCollectionsDeckOfCardsAsImmutableList() {
        this.cards = Lists.mutable.with(
                Card.streamCards()
                        .toArray(Card[]::new))
                .sortThis().toImmutable();
        this.cardsBySuit = this.cards.groupBy(Card::getSuit);
    }

    public MutableStack<Card> shuffle(Random random) {
        return this.cards.toList()
                .shuffleThis(random)
                .shuffleThis(random)
                .shuffleThis(random).toStack();
    }

    public MutableSet<Card> deal(MutableStack<Card> stack, int count) {
        return stack.pop(count).toSet();
    }

    public Card dealOneCard(MutableStack<Card> stack) {
        return stack.pop();
    }

    public ImmutableList<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand) {
        MutableStack<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public ImmutableList<Set<Card>> dealHands(MutableStack<Card> shuffled, int hands, int cardsPerHand) {
        return IntInterval.oneTo(hands).collect(i -> this.deal(shuffled, cardsPerHand));
    }

    public ImmutableList<Card> diamonds() {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public ImmutableList<Card> hearts() {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public ImmutableList<Card> spades() {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public ImmutableList<Card> clubs() {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Bag<Suit> countsBySuit() {
        return this.cards.asLazy().collect(Card::getSuit).toBag();
    }

    public Bag<Rank> countsByRank() {
        return this.cards.asLazy().collect(Card::getRank).toBag();
    }

    public ImmutableList<Card> getCards() {
        return this.cards;
    }

    public ImmutableListMultimap<Suit, Card> getCardsBySuit() {
        return this.cardsBySuit;
    }
}
