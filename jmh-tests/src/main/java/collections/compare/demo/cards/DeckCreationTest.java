package collections.compare.demo.cards;

import java.util.concurrent.TimeUnit;

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
    public ApacheCommonsDeckOfCards deckApache()
    {
        return new ApacheCommonsDeckOfCards();
    }

    @Benchmark
    public ApacheCommonsDeckOfCardsAsList deckApacheUnmodifiable()
    {
        return new ApacheCommonsDeckOfCardsAsList();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCards deckEC()
    {
        return new EclipseCollectionsDeckOfCards();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCardsAsImmutableList deckECImmutable()
    {
        return new EclipseCollectionsDeckOfCardsAsImmutableList();
    }

    @Benchmark
    public EclipseCollectionsDeckOfCardsAsReadableList deckECReadable()
    {
        return new EclipseCollectionsDeckOfCardsAsReadableList();
    }

    @Benchmark
    public GoogleGuavaDeckOfCards deckGuava()
    {
        return new GoogleGuavaDeckOfCards();
    }

    @Benchmark
    public GoogleGuavaDeckOfCardsAsImmutableList deckGuavaImmutable()
    {
        return new GoogleGuavaDeckOfCardsAsImmutableList();
    }

    @Benchmark
    public JDK8DeckOfCards deckJDK()
    {
        return new JDK8DeckOfCards();
    }

    @Benchmark
    public JDK8DeckOfCardsAsList deckJDKUnmodifiable()
    {
        return new JDK8DeckOfCardsAsList();
    }

    @Benchmark
    public VavrDeckOfCards deckJavaslang()
    {
        return new VavrDeckOfCards();
    }

    @Benchmark
    public VavrDeckOfCardsAsImmutableList deckJavaslangImmutable()
    {
        return new VavrDeckOfCardsAsImmutableList();
    }
}
