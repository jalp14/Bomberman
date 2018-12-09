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

/**
 *
 * @author jalpd
 */
public class CollisionDetectorDoor implements CollisionListener {

    private Bomberman bomberman;
    private SceneSetup sceneSetup;

    public CollisionDetectorDoor(Bomberman bomberman, SceneSetup sceneSetup) {
        this.bomberman = bomberman;
        this.sceneSetup = sceneSetup;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() == bomberman) {
            if ((sceneSetup.isKeyObtained == true && sceneSetup.isDoorOpen == true)) {
                System.out.println("Bomber touched the door. End of game. Congratulations!!!!!!");
                sceneSetup.levelFinished();
                sceneSetup.changeLevel();
                e.getReportingBody().destroy();
            }
        }
    }
}
