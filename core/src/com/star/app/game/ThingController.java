package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.ObjectPool;

public class ThingController extends ObjectPool<Thing> {

    //private GameController gc;

    @Override
    protected Thing newObject() {
        return new Thing();
    }

    public ThingController() {
        //this.gc = gc;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Thing thing = activeList.get(i);
            thing.render(batch);
        }
    }

    public void setup(Vector2 position, Vector2 velocity){
        getActiveElement().activate(position, velocity);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
