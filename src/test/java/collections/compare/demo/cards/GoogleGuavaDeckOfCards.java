package collections.compare.demo.cards;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedSetMultimap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GoogleGuavaDeckOfCards
{
    private ImmutableSortedSet<Card> cards;
    private SortedSetMultimap<Suit, Card> cardsBySuit;

    public GoogleGuavaDeckOfCards()
    {
        SortedSet<Card> set = new TreeSet<>();
        Arrays.stream(Suit.values()).forEach(
                suit -> Arrays.stream(Rank.values()).forEach(
                        rank -> set.add(new Card(rank, suit))));
        this.cards = ImmutableSortedSet.copyOf(set);
        this.cardsBySuit = Multimaps.unmodifiableSortedSetMultimap(
                set.stream().collect(
                        Multimaps.toMultimap(
                                Card::getSuit,
                                c -> c,
                                MultimapBuilder.hashKeys().treeSetValues()::build)));
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

    public SortedSet<Card> diamonds()
    {
        return Collections.unmodifiableSortedSet(this.cardsBySuit.get(Suit.DIAMONDS));
    }

    public SortedSet<Card> hearts()
    {
        return Collections.unmodifiableSortedSet(this.cardsBySuit.get(Suit.HEARTS));
    }

    public SortedSet<Card> spades()
    {
        return Collections.unmodifiableSortedSet(this.cardsBySuit.get(Suit.SPADES));
    }

    public SortedSet<Card> clubs()
    {
        return Collections.unmodifiableSortedSet(this.cardsBySuit.get(Suit.CLUBS));
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

    public SortedSetMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
