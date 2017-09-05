package collections.compare.demo.protocols;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import org.eclipse.collections.impl.factory.Maps;
import org.junit.Assert;
import org.junit.Test;

public class VavrProtocolTest
{
    private List<String> list = List.of("one", "two", "three");
    private Set<String> set = list.toSet();

    @Test
    public void filter()
    {
        Predicate<String> equals = "one"::equals;
        List<String> actualListOne =
                this.list.filter(equals);
        Assert.assertEquals(Arrays.asList("one"), actualListOne.toJavaList());

        List<String> actualListTwo =
                this.list.filter(equals.negate());
        Assert.assertEquals(Arrays.asList("two", "three"), actualListTwo.toJavaList());

        Set<String> actualSetOne =
                this.set.filter(equals);
        Assert.assertEquals(new HashSet<>(Arrays.asList("one")), actualSetOne.toJavaSet());
    }

    @Test
    public void groupBy()
    {
        Map<String, List<String>> groupedList =
                this.list.groupBy(String::toUpperCase);
        Assert.assertEquals(Maps.mutable.with(
                "ONE", List.of("one"),
                "TWO", List.of("two"),
                "THREE", List.of("three")), groupedList.toJavaMap());

        Map<String, ? extends Set<String>> groupedSet = this.set.groupBy(String::toUpperCase);
        Assert.assertEquals(Maps.mutable.with(
                "ONE", List.of("one").toSet(),
                "TWO", List.of("two").toSet(),
                "THREE", List.of("three").toSet()), groupedSet.toJavaMap());
    }
}
