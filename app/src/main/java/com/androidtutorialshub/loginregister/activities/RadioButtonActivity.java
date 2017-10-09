package com.androidtutorialshub.loginregister.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;

public class RadioButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    // Pirates are the best
                    Toast.makeText(RadioButtonActivity.this, "Clicked on Formget", Toast.LENGTH_LONG).show();
                    break;
            case R.id.radioButton2:
                if (checked) {
                    // Ninjas rule
                    Toast.makeText(RadioButtonActivity.this, "Clicked on Mailget", Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }

}
