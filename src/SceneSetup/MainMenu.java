/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneSetup;

import city.cs.engine.SoundClip;
import java.awt.FlowLayout;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 *
 * @author jalpd
 */
public class MainMenu extends javax.swing.JPanel {

    private javax.swing.JButton quitButton;
    private javax.swing.JLabel backgroundImage;
    private javax.swing.JLabel gameTitle;
    private javax.swing.JButton optionsButton;
    private javax.swing.JButton startBtn;
    private SceneSetup sceneSetup;

    public MainMenu(SceneSetup sceneSetup) {
        this.sceneSetup = sceneSetup;
        initComp();
    }

    public void initComp() {
        startBtn = new javax.swing.JButton();
        optionsButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        gameTitle = new javax.swing.JLabel();
        backgroundImage = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(600, 600));
        setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(null);

        startBtn.setBackground(new java.awt.Color(25, 66, 229));
        startBtn.setFont(new java.awt.Font("Gabriola", 1, 24));
        startBtn.setForeground(new java.awt.Color(0, 0, 0));
        startBtn.setText("Start");
        startBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        startBtn.setFocusPainted(false);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Pressed");
                sceneSetup.buttonClickedSound();
                sceneSetup.mainMenuMusic.stop();
                sceneSetup.startGame();
            }
        });

        add(startBtn);
        startBtn.setBounds(220, 220, 110, 40);

        optionsButton.setBackground(new java.awt.Color(25, 66, 229));
        optionsButton.setFont(new java.awt.Font("Gabriola", 1, 24));
        optionsButton.setForeground(new java.awt.Color(0, 0, 0));
        optionsButton.setText("Options");
        optionsButton.setFocusPainted(false);

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Option Pressed");
                sceneSetup.buttonClickedSound();
                sceneSetup.showOptionPanel();
            }
        });

        add(optionsButton);
        optionsButton.setBounds(220, 290, 110, 40);

        quitButton.setBackground(new java.awt.Color(25, 66, 229));
        quitButton.setFont(new java.awt.Font("Gabriola", 1, 24));
        quitButton.setForeground(new java.awt.Color(0, 0, 0));
        quitButton.setFocusPainted(false);
        quitButton.setText("Quit");

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneSetup.buttonClickedSound();
                System.exit(0);
            }
        });

        add(quitButton);
        quitButton.setBounds(220, 360, 110, 40);

        gameTitle.setIcon(new javax.swing.ImageIcon("payload/Backgrounds/title_titletext.png")); // NOI18N
        add(gameTitle);
        gameTitle.setBounds(-10, 10, 600, 360);

        backgroundImage.setIcon(new javax.swing.ImageIcon("payload/Backgrounds/main_background.jpg")); // NOI18N
        add(backgroundImage);
        backgroundImage.setBounds(0, 0, 570, 540);
    }

}
