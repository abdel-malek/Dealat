package  com.tradinos.core.network;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

public class PhotoMultipartRequest<T> extends TradinosRequest<T> {


    private static final String FILE_PART_NAME = "document";
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();


    /* To hold the parameter name and the File to upload */
    private Map<String,File> fileUploads = new HashMap<String,File>();

    /* To hold the parameter name and the string content to upload */
    private Map<String,String> stringUploads = new HashMap<String,String>();
    Context context ;
    public PhotoMultipartRequest(Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {
        super(context , url , method ,parser,successCallback,faildCallback );

    }

    public void addFileUpload(String param,File file) {
        fileUploads.put(param,file);
        buildMultipartEntity();
    }

    public void addStringUpload(String param,String content) {
        stringUploads.put(param,content);
        buildMultipartEntity(param);
    }

    /**
     * 要上传的文件
     */
    public Map<String,File> getFileUploads() {
        return fileUploads;
    }

    /**
     * 要上传的参数
     */
    public Map<String,String> getStringUploads() {
        return stringUploads;
    }



    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        headers.put("Accept", "application/json");

        return headers;
    }

    private void buildMultipartEntity()
    {
        mBuilder.addBinaryBody(FILE_PART_NAME, fileUploads.get("document"));
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    private void buildMultipartEntity(String key)
    {
        mBuilder.addTextBody(key, stringUploads.get(key), ContentType.APPLICATION_JSON);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType()
    {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            mBuilder.build().writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }
}