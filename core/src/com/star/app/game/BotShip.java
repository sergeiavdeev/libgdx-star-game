package com.star.app.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;
import com.star.app.screen.utils.Assets;

public class BotShip extends Ship implements Poolable {

    private boolean active;
    protected Circle fireArea;

    public BotShip(GameController gc, int hpMax, float enginePower) {
        super(gc, hpMax, enginePower);

        this.position = new Vector2(-100, -100);
        this.velocity = new Vector2(0, 0);
        this.hitArea = new Circle(position, 29);
        this.texture = Assets.getInstance().getAtlas().findRegion("ship");
        this.fireArea = new Circle(position, 200);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void activate(float x, float y, float vx, float vy) {

        position.set(x, y);
        velocity.set(vx, vy);
        hitArea.setPosition(x, y);
        active = true;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public Circle getFireArea() {
        return fireArea;
    }

    public void update(float dt) {
        super.update(dt);
        fireArea.setPosition(position);
        hitArea.setPosition(position);
    }

    public void deactivate() {
        active = false;
    }

    @Override
    protected void createWeapons() {

        weapons = new Weapon[]{
                new Weapon(gc, this, "Laser", 0.2f, 1, 300.0f, 300,
                        new Vector3[]{
                                new Vector3(28, 90, 0),
                                new Vector3(28, -90, 0)
                        }, true)
        };
    }
}
