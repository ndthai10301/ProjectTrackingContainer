package com.example.itrackingcontainer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText txtUsername,txtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.title_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtUsername=findViewById(R.id.txtUsername);
        txtPassword=findViewById(R.id.txtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.requestFocus();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    if (txtUsername.equals("") || txtPassword.equals("")) {
                        Toast.makeText(getBaseContext(), R.string.str_notify_login, Toast.LENGTH_LONG).show();
                    }
                    if (txtUsername.getText().toString().equals("guest") && txtPassword.getText().toString().equals("guest")) {
                        Intent intent = new Intent(getBaseContext(), TrackingContainer.class);
                        startActivity(intent);
                        // Set the button not-clickable..
                        btnLogin.setEnabled(false);

                        // Then re-enable it after 5 seconds..
                        final Runnable enableButton = new Runnable() {
                            @Override
                            public void run() {
                                btnLogin.setEnabled(true);
                            }
                        };

                        new Handler().postDelayed(enableButton, 5000);

                    } else {
                        //Toast.makeText(getBaseContext(),R.string.str_notify_login_false,Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Login.this)
                                .setTitle(R.string.dialog_notify)
                                .setMessage(R.string.str_notify_login_false)
                                .setCancelable(false)
                                .setPositiveButton(R.string.dialog_try, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(),R.string.str_internet,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
