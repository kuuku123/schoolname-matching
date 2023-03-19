package org.example.batch.worker;

import java.util.concurrent.ConcurrentHashMap;

public class ValidationResultRepository implements ResultRepository{

    private ConcurrentHashMap<String,Integer> result = new ConcurrentHashMap<>();
    @Override
    public ConcurrentHashMap<String, Integer> getRepo() {
        return result;
    }
}
