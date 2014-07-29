package com.respectmyintelligence.rmimath;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class Question {
	public static final int ADDITION = 1;
	public static final int SUBTRACTION = 2;
	public static final int MULTIPLICATION = 3;
	public static final int DIVISION = 4;

	public static final int FIRST_STEP_MAX = 200;
	public static final int SECOND_STEP_MAX = 120;

	private static final int MINIMUM = 2;

	private static final String TAG = "RMIMath";

	private Random mRandom;
	private int mQuestionNumber;
	private int mTopOperator;
	private int mBottomOperator;
	private int mTopNumber;
	private int mBottomNumber;
	private int mCurrentValue;
	private String mTopOperationText;
	private String mBottomOperationText;
	private int mLeftAnswer;
	private int mCenterAnswer;
	private int mRightAnswer;
	private int mCorrectAnswerLocation;
	private int[] mWrongAnswers = new int[2];

	public Question()
	{
		mRandom = new Random();
		mQuestionNumber = createQuestionNumber();
		mCurrentValue = mQuestionNumber;
		createOperatorCodes();
		mTopOperationText = createFirstStep();
		Log.i(TAG, "mCurrentValue after step 1 is " + mCurrentValue);
		mBottomOperationText = createSecondStep();
		Log.i(TAG, "mCurrentValue after step 2 is " + mCurrentValue);
		mWrongAnswers = createWrongAnswers();
		Log.i(TAG, "Wrong answers are " + mWrongAnswers[0] 
				+ " and " + mWrongAnswers[1]);
		mCorrectAnswerLocation = createCorrectAnswerLocation();
		Log.i(TAG, "mCorrectAnswerLocation is " + mCorrectAnswerLocation);
		mLeftAnswer = createLeftAnswer();
		mCenterAnswer = createCenterAnswer();
		mRightAnswer = createRightAnswer();
	}

	public int getQuestionNumber() {
		return mQuestionNumber;
	}

	public String getTopOperationText() {
		return mTopOperationText;
	}

	public String getBottomOperationText() {
		return mBottomOperationText;
	}

	public int getLeftAnswer() {
		return mLeftAnswer;
	}

	public int getCenterAnswer() {
		return mCenterAnswer;
	}

	public int getRightAnswer() {
		return mRightAnswer;
	}

	public int getCorrectAnswerLocation() {
		return mCorrectAnswerLocation;
	}

	private int createQuestionNumber()
	{
		return mRandom.nextInt(60) + 1;
	}

	private String operationToString(int operation)
	{
		String s = "";
		switch(operation)
		{
		case 1: s = "+";
		break;
		case 2: s = "-";
		break;
		case 3: s = "*";
		break;
		case 4: s = "/";
		}

		return s;
	}

	private int performOperation(int firstVal, int secondVal, int operator)
	{
		int num = 0;
		switch(operator)
		{
		case 1: num = firstVal + secondVal;
		break;
		case 2: num = firstVal - secondVal;
		break;
		case 3: num = firstVal * secondVal;
		break;
		case 4: num = firstVal / secondVal;
		break;
		}
		return num;
	}

	private int randomNumberInRange(int min, int max)
	{
		int num = 0;
		num = mRandom.nextInt(max - min + 1) + min;
		return num;
	}

	private void createOperatorCodes()
	{
		mTopOperator = mRandom.nextInt(4) + 1;
		mBottomOperator = mRandom.nextInt(4) + 1;

		//Logic to remove unwanted combinations
		if(mTopOperator == 1 && mBottomOperator == 1)
		{
			mTopOperator = mRandom.nextInt(3) + 2;
		}
		while(mTopOperator == 2 && mBottomOperator == 2)
		{
			mBottomOperator = mRandom.nextInt(4) + 1;
		}
	}

	private String createFirstStep()
	{
		String firstStep = "";
		switch(mTopOperator)
		{
		case 1: mTopNumber = createAdditionStep(FIRST_STEP_MAX, 1); //init numberCreated 
		break;
		case 2: mTopNumber = createSubtractionStep(1);
		break;
		case 3: mTopNumber = createMultiplicationStep(FIRST_STEP_MAX, 1);
		break;
		case 4: mTopNumber = createDivisionStep(1);
		break;
		}

		firstStep += operationToString(mTopOperator) + " ";
		mCurrentValue = performOperation(mCurrentValue, mTopNumber, mTopOperator);
		firstStep += mTopNumber;

		return firstStep;
	}

	private String createSecondStep()
	{
		String secondStep = "";

		switch(mBottomOperator)
		{
		case 1: mBottomNumber = createAdditionStep(SECOND_STEP_MAX, 2); //init numberCreated 
		break;
		case 2: mBottomNumber = createSubtractionStep(2);
		break;
		case 3: mBottomNumber = createMultiplicationStep(SECOND_STEP_MAX, 2);
		break;
		case 4: mBottomNumber = createDivisionStep(2);
		break;
		}

		secondStep += operationToString(mBottomOperator) + " ";
		mCurrentValue = performOperation(mCurrentValue, mBottomNumber, mBottomOperator);
		secondStep += mBottomNumber;

		return secondStep;
	}

	private int createAdditionStep(int max, int stepNumber)
	{
		int randomMax = max - mCurrentValue;
		int numberToAdd = 0; 
		//Cop-out method that prevents an error if the number generated can only be <0
		if(randomMax <= 0)
		{
			//Update operatorTop or bottom
			if(stepNumber == 1)
			{
				mTopOperator = 2;
			}
			else
			{
				mBottomOperator = 2;
			}

			//			numberToAdd = createSubtractionStep(stepNumber);
		}
		else //If the addition step is viable
		{
			numberToAdd = randomNumberInRange(MINIMUM, randomMax); //number that will be added
		}
		return numberToAdd;
	}

	private int createSubtractionStep(int stepNumber)
	{
		int randomMax = mCurrentValue - MINIMUM;
		int numberToSubtract;

		//Cop-out method: multiplies if subtraction isn't viable
		if(randomMax <= 0)
		{
			//Update operatorTop or bottom
			if(stepNumber == 1)
			{
				mTopOperator = 3;
			}
			else
			{
				mBottomOperator = 3;
			}

			numberToSubtract = createMultiplicationStep(FIRST_STEP_MAX, stepNumber);
		}
		else
		{
			numberToSubtract = mRandom.nextInt(randomMax) + 1; //number that will be subtracted
		}
		return numberToSubtract;
	}

	private int createMultiplicationStep(int max, int stepNumber)
	{
		/*
		 * Logic: rgenMax * currentvalue = 200 -> rgenMax = 200/currentValue
		 */
		int randomMax = max / mCurrentValue;
		int numberToMultiply;

		//If multiplication is inpossible, does division instead
		if(randomMax<= 1)
		{
			//Update operatorTop or bottom
			if(stepNumber == 1)
			{
				mTopOperator = 4;
			}
			else
			{
				mBottomOperator = 4;
			}

			numberToMultiply = createDivisionStep(stepNumber);
		}
		else
		{
			numberToMultiply = randomNumberInRange(MINIMUM, randomMax); 
		}
		return numberToMultiply;
	}

	private int createDivisionStep(int stepNumber)
	{
		/*
		 * Rules:
		 * currentValue % numberToDivide == 0
		 * numberToDivide != 1 && numberToDivide != currentValue  
		 */

		/*
		 * How this method works:
		 * 1) Creates an ArrayList
		 * 2) for loop that checks up to 1/2 of current value
		 * 		if the current number (i) % 0 of currentValue, store it in the ArrayList
		 * 3) If the array only has two values (therefore it is a prime number), do addition instead
		 * 		numberToDivide = doAdditionStep();
		 */
		ArrayList<Integer> factors = new ArrayList<Integer>();

		for(int i = 2; i <= (mCurrentValue / 2); i++)
		{
			if(mCurrentValue % i == 0)
			{
				factors.add(i);
			}
		}

		int numberToDivide = 0;

		//Cop out if the number is prime or division isn't viable
		if(factors.size() == 0)
		{
			//Update operatorTop or bottom
			if(stepNumber == 1)
			{
				mTopOperator = 1;
			}
			else
			{
				mBottomOperator = 1;
			}

			numberToDivide = createAdditionStep(FIRST_STEP_MAX, stepNumber);
		}
		else
		{
			numberToDivide = factors.get(mRandom.nextInt(factors.size()));
		}
		return numberToDivide;
	}

	private int[] createWrongAnswers()
	{
		int[] wrongAnswers = new int[2];
		int x = 0;
		do
		{
			for(int i = 0; i < 2; i++)
			{
				int randomMin = (int) (.75 * mCurrentValue);
				int randomMax = (int) (1.25 * mCurrentValue);
				wrongAnswers[i] = randomNumberInRange(randomMin, randomMax);
			}
			x++;
			if(x > 10)
			{
				wrongAnswers[1] = randomNumberInRange(2, 5);
			}
		}
		while(wrongAnswers[0] == wrongAnswers[1]
				|| wrongAnswers[0] == mCurrentValue
				|| wrongAnswers[1] == mCurrentValue);

		return wrongAnswers;
	}
	
	private int createCorrectAnswerLocation()
	{
		return mRandom.nextInt(3);
	}
	
	private int createLeftAnswer()
	{
		if(mCorrectAnswerLocation == 0)
			return mCurrentValue;
		else return mWrongAnswers[0];
	}
	
	private int createCenterAnswer()
	{
		if(mCorrectAnswerLocation == 1)
			return mCurrentValue;
		else if(mCorrectAnswerLocation == 0)
			return mWrongAnswers[0];
		else return mWrongAnswers[1];
	}
	
	private int createRightAnswer()
	{
		if(mCorrectAnswerLocation == 2)
			return mCurrentValue;
		else return mWrongAnswers[1];
	}

}
