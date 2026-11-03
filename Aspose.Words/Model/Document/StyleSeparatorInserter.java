// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.msObject;


/// <summary>
/// Implements operation of the style separator insertion. Style separator can be inserted 
/// using "ctrl" + "alt" + "enter" keys in the MSW.
/// </summary>
class StyleSeparatorInserter
{
    /// <summary>
    /// Inserts style separator in the current position by the way like MSW does.
    /// </summary>
    /// <param name="builder">Document builder.</param>
    static void insertStyleSeparator(DocumentBuilder builder)
    {
        Debug.assertTrue(builder != null);

        if (!canInsertStyleSeparator(builder))
            return;

        StyleSeparatorInserter inserter = new StyleSeparatorInserter();
        inserter.mBuilder = builder;

        boolean needInsertAgain = inserter.insert();
        if(needInsertAgain)
            inserter.insert();
    }

    /// <summary>
    /// Determines that style separator can be inserted into current position of the builder.
    /// </summary>
    private static boolean canInsertStyleSeparator(DocumentBuilder builder)
    {
        Paragraph para = builder.getCurrentParagraph();
        CompositeNode parentShape = para.getAncestor(NodeType.SHAPE);

        if (para.isInMainTextStory() && (parentShape == null))
            return true;
 
        WarningUtil.warnUnexpected(builder.getDocument().getWarningCallback(), 
            "Insertion of style separator allowed in the main text story.");       

        return false;
    }

    // Hide public constructor.
    private StyleSeparatorInserter() { }

    /// <summary>
    /// Insertion of the style separator.
    /// </summary> 
    private boolean insert()
    {
        // 1. Determine paragraph to which style separator properties has to be applied and
        // paragraph with line break.
        StyleSeparatorContext context = resolveStyleSeparatorContext();
        Paragraph styleSeparatorPara = context.getStyleSeparatorParagraph();
        Paragraph lineBreakPara = context.getLineBreakParagraph();

        if ((styleSeparatorPara == null) || (lineBreakPara == null))
            return false;

        // 2. Apply style separator properties.
        applyStyleSeparatorProperties(styleSeparatorPara);

        // 3. Insert paragraph with line break to the appropriate position in document tree (new paragraph was created).
        if (lineBreakPara.getParentNode() == null)
        {
            if (context.getLineBreakNextSibling() != null)
                context.getLineBreakParentNode().insertBefore(lineBreakPara, context.getLineBreakNextSibling());
            else if (context.getLineBreakPrevSibling() != null)
                context.getLineBreakParentNode().insertAfter(lineBreakPara, context.getLineBreakPrevSibling());
        }

        // 4. Update content of the paragraph with line break and move document builder if it is needed. 
        styleSeparatorPostProcess(context);
                
        return context.isRepeatedInsertExpected();
    }

    /// <summary>
    /// Applies style separator properties to the specified paragraph.
    /// </summary>
    private static void applyStyleSeparatorProperties(Paragraph para)
    {
        Debug.assertTrue(para != null);

        para.getParagraphBreakRunPr().set(FontAttr.SPECIAL_HIDDEN, AttrBoolEx.TRUE);
        para.getParagraphBreakRunPr().set(FontAttr.HIDDEN, AttrBoolEx.TRUE);
    }
    
