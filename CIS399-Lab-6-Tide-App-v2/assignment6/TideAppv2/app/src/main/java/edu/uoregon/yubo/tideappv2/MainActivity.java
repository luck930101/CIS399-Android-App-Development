package edu.uoregon.yubo.tideappv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private EditText dateedit;
    private Spinner locationSpinner;
    private Button button;
    private Dal dal = new Dal(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSpinner = (Spinner)findViewById(R.id.locationSpinner);
        dal.loadDb();
        button = (Button) findViewById(R.id.button);
        dateedit = (EditText) findViewById(R.id.dateedit);
        //display a spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.location_array,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        locationSpinner.setAdapter(adapter);
        button.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.button){
            String date = dateedit.getText().toString();
            String location = (String)locationSpinner.getSelectedItem();

            Intent intent = new Intent(this, SecondActivity.class);
            //send data to the second activity
            intent.putExtra("date", date);
            intent.putExtra("location",location);


            startActivity(intent);
        }
    }

}
