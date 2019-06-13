package ke.co.interintel.nikobizzapi.http;

import org.json.JSONObject;


/**
 * Created by Brian on 02/02/2016.
 */
public class ConsumeServiceHandler extends ResponseHandler {

    private static final String TAG = ConsumeServiceHandler.class.getSimpleName();

    public ConsumeServiceHandler() {
        //super(activity);
    }

    @Override
    public void successResponse(final JSONObject json) {
        super.successResponse(json);
        try {
            if (json.getString("response_status").equals(ResponseHandler.STATUS_SUCCESS)) {

            } else {
                showResponseMessage(getResponseMessage(json));
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.d(TAG, Constants.DEBUG_ERROR, e);
        }

    }

}
