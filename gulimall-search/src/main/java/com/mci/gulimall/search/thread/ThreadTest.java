package com.mci.gulimall.search.thread;

import com.alibaba.nacos.client.utils.JSONUtils;

import java.util.concurrent.*;

public class ThreadTest {
    // each system should have only 1 or 2 Thread pool
    public static ExecutorService service = Executors.newFixedThreadPool(10);

    public void thread(String[] args) throws ExecutionException, InterruptedException {
        // different ways for Thread

        // Thread extension
//        Thread01 thread01 = new Thread01();
//        thread01.start();

        // Runnable interface
//        Runnable01 runnable01 = new Runnable01();
//        new Thread(runnable01).start();

//        // Callable interface + FutureTask
//        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//        new Thread(futureTask).start();
//
//        // wait for the end of Thread execution and get the returned value
//        Integer integerResult = futureTask.get();

        // Thread pool, the only one should be used in real projects
        service.execute(new Runnable01());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                200,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println("main....ends");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main......starts......");

        // Completable future
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            System.out.println("Current Thread: "Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("Result: " + i);
//        }, executor);

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Current Thread: " + Thread.currentThread().getId());
//            int i = 10 / 0;
//            System.out.println("Result: " + i);
//
//            return i;
//        }, service).whenComplete((result, exception) -> {
//            System.out.println("ASynchronized task terminated...result is: " + result + " exception: " + exception);
//        }).exceptionally(throwable -> {
//            return -1;
//        });

//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Current Thread: " + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("Result: " + i);
//
//            return i;
//        }, service).handle((result, exception) -> {
//            if (result != null) {
//                return result * 2;
//            }
//
//            if (exception != null) {
//                return -1;
//            }
//
//            return 0;
//        });

//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Current Thread: " + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("Result: " + i);
//
//            return i;
//        }, service).thenRunAsync(() -> {
//            System.out.println("Task 2 starts...");
//        }, service);

//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Current Thread: " + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("Result: " + i);
//
//            return i;
//        }, service).thenAcceptAsync(res -> {
//            System.out.println("Task 2 starts...");
//        }, service);

//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("Current Thread: " + Thread.currentThread().getId());
//            int i = 10 / 4;
//            System.out.println("Result: " + i);
//
//            return i;
//        }, service).thenApplyAsync(res -> {
//            System.out.println("Task 2 starts..." + res);
//
//            return "Hello" + res;
//        }, service);

        CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1: " + Thread.currentThread().getId());
            int i = 10 / 4;
            System.out.println("Task 1 Result: " + i);

            return i;
        }, service);

        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2: " + Thread.currentThread().getId());
            int i = 10 / 4;
            System.out.println("Task 2 Result: " + i);
            return "Hello";
        }, service);

//        future01.runAfterBothAsync(future02, () -> {
//            System.out.println("Task 3 starts...");
//        }, service);

//        future01.thenAcceptBothAsync(future02, (f1, f2) -> {
//            System.out.println("Task 3 starts..." + f1 + "-->" + f2);
//        }, service);

//        future01.thenCombineAsync(future02, (f1, f2) -> {
//            return f1 + f2;
//        }, service);

        // runAfterEitherAsync
//        future01.runAfterEitherAsync(future02, () -> {
//            System.out.println("Task 3 starts...");
//        }, service);

        // acceptEitherAsync
//        future01.acceptEitherAsync(future02, (res) -> {
//            System.out.println("Task 3 starts...");
//        }, service);

        // applyToEitherAsync
//        future01.applyToEitherAsync(future02, res -> {
//            System.out.println("Task 3 starts...");
//            return res.toString();
//        }, service);

        System.out.println("main......ends......");
    }

    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println("Current Thread: " + Thread.currentThread().getId());

            int i = 10 / 2;
            System.out.println("Result: " + i);
        }
    }

    public static class Runnable01 implements Runnable {
        @Override
        public void run() {
            System.out.println("Current Thread: " + Thread.currentThread().getId());

            int i = 10 / 2;
            System.out.println("Result: " + i);
        }
    }

    public static class Callable01 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("Current Thread: " + Thread.currentThread().getId());

            int i = 10 / 2;
            System.out.println("Result: " + i);

            return i;
        }
    }
}
