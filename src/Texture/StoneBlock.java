/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Texture;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Fixture;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 *
 * @author jalpd
 */
public class StoneBlock extends StaticBody {

    private static final Shape stoneShape = new BoxShape(1.5f, 1.5f);
    private static final BodyImage stoneImage = new BodyImage("payload/Blocks/stone_texture.png", 3.0f);

    public StoneBlock(World mainScene) {
        super(mainScene, stoneShape);
        addImage(stoneImage);
    }
}
