package com.example.calculator;

import static java.lang.Math.floor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvExpression;
    private TextView tvResult;
    private double valueOne = Double.NaN;
    private double valueTwo = Double.NaN;
    private char currentOperation;
    private StringBuilder expressionString = new StringBuilder();

    private boolean valueOneHasDecimal = false;
    private boolean valueTwoHasDecimal = false;
    private double decimalPlaces = 0;
    private int decimalValue = 0;
    private String decimalValuetemp1 = "";
    private String decimalValuetemp2 = "";
    private int c = 0;
    private int c1 = 0;
    private int c2 = 0;


    // Constants
    private static final char PI = 'π';
    private static final char E = 'e';
    private static final char EQU = 0;

    // Basic Arithmetic Operations
    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';

    // Advanced Arithmetic Operations
    private static final char MODULUS = '%';
    private static final char POWER = '^';
    private static final char FACTORIAL = '!';

    // Trigonometric Functions
    private static final char SIN = 's';
    private static final char COS = 'c';
    private static final char TAN = 't';
    private static final char ASIN = 'S'; // Changed to 'a' for simplicity
    private static final char ACOS = 'C';
    private static final char ATAN = 'T';

    // Logarithmic Functions
    private static final char LOG = 'L';
    private static final char LN = 'l';
    private static final char EXP = 'E';
    private static final char ANTILOG = 'A';

    // Roots and Powers
    private static final char SQRT = '√';
    private static final char CBRT = '∛';
    private static final char NTHROOT = 'n';

    // Miscellaneous
    private static final char ABS = 'a';
    private static final char RECIPROCAL = 'R';
    private static final char ROUND = 'r';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // Number buttons
        int[] numberButtonIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(this::onNumberClick);
        }

        // Operator buttons
        int[] operatorButtonIds = {R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonModulo, R.id.buttonPower, R.id.buttonFactorial, R.id.buttonSqrt, R.id.buttonCbrt, R.id.btnNthRoot, R.id.btnAbs, R.id.btnReciprocal, R.id.btnRound};
        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(this::onOperatorClick);
        }

        // Trigonometric buttons
        int[] trigButtonIds = {R.id.buttonSin, R.id.buttonCos, R.id.buttonTan, R.id.buttonAsin, R.id.buttonAcos, R.id.buttonAtan};
        for (int id : trigButtonIds) {
            findViewById(id).setOnClickListener(this::onTrigoClick);
        }

        // Logarithmic buttons
        int[] logButtonIds = {R.id.buttonLog, R.id.buttonLn, R.id.buttonExp, R.id.buttonAntilog};
        for (int id : logButtonIds) {
            findViewById(id).setOnClickListener(this::onLogClick);
        }

        // Constant buttons
        findViewById(R.id.buttonPi).setOnClickListener(this::onConstantClick);
        findViewById(R.id.buttonE).setOnClickListener(this::onConstantClick);

        // Equal and Clear buttons
        findViewById(R.id.buttonEqual).setOnClickListener(this::onEqualClick);
        findViewById(R.id.buttonClear).setOnClickListener(this::onClearClick);
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        String number = button.getText().toString();

        if (Double.isNaN(valueOne) && currentOperation != EQU && (currentOperation == FACTORIAL || currentOperation == SQRT || currentOperation == CBRT || currentOperation == ROUND || currentOperation == ABS || currentOperation == RECIPROCAL)) {
            valueOne = Double.parseDouble(number);
            //expressionString.setLength(0); // Clear previous expression
            System.out.println(valueOne + " " + " " + number );
            expressionString.append(" "+number);
            tvExpression.setText(expressionString.toString());
        }else if (!Double.isNaN(valueOne) && currentOperation != EQU && (currentOperation == FACTORIAL || currentOperation == SQRT || currentOperation == CBRT ||  currentOperation == ROUND || currentOperation == ABS || currentOperation == RECIPROCAL)) {
            valueOne = (valueOne*10) + Double.parseDouble(number);
            //expressionString.setLength(0); // Clear previous expression
            System.out.println(valueOne + " " + " " + number );
            expressionString.append(number);
            tvExpression.setText(expressionString.toString());
        }
        else if (Double.isNaN(valueOne)) {
            valueOne = Double.parseDouble(number);
            expressionString.append(number);
            tvExpression.setText(expressionString.toString());
        } else if (currentOperation == EQU) {
            if(valueOneHasDecimal == true)
            {
                decimalValuetemp1 += number;
                decimalValue = Integer.parseInt(decimalValuetemp1);
                System.out.println("temp: "+decimalValuetemp1);
                int a = decimalValuetemp1.length();
                c1 = a;
                System.out.println("Size of temp: "+a+" "+c1);
                double b = decimalValue/Math.pow(10,a);
                System.out.println("decimal value: "+b);

                valueOne = floor(valueOne) + b;


//                valueOne = valueOne + Double.parseDouble(number)/10;
                System.out.println(valueOne +" "+ number);

            }
            else{
            valueOne = (valueOne * 10) + Double.parseDouble(number);}
            expressionString.append(number);
            tvExpression.setText(expressionString.toString());}

        else if (currentOperation != EQU && Double.isNaN(valueTwo)) {
            // An operator is selected, set valueTwo
//            tvResult.setText(number);
            valueTwo = Double.parseDouble(number);
            expressionString.append(" "+ number);
            tvExpression.setText(expressionString.toString());
        }
        else if (currentOperation != EQU && !Double.isNaN(valueTwo)) {
            // An operator is selected, set valueTwo
//            tvResult.setText(number);

            if(valueTwoHasDecimal == true)
            {

                decimalValuetemp2 += number;
                decimalValue = Integer.parseInt(decimalValuetemp2);
                System.out.println("temp: "+decimalValuetemp2);
                int a = decimalValuetemp2.length();
                c2 = a;
                System.out.println("Size of temp: "+a+" "+c2);
                double b = decimalValue/Math.pow(10,a);
                System.out.println("decimal value: "+b);

                valueTwo = floor(valueTwo) + b;

//                valueTwo = valueTwo + Double.parseDouble(number)/10;
                System.out.println(valueTwo +" "+ number);
            }
            else{
            valueTwo = (valueTwo*10) + Double.parseDouble(number);}
            expressionString.append(number);
            tvExpression.setText(expressionString.toString());
        }else {
            // Adding to existing value
            String currentText = tvExpression.getText().toString();
            if (currentText.equals("0") || currentText.equals("")) {
                tvExpression.setText(number);
            } else {
                tvExpression.append(number);
            }

            if (Double.isNaN(valueTwo)) {
                valueOne = Double.parseDouble(tvExpression.getText().toString());
            } else {
                valueTwo = Double.parseDouble(tvExpression.getText().toString());
            }

            expressionString.append(number);
            tvExpression.setText(expressionString.toString());
        }
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        char operation = button.getText().toString().charAt(0);

        if (!Double.isNaN(valueOne) && !Double.isNaN(valueTwo)) {
            try {
                c =c1+c2;
                BigDecimal result = BigDecimal.valueOf(performOperation(valueOne,valueTwo,currentOperation));
                result = result.setScale(c, RoundingMode.HALF_UP);
                expressionString.append(" = ").append(result.stripTrailingZeros().toPlainString());
                tvExpression.setText(expressionString.toString());
                valueOne = result.doubleValue();
                System.out.println(valueOne);
                valueTwo = Double.NaN;
            } catch (NumberFormatException | ArithmeticException e) {
                tvResult.setText("Invalid input");
                valueOne = Double.NaN;
                valueTwo = Double.NaN;
                return;
            }
        }

        currentOperation = operation;
