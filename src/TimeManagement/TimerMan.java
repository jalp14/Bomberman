/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimeManagement;

import SceneSetup.SceneSetup;
import SceneSetup.WorldSetup;
import Sprite.Bomb;
import Sprite.BombExplosion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import static javafx.util.Duration.seconds;

/**
 * Acts as a timer
 * @author jalpd
 */
public class TimerMan implements ActionListener {

    private BombExplosion explosion;

    /**
     * Initialises timer for explosions
     * @param explosion current instance of the explosion
     */
    public TimerMan(BombExplosion explosion) {
        this.explosion = explosion;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Timer Over");
        if (explosion != null) {
            explosion.destroy();
        }
    }

}
