package cn.mandata.react_native_voise.voise;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by Administrator on 2016/1/8.
 */
public class onReceiveEvent extends Event {
    private WritableMap parameters;

    protected onReceiveEvent(int viewTag, long timestampMs,WritableMap parameters) {
        super(viewTag, timestampMs);
        this.parameters=parameters;
    }
    private String EVENT_NAME="topReceive";
    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), getParameters());
    }

    public WritableMap getParameters() {
        return parameters;
    }
}