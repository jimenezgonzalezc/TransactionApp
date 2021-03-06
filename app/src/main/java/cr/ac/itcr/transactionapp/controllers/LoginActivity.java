package cr.ac.itcr.transactionapp.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import cr.ac.itcr.transactionapp.R;
import cr.ac.itcr.transactionapp.api.UserApiService;
import cr.ac.itcr.transactionapp.entity.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    // UI references.
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnRegister;
    public static ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        btnLogin = (Button) findViewById(R.id.btnSignIn);
        //Populate Arraylist
        try {
            populateArrayList();
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Dashboard.userList = userList;



        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyErrorMessage();
                if (login()) {
                    User logged_in = getUserLogged();
                    if (logged_in != null) {//User logged in successful
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("active_user", logged_in.getId());
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Populate an arrayList for testing
     */
    public void populateArrayList() throws InterruptedException, ExecutionException {
        this.userList = new ArrayList<>();

        UserApiService userGetService = new UserApiService();
        userList = userGetService.GetAll();
    }

    /**
     * Gets the user logged in base on username and password
     * if user doesnt exist, edittext will display error message
     * @return User logged in or null if user couldnt log in
     */
    public User getUserLogged(){
        String username = txtUsername.getText().toString();
        String pass = txtPassword.getText().toString();
        for(int i=0; i<userList.size(); i++){
            if(userList.get(i).getUser().equals(username)){
                if(userList.get(i).getPassword().equals(pass)){
                    return userList.get(i);
                }else{
                    setErrorMessage(txtPassword,"Invalid password");
                    return null;
                }
            }
        }
        setErrorMessage(txtUsername, "Username invalid");
        return null;
    }

    /**
     * Empties all the edittext to no show errors
     */
    public void emptyErrorMessage(){ txtUsername.setError(null); txtPassword.setError(null);}

    /**
     * Sets and error message to a give EditText
     * @param txt EditText to display message
     * @param errorMsg String error message to display
     */
    public void setErrorMessage(EditText txt, String errorMsg){
        txt.setError(errorMsg);
    }

    /**
     * Validate user login based un username and password.
     * @return true if can log in, false otherwise.
     */
    public boolean login(){
        //No empty spaces
        if(txtUsername.getText().toString().equals("")) {
            setErrorMessage(txtUsername, "This field cant be empty");
            return false;
        }
        if(txtPassword.getText().toString().equals("")){
            setErrorMessage(txtPassword,"This field cant be empty");
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

