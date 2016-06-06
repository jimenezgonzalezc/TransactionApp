package cr.ac.itcr.transactionapp.api;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by car_e on 6/4/2016.
 */
public interface IApi<Object> {

    public boolean Save(Object object) throws ExecutionException, InterruptedException;

    public boolean Update(Object object);

    public boolean Delete(Object object);

    public ArrayList<Object> GetAll() throws ExecutionException, InterruptedException;

//    public ArrayList<Object> GetBy(Object object);
    public boolean ChangeState(Object object);

}
