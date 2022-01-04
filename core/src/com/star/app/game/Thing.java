package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;
import com.star.app.screen.utils.Assets;

public class Thing implements Poolable {

    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float angle;
    private float rotationSpeed;
    private Circle hitArea;
    Type type;

    private final float RADIUS = 32.0f;
    private final float SIZE = RADIUS * 2;

    public Thing() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        active = false;
        angle = 0;
        texture = Assets.getInstance().getAtlas().findRegion("health");
        rotationSpeed = 0;
        hitArea = new Circle(0, 0, 0);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - RADIUS, position.y - RADIUS, RADIUS, RADIUS,
                SIZE, SIZE, 1, 1,
                angle);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        angle += rotationSpeed * dt;
        checkPosition();
        hitArea.setPosition(position);
    }

    private void checkPosition() {

        if (position.x < -RADIUS) {
            position.x = ScreenManager.SCREEN_WIDTH + RADIUS;
        }
        if (position.y < -RADIUS) {
            position.y = ScreenManager.SCREEN_HEIGHT + RADIUS;
        }
        if (position.x > ScreenManager.SCREEN_WIDTH + RADIUS) {
            position.x = -RADIUS;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT + RADIUS) {
            position.y = -RADIUS;
        }
    }

    public void activate(Vector2 position, Vector2 velocity) {
        this.position.set(position);
        this.velocity.set(velocity);
        angle = MathUtils.random(0.0f, 360.0f);
        rotationSpeed = MathUtils.random(-180.0f, 180.0f);
        hitArea.setPosition(position);
        hitArea.setRadius(RADIUS);

        int t = MathUtils.random(0, 2);
        if (t == 0) {
            this.type = Type.HEALTH;
        } else if (t == 1) {
            this.type = Type.BULLET;
        } else {
            this.type = Type.MONEY;
        }
        this.texture = Assets.getInstance().getAtlas().findRegion(type.getTexture());

        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public enum Type {
        HEALTH(0, "health"),
        BULLET(1, "box"),
        MONEY(2, "money");

        private int type;
        private String texture;

        Type(int type, String texture) {
            this.type = type;
            this.texture = texture;
        }

        int getType() {
            return type;
        }

        public String getTexture() {
            return texture;
        }
    }
}
