package com.avenwu.deepinandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.avenwu.deepinandroid.widget.TagInputLayout;

/**
 * Created by chaobin on 1/14/15.
 */
public class TagInputDemo extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TagInputLayout(this));
    }
}
