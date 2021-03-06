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
import cr.ac.itcr.transactionapp.entity.User;

/**
 * Class with functions about User
 */
public class UserApiService implements IApi<User> {
    /**
     * Function that save a user in the data base
     * @param user
     * @return
     */
    @Override
    public boolean Save(User user) throws ExecutionException, InterruptedException {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("user", user.getUser());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("debit", user.getDebit());

            ApiServicePost userPostService = new ApiServicePost();

            userPostService.execute(ConstantApi.url + ConstantApi.user + "/add", "/add", String.valueOf(jsonObject));

            try {
                return userPostService.get();
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

    /**
     * Function that update a user of the data base
     * @param user
     * @return boolean
     */
    @Override
    public boolean Update(User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", user.getId());
            jsonObject.put("user", user.getUser());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("debit", user.getDebit());

            ApiServicePost userPostService = new ApiServicePost();

            userPostService.execute(ConstantApi.url + ConstantApi.user + "/update", "/update", String.valueOf(jsonObject));

            try {
                return userPostService.get();
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

    /**
     * Function that delete a user in the data base
     * @param user
     * @return
     */
    @Override
    public boolean Delete(User user) {

        ApiServiceDelete userDeleteService = new ApiServiceDelete();

        userDeleteService.execute(ConstantApi.url + ConstantApi.user + "/delete/" + String.valueOf(user.getId()), "/delete");

        try {
            return userDeleteService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Function that get all users of the data base
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public ArrayList<User> GetAll() throws ExecutionException, InterruptedException {

        ApiServiceGet userGetService = new ApiServiceGet();

        userGetService.execute(ConstantApi.url + ConstantApi.user + "/all", "/all");

        try {
            return userGetService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean ChangeState(User user) {
        return false;
    }

    /**
     * Function that get a user find by id
     * @param userId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public ArrayList<User> GetUser(int userId) throws ExecutionException, InterruptedException {

        ApiServiceGet userGetService = new ApiServiceGet();

        userGetService.execute(ConstantApi.url + ConstantApi.user + "/get/"+ String.valueOf(userId), "/get");
        String g = ConstantApi.url + ConstantApi.user + "/get/"+ String.valueOf(userId);
        Log.d("url", g);
        try {
            return userGetService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public class ApiServiceGet extends AsyncTask<String,Void,ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(String... params) {

            String cadena = params[0];
            URL url;
            ArrayList<User> users = new ArrayList<>();

            try {
                //if (params[1] == "/all") {
                    url = new URL(cadena);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");

                    int responseCode = connection.getResponseCode();
                    Log.d("code", String.valueOf(responseCode + cadena));
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

                            User user = new User();
                            user.setId(jsonObject.getInt("id"));
                            user.setUser(jsonObject.getString("user"));
                            user.setEmail(jsonObject.getString("email"));
                            user.setPassword(jsonObject.getString("password"));
                            user.setDebit(jsonObject.getInt("debit"));
                            users.add(user);
                        }
                    }
                //}
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
            return users;
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
                url = new URL(params[0]);
                JSONObject user = new JSONObject(params[2]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0" +
                        " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(user.toString());

                writer.flush();
                writer.close();

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

    public class ApiServiceDelete extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            URL url;

            try {
                url = new URL(params[0]);

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
