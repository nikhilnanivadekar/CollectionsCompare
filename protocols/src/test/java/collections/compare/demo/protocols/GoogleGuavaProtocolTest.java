package collections.compare.demo.protocols;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

public class GoogleGuavaProtocolTest {
    private List<String> list = Lists.newArrayList("one", "two", "three");
    private Set<String> set = Sets.newHashSet("one", "two", "three");
    private Multiset<String> multiset = HashMultiset.create(Lists.newArrayList("one", "two", "three"));

    @Test
    public void filter() {
        // extends java.util.function.Predicate
        Predicate<String> equals = "one"::equals;
        ImmutableList<String> actualListOne =
                FluentIterable.from(this.list).filter(equals).toList();
        Assert.assertEquals(Arrays.asList("one"), actualListOne);

        ImmutableList<String> actualListTwo =
                FluentIterable.from(this.list).filter(e -> !"one".equals(e)).toList();
        Assert.assertEquals(Arrays.asList("two", "three"), actualListTwo);

        ImmutableSet<String> actualSetOne =
                FluentIterable.from(this.set).filter(equals).toSet();
        Assert.assertEquals(new HashSet<>(Arrays.asList("one")), actualSetOne);

        ImmutableMultiset<String> actualMultisetOne =
                FluentIterable.from(this.multiset).filter(equals).toMultiset();
        Assert.assertEquals(HashMultiset.create(Arrays.asList("one")), actualMultisetOne);
    }

    @Test
    public void groupBy() {
        ListMultimap<String, String> groupedList = this.list.stream().collect(
                Multimaps.toMultimap(
                        String::toUpperCase,
                        Function.identity(),
                        MultimapBuilder.hashKeys().arrayListValues()::build));
        ListMultimap<String, String> expectedGroupedList =
                MultimapBuilder.hashKeys().arrayListValues().build();
        expectedGroupedList.put("ONE", "one");
        expectedGroupedList.put("TWO", "two");
        expectedGroupedList.put("THREE", "three");
        Assert.assertEquals(expectedGroupedList, groupedList);

        SetMultimap<String, String> groupedSet = this.set.stream().collect(
                Multimaps.toMultimap(
                        String::toUpperCase,
                        Function.identity(),
                        MultimapBuilder.hashKeys().hashSetValues()::build));
        SetMultimap<String, String> expectedGroupedSet =
                MultimapBuilder.hashKeys().hashSetValues().build();
        expectedGroupedSet.put("ONE", "one");
        expectedGroupedSet.put("TWO", "two");
        expectedGroupedSet.put("THREE", "three");
        Assert.assertEquals(expectedGroupedSet, groupedSet);
    }
}
