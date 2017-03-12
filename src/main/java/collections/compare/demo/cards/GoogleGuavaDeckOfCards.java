package collections.compare.demo.cards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;

public class GoogleGuavaDeckOfCards
{
    private ImmutableSortedSet<Card> cards;
    private ImmutableSetMultimap<Suit, Card> cardsBySuit;

    public GoogleGuavaDeckOfCards()
    {
        EnumSet<Suit> suits = EnumSet.allOf(Suit.class);
        EnumSet<Rank> ranks = EnumSet.allOf(Rank.class);
        this.cards = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .collect(ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        ImmutableSetMultimap.Builder<Suit, Card> builder =
                new ImmutableSetMultimap.Builder<Suit, Card>().orderValuesBy(Comparator.naturalOrder());
        this.cards.forEach(card -> builder.put(card.getSuit(), card));
        this.cardsBySuit = builder.build();
    }

    public Deque<Card> shuffle(Random random)
    {
        List<Card> shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        ArrayDeque<Card> cards = new ArrayDeque<>();
        shuffled.forEach(cards::push);
        return cards;
    }

    public Set<Card> deal(Deque<Card> deque, int count)
    {
        Set<Card> hand = new HashSet<>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public Card dealOneCard(Deque<Card> deque)
    {
        return deque.pop();
    }

    public ImmutableList<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        Deque<Card> shuffle = this.shuffle(random);
        return IntStream.range(0, hands)
                .mapToObj(i -> this.deal(shuffle, cardsPerHand))
                .collect(ImmutableList.toImmutableList());
    }

    public Set<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public Set<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public Set<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public Set<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Multiset<Suit> countsBySuit()
    {
        return this.cards.stream().map(Card::getSuit).collect(Collectors.toCollection(HashMultiset::create));
    }

    public Multiset<Rank> countsByRank()
    {
        return this.cards.stream().map(Card::getRank).collect(Collectors.toCollection(HashMultiset::create));
    }

    public ImmutableSortedSet<Card> getCards()
    {
        return this.cards;
    }

    public ImmutableSetMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
