package SceneSetup;

import CollisionDetection.CollisionDetectorBomberman;
import Sprite.Bomberman;
import Texture.Door;
import TimeManagement.TimerMan;
import city.cs.engine.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;

/**
 * Base configuration for the game
 * @author jalpd
 */
public abstract class WorldSetup extends World {

    private float minXBound = -14.5f;
    private float minYBound = -14.5f;
    private float maxXBound = 14.5f;
    private float maxYBound = 14.5f;
    private int y = 0;
    private int b = 0;
    private int s = 0;
    private int q = 0;
    private Door door;

    /**
     * The Player
     */
    public Bomberman bomberman;

    /**
     * The virtual body of the player
     */
    public Body bottomGround;
    int currentScore;
    private SceneSetup sceneSetup;

    /**
     * Sets up the basic world
     * @param sceneSetup Main game scene
     */
    public void setupWorld(SceneSetup sceneSetup) {
        makeBorders();
        this.sceneSetup = sceneSetup;
        bomberman = new Bomberman(this);
        bomberman.setPosition(new Vec2(-3, 10));
        bomberman.getPosition();
        bomberman.addCollisionListener(new CollisionDetectorBomberman(bomberman, sceneSetup));
    }

    /**
     * Abstract method to have creepers in every level
     */
    public abstract void setupCreepers();

    /**
     * Abstract method to have door in every level
     */
    public abstract void setupDoor();

    /**
     * Abstract method to have borders in every level
     */
    public abstract void makeBorders();

    /**
     * Abstract method to have sound in every level
     */
    public abstract void setupSound();

    /**
     * Abstract method to start sound in every level
     */
    public abstract void startSound();

    /**
     * Abstract method to stop sound in every level
     */
    public abstract void stopSound();

    // public abstract boolean isLevelCompleted();

    /**
     * 
     * @return current instance of the door
     */
    public Door getDoor() {
        return door;
    }

    /**
     * Abstract method to have key in every level
     */
    public abstract void collectKey();

    /**
     *
     * @return returns current instance of the bomberman
     */
    public Bomberman getBomberman() {
        return bomberman;
    }

}
