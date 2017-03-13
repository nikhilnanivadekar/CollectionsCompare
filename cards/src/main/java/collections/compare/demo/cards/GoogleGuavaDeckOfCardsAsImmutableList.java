package collections.compare.demo.cards;

import com.google.common.base.Function;
import com.google.common.collect.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GoogleGuavaDeckOfCardsAsImmutableList
{
    private ImmutableList<Card> cards;
    private ImmutableListMultimap<Suit, Card> cardsBySuit;

    public GoogleGuavaDeckOfCardsAsImmutableList()
    {
        EnumSet<Suit> suits = EnumSet.allOf(Suit.class);
        EnumSet<Rank> ranks = EnumSet.allOf(Rank.class);
        this.cards = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .sorted()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ImmutableList::copyOf));
        //noinspection RedundantCast
        this.cardsBySuit = Multimaps.index(this.cards, (Function<Card, Suit>) card -> card.getSuit());
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

    public ImmutableList<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public ImmutableList<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public ImmutableList<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public ImmutableList<Card> clubs()
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

    public ImmutableList<Card> getCards()
    {
        return this.cards;
    }

    public ImmutableListMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
