// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.FormatterPal;


/// <summary>
/// Reader for all complex Dml diagram types.
/// </summary>
class DmlDiagramComplexTypesReader extends DmlReaderBase
{
    private DmlDiagramComplexTypesReader(NrxXmlReader reader)
    {
        mReader = reader;
    }

    static DmlDiagramString readString(NrxXmlReader reader)
    {
        DmlDiagramString str = new DmlDiagramString();
        str.setLanguage(reader.readAttribute("lang", ""));
        str.setValue(reader.readAttribute(""));
        return str;
    }

    static DmlColorTransformCategory[] readCategories(NrxXmlReader reader)
    {
        DmlDiagramComplexTypesReader complexTypesReader = new DmlDiagramComplexTypesReader(reader);
        ArrayList<DmlColorTransformCategory> categories = new ArrayList<DmlColorTransformCategory>();
        while (reader.readChild("catLst"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"cat"*/0:
                    msArrayList.add(categories, complexTypesReader.readCategory());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }

        return msArrayList.toArray(categories, new DmlColorTransformCategory[0]);
    }

    private DmlColorTransformCategory readCategory()
    {
        DmlColorTransformCategory category = new DmlColorTransformCategory();
        category.setCategoryType(mReader.readAttribute("type", ""));
        category.setPriority(FormatterPal.parseInt(mReader.readAttribute("pri", "0")));
        return category;
    }

    static DmlLayoutVariablePropertySet readLayoutVariableList(NrxXmlReader reader)
    {
        DmlLayoutVariablePropertySet varList = new DmlLayoutVariablePropertySet();
        String tagName = reader.getLocalName();
        while (reader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"orgChart"*/1:
                    varList.setOrgChart(reader.readBoolAttribute(false));
                    break;
                case /*"chMax"*/2:
                    varList.setChildMax(new DmlDiagramNodeCount(reader.readIntAttribute(-1)));
                    break;
                case /*"chPref"*/3:
                    varList.setChildPref(new DmlDiagramNodeCount(reader.readIntAttribute(-1)));
                    break;
                case /*"bulletEnabled"*/4:
                    varList.setBulletEnabled(reader.readBoolAttribute(false));
                    break;
                case /*"dir"*/5:
                    varList.setDirection(DmlDiagramEnum.dmlToDirection(reader.readAttribute("norm")));
                    break;
                case /*"hierBranch"*/6:
                    varList.setHierBranch(DmlDiagramEnum.dmlToHierBranchStyle(reader.readAttribute("norm")));
                    break;
                case /*"animOne"*/7:
                    varList.setAnimOne(DmlDiagramEnum.dmlToAnimOne(reader.readAttribute("one")));
                    break;
                case /*"animLvl"*/8:
                    varList.setAnimLevel(DmlDiagramEnum.dmlToAnimLevel(reader.readAttribute("none")));
                    break;
                case /*"resizeHandles"*/9:
                    varList.setResizeHandles(DmlDiagramEnum.dmlToResizeHandles(reader.readAttribute("rel")));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
        return varList;
    }

    private /*final*/ NrxXmlReader mReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"cat",
		"orgChart",
		"chMax",
		"chPref",
		"bulletEnabled",
		"dir",
		"hierBranch",
		"animOne",
		"animLvl",
		"resizeHandles"
	);

}

