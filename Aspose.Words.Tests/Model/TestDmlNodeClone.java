// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;


/// <summary>
/// Test DmlNode cloning.
/// </summary>
@Test
public class TestDmlNodeClone
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testDmlNodesClone()
    {
        DmlPicture dmlPicture = new DmlPicture();
        DmlPicture clonedPic = (DmlPicture)dmlPicture.deepClone(true, null);
        msAssert.areNotSame(dmlPicture, clonedPic);

        DmlShape dmlShape = new DmlShape(DmlNodeType.SHAPE);
        DmlShape clonedShape = (DmlShape)dmlShape.deepClone(true, null);
        msAssert.areNotSame(dmlShape, clonedShape);

        DmlLockedCanvas dmlLockedCanvas = new DmlLockedCanvas(DmlNodeType.LOCKED_CANVAS);
        DmlLockedCanvas clonedLoc = (DmlLockedCanvas)dmlLockedCanvas.deepClone(true, null);
        msAssert.areNotSame(dmlLockedCanvas, clonedLoc);

        DmlGroupShape dmlGroupShape = new DmlGroupShape(DmlNodeType.GROUP_SHAPE);
        DmlGroupShape clonedGroupShape = (DmlGroupShape)dmlGroupShape.deepClone(true, null);
        msAssert.areNotSame(dmlGroupShape, clonedGroupShape);

        DmlBlipFill dmlBlipFill = new DmlBlipFill();
        dmlBlipFill.setBlip(new DmlBlip());
        dmlBlipFill.setBlipFillMode(new DmlBlipFillTile());
        DmlBlipFill clonedBlipFill = (DmlBlipFill)dmlBlipFill.deepClone();
        msAssert.areNotSame(dmlBlipFill, clonedBlipFill);
    }

    @Test
    public void testDmlRotation3DClone()
    {
        DmlRotation3D rotation3D = new DmlRotation3D();

        rotation3D.setLatitude(DmlAngle.fromDegrees(50.0));
        rotation3D.setLongitude(DmlAngle.fromDegrees(100.0));
        rotation3D.setRevolution(DmlAngle.fromDegrees(150.0));

        DmlRotation3D cloned = rotation3D.deepClone();

        msAssert.areNotSame(rotation3D, cloned);

        msAssert.areEqual(50, cloned.getLatitude().getValueInDegrees());
        msAssert.areEqual(100, cloned.getLongitude().getValueInDegrees());
        msAssert.areEqual(150, cloned.getRevolution().getValueInDegrees());
    }

    @Test
    public void testDmlEffectsClone()
    {
        DmlAlphaBiLevelEffect effect = new DmlAlphaBiLevelEffect();
        
        // Change something in the original object.
        effect.setThreshold(40.0d);
        
        DmlAlphaBiLevelEffect cloned = (DmlAlphaBiLevelEffect)effect.deepClone();

        effect.setThreshold(20.0d);
        
        // Check values in the original and cloned objects.
        msAssert.areEqual(40.0d, cloned.getThreshold());
        msAssert.areEqual(20.0d, effect.getThreshold());

        msAssert.areNotSame(effect, cloned);
    }

    @Test
    public void testDmlBlipFillClone()
    {
        DmlBlipFill blipFill = new DmlBlipFill();
        blipFill.setRotateWithShape(true);
        blipFill.setBlip(new DmlBlip());
        blipFill.getBlip().setImageLink("TestLinkedPictureReference");

        ArrayList<DmlEffect> effects = new ArrayList<DmlEffect>();
        DmlColorChangeEffect dmlColor = new DmlColorChangeEffect();
        dmlColor.setConsiderAlphaValues(true);
        effects.add(dmlColor);
        effects.add(new DmlAlphaInverseEffect());

        blipFill.getBlip().setEffects(effects);

        DmlBlipFill cloned = (DmlBlipFill)blipFill.deepClone();

        blipFill.setRotateWithShape(false);
        msAssert.areEqual(2, cloned.getBlip().getEffects().size());

        // Check values in the original and cloned objects.
        msAssert.isTrue(cloned.getRotateWithShape());
        msAssert.isFalse(blipFill.getRotateWithShape());
        msAssert.areNotSame(blipFill.getBlip(), cloned.getBlip());
        msAssert.areNotSame(blipFill.getBlip().getEffects(), cloned.getBlip().getEffects());
        msAssert.areNotSame(blipFill, cloned);
    }

}

