// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.Text.RegularExpressions.GroupCollection;
import com.aspose.FormatterPal;
import com.aspose.Language;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Implements conversions of ruby runs for save formats that do not support ruby tags.
/// </summary>
class RubyConverter
{
    RubyConverter(DocumentValidator validator, SaveInfo saveInfo)
    {
        mSaveInfo = saveInfo;
        mValidator = validator;
    }

    /// <summary>
    /// Converts ruby run into form that is appropriate for current save format.
    /// </summary>
    void convert(Run rubyRun) throws Exception
    {
        switch (mSaveInfo.getSaveFormat())
        {
            case SaveFormat.DOC:
            case SaveFormat.DOT:
            case SaveFormat.RTF:
                insertTempNodes(convertToFieldNodes(rubyRun), rubyRun);
                break;

            case SaveFormat.DOCX:
            case SaveFormat.DOTX:
            case SaveFormat.DOTM:
            case SaveFormat.DOCM:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
            case SaveFormat.WORD_ML:
                // Do nothing.
                break;

            case SaveFormat.TEXT:
                // Do nothing.
                break;

            case SaveFormat.HTML:
            case SaveFormat.MHTML:
                // Do nothing.
                break;
            default:
                // WORDSNET-26731 Ruby run with EQ superscript should be treated as EQField.
                Ruby ruby = (Ruby)rubyRun.getRunPr().get(FontAttr.RUBY);
                Node[] nodes = G_SUBSCRIPT_REGEX.isMatch(ruby.getBase().getText())
                    ? convertToFieldNodes(rubyRun)
                    : new Node[] { convertToOfficeMath(rubyRun) };

                insertTempNodes(nodes, rubyRun);
                break;
        }
    }

    void revert()
    {
        for (Node node : mTempNodes)
            node.remove();
    }

    /// <summary>
    /// Transforms ruby EQ field to Ruby object. Original field is removed.
    /// </summary>
    /// <dev>
    /// The details on switches and instructions:
    /// https://docs.microsoft.com/en-us/archive/blogs/murrays/word-eq-field-and-east-asian-formatting
    /// https://support.microsoft.com/en-us/office/field-codes-eq-equation-field-27300091-3780-4b88-836f-ae49ecde4692
    /// </dev>
    static void transformEquation(FieldBundle fieldBundle) throws Exception
    {
        Ruby ruby = convertToRuby(fieldBundle);
        if (ruby == null)
            return;

        // Make holder run for Ruby.
        DocumentBase doc = fieldBundle.getStart().getDocument();
        Run rubyRun = new Run(doc, "", AttrCollectionExtension.deepClone(fieldBundle.getStart().getRunPr()));
        rubyRun.getRunPr().setAttr(FontAttr.RUBY, ruby);

        fieldBundle.getStart().insertPrevious(rubyRun);

        NodeRemover.remove(fieldBundle.getStart(), true, fieldBundle.getEnd(), true, NodeJoinMode.DONT_JOIN);
    }

    private static Ruby convertToRuby(FieldBundle fieldBundle)
    {
        Field field = fieldBundle.getField();
        return convertToRuby(field);
    }

    static Ruby convertToRuby(Field field)
    {
        final int JC_GROUP = 1;
        final int HPS_GROUP = 3;
        final int DIST_GROUP = 5;
        final int R1_GROUP = 6;
        final int R2_GROUP = 7;

        String fieldCode = field.getFieldCode();

        MatchCollection matches = G_EQ_FIELD_REGEX.matches(fieldCode);

        // We need exactly one match.
        if (matches.getCount() != 1)
            return null;

        GroupCollection groups = matches.get(0).getGroups();

        // WORDSNET-25920 There should not be '\do' subscript switch in base of Ruby.
        if (G_SUBSCRIPT_REGEX.isMatch(groups.get(R2_GROUP).getValue()))
            return null;

        Ruby ruby = new Ruby();

        // Fill general properties.
        if (groups.get(JC_GROUP).getSuccess())
            ruby.Alignment = (/*RubyAlignment*/int)FormatterPal.parseInt(groups.get(JC_GROUP).getValue());

        if (groups.get(HPS_GROUP).getSuccess())
            ruby.TopSize = FormatterPal.parseInt(groups.get(HPS_GROUP).getValue());

        ruby.Distance = FormatterPal.parseInt(groups.get(DIST_GROUP).getValue()) * 2;

        ruby.BaseSize = (/*int*/Integer)InlineHelper.fetchAttr(field.getStart(), FontAttr.SIZE);
        ruby.Language = (/*Language*/Integer)InlineHelper.fetchAttr(field.getStart(), FontAttr.LOCALE_ID_FAR_EAST);

        NodeRange fieldCodeRange = field.getFieldCodeRange();

        // Make copy for top run in ruby.
        addRubyChunks(groups.get(R1_GROUP).getValue(), groups.get(R1_GROUP).getIndex(), fieldCodeRange, ruby.getTop());

        // Make copy for base run in ruby.
        addRubyChunks(groups.get(R2_GROUP).getValue(), groups.get(R2_GROUP).getIndex(), fieldCodeRange, ruby.getBase());

        return ruby;
    }

