/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprite;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

/**
 *
 * @author jalpd
 */
public class Dongo extends Walker {

    private Vec2 posVelocity;
    private Vec2 negVelocity;

    private static final Shape dongoShape = new PolygonShape(-0.547f, 0.726f, 0.584f, 0.737f, 0.625f, -0.45f, 0.33f, -0.739f, -0.469f, -0.741f, -0.621f, -0.398f, -0.547f, 0.726f);
    private static final BodyImage dongoBodyImage = new BodyImage("payload/dongo.png");

    public Dongo(World world) {
        super(world, dongoShape);
        addImage(dongoBodyImage);
    }

    public void setLinearPositiveVelocity(Vec2 posVelocity) {
        this.posVelocity = posVelocity;
    }

    public void setLinearNegativeVelocity(Vec2 negVelocity) {
        this.negVelocity = negVelocity;
    }

    public void moveLeft(Dongo dongo) {
        dongo.setLinearVelocity(posVelocity);
    }

    public void moveRight(Dongo dongo) {
        dongo.setLinearVelocity(negVelocity);
    }

}
