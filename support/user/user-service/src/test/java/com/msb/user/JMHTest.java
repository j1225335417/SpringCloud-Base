//package com.xinghuo.user;
//
//
//import com.xinghuo.cosid.generator.IdGenerator;
//import com.xinghuo.cosid.generator.segment.BusinessCosIdSegmentChain;
//import com.xinghuo.user.core.UserApplication;
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicLong;
//
//@BenchmarkMode(Mode.Throughput)
//@OutputTimeUnit(TimeUnit.SECONDS)
//@State(Scope.Benchmark)
//public class JMHTest {
//
//    public static void main(String[] args) throws RunnerException {
//        Options options = new OptionsBuilder()
//                .include(JMHTest.class.getSimpleName())
//                .forks(1)
//                .build();
//
//        new Runner(options).run();
//    }
//
//    private ConfigurableApplicationContext springContext;
//
//    private BusinessCosIdSegmentChain businessCosIdSegmentChain;
//
//    @Setup
//    public void setUp() {
//        springContext = SpringApplication.run(UserApplication.class);
//        IdGenerator idGenerator = springContext.getBean(IdGenerator.class);
//        businessCosIdSegmentChain = idGenerator.getBusinessCosIdSegmentChain("test");
//    }
//
//    @TearDown
//    public void tearDown() {
//        springContext.close();
//    }
//
//    @Benchmark
//    public void testStringBuffer() {
//        try {
//            businessCosIdSegmentChain.getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
