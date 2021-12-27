package com.star.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;

public class Asteroid implements Poolable {

    private final Texture texture;
    private Vector2 position;
    private float lineSpeed;
    private float directAngle;
    private float angle;
    private float angleSpeed;
    private boolean isActive;

    public Asteroid() {
        texture = new Texture("asteroid.png");
        lineSpeed = 120;
        angleSpeed = 40.0f;
        init();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 128, position.y - 128, 128, 128,
                256, 256, 1, 1,
                angle, 0, 0, 256, 256, false, false);
    }

    public void update(float dt) {

        position.x += MathUtils.cosDeg(directAngle) * lineSpeed * dt;
        position.y += MathUtils.sinDeg(directAngle) * lineSpeed * dt;
        angle += angleSpeed * dt;
        if (position.y > ScreenManager.SCREEN_HEIGHT + 128 || position.x > ScreenManager.SCREEN_WIDTH + 128) {
            isActive = false;
        }
    }

    private void init() {
        position = new Vector2(MathUtils.random(256, ScreenManager.SCREEN_WIDTH - 256), -256);
        directAngle = MathUtils.random(15, 165);
        angle = 0;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        init();
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isTarget(Vector2 pos) {

        if (pos.x <= position.x + 100 &&
        pos.x >= position.x - 100 &&
        pos.y >= position.y - 100 &&
        pos.y <= position.y + 100)return true;

        return false;
    }
}
