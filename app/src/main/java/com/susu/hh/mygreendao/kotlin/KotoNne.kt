package com.susu.hh.mygreendao.kotlin

import android.util.Log

/**
 * 作者：sucheng on 2018/5/29 11:09
 */
class KotoNne{
    var kotNama:String="44"
    var kotage:Int=29
    fun myPrint(text:String){
        Log.i("kotlinOne",text)
    }

    override fun toString(): String {
        return "KotoNne(kotNama='$kotNama', kotage=$kotage)"
    }

}
