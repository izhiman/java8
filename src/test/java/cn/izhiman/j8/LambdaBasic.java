package cn.izhiman.j8;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 新操作符  ： ->
 * <p>
 * 本质上是对接口的实现
 * 箭头操作符左侧是接口中抽象方法的参数
 * 箭头操作符右侧是接口中抽象方法的实现
 * <p>
 * - 1. lambda参数类型可写 也可不写，不写时依靠类型推断判断参数类型
 * - 2. lambda需要函数式接口（接口中只有一个抽象方法）支持
 *
 * @author zhiman
 * @date 2019/12/26
 */
public class LambdaBasic {
    /**
     * 1.无参无返回值lambda
     */
    @Test
    public void sample1() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("传统的匿名内部类");
            }
        };
        r1.run();
        // lambda方式
        Runnable r2 = () -> System.out.println("无参无返回值lambda");
        r2.run();
    }

    /**
     * 2.有一个参数无返回值lambda
     */
    @Test
    public void sample2() {
        Consumer<String> consumer = msg -> System.out.println(msg);
        consumer.accept("只有一个参数可省略括号");
    }

    /**
     * 3.有两个参数有返回值lambda
     */
    @Test
    public void sample3() {
        Comparator<Integer> comparator = (o1, o2) -> o1.compareTo(o2);
    }

    @Test
    public void sample4() {
        System.out.println(numOperation(200, num -> num * num));
        System.out.println(numOperation(200, num -> num + num));
    }

    private Integer numOperation(Integer num, Operation<Integer> operation) {
        return operation.operate(num);
    }

    @Test
    public void sample5() {
        System.out.println(StrOperation(200, 300, (num1, num2) -> num1 + "" + num2));
        System.out.println(StrOperation(200, 300, (num1, num2) -> String.valueOf(num1 * num2)));
    }

    private String StrOperation(Integer num1, Integer num2, StrOperation<Integer, String> operation) {
        return operation.get(num1, num2);
    }


}

@FunctionalInterface
interface Operation<T> {
    T operate(T t);
}

interface StrOperation<T, R> {
    R get(T op1, T op2);
}