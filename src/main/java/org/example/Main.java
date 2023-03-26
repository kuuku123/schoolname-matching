package org.example;

import org.example.batch.algorithm.patter_matching.Regex;
import org.example.batch.algorithm.validation.SeleniumValidation;
import org.example.batch.repository.PatterMatchingResultRepository;
import org.example.batch.repository.ResultRepository;
import org.example.batch.repository.ValidationResultRepository;
import org.example.batch.worker.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {


        String sourceFile = "comments.csv";
//        String sourceFile = "testlittle.csv";
//        String sourceFile = "test.csv";
//        String sourceFile = "test2.csv";

        /**
         * 필요한 parameter 설정
         */
        int delay = 1500;
        int threads = 13;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream fileStream = classloader.getResourceAsStream(sourceFile);
        Worker patternMatchingWorker = new Worker(Executors.newFixedThreadPool(threads));
        Worker validationWorker = new Worker(Executors.newFixedThreadPool(threads));

        /**
         * read, write 작업준비
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
        Writer writer = new Writer("result.txt");



        Regex regex = new Regex();
        SeleniumValidation seleniumValidation = new SeleniumValidation();

        ResultRepository patterMatchingResultRepository = new PatterMatchingResultRepository();
        ResultRepository validationResultRepository = new ValidationResultRepository();

        patternMatching(br, patternMatchingWorker, regex, patterMatchingResultRepository);

        validation(validationWorker, seleniumValidation, patterMatchingResultRepository, validationResultRepository,delay,threads);

        writer.write(validationResultRepository.getRepo());

    }

    private static void validation(Worker validationWorker, SeleniumValidation seleniumValidation,
                                   ResultRepository patterMatchingResultRepository, ResultRepository validationResultRepository,
                                   int delay, int threads) {
        ConcurrentHashMap<String, Integer> patternMatchedResult = patterMatchingResultRepository.getRepo();
        int split = patternMatchedResult.size() / threads;
        if (split < threads) {
            split  = patternMatchedResult.size();
        }
        int index = 0;
        HashMap<String,Integer> needValidation = new HashMap<>();
        for (String key : patternMatchedResult.keySet()) {
            index += 1;
            needValidation.put(key,patternMatchedResult.get(key));
            if (index == split) {
                ValidationJob validationJob = new ValidationJob(seleniumValidation, validationResultRepository, needValidation, delay);
                validationWorker.work(validationJob);
                needValidation = new HashMap<>();
                index = 0;
            }
        }

        validationWorker.finish();
    }

    private static void patternMatching(BufferedReader br,Worker patternMatchingWorker, Regex regex, ResultRepository patterMatchingResultRepository) {
        StringBuilder source = new StringBuilder();

        while(true) {
            try {
                String s = br.readLine();
                if (s == null || s.equals("")) {
                    PatternMatchingJob job = new PatternMatchingJob(regex,source.toString(), patterMatchingResultRepository);
                    patternMatchingWorker.work(job);
                    if (s == null) {
                        break;
                    }
                    source = new StringBuilder();
                    continue;
                }

                source.append(s);
                source.append("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        patternMatchingWorker.finish();
    }
}