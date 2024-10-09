package com.example.androidproject.other_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;

import java.text.DecimalFormat;

public class TemperatureConversionAppActivity extends AppCompatActivity implements View

        .OnClickListener {
    private EditText editC, editF;
    private Button btnctc, btnctf, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature_conversion_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialView();
    }

    private void initialView() {
        editC = findViewById(R.id.editNumber2);
        editF = findViewById(R.id.editNumber1);
        btnctc = findViewById(R.id.btnctc);
        btnctf = findViewById(R.id.btnctf);
        btnClear = findViewById(R.id.btnclear);
        btnctc.setOnClickListener(this);
        btnctf.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        double celsiusValue, farenheitValue;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try {
            if(view.equals(btnClear)){
                editF.setText("");
                editC.setText("");
                return;
            } else if(view.equals(btnctc)){
                if(editF.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập giá trị farenheit!", Toast.LENGTH_SHORT).show();
                    return;
                }
                farenheitValue = Double.parseDouble(editF.getText().toString());
                celsiusValue = (farenheitValue - 32) / 1.8;
                editC.setText(String.valueOf(decimalFormat.format(celsiusValue)));
            } else if(view.equals(btnctf)){
                if(editC.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập giá trị celsius!", Toast.LENGTH_SHORT).show();
                    return;
                }
                celsiusValue = Double.parseDouble(editC.getText().toString());
                farenheitValue = 1.8 * celsiusValue + 32;
                editF.setText(String.valueOf(decimalFormat.format(farenheitValue)));
            }
        } catch (Exception exception){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập giá trị số!", Toast.LENGTH_SHORT).show();
        }
    }
}