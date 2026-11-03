// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2006 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.msMath;
import com.aspose.FormatterPal;
import com.aspose.ConvertUtilCore;
import com.aspose.StringUtil;
import com.aspose.IntToObjDictionary;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.DrColor;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.MathUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.CrcMaker;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ImageSizeCore;
import com.aspose.ImageUtil;
import com.aspose.ms.System.msArray;
import com.aspose.ZipUtilPal;
import com.aspose.ZipMethod;
import com.aspose.Debug;


/*static*/ class VmlUtil
{
	/* Simulation of static class by using private constructor */
	private VmlUtil()
	{}

    /// <summary>
    /// Parses comma-delimited string of integers.
    /// </summary>
    static int[] vmlToIntArray(String value)
    {
        String[] strings = msString.split(value, ',');
        int[] ints = new int[strings.length];

        for (int i = 0; i < strings.length; i++)
        {
            String str = strings[i];
            // Resiliency Jira 6808 - sometimes we get comma delimited string of doubles.
            ints[i] = (int)msMath.round(FormatterPal.parseDouble(str));
        }

        return ints;
    }

    static String emuToVmlPoints(int value)
    {
        return distanceToVml(ConvertUtilCore.emuToPoint(value));
    }

    static String emuToVmlMillimeters(int value)
    {
        return millimetersToVml(ConvertUtilCore.emuToMillimeter(value));
    }

    /// <summary>
    /// Gets value for 'ShapeID' attribute for OleObjects.
    /// </summary>
    /// <remarks>
    /// WORDSNET-3646 In case shape has 'id' attribute not equal to 'spid' OleObject should refer to 'id' attribute which goes to Name.
    /// </remarks>
    /// <param name="shape"></param>
    /// <returns></returns>
    static String getOleObjectShapeId(ShapeBase shape)
    {
        return StringUtil.hasChars(shape.getName()) ? shape.getName() : NrxXmlUtil.getShapeId(shape);
    }

    static String getInset(IntToObjDictionary<Object> props)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = ShapeAttr.TEXTBOX_LEFT; i <= ShapeAttr.TEXTBOX_BOTTOM; i++)
        {
            Object value = props.get(i);
            if (value != null)
                msStringBuilder.append(sb, distanceToVml(ConvertUtilCore.emuToPoint((/*int*/Integer)value)));

            sb.append(',');
        }

        return msString.trimEnd(sb.toString(), ',');
    }

    static String fixedToVml(Object value)
    {
        if (value == null)
            return null;

        int intValue = (/*int*/Integer)value;  // Has to be on a separate line for Java to work.
        return fixedToVml(intValue);
    }

    static String fixedToVml(int value)
    {
        return fixedToVml(value, true);
    }

    /// <summary>
    /// The same like FixedToVml but with or without adding "f".
    /// We need this upon writing FillToLeft/FillToRight/FillToTop/FillToBottom attrs to mimic MS Word behavior.
    /// </summary>
    static String fixedToVml(int value, boolean includeSuffix)
    {
        if (value == 0)
            return "0";

        if (value % 0x4000 == 0)
            return FormatterPal.doubleToStr2Decimals((double)value / (double) 0x10000);

        if (includeSuffix)
            return Integer.toString(value) + "f";

        return Integer.toString(value);
    }

    /// <summary>
    /// Converts boolean value to VML string.
    /// Return null if the value equals default value.
    /// </summary>
    static String boolToVml(Object value, boolean defaultValue)
    {
        boolean actualValue = (/*boolean*/Boolean)value; // Has to be on a separate line for autoporting to Java to work correctly.

        if (actualValue != defaultValue)
            return boolToVml(actualValue);
        else
            return null;
    }

    static String boolToVml(Object value)
    {
        if (value == null)
            return null;

        boolean boolValue = (/*boolean*/Boolean)value;   // Has to be on a separate line for autoporting to Java to work correctly.
        return boolToVml(boolValue);
    }

    static String boolToVml(boolean value)
    {
        return value ? "t" : "f";
    }

    static String fixedDegreesToVml(int value)
    {
        double degrees = ConvertUtilCore.fixedToDouble(value);
        if (degrees == msMath.round(degrees))
        {
            // Rotation is a whole number, MS Word writes as a value in degrees, we do the same.
            return FormatterPal.intToXml((int)degrees);
        }
        else
        {
            // Rotation has a fraction. MS Word writes as fixed value, we do the same.
            return FormatterPal.intToXml(value) + "fd";
        }
    }

    static String colorsToVml(GradientColor[] colors, IVmlShapeWriterContext context)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < colors.length; i++)
        {
            int start = colors[i].Start;
            DrColor color = colors[i].Color;

            if (start == 0)
                msStringBuilder.append(sb, msString.format("{0} {1};", 0, context.colorToVml(color)));
            else if (start == colors.length - 1)
                msStringBuilder.append(sb, msString.format("{0} {1};", 1, context.colorToVml(color)));
            else
                msStringBuilder.append(sb, msString.format("{0} {1};", fixedToVml(start), context.colorToVml(color)));
        }

        if (sb.length() > 0)
            return sb.substring((0), (0) + (sb.length() - 1));
        else
            return "";
    }

    static String pointToVml(/*Point*/long point)
    {
        return msString.format("{0},{1}", FormatterPal.intToXml(msPoint.getX(point)), FormatterPal.intToXml(msPoint.getY(point)));
    }

    static String pointToVml(double x, double y, boolean isAbsolute)
    {
        return msString.format("{0},{1}", distanceToVml(x, isAbsolute), distanceToVml(y, isAbsolute));
    }

    static String distanceToVml(Object value, boolean isAbsolute)
    {
        if (value == null)
            return null;

        double doubleValue = (/*double*/Double)value; // Has to be on a separate line for Java to work.
        return distanceToVml(doubleValue, isAbsolute);
    }

    static String distanceToVml(double value, boolean isAbsolute)
    {
        if (isAbsolute)
            return distanceToVml(value);
        else
            return FormatterPal.intToXml(MathUtil.doubleToInt(value)); // Child shape coordinates are to be written as integers.
    }

    static String distanceToVml(double value)
    {
        // Value may be very small but does not equal to zero. However, it will be written as zero.
        // So, need to compare with rounded value while choosing output format.
        if (msMath.round(value, 2) == 0)
            return "0";

        if (value % 72.0 == 0)
            return FormatterPal.doubleToStr2Decimals(value / 72.0) + "in";

        return FormatterPal.doubleToStr2Decimals(value) + "pt";
    }

    static String millimetersToVml(double value)
    {
        if (value == 0)
            return "0";
        else
            return FormatterPal.doubleToStr9Decimals(value) + "mm";
    }

    static String percentsToVml(int value)
    {
        if (value == 0)
            return "0";
        else
            return FormatterPal.intToXml(value) + "%";
    }

    static String borderToVmlColor(Border value, IVmlShapeWriterContext context)
    {
        // TODO 3: In fact I still do not know exactly when to write image borders and when not.
        // Currently my output does not always matches MS Word in that matter.
        // But the appearance of the documents seems to match, so I am ok with it.
        if ((value != null) && value.isVisible())
        {
            DrColor color = value.getColorInternal();
            return context.colorToVml(color);
        }
        else
            return "";
    }

    static String perspectiveToVml(int value)
    {
        double perspective = (value / PERSPECTIVE_MULTIPLIER);
        return FormatterPal.doubleToStr11HashesEZero(perspective);
    }

    static int vmlToPerspective(String value)
    {
        return MathUtil.doubleToInt(FormatterPal.parseDouble(value) * PERSPECTIVE_MULTIPLIER);
    }

    /// <summary>
    /// Calculates hash code of the diagram node shape used in DiagramNodeRelation as reference.
    /// The hash is a CRC32 of the shape name stored in Escher.
    /// </summary>
    /// <param name="shapeName">Shape name from ShapeAttr.ShapeName attribute.</param>
    /// <returns>Hash code for the given name.</returns>
    static int calculateDiagramHash(String shapeName)
    {
        // RK MS Word 2007 prepends # before the shape name and looks like it has to be excluded from hash.
        byte[] namebytes = Encoding.getUnicode().getBytes(msString.trim(shapeName, '#'));

        CrcMaker crcMaker = new CrcMaker();
        return crcMaker.makeCRC(namebytes);
    }

    /// <summary>
    /// Pack PICT image bytes into compressed PICT (*.pcz) format.
    /// </summary>
    static byte[] packPictImage(byte[] imageBytes) throws Exception
    {
        // PICT image bytes taken from DOC contain two blocks of data: header and body.
        // Header has length 0x22 and contains PICT image parameters,
        // such as length of uncompressed image file (Int32, goes first), width, height and some others.
        // Body is a zipped stream of image bytes.

        // Build uncompressed PICT image here.
        // It consists of the starter, which is 512 bytes long, and image data proper.
        MemoryStream imageStream = new MemoryStream();
        BinaryWriter writer = new BinaryWriter(imageStream);

        // Write prefix.
        String prefix = "MSOFFICE9.0";
        for (int i = 0; i < prefix.length(); i++)
        {
            writer.writeByte((byte)prefix.charAt(i));
        }

        writer.writeUInt32((/*uint*/int)imageBytes.length);

        // Write image bytes.
        ImageSizeCore imageSize = ImageUtil.getPictSize(imageBytes);
        writer.writeInt32(imageSize.getLeft());
        writer.writeInt32(imageSize.getTop());
        writer.writeInt32(imageSize.getRight());
        writer.writeInt32(imageSize.getBottom());
        writer.writeInt32(imageSize.getWidthEmus());
        writer.writeInt32(imageSize.getHeightEmus());

        // This should be a size of compressed image bytes. Beats me why it should be stored here in uncompressed image.
        // Writing 0 seems to be ok.
        writer.writeUInt32((/*uint*/int)0);

        // MS Word stores 0xfe00 here. Can be as well put to 0 as it seems nobody cares about it.
        writer.writeUInt16((/*ushort*/short)0xfe00);

        // Fill the rest of the starter bytes with 0. May be this is excessive and they are 0 anyway?
        for (int i = 0; i < 467; i++)
        {
            writer.writeByte((byte)0);
        }

        // Write image bytes.
        writer.writeBytes(imageBytes);

        // Pack image into compressed form.
        return packImage(imageStream.toArray());
    }

    static byte[] unpackPictImage(byte[] packedBytes) throws Exception
    {
        byte[] unpackedBytes = unpackImage(packedBytes);

        // See PackPictImage. It seems there is a 512 bytes header. We don't need it in the model.
        byte[] imageBytes = new byte[unpackedBytes.length - 512];
        msArray.copy(unpackedBytes, 512, imageBytes, 0, imageBytes.length);

        return imageBytes;
    }

    /// <summary>
    /// Pack image into compressed image format (*.wmz, *.pcz, etc.).
    /// </summary>
    static byte[] packImage(byte[] imageBytes) throws Exception
    {
        imageBytes = ZipUtilPal.deflate(imageBytes, ZipMethod.DEFLATE);

        byte[] packedImageBytes = new byte[imageBytes.length + G_PACKED_IMAGE_HEADER.length];
        System.arraycopy(imageBytes, 0, packedImageBytes, G_PACKED_IMAGE_HEADER.length, imageBytes.length);

        for (int i = 0; i < G_PACKED_IMAGE_HEADER.length; i++)
        {
            packedImageBytes[i] = G_PACKED_IMAGE_HEADER[i];
        }

        return packedImageBytes;
    }

    /// <summary>
    /// Unpacks images that are compressed (*.wmz, *.pcz, etc.).
    /// Safe to call if image is not compressed, in this case returns image bytes.
    /// </summary>
    static byte[] unpackImage(byte[] imageBytes) throws Exception
    {
        if (isImagePacked(imageBytes))
        {
            // Skip first 10 bytes of the header.
            MemoryStream imageStream = new MemoryStream(imageBytes, 10, imageBytes.length - 10);
            byte[] unpackedImageBytes = ZipUtilPal.inflate(imageStream, 0, ZipMethod.DEFLATE);
            return unpackedImageBytes;
        }

        return imageBytes;
    }

    /// <summary>
    /// Convert array of <see cref="PathPoint"/> to VML string.
    /// </summary>
    /// <param name="pathPointArrey">PathPoint array to convert.</param>
    /// <param name="coordDelimiter">Delimiter between X and Y coordinates.</param>
    /// <param name="pairDelimiter">Delimiter between XY pair.</param>
    /// <returns></returns>
    static String pathPointsToVml(PathPoint[] pathPointArrey, char coordDelimiter, char pairDelimiter)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < pathPointArrey.length; i++)
        {
            PathPoint loc = pathPointArrey[i];
            msStringBuilder.append(sb, pathValueToVml(loc.getX()));
            sb.append(coordDelimiter);
            msStringBuilder.append(sb, pathValueToVml(loc.getY()));
            sb.append(pairDelimiter);
        }

        sb.delete((sb.length() - 1), (sb.length() - 1) + (1));
        return sb.toString();
    }

    static String pathValueToVml(PathValue value)
    {
        if (value.isFormula())
            return "@" + FormatterPal.intToXml(value.getValue());
        else
            return FormatterPal.intToXml(value.getValue());
    }

    /// <summary>
    /// Calculates line vertices coordinates according to geometry data and specified
    /// shape size and positions.
    /// </summary>
    /// <param name="shape">Source shape.</param>
    /// <param name="t">Actual top position.</param>
    /// <param name="l">Actual left position.</param>
    /// <param name="w">Actual width.</param>
    /// <param name="h">Actual height.</param>
    /// <returns>Array with line vertices coordinates.</returns>
    static double[] getLineVertices(ShapeBase shape, double t, double l, double w, double h)
    {
        Debug.assertTrue(shape != null);

        PathPoint[] vertices = (PathPoint[])shape.fetchShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES);

        // Expected two PathPoint to process.
        if ((vertices == null) || (vertices.length < 2))
            return new double[0];

        return new double[] { (vertices[0].getX().getValue() > 0) ? l + w : l,
            (vertices[0].getY().getValue() > 0) ? t + h : t,
            (vertices[1].getX().getValue() > 0) ? l + w : l,
            (vertices[1].getY().getValue() > 0) ? t + h : t};
    }

    static boolean isWordArt(/*ShapeType*/int shapeType, boolean isGeoTextOn)
    {
        switch (shapeType)
        {
            case ShapeType.NON_PRIMITIVE:
            case ShapeType.TEXT_SIMPLE:
            case ShapeType.TEXT_OCTAGON:
            case ShapeType.TEXT_HEXAGON:
            case ShapeType.TEXT_CURVE:
            case ShapeType.TEXT_WAVE:
            case ShapeType.TEXT_RING:
            case ShapeType.TEXT_ON_CURVE:
            case ShapeType.TEXT_ON_RING:
            case ShapeType.TEXT_PLAIN_TEXT:
            case ShapeType.TEXT_STOP:
            case ShapeType.TEXT_TRIANGLE:
            case ShapeType.TEXT_TRIANGLE_INVERTED:
            case ShapeType.TEXT_CHEVRON:
            case ShapeType.TEXT_CHEVRON_INVERTED:
            case ShapeType.TEXT_RING_INSIDE:
            case ShapeType.TEXT_RING_OUTSIDE:
            case ShapeType.TEXT_ARCH_UP_CURVE:
            case ShapeType.TEXT_ARCH_DOWN_CURVE:
            case ShapeType.TEXT_CIRCLE_CURVE:
            case ShapeType.TEXT_BUTTON_CURVE:
            case ShapeType.TEXT_ARCH_UP_POUR:
            case ShapeType.TEXT_ARCH_DOWN_POUR:
            case ShapeType.TEXT_CIRCLE_POUR:
            case ShapeType.TEXT_BUTTON_POUR:
            case ShapeType.TEXT_CURVE_UP:
            case ShapeType.TEXT_CURVE_DOWN:
            case ShapeType.TEXT_CASCADE_UP:
            case ShapeType.TEXT_CASCADE_DOWN:
            case ShapeType.TEXT_WAVE_1:
            case ShapeType.TEXT_WAVE_2:
            case ShapeType.TEXT_WAVE_3:
            case ShapeType.TEXT_WAVE_4:
            case ShapeType.TEXT_INFLATE:
            case ShapeType.TEXT_DEFLATE:
            case ShapeType.TEXT_INFLATE_BOTTOM:
            case ShapeType.TEXT_DEFLATE_BOTTOM:
            case ShapeType.TEXT_INFLATE_TOP:
            case ShapeType.TEXT_DEFLATE_TOP:
            case ShapeType.TEXT_DEFLATE_INFLATE:
            case ShapeType.TEXT_DEFLATE_INFLATE_DEFLATE:
            case ShapeType.TEXT_FADE_RIGHT:
            case ShapeType.TEXT_FADE_LEFT:
            case ShapeType.TEXT_FADE_UP:
            case ShapeType.TEXT_FADE_DOWN:
            case ShapeType.TEXT_SLANT_UP:
            case ShapeType.TEXT_SLANT_DOWN:
            case ShapeType.TEXT_CAN_UP:
            case ShapeType.TEXT_CAN_DOWN:
                return isGeoTextOn;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns the modifier string value in VML.
    /// </summary>
    static String vmlModifier(Object value)
    {
        int modifier = (/*int*/Integer)value;
        if (modifier == 0)
            return "";

        String modifierFormat = (modifier > 0)
            ? "lighten({0})"
            : "darken({0})";
        return msString.format(modifierFormat, Math.abs(modifier));
    }

    /// <summary>
    /// Returns the base color string value in VML.
    /// </summary>
    static String vmlBaseColor(Object value)
    {
        DrColor baseColor = (DrColor)value;

        return (baseColor.isEmpty())
            ? ""
            : msString.format("rgb({0},{1},{2})", baseColor.getR(), baseColor.getG(), baseColor.getB());
    }

    /// <summary>
    /// Gets adjustment key by index.
    /// </summary>
    static int getAdjustKey(int index)
    {
        int adjKey = ShapeAttr.GEOMETRY_ADJUST_1 + index - 1;
        return adjKey;
    }

    private static boolean isImagePacked(byte[] imageBytes)
    {
        // I have seen this header as 1F 8B 08 00 00 00 00 00 02 0B
        //                   and also 1F 8B 08 00 00 00 00 00 04 0B
        // As I do not know the format of this header I will check only first four bytes.
        // I think it is safe and reliable enough, at least for now.
        final int NUMBER_OF_BYTES_TO_CHECK = 4;

        if ((imageBytes == null) || (imageBytes.length < NUMBER_OF_BYTES_TO_CHECK))
            return false;

        for (int i = 0; i < NUMBER_OF_BYTES_TO_CHECK; i++)
        {
            if (imageBytes[i] != G_PACKED_IMAGE_HEADER[i])
                return false;
        }

        return true;
    }

    private static /*final*/ byte[] G_PACKED_IMAGE_HEADER = { (byte) 0x1f, (byte) 0x8b, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x0b };

    private static final double PERSPECTIVE_MULTIPLIER = 16777216.0; // 0.000000059604644775
}

