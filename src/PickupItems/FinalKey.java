/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PickupItems;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Fixture;
import city.cs.engine.GhostlyFixture;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 *
 * @author jalpd
 */
public class FinalKey extends StaticBody {

    private Shape finalKeyShape;
    private Fixture finalKeyFixture;
    private static final BodyImage finalKeyImage = new BodyImage("payload/Powerups/final_key.png");

    public FinalKey(World world) {
        super(world);
        finalKeyShape = new BoxShape(0.5f, 0.5f);
        finalKeyFixture = new GhostlyFixture(this, finalKeyShape);
    }

    public Shape getShape() {
        return finalKeyShape;
    }
}
