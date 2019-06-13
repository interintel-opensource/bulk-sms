package ke.co.interintel.nikobizzapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import ke.co.interintel.nikobizzapi.http.GetHostHandler;
import okhttp3.Callback;
import okhttp3.MediaType;


/**
 * Created by Brian on 1/2/2017.
 */

public class Nikobizz {

    private static final String TAG = Nikobizz.class.getSimpleName();

    private String API_KEY;
    private String USERNAME;
    private String PASSWORD;
    private String INSTITUTIONID;


    public static final MediaType JSON
            = MediaType.parse(Constants.MEDIA_TYPE_JSON);

    public Nikobizz(String apiKey, String username, String password, String institutionId) {

        this.API_KEY = apiKey;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.INSTITUTIONID = institutionId;

    }

    public void sendMPESA(String msisdn, String sendAmount, String paymentMethod, String transactionID, String scheduleSend, Callback callback) {

        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new NullPointerException("API KEY cannot be null");
        }


        try {

            JSONObject payload = initPayload();

            JSONObject credentials = new JSONObject();
            credentials.put("username", USERNAME);
            credentials.put("password", PASSWORD);

            payload.put("credentials", credentials);


            payload.put("MSISDN", msisdn);
            payload.put("amount", sendAmount);
            payload.put("currency", "KES");
            payload.put("institution_id", INSTITUTIONID);//Institution ID
            payload.put("product_item_id", "3");//M-PESA Product ID
            payload.put("payment_method", paymentMethod);//Payment M-PESA|MIPAY
            payload.put("ext_outbound_id", transactionID);
            payload.put("scheduled_send", scheduleSend); // d/m/Y H:M (am/pm)


            this.serviceRequest(payload, Constants.SERVICE_REMIT, callback);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void serviceRequest(JSONObject payload, String nextService, Callback callback) {

        //ResponseHandler responseHandler = new ConsumeServiceHandler(callback);

        GetHostHandler getHostHandler = new GetHostHandler(nextService, this.API_KEY, callback, payload);
        getHostHandler.get(Constants.SERVICE_GET_HOST, getHostHandler);
    }

    private JSONObject initPayload() {
        Hashtable<String, String> toReturn = new Hashtable<>();

        toReturn.put("timestamp", String.valueOf(System.currentTimeMillis()));

        return new JSONObject(toReturn);
    }

    public void sendMPESA(String msisdn, String sendAmount, String paymentMethod, Callback callback) {
        sendMPESA(msisdn, sendAmount, paymentMethod, "", "", callback);
    }

}
