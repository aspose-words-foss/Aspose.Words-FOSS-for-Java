// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Provides access to internal Docx/Wml structure.
/// </summary>
interface IXmlExportContext
{
    /// <summary>
    /// Searches for a XML node with using specified parent node and XPath query. First found node is returned.
    /// </summary>
    public org.w3c.dom.Node getXmlNode(org.w3c.dom.Element parentNode, String xpath);

    /// <summary>
    /// Searches for XML nodes with using specified parent node and XPath query. Returns all found nodes.
    /// </summary>
    public ArrayList<org.w3c.dom.Node> getXmlNodes(org.w3c.dom.Element parentNode, String xpath);

    public org.w3c.dom.Document getXmlDocument();
}

