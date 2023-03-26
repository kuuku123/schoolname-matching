package org.example.batch.algorithm.validation;

import java.util.HashMap;

public interface ValidationAlgorithm {

    HashMap<String,Integer> verify(HashMap<String,Integer> source, int delay);
}
