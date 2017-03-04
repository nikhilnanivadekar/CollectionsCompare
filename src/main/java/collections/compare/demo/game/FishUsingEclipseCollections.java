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
    private final MutableStack<Card> shuffledCards;
    private final MutableSetMultimap<Integer, Card> cardsPerPlayer = Multimaps.mutable.set.empty();

    private final int numberOfPlayers;
    private final long seed;

    private Outcome outcome;

    public FishUsingEclipseCollections(int numberOfPlayers, long seed)
    {
        this.numberOfPlayers = numberOfPlayers;
        this.seed = seed;
        this.shuffledCards = this.deck.shuffle(new Random(this.seed));
    }

    public Outcome getOutcome()
    {
        return this.outcome;
    }

    @Override
    public void deal()
    {
        int numberOfCardsPerPlayer = this.getNumberOfCardsPerPlayer(this.numberOfPlayers);

        for (int i = 0; i < numberOfCardsPerPlayer; i++)
        {
            for (int j = 1; j <= this.numberOfPlayers; j++)
            {
                this.cardsPerPlayer.put(j, this.deck.dealOneCard(this.shuffledCards));
            }
        }
        LOGGER.info("Here are the cards which each player has:");
        this.logCardsPerPlayer();
    }

    @Override
    public boolean playTurn(int playerNumber)
    {
        MutableSet<Card> cards = this.cardsPerPlayer.get(playerNumber);

        MutableBag<Rank> ranks = this.getRanks(cards);
        Rank probableBook = this.getProbableBook(ranks).getOne();
        LOGGER.info("Player:{} can have a probable Book for Rank:{}", playerNumber, probableBook);

        int nextPlayer = playerNumber == this.numberOfPlayers ? 1 : playerNumber + 1;

        MutableSet<Card> cardsWithNextPlayer = this.cardsPerPlayer.get(nextPlayer);
        if (cardsWithNextPlayer.isEmpty())
        {
            LOGGER.info("Player:{} has no cards left! GAME OVER!!!", nextPlayer);
            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            this.outcome = Outcome.PLAYER_NO_CARDS;
            return false;
        }

        Card card = cardsWithNextPlayer.detectWith(Card::isSameRank, probableBook);
        LOGGER.info("Next Player:{} " + (card == null ? "No card for Rank:" + probableBook : "has Card:" + card), nextPlayer);

        if (card == null)
        {
            LOGGER.info("Go FISH!");
            if (this.shuffledCards.isEmpty())
            {
                LOGGER.info("Pond is dry. GAME OVER!!!");
                this.logCardsPerPlayer();
                logCardCountPerPlayer();
                this.outcome = Outcome.POND_DRY;
                return false;
            }
            Card cardFromPond = this.deck.dealOneCard(this.shuffledCards);
            LOGGER.info("Card from Pond:{}", cardFromPond);
            this.cardsPerPlayer.put(playerNumber, cardFromPond);
            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            return this.checkIfPlayerWins(playerNumber);
        }
        else
        {
            this.cardsPerPlayer.remove(nextPlayer, card);
            this.cardsPerPlayer.put(playerNumber, card);
            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            return this.checkIfPlayerWins(playerNumber);
        }
    }

    private void logCardCountPerPlayer()
    {
        this.cardsPerPlayer.forEachKeyMultiValues((player, cardsPerPlayer) -> LOGGER.info("Player:{} has {} cards", player, ((MutableSet<Card>) cardsPerPlayer).size()));
    }

    private boolean checkIfPlayerWins(int playerNumber)
    {
        MutableBag<Rank> newRanks = this.getRanks(this.cardsPerPlayer.get(playerNumber));
        int rankCount = this.getProbableBook(newRanks).getTwo();
        if (rankCount == 4)
        {
            LOGGER.info("Player:{} has a BOOK! Player:{} WINS!!!", playerNumber, playerNumber);
            this.outcome = Outcome.WINNER;
            return false;
        }
        this.outcome = Outcome.CONTINUE;
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

    private void logCardsPerPlayer()
    {
        this.cardsPerPlayer.forEachKeyMultiValues((player, cards) -> LOGGER.info("Player:{}, cards:{}", player, cards.toString()));
    }
}
