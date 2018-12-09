/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Texture;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 *
 * @author jalpd
 */
public class ExplodableBlock extends StaticBody {

    private static final BodyImage explodableBlockImage = new BodyImage("payload/Blocks/explodable_block.png", 2.0f);

    private static final Shape explodableBlockShape = new BoxShape(1.5f, 1.5f);

    public ExplodableBlock(World mainScene) {
        super(mainScene, explodableBlockShape);
        addImage(explodableBlockImage);
    }
}
