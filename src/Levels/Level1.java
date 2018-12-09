/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Levels;

import CollisionDetection.CollisionDetectorBomberman;
import CollisionDetection.CollisionDetectorCreep;
import CollisionDetection.CollisionDetectorDoor;
import CollisionDetection.FinalKeySensor;
import CollisionDetection.PickupSensors;
import PickupItems.FinalKey;
import PickupItems.SpeedPowerUp;
import SceneSetup.FileIO;
import SceneSetup.KeyboardController;
import SceneSetup.SceneSetup;
import SceneSetup.WorldSetup;
import Sprite.Bandit;
import Sprite.Bomberman;
import Sprite.Creeper;
import Sprite.Dongo;
import Texture.Door;
import Texture.StoneBlock;
import TimeManagement.TimerMan;
import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;
import javax.swing.Timer;

/**
 *
 * @author jalpd
 */
public class Level1 extends WorldSetup {

    private float minXBound = -14.5f;
    private float minYBound = -14.5f;
    private float maxXBound = 14.5f;
    private float maxYBound = 14.5f;
    private StoneBlock stoneblock[];
    private int y = 0;
    private int b = 0;
    private int s = 0;
    private int q = 0;
    private Door door;
    private Creeper creep1;
    private Creeper creep2;
    private Body leftWallBody;
    private Shape leftWallShape;
    private Shape bottomGroundShape;
    private Body bottomGround;
    private Shape topGroundShape;
    private Body topGround;
    private Body rightWallBody;
    private Shape rightWallShape;
    private SceneSetup sceneSetup;
    private SpeedPowerUp speedPowerUp;
    private Sensor pickupSensor;
    private FinalKey finalKey;
    private Sensor finalKeySensor;
    private FileIO currentVolume;
    private SoundClip backgroundMusic;

    @Override
    public void setupWorld(SceneSetup sceneSetup) {
        super.setupWorld(sceneSetup);
        this.sceneSetup = sceneSetup;
        setupCreepers();
        setupDoor();
        makeBorders();
        putStoneTexture();
        putSpeedPowerUp();
        collectKey();
        currentVolume = new FileIO();
        currentVolume.readFromFile();
        setupSound();
    }

    @Override
    public void setupSound() {
        try {
            backgroundMusic = new SoundClip("payload/GameSounds/lvl1BkgSound.wav");
            backgroundMusic.loop();
            backgroundMusic.setVolume(currentVolume.getVolume());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void startSound() {
        backgroundMusic.resume();
    }

    @Override
    public void stopSound() {
        backgroundMusic.stop();
    }

    @Override
    public void collectKey() {
        finalKey = new FinalKey(this);
        finalKey.setPosition(new Vec2(-10, -6));
        finalKey.removeAllImages();
        finalKey.addImage(new BodyImage("payload/Powerups/final_key.png", 2.0f));
        finalKeySensor = new Sensor(finalKey, finalKey.getShape());
        finalKeySensor.addSensorListener(new FinalKeySensor(bomberman, sceneSetup, door));
    }

    @Override
    public void setupCreepers() {
        // Creeper 1
        creep1 = new Creeper(this);
        creep1.setPosition(new Vec2(4, -6.5f));
        creep1.setLinearPositiveVelocity(new Vec2(2, 0));
        creep1.setLinearNegativeVelocity(new Vec2(-2, 0));
        creep1.moveRight(creep1);

        // Creeper 2
        creep2 = new Creeper(this);
        creep2.setPosition(new Vec2(0, -12f));
        creep2.setLinearPositiveVelocity(new Vec2(4, 0));
        creep2.setLinearNegativeVelocity(new Vec2(-4, 0));
        creep2.moveLeft(creep2);
    }

    @Override
    public void setupDoor() {
        door = new Door(this);
        door.setPosition(new Vec2(12.5f, -12.0f));
        door.addCollisionListener(new CollisionDetectorCreep(creep2, true));
        door.addCollisionListener(new CollisionDetectorDoor(bomberman, sceneSetup));
    }

    public void putSpeedPowerUp() {
        speedPowerUp = new SpeedPowerUp(this, bomberman);
        pickupSensor = new Sensor(speedPowerUp, speedPowerUp.getShape());
        pickupSensor.addSensorListener(new PickupSensors(bomberman));
    }

    public void putStoneTexture() {
        stoneblock = new StoneBlock[20];
        for (int x = 0; x < 20; x++) {
            stoneblock[x] = new StoneBlock(this);
            stoneblock[x].addCollisionListener(new CollisionDetectorCreep(creep1, false));
            stoneblock[x].addCollisionListener(new CollisionDetectorCreep(creep2, false));
        }
        // First Row
        stoneblock[y].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 6));
        stoneblock[y + 1].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 6));
        stoneblock[y + 2].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 6));
        stoneblock[y + 3].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 6));
        stoneblock[y + 4].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 6));

        // Second Row
        stoneblock[y + 5].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 12));
        stoneblock[y + 6].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 12));
        stoneblock[y + 7].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 12));
        stoneblock[y + 8].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 12));
        stoneblock[y + 9].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 12));

        // Third Row
        stoneblock[y + 10].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 18));
        stoneblock[y + 11].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 18));
        stoneblock[y + 12].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 18));
        stoneblock[y + 13].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 18));
        stoneblock[y + 14].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 18));

        // Fourth Row
        stoneblock[y + 15].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 24));
        stoneblock[y + 16].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 24));
        stoneblock[y + 17].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 24));
        stoneblock[y + 18].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 24));
        stoneblock[y + 19].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 24));
    }

    @Override
    public void makeBorders() {
        bottomGroundShape = new BoxShape(25, 1f);
        bottomGround = new StaticBody(this, bottomGroundShape);
        bottomGround.setPosition(new Vec2(-9.5f, -14.5f));
        bottomGround.setLineColor(Color.GRAY);

        //Top Ground
        topGroundShape = new BoxShape(25, 1f);
        topGround = new StaticBody(this, topGroundShape);
        topGround.setPosition(new Vec2(9.5f, 14.5f));
        topGround.setLineColor(Color.GRAY);

        // Left Wall
        leftWallShape = new BoxShape(1f, 25);
        leftWallBody = new StaticBody(this, leftWallShape);
        leftWallBody.setPosition(new Vec2(-14.5f, 0));
        leftWallBody.setLineColor(Color.GRAY);
        leftWallBody.addCollisionListener(new CollisionDetectorCreep(creep1, false));
        leftWallBody.addCollisionListener(new CollisionDetectorCreep(creep2, false));

        //Right Wall
        rightWallShape = new BoxShape(1f, 25);
        rightWallBody = new StaticBody(this, rightWallShape);
        rightWallBody.setPosition(new Vec2(14.5f, 0));
        rightWallBody.setLineColor(Color.GRAY);
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep1, true));
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep2, true));
    }

}
