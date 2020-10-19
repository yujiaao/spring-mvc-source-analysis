package debug.test;

import org.junit.Test;

import java.util.function.Consumer;

public class Functional {

    public static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
        consumer.accept(t);
    }

    @Test
    public void lambadaTest_02() {
        useConsumer(System.out::println, "Hello World!");
    }

}
