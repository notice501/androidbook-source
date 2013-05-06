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
        t2.setMovementMethod(LinkMovementMethod.getInstance());//����TextView�ɵ�����ڶ��ַ�ʽʵ��
        
        TextView t3 = (TextView) findViewById(R.id.tv03);
        t3.setText(
                Html.fromHtml(
                    "<b>text3:</b> " +
                    "<a href=\"http://www.google.com\">���ӵ�google</a> " +
                    "ʹ��HTML��java������ʵ��"));
        t3.setMovementMethod(LinkMovementMethod.getInstance());//�����ַ�ʽʵ��
        
        //����һ�� SpannableString����  
        SpannableString ss = new SpannableString(
        "text4: ������ﲦ��绰������������ӵ�google");
        
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 6,
               Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//����0��6�ַ�Ϊ����
        ss.setSpan(new URLSpan("tel:4155551212"), 9, 11,
               Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//����9��11�ַ�Ϊ��������
        ss.setSpan(new URLSpan("http://www.google.com"), 18, 20,  
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//����18��20Ϊ��վ����
        ss.setSpan(new BackgroundColorSpan(Color.RED), 23 ,29,
        		Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//����  23��29�ַ�Ϊ��ɫ����

        TextView t4 = (TextView) findViewById(R.id.tv04);
        t4.setText(ss);//SpannableString�������ø�TextView
        t4.setMovementMethod(LinkMovementMethod.getInstance());//������ʵ�ַ�ʽ
        
        
        
        
    }
}