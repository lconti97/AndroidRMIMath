package com.respectmyintelligence.rmimath;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionFragment extends Fragment {
	public static final int LEFT_ANSWER_CLICKED = 0;
	public static final int CENTER_ANSWER_CLICKED = 1;
	public static final int RIGHT_ANSWER_CLICKED = 2;
	
	
	private Question mQuestion;
	private TextView mQuestionTextView;
	private TextView mOperationTopTextView;
	private TextView mOperationBottomTextView;
	private Button mLeftAnswerButton;
	private Button mCenterAnswerButton;
	private Button mRightAnswerButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_question, parent, false);
		
		mQuestionTextView = (TextView)v.findViewById(R.id.question_text_view);
		mOperationTopTextView = (TextView)v.findViewById(R.id.operation_top_text_view);
		mOperationBottomTextView = (TextView)v.findViewById(R.id.operation_bottom_text_view);
		
		mLeftAnswerButton = (Button)v.findViewById(R.id.left_answer_button);
		mLeftAnswerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				checkAnswer(LEFT_ANSWER_CLICKED);
			}
		});
		
		mCenterAnswerButton = (Button)v.findViewById(R.id.center_answer_button);
		mCenterAnswerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(CENTER_ANSWER_CLICKED);
			}
		});
		
		mRightAnswerButton = (Button)v.findViewById(R.id.right_answer_button);
		mRightAnswerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(RIGHT_ANSWER_CLICKED);
			}
		});
		
		nextQuestion();
		
		return v;
	}
	
	private void nextQuestion()
	{
		mQuestion = new Question();
		
		mQuestionTextView.setText(
				mQuestion.getQuestionNumber() + "");
		mOperationTopTextView.setText(
				mQuestion.getTopOperationText());
		mOperationBottomTextView.setText(
				mQuestion.getBottomOperationText());
		mLeftAnswerButton.setText(
				mQuestion.getLeftAnswer() + "");
		mCenterAnswerButton.setText(
				mQuestion.getCenterAnswer() + "");
		mRightAnswerButton.setText(
				mQuestion.getRightAnswer() + "");
	}
	
	private void checkAnswer(int answerClicked)
	{
		if (answerClicked == mQuestion.getCorrectAnswerLocation())
		{
			Toast.makeText(getActivity(), R.string.correct, Toast.LENGTH_SHORT)
				.show();
			nextQuestion();
		}
		else 
		{
			Toast.makeText(getActivity(), R.string.incorrect, Toast.LENGTH_SHORT)
				.show();
		}
	}
}