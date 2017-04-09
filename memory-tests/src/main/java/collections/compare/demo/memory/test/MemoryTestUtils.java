package collections.compare.demo.memory.test;

import java.util.List;

import objectexplorer.MemoryMeasurer;
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
        return MemoryMeasurer.measureBytes(object) / 1024;
    }

    public static List<String> getStringList(int size)
    {
        return Interval.fromTo(0, size - 1).collect(String::valueOf).toList();
    }
}
