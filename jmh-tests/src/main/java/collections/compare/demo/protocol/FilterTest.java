package collections.compare.demo.protocol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.FluentIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.list.Interval;
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
public class FilterTest
{
    @State(Scope.Thread)
    public static class Input
    {
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
    public int apacheFluentIterable_List(Input input)
    {
        List<Integer> evensList = input.apacheFluentList.filter(integer -> integer % 2 == 0).toList();
        return evensList.size();
    }

    @Benchmark
    public int apacheFluentIterable_Set(Input input)
    {
        Set<Integer> evensSet = new HashSet<>();
        input.apacheFluentSet.filter(integer -> integer % 2 == 0).copyInto(evensSet);
        return evensSet.size();
    }

    @Benchmark
    public int eclipseCollectionsListEager(Input input)
    {
        List<Integer> evensList = input.ecList.select(integer -> integer % 2 == 0);
        return evensList.size();
    }

    @Benchmark
    public int eclipseCollectionsSetEager(Input input)
    {
        Set<Integer> evensSet = input.ecSet.select(integer -> integer % 2 == 0);
        return evensSet.size();
    }

    @Benchmark
    public int eclipseCollectionsImmutableSortedSetEager(Input input)
    {
        // Purposely using the target collection variant since other frameworks do not return an immutable collection.
        // Creating an immutable collection is a O(n) operation, and we are not trying to test that.
        Set<Integer> evensSet = input.ecImmutableSortedSet.select(integer -> integer % 2 == 0, Sets.mutable.empty());
        return evensSet.size();
    }

    @Benchmark
    public int eclipseCollectionsListLazy(Input input)
    {
        List<Integer> evensList = input.ecList.asLazy().select(integer -> integer % 2 == 0).toList();
        return evensList.size();
    }

    @Benchmark
    public int eclipseCollectionsSetLazy(Input input)
    {
        Set<Integer> evensList = input.ecSet.asLazy().select(integer -> integer % 2 == 0).toSet();
        return evensList.size();
    }

    @Benchmark
    public int eclipseCollectionsImmutableSortedSetLazy(Input input)
    {
        Set<Integer> evensList = input.ecImmutableSortedSet.asLazy().select(integer -> integer % 2 == 0).toSet();
        return evensList.size();
    }

    @Benchmark
    public int guavaFluentIterable_List(Input input)
    {
        List<Integer> evensList = input.guavaFluentList.filter(integer -> integer % 2 == 0).toList();
        return evensList.size();
    }

    @Benchmark
    public int guavaFluentIterable_Set(Input input)
    {
        Set<Integer> evensSet = input.guavaFluentSet.filter(integer -> integer % 2 == 0).toSet();
        return evensSet.size();
    }

    @Benchmark
    public int guavaImmutableSortedSet(Input input)
    {
        Set<Integer> evensSet = input.guavaImmutableSortedSet.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toSet());
        return evensSet.size();
    }

    @Benchmark
    public int jdkListLazy(Input input)
    {
        List<Integer> evensList = input.jdkList.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        return evensList.size();
    }

    @Benchmark
    public int jdkSetLazy(Input input)
    {
        Set<Integer> evensSet = input.jdkSet.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toSet());
        return evensSet.size();
    }

    @Benchmark
    public int jdkSortedSetLazy(Input input)
    {
        Set<Integer> evensSet = input.jdkSortedSet.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toSet());
        return evensSet.size();
    }

    @Benchmark
    public int javaSlangList(Input input)
    {
        javaslang.collection.List<Integer> evensList = input.jsList.filter(integer -> integer % 2 == 0);
        return evensList.size();
    }

    @Benchmark
    public int javaSlangSet(Input input)
    {
        javaslang.collection.Set<Integer> evensSet = input.jsSet.filter(integer -> integer % 2 == 0);
        return evensSet.size();
    }

    @Benchmark
    public int javaSlangSortedSet(Input input)
    {
        javaslang.collection.TreeSet<Integer> evensSet = input.jsSortedSet.filter(integer -> integer % 2 == 0);
        return evensSet.size();
    }
}
