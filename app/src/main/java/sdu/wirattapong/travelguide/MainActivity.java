package sdu.wirattapong.travelguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private TravelTABLE objTravelTABLE;
    private OrderTABLE objOrderTABLE;

    private EditText userEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Widget
        initialWidget();

        //Connected SQLite
        connectedSQLite();

        //Test Add Value
        //testAddValue();

        //Delete All Data
        deleteAlldata();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    } // on Create

    private void deleteAlldata() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("TravelGuide.db", MODE_APPEND, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("travelTABLE", null, null);
        objSqLiteDatabase.delete("orderTABLE", null, null);

    } //deleteAlldata

    private void synJSONtoSQLite() {
        //Setup Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        //Loop 2 Times
        int intTime = 0;
        while (intTime <= 1) {

            //Variable & Constant
            InputStream objInputStream = null;
            String strJSON = null;
            String strUserURL = "http://hosting.dusit.ac.th/usertable.php";
            String strTravelURL = "http://hosting.dusit.ac.th/traveltable.php";
            HttpPost objHttpPost;

            //1. Create InputStream
            try {
                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTime) {
                    case 0:
                        objHttpPost = new HttpPost(strUserURL);
                        break;
                    default:
                        objHttpPost = new HttpPost(strTravelURL);
                        break;
                }
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();
            } catch (Exception e) {
                Log.d("master", "InputStream ==> " + e.toString());
            }
            //2. Create strJSON
            try {
                BufferedReader objBufferedReader = new BufferedReader
                        (new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuider = new StringBuilder();
                String strLine = null;
                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuider.append(strLine);
                }
                objInputStream.close();
                strJSON = objStringBuider.toString();
            } catch (Exception e) {
                Log.d("master", "strJSON.==>" + e.toString());
            }

            //3. Update to SQLite
            try {
                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject jsonObject = objJsonArray.getJSONObject(i);
                    switch (intTime) {
                        case 0:
                            //Update usertable
                            String strUser = jsonObject.getString("User");
                            String strPassword = jsonObject.getString("Password");
                            String strName = jsonObject.getString("Name");
                            objUserTABLE.addNewUser(strUser, strPassword, strName);
                            break;
                        default:
                            //Update travelTABLE
                            String strTravel = jsonObject.getString("Travel");
                            String strSource = jsonObject.getString("Source");
                            String strPrice = jsonObject.getString("Price");
                            objTravelTABLE.addNewTravel(strTravel, strSource, strPrice);
                            break;
                    }
                }

            } catch (Exception e) {
                Log.d("master", "Update SQLite ==> " + e.toString());
            }

            //Increase intTimes
            intTime += 1;
        } //while

    }//Synchronize JSON to SQLite

    private void initialWidget() {
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText);

    }//Initial Widget

    private void testAddValue() {
        objUserTABLE.addNewUser("testUser", "testPass", "testName");
        objTravelTABLE.addNewTravel("TestTravel", "testSource", "testPrice");
        objOrderTABLE.addOrder("testOfficer", "testDesk", "testFood", "testItem");

    } // testAddValue


    private void connectedSQLite() {
        objUserTABLE = new UserTABLE(this);
        objTravelTABLE = new TravelTABLE(this);
        objOrderTABLE = new OrderTABLE(this);

    }//Connected SQLite

    public void clickLogin(View view) {
        String strUser = userEditText.getText().toString().trim();
        String strPassword = passwordEditText.getText().toString().trim();

        //Check Zero
        if (strUser.equals("") || strPassword.equals("")) {
            //Have Space
            errorDialog("มีช่องว่าง", "กรุณากรองให้ครบ ทุกช่องครับ");
        } else {
            //No Space
            checkUserPassword(strUser, strPassword);
        }

    }

    private void checkUserPassword(String strUser, String strPassword) {
        try {
            String[] strMyResult = objUserTABLE.searchUserPassword(strUser);
            if (strPassword.equals(strMyResult[2])) {
                //Password True
                welcomeDialog(strUser);
            } else {
                //Password False
                errorDialog("Password False", "Please Try Again Password False");
            }
        } catch (Exception e) {
            errorDialog("User False", "ไม่มี" + strUser + "ใน ฐานข้อมูลของเรา");
        }
    } //checkUserPassword

    private void welcomeDialog(final String strName) {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        //objBuilder.setIcon(R.drawable.travelguide);
        objBuilder.setTitle("Welcome");
        objBuilder.setMessage("ยินดีต้อนรับ" + strName + "/n" + "สู่ระบบของเรา");
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Officer", strName);
                startActivity(objIntent);
                finish();
            }
        });
        objBuilder.show();
    } //welcomDialog

    private void errorDialog(String strTitle, String strMessage) {
        AlertDialog.Builder objBuider = new AlertDialog.Builder(this);
        //objBuider.setIcon(R.drawable.icon);
        objBuider.setTitle(strTitle);
        objBuider.setMessage(strMessage);
        objBuider.setCancelable(false);
        objBuider.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuider.show();
    }

}
