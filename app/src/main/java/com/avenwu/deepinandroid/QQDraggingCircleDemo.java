package com.avenwu.deepinandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.avenwu.deepinandroid.widget.PolygonWithQuadraticBezirView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chaobin on 12/25/14.
 */
public class QQDraggingCircleDemo extends ActionBarActivity {
    @InjectView(R.id.shape2)
    PolygonWithQuadraticBezirView mShape2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qq_dragging_circle_layout);
        ButterKnife.inject(this);
        mShape2.setFilled(true);
    }
}
