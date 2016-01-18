# react-native-voise
react-native-voise provide voice recognition service to app,the voice recognition service come from [baidu voise](http://yuyin.baidu.com/ "Baidu Voise") 
###Regist baidu voise account
Go to [Baidu Voise](http://yuyin.baidu.com/ "Baidu Voise"),Regist account for voise service ,and create a app ,then get App ID、 API Key、Secret Key. 
### Installation

```
npm install react-native-voise --save
```

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

* Register Module (in MainActivity.java)

```java
import cn.mandata.react_native_voise.BaiduVoiseLibPackage;  // <--- import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
  ......

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mReactRootView = new ReactRootView(this);

    mReactInstanceManager = ReactInstanceManager.builder()
      .setApplication(getApplication())
      .setBundleAssetName("index.android.bundle")
      .setJSMainModuleName("index.android")
      .addPackage(new MainReactPackage())
      .addPackage(new BaiduVoiseLibPackage()) // <------ add this line to yout MainActivity class
      .setUseDeveloperSupport(BuildConfig.DEBUG)
      .setInitialLifecycleState(LifecycleState.RESUMED)
      .build();

    mReactRootView.startReactApplication(mReactInstanceManager, "AndroidRNSample", null);

    setContentView(mReactRootView);
  }

  ......

}
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
		          onReceive={this.onReceive}>      
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

