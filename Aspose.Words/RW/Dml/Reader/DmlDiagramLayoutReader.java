// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.AnyXmlReader;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.FormatterPal;


/// <summary>
/// Class used to read DML diagrams layout definition.
/// </summary>
class DmlDiagramLayoutReader extends DmlReaderBase
{
    private DmlDiagramLayoutReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    static DmlDiagramLayout read(DocxDocumentReaderBase reader, String relId) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (!"layoutDef".equals(xmlReader.getLocalName()))
            return null;

        DmlDiagramLayoutReader diagramLayoutReader = new DmlDiagramLayoutReader(reader);

        DmlDiagramLayout layout = new DmlDiagramLayout(relId);
        layout.setUniqueId(xmlReader.readAttribute("uniqueId", ""));
        layout.setMinVersion(xmlReader.readAttribute("minVer", ""));
        layout.setDefaultStyle(xmlReader.readAttribute("defStyle", ""));

        ArrayList<DmlDiagramString> titles = new ArrayList<DmlDiagramString>();
        ArrayList<DmlDiagramString> descriptions = new ArrayList<DmlDiagramString>();
        while (xmlReader.readChild("layoutDef"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"title"*/0:
                    msArrayList.add(titles, DmlDiagramComplexTypesReader.readString(xmlReader));
                    break;
                case /*"desc"*/1:
                    msArrayList.add(descriptions, DmlDiagramComplexTypesReader.readString(xmlReader));
                    break;
                case /*"catLst"*/2:
                    layout.setCategories(DmlDiagramComplexTypesReader.readCategories(xmlReader));
                    break;
                case /*"sampData"*/3:
                    layout.setSampleData(diagramLayoutReader.readSampleData());
                    break;
                case /*"styleData"*/4:
                    layout.setStyleData(diagramLayoutReader.readSampleData());
                    break;
                case /*"clrData"*/5:
                    layout.setColorTransformSampleData(diagramLayoutReader.readSampleData());
                    break;
                case /*"layoutNode"*/6:
                    layout.setLayoutNode(diagramLayoutReader.readLayoutNode());
                    break;
                case /*"extLst"*/7:
                    layout.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
        layout.setTitles(msArrayList.toArray(titles, new DmlDiagramString[0]));
        layout.setDescriptions(msArrayList.toArray(descriptions, new DmlDiagramString[0]));

        return layout;
    }

    private DmlDiagramDataModel readSampleData() throws Exception
    {
        String tagName = getXmlReader().getLocalName();
        boolean useDefault = AnyXmlReader.convertToBool(getXmlReader().readAttribute("useDef", ""));
        getXmlReader().moveToElement();

        DmlDiagramDataModel data = null;
        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"dataModel"*/8:
                    data = DmlDiagramDataReader.read(mDocumentReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        if (data != null)
            data.setUseDefault(useDefault);

        return data;
    }

    private DmlDiagramLayoutNode readLayoutNode() throws Exception
    {
        DmlDiagramLayoutNode node = new DmlDiagramLayoutNode();
        node.setName(getXmlReader().readAttribute("name", ""));
        node.setStyleLabel(getXmlReader().readAttribute("styleLbl", ""));
        node.setChildOrder(DmlDiagramEnum.dmlToChildOrder(getXmlReader().readAttribute("chOrder", "b")));
        node.setMoveWith(getXmlReader().readAttribute("moveWith", ""));

        node.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>());
        while (getXmlReader().readChild("layoutNode"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"alg"*/9:
                    msArrayList.add(node.getContent(), readAlgorithm());
                    break;
                case /*"shape"*/10:
                    msArrayList.add(node.getContent(), readShape());
                    break;
                case /*"presOf"*/11:
                    msArrayList.add(node.getContent(), readPresentation());
                    break;
                case /*"constrLst"*/12:
                    msArrayList.add(node.getContent(), readConstraintList());
                    break;
                case /*"ruleLst"*/13:
                    msArrayList.add(node.getContent(), readRuleList());
                    break;
                case /*"varLst"*/14:
                    msArrayList.add(node.getContent(), DmlDiagramComplexTypesReader.readLayoutVariableList(getXmlReader()));
                    break;
                case /*"forEach"*/15:
                    msArrayList.add(node.getContent(), readForEach());
                    break;
                case /*"layoutNode"*/6:
                    msArrayList.add(node.getContent(), readLayoutNode());
                    break;
                case /*"choose"*/16:
                    msArrayList.add(node.getContent(), readChoose());
                    break;
                case /*"extLst"*/7:
                    node.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return node;
    }

    private DmlAlgorithm readAlgorithm() throws Exception
    {
        DmlAlgorithm algorithm = new DmlAlgorithm();
        algorithm.setType(DmlDiagramEnum.dmlToAlgorithmType(getXmlReader().readAttribute("type", "")));
        algorithm.setRevision(getXmlReader().readIntAttribute("rev", 0));

        while (getXmlReader().readChild("alg"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"param"*/17:
                {
                    DmlParameter parameter = readParameter();
                    // Experiments shows that MW uses only first parameter value of the specified type.
                    if (!algorithm.getParams().containsKey(parameter.getType()))
                        algorithm.getParams().add(parameter.getType(), parameter.getValue());
                    break;
                }
                case /*"extLst"*/7:
                    algorithm.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return algorithm;
    }

    private DmlParameter readParameter()
    {
        DmlParameter parameter = new DmlParameter();
        parameter.setType(getXmlReader().readAttribute("type", ""));
        parameter.setValue(getXmlReader().readAttribute(""));
        return parameter;
    }

    private DmlDiagramShape readShape() throws Exception
    {
        DmlDiagramShape shape = new DmlDiagramShape();
        shape.setType(getXmlReader().readAttribute("type", "none"));
        shape.setRotation(DmlAngle.fromDegrees(getXmlReader().readDoubleAttribute("rot", 0.0)));
        shape.setZOrder(getXmlReader().readIntAttribute("zOrderOff", 0));
        shape.setHideGeometry(getXmlReader().readBoolAttribute("hideGeom", false));
        shape.setPreventTextEditing(getXmlReader().readBoolAttribute("lkTxEntry", false));
        shape.setImagePlaceholder(getXmlReader().readBoolAttribute("blipPhldr", false));

        shape.setBlipReference(getXmlReader().readAttribute("blip", ""));
        if (StringUtil.hasChars(shape.getBlipReference()))
        {
            Debug.writeLine(shape.getBlipReference());
            shape.setBlipData(mDocumentReader.getBinData(shape.getBlipReference()));
        }

        while (getXmlReader().readChild("shape"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"adjLst"*/18:
                    shape.setAdjustList(readAdjustList());
                    break;
                case /*"extLst"*/7:
                    shape.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return shape;
    }

    private DmlShapeAdjust[] readAdjustList()
    {
        ArrayList<DmlShapeAdjust> list = new ArrayList<DmlShapeAdjust>();
        while (getXmlReader().readChild("adjLst"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"adj"*/19:
                    msArrayList.add(list, readAdjust());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return msArrayList.toArray(list, new DmlShapeAdjust[0]);
    }

    private DmlShapeAdjust readAdjust()
    {
        DmlShapeAdjust adjust = new DmlShapeAdjust();
        adjust.setHandleIndex(getXmlReader().readIntAttribute("idx", 0));
        adjust.setValue(getXmlReader().readDoubleAttribute(0.0));
        return adjust;
    }

    private DmlPresentationOf readPresentation()
    {
        DmlPresentationOf presentation = new DmlPresentationOf();
        readIteratorAttributes(presentation.getIteratorAttributes());
        return presentation;
    }

    private void readIteratorAttributes(DmlIteratorAttributes attributes)
    {
        attributes.setAxisType(DmlDiagramEnum.dmlToAxisTypeList(getXmlReader().readAttribute("axis", "none")));
        attributes.setPointType(DmlDiagramEnum.dmlToElementTypeList(getXmlReader().readAttribute("ptType", "all")));
        attributes.setHideLastTransition(DmlDiagramEnum.dmlToBoolList(getXmlReader().readAttribute("hideLastTrans", "true")));
        attributes.setStart(DmlDiagramEnum.dmlToIntList(getXmlReader().readAttribute("st", "1")));
        attributes.setCount(DmlDiagramEnum.dmlToIntList(getXmlReader().readAttribute("cnt", "0")));
        attributes.setStep(DmlDiagramEnum.dmlToIntList(getXmlReader().readAttribute("step", "1")));
    }

    private DmlConstraintList readConstraintList()
    {
        DmlConstraintList result = new DmlConstraintList();

        result.setValues(new ArrayList<DmlConstraint>());
        while (getXmlReader().readChild("constrLst"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"constr"*/20:
                    msArrayList.add(result.getValues(), readConstraint());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return result;
    }

    private DmlConstraint readConstraint()
    {
        DmlConstraint constraint = new DmlConstraint();
        constraint.setOperator(DmlDiagramEnum.dmlToBooleanOperator(getXmlReader().readAttribute("op", "none")));
        constraint.setValue(readDoubleValue("val", 0.0));
        constraint.setFactor(readDoubleValue("fact", 1.0));
        constraint.setConstraintAttributes(readConstraintAttributes());
        constraint.setConstraintReferenceAttributes(readConstraintReferenceAttributes());
        return constraint;
    }

    private DmlConstraintAttributes readConstraintReferenceAttributes()
    {
        /*DmlConstraintType*/int type = DmlDiagramEnum.dmlToConstraintType(getXmlReader().readAttribute("refType", "none"));
        /*DmlConstraintRelationship*/int forAxis = DmlDiagramEnum.dmlToConstraintRelationship(getXmlReader().readAttribute("refFor", "self"));
        String forName = getXmlReader().readAttribute("refForName", "");
        /*DmlElementType*/int pointType = DmlDiagramEnum.dmlToElementType(getXmlReader().readAttribute("refPtType", "all"));
        return new DmlConstraintAttributes(type, forAxis, forName, pointType);
    }

    private DmlConstraintAttributes readConstraintAttributes()
    {
        /*DmlConstraintType*/int type = DmlDiagramEnum.dmlToConstraintType(getXmlReader().readAttribute("type", ""));
        /*DmlConstraintRelationship*/int forAxis = DmlDiagramEnum.dmlToConstraintRelationship(getXmlReader().readAttribute("for", "self"));
        String forName = getXmlReader().readAttribute("forName", "");
        /*DmlElementType*/int pointType = DmlDiagramEnum.dmlToElementType(getXmlReader().readAttribute("ptType", "all"));
        return new DmlConstraintAttributes(type, forAxis, forName, pointType);
    }

    private DmlNumericRuleList readRuleList()
    {
        DmlNumericRuleList result = new DmlNumericRuleList();

        result.setValues(new ArrayList<DmlNumericRule>());
        while (getXmlReader().readChild("ruleLst"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"rule"*/21:
                    msArrayList.add(result.getValues(), readRule());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return result;
    }

    private DmlNumericRule readRule()
    {
        DmlNumericRule rule = new DmlNumericRule();
        rule.setValue(readDoubleValue("val", Double.NaN));
        rule.setFactor(readDoubleValue("fact", Double.NaN));
        rule.setMax(readDoubleValue("max", Double.NaN));
        rule.setConstraintAttributes(readConstraintAttributes());
        return rule;
    }

    private double readDoubleValue(String name, double defaultValue)
    {
        String val = getXmlReader().readAttribute(name, null);

        if (val == null)
            return defaultValue;

        if (msString.equals(val, "inf", StringComparison.ORDINAL_IGNORE_CASE))
            return Double.POSITIVE_INFINITY;
        if (msString.equals(val, "-inf", StringComparison.ORDINAL_IGNORE_CASE))
            return Double.NEGATIVE_INFINITY;
        if (msString.equals(val, "nan", StringComparison.ORDINAL_IGNORE_CASE))
            return Double.NaN;

        return FormatterPal.tryParseDoubleInvariant(val);
    }

    private DmlForEach readForEach() throws Exception
    {
        DmlForEach forEach = new DmlForEach();
        forEach.setName(getXmlReader().readAttribute("name", ""));
        forEach.setReference(getXmlReader().readAttribute("ref", ""));
        readIteratorAttributes(forEach.getIteratorAttributes());

        forEach.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>());
        while (getXmlReader().readChild("forEach"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"alg"*/9:
                    msArrayList.add(forEach.getContent(), readAlgorithm());
                    break;
                case /*"shape"*/10:
                    msArrayList.add(forEach.getContent(), readShape());
                    break;
                case /*"presOf"*/11:
                    msArrayList.add(forEach.getContent(), readPresentation());
                    break;
                case /*"constrLst"*/12:
                    msArrayList.add(forEach.getContent(), readConstraintList());
                    break;
                case /*"ruleLst"*/13:
                    msArrayList.add(forEach.getContent(), readRuleList());
                    break;
                case /*"forEach"*/15:
                    msArrayList.add(forEach.getContent(), readForEach());
                    break;
                case /*"layoutNode"*/6:
                    msArrayList.add(forEach.getContent(), readLayoutNode());
                    break;
                case /*"choose"*/16:
                    msArrayList.add(forEach.getContent(), readChoose());
                    break;
                case /*"extLst"*/7:
                    forEach.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return forEach;
    }

    private DmlChoose readChoose() throws Exception
    {
        DmlChoose choose = new DmlChoose();
        choose.setName(getXmlReader().readAttribute("name", ""));

        choose.setIf(new ArrayList<DmlWhen>());
        while (getXmlReader().readChild("choose"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"if"*/22:
                    msArrayList.add(choose.getIf(), readWhen());
                    break;
                case /*"else"*/23:
                    choose.setElse(readOtherwise());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return choose;
    }

    private DmlWhen readWhen() throws Exception
    {
        DmlWhen when = new DmlWhen();
        when.setName(getXmlReader().readAttribute("name", ""));
        readIteratorAttributes(when.getIteratorAttributes());
        when.setFunction(DmlDiagramEnum.dmlToFunctionType(getXmlReader().readAttribute("func", "")));
        when.setArgument(DmlDiagramEnum.dmlToVariableType(getXmlReader().readAttribute("arg", "none")));
        when.setOperator(DmlDiagramEnum.dmlToFunctionOperator(getXmlReader().readAttribute("op", "")));
        when.setValue(getXmlReader().readAttribute(""));

        when.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>());
        while (getXmlReader().readChild("if"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"alg"*/9:
                    msArrayList.add(when.getContent(), readAlgorithm());
                    break;
                case /*"shape"*/10:
                    msArrayList.add(when.getContent(), readShape());
                    break;
                case /*"presOf"*/11:
                    msArrayList.add(when.getContent(), readPresentation());
                    break;
                case /*"constrLst"*/12:
                    msArrayList.add(when.getContent(), readConstraintList());
                    break;
                case /*"ruleLst"*/13:
                    msArrayList.add(when.getContent(), readRuleList());
                    break;
                case /*"forEach"*/15:
                    msArrayList.add(when.getContent(), readForEach());
                    break;
                case /*"layoutNode"*/6:
                    msArrayList.add(when.getContent(), readLayoutNode());
                    break;
                case /*"choose"*/16:
                    msArrayList.add(when.getContent(), readChoose());
                    break;
                case /*"extLst"*/7:
                    when.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return when;
    }

    private DmlOtherwise readOtherwise() throws Exception
    {
        DmlOtherwise otherwise = new DmlOtherwise();
        otherwise.setName(getXmlReader().readAttribute("name", ""));

        otherwise.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>());
        while (getXmlReader().readChild("else"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"alg"*/9:
                    msArrayList.add(otherwise.getContent(), readAlgorithm());
                    break;
                case /*"shape"*/10:
                    msArrayList.add(otherwise.getContent(), readShape());
                    break;
                case /*"presOf"*/11:
                    msArrayList.add(otherwise.getContent(), readPresentation());
                    break;
                case /*"constrLst"*/12:
                    msArrayList.add(otherwise.getContent(), readConstraintList());
                    break;
                case /*"ruleLst"*/13:
                    msArrayList.add(otherwise.getContent(), readRuleList());
                    break;
                case /*"forEach"*/15:
                    msArrayList.add(otherwise.getContent(), readForEach());
                    break;
                case /*"layoutNode"*/6:
                    msArrayList.add(otherwise.getContent(), readLayoutNode());
                    break;
                case /*"choose"*/16:
                    msArrayList.add(otherwise.getContent(), readChoose());
                    break;
                case /*"extLst"*/7:
                    otherwise.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        return otherwise;
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"title",
		"desc",
		"catLst",
		"sampData",
		"styleData",
		"clrData",
		"layoutNode",
		"extLst",
		"dataModel",
		"alg",
		"shape",
		"presOf",
		"constrLst",
		"ruleLst",
		"varLst",
		"forEach",
		"choose",
		"param",
		"adjLst",
		"adj",
		"constr",
		"rule",
		"if",
		"else"
	);

}

