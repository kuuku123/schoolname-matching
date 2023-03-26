package org.example.batch;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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

            ArrayList<String> keyList = new ArrayList<>(verifiedResult.keySet());
            keyList.sort((s1 ,s2) -> s1.compareTo(s2));
            for (String pattern : keyList) {
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
