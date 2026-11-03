// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ms.System.Guid;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Utility functions for working with diagram simple types.
/// </summary>
/*static*/ class DmlDiagramEnum
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramEnum()
	{}

    static boolean[] dmlToBoolList(String value)
    {
        String[] listItems = msString.split(value, LIST_SEPARATOR);

        boolean[] result = new boolean[listItems.length];
        for (int i = 0; i < listItems.length; i++)
            result[i] = AnyXmlReader.convertToBool(listItems[i]);

        return result;
    }

    static String boolListToDml(boolean[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (boolean b : value)
        {
            msStringBuilder.append(builder, b ? "true" : "false");
            builder.append(LIST_SEPARATOR);
        }

        return msString.trim(builder.toString());
    }

    static int[] dmlToIntList(String value)
    {
        String[] listItems = msString.split(value, LIST_SEPARATOR);

        int[] result = new int[listItems.length];
        for (int i = 0; i < listItems.length; i++)
            result[i] = FormatterPal.xmlToInt(listItems[i]);

        return result;
    }

    static String intListToDml(int[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (int i : value)
        {
            builder.append(i);
            builder.append(LIST_SEPARATOR);
        }

        return msString.trim(builder.toString());
    }

    static DmlModelId dmlToModelId(String value)
    {
        if (!StringUtil.hasChars(value))
            return null;

        int intValue = FormatterPal.tryParseInt(value);
        if(intValue == Integer.MIN_VALUE)
            return new DmlModelIdGuid(new Guid(value));
        return new DmlModelIdInteger(intValue);
    }

    static String modelIdToDml(DmlModelId value)
    {
        DmlModelIdInteger intId = ms.as(value, DmlModelIdInteger.class);
        if (intId != null)
            return intId.toString();

        DmlModelIdGuid guidId = ms.as(value, DmlModelIdGuid.class);
        if (guidId != null)
            return "{" + guidId.toString().toUpperCase() + "}";

        return "";
    }

    static /*DmlConnectionType*/int dmlToConnectionType(String value)
    {
        return (/*DmlConnectionType*/int)G_CONNECTION_TYPE_MAP.getValue(value, (int)DmlConnectionType.PARENT_OF);
    }

    static String connectionTypeToDml(/*DmlConnectionType*/int value)
    {
        return G_CONNECTION_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlPointType*/int dmlToPointType(String value)
    {
        return (/*DmlPointType*/int)G_POINT_TYPE_MAP.getValue(value, (int)DmlPointType.NODE);
    }

    static String pointTypeToDml(/*DmlPointType*/int value)
    {
        return G_POINT_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlChildOrder*/int dmlToChildOrder(String value)
    {
        return (/*DmlChildOrder*/int)G_CHILD_ORDER_MAP.getValue(value, (int)DmlChildOrder.BOTTOM);
    }

    static String childOrderToDml(/*DmlChildOrder*/int value)
    {
        return G_CHILD_ORDER_MAP.getValue((int)value, "");
    }

    static /*DmlAlgorithmType*/int dmlToAlgorithmType(String value)
    {
        return (/*DmlAlgorithmType*/int)G_ALGORITHM_TYPE_MAP.getValue(value, 0);
    }

    static String algorithmTypeToDml(/*DmlAlgorithmType*/int value)
    {
        return G_ALGORITHM_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlAxisType*/int dmlToAxisType(String value)
    {
        return (/*DmlAxisType*/int)G_AXIS_TYPE_MAP.getValue(value, (int)DmlAxisType.NONE);
    }

    static String axisTypeToDml(/*DmlAxisType*/int value)
    {
        return G_AXIS_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlAxisType*/int[] dmlToAxisTypeList(String value)
    {
        String[] listItems = msString.split(value, LIST_SEPARATOR);

        /*DmlAxisType*/int[] result = new /*DmlAxisType*/int[listItems.length];
        for (int i = 0; i < listItems.length; i++)
            result[i] = dmlToAxisType(listItems[i]);

        return result;
    }

    static String axisTypeListToDml(/*DmlAxisType*/int[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (/*DmlAxisType*/int type : value)
        {
            msStringBuilder.append(builder, axisTypeToDml(type));
            builder.append(LIST_SEPARATOR);
        }

        return msString.trim(builder.toString());
    }

    static /*DmlElementType*/int dmlToElementType(String value)
    {
        return (/*DmlElementType*/int)G_ELEMENT_TYPE_MAP.getValue(value, (int)DmlElementType.ALL);
    }

    static String elementTypeToDml(/*DmlElementType*/int value)
    {
        return G_ELEMENT_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlElementType*/int[] dmlToElementTypeList(String value)
    {
        String[] listItems = msString.split(value, LIST_SEPARATOR);

        /*DmlElementType*/int[] result = new /*DmlElementType*/int[listItems.length];
        for (int i = 0; i < listItems.length; i++)
            result[i] = dmlToElementType(listItems[i]);

        return result;
    }

    static String elementTypeListToDml(/*DmlElementType*/int[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (/*DmlElementType*/int type : value)
        {
            msStringBuilder.append(builder, elementTypeToDml(type));
            builder.append(LIST_SEPARATOR);
        }

        return msString.trim(builder.toString());
    }

    static /*DmlBooleanOperator*/int dmlToBooleanOperator(String value)
    {
        return (/*DmlBooleanOperator*/int)G_BOOLEAN_OPERATOR_MAP.getValue(value, (int)DmlBooleanOperator.NONE);
    }

    static String booleanOperatorToDml(/*DmlBooleanOperator*/int value)
    {
        return G_BOOLEAN_OPERATOR_MAP.getValue((int)value, "");
    }

    static /*DmlConstraintRelationship*/int dmlToConstraintRelationship(String value)
    {
        return (/*DmlConstraintRelationship*/int)G_CONSTRAINT_RELATIONSHIP_MAP.getValue(value, (int)DmlConstraintRelationship.SELF);
    }

    static String constraintRelationshipToDml(/*DmlConstraintRelationship*/int value)
    {
        return G_CONSTRAINT_RELATIONSHIP_MAP.getValue((int)value, "");
    }

    static /*DmlDiagramDirection*/int dmlToDirection(String value)
    {
        return (/*DmlDiagramDirection*/int)G_DIRECTION_MAP.getValue(value, (int)DmlDiagramDirection.NORMAL);
    }

    static String directionToDml(/*DmlDiagramDirection*/int value)
    {
        return G_DIRECTION_MAP.getValue((int)value, "");
    }

    static boolean isDirection(String value)
    {
        return !StringToIntBidirectionalMap.isNullSubstitute(G_DIRECTION_MAP.tryGetValue(value));
    }

    static /*DmlHierBranchStyle*/int dmlToHierBranchStyle(String value)
    {
        return (/*DmlHierBranchStyle*/int)G_HIER_BRANCH_STYLE_MAP.getValue(value, (int)DmlHierBranchStyle.STANDARD);
    }

    static String hierBranchStyleToDml(/*DmlHierBranchStyle*/int value)
    {
        return G_HIER_BRANCH_STYLE_MAP.getValue((int)value, "");
    }

    static boolean isHierBranchStyle(String value)
    {
        return !StringToIntBidirectionalMap.isNullSubstitute(G_HIER_BRANCH_STYLE_MAP.tryGetValue(value));
    }

    static /*DmlAnimOne*/int dmlToAnimOne(String value)
    {
        return (/*DmlAnimOne*/int)G_ANIM_ONE.getValue(value, (int)DmlAnimOne.ONE);
    }

    static String animOneToDml(/*DmlAnimOne*/int value)
    {
        return G_ANIM_ONE.getValue((int)value, "");
    }

    static boolean isAnimOne(String value)
    {
        return !StringToIntBidirectionalMap.isNullSubstitute(G_ANIM_ONE.tryGetValue(value));
    }

    static /*DmlAnimLevel*/int dmlToAnimLevel(String value)
    {
        return (/*DmlAnimLevel*/int)G_ANIM_LEVEL_MAP.getValue(value, (int)DmlAnimLevel.NONE);
    }

    static String animLevelToDml(/*DmlAnimLevel*/int value)
    {
        return G_ANIM_LEVEL_MAP.getValue((int)value, "");
    }

    static boolean isAnimLevel(String value)
    {
        return !StringToIntBidirectionalMap.isNullSubstitute(G_ANIM_LEVEL_MAP.tryGetValue(value));
    }

    static /*DmlResizeHandles*/int dmlToResizeHandles(String value)
    {
        return (/*DmlResizeHandles*/int)G_RESIZE_HANDLES_MAP.getValue(value, (int)DmlResizeHandles.RELATIVE);
    }

    static String resizeHandlesToDml(/*DmlResizeHandles*/int value)
    {
        return G_RESIZE_HANDLES_MAP.getValue((int)value, "");
    }

    static boolean isResizeHandles(String value)
    {
        return !StringToIntBidirectionalMap.isNullSubstitute(G_RESIZE_HANDLES_MAP.tryGetValue(value));
    }

    static /*DmlFunctionType*/int dmlToFunctionType(String value)
    {
        return (/*DmlFunctionType*/int)G_FUNCTION_TYPE_MAP.getValue(value, 0);
    }

    static String functionTypeToDml(/*DmlFunctionType*/int value)
    {
        return G_FUNCTION_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlVariableType*/int dmlToVariableType(String value)
    {
        return (/*DmlVariableType*/int)G_VARIABLE_TYPE_MAP.getValue(value, (int)DmlVariableType.NONE);
    }

    static String variableTypeToDml(/*DmlVariableType*/int value)
    {
        return G_VARIABLE_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlFunctionOperator*/int dmlToFunctionOperator(String value)
    {
        return (/*DmlFunctionOperator*/int)G_FUNCTION_OPERATOR_MAP.getValue(value, 0);
    }

    static String functionOperatorToDml(/*DmlFunctionOperator*/int value)
    {
        return G_FUNCTION_OPERATOR_MAP.getValue((int)value, "");
    }

    static /*DmlColorApplicationMethod*/int dmlToColorApplicationMethod(String value)
    {
        return (/*DmlColorApplicationMethod*/int)G_COLOR_APPLICATION_METHOD_MAP.getValue(value, (int)DmlColorApplicationMethod.SPAN);
    }

    static String colorApplicationMethodToDml(/*DmlColorApplicationMethod*/int value)
    {
        return G_COLOR_APPLICATION_METHOD_MAP.getValue((int)value, "");
    }

    static /*DmlHueDirection*/int dmlToHueDirection(String value)
    {
        return (/*DmlHueDirection*/int)G_HUE_DIRECTION_MAP.getValue(value, (int)DmlHueDirection.CLOCKWISE);
    }

    static String hueDirectionToDml(/*DmlHueDirection*/int value)
    {
        return G_HUE_DIRECTION_MAP.getValue((int)value, "");
    }

    static /*DmlNodeHorizontalAlignment*/int dmlToNodeHorizontalAlignment(String value)
    {
        return (/*DmlNodeHorizontalAlignment*/int)G_NODE_HORIZONTAL_ALIGNMENT_MAP.getValue(value, 0);
    }

    static String nodeHorizontalAlignmentToDml(/*DmlNodeHorizontalAlignment*/int value)
    {
        return G_NODE_HORIZONTAL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*DmlNodeVerticalAlignment*/int dmlToNodeVerticalAlignment(String value)
    {
        return (/*DmlNodeVerticalAlignment*/int)G_NODE_VERTICAL_ALIGNMENT_MAP.getValue(value, 0);
    }

    static String nodeVerticalAlignmentToDml(/*DmlNodeVerticalAlignment*/int value)
    {
        return G_NODE_VERTICAL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*DmlFallbackDimension*/int dmlToFallbackDimension(String value)
    {
        return (/*DmlFallbackDimension*/int)G_FALLBACK_DIMENSIONS_MAP.getValue(value, 0);
    }

    static String fallbackDimensionToDml(/*DmlFallbackDimension*/int value)
    {
        return G_FALLBACK_DIMENSIONS_MAP.getValue((int)value, "");
    }

    static /*DmlDiagramHorizontalAlignment*/int dmlToDiagramHorizontalAlignment(String value)
    {
        return (/*DmlDiagramHorizontalAlignment*/int)G_DIAGRAM_HORIZONTAL_ALIGNMENT_MAP.getValue(value, (int)DmlDiagramHorizontalAlignment.NONE);
    }

    static String diagramHorizontalAlignmentToDml(/*DmlDiagramHorizontalAlignment*/int value)
    {
        return G_DIAGRAM_HORIZONTAL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    public static /*DmlVerticalAlignment*/int dmlToVerticalAlignment(String value)
    {
        return (/*DmlVerticalAlignment*/int)G_VERTICAL_ALIGNMENT_MAP.getValue(value, (int)DmlVerticalAlignment.NONE);
    }

    static String verticalAlignmentToDml(/*DmlVerticalAlignment*/int value)
    {
        return G_VERTICAL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*DmlBreakpoint*/int dmlToBreakpoint(String value)
    {
        return (/*DmlBreakpoint*/int)G_BREAKPOINT_MAP.getValue(value, (int)DmlBreakpoint.END_OF_CANVAS);
    }

    static /*DmlOffset*/int dmlToOffset(String value)
    {
        return (/*DmlOffset*/int)G_OFFSET_MAP.getValue(value, (int)DmlOffset.OFFSET);
    }

    static /*DmlContinueDirection*/int dmlToContinueDirection(String value)
    {
        return (/*DmlContinueDirection*/int)G_CONTINUE_DIRECTION.getValue(value, (int)DmlContinueDirection.SAME_DIRECTION);
    }

    static /*DmlAutoTextRotation*/int dmlToAutoTextRotation(String value)
    {
        return (/*DmlAutoTextRotation*/int)G_AUTO_TEXT_ROTATION.getValue(value, (int)DmlAutoTextRotation.UPRIGHT);
    }

    static /*DmlDiagramTextAlignment*/int dmlToDiagramTextAlignment(String value)
    {
        return (/*DmlDiagramTextAlignment*/int)G_DIAGRAM_TEXT_ALIGNMENT.getValue(value, (int)DmlDiagramTextAlignment.CENTER);
    }

    static /*DmlTextAnchorHorizontal*/int dmlToTextAnchorHorizontal(String value)
    {
        return (/*DmlTextAnchorHorizontal*/int) G_TEXT_ANCHOR_HORIZONTAL.getValue(value, (int) DmlTextAnchorHorizontal.NONE);
    }

    static /*DmlTextAnchorVertical*/int dmlToTextAnchorVertical(String value)
    {
        return (/*DmlTextAnchorVertical*/int) G_TEXT_ANCHOR_VERTICAL.getValue(value, (int) DmlTextAnchorVertical.MIDDLE);
    }

    static /*DmlTextBlockDirection*/int dmlToTextBlockDirection(String value)
    {
        return (/*DmlTextBlockDirection*/int) G_TEXT_BLOCK_DIRECTION.getValue(value, (int) DmlTextBlockDirection.HORIZONTAL);
    }

    static /*DmlConstraintType*/int dmlToConstraintType(String value)
    {
        return (/*DmlConstraintType*/int) G_CONSTRAINT_TYPE.getValue(value, (int) DmlConstraintType.NONE);
    }

    static String constraintTypeToDml(/*DmlConstraintType*/int constraintType)
    {
        return G_CONSTRAINT_TYPE.getValue((int) constraintType, "");
    }

    static /*DmlConnectorDimensions*/int dmlToConnectorDimensions(String value)
    {
        return (/*DmlConnectorDimensions*/int) G_CONNECTOR_DIMENSION.getValue(value, (int) DmlConnectorDimensions.TWO_DIMENSIONAL);
    }

    static /*DmlConnectorPoint*/int dmlToConnectorPoint(String value)
    {
        return (/*DmlConnectorPoint*/int) G_CONNECTOR_POINT.getValue(value, (int) DmlConnectorPoint.AUTO);
    }

    static /*DmlArrowheadStyle*/int dmlToArrowheadStyle(String value)
    {
        return (/*DmlArrowheadStyle*/int)G_ARROWHEAD_STYLE.getValue(value, (int)DmlArrowheadStyle.AUTO);
    }

    static /*DmlConnectorRouting*/int dmlToConnectorRouting(String value)
    {
        return (/*DmlConnectorRouting*/int)G_CONNECTOR_ROUTING.getValue(value, (int)DmlConnectorRouting.STRAIGHT);
    }

    private static /*final*/ StringToIntBidirectionalMap G_CONNECTION_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_POINT_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_CHILD_ORDER_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ALGORITHM_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_AXIS_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ELEMENT_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_BOOLEAN_OPERATOR_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_CONSTRAINT_RELATIONSHIP_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_DIRECTION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_HIER_BRANCH_STYLE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_ANIM_ONE;
    private static /*final*/ StringToIntBidirectionalMap G_ANIM_LEVEL_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_RESIZE_HANDLES_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_FUNCTION_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_VARIABLE_TYPE_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_FUNCTION_OPERATOR_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_COLOR_APPLICATION_METHOD_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_HUE_DIRECTION_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_NODE_HORIZONTAL_ALIGNMENT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_NODE_VERTICAL_ALIGNMENT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_FALLBACK_DIMENSIONS_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_DIAGRAM_HORIZONTAL_ALIGNMENT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_VERTICAL_ALIGNMENT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_BREAKPOINT_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_OFFSET_MAP;
    private static /*final*/ StringToIntBidirectionalMap G_CONTINUE_DIRECTION;
    private static /*final*/ StringToIntBidirectionalMap G_AUTO_TEXT_ROTATION;
    private static /*final*/ StringToIntBidirectionalMap G_DIAGRAM_TEXT_ALIGNMENT;
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_ANCHOR_HORIZONTAL;
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_ANCHOR_VERTICAL;
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_BLOCK_DIRECTION;
    private static /*final*/ StringToIntBidirectionalMap G_CONSTRAINT_TYPE;
    private static /*final*/ StringToIntBidirectionalMap G_CONNECTOR_DIMENSION;
    private static /*final*/ StringToIntBidirectionalMap G_CONNECTOR_POINT;
    private static /*final*/ StringToIntBidirectionalMap G_ARROWHEAD_STYLE;
    private static /*final*/ StringToIntBidirectionalMap G_CONNECTOR_ROUTING;

    private static final char LIST_SEPARATOR = ' ';

    static/* DmlDiagramEnum()*/
    {
        G_CONNECTION_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] {"parOf", "presOf", "presParOf", "unknownRelationship"},
            new int[]
                {
                    (int) DmlConnectionType.PARENT_OF, (int) DmlConnectionType.PRESENTATION_OF,
                    (int) DmlConnectionType.PRESENTATION_PARENT_OF, (int) DmlConnectionType.UNKNOWN_RELATIONSHIP
                });


        G_POINT_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] {"node", "asst", "doc", "pres", "parTrans", "sibTrans"},
            new int[]
                {
                    (int) DmlPointType.NODE, (int) DmlPointType.ASSISTANT_ELEMENT,
                    (int) DmlPointType.DOCUMENT, (int) DmlPointType.PRESENTATION,
                    (int) DmlPointType.PARENT_TRANSITION, (int) DmlPointType.SIBLING_TRANSITION
                });

        G_CHILD_ORDER_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] { "b", "t" },
            new int[]
                {
                    (int) DmlChildOrder.BOTTOM, (int) DmlChildOrder.TOP
                });

        G_ALGORITHM_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[] {"composite", "conn", "cycle", "hierChild", "hierRoot", "lin", "pyra", "snake", "sp", "tx"},
            new int[]
                {
                    (int) DmlAlgorithmType.COMPOSITE, (int) DmlAlgorithmType.CONNECTOR,
                    (int) DmlAlgorithmType.CYCLE, (int) DmlAlgorithmType.HIERARCHY_CHILD,
                    (int) DmlAlgorithmType.HIERARCHY_ROOT, (int) DmlAlgorithmType.LINEAR,
                    (int) DmlAlgorithmType.PYRAMID, (int) DmlAlgorithmType.SNAKE, (int) DmlAlgorithmType.SPACE,
                    (int) DmlAlgorithmType.TEXT
                });

        G_AXIS_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ancst", "ancstOrSelf", "ch", "des", "desOrSelf", "follow", "followSib", "none", "par", "preced",
                    "precedSib", "root", "self"
                },
            new int[]
                {
                    (int) DmlAxisType.ANCESTOR, (int) DmlAxisType.ANCESTOR_OR_SELF,
                    (int) DmlAxisType.CHILD, (int) DmlAxisType.DESCENDANT,
                    (int) DmlAxisType.DESCENDANT_OR_SELF, (int) DmlAxisType.FOLLOW,
                    (int) DmlAxisType.FOLLOW_SIBLING, (int) DmlAxisType.NONE, (int) DmlAxisType.PARENT,
                    (int) DmlAxisType.PRECEDING, (int) DmlAxisType.PRECEDING_SIBLING, (int) DmlAxisType.ROOT,
                    (int) DmlAxisType.SELF
                });

        G_ELEMENT_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "all", "asst", "doc", "node", "nonAsst", "nonNorm", "norm", "parTrans", "pres", "sibTrans"
                },
            new int[]
                {
                    (int) DmlElementType.ALL, (int) DmlElementType.ASSISTANT,
                    (int) DmlElementType.DOCUMENT, (int) DmlElementType.NODE,
                    (int) DmlElementType.NON_ASSISTANT, (int) DmlElementType.NON_NORMAL,
                    (int) DmlElementType.NORMAL, (int) DmlElementType.PARENT_TRANSITION,
                    (int) DmlElementType.PRESENTATION, (int) DmlElementType.SIBLING_TRANSITION
                });


        G_BOOLEAN_OPERATOR_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "equ", "gte", "lte", "none"
                },
            new int[]
                {
                    (int) DmlBooleanOperator.EQUAL, (int) DmlBooleanOperator.GREATER_OR_EQUAL,
                    (int) DmlBooleanOperator.LESS_OR_EQUAL, (int) DmlBooleanOperator.NONE
                });

        G_CONSTRAINT_RELATIONSHIP_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ch", "des", "self"
                },
            new int[]
                {
                    (int) DmlConstraintRelationship.CHILD, (int) DmlConstraintRelationship.DESCENDANT,
                    (int) DmlConstraintRelationship.SELF
                });

        G_DIRECTION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "norm", "rev"
                },
            new int[]
                {
                    (int) DmlDiagramDirection.NORMAL, (int) DmlDiagramDirection.REVERSED
                });

        G_HIER_BRANCH_STYLE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "hang", "init", "l", "r", "std"
                },
            new int[]
                {
                    (int) DmlHierBranchStyle.HANGING, (int) DmlHierBranchStyle.INITIAL,
                    (int) DmlHierBranchStyle.LEFT, (int) DmlHierBranchStyle.RIGHT,
                    (int) DmlHierBranchStyle.STANDARD
                });

        G_ANIM_ONE = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "branch", "none", "one"
                },
            new int[]
                {
                    (int) DmlAnimOne.BRANCH,
                    (int) DmlAnimOne.NONE,
                    (int) DmlAnimOne.ONE
                });

        G_ANIM_LEVEL_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ctr", "lvl", "none"
                },
            new int[]
                {
                    (int) DmlAnimLevel.CENTER,
                    (int) DmlAnimLevel.LEVEL,
                    (int) DmlAnimLevel.NONE
                });

        G_RESIZE_HANDLES_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "exact", "rel"
                },
            new int[]
                {
                    (int) DmlResizeHandles.EXACT,
                    (int) DmlResizeHandles.RELATIVE
                });

        G_FUNCTION_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "cnt", "depth", "maxDepth", "pos", "posEven", "posOdd", "revPos", "var"
                },
            new int[]
                {
                    (int) DmlFunctionType.COUNT, (int) DmlFunctionType.DEPTH,
                    (int) DmlFunctionType.MAX_DEPTH, (int) DmlFunctionType.POSITION,
                    (int) DmlFunctionType.POSITION_EVEN, (int) DmlFunctionType.POSITION_ODD,
                    (int) DmlFunctionType.REVERSE_POSITION, (int) DmlFunctionType.VARIABLE
                });

        G_VARIABLE_TYPE_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "animLvl", "animOne", "bulEnabled", "chMax", "chPref", "dir", "hierBranch", "none", "orgChart",
                    "resizeHandles"
                },
            new int[]
                {
                    (int) DmlVariableType.ANIM_LEVEL, (int) DmlVariableType.ANIM_ONE,
                    (int) DmlVariableType.BULLET_ENABLED, (int) DmlVariableType.CHILD_MAX,
                    (int) DmlVariableType.CHILD_PREF, (int) DmlVariableType.DIRECTION,
                    (int) DmlVariableType.HIER_BRANCH_STYLE, (int) DmlVariableType.NONE,
                    (int) DmlVariableType.ORG_CHART,
                    (int) DmlVariableType.RESIZE_HANDLES
                });

        G_FUNCTION_OPERATOR_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "equ", "gt", "gte", "lt", "lte", "neq"
                },
            new int[]
                {
                    (int) DmlFunctionOperator.EQUAL, (int) DmlFunctionOperator.GREATER,
                    (int) DmlFunctionOperator.GREATER_OR_EQUAL, (int) DmlFunctionOperator.LESS,
                    (int) DmlFunctionOperator.LESS_OR_EQUAL, (int) DmlFunctionOperator.NOT_EQUAL
                });

        G_COLOR_APPLICATION_METHOD_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "cycle", "repeat", "span"
                },
            new int[]
                {
                    (int) DmlColorApplicationMethod.CYCLE, (int) DmlColorApplicationMethod.REPEAT,
                    (int) DmlColorApplicationMethod.SPAN
                });

        G_HUE_DIRECTION_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "cw", "ccw"
                },
            new int[]
                {
                    (int) DmlHueDirection.CLOCKWISE, (int) DmlHueDirection.COUNTERCLOCKWISE
                });

        G_NODE_HORIZONTAL_ALIGNMENT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ctr", "l", "r"
                },
            new int[]
                {
                    (int) DmlNodeHorizontalAlignment.CENTER, (int) DmlNodeHorizontalAlignment.LEFT,
                    (int) DmlNodeHorizontalAlignment.RIGHT
                });

        G_NODE_VERTICAL_ALIGNMENT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "b", "mid", "t"
                },
            new int[]
                {
                    (int) DmlNodeVerticalAlignment.BOTTOM, (int) DmlNodeVerticalAlignment.MIDDLE,
                    (int) DmlNodeVerticalAlignment.TOP
                });

        G_FALLBACK_DIMENSIONS_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "1D", "2D"
                },
            new int[]
                {
                    (int) DmlFallbackDimension.ONE_DIMENSION, (int) DmlFallbackDimension.TWO_DIMENSIONS
                });

        G_DIAGRAM_HORIZONTAL_ALIGNMENT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ctr", "l", "none", "r"
                },
            new int[]
                {
                    (int) DmlDiagramHorizontalAlignment.CENTER, (int) DmlDiagramHorizontalAlignment.LEFT,
                    (int) DmlDiagramHorizontalAlignment.NONE, (int) DmlDiagramHorizontalAlignment.RIGHT
                });

        G_VERTICAL_ALIGNMENT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "b", "mid", "none", "t"
                },
            new int[]
                {
                    (int) DmlVerticalAlignment.BOTTOM, (int) DmlVerticalAlignment.MIDDLE,
                    (int) DmlVerticalAlignment.NONE, (int) DmlVerticalAlignment.TOP
                });

        G_BREAKPOINT_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "bal", "endCnv", "fixed"
                },
            new int[]
                {
                    (int) DmlBreakpoint.BALANCED, (int) DmlBreakpoint.END_OF_CANVAS,
                    (int) DmlBreakpoint.FIXED
                });

        G_OFFSET_MAP = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ctr", "off"
                },
            new int[]
                {
                    (int) DmlOffset.CENTER, (int) DmlOffset.OFFSET
                });

        G_CONTINUE_DIRECTION = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "revDir", "sameDir"
                },
            new int[]
                {
                    (int) DmlContinueDirection.REVERSE_DIRECTION, (int) DmlContinueDirection.SAME_DIRECTION
                });

        G_AUTO_TEXT_ROTATION = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "none", "upr", "grav"
                },
            new int[]
                {
                    (int) DmlAutoTextRotation.NONE, (int) DmlAutoTextRotation.UPRIGHT,
                    (int) DmlAutoTextRotation.GRAVITY
                });

        G_DIAGRAM_TEXT_ALIGNMENT = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "l", "ctr", "r"
                },
            new int[]
                {
                    (int) DmlDiagramTextAlignment.LEFT, (int) DmlDiagramTextAlignment.CENTER,
                    (int) DmlDiagramTextAlignment.RIGHT
                });

        G_TEXT_ANCHOR_HORIZONTAL = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "ctr", "none"
                },
            new int[]
                {
                    (int) DmlTextAnchorHorizontal.CENTER, (int) DmlTextAnchorHorizontal.NONE
                });

        G_TEXT_ANCHOR_VERTICAL = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "b", "mid", "t"
                },
            new int[]
                {
                    (int) DmlTextAnchorVertical.BOTTOM, (int) DmlTextAnchorVertical.MIDDLE,
                    (int) DmlTextAnchorVertical.TOP
                });

        G_TEXT_BLOCK_DIRECTION = DmlEnumUtil.initHashtableWithValues(
            new String[]
                {
                    "horz", "vert"
                },
            new int[]
                {
                    (int) DmlTextBlockDirection.HORIZONTAL, (int) DmlTextBlockDirection.VERTICAL
                });

        G_CONSTRAINT_TYPE = DmlEnumUtil.initHashtableWithValues(
            new String[]
            {
                "none", "alignOff", "begMarg", "bendDist", "begPad", "b", "bMarg", "bOff", "ctrX", "ctrXOff", "ctrY",
                "ctrYOff", "connDist", "diam", "endMarg", "endPad", "h", "hArH", "hOff", "l", "lMarg", "lOff", "r",
                "rMarg", "rOff", "primFontSz", "pyraAcctRatio", "secFontSz", "sibSp", "secSibSp", "sp", "stemThick",
                "t", "tMarg", "tOff", "userA", "userB", "userC", "userD", "userE", "userF", "userG", "userH",
                "userI", "userJ", "userK", "userL", "userM", "userN", "userO", "userP", "userQ", "userR", "userS",
                "userT", "userU", "userV", "userW", "userX", "userY", "userZ", "w", "wArH", "wOff"
            },
            new int[]
            {
                (int) DmlConstraintType.NONE, (int) DmlConstraintType.ALIGNMENT_OFFSET,
                (int) DmlConstraintType.BEGINNING_MARGIN, (int) DmlConstraintType.BENDING_DISTANCE,
                (int) DmlConstraintType.BEGIN_PADDING, (int) DmlConstraintType.BOTTOM,
                (int) DmlConstraintType.BOTTOM_MARGIN, (int) DmlConstraintType.BOTTOM_OFFSET,
                (int) DmlConstraintType.CENTER_X, (int) DmlConstraintType.CENTER_X_OFFSET,
                (int) DmlConstraintType.CENTER_Y, (int) DmlConstraintType.CENTER_Y_OFFSET,
                (int) DmlConstraintType.CONNECTION_DISTANCE, (int) DmlConstraintType.DIAMETER,
                (int) DmlConstraintType.END_MARGIN, (int) DmlConstraintType.END_PADDING,
                (int) DmlConstraintType.HEIGHT, (int) DmlConstraintType.ARROWHEAD_HEIGHT,
                (int) DmlConstraintType.HEIGHT_OFFSET, (int) DmlConstraintType.LEFT,
                (int) DmlConstraintType.LEFT_MARGIN, (int) DmlConstraintType.LEFT_OFFSET,
                (int) DmlConstraintType.RIGHT, (int) DmlConstraintType.RIGHT_MARGIN,
                (int) DmlConstraintType.RIGHT_OFFSET, (int) DmlConstraintType.PRIMARY_FONT_SIZE,
                (int) DmlConstraintType.PYRAMID_ACCENT_RATIO, (int) DmlConstraintType.SECONDARY_FONT_SIZE,
                (int) DmlConstraintType.SIBLING_SPACING, (int) DmlConstraintType.SECONDARY_SIBLING_SPACING,
                (int) DmlConstraintType.SPACING, (int) DmlConstraintType.STEM_THICKNESS,
                (int) DmlConstraintType.TOP, (int) DmlConstraintType.TOP_MARGIN, (int) DmlConstraintType.TOP_OFFSET,
                (int) DmlConstraintType.USER_A, (int) DmlConstraintType.USER_B, (int) DmlConstraintType.USER_C,
                (int) DmlConstraintType.USER_D, (int) DmlConstraintType.USER_E, (int) DmlConstraintType.USER_F,
                (int) DmlConstraintType.USER_G, (int) DmlConstraintType.USER_H, (int) DmlConstraintType.USER_I,
                (int) DmlConstraintType.USER_J, (int) DmlConstraintType.USER_K, (int) DmlConstraintType.USER_L,
                (int) DmlConstraintType.USER_M, (int) DmlConstraintType.USER_N, (int) DmlConstraintType.USER_O,
                (int) DmlConstraintType.USER_P, (int) DmlConstraintType.USER_Q, (int) DmlConstraintType.USER_R,
                (int) DmlConstraintType.USER_S, (int) DmlConstraintType.USER_T, (int) DmlConstraintType.USER_U,
                (int) DmlConstraintType.USER_V, (int) DmlConstraintType.USER_W, (int) DmlConstraintType.USER_X,
                (int) DmlConstraintType.USER_Y, (int) DmlConstraintType.USER_Z,
                (int) DmlConstraintType.WIDTH, (int) DmlConstraintType.ARROWHEAD_WIDTH,
                (int) DmlConstraintType.WIDTH_OFFSET
            });

        G_CONNECTOR_DIMENSION = DmlEnumUtil.initHashtableWithValues(
            new String[]
            {
                "1D", "2D", "cust"
            },
            new int[]
            {
                (int) DmlConnectorDimensions.ONE_DIMENSIONAL, (int) DmlConnectorDimensions.TWO_DIMENSIONAL,
                (int) DmlConnectorDimensions.CUSTOM
            });

        G_CONNECTOR_POINT = DmlEnumUtil.initHashtableWithValues(
            new String[]
            {
                "auto", "bCtr", "bL", "bR", "ctr", "midL", "midR", "radial", "tCtr", "tL", "tR"
            },
            new int[]
            {
                (int) DmlConnectorPoint.AUTO, (int) DmlConnectorPoint.BOTTOM_CENTER,
                (int) DmlConnectorPoint.BOTTOM_LEFT, (int) DmlConnectorPoint.BOTTOM_RIGHT,
                (int) DmlConnectorPoint.CENTER, (int) DmlConnectorPoint.MIDDLE_LEFT,
                (int) DmlConnectorPoint.MIDDLE_RIGHT, (int) DmlConnectorPoint.RADIAL,
                (int) DmlConnectorPoint.TOP_CENTER, (int) DmlConnectorPoint.TOP_LEFT,
                (int) DmlConnectorPoint.TOP_RIGHT
            });

        G_ARROWHEAD_STYLE = DmlEnumUtil.initHashtableWithValues(
            new String[] {"arr", "auto", "noArr"},
            new int[]
            {
                (int)DmlArrowheadStyle.ARROWHEAD_PRESENT, (int)DmlArrowheadStyle.AUTO,
                (int)DmlArrowheadStyle.NO_ARROWHEAD
            });

        G_CONNECTOR_ROUTING = DmlEnumUtil.initHashtableWithValues(
            new String[] {"bend", "curve", "longCurve", "stra"},
            new int[]
            {
                (int)DmlConnectorRouting.BENDING, (int)DmlConnectorRouting.CURVE,
                (int)DmlConnectorRouting.LONG_CURVE, (int)DmlConnectorRouting.STRAIGHT
            });
    }
}

