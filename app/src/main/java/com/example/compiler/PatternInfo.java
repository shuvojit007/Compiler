package com.example.compiler;

import java.util.regex.Pattern;

public class PatternInfo {
    public int dayColor;
    public int nightColor;
    public Pattern pattern;

    public PatternInfo(Pattern pattern, int dayColor, int nightColor) {
        this.pattern = pattern;
        this.dayColor = dayColor;
        this.nightColor = nightColor;
    }

    public int getDayColor() {
        return dayColor;
    }

    public void setDayColor(int dayColor) {
        this.dayColor = dayColor;
    }

    public int getNightColor() {
        return nightColor;
    }

    public void setNightColor(int nightColor) {
        this.nightColor = nightColor;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
