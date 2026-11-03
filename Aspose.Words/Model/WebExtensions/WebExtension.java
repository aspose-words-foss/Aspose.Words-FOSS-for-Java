// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a web extension object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-office-add-ins/">Work with Office Add-ins</a> documentation article.</para>
/// </summary>
/// <dev>2.2.7 CT_OsfWebExtension</dev>
public class WebExtension
{
    // Hide ctr.
    WebExtension() { }

    /// <summary>
    /// Uniquely identifies the web extension instance in the current document.
    /// </summary>
    public String getId() { return mId; }; public void setId(String value) { mId = value; };

    private String mId;

    /// <summary>
    /// Specifies whether the user can interact with the web extension or not.
    /// </summary>
    public boolean isFrozen() { return mIsFrozen; }; public void isFrozen(boolean value) { mIsFrozen = value; };

    private boolean mIsFrozen;

    /// <summary>
    /// Specifies the primary reference to an web extension.
    /// </summary>
    public WebExtensionReference getReference() { return mReference; }

    /// <summary>
    /// Specifies a list of web extension bindings.
    /// </summary>
    public WebExtensionBindingCollection getBindings() { return mBindings; }

    /// <summary>
    /// Specifies alternate references to a web extension.
    /// </summary>
    public WebExtensionReferenceCollection getAlternateReferences() { return mAlternateReferences; }

    /// <summary>
    /// Represents a set of web extension custom properties.
    /// </summary>
    public WebExtensionPropertyCollection getProperties() { return mProperties; }

    /// <summary>
    /// Specifies a static image used to render the contents of the application for Office when it is not active.
    /// </summary>
    DmlBlip getSnapshot() { return mSnapshot;  }
    void setSnapshot(DmlBlip value) { mSnapshot = value; }

    private DmlBlip mSnapshot;
    private /*final*/ WebExtensionReference mReference = new WebExtensionReference();
    private /*final*/ WebExtensionBindingCollection mBindings = new WebExtensionBindingCollection();
    private /*final*/ WebExtensionPropertyCollection mProperties = new WebExtensionPropertyCollection();
    private /*final*/ WebExtensionReferenceCollection mAlternateReferences = new WebExtensionReferenceCollection();
}

