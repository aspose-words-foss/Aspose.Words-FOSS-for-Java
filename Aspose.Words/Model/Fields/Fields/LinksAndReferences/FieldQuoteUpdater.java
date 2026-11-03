// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.DictionaryExtension;
import java.util.HashMap;


class FieldQuoteUpdater
{
    FieldQuoteUpdater(Field field)
    {
        Debug.assertTrue(field.getType() == FieldType.FIELD_QUOTE || field.getType() == FieldType.FIELD_SHAPE);
        mField = field;
    }

    FieldUpdateAction update() throws Exception
    {
        collectResult();

        if (mErrorText != null)
            return new FieldUpdateActionInsertErrorMessage(mField, mErrorText);

        if (mResult != null)
            return new FieldUpdateActionApplyResult(mField, new NodeRangeFieldResult(mResult));

        return new FieldUpdateActionApplyResult(mField, "");
    }

    /// <summary>
    /// Concatenates parts provided by the field arguments.
    /// </summary>
    /// <remarks>
    /// There is an undocumented feature for the QUOTE field. Its field code can include a variable count of arguments.
    /// A result is then achieved through the concatenation of argument without any delimiter.
    /// </remarks>
    private void collectResult() throws Exception
    {
        mErrorText = null;
        mResult = null;

        mField.getFieldCodeCache().isolateElements();

        // Use global variable for C++ porting. If use local variable parent node of result is garbaged and NullReferenceException is thrown in C++.
        mBuilder = new ResultBuilder(mField.getDocument(), mField.getStart().getParentParagraph());

        // WORDSNET-19717 It seems like the QUOTE field still applies CHARFORMAT to encoded chars, but not to text parts.
        CharFormatProvider formatProvider = new CharFormatProvider(mField);
        Inline formatSource = formatProvider.getSourceNode();

        for (FieldArgument argument : mField.getFieldCodeCache().getArguments())
        {
            // If the argument text is enclosed in double quotes or the argument represents a single field's result range
            // pass the text part without any changes.
            if (argument.isInDoubleQuotes() || argument.isSingleFieldResult())
            {
                appendArgumentRange(mBuilder, argument);
                continue;
            }

            CharHolder ch = new CharHolder();
            /*ExtractResult*/int result = tryExtractChar(argument, ch);
            switch (result)
            {
                case ExtractResult.EXTRACTED:
                {
                    // Append extracted char.
                    Run run = new Run(mField.getDocument(), Character.toString(ch.getValue()), formatSource.getRunPr());
                    mBuilder.append(run);
                    break;
                }
                case ExtractResult.SKIPPED:
                {
                    // The text part does not represent a char code.
                    appendArgument(mBuilder, argument);
                    break;
                }
                default:
                {
                    mErrorText = getErrorText(result);
                    return;
                }
            }
        }

        if (mBuilder.isEmpty())
            return;

        mResult = mBuilder.getResultRange();
    }

    private void appendArgument(ResultBuilder builder, FieldArgument argument) throws Exception
    {
        Table table = findTable(argument.getRange());
        if (table != null)
        {
            // Skip leading unquoted tables.
            if (builder.isEmpty())
                return;

            appendTableContent(builder, table);
            return;
        }

        appendArgumentRange(builder, argument);
    }

    private static Table findTable(NodeRange range)
    {
        for (Node node : range)
        {
            if (node.getNodeType() == NodeType.TABLE)
                return (Table)node;
        }

        return null;
    }

    private void appendTableContent(ResultBuilder builder, Table table) throws Exception
    {
        Paragraph paragraph = new Paragraph(mField.getDocument());
        for (Row row : (Iterable<Row>) table.getRows())
        {
            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                for (Node node : new NodeRange(cell, cell))
                {
                    if (node instanceof IInline)
                        paragraph.appendChild(node.deepClone(false));
                }
            }
        }

        if (!paragraph.hasChildNodes())
            return;

