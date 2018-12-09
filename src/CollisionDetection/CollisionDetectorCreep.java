/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import Sprite.Creeper;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

/**
 *
 * @author jalpd
 */
public class CollisionDetectorCreep implements CollisionListener {

    private Creeper creep;
    private boolean counter = false;
    private boolean fromBorder;

    public CollisionDetectorCreep(Creeper creep, boolean fromBorder) {
        this.creep = creep;
        this.fromBorder = fromBorder;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() == creep) {
            if (fromBorder) {
                counter = true;
                System.out.println("Collided with border");
            } else {
                counter = false;
            }
            if (counter == true) {
                creep.moveRight(creep);
                counter = false;
            } else if (counter == false) {
                creep.moveLeft(creep);
                counter = true;
            }

        }
    }
}
