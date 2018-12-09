/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneSetup;

/**
 *
 * @author jalpd
 */
import Levels.Level1;
import Levels.Level2;
import Levels.Level3;
import Sprite.Bomberman;
import TimeManagement.TimerMan;
import javax.swing.*;

import city.cs.engine.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jalpd
 */
public class SceneSetup extends javax.swing.JFrame {

    private WorldSetup mainScene;
    private UserView mainView;
    private Bomberman bomberman;
    private GridBagConstraints gbc = new GridBagConstraints();

    // Create a split panel
    private JSplitPane splitPane;
    private JPanel statusPanel;
    private JSplitPane splitPaneStatus;
    JPanel pausePanel;

    // Create MainMenu view
    private JPanel mainMenuPanel;

    private JLabel gameStatus = new JLabel();
    private JLabel gameScore = new JLabel();

    // IO Stuff
    FileWriter fileWriter;
    PrintWriter printWriter;

    // Progress Bar
    private JProgressBar healthBar = new JProgressBar();

    // Debugger View
    JFrame debugView;

    // Current Crepeer Position
    float creepPosX;
    float creepPosY;

    // Pause Menu buttons
    JButton pauseButton;
    JButton resetButton;
    boolean counter = false;

    /**
     * Checks whether the door is open or close
     */
    public boolean isDoorOpen = false;

    /**
     * Checks whether the key is obtained by the bomberman
     */
    public boolean isKeyObtained = false;

    int setLevel;

    /**
     *
     */
    public int healtlthFromSaveFile;
    private int currentHealth = 100;

    JFrame mainFrame;

    private JPanel controlPanel;
    private JPanel optionPanel;
    private CardLayout c1;

    /**
     *
     */
    public SoundClip mainMenuMusic;

    /**
     *
     */
    public SoundClip btnClickedSound;
    private FileIO gameData;

    private JDialog gameOver;

    // Drop Down Menu
    String[] levelNames = {"Select Item", "Level 1", "Level 2", "Level 3"};
    JComboBox changeLevels = new JComboBox(levelNames);

    // KeyBindings
    KeyboardController keyboardController;

// Init a new scene

    /**
     * Initialises the game
     */
    public SceneSetup() {
        // Setup Buttons
        setupButtons();
        gameData = new FileIO();
        gameData.readFromFile();
        //Setup Layout
        showMainMenu();
    }

    /**
     * Initialises the GameOver dialog
     */
    public void showGameOverDialog() {
        gameOver = new GameOver(this, true);
        gameOver.setLocationByPlatform(true);
        gameOver.setSize(376, 279);
        gameOver.setVisible(true);
    }

    /**
     * Switches the panel to show the Option panel
     */
    public void showOptionPanel() {
        c1.show(controlPanel, "option");
    }

    /**
     * Switches the panel to show the MainMenu panel
     */
    public void showParentPanel() {
        c1.show(controlPanel, "main");
    }

