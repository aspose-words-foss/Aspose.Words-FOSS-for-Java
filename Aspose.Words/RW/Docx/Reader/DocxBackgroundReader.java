// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/06/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DrColor;
import com.aspose.StringUtil;


/// <summary>
/// This class allows reading the 17.2.1 background (Document Background) element.
/// </summary>
/*static*/ class DocxBackgroundReader
{
	/* Simulation of static class by using private constructor */
	private DocxBackgroundReader()
	{}

    /// <summary>
    /// Reads a DOCX 'w:background' element from the specified reader.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DrColor color = null;
        String themeColor = null;
        String themeShade = null;
        String themeTint = null;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"color"*/0: // w:color
                    {
                        color = VmlColor.vmlToColor(xmlReader.getValue());
                        // RK Default is white. DOC files do not have this attribute when the color is white.
                        // Let's pretend we don't have it too to make gold ExportImport tests pass.
                        if (DrColor.equals(color, DrColor.WHITE))
                            color = null;
                        break;
                    }
                case /*"themeColor"*/1:
                    themeColor = xmlReader.getValue();
                    break;
                case /*"themeShade"*/2:
                    themeShade = xmlReader.getValue();
                    break;
                case /*"themeTint"*/3:
                    themeTint = xmlReader.getValue();
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }
        xmlReader.moveToElement();

        Shape shape = readShape(reader);
        if (shape == null)
            shape = createDefaultBackgroundShape(reader.getDocument());

        reader.getDocument().setBackgroundShapeSafe(shape);

        if (color != null)
            shape.setShapeAttrInternal(ShapeAttr.FILL_COLOR, color);
        if (themeColor != null)
            shape.setShapeAttrInternal(ShapeAttr.THEME_COLOR, themeColor);
        if (themeShade != null)
            shape.setShapeAttrInternal(ShapeAttr.THEME_SHADE, themeShade);
        if (themeTint != null)
            shape.setShapeAttrInternal(ShapeAttr.THEME_TINT, themeTint);
    }

    /// <summary>
    /// Creates a VML shape for document background.
    /// </summary>
    private static Shape createDefaultBackgroundShape(DocumentBase document)
    {
        return new Shape(document, ShapeType.RECTANGLE);
    }

    /// <summary>
    /// Reads a background shape.
    /// </summary>
    private static Shape readShape(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        Shape shape = null;
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"background"*/4: // v:background
                    shape = createDefaultBackgroundShape(reader.getDocument());
                    VmlShapeReader.readShape(reader, shape);
                    break;
                case /*"AlternateContent"*/5:
                    shape = readAlternateContent(reader);
                    break;
                case /*"drawing"*/6:
                    shape = (Shape)((INrxDmlReader)reader).readDrawing(new RunPr());
                    reader.getDocument().removeChild(shape); // shape is inserting into document during reading
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
        return shape;
    }

    /// <summary>
    /// Reads an alternate content block. Returns a shape of the choice part.
    /// </summary>
    private static Shape readAlternateContent(DocxDocumentReaderBase reader) throws Exception
    {
        Shape shape = null;
        Shape fallbackShape = null;
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("AlternateContent"))
        {
            // Choice and Fallback, are used to provide alternates for specified content.
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Choice"*/7:
                    shape = readChoice(reader);
                    break;
                case /*"Fallback"*/8:
                    fallbackShape = readShape(reader);
                    break;
                default:
                    xmlReader.ignoreElement(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX, xmlReader.getLocalName());
                    break;
            }
        }

        if (fallbackShape != null)
        {
            if (shape == null)
            {
                if (fallbackShape.getMarkupLanguage() == ShapeMarkupLanguage.VML)
                    return fallbackShape;
                shape = createDefaultBackgroundShape(reader.getDocument());
                shape.getRunPr().setAlternateContent(new AlternateContent());
            }
            AlternateContent alternateContent = shape.getRunPr().getAlternateContent();
            Paragraph fallbackContainer = new Paragraph(reader.getDocument());
            fallbackContainer.appendChild(fallbackShape);
            alternateContent.setFallBack(fallbackContainer);
        }
        else
        {
            if (shape != null)
                shape.getRunPr().remove(FontAttr.ALTERNATE_CONTENT);
        }

        return shape;
    }

    /// <summary>
    /// Reads a shape of the choice part of an alternate content block.
    /// </summary>
    private static Shape readChoice(DocxDocumentReaderBase reader) throws Exception
    {
        boolean choiceMatched = false;
        AlternateContent alternateContent = new AlternateContent();
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Requires"*/9:
                    // The Requires attribute specifies a set of space-delimited namespaces that must be 
                    // understood in order to select that choice.
                    String prefix = xmlReader.getValue();
                    if (StringUtil.hasChars(prefix) &&
                        NrxRunReaderBase.isAllowedChoiceRequirement(xmlReader.lookupNamespace(prefix)))
                    {
                        choiceMatched = true;
                        alternateContent.setRequires(prefix);
                    }
                    break;
                default:
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX, xmlReader.getLocalName());
                    break;
            }
        }
        xmlReader.moveToElement();

        if (choiceMatched)
        {
            Shape shape = readShape(reader);
            if (shape == null) // choice may be empty
                shape = createDefaultBackgroundShape(reader.getDocument());
            shape.getRunPr().setAlternateContent(alternateContent);
            return shape;
        }
        else
        {
            xmlReader.ignoreElement();
            return null;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"color",
		"themeColor",
		"themeShade",
		"themeTint",
		"background",
		"AlternateContent",
		"drawing",
		"Choice",
		"Fallback",
		"Requires"
	);

}

