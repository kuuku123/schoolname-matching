package org.example.batch.dto;

public class PatternDto {

    private String pattern;
    private int count;

    public PatternDto(String pattern, int count) {
        this.pattern = pattern;
        this.count = count;
    }

    public String getPattern() {
        return pattern;
    }

    public int getCount() {
        return count;
    }
}
