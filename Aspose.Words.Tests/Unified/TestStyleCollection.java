// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.lang.Class;
import com.aspose.TestUtilPal;
import com.aspose.Debug;
import java.awt.Color;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Tests that operate on and mostly involve the style collection in the document.
/// </summary>
@Test
public class TestStyleCollection
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Test that empty doc has some default styles and test some basic operations on the Styles collection.
    /// </summary>
    @Test
    public void testEmptyDoc() throws Exception
    {
        Document doc = new Document();

        StyleCollection styles = doc.getStyles();
        msAssert.areEqual(4, styles.getCount()); //Default Paragraph Font, No List, Normal, TableNormal
        msAssert.areEqual(15, styles.getNextFreeIstd());

        //Styles are sorted by istd so the first is Normal
        Style style = styles.get(0);
        msAssert.areEqual("Normal", style.getName());
        msAssert.areEqual(StyleIndex.NORMAL, style.getIstd());

        //Test get by name
        msAssert.isNotNull(styles.get("No List"));
        msAssert.isNull(styles.get("XXX"));

        //Test get by istd
        msAssert.isNotNull(styles.getByIstd(StyleIndex.DEFAULT_PARAGRAPH_FONT, false));
        msAssert.isNull(styles.getByIstd(StyleIndex.HEADING_1, false));
    }

    /// <summary>
    /// Test adding a user defined style works.
    /// </summary>
    @Test
    public void testAddUserStyle() throws Exception
    {
        Document doc = new Document();

        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "AAA");

        msAssert.isNotNull(doc.getStyles().get("AAA"));
        msAssert.areEqual("AAA", style.getName());
        msAssert.areEqual(StyleIdentifier.USER, style.getStyleIdentifier());
        msAssert.areEqual("", style.getBaseStyleName());
        msAssert.areEqual("AAA", style.getNextParagraphStyleName());
        msAssert.areEqual(false, style.getBuiltIn());
        msAssert.areEqual(10d, style.getFont().getSize());
        msAssert.areEqual(0d, style.getParagraphFormat().getSpaceBefore());

        //Normal in Word2003 has font size 12pt and it will be inherited.
        style.setBaseStyleName("Normal");
        msAssert.areEqual(12d, style.getFont().getSize());
    }

    /// <summary>
    /// Test that built in styles are created on demand in the document.
    /// </summary>
    @Test
    public void testCreateBuiltinOnDemand() throws Exception
    {
        Document doc = new Document();

        //By the default only 4 styles in the collection.
        msAssert.areEqual(4, doc.getStyles().getCount());

        //Requesting a built in style by name that is not in a collection, creates it.
        Style style = doc.getStyles().get("Heading 1");
        msAssert.isNotNull(style);
        msAssert.areEqual(StyleIndex.HEADING_1, style.getIstd());
        // RK FOSS The default font size changed from 16 to 14 when I upgraded from Blank.doc to Blank.docx. Recheck later.
        msAssert.areEqual(14d, style.getFont().getSize());
        msAssert.areEqual(5, doc.getStyles().getCount());

        //Requesting a built in style by identifier also creates it.
        style = doc.getStyles().getByStyleIdentifier(StyleIdentifier.HEADING_2);
        msAssert.isNotNull(style);
        msAssert.areEqual(6, doc.getStyles().getCount());

        //Requesting a built in style with a non fixed istd, check istd is changed during import.
        style = doc.getStyles().get("Message Header");
        msAssert.isNotNull(style);
        msAssert.areEqual(7, doc.getStyles().getCount());
        //Istd and NextIstd will be 15 because its the first non fixed istd in our document.
        msAssert.areEqual(15, style.getIstd());
        msAssert.areEqual(15, style.getNextIstd());

        //Requesting a built in style indirectly also creates.
        style.setNextParagraphStyleName("Heading 3");
        msAssert.areEqual(8, doc.getStyles().getCount());
    }

    /// <summary>
    /// This test checks that the embedded resource AllStyles.docx contains what we expect it to contain.
    /// </summary>
    @Test
    public void testAllBuiltInStyles2007AreInResource() throws Exception
    {
        Document doc = new Document();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2007);

        // This loads the document from the embedded resource.
        StyleCollection styleCollection = new StyleCollection(doc);

        StyleCollection styles = styleCollection.getBuiltInStyles();

        // We expect only built-in styles here.
        for (Style style : styles)
        {
            // The only exception we can allow is for character styles linked to built-in paragraph styles.
            if (!style.getBuiltIn())
            {
                switch (gStringSwitchMap.of(style.getName()))
                {
                    case /*"Intense Quote Char"*/0:
                    case /*"Quote Char"*/1:
                    case /*"Note Heading Char"*/2:
                        break;
                    default:
                        Assert.fail("A user defined style found: " + style.getName());
                        break;
                }
            }
        }

        Class styleIdentifierType = StyleIdentifier.class;
        int[] styleIdentifiers = TestUtilPal.getEnumValues(styleIdentifierType);

        // This is to allow porting to Java.
        for (int stiInt : styleIdentifiers)
        {
            /*StyleIdentifier*/int sti = (/*StyleIdentifier*/int) stiInt;

            if ((sti == StyleIdentifier.USER) || (sti == StyleIdentifier.NIL))
                continue;

            if (styles.getBySti(sti, false) == null)
            {
                switch (sti)
                {
                    case StyleIdentifier.HTML_TOP_OF_FORM:
                    case StyleIdentifier.HTML_BOTTOM_OF_FORM:
                        // These are obscure or rare styles. If the user requests to create such style he will not be able
                        // to do so because the resource document does not have them and its okay.
                        break;

                    case StyleIdentifier.OUTLINE_LIST_1:
                    case StyleIdentifier.OUTLINE_LIST_2:
                    case StyleIdentifier.OUTLINE_LIST_3:
                        // These are list styles for outline formatting. I don't understand why MS Word does not create them in the this document.
                        // But it is okay because I think we have list style creation hardcoded somewhere in the lists code.
                        break;

                    default:
                        if (sti >= StyleIdentifier.PLAIN_TABLE_1 &&
                            sti <= StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_6)
                            // These are list styles for tables in word 2013.
                            // They aren't exist in word 2007.
                            break;

                        Debug.writeLine("Cannot find style in the embedded resource: " + StyleIdentifier.toString(sti));
                        break;
                }
            }
        }

        msAssert.areEqual(378, styleIdentifiers.length); // This is how many we have defined in the enum.
        msAssert.areEqual(265, styles.getCount()); // This is how many we have in the document.
        // The difference is made up of:
        // MINUS
        // 1. User
        // 2. Nil
        // 3. HtmlTopOfForm
        // 4. HtmlBottomOfForm
        // 5. OutlineList1
        // 6. OutlineList2
        // 7. OutlineList3
        // 8. from StyleIdentifier.PlainTable1 to StyleIdentifier.ListTable7ColorfulAccent6, it is 104 styles
        //
        // PLUS
        // 1. Intense Quote Char
        // 2. Quote Char
        // 3. Note Heading Char
        //
        // TOTAL DIFF = 108
    }

    /// <summary>
    /// This test checks that the embedded resource AllStyles2013.docx contains what we expect it to contain.
    /// </summary>
    @Test
    public void testAllBuiltInStyles2013AreInResource() throws Exception
    {
        Document doc = new Document();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2013);

        // This loads the document from the embedded resource.
        StyleCollection styleCollection = new StyleCollection(doc);

        StyleCollection styles = styleCollection.getBuiltInStyles();

        // We expect only built-in styles here.
        for (Style style : styles)
        {
            // The only exception we can allow is for character styles linked to built-in paragraph styles.
            if (!style.getBuiltIn())
            {
                switch (gStringSwitchMap.of(style.getName()))
                {
                    case /*"Balloon Text Char"*/3:
                    case /*"Body Text 2 Char"*/4:
                    case /*"Body Text 3 Char"*/5:
                    case /*"Body Text Char"*/6:
                    case /*"Body Text First Indent 2 Char"*/7:
                    case /*"Body Text First Indent Char"*/8:
                    case /*"Body Text Indent 2 Char"*/9:
                    case /*"Body Text Indent 3 Char"*/10:
                    case /*"Body Text Indent Char"*/11:
                    case /*"Closing Char"*/12:
                    case /*"Comment Subject Char"*/13:
                    case /*"Comment Text Char"*/14:
                    case /*"Date Char"*/15:
                    case /*"Document Map Char"*/16:
                    case /*"E-mail Signature Char"*/17:
                    case /*"Endnote Text Char"*/18:
                    case /*"Footer Char"*/19:
                    case /*"Footnote Text Char"*/20:
                    case /*"HTML Address Char"*/21:
                    case /*"HTML Preformatted Char"*/22:
                    case /*"Header Char"*/23:
                    case /*"Heading 1 Char"*/24:
                    case /*"Heading 2 Char"*/25:
                    case /*"Heading 3 Char"*/26:
                    case /*"Heading 4 Char"*/27:
                    case /*"Heading 5 Char"*/28:
                    case /*"Heading 6 Char"*/29:
                    case /*"Heading 7 Char"*/30:
                    case /*"Heading 8 Char"*/31:
                    case /*"Heading 9 Char"*/32:
                    case /*"Intense Quote Char"*/0:
                    case /*"Macro Text Char"*/33:
                    case /*"Message Header Char"*/34:
                    case /*"Note Heading Char"*/2:
                    case /*"Plain Text Char"*/35:
                    case /*"Quote Char"*/1:
                    case /*"Salutation Char"*/36:
                    case /*"Signature Char"*/37:
                    case /*"Subtitle Char"*/38:
                    case /*"Title Char"*/39:
                        break;
                    default:
                        Assert.fail("A user defined style found: " + style.getName());
                        break;
                }
            }
        }

        Class styleIdentifierType = StyleIdentifier.class;
        int[] styleIdentifiers = TestUtilPal.getEnumValues(styleIdentifierType);

        // This is to allow porting to Java.
        for (int stiInt : styleIdentifiers)
        {
            /*StyleIdentifier*/int sti = (/*StyleIdentifier*/int) stiInt;

            if ((sti == StyleIdentifier.USER) || (sti == StyleIdentifier.NIL))
                continue;

            if (styles.getBySti(sti, false) == null)
            {
                switch (sti)
                {
                    case StyleIdentifier.HTML_TOP_OF_FORM:
                    case StyleIdentifier.HTML_BOTTOM_OF_FORM:
                        // These are obscure or rare styles. If the user requests to create such style he will not be able
                        // to do so because the resource document does not have them and its okay.
                        break;

                    case StyleIdentifier.OUTLINE_LIST_1:
                    case StyleIdentifier.OUTLINE_LIST_2:
                    case StyleIdentifier.OUTLINE_LIST_3:
                        // These are list styles for outline formatting. I don't understand why MS Word does not create them in the this document.
                        // But it is okay because I think we have list style creation hardcoded somewhere in the lists code.
                        break;

                    default:
                        if (sti >= StyleIdentifier.TABLE_SIMPLE_1 &&
                            sti <= StyleIdentifier.COLORFUL_GRID_ACCENT_6)
                            // These are list styles for tables in word 2007.
                            // They aren't exist in word 2013.
                            break;

                        Debug.writeLine("Cannot find style in the embedded resource: " + StyleIdentifier.toString(sti));
                        break;
                }
            }
        }

        msAssert.areEqual(378, styleIdentifiers.length); // This is how many we have defined in the enum.
        msAssert.areEqual(264, styles.getCount()); // This is how many we have in the document.
        // The difference is made up of:
        // MINUS
        // 1. User
        // 2. Nil
        // 3. HtmlTopOfForm
        // 4. HtmlBottomOfForm
        // 5. OutlineList1
        // 6. OutlineList2
        // 7. OutlineList3
        // 8. from StyleIdentifier.TableSimple1 to StyleIdentifier.ColorfulGridAccent6, it is 142 styles
        //
        // PLUS 40 Linked Char Styles
        //
        // TOTAL DIFF = 109
    }

    /// <summary>
    /// WORDSNET-8813 UseDestinationStyles is not working with Heading styles.
    /// There is DOCM format, we have to get BuildIn StyleCollection depending on load format.
    /// DOCM/DOTM/DOTX etc have been added to switch.
    /// </summary>
    @Test
    public void testJira8813()
    {
        StyleCollection styles = StyleCollection.getBuiltInStyles(LoadFormat.DOCM);

        msAssert.areEqual(265, styles.getCount());

        //Requesting a built in style by name.
        Style style = styles.get("Heading 1");
        msAssert.isNotNull(style);

        msAssert.areEqual(new Color((54), (95), (145), (255)), style.getFont().getColor());
    }




    @Test
    public void testStyleCollectionEnumerator() throws Exception
    {
        Document document = TestUtil.open("Model\\Style\\TestStyleCollectionEnumerator.docx");
        StyleCollection styles = document.getStyles();

        int count = 0;
        Iterator<Style> enumerator = styles.iterator();
        while (enumerator.hasNext())
        {
            Object current = enumerator.next();
            msAssert.isNotNull(current);
            count++;
        }

        msAssert.areEqual(styles.getCount(), count);
    }




    private static boolean toBeDeleted(Style style)
    {
        ArrayList<Integer> stylesToKeep = new ArrayList<Integer>();
                {
                    stylesToKeep.add(StyleIdentifier.DEFAULT_PARAGRAPH_FONT);
                    stylesToKeep.add(StyleIdentifier.NORMAL);
                    stylesToKeep.add(StyleIdentifier.NO_LIST);
                    stylesToKeep.add(StyleIdentifier.TABLE_NORMAL);
                }

        if (stylesToKeep.contains(style.getStyleIdentifier()) || style.getName().equals("1Bullets"))
            return false;

        return true;
    }

    private static void deleteStyle(Style style)
    {
        Style replaceWith;
        switch (style.getType())
        {
            case StyleType.PARAGRAPH:
                replaceWith = style.getStyles().getByStyleIdentifier(StyleIdentifier.NORMAL);
                break;
            case StyleType.CHARACTER:
                replaceWith = style.getStyles().getByStyleIdentifier(StyleIdentifier.DEFAULT_PARAGRAPH_FONT);
                break;
            case StyleType.TABLE:
                replaceWith = style.getStyles().getByStyleIdentifier(StyleIdentifier.TABLE_NORMAL);
                break;
            case StyleType.LIST:
                replaceWith = style.getStyles().getByStyleIdentifier(StyleIdentifier.NO_LIST);
                break;
            default:
                throw new IllegalArgumentException();
        }

        String normalName = replaceWith.getName();
        replaceWith.setName(style.getName());
        replaceWith.setName(normalName);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Intense Quote Char",
		"Quote Char",
		"Note Heading Char",
		"Balloon Text Char",
		"Body Text 2 Char",
		"Body Text 3 Char",
		"Body Text Char",
		"Body Text First Indent 2 Char",
		"Body Text First Indent Char",
		"Body Text Indent 2 Char",
		"Body Text Indent 3 Char",
		"Body Text Indent Char",
		"Closing Char",
		"Comment Subject Char",
		"Comment Text Char",
		"Date Char",
		"Document Map Char",
		"E-mail Signature Char",
		"Endnote Text Char",
		"Footer Char",
		"Footnote Text Char",
		"HTML Address Char",
		"HTML Preformatted Char",
		"Header Char",
		"Heading 1 Char",
		"Heading 2 Char",
		"Heading 3 Char",
		"Heading 4 Char",
		"Heading 5 Char",
		"Heading 6 Char",
		"Heading 7 Char",
		"Heading 8 Char",
		"Heading 9 Char",
		"Macro Text Char",
		"Message Header Char",
		"Plain Text Char",
		"Salutation Char",
		"Signature Char",
		"Subtitle Char",
		"Title Char"
	);

}

