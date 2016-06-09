package cr.ac.itcr.transactionapp.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import cr.ac.itcr.transactionapp.R;
import cr.ac.itcr.transactionapp.api.TransactionApiService;
import cr.ac.itcr.transactionapp.api.UserApiService;
import cr.ac.itcr.transactionapp.entity.Transaction;
import cr.ac.itcr.transactionapp.entity.User;

public class ManageTransaction extends Fragment {
    private OnFragmentInteractionListener mListener;
    private EditText txtAmount;
    private RadioButton radioActive;
    private RadioButton radioNotActive;
    private ImageButton btnSave;
    private ImageButton btnDelete;
    private Spinner spinType;
    private EditText txtDate;
    private Transaction active_transaction;
    private ArrayList<Transaction> myTrans;

    public ManageTransaction() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Manage Transaction");
        // Inflate the layout for this fragment
        Bundle b = getArguments();
        //Get from API active transaction
        //Get the active transaction
        try {
            active_transaction = getTransObject(b.getInt("active_transaction"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manage_transaction, container, false);
        //Widgets
        spinType = (Spinner)v.findViewById(R.id.spinType);
        txtAmount = (EditText)v.findViewById(R.id.txtAmount);
        radioActive = (RadioButton)v.findViewById(R.id.radioActive);
        radioNotActive = (RadioButton)v.findViewById(R.id.radioNotActive);

        btnDelete = (ImageButton)v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlert();
            }
        });
        btnSave = (ImageButton)v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isReady()){
                    active_transaction.setDate(txtDate.getText().toString());
                    active_transaction.setAmount(Integer.parseInt(txtAmount.getText().toString()));
                    if(radioActive.isChecked())
                        active_transaction.setState(true);
                    else
                        active_transaction.setState(false);
                    if(spinType.getSelectedItem().toString().equalsIgnoreCase("Credit"))
                        active_transaction.setType(true);
                    else active_transaction.setType(false);
                    active_transaction.setUser_id(Dashboard.active_user_id);
                    updateTransaction(active_transaction);
                    Toast.makeText(getActivity().getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });
        txtDate = (EditText)v.findViewById(R.id.txtDate);
        spinType = (Spinner)v.findViewById(R.id.spinType);
        //create adapter for spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext().getApplicationContext(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinType.setAdapter(adapter);
        //Loads the transactions details
        setTransDetail();
        return v;
    }

    public void updateTransaction(Transaction transaction){
        TransactionApiService transactionGetService = new TransactionApiService();
        transactionGetService.Update(transaction);
    }
    /**
     * Checks to see if the textfields are empty and if state radiogroup is selected
     * @return true: If can can edit in, false otherwise
     */
    public boolean isReady(){
        if (txtDate.getText().toString().length() <= 0) {
            setErrorMessage(txtDate, "This field cannot be empty.");
            return false;
        }
        if (txtAmount.getText().toString().length() <= 0) {
            setErrorMessage(txtAmount, "This field cannot be empty.");
            return false;
        }
        try{
            if(Integer.parseInt(txtAmount.getText().toString()) <= 0) {
                setErrorMessage(txtAmount, "This field must be greater than 0");
                return false;
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            setErrorMessage(txtAmount, "This field must be a number");
            return false;
        }
        return true;
    }

    public void setErrorMessage(EditText txt, String errorMsg){
        txt.setError(errorMsg);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Used to populate a users transaction list
     */
    public void getMyTransactions() throws ExecutionException, InterruptedException {
        TransactionApiService transactionGetService = new TransactionApiService();
        myTrans = transactionGetService.GetByUser(Dashboard.active_user_id);
    }

    public Transaction getTransObject(int transId) throws ExecutionException, InterruptedException {
        getMyTransactions();
        for(int i = 0; i < myTrans.size(); i++){
            if(transId==myTrans.get(i).getId()){
                return myTrans.get(i);
            }
        }
        return null;
    }

    public void setTransDetail(){
        txtAmount.setText(String.valueOf(active_transaction.getAmount()));
        txtDate.setText(active_transaction.getDate());
        if(active_transaction.getType()){
            spinType.setSelection(0);//It is credit
        }else spinType.setSelection(1);//It is debit
        if(active_transaction.isState()){//It is active
            radioActive.setChecked(true);
        }else radioNotActive.setChecked(true);
    }


    /**
    **
     * Alert to show in case of deletion
    */
    public void showDeleteAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure you want to delete this transaction?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    //Erase transaction with active_transaction_id
                TransactionApiService transactionGetService = new TransactionApiService();
                transactionGetService.Delete(active_transaction);
                User u = null;
                try {
                    u = getUser();
                    u.setDebit(u.getDebit()+active_transaction.getAmount());
                    updateUser(u);
                    getActivity().onBackPressed();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * Get user from data base
     */
    public User getUser() throws ExecutionException, InterruptedException {
        UserApiService userApiService = new UserApiService();
        ArrayList<User> u = userApiService.GetUser(Dashboard.active_user_id);
        return u.get(0);
    }

    /**
     * Update user in the data base
     */
    public void updateUser(User user) {
        UserApiService userApiService = new UserApiService();
        userApiService.Update(user);
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
