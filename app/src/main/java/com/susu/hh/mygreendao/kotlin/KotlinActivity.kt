package com.susu.hh.mygreendao.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.jakewharton.rxbinding2.view.RxView
import com.susu.hh.mygreendao.R
import com.susu.hh.mygreendao.utils.OkUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val arrayList = ArrayList<String>()
        val kotoNne = KotoNne()
        kotoNne.kotNama="suye"
        val listOf = listOf<KotoNne>(KotoNne(),kotoNne)
        listOf.forEach {a->
            Log.i("foreach",a.toString())
        }
        for (index in listOf.indices) {
            Log.i("foreach2","item at $index is ${listOf[index]}")
        }

        arrayList.add("nidhaio")
       var bt_add = findViewById<Button>(R.id.bt_add)
        RxView.clicks(bt_add)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe { a ->
                    getDataGet(KotoNne())
                            .doOnError { erro -> concolog("erro" + erro.message) }
                            .subscribe(getObserver())
                }
    }

    fun filt2(txt: String): Observable<String> {
        return Observable.create { ea: ObservableEmitter<String> ->
            ea.onNext(txt)
            ea.onComplete()
        }
    }

    fun filt(speaktext: String): Observable<String> {
        return Observable.create { e ->
            e.onNext(speaktext)
            e.onComplete()
        }
    }

    fun getDataGet(data: Any): Observable<String> {
        return OkUtils.checkQuan(this, data)
    }

    private fun getObserver(): Observer<Any> {
        return object : Observer<Any> {
            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(@NonNull o: Any) {
                concolog("onNext2$o")
            }

            override fun onError(@NonNull e: Throwable) {
                concolog("onError"+e.message)
            }

            override fun onComplete() {
                concolog("onComplete2")
            }
        }
    }

    fun concolog(ii: String) {
        Log.i("日志", ii)
    }

}
