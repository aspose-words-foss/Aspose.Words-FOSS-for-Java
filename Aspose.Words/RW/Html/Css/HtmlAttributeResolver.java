// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntStack;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import com.aspose.StackExtension;
import com.aspose.StringUtil;
import com.aspose.Language;


/// <summary>
/// Resolves HTML attributes that don't have CSS analogs.
/// </summary>
class HtmlAttributeResolver
{
    HtmlAttributeResolver()
    {
        mLocaleIdStack = new IntStack();
        mLocaleIdBiStack = new IntStack();
        mLocaleIdFarEastStack = new IntStack();
        mInsertionRevisions = new msStack<EditRevision>();
        mDeletionRevisions = new msStack<EditRevision>();
    }

    /// <summary>
    /// Adds an element to the stack and calculates values of HTML attributes for this element.
    /// </summary>
    /// <param name="element">HTML element.</param>
    /// <param name="elementDeclarations">HTML element actual declarations. The actual declarations are the used declarations after any
    /// such adjustments have been made.</param>
    void pushElement(IHtmlElementProvider element, CssDeclarationCollection elementDeclarations)
    {
        processLangAttribute(element, elementDeclarations);
        processRevisions(element);
    }

    /// <summary>
    /// Removes a top element from the stack.
    /// </summary>
    void popElement()
    {
        mInsertionRevisions.pop();
        mDeletionRevisions.pop();
        mLocaleIdStack.pop();
        mLocaleIdBiStack.pop();
        mLocaleIdFarEastStack.pop();
    }

    private void processRevisions(IHtmlElementProvider element)
    {
        EditRevision insertionRevision = ("ins".equals(element.getElementName()))
                                             ? getRevision(element, EditRevisionType.INSERTION)
                                             : getCurrentRevision(mInsertionRevisions);
        mInsertionRevisions.push(insertionRevision);

        EditRevision deletionRevision = ("del".equals(element.getElementName()))
                                            ? getRevision(element, EditRevisionType.DELETION)
                                            : getCurrentRevision(mDeletionRevisions);
        mDeletionRevisions.push(deletionRevision);
    }

    private static EditRevision getRevision(IHtmlElementProvider element, /*EditRevisionType*/int revisionType)
    {
        String author = element.getAttributeValue("cite", "");
        DateTime datetime = FormatterPal.xmlToDateTime(element.getAttributeValue("datetime", ""));
        return new EditRevision(revisionType, author, datetime);
    }

    private static EditRevision getCurrentRevision(msStack<EditRevision> revisions)
    {
        return StackExtension.top(revisions);
    }

    private void processLangAttribute(IHtmlElementProvider element, CssDeclarationCollection elementDeclarations)
    {
        // Language information is inherited by elements inside the one where the declaration was made.
        int localeId = getLocaleId();
        int localeIdBi = getLocaleIdBi();
        int localeIdFarEast = getLocaleIdFarEast();

        String lang = element.getAttributeValue("lang");
        if (StringUtil.hasChars(lang))
        {
            int langLocaleId = HtmlLocaleConverter.tagToLocale(lang);
            if (langLocaleId != (int)Language.INVARIANT_CULTURE)
            {
                if (CssUtil.getDirection(elementDeclarations) == CssDirection.RTL)
                {
                    localeIdBi = langLocaleId;
                }
                else
                {
                    localeId = langLocaleId;
                    localeIdFarEast = langLocaleId;
                }
            }
        }

        mLocaleIdStack.push(localeId);
        mLocaleIdBiStack.push(localeIdBi);
        mLocaleIdFarEastStack.push(localeIdFarEast);
    }

    int getLocaleId()
    {
        return (mLocaleIdStack.getCount() != 0)
                   ? mLocaleIdStack.peek()
                   : (int)Language.NO_PROOF;
    }

    int getLocaleIdBi()
    {
        return (mLocaleIdBiStack.getCount() != 0)
                   ? mLocaleIdBiStack.peek()
                   : (int)Language.NO_PROOF;
    }

    int getLocaleIdFarEast()
    {
        return (mLocaleIdFarEastStack.getCount() != 0)
                   ? mLocaleIdFarEastStack.peek()
                   : (int)Language.NO_PROOF;
    }


    /// <summary>
    /// Gets an insertion revision for the current element.
    /// Can be null, if the element doesn't have an insertion revision.
    /// </summary>
    EditRevision getInsertionRevision() { return StackExtension.top(mInsertionRevisions); }

    /// <summary>
    /// Gets a deletion revision for the current element.
    /// Can be null, if the element doesn't have a deletion revision.
    /// </summary>
    EditRevision getDeletionRevision() { return StackExtension.top(mDeletionRevisions); }

    private /*final*/ IntStack mLocaleIdStack;
    private /*final*/ IntStack mLocaleIdBiStack;
    private /*final*/ IntStack mLocaleIdFarEastStack;

    /// <summary>
    /// Stack of insertion revisions.
    /// </summary>
    private /*final*/ msStack<EditRevision> mInsertionRevisions;

    /// <summary>
    /// Stack of deletion revisions.
    /// </summary>
    private /*final*/ msStack<EditRevision> mDeletionRevisions;
}