    /// <summary>
    /// Implements post processing after insertion of the style separator.
    /// </summary>
    private void styleSeparatorPostProcess(StyleSeparatorContext context)
    {
        Debug.assertTrue(context != null);

        Paragraph lineBreakPara = context.getLineBreakParagraph();

        // 1. Update line break paragraph content.
        Run run = lineBreakPara.getFirstRun();
        boolean hasText = (run != null) && StringUtil.hasChars(run.getText());
        boolean startWithSpace = hasText && msString.startsWith(run.getText(), gSpaceStr, StringComparison.ORDINAL);

        // MSW does not append the space for first paragraph into SDT and for last empty
        // paragraph in cell.
        if (!context.isRepeatedInsertExpected() && 
            (!lineBreakPara.isEndOfCell() || !lineBreakPara.isEmptyOrContainsOnlyCrossAnnotation()))
        {
            if (!hasText)
                lineBreakPara.appendChild(new Run(getDoc(), gSpaceStr));

            if (!msString.startsWith(lineBreakPara.getFirstRun().getText(), gSpaceStr, StringComparison.ORDINAL))
                lineBreakPara.prependChild(new Run(getDoc(), gSpaceStr));
        }

        // 2. Move position of the builder.
        if (isNeedMovePositon(context.getStyleSeparatorParagraph()))
        {
            if (!hasText)
                moveTo(lineBreakPara);
            else if (startWithSpace && !context.isRepeatedInsertExpected())
                moveTo(lineBreakPara.getFirstRun());
            else
                moveTo(lineBreakPara.getRuns().get(1));
        }
    }

