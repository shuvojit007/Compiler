package com.example.compiler;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

abstract class TouchableSpan extends ClickableSpan {
    private boolean isPressed;
    private boolean isUnderLineEnabled;
    private int normalTextColor;
    private int pressedTextColor;

    TouchableSpan(int i, int i2, boolean z) {
        this.normalTextColor = i;
        this.pressedTextColor = i2;
        this.isUnderLineEnabled = z;
    }

    void setPressed(boolean z) {
        this.isPressed = z;
    }

    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setColor(this.isPressed ? this.pressedTextColor : this.normalTextColor);
        textPaint.bgColor = 0;
        textPaint.setUnderlineText(this.isUnderLineEnabled);
    }
}
