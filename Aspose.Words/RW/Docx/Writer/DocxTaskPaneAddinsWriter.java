// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.words.ref.Ref;


/// <summary>
/// Exports task pane add-ins collection.
/// </summary>
/*static*/ class DocxTaskPaneAddinsWriter
{
	/* Simulation of static class by using private constructor */
	private DocxTaskPaneAddinsWriter()
	{}

    /// <summary>
    /// Writes task pane add-ins to the package.
    /// </summary>
    static void write(DocxDocumentWriter writer) throws Exception
    {
        TaskPaneCollection taskPanes = writer.getDocument().getWebExtensionTaskPanes();
        if (taskPanes.getCount() == 0)
            return;

        writeTaskPanes(writer, taskPanes);
    }

    /// <summary>
    /// Writes "2.2.9 CT_OsfTaskpanes" element.
    /// </summary>
    private static void writeTaskPanes(DocxDocumentWriter writer, TaskPaneCollection taskPanes) throws Exception
    {
        String relId = null;
        // Prepare appropriate package part.
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder builder = writer.createChildPartAndBuilder(null, "/word/webextensions/taskpanes.xml",
            DocxContentType.WEB_EXTENSION_TASK_PANES, writer.getRelTypes().getWebExtensionTaskPanes(), /*out*/ referenceToRelId);
        relId = referenceToRelId.get();
        writer.pushBuilder(builder);

        // Write task panes.
        builder.startDocument("wetp:taskpanes");
        builder.writeAttributeString("xmlns:wetp", "http://schemas.microsoft.com/office/webextensions/taskpanes/2010/11");

        for (TaskPane taskPane : (Iterable<TaskPane>) taskPanes)
        {
            relId = DocxWebExtensionWriter.write(writer, builder.getPart(), taskPane.getWebExtension());
            writeTaskPane(builder, taskPane, relId);
        }

        builder.endDocument();
        writer.popBuilder();
    }

    /// <summary>
    /// Writes "2.2.8 CT_OsfTaskpane" element.
    /// </summary>
    private static void writeTaskPane(DocxBuilder builder, TaskPane taskPane, String relToWebExtension)
    {
        final String TAKS_PANE_ELEMENT = "wetp:taskpane";
        builder.startElement(TAKS_PANE_ELEMENT);

        builder.writeAttribute("dockstate", DocxEnum.taskPaneDockStateToDocx(taskPane.getDockState()));
        builder.writeAttributeIfTrue("locked", taskPane.isLocked());
        builder.writeAttribute("visibility", taskPane.isVisible());
        builder.writeAttribute("width", taskPane.getWidth());
        builder.writeAttribute("row", taskPane.getRow());

        boolean isStrict = builder.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT;
        builder.writeElementWithAttributes("wetp:webextensionref",
            "xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isStrict),
            "r:id", relToWebExtension);

        builder.endElement(TAKS_PANE_ELEMENT);
    }
}

