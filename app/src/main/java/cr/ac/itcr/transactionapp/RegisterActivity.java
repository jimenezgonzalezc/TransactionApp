package cr.ac.itcr.transactionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import cr.ac.itcr.transactionapp.entity.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtEmail;
    private EditText txtDebit;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword= (EditText)findViewById(R.id.txtPassword);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtDebit = (EditText)findViewById(R.id.txtDebit);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals("") || txtEmail.getText().toString().equals("") || txtDebit.getText().toString().equals("")){
                    Log.e("unable to register...","Im sorry... try again");
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    //Empty spaces exist hoe
                }{
                    Log.e("unable to register...","Im sorry... try again");
                    //Go ahead and log in bitch
                }
            }
        });
    }



}
