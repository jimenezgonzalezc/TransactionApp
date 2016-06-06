package cr.ac.itcr.transactionapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    // UI references.
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        btnLogin = (Button) findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login()){
                    Bundle b = new Bundle();
                    b.putString("user","user_logged_in_object_here");
                    Log.e("LoginActivity","Good to go");
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
                    //Cant log in
                }else{
                    Log.e("LoginActivity","Cant log in...but go ahead and go??");
                    //Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    //startActivity(intent);
                    //Log In here
                }
            }
        });
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LoginActivity","Going to Register??");
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Validate user login based un username and password.
     * @return true if can log in, false otherwise.
     */
    public boolean login(){
        if( txtPassword.getText().toString().equals("") || txtUsername.getText().toString().equals("")){
            showAlert("Empty","Please fill the empty spaces");
            return false;
        }
        return true;
    }


    public void showAlert(String title,String button){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        // Add the buttons
        builder.setNeutralButton(button, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                Log.e("My Alert worked","Show alert hereon ok");

                // User clicked OK button
            }
        });
        builder.setTitle(title);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }




}

