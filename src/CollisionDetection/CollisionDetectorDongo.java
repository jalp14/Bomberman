/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import Sprite.Dongo;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

/**
 *
 * @author jalpd
 */
public class CollisionDetectorDongo implements CollisionListener {

    private Dongo dongo;
    private boolean counter = false;
    private boolean fromBorder;

    public CollisionDetectorDongo(Dongo dongo, boolean fromBorder) {
        this.dongo = dongo;
        this.fromBorder = fromBorder;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() == dongo) {
            if (fromBorder) {
                counter = true;
                System.out.println("Dongo collided with border");
            } else {
                counter = false;
            }

            if (counter == true) {
                dongo.moveRight(dongo);
                counter = false;
            } else if (counter == false) {
                dongo.moveLeft(dongo);
                counter = true;
            }
        }
    }
}
