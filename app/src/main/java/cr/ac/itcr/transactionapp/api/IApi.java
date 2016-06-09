package cr.ac.itcr.transactionapp.api;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public interface IApi<Object> {

    public boolean Save(Object object) throws ExecutionException, InterruptedException;

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll() throws ExecutionException, InterruptedException;

    public boolean ChangeState(Object object);

}
