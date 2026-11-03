// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/02/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;


class OfficeMathUtil
{
    /// <summary>
    /// ctor.
    /// </summary>
    private OfficeMathUtil(OfficeMath srcOMath)
    {
        mSrcOMath = srcOMath;
    }

    /// <summary>
    ///  Changes display format type to <see cref="OfficeMathDisplayType.Inline"/>.
    /// </summary>
    static void changeToInline(OfficeMath srcOMath)
    {
        OfficeMathUtil officeMathUtil = new OfficeMathUtil(srcOMath);
        officeMathUtil.changeToInlineCore();
    }

    /// <summary>
    /// Changes display format type to <see cref="OfficeMathDisplayType.Display"/>.
    /// </summary>
    static void changeToDisplay(OfficeMath srcOMath)
    {
        OfficeMathUtil officeMathUtil = new OfficeMathUtil(srcOMath);
        officeMathUtil.changeToDisplayCore();
    }

    /// <summary>
    /// Converts specified shape to office math object if needed.
    /// </summary>
    /// <remarks>
    /// The method can to be called from the document validator or postloader.
    /// </remarks>
    static void convertShapeToOfficeMath(Shape shape, IWarningCallback warningCallback)
    {
        byte[] equationXMLVal = retrieveEquationXML(shape);
        if (!shape.isTopLevel() || (equationXMLVal == null))
            return;

        // Inform customer that shape was not converted to OfficeMath object.
        WarningUtil.warn(warningCallback, WarningType.DATA_LOSS,
            WarningSource.VALIDATOR, WarningStrings.INVALID_OFFICE_MATH_XML);
    }

    /// <summary>
    /// Retrieves "EquationXML" attribute and tries to cast value to "byte[]" type;
    /// </summary>
    static byte[] retrieveEquationXML(Shape shape)
    {
        Debug.assertTrue(shape != null);

        // Note: for RTF format "EquationXML" attribute uses as flag (value type is string), not as XML container.
        // Additionally check "TestSaveWarningsForShapePr".
        return ms.as(shape.getShapePr().get(ShapeAttr.EQUATION_XML), byte[].class);
    }

    /// <summary>
    /// Check if this object must be rendered in the inline mode.
    /// </summary>
    static boolean isInline(OfficeMath officeMath)
    {
        OfficeMath topLevelMath = officeMath.getTopLevelOfficeMath();
        /*MathObjectType*/int type = topLevelMath.getMathObjectType();
        switch (type)
        {
            // WORDSNET-16567 If a parent paragraph has a run with text inside,
            // then MathPara objects must be displayed in inline mode.
            case MathObjectType.O_MATH_PARA:
            // WORDSNET-19216 OMath object may be "not inline".
            case MathObjectType.O_MATH:
                return isOfficeMathInLine(topLevelMath);
            default:
                return false;
        }
    }

    private void changeToInlineCore()
    {
        // Display format type has effect for top level Office Math only.
        if (mSrcOMath.isTopLevel())
        {
            // Parent MathObjectOMathPara have to be removed and all child nodes preserved.
            if (mSrcOMath.getMathObject().getMathObjectType() == MathObjectType.O_MATH_PARA)
            {
                OfficeMath firstChild = (OfficeMath)mSrcOMath.getFirstChild();
                mSrcOMath.setMathObject(firstChild.getMathObject());

                // Copy child nodes to current Office Math.
                while (firstChild.hasChildNodes())
                    mSrcOMath.appendChild(firstChild.getFirstChild());

                firstChild.remove();

                processInlineOMathSiblings();
            }
        }
    }

    private void changeToDisplayCore()
    {
        // Display format type has effect for top level Office Math only.
        if (mSrcOMath.isTopLevel())
        {
            MathObject mathObject = mSrcOMath.getMathObject();

            // Current OMath have to be wrapped into MathObjectOMathPara.
            if (mathObject.getMathObjectType() != MathObjectType.O_MATH_PARA)
            {
                // Create new office math paragraph.
                MathObjectOMathPara oMathPara = new MathObjectOMathPara();

                // Create OfficeMath using current MathObject.
                OfficeMath dstOMath = new OfficeMath(mSrcOMath.getDocument(), mathObject);

                // Set OfficeMath paragraph to current MathObject.
                mSrcOMath.setMathObject(oMathPara);

                // Copy child nodes.
                while (mSrcOMath.hasChildNodes())
                    dstOMath.appendChild(mSrcOMath.getFirstChild());

                // Append office math to the office math paragraph.
                mSrcOMath.appendChild(dstOMath);

                processDisplayOMathSiblings(dstOMath, true);
                processDisplayOMathSiblings(dstOMath, false);
            }
        }
    }

