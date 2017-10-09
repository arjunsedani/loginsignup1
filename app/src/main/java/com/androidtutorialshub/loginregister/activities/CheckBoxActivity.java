package com.androidtutorialshub.loginregister.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;

public class CheckBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.activity_check_box_1:
                if (checked)
                // Put some meat on the sandwich
                    Toast.makeText(CheckBoxActivity.this, "Clicked on Formget", Toast.LENGTH_LONG).show();
                else
                // Remove the meat
                break;
            case R.id.activity_check_box_2:
                if (checked)
                // Cheese me
                    Toast.makeText(CheckBoxActivity.this, "Clicked on Mailget", Toast.LENGTH_LONG).show();
               else
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
    }
}
