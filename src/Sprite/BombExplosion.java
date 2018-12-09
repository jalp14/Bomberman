/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprite;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author jalpd
 */
public class BombExplosion extends DynamicBody {

    private static final BodyImage explosionImage = new BodyImage("payload/Bomb/anim1.png", 3.0f);

    private static final Shape bombExplosion = new BoxShape(1.0f, 1.0f);

    private Timer timer;

    public BombExplosion(World world) {
        super(world, bombExplosion);
        addImage(explosionImage);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    BombExplosion.this.destroy();
                } catch (NullPointerException ne) {

                }
            }
        }, 300, 300);
    }
}
