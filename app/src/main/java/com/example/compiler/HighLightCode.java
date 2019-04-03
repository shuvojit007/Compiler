package com.example.compiler;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.util.regex.Matcher;

public class HighLightCode {

    public  SpannableString setHighLight(String str,String type, Context cn) {
        SpannableString ss = new SpannableString(str);
        for (PatternInfo patternInfo : new HighlighterConstant().getLanguageClass(type)) {
            Matcher matcher = patternInfo.pattern.matcher(ss);
            while (matcher.find()) {

                Log.d("PCOLOR", "setHighLight: " + patternInfo.dayColor);

                int expectedColor;

                if (Build.VERSION.SDK_INT <= 22) {
                    expectedColor = cn.getResources().getColor(patternInfo.nightColor);
                } else {
                    expectedColor = cn.getColor(patternInfo.nightColor);
                }

                ss.setSpan(new ForegroundColorSpan(Color.parseColor(String.format("#%06X", (0xFFFFFF & expectedColor)))), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }


        return ss;
    }
}
