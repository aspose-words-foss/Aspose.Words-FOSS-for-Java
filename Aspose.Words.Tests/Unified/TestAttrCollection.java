// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.DrColor;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


@Test
public class TestAttrCollection
{
    /// <summary>
    /// Test how attributes collection and revision attributes collection are cloned.
    /// </summary>
    @Test
    public void testCloneWithRevision()
    {
        //Create the main attribute collection and set the original attribute value.
        RunPr attrs = new RunPr();
        attrs.setAttr(100, "xxx");

        //Create the revision attributes collection and add the revised attribute value.
        attrs.setFormatRevision(new FormatRevision(new RunPr(), "me", new DateTime(2005, 5, 23, 0, 0, 0)));
        attrs.getFormatRevision().getRevPr().setAttr(100, "yyy");

        //Clone the main attribute collection with all its attributes including revision attributes inside it.
        RunPr clonedAttrs = AttrCollectionExtension.deepClone(attrs);

        //Check the main attribute collection after cloning.
        msAssert.isTrue(clonedAttrs != attrs);
        msAssert.areEqual("xxx", (String)clonedAttrs.fetchAttr(100));

        //Change cloned attributes and check the original is not affected.
        clonedAttrs.setAttr(101, "ooo");
        msAssert.isFalse(attrs.containsKey(101));

        //Check the revision attribute collection after cloning.
        msAssert.isTrue(clonedAttrs.getFormatRevision() != attrs.getFormatRevision());
        msAssert.isTrue(clonedAttrs.getFormatRevision().getRevPr() != attrs.getFormatRevision().getRevPr());
        msAssert.areEqual("yyy", (String)clonedAttrs.getFormatRevision().getRevPr().fetchAttr(100));

        //Changed cloned revision attributes and check the original is not affected.
        clonedAttrs.getFormatRevision().getRevPr().setAttr(101, "ooo");
        msAssert.isFalse(attrs.getFormatRevision().getRevPr().containsKey(101));
    }

    /// <summary>
    /// Test how attribute collection expands simple attributes to full formatting.
    /// </summary>
    @Test
    public void testExpandSimpleAttr()
    {
        //Let's say:
        //srcAttrs are direct attributes specified on the run.
        //dstAttrs is a copy of the attributes specified in the style of the run.
        //
        //By merging srcAttrs into dstAttrs we achieve expansion into full formatting attributes.

        RunPr srcAttrs = new RunPr();
        srcAttrs.setAttr(101, "srcOnly");        //This only exists in the source, copied to dst.
        srcAttrs.setAttr(202, "srcOverrides");    //This overrides the attribute in dst.

        RunPr dstAttrs = new RunPr();
        dstAttrs.setAttr(201, "dstOnly");        //This exists only in dst collection and stays there.
        dstAttrs.setAttr(202, "dstOverridden");    //This will be overridden by an attribute from the source.

        srcAttrs.expandTo(dstAttrs);

        msAssert.areEqual(3, dstAttrs.getCount());
        msAssert.areEqual("srcOnly", (String)dstAttrs.getDirectAttr(101));
        msAssert.areEqual("dstOnly", (String)dstAttrs.getDirectAttr(201));
        msAssert.areEqual("srcOverrides", (String)dstAttrs.getDirectAttr(202));
    }

