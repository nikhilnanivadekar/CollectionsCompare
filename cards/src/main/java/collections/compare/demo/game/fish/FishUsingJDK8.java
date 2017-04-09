package collections.compare.demo.game.fish;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.JDK8DeckOfCards;
import collections.compare.demo.cards.Rank;
import collections.compare.demo.game.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishUsingJDK8 extends Fish
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FishUsingJDK8.class);

    private final JDK8DeckOfCards deck = new JDK8DeckOfCards();
    private final Deque<Card> shuffledCards;
    private final Map<Integer, Set<Card>> cardsPerPlayer = new HashMap<>();

    private final int numberOfPlayers;
    private final long seed;

    private Outcome outcome;

    public FishUsingJDK8(int numberOfPlayers, long seed)
    {
        this.numberOfPlayers = numberOfPlayers;
        this.seed = seed;
        this.shuffledCards = this.deck.shuffle(new Random(this.seed));
        for (int i = 1; i <= numberOfPlayers; i++)
        {
            cardsPerPlayer.put(i, new HashSet<>());
        }
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
                Set<Card> cards = this.cardsPerPlayer.get(j);

                cards.add(this.deck.dealOneCard(this.shuffledCards));
            }
        }
        LOGGER.info("Here are the cards which each player has:");
        this.logCardsPerPlayer();
    }

    @Override
    public boolean playTurn(int playerNumber)
    {
        Set<Card> cards = this.cardsPerPlayer.get(playerNumber);

        Map<Rank, Integer> ranks = this.getRanks(cards);
        Rank probableBook = this.getProbableBook(ranks);
        LOGGER.info("Player:{} can have a probable Book for Rank:{}", playerNumber, probableBook);

        int nextPlayer = playerNumber == this.numberOfPlayers ? 1 : playerNumber + 1;

        Set<Card> cardsWithNextPlayer = this.cardsPerPlayer.get(nextPlayer);
        if (cardsWithNextPlayer.isEmpty())
        {
            LOGGER.info("Player:{} has no cards left! GAME OVER!!!", nextPlayer);
            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            this.outcome = Outcome.PLAYER_NO_CARDS;
            return false;
        }

        Card card = cardsWithNextPlayer.stream()
                .filter(each -> each.isSameRank(probableBook))
                .findFirst()
                .orElse(null);
        LOGGER.info("Next Player:{} " + (card == null ? "No card for Rank:" + probableBook : "has Card:" + card), nextPlayer);

        if (card == null)
        {
            LOGGER.info("Go FISH!");
            if (this.shuffledCards.isEmpty())
            {
                LOGGER.info("Pond is dry. GAME OVER!!!");
                this.logCardsPerPlayer();
                this.logCardCountPerPlayer();
                this.outcome = Outcome.POND_DRY;
                return false;
            }
            Card cardFromPond = this.deck.dealOneCard(this.shuffledCards);
            LOGGER.info("Card from Pond:{}", cardFromPond);
            this.cardsPerPlayer.get(playerNumber).add(cardFromPond);

            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            return this.checkIfPlayerWins(playerNumber);
        }
        else
        {
            this.cardsPerPlayer.get(nextPlayer).remove(card);
            this.cardsPerPlayer.get(playerNumber).add(card);
            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            return this.checkIfPlayerWins(playerNumber);
        }
    }

    private void logCardCountPerPlayer()
    {
        this.cardsPerPlayer.forEach((player, cardsPerPlayer) -> LOGGER.info("Player:{} has {} cards", player, cardsPerPlayer.size()));
    }

    private boolean checkIfPlayerWins(int playerNumber)
    {
        Map<Rank, Integer> newRanks = this.getRanks(this.cardsPerPlayer.get(playerNumber));
        Rank probableBook = this.getProbableBook(newRanks);
        int rankCount = newRanks.get(probableBook);
        if (rankCount == 4)
        {
            LOGGER.info("Player:{} has a BOOK! Player:{} WINS!!!", playerNumber, playerNumber);
            this.outcome = Outcome.WINNER;
            return false;
        }
        this.outcome = Outcome.CONTINUE;
        return true;
    }

    private Rank getProbableBook(Map<Rank, Integer> ranks)
    {
        int count = 0;
        Rank topOccurrences = null;
        for (Rank rank : ranks.keySet())
        {
            if (ranks.get(rank) > count)
            {
                count = ranks.get(rank);
                topOccurrences = rank;
            }
        }
        return topOccurrences;
    }

    private Map<Rank, Integer> getRanks(Set<Card> cards)
    {
        Map<Rank, Integer> ranks = new HashMap<>();
        for (Card card : cards)
        {
            Rank rank = card.getRank();
            Integer count = ranks.get(rank);
            if (count == null)
            {
                ranks.put(rank, 0);
            }
            count = ranks.get(rank) + 1;
            ranks.put(rank, count);
        }
        return ranks;
    }

    private void logCardsPerPlayer()
    {
        this.cardsPerPlayer.forEach((player, cards) -> LOGGER.info("Player:{}, cards:{}", player, cards.toString()));
    }
}
