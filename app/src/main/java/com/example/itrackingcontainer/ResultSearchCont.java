package com.example.itrackingcontainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchCont extends AppCompatActivity {
    ListView lvContainer;
    List<Container> containerList;
    AdapterContainer adapterContainer;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Container container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search_cont);
        setTitle(R.string.result_str_cont);
        lvContainer=findViewById(R.id.lvResultSearchCont);
        final ImageView imgback=findViewById(R.id.btnBackhome);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"OK back",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getBaseContext(),TrackingContainer.class);
                startActivity(intent);
                // Set the button not-clickable..
                imgback.setEnabled(false);

                // Then re-enable it after 5 seconds..
                final Runnable enableButton = new Runnable() {
                    @Override
                    public void run() {
                        imgback.setEnabled(true);
                    }
                };

                new Handler().postDelayed(enableButton, 5000);
            }
        });
        containerList= new ArrayList<>();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor=sharedPreferences.edit();
        containerList=null;
        Intent intent=getIntent();
        container=(Container) intent.getSerializableExtra("objCont");
        containerList.add(container);
        adapterContainer= new AdapterContainer(containerList);
        lvContainer.setAdapter(adapterContainer);
        //Toast.makeText(getBaseContext(),container.getID(),Toast.LENGTH_LONG).show();
    }
}
