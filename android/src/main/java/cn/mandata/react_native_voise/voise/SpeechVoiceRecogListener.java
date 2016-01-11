package cn.mandata.react_native_voise.voise;

import com.baidu.voicerecognition.android.Candidate;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import java.util.List;

import cn.mandata.react_native_voise.R;

/**
 * Created by Administrator on 2016/1/10.
 */

class SpeechVoiceRecogListener implements VoiceRecognitionClient.VoiceClientStatusChangeListener {
    private ThemedReactContext context;
    private ReactViewGroup view;
    private VoiceRecognitionClient mASREngine;
    public SpeechVoiceRecogListener(ReactViewGroup view, ThemedReactContext context, VoiceRecognitionClient mASREngine)
    {
        this.view=view;
        this.context=context;
        this.mASREngine=mASREngine;
    }
    @Override
    public void onClientStatusChange(int status, Object obj) {
        String result=getResult(obj);
        long vol = mASREngine.getCurrentDBLevelMeter();
        WritableMap data= Arguments.createMap();
        data.putInt("status",status);
        data.putString("result",result);
        data.putString("value", vol+"");
        this.emitEvent("topChange",data);

    }
    private String getResult(Object result) {
        if (result != null && result instanceof List) {
            List results = (List) result;
            if (results.size() > 0) {
                if (results.get(0) instanceof List) {
                    List<List<Candidate>> sentences = (List<List<Candidate>>) result;
                    StringBuffer sb = new StringBuffer();
                    for (List<Candidate> candidates : sentences) {
                        if (candidates != null && candidates.size() > 0) {
                            sb.append(candidates.get(0).getWord());
                        }
                    }
                   return (sb.toString());
                } else {
                    return (results.get(0).toString());
                }
            }
        }
        return "";
    }
    @Override
    public void onError(int errorType, int errorCode) {
        WritableMap data= Arguments.createMap();
        data.putString("error",context.getString(R.string.error_occur,Integer.toHexString(errorCode)));
        this.emitEvent("onError",data);
    }

    @Override
    public void onNetworkStatusChange(int status, Object obj) {
        // 这里不做任何操作不影响简单识别
    }

    private void emitEvent(String eventName, WritableMap data) {
        ReactContext reactContext = (ReactContext) this.view.getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                this.view.getId(),
                eventName,
                data);
    }
}