package collections.compare.demo.protocols.jmh;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.FluentIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.Interval;
import org.junit.Assert;
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
public class FilterJMHTest
{
    private List<Integer> jdkList = new ArrayList<>(Interval.oneTo(100));
    private Set<Integer> jdkSet = new HashSet<>(jdkList);

    private MutableList<Integer> ecList = Lists.mutable.withAll(Interval.oneTo(100));
    private MutableSet<Integer> ecSet = ecList.toSet();

    private javaslang.collection.List<Integer> jsList = javaslang.collection.List.ofAll(Interval.oneTo(100));
    private javaslang.collection.Set<Integer> jsSet = jsList.toSet();

    @Benchmark
    public void filterJDKLazy()
    {
        List<Integer> evensList = jdkList.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        List<Integer> oddsList = jdkList.stream().filter(integer -> integer % 2 == 1).collect(Collectors.toList());
        Set<Integer> evensSet = jdkSet.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toSet());
        Set<Integer> oddsSet = jdkSet.stream().filter(integer -> integer % 2 == 1).collect(Collectors.toSet());
        Assert.assertEquals(evensList.size(), evensSet.size());
        Assert.assertEquals(oddsList.size(), oddsSet.size());
     }

    @Benchmark
    public void filterApacheFluentIterable()
    {
        List<Integer> evensList = FluentIterable.of(jdkList).filter(integer -> integer % 2 == 0).toList();
        List<Integer> oddsList = FluentIterable.of(jdkList).filter(integer -> integer % 2 == 1).toList();
        Set<Integer> evensSet = new HashSet<>();
        FluentIterable.of(jdkSet).filter(integer -> integer % 2 == 0).copyInto(evensSet);
        Set<Integer> oddsSet = new HashSet<>();
        FluentIterable.of(jdkSet).filter(integer -> integer % 2 == 1).copyInto(oddsSet);
        Assert.assertEquals(evensList.size(), evensSet.size());
        Assert.assertEquals(oddsList.size(), oddsSet.size());
    }

    @Benchmark
    public void filterGuavaFluentIterable()
    {
        List<Integer> evensList =
                com.google.common.collect.FluentIterable.from(jdkList).filter(integer -> integer % 2 == 0).toList();
        List<Integer> oddsList =
                com.google.common.collect.FluentIterable.from(jdkList).filter(integer -> integer % 2 == 1).toList();
        Set<Integer> evensSet =
                com.google.common.collect.FluentIterable.from(jdkSet).filter(integer -> integer % 2 == 0).toSet();
        Set<Integer> oddsSet =
                com.google.common.collect.FluentIterable.from(jdkSet).filter(integer -> integer % 2 == 1).toSet();
        Assert.assertEquals(evensList.size(), evensSet.size());
        Assert.assertEquals(oddsList.size(), oddsSet.size());
    }

    @Benchmark
    public void filterEclipseCollectionsEager()
    {
        List<Integer> evensList = ecList.select(integer -> integer % 2 == 0);
        List<Integer> oddsList = ecList.select(integer -> integer % 2 == 1);
        Set<Integer> evensSet = ecSet.select(integer -> integer % 2 == 0);
        Set<Integer> oddsSet = ecSet.select(integer -> integer % 2 == 1);
        Assert.assertEquals(evensList.size(), evensSet.size());
        Assert.assertEquals(oddsList.size(), oddsSet.size());
    }

    @Benchmark
    public void filterEclipseCollectionsLazy()
    {
        List<Integer> evensList = ecList.asLazy().select(integer -> integer % 2 == 0).toList();
        List<Integer> oddsList = ecList.asLazy().select(integer -> integer % 2 == 1).toList();
        Set<Integer> evensSet = ecSet.asLazy().select(integer -> integer % 2 == 0).toSet();
        Set<Integer> oddsSet = ecSet.asLazy().select(integer -> integer % 2 == 1).toSet();
        Assert.assertEquals(evensList.size(), evensSet.size());
        Assert.assertEquals(oddsList.size(), oddsSet.size());
    }

    @Benchmark
    public void filterJavaSlang()
    {
        javaslang.collection.List<Integer> evensList = jsList.filter(integer -> integer % 2 == 0);
        javaslang.collection.List<Integer> oddsList = jsList.filter(integer -> integer % 2 == 1);
        javaslang.collection.Set<Integer> evensSet = jsSet.filter(integer -> integer % 2 == 0);
        javaslang.collection.Set<Integer> oddsSet = jsSet.filter(integer -> integer % 2 == 1);
        Assert.assertEquals(evensList.size(), evensSet.size());
        Assert.assertEquals(oddsList.size(), oddsSet.size());
    }
}
