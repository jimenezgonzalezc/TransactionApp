package cr.ac.itcr.transactionapp.entity;

/**
 * Created by car_e on 6/4/2016.
 */
public class Transaction {
    private int id;
    private int user_id;
    private String date;
    /**
     * The type is either credit(true) or debit (false)
     */
    private boolean type;
    private int amount;
    /**
     * State of the transaction, active (true) or non-active (false)
     */
    private boolean state;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
