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
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cr.ac.itcr.transactionapp.R;
import cr.ac.itcr.transactionapp.adapter.TransactionAdapter;
import cr.ac.itcr.transactionapp.entity.Transaction;

public class TransactionListFragment extends Fragment {
    private ListView listViewTransactions;
    private static TransactionAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Transactions");
        setHasOptionsMenu(true);
        Bundle b = getArguments();
        int user_id = b.getInt("active_user");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        listViewTransactions = (ListView)v.findViewById(R.id.listViewTransactions);
        //Obtain id from bundle
        adapter = new TransactionAdapter(getActivity().getApplicationContext(),getMyTransactions(user_id));
        listViewTransactions.setAdapter(adapter);
        listViewTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment f = new ManageTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("active_transaction",Dashboard.transList.get(position).getId());
                f.setArguments(bundle);
                transaction.replace(R.id.content_dashboard,f);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Used to populate a users transaction list
     * @param user_id int: The id if the user we are gonna get the transactions from
     * @return ArrayList<Transaction> List of transactions, empty if none
     */
    public ArrayList<Transaction> getMyTransactions(int user_id){
        ArrayList<Transaction> myTrans = new ArrayList<>();
        for(int i=0; i<Dashboard.transList.size();i++){
            if (Dashboard.transList.get(i).getUser_id() == user_id){
                myTrans.add(Dashboard.transList.get(i));
            }
        }
        if (myTrans.isEmpty())
            showEmptyDialog();
        return myTrans;
    }

    /**
     * Show a dialog when the exams are empty
     */
    public void showEmptyDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("No transactions");
        alertDialog.setMessage("Do you want to create a new one?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Delete the exam
                //Fragment manager to manage a fragment transaction
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //Fragment to replace
                Fragment f = new NewTransactionFragment();
                transaction.replace(R.id.content_dashboard, f);
                //On back then go back to TransactionList
                transaction.addToBackStack(null);
                //Commit transaction
                transaction.commit();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
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
