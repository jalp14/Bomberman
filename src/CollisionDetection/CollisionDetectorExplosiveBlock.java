/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import SceneSetup.SceneSetup;
import Sprite.Bomberman;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.UserView;

/**
 *
 * @author jalpd
 */
public class CollisionDetectorExplosiveBlock implements CollisionListener {

    private Bomberman bomberman;
    private SceneSetup sceneSetup;

    public CollisionDetectorExplosiveBlock(Bomberman bomberman, SceneSetup sceneSetup) {
        this.bomberman = bomberman;
        this.sceneSetup = sceneSetup;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() == bomberman) {
            System.out.println("Bomberman Collided");
            bomberman.setBombermanHealth(0);
            e.getOtherBody().destroy();
            sceneSetup.updateStatus();
        }
    }
}
