package edu.gatech.seclass.sdpsalarycomp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SDPSalaryCompActivity extends AppCompatActivity {

    private List<String> cities = new ArrayList<>();
    private ArrayAdapter<String> currentAdapter;
    private ArrayAdapter<String> newAdapter;
    private int[] prices ={160, 152, 197, 201, 153, 244, 232, 241, 198, 114, 139, 217};
    private EditText baseSalary;
    private Spinner currentCity;
    private Spinner newCity;
    private TextView targetSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpsalary_comp);

        newView();

        startData();

        startListener();
    }

    private void newView() {

        baseSalary = (EditText) findViewById(R.id.baseSalary);
        currentCity = (Spinner) findViewById(R.id.currentCity);
        newCity = (Spinner) findViewById(R.id.newCity);
        targetSalary = (TextView) findViewById(R.id.targetSalary);

        currentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        newAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        currentCity.setAdapter(currentAdapter);
        newCity.setAdapter(newAdapter);

    }

    private void startData(){

        cities.add(getString(R.string.city1));
        cities.add(getString(R.string.city2));
        cities.add(getString(R.string.city3));
        cities.add(getString(R.string.city4));
        cities.add(getString(R.string.city5));
        cities.add(getString(R.string.city6));
        cities.add(getString(R.string.city7));
        cities.add(getString(R.string.city8));
        cities.add(getString(R.string.city9));
        cities.add(getString(R.string.city10));
        cities.add(getString(R.string.city11));
        cities.add(getString(R.string.city12));


        currentAdapter.addAll(cities);
        newAdapter.addAll(cities);
        }




    private void startListener() {

        newCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertSalary( );                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

            //listen the change of  Current City
        currentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertSalary( );                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            // for baseSalary, add textchangedListener
        baseSalary.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
            }

            @Override
            public void afterTextChanged(Editable s){
                convertSalary( );
            }
        });
    }



    private void convertSalary( ) {

        String baseSalaryStr = baseSalary.getText().toString().trim();
          if (TextUtils.isEmpty(baseSalaryStr) || baseSalaryStr.contains("-")) {

            baseSalary.setError("Invalid salary");
            targetSalary.setText("");
        } else {
              float tempSalary = Float.parseFloat(baseSalaryStr);
              float target = tempSalary * prices[newCity.getSelectedItemPosition()] / prices[currentCity.getSelectedItemPosition()];

              targetSalary.setText(String.valueOf(Math.round(target)));


        }
    }
}
















