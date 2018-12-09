/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Texture;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 *
 * @author jalpd
 */
public class GrassBlock extends StaticBody {

    private static final BodyImage grassImage = new BodyImage("payload/Blocks/grass_texture.png", 2.5f);

    private static final Shape grass = new BoxShape(1.4f, 1.4f);

    public GrassBlock(World mainScene) {
        super(mainScene, grass);
        addImage(grassImage);
    }

}
