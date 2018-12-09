/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PickupItems;

import CollisionDetection.PickupSensors;
import Sprite.Bomberman;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Fixture;
import city.cs.engine.GhostlyFixture;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 *
 * @author jalpd
 */
public class SpeedPowerUp extends StaticBody {

    private Shape speedUpShape;
    private static final BodyImage speedUpImage = new BodyImage("payload/Powerups/SpeedPowerUp.png", 1.0f);

    private Bomberman bomberman;
    private Fixture speedUpFixture;

    public SpeedPowerUp(World world, Bomberman bombeBombmerman) {
        super(world);
        this.bomberman = bomberman;
        speedUpShape = new BoxShape(0.1f, 0.1f);
        speedUpFixture = new GhostlyFixture(this, speedUpShape);
        addImage(speedUpImage);
    }

    public Shape getShape() {
        return speedUpShape;
    }
}
