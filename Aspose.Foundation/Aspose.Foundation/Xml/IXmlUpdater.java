// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/05/2016 by Alexander Zhiltsov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This interface is used in 
/// <see cref="XmlUtilPal.ReadOuterXml(System.Xml.XmlTextReader, IXmlUpdater, XmlTextReaderNamespaceStorage)"/>
/// to be able updating XML during reading. Now only namespace changing is supported.
/// </summary>
public interface IXmlUpdater
{
    /// <summary>
    /// Replaces one namespace with another.
    /// </summary>
    public String replaceNamespace(String namespaceUrl);
}

