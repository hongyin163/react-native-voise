# react-native-voise
react-native-voise provide voice recognition service to app,the voice recognition service come from [baidu voise](http://yuyin.baidu.com/ "Baidu Voise") 
###Regist baidu voise account
Go to [Baidu Voise](http://yuyin.baidu.com/ "Baidu Voise"),Regist account for voise service ,and create a app ,then get App ID、 API Key、Secret Key. 

### Installation

```
npm install react-native-voise --save
```

### Download and import SDK

[Download SDK](http://bos.nj.bpc.baidu.com/v1/audio/Baidu-Voice-SDK-Android-1.6.2.zip)

In the `android` floder create a new folder named `libs`, and then put the `*.jar` file in the SDK into `libs`.

In the `android/src/main` floder create a new folder named `jniLibs`, and then put `x86`, `mips`, `aremabi` into `jniLibs`.

### Add it to your android project

* In `android/setting.gradle`

```gradle
include ':react-native-voise'
project(':react-native-voise').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-voise/android')
```

* In `android/app/build.gradle`

```gradle
...
dependencies {
  ...
  compile project(':react-native-voise')
}
```

* Register Module (in `MainApplication.java`)

```java
import cn.mandata.react_native_voise.BaiduVoiseLibPackage;  // <--- import

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    ......

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new BaiduVoiseLibPackage()  // <------ add this line to yout MainApplication class
      );
    }
  };

  ......
}
```
## Modify AndroidManifest.xml

Add user permission

```
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

```

## Example
```javascript
/* @flow */
'use strict';

var React = require('react-native');
var {
  BaiduVoise,
  SpeechRecognizer
}=require('react-native-voise');

var {
  StyleSheet,
  View,
  Text
} = React;

var Component = React.createClass({
	getInitialState() {
    	return { result:'' }
  	},
	onReceive:function (results) {
		//results is a list ,the first one is the best result.
	    this.setState((state)=>{
	      state.result=results[0];
	    });
	},
	render: function() {
		return (
			<View style={styles.container}>
				<Text>this.state.result</Text>
				<BaiduVoise 
		          ref={'BaiduVoise'}
		          style={styles.button}
		          api_key={'q0UcNM0glvjekMtBQNWzM92y'} 
		          secret_key={'8hRsMQCQGNdwqnyF8GkWBgr6WObZFT5l'} 
		          onReceive={this.onReceive.bind(this)}>      
		            <Text>点击，说话</Text>
		        </BaiduVoise>
			</View>
		);
	}
});

var styles = StyleSheet.create({
	container:{
		flex:1
	},
 	button:{
        height:50,
    }
});


module.exports = Component;

```

![](https://github.com/hongyin163/react-native-voise/blob/master/sample/voisedemo0.gif?raw=true)