    /// <summary>
    /// Test how boolex attribute is expanded.
    /// At the moment it is a simple attribute, but I think I should make it complex.
    /// </summary>
    @Test
    public void testExpandBoolEx()
    {
        RunPr srcAttrs = new RunPr();
        srcAttrs.setAttr(FontAttr.EMBOSS, AttrBoolEx.TRUE);
        srcAttrs.setAttr(FontAttr.ITALIC, AttrBoolEx.TOGGLE);
        srcAttrs.setAttr(FontAttr.BIDI, AttrBoolEx.TOGGLE);
        srcAttrs.setAttr(FontAttr.BOLD, AttrBoolEx.SAME);
        srcAttrs.setAttr(FontAttr.HIDDEN, AttrBoolEx.SAME);
        srcAttrs.setAttr(FontAttr.ALL_CAPS, AttrBoolEx.FALSE);

        RunPr dstAttrs = new RunPr();
        dstAttrs.setAttr(FontAttr.EMBOSS, AttrBoolEx.FALSE);
        dstAttrs.setAttr(FontAttr.BIDI, AttrBoolEx.TRUE);
        dstAttrs.setAttr(FontAttr.HIDDEN, AttrBoolEx.TRUE);

        srcAttrs.expandTo(dstAttrs);

        msAssert.areEqual(6, dstAttrs.getCount());
        msAssert.areEqual(AttrBoolEx.TRUE, dstAttrs.getDirectAttr(FontAttr.EMBOSS));    //A value was overridden.
        msAssert.areEqual(AttrBoolEx.TRUE, dstAttrs.getDirectAttr(FontAttr.ITALIC));    //Was toggled from default.
        msAssert.areEqual(AttrBoolEx.FALSE, dstAttrs.getDirectAttr(FontAttr.BIDI));    //Was toggled.
        msAssert.areEqual(AttrBoolEx.FALSE, dstAttrs.getDirectAttr(FontAttr.BOLD));    //Default was kept
        msAssert.areEqual(AttrBoolEx.TRUE, dstAttrs.getDirectAttr(FontAttr.HIDDEN));    //Same value was kept.
        msAssert.areEqual(AttrBoolEx.FALSE, dstAttrs.getDirectAttr(FontAttr.ALL_CAPS));//A value was set.
    }

    private static class DummyBorderAttrSource implements IBorderAttrSource
    {
        public Object getDirectBorderAttr(int key)
        {
            throw new UnsupportedOperationException();
        }

        public Object fetchInheritedBorderAttr(int key)
        {
            throw new UnsupportedOperationException();
        }

        public void setBorderAttr(int key, Object value)
        {
            throw new UnsupportedOperationException();
        }

        public SortedList<Integer, Integer> getPossibleBorderKeys()
        {
            throw new UnsupportedOperationException();
        }
    }

    /// <summary>
    /// Test expanding border complex attribute.
    /// </summary>
    @Test
    public void testExpandBorder()
    {
        RunPr srcAttrs = new RunPr();

        DummyBorderAttrSource parent = new DummyBorderAttrSource(); // expand object lifetime for C++
        Border src101 = new Border(parent, 101);
        src101 = src101;
        srcAttrs.setAttr(101, src101);    //This is an inherited border.

        Border src102 = new Border(LineStyle.SINGLE, 456, DrColor.BLACK);
        srcAttrs.setAttr(102, src102);    //Sets a new border.

        Border src103 = new Border(LineStyle.SINGLE, 789, DrColor.BLACK);
        srcAttrs.setAttr(103, src103);

        RunPr dstAttrs = new RunPr();

        Border dst101 = new Border(LineStyle.SINGLE, 123, DrColor.BLACK);
        dstAttrs.setAttr(101, dst101);

        Border dst103 = new Border(LineStyle.SINGLE, 999, DrColor.BLACK);
        dstAttrs.setAttr(103, dst103);


        srcAttrs.expandTo(dstAttrs);

        //Test when source border is in inherited mode it does not override destination.
        Border b = (Border)dstAttrs.getDirectAttr(101);
        src101 = src101;
        msAssert.areEqual(123, b.getRawLineWidth());
        msAssert.areEqual(b, dst101);        //The destination object stays as is.

        //Test when destination border is missing, it is set to a clone of the source border.
        b = (Border)dstAttrs.getDirectAttr(102);
        src101 = src101;
        msAssert.areEqual(456, b.getRawLineWidth());
        msAssert.isTrue(b != src102);        //The destination object is a clone from the source.

        //Test when source border overrides an existing destination border.
        b = (Border)dstAttrs.getDirectAttr(103);
        src101 = src101;
        msAssert.areEqual(789, b.getRawLineWidth());
        msAssert.isTrue(b != src103);        //This is actually a new object.
        msAssert.isTrue(b != dst103);
    }

