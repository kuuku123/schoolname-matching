package org.example.batch.algorithm;

import org.example.batch.PatternDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex implements PatterMatchingAlgorithm{

    @Override
    public PatternDto[] solve(String source) {

        ArrayList<PatternDto> patternDtos = new ArrayList<>();

//        String regexPattern1 = "\\S+(초등학교|중학교|고등학교)";
        String regexPattern1 = "[ㄱ-힣]{2,6}(초등학교|중학교|고등학교)";
//        String regexPattern2 = "\\S+(초|중|고)(?!등학교|학교)";
        String regexPattern2 = "[ㄱ-힣]{2,6}(초|중|고)(?!등학교|학교)";

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

        fillMatchingResult(stringIntegerHashMap, regexPattern1,source);
        fillMatchingResult(stringIntegerHashMap, regexPattern2,source);

        for (String s : stringIntegerHashMap.keySet()) {
            patternDtos.add(new PatternDto(s,stringIntegerHashMap.get(s)));
        }

        return patternDtos.toArray(new PatternDto[0]);
    }

    private void fillMatchingResult(HashMap<String, Integer> stringIntegerHashMap, String regexPattern, String source) {

        Pattern compile = Pattern.compile(regexPattern);
        Matcher matcher = compile.matcher(source);
        while(matcher.find()) {
            String group = matcher.group();
            Integer integer = stringIntegerHashMap.getOrDefault(group,0);
            stringIntegerHashMap.put(group,integer+1);
        }
    }

}
