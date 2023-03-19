package org.example.batch;

import org.example.batch.algorithm.ValidationAlgorithm;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Writer {

    private final String filePath;

    public Writer(String filePath) {
        this.filePath = filePath;
    }

    public void write(ConcurrentHashMap<String,Integer> verifiedResult) {

        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8");
            BufferedWriter bw = new BufferedWriter(writer);

            for (String pattern : verifiedResult.keySet()) {
                Integer count = verifiedResult.get(pattern);
                bw.write(pattern + " "+ count);
                bw.newLine();
            }

            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}