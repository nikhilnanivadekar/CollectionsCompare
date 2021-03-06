package collections.compare.demo.protocols;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.FluentIterable;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.junit.Assert;
import org.junit.Test;

public class ApacheCommonsProtocolTest
{
    private List<String> list = Arrays.asList("one", "two", "three");
    private Set<String> set = new HashSet<>(this.list);
    private Bag<String> bag = new HashBag<>(this.list);

    @Test
    public void filter()
    {
        // does not extend java.util.function.Predicate
        Predicate<String> equals = "one"::equals;
        List<String> actualListOne =
                FluentIterable.of(this.list).filter(equals).toList();
        Assert.assertEquals(Arrays.asList("one"), actualListOne);

        List<String> actualListTwo =
                FluentIterable.of(this.list).filter(e -> !"one".equals(e)).toList();
        Assert.assertEquals(Arrays.asList("two", "three"), actualListTwo);

        Set<String> actualSetOne = new HashSet<>();
        FluentIterable.of(this.set).filter(equals).copyInto(actualSetOne);
        Assert.assertEquals(new HashSet<>(Arrays.asList("one")), actualSetOne);

        MultiSet<String> actualMultiSetOne = new HashMultiSet<>();
        FluentIterable.of(this.bag).filter(equals).copyInto(actualMultiSetOne);
        Assert.assertEquals(new HashMultiSet<>(Arrays.asList("one")), actualMultiSetOne);
    }

    @Test
    public void groupBy()
    {
        // No Protocol Alternative Found
    }
}
