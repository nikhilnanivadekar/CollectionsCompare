package collections.compare.demo;

import java.util.Arrays;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.Assert;
import org.junit.Test;

public class IntroTest
{
    private static final String HELLO_WORLD = "Hello World";

    @Test
    public void helloWorld()
    {
        Assert.assertEquals("Hello World", HELLO_WORLD);
    }

    @Test
    public void apacheCollections()
    {
        Assert.assertEquals(Arrays.asList("Hello World"), CollectionUtils.select(Arrays.asList(HELLO_WORLD), a -> true));
    }

    @Test
    public void eclipseCollections()
    {
        Assert.assertEquals("Hello World", Lists.mutable.with(HELLO_WORLD).getOnly());
    }

    @Test
    public void guava()
    {
        Assert.assertEquals("Hello World", com.google.common.collect.Lists.newArrayList(HELLO_WORLD).get(0));
    }
}
