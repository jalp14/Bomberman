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
import city.cs.engine.WorldView;

/**
 *
 * @author jalpd
 */
public class Door extends StaticBody {

    private static final Shape doorShape = new BoxShape(0.5f, 0.75f);
    private static final BodyImage doorImage = new BodyImage("payload/Door/Door_Close.png", 1.5f);

    public Door(World world) {
        super(world, doorShape);
        addImage(doorImage);
    }

}
