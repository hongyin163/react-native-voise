package cn.mandata.react_native_voise.voise;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Administrator on 2015/12/30.
 */
public class VoiseRecognitionManager extends ViewGroupManager<ReactViewGroup> {
    public static final int COMMAND_SHOW = 0;
    public static final int COMMAND_DISMISS = 1;
    private static final String CLASS_NAME = "VoiseRecognition";
    private ReactViewGroup view=null;
    private BaiduASRDigitalDialog mDialog=null;

    @Override
    public String getName() {
        return CLASS_NAME;
    }

    @Override
    protected ReactViewGroup createViewInstance(ThemedReactContext reactContext) {
        this.view= new ReactViewGroup(reactContext);
        return this.view;
    }

    @Override
    public @Nullable Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "show",
                COMMAND_SHOW,
                "dismiss",
                COMMAND_DISMISS);
    }

    @Override
    public void receiveCommand(
            ReactViewGroup view,
            int commandId,
            @Nullable ReadableArray config) {
            if(commandId==COMMAND_SHOW){
                this.showDialog(view.getContext(),config.getMap(0));
            }else if(commandId==COMMAND_DISMISS){
                this.dismiss();
            }
    }
    //    @Override
//    public Map<String, Object> getConstants() {
//        final Map<String, Object> constants = MapBuilder.newHashMap();
//        constants.put("LANGUAGE_CHINESE", VoiceRecognitionConfig.LANGUAGE_CHINESE);
//        constants.put("LANGUAGE_CANTONESE", VoiceRecognitionConfig.LANGUAGE_CANTONESE);
//        constants.put("LANGUAGE_SICHUAN", VoiceRecognitionConfig.LANGUAGE_SICHUAN);
//        constants.put("LANGUAGE_ENGLISH", VoiceRecognitionConfig.LANGUAGE_ENGLISH);
//        return constants;
//    }
    @ReactMethod
    public void showDialog(Context context,ReadableMap config) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Bundle params=new Bundle();
        String api_key=config.getString("api_key");
        String secret_key=config.getString("secret_key");
        //设置开放平台 API Key
        params.putString(BaiduASRDigitalDialog.PARAM_API_KEY,api_key);
        //设置开放平台 Secret Key
        params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,secret_key);

        //设置识别领域：搜索、输入、地图、音乐……，可选。默认为输入。
        Object PARAM_PROP_VALUE= config.getInt("prop");
        params.putInt( BaiduASRDigitalDialog.PARAM_PROP, PARAM_PROP_VALUE==null?VoiceRecognitionConfig.PROP_INPUT:(Integer) PARAM_PROP_VALUE);

        //设置语种类型：中文普通话，中文粤语，英文，可选。默认为中文普通话
        Object LANGUAGE=config.getString("language");
        params.putString( BaiduASRDigitalDialog.PARAM_LANGUAGE,LANGUAGE==null?VoiceRecognitionConfig.LANGUAGE_CHINESE:LANGUAGE.toString());
        //如果需要语义解析，设置下方参数。领域为输入不支持
        Object PARAM_NLU_ENABLE=config.getBoolean("nlu_enable");
        params.putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE,PARAM_NLU_ENABLE==null?false:(boolean)PARAM_NLU_ENABLE);
        // 设置对话框主题，可选。BaiduASRDigitalDialog 提供了蓝、暗、红、绿、橙四中颜色，每种颜
        //        色又分亮、暗两种色调。共 8 种主题，开发者可以按需选择，取值参考 BaiduASRDigitalDialog 中
        //        前缀为 THEME_的常量。默认为亮蓝色
        Object PARAM_DIALOG_THEME=config.getInt("dialog_theme");
        params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,PARAM_DIALOG_THEME==null?BaiduASRDigitalDialog.THEME_RED_DEEPBG:(Integer) PARAM_DIALOG_THEME);

//
////设置识别领域：搜索、输入、地图、音乐……，可选。默认为输入。
//        params.putInt( BaiduASRDigitalDialog.PARAM_PROP,VoiceRecognitionConfig.PROP_INPUT);
////设置语种类型：中文普通话，中文粤语，英文，可选。默认为中文普通话
//        params.putString( BaiduASRDigitalDialog.PARAM_LANGUAGE,VoiceRecognitionConfig.LANGUAGE_CHINESE);
////如果需要语义解析，设置下方参数。领域为输入不支持
//        params.putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE,false);
//// 设置对话框主题，可选。BaiduASRDigitalDialog 提供了蓝、暗、红、绿、橙四中颜色，每种颜
////        色又分亮、暗两种色调。共 8 种主题，开发者可以按需选择，取值参考 BaiduASRDigitalDialog 中
////        前缀为 THEME_的常量。默认为亮蓝色
//        params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,BaiduASRDigitalDialog.THEME_RED_LIGHTBG);

        params.putBoolean( BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE,true);
        params.putBoolean( BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE,true);
        params.putBoolean( BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE,true);
        mDialog=new BaiduASRDigitalDialog(context,params);
        final ThemedReactContext reactContext=(ThemedReactContext)context;
        final ReactViewGroup tempView=this.view;
        DialogRecognitionListener mRecognitionListener=new DialogRecognitionListener(){

            @Override
            public void onResults(Bundle results){
                ArrayList<String> rs=results !=null?results
                        .getStringArrayList(RESULTS_RECOGNITION):null;
                if(rs!=null){
                    WritableArray params=Arguments.createArray();
                    for (String r : rs) {
                        params.pushString(r);
                    }
//                    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                            .emit("onReceive", params);
                    WritableMap data=Arguments.createMap();
                    data.putArray("result",params);
//                    reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher()
//                            .dispatchEvent(new onReceiveEvent(tempView.getId(), SystemClock.uptimeMillis(),data));
                    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                            tempView.getId(),
                            "topChange",
                            data);
                    //此处处理识别结果，识别结果可能有多个，按置信度从高到低排列，第一个元素是置信度最高的结果。
                }
            }
        };
        mDialog.setDialogRecognitionListener(mRecognitionListener);
        mDialog.show();
    }
    @ReactMethod
    public void dismiss(){
        if(this.mDialog!=null)
            this.mDialog.dismiss();
    }

    /**
     * 得到某个类的静态属性 java反射机制
     *
     * @param className
     * @param fieldName
     * @return
     * @throws Exception
     */
    private Object getStaticProperty(String className, String fieldName)
            throws Exception {
        Class ownerClass = Class.forName(className);

        Field field = ownerClass.getField(fieldName);

        Object property = field.get(ownerClass);

        return property;
    }
}
