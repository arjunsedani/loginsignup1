package com.androidtutorialshub.loginregister.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Text View", "EditText", "Button",
                "ImageView", "ImageButton", "CheckBox", "Radio button", "RadioGroup",
                "ListView", "Spinner", "AutoCompleteTextView", "gridview", "Maps", "navigation",
                "sharedpreference", "SLIDEVIEW", "DOWNLOAD IMAGE", "BROADCAST RX", "DYNAMIC BROADCAST RX", "GIF",
                "long press", "network", "flexible space","Expandable List" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(getResources().getColor(android.R.color.black));
                return textView;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                if(position == 0){
                    launchTxtactivity();
                }
                if(position == 1){
                    launchEditTextactivity();
                }
                if(position == 2){
                    launchButtonactivity();
                }
                if(position == 3){
                    launchImageactivity();
                }
                if(position == 4){
                    launchImageButtonactivity();
                }
                if(position == 5){
                    launchCheckBoxactivity();
                }
                if(position == 6){
                    launchRadioButtonactivity();
                }
                if(position == 7){
                    launchRadioGroupactivity();
                }
                if(position == 8){
                    launchListViewactivity();
                }
                if(position == 9){
                    launchSpinneractivity();
                }
                if(position == 10){
                    launchAutoCompleteTextactivity();
                }
                if(position == 11){
                    launchGridactivity();
                }
                if(position == 12){
                    launchMapactivity();
                }
                if(position == 13){
                    launchNavigationactivity();
                }
                if(position == 14){
                    launchSharedPreferanceactivity();
                }
                if(position == 15){
                    launchSlideactivity();
                }
                if(position == 16){
                    launchImageDownloadactivity();
                }
                if(position == 17){
                    launchBroadcastctivity();
                }
                if(position == 18){
                    launchDynamicBroadcastctivity();
                }
                if(position == 19){
                    launchGIFActivity();
                }
                if(position == 20){
                    launchlongpressActivity();
                }
                if(position == 21){
                    launchneworkActivity();
                }
                if(position == 22){
                    launchflexibleActivity();
                }
                if(position == 23){
                    launchexpandableList();
                }
                if(position == 24){
                    Toast.makeText(ListActivity.this, "Clicked on position 2", Toast.LENGTH_LONG).show();
                }
                if(position == 25){
                    Toast.makeText(ListActivity.this, "Clicked on position 2", Toast.LENGTH_LONG).show();
                }


            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void launchTxtactivity() {

        Intent intent = new Intent(this, TxtActivity.class);
        startActivity(intent);
    }

    private void launchEditTextactivity() {

        Intent intent = new Intent(this, EditTextActivity.class);
        startActivity(intent);
    }

    private void launchButtonactivity() {

        Intent intent = new Intent(this, ButtonActivity.class);
        startActivity(intent);
    }
    private void launchImageactivity() {

        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    private void launchImageButtonactivity() {

        Intent intent = new Intent(this, ImageButtonActivity.class);
        startActivity(intent);
    }

    private void launchCheckBoxactivity() {

        Intent intent = new Intent(this, CheckBoxActivity.class);
        startActivity(intent);
    }

    private void launchRadioButtonactivity() {

        Intent intent = new Intent(this, RadioButtonActivity.class);
        startActivity(intent);
    }

    private void launchRadioGroupactivity() {

//        Intent intent = new Intent(this, RadioGroupActivity.class);
//        startActivity(intent);
        RadioGroupActivity fragment = new RadioGroupActivity();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame, fragment, "gfdgd");
        fragmentTransaction.commit();
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radiogrpButton1:
                if (checked)
                    // Pirates are the best
                    Toast.makeText(ListActivity.this, "Clicked on Formget", Toast.LENGTH_LONG).show();
                break;
            case R.id.radiogrpButton2:
                if (checked) {
                    // Ninjas rule
                    Toast.makeText(ListActivity.this, "Clicked on Mailget", Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }
    private void launchListViewactivity() {

        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    private void launchSpinneractivity() {

        Intent intent = new Intent(this, SpinnerActivity.class);
        startActivity(intent);
    }
    private void launchAutoCompleteTextactivity() {

        Intent intent = new Intent(this, AutoCompleteTextActivity.class);
        startActivity(intent);
    }
    private void launchGridactivity() {

        Intent intent = new Intent(this, GridActivity.class);
        startActivity(intent);
    }
    private void launchMapactivity() {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    private void launchNavigationactivity() {

        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }
    private void launchSharedPreferanceactivity() {

        Intent intent = new Intent(this, SharedPreference.class);
        startActivity(intent);
    }
    private void launchSlideactivity() {

        Intent intent = new Intent(this, ScreenSlide.class);
        startActivity(intent);
    }
    private void launchImageDownloadactivity() {

        Intent intent = new Intent(this, DownloadImgActivity.class);
        startActivity(intent);
    }
    private void launchBroadcastctivity() {

        Intent intent = new Intent(this, BroadcastActivity.class);
        startActivity(intent);
    }
    private void launchGIFActivity() {

        Intent intent = new Intent(this, FrescoAppGIFActivity.class);
        startActivity(intent);
    }
    private void launchDynamicBroadcastctivity() {

        Intent intent = new Intent(this, DynamicBroadcastActivity.class);
        startActivity(intent);
    }
    private void launchlongpressActivity() {

        Intent intent = new Intent(this, OnLongPress.class);
        startActivity(intent);
    }
    private void launchneworkActivity() {

        Intent intent = new Intent(this, NetworkConnectivityActivity.class);
        startActivity(intent);
    }
    private void launchflexibleActivity() {

        Intent intent = new Intent(this, FlexibleActivity.class);
        startActivity(intent);
    }
    private void launchexpandableList() {

        Intent intent = new Intent(this, ExpandableList.class);
        startActivity(intent);
    }




}
