package com.avenwu.deepinandroid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.avenwu.deepinandroid.R;

/**
 * Created by chaobin on 1/14/15.
 */
public class TagInputLayout extends FlowLayout implements TextWatcher, View.OnKeyListener, View.OnClickListener {
    private final String TAG = TagInputLayout.class.getSimpleName();
    private EditText mInputView;
    private char[] mKeyChar = new char[]{',', '，'};
    private int[] mKeyCode = new int[]{
            KeyEvent.KEYCODE_ENTER,
            KeyEvent.KEYCODE_COMMA,
            KeyEvent.KEYCODE_NUMPAD_COMMA};
    private int mTagLayout = -1;
    private int mTagMarginLeft;
    private int mTagMarginTop;
    private int mTagMarginRight;
    private int mTagMarginBottom;
    private int mTextSize;

    public TagInputLayout(Context context) {
        this(context, null);
    }

    public TagInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                getContext().getResources().getDisplayMetrics());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagInputLayout);
        mTagLayout = a.getInt(R.styleable.TagInputLayout_tag_layout, -1);
        mTagMarginLeft = a.getInt(R.styleable.TagInputLayout_tag_margin_left, margin);
        mTagMarginTop = a.getInt(R.styleable.TagInputLayout_tag_margin_top, margin);
        mTagMarginRight = a.getInt(R.styleable.TagInputLayout_tag_margin_right, margin);
        mTagMarginBottom = a.getInt(R.styleable.TagInputLayout_tag_margin_bottom, margin);
        mTextSize = a.getInt(android.R.attr.textSize, 14);
        a.recycle();
        mInputView = new EditText(context, attrs);
        mInputView.setLayoutParams(genarateLayoutParams());
        mInputView.setBackgroundColor(Color.TRANSPARENT);
        mInputView.addTextChangedListener(this);
        mInputView.setOnKeyListener(this);
        mInputView.setTextSize(mTextSize);
        //random small width just to make the blinking tips visible
        mInputView.setMinWidth(20);
        mInputView.setSingleLine();
        setOnClickListener(this);
        addView(mInputView);
    }

    private LayoutParams genarateLayoutParams() {
        MarginLayoutParams params = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(mTagMarginLeft, mTagMarginTop, mTagMarginRight, mTagMarginBottom);
        return params;
    }

    /**
     * set the keyCode list which trigger the TAG generation, the default keyCode are
     * {@link android.view.KeyEvent#KEYCODE_ENTER},{@link android.view.KeyEvent#KEYCODE_COMMA}
     *
     * @param keyChar comma on mobile are not always keyCode, null will be ignored
     * @param keyCode keyCode defined in {@link android.view.KeyEvent}, null will be ignored
     */
    public void setActionKeyCode(char[] keyChar, int... keyCode) {
        if (keyChar != null && keyChar.length > 0) {
            mKeyChar = keyChar;
        }
        if (keyCode != null && keyCode.length > 0) {
            mKeyCode = keyCode;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "keyCode=" + keyCode);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isKeyCodeHit(keyCode)) {
                if (!TextUtils.isEmpty(mInputView.getText().toString())) {
                    generateTag();
                }
                return true;
            } else if (KeyEvent.KEYCODE_DEL == keyCode) {
                if (TextUtils.isEmpty(mInputView.getText().toString())) {
                    deleteTag();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKeyCodeHit(int keyCode) {
        if (mKeyCode != null && mKeyCode.length > 0) {
            for (int key : mKeyCode) {
                if (key == keyCode) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isKeyCharHit(char keyChar) {
        if (mKeyChar != null && mKeyChar.length > 0) {
            for (char key : mKeyChar) {
                if (key == keyChar) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged s=" + s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged s=" + s);
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged s=" + s);
        if (s.length() > 0) {
            if (isKeyCharHit(s.charAt(0))) {
                mInputView.setText("");
            } else if (isKeyCharHit(s.charAt(s.length() - 1))) {
                mInputView.setText(s.subSequence(0, s.length() - 1) + "");
                generateTag();
            }
        }
    }

    private int mCheckIndex = -1;

    @Override
    public void onClick(View v) {
        if (v instanceof TagInputLayout) {
            mInputView.requestFocus();
            InputMethodManager m = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            m.showSoftInput(mInputView, InputMethodManager.SHOW_FORCED);
            // clear check status
            if (mCheckIndex != -1) {
                updateCheckStatus(getChildAt(mCheckIndex), false);
                mCheckIndex = -1;
            }
            return;
        }
        final int index = indexOfChild(v);
        // skip unnecessary setting
        if (index != mCheckIndex) {
            mCheckIndex = index;
            updateCheckStatus(v, true);
            for (int i = 0; i < index; i++) {
                updateCheckStatus(getChildAt(i), false);
            }
            //skip input box
            for (int i = index + 1; i < getChildCount() - 1; i++) {
                updateCheckStatus(getChildAt(i), false);
            }
        }
    }

    private void deleteTag() {
        if (getChildCount() > 1) {
            removeViewAt(mCheckIndex == -1 ? indexOfChild(mInputView) - 1 : mCheckIndex);
            mCheckIndex = -1;
            mInputView.requestFocus();
        }
    }

    private void generateTag() {
        String tagString = mInputView.getText().toString();
        mInputView.getText().clear();
        final int targetIndex = indexOfChild(mInputView);
        TextView tag;
        if (mTagLayout != -1) {
            View view = View.inflate(getContext(), mTagLayout, null);
            if (view instanceof TextView) {
                tag = (TextView) view;
            } else {
                throw new IllegalArgumentException("The custom layout for tag label must have TextView as root element");
            }
        } else {
            tag = new TextView(getContext());
            updateCheckStatus(tag, false);
            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                    getContext().getResources().getDisplayMetrics());
            tag.setPadding(padding, padding, padding, padding);
        }
        tag.setText(tagString);
        tag.setTextSize(14);
        tag.setSingleLine();
        tag.setEllipsize(TextUtils.TruncateAt.END);
        tag.setClickable(true);
        tag.setOnClickListener(this);
        addView(tag, targetIndex, genarateLayoutParams());
        mInputView.requestFocus();
    }
// TextView seems do not support checked status for background
//    private Drawable defaultBackground4TagLabel() {
//        final int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getContext().getResources().getDisplayMetrics());
//        StateListDrawable selector = new StateListDrawable();
//        selector.addState(new int[]{android.R.attr.state_pressed}, newRoundRectShape(0xff3f51b5, radius));
//        selector.addState(new int[]{android.R.attr.state_checked}, newRoundRectShape(0xff3f51b5, radius));
//        selector.addState(new int[]{android.R.attr.state_focused}, newRoundRectShape(0xff3f51b5, radius));
//        selector.addState(new int[]{}, newRoundRectShape(0xff4285f4, radius));
//        return selector;
//    }

    private Drawable newRoundRectShape(int color, int radius) {
        ShapeDrawable shape = new ShapeDrawable(new RoundRectShape(new float[]{radius, radius,
                radius, radius, radius, radius, radius, radius}, null, null));
        shape.getPaint().setStyle(Paint.Style.FILL);
        shape.getPaint().setColor(color);
        shape.getPaint().setAntiAlias(true);
        return shape;
    }

    private void updateCheckStatus(View view, boolean checked) {
        if (view == null) return;
        final int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getContext().getResources().getDisplayMetrics());
        view.setBackgroundDrawable(newRoundRectShape(checked ? 0xff3f51b5 : 0xff4285f4, radius));
    }
}
