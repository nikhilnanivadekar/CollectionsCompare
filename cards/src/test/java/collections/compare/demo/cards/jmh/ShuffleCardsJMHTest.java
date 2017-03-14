package collections.compare.demo.cards.jmh;

import java.util.Deque;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.ApacheCommonsDeckOfCardsAsList;
import collections.compare.demo.cards.Card;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCardsAsImmutableList;
import collections.compare.demo.cards.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.GoogleGuavaDeckOfCardsAsImmutableList;
import collections.compare.demo.cards.JDK8DeckOfCards;
import collections.compare.demo.cards.JDK8DeckOfCardsAsList;
import collections.compare.demo.cards.JavaSlangDeckOfCards;
import collections.compare.demo.cards.JavaSlangDeckOfCardsAsImmutableList;
import javaslang.collection.Stack;
import org.eclipse.collections.api.stack.MutableStack;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class ShuffleCardsJMHTest {
    @State(Scope.Thread)
    public static class Deck {
        public ApacheCommonsDeckOfCards apacheCommonsDeckOfCards = new ApacheCommonsDeckOfCards();
        public ApacheCommonsDeckOfCardsAsList apacheCommonsDeckOfCardsAsList = new ApacheCommonsDeckOfCardsAsList();
        public EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCards = new EclipseCollectionsDeckOfCards();
        public EclipseCollectionsDeckOfCardsAsImmutableList eclipseCollectionsDeckOfCardsAsImmutableList = new EclipseCollectionsDeckOfCardsAsImmutableList();
        public EclipseCollectionsDeckOfCardsAsReadableList eclipseCollectionsDeckOfCardsAsReadableList = new EclipseCollectionsDeckOfCardsAsReadableList();
        public GoogleGuavaDeckOfCards googleGuavaDeckOfCards = new GoogleGuavaDeckOfCards();
        public GoogleGuavaDeckOfCardsAsImmutableList googleGuavaDeckOfCardsAsImmutableList = new GoogleGuavaDeckOfCardsAsImmutableList();
        public JDK8DeckOfCards jdk8DeckOfCards = new JDK8DeckOfCards();
        public JDK8DeckOfCardsAsList jdk8DeckOfCardsAsList = new JDK8DeckOfCardsAsList();
        public JavaSlangDeckOfCards javaslangDeckOfCards = new JavaSlangDeckOfCards();
        public JavaSlangDeckOfCardsAsImmutableList javaslangDeckOfCardsAsImmutableList = new JavaSlangDeckOfCardsAsImmutableList();
    }

    @Benchmark
    public Deque<Card> shuffleApache(Deck deck) {
        return deck.apacheCommonsDeckOfCards.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Deque<Card> shuffleApacheUnmodifiable(Deck deck) {
        return deck.apacheCommonsDeckOfCardsAsList.shuffle(new Random(123456789L));
    }

    @Benchmark
    public MutableStack<Card> shuffleEC(Deck deck) {
        return deck.eclipseCollectionsDeckOfCards.shuffle(new Random(123456789L));
    }

    @Benchmark
    public MutableStack<Card> shuffleECImmutable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
    }

    @Benchmark
    public MutableStack<Card> shuffleECReadable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Deque<Card> shuffleGuava(Deck deck) {
        return deck.googleGuavaDeckOfCards.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Deque<Card> shuffleGuavaImmutable(Deck deck) {
        return deck.googleGuavaDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Deque<Card> shuffleJDK(Deck deck) {
        return deck.jdk8DeckOfCards.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Deque<Card> shuffleJDKUnmodifiable(Deck deck) {
        return deck.jdk8DeckOfCardsAsList.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Stack<Card> shuffleJavaslang(Deck deck) {
        return deck.javaslangDeckOfCards.shuffle(new Random(123456789L));
    }

    @Benchmark
    public Stack<Card> shuffleJavaslangImmutable(Deck deck) {
        return deck.javaslangDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
    }
}
