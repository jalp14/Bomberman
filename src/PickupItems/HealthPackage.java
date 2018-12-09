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
public class HealthPackage extends StaticBody {

    private Shape healthPackageShape;
    private Fixture healthPackageFixture;

    private static final BodyImage healthPackageImage = new BodyImage("payload/Powerups/health_package.png");

    public HealthPackage(World world) {
        super(world);
        healthPackageShape = new BoxShape(0.5f, 0.5f);
        healthPackageFixture = new GhostlyFixture(this, healthPackageShape);
    }

    public Shape getShape() {
        return healthPackageShape;
    }

}
