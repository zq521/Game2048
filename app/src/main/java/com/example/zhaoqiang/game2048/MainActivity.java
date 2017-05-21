package com.example.zhaoqiang.game2048;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private TextView text;
    private int score;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    private static MainActivity mainActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        mainActivity = this;

    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        text.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();

    }

}
