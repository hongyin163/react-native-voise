import React, { PropTypes } from 'react';
import ReactNative, {
  requireNativeComponent,
  View,
  UIManager,
  TouchableOpacity
} from 'react-native';
import params from './Params';

var VOISE_REF = 'baiduVoise';

var BaiduVoise = React.createClass({
    getDefaultProps: function() {
      return {
        prop:params.PROP_FINANCE,
        language:params.LANGUAGE_CHINESE,
        dialog_theme:params.THEME_RED_DEEPBG,
        nlu_enable:false,
      };
    },
    propTypes: {
      ...View.propTypes,
      api_key: PropTypes.string,
      secret_key: PropTypes.string,
      prop: PropTypes.number,
      language: PropTypes.string,
      dialog_theme: PropTypes.number,
    },
    show: function(callback) {
      var config = {
        api_key: this.props.api_key,
        secret_key: this.props.secret_key,
        prop: this.props.prop,
        language: this.props.language,
        dialog_theme: this.props.dialog_theme,
        nlu_enable: this.props.nlu_enable
      };
      //RCTVoiseRecognition.showDialog(config);
      UIManager.dispatchViewManagerCommand(
        ReactNative.findNodeHandle(this.refs[VOISE_REF]),
        UIManager.VoiseRecognition.Commands.show,
        [config]);
    },
    hide: function() {
      UIManager.dispatchViewManagerCommand(
        ReactNative.findNodeHandle(this.refs[VOISE_REF]),
        UIManager.VoiseRecognition.Commands.dismiss,
        []);
    },
    onReceive: function(e) {
      var result=e.nativeEvent.result;
      if(this.props.nlu_enable){
        var str=e.nativeEvent.result;
        var obj=JSON.parse(str);
        result=obj.item;
      }
      this.props.onReceive&&this.props.onReceive(result);
    },
    onPress:function (argument) {
      var me=this
      me.show();
    },
    render: function() {
      return (  
        <TouchableOpacity
            activeOpacity ={0.5}       
            underlayColor="#B5B5B5"
            onPress={this.onPress}>
            <VoiseRecognition
              ref={VOISE_REF}
              onChange={this.onReceive}
              style= {this.props.style}> 
              {this.props.children}
            </VoiseRecognition>
        </TouchableOpacity>
      );
    },

});

BaiduVoise.Params=params;

var VoiseRecognition = requireNativeComponent('VoiseRecognition', BaiduVoise, {
  nativeOnly: {
    onChange: true
  }
});

module.exports = BaiduVoise;
