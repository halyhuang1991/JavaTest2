package com.test.base;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.*;

public class j8feature {
    public static void run() {
        List<String> ls = Arrays.asList("a", "b", "d");
        // ls.forEach(e -> System.out.println(e));
        ls.sort((e1, e2) -> e1.compareTo(e2));
        ls.forEach((String e) -> {
            System.out.println(StringUtils.repeat("*", 10));
            System.out.println(e);
            System.out.println(e.hashCode());
        });
        System.out.println(StringUtils.repeat("*", 10));
        long sum = ls.stream().filter((String e) -> {
            return e.hashCode() > 97;
        }).mapToInt(x -> x.hashCode() * 2).sum();
        System.out.println(sum);
        List<Integer> ls1 = ls.stream().map(x -> x.hashCode()).collect(Collectors.toList());
        ls1.forEach(x -> System.out.print(x));

    }
}