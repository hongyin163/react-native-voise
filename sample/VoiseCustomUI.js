var React=require('react-native');
var TitleBar=require('./TitleBar');
var {
  BaiduVoise,
  SpeechRecognizer
}=require('react-native-voise');

var {
  AppRegistry,
  Text,
  View,
  LayoutAnimation,
  StyleSheet,
  PanResponder,
  TouchableWithoutFeedback,
  Dimensions, 
  Animated,
  Navigator,
  TouchableOpacity,
  Dimensions
} = React;
var SCREEN_WIDTH = Dimensions.get('window').width;

var  API_KEY='q0UcNM0glvjekMtBQNWzM92y';
var  SECRET_KEY='8hRsMQCQGNdwqnyF8GkWBgr6WObZFT5l';

var App = React.createClass({
  getInitialState() {
    return {
      result:'',
      status:'',
      value:''
    }
  },
  onChange:function (e) {
   this.setState((state)=>{
      state.result=e.result;
      state.status=e.status;
      state.value=e.value;
    });    
   var v=SCREEN_WIDTH*Number(e.value)/100;
   this._v.setNativeProps({style:{width:v}});
  },
  onFinish:function (e) {
    this.setState((state)=>{
      state.result=e.result;
      state.status=e.status;
      state.value=e.value;
    }); 
    
  },
  onUpdateResults:function (e) {
    this.setState((state)=>{
      state.result=e.result;
      state.status=e.status;
      state.value=e.value;
    }); 
  },
  onStartRecording:function (e) {
    this.setState((state)=>{
      state.result=e.result;
      state.status=e.status;
      state.value=e.value;
    }); 
  },
  render: function() {
    return (
      <View style={styles.container}>
        <TitleBar/>
        
        <View style={{flex:1}}> 
          <Text>
            result:{this.state.result}       
          </Text>         
          <Text>
            status:{this.state.status}
          </Text>         
          <Text>
            value:{this.state.value}
          </Text>             
          <View style={styles.progress}>
              <View ref={(v)=>this._v=v} style={{height:20,width:10,backgroundColor:'red'}}/>
          </View>      
        </View>       
        <SpeechRecognizer 
          ref={'SpeechRecognizer'}
          api_key={API_KEY} 
          secret_key={SECRET_KEY} 
          onChange={this.onChange}
          onStartRecording={this.onStartRecording}
          onFinish={this.onFinish}
          onUpdateResults={this.onUpdateResults}>      
            <View  style={styles.button}>
              <Text style={styles.btntext}>LongTap,Speech</Text>
            </View>    
        </SpeechRecognizer>
      </View>
    );
  }
});
var styles=StyleSheet.create({
    container:{
        flex:1
    },
    box:{
        backgroundColor:'red'
    },
    button:{
        height:50,
        justifyContent:'center',
        alignItems: 'center',
        borderTopWidth:2,
        borderTopColor:'#e5e5e5'
    },
    progress:{
      height:20,
      width:SCREEN_WIDTH,
      backgroundColor:'#e5e5e5',
      flexDirection:'row',
      justifyContent:"flex-start",
      alignItems: 'center',
    },
})

module.exports = App;