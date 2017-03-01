package collections.compare.demo.cards;

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

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.multiset.HashMultiSet;

public class ApacheCommonsDeckOfCards
{
    private SortedSet<Card> cards;
    private MultiValuedMap<Suit, Card> cardsBySuit;

    public ApacheCommonsDeckOfCards()
    {
        SortedSet<Card> set = new TreeSet<>();
        Arrays.stream(Suit.values()).forEach(
                suit -> Arrays.stream(Rank.values()).forEach(
                        rank -> set.add(new Card(rank, suit))));
        this.cards = Collections.unmodifiableSortedSet(set);
        SetValuedMap<Suit, Card> cbs = MultiMapUtils.newSetValuedHashMap();
        set.stream().forEach(card -> cbs.put(card.getSuit(), card));
        this.cardsBySuit = MultiMapUtils.unmodifiableMultiValuedMap(cbs);
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
        return new TreeSet<>(this.cardsBySuit.get(Suit.DIAMONDS));
    }

    public SortedSet<Card> hearts()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.HEARTS));
    }

    public SortedSet<Card> spades()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.SPADES));
    }

    public SortedSet<Card> clubs()
    {
        return new TreeSet<>(this.cardsBySuit.get(Suit.CLUBS));
    }

    public Bag<Suit> countsBySuit()
    {
        return this.cards.stream().map(Card::getSuit).collect(Collectors.toCollection(HashBag::new));
    }

    public MultiSet<Rank> countsByRank()
    {
        return this.cards.stream().map(Card::getRank).collect(Collectors.toCollection(HashMultiSet::new));
    }

    public SortedSet<Card> getCards()
    {
        return this.cards;
    }

    public MultiValuedMap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
