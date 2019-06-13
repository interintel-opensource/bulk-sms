package ke.co.interintel.nikobizzapi;

/**
 * Created by Brian on 02/02/2016.
 */
public class Constants {
    public final static String SERVICE_GET_HOST = "get-my-host/";
    public final static String SERVICE_HOME = "HOME";
    public final static String SERVICE_INSTITUTION_PAGE = "INSTITUTION PAGE";
    public final static String SERVICE_LOGIN = "LOGIN";
    public final static String MIPAY_HOST = "mipaymobile.com";

    //Gateway Variables (Change to configure a diffferent gateway/Institution) //

    //public final static String GATEWAY_HOST = "bidfather.com";
    //public final static String GATEWAY_HOST = "mchaama.com";
    //public final static String GATEWAY_HOST = "shikangoma.com";
    public final static String GATEWAY_HOST = "nikobizz.com";
    //public final static String GATEWAY_HOST = "mipaymobile.com";
    //public final static String GATEWAY_HOST = "muziqbit.com";

    public static final boolean IS_INSTITUTION = false;
    public static final String INSTITUTION_ID = "35";
    //End gateway variables

    public final static String URL = "https://" + GATEWAY_HOST + "/api/";


    public static final int CHID = 1;
    public static final String SERVICE_REMIT = "REMIT";


    public static String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
}
