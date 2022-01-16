package com.star.app.game;

public class GameStatistic {

    private int score;
    private int money;

    private final static GameStatistic instance = new GameStatistic();

    private GameStatistic() {}

    public static GameStatistic getInstance() {
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