    /// <summary>
    /// Test how tab stops attribute is expanded.
    /// </summary>
    @Test
    public void testExpandTabStops()
    {
        RunPr srcAttrs = new RunPr();
        TabStopCollection srcTabStops = new TabStopCollection();
        srcAttrs.setAttr(101, srcTabStops);

        srcTabStops.add(new TabStop(1, TabAlignment.RIGHT, TabLeader.DOTS));    //This is an added tabstop
        srcTabStops.add(new TabStop(2, TabAlignment.CENTER, TabLeader.DOTS));//This overrides existing tab stop
        srcTabStops.add(new TabStop(3, TabAlignment.CLEAR, TabLeader.NONE));    //This is a delete tabstop command.

        RunPr dstAttrs = new RunPr();
        TabStopCollection dstTabStops = new TabStopCollection();
        dstAttrs.setAttr(101, dstTabStops);

        dstTabStops.add(new TabStop(2, TabAlignment.LEFT, TabLeader.NONE));    //Tabstop to be overridden.
        dstTabStops.add(new TabStop(3, TabAlignment.LEFT, TabLeader.NONE));    //Tabstop to be deleted.


        srcAttrs.expandTo(dstAttrs);

        TabStopCollection tabStops = (TabStopCollection)dstAttrs.getDirectAttr(101);

        //Check it is actually a new object.
        msAssert.isTrue(tabStops != srcTabStops);
        msAssert.isTrue(tabStops != dstTabStops);

        //Check a tab stop was added.
        msAssert.areEqual(1, tabStops.getPositionTwipsByIndex(0));
        msAssert.areEqual(TabAlignment.RIGHT, tabStops.getByPositionTwips(1).getAlignment());

        //Check a tab stop was overridden.
        msAssert.areEqual(TabAlignment.CENTER, tabStops.getByPositionTwips(2).getAlignment());

        // Clear tab stops are left in the model during attribute expansion.
        msAssert.areEqual(TabAlignment.CLEAR, tabStops.getByPositionTwips(3).getAlignment());
    }

    /// <summary>
    /// Test how an expanded attribute collection can be collapsed into a "normal" collection.
    /// This is needed during RTF import.
    /// </summary>
    @Test
    public void testCollapseSimple()
    {
        // Imagine we read this style definition from RTF.
        RunPr styleAttrs = new RunPr();
        styleAttrs.setAttr(FontAttr.ISTD, 15);
        styleAttrs.setAttr(FontAttr.BOLD, AttrBoolEx.TRUE);
        styleAttrs.setAttr(FontAttr.ITALIC, AttrBoolEx.TRUE);
        styleAttrs.setAttr(FontAttr.EMBOSS, AttrBoolEx.TRUE);

        // We also read this run definition from RTF.
        RunPr runAttrs = new RunPr();
        runAttrs.setAttr(FontAttr.ISTD, 15);
        runAttrs.setAttr(FontAttr.ITALIC, AttrBoolEx.TRUE);
        runAttrs.setAttr(FontAttr.EMBOSS, AttrBoolEx.FALSE);
        runAttrs.setAttr(FontAttr.OUTLINE, AttrBoolEx.TRUE);

        // This calculates properties that we can store for the run in the model.
        runAttrs.collapse(styleAttrs, FontAttr.ISTD);

        msAssert.areEqual(4, styleAttrs.getCount());
        msAssert.areEqual(4, runAttrs.getCount());
        msAssert.areEqual(15, runAttrs.getDirectAttr(FontAttr.ISTD));

        // When Bold=true in the style and Bold=missing in the run in RTF, it means Bold=false
        // in the model because there is inheritance in the model, but no inheritance in RTF.
        msAssert.areEqual(AttrBoolEx.FALSE, runAttrs.getDirectAttr(FontAttr.BOLD));

        // When Italic=true in the style and Italic=true in the run in RTF, it means Italic=missing
        // in the model because it means that was simply an expanded attribute.

        // When Emboss=true in the style and Emboss=false in the run in RTF, it means Emboss=false
        // in the model because it is an attribute that was specified on the run directly.
        msAssert.areEqual(AttrBoolEx.FALSE, runAttrs.getDirectAttr(FontAttr.EMBOSS));

        // When Outline=missing in the style Outline=true in the run in RTF, it means Outline=true
        // in the model because it is an attribute that was specified on the run directly.
        msAssert.areEqual(AttrBoolEx.TRUE, runAttrs.getDirectAttr(FontAttr.OUTLINE));
    }

