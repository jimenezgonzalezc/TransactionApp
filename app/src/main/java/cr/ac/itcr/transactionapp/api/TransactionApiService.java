package cr.ac.itcr.transactionapp.api;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.net.ssl.HttpsURLConnection;
import cr.ac.itcr.transactionapp.entity.Transaction;


public class TransactionApiService implements IApi<Transaction> {
    @Override
    public boolean Save(Transaction transaction) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", transaction.getUser_id());
            jsonObject.put("date", transaction.getDate());
            jsonObject.put("type", transaction.isType());
            jsonObject.put("amount", transaction.getAmount());
            jsonObject.put("state", transaction.isState());
            ApiServicePost transactionPostService = new ApiServicePost();

            transactionPostService.execute(ConstantApi.url + ConstantApi.transaction + "/add", "/add", String.valueOf(jsonObject));

            try {
                return transactionPostService.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean Update(Transaction transaction) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", transaction.getId());
            jsonObject.put("user_id", transaction.getUser_id());
            jsonObject.put("date", transaction.getDate());
            jsonObject.put("type", transaction.isType());
            jsonObject.put("amount", transaction.getAmount());
            jsonObject.put("state", transaction.isState());

            ApiServicePost transactionPostService = new ApiServicePost();

            transactionPostService.execute(ConstantApi.url + ConstantApi.transaction + "/update", "/update", String.valueOf(jsonObject));

            try {
                return transactionPostService.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean Delete(Transaction transaction) {

        ApiServiceDelete transactionDeleteService = new ApiServiceDelete();

        transactionDeleteService.execute(ConstantApi.url + ConstantApi.transaction + "/delete/" + String.valueOf(transaction.getId()), "/delete");

        try {
            return transactionDeleteService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Transaction> GetAll() throws ExecutionException, InterruptedException {

        ApiServiceGet transactionGetService = new ApiServiceGet();

        transactionGetService.execute(ConstantApi.url + ConstantApi.transaction + "/all", "/all");

        try {
            return transactionGetService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean ChangeState(Transaction transaction) {
        ApiServicePost transactionChangeStateService = new ApiServicePost();

        transactionChangeStateService.execute(ConstantApi.url + ConstantApi.transaction + "/changeState/" + String.valueOf(transaction.getId()), "/changeState");

        try {
            return transactionChangeStateService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public class ApiServiceGet extends AsyncTask<String,Void,ArrayList<Transaction>> {

        @Override
        protected ArrayList<Transaction> doInBackground(String... params) {

            String cadena = params[0];
            URL url;
            ArrayList<Transaction> transactions = new ArrayList<>();

            try {
                if (params[1] == "/all") {
                    url = new URL(cadena);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");

                    int responseCode = connection.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        JSONArray jsonArray = new JSONArray(result.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Transaction transaction = new Transaction();
                            transaction.setId(jsonObject.getInt("id"));
                            transaction.setId(jsonObject.getInt("user_id"));
                            transaction.setDate(jsonObject.getString("date"));
                            transaction.setType(jsonObject.getBoolean("type"));
                            transaction.setAmount(jsonObject.getInt("amount"));
                            transaction.setState(jsonObject.getBoolean("state"));
                            transactions.add(transaction);
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return transactions;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ApiServicePost extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            URL url;

            try {
                if (params[1] == "/changeState") {
                    url = new URL(params[0]);
                    Log.d("url", String.valueOf(url));

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK)
                        return true;
                    else
                        return false;
                }
                else
                {
                    url = new URL(params[0]);
                    Log.d("url", String.valueOf(url));
                    JSONObject transaction = new JSONObject(params[2]);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestMethod("POST");

                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                    writer.write(transaction.toString());
                    Log.d("Transaction", transaction.toString());

                    writer.flush();
                    writer.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK)
                        return true;
                    else
                        return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ApiServiceDelete extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            URL url;

            try {
                url = new URL(params[0]);
                Log.d("url", String.valueOf(url));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0" +
                        " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("DELETE");
                conn.connect();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                    return true;
                else
                    return false;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
