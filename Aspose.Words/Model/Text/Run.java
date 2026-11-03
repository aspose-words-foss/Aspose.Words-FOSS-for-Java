// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Represents a run of characters with the same font formatting.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>All text of the document is stored in runs of text.</p>
/// <p><see cref="Run"/> can only be a child of <see cref="Paragraph"/> or inline <see cref="Aspose.Words.Markup.StructuredDocumentTag"/>.</p>
/// </remarks>
public class Run extends Inline
{
    /// <summary>
    /// Initializes a new instance of the <see cref="Run"/> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Run"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To append <see cref="Run"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the paragraph where you want the run inserted.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Run(DocumentBase doc)
    {
    	this(doc, "");
	
    }

    /// <summary>
    /// Initializes a new instance of the <b>Run</b> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Run"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To append <see cref="Run"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the paragraph where you want the run inserted.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    /// <param name="text">The text of the run.</param>
    public Run(DocumentBase doc, String text)
    {
    	this(doc, text, new RunPr());
	
    }

    Run(DocumentBase doc, String text, RunPr runPr)
    {
    	super(doc, runPr);
	
        if (text == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "text");

        mText = text;
    }

    /// <summary>
    /// Returns <see cref="NodeType.Run"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.RUN; }

    /// <summary>
    /// Gets or sets the text of the run.
    /// </summary>
    
    public String getText() { return mText; }
    
    public void setText(String value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        if (msString.equals(mText, value))
            return;

        if (getDocument().isTrackRevisionsEnabled())
        {
            EditSession editSession = fetchDocumentOrGlossaryMain().getEditSession();

            if (!msString.equals(mText, ""))
            {
                // Make copy of this run. It will be marked for deletion.
                Run oldTextRun = new Run(getDocument(), mText, AttrCollectionExtension.deepClone(getRunPr()));

                SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(getDocument());
                try /*JAVA: was using*/
                {
                    // WORDSNET-16417 Use ParentNode instead of ParentParagraph to insert it into SDT
                    // Have to suspend tracking because actually we insert run but it should be marked for deletion.
                    getParentNode().insertAfter(oldTextRun, this);
                    RevisionTrackingUtil.addDeleteRevision(oldTextRun, editSession);
                }
                finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
            }

            RevisionTrackingUtil.addInsertRevision(this, editSession);
        }

        mText = value;
    }

    /// <summary>
    /// Gets a boolean value indicating either the run is a phonetic guide.
    /// </summary>
    public boolean isPhoneticGuide() { return getRunPr().contains(FontAttr.RUBY); }

    /// <summary>
    /// Gets a <see cref="PhoneticGuide"/> object.
    /// </summary>
    public PhoneticGuide getPhoneticGuide()
    {
        if (mPhoneticGuide == null)
            mPhoneticGuide = new PhoneticGuide(this);

        return mPhoneticGuide;
    }

