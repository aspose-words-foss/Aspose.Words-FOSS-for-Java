// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.MathUtil;


/// <summary>
/// Class used to write DrawingML 3D properties.
/// </summary>
/*static*/ class Dml3DPropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private Dml3DPropertiesWriter()
	{}

    static void writeScene3D(DmlScene3DProperties scene3DProps, IDmlShapeWriterContext writer,
        boolean isTextEffect) throws Exception
    {
        String prefix = isTextEffect ? "w14" : "a";
        writeScene3D(prefix, scene3DProps, writer, false);
    }

    static void writeScene3D(String prefix, DmlScene3DProperties scene3DProps,
        IDmlShapeWriterContext writer, boolean isThemeWriting) throws Exception
    {
        // Check if we have something to write.
        if (scene3DProps == null)
            return;

        // Do not write properties that are inherited from theme for shapes.
        if (!isThemeWriting && scene3DProps.isTheme())
            return;

        String tagName = msString.format("{0}:scene3d", prefix);
        
        // Child elements have 'a' prefix.
        if ("dgm".equals(prefix))
            prefix = "a";

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(tagName);

        writeScene3DCamera(prefix, scene3DProps.getCamera(), builder, writer.getCompliance());
        writeScene3DLightRig(prefix, scene3DProps.getLightRig(), builder);
        writeScene3DBackdrop(prefix, scene3DProps.getBackdropPlane(), writer);

        DmlExtensionListWriter.write(scene3DProps.getExtensions(), writer);

        builder.endElement(tagName);
    }

    private static void writeScene3DLightRig(String prefix, DmlLightRig lightRig, NrxXmlBuilder builder)
    {
        String tagName = msString.format("{0}:lightRig", prefix);
        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "rig"), DmlEnum.lightRigTypeDml(lightRig.getLightRigType()));
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "dir"), DmlEnum.lightRigDirectionToDml(lightRig.getDirection()));
        writeScene3DRotation(prefix, lightRig.getRotation(), builder);
        builder.endElement(tagName);
    }

    private static void writeScene3DBackdrop(String prefix, DmlBackdropPlane backdropPlane,
        IDmlShapeWriterContext writer) throws Exception
    {
        if (backdropPlane == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        String tagName = msString.format("{0}:backdrop", prefix);

        builder.startElement(tagName);

        DmlPoint3D dmlPoint3D = backdropPlane.getAnchor();
        DmlBasicsWriter.writeXYZ(msString.format("{0}:anchor", prefix), dmlPoint3D.getX(), dmlPoint3D.getY(), dmlPoint3D.getZ(), builder);

        dmlPoint3D = backdropPlane.getNormalVector();
        DmlBasicsWriter.writeDxDyDz(msString.format("{0}:norm", prefix), dmlPoint3D.getX(), dmlPoint3D.getY(), dmlPoint3D.getZ(), builder);

        dmlPoint3D = backdropPlane.getUpVector();
        DmlBasicsWriter.writeDxDyDz(msString.format("{0}:up", prefix), dmlPoint3D.getX(), dmlPoint3D.getY(), dmlPoint3D.getZ(), builder);

        DmlExtensionListWriter.write(backdropPlane.getExtensions(), writer);

        builder.endElement(tagName);
    }

    private static void writeScene3DCamera(String prefix, DmlCamera dmlCamera, NrxXmlBuilder builder, 
        /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = compliance == OoxmlComplianceCore.ISO_STRICT;
        String tagName = msString.format("{0}:camera", prefix);

        builder.startElement(tagName);

        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "prst"), DmlEnum.presetCameraTypeToDml(dmlCamera.getPresetCameraType()));
        
        // Do not write default value.
        if (!MathUtil.areEqual(dmlCamera.getFovAngle().getValueInDegrees(), 180.0d))
            builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "fov"), dmlCamera.getFovAngle().getValue());

        // Do not write default value.
        if (!MathUtil.areEqual(dmlCamera.getZoom(), 1.0d))
            builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "zoom"), 
                DmlPercentageUtil.toPercentOrDmlPercent(dmlCamera.getZoom(), isIsoStrict));

        writeScene3DRotation(prefix, dmlCamera.getRotation(), builder);

        builder.endElement(tagName);
    }

    private static void writeScene3DRotation(String prefix, DmlRotation3D rotation, NrxXmlBuilder builder)
    {
        if (rotation == null)
            return;

        String tagName = msString.format("{0}:rot", prefix);

        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "lat"), rotation.getLatitude().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "lon"), rotation.getLongitude().getValue());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "rev"), rotation.getRevolution().getValue());
        builder.endElement(tagName);
    }

    static void writeShape3D(DmlShape3DProperties shape3DProps, IDmlShapeWriterContext writer,
        boolean isTextEffect) throws Exception
    {
        String prefix = isTextEffect ? "w14" : "a";
        writeShape3D(prefix, shape3DProps, writer, false);
    }

    static void writeShape3D(String prefix, DmlShape3DProperties shape3DProps,
        IDmlShapeWriterContext writer, boolean isThemeWriting) throws Exception
    {
        // Check if we have something to write.
        if (shape3DProps == null)
            return;

        // Do not write properties that are inherited from theme for shapes.
        if (!isThemeWriting && shape3DProps.isTheme())
            return;

        String partTagName = ("w14".equals(prefix)) ? "props3d" : "sp3d";
        String tagName = msString.format("{0}:{1}", prefix, partTagName);

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);
        builder.writeAttributeIfNotZero(DmlNamespaceUtil.getAttrName(prefix, "extrusionH"), shape3DProps.getExtrusionHeight());
        builder.writeAttributeIfNotZero(DmlNamespaceUtil.getAttrName(prefix, "contourW"), shape3DProps.getContourWidth());
        builder.writeAttributeIfNotDefault(DmlNamespaceUtil.getAttrName(prefix, "prstMaterial"), 
            DmlEnum.presetMaterialToDml(shape3DProps.getPresetMaterial()), "warmMatte");
        builder.writeAttributeIfNotZero(DmlNamespaceUtil.getAttrName(prefix, "z"), shape3DProps.getZ());

        writeBevel(prefix, "bevelT", shape3DProps.getBevelTop(), builder);
        writeBevel(prefix, "bevelB", shape3DProps.getBevelBottom(), builder);
        writeExtrusionColor(prefix, shape3DProps.getExtrusionColor(), writer);
        writeContourClr(prefix, shape3DProps.getContourColor(), writer);

        DmlExtensionListWriter.write("a", shape3DProps.getExtensions(), writer);

        builder.endElement(tagName);
    }

    private static void writeExtrusionColor(String prefix, DmlColor extrusionColor, IDmlShapeWriterContext writer)
    {
        if (extrusionColor == null)
            return;
        
        String tagName = msString.format("{0}:extrusionClr", prefix);

        writer.getBuilder().startElement(tagName);
        DmlColorWriter.write(prefix, extrusionColor, writer);
        writer.getBuilder().endElement(tagName);
    }

    private static void writeContourClr(String prefix, DmlColor contourColor, IDmlShapeWriterContext writer)
    {
        if (contourColor == null)
            return;

        String tagName = msString.format("{0}:contourClr", prefix);

        writer.getBuilder().startElement(tagName);
        DmlColorWriter.write(prefix,contourColor, writer);
        writer.getBuilder().endElement(tagName);
    }

    private static void writeBevel(String prefix, String elementName, DmlBevel bevel, NrxXmlBuilder builder)
    {
        if (bevel == null)
            return;
        
        String tagName = msString.format("{0}:{1}", prefix, elementName);
        
        builder.startElement(tagName);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "w"), bevel.getWidth());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "h"), bevel.getHeight());
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "prst"), DmlEnum.bevelPresetTypeToDml(bevel.getBevelPresetType()));
        builder.endElement(tagName);
    }
}

