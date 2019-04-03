package com.example.compiler;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CodeEditText extends AppCompatEditText {

    private static final Pattern PATTERN_ENDIF = Pattern.compile("(#endif)\\b");
    private static final Pattern PATTERN_INSERT_UNIFORM = Pattern.compile("\\b(uniform[a-zA-Z0-9_ \t;\\[\\]\r\n]+[\r\n])\\b", 8);
    private static final Pattern PATTERN_TRAILING_WHITE_SPACE = Pattern.compile("[\\t ]+$", 8);


    SparseArray<String> lineNumbers;
    protected Rect mDrawingRect;
    protected Rect mLineBounds;
    protected int mLinePadding;
    protected Point mMaxSize;
    protected int mPadding;
    protected int mPaddingDP;
    protected Paint mPaintNumbers;
    protected float mScale;

    private int tabWidth;
    private int tabWidthInCharacters;




    private class TabWidthSpan extends ReplacementSpan {
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        }

        private TabWidthSpan() {
        }

        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return CodeEditText.this.tabWidth;
        }
    }
    void convertTabs(Editable editable, int i, int i2) {
        if (this.tabWidth >= 1 && editable.length() >= 1) {
            String obj = editable.toString();
            i2 += i;
            while (true) {
                i = obj.indexOf("\t", i);
                if (i > -1 && i < i2) {
                    int i3 = i + 1;
                    editable.setSpan(new TabWidthSpan(), i, i3, 33);
                    i = i3;
                }
            }
        }
    }



    public void setWrapMode(boolean z) {
        setHorizontallyScrolling(z);
    }

    public void setTabWidth(int i) {
        if (this.tabWidthInCharacters != i) {
            this.tabWidthInCharacters = i;
            this.tabWidth = Math.round(getPaint().measureText("m") * ((float) i));
        }
    }


    private void clearSpans(Editable editable, int i, int i2) {
        ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) editable.getSpans(i, i2, ForegroundColorSpan.class);
        i2 = foregroundColorSpanArr.length;
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                editable.removeSpan(foregroundColorSpanArr[i3]);
                i2 = i3;
            } else {
                return;
            }
        }
    }

    private void clearSpans(Editable editable) {
        ForegroundColorSpan[] foregroundColorSpanArr = (ForegroundColorSpan[]) editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
        int length = foregroundColorSpanArr.length;
        while (true) {
            int i = length - 1;
            if (length > 0) {
                editable.removeSpan(foregroundColorSpanArr[i]);
                length = i;
            } else {
                return;
            }
        }
    }


    public String getCleanText() {
        return PATTERN_TRAILING_WHITE_SPACE.matcher(getText()).replaceAll("");
    }


    public String getEnDIF() {
        return PATTERN_ENDIF.matcher(getText()).replaceAll("");
    }



    private int endIndexOfLastEndIf(Editable editable) {
        Matcher matcher = PATTERN_ENDIF.matcher(editable);
        int i = -1;
        while (matcher.find()) {
            i = matcher.end();
        }
        return i;
    }


    public CodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mPaintNumbers = new Paint();
        this.mPaddingDP = 0;

        this.lineNumbers = new SparseArray();

        this.tabWidthInCharacters = 0;
        this.tabWidth = 0;



        this.mPaintNumbers.setTypeface(Typeface.MONOSPACE);
        this.mPaintNumbers.setTextAlign(Paint.Align.LEFT);
        this.mPaintNumbers.setAntiAlias(true);
        this.mPaintNumbers.setTextSize(getTextSize() * 0.85f);
        this.mDrawingRect = new Rect();
        this.mLineBounds = new Rect();
        this.mScale = context.getResources().getDisplayMetrics().density;
        this.mPadding = (int) (((float) this.mPaddingDP) * this.mScale);
        setTabWidth(2);
        setHorizontallyScrolling(false);
        setBackgroundColor(getResources().getColor(R.color.ch_background));
        setTextColor(getResources().getColor(R.color.ch_plain_text));
    }


    public void setReadOnly(boolean flag) {
        if (flag) {
            setFocusable(false);
            setClickable(false);
            setLongClickable(false);
            return;
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setLongClickable(true);
    }
    private void updateLinePadding(float f) {
        float floor = ((float) ((int) (Math.floor(Math.log10((double) getLineCount())) + 1.0d))) * this.mPaintNumbers.getTextSize();
        int i = this.mPadding;
        int i2 = (int) (f + (floor + ((float) i)));
        if (this.mLinePadding != i2) {
            this.mLinePadding = i2;
            setPadding(this.mLinePadding - i, i, i, i);
        }

    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int i;
        int i2;
        int lineCount = getLineCount();
        updateLinePadding(getTextSize());
        getDrawingRect(mDrawingRect);
        Layout layout = getLayout();
        if (layout != null) {
            lineNumbers.clear();
            lineNumbers.put(0, Integer.toString(1));
            String[] split = getText().toString().replace("\n", "\n ").split("\n");
            i = 0;
            i2 = 1;
            while (i < split.length) {
                String str = split[i];
                i++;
                this.lineNumbers.put(layout.getLineForOffset(i2), Integer.toString(i));
                i2 += str.length();
            }
        }
        int textSize = (int) (((float) (this.mDrawingRect.left + this.mLinePadding)) - getTextSize());
        getLineBounds(0, this.mLineBounds);
        int i3 = this.mLineBounds.bottom;
        i = this.mLineBounds.top;
        i2 = lineCount - 1;
        getLineBounds(i2, this.mLineBounds);
        int bottom = this.mLineBounds.bottom;
        int i5 = this.mLineBounds.top;


        if (lineCount <= 1 || bottom <= i3 || i5 <= i) {
            i3 = 0;
        } else {
            i3 = Math.max(0, ((this.mDrawingRect.top - i3) * i2) / (bottom - i3));
            lineCount = Math.min(lineCount, (((this.mDrawingRect.bottom - i) * i2) / (i5 - i)) + 1);
        }


        while (i3 < lineCount) {
            int lineBounds = getLineBounds(i3, this.mLineBounds);
            Point point = this.mMaxSize;
            if (point != null && point.x < this.mLineBounds.right) {
                this.mMaxSize.x = this.mLineBounds.right;
            }
            if (this.lineNumbers.get(i3) != null) {
                canvas.drawText((String) this.lineNumbers.get(i3), (float) (this.mDrawingRect.left + this.mPadding), (float) lineBounds, this.mPaintNumbers);
                float f = (float) textSize;
                canvas.drawLine(f, (float) this.mDrawingRect.top, f, (float) this.mDrawingRect.bottom, this.mPaintNumbers);
            }
            i3++;
        }
        getLineBounds(i2, this.mLineBounds);
        Point point2 = this.mMaxSize;
        if (point2 != null) {
            point2.y = this.mLineBounds.bottom;
            point2 = this.mMaxSize;
            point2.x = Math.max((point2.x + this.mPadding) - this.mDrawingRect.width(), 0);
            point2 = this.mMaxSize;
            point2.y = Math.max((point2.y + this.mPadding) - this.mDrawingRect.height(), 0);
        }

        super.onDraw(canvas);
    }
}