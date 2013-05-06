package com.pms.tvdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

public class MyTextView extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView t2 = (TextView) findViewById(R.id.tv02);
        t2.setMovementMethod(LinkMovementMethod.getInstance());//设置TextView可点击，第二种方式实现
        
        TextView t3 = (TextView) findViewById(R.id.tv03);
        t3.setText(
                Html.fromHtml(
                    "<b>text3:</b> " +
                    "<a href=\"http://www.google.com\">链接到google</a> " +
                    "使用HTML在java代码中实现"));
        t3.setMovementMethod(LinkMovementMethod.getInstance());//第三种方式实现
        
        //创建一个 SpannableString对象  
        SpannableString ss = new SpannableString(
        "text4: 点击这里拨打电话，点击这里链接到google");
        
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 6,
               Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置0到6字符为粗体
        ss.setSpan(new URLSpan("tel:4155551212"), 9, 11,
               Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置9到11字符为拨号链接
        ss.setSpan(new URLSpan("http://www.google.com"), 18, 20,  
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置18到20为网站链接
        ss.setSpan(new BackgroundColorSpan(Color.RED), 23 ,29,
        		Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设置  23到29字符为红色高亮

        TextView t4 = (TextView) findViewById(R.id.tv04);
        t4.setText(ss);//SpannableString对象设置给TextView
        t4.setMovementMethod(LinkMovementMethod.getInstance());//第四种实现方式
        
        
        
        
    }
}