/*
 * ParallelEventGateway.fx
 *
 * Created on 13/02/2010, 11:24:44 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
/**
 * @author javier.solis
 */

public class ParallelStartEventGateway extends EventBasedGateway
{
    public override function create(): Node
    {
        initializeCustomNode();
        w=50;
        h=50;
        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            styleClass: "gateway"
        };

        var colorAdjust=ColorAdjust
        {
            hue:0.25
            brightness:0.28
            contrast:0.25
            saturation:1
        }

        var message=ImageView
        {
            styleClass: "modifierParallel"
            x: 16
            y: 16
            scaleX: 1.1
            scaleY: 1.1
            effect: colorAdjust
        };

        return Group
        {
            content: [
                shape, Circle
                {
                    centerX: w/2
                    centerY: h/2
                    radius: w/3.5
                    styleClass: "modifierGateway2"
                },
                message
            ]
            translateX: bind x - w/2
            translateY: bind y - w/2
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }
}