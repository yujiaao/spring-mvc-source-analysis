package com;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * CompletableFuture类实现了CompletionStage和Future接口。Future是Java 5添加的类，用来描述一个异步计算的结果，但是获取一个结果时方法较少,要么通过轮询isDone，确认完成后，调用get()获取值，要么调用get()设置一个超时时间。但是这个get()方法会阻塞住调用线程，这种阻塞的方式显然和我们的异步编程的初衷相违背。
 * 为了解决这个问题，JDK吸收了guava的设计思想，加入了Future的诸多扩展功能形成了CompletableFuture。
 *
 * CompletionStage是一个接口，从命名上看得知是一个完成的阶段，它里面的方法也标明是在某个运行阶段得到了结果之后要做的事情。
 *
 * 作者：数齐
 * 链接：https://www.jianshu.com/p/6f3ee90ab7d3
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class CompletableFutureTest {

    /**
     * 首先说明一下已Async结尾的方法都是可以异步执行的，如果指定了线程池，会在指定的线程池中执行，
     * 如果没有指定，默认会在ForkJoinPool.commonPool()中执行，下文中将会有好多类似的，都不详细解释了。
     * 关键的入参只有一个Function，它是函数式接口，所以使用Lambda表示起来会更加优雅。
     * 它的入参是上一个阶段计算后的结果，返回值是经过转化后结果。
     */
    @Test
    public void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " world").join();
        System.out.println(result);
    }

    /**
     * 首先说明一下已Async结尾的方法都是可以异步执行的，如果指定了线程池，会在指定的线程池中执行，如果没有指定，默认会在ForkJoinPool.commonPool()中执行，下文中将会有好多类似的，都不详细解释了。关键的入参只有一个Function，它是函数式接口，所以使用Lambda表示起来会更加优雅。它的入参是上一个阶段计算后的结果，返回值是经过转化后结果。
     *
     */
    @Test
    public void thenAccept(){
        CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s+" world"));
    }

    /**
     * 对上一步的计算结果不关心，执行下一个操作。
     * @throws InterruptedException
     */
    @Test
    public void thenRun() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
        //while (true){}
        Thread.sleep(2000);
    }


    /**
     * 4.结合两个CompletionStage的结果，进行转化后返回
     */
    @Test
    public void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }


    /**
     * 它需要原来的处理返回值，并且other代表的CompletionStage也要返回值之后，利用这两个返回值，进行转换后返回指定类型的值。
     * 结合两个CompletionStage的结果，进行消耗
     *
     * @throws InterruptedException
     */
    @Test
    public void thenAcceptBoth() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));
      //  while (true){}
        Thread.sleep(6000);
    }


    /**
     * 在两个CompletionStage都运行完执行。
     * 不关心这两个CompletionStage的结果，只关心这两个CompletionStage执行完毕，之后在进行操作（Runnable）。
     * @throws InterruptedException
     */
    @Test
    public void runAfterBoth() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        //while (true){}
        Thread.sleep(6000);
    }


    /**
     * 两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的转化操作。
     * 我们现实开发场景中，总会碰到有两种渠道完成同一个事情，所以就可以调用这个方法，找一个最快的结果进行处理。
     *
     */
    @Test
    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), s -> s).join();
        System.out.println(result);
    }


    @Test
    public void acceptEither() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), System.out::println);
        //while (true){}

        Thread.sleep(3000); // 不用等慢的

    }


    /**
     * 两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）。
     */
    @Test
    public void runAfterEither() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        Thread.sleep(3000); // 不用等慢的
    }


    /**
     * 当运行时出现了异常，可以通过exceptionally进行补偿。
     */
    @Test
    public void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }


    /**
     * 运行完成时，对结果的记录。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。
     * 这里为什么要说成记录，因为这几个方法都会返回CompletableFuture，
     * 当Action执行完毕后它的结果返回原始的CompletableFuture的计算结果或者返回异常。所以不会对结果产生任何的作用。
     */
    @Test
    public void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println(s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }


    /**
     * 运行完成时，对结果的处理。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。
     */
    @Test
    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }


    @Test
    public void handle1() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }


    @Test
    public void testCompletableFuture(){
        CompletableFuture<List<String>> ids = ifhIds();

        CompletableFuture<List<String>> result = ids.thenComposeAsync(l -> {
            Stream<CompletableFuture<String>> zip =
                    l.stream().map(i -> {
                        CompletableFuture<String> nameTask = ifhName(i);
                        CompletableFuture<Integer> statTask = ifhStat(i);

                        return nameTask.thenCombineAsync(statTask, (name, stat) -> "Name " + name + " has stats " + stat);
                    });
            List<CompletableFuture<String>> combinationList = zip.collect(Collectors.toList());
            CompletableFuture<String>[] combinationArray = combinationList.toArray(new CompletableFuture[combinationList.size()]);

            CompletableFuture<Void> allDone = CompletableFuture.allOf(combinationArray);
            return allDone.thenApply(v -> combinationList.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList()));
        });

        List<String> results = result.join();
        assertTrue(results.containsAll(
                Arrays.asList(
                        "Name Joe has stats 103",
                        "Name Bart has stats 104",
                        "Name Henry has stats 105",
                        "Name Nicole has stats 106",
                        "Name ABSLAJNFOAJNFOANFANSF has stats 121")));
    }

    private CompletableFuture<Integer> ifhStat(final String i) {
        final int[] all = {103,104,105,106,121};
        return CompletableFuture.supplyAsync(() -> all[Integer.parseInt(i)]);
    }

    private CompletableFuture<String> ifhName(String i) {
        final String[] all={"Joe","Bart", "Henry", "Nicole", "ABSLAJNFOAJNFOANFANSF"};
        return CompletableFuture.supplyAsync(() -> all[Integer.parseInt(i)]);
    }

    private CompletableFuture<List<String>> ifhIds() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList("0","1","2","3","4"));
    }



}
