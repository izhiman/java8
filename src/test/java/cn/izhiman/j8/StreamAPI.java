package cn.izhiman.j8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流：用来处理数据
 * 操作步骤：
 * 1.得到流
 * 2.操作流（流水线式的操作）
 * 3.终止流（终止流水线）
 *
 * @author zhiman
 * @date 2019/12/26
 */
public class StreamAPI {
    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 7777, Employee.State.BUSY),
            new Employee("李四", 23, 9999.2, Employee.State.FREE),
            new Employee("王五", 35, 99999.2, Employee.State.VOCATION),
            new Employee("赵六", 55, 999999.2, Employee.State.BUSY),
            new Employee("田七", 27, 19999.2, Employee.State.BUSY)
    );

    // --------------------------------创建流,四种方式----------------------------------------
    @Test
    public void sample1() {
        // 1. Collection.stream()  Collection.stream()
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Stream<String> stream1 = list.stream();
        Stream<String> stream2 = list.stream();
        // 2. Arrays.stream(数组对象)得到流
        String[] strs = new String[10];
        strs[0] = "0";
        strs[1] = "1";
        strs[2] = "2";
        Stream<String> stream3 = Arrays.stream(strs);

        // 3. Stream中的静态of方法获取流
        Stream<String> stream4 = Stream.of("xsc", "ccc");
        Stream<List<String>> stream5 = Stream.of(list, list);
        Stream<String> stream6 = Stream.of(strs);

        stream6.forEach(System.out::println);


        // 4. 创建无限流
        Stream<Integer> stream7 = Stream.iterate(2, x -> x + 2);
        //stream7.forEach(System.out::println);

        Stream<Integer> stream8 = Stream.generate(new Random()::nextInt);
        stream8.limit(10).forEach(System.out::println);
    }


    // --------------------------------操作流,三类----------------------------------------

    /**
     * -> 1. 筛选、切片、去重
     * filter --- 过滤
     * limit --- 取前
     * skip(n) --- 取后，扔掉前面n个，和limit 互补
     * distinct --- 去重
     */
    @Test
    public void sample2() {
        // 操作流
        // 特点：惰性求值
        employees.stream().filter(e -> {
            System.out.println("过滤: " + e.getName());
            return e.getAge() >= 35;
        }).forEach(System.out::println);
        System.out.println("--------------------");
        employees.stream()
                .limit(2)
                .forEach(System.out::println);
        System.out.println("--------------------");
        employees.stream()
                .skip(2)
                .forEach(System.out::println);
        System.out.println("--------------------");
        employees.stream()
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * ->2. 映射
     * map --- 将元素映射为其他值或者提取元素，对每个元素生效
     * flatMap --- 将流平铺到一个流中 如 : s1 = [1,2,3] s2=[4,5,6]
     */
    @Test
    public void sample3() {
        //map
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        System.out.println("--------------------");

        // flatMap 对map在下面场景的改进
        List<String> list = Arrays.asList("aaa", "bbb", "cccc", "ddd");
        Stream<Stream<Character>> stream1 = list.stream()
                .map(StreamAPI::chFilter);
        stream1.forEach(sm -> sm.forEach(System.out::println));
        System.out.println("--------------------");
        Stream<Character> stream2 = list.stream().flatMap(StreamAPI::chFilter);
        stream2.forEach(System.out::println);
    }

    /**
     * -> 3. 排序
     * sorted() --- 自然排序（Comparable）
     * sorted(Comparator) --- 定制排序（Comparator）
     */
    @Test
    public void sample4() {
        List<String> list = Arrays.asList("aaa", "eee", "cccc", "bbb", "ddd");
        // 自然排序
        list.stream().sorted().forEach(System.out::println);
        System.out.println("--------------------");

        // 定制排序
        employees.stream().sorted((e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return (int) Math.round(e1.getSalary() - e2.getSalary());
            } else {
                return e1.getAge() - e2.getAge();
            }
        }).forEach(System.out::println);

    }


    private static Stream<Character> chFilter(String str) {
        List<Character> l = new LinkedList<>();
        for (Character ch : str.toCharArray()) {
            l.add(ch);
        }
        return l.stream();
    }

    // --------------------------------终止流,三类----------------------------------------

    /**
     * -> 1.查找与匹配
     * allMatch --- 检查是否匹配所有元素
     * anyMatch --- 至少一个匹配
     * noneMatch --- 是否没有匹配所有元素(一个都没匹配到？)
     * findFirst --- 返回第一个元素
     * findAny --- 返回任意一个
     * count --- 返回流中元素个数
     * max --- 返回流中最大值
     * min --- 返回流中最小值
     */
    @Test
    public void sample5() {
        // allMatch----检查是否匹配所有元素
        boolean b1 = employees.stream().allMatch(employee -> employee.getState().equals(Employee.State.BUSY));
        System.out.println(b1);
        System.out.println("--------------------");

        // anyMatch----至少一个匹配
        boolean b2 = employees.stream().anyMatch(employee -> employee.getState().equals(Employee.State.BUSY));
        System.out.println(b2);
        System.out.println("--------------------");

        // noneMatch---是否没有匹配所有元素 (一个都没匹配到？) true:false
        boolean b3 = employees.stream().noneMatch(employee -> employee.getState().equals(Employee.State.BUSY));
        System.out.println(b3);
        System.out.println("--------------------");

        Optional<Employee> first = employees.stream().
                sorted((e1, e2) -> (
                        (int) (e1.getSalary() - e2.getSalary())
                )).findFirst();
        System.out.println(first.get());
        System.out.println("--------------------");
        //  findAny---返回任意一个
        Optional<Employee> any = employees.stream().filter(employee -> employee.getState().equals(Employee.State.FREE)).findAny();
        System.out.println(any.get());
        System.out.println("--------------------");

        //count---返回流中元素个数
        System.out.println(employees.stream().count());
        System.out.println("--------------------");

        // max---返回流中最大值
        Optional<Employee> maxEmployee = employees.stream().max((e1, e2) -> (
                (int) (e1.getSalary() - e2.getSalary())));
        System.out.println(maxEmployee.get());
        System.out.println("--------------------");

        // min---返回流中最小值
        Optional<Double> min = employees.stream().map(Employee::getSalary).min((s1, s2) -> (int) (s1 - s2));
        System.out.println(min.get());
        System.out.println("--------------------");
    }

    /**
     * -> 2. 归约
     * reduce--- 将流中元素反复结合起来，得到一个同类型的新值
     */
    @Test
    public void sample6() {
        // reduce(T identity, BinaryOperator<T> accumulator);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer reduce = list.stream().reduce(0, Integer::sum);

        /**
         * 注意第三个参数用于并行流，多个子结果如何合并成最终结果
         * @see <a herf='https://www.cnblogs.com/noteless/p/9511407.html'></a>
         *
         */
        Integer reduce3 = list.parallelStream().reduce(0, Integer::sum, Integer::sum);
        Integer reduce4 = list.parallelStream().reduce(0, Integer::sum, (x, y) -> x - y);
        System.out.println(reduce3 + " ;; " + reduce4);
        System.out.println("--------------------");

        // reduce(BinaryOperator<T> accumulator)
        Optional<Double> reduce1 = employees.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(reduce1.get());
        System.out.println("--------------------");
    }

    /**
     * 收集
     * <p>
     * -> 3.1 collect --- 将流转换为其他类型，多为Collection
     */
    @Test
    public void sample7() {
        List<String> names1 = employees.stream().map(Employee::getName).collect(Collectors.toList());
        names1.forEach(System.out::println);
        System.out.println("--------------------");

        // 收集到指定容器
        HashSet<String> names2 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        names2.forEach(System.out::println);
        System.out.println("--------------------");
    }

    /**
     * -> 3.2 collect --- 数据处理
     */
    @Test
    public void sample8() {
        // 数目，和count功能相同
        Long count = employees.stream().collect(Collectors.counting());

        // 求和
        Double avg = employees.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);
        System.out.println("--------------------");

        //最大值 和max()功能相同
        Optional<Employee> max = employees.stream().collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());
        System.out.println("--------------------");

        //最小值 和min()功能相同
        Optional<Double> min = employees.stream().map(Employee::getSalary).collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());
        System.out.println("--------------------");
    }

    /**
     * -> 3.3 collect --- 分组和分区（partition）
     */
    @Test
    public void sample9() {
        // 单级分组
        Map<Employee.State, List<Employee>> groups = employees
                .stream().collect(Collectors.groupingBy(Employee::getState));
        System.out.println(groups);
        System.out.println("--------------------");

        // 多级分组
        Map<Employee.State, Map<String, List<Employee>>> groups2 = employees
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getState,
                        Collectors.groupingBy(e -> {
                            if (e.getAge() < 35) {
                                return "青少年";
                            } else if (e.getAge() < 60) {
                                return "壮年";
                            } else return "老年";
                        })));
        System.out.println(groups2);
        System.out.println("--------------------");

        Map<Boolean, List<Employee>> partition = employees.
                stream().
                collect(Collectors.partitioningBy(e -> e.getSalary() > 10000));
        System.out.println(partition);
        System.out.println("--------------------");
    }


    /**
     * -> 3.4 collect --- 其他(joining、summary）
     */
    @Test
    public void sample10() {
        // summary
        DoubleSummaryStatistics dss = employees.
                stream().
                collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());
        System.out.println(dss.getMin());
        System.out.println(dss.getAverage());
        System.out.println(dss.getSum());
        System.out.println(dss.getCount());
        System.out.println("--------------------");

        //joining
        String joining = employees.stream().map(Employee::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(joining);
        System.out.println("--------------------");
    }
}


