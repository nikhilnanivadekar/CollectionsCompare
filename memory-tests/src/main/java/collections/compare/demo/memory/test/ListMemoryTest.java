package collections.compare.demo.memory.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.Interval;

/**
 * Memory Measurer dependency: https://github.com/DimitrisAndreou/memory-measurer
 * <p>
 * Memory Measurer found from SO: http://stackoverflow.com/a/9368930/5182052
 */
public final class ListMemoryTest
{
    private ListMemoryTest()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void main(String[] args)
    {
        ListMemoryTest.memoryBenchInteger(Lists.mutable.empty());
        ListMemoryTest.memoryBenchInteger(new ArrayList<>());
        ListMemoryTest.memoryBenchInteger(new LinkedList<>());
        ListMemoryTest.memoryBenchIntegerJavaSlangList();
        ListMemoryTest.memoryBenchIntegerGuavaImmutableList();
        ListMemoryTest.memoryBenchIntegerEcImmutableList();
        ListMemoryTest.memoryBenchIntegerEcUnmodifiableList();
        ListMemoryTest.memoryBenchIntegerJdkUnmodifiableList();

        ListMemoryTest.memoryBenchString(Lists.mutable.empty());
        ListMemoryTest.memoryBenchString(new ArrayList<>());
        ListMemoryTest.memoryBenchString(new LinkedList<>());
        ListMemoryTest.memoryBenchStringJavaSlangList();
        ListMemoryTest.memoryBenchStringGuavaImmutableList();
        ListMemoryTest.memoryBenchStringEcImmutableList();
        ListMemoryTest.memoryBenchStringEcUnmodifiableList();
        ListMemoryTest.memoryBenchStringJdkUnmodifiableList();
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

    private static void memoryBenchIntegerJavaSlangList()
    {
        javaslang.collection.List<Integer> javaSlangList = javaslang.collection.List.empty();
        MemoryTestUtils.printMemoryUtilization("Integer", javaSlangList, javaSlangList.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            if (i % 10_000 == 0)
            {
                javaSlangList = javaslang.collection.List.ofAll(Interval.fromTo(0, i));
                MemoryTestUtils.printMemoryUtilization("Integer", javaSlangList, javaSlangList.size());
            }
        }
    }

    private static void memoryBenchIntegerGuavaImmutableList()
    {
        com.google.common.collect.ImmutableList<Integer> guavaImmutableList = com.google.common.collect.ImmutableList.<Integer>builder().build();
        MemoryTestUtils.printMemoryUtilization("Integer", guavaImmutableList, guavaImmutableList.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            if (i % 10_000 == 0)
            {
                guavaImmutableList = com.google.common.collect.ImmutableList.<Integer>builder().addAll(Interval.fromTo(0, i)).build();
                MemoryTestUtils.printMemoryUtilization("Integer", guavaImmutableList, guavaImmutableList.size());
            }
        }
    }

    private static void memoryBenchIntegerEcImmutableList()
    {
        ImmutableList<Integer> ecImmutableList = Lists.immutable.empty();
        MemoryTestUtils.printMemoryUtilization("Integer", ecImmutableList, ecImmutableList.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            if (i % 10_000 == 0)
            {
                ecImmutableList = Lists.immutable.withAll(Interval.fromTo(0, i));
                MemoryTestUtils.printMemoryUtilization("Integer", ecImmutableList, ecImmutableList.size());
            }
        }
    }

    private static void memoryBenchIntegerEcUnmodifiableList()
    {
        MutableList<Integer> ecUnmodifiableList = Lists.mutable.<Integer>empty().asUnmodifiable();
        MemoryTestUtils.printMemoryUtilization("Integer", ecUnmodifiableList, ecUnmodifiableList.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            if (i % 10_000 == 0)
            {
                ecUnmodifiableList = Lists.mutable.withAll(Interval.fromTo(0, i)).asUnmodifiable();
                MemoryTestUtils.printMemoryUtilization("Integer", ecUnmodifiableList, ecUnmodifiableList.size());
            }
        }
    }

    private static void memoryBenchIntegerJdkUnmodifiableList()
    {
        List<Integer> jdkUnmodifiableList = Collections.unmodifiableList(new ArrayList<>());
        MemoryTestUtils.printMemoryUtilization("Integer", jdkUnmodifiableList, jdkUnmodifiableList.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            if (i % 10_000 == 0)
            {
                jdkUnmodifiableList = Collections.unmodifiableList(Arrays.asList(Interval.fromTo(0, i).toArray()));
                MemoryTestUtils.printMemoryUtilization("Integer", jdkUnmodifiableList, jdkUnmodifiableList.size());
            }
        }
    }

    private static void memoryBenchStringJavaSlangList()
    {
        javaslang.collection.List<String> javaSlangList = javaslang.collection.List.empty();
        MemoryTestUtils.printMemoryUtilization("String", javaSlangList, javaSlangList.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                javaSlangList = javaslang.collection.List.ofAll(MemoryTestUtils.getStringList(i));
                MemoryTestUtils.printMemoryUtilization("String", javaSlangList, javaSlangList.size());
            }
        }
    }

    private static void memoryBenchStringGuavaImmutableList()
    {
        com.google.common.collect.ImmutableList<String> guavaImmutableList = com.google.common.collect.ImmutableList.<String>builder().build();
        MemoryTestUtils.printMemoryUtilization("String", guavaImmutableList, guavaImmutableList.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                guavaImmutableList = com.google.common.collect.ImmutableList.<String>builder().addAll(MemoryTestUtils.getStringList(i)).build();
                MemoryTestUtils.printMemoryUtilization("String", guavaImmutableList, guavaImmutableList.size());
            }
        }
    }

    private static void memoryBenchStringEcImmutableList()
    {
        ImmutableList<String> ecImmutableList = Lists.immutable.empty();
        MemoryTestUtils.printMemoryUtilization("String", ecImmutableList, ecImmutableList.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecImmutableList = Lists.immutable.withAll(MemoryTestUtils.getStringList(i));
                MemoryTestUtils.printMemoryUtilization("String", ecImmutableList, ecImmutableList.size());
            }
        }
    }

    private static void memoryBenchStringEcUnmodifiableList()
    {
        MutableList<String> ecUnmodifiableList = Lists.mutable.<String>empty().asUnmodifiable();
        MemoryTestUtils.printMemoryUtilization("String", ecUnmodifiableList, ecUnmodifiableList.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                ecUnmodifiableList = Lists.mutable.withAll(MemoryTestUtils.getStringList(i)).asUnmodifiable();
                MemoryTestUtils.printMemoryUtilization("String", ecUnmodifiableList, ecUnmodifiableList.size());
            }
        }
    }

    private static void memoryBenchStringJdkUnmodifiableList()
    {
        List<String> jdkUnmodifiableList = Collections.unmodifiableList(new ArrayList<>());
        MemoryTestUtils.printMemoryUtilization("String", jdkUnmodifiableList, jdkUnmodifiableList.size());

        for (int i = 0; i < 1_000_001; i++)
        {
            if (i % 10_000 == 0)
            {
                List<String> arrayList = new ArrayList<>();
                arrayList.addAll(MemoryTestUtils.getStringList(i));
                jdkUnmodifiableList = Collections.unmodifiableList(arrayList);
                MemoryTestUtils.printMemoryUtilization("String", jdkUnmodifiableList, jdkUnmodifiableList.size());
            }
        }
    }
}
