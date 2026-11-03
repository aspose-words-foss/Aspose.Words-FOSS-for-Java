// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the reference to a web extension. The reference is used to identify the provider location and version of the
/// extension.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-office-add-ins/">Work with Office Add-ins</a> documentation article.</para>
/// </summary>
/// <dev>2.2.5 CT_OsfWebExtensionReference. This essence has public constructor to provide ability to populate alternate references.</dev>
public class WebExtensionReference
{
    /// <summary>
    /// Identifier associated with the web extension within a catalog provider.
    /// </summary>
    public String getId() { return mId; }; public void setId(String value) { mId = value; };

    private String mId;

    /// <summary>
    /// Specifies the version of the web extension.
    /// </summary>
    public String getVersion() { return mVersion; }; public void setVersion(String value) { mVersion = value; };

    private String mVersion;

    /// <summary>
    ///  Specifies the instance of the marketplace where the web extension is stored.
    /// </summary>
    public String getStore() { return mStore; }; public void setStore(String value) { mStore = value; };

    private String mStore;

    /// <summary>
    /// Specifies the type of marketplace.
    /// </summary>
    public /*WebExtensionStoreType*/int getStoreType() { return mStoreType; }; public void setStoreType(/*WebExtensionStoreType*/int value) { mStoreType = value; };

    private /*WebExtensionStoreType*/int mStoreType;
}

