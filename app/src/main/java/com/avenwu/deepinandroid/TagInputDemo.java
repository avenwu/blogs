package com.avenwu.deepinandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.avenwu.deepinandroid.widget.TagInputLayout;

/**
 * Created by chaobin on 1/14/15.
 */
public class TagInputDemo extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new TagInputLayout(this));
        setContentView(R.layout.test_tag_input_layout);

    }

    public void onGetTags(View view) {
        TextView tv = (TextView) findViewById(R.id.tv_tags);
        tv.setText("All tags:\n");
        for (CharSequence tag : ((TagInputLayout) findViewById(R.id.tags)).getTagArray()) {
            tv.append(tag);
            tv.append("  ");
        }
    }
}
