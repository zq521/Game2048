package com.example.zhaoqiang.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by 轩韩子 on 2017/5/20.
 * at 21:32
 * 卡片
 */

public class Card extends FrameLayout {
    private int number = 0;//保存数字
    private TextView textView;//显示数字

    public Card(Context context) {
        super(context);
        //初始化
        textView = new TextView(getContext());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);//居中
        textView.setBackgroundColor(getResources().getColor(R.color.yellow));
        //添加
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);//设置偏移量
        addView(textView, lp);

        setNumber(0);//初始化数值为0
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if (number>0){
        textView.setText(number + "");
        }else {
            textView.setText("");
        }
    }

    public boolean equals(Card o) {
        return getNumber() == o.getNumber();
    }

}
