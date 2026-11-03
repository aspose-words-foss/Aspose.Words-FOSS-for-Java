// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.OpcPackagePart;
import com.aspose.words.ref.Ref;


/// <summary>
/// Writes the customizations.xml and attachedToolbars.bin parts.
/// See http://www.documentinteropinitiative.org/additionalinfo/sect1.aspx for details.
/// </summary>
class DocxCustomizationsWriter
{
    static void write(DocxDocumentWriter writer) throws Exception
    {
        if (!writer.getDocument().hasCustomizations())
            return;

        DocxBuilder builder = writer.createChildPartAndBuilder(
            "customizations.xml", DocxContentType.CUSTOMIZATIONS, writer.getRelTypes().getCustomizations());

        builder.startDocument("wne:tcg");
        builder.writeAttributeString("xmlns:r", writer.getDocxNamespaces().getRelationships());
        builder.writeAttributeString("xmlns:wne", writer.getDocxNamespaces().getWmlBeta());

        writeKeyMaps(writer, builder);
        writeToolbars(writer, builder);
        writeAcds(writer, builder);

        builder.endDocument(); 
    }

    private static void writeKeyMaps(DocxDocumentWriter writer, DocxBuilder builder)
    {
        Debug.assertTrue(builder != null);

        Document doc = writer.getDocument();
        if (!doc.hasKeyMaps())
            return;

        builder.startElement("wne:keymaps");

        for (KeyMap keyMap : doc.getKeyMaps())
        {
            if (keyMap.KeymapType == KeyMapType.NONE)
                continue;

            builder.startElement("wne:keymap");    

            if (keyMap.KeymapType == KeyMapType.MASK)
                builder.writeAttribute("wne:mask", 1);

            writeKeyMapKey(builder, "wne:chmPrimary", keyMap.ChmPrimary);
            writeKeyMapKey(builder, "wne:chmSecondary", keyMap.ChmSecondary);
            writeKeyMapKey(builder, "wne:kcmPrimary", keyMap.KcmPrimary);
            writeKeyMapKey(builder, "wne:kcmSecondary", keyMap.KcmSecondary);

            switch (keyMap.KeymapType)
            {
                case KeyMapType.NONE:
                case KeyMapType.MASK:
                    // Do nothing, handled above.
                    break;
                case KeyMapType.ALLOCATED_COMMAND:
                {
                    builder.startElement("wne:acd");
                    builder.writeAttribute("wne:acdName", formatAcdName(keyMap.AllocatedCommandIndex));
                    builder.endElement();   // wne:acd
                    break;
                }
                case KeyMapType.FIXED_COMMAND:
                {
                    builder.startElement("wne:fci");
                    int fciIndex = (int)keyMap.FixedCommandIdentifier;
                    builder.writeAttribute("wne:fciIndex", FormatterPal.intToStrX4(fciIndex));
                    builder.writeAttribute("wne:swArg", FormatterPal.intToStrX4(keyMap.FixedCommandArgument));
                    builder.endElement();   // wne:fci
                    break;
                }
                case KeyMapType.MACRO:
                {
                    builder.startElement("wne:macro");
                    builder.writeAttribute("wne:macroName", keyMap.MacroName);
                    builder.endElement();   // wne:macro
                    break;
                }
                case KeyMapType.INSERT_CHARACTER:
                {
                    builder.startElement("wne:wch");
                    builder.writeAttribute("wne:val", FormatterPal.intToStrX8(keyMap.CharacterCode));
                    builder.endElement();   // wne:wch
                    break;
                }
                case KeyMapType.LEGACY_MACRO:
                {
                    builder.startElement("wne:wll");
                    builder.writeAttribute("wne:macroName", keyMap.MacroName);
                    builder.endElement();   // wne:wll
                    break;
                }
                default:
                    throw new IllegalStateException("Unknown keymap type.");
            }

            builder.endElement();// wne:keymap
        }

        builder.endElement();   // wne:keymaps
    }

    private static void writeKeyMapKey(NrxXmlBuilder xmlBuilder, String name, int value)
    {
        if (value != 0)
            xmlBuilder.writeAttribute(name, FormatterPal.intToStrX4(value));
    }

    private static String formatAcdName(int acdIndex)
    {
        return msString.format("acd{0}", acdIndex);
    }

    private static void writeToolbars(DocxDocumentWriter writer, DocxBuilder builder) throws Exception
    {
        Debug.assertTrue(builder != null);

        Document doc = writer.getDocument();
        if (!doc.hasAllocatedCommands() && !doc.hasAttachedToolbars())
            return;

        builder.startElement("wne:toolbars");

        if (doc.hasAllocatedCommands())
        {
            builder.startElement("wne:acdManifest");
            for (int i = 0; i < doc.getAllocatedCommands().size(); i++)
            {
                builder.startElement("wne:acdEntry");
                builder.writeAttribute("wne:acdName", formatAcdName(i));
                builder.endElement();   // wne:acdEntry
            }
            builder.endElement();   // wne:acdManifest
        }

        if (doc.hasAttachedToolbars())
        {
            String relId = null;
            Ref<String> referenceToRelId = new Ref<String>(relId);
            OpcPackagePart attachedToolbarsPart = writer.getPackage().createChildPart(
                builder.getPart(), "attachedToolbars.bin", DocxContentType.ATTACHED_TOOLBARS, writer.getRelTypes().getAttachedToolbars(), /*out*/ referenceToRelId);
            relId = referenceToRelId.get();
            attachedToolbarsPart.getStream().write(doc.getAttachedToolbars(), 0, doc.getAttachedToolbars().length);
            builder.writeRelationshipId("wne:toolbarData", relId);
        }

        builder.endElement();   // wne:toolbars
    }

    private static void writeAcds(DocxDocumentWriter writer, NrxXmlBuilder builder)
    {
        Document doc = writer.getDocument();
        if (!doc.hasAllocatedCommands())
            return;

        builder.startElement("wne:acds");
        for (int i = 0; i < doc.getAllocatedCommands().size(); i++)
        {
            AllocatedCommand acd = (AllocatedCommand)doc.getAllocatedCommands().get(i);
            if (acd.FciBasedOn != FixedCommandIdentifier.NONE)
            {
                builder.startElement("wne:acd");
                builder.writeAttribute("wne:argValue", acd.ArgValue);
                builder.writeAttribute("wne:acdName", formatAcdName(i));
                int fciIndexBasedOn = (int)acd.FciBasedOn;
                builder.writeAttribute("wne:fciIndexBasedOn", FormatterPal.intToStrX4(fciIndexBasedOn));
                builder.endElement();   // wne:acd
            }
        }
        builder.endElement();   
    }
}

