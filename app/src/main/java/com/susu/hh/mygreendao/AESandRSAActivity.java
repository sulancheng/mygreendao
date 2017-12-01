package com.susu.hh.mygreendao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.susu.hh.mygreendao.utils.AES;

public class AESandRSAActivity extends Activity {
    /**
     * Android端的加密思路需要4步：
     1.生成AES密钥；
     2.使用RSA公钥加密刚刚生成的AES密钥；
     3.再使用第1步生成的AES密钥，通过AES加密需要提交给服务端的数据；
     4.将第2与第3生成的内容传给服务端。

     JAVA服务端的解密思路只需3步：
     1.获取到客户端传过来的AES密钥密文和内容密文；
     2.使用RSA私钥解密从客户端拿到的AES密钥密文；
     3.再使用第2步解密出来的明文密钥，通过AES解密内容的密文。*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aesand_rsa);
        initAES();
    }

    private void initAES() {
        String content = "我是谁，我在那里？";
        String myAEDkey = AES.generateKeyString();
        String jiamihoucontent = AES.encrypt(content, myAEDkey);
        Log.i("AES","AES加密的内容：加密后="+jiamihoucontent);
        String jiemicontent = AES.decrypt(jiamihoucontent, myAEDkey);
        Log.i("AES","AES加密的内容：解密后="+jiemicontent);
    }
}
