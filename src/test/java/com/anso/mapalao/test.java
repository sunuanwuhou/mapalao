package com.anso.mapalao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author qiumeng
 * @Package com.anso.mapalao
 * @Description:
 * @date 2018 2018/4/26 21:36
 */
public class test {

    public static void main(String args[]) {
        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);
        IntStream intStream = integers.stream().mapToInt((x) -> x);

    }


}
