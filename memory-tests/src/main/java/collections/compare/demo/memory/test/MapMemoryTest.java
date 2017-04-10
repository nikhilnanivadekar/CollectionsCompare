package collections.compare.demo.memory.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;

/**
 * Memory Measurer dependency: https://github.com/DimitrisAndreou/memory-measurer
 * <p>
 * Memory Measurer found from SO: http://stackoverflow.com/a/9368930/5182052
 */
public final class MapMemoryTest
{
    private MapMemoryTest()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void main(String[] args)
    {
        MapMemoryTest.memoryBenchInteger(Maps.mutable.empty());
        MapMemoryTest.memoryBenchInteger(new HashMap<>());
        MapMemoryTest.memoryBenchInteger(new LinkedHashMap<>());
        MapMemoryTest.memoryBenchIntegerJavaSlangMap();
        MapMemoryTest.memoryBenchIntegerGuavaImmutableMap();
        MapMemoryTest.memoryBenchIntegerEcImmutableMap();
        MapMemoryTest.memoryBenchIntegerEcUnmodifiableMap();
        MapMemoryTest.memoryBenchIntegerJdkUnmodifiableMap();

        MapMemoryTest.memoryBenchString(Lists.mutable.empty());
        MapMemoryTest.memoryBenchString(new ArrayList<>());
        MapMemoryTest.memoryBenchString(new LinkedList<>());
        MapMemoryTest.memoryBenchStringJavaSlangMap();
        MapMemoryTest.memoryBenchStringGuavaImmutableMap();
        MapMemoryTest.memoryBenchStringEcImmutableMap();
        MapMemoryTest.memoryBenchStringEcUnmodifiableMap();
        MapMemoryTest.memoryBenchStringJdkUnmodifiableMap();
    }

    private static void memoryBenchInteger(Map<Integer, Integer> map)
    {
        MemoryTestUtils.printMemoryUtilization("Integer", map, map.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            map.put(i, i);
            MemoryTestUtils.printMemoryUtilization("Integer", map, map.size());
        }
    }

    private static void memoryBenchString(Collection<String> collection)
    {
        MemoryTestUtils.printMemoryUtilization("String", collection, collection.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            collection.add(String.valueOf(i));
            MemoryTestUtils.printMemoryUtilization("String", collection, collection.size());
        }
    }

