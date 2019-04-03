package com.example.compiler;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Spinner;

import java.lang.reflect.Field;

public class Spnnier extends android.support.v7.widget.AppCompatSpinner {
    public Spnnier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* renamed from: a */
    private void SpinnerSelect() {
        try {
            Class superclass = getClass().getSuperclass();
            if (superclass != null) {
                Field declaredField = superclass.getDeclaredField("mSelectedPosition");
                declaredField.setAccessible(true);
                declaredField.setInt(this, -1);
            }
        } catch (Throwable e) {
            Log.d("Exception Private", "ex", e);
        }
    }

    public void setSelection(int i) {
        SpinnerSelect();
        super.setSelection(i);
    }
}
