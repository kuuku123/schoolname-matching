package org.example.batch.repository;

import java.util.concurrent.ConcurrentHashMap;

public class ValidationResultRepository implements ResultRepository{

    private ConcurrentHashMap<String,Integer> result = new ConcurrentHashMap<>();
    @Override
    public ConcurrentHashMap<String, Integer> getRepo() {
        return result;
    }
}
