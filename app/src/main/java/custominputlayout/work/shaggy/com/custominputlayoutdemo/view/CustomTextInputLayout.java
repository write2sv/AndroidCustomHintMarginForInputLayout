package custominputlayout.work.shaggy.com.custominputlayoutdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.common.base.Optional;
import custominputlayout.work.shaggy.com.custominputlayoutdemo.R;

public class CustomTextInputLayout extends TextInputLayout {

    private Optional<Integer> hintBottomMargin = Optional.absent();

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout);
        int userHintBottomMargin = typedArray.getInt(R.styleable.CustomTextInputLayout_hintBottomMargin, Integer.MIN_VALUE);

        if (userHintBottomMargin != Integer.MIN_VALUE) {
            hintBottomMargin = Optional.of(userHintBottomMargin);
        }

        typedArray.recycle();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        do {
            if (! isInstanceOfEditText(child)) {
                break;
            }

            if (! isInstanceOfLinearLayoutParams(params)) {
                break;
            }

            android.widget.LinearLayout.LayoutParams llp = (android.widget.LinearLayout.LayoutParams) params;

            if (hintBottomMargin.isPresent()) {
                llp.topMargin = hintBottomMargin.get();
                break;
            }

            //By Default increase the Margin between Hint and Edit Text by 30%
            int marginDelta = (int)(llp.topMargin * 0.3f);
            llp.topMargin = llp.topMargin + marginDelta;

        } while (false);

    }

    private boolean isInstanceOfEditText(View view) {
        return view instanceof EditText;
    }

    private boolean isInstanceOfLinearLayoutParams( ViewGroup.LayoutParams params) {
        return params instanceof android.widget.LinearLayout.LayoutParams;
    }

}