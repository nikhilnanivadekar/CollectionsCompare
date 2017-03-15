package collections.compare.demo.protocol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Function;
import org.apache.commons.collections4.FluentIterable;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.list.Interval;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@SuppressWarnings("RedundantCast")
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class TransformTest {
    @State(Scope.Thread)
    public static class Input {
        public List<Integer> jdkList = new ArrayList<>(Interval.oneTo(100));
        public Set<Integer> jdkSet = new HashSet<>(jdkList);
        public SortedSet<Integer> jdkSortedSet = new TreeSet<>(jdkList);

        public FluentIterable<Integer> apacheFluentList = FluentIterable.of(jdkList);
        public FluentIterable<Integer> apacheFluentSet = FluentIterable.of(jdkSet);

        public com.google.common.collect.FluentIterable<Integer> guavaFluentList =
                com.google.common.collect.FluentIterable.from(jdkList);
        public com.google.common.collect.FluentIterable<Integer> guavaFluentSet =
                com.google.common.collect.FluentIterable.from(jdkSet);
        public com.google.common.collect.ImmutableSortedSet<Integer> guavaImmutableSortedSet =
                com.google.common.collect.ImmutableSortedSet.copyOf(jdkSortedSet);

        public MutableList<Integer> ecList = Lists.mutable.withAll(Interval.oneTo(100));
        public MutableSet<Integer> ecSet = ecList.toSet();
        public ImmutableSortedSet<Integer> ecImmutableSortedSet = SortedSets.immutable.ofAll(jdkSortedSet);

        public javaslang.collection.List<Integer> jsList = javaslang.collection.List.ofAll(Interval.oneTo(100));
        public javaslang.collection.Set<Integer> jsSet = jsList.toSet();
        public javaslang.collection.TreeSet<Integer> jsSortedSet = javaslang.collection.TreeSet.ofAll(jsSet);
    }

    @Benchmark
    public int apacheFluentIterable_List(Input input) {
        List<Integer> evensList = input.apacheFluentList.transform(integer -> integer * 2).toList();
        return evensList.size();
    }

    @Benchmark
    public int apacheFluentIterable_Set(Input input) {
        Set<Integer> evensSet = new HashSet<>();
        input.apacheFluentSet.transform(integer -> integer * 2).copyInto(evensSet);
        return evensSet.size();
    }

    @Benchmark
    public int eclipseCollectionsListEager(Input input) {
        List<Integer> evensList = input.ecList.collect(integer -> integer * 2);
        return evensList.size();
    }

    @Benchmark
    public int eclipseCollectionsSetEager(Input input) {
        Set<Integer> evensSet = input.ecSet.collect(integer -> integer * 2);
        return evensSet.size();
    }

    @Benchmark
    public int eclipseCollectionsImmutableSortedSetEager(Input input) {
        ImmutableList<Integer> evensSet = input.ecImmutableSortedSet.collect(integer -> integer * 2);
        return evensSet.size();
    }

    @Benchmark
    public int eclipseCollectionsListLazy(Input input) {
        List<Integer> evensList = input.ecList.asLazy().collect(integer -> integer * 2).toList();
        return evensList.size();
    }

    @Benchmark
    public int eclipseCollectionsSetLazy(Input input) {
        Set<Integer> evensList = input.ecSet.asLazy().collect(integer -> integer * 2).toSet();
        return evensList.size();
    }

    @Benchmark
    public int eclipseCollectionsImmutableSortedSetLazy(Input input) {
        Set<Integer> evensList = input.ecImmutableSortedSet.asLazy().collect(integer -> integer * 2).toSortedSet();
        return evensList.size();
    }

    @Benchmark
    public int guavaFluentIterable_List(Input input) {
        List<Integer> evensList = input.guavaFluentList.transform((Function<Integer, Integer>) integer -> integer * 2).toList();
        return evensList.size();
    }

    @Benchmark
    public int guavaFluentIterable_Set(Input input) {
        Set<Integer> evensSet = input.guavaFluentSet.transform((Function<Integer, Integer>) integer -> integer * 2).toSet();
        return evensSet.size();
    }

    @Benchmark
    public int guavaImmutableSortedSet(Input input) {
        List<Integer> evensSet = input.guavaImmutableSortedSet.stream().map((Function<Integer, Integer>) integer -> integer * 2).collect(Collectors.toList());
        return evensSet.size();
    }

    @Benchmark
    public int jdkListLazy(Input input) {
        List<Integer> evensList = input.jdkList.stream().map(integer -> integer * 2).collect(Collectors.toList());
        return evensList.size();
    }

    @Benchmark
    public int jdkSetLazy(Input input) {
        Set<Integer> evensSet = input.jdkSet.stream().map(integer -> integer * 2).collect(Collectors.toSet());
        return evensSet.size();
    }

    @Benchmark
    public int jdkSortedSetLazy(Input input) {
        Set<Integer> evensSet = input.jdkSortedSet.stream().map(integer -> integer * 2).collect(Collectors.toSet());
        return evensSet.size();
    }

    @Benchmark
    public int javaSlangList(Input input) {
        javaslang.collection.List<Integer> evensList = input.jsList.map(integer -> integer * 2);
        return evensList.size();
    }

    @Benchmark
    public int javaSlangSet(Input input) {
        javaslang.collection.Set<Integer> evensSet = input.jsSet.map(integer -> integer * 2);
        return evensSet.size();
    }

    @Benchmark
    public int javaSlangSortedSet(Input input) {
        javaslang.collection.TreeSet<Integer> evensSet = input.jsSortedSet.map(integer -> integer * 2);
        return evensSet.size();
    }
}