    /**
     * Sets up the sound for MainMenu
     */
    public void setupSound() {
        try {
            mainMenuMusic = new SoundClip("payload/GameSounds/MainMenu/mainmenu.wav");
            mainMenuMusic.loop();
            mainMenuMusic.setVolume(gameData.getVolume());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     *  Encapsulation to retrieve private variable
     * @return returns the FileIO instance to allow other classes to read from file
     */
    public FileIO getGameData() {
        return gameData;
    }

    /**
     * sets up the click sound for buttons 
     */
    public void buttonClickedSound() {
        try {
            btnClickedSound = new SoundClip("payload/GameSounds/buttonClick.wav");
            btnClickedSound.play();
            btnClickedSound.setVolume(gameData.getSfx());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     * Sets up the main menu and adds it to the main frame
     */
    public void showMainMenu() {
        mainFrame = new JFrame();
        setupSound();
        c1 = new CardLayout();
        controlPanel = new JPanel(c1);
        mainMenuPanel = new MainMenu(this);
        optionPanel = new OptionPanel(this);
        controlPanel.add(mainMenuPanel);
        c1.addLayoutComponent(mainMenuPanel, "main");
        controlPanel.add(optionPanel);
        c1.addLayoutComponent(optionPanel, "option");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationByPlatform(true);
        mainFrame.setPreferredSize(new Dimension(560, 560));
        mainFrame.add(controlPanel);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * Replaces main menu with the actual game view
     */
    public void startGame() {
        mainFrame.removeAll();
        mainFrame.dispose();
        setupGameUI();
    }

    /**
     * Closes the game
     */
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Sets up the game view and adds it to the main frame
     */
    public void setupGameUI() {
        // Init Level 1
        setLevel = 1;
        mainScene = new Level1();
        mainScene.setupWorld(this);
        mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg_1.png");
        mainFrame = new JFrame("Bomberman");
        splitPane = new JSplitPane();
        setupBottomPanel();

        // Top Split Pane
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(600);
        splitPane.setTopComponent(mainView);
        splitPane.setBottomComponent(splitPaneStatus);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationByPlatform(true);
        mainFrame.setPreferredSize(new Dimension(600, 750));
        getContentPane().setLayout(new GridLayout());
        mainFrame.add(splitPane);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainScene.start();
        mainScene.setGravity(0);
        mainView.requestFocus();
        setupKeyboard();
    }

    /**
     * Sets up the drop down menu and adds option to change levels
     */
    public void setupDropDownMenu() {
        changeLevels.setSelectedIndex(0);
        changeLevels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected Item is " + changeLevels.getSelectedItem());
                if (changeLevels.getSelectedIndex() == 1) {
                    setLevel = 1;
                    jumpLevel();
                } else if (changeLevels.getSelectedIndex() == 2) {
                    setLevel = 2;
                    jumpLevel();
                } else if (changeLevels.getSelectedIndex() == 3) {
                    setLevel = 3;
                    jumpLevel();
                }
            }
        });
    }

    /**
     * Sets up the bottom panel to be shown under the game
     */
    public void setupBottomPanel() {
        statusPanel = new JPanel(new GridBagLayout());
        splitPaneStatus = new JSplitPane();
        pausePanel = new JPanel();

        // Drop down menu management
        setupDropDownMenu();
        pausePanel.add(changeLevels);

        // Add buttons to the pause panel
        pausePanel.add(pauseButton);
        pausePanel.add(resetButton);

        gameStatus.setText("Game Running");
        gameStatus.setBackground(Color.black);
        gbc.gridx = 0;
        gbc.gridy = 0;
        statusPanel.add(gameStatus, gbc);
        healthBar.setString("100");
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.green);
        healthBar.setValue(100);
        healthBar.setMaximum(100);
        gbc.gridx = 0;
        gbc.gridy = 2;
        statusPanel.add(healthBar, gbc);

        // Bottom Split Pane
        splitPaneStatus.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneStatus.setDividerLocation(300);
        splitPaneStatus.setTopComponent(statusPanel);
        splitPaneStatus.setBottomComponent(pausePanel);

    }

    /**
     * Sets up the button for the main game
     */
    public void setupButtons() {
        // Init Buttons
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        // Add Listener to buttons
        pauseButton.setBackground(new java.awt.Color(25, 66, 229));
        pauseButton.setFont(new java.awt.Font("Gabriola", 1, 24));
        pauseButton.setForeground(new java.awt.Color(0, 0, 0));
        pauseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pauseButton.setFocusPainted(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter == false) {
                    pauseButton.setText("Start");
                    stopSimulation();
                    counter = true;
                    System.out.println("Pause Pressed");
                } else {
                    pauseButton.setText("Pause");
                    startSimulation();
                    counter = false;
                    System.out.println("Start Pressed");
                }

            }
        });

        resetButton.setBackground(new java.awt.Color(25, 66, 229));
        resetButton.setFont(new java.awt.Font("Gabriola", 1, 24));
        resetButton.setForeground(new java.awt.Color(0, 0, 0));
        resetButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSimulation();
                System.out.println("Reset Pressed");
            }
        });

    }

    /**
     * Sets up the keyboard controls
     */
    public void setupKeyboard() {
        keyboardController = new KeyboardController(mainScene.getBomberman(), mainView);
        keyboardController.keyBindings();
    }

    /**
     * Allows user to jump between levels
     */
    public void jumpLevel() {
        stopSimulation();
        if (setLevel == 3) {
            System.out.println("Init Level 3");
            mainScene = new Level3();
            mainScene.setupWorld(this);
            mainScene.setGravity(0);
            mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg.png");
            mainView.setWorld(mainScene);
            setupKeyboard();
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane.setDividerLocation(600);
            splitPane.setTopComponent(mainView);
            splitPane.setBottomComponent(splitPaneStatus);
            //   debugView = new DebugViewer(mainScene, 600, 600);
            splitPane.setTopComponent(mainView);
            mainScene.start();
            resetProgressBar();
            resetGameStatus();

        } else if (setLevel == 2) {
            System.out.println("Init Level 2");
            setLevel++;
            mainScene = new Level2();
            mainScene.setupWorld(this);
            mainScene.setGravity(0);
            mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg_2.png");
            mainView.setWorld(mainScene);
            setupKeyboard();
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane.setDividerLocation(600);
            splitPane.setTopComponent(mainView);
            splitPane.setBottomComponent(splitPaneStatus);
            //   debugView = new DebugViewer(mainScene, 600, 600);
            splitPane.setTopComponent(mainView);
            mainScene.start();
            resetProgressBar();
            resetGameStatus();
        } else if (setLevel == 1) {
            System.out.println("Init Level 1");
            setLevel++;
            mainScene = new Level1();
            mainScene.setupWorld(this);
            mainScene.setGravity(0);
            mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg_1.png");
            mainView.setWorld(mainScene);
            setupKeyboard();
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane.setDividerLocation(600);
            splitPane.setTopComponent(mainView);
            splitPane.setBottomComponent(splitPaneStatus);
            //   debugView = new DebugViewer(mainScene, 600, 600);
            splitPane.setTopComponent(mainView);
            resetProgressBar();
            resetGameStatus();
            mainScene.start();
            mainView.requestFocus();
        }
    }

    /**
     * Encapsulation for private field
     * @return Returns the current instance of the Bomberman
     */
    public Bomberman getBomberman() {
        return mainScene.getBomberman();
    }

    /**
     * Goes to the next level when the  current level is finished
     */
    public void changeLevel() {
        // Get the current Health
        stopSimulation();
        mainScene.stopSound();
        if (setLevel == 3) {
            showGameOverDialog();
        } else if (setLevel == 2) {
            System.out.println("Progress to Level 3");
            setLevel++;
            mainScene = new Level3();
            mainScene.setupWorld(this);
            mainScene.setGravity(0);
            mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg.png");
            mainView.setWorld(mainScene);
            setupKeyboard();
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane.setDividerLocation(600);
            splitPane.setTopComponent(mainView);
            splitPane.setBottomComponent(splitPaneStatus);
            splitPane.setTopComponent(mainView);
            gameStatus.setText("Game Running");
            mainScene.start();
            mainView.requestFocus();
            mainScene.bomberman.setBombermanHealth(currentHealth);
        } else if (setLevel == 1) {
            System.out.println("Progress to Level 2");
            setLevel++;
            mainScene = new Level2();
            mainScene.setupWorld(this);
            mainScene.setGravity(0);
            mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg_2.png");
            mainView.setWorld(mainScene);

            setupKeyboard();
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane.setDividerLocation(600);
            splitPane.setTopComponent(mainView);
            splitPane.setBottomComponent(splitPaneStatus);
            gameStatus.setText("Game Running");
            mainScene.start();
            mainView.requestFocus();
            System.out.println(currentHealth);
            bomberman.setBombermanHealth(currentHealth);
            System.out.println("Current Health is " + mainScene.bomberman.getBombermanHealth());
        }
    }

    /**
     * Resets progress bar 
     */
    public void resetProgressBar() {
        // healthBar.setValue(this.getBomberman().getBombermanHealth());
        //  healthBar.setString(Integer.toString(this.getBomberman().getBombermanHealth()));
        healthBar.setValue(100);
        healthBar.setForeground(Color.green);
        healthBar.setString("100");
    }

    /**
     * Resets the game status
     */
    public void resetGameStatus() {
        gameStatus.setText("Game Running");
        currentHealth = 100;
//        bomberman.setBombermanHealth(100);
        setLevel = 1;
    }

    /**
     * Resumes the game if paused
     */
    public void startSimulation() {
        mainScene.startSound();
        mainScene.start();
    }

    /**
     * Pauses the game
     */
    public void stopSimulation() {
        mainScene.stopSound();
        mainScene.stop();
    }

    /**
     * Restarts the game
     */
    public void resetSimulation() {
        splitPane.remove(mainView);
        mainScene.stopSound();
        mainView = null;
        mainScene = null;
        mainScene = new Level1();
        mainScene.setupWorld(this);
        mainView = new BackgroundSetup(mainScene, 600, 800, "payload/Backgrounds/bkg.png");
        splitPane.setTopComponent(mainView);
        mainScene.setGravity(0);
        mainScene.start();
        setupKeyboard();
        resetGameStatus();
        resetProgressBar();
        mainView.setWorld(mainScene);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(600);
        splitPane.setTopComponent(mainView);
        splitPane.setBottomComponent(splitPaneStatus);
    }

    /**
     * Encapsulation for private field
     * @return returns the current instance of the game world
     */
    public World getMainScene() {
        return mainScene;
    }

    /**
     * Encapsulation for private field
     * @return returns the current instance of the game view
     */
    public UserView getMainView() {
        return mainView;
    }

    /**
     * Encapsulation for private field
     * @return returns the current instance of the keyboard controller
     */
    public KeyboardController getKeyboardController() {
        return keyboardController;
    }

    /**
     * Encapsulation for private field
     * @return returns the current health of the bomberman
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Encapsulation for private field
     * @param sets the current health 
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    /**
     * Encapsulation for private field
     * @param reduceBy reduce the current health by a factor 
     */
    public void reduceBombermanHealth(int reduceBy) {
        currentHealth = currentHealth - reduceBy;
    }

    /**
     * Encapsulation for private field
     * @param bomberman sets the bomberman in the game
     */
    public void setBomberman(Bomberman bomberman) {
        this.bomberman = bomberman;
        System.out.println("Bomberman set");
    }

    /**
     * Updates the health bar 
     */
    public void updateStatus() {
        if (bomberman.getBombermanHealth() > 50) {
            healthBar.setForeground(Color.green);
        }
        if (bomberman.getBombermanHealth() <= 50) {
            healthBar.setForeground(Color.orange);
        }
        if (bomberman.getBombermanHealth() <= 20) {
            healthBar.setForeground(Color.red);
        }
        healthBar.setString(" " + currentHealth);
        healthBar.setValue(currentHealth);
    }

    /**
     * Keeps track of whether level is finished or not 
     */
    public void levelFinished() {
        gameStatus.setText("Bomberman touched the door. Level Finsished. Congratulations!!!!");
        isDoorOpen = false;
        isKeyObtained = false;
    }

    /**
     * 
     * @param args default args of the main method
     */
    public static void main(String[] args) {
        new SceneSetup();
    }
}
