package sdu.wirattapong.travelguide;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    //Explicit
    private TextView officerTextView;
    private Spinner deskSpinner;
    private ListView travelListView;
    private String officerString,deskString,travelString, itemString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //Bind Widget
        bindWidget();
        //Show Officer
        showOfficer();
        //Show Spinner
        showSpinner();
        //Create ListView
        createListView();
    }

    private void showSpinner() {
        //Create Spiner
        final String[] strDeskSpiner = {"One", "Two", "Three", "Four", "Five"};
        ArrayAdapter<String> deskAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strDeskSpiner);
        deskSpinner.setAdapter(deskAdapter);
        //Active onClick
        deskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strDesk = strDeskSpiner[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                strDesk = strDeskSpiner[0];
            }
        });
    }

    private void showOfficer() {
        officerString = getIntent().getStringExtra("Officer");
        officerTextView.setText(officerString);

    }

    private void createListView() {
        TravelTABLE objTravelTABLE = new TravelTABLE(this);
        final String[] strTravel = objTravelTABLE.readAllTravel(1);
        String[] strSource = objTravelTABLE.readAllTravel(2);
        String[] strPrice = objTravelTABLE.readAllTravel(3);
        MyAdapter objMyAdapter = new MyAdapter(OrderActivity.this,
                strTravel, strSource, strPrice);
        travelListView.setAdapter(objMyAdapter);

        travelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                travelString = strTravel[position];
                chooseItem();
            }

            private void chooseItem() {
                final CharSequence[] choiceCharSequence = {"1 set", "2 set", "3 set", "4 set", "5 set"};
                AlertDialog.Builder objBuider = new AlertDialog.Builder(this);
                objBuider.setTitle(travelString);
                objBuider.setSingleChoiceItems(choiceCharSequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                itemString = "1";
                                break;
                            case 1:
                                itemString = "2";
                                break;
                            case 2:
                                itemString = "3";
                                break;
                            case 3:
                                itemString = "4";
                                break;
                            case 4:
                                itemString = "5";
                                break;
                        } // Swich
                        uploadToSQLite();
                        dialog.dismiss();
                    } //event
                });

            }


        });
    }

    private void uploadToSQLite() {

    }//uploadToSQLite

    private void bindWidget() {
        officerTextView = (TextView) findViewById(R.id.textView2);
        deskSpinner = (Spinner) findViewById(R.id.spinner);
        travelListView = (ListView) findViewById(R.id.listView);
    }

}
