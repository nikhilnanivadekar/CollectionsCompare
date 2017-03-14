package collections.compare.demo.protocols;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Sets;
import org.junit.Assert;
import org.junit.Test;

public class JDK8ProtocolTest {
    private List<String> list = Arrays.asList("one", "two", "three");
    private Set<String> set = new HashSet<>(this.list);
    private MutableBag<String> bag = Bags.mutable.with("one", "two", "three");

    @Test
    public void filter() {
        Predicate<String> equals = "one"::equals;
        List<String> actualListOne =
                this.list.stream().filter(equals).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("one"), actualListOne);

        List<String> actualListTwo =
                this.list.stream().filter(equals.negate()).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("two", "three"), actualListTwo);

        Set<String> actualSetOne =
                this.set.stream().filter(equals).collect(Collectors.toSet());
        Assert.assertEquals(new HashSet<>(Arrays.asList("one")), actualSetOne);

        Bag<String> actualBagOne =
                this.bag.stream().filter(equals).collect(Collectors.toCollection(Bags.mutable::empty));
        Assert.assertEquals(Bags.mutable.with("one"), actualBagOne);
    }

    @Test
    public void groupBy() {
        Map<String, List<String>> groupedList =
                this.list.stream().collect(Collectors.groupingBy(String::toUpperCase));
        Assert.assertEquals(Maps.mutable.with(
                "ONE", Lists.mutable.with("one"),
                "TWO", Lists.mutable.with("two"),
                "THREE", Lists.mutable.with("three")), groupedList);

        Map<String, Set<String>> groupedSet = this.set.stream().collect(
                Collectors.groupingBy(String::toUpperCase,
                        Collectors.mapping(Function.identity(), Collectors.toSet())));
        Assert.assertEquals(Maps.mutable.with(
                "ONE", Sets.mutable.with("one"),
                "TWO", Sets.mutable.with("two"),
                "THREE", Sets.mutable.with("three")), groupedSet);

        Map<String, Map<String, Long>> groupedBag = this.bag.stream().collect(
                Collectors.groupingBy(String::toUpperCase,
                        Collectors.groupingBy(Function.identity(), Collectors.counting())));
        Assert.assertEquals(Maps.mutable.with(
                "ONE", Maps.mutable.with("one", Long.valueOf(1)),
                "TWO", Maps.mutable.with("two", Long.valueOf(1)),
                "THREE", Maps.mutable.with("three", Long.valueOf(1))), groupedBag);
    }
}
