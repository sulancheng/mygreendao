package com.susu.hh.mygreendao.downloadutil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.susu.hh.mygreendao.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadActivity extends Activity {
    private Handler mhandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           if (msg.what == 001){
              int positon = msg.arg1;
               ProgressInfo progressInfo = (ProgressInfo) msg.obj;
               Log.i("downloadtest",positon+"  "+progressInfo.toString());
               progressInfos.get(positon).setProgress(progressInfo.getProgress());
//               adressrecyAdapter.pudata(progressInfos);
           }
        }
    };
    private List<ProgressInfo> progressInfos;
    private DownAdapter adressrecyAdapter;
    private DownLoadAdapter downLoadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        RecyclerView recyclerView = findViewById(R.id.rcyc);

        progressInfos = new ArrayList<>();
        for (int i=0;i<100;i++){
            progressInfos.add(new ProgressInfo("下载"+i,i+""));
        }
        adressrecyAdapter = new DownAdapter(progressInfos);
        downLoadAdapter = new DownLoadAdapter(this,progressInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adressrecyAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adressrecyAdapter.isFirstOnly(true);
        //adressrecyAdapter.setNotDoAnimationCount(0);
        adressrecyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i("adressactivitymeide", "onItemChildClick: ");
                ProgressBar progressBar = (ProgressBar) adressrecyAdapter.getViewByPosition(recyclerView,position,R.id.pb_download);
                ProgressInfo thispro = (ProgressInfo) progressInfos.get(position);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sfjsj/demo/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File appfile = new File(file, "5656.flv");
                MyThread myThread = new MyThread("http://111.205.184.233:5011/vod/3277eb803d497878aec4caf6cbeeb836.flv", appfile,position,thispro, new Downpro() {
                    @Override
                    public void jindu(ProgressInfo progressInfo,int positon) {
                        progressInfos.get(positon).setProgress(progressInfo.getProgress());
                        progressBar.setProgress(progressInfo.getProgress());
//                        mhandle.obtainMessage(001,position,0,progressInfo).sendToTarget();
                    }
                });
                myThread.start();
            }
        });


        recyclerView.setAdapter(downLoadAdapter);
        downLoadAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                Log.i("adressactivitymeide", "onItemChildClick: ");
                ProgressInfo thispro = (ProgressInfo) progressInfos.get(position);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/sfjsj/demo/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File appfile = new File(file, "5656.flv");
                MyThread myThread = new MyThread("http://111.205.184.233:5011/vod/3277eb803d497878aec4caf6cbeeb836.flv", appfile,position,thispro, new Downpro() {
                    @Override
                    public void jindu(ProgressInfo progressInfo,int positon) {
                        progressInfos.get(position).setProgress(progressInfo.getProgress());
                        EventBus.getDefault().post(progressInfo);
//                        mhandle.obtainMessage(001,position,0,progressInfo).sendToTarget();
                    }
                });
                myThread.start();
            }
        });

    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface OnItemChildClickListener {
        void onItemChildClick(View view, int position);
    }
    public class DownLoadAdapter extends RecyclerView.Adapter<DownLoadAdapter.MyViewHolder>{
        private List<ProgressInfo> mDatas;
        private Context mContext;
        private LayoutInflater inflater;

        public DownLoadAdapter(Context context, List<ProgressInfo> datas){
            this. mContext=context;
            this. mDatas=datas;
            inflater=LayoutInflater.from(mContext);
        }

        @Override
        public int getItemCount() {

            return mDatas.size();
        }
        private OnItemClickListener mOnItemClickListener;//声明接口
        private OnItemChildClickListener mOnItemChildClickListener;//声明接口

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }
        public void setOnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener) {
            this.mOnItemChildClickListener = mOnItemChildClickListener;
        }
        //填充onCreateViewHolder方法返回的holder中的控件
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            ProgressInfo progressInfo = progressInfos.get(position);
            holder.progressInfo = progressInfo;
            holder.pb_download.setProgress(mDatas.get(position).getProgress());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemChildClickListener != null) {
                        int position = holder.getLayoutPosition();
                        mOnItemChildClickListener.onItemChildClick(holder.button, position);
                    }
                }
            });
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }

        }

        //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout. event_adapter,parent, false);
            MyViewHolder holder= new MyViewHolder(view);
