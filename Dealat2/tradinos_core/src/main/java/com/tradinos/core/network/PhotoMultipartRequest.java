package com.tradinos.core.network;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;

public class PhotoMultipartRequest<T> extends TradinosRequest<T> {

    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();

    /* To hold the parameter name and the File to upload */
    private Map<String, File> fileUploads = new HashMap<String, File>();

    /* To hold the parameter name and the string content to upload */
    private Map<String, String> stringUploads = new HashMap<String, String>();
    Context context;

    public PhotoMultipartRequest(Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {
        super(context, url, method, parser, successCallback, faildCallback);

    }

    public void addFileUpload(String key, File file) {
        fileUploads.put(key, file);
        buildMultipartEntity(key);
    }

    public void addStringUpload(String param, String content) {
        stringUploads.put(param, content);
        buildStringMultipartEntity(param);
    }

    /**
     * 要上传的文件
     */
    public Map<String, File> getFileUploads() {
        return fileUploads;
    }

    /**
     * 要上传的参数
     */
    public Map<String, String> getStringUploads() {
        return stringUploads;
    }

    private void buildMultipartEntity(String key) {
        mBuilder.addBinaryBody(key, fileUploads.get(key));
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    private void buildStringMultipartEntity(String key) {
        mBuilder.addTextBody(key, stringUploads.get(key), ContentType.APPLICATION_JSON);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType() {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mBuilder.build().writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }
}