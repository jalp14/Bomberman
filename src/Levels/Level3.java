/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Levels;

import CollisionDetection.CollisionDetectorBandit;
import CollisionDetection.CollisionDetectorBomberman;
import CollisionDetection.CollisionDetectorCreep;
import CollisionDetection.CollisionDetectorDongo;
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
import Sprite.Dongo;
import Texture.Door;
import Texture.ExplodableBlock;
import Texture.GrassBlock;
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
public class Level3 extends WorldSetup {

    private float minXBound = -14.5f;
    private float minYBound = -14.5f;
    private float maxXBound = 14.5f;
    private float maxYBound = 14.5f;
    private StoneBlock stoneblock[];
    private GrassBlock grassblock[];
    private int y = 0;
    private int b = 0;
    private int s = 0;
    private int q = 0;
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
    private ExplodableBlock explodableBlock[];
    private Bandit bandit;
    private HealthPackage healthPackage;
    private Sensor healthPackageSensor;
    private SpeedPowerUp speedPowerUp;
    private Sensor pickupSensor;
    private FinalKey finalKey;
    private Sensor finalKeySensor;
    private Dongo dongo;
    private SoundClip backgroundMusic;
    private FileIO currentVolume;

    @Override
    public void setupWorld(SceneSetup sceneSetup) {
        super.setupWorld(sceneSetup);
        this.sceneSetup = sceneSetup;
        setupCreepers();
        setupDongo();
        setupBandit();
        setupDoor();
        makeBorders();
        putStoneTexture();
        setupExplodableBlocks();
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
            backgroundMusic = new SoundClip("payload/GameSounds/lvl3BkgSound.wav");
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
        finalKey.setPosition(new Vec2(8, -4));
        finalKey.addImage(new BodyImage("payload/Powerups/final_key.png", 2.0f));
        finalKeySensor = new Sensor(finalKey, finalKey.getShape());
        finalKeySensor.addSensorListener(new FinalKeySensor(bomberman, sceneSetup, door));
    }

    public void setupHealthPackage() {
        healthPackage = new HealthPackage(this);
        healthPackage.removeAllImages();
        healthPackage.addImage(new BodyImage("payload/Powerups/health_package.png"));
        healthPackage.setPosition(new Vec2(-8, -12));
        healthPackageSensor = new Sensor(healthPackage, healthPackage.getShape());
        healthPackageSensor.addSensorListener(new HealthPackageSensor(bomberman, sceneSetup));
    }

    public void putSpeedPowerUp() {
        speedPowerUp = new SpeedPowerUp(this, bomberman);
        pickupSensor = new Sensor(speedPowerUp, speedPowerUp.getShape());
        pickupSensor.addSensorListener(new PickupSensors(bomberman));
        speedPowerUp.setPosition(new Vec2(12, 12));
    }

    public void setupBandit() {
        bandit = new Bandit(this);
        bandit.setPosition(new Vec2(-6, 5.0f));
        bandit.setLinearPositiveVelocity(new Vec2(4, 0));
        bandit.setLinearNegativeVelocity(new Vec2(-4, 0));
        bandit.moveLeft(bandit);
    }