    /// <summary>
    /// Converts Ruby to EQ field nodes.
    /// </summary>
    static Node[] convertToFieldNodes(Run rubyRun)
    {
        Ruby ruby = (Ruby)rubyRun.getRunPr().get(FontAttr.RUBY);
        DocumentBase doc = rubyRun.getDocument();

        // 1 - field start
        // 2 - field code start
        //     top runs
        // 3 - field code middle
        //     base runs
        // 4 - field code end
        // 5 - field separator
        // 6 - field end
        int nodeIdx = 0;
        Node[] fieldNodes = new Node[6 + ruby.getTop().getCount() + ruby.getBase().getCount()];

        RunPr baseRunPr = new RunPr();
        baseRunPr.setAttr(FontAttr.SIZE, ruby.BaseSize);

        fieldNodes[nodeIdx++] = new FieldStart(doc, AttrCollectionExtension.deepClone(baseRunPr), FieldType.FIELD_EQUATION);

        String fieldCodeStart = msString.format("EQ \\* jc{0} \\* \"Font:{1}\" \\* hps{2} \\o\\ad(\\s\\up {3}(",
                                              (int)ruby.Alignment,
                                              rubyRun.getFont().getNameAscii(),
                                              ruby.TopSize,
                                              ruby.Distance / 2);
        fieldNodes[nodeIdx++] = new Run(doc, fieldCodeStart, AttrCollectionExtension.deepClone(baseRunPr));

        for (RubyChunk chunk : ruby.getTop())
            fieldNodes[nodeIdx++] = new Run(doc, chunk.Text, AttrCollectionExtension.deepClone(chunk.RunPr));

        fieldNodes[nodeIdx++] = new Run(doc, "),", AttrCollectionExtension.deepClone(baseRunPr));

        for (RubyChunk chunk : ruby.getBase())
            fieldNodes[nodeIdx++] = new Run(doc, chunk.Text, AttrCollectionExtension.deepClone(chunk.RunPr));

        fieldNodes[nodeIdx++] = new Run(doc, ")", AttrCollectionExtension.deepClone(baseRunPr));
        fieldNodes[nodeIdx++] = new FieldSeparator(doc, AttrCollectionExtension.deepClone(baseRunPr), FieldType.FIELD_EQUATION);
        fieldNodes[nodeIdx] = new FieldEnd(doc, AttrCollectionExtension.deepClone(baseRunPr), FieldType.FIELD_EQUATION, true);

        return fieldNodes;
    }

    private static void addRubyChunks(String text, int index, NodeRange range, RubyChunkCollection chunks)
    {
        int pos = 0;
        int remains = text.length();

        while (remains > 0)
        {
            Run run = getRunAt(range, index + pos);

            // Run position.
            int runStart = getRunStart(range, run);

            // Local position in run.
            int localPos = index + pos - runStart;

            RubyChunk chunk = new RubyChunk();

            int lenFromThisRun = Math.min(run.getText().length() - localPos, remains);
            chunk.Text = run.getText().substring((localPos), (localPos) + (lenFromThisRun));
            chunk.RunPr =  AttrCollectionExtension.deepClone(run.getRunPr());
            chunks.add(chunk);

            pos += lenFromThisRun;
            remains -= lenFromThisRun;
        }
    }

    /// <summary>
    /// Converts Ruby to OfficeMath object.
    /// </summary>
    private static OfficeMath convertToOfficeMath(Run rubyRun)
    {
        Ruby ruby = (Ruby)rubyRun.getRunPr().get(FontAttr.RUBY);
        DocumentBase doc = rubyRun.getDocument();

        OfficeMath mathPara = new OfficeMath(doc, new MathObjectOMathPara(), rubyRun.getRunPr());
        OfficeMath math = new OfficeMath(doc, new MathObjectOMath(), rubyRun.getRunPr());

        MathObjectMatrix objMatrix = new MathObjectMatrix();
        // WORDSNET-17442 The vertical justification of the matrix representing Ruby, should be "bottom".
        objMatrix.setBaseJustification(MathBaseJustification.BOTTOM);
        objMatrix.setColumnSpacingRule(MathSpacingRule.EXACTLY);
        objMatrix.setColumnGap(0);

        addColumnPr(objMatrix, ruby.Alignment);

        OfficeMath officeMathMatrix = new OfficeMath(doc, objMatrix, rubyRun.getRunPr());
        OfficeMath rowTop = new OfficeMath(doc, new MathObjectMatrixRow(), rubyRun.getRunPr());
        OfficeMath rowBase = new OfficeMath(doc, new MathObjectMatrixRow(), rubyRun.getRunPr());
        officeMathMatrix.appendChild(rowTop);
        officeMathMatrix.appendChild(rowBase);

        math.appendChild(officeMathMatrix);
        mathPara.appendChild(math);
        mathPara.isConvertedFromRuby(true);

        // Add the top text.
        addRowArgument(doc, ruby, rowTop, true);
        // Add the base text.
        addRowArgument(doc, ruby, rowBase, false);

        return mathPara;
    }

