
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprite;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.Walker;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

/**
 *
 * @author jalpd
 */
public class Creeper extends Walker {

    private WorldView sceneView;
    private Vec2 posVelocity;
    private Vec2 negVelocity;

    private static final Shape creeperShape = new PolygonShape(-0.228f, 0.367f, 0.206f, 0.374f, 0.398f, 0.197f, 0.319f, -0.4f,
            -0.336f, -0.4f, -0.415f, 0.195f, -0.228f, 0.367f);
    private static final BodyImage creepbodyImage = new BodyImage("payload/Creep/Creep_F_f00.png", 2f);

    public Creeper(World world) {
        super(world, creeperShape);
        addImage(creepbodyImage);
    }

    public void setLinearPositiveVelocity(Vec2 posVelocity) {
        this.posVelocity = posVelocity;
    }

    public void setLinearNegativeVelocity(Vec2 negVelocity) {
        this.negVelocity = negVelocity;
    }

    public void moveUp(Creeper creep) {
        creep.setLinearPositiveVelocity(posVelocity);
    }

    public void moveDown(Creeper creep) {
        creep.setLinearNegativeVelocity(negVelocity);
    }

    public void moveLeft(Creeper creep) {
        creep.setLinearVelocity(posVelocity);
    }

    public void moveRight(Creeper creep) {
        creep.setLinearVelocity(negVelocity);
    }

}
