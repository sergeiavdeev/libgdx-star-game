package com.star.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.star.app.game.GameStatistic;
import com.star.app.screen.utils.Assets;
import org.w3c.dom.events.MouseEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class GameOverScreen extends AbstractScreen{

    private BitmapFont font72;
    private BitmapFont font32;

    private int money;
    private int score;

    public GameOverScreen(SpriteBatch batch) {
        super(batch);
    }

    public void update(float dt) {

        if (Gdx.input.justTouched()) {
            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
        }
    }

    @Override
    public void show() {

        this.font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
        this.font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf");

        money = GameStatistic.getInstance().getMoney();
        score = GameStatistic.getInstance().getScore();
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);
        batch.begin();
        font72.draw(batch, "Game Over", 0, 600, 1280, Align.center, false);
        font32.draw(batch, "Score: " + score, 0, 500, 1280, Align.center, false);
        font32.draw(batch, "Money: " + money, 0, 450, 1280, Align.center, false);
        batch.end();

    }

    @Override
    public void dispose() {

    }
}