    /// <summary>
    /// Adds a new columnPr in the matrix ColumnPrCollection
    /// </summary>
    /// <param name="objMatrix">The specified <see cref="MathObjectMatrix"/>></param>
    /// <param name="alignment">The specified <see cref="RubyAlignment"/>></param>
    private static void addColumnPr(MathObjectMatrix objMatrix, /*RubyAlignment*/int alignment)
    {
        MathMatrixColumnPr columnPr = new MathMatrixColumnPr();
        columnPr.setHorizontalAlignment(getHorizontalAlignment(alignment));
        objMatrix.getColumnPrCollection().add(columnPr);
    }

    /// <summary>
    /// Returns a column alignment, using <see cref="RubyAlignment"/>
    /// </summary>
    /// <returns><see cref="HorizontalAlignment"/></returns>
    private static /*HorizontalAlignment*/int getHorizontalAlignment(/*RubyAlignment*/int alignment)
    {
        switch (alignment)
        {
            case RubyAlignment.LEFT:
                 return HorizontalAlignment.LEFT;
            case RubyAlignment.RIGHT:
            case RubyAlignment.RIGHT_VERTICAL:
                return HorizontalAlignment.RIGHT;
            default:
                return HorizontalAlignment.CENTER;
        }
    }

    /// <summary>
    /// Adds argument to the specified <see cref="OfficeMath"/>
    /// </summary>
    /// <param name="doc">The specified <see cref="DocumentBase"/></param>
    /// <param name="ruby">The specified <see cref="Ruby"/></param>
    /// <param name="row">The specified <see cref="OfficeMath"/></param>
    /// <param name="top">If "true" ruby.Top is used, otherwise ruby.Base is used</param>
    private static void addRowArgument(DocumentBase doc, Ruby ruby, OfficeMath row, boolean top)
    {
        RunPr runPr;
        String text;

        // WORDSNET-21509 ruby.Top ('w:rt' tag - Phonetic Guide Text) can be empty, just exit.
        // WORDSNET-25876 rubyBase can be empty as well
        if ((top && (ruby.getTop().getCount() == 0)) || (!top && (ruby.getBase().getCount() == 0)))
            return;

        if (top)
        {
            runPr = AttrCollectionExtension.deepClone(ruby.getTop().get(0).RunPr);
            runPr.setSize(ruby.TopSize);
            text = ruby.getTop().getText();
        }
        else
        {
            runPr = AttrCollectionExtension.deepClone(ruby.getBase().get(0).RunPr);
            runPr.setSize(ruby.BaseSize);
            text = ruby.getBase().getText();
        }

        if (ruby.Alignment == RubyAlignment.DISTRIBUTE_LETTER || ruby.Alignment == RubyAlignment.DISTRIBUTE_SPACE)
            addTextToRowIfAlignmentIsDistribute(doc, ruby, row, top, text, runPr);
        else
            addTextToRow(text, doc, runPr, row);
    }

    /// <summary>
    /// Add text if RubyAlignment is "DistributeLetter" or "DistributeSpace".
    /// </summary>
    private static void addTextToRowIfAlignmentIsDistribute(DocumentBase doc, Ruby ruby, OfficeMath row, boolean top,
        String text, RunPr runPr)
    {
        if (ruby.getTop().getText().length() == ruby.getBase().getText().length())
        {
            // Add each letter in a separate column.
            for (int i = 0; i < text.length(); i++)
            {
                // Add columnPr for the additional column.
                if (i > 0 && top)
                {
                    OfficeMath officeMatrix = (OfficeMath)row.getParentNode();
                    addColumnPr(((MathObjectMatrix)officeMatrix.getMathObject()), ruby.Alignment);
                }

                addTextToRow(text.substring((i), (i) + (1)), doc, runPr, row);
            }
        }
        else
        {
            runPr.setSpacing(getSpacing(ruby, top));
            addTextToRow(text, doc, runPr, row);
        }
    }

