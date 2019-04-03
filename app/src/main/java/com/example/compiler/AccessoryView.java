package com.example.compiler;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class AccessoryView extends LinearLayout {
    public IAccessoryView iAccessoryView;
    private TypedValue outValue;

    public interface IAccessoryView {
        void onButtonAccessoryViewClicked(String str);
    }

    public static final class PixelDipConverter {
        private PixelDipConverter() {
        }

        public static float convertDpToPixel(float f, Context context) {
            return (f * ((float) context.getResources().getDisplayMetrics().densityDpi)) / 160.0f;
        }

        public static float convertPixelsToDp(float f, Context context) {
            return f / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
        }
    }

    public AccessoryView(Context context) {
        super(context);
        init();
    }

    public AccessoryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AccessoryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.OVER_SCROLL_ALWAYS);
        createView();
    }

    public void setInterface(IAccessoryView iAccessoryView) {
        this.iAccessoryView = iAccessoryView;
    }

    public void createView() {
        removeAllViews();
        this.outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.selectableItemBackgroundBorderless, this.outValue, true);
        String []str = new String[29];
        int i = 0;
        str[0] = "TAB";
        str[1] = "{";
        str[2] = "}";
        str[3] = "(";
        str[4] = ")";
        str[5] = "\"";
        str[6] = ".";
        str[7] = ";";
        str[8] = "/";
        str[9] = "<";
        str[10] = ">";
        str[11] = "!";
        str[12] = "=";
        str[13] = "[";
        str[14] = "]";
        str[15] = "&";
        str[16] = "|";
        str[17] = "#";
        str[18] = "*";
        str[19] = "+";
        str[20] = "-";
        str[21] = ":";
        str[22] = "%";
        str[23] = ",";
        str[24] = "-";
        str[25] = "@";
        str[26] = "?";
        str[27] = "^";
        str[28] = "'";
        int length = str.length;
        while (i < length) {
            addAButton(str[i]);
            i++;
        }
        updateTextColors();
    }

    private void addAButton(String str) {
        int convertDpToPixel = (int) PixelDipConverter.convertDpToPixel(50.0f, getContext());
        View button = new Button(getContext());


        button.setLayoutParams(new LayoutParams(convertDpToPixel, convertDpToPixel));
        ((Button) button).setGravity(17);

        ((Button) button).setText(str);
        ((Button) button).setTextSize(15.0f);
        ((Button) button).setAllCaps(true);
        ((Button) button).setTextColor(ContextCompat.getColor(getContext(), R.color.colorItemText));

        button.setClickable(true);



        ((Button) button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iAccessoryView.onButtonAccessoryViewClicked(((Button) v).getText().toString());
            }
        });
        button.setBackgroundResource(this.outValue.resourceId);
        addView(button);
    }

    public void updateTextColors() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorWhite));
            ((Button) getChildAt(i)).setTextColor(getResources().getColor(R.color.colorItemText));
        }
    }
}
