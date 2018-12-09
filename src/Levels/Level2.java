/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Levels;

import CollisionDetection.BanditSensor;
import CollisionDetection.CollisionDetectorBandit;
import CollisionDetection.CollisionDetectorBombExplosion;
import CollisionDetection.CollisionDetectorBomberman;
import CollisionDetection.CollisionDetectorCreep;
import CollisionDetection.CollisionDetectorDoor;
import CollisionDetection.CollisionDetectorExplosiveBlock;
import CollisionDetection.FinalKeySensor;
import CollisionDetection.HealthPackageSensor;
import CollisionDetection.PickupSensors;
import PickupItems.FinalKey;
import PickupItems.HealthPackage;
import PickupItems.SpeedPowerUp;
import SceneSetup.FileIO;
import SceneSetup.KeyboardController;
import SceneSetup.SceneSetup;
import SceneSetup.WorldSetup;
import Sprite.Bandit;
import Sprite.Bomberman;
import Sprite.Creeper;
import Texture.Door;
import Texture.ExplodableBlock;
import Texture.GrassBlock;
import Texture.IceBlock;
import Texture.StoneBlock;
import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import java.awt.Color;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;

/**
 *
 * @author jalpd
 */
public class Level2 extends WorldSetup {

    private float minXBound = -14.5f;
    private float minYBound = -14.5f;
    private float maxXBound = 14.5f;
    private float maxYBound = 14.5f;
    private GrassBlock grassblock[];
    private int y = 0;
    private int b = 0;
    private int s = 0;
    private int q = 0;
    private int l = 0;
    private Door door;
    private Creeper creep1;
    private Creeper creep2;
    private Creeper creep3;
    private Body leftWallBody;
    private Shape leftWallShape;
    private Shape bottomGroundShape;
    private Body bottomGround;
    private Shape topGroundShape;
    private Body topGround;
    private Body rightWallBody;
    private Shape rightWallShape;
    SceneSetup sceneSetup;
    private Bandit bandit;
    private IceBlock iceBlock[];
    private HealthPackage healthPackage;
    private Sensor healthPackageSensor;
    private SpeedPowerUp speedPowerUp;
    private Sensor pickupSensor;
    private FinalKey finalKey;
    private Sensor finalKeySensor;
    private SoundClip backgroundMusic;
    private FileIO currentVolume;

    @Override
    public void setupWorld(SceneSetup sceneSetup) {
        super.setupWorld(sceneSetup);
        this.sceneSetup = sceneSetup;
        setupIceBlock();
        setupCreepers();
        setupBandit();
        setupDoor();
        makeBorders();
        setupGrass();
        setupHealthPackage();
        putSpeedPowerUp();
        collectKey();
        currentVolume = new FileIO();
        currentVolume.readFromFile();
        setupSound();
    }