    /// <summary>
    /// Calculates a spacing between letters.
    /// </summary>
    /// <remarks>
    /// This experimental method is trying to mimic the MS Office behavior.
    /// </remarks>
    /// <param name="ruby">The specified <see cref="Ruby"/></param>
    /// <param name="top">If "true" calculates spacing for the top, otherwise for the base</param>
    /// <returns>The spacing value</returns>
    private static int getSpacing(Ruby ruby, boolean top)
    {
        double widthBase = ruby.getBase().getText().length() * ruby.BaseSize;
        double widthTop = ruby.getTop().getText().length() * ruby.TopSize;
        double delta = widthBase - widthTop;
        int range = (ruby.Alignment == RubyAlignment.DISTRIBUTE_LETTER) ? 5 : 4;
        int length = top ? ruby.getTop().getText().length() : ruby.getBase().getText().length();
        int spacing = ((int)Math.abs(delta) / length) * range;

        if (((delta > 0) && top) || ((delta < 0) && !top))
            return spacing;

        return 0;
    }

    /// <summary>
    /// Adds the specified text to the specified <see cref="OfficeMath"/>.
    /// </summary>
    /// <param name="text">The specified text</param>
    /// <param name="doc">The specified <see cref="DocumentBase"/></param>
    /// <param name="runPr">The specified <see cref="RunPr"/></param>
    /// <param name="row">The specified <see cref="OfficeMath"/></param>
    private static void addTextToRow(String text, DocumentBase doc, RunPr runPr, OfficeMath row)
    {
        Run argRun = new Run(doc, text, runPr);
        OfficeMath arg = new OfficeMath(doc, new MathObjectArgumentBase(MathObjectType.ARGUMENT));
        row.appendChild(arg);
        arg.appendChild(argRun);
    }

    /// <summary>
    /// Get run from NodeRange at given text position.
    /// </summary>
    private static Run getRunAt(NodeRange range, int index)
    {
        int pos = 0;
        for (Node node : range)
        {
            if ((pos + node.getTextLength() > index) && node.getNodeType() == NodeType.RUN)
                return (Run)node;

            pos += node.getTextLength();
        }

        return ms.as(range.getEnd().getNode(), Run.class);
    }

    private static int getRunStart(NodeRange range, Run run)
    {
        int pos = 0;
        for (Node node : range)
        {
            if (node == run)
                return pos;

            pos += node.getTextLength();
        }

        return 0;
    }

    /// <summary>
    /// Inserts temporary nodes after given reference node.
    /// </summary>
    private void insertTempNodes(Node[] nodes, Node refNode) throws Exception
    {
        for (Node node : nodes)
        {
            insertTempNode(node, refNode);
            refNode = node;
        }
    }

    /// <summary>
    /// Inserts temporary node after given reference node.
    /// </summary>
    private void insertTempNode(Node node, Node refNode) throws Exception
    {
        refNode.insertNext(node);
        msArrayList.add(mTempNodes, node);
        node.accept(mValidator);
    }

    private /*final*/ SaveInfo mSaveInfo;
    private /*final*/ DocumentValidator mValidator;

    /// <summary>
    /// List of nodes which are inserted temporary and should be removed after document saving.
    /// </summary>
    private /*final*/ ArrayList<Node> mTempNodes = new ArrayList<Node>();

    // WORDSNET-23839 The switches in EQ field ("jc", "Font" and "hps") are optional. The regex is adjusted accordingly.
    private static /*final*/ Regex G_EQ_FIELD_REGEX = new Regex(
        "EQ\\s*" +
        "(?:\\\\\\*\\s*jc([0-5]{1}))?\\s*" + // Optional "\* jc" switch.
        "(?:\\\\\\*\\s*\\x22Font\\:(.*)\\x22)?\\s*" + // Optional "\* Font" switch.
        "(?:\\\\\\*\\s*hps([0-9]+))?\\s*" + // Optional "\* hps" switch.
        "\\\\o\\s*(\\\\a[ldr]{1}\\s*)?\\(\\\\s\\s*\\\\up\\s*([0-9]+)\\s*\\((.*)\\)\\s*,\\s*(.*)\\)", // Instructions.
        RegexOptions.COMPILED);

    /// <summary>
    /// Regex matches to subscript switch of EQ field (\s\do).
    /// See ISO-IEC-29500 p4, 14.9.4.6 EQ for details.
    /// </summary>
    private static /*final*/ Regex G_SUBSCRIPT_REGEX = new Regex("\\\\s\\\\do\\s*(\\d+)");
}

