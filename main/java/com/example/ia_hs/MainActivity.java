package com.example.ia_hs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText goldWeight, goldValue;
    Button btnCal, btnReset;
    TextView totGold, zakatpay, totzakat;
    Spinner spinner;
    ArrayAdapter<CharSequence>adapter;
    float gWeight, gVal;

    SharedPreferences sharedPref;
    private Menu menu;

    public void onItemSelected(AdapterView<?> parent, View view,
                                int pos, long id){

    }

    public void onNothingSelected(AdapterView<?> parent){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        goldWeight = findViewById(R.id.amount);
        goldValue = findViewById(R.id.currGV);
        totGold = findViewById(R.id.totGold);
        zakatpay = findViewById(R.id.zakatpay);
        totzakat = findViewById(R.id.totzakat);
        btnCal = findViewById(R.id.btnCal);
        btnReset = findViewById(R.id.btnReset);

        btnCal.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gWeight = sharedPref.getFloat("weight", 0.0F);
        gVal = sharedPref.getFloat("value", 0.0F);

        goldWeight.setText("" + gWeight);
        goldValue.setText("" + gVal);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){

        try {
            switch (v.getId()){
                case R.id.btnCal:
                    calc();

                    break;

                case R.id.btnReset:
                    goldWeight.setText("");
                    goldValue.setText("");
                    totGold.setText("Total of Gold Value:RM");
                    zakatpay.setText("Zakat Payable:RM");
                    totzakat.setText("Total of Zakat:RM");

                    break;
            }
        } catch (java.lang.NumberFormatException e){
            Toast.makeText(this, "Data Missing!", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this, "Unknown Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();

            Log.d("Exception", e.getMessage());
        }

    }

    public void calc() {
        DecimalFormat df = new DecimalFormat("##.00");
        float gWeight = Float.parseFloat(goldWeight.getText().toString());
        float gVal = Float.parseFloat(goldValue.getText().toString());
        String st = spinner.getSelectedItem().toString();
        double tVal, uruf, zakatPay, tZakat;

        sharedPref.edit().putFloat("weight", gWeight).apply();
        sharedPref.edit().putFloat("value", gVal).apply();

        if(st.equals("Keep")){
            tVal = gWeight * gVal;
            uruf = gWeight - 85;

            if (uruf>=0.0){
                zakatPay = uruf * gVal;
                tZakat = zakatPay * 0.025;
            }
            else{
                zakatPay = 0.0;
                tZakat = zakatPay * 0.025;
            }

            totGold.setText("Total of Gold Value:RM" + df.format(tVal));
            zakatpay.setText("Zakat Payable:RM" + df.format(zakatPay));
            totzakat.setText("Total of Zakat:RM" + df.format(tZakat));

        }

        else{
            tVal = gWeight * gVal;
            uruf = gWeight - 200;

            if (uruf>=0.0){
                zakatPay = uruf * gVal;
                tZakat = zakatPay * 0.025;
            }
            else{
                zakatPay = 0.0;
                tZakat = zakatPay * 0.025;
            }

            totGold.setText("Total of Gold Value:RM" + df.format(tVal));
            zakatpay.setText("Zakat Payable:RM" + df.format(zakatPay));
            totzakat.setText("Total of Zakat:RM" + df.format(tZakat));
        }
    }

}