// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcPackagePart;
import com.aspose.OpcRelationship;


/// <summary>
/// Provides ability to read a collection of task pane add-ins ("2.2.9 CT_OsfTaskpanes" of the MS-OWEXML spec).
/// </summary>
class DocxTaskPaneAddinsReader
{
    /// <summary>
    /// Ctr.
    /// </summary>
    private DocxTaskPaneAddinsReader(DocxDocumentReader reader)
    {
        mDocument = reader.getDocument();
        mReader = reader;
    }

    /// <summary>
    /// Reads task pane add-ins.
    /// </summary>
    static void read(DocxDocumentReader reader) throws Exception
    {
        DocxTaskPaneAddinsReader taskPaneReader = new DocxTaskPaneAddinsReader(reader);
        taskPaneReader.read();
    }

    /// <summary>
    /// Reads "2.2.9 CT_OsfTaskpanes" element.
    /// </summary>
    private void read() throws Exception
    {
        DocxXmlReader taskPanesReader = mReader.switchToPartReaderByRelType(null, mReader.getRelTypes().getWebExtensionTaskPanes());
        if (taskPanesReader == null)
            return;

        TaskPaneCollection taskPanes = mDocument.getWebExtensionTaskPanes();

        while (taskPanesReader.readChild("taskpanes")) // wetp:taskpanes
        {
            switch (gStringSwitchMap.of(taskPanesReader.getLocalName()))
            {
                case /*"taskpane"*/0: // wetp:taskpane
                {
                    TaskPane taskPane = readTaskPane(taskPanesReader);
                    if (taskPane != null)
                        taskPanes.add(taskPane);
                    break;
                }
                default:
                    taskPanesReader.ignoreElement();
                    break;
            }
        }

        mReader.restorePartReader();
    }

    /// <summary>
    /// Reads "2.2.8 CT_OsfTaskpane" element.
    /// </summary>
    private TaskPane readTaskPane(DocxXmlReader xmlReader) throws Exception
    {
        TaskPane taskPane = new TaskPane();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"row"*/1:
                    // Actually it is "unsignedInt" attribute according to spec.
                    taskPane.setRow(xmlReader.getValueAsInt());
                    break;
                case /*"width"*/2:
                    taskPane.setWidth(xmlReader.getValueAsDouble());
                    break;
                case /*"locked"*/3:
                    taskPane.isLocked(xmlReader.getValueAsBool());
                    break;
                case /*"visibility"*/4:
                    taskPane.isVisible(xmlReader.getValueAsBool());
                    break;
                case /*"dockstate"*/5:
                    taskPane.setDockState(DocxEnum.docxToTaskPaneDockState(xmlReader.getValue().toLowerCase()));
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        while (xmlReader.readChild("taskpane")) // wetp:taskpane
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"webextensionref"*/6:
                {
                    String relId = xmlReader.readAttribute("id", "");
                    readWebExtensionReference(relId, xmlReader.getPart(), taskPane.getWebExtension());
                    break;
                }
                default:
                    // For example "extLst", which may be ignored according to spec.
                    xmlReader.ignoreElement();
                    break;
            }
        }

        return taskPane;
    }

    /// <summary>
    /// Reads content which referenced by "2.2.10 CT_WebExtensionPartRef" element.
    /// </summary>
    private void readWebExtensionReference(String relId, OpcPackagePart part, WebExtension extension) throws Exception
    {
        OpcRelationship relationship = part.getRels().getById(relId);
        if (relationship.isExternal())
        {
            mReader.warnUnexpected(WarningStrings.TASK_PANE_ADDIN_HAS_UNEXPECTED_REFERENCE_TYPE);
            return;
        }

        DocxWebExtensionReader.readWebExtension(relId, mReader, extension);
    }

    private /*final*/ Document mDocument;
    private /*final*/ DocxDocumentReader mReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"taskpane",
		"row",
		"width",
		"locked",
		"visibility",
		"dockstate",
		"webextensionref"
	);

}

