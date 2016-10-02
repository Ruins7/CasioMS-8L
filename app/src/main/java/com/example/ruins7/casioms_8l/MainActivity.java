package com.example.ruins7.casioms_8l;

import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText display;//number to be displayed
    private EditText mode;//current mode
    private static String number1 = "0";//first input
    private static String number2 = "0";//second input
    private static String resultNum = "0";//result of calculation
    private static String operation = "C";//current operation, the default is clear
    private static int mrcTimes = 0;//times of MRC
    private static String storeMemory = "0";//store
    private static char point = '.';

    private static CalculateType ct = new CalculateType();

    private int[] allNumbers = {R.id.text0, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6, R.id.text7, R.id.text8, R.id.text9, R.id.point};
    private Button[] numberButtons = new Button[allNumbers.length];

    private int[] operations = {R.id.multiply, R.id.divide, R.id.minus, R.id.plus, R.id.clear, R.id.percentage, R.id.evolution, R.id.mrc, R.id.mminus, R.id.mplus};
    private Button[] operationButtons = new Button[operations.length];

    private int[] equalOperation = {R.id.equal};
    private Button[] equalButton = new Button[equalOperation.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set display is 0
        resultNum = "0";
        display = (EditText) findViewById(R.id.display);
        mode = (EditText) findViewById(R.id.mode);
        display.setText("0");
        mode.setText("");//default mode is ""
        display.setEnabled(false);//unable click
        mode.setEnabled(false);

        //store init
        mrcTimes = 0;
        storeMemory = "0";

        GetNumber GetNumber = new GetNumber();
        //set onclick listener for all numbers
        for (int i = 0; i < allNumbers.length; i++) {
            numberButtons[i] = (Button) findViewById(allNumbers[i]);
            numberButtons[i].setOnClickListener(GetNumber);
        }

        GetOperation getOperation = new GetOperation();
        //set onclick listener for all operations
        for (int i = 0; i < operations.length; i++) {
            operationButtons[i] = (Button) findViewById(operations[i]);
            operationButtons[i].setOnClickListener(getOperation);
        }

        GetEquation getEquation = new GetEquation();
        for (int i = 0; i < equalOperation.length; i++) {
            equalButton[i] = (Button) findViewById(equalOperation[i]);
            equalButton[i].setOnClickListener(getEquation);
        }

    }

    public void clear() {
        number1 = "0";
        number2 = "0";
        resultNum = "0";
        operation = "C";
        mrcTimes = 0;
        storeMemory = "0";
        display.setText("0");
        mode.setText("");
    }

    public boolean pointExist(String inputNum){
            Boolean exist = false;
            char[] c = inputNum.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if(c[i] == point) {
                    exist = true;
                }
            }
        return exist;
    }

    //get input number
    class GetNumber implements OnClickListener {

        @Override
        public void onClick(View v) {
            Boolean pointExist = false;
            if (operation.equals("C")) {//number1 assignment
                if (number1.equals("0")) {
                    String text = ((Button) v).getText().toString();
                        number1 = text;
                        display.setText(number1);
                } else if (!number1.equals("0")) {
                    String text = ((Button) v).getText().toString();
                    if(text.equals(".")){
                        pointExist = pointExist(number1);
                        if(pointExist == true){
                            display.setText("ERROR");
                        }else{
                            number1 += text;
                            display.setText(number1);
                        }
                    }else{
                        number1 += text;
                        display.setText(number1);
                    }
                }
            } else if (!operation.equals("C")) {//number2 assignment only +-*/
                if (number2.equals("0")) {
                    String text = ((Button) v).getText().toString();
                    number2 = text;
                    display.setText(text);
                } else if (!number2.equals("0")) {
                    String text = ((Button) v).getText().toString();
                    if(text.equals(".")){
                        pointExist = pointExist(number2);
                        if(pointExist == true){
                            display.setText("ERROR");
                        }else{
                            number2 += text;
                            display.setText(number2);
                        }
                    }else{
                        number2 += text;
                        display.setText(number2);
                    }
                }
            }
        }
    }

    //get operation
    class GetOperation implements OnClickListener {

        @Override
        public void onClick(View v) {
            //calculate first ,then get operation
            if (!number2.equals("0") && (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/"))) {//second time +-*/
                resultNum = ct.calculate(number1, number2, operation);
                number1 = resultNum;
                number2 = "0";
                display.setText(resultNum);
            }
            String text = ((Button) v).getText().toString();
            operation = text;

            if (operation.equals("C")) {
                clear();
            } else if (operation.equals("%")) {
                resultNum = ct.calculate(number1, number2, operation);
                display.setText(resultNum);
            } else if (operation.equals("√")) {
                resultNum = ct.calculate(number1, number2, operation);
                display.setText(resultNum);
                mode.setText("E");
            } else if (operation.equals("MRC")) {
                mrcTimes++;
                if (mrcTimes == 1) {
                    display.setText(storeMemory);
                } else if (mrcTimes == 2) {
                    storeMemory = "0";
                    mrcTimes = 0;
                    mode.setText("");
                }
            } else if (operation.equals("M+")) {
                storeMemory = number1;
                mode.setText("M");
            } else if (operation.equals("M-")) {
                resultNum = ct.calculate(storeMemory, number2, "-");
                number1 = resultNum;
                display.setText(resultNum);
            }
        }
    }

    //get =
    class GetEquation implements OnClickListener {

        @Override
        public void onClick(View v) {
            resultNum = ct.calculate(number1, number2, operation);
            number1 = resultNum;
            number2 = "0";
            display.setText(resultNum);
        }
    }

}