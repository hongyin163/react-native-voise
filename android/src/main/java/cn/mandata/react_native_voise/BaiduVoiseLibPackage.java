package cn.mandata.react_native_voise;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.mandata.react_native_voise.voise.SpeechRecognizerManager;
import cn.mandata.react_native_voise.voise.VoiseRecognitionManager;

/**
 * Created by Administrator on 2015/11/22.
 */
public class BaiduVoiseLibPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        //modules.add(new VoiseRecognition(reactContext));
        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new VoiseRecognitionManager(),
                new SpeechRecognizerManager()
        );
    }
}
