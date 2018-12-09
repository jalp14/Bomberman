/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import Sprite.Bandit;
import Sprite.Bomberman;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;

/**
 *
 * @author jalpd
 */
public class BanditSensor implements SensorListener {
    
    private Bomberman bomberman;
    
    public BanditSensor(Bomberman bomberman){
        this.bomberman = bomberman;
    }
    
    @Override
    public void beginContact(SensorEvent e){
        if (e.getContactBody() == bomberman){
            System.out.println("Bomberman Nearby");
        } 
    }
    
    @Override
    public void endContact(SensorEvent e){
         System.out.println("Bomberman out of area");
        
    }
    
}
