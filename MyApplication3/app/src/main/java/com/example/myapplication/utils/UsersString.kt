package com.example.myapplication.utils

object UsersString {
    public var firstString:String?=null
    public var secondString:String?=null
    public var thirdString:String?=null
    public fun add(userString:String){
        var flag:Int = 1
        if(firstString != null){
            if(secondString != null){
                if(thirdString != null){
                    thirdString = secondString
                    secondString = firstString
                    firstString = userString
                }else{
                    thirdString = userString
                }
            }else{
                secondString = userString
            }
        }else{
            firstString = userString
        }
    }

}
