package cn.mandata.react_native_voise.voise;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/1/8.
 */
public class SpeechRecognitionListener implements RecognitionListener {
    private ThemedReactContext context;
    private  ReactViewGroup view;
    public SpeechRecognitionListener(ReactViewGroup view, ThemedReactContext context)
    {
        this.view=view;
        this.context=context;
    }
    @Override
    public void onReadyForSpeech(Bundle bundle) {
        WritableMap data=Arguments.createMap();
        this.emitEvent("topReadyForSpeech",data );
    }

    @Override
    public void onBeginningOfSpeech() {
        WritableMap data=Arguments.createMap();
        this.emitEvent("topBeginningOfSpeech",data );
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        WritableMap data=Arguments.createMap();
        data.putDouble("rms",rmsdB);
        this.emitEvent("topRmsChanged",data );
    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        WritableMap data=Arguments.createMap();
        this.emitEvent("topEndOfSpeech",data );
    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
        WritableMap data=Arguments.createMap();
        data.putString("error",sb.toString());
        this.emitEvent("topError",data );
    }

    @Override
    public void onResults(Bundle results) {
        //long end2finish = System.currentTimeMillis() - speechEndTime;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //print("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        try {
            print("origin_result=\n" + new JSONObject(json_res).toString(4));
        } catch (Exception e) {
            print("origin_result=[warning: bad json]\n" + json_res);
        }
        WritableMap data=Arguments.createMap();
        data.putArray("results",Arguments.fromArray(nbest));
        this.emitEvent("topResults",data);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//        if (nbest.size() > 0) {
//            print("~临时识别结果：" + Arrays.toString(nbest.toArray(new String[0])));
//        }
        WritableMap data=Arguments.createMap();
        data.putArray("results",Arguments.fromArray(nbest));
        this.emitEvent("topPartialResults",data);
    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public void print(String msg){

    }
    private void emitEvent(String eventName, WritableMap data) {
        ReactContext reactContext = (ReactContext) this.view.getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                this.view.getId(),
                eventName,
                data);
    }
}
