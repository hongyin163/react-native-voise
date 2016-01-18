/* @flow */
'use strict';

var React = require('react-native');
var Button=require('./Button');
var Dialog=require('./VoiseDialog');
var Tap=require('./VoiseCustomUI');
var {
  StyleSheet,
  View,
  TouchableOpacity,
  Text,
  Navigator,
  NativeAppEventEmitter
} = React;


var Component = React.createClass({
	onBack:function (argument) {
    	this._nav.pop();
  	},
	componentWillMount: function() {
		var me=this;
		NativeAppEventEmitter.addListener('back', this.onBack);
	},
	renderScene:function (route, navigator) {
		switch(route.name){
			case "main":
				return(
					<View style={styles.container}>						
						<Button text={'Dialog Mode'} onPress={this.bindSelect('Dialog')}/>
						<Button text={'Tap Mode'} onPress={this.bindSelect('Tap')}/>
					</View>
	            );
			break;
			case "Dialog":
				return (<Dialog nav={navigator}/>)
			break;		
			case "Tap":
				return (<Tap nav={navigator}/>)
			break;		
		}
	},
	bindSelect:function (name) {
		var me=this;
		return function(){
			me.onSelect(name);
		};
	},
	onSelect:function (name) {
		this._nav.push({name:name})
	},
  	render: function() {
	    return (
	    	<Navigator
	            ref={(n)=>this._nav=n}
	            debugOverlay={false}
	            style={{flex:1}}
	            configureScene={(route) =>Navigator.SceneConfigs.PushFromRight}
	            initialRoute={{name:'main'}}
	            renderScene={this.renderScene}/>

	    );
  	}
});


var styles = StyleSheet.create({
	container:{
		flex:1
	}
});


module.exports = Component;
