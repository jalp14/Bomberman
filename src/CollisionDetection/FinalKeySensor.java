/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import SceneSetup.SceneSetup;
import Sprite.Bomberman;
import Texture.Door;
import city.cs.engine.BodyImage;
import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;

/**
 *
 * @author jalpd
 */
public class FinalKeySensor implements SensorListener {

    private Bomberman bomberman;
    private Door door;
    private SceneSetup sceneSetup;

    public FinalKeySensor(Bomberman bomberman, SceneSetup sceneSetup, Door door) {
        this.bomberman = bomberman;
        this.sceneSetup = sceneSetup;
        this.door = door;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() == bomberman) {
            System.out.println("Bomberman got the key. Door Unlocked");
            sceneSetup.isKeyObtained = true;
            sceneSetup.isDoorOpen = true;
            door.removeAllImages();
            door.addImage(new BodyImage("payload/Door/Door_Open.png", 1.5f));
            e.getSensor().getBody().destroy();
        }
    }

    @Override
    public void endContact(SensorEvent e) {
    }

}
