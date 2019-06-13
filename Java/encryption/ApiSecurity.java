package ke.co.interintel.nikobizzapi.encryption;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ApiSecurity {

    public static String TAG = ApiSecurity.class.getSimpleName();

    public static String hmacSHA256(byte[] secret, String message) {
        String hash = null;
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret, "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = new String(Base64.encode(sha256_HMAC.doFinal(message.getBytes()), Base64.DEFAULT));
            //Log.i(TAG, hash);

        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }
        return hash;
    }

    public static JSONObject packagePayload(String secret, JSONObject params) throws JSONException {
        JSONObject paramsJsonObj = new JSONObject();

        List<String> keys = new ArrayList<>();
        JSONArray jArray = params.names();

        for (int i = 0; i < jArray.length(); i++) {
            keys.add(jArray.getString(i));
        }

        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);

        List<String> paramsArray = new ArrayList<>();
        for (String key : keys) {
            if (key.equals("sec_hash") || key.equals("credentials")) continue;
            Object json = params.get(key);
            if (!(json instanceof JSONObject) && !(json instanceof JSONArray)) {

                String value = params.getString(key);
                paramsArray.add(key.toLowerCase() + "=" + value);
            }

        }

        String payload = TextUtils.join("&", paramsArray);
        try {

            byte[] secretKey = Base64.decode(secret, Base64.DEFAULT);
            //Log.i("SECRET KEY",new String(secretKey));
            paramsJsonObj.put("sec_hash", hmacSHA256(secretKey, payload));

            Iterator<String> iter = params.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                paramsJsonObj.put(key.toLowerCase(), params.get(key));
            }
            //Log.d(TAG, paramsJsonObj.getString("sec_hash"));
        } catch (JSONException ex) {

        }

        return paramsJsonObj;
    }
}
