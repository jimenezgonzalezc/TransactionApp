package cr.ac.itcr.transactionapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import cr.ac.itcr.transactionapp.R;
import cr.ac.itcr.transactionapp.api.UserApiService;
import cr.ac.itcr.transactionapp.entity.User;

public class RegisterActivity extends AppCompatActivity{
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtEmail;
    private EditText txtDebit;
    private Button btnRegister;
    private ArrayList<User> arrayUsers;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Register");
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword= (EditText)findViewById(R.id.txtPassword);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtDebit = (EditText)findViewById(R.id.txtDebit);
        //Get form API
        //Populate here
        UserApiService userGetService = new UserApiService();
        try {
            arrayUsers = userGetService.GetAll();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //arrayUsers = new ArrayList<>();

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyErrorMessage();
                if (canRegister()) {
                    User user = newUser();
                    try {
                        saveUser(user);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Add new user here man
                    Intent intent = new Intent(RegisterActivity.this, Dashboard.class);
                    Bundle bundle = new Bundle();
                    //Sets the active users id
                    bundle.putInt("active_user", user.getId());
                    //PUt extra params to intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    //Empty spaces exist hoe
                }
            }
        });
    }

    /** Save new user in the data base
     *
     * @param user
     */
    public void saveUser(User user) throws ExecutionException, InterruptedException {
        UserApiService userService = new UserApiService();
        userService.Save(user);
    }

    /**
     * Creates the new user object to add to the arrayList of users and to the database from the api
     * @return User object created
     */
    public User newUser(){
        User new_user = new User();
        new_user.setDebit(Integer.parseInt(txtDebit.getText().toString()));
        new_user.setUser(txtUsername.getText().toString());
        new_user.setEmail(txtEmail.getText().toString());
        new_user.setPassword(txtPassword.getText().toString());
        return new_user;
    }


    /**
     * Checks to see if the fields are empty or not, invoking the checkEmpty() method and checks for username availability
     * @return True if it can register, false otherwise;
     */
    public boolean canRegister(){
        //Check empty text fields
        if(checkEmpty()){//False if tru (empty)
            return false;
        }
        //Check availability for username
        for(int i=0; i<arrayUsers.size(); i++){
            if(arrayUsers.get(i).getUser().equals(txtUsername.getText().toString())){
                setErrorMessage(txtUsername,"Username unavailable");
                return false;
            }
        }
        return true;
    }

    /**
     * Empties all the edittext to no show errors
     */
    public void emptyErrorMessage(){ txtUsername.setError(null); txtPassword.setError(null); txtEmail.setError(null); txtDebit.setError(null);}

    /**
     * Checks to see if the text fields are empty and if the email is a valid email (Contains @)
     * @return True if empty or invalid, false otherwise
     */
    public boolean checkEmpty() {
        if (txtUsername.getText().toString().length() <= 0) {
            setErrorMessage(txtUsername, "This field cannot be empty.");
            return true;
        }
        if (txtPassword.getText().toString().length() <= 0) {
            setErrorMessage(txtPassword, "This field cannot be empty.");
            return true;
        }
        if (txtDebit.getText().toString().length() <= 0) {
            setErrorMessage(txtDebit, "This field cannot be empty.");
            return true;
        }
        try{
            if(Integer.parseInt(txtDebit.getText().toString()) <= 0) {
                setErrorMessage(txtDebit, "This field must be greater");
                return true;
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            setErrorMessage(txtDebit, "This field must be a number");
            return true;
        }
        if (txtEmail.getText().toString().length() <= 0) {
            setErrorMessage(txtEmail, "This field cannot be empty.");
            return true;
        }else if(!txtEmail.getText().toString().contains("a")){
            setErrorMessage(txtEmail, "Invalid email format.");
            return true;
        }
        return false;
    }

    public void setErrorMessage(EditText txt, String errorMsg){
        txt.setError(errorMsg);
    }



}
