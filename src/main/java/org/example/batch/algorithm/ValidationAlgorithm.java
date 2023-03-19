package org.example.batch.algorithm;

import java.util.HashMap;

public interface ValidationAlgorithm {

    HashMap<String,Integer> verify(HashMap<String,Integer> source,int delay);
}
