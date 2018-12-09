/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneSetup;

import Sprite.Bomberman;
import city.cs.engine.UserView;
import city.cs.engine.World;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Allows to have image as a background in game
 * @author jalpd
 */
public class BackgroundSetup extends UserView {

    Bomberman bomberman;

    private Image backgroundImage;
    private String imagePath;

    public BackgroundSetup(World world, int width, int height, String imagePath) {
        super(world, width, height);
        // this.bomberman = bomberman;
        this.imagePath = imagePath;
        this.backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        // N/A
    }

}
