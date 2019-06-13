package ke.co.interintel.nikobizzapi.http;

import android.util.Log;

import org.json.JSONObject;

import ke.co.interintel.nikobizzapi.models.LocationIP;
import okhttp3.Callback;


/**
 * Created by brian on 02/02/2016.
 */
public class GetHostHandler extends ResponseHandler {

    private String service;

    private Callback responseHandler;
    private JSONObject parameters;
    private String apiKey;

    private String TAG = GetHostHandler.class.getSimpleName();

    public GetHostHandler(String nextService, String apiKey, Callback responseHandler,
                          JSONObject parameters) {
        //super(activity);
        this.service = nextService;
        this.responseHandler = responseHandler;
        this.parameters = parameters;
        this.apiKey = apiKey;
    }


    @Override
    public void successResponse(JSONObject json) {
        super.successResponse(json);
        try {

            LocationIP locationIP = new LocationIP(json);
            consumeNextService(locationIP);

        } catch (Exception e) {

            showResponseMessage("Api Response Error");
            //Log.d(TAG, Constants.DEBUG_ERROR, e);
        }

    }

    public void consumeNextService(LocationIP locationIP) {
        if (service != null) {
            post(locationIP, service, parameters, apiKey, responseHandler);
        } else {
            Log.i(TAG, "The next service after Getting Host cannot be null");
        }
    }
}
