/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Platform,
  NativeModules,
} from 'react-native';

class datepicker extends Component {

   constructor(props){
     super(props);

     this.state={};

     this.birthday=this.birthday.bind(this);
   }

   birthday(){
     if(Platform.OS=='android'){
       console.log(NativeModules.DatePickerDialog);
       NativeModules.DatePickerDialog.showDatePicker('选择生日',(date)=>{
         this.setState({
           birthday:date,
         });
       });
     }else{
       Alert.alert('暂未开发');
     }
   }

  render() {
    return (
      <View style={styles.container}>

      <TouchableOpacity onPress={this.birthday}>
      <View style={styles.item}>
      <Text style={{width:80,flex:1,marginLeft:20}}>生日</Text>
      {this.state.birthday?
      <Text style={{marginRight:20}}>{this.state.birthday}</Text>
        :null
      }
      </View>
      </TouchableOpacity>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding:20,
    backgroundColor: '#F5FCFF',
  },

  item:{
    flex:1,
    height:50,
    alignItems:'center',
    borderBottomColor:'#ddd',
    borderBottomWidth:1,
    flexDirection:'row',
  },

});

AppRegistry.registerComponent('datepicker', () => datepicker);
