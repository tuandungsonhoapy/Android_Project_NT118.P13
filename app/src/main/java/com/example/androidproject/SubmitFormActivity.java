package com.example.androidproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.StringJoiner;

public class SubmitFormActivity extends AppCompatActivity {
    private Toast toast;
    private EditText editFullName, editCCCD, editExtraInfo;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_submit_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(getApplicationContext());
                mydialog.setTitle("Confirm");
                mydialog.setMessage("Are you sure You want to exit!");
                mydialog.setIcon(R.drawable.warning_icon);
                mydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                mydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
        initialView();
    }

    private void initialView() {
        editFullName = findViewById(R.id.editFullName);
        editCCCD = findViewById(R.id.editCCCD);
        editExtraInfo = findViewById(R.id.editExtraInfo);
        Button button = findViewById(R.id.button7);
        radioGroup = findViewById(R.id.radioGr);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editFullName.getText().toString();
                if(fullName.length() < 3){
                    Toast.makeText(getApplicationContext(), "Full Name to be at least 3 characters!", Toast.LENGTH_SHORT).show();
                    editFullName.requestFocus();
                    return;
                }
                String CCCD = editCCCD.getText().toString();
                if(CCCD.length() != 12){
                    Toast.makeText(getApplicationContext(), "CCCD must have 12 characters!", Toast.LENGTH_SHORT).show();
                    editCCCD.requestFocus();
                    return;
                }
                // Get degree information
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);
                String degreeInfo = radioButton.getText().toString();
                // Get interest (favorite) information
                StringJoiner stringJoiner = new StringJoiner("-");
                if(checkBox1.isChecked()) stringJoiner.add(checkBox1.getText().toString());
                if(checkBox2.isChecked()) stringJoiner.add(checkBox2.getText().toString());
                if(checkBox3.isChecked()) stringJoiner.add(checkBox3.getText().toString());
                String extraInfo = editExtraInfo.getText().toString();
                String summaryInfo = fullName + "\n" + CCCD + "\n" + degreeInfo + "\n" +
                        stringJoiner.toString() + "\n";
                summaryInfo += "-------------Extra Information------------"+ "\n" + extraInfo + "\n" +
                               "------------------------------------------";
                // Create dialog to show personal information
                AlertDialog.Builder dialog = new AlertDialog.Builder(SubmitFormActivity.this);
                dialog.setTitle("Personal Information");
                dialog.setMessage(summaryInfo);
                dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.create().show();
            }
        });
    }
}