    /// <summary>
    /// For RTF import.
    /// When a style does not have a border, but a run has,
    /// collapsing leaves the border on the run without changes.
    /// </summary>
    @Test
    public void testCollapseBorderAdded()
    {
        RunPr styleAttrs = new RunPr();

        RunPr runAttrs = new RunPr();
        runAttrs.setAttr(FontAttr.BORDER, new Border(LineStyle.DOT, 8, DrColor.BLUE));

        runAttrs.collapse(styleAttrs);

        msAssert.areEqual(0, styleAttrs.getCount());
        msAssert.areEqual(1, runAttrs.getCount());
        msAssert.isTrue(runAttrs.getBorder().equals(new Border(LineStyle.DOT, 8, DrColor.BLUE)));
    }

    /// <summary>
    /// For RTF import.
    /// When a style has a border and a run has the same border,
    /// collapsing removes the border from the run.
    /// </summary>
    @Test
    public void testCollapseBorderSame()
    {
        RunPr styleAttrs = new RunPr();
        styleAttrs.setAttr(FontAttr.BORDER, new Border(LineStyle.DOT, 8, DrColor.BLUE));

        RunPr runAttrs = new RunPr();
        runAttrs.setAttr(FontAttr.BORDER, new Border(LineStyle.DOT, 8, DrColor.BLUE));

        runAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.areEqual(0, runAttrs.getCount());
    }

    /// <summary>
    /// For RTF import.
    /// When a style has a border and a run has a border, but different,
    /// collapsing leaves the border on the run.
    /// </summary>
    @Test
    public void testCollapseBorderChanged()
    {
        RunPr styleAttrs = new RunPr();
        styleAttrs.setAttr(FontAttr.BORDER, new Border(LineStyle.DOT, 8, DrColor.BLUE));

        RunPr runAttrs = new RunPr();
        runAttrs.setAttr(FontAttr.BORDER, new Border(LineStyle.DOT, 8, DrColor.RED));

        runAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.isTrue(styleAttrs.getBorder().equals(new Border(LineStyle.DOT, 8, DrColor.BLUE)));

        msAssert.areEqual(1, runAttrs.getCount());
        msAssert.isTrue(runAttrs.getBorder().equals(new Border(LineStyle.DOT, 8, DrColor.RED)));
    }

    /// <summary>
    /// For RTF import.
    /// When a style has a border, but the run does not have a border,
    /// collapsing adds an emtpy border to the run.
    /// </summary>
    @Test
    public void testCollapseBorderRemoved()
    {
        RunPr styleAttrs = new RunPr();
        styleAttrs.setAttr(FontAttr.BORDER, new Border(LineStyle.DOT, 8, DrColor.BLUE));

        RunPr runAttrs = new RunPr();

        runAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.isTrue(styleAttrs.getBorder().equals(new Border(LineStyle.DOT, 8, DrColor.BLUE)));

        msAssert.areEqual(1, runAttrs.getCount());
        msAssert.isTrue(runAttrs.getBorder().equals(new Border()));

        // It is important that the empty border object on the run is not the global default instance. It must be a copy of it.
        msAssert.isTrue(runAttrs.getBorder() != (Border)RunPr.fetchDefaultAttr(FontAttr.BORDER));
    }

    /// <summary>
    /// In RTF, the tyle has no tab stops, but the paragraph has one.
    /// Collapsing leaves paragraph tab stop without changes in the model.
    /// </summary>
    @Test
    public void testCollapseTabStopsAdded()
    {
        ParaPr styleAttrs = new ParaPr();

        ParaPr paraAttrs = new ParaPr();
        paraAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        paraAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);

        paraAttrs.collapse(styleAttrs);

        msAssert.areEqual(0, styleAttrs.getCount());

