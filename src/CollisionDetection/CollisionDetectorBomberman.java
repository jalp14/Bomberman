package CollisionDetection;

import SceneSetup.BombermanDead;
import SceneSetup.GameOver;
import SceneSetup.SceneSetup;
import Sprite.Bandit;
import Sprite.BombExplosion;
import Sprite.Bomberman;
import Sprite.Creeper;
import Sprite.Dongo;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CollisionDetectorBomberman implements CollisionListener {

    private Bomberman bomberman;

    private SceneSetup sceneSetup;
    private SoundClip bounceSound;

    private JDialog bombermanDead;

    public CollisionDetectorBomberman(Bomberman bomberman, SceneSetup sceneSetup) {
        this.bomberman = bomberman;
        this.sceneSetup = sceneSetup;
    }

    public void bombermanDead() {
        bombermanDead = new BombermanDead(sceneSetup, true);
        bombermanDead.setSize(373, 212);
        bombermanDead.setLocationByPlatform(true);
        bombermanDead.setVisible(true);
    }

    public void playBounceSound() {
        try {
            bounceSound = new SoundClip("payload/GameSounds/collisionSound.wav");
            bounceSound.play();
            bounceSound.setVolume(sceneSetup.getGameData().getSfx());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        playBounceSound();

        if (e.getOtherBody() instanceof Creeper) {
            sceneSetup.reduceBombermanHealth(10);
            if (sceneSetup.getCurrentHealth() <= 0) {
                bomberman.stopSound();
                System.out.println("Bomberman Dead");
                sceneSetup.setBomberman(bomberman);
                sceneSetup.updateStatus();
                e.getReportingBody().destroy();
                bombermanDead();
            } else {
                bomberman.setBombermanHealth(sceneSetup.getCurrentHealth());
                bomberman.bombermanLowHealth();
                sceneSetup.setBomberman(bomberman);
                sceneSetup.updateStatus();
                System.out.println("Bombeman Health is " + sceneSetup.getCurrentHealth());
            }
        } else if (e.getOtherBody() instanceof Bandit) {
            sceneSetup.reduceBombermanHealth(20);
            if (sceneSetup.getCurrentHealth() <= 0) {
                System.out.println("Bomberman Dead");
                sceneSetup.setBomberman(bomberman);
                sceneSetup.updateStatus();
                e.getReportingBody().destroy();
                bombermanDead();
            } else {
                bomberman.setBombermanHealth(sceneSetup.getCurrentHealth());
                sceneSetup.setBomberman(bomberman);
                sceneSetup.updateStatus();
                System.out.println("Bombeman Health is " + sceneSetup.getCurrentHealth());
            }
        } else if (e.getOtherBody() instanceof Dongo) {
            sceneSetup.reduceBombermanHealth(30);
            if (sceneSetup.getCurrentHealth() <= 0) {
                System.out.println("Bomberman Dead");
                sceneSetup.setBomberman(bomberman);
                sceneSetup.updateStatus();
                e.getReportingBody().destroy();
                bombermanDead();
            } else {
                bomberman.setBombermanHealth(sceneSetup.getCurrentHealth());
                sceneSetup.setBomberman(bomberman);
                sceneSetup.updateStatus();
                System.out.println("Bombeman Health is " + sceneSetup.getCurrentHealth());
            }
        } else if (e.getOtherBody() instanceof BombExplosion) {
            sceneSetup.setCurrentHealth(0);
            sceneSetup.setBomberman(bomberman);
            sceneSetup.updateStatus();
            e.getReportingBody().destroy();
            bombermanDead();
        }

    }
}
