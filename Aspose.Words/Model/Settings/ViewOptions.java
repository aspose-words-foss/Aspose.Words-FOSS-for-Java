// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2005 by Roman Korchagin, Michael Morozoff

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides various options that control how a document is shown in Microsoft Word.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-word-document-options-and-appearance/">Work with Options and Appearance of Word Documents</a> documentation article.</para>
/// </summary>
/// <seealso cref="Document"/>
/// <seealso cref="Document.ViewOptions"/>
public class ViewOptions implements Cloneable
{
    ViewOptions()
    {
        setViewType(ViewType.NORMAL);
        setZoomPercent(100);
        setZoomType(ZoomType.NONE);
    }

    /// <summary>
    /// Controls the view mode in Microsoft Word.
    /// </summary>
    /// <remarks><p>Although Aspose.Words is able to read and write this option, its usage is application-specific.
    /// For example MS Word 2013 does not respect the value of this option.</p></remarks>
    public /*ViewType*/int getViewType() { return mViewType; }; public void setViewType(/*ViewType*/int value) { mViewType = value; };

    private /*ViewType*/int mViewType;

    /// <summary>
    /// Gets or sets a zoom value based on the size of the window.
    /// </summary>
    public /*ZoomType*/int getZoomType() { return mZoomType; }; public void setZoomType(/*ZoomType*/int value) { mZoomType = value; };

    private /*ZoomType*/int mZoomType;

    /// <summary>
    /// Gets or sets the percentage at which you want to view your document.
    /// </summary>
    /// <remarks>
    /// <p>Although Aspose.Words is able to read and write this option, its usage is application-specific.
    /// For example MS Word 2013 does not respect the value of this option. </p>
    /// </remarks>
    public int getZoomPercent() { return mZoomPercent; }; public void setZoomPercent(int value) { mZoomPercent = value; };

    private int mZoomPercent;

    /// <summary>
    /// Turns off display of the space between the top of the text and the top edge of the page.
    /// </summary>
    public boolean getDoNotDisplayPageBoundaries() { return mDoNotDisplayPageBoundaries; }; public void setDoNotDisplayPageBoundaries(boolean value) { mDoNotDisplayPageBoundaries = value; };

    private boolean mDoNotDisplayPageBoundaries;

    /// <summary>
    /// Controls display of the background shape in print layout view.
    /// </summary>
    public boolean getDisplayBackgroundShape() { return mDisplayBackgroundShape; }; public void setDisplayBackgroundShape(boolean value) { mDisplayBackgroundShape = value; };

    private boolean mDisplayBackgroundShape;

    /// <summary>
    /// Specifies whether the document is in forms design mode.
    /// </summary>
    /// <remarks>
    /// <p>Currently works only for documents in WordML format.</p>
    /// </remarks>
    public boolean getFormsDesign() { return mFormsDesign; }; public void setFormsDesign(boolean value) { mFormsDesign = value; };

    private boolean mFormsDesign;

    ViewOptions deepClone()
    {
        return (ViewOptions)memberwiseClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

