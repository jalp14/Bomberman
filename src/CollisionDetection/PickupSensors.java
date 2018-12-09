/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import Sprite.Bomberman;
import TimeManagement.TimerMan;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;

/**
 *
 * @author jalpd
 */
public class PickupSensors implements SensorListener {

    private Bomberman bomberman;
    private TimerMan timerMan;

    public PickupSensors(Bomberman bomberman) {
        this.bomberman = bomberman;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() == bomberman) {
            System.out.println("Item Picked up");
            bomberman.setBombermanSpeed(bomberman.getBombermanSpeed() * 2);
            bomberman.setBombermanJumpPower(bomberman.getBombermanJumpPower() * 2);
            e.getSensor().getBody().destroy();
        }
    }

    @Override
    public void endContact(SensorEvent e) {

    }

}
