package ke.co.interintel.nikobizzapi.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by brian on 8/25/2016.
 */
public class LocationIP {

    private String ip;
    private String lat;
    private String lng;

    public LocationIP(String jsonPayload) throws JSONException {
        this(new JSONObject(jsonPayload));
    }

    public LocationIP(JSONObject jsonObjectPayload) throws JSONException {
        this.setIp(jsonObjectPayload.getString("ip"));
        this.setLat(jsonObjectPayload.getString("lat"));
        this.setLng(jsonObjectPayload.getString("lng"));

    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }


}