    /// <summary>
    /// Process nodes before and after office math node with <see cref="OfficeMathDisplayType.Inline"/>.
    /// </summary>
    private void processInlineOMathSiblings()
    {
        Run nextRun = getNextRun();
        Run previousRun = getPreviousRun();

        // If last child of the office math is run which ends with line break,
        // its line break has to be removed.
        if (mSrcOMath.getLastChild().getNodeType() == NodeType.RUN)
        {
            Run lastRun = (Run)mSrcOMath.getLastChild();

            if (endsWithLineBreak(lastRun))
                trimLastChar(lastRun); // Remove line break.

            // MSW considers Office Math as inline when parent paragraph contains at least one run.
            // Create and add new empty run if required.
            Run newRun = new Run(mSrcOMath.getDocument(), ControlChar.SPACE);
            newRun.setRunPr(AttrCollectionExtension.deepClone(lastRun.getRunPr()));

            if ((nextRun == null) || !startsWithSpace(nextRun))
                mSrcOMath.insertNext(newRun);
        }

        if ((previousRun != null) && endsWithLineBreak(previousRun))
        {
            trimLastChar(previousRun); // Remove line break.

            if ((previousRun.getText().length() == 0) && (previousRun.getPreviousSibling().getNodeType() == NodeType.RUN))
            {
                Run prePreviousRun = (Run)previousRun.getPreviousSibling();

                // White space has to be added to the end of the last run
                // if previous run hasn't a white space at the end.
                if (!endsWithSpace(prePreviousRun))
                    previousRun.setText(previousRun.getText() + ControlChar.SPACE); // Add white space.
            }
            else
            {
                previousRun.setText(previousRun.getText() + ControlChar.SPACE); // Add white space.
            }
        }
    }

    /// <summary>
    /// Process nodes before and after office math node with <see cref="OfficeMathDisplayType.Display"/>.
    /// </summary>
    /// <param name="dstOMath">Destination display Office math node.</param>
    /// <param name="isNextSibling">True - when it is next sibling node.</param>
    private void processDisplayOMathSiblings(OfficeMath dstOMath, boolean isNextSibling)
    {
        Node siblingNode = isNextSibling ? mSrcOMath.getNextSibling() : mSrcOMath.getPreviousSibling();

        if (siblingNode != null)
        {
            /*NodeType*/int siblingNodeType = siblingNode.getNodeType();

            // MSW inserts line break only before nodes of these types.
            if ((siblingNodeType == NodeType.RUN) ||
                (siblingNodeType == NodeType.SHAPE) ||
                (siblingNodeType == NodeType.GROUP_SHAPE))
            {
                Run lineBreakRun = new Run(mSrcOMath.getDocument(), ControlChar.LINE_BREAK);

                if (siblingNodeType == NodeType.RUN)
                {
                    Run run = (Run)siblingNode;

                    lineBreakRun = (Run)run.deepClone(false);
                    lineBreakRun.setText(ControlChar.LINE_BREAK);

                    // Cut the first/last char (SpaceChar).
                    if ((startsWithSpace(run) && isNextSibling))
                        trimFirstChar(run);

                    if (endsWithSpace(run) && !isNextSibling)
                        trimLastChar(run);
                }

                insertLineBreakRun(dstOMath, lineBreakRun, isNextSibling);
            }
        }
    }

    /// <summary>
    /// Inserts lineBreak run to the and of the office math or before office math node.
    /// </summary>
    private void insertLineBreakRun(OfficeMath dstOMath, Run lineBreakRun, boolean isNextSibling)
    {
        if (isNextSibling)
            dstOMath.appendChild(lineBreakRun);
        else
            mSrcOMath.insertPrevious(lineBreakRun);
    }

    private Run getNextRun()
    {
        return ms.as(mSrcOMath.getNextSibling(), Run.class);
    }

    private Run getPreviousRun()
    {
        return ms.as(mSrcOMath.getPreviousSibling(), Run.class);
    }

    private static boolean endsWithSpace(Run run)
    {
        return (run != null) && (run.getText().length() > 0) && (run.getText().charAt(run.getText().length() - 1) == ControlChar.SPACE_CHAR);
    }

    private static boolean startsWithSpace(Run run)
    {
        return (run != null) && (run.getText().length() > 0) && (run.getText().charAt(0) == ControlChar.SPACE_CHAR);
    }

    private static boolean endsWithLineBreak(Run run)
    {
        return (run != null) && (run.getText().length() > 0) && (run.getText().charAt(run.getText().length() - 1) == ControlChar.LINE_BREAK_CHAR);
    }

    private static boolean startsWithLineBreak(Run run)
    {
        return (run != null) && (run.getText().length() > 0) && (run.getText().charAt(0) == ControlChar.LINE_BREAK_CHAR);
    }

    private static void trimLastChar(Run run)
    {
        if (run != null && run.getText().length() > 0)
            run.setText(run.getText().substring((0), (0) + (run.getText().length() - 1)));
    }

    private static void trimFirstChar(Run run)
    {
        if (run != null && run.getText().length() > 0)
            run.setText(run.getText().substring((1), (1) + (run.getText().length() - 1)));
    }

