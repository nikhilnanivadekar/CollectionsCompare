package collections.compare.demo.memory.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.Interval;

/**
 * Memory Measurer dependency: https://github.com/DimitrisAndreou/memory-measurer
 * <p>
 * Memory Measurer found from SO: http://stackoverflow.com/a/9368930/5182052
 */
public final class SetMemoryTest
{
    private SetMemoryTest()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void main(String[] args)
    {
        SetMemoryTest.memoryBenchInteger(Sets.mutable.empty());
        SetMemoryTest.memoryBenchInteger(new HashSet<>());
        SetMemoryTest.memoryBenchInteger(new LinkedHashSet<>());
        SetMemoryTest.memoryBenchIntegerJavaSlangSet();
        SetMemoryTest.memoryBenchIntegerGuavaImmutableSet();
        SetMemoryTest.memoryBenchIntegerEcImmutableSet();
        SetMemoryTest.memoryBenchIntegerEcUnmodifiableSet();
        SetMemoryTest.memoryBenchIntegerJdkUnmodifiableSet();

        SetMemoryTest.memoryBenchString(Lists.mutable.empty());
        SetMemoryTest.memoryBenchString(new ArrayList<>());
        SetMemoryTest.memoryBenchString(new LinkedList<>());
        SetMemoryTest.memoryBenchStringJavaSlangSet();
        SetMemoryTest.memoryBenchStringGuavaImmutableSet();
        SetMemoryTest.memoryBenchStringEcImmutableSet();
        SetMemoryTest.memoryBenchStringEcUnmodifiableSet();
        SetMemoryTest.memoryBenchStringJdkUnmodifiableSet();
    }

    private static void memoryBenchInteger(Collection<Integer> collection)
    {
        MemoryTestUtils.printMemoryUtilization("Integer", collection, collection.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            collection.add(i);
            MemoryTestUtils.printMemoryUtilization("Integer", collection, collection.size());
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

    private static void memoryBenchIntegerJavaSlangSet()
    {
        javaslang.collection.HashSet<Integer> javaSlangSet = javaslang.collection.HashSet.empty();
        MemoryTestUtils.printMemoryUtilization("Integer", javaSlangSet, javaSlangSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                javaSlangSet = javaslang.collection.HashSet.ofAll(Interval.fromTo(0, i - 1));
                MemoryTestUtils.printMemoryUtilization("Integer", javaSlangSet, javaSlangSet.size());
            }
        }
    }

    private static void memoryBenchIntegerGuavaImmutableSet()
    {
        com.google.common.collect.ImmutableSet<Integer> guavaImmutableSet = com.google.common.collect.ImmutableSet.<Integer>builder().build();
        MemoryTestUtils.printMemoryUtilization("Integer", guavaImmutableSet, guavaImmutableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                guavaImmutableSet = com.google.common.collect.ImmutableSet.<Integer>builder().addAll(Interval.fromTo(0, i - 1)).build();
                MemoryTestUtils.printMemoryUtilization("Integer", guavaImmutableSet, guavaImmutableSet.size());
            }
        }
    }

    private static void memoryBenchIntegerEcImmutableSet()
    {
        ImmutableSet<Integer> ecImmutableSet = Sets.immutable.empty();
        MemoryTestUtils.printMemoryUtilization("Integer", ecImmutableSet, ecImmutableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecImmutableSet = Sets.immutable.withAll(Interval.fromTo(0, i - 1));
                MemoryTestUtils.printMemoryUtilization("Integer", ecImmutableSet, ecImmutableSet.size());
            }
        }
    }

    private static void memoryBenchIntegerEcUnmodifiableSet()
    {
        MutableSet<Integer> ecUnmodifiableSet = Sets.mutable.<Integer>empty().asUnmodifiable();
        MemoryTestUtils.printMemoryUtilization("Integer", ecUnmodifiableSet, ecUnmodifiableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecUnmodifiableSet = Sets.mutable.withAll(Interval.fromTo(0, i - 1)).asUnmodifiable();
                MemoryTestUtils.printMemoryUtilization("Integer", ecUnmodifiableSet, ecUnmodifiableSet.size());
            }
        }
    }

    private static void memoryBenchIntegerJdkUnmodifiableSet()
    {
        Set<Integer> jdkUnmodifiableSet = Collections.unmodifiableSet(new HashSet<>());
        MemoryTestUtils.printMemoryUtilization("Integer", jdkUnmodifiableSet, jdkUnmodifiableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                Set<Integer> set = new HashSet<>();
                set.addAll(Interval.fromTo(0, i - 1));
                jdkUnmodifiableSet = Collections.unmodifiableSet(set);
                MemoryTestUtils.printMemoryUtilization("Integer", jdkUnmodifiableSet, jdkUnmodifiableSet.size());
            }
        }
    }

    private static void memoryBenchStringJavaSlangSet()
    {
        javaslang.collection.HashSet<String> javaSlangSet = javaslang.collection.HashSet.empty();
        MemoryTestUtils.printMemoryUtilization("String", javaSlangSet, javaSlangSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                javaSlangSet = javaslang.collection.HashSet.ofAll(MemoryTestUtils.getStringList(i));
                MemoryTestUtils.printMemoryUtilization("String", javaSlangSet, javaSlangSet.size());
            }
        }
    }

    private static void memoryBenchStringGuavaImmutableSet()
    {
        com.google.common.collect.ImmutableSet<String> guavaImmutableSet = com.google.common.collect.ImmutableSet.<String>builder().build();
        MemoryTestUtils.printMemoryUtilization("String", guavaImmutableSet, guavaImmutableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                guavaImmutableSet = com.google.common.collect.ImmutableSet.<String>builder().addAll(MemoryTestUtils.getStringList(i)).build();
                MemoryTestUtils.printMemoryUtilization("String", guavaImmutableSet, guavaImmutableSet.size());
            }
        }
    }

    private static void memoryBenchStringEcImmutableSet()
    {
        ImmutableSet<String> ecImmutableSet = Sets.immutable.empty();
        MemoryTestUtils.printMemoryUtilization("String", ecImmutableSet, ecImmutableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecImmutableSet = Sets.immutable.withAll(MemoryTestUtils.getStringList(i));
                MemoryTestUtils.printMemoryUtilization("String", ecImmutableSet, ecImmutableSet.size());
            }
        }
    }

    private static void memoryBenchStringEcUnmodifiableSet()
    {
        MutableSet<String> ecUnmodifiableSet = Sets.mutable.<String>empty().asUnmodifiable();
        MemoryTestUtils.printMemoryUtilization("String", ecUnmodifiableSet, ecUnmodifiableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecUnmodifiableSet = Sets.mutable.withAll(MemoryTestUtils.getStringList(i)).asUnmodifiable();
                MemoryTestUtils.printMemoryUtilization("String", ecUnmodifiableSet, ecUnmodifiableSet.size());
            }
        }
    }

    private static void memoryBenchStringJdkUnmodifiableSet()
    {
        Set<String> jdkUnmodifiableSet = Collections.unmodifiableSet(new HashSet<>());
        MemoryTestUtils.printMemoryUtilization("String", jdkUnmodifiableSet, jdkUnmodifiableSet.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                Set<String> set = new HashSet<>();
                set.addAll(MemoryTestUtils.getStringList(i));
                jdkUnmodifiableSet = Collections.unmodifiableSet(set);
                MemoryTestUtils.printMemoryUtilization("String", jdkUnmodifiableSet, jdkUnmodifiableSet.size());
            }
        }
    }
}
