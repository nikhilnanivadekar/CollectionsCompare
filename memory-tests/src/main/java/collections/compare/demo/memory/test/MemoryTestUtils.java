package collections.compare.demo.memory.test;

import java.util.List;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.list.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MemoryTestUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTestUtils.class);

    private MemoryTestUtils()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void printMemoryUtilization(String type, Object object, int size)
    {
        if (size % 10_000 == 0)
        {
            System.gc();
            System.gc();
            System.gc();
            LOGGER.info("{} Class {} Size:{} Memory:{} Kb", type, object.getClass(), size, MemoryTestUtils.measureKbs(object));
        }
    }

    public static long measureKbs(Object object)
    {
        return ObjectSizeCalculator.getObjectSize(object) / 1024;
    }

    public static List<String> getStringList(int size)
    {
        return Interval.fromTo(0, size - 1).collect(String::valueOf).toList();
    }

    public static MutableMap<Integer, Integer> getIntegerMap(int size)
    {
        MutableMap<Integer, Integer> map = Maps.mutable.withInitialCapacity(size);
        Interval.fromTo(0, size - 1).each(each -> map.put(each, -each));
        return map;
    }

    public static MutableMap<String, String> getStringMap(int size)
    {
        MutableMap<String, String> map = Maps.mutable.withInitialCapacity(size);
        Interval.fromTo(0, size - 1).each(each -> map.put(String.valueOf(each), String.valueOf(-each)));
        return map;
    }

}
