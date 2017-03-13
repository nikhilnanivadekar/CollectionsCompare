package collections.compare.demo.game.fish;

import java.util.Deque;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import collections.compare.demo.cards.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.Rank;
import collections.compare.demo.game.Outcome;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishUsingApacheCommons extends Fish
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FishUsingApacheCommons.class);

    private final ApacheCommonsDeckOfCards deck = new ApacheCommonsDeckOfCards();
    private final Deque<Card> shuffledCards;
    private final SetValuedMap<Integer, Card> cardsPerPlayer = MultiMapUtils.newSetValuedHashMap();

    private final int numberOfPlayers;
    private final long seed;

    private Outcome outcome;

    public FishUsingApacheCommons(int numberOfPlayers, long seed)
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
        Set<Card> cards = this.cardsPerPlayer.get(playerNumber);

        Bag<Rank> ranks = this.getRanks(cards);
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
            this.cardsPerPlayer.removeMapping(nextPlayer, card);
            this.cardsPerPlayer.put(playerNumber, card);
            this.logCardsPerPlayer();
            this.logCardCountPerPlayer();
            return this.checkIfPlayerWins(playerNumber);
        }
    }

    private void logCardCountPerPlayer()
    {
        for (int player : this.cardsPerPlayer.keySet())
        {
            Set<Card> cards = this.cardsPerPlayer.get(player);
            LOGGER.info("Player:{} has {} cards", player, cards.size());
        }
    }

    private boolean checkIfPlayerWins(int playerNumber)
    {
        Bag<Rank> newRanks = this.getRanks(this.cardsPerPlayer.get(playerNumber));
        Rank rank = this.getProbableBook(newRanks);
        int rankCount = newRanks.getCount(rank);
        if (rankCount == 4)
        {
            LOGGER.info("Player:{} has a BOOK! Player:{} WINS!!!", playerNumber, playerNumber);
            this.outcome = Outcome.WINNER;
            return false;
        }
        this.outcome = Outcome.CONTINUE;
        return true;
    }

    private Rank getProbableBook(Bag<Rank> ranks)
    {
        int count = 0;
        Rank topOccurrence = null;
        for (Rank rank : ranks)
        {
            if (ranks.getCount(rank) > count)
            {
                count = ranks.getCount(rank);
                topOccurrence = rank;
            }
        }
        return topOccurrence;
    }

    private Bag<Rank> getRanks(Set<Card> cards)
    {
        return cards.stream().map(Card::getRank).collect(Collectors.toCollection(HashBag::new));
    }

    private void logCardsPerPlayer()
    {
        for (int player : this.cardsPerPlayer.keySet())
        {
            Set<Card> cards = this.cardsPerPlayer.get(player);
            LOGGER.info("Player:{}, cards:{}", player, cards.toString());
        }
    }
}
