/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import Sprite.Bandit;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

/**
 *
 * @author jalpd
 */
public class CollisionDetectorBandit implements CollisionListener {
    
    private Bandit bandit;
    private boolean counter = false;
    private boolean fromBorder;
    
    public CollisionDetectorBandit(Bandit bandit, boolean fromBorder){
        this.bandit = bandit;
        this.fromBorder = fromBorder;
    }
    
    @Override
    public void collide(CollisionEvent e){
        if (e.getOtherBody() == bandit){
            if(fromBorder){
                counter = true;
                System.out.println("Bandit Collision");
            } else 
                counter = false;
            if (counter == true){
                bandit.moveRight(bandit);
            } else if (counter == false){
                bandit.moveLeft(bandit);
                counter = true;
            }
        }
    }
    
}
