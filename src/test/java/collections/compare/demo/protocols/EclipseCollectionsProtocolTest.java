package collections.compare.demo.protocols;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.Sets;
import org.junit.Assert;
import org.junit.Test;

public class EclipseCollectionsProtocolTest
{
    private MutableList<String> stringMutableList = Lists.mutable.with("one", "two", "three");
    private MutableSet<String> stringMutableSet = Sets.mutable.with("one", "two", "three");
    private MutableBag<String> stringMutableBag = Bags.mutable.with("one", "two", "three");

    @Test
    public void filter()
    {
        // extends java.util.function.Predicate
        Predicate<String> equals = "one"::equals;
        MutableList<String> actualListOne =
                this.stringMutableList.select(equals);
        Assert.assertEquals(Arrays.asList("one"), actualListOne);

        MutableList<String> actualListTwo =
                this.stringMutableList.reject(equals);
        Assert.assertEquals(Arrays.asList("two", "three"), actualListTwo);

        MutableSet<String> actualSetOne =
                this.stringMutableSet.select(equals);
        Assert.assertEquals(new HashSet<>(Arrays.asList("one")), actualSetOne);

        MutableBag<String> actualBagOne =
                this.stringMutableBag.select(equals);
        Assert.assertEquals(Bags.mutable.with("one"), actualBagOne);
    }

    @Test
    public void groupBy()
    {
        MutableListMultimap<String, String> groupedList =
                this.stringMutableList.groupBy(String::toUpperCase);
        MutableListMultimap<String, String> expectedGroupedList =
                Multimaps.mutable.list.with("ONE", "one", "TWO", "two", "THREE", "three");
        Assert.assertEquals(expectedGroupedList, groupedList);

        MutableSetMultimap<String, String> groupedSet =
                this.stringMutableSet.groupBy(String::toUpperCase);
        MutableSetMultimap<String, String> expectedGroupedSet =
                Multimaps.mutable.set.with("ONE", "one", "TWO", "two", "THREE", "three");
        Assert.assertEquals(expectedGroupedSet, groupedSet);

        MutableBagMultimap<String, String> groupedBag =
                this.stringMutableBag.groupBy(String::toUpperCase);
        MutableBagMultimap<String, String> expectedGroupedBag =
                Multimaps.mutable.bag.with("ONE", "one", "TWO", "two", "THREE", "three");
        Assert.assertEquals(expectedGroupedSet, groupedSet);
    }
}