//            View itemView = ((RelativeLayout) holder.itemView).getChildAt(0);
            return holder;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            Button button;
            ProgressBar pb_download;
            ProgressInfo progressInfo;
            public MyViewHolder(View view) {
                super(view);
                //注册事件
                EventBus.getDefault().register(this);
                button = view.findViewById(R.id. bt_down);
                pb_download = view.findViewById(R.id. pb_download);
            }
            @Subscribe(threadMode = ThreadMode.MAIN)
            public void onMoonEvent(ProgressInfo messageEvent){
                if(this.progressInfo == messageEvent){
                    this.pb_download.setProgress(messageEvent.getProgress());
                }
            }
            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                //取消注册事件
                EventBus.getDefault().unregister(this);
            }
        }

    }
    public class DownAdapter extends BaseQuickAdapter<ProgressInfo, BaseViewHolder> {
        public void pudata(List<ProgressInfo> data){
            setNewData(data);
        }
        public DownAdapter(@Nullable List<ProgressInfo> data) {
            super(R.layout.event_adapter, data);

        }
        private BaseViewHolder helper;
        @Override
        protected void convert(BaseViewHolder helper, ProgressInfo item) {
            helper.addOnClickListener(R.id.bt_down);
            this.helper = helper;
            ProgressBar progress = (ProgressBar) helper.getView(R.id.pb_download);
            item.setPosition(helper.getLayoutPosition());
            progress.setProgress(item.getProgress());
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    private class MyThread extends Thread {
        private String url;
        private int position;
        private File file;
        private Downpro inter;
        private ProgressInfo progressInfo;
        private int mLoadedByteLength;
        public MyThread(String url, File file,int position,ProgressInfo progressInfo,Downpro inter) {
            this.url = url;
            this.file = file;
            this.inter = inter;
            this.position = position;
            this.progressInfo = progressInfo;
        }

        @Override
        public void run() {
            try {
                //1.获取服务器地址（URL）
                //String path = "http://192.168.15.28:8080/weixin/2.jpg";
                String path = url;
                URL url = new URL(path);
                //2.建立连接
                HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
                //3设置请求方式和请求时间
                openConnection.setRequestMethod("GET");
                openConnection.setConnectTimeout(5000);
                if(mLoadedByteLength > 0 && mLoadedByteLength < openConnection.getContentLength()){
                    openConnection.setRequestProperty("Range", "bytes=" + mLoadedByteLength + "-");
                }
                //4判断响应
                if (openConnection.getResponseCode() == 200) {
                    //5.响应正确，下载图片（通过流）
                    InputStream inputStream = openConnection.getInputStream();
                    int contentLength = openConnection.getContentLength();
                    //将流变成图片
                    //Bitmap bm = BitmapFactory.decodeStream(inputStream);

                    OutputStream outputStream = new FileOutputStream(file);
                    int len;
                    int count = 0;
                    byte[] arr = new byte[1024];
                    while ((len = inputStream.read(arr)) != -1) {
                        if (this.isInterrupted()) break;
                        outputStream.write(arr, 0, len);
                        mLoadedByteLength+=len;
                        count += len;
                        int aa = (count * 100) / contentLength;
                        Log.i("下载进度", "aa =" + aa);
                        progressInfo.setProgress(aa);
                        if(inter!=null){
                            inter.jindu(progressInfo,this.position);
                        }
                    }
                    if (outputStream != null) {

                        outputStream.close();
                    }
                    if (inputStream != null)
                        inputStream.close();
                    if (!this.isInterrupted()) {
                        Log.e("DownloadTActivity", "下载成功！");
                    }
                }

            } catch (Exception e) {
                Log.e("DownloadTActivity", "下载失败！");
                if (file != null && file.exists()) {
                    file.delete();
                }
                e.printStackTrace();
            }

        }


    }
    public interface Downpro{
        void jindu(ProgressInfo myprogressInfo,int positon);
    }
}
