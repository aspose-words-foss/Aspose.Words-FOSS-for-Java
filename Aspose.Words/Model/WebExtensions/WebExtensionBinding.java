// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a binding relationship between a web extension and the data in the document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-office-add-ins/">Work with Office Add-ins</a> documentation article.</para>
/// </summary>
/// <dev>2.2.3 CT_OsfWebExtensionBinding</dev>
public class WebExtensionBinding
{
    /// <summary>
    /// Hide default ctr.
    /// </summary>
    WebExtensionBinding() { }

    /// <summary>
    /// Creates web extension binding with specified parameters.
    /// </summary>
    /// <param name="id">Binding identifier.</param>
    /// <param name="bindingType">Binding type.</param>
    /// <param name="appRef">Binding key used to map the binding entry in this list with the bound data in the document.</param>
    public WebExtensionBinding(String id, /*WebExtensionBindingType*/int bindingType, String appRef)
    {
        setId(id);
        setAppRef(appRef);
        setBindingType(bindingType);
    }

    /// <summary>
    /// Specifies the binding identifier.
    /// </summary>
    public String getId() { return mId; }; public void setId(String value) { mId = value; };

    private String mId;

    /// <summary>
    /// Specifies the binding type.
    /// </summary>
    public /*WebExtensionBindingType*/int getBindingType() { return mBindingType; }; public void setBindingType(/*WebExtensionBindingType*/int value) { mBindingType = value; };

    private /*WebExtensionBindingType*/int mBindingType;

    /// <summary>
    /// Specifies the binding key used to map the binding entry in this list with the bound data in the document.
    /// </summary>
    /// <dev>It may be, for example, identifier of the SDT.</dev>
    public String getAppRef() { return mAppRef; }; public void setAppRef(String value) { mAppRef = value; };

    private String mAppRef;
}

