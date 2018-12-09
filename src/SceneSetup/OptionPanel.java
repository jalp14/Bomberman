/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneSetup;

import city.cs.engine.SoundClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jalpd
 */
public class OptionPanel extends javax.swing.JPanel {

    private javax.swing.JButton backButton;
    private javax.swing.JLabel backgroundImage;
    private javax.swing.JButton restButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton instructionButton;
    private javax.swing.JLabel soundLevel;
    private javax.swing.JLabel sfxLevel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField userInput;
    private javax.swing.JLabel userName;
    private javax.swing.JSlider volumeSlider;
    private javax.swing.JSlider sfxSlider;
    private SceneSetup sceneSetup;
    private float volumeOffset = 1.0f;
    private float sfxOffset = 1.0f;
    public JDialog instructionBox;
    private FileIO fileManagement;

    public OptionPanel(SceneSetup sceneSetup) {
        this.sceneSetup = sceneSetup;
        fileManagement = new FileIO();
        fileManagement.readFromFile();
        initComp();

    }

    public void showInstructions() {
        instructionBox = new GameInstructions(sceneSetup, true);
        instructionBox.setSize(376, 279);
        instructionBox.setLocationByPlatform(true);
        instructionBox.setVisible(true);
    }

    public void initComp() {
        backButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        restButton = new javax.swing.JButton();
        instructionButton = new javax.swing.JButton();
        soundLevel = new javax.swing.JLabel();
        sfxLevel = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        userInput = new javax.swing.JTextField();
        volumeSlider = new javax.swing.JSlider();
        sfxSlider = new javax.swing.JSlider();
        titleLabel = new javax.swing.JLabel();
        backgroundImage = new javax.swing.JLabel();

        setLayout(null);

        backButton.setBackground(new java.awt.Color(25, 66, 229));
        backButton.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        backButton.setForeground(new java.awt.Color(0, 0, 0));
        backButton.setText("Back");
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneSetup.buttonClickedSound();
                sceneSetup.showParentPanel();
            }
        });

        add(backButton);
        backButton.setBounds(10, 480, 110, 40);

        saveButton.setBackground(new java.awt.Color(25, 66, 229));
        saveButton.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        saveButton.setForeground(new java.awt.Color(0, 0, 0));
        saveButton.setText("Save");
        saveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        saveButton.setFocusPainted(false);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneSetup.buttonClickedSound();
                fileManagement.saveToFile(userInput.getText(), volumeOffset, sfxOffset);
                System.out.println("Saved username and volume");
            }
        });

        add(saveButton);
        saveButton.setBounds(160, 290, 110, 40);

        restButton.setBackground(new java.awt.Color(25, 66, 229));
        restButton.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        restButton.setForeground(new java.awt.Color(0, 0, 0));
        restButton.setText("Reset ");
        restButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        restButton.setFocusPainted(false);

        add(restButton);
        restButton.setBounds(300, 290, 110, 40);

        instructionButton.setBackground(new java.awt.Color(25, 66, 229));
        instructionButton.setFont(new java.awt.Font("Gabriola", 1, 24));
        instructionButton.setForeground(new java.awt.Color(0, 0, 0));
        instructionButton.setText("Instruction");
        instructionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        instructionButton.setFocusPainted(false);
        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Showing instructions");
                sceneSetup.buttonClickedSound();
                showInstructions();
            }
        });

        add(instructionButton);
        instructionButton.setBounds(200, 350, 160, 40);

        userName.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        userName.setForeground(new java.awt.Color(255, 255, 255));
        userName.setText("Username");
        add(userName);
        userName.setBounds(140, 220, 110, 40);

        soundLevel.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        soundLevel.setForeground(new java.awt.Color(255, 255, 255));
        soundLevel.setText("Game Music");
        add(soundLevel);
        soundLevel.setBounds(140, 130, 110, 40);

        sfxLevel.setFont(new java.awt.Font("Ebrima", 1, 18));
        sfxLevel.setForeground(new java.awt.Color(255, 255, 255));
        sfxLevel.setText("SFX Level");
        add(sfxLevel);
        sfxLevel.setBounds(140, 175, 110, 40);

        sfxSlider.setBackground(new java.awt.Color(153, 0, 255));
        sfxSlider.setForeground(new java.awt.Color(255, 51, 102));
        sfxSlider.setValue(Math.round(fileManagement.getSfx() * 100));
        sfxSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        add(sfxSlider);
        sfxSlider.setBounds(270, 185, 200, 20);
        sfxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sfxOffset = sfxSlider.getValue() / 100f;
                if (sfxOffset == 0.0) {
                    sceneSetup.btnClickedSound.setVolume(0.001f);
                } else {
                    sceneSetup.btnClickedSound.setVolume(sfxOffset);
                }
            }
        });

        userInput.setBackground(new java.awt.Color(255, 255, 255));
        userInput.setToolTipText("");
        add(userInput);
        userInput.setBounds(270, 224, 200, 30);

        volumeSlider.setBackground(new java.awt.Color(153, 0, 255));
        volumeSlider.setForeground(new java.awt.Color(255, 51, 102));
        volumeSlider.setValue(Math.round(fileManagement.getVolume() * 100));
        volumeSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        add(volumeSlider);
        volumeSlider.setBounds(270, 140, 200, 20);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                volumeOffset = volumeSlider.getValue() / 100f;
                if (volumeOffset == 0.0) {
                    // volumeOffset = 0;
                    sceneSetup.mainMenuMusic.setVolume(0.001f);
                } else {
                    sceneSetup.mainMenuMusic.setVolume(volumeOffset);
                }
            }
        });

        titleLabel.setFont(new java.awt.Font("Harrington", 1, 48)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Options");
        titleLabel.setToolTipText("");
        add(titleLabel);
        titleLabel.setBounds(180, 20, 190, 70);

        backgroundImage.setIcon(new javax.swing.ImageIcon("payload/Backgrounds/main_background.jpg")); // NOI18N
        add(backgroundImage);
        backgroundImage.setBounds(0, 0, 576, 576);
    }

}
