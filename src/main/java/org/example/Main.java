package org.example;

import org.example.batch.Writer;
import org.example.batch.algorithm.Regex;
import org.example.batch.algorithm.SeleniumValidation;
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


//        String sourceFile = "comments.csv";
//        String sourceFile = "testlittle.csv";
        String sourceFile = "test.csv";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream fileStream = classloader.getResourceAsStream(sourceFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));

        Writer writer = new Writer("result.txt");
        Worker patternMatchingWorker = new Worker(Executors.newFixedThreadPool(10));
        Worker validationWorker = new Worker(Executors.newFixedThreadPool(10));

        Regex regex = new Regex();
        SeleniumValidation seleniumValidation = new SeleniumValidation();

        PatterMatchingResultRepository patterMatchingResultRepository = new PatterMatchingResultRepository();
        ValidationResultRepository validationResultRepository = new ValidationResultRepository();

        patternMatching(br, patternMatchingWorker, regex, patterMatchingResultRepository);

        validation(validationWorker, seleniumValidation, patterMatchingResultRepository, validationResultRepository,1000);

        writer.write(validationResultRepository.getRepo());

    }

    private static void validation(Worker validationWorker, SeleniumValidation seleniumValidation, PatterMatchingResultRepository patterMatchingResultRepository, ValidationResultRepository validationResultRepository,int delay) {
        ConcurrentHashMap<String, Integer> patternMatchedResult = patterMatchingResultRepository.getRepo();
        int split = patternMatchedResult.size() / 10;
        if (split < 10) {
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

    private static void patternMatching(BufferedReader br,Worker patternMatchingWorker, Regex regex, PatterMatchingResultRepository patterMatchingResultRepository) {
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
        System.out.println("hi");
    }
}