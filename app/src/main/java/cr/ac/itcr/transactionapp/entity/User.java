package cr.ac.itcr.transactionapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String user;
    private String email;
    private String password;
    private int debit;

    public User(int id, String username, String email, String password, int debit){
        this.id = id;
        this.user = username;
        this.email = email;
        this.password = password;
        this.debit = debit;
    }

    //Empty Constructor
    public User(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(debit);
        dest.writeString(user);
        dest.writeString(password);
        dest.writeString(email);
    }
}
