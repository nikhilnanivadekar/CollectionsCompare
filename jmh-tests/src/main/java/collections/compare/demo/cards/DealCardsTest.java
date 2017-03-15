package collections.compare.demo.cards;

import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javaslang.collection.Stack;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.stack.MutableStack;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class DealCardsTest {
    @State(Scope.Thread)
    public static class Deck {
        public ApacheCommonsDeckOfCards apacheCommonsDeckOfCards;
        public ApacheCommonsDeckOfCardsAsList apacheCommonsDeckOfCardsAsList;
        public EclipseCollectionsDeckOfCards eclipseCollectionsDeckOfCards;
        public EclipseCollectionsDeckOfCardsAsImmutableList eclipseCollectionsDeckOfCardsAsImmutableList;
        public EclipseCollectionsDeckOfCardsAsReadableList eclipseCollectionsDeckOfCardsAsReadableList;
        public GoogleGuavaDeckOfCards googleGuavaDeckOfCards;
        public GoogleGuavaDeckOfCardsAsImmutableList googleGuavaDeckOfCardsAsImmutableList;
        public JDK8DeckOfCards jdk8DeckOfCards;
        public JDK8DeckOfCardsAsList jdk8DeckOfCardsAsList;
        public JavaSlangDeckOfCards javaslangDeckOfCards;
        public JavaSlangDeckOfCardsAsImmutableList javaslangDeckOfCardsAsImmutableList;

        public Deque<Card> shuffledApacheCommonsDeckOfCards;
        public Deque<Card> shuffledApachedCommonsDeckOfCardsAsList;
        public MutableStack<Card> shuffledEclipseCollectionsDeckOfCards;
        public MutableStack<Card> shuffledEclipseCollectionsDeckOfCardsAsImmutableList;
        public MutableStack<Card> shuffledEclipseCollectionsDeckOfCardsAsReadableList;
        public Deque<Card> shuffledGoogleGuavaDeckOfCards;
        public Deque<Card> shuffledGoogleGuavaDeckOfCardsAsImmutableList;
        public Deque<Card> shuffledJdk8DeckOfCards;
        public Deque<Card> shuffledJdk8DeckOfCardsAsList;
        public Stack<Card> shuffledJavaslangDeckOfCards;
        public Stack<Card> shuffledJavaslangDeckOfCardsAsImmutableList;

        @Setup(Level.Invocation)
        public void setup() {
            apacheCommonsDeckOfCards = new ApacheCommonsDeckOfCards();
            apacheCommonsDeckOfCardsAsList = new ApacheCommonsDeckOfCardsAsList();
            eclipseCollectionsDeckOfCards = new EclipseCollectionsDeckOfCards();
            eclipseCollectionsDeckOfCardsAsImmutableList = new EclipseCollectionsDeckOfCardsAsImmutableList();
            eclipseCollectionsDeckOfCardsAsReadableList = new EclipseCollectionsDeckOfCardsAsReadableList();
            googleGuavaDeckOfCards = new GoogleGuavaDeckOfCards();
            googleGuavaDeckOfCardsAsImmutableList = new GoogleGuavaDeckOfCardsAsImmutableList();
            jdk8DeckOfCards = new JDK8DeckOfCards();
            jdk8DeckOfCardsAsList = new JDK8DeckOfCardsAsList();
            javaslangDeckOfCards = new JavaSlangDeckOfCards();
            javaslangDeckOfCardsAsImmutableList = new JavaSlangDeckOfCardsAsImmutableList();

            shuffledApacheCommonsDeckOfCards = apacheCommonsDeckOfCards.shuffle(new Random(123456789L));
            shuffledApachedCommonsDeckOfCardsAsList = apacheCommonsDeckOfCardsAsList.shuffle(new Random(123456789L));
            shuffledEclipseCollectionsDeckOfCards = eclipseCollectionsDeckOfCards.shuffle(new Random(123456789L));
            shuffledEclipseCollectionsDeckOfCardsAsImmutableList = eclipseCollectionsDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
            shuffledEclipseCollectionsDeckOfCardsAsReadableList = eclipseCollectionsDeckOfCardsAsReadableList.shuffle(new Random(123456789L));
            shuffledGoogleGuavaDeckOfCards = googleGuavaDeckOfCards.shuffle(new Random(123456789L));
            shuffledGoogleGuavaDeckOfCardsAsImmutableList = googleGuavaDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
            shuffledJdk8DeckOfCards = jdk8DeckOfCards.shuffle(new Random(123456789L));
            shuffledJdk8DeckOfCardsAsList = jdk8DeckOfCardsAsList.shuffle(new Random(123456789L));
            shuffledJavaslangDeckOfCards = javaslangDeckOfCards.shuffle(new Random(123456789L));
            shuffledJavaslangDeckOfCardsAsImmutableList = javaslangDeckOfCardsAsImmutableList.shuffle(new Random(123456789L));
        }
    }

    @Benchmark
    public List<Set<Card>> dealApache(Deck deck) {
        return deck.apacheCommonsDeckOfCards.dealHands(deck.shuffledApacheCommonsDeckOfCards, 5, 5);
    }

    @Benchmark
    public List<Set<Card>> dealApacheUnmodifiable(Deck deck) {
        return deck.apacheCommonsDeckOfCardsAsList.dealHands(deck.shuffledApachedCommonsDeckOfCardsAsList, 5, 5);
    }

    @Benchmark
    public ImmutableList<Set<Card>> dealEC(Deck deck) {
        return deck.eclipseCollectionsDeckOfCards.dealHands(deck.shuffledEclipseCollectionsDeckOfCards, 5, 5);
    }

    @Benchmark
    public ImmutableList<Set<Card>> dealECImmutable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsImmutableList.dealHands(deck.shuffledEclipseCollectionsDeckOfCardsAsImmutableList, 5, 5);
    }

    @Benchmark
    public ListIterable<Set<Card>> dealECReadable(Deck deck) {
        return deck.eclipseCollectionsDeckOfCardsAsReadableList.dealHands(deck.shuffledEclipseCollectionsDeckOfCardsAsReadableList, 5, 5);
    }

    @Benchmark
    public com.google.common.collect.ImmutableList<Set<Card>> dealGuava(Deck deck) {
        return deck.googleGuavaDeckOfCards.dealHands(deck.shuffledGoogleGuavaDeckOfCards, 5, 5);
    }

    @Benchmark
    public com.google.common.collect.ImmutableList<Set<Card>> dealGuavaImmutable(Deck deck) {
        return deck.googleGuavaDeckOfCardsAsImmutableList.dealHands(deck.shuffledGoogleGuavaDeckOfCardsAsImmutableList, 5, 5);
    }

    @Benchmark
    public List<Set<Card>> dealJDK(Deck deck) {
        return deck.jdk8DeckOfCards.dealHands(deck.shuffledJdk8DeckOfCards, 5, 5);
    }

    @Benchmark
    public List<Set<Card>> dealJDKUnmodifiable(Deck deck) {
        return deck.jdk8DeckOfCardsAsList.dealHands(deck.shuffledJdk8DeckOfCardsAsList, 5, 5);
    }

    @Benchmark
    public javaslang.collection.List<javaslang.collection.Set<Card>> dealJavaslang(Deck deck) {
        return deck.javaslangDeckOfCards.dealHands(deck.shuffledJavaslangDeckOfCards, 5, 5);
    }

    @Benchmark
    public javaslang.collection.List<javaslang.collection.Set<Card>> dealJavaslangImmutable(Deck deck) {
        return deck.javaslangDeckOfCardsAsImmutableList.dealHands(deck.shuffledJavaslangDeckOfCardsAsImmutableList, 5, 5);
    }
}
