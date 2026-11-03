// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.OpcPackagePart;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StreamUtil;
import com.aspose.ms.System.Convert;
import com.aspose.StringUtil;
import com.aspose.DictionaryExtension;
import java.util.Map;
import com.aspose.ms.System.msString;
import java.util.HashMap;


/// <summary>
/// Reads the Word 97-2003 toolbar and keyboard command customizations.
/// More info http://www.documentinteropinitiative.org/additionalinfo/sect1.aspx
/// </summary>
class DocxCustomizationsReader
{
    /// <summary>
    /// The static method that is easy to invoke.
    /// </summary>
    static void read(DocxDocumentReader docReader) throws Exception
    {
        DocxCustomizationsReader customizationsReader = new DocxCustomizationsReader();
        customizationsReader.readCore(docReader);
    }

    /// <summary>
    /// Reads 1.2.1.1.12. tcg (Keyboard and Toolbar Customizations) and all related info.
    /// </summary>
    private void readCore(DocxDocumentReader reader) throws Exception
    {
        // If there is no customization part there is nothing to read.
        OpcPackagePart customizationsPart = reader.getPartByRelationshipType(reader.getDocumentPart(), reader.getRelTypes().getCustomizations());
        if (customizationsPart == null)
            return;

        mDocReader = reader;
        mXmlReader = new DocxXmlReader(customizationsPart, mDocReader.getComplianceInfo());
        while (mXmlReader.readChild("tcg"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"keymaps"*/0:
                case /*"keymapsBad"*/1:
                    // RK We read both of these elements into one keymap collection for the time being.
                    // I do not really understand the difference between them well and have never seen keymapsBad.
                    readKeymaps();
                    break;
                case /*"toolbars"*/2:
                    readToolbars();
                    break;
                case /*"acds"*/3:
                    readAcds();
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }

        buildAllocatedCommands();
        resolveKeyMapsWithAcdNames();
    }

    /// <summary>
    /// String reads 1.2.1.1.9. keymaps (Key Map Customizations) or 1.2.1.1.10. keymapsBad (Key Map Customizations for Mismatched Keyboards).
    /// </summary>
    private void readKeymaps()
    {
        // Check for null because can be invoked twice.
        if (mDocReader.getDocument().getKeyMaps() == null)
            mDocReader.getDocument().setKeyMaps(new ArrayList<KeyMap>());

        String parentName = mXmlReader.getLocalName();
        while (mXmlReader.readChild(parentName))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"keymap"*/4:
                    readKeymap();
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 1.2.1.1.8. keymap (Key Map Entry).
    /// </summary>
    private void readKeymap()
    {
        KeyMap keyMap = new KeyMap();
        mDocReader.getDocument().getKeyMaps().add(keyMap);

        // Read attributes.
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"chmPrimary"*/5:
                    keyMap.ChmPrimary = NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                case /*"chmSecondary"*/6:
                    keyMap.ChmSecondary = NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                case /*"kcmPrimary"*/7:
                    keyMap.KcmPrimary = NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                case /*"kcmSecondary"*/8:
                    keyMap.KcmSecondary = NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                case /*"mask"*/9:
                    if (mXmlReader.getValueAsBool())
                        keyMap.KeymapType = KeyMapType.MASK;
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // Read elements.
        while (mXmlReader.readChild("keymap"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"acd"*/10:
                    // A keymap with an acdName requires resolution into acd index, store it in the temporary list.
                    keyMap.KeymapType = KeyMapType.ALLOCATED_COMMAND;
                    mKeyMapsWithAcd.put(mXmlReader.readAttribute("acdName", ""), keyMap);
                    break;
                case /*"fci"*/11:
                    readKeyMapFci(keyMap);
                    break;
                case /*"macro"*/12:
                    keyMap.KeymapType = KeyMapType.MACRO;
                    keyMap.MacroName = mXmlReader.readAttribute("macroName", "");
                    break;
                case /*"wch"*/13:
                    keyMap.KeymapType = KeyMapType.INSERT_CHARACTER;
                    keyMap.CharacterCode = NrxXmlUtil.hexToInt(mXmlReader.readVal());   // This is an element with the "val" attribute.
                    break;
                case /*"wll"*/14:
                    keyMap.KeymapType = KeyMapType.LEGACY_MACRO;
                    keyMap.MacroName = mXmlReader.readAttribute("macroName", "");
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 1.2.1.1.7. fci (Fixed Command Keyboard Customization Action).
    /// </summary>
    private void readKeyMapFci(KeyMap keyMap)
    {
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"fciIndex"*/15:
                    keyMap.KeymapType = KeyMapType.FIXED_COMMAND;
                    keyMap.FixedCommandIdentifier = (/*FixedCommandIdentifier*/int)NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                case /*"fciName"*/16:
                    keyMap.KeymapType = KeyMapType.FIXED_COMMAND;
                    keyMap.FixedCommandIdentifier = DocxCustomizationsEnum.docxToFixedCommandIdentifier(mXmlReader.getValue());
                    break;
                case /*"swArg"*/17:
                    keyMap.FixedCommandArgument = NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 1.2.1.1.14. toolbars (Legacy Toolbar Customizations).
    /// </summary>
    private void readToolbars()
    {
        while (mXmlReader.readChild("toolbars"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"acdManifest"*/18:
                    readAcdManifest();
                    break;
                case /*"toolbarData"*/19:
                    readToolbarData();
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 1.2.1.1.4. acdManifest (Allocated Command Manifest).
    /// </summary>
    private void readAcdManifest()
    {
        while (mXmlReader.readChild("acdManifest"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"acdEntry"*/20:
                    readAcdEntry();
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 1.2.1.1.3. acdEntry (Allocated Command Manifest Entry).
    /// </summary>
    private void readAcdEntry()
    {
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"acdName"*/21:
                    // Add the acdName to our internal list. We build the data for the model from this later on.
                    msArrayList.add(mAcdManifest, mXmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the attachedToolbars.bin into the model.
    /// </summary>
    private void readToolbarData()
    {
        String relId = mXmlReader.readId();
        String target = mXmlReader.getPart().getRelationshipTarget(relId);
        OpcPackagePart attachedToolbarsPart = mDocReader.fetchPartByName(target);
        mDocReader.getDocument().setAttachedToolbars(StreamUtil.copyStreamToByteArray(attachedToolbarsPart.getStream()));
    }

    /// <summary>
    /// Reads 1.2.1.1.5. acds (Allocated Commands)
    /// </summary>
    private void readAcds()
    {
        while (mXmlReader.readChild("acds"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"acd"*/10:
                    readAcd();
                    break;
                default:
                    mXmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 1.2.1.1.1. acd (Allocated Command).
    /// </summary>
    private void readAcd()
    {
        String acdName = null;
        AllocatedCommand acd = new AllocatedCommand();

        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"acdName"*/21:
                    acdName = mXmlReader.getValue();
                    break;
                case /*"argValue"*/22:
                    acd.ArgValue = Convert.fromBase64String(mXmlReader.getValue());
                    break;
                case /*"fciBasedOn"*/23:
                    acd.FciBasedOn = DocxCustomizationsEnum.docxToFixedCommandIdentifier(mXmlReader.getValue());
                    break;
                case /*"fciIndexBasedOn"*/24:
                    acd.FciBasedOn = (/*FixedCommandIdentifier*/int)NrxXmlUtil.hexToInt(mXmlReader.getValue());
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        // Store the acd in the temporary list. We build the collection of allocated commands for the model later on.
        if (StringUtil.hasChars(acdName))
            mAcds.put(acdName, acd);
    }

    /// <summary>
    /// Builds the proper <see cref="Document.AllocatedCommands"/> collection out of the temporary collections that we have read from DOCX.
    /// </summary>
    private void buildAllocatedCommands()
    {
        if ((mAcdManifest.size() == 0) || (mAcds.size() == 0))
            return;

        Document doc = mDocReader.getDocument();
        doc.setAllocatedCommands(new ArrayList<AllocatedCommand>());

        // The entries in the acdManifest have the correct order for the binary data. Our allocated commands in the model
        // match the binary data too. So loop through the acdManifest and fetch the corresponding acd from the acds collection
        // to build the collection for the model.
        for (String acdName : mAcdManifest)
        {
            AllocatedCommand acd = DictionaryExtension.getValueOrNull(mAcds, acdName);

            // If we cannot find an acd for some reason we should use the "unused" command. These can occur in binary.
            // This code is not really tested as I could not find any documents with unused commands.
            if (acd == null)
                acd = new AllocatedCommand();

            doc.getAllocatedCommands().add(acd);
        }
    }

    /// <summary>
    /// Key mappings that invoke allocated commands (acd) are linked to their acd using an index in the model.
    /// This resolves the acd names read from the DOCX into indexes for the model.
    /// </summary>
    private void resolveKeyMapsWithAcdNames()
    {
        for (Map.Entry<String, KeyMap> entry : mKeyMapsWithAcd.entrySet())
        {
            String acdName = entry.getKey();
            KeyMap keyMap = entry.getValue();
            keyMap.AllocatedCommandIndex = findAcdIndexInManifest(acdName);
        }
    }

    private int findAcdIndexInManifest(String acdName)
    {
        for (int i = 0; i < mAcdManifest.size(); i++)
        {
            if (msString.equals(mAcdManifest.get(i), acdName))
                return i;
        }
        return -1;
    }

    private DocxDocumentReader mDocReader;
    private DocxXmlReader mXmlReader;
    /// <summary>
    /// Contains string acdName values. Created when loading wne:acdManifest.
    /// </summary>
    private /*final*/ ArrayList<String> mAcdManifest = new ArrayList<String>();
    /// <summary>
    /// The key is a string acdName, the value is <see cref="AllocatedCommand"/>. 
    /// Created during loading of wne:acds.
    /// Used together with <see cref="mAcdManifest"/> to build <see cref="Document.AllocatedCommands"/>.
    /// </summary>
    private /*final*/ HashMap<String, AllocatedCommand> mAcds = new HashMap<String, AllocatedCommand>();
    /// <summary>
    /// The key is a string acdName, the value is <see cref="KeyMap"/>.
    /// Created during loading of wne:keymaps for wne:keymap elements that contain wne:acd in them.
    /// Used to resolve <see cref="KeyMap.AllocatedCommandIndex"/> values.
    /// </summary>
    private /*final*/ HashMap<String, KeyMap> mKeyMapsWithAcd = new HashMap<String, KeyMap>();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"keymaps",
		"keymapsBad",
		"toolbars",
		"acds",
		"keymap",
		"chmPrimary",
		"chmSecondary",
		"kcmPrimary",
		"kcmSecondary",
		"mask",
		"acd",
		"fci",
		"macro",
		"wch",
		"wll",
		"fciIndex",
		"fciName",
		"swArg",
		"acdManifest",
		"toolbarData",
		"acdEntry",
		"acdName",
		"argValue",
		"fciBasedOn",
		"fciIndexBasedOn"
	);

}

