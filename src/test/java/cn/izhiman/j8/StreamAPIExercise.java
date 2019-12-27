package cn.izhiman.j8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author zhiman
 * @date 2019/12/27
 */
public class StreamAPIExercise {
    @Test
    public void exer1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.stream().map(l -> l * l).forEach(System.out::println);
        System.out.println("-------------------------------------");

        System.out.println(map(list.stream(), x -> x * x));
        System.out.println("-------------------------------------");

        List<Integer> mapRet = list.stream().reduce(new ArrayList<Integer>(), (acc, x) -> {
            List<Integer> newAcc = new ArrayList<>(acc);
            newAcc.add(x * x);
            return newAcc;
        }, (List<Integer> left, List<Integer> right) -> {
            List<Integer> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
        System.out.println(mapRet);
        System.out.println("-------------------------------------");
    }


    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {

        return stream.reduce(new ArrayList<O>(), (acc, x) -> {
            // We are copying data from acc to new list instance. It is very inefficient,
            // but contract of Stream.reduce method requires that accumulator function does
            // not mutate its arguments.
            // Stream.collect method could be used to implement more efficient mutable reduction,
            // but this exercise asks to use reduce method.
            List<O> newAcc = new ArrayList<>(acc);
            newAcc.add(mapper.apply(x));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            // We are copying left to new list to avoid mutating it.
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }
}
