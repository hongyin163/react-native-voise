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
  getInitialState:function() {
    return { result:'result' }
  },
  onReceive:function (results) {
    this.setState((state)=>{
      state.result=results[0];
    });
  },
  render: function() {
    return (
      <View style={styles.container}>
        <TitleBar/>
        <View style={{flex:1,padding:10}}> 
          <Text>{this.state.result}</Text>
        </View>
        <BaiduVoise 
          ref={'BaiduVoise'}         
          api_key={API_KEY} 
          secret_key={SECRET_KEY} 
          onReceive={this.onReceive}>     
            <View  style={styles.button}>
              <Text style={styles.btntext}>SingleTap，Speech</Text>
            </View>             
        </BaiduVoise>  
      </View>
    );
  }
});

var styles=StyleSheet.create({
    container:{
        flex:1
    },
    button:{
        height:50,
        justifyContent:'center',
        alignItems: 'center',
        borderTopWidth:2,
        borderTopColor:'#e5e5e5'
    },
    btntext:{
     
    }
});

module.exports = App;