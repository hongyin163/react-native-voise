// var {
//   PropTypes,
//   UIManager,
//   TouchableOpacity
// } = require('react-native');

var requireNativeComponent = require('requireNativeComponent');
var React = require('React');
var View = require('View');
var PropTypes = require('ReactPropTypes');
var UIManager = require('UIManager');
var TouchableWithoutFeedback=require('TouchableWithoutFeedback');
var VOISE_REF = 'SpeechRecognizer';
var params=require('./Params');

var Status={
  CLIENT_STATUS_START_RECORDING:0,
  CLIENT_STATUS_NONE:1,
  CLIENT_STATUS_SPEECH_START:2,
  CLIENT_STATUS_SPEECH_END:4,
  CLIENT_STATUS_FINISH:5,
  CLIENT_STATUS_PLAY_BEGINE_TONE_START:6,
  CLIENT_STATUS_PLAY_BEGINE_TONE_END:7,
  CLIENT_STATUS_PLAY_END_TONE_START:8,
  CLIENT_STATUS_PLAY_END_TONE_END:9,
  CLIENT_STATUS_UPDATE_RESULTS:10,
  CLIENT_STATUS_AUDIO_DATA:11,
  CLIENT_STATUS_USER_CANCELED:61440,
  CLIENT_STATUS_ERROR:65535
  
}
var reco_result={
  result:'',
  status:'',
  value:''
};
var SpeechComponent = React.createClass({
    propTypes: {
      ...View.propTypes,   
      onStartRecording:PropTypes.func,
      onSpeechStart:PropTypes.func,
      onSpeechEnd:PropTypes.func,
      onFinish:PropTypes.func,
      onUpdateResults:PropTypes.func,
      onUserCanceled:PropTypes.func,
    },
    getDefaultProps: function() {
      return {
        prop:params.PROP_FINANCE,
        language:params.LANGUAGE_CHINESE,
        dialog_theme:params.THEME_RED_DEEPBG,
        nlu_enable:false,
        enableVoicePower:true,
        enableBeginSoundEffect:true,
        enableEndSoundEffect:true

      };
    },
    start: function(callback) {
      var config = {
        api_key: this.props.api_key,
        secret_key: this.props.secret_key,
        prop: this.props.prop,
        language: this.props.language,
        enableVoicePower: this.props.enableVoicePower,
        enableBeginSoundEffect: this.props.enableBeginSoundEffect,
        enableEndSoundEffect: this.props.enableEndSoundEffect,
      };
      UIManager.dispatchViewManagerCommand(
        React.findNodeHandle(this.refs[VOISE_REF]),
        UIManager.SpeechRecognizer.Commands.start,
        [config]);
    },
    stop: function() {
      UIManager.dispatchViewManagerCommand(
        React.findNodeHandle(this.refs[VOISE_REF]),
        UIManager.SpeechRecognizer.Commands.stop,
        []);
    },
    onError: function(e) {
  
      this.props.onError&&this.props.onError(e);
    },
    onChange: function(e) {      

      var status=e.nativeEvent.status;
      switch (status) {
        // 语音识别实际开始，这是真正开始识别的时间点，需在界面提示用户说话。
        case Status.CLIENT_STATUS_START_RECORDING:
            reco_result.status="START_RECORDING";
            reco_result.value=e.nativeEvent.value;
            this.props.onStartRecording&&this.props.onStartRecording(reco_result);
            break;
        case Status.CLIENT_STATUS_SPEECH_START: // 检测到语音起点
            reco_result.status="SPEECH_START";
            this.props.onSpeechStart&&this.props.onSpeechStart(reco_result);
            break;
        // 已经检测到语音终点，等待网络返回
        case Status.CLIENT_STATUS_SPEECH_END:
            reco_result.status="SPEECH_END";
            this.props.onSpeechEnd&&this.props.onSpeechEnd(reco_result);
            break;
        // 语音识别完成，显示reco_result中的结果
        case Status.CLIENT_STATUS_FINISH:
            reco_result.status="FINISH";
            reco_result.result=e.nativeEvent.result;
            reco_result.value=e.nativeEvent.value;
            this.props.onFinish&&this.props.onFinish(reco_result);
            break;
        // 处理连续上屏
        case Status.CLIENT_STATUS_UPDATE_RESULTS:
            reco_result.status="UPDATE_RESULTS";
            reco_result.result=e.nativeEvent.result;
            reco_result.value=e.nativeEvent.value;
            this.props.onUpdateResults&&this.props.onUpdateResults(reco_result);
            break;
        // 用户取消
        case Status.CLIENT_STATUS_USER_CANCELED:
            reco_result.status="USER_CANCELED";
            reco_result.value=e.nativeEvent.value;
            this.props.onUserCanceled&&this.props.onUserCanceled(reco_result);
            break;
        default:
            break;
      }
      this.props.onChange&&this.props.onChange(reco_result);
    },
    onPressIn:function (argument) {
      this.start();
    },
    onPressOut:function (argument) {
      this.stop();
    },
    render: function() {
      return (  
        <TouchableWithoutFeedback           
            onPressIn={this.onPressIn}
            onPressOut={this.onPressOut}>
            <SpeechRecognizer
                ref={VOISE_REF}
                onChange={this.onChange}
                onError={this.onError}     
                style= {this.props.style}> 
              {this.props.children}
            </SpeechRecognizer>
        </TouchableWithoutFeedback>
      );
    },

});


SpeechComponent.Params=params;

var SpeechRecognizer = requireNativeComponent('SpeechRecognizer', SpeechComponent, {
  nativeOnly: {
    onChange: true,
    onError:true
  }
});


module.exports = SpeechComponent;