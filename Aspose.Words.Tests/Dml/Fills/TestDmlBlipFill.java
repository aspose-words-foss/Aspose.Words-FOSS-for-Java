// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.MathUtil;


@Test
public class TestDmlBlipFill
{
    @Test
    public void deepClone()
    {
        DmlBlipFill fill = new DmlBlipFill();
        //Arrange
        fill.getBlip().setEmbedImage(new byte[] { (byte) 192 });
        DmlBlipFillStretch blipFillStretch = new DmlBlipFillStretch();
        fill.setBlipFillMode(blipFillStretch);
        blipFillStretch.setFillRectangle(new DmlPercentageOffsetRectangle(1.0,2.0,3.0,4.0));
        fill.setSourceRectangle(new DmlPercentageOffsetRectangle(5.0, 6.0, 7.0, 8.0));
        //Act
        DmlBlipFill result = (DmlBlipFill)fill.deepClone();
        //Assert
        msAssert.areEqual(new byte[] { (byte) 192 }, result.getBlip().getEmbedImage());
        msAssert.areEqual(4, ((DmlBlipFillStretch)result.getBlipFillMode()).getFillRectangle().getBottomOffset());
        msAssert.areEqual(8, result.getSourceRectangle().getBottomOffset());
    }

    /// <summary>
    /// Relates with WORDSNET-16359
    /// Checks obtaining and setting opacity for blip fill.
    /// </summary>
    @Test
    public void testOpacity()
    {
        DmlBlipFill blipFill = new DmlBlipFill();
        ArrayList<DmlEffect> effects = blipFill.getBlip().getEffects();

        // 1. There is not alpha modulate fixed effect.
        msAssert.areEqual(0, effects.size());
        msAssert.isTrue(MathUtil.areEqual(1.0, blipFill.getOpacity()));

        // 2. Check, that setter appends alpha modulate fixed effect to effects collection.
        blipFill.setOpacity(0.7);
        msAssert.areEqual(1, effects.size());
        msAssert.isTrue(MathUtil.areEqual(0.7, ((DmlAlphaModulateFixedEffect)effects.get(0)).getAmount()));

        // 3. Setter changes last alpha modulate fixed effect in the effects sequence.
        effects.add(new DmlBlurEffect());
        DmlAlphaModulateFixedEffect newAplhaFixed = new DmlAlphaModulateFixedEffect();
        newAplhaFixed.setAmount(0.5);
        effects.add(newAplhaFixed);
        effects.add(new DmlDuotoneEffect());

        blipFill.setOpacity(0.75);
        msAssert.areEqual(4, effects.size());
        msAssert.isTrue(MathUtil.areEqual(0.7, ((DmlAlphaModulateFixedEffect)effects.get(0)).getAmount()));
        msAssert.isTrue(MathUtil.areEqual(0.75, ((DmlAlphaModulateFixedEffect)effects.get(2)).getAmount()));

        // 4. Getter obtains last alpha modulate fixed effect in the effects sequence.
        msAssert.isTrue(MathUtil.areEqual(0.75, blipFill.getOpacity()));
        msAssert.areEqual(4, effects.size());
    }
}
