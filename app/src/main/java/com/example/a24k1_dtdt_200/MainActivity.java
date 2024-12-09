package com.example.a24k1_dtdt_200;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonSquare, buttonRoot, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAc, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        assignId(buttonC, R.id.button_c);
        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonBrackClose, R.id.button_close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiple);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_subtraction);
        assignId(buttonSquare, R.id.button_square);
        assignId(buttonRoot, R.id.button_root);
        assignId(buttonEquals, R.id.button_equal);
        assignId(button0, R.id.button_zero);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAc, R.id.button_action);
        assignId(buttonDot, R.id.button_dot);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        switch (buttonText) {
            case "AC":
                // Reset both input and result views
                solutionTv.setText("");
                resultTv.setText("0");
                return;

            case "=":
                // Calculate and display the result only when "=" is pressed
                String finalResult = getResults(dataToCalculate);
                if (!finalResult.equals("ERROR")) {
                    resultTv.setText(finalResult);
                    solutionTv.setText(finalResult); // Optionally update the input field with the result
                }
                return;

            case "C":
                if (!dataToCalculate.isEmpty()) {
                    // Remove the last character if text is not empty
                    dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                }
                solutionTv.setText(dataToCalculate);
                return;

            case "x²":  // For square
                // Append Math.pow(x, 2) for square calculation
                dataToCalculate = "Math.pow(" + dataToCalculate + ", 2)";
                solutionTv.setText(dataToCalculate);
                return;

            case "√":  // For square root
                // Append Math.sqrt(x) for square root calculation
                dataToCalculate = "Math.sqrt(" + dataToCalculate + ")";
                solutionTv.setText(dataToCalculate);
                return;

            default:
                // Append the button text to the current input
                dataToCalculate += buttonText;
                solutionTv.setText(dataToCalculate);
                return;
        }
    }
    
    String getResults(String data) {
        // Evaluate the expression using Rhino
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Object result = context.evaluateString(scriptable, data, "JavaScript", 1, null);

            String finalResult = result.toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");  // Remove decimal if integer result
            }
            return finalResult;
        } catch (Exception e) {
            return "ERROR";  // Return error if something goes wrong
        } finally {
            Context.exit();  // Ensure the context is exited to prevent memory leaks
        }
    }

}