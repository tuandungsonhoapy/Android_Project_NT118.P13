package com.example.androidproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;

public class CalculatorActivity extends AppCompatActivity {
    private TextView textView, textView1, textView2, textView3;
    private Button button, button1;
    private Spinner sp;
    private EditText editText, editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initalView();
    }

    private void initalView() {
        textView = findViewById(R.id.textView4);
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);
        textView3 = findViewById(R.id.textView1);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button2);
        sp = findViewById(R.id.sp1);
        editText = findViewById(R.id.editTextNumber1);
        editText1 = findViewById(R.id.editTextNumber);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operation = sp.getSelectedItem().toString();
                String firstNumber = editText.getText().toString();
                String secondNumber = editText1.getText().toString();
                try {
                    double a = Double.parseDouble(firstNumber);
                    double b = Double.parseDouble(secondNumber);
                    String result = Calculate(a,b,operation);
                    textView3.setText(result);
                }catch (Exception exception){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số hợp lệ.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    textView3.setText("Result");
                    editText.setText("");
                    editText1.setText("");
                } catch (Exception exception){
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String Calculate(double a, double b, String operation){
        switch (operation){
            case "+": return String.valueOf(a+b);
            case "-": return String.valueOf(a-b);
            case "*": return String.valueOf(a*b);
            case "/":
                try {
                    return String.valueOf(a/b);
                } catch (Exception exception){
                    return "Không thể chia cho 0!";
                }
        }
        return "0";
    }
}