        builder.append(new NodeRange(paragraph.getFirstChild(), paragraph.getLastChild()));
    }

    private static void appendArgumentRange(ResultBuilder builder, FieldArgument argument) throws Exception
    {
        builder.append(argument.getRange());

        if (!argument.isInDoubleQuotes())
            return;

        Run first = ms.as(builder.getResultBySource(argument.getRange().getStart().getNode()), Run.class);
        Run last = ms.as(builder.getResultBySource(argument.getRange().getEnd().getNode()), Run.class);

        trimDoubleQuote(first, true);
        trimDoubleQuote(last, false);
    }

    private static void trimDoubleQuote(Run run, boolean start)
    {
        if (run == null || msString.isNullOrEmpty(run.getText()))
            return;

        int index = start ? 0 : run.getText().length() - 1;
        if (!FieldCodeParser.isDoubleQuote(run.getText().charAt(index)))
            return;

        run.setText(msString.remove(run.getText(), index, 1));
    }

    private static /*ExtractResult*/int tryExtractChar(FieldArgument argument, CharHolder ch)
    {
        String argumentText = argument.getNormalizedText();

        // Try to parse a hexadecimal char code.
        /*ExtractResult*/int result = collectCharByHexadecimalCode(argumentText, ch);
        if (result != ExtractResult.SKIPPED)
            return result;

        // Try to parse a decimal char code.
        return collectCharByDecimalCode(argumentText, ch);
    }

    /// <summary>
    /// Checks whether the specified text represents a valid hexadecimal char code and appends
    /// the corresponding character to the specified string builder if successful.
    /// </summary>
    /// <remarks>
    /// See http://msdn.microsoft.com/en-us/library/ff534940(v=office.12).aspx for any details.
    /// </remarks>
    private static /*ExtractResult*/int collectCharByHexadecimalCode(String text, CharHolder ch)
    {
        if (!StringUtil.startsWithOrdinalIgnoreCase(text, HEX_PREFIX))
            return ExtractResult.SKIPPED;

        int hexPrefixLength = HEX_PREFIX.length();
        if (text.length() == hexPrefixLength)
            return ExtractResult.ERROR_DIGIT_EXPECTED;

        final int MAX_CHAR_CODE_LENGTH = 4;
        int charCodeLength = Math.min(text.length() - hexPrefixLength, MAX_CHAR_CODE_LENGTH);
        int charCode = FormatterPal.tryParseHex(text.substring((hexPrefixLength), (hexPrefixLength) + (charCodeLength)));
        return collectCharByCode(charCode, ExtractResult.ERROR_INVALID_HEX_DIGIT, ch);
    }

    /// <summary>
    /// Checks whether the specified text represents a valid decimal char code and appends
    /// the corresponding character to the specified string builder if successful.
    /// </summary>
    /// <remarks>
    /// See http://msdn.microsoft.com/en-us/library/ff534940(v=office.12).aspx for any details.
    /// </remarks>
    private static /*ExtractResult*/int collectCharByDecimalCode(String text, CharHolder ch)
    {
        if (!StringUtil.hasChars(text) || !StringUtil.isDigit(text.charAt(0)))
            return ExtractResult.SKIPPED;

        final int MAX_CHAR_CODE_LENGTH = 3;
        int charCodeLength = Math.min(text.length(), MAX_CHAR_CODE_LENGTH);
        String charCodeText = (text.length() > charCodeLength) ? text.substring((0), (0) + (charCodeLength)) : text;
        int charCode = FormatterPal.tryParseInt(charCodeText);
        return collectCharByCode(charCode, ExtractResult.ERROR_DIGIT_EXPECTED, ch);
    }

    /// <summary>
    /// Checks whether the specified char code is valid and appends
    /// the corresponding character to the specified string builder if successful.
    /// </summary>
    /// <remarks>
    /// See http://msdn.microsoft.com/en-us/library/ff534940(v=office.12).aspx for any details.
    /// </remarks>
    private static /*ExtractResult*/int collectCharByCode(int charCode, /*ExtractResult*/int parseErrorResult, CharHolder ch)
    {
        if (charCode == Integer.MIN_VALUE)
            return parseErrorResult;

        ch.setValue((char)charCode);
        if (ch.getValue() == ControlChar.PARAGRAPH_BREAK_CHAR)
            return ExtractResult.ERROR_PARAGRAPH_BREAK_CHAR;

        return ExtractResult.EXTRACTED;
    }

    /// <summary>
    /// Returns an error message for an erroneous <see cref="ExtractResult"/> value.
    /// </summary>
    /// <param name="errorResult"></param>
    /// <returns></returns>
    private static String getErrorText(/*ExtractResult*/int errorResult)
    {
        switch (errorResult)
        {
            case ExtractResult.ERROR_DIGIT_EXPECTED:
                return "Error! Digit expected.";
            case ExtractResult.ERROR_INVALID_HEX_DIGIT:
                return "Error! Not a valid Hex digit.";
            case ExtractResult.ERROR_PARAGRAPH_BREAK_CHAR:
                return "Error! Cannot insert return character.";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "errorResult");
        }
    }

    private /*final*/ Field mField;

    private NodeRange mResult;
    private ResultBuilder mBuilder;
    private String mErrorText;

    /// <summary>
    /// Gets the common hexadecimal string prefix: "0x".
    /// </summary>
    private static final String HEX_PREFIX = "0x";

    private /*enum*/ final class ExtractResult
    {
        private ExtractResult(){}
        
        public static final int EXTRACTED = 0;
        public static final int SKIPPED = 1;
        public static final int ERROR_DIGIT_EXPECTED = 2;
        public static final int ERROR_INVALID_HEX_DIGIT = 3;
        public static final int ERROR_PARAGRAPH_BREAK_CHAR = 4;

        public static final int length = 5;
    }

    private static class CharHolder
    {
        CharHolder()
        {
            setValue('\u0000');
        }

        char getValue() { return mValue; };
        void setValue(char value) { mValue = value; };

        private char mValue;
    }

    private static class ResultBuilder implements INodeCloningListener
    {
        public void /*INodeCloningListener.*/notifyNodeCloned(Node source, Node clone)
        {
            mSourceToResultDictionary.put(source, clone);
            mResultToSourceDictionary.put(clone, source);
        }

        Node getResultBySource(Node source)
        {
            return DictionaryExtension.getValueOrNull(mSourceToResultDictionary, source);
        }

        boolean isEmpty() { return mIsEmpty; }; private void isEmpty(boolean value) { mIsEmpty = value; };

        private boolean mIsEmpty;

        ResultBuilder(DocumentBase document, Paragraph startParentParagraph)
        {
            isEmpty(true);
            Run run = new Run(document);
            Paragraph paragraph = (Paragraph)startParentParagraph.deepClone(false);
            Body body = new Body(document);
            Section section = new Section(document);

            mDocument = document;
            mRoot = new ShadowDocument(document);
            mRoot.appendChild(section);
            section.appendChild(body);
            body.appendChild(paragraph);
            paragraph.appendChild(run);
            mRefNode = run;
        }

        void append(Node run) throws Exception
        {
            Paragraph paragraph = new Paragraph(mDocument);
            paragraph.appendChild(run);
            append(new NodeRange(run, run));
        }

        void append(NodeRange range) throws Exception
        {
            isEmpty(false);

            if (copyRangeWithinOfficeMath(range))
                return;

            copyRange(range, mRefNode);
        }

        private boolean copyRangeWithinOfficeMath(NodeRange range) throws Exception
        {
            OfficeMath officeMath = getTopLevelOfficeMathAncestor(range);
            if (officeMath == null)
                return false;

            CompositeNode startParent = range.getStart().getNode().getParentNode();
            CompositeNode endParent = range.getEnd().getNode().getParentNode();
            if (!NodeUtil.isAncestorOrSelf(endParent, startParent))
                return false;

            Node dummyRefNode = cloneAncestorOfficeMath(range.getStart().getNode(), officeMath);
            copyRange(range, dummyRefNode);
            dummyRefNode.remove();

            return true;
        }

        private static OfficeMath getTopLevelOfficeMathAncestor(NodeRange range)
        {
            OfficeMath startAncestor = getTopLevelOfficeMathAncestor(range.getStart().getNode());
            OfficeMath endAncestor = getTopLevelOfficeMathAncestor(range.getEnd().getNode());
            return startAncestor == endAncestor
                ? startAncestor
                : null;
        }

        private static OfficeMath getTopLevelOfficeMathAncestor(Node node)
        {
            CompositeNode officeMath = node.getAncestor(NodeType.OFFICE_MATH);
            if (officeMath == null)
                return null;

            return ((OfficeMath)officeMath).getTopLevelOfficeMath();
        }

        private Node cloneAncestorOfficeMath(Node node, OfficeMath officeMath)
        {
            Node dummyRefNode = null;

            CompositeNode top = null;
            CompositeNode parent = node.getParentNode();

            while (true)
            {
                CompositeNode clone = (CompositeNode)parent.deepClone(false, this);

                if (dummyRefNode == null)
                    dummyRefNode = clone.appendChild(node.deepClone(false));

                if (top != null)
                    clone.appendChild(top);

                top = clone;

                if (parent == officeMath)
                    break;

                parent = parent.getParentNode();
            }

            mRefNode.insertPrevious(top);

            return dummyRefNode;
        }

        private void copyRange(NodeRange range, Node refNode) throws Exception
        {
            final /*NodeCopierOptions*/int OPTIONS = NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS | NodeCopierOptions.CLONE_NODE;
            NodeCopier.copy(range, refNode, null, this, OPTIONS);
        }

        NodeRange getResultRange()
        {
            finalizeBuilding();

            return FieldUtil.buildFieldResultNodeRange(mRoot, mRoot);
        }

        private void finalizeBuilding()
        {
            for (Node node : NodeFinder.findNodes(new NodeRange(mRoot, mRoot), NodeType.OFFICE_MATH))
            {
                OfficeMath current = (OfficeMath)node;

                OfficeMath previous = ms.as(current.getPreviousSibling(), OfficeMath.class);
                if (previous == null)
                    continue;

                Node currentSource = mResultToSourceDictionary.get(current);
                Node previousSource = mResultToSourceDictionary.get(previous);
                if (currentSource != previousSource && !current.isTopLevel())
                    continue;

                if (moveChildNodes(current, previous))
                    current.remove();
            }
        }

        private static boolean moveChildNodes(CompositeNode source, CompositeNode target)
        {
            Node[] children = source.getChildNodes(NodeType.ANY, false).toArray();
            for (Node child : children)
            {
                if (!target.canInsert(child))
                    return false;

                target.appendChild(child);
            }

            return true;
        }

        private /*final*/ HashMap<Node, Node> mSourceToResultDictionary = new HashMap<Node, Node>();
        private /*final*/ HashMap<Node, Node> mResultToSourceDictionary = new HashMap<Node, Node>();

        private /*final*/ DocumentBase mDocument;
        private /*final*/ ShadowDocument mRoot;
        private /*final*/ Run mRefNode;
    }

    /// <summary>
    /// Shadow document node that holds multi-section QUOTE field result.
    /// </summary>
    private static class ShadowDocument extends CompositeNode
    {
        ShadowDocument(DocumentBase document)
        {
            setDocument(document);
        }

        public /*override*/ /*NodeType*/int getNodeType() { return NodeType.DOCUMENT; }

        public /*override*/ boolean accept(DocumentVisitor visitor)
        {
            return true;
        }

        public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor)
        {
            return VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor)
        {
            return VisitorAction.CONTINUE;
        }
    }
}

