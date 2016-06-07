package cr.ac.itcr.transactionapp.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cr.ac.itcr.transactionapp.R;
import cr.ac.itcr.transactionapp.entity.Transaction;


public class NewTransactionFragment extends Fragment {
    private Spinner spinType;
    private EditText txtAmount;
    private RadioButton radioActive;
    private RadioButton radioNonActive;
    private Button btnCreate;
    private int active_user;
    private OnFragmentInteractionListener mListener;

    public NewTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Set title
        getActivity().setTitle("New Transaction");
        // Inflate the layout for this fragment
        Bundle b = getArguments();
        active_user = b.getInt("active_user");
        View v =  inflater.inflate(R.layout.fragment_new_transaction, container, false);
        btnCreate = (Button)v.findViewById(R.id.btnCreate);
        radioNonActive = (RadioButton)v.findViewById(R.id.radioNotActive);
        radioActive = (RadioButton)v.findViewById(R.id.radioActive);
        txtAmount = (EditText)v.findViewById(R.id.txtAmount);
        spinType = (Spinner)v.findViewById(R.id.spinType);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext().getApplicationContext(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinType.setAdapter(adapter);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set no errors on the edittext
                setNoErrors();
                if(isReady()){
                    Transaction newTrans = new Transaction();
                    newTrans.setAmount(Integer.valueOf(txtAmount.getText().toString()));
                    newTrans.setUser_id(active_user);
                    if(spinType.getSelectedItem().toString().equalsIgnoreCase("Credit")){
                        newTrans.setType(true);
                    }else{
                        newTrans.setType(false);
                    }
                    if(radioActive.isSelected()){
                        newTrans.setState(true);
                    }else{
                        newTrans.setState(false);
                    }
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                    String date = df.format(Calendar.getInstance().getTime());
                    newTrans.setDate(date);
                    //Add to the transaction table
                    //This case list of transactions
                    Dashboard.transList.add(newTrans);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    // Add the buttons
                    builder.setNeutralButton("Continue", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            //Fragment manager to manage a fragment transaction
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            //Fragment to replace
                            Fragment f = new TransactionListFragment();
                            //Prepare bundle to send info the the other fragment
                            Bundle bundle = new Bundle();
                            //Send the position of the list item that has been selected
                            bundle.putInt("active_user",active_user);
                            f.setArguments(bundle);
                            transaction.replace(R.id.content_dashboard, f);
                            //On back then go back to ExamListFragment
                            transaction.addToBackStack(null);
                            //Commit transaction
                            transaction.commit();
                        }
                    });
                    builder.setTitle("Transaction inserted successfully");

                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        return v;
    }

    /**
     * Checks to see if the textfields are empty and if state radiogroup is selected
     * @return true: If can log in, false otherwise
     */
    public boolean isReady(){
        //Empty text amount
        if(txtAmount.getText().length() <= 0){
            txtAmount.setError("Field is empty");
        }
        //Check if radios are checked
        if(radioNonActive.isChecked() || radioActive.isChecked()){
            return true;
        }else{
            showAlert("Status","You must select a status");
            return false;
        }
    }

    public void setNoErrors(){
        txtAmount.setError(null);
    }

    /**
     * Shows an alert dialog with the title and button
     * @param title String: Title of the Alert DIalog
     * @param button String: Button text
     */
    public void showAlert(String title,String button){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
