package cn.izhiman.j8;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 内置的四大函数式接口：
 * Consumer<T> :消费型接口
 * - void accept(T t);
 * Supplier<T>: 供给型接口
 * - T get();
 * Function<T, R> :函数式接口
 * - R apply(T t)
 * Predicate<T t>: 断言型接口
 * -boolean test(T t);
 *
 * @author zhiman
 * @date 2019/12/26
 */
public class InnerFunctionInterface {
    // ------------ Consumer<T> :消费型接口,特点："有去无回"---------------------
    @Test
    public void sample1() {
        happy("小马", msg -> System.out.println(msg + "真帅"));
    }

    private void happy(String str, Consumer<String> consumer) {
        consumer.accept(str);
    }

    // ------------ Supplier<T>: 供给型接口,特点："无中生有"---------------------
    @Test
    public void sample2() {
        System.out.println(getNumList(10, () -> new Random().nextInt()));
    }

    private List<Integer> getNumList(Integer n, Supplier<Integer> supplier) {
        List<Integer> ret = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            ret.add(supplier.get());
        }
        return ret;
    }

    // ------------ Predicate<T t>: 断言型接口,特点：有来有回---------------------
    @Test
    public void sample3() {
        System.out.println(handleStr(" Function<T, R> :函数式接口,,特点：有来有回",
                (str) -> str.substring(str.indexOf("：") + 1)));
    }

    private String handleStr(String str, Function<String, String> handler) {
        return handler.apply(str);
    }

    // ------------ Function<T, R> :函数式接口,,特点：返回类型固定，为boolean---------------------
    @Test
    public void sample4() {
        List<String> strs = Arrays.asList("qwqe", "efqfy8hqfh", "q", "xx", "fsaf");
        System.out.println(handleStr(strs, str -> str.length() > 3));
    }

    private List<String> handleStr(List<String> strs, Predicate<String> predicate) {
        return strs.stream()
                .filter(str -> predicate.test(str))
                .collect(Collectors.toList());
    }
}