        msAssert.areEqual(1, paraAttrs.getCount());
        msAssert.areEqual(1, paraAttrs.getTabStops().getCount());
    }

    /// <summary>
    /// In RTF, the style has one tab stop, paragraph has two.
    /// Collapsing leaves only the added tab stop in the paragraph in the model.
    /// </summary>
    @Test
    public void testCollapseTabStopAdded()
    {
        ParaPr styleAttrs = new ParaPr();
        styleAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        styleAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);

        ParaPr paraAttrs = new ParaPr();
        paraAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        paraAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);
        paraAttrs.getTabStops().add(200.0, TabAlignment.LEFT, TabLeader.NONE);

        paraAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.areEqual(1, styleAttrs.getTabStops().getCount());

        msAssert.areEqual(1, paraAttrs.getCount());
        msAssert.areEqual(1, paraAttrs.getTabStops().getCount());
        msAssert.areEqual(200.0, paraAttrs.getTabStops().getPositionByIndex(0));
    }

    /// <summary>
    /// In RTF, the style has two tab stops, the paragraph has only one.
    /// Collapsing leaves only the deleted tab stop in the paragraph in the model.
    /// </summary>
    @Test
    public void testCollapseTabStopRemoved()
    {
        ParaPr styleAttrs = new ParaPr();
        styleAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        styleAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);
        styleAttrs.getTabStops().add(200.0, TabAlignment.LEFT, TabLeader.NONE);

        ParaPr paraAttrs = new ParaPr();
        paraAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        paraAttrs.getTabStops().add(200.0, TabAlignment.LEFT, TabLeader.NONE);

        paraAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.areEqual(2, styleAttrs.getTabStops().getCount());

        msAssert.areEqual(1, paraAttrs.getCount());
        msAssert.areEqual(1, paraAttrs.getTabStops().getCount());
        msAssert.isTrue(paraAttrs.getTabStops().get(0).equals(new TabStop(100f, TabAlignment.CLEAR, TabLeader.NONE)));
    }

    /// <summary>
    /// In RTF, the style has two tab stops, the paragraph has no tab stops at all.
    /// Collapsing leaves paragraph with two "clear" tab stops in the model.
    /// </summary>
    @Test
    public void testCollapseTabStopsRemoved()
    {
        ParaPr styleAttrs = new ParaPr();
        styleAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        styleAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);
        styleAttrs.getTabStops().add(200.0, TabAlignment.LEFT, TabLeader.NONE);

        ParaPr paraAttrs = new ParaPr();

        paraAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.areEqual(2, styleAttrs.getTabStops().getCount());

        msAssert.areEqual(1, paraAttrs.getCount());
        msAssert.areEqual(2, paraAttrs.getTabStops().getCount());
        msAssert.isTrue(paraAttrs.getTabStops().get(0).equals(new TabStop(100f, TabAlignment.CLEAR, TabLeader.NONE)));
        msAssert.isTrue(paraAttrs.getTabStops().get(1).equals(new TabStop(200f, TabAlignment.CLEAR, TabLeader.NONE)));
    }

    /// <summary>
    /// In RTF, the style has one tab stop, the paragraph has the same.
    /// Collapsing leaves paragraph with no tab stops attribute in the model.
    /// </summary>
    @Test
    public void testCollapseTabStopSame()
    {
        ParaPr styleAttrs = new ParaPr();
        styleAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        styleAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);

        ParaPr paraAttrs = new ParaPr();
        paraAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        paraAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);

        paraAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.areEqual(1, styleAttrs.getTabStops().getCount());

        msAssert.areEqual(0, paraAttrs.getCount());
    }

    /// <summary>
    /// In RTF, the style has one tab stop, the paragraph has a different tab stop in the same position.
    /// Collapsing leaves the paragraph with it tab stop without changes in the model.
    /// </summary>
    @Test
    public void testCollapseTabStopDifferent()
    {
        ParaPr styleAttrs = new ParaPr();
        styleAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        styleAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.NONE);

        ParaPr paraAttrs = new ParaPr();
        paraAttrs.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());
        paraAttrs.getTabStops().add(100.0, TabAlignment.LEFT, TabLeader.DOTS);    // DOTS different

        paraAttrs.collapse(styleAttrs);

        msAssert.areEqual(1, styleAttrs.getCount());
        msAssert.areEqual(1, styleAttrs.getTabStops().getCount());

        msAssert.areEqual(1, paraAttrs.getCount());
        msAssert.areEqual(1, paraAttrs.getTabStops().getCount());
        msAssert.isTrue(paraAttrs.getTabStops().get(0).equals(new TabStop(100f, TabAlignment.LEFT, TabLeader.DOTS)));
    }

    /// <summary>
    /// Tests comparing collections by the <see cref="AttrCollection.Equals(AttrCollection,AttrCollection,int[])"/>
    /// method.
    /// </summary>
    @Test
    public void testEqual() throws Exception
    {
        ParaPr paraPr1 = new ParaPr();
        ParaPr paraPr2 = new ParaPr();

        // Check empty collections.
        msAssert.isTrue(AttrCollection.equals(paraPr1, paraPr2, null));

        // Check simple attributes.
        paraPr1.setBidi(true);
        msAssert.isFalse(AttrCollection.equals(paraPr1, paraPr2, null));
        msAssert.isFalse(AttrCollection.equals(paraPr2, paraPr1, null));
        msAssert.isTrue(AttrCollection.equals(paraPr1, paraPr2, new int[] { ParaAttr.BIDI }));
        paraPr2.setBidi(false);
        msAssert.isFalse(AttrCollection.equals(paraPr1, paraPr2, null));
        msAssert.isTrue(AttrCollection.equals(paraPr1, paraPr2, new int[] { ParaAttr.BIDI }));
        paraPr2.setBidi(true);
        msAssert.isTrue(AttrCollection.equals(paraPr1, paraPr2, null));

        // Check complex attributes.
        Document doc = new Document();
        Style style = doc.getStyles().getByStyleIdentifier(StyleIdentifier.NORMAL);
        style.getParagraphFormat().getShading().setTexture(TextureIndex.TEXTURE_20_PERCENT);
        Paragraph para = new Paragraph(doc);

        Shading shading1 = new Shading(para.getParagraphFormat(), ParaAttr.SHADING);
        shading1 = shading1;
        msAssert.isTrue(((IComplexAttr)shading1).isInheritedComplexAttr());
        paraPr1.add(ParaAttr.SHADING, shading1);
        msAssert.isTrue(AttrCollection.equals(paraPr1, paraPr2, null));
        msAssert.isTrue(AttrCollection.equals(paraPr2, paraPr1, null));

        shading1.setTexture(TextureIndex.TEXTURE_25_PERCENT);
        msAssert.isFalse(AttrCollection.equals(paraPr1, paraPr2, null));
        msAssert.isFalse(AttrCollection.equals(paraPr2, paraPr1, null));
    }

    /// <summary>
    /// WORDSNET-21593 Investigate SortedShortListIntegerFallback initial capacity.
    /// Initial capacity for font and paragraph properties were reduced.
    /// </summary>
    @Test
    public void test21593()
    {
        ParaPr paraPr = new ParaPr();
        paraPr.set(ParaAttr.ALIGNMENT, ParagraphAlignment.LEFT);

        msAssert.areEqual(2, paraPr.getCapacity());

        RunPr runPr = new RunPr();
        runPr.set(FontAttr.BOLD, true);

        msAssert.areEqual(4, runPr.getCapacity());
    }


    /// <summary>
    /// Gets list of legacy TabStops.
    /// </summary>
    private static ArrayList<TabStop> getLegacyTabStops(ParaPr paraPr)
    {
        TabStopCollection tabStops = (TabStopCollection)paraPr.get(ParaAttr.TAB_STOPS);

        ArrayList<TabStop> legacyTabStops = new ArrayList<TabStop>();

        if (tabStops != null)
        {
            for (int i = 0; i < tabStops.getCount(); i++)
            {
                if (tabStops.get(i).isLegacyTab())
                    msArrayList.add(legacyTabStops, tabStops.get(i));
            }
        }

        return legacyTabStops;
    }
}

