package org.example.batch.worker;

import org.example.batch.dto.PatternDto;
import org.example.batch.algorithm.patter_matching.PatterMatchingAlgorithm;
import org.example.batch.repository.ResultRepository;

import java.util.concurrent.ConcurrentHashMap;


public class PatternMatchingJob implements Runnable{

    private final PatterMatchingAlgorithm algorithm;
    private final String source;
    private final ResultRepository resultRepository;


    public PatternMatchingJob(PatterMatchingAlgorithm algorithm, String source, ResultRepository resultRepository) {
        this.algorithm = algorithm;
        this.source = source;
        this.resultRepository = resultRepository;
    }

    @Override
    public void run() {
        PatternDto[] solve = algorithm.solve(source);
        ConcurrentHashMap<String, Integer> patternMatchedResult = resultRepository.getRepo();
        for (PatternDto patternDto : solve) {
            String key = patternDto.getPattern();
            int value = patternDto.getCount();
            patternMatchedResult.put(key,value);
        }
    }
}