    public void setupExplodableBlocks() {
        explodableBlock = new ExplodableBlock[9];
        for (int t = 0; t < 9; t++) {
            explodableBlock[t] = new ExplodableBlock(this);
            explodableBlock[t].addCollisionListener(new CollisionDetectorExplosiveBlock(bomberman, sceneSetup));
            explodableBlock[t].addCollisionListener(new CollisionDetectorCreep(creep1, false));
            explodableBlock[t].addCollisionListener(new CollisionDetectorCreep(creep2, false));
            explodableBlock[t].addCollisionListener(new CollisionDetectorBandit(bandit, false));
            explodableBlock[t].addCollisionListener(new CollisionDetectorDongo(dongo, false));
        }
        b = 0;
        explodableBlock[b].setPosition(new Vec2(minXBound + 2.4f, maxYBound - 3f));
        explodableBlock[b + 1].setPosition(new Vec2(maxXBound - 18f, maxYBound - 3f));
        explodableBlock[b + 2].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 15f));
        explodableBlock[b + 3].setPosition(new Vec2(minXBound + 11.8f, maxYBound - 15f));
        explodableBlock[b + 4].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 21f));
        explodableBlock[b + 5].setPosition(new Vec2(minXBound + 14.8f, maxYBound - 15f));
        explodableBlock[b + 6].setPosition(new Vec2(minXBound + 14.8f, maxYBound - 21f));
        explodableBlock[b + 7].setPosition(new Vec2(minXBound + 17.8f, maxYBound - 21f));
        explodableBlock[b + 8].setPosition(new Vec2(minXBound + 2.4f, minYBound + 20f));

    }

    @Override
    public void setupCreepers() {

        // Creeper 1
        creep1 = new Creeper(this);
        creep1.setPosition(new Vec2(6, -6.5f));
        creep1.setLinearPositiveVelocity(new Vec2(4, 0));
        creep1.setLinearNegativeVelocity(new Vec2(-4, 0));
        creep1.moveRight(creep1);

        // Creeper 2
        creep2 = new Creeper(this);
        creep2.setPosition(new Vec2(0, -12f));
        creep2.setLinearPositiveVelocity(new Vec2(4, 0));
        creep2.setLinearNegativeVelocity(new Vec2(-4, 0));
        creep2.moveLeft(creep2);

        // Creeper 3 (Vertical)
        creep3 = new Creeper(this);
        creep3.setPosition(new Vec2(12.5f, 13.5f));
        creep3.setLinearPositiveVelocity(new Vec2(0, 6));
        creep3.setLinearNegativeVelocity(new Vec2(0, -4));
        creep3.moveUp(creep3);

    }

    @Override
    public void setupDoor() {
        door = new Door(this);
        door.setPosition(new Vec2(-10.5f, 12.0f));
        door.addCollisionListener(new CollisionDetectorCreep(creep2, true));
        door.addCollisionListener(new CollisionDetectorDoor(bomberman, sceneSetup));
    }

    public void setupDongo() {
        dongo = new Dongo(this);
        dongo.setPosition(new Vec2(0, 10));
        dongo.setLinearPositiveVelocity(new Vec2(2, 0));
        dongo.setLinearNegativeVelocity(new Vec2(-2, 0));
        dongo.moveLeft(dongo);
    }

    public void setupGrass() {
        grassblock = new GrassBlock[30];
        for (int g = 0; g < 30; g++) {
            grassblock[g] = new GrassBlock(this);
            grassblock[g].addCollisionListener(new CollisionDetectorCreep(creep1, false));
            grassblock[g].addCollisionListener(new CollisionDetectorCreep(creep2, false));
        }
        grassblock[s].setPosition(new Vec2(minXBound + 2.4f, maxYBound - 3f));
        grassblock[s + 1].setPosition(new Vec2(maxXBound - 3f, maxYBound - 3f));
        grassblock[s + 2].setPosition(new Vec2(minXBound + 2.8f, minYBound + 2f));
        //  grassblock[s+3].setPosition(new Vec2(maxXBound - 3f, minYBound + 2f));

        grassblock[s + 4].setPosition(new Vec2(minXBound + 5.8f, maxYBound - 3f));
        grassblock[s + 5].setPosition(new Vec2(minXBound + 8.8f, maxYBound - 3f));
        grassblock[s + 6].setPosition(new Vec2(minXBound + 11.8f, maxYBound - 3f));

        grassblock[s + 7].setPosition(new Vec2(maxXBound - 6f, maxYBound - 3f));
        grassblock[s + 8].setPosition(new Vec2(maxXBound - 9f, maxYBound - 3f));

        grassblock[s + 9].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 6f));

        grassblock[s + 10].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 9f));
        grassblock[s + 11].setPosition(new Vec2(minXBound + 5.8f, maxYBound - 9f));
        grassblock[s + 12].setPosition(new Vec2(minXBound + 8.8f, maxYBound - 9f));

        grassblock[s + 13].setPosition(new Vec2(maxXBound - 3f, maxYBound - 6f));
        grassblock[s + 14].setPosition(new Vec2(maxXBound - 3f, maxYBound - 9f));
        grassblock[s + 15].setPosition(new Vec2(maxXBound - 6f, maxYBound - 9f));
        grassblock[s + 16].setPosition(new Vec2(maxXBound - 9f, maxYBound - 9f));

        grassblock[s + 17].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 15f));
        grassblock[s + 18].setPosition(new Vec2(minXBound + 5.8f, maxYBound - 15f));
        grassblock[s + 19].setPosition(new Vec2(minXBound + 8.8f, maxYBound - 15f));
        grassblock[s + 20].setPosition(new Vec2(minXBound + 11.8f, maxYBound - 15f));
        grassblock[s + 21].setPosition(new Vec2(minXBound + 14.8f, maxYBound - 15f));
        grassblock[s + 22].setPosition(new Vec2(minXBound + 17.8f, maxYBound - 15f));

        grassblock[s + 23].setPosition(new Vec2(minXBound + 2.8f, maxYBound - 21f));
        grassblock[s + 24].setPosition(new Vec2(minXBound + 5.8f, maxYBound - 21f));
        grassblock[s + 25].setPosition(new Vec2(minXBound + 8.8f, maxYBound - 21f));
        grassblock[s + 26].setPosition(new Vec2(minXBound + 11.8f, maxYBound - 21f));
        grassblock[s + 27].setPosition(new Vec2(minXBound + 14.8f, maxYBound - 21f));
        grassblock[s + 28].setPosition(new Vec2(minXBound + 17.8f, maxYBound - 21f));
    }

    public void putStoneTexture() {
        stoneblock = new StoneBlock[20];
        for (int x = 0; x < 20; x++) {
            stoneblock[x] = new StoneBlock(this);
            stoneblock[x].removeAllImages();
            stoneblock[x].addImage(new BodyImage("payload/Blocks/solid_block_lv2.png", 3.0f));
            stoneblock[x].addCollisionListener(new CollisionDetectorCreep(creep1, false));
            stoneblock[x].addCollisionListener(new CollisionDetectorCreep(creep2, false));
            //stoneblock[x].addCollisionListener(new CollisionDetectorBombExplosion(sceneSetup.getKeyboardController().getBombExplosion()));
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
        bottomGround.addCollisionListener(new CollisionDetectorCreep(creep3, false));

        //Top Ground
        topGroundShape = new BoxShape(25, 1f);
        topGround = new StaticBody(this, topGroundShape);
        topGround.setPosition(new Vec2(9.5f, 14.5f));
        topGround.setLineColor(Color.GRAY);
        topGround.addCollisionListener(new CollisionDetectorCreep(creep3, true));

        // Left Wall
        leftWallShape = new BoxShape(1f, 25);
        leftWallBody = new StaticBody(this, leftWallShape);
        leftWallBody.setPosition(new Vec2(-14.5f, 0));
        leftWallBody.setLineColor(Color.GRAY);
        leftWallBody.addCollisionListener(new CollisionDetectorCreep(creep1, false));
        leftWallBody.addCollisionListener(new CollisionDetectorCreep(creep2, false));
        leftWallBody.addCollisionListener(new CollisionDetectorBandit(bandit, false));
        leftWallBody.addCollisionListener(new CollisionDetectorDongo(dongo, false));

        //Right Wall
        rightWallShape = new BoxShape(1f, 25);
        rightWallBody = new StaticBody(this, rightWallShape);
        rightWallBody.setPosition(new Vec2(14.5f, 0));
        rightWallBody.setLineColor(Color.GRAY);
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep1, true));
        rightWallBody.addCollisionListener(new CollisionDetectorCreep(creep2, true));
        rightWallBody.addCollisionListener(new CollisionDetectorBandit(bandit, true));
        rightWallBody.addCollisionListener(new CollisionDetectorDongo(dongo, true));
    }

}
