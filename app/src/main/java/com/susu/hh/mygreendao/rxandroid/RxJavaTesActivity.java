package com.susu.hh.mygreendao.rxandroid;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.susu.hh.mygreendao.R;
import com.susu.hh.mygreendao.utils.OkUtils;
import com.susu.hh.mygreendao.widge.MultistageProgress;
import com.susu.hh.mygreendao.widge.ObservableScrollView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RxJavaTesActivity extends AppCompatActivity {

    private Button bt_start;
    private EditText et_et;
    private Button tv_cont;
    private RelativeLayout rl_head;
    private ObservableScrollView sv_sroll;
    private int height;
    private ImageView iv_head;
    private MultistageProgress mupro;
    private EditText et_send;
    private Button bt_send;
    private WebSocketClient webSocketClient;

    //成功
//    通过 subscribeOn 和 observeOn 两个操作符能改变线程的执行状态。
//    subscribeOn 在操作链上最好只调用一次，如果多次调用，依然只有第一次生效
//    subscribeOn 用来指定 observable 在哪个线程上创建执行操作，如果想要通过observables 发射事件给Android的View，那么需要保证订阅者在Android的UI线程上执行操作。
//    另一方面， observeOn 可以在链上调用多次，它主要是用来指定下一个操作在哪一个线程上执行
//    主要用到三种schedulers：
//            Schedulers.io(): 适合I/O类型的操作，比如网络请求，磁盘操作。
//            Schedulers.computation(): 适合计算任务，比如事件循环或者回调处理。
//            AndroidSchedulers.mainThread() : 回调主线程，比如UI操作。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rx_java_tes);
        findb();
        onclick();
        conn();
    }

    private void onclick() {
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_send.getText().toString();
                send(s);
            }
        });
    }

    private Animator.AnimatorListener listener;

    private Observable<String> ddAnimal() {
        return Observable.create(a -> {
            ObjectAnimator animator = ObjectAnimator.ofFloat(iv_head, "rotation", 0, -10, 0, 10, 0);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setInterpolator(new CycleInterpolator(10));
            animator.setDuration(1000);
            animator.start();

            if (listener == null) {
                listener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.setStartDelay(800);
                        animation.start();
                        a.onNext("动画完成了");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
            }
            animator.addListener(listener);
        });

    }

    private void findb() {
        bt_start = findViewById(R.id.bt_start);
        iv_head = findViewById(R.id.iv_head);
//        bt_start.setOnClickListener(v->test4());
        et_et = findViewById(R.id.et_et);
        tv_cont = findViewById(R.id.tv_cont);
        rl_head = findViewById(R.id.rl_head);
        sv_sroll = findViewById(R.id.sv_sroll);
        et_send = findViewById(R.id.et_send);
        bt_send = findViewById(R.id.bt_send);
        mupro = findViewById(R.id.mupro);
        int[] colors = {Color.parseColor("#ff1200"), Color.parseColor("#FF4081"), Color.parseColor("#fff526")};
        float[] weights = {1, 2, 5};
        mupro.setColors(colors, weights);
        rl_head.post(new Runnable() {
            @Override
            public void run() {
                height = rl_head.getHeight();
            }
        });
        rl_head.setAlpha(0);
        sv_sroll.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                Log.i("onScrollChanged", "x=" + x + " ---- y=" + y + " -----oldx=" + oldx + " ---oldy=" + oldy);
                if (y <= height * 5) {
                    float scale = (float) y / (height * 5);
                    float alpha = (10 * scale);
                    Log.i("onScrollChangedch", "scale=" + scale + " ---- alpha=" + alpha + "      heightheight=" + height);
//          Log.i("TAG","alpha--->"+alpha);

                    //layout全部透明
//          layoutHead.setAlpha(scale);

                    //只是layout背景透明(仿知乎滑动效果)
                    rl_head.setAlpha(scale);
                }
            }
        });
        test4();
    }

    public void fas() {
        Integer[] items = new Integer[100];
        for (int x = 0; x < 100; x++) {
            items[x] = x + 1;
        }
        Observable.fromArray(items).subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(a -> concolog(a.toString()))
                .concatMap(a -> speak(a.toString()))
                .doOnError(err -> concolog(err.getMessage() + "erro"))//没有报异常会崩溃
                .subscribe(getObserver());
    }

    public void start() {

        Observable.just(true).
                subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(a -> change())
//                .flatMap(a ->speak("今天RX入门！！！！！"))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(result -> tv_cont.setText(result.toString()))
                .switchMap(resulet -> speak(resulet.toString()))  //使用 switchMap来替代  flatMap， switchMap停止之前发出的请求
                .filter(new io.reactivex.functions.Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return s.toString().contains("s");
                    }
                })
                .doOnNext(resulet -> concolog(resulet.toString()))
                .doOnError(err -> concolog(err.getMessage() + "erro"))//没有报异常会崩溃
                .doOnComplete(() -> concolog("onComplete1"))
                .subscribe(getObserver());
    }

    private Observer<Object> getObserver() {
        return new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                concolog("onNext2" + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                concolog("onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                concolog("onComplete2");
            }
        };
    }


    public void concolog(String text) {
        Log.i("startdayin", text);
    }

    public Observable<String> getDataGet(String data) {
        return OkUtils.checkQuan(this, data);
    }

    @SuppressLint("CheckResult")
    public void test4() {
        RxView.clicks(tv_cont)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe((Object a) -> {
                    ddAnimal()
                            .concatMap(done -> getDataGet(done))
                            .doOnError(erro -> concolog("erro" + erro.getMessage()))
                            .subscribe(getObserver());
                });
//        filt("mydata")
//               .concatMap(data->getDataGet(data))
//               .doOnError(erro->concolog("erro"+erro.getMessage()))
//               .subscribe(getObserver());
    }

    public Observable<String> speak(String speaktext) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (speaktext.contains("100")) {
                    e.onError(new Throwable("包含非法字符"));
                } else {
                    e.onNext(speaktext);
                    e.onComplete();
                }
            }
        }).timeout(5000, MILLISECONDS, Observable.error(new RuntimeException("超时异常")));//没有onComplete抛异常
