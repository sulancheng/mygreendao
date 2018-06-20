package com.susu.hh.mygreendao.rxandroid;

/**
 * 作者：sucheng on 2018/5/31 14:54
 */
public class RxUtils {
    private Face face;
    public interface Face{
       boolean getoffOron();
    }
    private void setuuiu(){
        throw  new RuntimeException("only shut");
    }
    public void setCanshuInterface(Face face){
        this.face= face;
    }
    public void yewu(){
        if (face!=null) {
            boolean b = face.getoffOron();//开或者关
        }
    }
}
