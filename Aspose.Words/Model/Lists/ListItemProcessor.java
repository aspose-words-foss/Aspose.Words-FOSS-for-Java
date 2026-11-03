// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/05/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.EnumUtilPal;


/// <summary>
/// Base for classes that need to go through all list items/paragraphs in numeration order by numeration areas
/// (Body, HeaderFooter, Footnote, Endnote, Shape, Comment).
/// For more information please see description of the <see cref="ListLabelUpdater"/> class.
/// </summary>
abstract class ListItemProcessor extends DocumentVisitor
{
    public final /*override*/ /*VisitorAction*/int visitBodyStart(Body body)
    {
        onNumberingAreaStart(ListNumberingArea.BODY);
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitBodyEnd(Body body)
    {
        onNumberingAreaEnd();
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitHeaderFooterStart(HeaderFooter headerFooter)
    {
        onNumberingAreaStart(ListNumberingArea.HEADER_FOOTER);
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitHeaderFooterEnd(HeaderFooter headerFooter)
    {
        onNumberingAreaEnd();
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        switch (footnote.getFootnoteType())
        {
            case FootnoteType.FOOTNOTE:
                onNumberingAreaStart(ListNumberingArea.FOOTNOTE);
                break;
            case FootnoteType.ENDNOTE:
                onNumberingAreaStart(ListNumberingArea.ENDNOTE);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitFootnoteEnd(Footnote footnote)
    {
        onNumberingAreaEnd();
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment)
    {
        onNumberingAreaStart(ListNumberingArea.COMMENT);
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitCommentEnd(Comment comment)
    {
        onNumberingAreaEnd();
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape)
    {
        onNumberingAreaStart(ListNumberingArea.SHAPE);
        return VisitorAction.CONTINUE;
    }

    public final /*override*/ /*VisitorAction*/int visitShapeEnd(Shape shape)
    {
        onNumberingAreaEnd();
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// The method is executed when a numbering area is started.
    /// </summary>
    protected abstract void onNumberingAreaStart(/*ListNumberingArea*/int area);

    /// <summary>
    /// The method is executed when a numbering area is ended.
    /// </summary>
    protected abstract void onNumberingAreaEnd();

    /// <summary>
    /// Count of items in <see cref="ListNumberingArea"/>.
    /// </summary>
    protected static /*final*/ int NUMBER_OF_AREAS = 
        EnumUtilPal.getEffectiveArrayLength((6));

    /// <summary>
    /// Designates an area of continuous list numbering.
    /// </summary>
    /*enum*/ final class ListNumberingArea
    {
        private ListNumberingArea(){}
        
        public static final int BODY = 0;
        public static final int HEADER_FOOTER = 1;
        public static final int FOOTNOTE = 2;
        public static final int ENDNOTE = 3;
        public static final int SHAPE = 4;
        public static final int COMMENT = 5;

        public static final int length = 6;
    }
}