    /// <summary>
    /// Checks if <see cref="OfficeMath"/> must be rendered in the inline mode.
    /// </summary>
    /// <param name="topLevelMath">specified <see cref="OfficeMath"/></param>
    /// <returns>"True" if <see cref="OfficeMath"/> must be rendered in the inline mode, otherwise - "false"</returns>
    private static boolean isOfficeMathInLine(OfficeMath topLevelMath)
    {
        if (topLevelMath.isConvertedFromRuby() || (topLevelMath.getParentParagraph() == null))
            return false;

        // WORDSNET-26665 Formula in structured document tag should be rendered as "Inline".
        Paragraph para = (Paragraph)topLevelMath.getAncestor(NodeType.PARAGRAPH);

        if ((para != null) && (para.getParentNode() != null) && (para.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
            return true;

        // All elements before of after the line break char should be ignored.
        int startCheck = getIndexForOfficeMathCheck(topLevelMath, true);
        int endCheck = getIndexForOfficeMathCheck(topLevelMath, false);

        for (int i = startCheck; i < endCheck; i++)
        {
            Node currentNode = topLevelMath.getParentParagraph().getChildNodes(NodeType.ANY, false).get(i);

            // If paragraph contains Shape, OfficeMath object is "inline".
            if (NodeUtil.isDrawingObject(currentNode))
                return true;

            Run run = ms.as(currentNode, Run.class);

            // If paragraph contains Run, OfficeMath object is "inline".
            if ((run != null) && StringUtil.hasChars(run.getText()))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns the start or end index for iterating over the paragraph children,
    /// taking into account the line break char.
    /// </summary>
    /// <remarks>
    /// The 4-steps check:
    /// 1. Checks, if the first run of the see cref="OfficeMath" starts with the line break char.
    /// 2. Checks, if the last run of the see cref="OfficeMath" ends with the line break char.
    /// 3. Checks, if the previous run ends with the line break char.
    /// 4. Checks, if the next run starts with the line break char.
    /// </remarks>
    /// <param name="officeMath">Specified <see cref="OfficeMath"/></param>
    /// <param name="isStartIndex">Determines which index (start or end) should be returned</param>
    /// <returns>Start or end index of the paragraph child</returns>
    private static int getIndexForOfficeMathCheck(OfficeMath officeMath, boolean isStartIndex)
    {
        int indexMath = officeMath.getParentParagraph().getChildNodes(NodeType.ANY, false).indexOf(officeMath);
        int index = isStartIndex ? 0 : officeMath.getParentParagraph().getChildNodes(NodeType.ANY, false).getCount();
        int checkedIndex = isStartIndex ? indexMath + 1 : indexMath;

        // Check, if the first (or last) child of the office math starts (or ends) with the line break char.
        if (doesNodeStartOrEndWithLineBreak(officeMath, isStartIndex))
            return checkedIndex;

        Node neighborNode = isStartIndex ? officeMath.getPreviousSibling() : officeMath.getNextSibling();

        // Check, if the neighboring children of the paragraph contains the line break char.
        if (doesNodeStartOrEndWithLineBreak(neighborNode, !isStartIndex))
            return checkedIndex;

        return index;
    }

    /// <summary>
    /// Checks if the specified <see cref="Node"/> or the first (or last) child starts (or ends) with the line break char.
    /// </summary>
    /// <param name="node">The specified <see cref="Node"/></param>
    /// <param name="fromBeginning">Determines whether to check the beginning or the end</param>
    /// <returns>"True" if <see cref="Node"/> starts (ends) with the line break char, otherwise - "false"</returns>
    static boolean doesNodeStartOrEndWithLineBreak(Node node, boolean fromBeginning)
    {
        if (node == null)
            return false;

        boolean isLineBreakFound = false;

        switch (node.getNodeType())
        {
            case NodeType.OFFICE_MATH:
                OfficeMath officeMath = (OfficeMath)node;
                if (officeMath.hasChildNodes())
                {
                    Node officeNode = fromBeginning ? officeMath.getFirstChild() : officeMath.getLastChild();
                    isLineBreakFound = doesNodeStartOrEndWithLineBreak(officeNode, fromBeginning);
                }
                break;
            // WORDSNET-16567 If a parent paragraph has a run with text inside,
            // then OfficeMath objects must be displayed in inline mode.
            case NodeType.RUN:
                isLineBreakFound = doesRunStartOrEndWithLineBreak(node, fromBeginning);
                break;
            default:
                break;
        }

        return isLineBreakFound;
    }

    /// <summary>
    /// Checks if the specified node type is a run and the run starts (or ends) with the line break char.
    /// </summary>
    /// <param name="node">Specified <see cref="Node"/></param>
    /// <param name="startsWith">Determines whether to check the beginning or end of the node text</param>
    /// <returns>"True" if node is a run and the run text starts (or ends) with line break, otherwise - "false"</returns>
    private static boolean doesRunStartOrEndWithLineBreak(Node node, boolean startsWith)
    {
        Run run = ms.as(node, Run.class);
        return startsWith ? startsWithLineBreak(run) : endsWithLineBreak(run);
    }

    private /*final*/ OfficeMath mSrcOMath;
}