    /// <summary>
    /// Indicates that run contains only symbols.
    /// </summary>
    boolean isSymbolic()
    {
        if (mText.length() == 0)
            return false;

        for (int i = 0; i < mText.length(); i++)
            if (!StringUtil.isPrivateUseCategory(mText.charAt(i)))
                return false;

        return true;
    }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitRun"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitRun(this));
    }

    /// <summary>
    /// Cuts the text of the run.
    /// </summary>
    /// <param name="startIndex">A text position the new text should be started from.</param>
    /// <param name="length">The length of the new text.</param>
    /// <param name="isClone">True to return a new run, false to alter this run and return a reference to it.</param>
    /// <returns></returns>
    Run cutText(int startIndex, int length, boolean isClone)
    {
        Run run = (!isClone) ? this : (Run)deepClone(false);

        if (length == 0)
            run.setText("");
        else
            run.setText(run.getText().substring((startIndex), (startIndex) + (length)));

        return run;
    }

    /// <summary>
    /// Splits the run before leftPartLength position.
    /// First leftPartLength chars go to a new run before the remaining part of an old run.
    /// </summary>
    /// <remarks>It does nothing when the whole run would go in one part of split.</remarks>
    /// <param name="leftPartLength"></param>
    /// <returns>A Run object representing the newly inserted run or null if the run was not actually split.</returns>
    Run splitBefore(int leftPartLength)
    {
        Run insertedRun = null;

        if ((leftPartLength > 0) && (leftPartLength < getText().length()))
        {
            insertedRun = (Run) getParentNode().insertBefore(
                new Run(getDocument(), getText().substring((0), (0) + (leftPartLength)), AttrCollectionExtension.deepClone(getRunPr())), this);
            cutText(leftPartLength, getText().length() - leftPartLength, false);
        }

        return insertedRun;
    }

    /// <summary>
    /// Splits the run after leftPartLength position.
    /// First leftPartLength chars remain in the old run, the rest goes to a new run after it.
    /// </summary>
    /// <remarks>It does nothing when the whole run would go in one part of split.</remarks>
    /// <param name="leftPartLength"></param>
    /// <returns>A Run object representing the newly inserted run or null if the run was not actually split.</returns>
    Run splitAfter(int leftPartLength)
    {
        Run insertedRun = null;

        if ((leftPartLength > 0) && (leftPartLength < getText().length()))
        {
            insertedRun = (Run) getParentNode().insertAfter(
                new Run(getDocument(), getText().substring((leftPartLength), (leftPartLength) + (getText().length() - leftPartLength)), AttrCollectionExtension.deepClone(getRunPr())), this);
            cutText(0, leftPartLength, false);
        }

        return insertedRun;
    }

    /// <summary>
    /// Returns true if a passed string represents a symbol run.
    /// </summary>
    /// <remarks>
    /// More info in NrxRunReaderBase.ReadSymbol().
    /// It seems to me that the symbol characters are stored as one-character run in the model.
    /// So to find them I check if the run has the first character that belongs to Unicode 'Private Use' category.
    /// 'Private Use' means that no font substitution can be done on such characters because
    /// they are providing glyphs entirely different from conventional glyphs for these characters.
    ///</remarks>
    static boolean isSymbolicCharacter(String text)
    {
        return (StringUtil.hasChars(text)) && StringUtil.isPrivateUseCategory(text.charAt(0));
    }

    private String mText;
    private PhoneticGuide mPhoneticGuide;

    /// <summary>
    /// These keys will be ignored in run properties comparison on join of LTR runs. Keep sorted since we use binary search.
    /// FontAttr.RsidRPr and FontAttr.RsidR should be ignored since they are the unique editing session IDs.
    /// Other keys are specific for LTR runs.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_LTR_RUNS =
    {
        FontAttr.RSID_R_PR, FontAttr.RSID_R, FontAttr.ISTD, FontAttr.BOLD_BI, FontAttr.ITALIC_BI, FontAttr.NAME_BI, FontAttr.LOCALE_ID_BI,
        FontAttr.SIZE_BI
    };

    /// <summary>
    /// These keys will be ignored in run properties comparison on join RTL runs. Keep sorted since we use binary search.
    /// FontAttr.RsidRPr and FontAttr.RsidR should be ignored since they are the unique editing session IDs.
    /// Other keys are specific for RTL runs.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_RTL_RUNS =
    {
        FontAttr.RSID_R_PR, FontAttr.RSID_R, FontAttr.ISTD, FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SIZE, FontAttr.NAME_ASCII,
        FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER, FontAttr.LOCALE_ID, FontAttr.LOCALE_ID_FAR_EAST
    };

    /// <summary>
    /// All possible attributes that can be added to the ignore set during join runs.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_ALL =
    {
        FontAttr.HIGHLIGHT_COLOR, FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SMALL_CAPS, FontAttr.ALL_CAPS, FontAttr.SPACING,
        FontAttr.COLOR, FontAttr.EMBOSS, FontAttr.ENGRAVE, FontAttr.SIZE, FontAttr.POSITION, FontAttr.VERTICAL_ALIGNMENT,
        FontAttr.KERNING, FontAttr.NAME_ASCII, FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER, FontAttr.BOLD_BI, FontAttr.ITALIC_BI,
        FontAttr.BIDI, FontAttr.COMPLEX_SCRIPT, FontAttr.NAME_BI, FontAttr.SCALING, FontAttr.LOCALE_ID_BI, FontAttr.SIZE_BI,
        FontAttr.LOCALE_ID, FontAttr.LOCALE_ID_FAR_EAST, FontAttr.CHARACTER_CATEGORY_HINT, FontAttr.NO_PROOFING,
        FontAttr.UNDERLINE_COLOR, FontAttr.THEME_COLOR,  
    };

    /// <summary>
    /// In IgnoreSpacing mode, if the run text contains only spaces, these attributes are ignored.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_IGNORE_SPACING =
    {
        FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SMALL_CAPS, FontAttr.ALL_CAPS, FontAttr.SPACING, FontAttr.COLOR, FontAttr.EMBOSS,
        FontAttr.ENGRAVE, FontAttr.SIZE, FontAttr.POSITION, FontAttr.VERTICAL_ALIGNMENT, FontAttr.KERNING, FontAttr.NAME_ASCII,
        FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER, FontAttr.BOLD_BI, FontAttr.ITALIC_BI, FontAttr.COMPLEX_SCRIPT, FontAttr.NAME_BI,
        FontAttr.SCALING, FontAttr.LOCALE_ID_BI, FontAttr.SIZE_BI, FontAttr.LOCALE_ID, FontAttr.LOCALE_ID_FAR_EAST,
        FontAttr.CHARACTER_CATEGORY_HINT, FontAttr.NO_PROOFING, FontAttr.UNDERLINE_COLOR, FontAttr.THEME_COLOR
    };

    /// <summary>
    /// In IgnoreInsignificant mode, if the run text contains only spaces, these attributes are ignored.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_INSIGNIFICANT_SPACING =
    {
        FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SMALL_CAPS, FontAttr.ALL_CAPS, FontAttr.COLOR, FontAttr.EMBOSS, FontAttr.ENGRAVE,
        FontAttr.POSITION, FontAttr.VERTICAL_ALIGNMENT, FontAttr.KERNING, FontAttr.NAME_ASCII, FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER,
        FontAttr.BOLD_BI, FontAttr.ITALIC_BI, FontAttr.COMPLEX_SCRIPT, FontAttr.NAME_BI, FontAttr.LOCALE_ID_BI, FontAttr.LOCALE_ID,
        FontAttr.LOCALE_ID_FAR_EAST, FontAttr.CHARACTER_CATEGORY_HINT, FontAttr.NO_PROOFING, FontAttr.UNDERLINE_COLOR, FontAttr.THEME_COLOR
    };

    /// <summary>
    /// In IgnoreRedundant mode, if the run text contains only spaces, these attributes are ignored.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_IGNORE_REDUNDANT_SPACING =
    {
        FontAttr.COLOR, FontAttr.VERTICAL_ALIGNMENT, FontAttr.NO_PROOFING, FontAttr.UNDERLINE_COLOR, FontAttr.THEME_COLOR 
    };

    /// <summary>
    /// In IgnoreRedundant mode, only these attributes can be ignored.
    /// </summary>
    static /*final*/ int[] KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_IGNORE_REDUNDANT =
    {
        FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SIZE, FontAttr.NAME_ASCII, FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER, FontAttr.BOLD_BI,
        FontAttr.ITALIC_BI, FontAttr.BIDI, FontAttr.COMPLEX_SCRIPT, FontAttr.NAME_BI, FontAttr.LOCALE_ID_BI, FontAttr.SIZE_BI,
        FontAttr.LOCALE_ID, FontAttr.LOCALE_ID_FAR_EAST, FontAttr.CHARACTER_CATEGORY_HINT, FontAttr.NO_PROOFING
    };

    /// <summary>
    /// In IgnoreRedundant mode and a specific Ascii character category, only the specified attributes are considered.
    /// </summary>
    static /*final*/ int[] KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_ASCII =
    {
        FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SIZE, FontAttr.NAME_ASCII, FontAttr.LOCALE_ID
    };

    /// <summary>
    /// In IgnoreRedundant mode and a specific FarEast character category, only the specified attributes are considered.
    /// </summary>
    static /*final*/ int[] KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_FAR_EAST =
    {
        FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SIZE, FontAttr.NAME_FAR_EAST, FontAttr.LOCALE_ID_FAR_EAST, FontAttr.CHARACTER_CATEGORY_HINT
    };

    /// <summary>
    /// In IgnoreRedundant mode and a specific ComplexScript character category, only the specified attributes are considered.
    /// </summary>
    static /*final*/ int[] KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_COMPLEX_SCRIPT =
    {
        FontAttr.BOLD_BI, FontAttr.ITALIC_BI, FontAttr.NAME_BI, FontAttr.LOCALE_ID_BI, FontAttr.SIZE_BI
    };

    /// <summary>
    /// In IgnoreRedundant mode and a specific Other character category, only the specified attributes are considered.
    /// </summary>
    static /*final*/ int[] KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_OTHER =
    {
        FontAttr.BOLD, FontAttr.ITALIC, FontAttr.SIZE, FontAttr.NAME_OTHER, FontAttr.LOCALE_ID
    };


}