    private static void memoryBenchIntegerJavaSlangMap()
    {
        javaslang.collection.HashMap<Integer, Integer> javaSlangMap = javaslang.collection.HashMap.empty();
        MemoryTestUtils.printMemoryUtilization("Integer", javaSlangMap, javaSlangMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                javaSlangMap = javaslang.collection.HashMap.ofAll(MemoryTestUtils.getIntegerMap(i));
                MemoryTestUtils.printMemoryUtilization("Integer", javaSlangMap, javaSlangMap.size());
            }
        }
    }

    private static void memoryBenchIntegerGuavaImmutableMap()
    {
        com.google.common.collect.ImmutableMap<Integer, Integer> guavaImmutableMap = com.google.common.collect.ImmutableMap.<Integer, Integer>builder().build();
        MemoryTestUtils.printMemoryUtilization("Integer", guavaImmutableMap, guavaImmutableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                guavaImmutableMap = com.google.common.collect.ImmutableMap.<Integer, Integer>builder().putAll(MemoryTestUtils.getIntegerMap(i)).build();
                MemoryTestUtils.printMemoryUtilization("Integer", guavaImmutableMap, guavaImmutableMap.size());
            }
        }
    }

    private static void memoryBenchIntegerEcImmutableMap()
    {
        ImmutableMap<Integer, Integer> ecImmutableMap = Maps.immutable.empty();
        MemoryTestUtils.printMemoryUtilization("Integer", ecImmutableMap, ecImmutableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecImmutableMap = Maps.immutable.withAll(MemoryTestUtils.getIntegerMap(i));
                MemoryTestUtils.printMemoryUtilization("Integer", ecImmutableMap, ecImmutableMap.size());
            }
        }
    }

    private static void memoryBenchIntegerEcUnmodifiableMap()
    {
        MutableMap<Integer, Integer> ecUnmodifiableMap = Maps.mutable.<Integer, Integer>empty().asUnmodifiable();
        MemoryTestUtils.printMemoryUtilization("Integer", ecUnmodifiableMap, ecUnmodifiableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecUnmodifiableMap = UnifiedMap.newMap(MemoryTestUtils.getIntegerMap(i)).asUnmodifiable();
                MemoryTestUtils.printMemoryUtilization("Integer", ecUnmodifiableMap, ecUnmodifiableMap.size());
            }
        }
    }

    private static void memoryBenchIntegerJdkUnmodifiableMap()
    {
        Map<Integer, Integer> jdkUnmodifiableMap = Collections.unmodifiableMap(new HashMap<>());
        MemoryTestUtils.printMemoryUtilization("Integer", jdkUnmodifiableMap, jdkUnmodifiableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                Map<Integer, Integer> map = new HashMap<>();
                map.putAll(MemoryTestUtils.getIntegerMap(i));
                jdkUnmodifiableMap = Collections.unmodifiableMap(map);
                MemoryTestUtils.printMemoryUtilization("Integer", jdkUnmodifiableMap, jdkUnmodifiableMap.size());
            }
        }
    }

    private static void memoryBenchStringJavaSlangMap()
    {
        javaslang.collection.HashMap<String, String> javaSlangMap = javaslang.collection.HashMap.empty();
        MemoryTestUtils.printMemoryUtilization("String", javaSlangMap, javaSlangMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                javaSlangMap = javaslang.collection.HashMap.ofAll(MemoryTestUtils.getStringMap(i));
                MemoryTestUtils.printMemoryUtilization("String", javaSlangMap, javaSlangMap.size());
            }
        }
    }

    private static void memoryBenchStringGuavaImmutableMap()
    {
        com.google.common.collect.ImmutableMap<String, String> guavaImmutableMap = com.google.common.collect.ImmutableMap.<String, String>builder().build();
        MemoryTestUtils.printMemoryUtilization("String", guavaImmutableMap, guavaImmutableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                guavaImmutableMap = com.google.common.collect.ImmutableMap.<String, String>builder().putAll(MemoryTestUtils.getStringMap(i)).build();
                MemoryTestUtils.printMemoryUtilization("String", guavaImmutableMap, guavaImmutableMap.size());
            }
        }
    }

    private static void memoryBenchStringEcImmutableMap()
    {
        ImmutableMap<String, String> ecImmutableMap = Maps.immutable.empty();
        MemoryTestUtils.printMemoryUtilization("String", ecImmutableMap, ecImmutableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecImmutableMap = Maps.immutable.withAll(MemoryTestUtils.getStringMap(i));
                MemoryTestUtils.printMemoryUtilization("String", ecImmutableMap, ecImmutableMap.size());
            }
        }
    }

    private static void memoryBenchStringEcUnmodifiableMap()
    {
        MutableMap<String, String> ecUnmodifiableMap = Maps.mutable.<String, String>empty().asUnmodifiable();
        MemoryTestUtils.printMemoryUtilization("String", ecUnmodifiableMap, ecUnmodifiableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecUnmodifiableMap = UnifiedMap.newMap(MemoryTestUtils.getStringMap(i)).asUnmodifiable();
                MemoryTestUtils.printMemoryUtilization("String", ecUnmodifiableMap, ecUnmodifiableMap.size());
            }
        }
    }

    private static void memoryBenchStringJdkUnmodifiableMap()
    {
        Map<String, String> jdkUnmodifiableMap = Collections.unmodifiableMap(new HashMap<>());
        MemoryTestUtils.printMemoryUtilization("String", jdkUnmodifiableMap, jdkUnmodifiableMap.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                Map<String, String> map = new HashMap<>();
                map.putAll(MemoryTestUtils.getStringMap(i));
                jdkUnmodifiableMap = Collections.unmodifiableMap(map);
                MemoryTestUtils.printMemoryUtilization("String", jdkUnmodifiableMap, jdkUnmodifiableMap.size());
            }
        }
    }
}
