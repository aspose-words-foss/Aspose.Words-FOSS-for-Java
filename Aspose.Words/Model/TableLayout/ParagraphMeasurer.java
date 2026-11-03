// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrFont;
import com.aspose.ms.System.msChar;


/// <summary>
/// The main purpose of this class is calculating layout (min and max) paragraph width.
/// </summary>
class ParagraphMeasurer
{
    LayoutWidth getLayoutWidth(Paragraph paragraph)
    {
        LayoutWidth layoutWidth = new LayoutWidth();
        int lockCount = 0;

        for (Node childNode : (Iterable<Node>) paragraph.getChildNodes(NodeType.ANY, false))
        {
            // TODO: Add support of explicit line breaks
            switch (childNode.getNodeType())
            {
                case NodeType.FIELD_START:
                {
                    lockCount++;
                    break;
                }
                case NodeType.FIELD_SEPARATOR:
                {
                    lockCount--;
                    break;
                }
                case NodeType.FIELD_END:
                {
                    FieldEnd fieldEnd = (FieldEnd)childNode;
                    if (!fieldEnd.hasSeparator())
                        lockCount--;
                    
                    break;
                }
                default:
                {
                    if (lockCount == 0)
                        processNode(childNode, layoutWidth);

                    break;
                }
            }

            if (mIsInsideWord)
                addWordWidth(layoutWidth);
        }

        ParagraphFormat pf = paragraph.getParagraphFormat();

        double toAdd = pf.getLeftIndent() + pf.getRightIndent();

        if (pf.getFirstLineIndent() < 0)
            toAdd -= pf.getFirstLineIndent();

        layoutWidth.add(toAdd);

        return layoutWidth;
    }

    private void processNode(Node node, LayoutWidth width)
    {
        // TODO: Add support of explicit line breaks
        switch (node.getNodeType())
        {
            case NodeType.RUN:
            {
                Run run = (Run)node;
                // WORDSJAVA-862/864: Fetch font only once per Run and pass it to
                // all child method calls. Fetching font and it's attributes is very expensive
                // operation: this fix raised performance up to 2 times on IBM performance tests.
                DrFont font = getDrawingFont(run.getFont(), run.fetchDocumentOrGlossaryMain());
                String text = run.getText();
                updateMinWidth(font, text, width);
                width.setMax(width.getMax() + font.getTextWidthPoints(text));
                break;
            }
            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
            {
                ShapeBase shape = (ShapeBase)node;

                // WORDSNET-16273 Take in attention shapes with "Square" wrap type.
                if (shape.isInline() || ((shape.getWrapType() == WrapType.SQUARE) && (shape.getWrapSide() == WrapSide.BOTH)))
                {
                    width.setMin(Math.max(width.getMin(), shape.getWidth()));
                    width.setMax(width.getMax() + shape.getWidth());
                }

                break;
            }
            default:
            {
                // Skip other types of nodes.

                break;
            }
        }
    }

    private void updateMinWidth(DrFont font, String text, LayoutWidth layoutWidth)
    {
        int startIndex = 0, length = 0;

        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);

            if (msChar.isWhiteSpace(c) || (c == '-'))
            {
                if (mIsInsideWord)
                {
                    addSubstringWidth(font, text, startIndex, length);
                    addWordWidth(layoutWidth);
                    length = 0;
                }
            }
            else
            {
                if (!mIsInsideWord)
                {
                    mIsInsideWord = true;
                    startIndex = i;
                }
                length++;
            }
        }

        if (mIsInsideWord)
            addSubstringWidth(font, text, startIndex, length);
    }

    private void addSubstringWidth(DrFont font, String text, int startIndex, int length)
    {
        String substring = text.substring((startIndex), (startIndex) + (length));
        mCurrentWordWidth += font.getTextWidthPoints(substring);
    }

    private void addWordWidth(LayoutWidth layoutWidth)
    {
        layoutWidth.setMin(Math.max(layoutWidth.getMin(), mCurrentWordWidth));
        mCurrentWordWidth = 0.0;
        mIsInsideWord = false;
    }

    private static DrFont getDrawingFont(Font font, Document doc)
    {
        return doc.getFontProvider().fetchDrFont(font.getName(), (float)font.getSize(), font.getFontStyle());
    }

    private boolean mIsInsideWord;
    private double mCurrentWordWidth;
}
