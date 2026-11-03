// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ArrayUtil;
import com.aspose.TestUtilPal;


/// <summary>
/// Test DuplicateFontFormattingStripper. Tests complex attribute equality as stripper relies on it.
/// </summary>
@Test
public class TestDuplicateFormattingStripper extends TestFieldsBase
{
    @Test
    public void testDrColorEquality()
    {
        DrColor color = new DrColor(DrColor.RED.toArgb());
        checkEquality(DrColor.RED, color);
    }

    private static void checkEquality(Object source, Object clone)
    {
        msAssert.isTrue(source.equals(clone));
        msAssert.isFalse(source.equals(null));
    }

    @Test
    public void testBorderEquality()
    {
        Border border = new Border(LineStyle.DASH_DOT_STROKER, 2, DrColor.YELLOW_GREEN);
        Border clone = border.deepClone();
        checkEquality(border, clone);
    }

    @Test
    public void testShadingEquality()
    {
        Shading shading = makeTestShading();
        Shading clone = makeTestShading();
        checkEquality(shading, clone);
    }

    private static Shading makeTestShading()
    {
        Shading shading = new Shading();
        shading.setTexture(TextureIndex.TEXTURE_SOLID);
        shading.setThemeColor("black");
        shading.setThemeTint("tint");
        shading.setForegroundPatternColorInternal(new DrColor(DrColor.BROWN.toArgb()));
        shading.setBackgroundPatternColorInternal(new DrColor(DrColor.BLUE.toArgb()));

        return shading;
    }

    @Test
    public void testDuplicateFormattingStripping() throws Exception
    {
        final String FILE_NAME = "Fields\\Format\\TestDuplicateFormattingStripping.docx";
        Document doc = TestUtil.open(FILE_NAME);

        TestFontAttribute[] fontAttributes = getAllFontAttributes();

        // Check that direct formatting was not present in the original document.
        checkDirectFormattingStripped(doc, "Direct formatting is present in the original document.");

        duplicateAttrsToDirectFormatting(doc, fontAttributes);

        // Strip duplicated formatting.
        TocNormalEntryAttributeModifier.stripInlineFontAttrsForToc(doc);

        // Check that direct formatting was stripped from testDoc
        checkDirectFormattingStripped(doc, "Direct formatting remained after stripping.");

        // Check that all attributes are tested.
        checkAllAttributesTested(fontAttributes);
    }

    private static void duplicateAttrsToDirectFormatting(Document doc, TestFontAttribute[] fontAttributes)
    {
        NodeCollection nodes = doc.getChildNodes(NodeType.ANY, true);
        for (Node node : (Iterable<Node>) nodes)
        {
            Inline inlineNode = ms.as(node, Inline.class);
            if (inlineNode != null)
            {
                duplicateAttrsToDirectFormatting(inlineNode, fontAttributes);
            }
        }
    }

    private static void duplicateAttrsToDirectFormatting(Inline inlineNode, TestFontAttribute[] fontAttributes)
    {
        for (TestFontAttribute testAttr : fontAttributes)
        {
            if (!ignoreAttrForStripTest(testAttr.Key))
            {
                Object attr = inlineNode.getFont().fetchAttr(testAttr.Key);

                if (attr != null)
                {
                    // Clone the attribute to check equality better.
                    Object clonedAttribute = cloneAttribute(attr);

                    // Set the same attribute directly.
                    inlineNode.getRunPr().setAttr(testAttr.Key, clonedAttribute);

                    // Remember the attribute is checked:
                    testAttr.CheckState = AttrCheckState.CHECKED;
                }
            }
        }
    }

    private static Object cloneAttribute(Object attr)
    {
        IComplexAttr complexAttr = ms.as(attr, IComplexAttr.class);
        if (complexAttr != null)
            return complexAttr.deepCloneComplexAttr();

        DrColor color = ms.as(attr, DrColor.class);
        if (color != null)
            return new DrColor(color.toArgb());

        return attr;
    }

    private static void checkDirectFormattingStripped(Document doc, String context)
    {
        NodeCollection nodes = doc.getChildNodes(NodeType.ANY, true);
        for (Node node : (Iterable<Node>) nodes)
        {
            if (!(node instanceof Inline))
                continue;

            Inline inlineNode = (Inline)node;

            // List not stripped attributes.
            int unstrippedAttrCount = 0;
            for (int attrIdx = 0; attrIdx < inlineNode.getRunPr().getCount(); ++attrIdx)
            {
                int attrKey = inlineNode.getRunPr().getKey(attrIdx);
                Object attrValue = inlineNode.getRunPr().get(attrKey);

                if (!ignoreAttrForStripTest(attrKey))
                {
                    Debug.writeLine(msString.format("Direct Formatting Attribute is present: {0} : {1}", attrKey, attrValue));
                    unstrippedAttrCount++;
                }
            }

            msAssert.areEqual(0, unstrippedAttrCount, context);
        }
    }

