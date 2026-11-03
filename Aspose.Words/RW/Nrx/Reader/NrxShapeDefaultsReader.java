// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Map;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.msString;


/// <summary>
/// Implement reading of 'shapeDefaults' and 'hdrShapeDefaults' elements.
/// </summary>
/*static*/ class NrxShapeDefaultsReader
{
	/* Simulation of static class by using private constructor */
	private NrxShapeDefaultsReader()
	{}

    static void read(NrxXmlReader xmlReader, Map<String, ConnectorRule> connectors)
    {
        String tagName = xmlReader.getLocalName();

        while (xmlReader.readChild(tagName))
        {
            if ("shapelayout".equals(xmlReader.getLocalName()))
                readShapeLayout(xmlReader, connectors);
            else
                xmlReader.ignoreElement();
        }
    }

    private static void readShapeLayout(NrxXmlReader xmlReader, Map<String, ConnectorRule> connectors)
    {
        while (xmlReader.readChild("shapelayout"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rules"*/0:
                    readRules(xmlReader, connectors);
                    break;

                case /*"idmap"*/1:
                    break;

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readRules(NrxXmlReader xmlReader, Map<String, ConnectorRule> connectors)
    {
        while (xmlReader.readChild("rules"))
        {
            if ("r".equals(xmlReader.getLocalName()))
                readRule(xmlReader, connectors);
            else
                xmlReader.ignoreElement();
        }
    }

    private static void readRule(NrxXmlReader xmlReader, Map<String, ConnectorRule> connectors)
    {
        String id = "";
        ConnectorRule rule = new ConnectorRule();
        while (xmlReader.moveToNextAttribute())
        {
            if ("idref".equals(xmlReader.getLocalName()))
                // Get unparsed as we have mShapeNamesMap already that maps shapes by name.
                id = xmlReader.getValue();
        }

        while (xmlReader.readChild("r"))
        {
            if ("proxy".equals(xmlReader.getLocalName()))
                readProxy(xmlReader, rule);
            else
                xmlReader.ignoreElement();
        }

        if (StringUtil.hasChars(rule.ShapeAIdRaw) && StringUtil.hasChars(rule.ShapeBIdRaw))
        {
            // WORDSNET-11580 Ignore duplicated connector rules.
            if (!connectors.containsKey(id))
                msDictionary.add(connectors, id, rule);
        }
    }

    private static void readProxy(NrxXmlReader xmlReader, ConnectorRule rule)
    {
        boolean isStart = false;
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"start"*/2:
                    isStart = true;
                    break;

                case /*"end"*/3:
                    isStart = false;
                    break;

                case /*"idref"*/4:
                    if (isStart)
                        rule.ShapeAIdRaw = xmlReader.getValue();
                    else
                        rule.ShapeBIdRaw = xmlReader.getValue();
                    break;

                case /*"connectloc"*/5:
                    if (isStart)
                        rule.ShapeASite = xmlReader.getValueAsInt();
                    else
                        rule.ShapeBSite = xmlReader.getValueAsInt();
                    break;

                default:
                    warnNotSupported(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private static void warnNotSupported(NrxXmlReader reader)
    {
        reader.warn(
            WarningType.MINOR_FORMATTING_LOSS,
            WarningSource.DRAWING_ML,
            msString.format(WarningStrings.NOT_SUPPORTED_TAG, reader.getLocalName()));
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"rules",
		"idmap",
		"start",
		"end",
		"idref",
		"connectloc"
	);

}

