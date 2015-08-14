package fr.thomasmunoz.myapplication;

import com.loopj.android.http.*;

import org.apache.http.Header;
/**
 * Created by thomasmunoz on 14/08/15.
 */
public class FreeboxTvController
{

    private String controllerId;
    private String baseUrl = "http://hd1.freebox.fr/pub/remote_control";

    public void changeChannel(String channel){
        performAction(channel);
    }

    public void next(){
        performAction("prgm_inc");
    }

    public void previous(){
        performAction("prgm_dec");
    }

    public void setControllerId(String controllerId){
        this.controllerId = controllerId;
    }

    protected void performAction(String key) {
        String path =  baseUrl + "?code=" + controllerId + "&key=" + key;
        System.out.println(path);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(path, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {}

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {}

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {}

            @Override
            public void onRetry(int retryNo) {}
        });
    }

}
