// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2015 by Vadim Polienko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ImageUtil;
import com.aspose.Debug;

import java.awt.image.BufferedImage;


/// <summary>
/// Common functionality for FieldDisplayBarcode and FieldMergeBarcode
/// </summary>
/*static*/ class FieldBarcodeUtil
{
	/* Simulation of static class by using private constructor */
	private FieldBarcodeUtil()
	{}

    /// <summary>
    /// Straightforward processing of MERGEBARCODE field.
    /// This is done just like Word does - replacing by DISPLAYBARCODE field.
    /// </summary>
    /// <param name="field"></param>
    static void replaceMergeBarcode(Field field) throws Exception
    {
        // Create new field code from current arguments & switches.
        String newFieldCode = rebuildFieldCode(field, "DISPLAYBARCODE");

        // Modify field type.
        field.setFieldType(FieldType.FIELD_DISPLAY_BARCODE);

        // Set new field code and forcibly invalidate field code cache.
        field.setFieldCode(newFieldCode);
        field.invalidateFieldCodeCache();

        // Remove the result.
        field.removeFieldResult();
    }

    /// <summary>
    /// Rebuild full field code from field arguments and switches (in FieldCodeCache).
    /// </summary>
    /// <param name="field"></param>
    /// <param name="fieldTypeName"></param>
    /// <returns>Field code</returns>
    private static String rebuildFieldCode(Field field, String fieldTypeName)
    {
        FieldCode fieldCodeCache = field.getFieldCodeCache();

        boolean isInQuotes = msString.startsWith(field.getResult(), "\"", StringComparison.ORDINAL) &&
            msString.endsWith(field.getResult(), "\"", StringComparison.ORDINAL);
        StringBuilder sb = new StringBuilder();

        msStringBuilder.append(sb, (fieldTypeName != null ? fieldTypeName : fieldCodeCache.getFieldType()));

        sb.append(' ');

        if (!isInQuotes)
            sb.append('"');

        msStringBuilder.append(sb, field.getResult());

        if (!isInQuotes)
            sb.append('"');

        for (int i = 1; i < fieldCodeCache.getArguments().size(); i++)
        {
            sb.append(' ');
            msStringBuilder.append(sb, fieldCodeCache.getArgument(i).getText());
        }

        for (FieldSwitch fieldSwitch : fieldCodeCache.getSwitches())
        {
            sb.append(' ');
            msStringBuilder.append(sb, fieldSwitch.getName());
            sb.append(' ');
            msStringBuilder.append(sb, fieldSwitch.getArgument().getText());
        }

        return sb.toString();
    }

    private static Node getFakeResultNode(/*FieldType*/int fieldType, Document document, BarcodeParameters parameters) throws Exception
    {
        RunPr errorRunPr = new RunPr();
        errorRunPr.setBold(AttrBoolEx.TRUE);

        IBarcodeGenerator generator = document.getFieldOptions().getBarcodeGenerator();
        if (generator == null)
            return new Run(document, NO_GENERATOR_ERROR_MESSAGE, errorRunPr);

        BufferedImage image = generateBarcodeImage(generator, fieldType, parameters);
        if (image == null)
            return new Run(document, IMAGE_GENERATION_ERROR_MESSAGE, errorRunPr);

        // Check whether the barcode data provided by IBarcodeGenerator is in supported image format.
        /*FileFormat*/int imageType = image.getType();
        if (!ImageUtil.isImage(imageType))
            return new Run(document, IMAGE_FORMA_ERROR_MESSAGE, errorRunPr);

        Shape shape = new Shape(document, ShapeType.IMAGE);
        shape.getImageData().setImage(image);

        // WORDSNET-14821 There are two stages of fields update. The common one, after which ShapeValidator is called,
        // and field's update through layout after which ShapeValidator is not called and zero shape size is not fixed.
        // So we have to validate zero shape size here.
        shape.makeSizeValid();

        // WORDSNET-17480 Make sure generated image is rendered inline.
        shape.setWrapType(WrapType.INLINE);

        return shape;
    }

    private static BufferedImage generateBarcodeImage(
        IBarcodeGenerator generator,
        /*FieldType*/int fieldType,
        BarcodeParameters parameters) throws Exception
    {
        switch (fieldType)
        {
            case FieldType.FIELD_BARCODE:
                return generator.getOldBarcodeImage(parameters);
            case FieldType.FIELD_DISPLAY_BARCODE:
                return generator.getBarcodeImage(parameters);
            default:
                Debug.fail("Invalid field type");
                return null;
        }
    }

    /// <summary>
    /// Core logic for updating BARCODE or DISPLAYBARCODE fields.
    /// </summary>
    static NodeRange getFakeResult(/*FieldType*/int fieldType, Document document, BarcodeParameters parameters) throws Exception
    {
        Node result = getFakeResultNode(fieldType, document, parameters);
        return new NodeRange(result, result);
    }

    static /*FieldSwitchType*/int getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*DISPLAY_TEXT_SWITCH*/0:
            case /*ADD_START_STOP_CHAR_SWITCH*/1:
            case /*FIX_CHECK_DIGIT_SWITCH*/2:
                return FieldSwitchType.FLAG;
            case /*SYMBOL_HEIGHT_SWITCH*/3:
            case /*SYMBOL_ROTATION_SWITCH*/4:
            case /*SCALING_FACTOR_SWITCH*/5:
            case /*FOREGROUND_COLOR_SWITCH*/6:
            case /*BACKGROUND_COLOR_SWITCH*/7:
            case /*POS_CODE_STYLE_SWITCH*/8:
            case /*CASE_CODE_STYLE_SWITCH*/9:
            case /*ERROR_CORRECTION_LEVEL_SWITCH*/10:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    static final int BARCODE_VALUE_ARGUMENT_INDEX = 0;
    static final int BARCODE_TYPE_ARGUMENT_INDEX = 1;

    private static final String NO_GENERATOR_ERROR_MESSAGE = "Error! Bar code generator is not set.";
    private static final String IMAGE_GENERATION_ERROR_MESSAGE = "Error! Image was not generated.";
    private static final String IMAGE_FORMA_ERROR_MESSAGE = "Error! Image format is not recognized. Supported image formats are Bmp, Emf, Gif, Jpeg, Png, Tiff, Wmf, Pict, Ico, WebP, Svg.";

    static final String SYMBOL_HEIGHT_SWITCH = "\\h";
    static final String SYMBOL_ROTATION_SWITCH = "\\r";
    static final String SCALING_FACTOR_SWITCH = "\\s";
    static final String DISPLAY_TEXT_SWITCH = "\\t";
    static final String FOREGROUND_COLOR_SWITCH = "\\f";
    static final String BACKGROUND_COLOR_SWITCH = "\\b";
    static final String POS_CODE_STYLE_SWITCH = "\\p";
    static final String CASE_CODE_STYLE_SWITCH = "\\c";
    static final String ADD_START_STOP_CHAR_SWITCH = "\\d";
    static final String FIX_CHECK_DIGIT_SWITCH = "\\x";
    static final String ERROR_CORRECTION_LEVEL_SWITCH = "\\q";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\t",
		"\\d",
		"\\x",
		"\\h",
		"\\r",
		"\\s",
		"\\f",
		"\\b",
		"\\p",
		"\\c",
		"\\q"
	);

}

