package com.example.zhaoqiang.game2048;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 轩韩子 on 2017/5/20.
 * at 20:
 * 游戏操作
 */

public class GameView extends GridLayout {
    private Card[][] cardMap = new Card[4][4];//记录游戏
    private List<Point> emptyPoint = new ArrayList<>();//空点列表

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        setColumnCount(4);//设置每行四列
        setBackgroundColor(getResources().getColor(R.color.white));

        setOnTouchListener(new OnTouchListener() {
            private float startX, startY;
            private float endX, endY;
            private float offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        endY = event.getY();

                        offsetX = endX - startX;
                        offsetY = endY - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {//水平
                            if (offsetX < -5) {
                                System.out.println("Left");
                                moveLeft();
                            } else if (offsetX > 5) {
                                System.out.println("Right");
                                moveRight();
                            }
                        } else {//数值
                            if (offsetY < -5) {
                                System.out.println("Up");
                                moveUp();
                            } else if (offsetY > 5) {
                                System.out.println("Down");
                                moveDown();
                            }
                        }
                }
                return true;
            }
        });
    }



    private void moveLeft() {
        boolean move = false;

        for (int i = 0; i < 4; i++) {//行循环
            for (int j = 0; j < 4; j++) {//列循环
                for (int y = j + 1; y < 4; y++) {//从当前位置向右扫描
                    if (cardMap[i][y].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() <= 0) {//当前值为0
                            cardMap[i][j].setNumber(cardMap[i][y].getNumber());//空位  左移
                            cardMap[i][y].setNumber(0);//清空
                            y = j + 1;  //避免2 0 2不合并清空
                            move = true;
                        } else if (cardMap[i][j].equals(cardMap[i][y])) { //相等
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);//左移  合并
                            cardMap[i][y].setNumber(0);//清空
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            move = true;
                        }
                    }
                }
            }
        }
        if (move) {
            addRandomNumber();
            checkGame();//游戏结束
        }

    }

    private void moveRight() {
        boolean move = false;

        for (int i = 0; i < 4; i++) {//行循环
            for (int j = 3; j >= 0; j--) {//列循环
                for (int y = j - 1; y >= 0; y--) {//从当前位置向右扫描
                    if (cardMap[i][y].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() <= 0) {//当前值为0
                            cardMap[i][j].setNumber(cardMap[i][y].getNumber());//空位  右移
                            cardMap[i][y].setNumber(0);//清空
                            y = j - 1;  //避免2 0 2不合并清空
                            move = true;
                        } else if (cardMap[i][j].equals(cardMap[i][y])) { //相等
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);//右移  合并
                            cardMap[i][y].setNumber(0);//清空
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());

                            move = true;
                        }
                    }
                }
            }
        }
        if (move) {
            addRandomNumber();
            checkGame();//游戏结束
        }


    }

    private void moveUp() {
        boolean move = false;

        for (int j = 0; j < 4; j++) {//列循环
            for (int i = 0; i < 4; i++) {//行循环
                for (int x = i + 1; x < 4; x++) {//从当前位置向下扫描
                    if (cardMap[x][j].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() <= 0) {//当前值为0
                            cardMap[i][j].setNumber(cardMap[x][j].getNumber());//空位  上移
                            cardMap[x][j].setNumber(0);//清空
                            x = j + 1;  //避免2 0 2不合并清空
                            move = true;
                        } else if (cardMap[i][j].equals(cardMap[x][j])) { //相等
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);//左移 上移
                            cardMap[x][j].setNumber(0);//清空
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            move = true;
                        }
                    }
                }
            }
        }
        if (move) {
            addRandomNumber();
            checkGame();//游戏结束
        }
    }


    private void moveDown() {
        boolean move = false;
        for (int j = 0; j < 4; j++) {//列循环
            for (int i = 3; i >= 0; i--) {//行循环
                for (int x = i - 1; x >= 0; x--) {//从当前位置向上扫描
                    if (cardMap[x][j].getNumber() > 0) {
                        if (cardMap[i][j].getNumber() <= 0) {//当前值为0
                            cardMap[i][j].setNumber(cardMap[x][j].getNumber());//空位  上移
                            cardMap[x][j].setNumber(0);//清空
                            x = j - 1;  //避免2 0 2不合并清空
                            move = true;
                        } else if (cardMap[i][j].equals(cardMap[x][j])) { //相等
                            cardMap[i][j].setNumber(cardMap[i][j].getNumber() * 2);//左移 上移
                            cardMap[x][j].setNumber(0);//清空
                            MainActivity.getMainActivity().addScore(cardMap[i][j].getNumber());
                            move = true;
                        }
                    }
                }


            }
        }
        if (move) {
            addRandomNumber();
            checkGame();//游戏结束
        }

    }


    private void checkGame() {

        boolean complete = true; //默认游戏结束

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardMap[i][j].getNumber() <= 0 ||
                        (i > 0 && cardMap[i][j].equals(cardMap[i - 1][j])) ||
                        (i < 3 && cardMap[i][j].equals(cardMap[i + 1][j])) ||
                        (j > 0 && cardMap[i][j].equals(cardMap[i][j - 1])) ||
                        (j > 3 && cardMap[i][j].equals(cardMap[i][j + 1]))) {
                    complete = false;
                    break;
                }
            }
        }
        if (complete) {
            new AlertDialog.Builder(getContext())
                    .setTitle("2048")
                    .setMessage("游戏结束")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardSize = (Math.min(w, h) - 10) / 4;//计算卡牌尺寸
        addCard(cardSize);

        startGame();//开始游戏
    }

    private void startGame() {
        MainActivity.getMainActivity().clearScore();//开始游戏时把分数清零

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cardMap[i][j].setNumber(0);//清空界面
            }
        }
        addRandomNumber();
        addRandomNumber();
    }

    private void addCard(int cardSize) {
        Card card;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                card = new Card(getContext());
                card.setNumber(0);//生成空点
                addView(card, cardSize, cardSize);
                cardMap[i][j] = card;//添加卡片
            }
        }

    }

    private void addRandomNumber() {
        emptyPoint.clear();//清空

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardMap[i][j].getNumber() <= 0) {//用0表示空点
                    emptyPoint.add(new Point(i, j));
                }
            }
        }
        //随机获取空点
        Point point = emptyPoint.remove((int) Math.random() * emptyPoint.size());//随机获取空点
        cardMap[point.x][point.y].setNumber(Math.random() > 0.1 ? 2 : 4);//按9：1的概率生成2和4
    }

}
