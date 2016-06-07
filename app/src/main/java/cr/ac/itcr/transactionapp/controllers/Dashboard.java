package cr.ac.itcr.transactionapp.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cr.ac.itcr.transactionapp.R;
import cr.ac.itcr.transactionapp.entity.Transaction;
import cr.ac.itcr.transactionapp.entity.User;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentAbout.OnFragmentInteractionListener,
        TransactionListFragment.OnFragmentInteractionListener,
        NewTransactionFragment.OnFragmentInteractionListener,
        ManageTransaction.OnFragmentInteractionListener{
    public static ArrayList<User> userList;
    public static ArrayList<Transaction> transList;
    public static int active_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Get intent data
        active_user_id = getIntent().getIntExtra("active_user",1);
        populateTransList();

        //Place first fragment
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_dashboard, new FragmentAbout());
        tx.commit();
    }

    /**
     * Creates a temporary arraylist of transactions, simulates the access from the api for simple uses.
     */
    public void populateTransList(){
        transList = new ArrayList<>();
        int []amount = {100,200,300,400,500,600,700,800,900,1000};
        Transaction temp ;
        for(int i=0; i<10;i++){
            temp = new Transaction();
            temp.setAmount(amount[i]);
            temp.setDate("Feb 13 2010");
            temp.setId(i);
            temp.setUser_id(1);
            temp.setState(false);
            temp.setType(true);
            transList.add(temp);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_transaction) {
            //Add a new transaction, get FragmentTransaction
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            //Bundle to set params
            Bundle bundle = new Bundle();
            bundle.putInt("active_user", active_user_id);
            //Add fragment new transaction
            Fragment newTransFrag = new NewTransactionFragment();
            newTransFrag.setArguments(bundle);
            tx.replace(R.id.content_dashboard, newTransFrag);
            tx.commit();

        } else if (id == R.id.nav_about) {
            //Add fragment manage transaction
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.content_dashboard, new FragmentAbout());
            tx.commit();
        } else if (id == R.id.nav_sign_out) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            //Disable back button press
            finish();
        }else if(id== R.id.nav_my_transactions){
            //Add a new transaction, get FragmentTransaction
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            //Bundle to set params
            Bundle bundle = new Bundle();
            bundle.putInt("active_user", active_user_id);
            //Fragment to call
            Fragment newTransFrag = new TransactionListFragment();
            newTransFrag.setArguments(bundle);
            tx.replace(R.id.content_dashboard, newTransFrag);
            tx.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
