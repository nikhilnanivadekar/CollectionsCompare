package collections.compare.demo.protocols;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JDK8ProtocolTest
{
    private List<String> stringMutableList = Arrays.asList("one", "two", "three");
    private Set<String> stringMutableSet = new HashSet<>(stringMutableList);
    private MutableBag<String> stringMutableBag = Bags.mutable.with("one", "two", "three");

    @Test
    public void filter()
    {
        Predicate<String> equals = "one"::equals;
        List<String> actualListOne =
                stringMutableList.stream().filter(equals).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("one"), actualListOne);

        List<String> actualListTwo =
                stringMutableList.stream().filter(equals.negate()).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("two", "three"), actualListTwo);

        Set<String> actualSetOne =
                stringMutableSet.stream().filter(equals).collect(Collectors.toSet());
        Assert.assertEquals(new HashSet<>(Arrays.asList("one")), actualSetOne);

        Bag<String> actualBagOne =
                stringMutableBag.stream().filter(equals).collect(Collectors.toCollection(Bags.mutable::empty));
        Assert.assertEquals(Bags.mutable.with("one"), actualBagOne);
    }

    @Test
    public void groupBy()
    {
        Map<String, List<String>> groupedList =
                stringMutableList.stream().collect(Collectors.groupingBy(String::toUpperCase));
        Assert.assertEquals(Maps.mutable.with(
                "ONE", Lists.mutable.with("one"),
                "TWO", Lists.mutable.with("two"),
                "THREE", Lists.mutable.with("three")), groupedList);

        Map<String, Set<String>> groupedSet = stringMutableSet.stream().collect(
                Collectors.groupingBy(String::toUpperCase,
                        Collectors.mapping(Function.identity(), Collectors.toSet())));
        Assert.assertEquals(Maps.mutable.with(
                "ONE", Sets.mutable.with("one"),
                "TWO", Sets.mutable.with("two"),
                "THREE", Sets.mutable.with("three")), groupedSet);

        Map<String, Map<String, Long>> groupedBag = stringMutableBag.stream().collect(
                Collectors.groupingBy(String::toUpperCase,
                        Collectors.groupingBy(Function.identity(), Collectors.counting())));
        Assert.assertEquals(Maps.mutable.with(
                "ONE", Maps.mutable.with("one", Long.valueOf(1)),
                "TWO", Maps.mutable.with("two", Long.valueOf(1)),
                "THREE", Maps.mutable.with("three", Long.valueOf(1))), groupedBag);
    }
}
