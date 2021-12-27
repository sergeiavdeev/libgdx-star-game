package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.star.app.game.helpers.ObjectPool;

public class AsteroidController extends ObjectPool<Asteroid> {
    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public void render(SpriteBatch batch) {

        for(int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void update(float dt) {

        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }

        if (activeList.size() < 2) {
            setup();
        }

        checkPool();
    }

    public void setup() {
        getActiveElement().activate();
    }
}
