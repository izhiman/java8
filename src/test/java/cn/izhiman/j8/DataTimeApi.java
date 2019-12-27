package cn.izhiman.j8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author zhiman
 * @date 2019/12/27
 */
public class DataTimeApi {
    @Test
    public void sample() throws InterruptedException {
        // 代替 System.currentTimeMillis();
        Instant start = Instant.now();
        //Thread.sleep(3000);
        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
        LocalDate localDate1 = LocalDate.of(1994,1,15);
        LocalDate localDate2 = LocalDate.now();

        System.out.println(Period.between(localDate1,localDate2).getDays());

    }
}
