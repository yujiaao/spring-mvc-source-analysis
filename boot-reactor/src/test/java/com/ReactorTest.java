package com;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ReactorTest {


    @Test
    public void testReactor() {

        Flux<String> ids = ifhIds();

        Flux<String> combinations =
                ids.flatMap(id -> {
                    Mono<String> nameTask = ifhName(id);
                    Mono<Integer> statTask = ifhStat(id);

                    return nameTask.zipWith(statTask,
                            (name, stat) -> "Name " + name + " has stats " + stat);
                });

        Mono<List<String>> result = combinations.collectList();

        List<String> results = result.block();
        assertTrue(results.containsAll(
                Arrays.asList(
                        "Name Joe has stats 103",
                        "Name Bart has stats 104",
                        "Name Henry has stats 105",
                        "Name Nicole has stats 106",
                        "Name ABSLAJNFOAJNFOANFANSF has stats 121")));

    }

    private Mono<Integer> ifhStat(final String i) {
        final int[] all = {103, 104, 105, 106, 121};
        return Mono.just(all[Integer.parseInt(i)]);
    }

    private Mono<String> ifhName(String i) {
        final String[] all = {"Joe", "Bart", "Henry", "Nicole", "ABSLAJNFOAJNFOANFANSF"};
        return Mono.just(all[Integer.parseInt(i)]);
    }

    private Flux<String> ifhIds() {
        return Flux.just("0", "1", "2", "3", "4");
    }


    @Test
    public void testFluxRange() {

        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error: " + error));
    }

    @Test
    public void testFluxSubScribe() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"));
    }

    @Test
    public void testFluxSubScriber() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(10));  //想从源头获取10个元素，但只有4个就结束了。
    }

    @Test
    public void testFluxSubScriber10() {
        Flux<Integer> ints = Flux.range(1, 12);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(10));  //想从源头获取10个元素，第11个被忽略。Done不会被执行

        ints.subscribe(i -> System.out.println(i)); //显示全部。
    }

    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }

    @Test
    public void testViaStepVerifier() {
        StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error")
                .verify();

    }


    private String getStringSync() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, Reactor!";
    }

    @Test
    public void testSyncToAsync() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(() -> getStringSync())    // 1
                .subscribeOn(Schedulers.elastic())  // 2
                .subscribe(System.out::println, null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }
}