//        tvResult.setText("");


        expressionString.append(" "+operation);

        tvExpression.setText(expressionString.toString());
    }

    public void onTrigoClick(View view) {
        Button button = (Button) view;
        char operation = button.getText().toString().charAt(0);

        if (!Double.isNaN(valueOne)) {
            try {
                double result = performTrigoOperation(valueOne, operation);
//                tvResult.setText(String.valueOf(result));
                expressionString.append(" = ").append(result);
                tvExpression.setText(expressionString.toString());
                valueOne = result;
            } catch (NumberFormatException | ArithmeticException e) {
                tvResult.setText("Invalid input");
                valueOne = Double.NaN;
                return;
            }
        }
    }

    public void onLogClick(View view) {
        Button button = (Button) view;
        char operation = button.getText().toString().charAt(0);
        if (Double.isNaN(valueOne))
        {
            currentOperation = operation;
            expressionString.append(operation);
            tvExpression.setText(expressionString.toString());
        }

        if (!Double.isNaN(valueOne)) {
            try {
                double result = performLogOperation(valueOne, operation);
//                tvResult.setText(String.valueOf(result));
                expressionString.append(" = ").append(result);
                tvExpression.setText(expressionString.toString());
                valueOne = result;
            } catch (NumberFormatException | ArithmeticException e) {
                tvResult.setText("Invalid input");
                valueOne = Double.NaN;
                return;
            }
        }
    }

    public void onConstantClick(View view) {
        Button button = (Button) view;
        char constant = button.getText().toString().charAt(0);

        if (constant == PI) {
//            tvResult.setText(String.valueOf(Math.PI));
            if(!Double.isNaN(valueOne)){
                valueTwo = Math.PI;
            }
            else{
                valueOne = Math.PI;
            }

            expressionString.append(" π");
            tvExpression.setText(expressionString.toString());
        } else if (constant == E) {
//            tvResult.setText(String.valueOf(Math.E));
            if(!Double.isNaN(valueOne)){
                valueTwo = Math.E;
            }
            else{
                valueOne = Math.E;
            }
            expressionString.append(" e");
            tvExpression.setText(expressionString.toString());
        }
    }

    public void onEqualClick(View view) {
        if (currentOperation == FACTORIAL || currentOperation == SQRT || currentOperation == CBRT || currentOperation == ROUND || currentOperation == ABS || currentOperation == RECIPROCAL) {
            valueTwo = 1;}
        if (!Double.isNaN(valueOne) && !Double.isNaN(valueTwo) && currentOperation != EQU) {
            try {
                c =c1+c2;
                BigDecimal result = BigDecimal.valueOf(performOperation(valueOne,valueTwo,currentOperation));
                result = result.setScale(c, RoundingMode.HALF_UP);
                tvResult.setText(String.valueOf(result.stripTrailingZeros().toPlainString()));
                expressionString.append(" = ").append(result.stripTrailingZeros().toPlainString());
                tvExpression.setText(expressionString.toString());
                valueOne = result.doubleValue();
                System.out.println(valueOne);
                valueTwo = Double.NaN;

                currentOperation = EQU;
                decimalPlaces = 0;
                decimalValue = 0;
                valueOneHasDecimal = false;
                valueTwoHasDecimal = false;
                decimalValuetemp1 = "";
                decimalValuetemp2 = "";
                c1 =c;
                c2 =0;

            } catch (NumberFormatException | ArithmeticException e) {
                tvResult.setText("Invalid input");
            }
        } else {
            tvResult.setText("Invalid input");
        }
    }

    public void onClearClick(View view) {
        tvResult.setText("");
        tvExpression.setText("");
        valueOne = Double.NaN;
        valueTwo = Double.NaN;
        currentOperation = EQU;
        decimalPlaces = 0;
        decimalValue = 0;
        expressionString.setLength(0); // Reset expressionString
        valueOneHasDecimal = false;
        valueTwoHasDecimal = false;
        decimalValuetemp1 = "";
        decimalValuetemp2 = "";
        c = 0;
        c1 = 0;
        c2 = 0;
    }

    public void onClearDisplay(View view) {
        tvExpression.setText("");
        expressionString.setLength(0);
    }
    public void onDecimalClick(View view) {
        // only printing .
//        String currentText = tvExpression.getText().toString();
        if(!Double.isNaN(valueOne) && Double.isNaN(valueTwo) && valueOneHasDecimal==false)
        {
            valueOneHasDecimal = true;
            decimalPlaces = 1;

        }
        if(!Double.isNaN(valueOne) && !Double.isNaN(valueTwo) && valueTwoHasDecimal==false && currentOperation != EQU){
            valueTwoHasDecimal = true;
            decimalPlaces = 1;

        }
            tvExpression.append(".");
            expressionString.append(".");
            tvExpression.setText(expressionString.toString());
//            if(!Double.isNaN(valueOne) && Double.isNaN(valueTwo))
//            {
//                valueOne = Decimal(valueOne);
//            }

    }


    private double performOperation(double valueOne, double valueTwo, char operation) {
        switch (operation) {
            case ADDITION:
                return valueOne + valueTwo;
            case SUBTRACTION:
                return valueOne - valueTwo;
            case MULTIPLICATION:
                return valueOne * valueTwo;
            case DIVISION:
                if (valueTwo == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return valueOne / valueTwo;
            case MODULUS:
                return valueOne % valueTwo;
            case POWER:
                return Math.pow(valueOne, valueTwo);
            case FACTORIAL:
                return factorial(valueOne);
            case SQRT:
                return Math.sqrt(valueOne);
            case CBRT:
                return Math.cbrt(valueOne);
            case NTHROOT:
                return Math.pow(valueOne, 1 / valueTwo);
            case ROUND:
                return Math.round(valueOne);
            case ABS:
                return Math.abs(valueOne);
            case RECIPROCAL:
                if (valueOne == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return 1 / valueOne;
            default:
                throw new ArithmeticException("Invalid operation");
        }
    }

    private double factorial(double n) {
        if (n < 0 || n != (int)n) { // Factorial is not defined for negative numbers or non-integers
            throw new ArithmeticException("Invalid input for factorial");
        }
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    private double performTrigoOperation(double value, char operation) {
        switch (operation) {
            case SIN:
                return Math.sin(Math.toRadians(value));
            case COS:
                return Math.cos(Math.toRadians(value));
            case TAN:
                return Math.tan(Math.toRadians(value));
            case ASIN:
                return Math.toDegrees(Math.asin(value));
            case ACOS:
                return Math.toDegrees(Math.acos(value));
            case ATAN:
                return Math.toDegrees(Math.atan(value));
            default:
                throw new ArithmeticException("Invalid trigonometric operation");
        }
    }

    private double performLogOperation(double value, char operation) {
        switch (operation) {
            case LOG:
                if (value <= 0) {
                    throw new ArithmeticException("Logarithm undefined for zero or negative values");
                }
                return Math.log10(value);
            case LN:
                if (value <= 0) {
                    throw new ArithmeticException("Natural log undefined for zero or negative values");
                }
                return Math.log(value);
            case EXP:
                return Math.exp(value);
            case ANTILOG:
                return Math.pow(10, value);
            default:
                throw new ArithmeticException("Invalid logarithmic operation");
        }
    }
}