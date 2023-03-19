package org.example.batch.worker;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class PatterMatchingResultRepository implements ResultRepository {

    private ConcurrentHashMap<String,Integer> result = new ConcurrentHashMap<>();
    @Override
    public ConcurrentHashMap<String, Integer> getRepo() {
        return result;
    }
}
