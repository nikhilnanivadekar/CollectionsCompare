package collections.compare.demo.cards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JDK8DeckOfCardsAsList {
    private List<Card> cards;
    private Map<Suit, List<Card>> cardsBySuit;

    public JDK8DeckOfCardsAsList() {
        EnumSet<Suit> suits = EnumSet.allOf(Suit.class);
        EnumSet<Rank> ranks = EnumSet.allOf(Rank.class);
        this.cards = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .sorted()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
        this.cardsBySuit =
                this.cards.stream().collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Card::getSuit,
                                Collectors.mapping(Function.identity(),
                                        Collectors.collectingAndThen(
                                                Collectors.toList(),
                                                Collections::unmodifiableList))),
                        Collections::unmodifiableMap));
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
        Deque<Card> shuffle = this.shuffle(random);
        return IntStream.range(0, hands)
                .mapToObj(i -> this.deal(shuffle, cardsPerHand))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
    }

    public List<Set<Card>> dealHands(Deque<Card> shuffled, int hands, int cardsPerHand) {
        return IntStream.range(0, hands)
                .mapToObj(i -> this.deal(shuffled, cardsPerHand))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
    }

    public List<Card> diamonds() {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public List<Card> hearts() {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public List<Card> spades() {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public List<Card> clubs() {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Map<Suit, Long> countsBySuit() {
        return this.cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    public Map<Rank, Long> countsByRank() {
        return this.cards.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public Map<Suit, List<Card>> getCardsBySuit() {
        return this.cardsBySuit;
    }
}
