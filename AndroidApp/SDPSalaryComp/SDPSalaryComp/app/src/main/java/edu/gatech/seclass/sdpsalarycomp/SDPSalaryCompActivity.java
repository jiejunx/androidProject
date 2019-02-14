package edu.gatech.seclass.sdpsalarycomp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SDPSalaryCompActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner currentCity;
    private Spinner newCity;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> cities;
    private TextView baseSalary;
    private TextView targetSalary;
    private ArrayList<Integer> prices;
    private int currentIndex;
    private int targetIndex;
    private int target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpsalary_comp);


        currentCity = (Spinner) findViewById(R.id.currentCity);
        newCity = (Spinner) findViewById(R.id.newCity);
        baseSalary = (TextView) findViewById(R.id.baseSalary);
        targetSalary = (TextView) findViewById(R.id.targetSalary);


        cities = new ArrayList<String>();
        cities.add("Atlanta, GA");
        cities.add("Austin, TX");
        cities.add("Boston, MA");
        cities.add("Honolulu, HI");
        cities.add("Las Vegas, NV");
        cities.add("Mountain View, CA");
        cities.add("New York City, NY");
        cities.add("San Francisco, CA");
        cities.add("Seattle, WA");
        cities.add("Springfield, MO");
        cities.add("Tampa, FL");
        cities.add("Washington D.C.");

        prices = new ArrayList<Integer>(Arrays.asList(160, 152, 197, 201, 153, 244, 232, 241, 198, 114, 139, 217));

        adapter = new ArrayAdapter(SDPSalaryCompActivity.this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentCity.setAdapter(adapter);
        newCity.setAdapter(adapter);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "selected : ", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public String convertSalary(String baseSalary, String currentCity, String targetCity) {

        Double tempSalary = Double.parseDouble(baseSalary);
        int temp = tempSalary.intValue();
        if ((tempSalary < 0) || (baseSalary.contains("."))) {

            System.out.print(" error mark here, change");

        } else {
            target = temp * (prices.get(targetIndex) / prices.get(currentIndex));

            currentIndex = Arrays.asList(cities).indexOf(currentCity);

            targetIndex = Arrays.asList(cities).indexOf(targetCity);


        }
    }
}
















