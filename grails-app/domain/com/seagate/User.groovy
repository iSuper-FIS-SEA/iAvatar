package com.seagate

class User {

  String name
  String email
  //byte[] avatar
  String avatarPath

  static constraints = {
    name blank:false
    email blank:false, unique:true
    //avatar nullable:true
    avatarPath nullable:true
  }
}
