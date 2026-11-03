// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


@Test
public class TestDmlColorModifiers
{
    @Test
    public void modify_DmlAlpha_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlAlpha(), 0.40, G_COLOR);
        //Assert
        msAssert.areEqual(102.0, result.getA());
    }

    @Test
    public void modify_DmlAlphaModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlAlphaModulation(), 2.0, G_COLOR);
        //Assert
        msAssert.areEqual(50.0, result.getA());
    }

    @Test
    public void modify_DmlAlphaOffset_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlAlphaOffset(), -G_COLOR.getA() / 255.0, G_COLOR);
        //Assert
        msAssert.areEqual(0.0, result.getA());
    }

    @Test
    public void modify_DmlShade_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlShade(), 0.25, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 0x32, 0x4e, 0x6a), result);
    }

    @Test
    public void modify_DmlTint_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlTint(), 0.3, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 0xdf, 0xe6, 0xf0), result);
    }

    @Test
    public void modify_DmlBlue_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlBlue(), 0.4, G_COLOR);
        //Assert
        msAssert.areEqual(170.0, result.getB());
    }

    @Test
    public void modify_DmlBlueModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlBlueModulation(), 0.5, G_COLOR);
        //Assert
        msAssert.areEqual(146.0, result.getB());
    }

    @Test
    public void modify_DmlBlueOffset_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlBlueOffset(), -G_COLOR.getB() / 255.0, G_COLOR);
        //Assert
        msAssert.areEqual(0.0, result.getB());
    }

    @Test
    public void modify_DmlRed_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlRed(), 0.4, G_COLOR);
        //Assert
        msAssert.areEqual(170.0, result.getR());
    }

    @Test
    public void modify_DmlRedModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlRedModulation(), 0.5, G_COLOR);
        //Assert
        msAssert.areEqual(71.0, result.getR());
    }

    @Test
    public void modify_DmlRedOffset_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlRedOffset(), -G_COLOR.getR() / 255.0, G_COLOR);
        //Assert
        msAssert.areEqual(0.0, result.getR());
    }

    @Test
    public void modify_DmlGreen_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlGreen(), 0.4, G_COLOR);
        //Assert
        msAssert.areEqual(170.0, result.getG());
    }

    @Test
    public void modify_DmlGreenModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlGreenModulation(), 0.5, G_COLOR);
        //Assert
        msAssert.areEqual(109, result.getG());
    }

    @Test
    public void modify_DmlGreenOffset_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlGreenOffset(), -G_COLOR.getG() / 255.0, G_COLOR);
        //Assert
        msAssert.areEqual(0.0, result.getG());
    }

    @Test
    public void modify_DmlInverse_ModifiedCorrectly()
    {
        //Arrange
        DmlInverse modificator = new DmlInverse();
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = new DrColor(
            G_COLOR.getA(),
            255 - G_COLOR.getR(),
            255 - G_COLOR.getG(),
            255 - G_COLOR.getB());
        msAssert.areEqual(expected, result);
    }

    @Test
    public void modify_DmlComplement_ModifiedCorrectly()
    {
        //Arrange
        DmlComplement modificator = new DmlComplement();
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = DrColor.fromArgb(0xC7, 0x96, 0x64);
        msAssert.areEqual(expected, result);
    }

    @Test
    public void modify_DmlHue_ModifiedCorrectly()
    {
        //Arrange
        DmlHue modificator = new DmlHue();
        modificator.setValue(DmlAngle.fromDegrees(45.0));
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = new DrColor(G_COLOR.getA(), 199, 175, 100);
        msAssert.areEqual(expected, result);
    }

    @Test
    public void modify_DmlHueOffset_ModifiedCorrectly()
    {
        //Arrange
        DmlHueOffset modificator = new DmlHueOffset();
        modificator.setValue(DmlAngle.fromDegrees(-110));
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = new DrColor(G_COLOR.getA(), 133, 199, 100);
        msAssert.areEqual(expected, result);
    }

    @Test
    public void modify_DmlHueModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlHueModulation(), 0.5, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 125, 199, 100), result);
    }

    @Test
    public void modify_DmlLuminance_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlLuminance(), 0.25, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 33, 63, 94), result);
    }

    @Test
    public void modify_DmlLuminanceModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlLuminanceModulation(), 0.25, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 19, 37, 55), result);
    }

    @Test
    public void modify_DmlLuminanceOffset_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlLuminanceOffset(), -0.09, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 66, 127, 187), result);
    }

    @Test
    public void modify_DmlSaturation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlSaturation(), 0.25, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 123, 149, 176), result);
    }

    @Test
    public void modify_DmlSaturationModulation_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlSaturationModulation(), 0.5, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 125, 149, 175), result);
    }

    /// <summary>
    /// WORDSNET-20638 Tests if the saturation modulation modifier is applied correctly.
    /// </summary>
    @Test
    public void modify_DmlSaturationModulation_A()
    {
        //Arrange
        DrColor[] sourceColors = new DrColor[]
        {
            new DrColor(68, 114, 196), // kleinBlueColor
            new DrColor(237, 125, 49), // cadmiumOrangeColor
            new DrColor(165, 165, 165), // quickSilverColor
            new DrColor(255, 192, 0), //amberColor
            new DrColor(230, 77, 25), //trinidadColor
            new DrColor(112, 173, 71) //rybGreenColor
        };

        DrColor[] expectedColors = new DrColor[]
        {
            new DrColor(4, 95, 255),
            new DrColor(255, 106, 0),
            new DrColor(165, 165, 165),
            new DrColor(255, 255, 0),
            new DrColor(255, 26, 0),
            new DrColor(101, 224, 19)
        };

        double[] satModValues = new double[] { 2.0 };

        //Assert
        assertArrayOfModifedColors(new DmlSaturationModulation(), satModValues, sourceColors, expectedColors);
    }

    @Test
    public void modify_DmlSaturationOffset_ModifiedCorrectly()
    {
        DrColor result = modifyColor(new DmlSaturationOffset(), -0.08, G_COLOR);
        //Assert
        msAssert.areEqual(new DrColor(G_COLOR.getA(), 108, 149, 191), result);
    }

    /// <summary>
    /// WORDSNET-20719 Tests if the saturation offset is correctly applied for <see cref="DrColor"/> with zero saturation.
    /// </summary>
    @Test
    public void modify_DmlSaturationOffset_ZeroSaturation()
    {
        // Color with zero saturation and lightness less than 50 (25%).
        DrColor capeCodeColor = new DrColor(64, 64, 64);
        // Color with zero saturation and lightness greater than 50 (64.7%).
        DrColor quickSilverColor = new DrColor(165, 165, 165);
        DrColor[] sourceColors = new DrColor[] { capeCodeColor, quickSilverColor };
        double[] satOffValues = new double[] { 0.5, 1.0, 1.5, 2.0 };

        DrColor[] expectedColors = new DrColor[]
        {
            // CapeCodeColor modifications.
            new DrColor (96, 32, 0),
            new DrColor (128, 0, 0),
            new DrColor (160, 0, 0),
            new DrColor (192, 0, 0),

            // QuickSilverColor modifications.
            new DrColor (210, 120, 0),
            new DrColor (254, 75, 0),
            new DrColor (255, 30, 0),
            new DrColor (255, 0, 0),
        };

        //Assert
        assertArrayOfModifedColors(new DmlSaturationOffset(), satOffValues, sourceColors, expectedColors);
    }

    @Test
    public void modify_DmlGray_ModifiedCorrectly()
    {
        //Arrange
        DmlGray modificator = new DmlGray();
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = new DrColor(G_COLOR.getA(), 140, 140, 140);
        msAssert.areEqual(expected, result);
    }

    @Test
    public void modify_DmlGamma_ModifiedCorrectly()
    {
        //Arrange
        DmlGamma modificator = new DmlGamma();
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = new DrColor(G_COLOR.getA(), 0xA8, 0xCA, 0xE5);
        msAssert.areEqual(expected, result);
    }

    @Test
    public void modify_DmlInverseGamma_ModifiedCorrectly()
    {
        //Arrange
        DmlInverseGamma modificator = new DmlInverseGamma();
        //Act
        DrColor result = modificator.modify(G_COLOR);
        //Assert
        DrColor expected = new DrColor(G_COLOR.getA(), 0x20, 0x4E, 0x93);
        msAssert.areEqual(expected, result);
    }

    /// <summary>
    /// Compares the array <see cref = "DrColor"/> with the applied modifiers with the expected array <see cref = "DrColor"/>.
    /// </summary>
    /// <param name="modType">The specified modifier</param>
    /// <param name="modValues">The array of the modifier values</param>
    /// <param name="sourceColors">The array of source <see cref="DrColor"/></param>
    /// <param name="expectedColors">The array of expected <see cref="DrColor"/></param>
    private static void assertArrayOfModifedColors(
        DmlPercentageBasedColorModifier modType,
        double[] modValues,
        DrColor[] sourceColors,
        DrColor[] expectedColors)
    {
        int indexOfExpectedColor = 0;

        for (int i = 0; i < sourceColors.length; i++)
        {
            for (int j = 0; j < modValues.length; j++)
            {
                double modificatorValue = modValues[j];
                DrColor result = modifyColor(modType, modificatorValue, sourceColors[i]);
                DrColor expectedColor = expectedColors[indexOfExpectedColor];
                msAssert.areEqual(expectedColor, result, msString.format("Expected ({0},{1},{2}) but was ({3},{4},{5}) ",
                       expectedColor.getR(), expectedColor.getG(), expectedColor.getB(), result.getR(), result.getG(), result.getB()));
                indexOfExpectedColor++;
            }
        }
    }

    private static DrColor modifyColor(DmlPercentageBasedColorModifier modifier, double value, DrColor color)
    {
        //Arrange
        modifier.setValue(value);
        //Act
        return modifier.modify(color);
    }

    private static /*final*/ DrColor G_COLOR = new DrColor(25, 100, 150, 200);
}

