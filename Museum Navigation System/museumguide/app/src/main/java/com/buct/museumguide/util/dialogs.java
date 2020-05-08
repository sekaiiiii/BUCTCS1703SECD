package com.buct.museumguide.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.buct.museumguide.MainActivity;

public class dialogs {
    /*特判是否第一次登录*/
    public static void commonDialog(Context context,String title,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);// 设置标题
        // builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setMessage(msg);// 为对话框设置内容
        builder.setCancelable(false);
        // 为对话框设置取消按钮
        builder.setPositiveButton("去登录", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                myToast("您点击了确定按钮",context);
            }
        });
        builder.create().show();// 使用show()方法显示对话框

    }

    public static void myToast(String s,Context context) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
