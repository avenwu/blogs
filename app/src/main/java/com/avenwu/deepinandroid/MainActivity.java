package com.avenwu.deepinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void showShortcut(View view) {
		startActivity(new Intent(this, ShortcutDemo.class));
	}

	public void showDrawerFrame(View view) {
		Intent intent = new Intent(this, RefreshWidgetActivity.class);
		intent.putExtra("fragment", DrawerDemoFragment.class);
		startActivity(intent);
	}

	public void showRefreshLayout(View view) {
		Intent intent = new Intent(this, RefreshWidgetActivity.class);
		intent.putExtra("fragment", RefreshDemoFragment.class);
		startActivity(intent);
	}
}
