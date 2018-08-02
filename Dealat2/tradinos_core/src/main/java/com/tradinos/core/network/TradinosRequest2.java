package com.tradinos.core.network;

import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Farah on 4/22/16.
 */
public class TradinosRequest2<T>  extends  TradinosRequest{

    private SuccessCallback<T> successCallback;
    FaildCallback faildCallback;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private Context context;
    private TradinosParser<T> parser;
    private boolean authenticationRequired = false;

    String url = "";

    public TradinosRequest2(Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {

        super(context,url,method,parser,successCallback,faildCallback);
        this.url = url;

        this.context = context;
        parameters = new HashMap<>();
        headers = new HashMap<>();
        //String lang = Locale.getDefault().getLanguage() ;
        headers.put("Lang","en");
        this.successCallback = successCallback;
        this.faildCallback = faildCallback;
        this.parser = parser;

        if(this.getMethod() == Method.POST)
            this.parser = parser;this.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
           if(volleyError.networkResponse.statusCode == 401){
               return new AuthFailureError() ;
               }
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
           volleyError = error;
            }
       return volleyError;
    }


    public void turnOnAuthentication(String username, String password) {
        try {
            String authenticationValue = "Basic " +
                    String.valueOf(Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP));
            getHeaders().put("Authorization", authenticationValue);
            authenticationRequired = true;
        } catch (Exception e) {
        }
    }

    public void turnOffAuthentication() {
        authenticationRequired = false;
    }

    public void Call() {
        InternetManager.getInstance(getContext()).addToRequestQueue(this, this.url);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            JSONObject json = new JSONObject(data);
            return Response.success(
                    json,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    public void addParameter(String key, String value) {

        parameters.put(key, value);
        if (getMethod() == Method.GET) {
            if (parameters.size() == 0)
                url += "?"  ;
            else
                url += "&";

            url+= key + "=" + value ;

        }

    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        try {
            T result = (T) parser.Parse(jsonObject.toString());
            successCallback.OnSuccess(result);
        } catch (JSONException e) {
            faildCallback.OnFaild(Code.ParsingError, "Parsing Error", "");
        }
    }



    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public void setAuthenticationRequired(boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }
}
