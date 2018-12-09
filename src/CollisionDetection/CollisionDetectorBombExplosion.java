/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CollisionDetection;

import Sprite.Bandit;
import Sprite.Bomb;
import Sprite.BombExplosion;
import Sprite.Creeper;
import Sprite.Dongo;
import Texture.ExplodableBlock;
import Texture.GrassBlock;
import Texture.IceBlock;
import Texture.StoneBlock;
import city.cs.engine.Body;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.Fixture;
import city.cs.engine.SolidFixture;

/**
 *
 * @author jalpd
 */
public class CollisionDetectorBombExplosion implements CollisionListener {

    private BombExplosion bombExplosion;
    private Bomb bomb;

    public CollisionDetectorBombExplosion(BombExplosion bombExplosion, Bomb bom) {
        this.bombExplosion = bombExplosion;
        this.bomb = bomb;
    }

    @Override
    public void collide(CollisionEvent e) {
//        bombExplosion.destroy();
        if (e.getOtherBody() instanceof Creeper) {
            System.out.println("Explosion Collision");
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();

        } else if (e.getOtherBody() instanceof StoneBlock) {
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();

        } else if (e.getOtherBody() instanceof Dongo) {
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();

        } else if (e.getOtherBody() instanceof Bandit) {
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();

        } else if (e.getOtherBody() instanceof GrassBlock) {
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();

        } else if (e.getOtherBody() instanceof IceBlock) {
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();

        } else if (e.getOtherBody() instanceof ExplodableBlock) {
            e.getOtherBody().destroy();
            e.getReportingBody().destroy();
        } else {
            bombExplosion.destroy();
        }
    }

}
