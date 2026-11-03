// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2012 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.DrColor;
import com.aspose.ms.System.msString;
import com.aspose.HashSetGeneric;
import com.aspose.Pair;


/// <summary>
/// Test various operations related to managing styles in the collection using public API. Add more operations to lists when supported:
/// - Setting Name property of a style
/// - Performing AddCopy on a style
/// - Comparing styles.
/// </summary>
@Test
public class TestStyleManagement
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }


    /// <summary>
    /// Verify that paragraph style istd is updated in list levels, when the style changes the istd due to name change.
    /// </summary>
    @Test
    public void testListStyleUpdated() throws Exception
    {
        Document src = TestUtil.open("Model\\Revision\\TestStyleRevisions.docx");
        StyleCollection sourceStyles = src.getStyles();
        Document target = new Document();
        Style addedStyle = target.getStyles().addCopy(sourceStyles.get("Intense Quote"));
        List list = target.getLists().fetchListByListId(addedStyle.getParaPr().getListId());
        msAssert.areEqual(addedStyle.getIstd(), list.getListLevels().get(0).getParaStyleIstd());
        msAssert.areNotEqual(sourceStyles.get("Intense Quote").getIstd(), list.getListLevels().get(0).getParaStyleIstd());
    }


    /// <summary>
    /// Verify renaming a style to built-in (with istd change), changes istd in styles that were based on it.
    /// </summary>
    @Test
    public void testRenameStyleBasedOn() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        StyleCollection styles = builder.getDocument().getStyles();
        Style baseStyle = Style.create(StyleType.PARAGRAPH, styles.getNextFreeIstd(), StyleIdentifier.USER, "BaseStyle");
        baseStyle.getRunPr().setColor(DrColor.CORAL);
        styles.add(baseStyle);
        builder.getParagraphFormat().setIstd(baseStyle.getIstd());
        builder.writeln("My text of Normal style. ");

        Style childStyle = Style.create(StyleType.PARAGRAPH, styles.getNextFreeIstd(), StyleIdentifier.USER, "DerivedStyle");
        styles.add(childStyle);
        childStyle.setBasedOnIstd(baseStyle.getIstd());
        childStyle.getRunPr().setItalic(AttrBoolEx.TRUE);
        builder.getParagraphFormat().setIstd(childStyle.getIstd());
        builder.writeln("My text of Derived style.");

        baseStyle.setName("BaseStyleNew");
        msAssert.areEqual(baseStyle.getIstd(), childStyle.getBasedOnIstd());

        baseStyle.setName("Normal");
        msAssert.areEqual(baseStyle.getIstd(), childStyle.getBasedOnIstd());
        msAssert.areEqual(0, childStyle.getBasedOnIstd());

        TestUtil.saveOpen(builder.getDocument(), "Model\\Style\\RenameBasedOnStyle.docx");
    }



    /// <summary>
    /// WORDSNET-7523 Style.Name throws NullReferenceException during copying style name from another Document.
    /// StyleCollection.ProcessListStyle had a wrong assumption that para.ListFormat.List.Style is always non-null.
    /// </summary>
    @Test
    public void testJira7523() throws Exception
    {
        final String TEST_NAME = "Model\\Style\\TestJira7523.docx";
        Document target = TestUtil.open(TEST_NAME);
        Document source = new Document();

        Style listStyle = source.getStyles().get("No List");
        Style targetListStyle = target.getStyles().addCopy(listStyle);
        targetListStyle.setName(listStyle.getName());

        // Exception would have happened before this line.
        msAssert.isNull(((Paragraph)target.getChild(NodeType.PARAGRAPH, 0, true)).getListFormat().getList().getStyle());
    }

    /// <summary>
    /// WORDSNET-7404 Style.Name do not change the style name.
    /// StyleIndex.BuiltInStyleNameEnglish handled Built-in style names incorrectly.
    /// </summary>
    @Test
    public void testJira7404() throws Exception
    {
        final String TEST_NAME = "Model\\Style\\TestJira7404";
        Document sourceDoc = TestUtil.open(TEST_NAME + "Source.docx");
        final String SOURCE_NAME = "Heading 1";
        Style sourceStyle = sourceDoc.getStyles().get(SOURCE_NAME);

        Document targetDoc = TestUtil.open(TEST_NAME + "Target.docx");
        Style addedStyle = targetDoc.getStyles().addCopy(sourceStyle);
        msAssert.isFalse(addedStyle.getBuiltIn());
        int styleCount = targetDoc.getStyles().getCount();

        addedStyle.setName(SOURCE_NAME);
        msAssert.areEqual(styleCount - 1, targetDoc.getStyles().getCount()); // make sure we removed "Heading 1_0" when renaming to "Heading 1"
        msAssert.isTrue(addedStyle.getBuiltIn());
        msAssert.areEqual(SOURCE_NAME, addedStyle.getName());
    }

    /// <summary>
    /// Tests setting empty name for the style throws an exception.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QStyle name can not be empty.\\E")
    public void testStyleSetNullName() throws Exception
    {
        StyleCollection collection = new Document().getStyles();
        Style result = collection.add(StyleType.PARAGRAPH, "myStyle");
        result.setName("");
    }

    /// <summary>
    /// Tests changing 'Normal' is correct.
    /// </summary>
    @Test
    public void testBuiltInStyles() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();
        StyleCollection styles = doc.getStyles();

        styles.get("Normal").getRunPr().setAttr(FontAttr.ITALIC, AttrBoolEx.TRUE);

        Style result = styles.add(StyleType.PARAGRAPH, "myStyle");
        result.getRunPr().setAttr(FontAttr.ITALIC, AttrBoolEx.TRUE);

        result.setName("Normal");
        msAssert.isTrue(result.getBuiltIn());
        msAssert.isTrue(((AttrBoolEx)styles.get("Normal").getRunPr().get(FontAttr.ITALIC)).toBool());
        builder.getCurrentParagraph().getParagraphFormat().setStyle(result);
        builder.write("Test");
        msAssert.areEqual(0, (/*int*/Integer)((Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true)).getParaPr().get(ParaAttr.ISTD));
        TestUtil.saveOpen(doc, "Model\\Style\\TestSetUserToBuiltInStyle.docx");

        result.setName("myStyle");
        msAssert.isFalse(result.getBuiltIn());
        msAssert.isTrue(((AttrBoolEx)styles.get("myStyle").getRunPr().get(FontAttr.ITALIC)).toBool());
        msAssert.areEqual(15, (/*int*/Integer)((Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true)).getParaPr().get(ParaAttr.ISTD));
        TestUtil.saveOpen(doc, "Model\\Style\\TestSetBuiltInToUserStyle.docx");

        result.setName("Normal");
        msAssert.isTrue(result.getBuiltIn());
        msAssert.isTrue(((AttrBoolEx)styles.get("Normal").getRunPr().get(FontAttr.ITALIC)).toBool());
        msAssert.areEqual(0, (/*int*/Integer)((Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true)).getParaPr().get(ParaAttr.ISTD));
        TestUtil.saveOpen(doc, "Model\\Style\\TestSetUserToBuiltInStyle2.docx");
    }

    /// <summary>
    /// Test "overwrite" style by renaming to existing name.
    /// Verify collection access.
    /// </summary>
    @Test
    public void testStyleSetExistingName() throws Exception
    {
        Document doc = new Document();
        StyleCollection collection = doc.getStyles();
        collection.add(StyleType.PARAGRAPH, "myStyle");
        Style result = collection.add(StyleType.PARAGRAPH, "myStyle1");
        result.setName("myStyle");
        msAssert.isNull(collection.get("myStyle1"));
        msAssert.isNotNull(collection.get("myStyle"));
    }

    /// <summary>
    /// Change style name and verify that it is successfully set.
    /// </summary>
    @Test
    public void testStyleSetRunRegularStyle() throws Exception
    {
        final String TEST_NAME = "Model\\Style\\TestStyleSetRunRegularStyle";
        DocumentBuilder builder = new DocumentBuilder();
        StyleCollection collection = builder.getDocument().getStyles();
        Style result = collection.add(StyleType.PARAGRAPH, "myStyle");
        builder.getCurrentParagraph().getParagraphFormat().setStyle(result);
        builder.write("test");
        msAssert.areEqual("myStyle", ((Paragraph)builder.getDocument().getChild(NodeType.PARAGRAPH, 0, true)).getParagraphStyle().getName());
        TestUtil.saveOpen(builder.getDocument(), TEST_NAME + "1.docx");

        final String NEW_NAME = "newName";
        result.setName(NEW_NAME);
        msAssert.areEqual(result.getIstd(), collection.get(NEW_NAME).getIstd());
        msAssert.areEqual(NEW_NAME, result.getName());
        msAssert.areEqual(NEW_NAME, ((Paragraph)builder.getDocument().getChild(NodeType.PARAGRAPH, 0, true)).getParagraphStyle().getName());
        TestUtil.saveOpen(builder.getDocument(), TEST_NAME + "2.docx");
    }

    /// <summary>
    /// Tests renaming custom style to 'Normal'.
    /// </summary>
    @Test
    public void testStyleSetRunBuiltInStyle() throws Exception
    {
        final String TEST_NAME = "Model\\Style\\TestStyleSetRunBuiltInStyle";
        DocumentBuilder builder = new DocumentBuilder();
        StyleCollection collection = builder.getDocument().getStyles();
        Style result = collection.add(StyleType.PARAGRAPH, "myStyle");
        builder.getCurrentParagraph().getParagraphFormat().setStyle(result);
        builder.write("test");
        msAssert.areEqual("myStyle", ((Paragraph)builder.getDocument().getChild(NodeType.PARAGRAPH, 0, true)).getParagraphStyle().getName());
        TestUtil.saveOpen(builder.getDocument(), TEST_NAME + "Regular.docx");

        final String NEW_NAME = "Normal";
        result.setName(NEW_NAME);
        msAssert.areEqual(result.getIstd(), collection.get(NEW_NAME).getIstd());
        msAssert.areEqual(NEW_NAME, result.getName());
        msAssert.areEqual(NEW_NAME, ((Paragraph)builder.getDocument().getChild(NodeType.PARAGRAPH, 0, true)).getParagraphStyle().getName());
        TestUtil.saveOpen(builder.getDocument(), TEST_NAME + "BuiltIn.docx");
    }

    /// <summary>
    /// Tests copying 'Normal' into the same document.
    /// </summary>
    @Test
    public void testAddCopyBuiltInStyleSameDoc() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        styles.get("Normal").getRunPr().setAttr(FontAttr.ITALIC, AttrBoolEx.TRUE);
        Style normal1 = styles.addCopy(styles.get("Normal"));
        msAssert.areEqual("Normal_0", normal1.getName());
        msAssert.isFalse(normal1.getBuiltIn());
        msAssert.areNotEqual(normal1.getIstd(), StyleIndex.NORMAL);
        msAssert.isNotNull(styles.get("Normal_0"));
        msAssert.areEqual(AttrBoolEx.TRUE, styles.get("Normal_0").getRunPr().get(FontAttr.ITALIC));
        TestUtil.saveOpen(doc, "Model\\Style\\TestAddCopyBuiltInStyleSameDoc.docx");
    }

    /// <summary>
    /// Tests copying 'Normal' into another document.
    /// </summary>
    @Test
    public void testAddCopyBuiltInStyleDifferentDoc() throws Exception
    {
        Document doc = new Document();
        StyleCollection styles = doc.getStyles();

        Style normal1 = styles.addCopy(new Document().getStyles().get("Normal"));
        msAssert.areEqual("Normal_0", normal1.getName());
        msAssert.isFalse(normal1.getBuiltIn());
        msAssert.areNotEqual(normal1.getIstd(), StyleIndex.NORMAL);
        msAssert.isNotNull(styles.get("Normal_0"));
        TestUtil.saveOpen(doc, "Model\\Style\\TestAddCopyBuiltInStyleDifferentDoc.docx");
    }

    /// <summary>
    /// AddCopy a style with revisions and make sure it is in the collection.
    /// </summary>
    @Test
    public void testAddStyleWithRevisions() throws Exception
    {
        Document src = TestUtil.open("Model\\Revision\\TestStyleRevisions.docx");
        RevisionCollection revisions = src.getRevisions();
        Style revisedStyle = revisions.get(revisions.getCount() - 2).getParentStyle();
        Document doc = new Document();

        msAssert.areEqual(0, doc.getRevisions().getCount());
        doc.getStyles().addCopy(revisedStyle);
        msAssert.areEqual(1, doc.getRevisions().getCount());
        msAssert.areEqual("Denis", doc.getRevisions().get(0).getAuthor());
    }

    /// <summary>
    /// Tests copying style and renaming it to 'Normal'.
    /// </summary>
    @Test
    public void testAddCopyParaStyle() throws Exception
    {
        Document doc = new Document();
        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "MyStyle");
        style.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);


        DocumentBuilder builder = new DocumentBuilder();
        Document document = builder.getDocument();
        Style importedStyle = document.getStyles().addCopy(style);
        builder.getCurrentParagraph().getParagraphFormat().setStyle(importedStyle);
        builder.writeln("This is centered text.");

        TestUtil.saveOpen(document, "Model\\Style\\AddCopyParaStyle.docx");

        importedStyle.setName("Normal");
        msAssert.isTrue(importedStyle.getBuiltIn());
        msAssert.areEqual(ParagraphAlignment.CENTER, document.getStyles().get("Normal").getParagraphFormat().getAlignment());
        TestUtil.saveOpen(builder.getDocument(), "Model\\Style\\AddCopyParaStyleRenamed.docx");

        importedStyle.setName("MyStyle2");
        msAssert.isFalse(importedStyle.getBuiltIn());
        msAssert.areEqual(ParagraphAlignment.CENTER, document.getStyles().get("MyStyle2").getParagraphFormat().getAlignment());
        TestUtil.saveOpen(builder.getDocument(), "Model\\Style\\AddCopyParaStyleRenamed2.docx");
    }

    @Test
    public void testAddCopyComplexStyle() throws Exception
    {
        Document src = TestUtil.open("Model\\Revision\\TestStyleRevisions.docx");
        StyleCollection sourceStyles = src.getStyles();
        DocumentBuilder builder = new DocumentBuilder();

        for (Style style : sourceStyles)
        {
            Style importedStyle = builder.getDocument().getStyles().getByName(style.getName(), false);
            if (importedStyle == null)
                importedStyle = builder.getDocument().getStyles().addCopy(style);

            switch (importedStyle.getType())
            {
                    case StyleType.PARAGRAPH:
                        writeCleanPara(builder);
                        builder.getCurrentParagraph().getParagraphFormat().setStyleName(importedStyle.getName());
                        builder.writeln(msString.format("Testing {0} style.", style.getName()));
                        break;
                    case StyleType.CHARACTER:
                        writeCleanPara(builder);
                        builder.pushFont();
                        builder.getFont().setStyle(importedStyle);
                        builder.writeln(msString.format("Testing {0} style.", importedStyle.getName()));
                        builder.popFont();
                        break;
                    case StyleType.LIST:
                        writeCleanPara(builder);
                        builder.getListFormat().setListId(importedStyle.getParaPr().getListId());
                        builder.writeln(msString.format("Testing {0} style.", importedStyle.getName()));
                        builder.getListFormat().removeNumbers();
                        break;
                    case StyleType.TABLE:
                        writeCleanPara(builder);
                        Table t = builder.startTable();
                        builder.insertCell();
                        if (t.getFirstRow().getTablePr().containsKey(TableAttr.ISTD))
                            t.getFirstRow().getTablePr().set(TableAttr.ISTD, importedStyle.getIstd());
                        else
                            t.getFirstRow().getTablePr().add(TableAttr.ISTD, importedStyle.getIstd());
                        builder.writeln(msString.format("Testing {0} style.", importedStyle.getName()));
                        builder.endRow();
                        builder.endTable();
                        break;
                    default:
                        Assert.fail();
                        break;
            }
        }

        TestUtil.saveOpen(builder.getDocument(), "Model\\Style\\AddCopyComplex.docx");
    }

    /// <summary>
    /// WORDSNET-7403 Style.BaseStyleName throws Exception System.InvalidOperationException.
    /// andrnosk: The problem occurs because we suppose that BaseStyleName property can be changed
    /// only for character and paragraph styles, but according to MS Word behavior,
    /// this property also can be modified for table styles.
    /// Fixed by adding StyleType.Table to the condition.
    /// </summary>
    @Test
    public void testJira7403() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestJira7403.docx");

        Style tableStyle = doc.getStyles().get("TableStyle");
        tableStyle.setBaseStyleName("Table Professional");

        msAssert.areEqual("Table Professional", tableStyle.getBaseStyleName());
    }

    /// <summary>
    /// Tests styles equality for cloned document with all MS Word 2007 styles.
    /// </summary>
    @Test
    public void testCompareAllStyles2007() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\AllStyles2007.docx");
        Document src = (Document)dst.deepClone(true);

        for (Style style : dst.getStyles())
        {
            Style style1 = style;
            Style style2 = src.getStyles().getByIstd(style.getIstd(), false);

            msAssert.isTrue(style1.equals(style2));
        }
    }

    /// <summary>
    /// Tests styles equality for cloned document with all MS Word 2003 styles.
    /// </summary>
    @Test
    public void testCompareAllStyles2003() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\AllStyles2003.docx");
        Document src = (Document)dst.deepClone(true);

        for (Style style : dst.getStyles())
        {
            Style style1 = style;
            Style style2 = src.getStyles().getByIstd(style.getIstd(), false);

            msAssert.isTrue(style1.equals(style2));
        }
    }

    /// <summary>
    /// Tests styles equality with different defaults.
    /// </summary>
    @Test
    public void testCompareStylesWithDifferentDefaults() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\StyleNormal1.docx");
        Document src = TestUtil.open("Model\\Style\\StyleNormal2.docx"); //Default font size was changed.

        Style style1 = dst.getStyles().get("MyNormal");
        Style style2 = src.getStyles().get("MyNormal");

        msAssert.areEqual(style1.getType(), StyleType.PARAGRAPH);
        msAssert.isFalse(style1.getBuiltIn());

        // We are not comparing defaults when comparing styles, so these styles are equal.
        msAssert.isTrue(style1.equals(style2));
    }

    /// <summary>
    /// Tests styles equality with different base styles.
    /// </summary>
    @Test
    public void testCompareStylesWithDifferentBaseStyle() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\TestStyles.docx");
        Document src = (Document)dst.deepClone(true);

        Style style1 = dst.getStyles().get("MyStyle");
        Style style2 = src.getStyles().get("MyStyle");

        msAssert.isTrue(style1.equals(style2));

        // Change base style font size.
        Style baseStyle1 = style1.getBaseStyle();
        baseStyle1.getFont().setSize(12.0);

        msAssert.isFalse(style1.equals(style2));
    }

    /// <summary>
    /// Tests styles equality with different linked styles.
    /// </summary>
    @Test
    public void testCompareLinkedStyles() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\TestStyles.docx");
        Document src = (Document)dst.deepClone(true);

        Style style1 = dst.getStyles().get("MyStyle");
        Style style2 = src.getStyles().get("MyStyle");

        msAssert.isTrue(style1.equals(style2));

        // Change linked style font size.
        Style linkedStyle1 = style1.getLinkedStyle();
        linkedStyle1.getFont().setSize(12.0);

        msAssert.isFalse(style1.equals(style2));
    }

    /// <summary>
    /// Tests equality of table styles.
    /// </summary>
    @Test
    public void testCompareTableStyles() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\TestStyles.docx");
        Document src = (Document)dst.deepClone(true);

        Style style1 = dst.getStyles().get("TableStyle1");
        Style style2 = src.getStyles().get("TableStyle1");

        msAssert.isTrue(style1.equals(style2));

        // Change something inside table style1.
        msAssert.areEqual(style1.getType(), StyleType.TABLE);
        ((TableStyle)style1).getRowPr().setHeightRule(HeightRule.AT_LEAST);

        // Styles are not same now.
        msAssert.isFalse(style1.equals(style2));
    }

    /// <summary>
    /// Tests different list styles are imported correctly.
    /// </summary>
    @Test
    public void testListLevelApi() throws Exception
    {
        Document dst = TestUtil.open("Model\\Style\\StyleNormal1.docx");
        Document src = TestUtil.open("Model\\Style\\StyleNormal2.docx"); // Style defaults are different.
        dst.appendDocument(src, ImportFormatMode.KEEP_DIFFERENT_STYLES);

        // There are two different styles: 'MyNormal' and 'ListParagraph'.
        // In addition, note that we compare completely expanded attributes
        // when take decision about styles equality.
        msAssert.areEqual(src.getStyles().getCount() + 2, dst.getStyles().getCount()); // List styles are different
        msAssert.isNull(dst.getStyles().get("List Paragraph").getRunPr().get(FontAttr.SIZE));
        msAssert.areEqual(28, dst.getStyles().get("List Paragraph_0").getRunPr().get(FontAttr.SIZE));
    }

    /// <summary>
    /// Tests equality of character styles.
    /// </summary>
    @Test
    public void testComparisonCharacterStyles() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");
        StyleCollection stylesA = docA.getStyles();
        StyleCollection stylesB = docB.getStyles();

        //Paragraph style should not affect character styles.
        stylesB.get("Normal").getParaPr().setLeftIndent(8);
        msAssert.isFalse(stylesA.get("Normal").equals(stylesB.get("Normal")));

        // Based on 'Default paragraph font'.
        msAssert.isTrue(stylesA.get("CharStyle1").equals(stylesB.get("CharStyle1")));
        msAssert.isFalse(stylesA.get("CharStyle1").equals(stylesB.get("CharStyle1Changed")));

        // Based on 'CharStyle1'.
        msAssert.isTrue(stylesA.get("CharStyle2").equals(stylesB.get("CharStyle2")));
        msAssert.isFalse(stylesA.get("CharStyle2").equals(stylesB.get("CharStyle2Changed")));
        msAssert.isFalse(stylesA.get("CharStyle2").equals(stylesB.get("CharStyle2OnlyBaseChanged")));
    }

    /// <summary>
    /// Tests equality of linked styles.
    /// </summary>
    @Test
    public void testComparisonLinkedStyles() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");

        msAssert.isTrue(docA.getStyles().get("ParaLinkedStyle3").equals(docB.getStyles().get("ParaLinkedStyle3")));
        msAssert.isFalse(docA.getStyles().get("ParaLinkedStyle3").equals(docB.getStyles().get("ParaLinkedStyle3OnlyLinkedChanged")));
    }

    /// <summary>
    /// Tests list styles equality.
    /// </summary>
    @Test
    public void testComparisonListStyles() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");

        // Styles have different ListId.
        msAssert.areNotEqual(docA.getStyles().get("ListStyle1").getParaPr().getListId(), docB.getStyles().get("ListStyle1").getParaPr().getListId());

        // But they compared correctly.
        msAssert.isTrue(docA.getStyles().get("ListStyle1").equals(docB.getStyles().get("ListStyle1")));
    }

    /// <summary>
    /// Test comparison of Lists.
    /// </summary>
    @Test
    public void testComparisonLists() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");
        List listA = doc.getLists().getListByListId(1);
        List listB = doc.getLists().getListByListId(2);
        List listC = doc.getLists().addCopy(listA);

        // Check ListLevels.
        msAssert.isTrue(listA.getListDef().getLevels().get(0).equals(listC.getListDef().getLevels().get(0)));
        msAssert.isFalse(listB.getListDef().getLevels().get(0).equals(listC.getListDef().getLevels().get(0)));

        // Check ListDefs.
        msAssert.isTrue(listA.getListDef().equals(listC.getListDef()));
        msAssert.isFalse(listB.getListDef().equals(listC.getListDef()));

        // Check Lists.
        msAssert.isTrue(listA.equals(listC));
        msAssert.isFalse(listB.equals(listC));
    }

    /// <summary>
    /// Tests comparison of list styles with dead loop in ListLevel.
    /// </summary>
    @Test
    public void testComparisonListStylesDeadLoop() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");
        StyleCollection stylesA = docA.getStyles();
        StyleCollection stylesB = docB.getStyles();

        // Make dead loop.
        docA.getLists().getListByListId(stylesA.get("ListStyle1").getParaPr().getListId()).getListLevels().get(0).setLinkedStyle(stylesA.get("ListStyle1"));
        docB.getLists().getListByListId(stylesB.get("ListStyle1").getParaPr().getListId()).getListLevels().get(0).setLinkedStyle(stylesB.get("ListStyle1"));
        msAssert.isTrue(stylesA.get("ListStyle1").equals(stylesB.get("ListStyle1")));

        docB.getLists().getListByListId(stylesB.get("ListStyle1").getParaPr().getListId()).getListLevels().get(0).getRunPr().setColor(DrColor.AQUA);
        msAssert.isFalse(stylesA.get("ListStyle1").equals(stylesB.get("ListStyle1")));
    }

    /// <summary>
    /// Tests equality styles with recursive loop to the next paragraph style.
    /// </summary>
    @Test
    public void testComparisonNextStylesDeadLoop() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");
        // Check styles with recursive loop to the next styles:
        // docA: S1->S2->S1,
        // docB: S1->S2->S3->S1 and S2 in docA not equal to S1 in docB.
        msAssert.isFalse(docA.getStyles().get("S1").equals(docB.getStyles().get("S1")));
        // Check styles with recursive loop to the next styles:
        // docA: C1->C2->C1,
        // docB: C1->C2->C3->C1 and all styles in both documents are equal.
        msAssert.isTrue(docA.getStyles().get("C1").equals(docB.getStyles().get("C1")));
    }

    /// <summary>
    /// Tests equality of non-modifiable styles.
    /// </summary>
    @Test
    public void testComparisonNonModifiableStyles() throws Exception
    {
        Document docA = new Document();
        Document docB = new Document();

        docA.getStyles().get("Default Paragraph Font").getRunPr().setSize(10);
        docB.getStyles().get("Default Paragraph Font").getRunPr().setSize(20);
        msAssert.isTrue(docA.getStyles().get("Default Paragraph Font").equals(docB.getStyles().get("Default Paragraph Font")));

        docA.getStyles().get("Table Normal").getParaPr().setAlignment(ParagraphAlignment.CENTER);
        docB.getStyles().get("Table Normal").getParaPr().setAlignment(ParagraphAlignment.RIGHT);
        msAssert.isTrue(docA.getStyles().get("Table Normal").equals(docB.getStyles().get("Table Normal")));
    }

    /// <summary>
    /// Tests styles equality when one of the styles is null.
    /// </summary>
    @Test
    public void testComparisonOneOfStylesNull() throws Exception
    {
        Document doc = new Document();

        msAssert.isFalse(doc.getStyles().get("Normal").equals(null));
    }

    /// <summary>
    /// Tests styles equality when one of the base styles is null.
    /// </summary>
    @Test
    public void testComparisonOneOfBaseStylesNull() throws Exception
    {
        Document DocA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document DocB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");
        StyleCollection stylesA = DocA.getStyles();
        StyleCollection stylesB = DocB.getStyles();

        stylesA.get("ListStyle1").setBasedOnIstd(stylesA.get("List Paragraph").getIstd());
        stylesB.get("ListStyle1").setBasedOnIstd(0);
        msAssert.isFalse(stylesA.get("ListStyle1").equals(stylesB.get("ListStyle1")));

        stylesA.get("ListStyle1").setBasedOnIstd(0);
        stylesB.get("ListStyle1").setBasedOnIstd(stylesB.get("List Paragraph").getIstd());
        msAssert.isFalse(stylesA.get("ListStyle1").equals(stylesB.get("ListStyle1")));
    }

    /// <summary>
    /// Tests equality of paragraph styles.
    /// </summary>
    @Test
    public void testComparisonParaStyles() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");

        // Based on 'Normal'.
        msAssert.isTrue(docA.getStyles().get("ParaStyle1").equals(docB.getStyles().get("ParaStyle1")));
        msAssert.isFalse(docA.getStyles().get("ParaStyle1").equals(docB.getStyles().get("ParaStyle1Changed")));

        // Based on 'ParaStyle1'.
        msAssert.isTrue(docA.getStyles().get("ParaStyle2").equals(docB.getStyles().get("ParaStyle2")));
        msAssert.isFalse(docA.getStyles().get("ParaStyle2").equals(docB.getStyles().get("ParaStyle2Changed")));
    }

    /// <summary>
    /// Tests equality of table styles.
    /// </summary>
    @Test
    public void testComparisonTableStyles() throws Exception
    {
        Document docA = TestUtil.open("Model\\Style\\TestStyleComparingA.docx");
        Document docB = TestUtil.open("Model\\Style\\TestStyleComparingB.docx");
        StyleCollection stylesA = docA.getStyles();
        StyleCollection stylesB = docB.getStyles();

        msAssert.isFalse(stylesA.get("Table Normal").equals(stylesB.get("TableStyle1")));

        //Paragraph style should not affect table styles.
        stylesB.get("Normal").getParaPr().setLeftIndent(8);
        msAssert.isFalse(stylesA.get("Normal").equals(stylesB.get("Normal")));

        // Based on 'Table Normal'.
        msAssert.isTrue(stylesA.get("TableStyle1").equals(stylesB.get("TableStyle1")));
        msAssert.isFalse(stylesA.get("TableStyle1").equals(stylesB.get("TableStyle1ChangedTblPr")));
        msAssert.isFalse(stylesA.get("TableStyle1").equals(stylesB.get("TableStyle1ChangedPPr")));
        msAssert.isFalse(stylesA.get("TableStyle1").equals(stylesB.get("TableStyle1ChangedRPr")));

        // Based on 'TableStyle1'.
        msAssert.isTrue(stylesA.get("TableStyle2").equals(stylesB.get("TableStyle2")));
        msAssert.isFalse(stylesA.get("TableStyle2").equals(stylesB.get("TableStyle2Changed")));
        msAssert.isFalse(stylesA.get("TableStyle2").equals(stylesB.get("TableStyle2BaseOnlyChanged")));
    }

    /// <summary>
    /// Tests 'Pair' correctly added and searched in HashSet.
    /// </summary>
    @Test
    public void testPair() throws Exception
    {
        Document doc = new Document();

        Style styleA = doc.getStyles().get("Normal");
        Style styleB = doc.getStyles().get("Table Normal");
        Style styleC = doc.getStyles().get("Default Paragraph Font");

        HashSetGeneric<Pair> hashSet = new HashSetGeneric<Pair>();

        hashSet.add(new Pair(styleA, styleB));

        msAssert.isTrue(hashSet.contains(new Pair(styleA, styleB)));
        msAssert.isFalse(hashSet.contains(new Pair(styleA, styleC)));
    }








    /// <summary>
    /// WORDSNET-14655 Incorrect formatting of 'Normal (Web)' after import from another document.
    /// This is duplicate for WORDSNET-14588
    /// </summary>
    @Test
    public void testJira14655() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Style\\TestJira14655Src.docx");
        Document dstDoc = TestUtil.open("Model\\Style\\TestJira14655Dst.docx");

        DocumentBuilder builder = new DocumentBuilder(dstDoc);
        builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        // Problematic style.
        Style style = dstDoc.getStyles().get("Normal (Web)");

        // Check attributes are expanded and collapsed.
        ParaPr paraPr = style.getParaPr();
        msAssert.isTrue((/*boolean*/Boolean)paraPr.get(ParaAttr.SPACE_AFTER_AUTO));

        RunPr runPr = style.getRunPr();
        msAssert.areEqual(24, runPr.get(FontAttr.SIZE));
        msAssert.areEqual(24, runPr.get(FontAttr.SIZE_BI));
    }

    // FOSS: TestJira15319 removed — its two .doc inputs contain private data and have been
    // removed from TestData (the Doc reader is gone in FOSS regardless). Not restored.







    /// <summary>
    /// Related to WORDSNET-16783
    /// Tests how style formatting is copied.
    /// </summary>
    @Test
    public void testJira16783A() throws Exception
    {
        String templateFileName = TestUtil.buildTestFileName("Model\\Style\\TestJira16783A Sample.docx");
        Document target = TestUtil.open("Model\\Style\\TestJira16783A Target.docx");

        target.copyStylesFromTemplate(templateFileName);

        Style style1 = target.getStyles().getByName("Style1", false);
        msAssert.areEqual(AttrBoolEx.TRUE, style1.getRunPr().get(FontAttr.BOLD));
        msAssert.areEqual(ParagraphAlignment.CENTER, style1.getParaPr().get(ParaAttr.ALIGNMENT));

        Style style2 = target.getStyles().getByName("Style2", false);
        msAssert.areEqual(style1.getIstd(), style2.getBasedOnIstd());
        msAssert.areEqual(AttrBoolEx.TRUE, style2.getRunPr().get(FontAttr.ITALIC));

        Style style3 = target.getStyles().getByName("Style3", false);
        msAssert.areEqual(style2.getIstd(), style3.getBasedOnIstd());
        msAssert.areEqual(Underline.SINGLE, style3.getRunPr().get(FontAttr.UNDERLINE));
    }

    /// <summary>
    /// Related to WORDSNET-16783
    /// Tests how style formatting is copied.
    /// </summary>
    @Test
    public void testJira16783B() throws Exception
    {
        String templateFileName = TestUtil.buildTestFileName("Model\\Style\\TestJira16783B Sample.docx");
        Document target = TestUtil.open("Model\\Style\\TestJira16783B Target.docx");

        target.copyStylesFromTemplate(templateFileName);

        Style style1 = target.getStyles().getByName("Style1", false);
        msAssert.isNull(style1.getRunPr().get(FontAttr.BOLD));

        Style style2 = target.getStyles().getByName("Style2", false);
        msAssert.areEqual(style1.getIstd(), style2.getBasedOnIstd());
        msAssert.areEqual(AttrBoolEx.TRUE, style2.getRunPr().get(FontAttr.ITALIC));

        Style style3 = target.getStyles().getByName("Style3", false);
        msAssert.areEqual(style2.getIstd(), style3.getBasedOnIstd());
        msAssert.areEqual(Underline.SINGLE, style3.getRunPr().get(FontAttr.UNDERLINE));
    }

    /// <summary>
    /// Related to WORDSNET-16783
    /// Tests that CopyAll method successfully passes circular dependency.
    /// </summary>
    @Test
    public void testJira16783Circular() throws Exception
    {
        Document sample = TestUtil.open("Model\\Style\\TestJira16783A Sample.docx");
        Document target = TestUtil.open("Model\\Style\\TestJira16783A Target.docx");

        Style style2 = sample.getStyles().getByName("Style2", false);
        Style style3 = sample.getStyles().getByName("Style3", false);

        // Style3 is based on Style2. Make circular dependency intentionally.
        style2.setBasedOnIstd(style3.getIstd());
        msAssert.areEqual(style2.getIstd(), style3.getBasedOnIstd());
        msAssert.areEqual(style3.getIstd(), style2.getBasedOnIstd());

        target.copyStylesFromTemplate(sample);

        style2 = target.getStyles().getByName("Style2", false);
        style3 = target.getStyles().getByName("Style3", false);

        msAssert.areEqual(style2.getIstd(), style3.getBasedOnIstd());
        msAssert.areEqual(style3.getIstd(), style2.getBasedOnIstd());
    }






    /// <summary>
    /// Relates to WORDSNET-17618
    /// Tests warning is issued when style that is being copied
    /// matches to a destination style of a different type.
    /// </summary>
    @Test
    public void testJira17618Warning() throws Exception
    {
        Document srcDoc = new Document();
        Document dstDoc = new Document();

        // Add styles with the same names but different types.
        srcDoc.getStyles().add(StyleType.CHARACTER, "Style1");
        dstDoc.getStyles().add(StyleType.PARAGRAPH, "Style1");

        dstDoc.setWarningCallback(new WarningInfoCollection());
        dstDoc.copyStylesFromTemplate(srcDoc);

        // Check warning.
        msAssert.isTrue(TestUtil.containsWarning((WarningInfoCollection)dstDoc.getWarningCallback(), WarningType.DATA_LOSS,
                WarningSource.UNKNOWN, msString.format(WarningStrings.CANNOT_COPY_STYLE, "Style1")));
        // Check style was not overridden.
        msAssert.areEqual(StyleType.PARAGRAPH, dstDoc.getStyles().get("Style1").getType());
    }

    /// <summary>
    /// WORDSNET-17878 Copied styles don't match the source styles.
    /// Styles after copying lost numbering. So styles int he destination don't match the source styles.
    /// Preserve numbering to fix the problem.
    /// </summary>
    @Test
    public void testJira17878() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Style\\TestJira17878Source.docx");
        Document targetDoc = TestUtil.open("Model\\Style\\TestJira17878Target.docx");

        targetDoc.copyStylesFromTemplate(srcDoc);

        // Check list count in the target document.
        ListCollection lists = targetDoc.getLists();
        msAssert.areEqual(4, lists.getCount());

        // Check that list and its definition exist in the appropriate collections.
        Style style = targetDoc.getStyles().getByName("My Custom Style 1", false);
        List list = lists.getListByListId((/*int*/Integer)style.getParaPr().get(ParaAttr.LIST_ID));

        msAssert.less(-1, lists.getIndexOfListDefByListDefId(list.getListDefId()), "List definition for \"My Custom Style 1\" was not found.");

        style = targetDoc.getStyles().getByName("My Custom Style 2", false);
        list = lists.getListByListId((/*int*/Integer)style.getParaPr().get(ParaAttr.LIST_ID));

        msAssert.less(-1, lists.getIndexOfListDefByListDefId(list.getListDefId()), "List definition for \"My Custom Style 2\" was not found.");
    }



    /// <summary>
    /// Related to WORDSNET-20094 Tests comparision of table styles.
    /// </summary>
    @Test
    public void testComparingTableStyles() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestComparingTableStyles.docx");
        Style tableStyle1 = doc.getStyles().get("TableStyle1");
        Style tableStyle2 = doc.getStyles().get("TableStyle2");
        Style tableStyle3 = doc.getStyles().get("TableStyle3");

        msAssert.isTrue(tableStyle1.equals(tableStyle2));
        msAssert.areEqual(DuplicateStyleRemover.getStyleHashCodeForEquality(tableStyle1), DuplicateStyleRemover.getStyleHashCodeForEquality(tableStyle2));
        msAssert.isFalse(tableStyle1.equals(tableStyle3));
        msAssert.isFalse(tableStyle2.equals(tableStyle3));
    }


    /// <summary>
    /// Checks numbered style.
    /// </summary>
    private static void checkNumberedStyle(Style style, int expectedLeftIndent)
    {
        // It is very important that the name of a linked style in list level will be the same as name of style
        // that refers to this list. Note, even if AW shows correct ListLabel for a paragraph with style
        // that violates this condition, Word does not recognize such list as a correct one and
        // does not show list label for that style.
        ListLevel listLevel = getListLevel(style);
        msAssert.areEqual(style.getName(), listLevel.getLinkedStyle().getName());
        // Check also indent to verify that we have copied a correct ListDef.
        msAssert.areEqual(expectedLeftIndent, listLevel.getParaPr().getLeftIndent());
    }

    /// <summary>
    /// Returns ListLevel of the specified style.
    /// </summary>
    private static ListLevel getListLevel(Style style)
    {
        List list = style.getDocument().getLists().getListByListId(style.getParaPr().getListId());
        ListLevel listLevel = list.getListLevels().get(style.getParaPr().getListLevel());

        return listLevel;
    }

    private static void writeCleanPara(DocumentBuilder builder)
    {
        builder.getCurrentParagraph().getParagraphFormat().setStyleName("Normal");
        builder.insertParagraph();
    }

    /// <summary>
    /// Adds copy of all "List Bullet" and "List Number" styles from one document into another.
    /// </summary>
    private static void copyListBulletAndNumberStyles(Document srcDoc, Document dstDoc)
    {
        Style dstStyle;

        // Copy all "List Bullet" styles.
        for (Style srcStyle : srcDoc.getStyles())
            if (srcStyle.getName().startsWith("List Bullet"))
            {
                dstStyle = dstDoc.getStyles().addCopy(srcStyle);
                dstStyle.setName(srcStyle.getName());
            }

        // Copy "List Number" style.
        Style srcListNumberStyle = srcDoc.getStyles().get("List Number");
        dstStyle = dstDoc.getStyles().addCopy(srcListNumberStyle);
        dstStyle.setName(srcListNumberStyle.getName());
    }

    /// <summary>
    /// Checks list level attributes.
    /// </summary>
    private static void checkListLevel(ListLevel listLevel, double numberPosition, double tabPosition, double textPosition)
    {
        msAssert.areEqual(numberPosition, listLevel.getNumberPosition());
        msAssert.areEqual(tabPosition, listLevel.getTabPosition());
        msAssert.areEqual(textPosition, listLevel.getTextPosition());
    }

    /// <summary>
    /// Replaces style with the specified name.
    /// </summary>
    /// <returns>Replaced style.</returns>
    /// <remarks>
    /// This is helper method for <see cref="TestJira14595"/>
    /// that replaces style using customer's scenario.
    /// </remarks>
    private static Style replaceStyle(String styleName, StyleCollection srcStyles, StyleCollection dstStyles)
    {
        final String TMP_STYLE_NAME = "tmpName";
        final String TMP_LINKED_STYLE_NAME = "tmpLinkedName";

        Style dstStyle = dstStyles.get(styleName);

        // Rename existing style to allow new added style have a good name.
        if (dstStyle != null)
        {
            dstStyle.setName(TMP_STYLE_NAME);

            // If style has linked style, then rename it as well.
            Style dstLinkedStyle = dstStyles.get(dstStyle.getLinkedStyleName());
            if (dstLinkedStyle != null)
                dstLinkedStyle.setName(TMP_LINKED_STYLE_NAME);
        }

        Style srcStyle = srcStyles.get(styleName);
        Style newStyle = dstStyles.addCopy(srcStyle);

        // Rename imported style to temporary name to override old style.
        newStyle.setName(TMP_STYLE_NAME);
        // Recover original name.
        newStyle.setName(styleName);

        // Rename linked style the same way as main style to override it too.
        String linkedStyleName = newStyle.getLinkedStyleName();
        Style newLinkedStyle = dstStyles.get(linkedStyleName);
        if (newLinkedStyle != null)
        {
            newLinkedStyle.setName(TMP_LINKED_STYLE_NAME);
            newLinkedStyle.setName(linkedStyleName);
        }

        return newStyle;
    }
}

