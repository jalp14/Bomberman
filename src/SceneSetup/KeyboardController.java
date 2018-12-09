/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneSetup;

import CollisionDetection.CollisionDetectorBombExplosion;
import Sprite.Bomb;
import Sprite.BombExplosion;
import Sprite.Bomberman;
import TimeManagement.TimerMan;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import org.jbox2d.common.Vec2;

/**
 * Sets up keyboard controls for the game
 * @author jalpd
 */
public class KeyboardController extends KeyAdapter implements ActionListener {

    private InputMap inputMap;
    private ActionMap actionMap;
    private Bomberman bomberWalker;
    private WorldView sceneView;
    private World bomberWorld;
    private BombExplosion bombExplosion;
    private Timer pickupTimer;
    private Bomb bombUp;

    /**
     * Initialised the keyboard controls
     * @param bomberWalker the current player
     * @param sceneView the current game view
     */
    public KeyboardController(Bomberman bomberWalker, WorldView sceneView) {
        this.bomberWalker = bomberWalker;
        this.sceneView = sceneView;
        bomberWorld = bomberWalker.getWorld();
        bomberWalker.setPosition(new Vec2(5, 0));
    }

    /**
     * Starts the timer for the bomb
     */
    public void startTimer() {
        pickupTimer = new Timer(1000, this);
        pickupTimer.setRepeats(false);
        pickupTimer.setInitialDelay(3000);
        pickupTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Timer Over");
        explodeBomb();
    }

    /**
     * Sets up explosions around the bomb
     */
    public void explodeBomb() {

        bombUp.createExplosion(new Vec2(bombUp.getPosition().x, bombUp.upOffset + 1.2f));
        bombUp.getExplosion().addCollisionListener(new CollisionDetectorBombExplosion(bombUp.getExplosion(), bombUp));

        bombUp.createExplosion(new Vec2(bombUp.leftOffset - 1.2f, bombUp.getPosition().y));
        bombUp.getExplosion().addCollisionListener(new CollisionDetectorBombExplosion(bombUp.getExplosion(), bombUp));

        bombUp.createExplosion(new Vec2(bombUp.rightOffset + 1.2f, bombUp.getPosition().y));
        bombUp.getExplosion().addCollisionListener(new CollisionDetectorBombExplosion(bombUp.getExplosion(), bombUp));

        bombUp.createExplosion(new Vec2(bombUp.getPosition().x, bombUp.downOffset - 1.2f));
        bombUp.getExplosion().addCollisionListener(new CollisionDetectorBombExplosion(bombUp.getExplosion(), bombUp));
    }

    /**
     * Sets up the bomb
     */
    public void setupBomb() {
        bombUp = new Bomb(bomberWorld);
        bombUp.setPosition(bomberWalker.getPosition());
        bombUp.setExplosionPosition(bombUp.getPosition());

    }

    /**
     * Sets up individual key controls 
     */
    public void keyBindings() {
        inputMap = this.sceneView.getInputMap(JComboBox.WHEN_IN_FOCUSED_WINDOW);
        actionMap = this.sceneView.getActionMap();

        // Key Pressed
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A Pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D Pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "S Pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W Pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Space Pressed");

        // Key Released
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A Released");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D Released");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S Released");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W Released");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "Space Released");

        // Key Pressed Events
        actionMap.put("A Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.startWalking(-bomberWalker.getBombermanSpeed());
                bomberWalker.startMovingLeft();
                System.out.println("A Pressed");
            }
        });

        actionMap.put("D Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.startWalking(bomberWalker.getBombermanSpeed());
                bomberWalker.startMovingRight();
                System.out.println("D Pressed");
            }
        });

        actionMap.put("S Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.jump(-bomberWalker.getBombermanJumpPower());
                bomberWalker.startMovingBack();
                System.out.println("S Pressed");
            }
        });

        actionMap.put("W Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.jump(bomberWalker.getBombermanJumpPower());
                bomberWalker.startMovingFront();
                System.out.println("W Pressed");
            }
        });

        actionMap.put("Space Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupBomb();
                startTimer();
            }
        });

        // Key Released Actions
        actionMap.put("A Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.stopWalking();
                bomberWalker.stopMovingLeft();
                bomberWalker.setLinearVelocity(new Vec2(0, 0));
            }
        });

        actionMap.put("D Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.stopWalking();
                bomberWalker.stopMovingRight();
                bomberWalker.setLinearVelocity(new Vec2(0, 0));
            }
        });

        actionMap.put("S Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // bomberWalker.jump(-2);
                bomberWalker.stopMovingBack();
                bomberWalker.setLinearVelocity(new Vec2(0, 0));;
            }
        });

        actionMap.put("W Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomberWalker.setLinearVelocity(new Vec2(0, 0));
                bomberWalker.stopMovingFront();
            }
        });

        actionMap.put("Space Released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    /**
     * Encapsulation for private field
     * @return returns the current instance of bomb explosions 
     */
    public BombExplosion getBombExplosion() {
        return bombExplosion;
    }

}