//        .timeout(1000, TimeUnit.MILLISECONDS, new Observable<String>() {
//            @Override
//            protected void subscribeActual(Observer<? super String> observer) {
//
//            }
//        });
//                .timeout(5000, TimeUnit.MILLISECONDS, new ObservableSource<String>() {
//                    @Override
//                    public void subscribe(Observer<? super String> observer) {
//                        //只要某个地方调用了onComplete就表示结束了
//                        Log.e(TAG,"Send error : " + " and clear cmd queue");
//                    }
//                });
    }

    public Observable<String> filt(String speaktext) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(speaktext);
                e.onComplete();
            }
        });
    }

    public Observable<String> change() {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //3
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    //4
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        e.onNext(s.toString());
                    }
                };

                //5
                et_et.addTextChangedListener(watcher);

                //6
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        et_et.removeTextChangedListener(watcher);
                    }
                });
//                e.onComplete();
                // e.onError(new Throwable("cuolema"));
            }
        }).debounce(1000, MILLISECONDS);
    }

    public void toalert(View view) {
        startActivity(new Intent(RxJavaTesActivity.this, AlertZjActivity.class));
    }

    private Socket socket;

    /**
     * 建立服务端连接
     */
    public void conn() {
//        new Thread() {
//
//            @Override
//            public void run() {
//
//                try {
//                    socket = new Socket("192.168.0.102", 9999);
//                    Log.i("JAVAsocket", "建立连接：" + socket);
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


//        try {
//            WebSocketFactory webSocketFactory = new WebSocketFactory();
//            WebSocketFactory factory = webSocketFactory.setConnectionTimeout(1000);
//
//// Create a WebSocket. The timeout value set above is used.
//            ws = factory.createSocket("ws://192.168.0.102:8081/webSocketServer");
//            ws.addListener(new WebSocketAdapter() {
//                @Override
//                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
//                    super.onConnected(websocket, headers);
//                    Log.i("androidwebsocket","onConnected ");
//                }
//
//                @Override
//                public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
//                    super.onConnectError(websocket, exception);
//                    Log.i("androidwebsocket","onConnectError "+exception.getError());
//                }
//
//                @Override
//                public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
//                    super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
//                    Log.i("androidwebsocket","onDisconnected "+closedByServer);
//                }
//
//                @Override
//                public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
//                    super.onStateChanged(websocket, newState);
//                    Log.i("androidwebsocket","onStateChanged "+newState);
//                }
//
//                @Override
//                public void onTextMessage(WebSocket websocket, String message) throws Exception {
//                    Log.i("android websocket","onTextMessage"+message);
//                }
//
//                @Override
//                public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
//                    super.onError(websocket, cause);
//                    Log.i("androidwebsocket","onError"+cause.getError());
//                }
//            });
//            ws.addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);
//            ws.connect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                URI uri = null;
                try {
                    uri = new URI("ws://192.168.0.102:8081/webSocketServer/syuy");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                webSocketClient = new WebSocketClient(uri) {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        Log.i("androidwebsocket","onOpen"+handshakedata);
                    }

                    @Override
                    public void onMessage(String message) {
                        Log.i("androidwebsocket","onMessage"+message);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.i("androidwebsocket","onClose "+code);
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.i("androidwebsocket","onError"+ex.getMessage());
                    }
                };
                webSocketClient.connect();
            }
        }).start();
    }

    /**
     * 发送消息
     */
    public void send(String txt) {
//        new Thread() {
//            @Override
//            public void run() {
//
//                try {
//                    // socket.getInputStream()
//                    DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
//                    writer.writeUTF(txt); // 写一个UTF-8的信息
//                    Log.i("JAVAsocket", "发送消息");
//                    returnbyserver();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        ws.sendText(txt);
        webSocketClient.send(txt);
    }
    private void returnbyserver(){
        //读取服务端的反馈
        InputStream input = null;
        DataInputStream reader;
        try {
            input = socket.getInputStream();
//            reader = new DataInputStream(input);
//            String msg = reader.readUTF();
            byte[] bts = new byte[1024];
            int len = input.read(bts);
//            int len;
//            StringBuffer result = new StringBuffer();
//            System.out.println("JAVAsocketreturn:" + msg);
//            while ((len = input.read(bts)) != -1) {
                String str = new String(bts, 0, len);
//                Log.i("JAVAsocket1",str);
//                result.append(str);
//            }
            Log.i("JAVAsocketreturn", str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
