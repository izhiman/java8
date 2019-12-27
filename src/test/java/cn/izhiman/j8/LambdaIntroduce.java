package cn.izhiman.j8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Value;
import org.junit.Test;

import java.util.*;

/**
 * 01 LambdaIntroduce 介绍为啥要用Lambda？
 *
 * @author zhiman
 * @date 2019/12/26
 * @see <a href="https://www.bilibili.com/video/av35195879?p=2">为什么使用Lambda</a>
 */
public class LambdaIntroduce {
    /**
     * 匿名内部类
     */
    public void sample1() {
        // 匿名内部类
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator1);
    }

    /**
     * lambda简单应用
     */
    public void sample2() {
        // lambda
        Comparator<Integer> comparator2 = (o1, o2) -> o1.compareTo(o2);
        // 和上面等价
        Comparator<Integer> comparator3 = Integer::compareTo;
        TreeSet<Integer> treeSet = new TreeSet<>(comparator2);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 7777),
            new Employee("李四", 23, 9999.2),
            new Employee("王五", 35, 99999.2),
            new Employee("赵六", 55, 999999.2),
            new Employee("田七", 27, 19999.2)
    );

    // ----------------------------------方式一 ： 传统方法----------------------------------
    @Test
    public void sample3() {
        System.out.println(findByAge(employees));
        System.out.println(findBySalary(employees));
    }

    // 根据不同需求写多个过滤器
    public List<Employee> findByAge(List<Employee> employees) {
        List<Employee> ret = new LinkedList<>();
        for (Employee e : employees) {
            if (e.getAge() >= 35) {
                ret.add(e);
            }
        }
        return ret;
    }

    // 根据不同需求写多个过滤器
    public List<Employee> findBySalary(List<Employee> employees) {
        List<Employee> ret = new LinkedList<>();
        for (Employee e : employees) {
            if (e.getSalary() > 10000) {
                ret.add(e);
            }
        }
        return ret;
    }

    // ----------------------------------方式二：策略模式----------------------------------
    @Test
    public void sample4() {
        System.out.println(find(employees, new AgeFilter()));
        System.out.println(find(employees, new SalaryeFilter()));
    }

    // 根据不同需求写多个过滤器
    public List<Employee> find(List<Employee> employees, EmployeeFilter<Employee> filter) {
        List<Employee> ret = new LinkedList<>();
        for (Employee e : employees) {
            if (filter.predict(e)) {
                ret.add(e);
            }
        }
        return ret;
    }

    // ----------------------------------方式三：策略模式优化----------------------------------
    @Test
    public void sample5() {
        // 使用匿名内部类优化，不需要额外创建类
        System.out.println(find(employees, new EmployeeFilter<Employee>() {
            @Override
            public boolean predict(Employee employee) {
                return employee.getSalary() > 10000;
            }
        }));
        System.out.println(find(employees, new EmployeeFilter<Employee>() {
            @Override
            public boolean predict(Employee employee) {
                return employee.getAge() >= 35;
            }
        }));
    }

    // ----------------------------------方式四：lambda----------------------------------
    @Test
    public void sample6() {
        // 使用匿名内部类优化，不需要额外创建类
        System.out.println(find(employees, employee -> employee.getSalary() > 10000));
        System.out.println(find(employees, employee -> employee.getAge() >= 35));
    }

    // ----------------------------------方式5：lambda流优化----------------------------------
    @Test
    public void sample7() {
        employees.stream()
                .filter(employee -> employee.getAge() >= 35)
                .forEach(System.out::println);
        System.out.println("----------------");
        employees.stream()
                .filter(employee -> employee.getSalary() >= 10000)
                .limit(2)
                .forEach(System.out::println);
    }


}


// ----------------------------------其他类和接口----------------------------------

/**
 * model
 */
@Data
@AllArgsConstructor
class Employee {
    private String name;
    private int age;
    private double salary;
    private State state;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public enum State {
        FREE, BUSY, VOCATION
    }
}

interface EmployeeFilter<T> {
    boolean predict(T t);
}

class SalaryeFilter implements EmployeeFilter<Employee> {
    @Override
    public boolean predict(Employee employee) {
        return employee.getSalary() > 10000;
    }
}

class AgeFilter implements EmployeeFilter<Employee> {
    @Override
    public boolean predict(Employee employee) {
        return employee.getAge() >= 35;
    }
}