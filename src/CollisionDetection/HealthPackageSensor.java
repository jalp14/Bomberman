/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import SceneSetup.SceneSetup;
import Sprite.Bomberman;
import Texture.GrassBlock;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;

/**
 *
 * @author jalpd
 */
public class HealthPackageSensor implements SensorListener {

    private Bomberman bomberman;
    private SceneSetup sceneSetup;
    private int healthDifference;

    public HealthPackageSensor(Bomberman bomberman, SceneSetup sceneSetup) {
        this.bomberman = bomberman;
        this.sceneSetup = sceneSetup;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() == bomberman) {
            healthDifference = 100 - bomberman.getBombermanHealth();
            System.out.println("Health Package picked up");
            sceneSetup.setCurrentHealth(bomberman.getBombermanHealth() + healthDifference);
            bomberman.setBombermanHealth(bomberman.getBombermanHealth() + healthDifference);
            System.out.println(bomberman.getBombermanHealth());
            sceneSetup.setBomberman(bomberman);
            sceneSetup.updateStatus();
            e.getSensor().getBody().destroy();
        }
    }

    @Override
    public void endContact(SensorEvent e) {
        sceneSetup.getBomberman().bombermanLowHealth();
    }
}
