package com.example.tipcalculator;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {

//    Variable for user interface
    private EditText billAmountEditText;
    private TextView percentTextView;
    private Button percentUpButton;
    private Button percentDownButton;
    private TextView tipTextView;
    private TextView totalTextView;


    private String billAmountString = "";
    private float tipPercent = .15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        //get references to the UI controls
        billAmountEditText = findViewById(R.id.editText_billAmount);
        percentTextView = findViewById(R.id.percentTextView);
        percentUpButton = findViewById(R.id.btn_percentUp);
        percentDownButton = findViewById(R.id.btn_percentDown);
        tipTextView = findViewById(R.id.tipAmountTextView);
        totalTextView = findViewById(R.id.totalAmountTextView);

        //set the listeners for EditText and Button
        billAmountEditText.setOnEditorActionListener(this);
        percentDownButton.setOnClickListener(this);
        percentUpButton.setOnClickListener(this);

    }

    //Saving States
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("billAmountString", billAmountString);
        outState.putFloat("tipPercent",tipPercent );

    }

    //Restore state when layout changes
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        if (savedInstanceState != null){
            billAmountString = savedInstanceState.getString("billAmountString", "");
            tipPercent = savedInstanceState.getFloat("tipPercent",0.15f);

            billAmountEditText.setText(billAmountString);
            calculateAndDisplay();
        }

    }

    private void calculateAndDisplay() {
        //get the bill amount
       billAmountString = billAmountEditText.getText().toString();
       float billAmount;
       if(billAmountString.equals("")){
           billAmount = 0;
       } else{
           billAmount = Float.parseFloat(billAmountString);
       }

       //Calculate Total
        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;

        //Display the results After
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        //Type inside
        calculateAndDisplay();
        return false;
    }


    @Override
    public void onClick(View view) {
        //Onclick increase tipPercent label
        if (view.getId() == R.id.btn_percentDown){
            tipPercent = tipPercent - 0.01f;
            calculateAndDisplay();
        } else if (view.getId() == R.id.btn_percentUp){
            tipPercent = tipPercent +0.01f;
            calculateAndDisplay();
        }
    }
}