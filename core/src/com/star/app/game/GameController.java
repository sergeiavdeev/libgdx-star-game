package com.star.app.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.screen.ScreenManager;

public class GameController {
    private Background background;
    private AsteroidController asteroidController;
    private BulletController bulletController;
    private ParticleController particleController;
    private ThingController thingController;
    private Hero hero;
    private Vector2 tempVec;

    public ParticleController getParticleController() {
        return particleController;
    }

    public AsteroidController getAsteroidController() {
        return asteroidController;
    }

    public Hero getHero() {
        return hero;
    }

    public Background getBackground() {
        return background;
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public ThingController getThingController() {
        return thingController;
    }

    public GameController() {
        this.background = new Background(this);
        this.hero = new Hero(this);
        this.asteroidController = new AsteroidController(this);
        this.bulletController = new BulletController(this);
        this.particleController = new ParticleController();
        this.thingController = new ThingController();
        this.tempVec = new Vector2();

        for (int i = 0; i < 3; i++) {
            asteroidController.setup(MathUtils.random(0, ScreenManager.SCREEN_WIDTH),
                    MathUtils.random(0, ScreenManager.SCREEN_HEIGHT),
                    MathUtils.random(-200, 200),
                    MathUtils.random(-200, 200), 1.0f);
        }
    }

    public void update(float dt) {
        background.update(dt);
        hero.update(dt);
        asteroidController.update(dt);
        bulletController.update(dt);
        particleController.update(dt);
        thingController.update(dt);
        checkCollisions();
    }

    private void checkCollisions() {
        for (int i = 0; i < asteroidController.getActiveList().size(); i++) {
            Asteroid a = asteroidController.getActiveList().get(i);
            collisionAsteroidHerro(a);
            for (int j = 0; j < bulletController.getActiveList().size(); j++) {
                if(collisionAsteroidBullet(a, bulletController.getActiveList().get(j)))break;
            }
        }

        for (int i = 0; i < thingController.getActiveList().size(); i++) {
            collisionThingHerro(thingController.getActiveList().get(i));
        }
    }

    private void collisionAsteroidHerro(Asteroid asteroid) {

        if (asteroid.getHitArea().overlaps(hero.getHitArea())) {
            float dst = asteroid.getPosition().dst(hero.getPosition());
            float halfOverLen = (asteroid.getHitArea().radius + hero.getHitArea().radius - dst) / 2;
            tempVec.set(hero.getPosition()).sub(asteroid.getPosition()).nor();
            hero.getPosition().mulAdd(tempVec, halfOverLen);
            asteroid.getPosition().mulAdd(tempVec, -halfOverLen);

            float sumScl = hero.getHitArea().radius + asteroid.getHitArea().radius;
            hero.getVelocity().mulAdd(tempVec, asteroid.getHitArea().radius / sumScl * 100);
            asteroid.getVelocity().mulAdd(tempVec, -hero.getHitArea().radius / sumScl * 100);

            if (asteroid.takeDamage(2)) {
                hero.addScore(asteroid.getHpMax() * 50);
            }
            hero.takeDamage(2);
        }
    }

    private boolean collisionAsteroidBullet(Asteroid asteroid, Bullet bullet) {

        if (asteroid.getHitArea().contains(bullet.getPosition())) {

            particleController.setup(bullet.getPosition().x +MathUtils.random(-4, 4), bullet.getPosition().y + MathUtils.random(-4, 4),
                    bullet.getVelocity().x * -0.3f + MathUtils.random(-30, 30), bullet.getVelocity().y * -0.3f + MathUtils.random(-30, 30),
                    0.2f, 2.2f, 1.5f,
                    1.0f, 1.0f, 1.0f, 1,
                    0, 0, 1, 0);

            bullet.deactivate();
            if (asteroid.takeDamage(1)) {
                hero.addScore(asteroid.getHpMax() * 100);
                int chance = MathUtils.random(0,(int)(100 * asteroid.getScale()) - 1);
                if(chance < 10) {
                    thingController.setup(asteroid.getPosition(), asteroid.getVelocity());
                }
            }
            return true;
        }
        return false;
    }

    private void collisionThingHerro(Thing thing) {

        if (thing.getHitArea().overlaps(hero.getHitArea())) {
            thing.deactivate();
            switch (thing.getType()) {
                case HEALTH:
                    hero.addHealth(10);
                    break;
                case BULLET:
                    hero.addBullet(50);
                    break;
                case MONEY:
                    hero.addMoney(5);
                    break;
                default:
                    break;
            }
        }
    }
}