    /// <summary>
    /// Detects that current position of the document builder has to be changed.
    /// </summary>
    private boolean isNeedMovePositon(Paragraph styleSeparatorPara)
    {
        if (!msObject.referenceEquals(getCurrentParagraph(), styleSeparatorPara) || !isAtEndOfParagraph())
            return false;

        // Step here when current paragraph of the document builder is style separator paragraph
        // and document builder is positioned in the end of paragraph.

        // Builder has to move to new position when current paragraph not in SDT.
        CompositeNode curParent = styleSeparatorPara.getParentNode();
        if (curParent.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return true;

        // Builder has not to change position when current paragraph is last paragraph in SDT.
        if (curParent.getLastNonMarkupCompositeDescendant() == styleSeparatorPara)
            return false;

        return true;
    } 

    /// <summary>
    /// Populates style separator context.
    /// </summary>
    private StyleSeparatorContext resolveStyleSeparatorContext()
    {
        StyleSeparatorContext context = new StyleSeparatorContext();        
        // Detect style separator paragraph for context.
        resolveStyleSeparatorParagraph(context, getCurrentParagraph());

        // Style separator paragraph can not be determined.
        if (context.getStyleSeparatorParagraph() == null)
            return context;

        // Determine paragraph with line break. 
        resolveLineBreakParagraph(context, context.getStyleSeparatorParagraph());

        return context;
    }

    /// <summary>
    /// Calculates paragraph with line break and set it in to context.
    /// </summary>
    private void resolveLineBreakParagraph(StyleSeparatorContext context, Node currentNode)
    {
        Debug.assertTrue(context != null);

        boolean isCurNodeStyleSeparator;

        while (currentNode != null)
        {
            if (currentNode.getNodeType() == NodeType.TABLE)
                context.isRepeatedInsertExpected(false);

            if (isBlockLevelSDT(currentNode))
            {
                // MSW appends additional paragraph before SDT with the space. However, if reopen document
                // and insert style separator then MSW append one more additional paragraph. So, it looks like
                // AW has to store some context for this case in the document. Currently simplified logic was
                // implemented: fact that additional paragraph was inserted determined by it content.
                context.isRepeatedInsertExpected(!context.getStyleSeparatorParagraph().hasNonWhitespaceChildren());
   
                if (!context.isRepeatedInsertExpected())
                {
                    // Append additional paragraph.
                    context.setLineBreakNextSibling(currentNode);
                    context.setLineBreakParagraph(new Paragraph(getDoc()));
                    context.setLineBreakParentNode(currentNode.getParentNode());
                    break;
                }

                // Additional paragraph already exist and position of the document builder has to be changed
                // after insertion of the style separator. At this case MSW does nothing.
                if (isNeedMovePositon(context.getStyleSeparatorParagraph()))
                    break;
            }

            isCurNodeStyleSeparator = (context.getStyleSeparatorParagraph() == currentNode);

            // Next paragraph after style separator is what are looking for.
            if (currentNode.getNodeType() == NodeType.PARAGRAPH && 
                (!isCurNodeStyleSeparator || context.getStyleSeparatorParagraph().isEndOfSection()))
            {             
                if (isCurNodeStyleSeparator)
                {
                    context.setLineBreakParagraph(new Paragraph(getDoc()));
                    context.setLineBreakPrevSibling(currentNode);                         
                }
                else
                {
                    context.setLineBreakParagraph((Paragraph)currentNode);
                }

                context.setLineBreakParentNode(currentNode.getParentNode());
                break;                
            }

            currentNode = currentNode.nextPreOrder(getDoc());
        }                       
    }

    /// <summary>
    /// Calculates style separator paragraph and set it in to context.
    /// </summary>  
    private void resolveStyleSeparatorParagraph(StyleSeparatorContext context, Node currentNode)
    {
        Debug.assertTrue(context != null);
       
        while (currentNode != null)
        {
            if (currentNode.getNodeType() == NodeType.PARAGRAPH)
            {
                // Current style separator paragraph is the last paragraph in the section and this paragraph is
                // last paragraph in the cell.
                // MSW just skip any processing at this case.
                Paragraph para = (Paragraph)currentNode;

                // MSW do nothing when positioned in the last paragraph of the cell or in the end
                // of the not last section or in the last paragraph of the last section which positioned inside SDT.
                if (para.isEndOfCell() || (para.isEndOfSection() && isBlockLevelSDT(para.getParentNode()) &&
                                         para.isEmptyOrContainsOnlyCrossAnnotation()))
                {
                    context.setStyleSeparatorParagraph(null);
                    break;
                }

                // Paragraph without style separator attributes is what are looking for.
                RunPr paraRunPr = para.getParagraphBreakRunPr();
                if (!paraRunPr.containsKey(FontAttr.SPECIAL_HIDDEN) || !paraRunPr.containsKey(FontAttr.HIDDEN))
                {
                    context.setStyleSeparatorParagraph(para);
                    break;
                }
            }

            currentNode = currentNode.nextPreOrder(getDoc());
        }
    }    

    /// <summary>
    /// Returns true when current node is block level structured document tag.
    /// </summary>
    private static boolean isBlockLevelSDT(Node node)
    {
        Debug.assertTrue(node != null);

        return (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) && NodeUtil.isBlockLevelNode(node);
    }

    /// <summary>
    /// Moves position of the document builder to the specified node.
    /// </summary>
    private void moveTo(Node node)
    {
        mBuilder.moveTo(node);
    }

    /// <summary>
    /// Gets the document object which is attached to document builder.
    /// </summary>
    private Document getDoc() { return mBuilder.getDocument(); }

    /// <summary>
    /// Returns the paragraph that is currently selected in the document builder.
    /// </summary>
    private Paragraph getCurrentParagraph() { return mBuilder.getCurrentParagraph(); }

    /// <summary>
    /// Returns true if the cursor of the document builder is at the end of the current paragraph.
    /// CrossStructureAnnotations must be ignored.
    /// </summary>
    private boolean isAtEndOfParagraph()
    {
        Node cursor = mBuilder.getCurrentNode();
        if (cursor == null)
            return true;

        // WORDSNET-19622 CrossStructureAnnotations must be ignored.
        // Walk all nodes from the end of the paragraph backwards.
        // If we have something other than CrossStructureAnnotation, it means we are not at the end of the paragraph.
        Node node = getCurrentParagraph().getLastChild();
        while ((node != null) && (node != cursor))
        {
            if (NodeUtil.isCrossStructureAnnotation(node))
                node = node.getPreviousSibling();
            else
                return false;
        }
        return NodeUtil.isCrossStructureAnnotation(node);
    }

    /// <summary>
    /// Document builder.
    /// </summary>
    private DocumentBuilder mBuilder;

    /// <summary>
    /// Space string.
    /// </summary>
    private static String gSpaceStr = " ";
}

