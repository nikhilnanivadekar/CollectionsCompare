package collections.compare.demo.game;

import java.util.Random;

import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.Rank;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishUsingEclipseCollections extends Fish
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FishUsingEclipseCollections.class);

    private final EclipseCollectionsDeckOfCards deck = new EclipseCollectionsDeckOfCards();
    private final MutableStack<Card> shuffledCards = this.deck.shuffle(new Random(123456789L));
    private final MutableSetMultimap<Integer, Card> cardsPerPlayer = Multimaps.mutable.set.empty();

    private int numberOfPlayers;

    public FishUsingEclipseCollections(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void deal()
    {
        int numberOfCardsPerPlayer = this.getNumberOfCardsPerPlayer(this.numberOfPlayers);

        for (int i = 0; i < numberOfCardsPerPlayer; i++)
        {
            for (int j = 1; j <= this.numberOfPlayers; j++)
            {
                this.cardsPerPlayer.put(j, this.deck.deal(this.shuffledCards, 1).getOnly());
            }
        }
    }

    public boolean playTurn(int playerNumber)
    {
        MutableSet<Card> cards = this.cardsPerPlayer.get(playerNumber);

        MutableBag<Rank> ranks = this.getRanks(cards);
        Rank probableBook = this.getProbableBook(ranks).getOne();

        int nextPlayer = playerNumber == this.numberOfPlayers ? 1 : playerNumber + 1;

        MutableSet<Card> cardsWithNextPlayer = this.cardsPerPlayer.get(nextPlayer);
        if (cardsWithNextPlayer.isEmpty())
        {
            LOGGER.info("Player:{} has no cards left! GAME OVER!!!", nextPlayer);
            return false;
        }

        Card card = cardsWithNextPlayer.detectWith(Card::isSameRank, probableBook);

        if (card == null)
        {
            LOGGER.info("Go FISH!");
            if (this.shuffledCards.isEmpty())
            {
                LOGGER.info("Pond is dry. GAME OVER!!!");
                return false;
            }
            return this.checkIfPlayerWins(playerNumber, cards);
        }
        else
        {
            return this.checkIfPlayerWins(playerNumber, cards);
        }
    }

    private boolean checkIfPlayerWins(int playerNumber, MutableSet<Card> cards)
    {
        Card card = this.deck.deal(this.shuffledCards, 1).getOnly();
        cards.add(card);
        MutableBag<Rank> newRanks = this.getRanks(cards);
        int rankCount = this.getProbableBook(newRanks).getTwo();
        if (rankCount == 4)
        {
            LOGGER.info("Player:{} has a BOOK! Player:{} WINS!!!", playerNumber, playerNumber);
            return false;
        }
        return true;
    }

    private ObjectIntPair<Rank> getProbableBook(MutableBag<Rank> ranks)
    {
        return ranks.topOccurrences(1).getFirst();
    }

    private MutableBag<Rank> getRanks(MutableSet<Card> cards)
    {
        return cards.collect(Card::getRank, Bags.mutable.empty());
    }
}
