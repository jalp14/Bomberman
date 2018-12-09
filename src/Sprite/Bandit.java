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
public class Bandit extends Walker {

    private Vec2 posVelocity;
    private Vec2 negVelocity;

    private static final Shape banditShape = new PolygonShape(0.191f, 1.022f, 1.052f, 0.587f, 1.307f, -0.072f, 1.105f, -0.512f, 0.051f, -0.74f, -0.481f, -0.217f, -0.736f, 0.723f, 0.191f, 1.022f);
    private static final BodyImage banditImage = new BodyImage("payload/bandit.gif", 2.5f);

    public Bandit(World world) {
        super(world, banditShape);
        addImage(banditImage);
        setPosition(new Vec2(2, 1));
    }

    public void setLinearPositiveVelocity(Vec2 posVelocity) {
        this.posVelocity = posVelocity;
    }

    public void setLinearNegativeVelocity(Vec2 negVelocity) {
        this.negVelocity = negVelocity;
    }

    public void moveLeft(Bandit bandit) {
        bandit.setLinearVelocity(posVelocity);
    }

    public void moveRight(Bandit bandit) {
        bandit.setLinearVelocity(negVelocity);
    }
}
