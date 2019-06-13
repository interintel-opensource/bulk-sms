package ke.co.interintel.nikobizzapi.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import ke.co.interintel.nikobizzapi.Constants;
import ke.co.interintel.nikobizzapi.encryption.ApiSecurity;
import ke.co.interintel.nikobizzapi.models.LocationIP;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static ke.co.interintel.nikobizzapi.Nikobizz.JSON;

/**
 * Created by brian on 02/02/2016.
 */
public class ResponseHandler implements Callback {
    public final static String STATUS_SUCCESS = "00";
    private static final String TAG = ResponseHandler.class.getSimpleName();

    private static OkHttpClient client = new OkHttpClient();


    public ResponseHandler() {

    }


    public void post(LocationIP locationIP, String service, JSONObject parameters, String apiKey, Callback callback) {
        if (apiKey == null) {
            Log.e(TAG, "You did not provide a valid api Key, you wont succeed");
        }

        OkHttpClient tempOkHttpClient;
        HttpUrl httpUrl = HttpUrl.parse(Constants.URL);

        try {
            parameters.put("gateway_host", httpUrl.host());
            parameters.put("CHID", String.valueOf(Constants.CHID));

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if (locationIP != null) {
            try {
                parameters.put("ip_address", locationIP.getIp());
                parameters.put("lat", locationIP.getLat());
                parameters.put("lng", locationIP.getLng());
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

        }

        if (httpUrl.host().equals(Constants.MIPAY_HOST)) {
            //Todo tempOkHttpClient = InterintelTrust.getInstance().clientWithOurSocketFactory(client);
            return;
        } else {
            tempOkHttpClient = client;
        }

        //String packagedPayload = ApiSecurity.packagePayload(API_KEY, payload);

        Log.i(TAG, "POST PARAMS :" + parameters.toString());
        RequestBody body = null;
        try {
            body = RequestBody.create(JSON, ApiSecurity.packagePayload(apiKey, parameters).toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String url = httpUrl.scheme() + "://" + httpUrl.host() + "/api/" + service + "/";
        Log.i(TAG, "POST url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        tempOkHttpClient.newCall(request)
                .enqueue(callback);

    }

    public void get(String service, Callback callback) {

        HttpUrl httpUrl = HttpUrl.parse(Constants.URL);

        String url = Constants.URL + service;
        Log.i(TAG, "GET url: " + url);

        if (httpUrl.host().equals(Constants.MIPAY_HOST)) {
            return;
            //todo tempOkHttpClient = InterintelTrust.getInstance().clientWithOurSocketFactory(client);
        } else {
            //client.sslSocketFactory(null);
            client = new OkHttpClient();
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);

    }


    @Override
    public void onFailure(Call call, IOException e) {
        showNetworkErrorMsg(true, null);

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {

        if (response.isSuccessful()) {
            String responseString = response.body().string();


            Log.i(TAG, "\nRESPONSE START ______________________________________\n\n");
            Log.i(TAG, responseString);
            Log.i(TAG, "\n\nRESPONSE END   ____________________________________");

            JSONObject responseData;
            try {
                responseData = new JSONObject(responseString);
                successResponse(responseData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            //throw new IOException("Unexpected code " + response);
            showNetworkErrorMsg(false, response);

            Log.d(TAG, "failure: " + response.message());
        }
    }

    public void successResponse(JSONObject response) {

    }


    protected void showNetworkErrorMsg(final boolean general, final Response response) {

    }

    protected void showResponseMessage(final String message) {

    }

    public static String getResponseMessage(JSONObject responseJson) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject response = responseJson.getJSONObject("response");
            Iterator<String> keys = response.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equals("session") && !key.equals("redirect"))
                    try {
                        sb.append(key.replace("_", " ").toUpperCase() + ": " + response.getString(key) + "\n");
                    } catch (Exception e) {
                        //Log.d(TAG, Constants.DEBUG_ERROR, e);
                    }
            }
        } catch (Exception e) {
            //Log.d(TAG, Constants.DEBUG_ERROR, e);
        }
        if (sb.length() == 0)
            return "Api Response Error";

        return sb.toString();
    }
}
