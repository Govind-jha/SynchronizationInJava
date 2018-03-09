package com.columbus.finteck.App;

import com.columbus.finteck.service.TransactionServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String... args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> transactions = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            transactions.add(executor.submit(() -> TransactionServiceImpl.getServiceConnection.DebitFromAccount(101, 10)));
        }

        transactions.forEach((future) -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
