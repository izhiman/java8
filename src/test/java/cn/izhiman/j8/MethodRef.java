package cn.izhiman.j8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * -> 1. 方法引用 ： 如果lambda体中的功能已经有方法实现了，并且该方法的参数列表及返回值类型和函数接口一致，可以使用lambda方法引用
 * 主要有三种格式
 * 对象::实例方法名
 * 类::静态方法名
 * 类::实例方法名（特殊）
 * <p>
 * -> 2. 构造器引用: 需要构造器的参数列表和函数接口方法一致，并且函数接口的返回值类型和构造器产生的对象类型一致
 * 格式： 构造器名::new
 * <p>
 * ->3. 数组引用
 * 格式： Type[]::new
 *
 * @author zhiman
 * @date 2019/12/26
 */
public class MethodRef {
    /**
     * 对象：：实例方法名
     */
    @Test
    public void sample1() {
        // 这里println方法的参数类型和返回值和 Consumer接口的accept一致
        // 可以用方法引用
        Consumer<String> consumer1 = str -> System.out.println(str);
        // 等价于下面
        PrintStream ps = System.out;
        Consumer<String> consumer2 = ps::println;
        consumer1.accept("xxxxxxxx");
        consumer2.accept("xxxxxxxx");

        // 下面方法等价
        Supplier<String> supplier1 = () -> new Integer(10).toString();
        Supplier<String> supplier2 = new Integer(10)::toString;
        supplier1.get();
        supplier2.get();
    }

    /**
     * 类：：静态方法名
     */
    @Test
    public void sample2() {
        Comparator<Integer> comparator1 = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> comparator2 = Integer::compare;
    }


    /**
     * 类：：实例方法名
     */
    @Test
    public void sample3() {
        //当第一个参数是调用者，后面的参数是被调用者时（可无）且返回类型符合要求，可用类：：实例方法名的格式
        BiPredicate<Integer, Integer> biPredicate1 = (x, y) -> x.equals(y);
        BiPredicate<Integer, Integer> biPredicate2 = Integer::equals;

        Consumer<String> consumer1 = str -> str.length();
        Consumer<String> consumer2 = String::length;

        BiFunction<Integer, Integer, Integer> biFunction1 = (x, y) -> x.compareTo(y);
        BiFunction<Integer, Integer, Integer> biFunction2 = Integer::compareTo;
    }

    /**
     * 构造器引用
     */
    @Test
    public void sample4() {
        // 下两种方式等价
        Supplier<StringBuilder> supplier1 = () -> new StringBuilder();
        Supplier<StringBuilder> supplier2 = StringBuilder::new;

        // 下两种方式等价
        Function<String, StringBuilder> function1 = str -> new StringBuilder(str);
        Function<String, StringBuilder> function2 = StringBuilder::new;

    }
    /**
     * 数组引用
     */
    @Test
    public void sample5() {
        // 下两种方式等价
        Function<Integer, String[]> function1 = len -> new String[len];
        Function<Integer, String[]> function2 = String[]::new;
        System.out.println(function1.apply(10).length);
    }
}