    private static boolean ignoreAttrForStripTest(int attr)
    {
        return (ArrayUtil.binarySearch(G_IGNORED_ATTRIBUTES, 0, G_IGNORED_ATTRIBUTES.length, attr) >= 0);
    }

    private static void checkAllAttributesTested(TestFontAttribute[] fontAttributes)
    {
        Debug.writeLine("Not tested attributes:");

        int attrCount = 0;
        // List not tested attributes.
        for (TestFontAttribute testAttr : fontAttributes)
        {
            if (testAttr.CheckState == AttrCheckState.NOT_CHECKED &&
                !ignoreAttrForStripTest(testAttr.Key))
            {
                Debug.writeLine(testAttr.Name);
                ++attrCount;
            }
        }

        Debug.writeLine(msString.format("{0} attributes not tested.", attrCount));
        msAssert.areEqual(0, attrCount);
    }

    @Test
    public void testIgonredAttributesOrder()
    {
        int previousAttr = G_IGNORED_ATTRIBUTES[0];
        for (int attrIdx = 1; attrIdx < G_IGNORED_ATTRIBUTES.length; ++attrIdx)
        {
            int currentAttr = G_IGNORED_ATTRIBUTES[attrIdx];
            msAssert.isTrue(currentAttr > previousAttr, msString.format("Attributes are not ordered, at idx {0}.", attrIdx));
            previousAttr = currentAttr;
        }
    }

    private static TestFontAttribute[] getAllFontAttributes() throws Exception
    {
        int[] attrValues = TestUtilPal.getIntConstantValues(FontAttr.class);
        TestFontAttribute[] fontAttributes = new TestFontAttribute[attrValues.length];

        for (int attrIdx = 0; attrIdx < attrValues.length; attrIdx++)
        {
            fontAttributes[attrIdx] = new TestFontAttribute(null, attrValues[attrIdx]);
        }

        return fontAttributes;
    }

    private /*enum*/ final class AttrCheckState
    {
        private AttrCheckState(){}
        
        public static final int NOT_CHECKED = 0;
        public static final int CHECKED = 1;

        public static final int length = 2;
    }

    private static class TestFontAttribute
    {
        public TestFontAttribute(String name, int attrKey)
        {
            Name = name;
            Key = attrKey;
            CheckState = AttrCheckState.NOT_CHECKED;
        }

        public /*final*/ String Name;
        public /*final*/ int Key;
        public /*AttrCheckState*/int CheckState;
    }

    // Must be ordered or binary search will fail.
    private static /*final*/ int[] G_IGNORED_ATTRIBUTES =
    {
        // These presumably cannot be inherited from a style or default.
        RevisionAttr.DELETE_REVISION, // 12
        RevisionAttr.INSERT_REVISION, // 14
        FontAttr.SYS_ATTRS_1_TRIGGER, // 16
        FontAttr.RSID_R_PR, // 30
        FontAttr.RSID_R,  // 40
        FontAttr.ISTD, // 50
        FontAttr.SYS_ATTRS_2_TRIGGER, // 202
        FontAttr.WORD_XP_ATTRS_TRIGGER, // 402
        FontAttr.PICTURE_BULLET_ID, // 480
        FontAttr.PICTURE_BULLET_FLAGS, // 490

        // This one I don't know what for, but it probably cannot be inherited either.
        FontAttr.CF_MATH_PR, // 600

        // Adding one of these caused different run type. It seems they don't make sense for a regular run.
        FontAttr.MATH_IS_LITERAL,
        FontAttr.MATH_IS_NORMAL_TEXT, // 710
        FontAttr.MATH_SCRIPT, // 720
        FontAttr.MATH_STYLE, // 730
        FontAttr.MATH_LINE_BREAK, // 740
        FontAttr.MATH_IS_ALIGNMENT_POINT, // 750
        FontAttr.MATH_IS_O_MATH, // 760
        FontAttr.FAR_EAST_LAYOUT, // 780

        FontAttr.ALTERNATE_CONTENT, // 790

        FontAttr.EFFECT_GLOW,                    // 810
        FontAttr.EFFECT_SHADOW,                  // 815
        FontAttr.EFFECT_REFLECTION,              // 820
        FontAttr.EFFECT_OUTLINE,                 // 825
        FontAttr.EFFECT_FILL,                    // 830
        FontAttr.EFFECT_SCENE_3_D,                 // 835
        FontAttr.EFFECT_PROPS_3_D,                 // 840
        FontAttr.OPEN_TYPE_LIGATURE,              // 850
        FontAttr.OPEN_TYPE_NUM_FORM,               // 855
        FontAttr.OPEN_TYPE_NUM_SPACING,            // 860
        FontAttr.OPEN_TYPE_STYLISTIC_SETS,         // 865
        FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES,  // 870
        FontAttr.FIT_TEXT,                       // 880
        FontAttr.RUBY,                          // 885

        FontAttr.SYS_SYMBOL                    // 890
    };

}

