package collections.compare.demo.cards;

import java.util.concurrent.TimeUnit;

import collections.compare.demo.cards.list.ApacheCommonsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCards;
import collections.compare.demo.cards.list.EclipseCollectionsDeckOfCardsAsReadableList;
import collections.compare.demo.cards.list.GoogleGuavaDeckOfCards;
import collections.compare.demo.cards.list.JDK8DeckOfCards;
import collections.compare.demo.cards.list.VavrDeckOfCards;
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
public class DeckCreationTest
{
    @Benchmark
    public collections.compare.demo.cards.sortedset.ApacheCommonsDeckOfCards deckApache()
    {
        return new collections.compare.demo.cards.sortedset.ApacheCommonsDeckOfCards();
    }

    @Benchmark
    public ApacheCommonsDeckOfCards deckApacheUnmodifiable()
    {
        return new ApacheCommonsDeckOfCards();
    }

    @Benchmark
    public collections.compare.demo.cards.sortedset.EclipseCollectionsDeckOfCards deckEC()
    {
        return new collections.compare.demo.cards.sortedset.EclipseCollectionsDeckOfCards();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCards deckECImmutable()
    {
        return new EclipseCollectionsDeckOfCards();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCardsAsReadableList deckECReadable()
    {
        return new EclipseCollectionsDeckOfCardsAsReadableList();
    }

    @Benchmark
    public collections.compare.demo.cards.sortedset.GoogleGuavaDeckOfCards deckGuava()
    {
        return new collections.compare.demo.cards.sortedset.GoogleGuavaDeckOfCards();
    }

    @Benchmark
    public GoogleGuavaDeckOfCards deckGuavaImmutable()
    {
        return new GoogleGuavaDeckOfCards();
    }

    @Benchmark
    public collections.compare.demo.cards.sortedset.JDK8DeckOfCards deckJDK()
    {
        return new collections.compare.demo.cards.sortedset.JDK8DeckOfCards();
    }

    @Benchmark
    public JDK8DeckOfCards deckJDKUnmodifiable()
    {
        return new JDK8DeckOfCards();
    }

    @Benchmark
    public collections.compare.demo.cards.sortedset.VavrDeckOfCards deckJavaslang()
    {
        return new collections.compare.demo.cards.sortedset.VavrDeckOfCards();
    }

    @Benchmark
    public VavrDeckOfCards deckJavaslangImmutable()
    {
        return new VavrDeckOfCards();
    }
}
