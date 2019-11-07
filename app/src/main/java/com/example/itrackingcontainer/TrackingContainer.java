package com.example.itrackingcontainer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TrackingContainer extends AppCompatActivity  {
    static boolean active = false;
    EditText txtInputCont;
    Button btnTimkiem;
    JSONArray jsonArray;
    JSONObject jsonObject;
    private String id,CntrNo,CntrClass,Status,BLNo,SealNo,SealNo1,OprID,DateIn,DateOut;
    private String cBlock,cBay,cRow,cTier,cArea,CJMode_CD,ImVoy,ExVoy,POD,FPOD;
    private String CMStatus,ShipID,ShipYear,ShipVoy,CMDWeight,LocalSZPT;
    private static final String TAG = "TrackingContainer";
    ProgressBar progressBar;
    String urlApi = "http://27.72.124.188:8888/api/Container/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_container);
        setTitle(R.string.title_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnTimkiem=findViewById(R.id.btnSearch);
        txtInputCont=findViewById(R.id.txtNhapSoContainer);
        progressBar=findViewById(R.id.prBar);

        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {

                    if (checkCont(txtInputCont.getText().toString()) == false) {
                        Toast.makeText(getBaseContext(), R.string.notify_empty_cont, Toast.LENGTH_LONG).show();
                        txtInputCont.requestFocus();
                    } else {
                        new DoGetData(urlApi).execute();
                        // Set the button not-clickable..
                        btnTimkiem.setEnabled(false);

                        // Then re-enable it after 5 seconds..
                        final Runnable enableButton = new Runnable() {
                            @Override
                            public void run() {
                                btnTimkiem.setEnabled(true);
                            }
                        };

                        new Handler().postDelayed(enableButton, 5000);

                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(),R.string.str_internet,Toast.LENGTH_LONG).show();
                }
            }
        });
        //Đổi chữ thường thanh chữ hoa
        txtInputCont.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            @Override

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }
            @Override
            public void afterTextChanged(Editable et) {
                String s=et.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    txtInputCont.setText(s);
                    txtInputCont.setSelection(txtInputCont.length()); //fix reverse texting
                }
            }
        });

        //--------------ket thuc doi chu thuong thanh hoa----------------------
    }

    private boolean checkCont(String cont)
    {
        boolean kt=false;
        if(!cont.toString().isEmpty())
        {
            kt=true;
        }
        return kt;
    }
    class DoGetData extends AsyncTask<Void, Void, Void> {
        String urlApi;
        String result = "";

        public DoGetData(String urlApi) {
            this.urlApi = urlApi+txtInputCont.getText().toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(urlApi);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                int byteCharacter;

                while ((byteCharacter = is.read()) != -1) {
                    result += (char) byteCharacter;
                }

                Log.d(TAG, "doInBackground: " + result);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Container container=null;
            //tvJsonArayAPI.setText(result);
            //Toast.makeText(getBaseContext(),result,Toast.LENGTH_LONG).show();
            //co chuoi tra ve result
           // if(!result.equals("")) {
                try {
                    //jsonArray = new JSONArray(result);
                    jsonObject= new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // for (int i = 0; i < jsonArray.length(); i++) {
                if(jsonObject!=null) {
                    try {
                        // jsonObject = jsonArray.getJSONObject(i);
                        //doc cac gia tri
                        id = jsonObject.getString("ID");
                        CntrNo = jsonObject.getString("CntrNo");
                        CntrClass = jsonObject.getString("CntrClass");
                        Status = jsonObject.getString("Status");
                        BLNo = jsonObject.getString("BLNo");
                        SealNo = jsonObject.getString("SealNo");
                        SealNo1 = jsonObject.getString("SealNo1");
                        OprID = jsonObject.getString("OprID");
                        DateIn = jsonObject.getString("DateIn");
                        DateOut = jsonObject.getString("DateOut");
                        cBlock = jsonObject.getString("cBlock");
                        cBay = jsonObject.getString("cBay");
                        cRow = jsonObject.getString("cRow");
                        cTier = jsonObject.getString("cTier");
                        cArea = jsonObject.getString("cArea");
                        CJMode_CD = jsonObject.getString("CJMode_CD");
                        ImVoy = jsonObject.getString("ImVoy");
                        ExVoy = jsonObject.getString("ExVoy");
                        POD = jsonObject.getString("POD");
                        FPOD = jsonObject.getString("FPOD");
                        CMStatus = jsonObject.getString("CMStatus");
                        ShipID = jsonObject.getString("ShipID");
                        ShipYear = jsonObject.getString("ShipYear");
                        ShipVoy = jsonObject.getString("ShipVoy");
                        CMDWeight = jsonObject.getString("CMDWeight");
                        LocalSZPT = jsonObject.getString("LocalSZPT");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // }
                    container=null;
                    //tao doi tuong
                    container = new Container(id, CntrNo, CntrClass, Status, BLNo, SealNo, SealNo1, OprID, DateIn,
                            DateOut, cBlock, cBay, cRow, cTier, cArea, CJMode_CD, ImVoy, ExVoy, POD, FPOD, CMStatus, ShipID,
                            ShipYear, ShipVoy, CMDWeight, LocalSZPT);
                    //Toast.makeText(getBaseContext(),container.getID()+" -- "+container.getCntrNo(),Toast.LENGTH_LONG).show();
                    //truyen doi tuong container
                    //Intent intent = new Intent(getBaseContext(), ResultSearchCont.class);

                    Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                    intent.putExtra("objCont", container);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getBaseContext(), "Không tìm thấy thông tin", Toast.LENGTH_LONG).show();
                    finish();
                }
//            }
//            else
//                Toast.makeText(getBaseContext(),"Error, can not get Json",Toast.LENGTH_LONG).show();
        }


    }
    //khong cho back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            //return false;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
