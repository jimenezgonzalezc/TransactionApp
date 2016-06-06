package cr.ac.itcr.transactionapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cr.ac.itcr.transactionapp.api.TransactionApiService;
import cr.ac.itcr.transactionapp.api.UserApiService;
import cr.ac.itcr.transactionapp.entity.Transaction;
import cr.ac.itcr.transactionapp.entity.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        UserApiService userGetService = new UserApiService();
//        ArrayList<User> listPlaces;
//        try {
//            listPlaces = userGetService.GetAll();
//            Log.d("get all", String.valueOf(listPlaces.size()));
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        TransactionApiService transactionGetService = new TransactionApiService();
        Transaction transaction = new Transaction();
        transaction.setId(5);
        boolean inser = transactionGetService.ChangeState(transaction);
        Log.d("Delete user", String.valueOf(inser));


//        TransactionApiService transactionGetService = new TransactionApiService();
//        Transaction transaction = new Transaction();
//        //transaction.setId(1);
//        transaction.setUser_id(2);
//        transaction.setDate("6/5/2016");
//        transaction.setType(true);
//        transaction.setAmount(1);
//        transaction.setState(true);
//        boolean inser = transactionGetService.Save(transaction);
//        Log.d("Save transaction", String.valueOf(inser));


//        TransactionApiService transactionGetService = new TransactionApiService();
//        Transaction transaction = new Transaction();
//        transaction.setId(1);
//        transaction.setUser_id(1);
//        transaction.setDate("6/5/2016");
//        transaction.setType(true);
//        transaction.setAmount(1000);
//        transaction.setState(true);
//        boolean inser = transactionGetService.Update(transaction);
//        Log.d("update transaction", String.valueOf(inser));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
