package collections.compare.demo.cards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
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

public class ApacheCommonsDeckOfCards {
    private SortedSet<Card> cards;
    private MultiValuedMap<Suit, Card> cardsBySuit;

    public ApacheCommonsDeckOfCards() {
        EnumSet<Suit> suits = EnumSet.allOf(Suit.class);
        EnumSet<Rank> ranks = EnumSet.allOf(Rank.class);
        this.cards = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(TreeSet::new),
                        Collections::unmodifiableSortedSet));
        SetValuedMap<Suit, Card> cbs = MultiMapUtils.newSetValuedHashMap();
        this.cards.forEach(card -> cbs.put(card.getSuit(), card));
        this.cardsBySuit = MultiMapUtils.unmodifiableMultiValuedMap(cbs);
    }

    public Deque<Card> shuffle(Random random) {
        List<Card> shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        ArrayDeque<Card> cards = new ArrayDeque<>();
        shuffled.forEach(cards::push);
        return cards;
    }

    public Set<Card> deal(Deque<Card> deque, int count) {
        Set<Card> hand = new HashSet<>();
        IntStream.range(0, count).forEach(i -> hand.add(deque.pop()));
        return hand;
    }

    public Card dealOneCard(Deque<Card> deque) {
        return deque.pop();
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand) {
        Deque<Card> shuffled = this.shuffle(random);
        return this.dealHands(shuffled, hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand) {
        return IntStream.range(0, hands)
                .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
    }

    public SortedSet<Card> diamonds() {
        return new TreeSet<>(this.cardsBySuit.get(Suit.DIAMONDS));
    }

    public SortedSet<Card> hearts() {
        return new TreeSet<>(this.cardsBySuit.get(Suit.HEARTS));
    }

    public SortedSet<Card> spades() {
        return new TreeSet<>(this.cardsBySuit.get(Suit.SPADES));
    }

    public SortedSet<Card> clubs() {
        return new TreeSet<>(this.cardsBySuit.get(Suit.CLUBS));
    }

    public Bag<Suit> countsBySuit() {
        return this.cards.stream()
                .map(Card::getSuit)
                .collect(Collectors.toCollection(HashBag::new));
    }

    public MultiSet<Rank> countsByRank() {
        return this.cards.stream()
                .map(Card::getRank)
                .collect(Collectors.toCollection(HashMultiSet::new));
    }

    public SortedSet<Card> getCards() {
        return this.cards;
    }

    public MultiValuedMap<Suit, Card> getCardsBySuit() {
        return this.cardsBySuit;
    }
}
