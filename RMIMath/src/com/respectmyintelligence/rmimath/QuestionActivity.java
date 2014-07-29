package com.respectmyintelligence.rmimath;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

public class QuestionActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.question_fragment_container);
		
		if (fragment == null)
		{
			fragment = new QuestionFragment();
			fm.beginTransaction()
				.add(R.id.question_fragment_container, fragment)
				.commit();
		}
		
	}
}
