package cr.ac.itcr.transactionapp.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cr.ac.itcr.transactionapp.entity.Transaction;
import cr.ac.itcr.transactionapp.*;

/**
 * Created by Mendez Soto on 6/5/2016.
 */
public class TransactionAdapter extends BaseAdapter {
    /**
     * Inflater to inflate the arraylist object
     */
    private LayoutInflater inflater;

    private ArrayList<Transaction> transactionList;
    private Context context;

    public TransactionAdapter(Context context, ArrayList<Transaction> transList){
        this.transactionList = transList;
        this.context = context;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return transactionList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return transactionList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return transactionList.get(position).getId();
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_item,parent,false);
        ImageView img;
        TextView txtType, txtState,txtAmount,txtDate;
        txtType = (TextView)v.findViewById(R.id.txtType);
        txtAmount = (TextView)v.findViewById(R.id.txtAmount);
        txtState= (TextView)v.findViewById(R.id.txtState);
        txtDate = (TextView)v.findViewById(R.id.txtDate);
        img = (ImageView) v.findViewById(R.id.imgItem);//Do whatever with this
        if(transactionList.get(position).getType()){
            txtType.setText("Debit");
        }else{
            txtType.setText("Credit");
        }
        txtAmount.setText(String.valueOf(transactionList.get(position).getAmount()));
        if(transactionList.get(position).isState()){
            txtState.setText("Active");
        }else{
            txtState.setText("Inactive");
        }
        txtDate.setText(transactionList.get(position).getDate());
        return v;
    }
}
