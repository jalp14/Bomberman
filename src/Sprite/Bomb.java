/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprite;

import SceneSetup.FileIO;
import TimeManagement.TimerMan;
import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.Fixture;
import city.cs.engine.GhostlyFixture;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import java.io.IOException;
import java.util.Timer;
import org.jbox2d.common.Vec2;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jalpd
 */
public class Bomb extends StaticBody {

    public float upOffset;
    public float downOffset;
    public float leftOffset;
    public float rightOffset;

    private World world;
    private Fixture bombFixture;
    private static final Shape bombShape = new CircleShape(0.5f, 0, 0);
    private static final BodyImage bombImage = new BodyImage("payload/Bomb/Bomb_f01.png", 0.5f);
    private Timer timer;
    private SoundClip bombSound;
    private FileIO gameData;
    BombExplosion bombExplosion;

    public Bomb(World world) {
        super(world);
        bombFixture = new GhostlyFixture(this, bombShape);
        addImage(bombImage);
        this.world = world;
        gameData = new FileIO();
        gameData.readFromFile();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Bomb.this.destroy();
                } catch (NullPointerException ne) {

                }
            }
        }, 3000, 3000);
    }

    public void setExplosionPosition(Vec2 newPosition) {
        upOffset = newPosition.y + 0.2f;
        downOffset = newPosition.y - 0.2f;
        leftOffset = newPosition.x - 0.5f;
        rightOffset = newPosition.x + 0.5f;
    }

    public void createExplosion(Vec2 position) {
        bombExplosion = new BombExplosion(world);
        bombExplosion.setPosition(position);
        try {
            bombSound = new SoundClip("payload/GameSounds/bomb.wav");
            bombSound.play();
            bombSound.setVolume(gameData.getVolume());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

    }

    public BombExplosion getExplosion() {
        return bombExplosion;
    }
}
