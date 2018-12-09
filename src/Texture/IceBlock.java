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
public class IceBlock extends StaticBody {

    private static final BodyImage iceBlockImage = new BodyImage("payload/Blocks/iceblock.png", 3.0f);
    private static final Shape iceBlockShape = new BoxShape(1.5f, 1.5f);

    public IceBlock(World mainScene) {
        super(mainScene, iceBlockShape);
        addImage(iceBlockImage);
    }

}