    @Override
    public void setupSound() {
        try {
            backgroundMusic = new SoundClip("payload/GameSounds/lvl2BkgSound.wav");
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
        finalKey.setPosition(new Vec2(-10, -13));
        finalKey.addImage(new BodyImage("payload/Powerups/final_key.png", 2.0f));
        finalKeySensor = new Sensor(finalKey, finalKey.getShape());
        finalKeySensor.addSensorListener(new FinalKeySensor(bomberman, sceneSetup, door));
    }

    public void setupHealthPackage() {
        healthPackage = new HealthPackage(this);
        healthPackage.removeAllImages();
        healthPackage.addImage(new BodyImage("payload/Powerups/health_package.png"));
        healthPackage.setPosition(new Vec2(-2, 0));
        healthPackageSensor = new Sensor(healthPackage, healthPackage.getShape());
        healthPackageSensor.addSensorListener(new HealthPackageSensor(bomberman, sceneSetup));
    }

    public void putSpeedPowerUp() {
        speedPowerUp = new SpeedPowerUp(this, bomberman);
        pickupSensor = new Sensor(speedPowerUp, speedPowerUp.getShape());
        pickupSensor.addSensorListener(new PickupSensors(bomberman));
        speedPowerUp.setPosition(new Vec2(8, 8));
    }

    @Override
    public void setupCreepers() {

        // Creeper 1
        creep1 = new Creeper(this);
        creep1.setPosition(new Vec2(6, -6.5f));
        creep1.setLinearPositiveVelocity(new Vec2(2, 0));
        creep1.setLinearNegativeVelocity(new Vec2(-2, 0));
        creep1.moveRight(creep1);

        // Creeper 2
        creep2 = new Creeper(this);
        creep2.setPosition(new Vec2(0, -12f));
        creep2.setLinearPositiveVelocity(new Vec2(4, 0));
        creep2.setLinearNegativeVelocity(new Vec2(-4, 0));
        creep2.moveLeft(creep2);

        // Creeper 3
        creep3 = new Creeper(this);
        creep3.setPosition(new Vec2(2, 5));
        creep3.setLinearPositiveVelocity(new Vec2(2, 0));
        creep3.setLinearNegativeVelocity(new Vec2(-2, 0));
        creep3.moveRight(creep3);
    }

    public void setupBandit() {
        // Bandit 1
        bandit = new Bandit(this);
        bandit.setPosition(new Vec2(-4, -0.5f));
        bandit.setLinearPositiveVelocity(new Vec2(5, 0));
        bandit.setLinearNegativeVelocity(new Vec2(-5, 0));
        bandit.moveLeft(bandit);
    }

    @Override
    public void setupDoor() {
        door = new Door(this);
        door.setPosition(new Vec2(12.5f, 8.0f));
        door.addCollisionListener(new CollisionDetectorCreep(creep2, true));
        door.addCollisionListener(new CollisionDetectorDoor(super.getBomberman(), sceneSetup));
    }

    public void setupGrass() {
        s = 0;
        grassblock = new GrassBlock[14];
        for (int g = 0; g < 14; g++) {
            grassblock[g] = new GrassBlock(this);
            grassblock[g].addCollisionListener(new CollisionDetectorCreep(creep1, false));
            grassblock[g].addCollisionListener(new CollisionDetectorCreep(creep2, false));
            grassblock[g].addCollisionListener(new CollisionDetectorCreep(creep3, true));
            grassblock[g].addCollisionListener(new CollisionDetectorBandit(bandit, false));
        }
        grassblock[s].setPosition(new Vec2(minXBound + 2.4f, maxYBound - 3f));
        grassblock[s + 1].setPosition(new Vec2(maxXBound - 3f, maxYBound - 3f));
        grassblock[s + 2].setPosition(new Vec2(minXBound + 5.8f, maxYBound - 3f));
        grassblock[s + 3].setPosition(new Vec2(minXBound + 8.8f, maxYBound - 3f));
        grassblock[s + 4].setPosition(new Vec2(maxXBound - 6f, maxYBound - 3f));
        grassblock[s + 5].setPosition(new Vec2(maxXBound - 9f, maxYBound - 3f));
        grassblock[s + 6].setPosition(new Vec2(maxXBound - 6f, maxYBound - 9f));
        grassblock[s + 7].setPosition(new Vec2(maxXBound - 9f, maxYBound - 9f));
        grassblock[s + 8].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 21f));
        grassblock[s + 9].setPosition(new Vec2(minXBound + 5.8f, maxYBound - 21f));
        grassblock[s + 10].setPosition(new Vec2(minXBound + 8.8f, maxYBound - 21f));
        grassblock[s + 11].setPosition(new Vec2(minXBound + 11.8f, maxYBound - 21f));
        grassblock[s + 12].setPosition(new Vec2(minXBound + 14.8f, maxYBound - 21f));
        grassblock[s + 13].setPosition(new Vec2(minXBound + 18.8f, maxYBound - 21.0f));
    }

    public void setupIceBlock() {
        iceBlock = new IceBlock[20];
        for (int c = 0; c < 20; c++) {
            iceBlock[c] = new IceBlock(this);
        }
        // First Row
        iceBlock[l].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 6));
        iceBlock[l + 1].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 6));
        iceBlock[l + 2].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 6));
        iceBlock[l + 3].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 6));
        iceBlock[l + 4].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 6));

        // Second Row
        iceBlock[l + 5].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 12));
        iceBlock[l + 6].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 12));
        iceBlock[l + 7].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 12));
        iceBlock[l + 8].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 12));
        iceBlock[l + 9].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 12));

        // Third Row
        iceBlock[l + 10].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 18));
        iceBlock[l + 11].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 18));
        iceBlock[l + 12].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 18));
        iceBlock[l + 13].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 18));
        iceBlock[l + 14].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 18));

        // Fourth Row
        iceBlock[l + 15].setPosition(new Vec2(minXBound + 4.5f, maxYBound - 24));
        iceBlock[l + 16].setPosition(new Vec2(minXBound + 9.5f, maxYBound - 24));
        iceBlock[l + 17].setPosition(new Vec2(minXBound + 14.5f, maxYBound - 24));
        iceBlock[l + 18].setPosition(new Vec2(minXBound + 19.5f, maxYBound - 24));
        iceBlock[l + 19].setPosition(new Vec2(minXBound + 24.5f, maxYBound - 24));
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
        leftWallBody.addCollisionListener(new CollisionDetectorCreep(creep3, false));
        leftWallBody.addCollisionListener(new CollisionDetectorBandit(bandit, false));

        //Right Wall
        rightWallShape = new BoxShape(1f, 25);
        rightWallBody = new StaticBody(this, rightWallShape);
        rightWallBody.setPosition(new Vec2(14.5f, 0));
        rightWallBody.setLineColor(Color.GRAY);
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep1, true));
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep2, true));
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep3, true));
        rightWallBody.addCollisionListener(new CollisionDetectorBandit(bandit, true));
    }

}
