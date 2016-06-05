package cr.ac.itcr.transactionapp.entity;

/**
 * Created by car_e on 6/4/2016.
 */
public class User {

    private int id;
    private String user;
    private String email;
    private String password;
    private int debit;

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
}
