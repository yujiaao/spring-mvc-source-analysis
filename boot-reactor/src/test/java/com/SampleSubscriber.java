package com;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

	public void hookOnSubscribe(Subscription subscription) {
		System.out.println("Subscribed");
		request(1);
	}

	public void hookOnNext(T value) {
		System.out.println("test = " + value);
		request(1);
	}


	@Test
	public void testSampleSub() {
		SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
		Flux<Integer> ints = Flux.range(1, 12);
		ints.subscribe(i -> System.out.println(i),
				error -> System.err.println("Error " + error),
				() -> {
					System.out.println("Done");
				},
				s -> s.request(10));
		ints.subscribe(ss);
	}


	@Test
	public void testCancle() {
		Flux.range(1, 10)
				.doOnRequest(r -> System.out.println("request of " + r))
				.subscribe(new BaseSubscriber<Integer>() {

					@Override
					public void hookOnSubscribe(Subscription subscription) {
						request(2);
					}

					@Override
					public void hookOnNext(Integer integer) {
						System.out.println("Cancelling after having received " + integer);
						cancel();
					}
				});
	}

	@Test
	public void testGenerate() {
		Flux<String> flux = Flux.generate(
				() -> 0,
				(state, sink) -> {
					sink.next("3 x " + state + " = " + 3 * state);
					if (state == 10) sink.complete();
					return state + 1;
				});

		flux.subscribe(i -> System.out.println(i));
	}

	@Test
	public void atomicLong() {
		Flux<String> flux = Flux.generate(
				AtomicLong::new,
				(state, sink) -> {
					long i = state.getAndIncrement();
					sink.next("3 x " + i + " = " + 3 * i);
					if (i == 10) sink.complete();
					return state;
				});
		flux.subscribe(i -> System.out.println(i));

	}

	@Test
	public void generateAndComsumer() {

		Flux<String> flux = Flux.generate(
				AtomicLong::new,
				(state, sink) -> {
					long i = state.getAndIncrement();
					sink.next("3 x " + i + " = " + 3 * i);
					if (i == 10) sink.complete();
					return state;
				}, (state) -> System.out.println("state: " + state));


		flux.subscribe(i -> System.out.println(i));

	}

	interface MyEventListener<T> {
		void onDataChunk(List<T> chunk);
		void processComplete();
	}

//	@Test
//	public void create(){
//		myEventProcessor = new EventPro
//
//		Flux<String> bridge = Flux.create(sink -> {
//			myEventProcessor.register(
//					new MyEventListener<String>() {
//
//						public void onDataChunk(List<String> chunk) {
//							for(String s : chunk) {
//								sink.next(s);
//							}
//						}
//
//						public void processComplete() {
//							sink.complete();
//						}
//					});
//		});
//	}
}

