/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprite;

import SceneSetup.FileIO;
import city.cs.engine.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;

/**
 *
 * @author jalpd
 */
public class Bomberman extends Walker {

    private World world;
    private int bombermanHealth;
    private int bombermanSpeed;
    private int bombermanJumpPower;

    private static final Shape bombermanShape = new PolygonShape(-0.14f, 0.67f, 0.2f, 0.67f, 0.66f, 0.13f, 0.44f, -1.3f, -0.43f, -1.3f, -0.59f, 0.15f, -0.14f, 0.67f);
    private static final BodyImage staticFrontImage = new BodyImage("payload/Bomberman/Front/Bman_F_f00.png", 3.0f);
    private static final BodyImage staticLeftImage = new BodyImage("payload/Bomberman/Side/Bman_F_f00.png", 3.0f);
    private static final BodyImage staticRightImage = new BodyImage("payload/Bomberman/Side/Bman_R_f00.png", 3.0f);
    private static final BodyImage staticBackImage = new BodyImage("payload/Bomberman/Back/Bman_B_f00.png", 3.0f);
    private static final BodyImage leftImage = new BodyImage("payload/Bomberman/Side/SideL.gif", 3.0f);
    private static final BodyImage rightImage = new BodyImage("payload/Bomberman/Side/SideR.gif", 3.0f);
    private static final BodyImage frontImage = new BodyImage("payload/Bomberman/Front/Front.gif", 3.0f);
    private static final BodyImage backImage = new BodyImage("payload/Bomberman/Back/Back.gif", 3.0f);
    private SoundClip lowHealthSound;
    private FileIO currentVolume;

    public Bomberman(World world) {
        super(world, bombermanShape);
        this.world = world;
        addImage(staticFrontImage);
        bombermanSpeed = 5;
        bombermanJumpPower = 5;
        currentVolume = new FileIO();
        currentVolume.readFromFile();
        playLowHealthSound();
    }

    public int getBombermanJumpPower() {
        return bombermanJumpPower;
    }

    public void bombermanLowHealth() {
        if (getBombermanHealth() <= 20) {
            System.out.println("Bomberman health is " + getBombermanHealth());
            playSound();
        } else {
            stopSound();
        }
    }

    public void playLowHealthSound() {
        try {
            lowHealthSound = new SoundClip("payload/GameSounds/lowHealth.wav");
            // lowHealthSound.loop();
            lowHealthSound.setVolume(currentVolume.getSfx());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void playSound() {
        lowHealthSound.loop();
    }

    public void stopSound() {
        lowHealthSound.stop();
    }

    public void setBombermanJumpPower(int bombermanJumpPower) {
        this.bombermanJumpPower = bombermanJumpPower;
    }

    public int getBombermanSpeed() {
        return bombermanSpeed;
    }

    public void setBombermanSpeed(int bombermanSpeed) {
        this.bombermanSpeed = bombermanSpeed;
    }

    public int getBombermanHealth() {
        return bombermanHealth;
    }

    public void setBombermanHealth(int bombermanHealth) {
        this.bombermanHealth = bombermanHealth;
    }

    public void startMovingLeft() {
        removeAllImages();
        addImage(leftImage);
    }

    public void stopMovingLeft() {
        removeAllImages();
        addImage(staticLeftImage);
    }

    public void startMovingRight() {
        removeAllImages();
        addImage(rightImage);
    }

    public void stopMovingRight() {
        removeAllImages();
        addImage(staticRightImage);
    }

    public void startMovingFront() {
        removeAllImages();
        addImage(backImage);
    }

    public void stopMovingFront() {
        removeAllImages();
        addImage(staticBackImage);
    }

    public void startMovingBack() {
        removeAllImages();
        addImage(frontImage);
    }

    public void stopMovingBack() {
        removeAllImages();
        addImage(staticFrontImage);
    }

    public World getWorld() {
        return world;
    }
}
