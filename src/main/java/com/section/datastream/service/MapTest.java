package com.section.datastream.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MapTest {
    public static void main(String[] args) throws InterruptedException {
        //test1();
        test2();
        test3();

    }

    private static void test2() throws InterruptedException {
        ConcurrentHashMap<Integer, Double> map = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        IntStream.range(1, 1000)
                .boxed()
                .forEach(i -> executorService.submit( () -> {
                    double amt = map.computeIfAbsent(1, k -> 0d);
                    map.put(1, amt + 1);
                }));
        executorService.shutdown();
        Thread.sleep(3000);
        System.out.println("Test2: " + map);
    }

    private static void test3() throws InterruptedException {
        ConcurrentHashMap<Integer, Double> map = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        IntStream.range(1, 1001)
                .boxed()
                .forEach(i -> executorService.submit( () -> {
                    double amt = map.computeIfAbsent(1, k -> 0d);
                    map.compute(1, (k, v) -> v + 1);
                }));
        executorService.shutdown();
        Thread.sleep(3000);
        System.out.println("Test3: " + map);
    }

    private static void test1() {
        Map<String, String> map = new HashMap<>();
        String v1 = map.computeIfAbsent("ab", k -> "abcd");
        String v2 = map.computeIfAbsent("ab", k -> "dcs");

        System.out.println(v1 + " " + v2);

        String v3 = map.compute("1", (k, v) -> v + "2");
        System.out.println(v3);
        System.out.println(map);

        String v4 = map.compute("1", (k, v) -> v + "2");
        System.out.println(v4);
        System.out.println(map);

        Map<Integer, Integer> map2 = new HashMap<>();
        map2.computeIfAbsent(1, k -> 0);
        int i1 = map2.compute(1, (k, v) -> v + 1);
        System.out.println(i1);
        int i2 = map2.compute(1, (k, v) -> v + 1);
        System.out.println(i2);

        System.out.println(map2);
    }
}
