package collections.compare.demo.cards;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javaslang.collection.List;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.SortedSets;
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
public class CartesianProductTest {
    private EnumSet<Suit> suits = EnumSet.allOf(Suit.class);
    private EnumSet<Rank> ranks = EnumSet.allOf(Rank.class);

    @Benchmark
    public int cartesianProductJDKUnmodifiableSortedSet() {
        SortedSet<Card> set = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(TreeSet::new),
                        Collections::unmodifiableSortedSet));
        return set.size();
    }

    @Benchmark
    public int cartesianProductECImmutableSortedSet1() {
        ImmutableSortedSet<Card> set = ranks.stream()
                .flatMap(rank -> suits.stream().map(suit -> new Card(rank, suit)))
                .collect(Collectors2.toImmutableSortedSet());
        return set.size();
    }

    @Benchmark
    public int cartesianProductECImmutableSortedSet2() {
        ImmutableSortedSet<Card> set = SortedSets.immutable.with(ranks.stream()
                .flatMap(rank -> suits.stream().map(suit -> new Card(rank, suit)))
                .toArray(Card[]::new));
        return set.size();
    }

    @Benchmark
    public int cartesianProductECImmutableSortedSet3() {
        ImmutableSortedSet<Card> set = Sets.cartesianProduct(ranks, suits)
                .collect(pair -> new Card(pair.getOne(), pair.getTwo()))
                .reduceInPlace(Collectors2.toImmutableSortedSet());
        return set.size();
    }

    @Benchmark
    public int cartesianProductGuavaImmutableSortedSet() {
        com.google.common.collect.ImmutableSortedSet<Card> set = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .collect(com.google.common.collect.ImmutableSortedSet.toImmutableSortedSet(Comparator.naturalOrder()));
        return set.size();
    }

    @Benchmark
    public int cartesianProductApacheUnmodifiableSortedSet() {
        SortedSet<Card> set = suits.stream()
                .flatMap(suit -> ranks.stream().map(rank -> new Card(rank, suit)))
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(TreeSet::new),
                        Collections::unmodifiableSortedSet));
        return set.size();
    }

    @Benchmark
    public int cartesianProductJavaslang() {
        javaslang.collection.TreeSet<Card> set = javaslang.collection.TreeSet.ofAll(suits)
                .flatMap(suit -> List.ofAll(ranks).map(rank -> new Card(rank, suit)));
        return set.size();
    }
}
