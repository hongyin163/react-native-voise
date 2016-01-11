package cn.mandata.react_native_voise.voise;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.views.view.ReactViewGroup;

import java.util.Map;

import javax.annotation.Nullable;

import cn.mandata.react_native_voise.R;

/**
 * Created by Administrator on 2016/1/8.
 */
public class SpeechRecognizerManager extends ViewGroupManager<ReactViewGroup> {
    public static final int COMMAND_START = 0;
    public static final int COMMAND_STOP = 1;
    private VoiceRecognitionClient mASREngine;
    private SpeechVoiceRecogListener mListener;
    @Override
    public String getName() {
        return "SpeechRecognizer";
    }

    @Override
    protected ReactViewGroup createViewInstance(ThemedReactContext reactContext) {
        ReactViewGroup view=new ReactViewGroup(reactContext);

        mASREngine = VoiceRecognitionClient.getInstance(reactContext);
        mListener= new SpeechVoiceRecogListener(view,reactContext,mASREngine);
        //mASREngine.setTokenApis(Constants.API_KEY, Constants.SECRET_KEY);
        return view;
    }

    @Override
    public @Nullable
    Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "start",
                COMMAND_START,
                "stop",
                COMMAND_STOP);
    }

    @Override
    public void receiveCommand(
            ReactViewGroup view,
            int commandId,
            @Nullable ReadableArray config) {
        if(commandId==COMMAND_START){
            this.start(view.getContext(),config.getMap(0));
        }else if(commandId==COMMAND_STOP){
            this.stop();
        }
    }
    public boolean start(Context context, ReadableMap input) {
        VoiceRecognitionConfig config = new VoiceRecognitionConfig();
        config.setProp(input.getInt("prop"));
        config.setLanguage(input.getString("language"));
        config.enableVoicePower(input.getBoolean("enableVoicePower")); // 音量反馈。
        if (input.getBoolean("enableBeginSoundEffect")) {
            config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start); // 设置识别开始提示音
        }
        if (input.getBoolean("enableEndSoundEffect")) {
            config.enableEndSoundEffect(R.raw.bdspeech_speech_end); // 设置识别结束提示音
        }
        config.setSampleRate(VoiceRecognitionConfig.SAMPLE_RATE_8K); // 设置采样率,需要与外部音频一致

        String api_key=input.getString("api_key");
        String secret_key=input.getString("secret_key");
        mASREngine.setTokenApis(api_key, secret_key);
        int code = mASREngine.startVoiceRecognition(mListener, config);
        if (code != VoiceRecognitionClient.START_WORK_RESULT_WORKING) {
            //mResult.setText(getString(R.string.error_start, code));
        }

        return code == VoiceRecognitionClient.START_WORK_RESULT_WORKING;
    }
    public void stop(){
        mASREngine.stopVoiceRecognition();
    }
}
