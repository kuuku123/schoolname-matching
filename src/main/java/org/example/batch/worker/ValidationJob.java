package org.example.batch.worker;

import org.example.batch.PatternDto;
import org.example.batch.algorithm.ValidationAlgorithm;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ValidationJob implements Runnable{

    private ValidationAlgorithm validationAlgorithm;
    private ResultRepository resultRepository;
    private HashMap<String,Integer> source;
    private int delay;

    public ValidationJob(ValidationAlgorithm validationAlgorithm, ResultRepository resultRepository, HashMap<String, Integer> source, int delay) {
        this.validationAlgorithm = validationAlgorithm;
        this.resultRepository = resultRepository;
        this.source = source;
        this.delay = delay;
    }

    @Override
    public void run() {
        HashMap<String, Integer> verified = validationAlgorithm.verify(source, delay);
        ConcurrentHashMap<String, Integer> verifiedResult = resultRepository.getRepo();
        for (String pattern : verified.keySet()) {
            Integer value = verified.getOrDefault(pattern,0);
            verifiedResult.put(pattern,value);
        }
    }
}
