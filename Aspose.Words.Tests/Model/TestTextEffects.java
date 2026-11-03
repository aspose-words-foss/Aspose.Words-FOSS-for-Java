// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/05/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestTextEffects
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testTextEffectsMainBody() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectsMainBody.docx");
    }

    @Test
    public void testTextEffectFillMainBody() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectFillMainBody.docx");
    }

    @Test
    public void testTextEffectOutlineMainBody() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectOutlineMainBody.docx");
    }

    @Test
    public void testTextEffectsSmartArt() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectsSmartArt.docx");
    }

    @Test
    public void testTextEffectFillSmartArt() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectFillSmartArt.docx");
    }

    @Test
    public void testTextEffectOutlineSmartArt() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectOutlineSmartArt.docx");
    }

    /// <summary>
    /// Tests the opening and saving of a file with text with a zero-width outline. See WORDSNET-17103
    /// </summary>
    @Test
    public void testTextEffectsOutlineZeroWidth() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestTextEffectsOutlineZeroWidth.docx");
    }

    @Test (enabled = false)
    public void testJira7723() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestJira7723.docx");
    }


    @Test
    public void testJira9175() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestJira9175.docx");
    }


    @Test
    public void testUnderline() throws Exception
    {
        TestUtil.openSaveOpen("Model\\TextEffects\\TestUnderline.docx");
    }




    /// <summary>
    /// WORDSNET-14297 When DOCX is opened, text effect font properties remain unfilled.
    /// HasDmlEffect method have been added to check if particular Dml text effect is applied.
    /// </summary>
    @Test
    public void test14297() throws Exception
    {
        Document doc = TestUtil.open("Model\\TextEffects\\Test14297.docx");

        RunCollection runs = doc.getFirstSection().getBody().getFirstParagraph().getRuns();

        Font runFont = runs.get(23).getFont();

        // One run might have several Dml text effects applied.
        msAssert.isTrue(runFont.hasDmlEffect(TextDmlEffect.SHADOW));
        msAssert.isTrue(runFont.hasDmlEffect(TextDmlEffect.EFFECT_3_D));

        // Check the other affects are not applied.
        msAssert.isFalse(runFont.hasDmlEffect(TextDmlEffect.OUTLINE));
        msAssert.isFalse(runFont.hasDmlEffect(TextDmlEffect.FILL));

        // Do not mix with TextEffect - it is animation effects like LasVegasLights etc.
        msAssert.areEqual(TextEffect.NONE, runFont.getTextEffect());

        msAssert.isTrue(runs.get(25).getFont().hasDmlEffect(TextDmlEffect.SHADOW));
        msAssert.isTrue(runs.get(27).getFont().hasDmlEffect(TextDmlEffect.REFLECTION));
        msAssert.isTrue(runs.get(29).getFont().hasDmlEffect(TextDmlEffect.OUTLINE));
        msAssert.isTrue(runs.get(35).getFont().hasDmlEffect(TextDmlEffect.EFFECT_3_D));
        msAssert.isTrue(runs.get(37).getFont().hasDmlEffect(TextDmlEffect.FILL));
    }
}

