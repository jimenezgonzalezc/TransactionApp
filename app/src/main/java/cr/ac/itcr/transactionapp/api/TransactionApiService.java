package cr.ac.itcr.transactionapp.api;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import cr.ac.itcr.transactionapp.entity.Transaction;

/**
 * Created by car_e on 6/4/2016.
 */
public class TransactionApiService implements IApi<Transaction> {
    @Override
    public boolean Save(Transaction transaction) {
        return false;
    }

    @Override
    public boolean Update(Transaction transaction) {
        return false;
    }

    @Override
    public boolean Delete(Transaction transaction) {
        return false;
    }

    @Override
    public ArrayList<Transaction> GetAll() throws ExecutionException, InterruptedException {
        return null;
    }
}
