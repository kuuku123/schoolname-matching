package org.example.batch.worker;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public interface ResultRepository {

    ConcurrentHashMap<String,Integer> getRepo();
}
