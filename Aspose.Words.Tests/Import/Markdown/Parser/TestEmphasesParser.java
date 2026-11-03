// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Emphases feature.
/// </summary>
public class TestEmphasesParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests a flanking type of delimiter run.
    /// </summary>
    @Test
    public void testDelimiterFlanking()
    {
        checkDelimiterRun("***abc", FlankingType.LEFT, "***");
        checkDelimiterRun("  _abc", FlankingType.LEFT, "_");
        checkDelimiterRun("**\"abc\"", FlankingType.LEFT, "**");
        checkDelimiterRun(" _\"abc\"", FlankingType.LEFT, "_");

        checkDelimiterRun("abc***", FlankingType.RIGHT, "***");
        checkDelimiterRun("abc_", FlankingType.RIGHT, "_");
        checkDelimiterRun("\"abc\"**", FlankingType.RIGHT, "**");
        checkDelimiterRun("\"abc\"_", FlankingType.RIGHT, "_");

        checkDelimiterRun("abc***def", FlankingType.BOTH, "***");
        checkDelimiterRun("\"abc\"_\"def\"", FlankingType.BOTH, "_");

        checkDelimiterRun("abc *** def", FlankingType.NONE, "***");
        checkDelimiterRun("a _ b", FlankingType.NONE, "_");
    }

    /// <summary>
    /// Tests simple emphasis.
    /// </summary>
    @Test
    public void testEmphasisA() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisA.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo bar*")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the opening * is followed by whitespace,
    /// and hence not part of a left-flanking delimiter run.
    /// </summary>
    @Test
    public void testEmphasisB() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisB.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "a * foo bar*")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the opening * is preceded by an alphanumeric and followed by punctuation,
    /// and hence not part of a left-flanking delimiter run
    /// </summary>
    @Test
    public void testEmphasisC() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisC.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "a*\"foo\"*");
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "a*\"foo\"*")});
    }

    /// <summary>
    /// Tests intraword emphasis with * is permitted.
    /// </summary>
    @Test
    public void testEmphasisD() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisD.md");

        msAssert.areEqual(1, doc.getCount());
        checkParagraph(doc.get(0), "foo*bar*");
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo"), new ExpectedInline(BlockType.ITALIC_INLINE, "*bar*")});
    }

    /// <summary>
    /// Tests intraword emphasis with * is permitted.
    /// </summary>
    @Test
    public void testEmphasisE() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisE.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "5"), new ExpectedInline(BlockType.ITALIC_INLINE, "*6*"), new ExpectedInline(BlockType.INLINE, "78")});
    }

    /// <summary>
    /// Tests simple emphasis with underscore.
    /// </summary>
    @Test
    public void testEmphasisF() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisF.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo bar*")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the opening _ is followed by whitespace.
    /// </summary>
    @Test
    public void testEmphasisG() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisG.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "_ foo bar_")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the opening _ is preceded by an alphanumeric and followed by punctuation.
    /// </summary>
    @Test
    public void testEmphasisH() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisH.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "a_\"foo\"_")});
    }

    /// <summary>
    /// Tests that emphasis with _ is not allowed inside words.
    /// </summary>
    @Test
    public void testEmphasisI() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisI.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo_bar_")});
    }

    /// <summary>
    /// Tests that emphasis with _ is not allowed inside words.
    /// </summary>
    @Test
    public void testEmphasisJ() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisJ.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "5_6_78")});
    }

    /// <summary>
    /// Tests that here _ does not generate emphasis, because the first delimiter run is right-flanking and
    /// the second left-flanking.
    /// </summary>
    @Test
    public void testEmphasisK() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisK.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "aa_\"bb\"_cc")});
    }

    /// <summary>
    /// Tests that this is emphasis, even though the opening delimiter is both left- and right-flanking,
    /// because it is preceded by punctuation.
    /// </summary>
    @Test
    public void testEmphasisL() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisL.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo-"), new ExpectedInline(BlockType.ITALIC_INLINE, "*(bar)*")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the closing delimiter does not match the opening delimiter.
    /// </summary>
    @Test
    public void testEmphasisM() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisM.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "_foo*")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the closing * is preceded by whitespace.
    /// </summary>
    @Test
    public void testEmphasisN() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisN.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*foo bar *")});
    }

    /// <summary>
    /// Tests a newline also counts as whitespace.
    /// </summary>
    @Test
    public void testEmphasisO() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisO.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*foo bar"+SOFT_BREAK+"*")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the second * is preceded by punctuation and followed by an
    /// alphanumeric (hence it is not part of a right-flanking delimiter run).
    /// </summary>
    @Test
    public void testEmphasisP() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisP.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*(*foo)")});
    }


    /// <summary>
    /// Tests that intraword emphasis with * is allowed.
    /// </summary>
    @Test
    public void testEmphasisR() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisR.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo*"), new ExpectedInline(BlockType.INLINE, "bar")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the closing _ is preceded by whitespace.
    /// </summary>
    @Test
    public void testEmphasisS() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisS.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "_foo bar _")});
    }

    /// <summary>
    /// Tests this is not emphasis, because the second _ is preceded by punctuation and followed by an alphanumeric.
    /// </summary>
    @Test
    public void testEmphasisT() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisT.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "_(_foo)")});
    }


    /// <summary>
    /// Tests that intraword emphasis is disallowed for _.
    /// </summary>
    @Test
    public void testEmphasisV() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisV.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "_foo_bar"+SOFT_BREAK+"_пристаням_стремятся"+SOFT_BREAK),
                new ExpectedInline(BlockType.ITALIC_INLINE, "*foo_bar_baz*")
            });
    }

    /// <summary>
    /// Tests that this is emphasis, even though the closing delimiter is both left- and right-flanking,
    /// because it is followed by punctuation.
    /// </summary>
    @Test
    public void testEmphasisW() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisW.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*(bar)*"), new ExpectedInline(BlockType.INLINE, ".")});
    }

    /// <summary>
    /// Tests strong emphasis.
    /// </summary>
    @Test
    public void testEmphasisX() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisX.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo bar**")});
    }

    /// <summary>
    /// Tests this is not strong emphasis, because the opening delimiter is followed by whitespace.
    /// </summary>
    @Test
    public void testEmphasisY() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisY.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "** foo bar**")});
    }

    /// <summary>
    /// Tests this is not strong emphasis, because the opening ** is preceded by an alphanumeric and followed
    /// by punctuation, and hence not part of a left-flanking delimiter run.
    /// </summary>
    @Test
    public void testEmphasisZ() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "a**\"foo\"**")});
    }

    /// <summary>
    /// Tests that intraword strong emphasis with ** is permitted.
    /// </summary>
    @Test
    public void testEmphasisZ1() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ1.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo"), new ExpectedInline(BlockType.BOLD_INLINE, "**bar**")});
    }

    /// <summary>
    /// Tests this is not strong emphasis, because the opening delimiter is followed by whitespace.
    /// </summary>
    @Test
    public void testEmphasisZ2() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ2.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "__ foo bar__")});
    }

    /// <summary>
    /// Tests that a newline counts as whitespace.
    /// </summary>
    @Test
    public void testEmphasisZ3() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ3.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "__"+SOFT_BREAK+"foo bar__")});
    }

    /// <summary>
    /// Tests that intraword strong emphasis is forbidden with __.
    /// </summary>
    @Test
    public void testEmphasisZ4() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ4.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "a__\"foo\"__")});
    }

    /// <summary>
    /// Tests this is not strong emphasis, because the opening __ is preceded by an alphanumeric
    /// and followed by punctuation.
    /// </summary>
    @Test
    public void testEmphasisZ5() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ5.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo__bar__"+SOFT_BREAK+"5__6__78"+SOFT_BREAK+"пристаням__стремятся__")});
    }

    /// <summary>
    /// Tests nested strong emphasis.
    /// </summary>
    @Test
    public void testEmphasisZ6() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ6.md");

        msAssert.areEqual(1, doc.getCount());
        ParagraphBlock paragraphBlock = (ParagraphBlock)doc.get(0);
        checkInlines(paragraphBlock, new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo, **bar**, baz**")});

        checkInlines(paragraphBlock.get(0),
            new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo, "), new ExpectedInline(BlockType.BOLD_INLINE, "**bar**"), new ExpectedInline(BlockType.INLINE, ", baz")});
    }

    /// <summary>
    /// Tests that this is strong emphasis, even though the opening delimiter is both left- and right-flanking,
    /// because it is preceded by punctuation.
    /// </summary>
    @Test
    public void testEmphasisZ7() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ7.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "foo-"), new ExpectedInline(BlockType.BOLD_INLINE, "**(bar)**")});
    }

    /// <summary>
    /// Tests that this is not strong emphasis, because the closing delimiter is preceded by whitespace.
    /// Nor can it be interpreted as an emphasized *foo bar *, because a literal * character cannot occur at the
    /// beginning or end of *-delimited emphasis or **-delimited strong emphasis, unless it is backslash-escaped.
    /// </summary>
    @Test
    public void testEmphasisZ8() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ8.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "**foo bar **")});
    }

    /// <summary>
    /// Tests that this is not strong emphasis, because the second ** is preceded by punctuation
    /// and followed by an alphanumeric.
    /// </summary>
    @Test
    public void testEmphasisZ9() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ9.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "**(**foo)")});
    }

    /// <summary>
    /// Tests nested emphases.
    /// </summary>
    @Test
    public void testEmphasisZ10() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ10.md");

        msAssert.areEqual(3, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*(**foo**)*")});
        checkInlines(doc.get(1),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.BOLD_INLINE, "**Gomphocarpus (*Gomphocarpus physocarpus*, syn."+SOFT_BREAK+"*Asclepias physocarpa*)**")
            });
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo \"*bar*\" foo**")});
    }

    /// <summary>
    /// Tests intraword strong emphasis.
    /// </summary>
    @Test
    public void testEmphasisZ11() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ11.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo**"), new ExpectedInline(BlockType.INLINE, "bar")});
    }

    /// <summary>
    /// Tests that this is not strong emphasis, because the closing delimiter is preceded by whitespace.
    /// </summary>
    @Test
    public void testEmphasisZ12() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ12.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "__foo bar __")});
    }

    /// <summary>
    /// Tests this is not strong emphasis, because the second __ is preceded by punctuation and followed by an alphanumeric.
    /// </summary>
    @Test
    public void testEmphasisZ13() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ13.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "__(__foo)")});
    }

    /// <summary>
    /// Tests nested emphases.
    /// </summary>
    @Test
    public void testEmphasisZ14() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ14.md");

        msAssert.areEqual(1, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*(**foo**)*")});
    }

    /// <summary>
    /// Tests that intraword strong emphasis is forbidden with __.
    /// </summary>
    @Test
    public void testEmphasisZ15() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ15.md");

        msAssert.areEqual(3, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "__foo__bar")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "__пристаням__стремятся")});
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo__bar__baz**")});
    }

    /// <summary>
    /// Tests that any nonempty sequence of inline elements can be the contents of an emphasized span.
    /// </summary>
    @Test
    public void testEmphasisZ16() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ16.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo bar/url*")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo"+SOFT_BREAK+"bar*")});
    }

    /// <summary>
    /// Tests that emphasis and strong emphasis can be nested inside emphasis.
    /// </summary>
    @Test
    public void testEmphasisZ17() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ17.md");

        msAssert.areEqual(7, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo **bar** baz*")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo *bar* baz*")});
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "**foo* bar*")});
        checkInlines(doc.get(3), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo *bar**")});
        checkInlines(doc.get(4), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo **bar** baz*")});
        checkInlines(doc.get(5), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "***foo** bar*")});
        checkInlines(doc.get(6), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo **bar***")});
    }

    /// <summary>
    /// Tests that a delimiter that can both open and close cannot form emphasis if the sum of the lengths
    /// of the delimiter runs containing the opening and closing delimiters is a multiple of 3.
    /// </summary>
    @Test
    public void testEmphasisZ18() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ18.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo**bar**baz*")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo**bar***")});
    }

    /// <summary>
    /// Tests that indefinite levels of nesting are possible.
    /// </summary>
    @Test
    public void testEmphasisZ19() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ19.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo **bar *baz* bim** bop*")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo *bar*/url*")});
    }

    /// <summary>
    /// Tests that there can be no empty emphasis or strong emphasis.
    /// </summary>
    @Test
    public void testEmphasisZ20() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ20.md");

        msAssert.areEqual(2, doc.getCount());
        checkParagraph(doc.get(0), "** is not an empty emphasis");
        checkParagraph(doc.get(1), "**** is not an empty strong emphasis");
    }

    /// <summary>
    /// Tests that any nonempty sequence of inline elements can be the contents of an strongly emphasized span.
    /// </summary>
    @Test
    public void testEmphasisZ21() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ21.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo bar/url**")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo"+SOFT_BREAK+"bar**")});
    }

    /// <summary>
    /// Tests that emphasis and strong emphasis can be nested inside strong emphasis.
    /// </summary>
    @Test
    public void testEmphasisZ22() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ22.md");

        msAssert.areEqual(8, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo *bar* baz**")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo **bar** baz**")});
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "****foo** bar**")});
        checkInlines(doc.get(3), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo **bar****")});
        checkInlines(doc.get(4), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo *bar* baz**")});
        checkInlines(doc.get(5), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo*bar*baz**")});
        checkInlines(doc.get(6), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "***foo* bar**")});
        checkInlines(doc.get(7), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo *bar***")});
    }

    /// <summary>
    /// Tests that when delimiters do not match evenly, the excess literal * characters will appear outside
    /// of the emphasis, rather than inside it.
    /// </summary>
    @Test
    public void testEmphasisZ23() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ23.md");

        msAssert.areEqual(6, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*"), new ExpectedInline(BlockType.ITALIC_INLINE, "*foo*")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo*"), new ExpectedInline(BlockType.INLINE, "*")});
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*"), new ExpectedInline(BlockType.BOLD_INLINE, "**foo**")});
        checkInlines(doc.get(3), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "***"), new ExpectedInline(BlockType.ITALIC_INLINE, "*foo*")});
        checkInlines(doc.get(4), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo**"), new ExpectedInline(BlockType.INLINE, "*")});
        checkInlines(doc.get(5), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo*"), new ExpectedInline(BlockType.INLINE, "***")});
    }

    /// <summary>
    /// Tests that if you want emphasis nested directly inside emphasis, you must use different delimiters.
    /// </summary>
    @Test
    public void testEmphasisZ24() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ24.md");

        msAssert.areEqual(4, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo**")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "**foo**")});
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "**foo**")});
        checkInlines(doc.get(3), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "**foo**")});
    }

    /// <summary>
    /// Tests that strong emphasis within strong emphasis is possible without switching delimiters.
    /// </summary>
    @Test
    public void testEmphasisZ25() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ25.md");

        msAssert.areEqual(3, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "****foo****")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "****foo****")});
        checkInlines(doc.get(2), new ExpectedInline[] {new ExpectedInline(BlockType.BOLD_INLINE, "******foo******")});
    }

    /// <summary>
    /// Tests that an interpretation <em><strong>...</strong></em> is always preferred to <strong><em>...</em></strong>.
    /// </summary>
    @Test
    public void testEmphasisZ26() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ26.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "***foo***")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*****foo*****")});
    }

    /// <summary>
    /// Tests that when two potential emphasis or strong emphasis spans overlap, so that the second begins before the
    /// first ends and ends after the first ends, the first takes precedence.
    /// </summary>
    @Test
    public void testEmphasisZ27() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ27.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo _bar*"), new ExpectedInline(BlockType.INLINE, " baz_")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*foo **bar *baz bim** bam*")});
    }

    /// <summary>
    /// Tests that when there are two potential emphasis or strong emphasis spans with the same closing delimiter,
    /// the shorter one (the one that opens later) takes precedence.
    /// </summary>
    @Test
    public void testEmphasisZ28() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ28.md");

        msAssert.areEqual(2, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "**foo "), new ExpectedInline(BlockType.BOLD_INLINE, "**bar baz**")});
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "*foo "), new ExpectedInline(BlockType.ITALIC_INLINE, "*bar baz*")});
    }

    /// <summary>
    /// Tests that inline code spans, links, images, and HTML tags group more tightly than emphasis.
    /// So, when there is a choice between an interpretation that contains one of these elements and
    /// one that does not, the former always wins.
    /// </summary>
    /// <remarks>
    /// BUT for a while these are emphases.
    /// </remarks>
    @Test
    public void testEmphasisZ29() throws Exception
    {
        MarkdownDocument doc = open("TestEmphasisZ29.md");

        msAssert.areEqual(9, doc.getCount());
        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "*"),
            new ExpectedInline(BlockType.LINK_TEXT, "bar*"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/url")});
        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "_foo "),
            new ExpectedInline(BlockType.LINK_TEXT, "bar_"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/url")});
        checkInlines(doc.get(2), new ExpectedInline[] {
            new ExpectedInline(BlockType.ITALIC_INLINE, "*<img src=\"foo\" title=\"*"),
            new ExpectedInline(BlockType.INLINE, "\"/>")});
        checkInlines(doc.get(3), new ExpectedInline[] {
            new ExpectedInline(BlockType.BOLD_INLINE, "**<a href=\"**"),
            new ExpectedInline(BlockType.INLINE, "\">")});
        checkInlines(doc.get(4), new ExpectedInline[] {
            new ExpectedInline(BlockType.BOLD_INLINE, "**<a href=\"**"),
            new ExpectedInline(BlockType.INLINE, "\">")});

        checkInlines(doc.get(5), new ExpectedInline[] { new ExpectedInline(BlockType.ITALIC_INLINE, "*a `*`*")});
        ItalicInlineBlock italicInline = (ItalicInlineBlock)((ParagraphBlock)doc.get(5)).get(0);
        checkInlines(italicInline, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "a "),
            new ExpectedInline(BlockType.INLINE_CODE, "`*`")});

        checkInlines(doc.get(6), new ExpectedInline[] {new ExpectedInline(BlockType.ITALIC_INLINE, "*a `_`*")});
        italicInline = (ItalicInlineBlock)((ParagraphBlock)doc.get(6)).get(0);
        checkInlines(italicInline, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "a "),
            new ExpectedInline(BlockType.INLINE_CODE, "`_`")});

        checkInlines(doc.get(7), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "**a"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar/?q=**>") });
        checkInlines(doc.get(8), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "__a"),
            new ExpectedInline(BlockType.AUTOLINK, "<http://foo.bar/?q=__>") });
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\Emphasis\\{0}", fileName));
    }

    /// <summary>
    /// Verifies delimiter run in a specified text.
    /// </summary>
    private static void checkDelimiterRun(String text, /*FlankingType*/int expectedFlankingType, String expectedOpening)
    {
        DelimiterRun delimiterRun = new DelimiterRun();
        delimiterRun.tryParse(text, 0);

        msAssert.areEqual(expectedFlankingType, delimiterRun.getDelimiters().get(0).getFlankingType());
        msAssert.areEqual(expectedOpening, delimiterRun.getOpening());
    }
}

