// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/08/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.DrColor;
import com.aspose.MathUtil;
import java.util.Iterator;


/// <summary>
/// Represents a single CSS declaration block.
/// The names of the values in the collection are case insensitive.
/// Note: don't set shorthand CSS properties! Use individual ones.
/// </summary>
/// <remarks>
/// This class is a wrapper for <see cref="CssDeclarationCollectionBuilder"/> class and should be used in HTML export only.
/// </remarks>
class CssStyle implements Iterable<CssDeclaration>
{
    CssStyle()
    {
        mDeclarationBuilder = new CssDeclarationCollectionBuilder();
    }

    void setIdentifier(String name, String value)
    {
        Debug.assertTrue(StringUtil.hasChars(value));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssIdentifierValue(value)));
    }

    void setString(String name, String value)
    {
        setString(name, value, false);
    }

    void setString(String name, String value, boolean writeEmptyValue)
    {
        Debug.assertTrue(StringUtil.hasChars(value) || writeEmptyValue);
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssStringValue(value)));
    }

    void setNumber(String name, double value)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssNumberValue(value)));
    }

    void setPercentage(String name, double value)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssPercentageValue(value)));
    }

    void setLength(String name, double value, /*CssUnit*/int unit)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssLengthValue(value, unit)));
    }

    void setColor(String name, DrColor value)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, CssHashValue.fromColor(value)));
    }

    void setUri(String name, String uriValue)
    {
        Debug.assertTrue(StringUtil.hasChars(uriValue));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssUriValue(uriValue)));
    }

    void setFunction(String name, String functionName, CssValueList functionArgs)
    {
        Debug.assertTrue(StringUtil.hasChars(functionName));
        Debug.assertTrue(functionArgs != null);
        Debug.assertTrue(functionArgs.getCount() > 0);

        mDeclarationBuilder.addOrReplace(
            new CssSpecifiedDeclaration(name, new CssFunctionValue(functionName, functionArgs)));
    }

    void setList(String name, CssValueList values)
    {
        Debug.assertTrue(values.getCount() > 0);
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, new CssPropertyValue(values)));
    }

    /// <summary>
    /// Sets 'font-family' CSS property.
    /// </summary>
    void setFontFamily(/*params*/ String... fontFamilyNames)
    {
        if (fontFamilyNames.length != 0)
        {
            CssValueList values = new CssValueList();
            for (String fontFamilyName : fontFamilyNames)
                values.add(CssValue.createFontFamilyValue(fontFamilyName));
            mDeclarationBuilder.addOrReplace(
                new CssSpecifiedDeclaration("font-family", new CssFontFamilyPropertyValue(values)));
        }
    }

    /// <summary>
    /// Sets 'background' CSS property.
    /// </summary>
    void setBackground(DrColor color)
    {
        mDeclarationBuilder.addOrReplace(
            new CssSpecifiedDeclaration("background",
                new CssBackgroundPropertyValue(CssHashValue.fromColor(color), null, null, null, null)));
    }

    /// <summary>
    /// Sets 'margin' CSS shorthand property.
    /// </summary>
    void setMargin(CssValue top, CssValue right, CssValue bottom, CssValue left)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("margin-top", top));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("margin-right", right));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("margin-bottom", bottom));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("margin-left", left));
    }

    /// <summary>
    /// Sets 'margin' CSS shorthand property.
    /// </summary>
    void setMargin(double top, double right, double bottom, double left)
    {
        setMargin(new CssLengthValue(top, CssUnit.PT),
            new CssLengthValue(right, CssUnit.PT),
            new CssLengthValue(bottom, CssUnit.PT),
            new CssLengthValue(left, CssUnit.PT));
    }

    /// <summary>
    /// Sets 'padding' CSS shorthand property.
    /// </summary>
    void setPadding(double top, double right, double bottom, double left)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("padding-top",
            new CssLengthValue(top, CssUnit.PT)));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("padding-right",
            new CssLengthValue(right, CssUnit.PT)));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("padding-bottom",
            new CssLengthValue(bottom, CssUnit.PT)));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("padding-left",
            new CssLengthValue(left, CssUnit.PT)));
    }

    /// <summary>
    /// Sets 'size' CSS property.
    /// </summary>
    void setPageSize(double width, double height, /*CssUnit*/int unit)
    {
        CssLengthValue widthValue = new CssLengthValue(width, unit);
        CssPageSizePropertyValue value = MathUtil.areEqual(width, height)
            ? CssPageSizePropertyValue.createLength(widthValue)
            : CssPageSizePropertyValue.createLength(widthValue, new CssLengthValue(height, unit));
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("size", value));
    }

    /// <summary>
    /// Sets 'border' CSS shorthand property.
    /// </summary>
    /// <param name="style">Style value.</param>
    /// <param name="width">Width value, can be <c>null</c>.</param>
    /// <param name="color">Colorvalue, can be <c>null</c>.</param>
    void setBorder(CssValue style, CssValue width, CssValue color)
    {
        CssPropertyDef borderPropertyDef = CssPropertyDefFactory.getPropertyDef("border");
        setBorder(borderPropertyDef, style, width, color);
    }

    /// <summary>
    /// Sets 'border-right' CSS shorthand property.
    /// </summary>
    /// <param name="style">Style value.</param>
    /// <param name="width">Width value, can be <c>null</c>.</param>
    /// <param name="color">Colorvalue, can be <c>null</c>.</param>
    void setBorderRight(CssValue style, CssValue width, CssValue color)
    {
        CssPropertyDef borderPropertyDef = CssPropertyDefFactory.getPropertyDef("border-right");
        setBorder(borderPropertyDef, style, width, color);
    }

    /// <summary>
    /// Sets 'border-bottom' CSS shorthand property.
    /// </summary>
    /// <param name="style">Style value.</param>
    /// <param name="width">Width value, can be <c>null</c>.</param>
    /// <param name="color">Colorvalue, can be <c>null</c>.</param>
    void setBorderBottom(CssValue style, CssValue width, CssValue color)
    {
        CssPropertyDef borderPropertyDef = CssPropertyDefFactory.getPropertyDef("border-bottom");
        setBorder(borderPropertyDef, style, width, color);
    }

    /// <summary>
    /// Sets 'text-decoration' CSS shorthand property.
    /// </summary>
    void setTextDecoration(CssValueList cssValues)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("text-decoration",
            new CssTextDecorationPropertyValue(cssValues)));
    }

    /// <summary>
    /// Sets 'font' CSS shorthand property.
    /// </summary>
    void setFont(CssValue fontSize, CssValue fontFamily)
    {
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("font",
            new CssFontPropertyValue(fontSize, fontFamily)));
    }

    /// <summary>
    /// Sets 'text-decoration' CSS shorthand property.
    /// </summary>
    void setTextDecoration(String value)
    {
        if (StringUtil.hasChars(value))
        {
            mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("text-decoration",
                new CssTextDecorationPropertyValue(new CssIdentifierValue(value))));
        }
    }

    /// <summary>
    /// Sets background linear-gradient CSS property.
    /// </summary>
    void setLinearGradientBackground(CssValueList linearGradientValues)
    {
        CssValueList collection = new CssValueList();
        for (CssValue linearGradientValue : linearGradientValues)
        {
            if (collection.getCount() > 0)
                collection.add(new CssCommaValue());
            collection.add(linearGradientValue);
        }
        mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration("background",
            new CssBackgroundPropertyValue(collection)));
    }

    /// <summary>
    /// Adds properties from another CssStyle.
    /// </summary>
    /// <param name="cssStyle"></param>
    void addPropertiesFrom(CssStyle cssStyle)
    {
        if (cssStyle != null)
        {
            mDeclarationBuilder.addOrReplace(cssStyle.mDeclarationBuilder);
        }
    }

    /// <summary>
    /// Adds the specified declaration to the style replacing a declaration with same property name if any exists.
    /// </summary>
    /// <remarks>
    /// This method has been added to improve performance.
    /// </remarks>
    /// <param name="declaration">CSS declaration.</param> 
    void addOrReplace(CssDeclaration declaration)
    {
        mDeclarationBuilder.addOrReplace(declaration);
    }

    /// <summary>
    /// Creates a CSS representation of the style.
    /// </summary>
    String toCss()
    {
        return (mDeclarationBuilder.getCount() != 0)
            ? mDeclarationBuilder.getDeclarations().toCss()
            : "";
    }


    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public Iterator<CssDeclaration> iterator()
    {
        return mDeclarationBuilder.iterator();
    }

    /// <summary>
    /// Removes the attribute with the specified name from the style.
    /// </summary>
    /// <param name="key"></param>
    void remove(String key)
    {
        mDeclarationBuilder.remove(key);
    }

    CssDeclarationCollection getDeclarations()
    {
        return mDeclarationBuilder.getDeclarations();
    }

    /// <summary>
    /// Sets 'border[-(top|left|right|bottom)]' CSS shorthand property.
    /// </summary>
    /// <param name="borderPropertyDef"></param>
    /// <param name="style">Style value.</param>
    /// <param name="width">Width value, can be <c>null</c>.</param>
    /// <param name="color">Colorvalue, can be <c>null</c>.</param>
    private void setBorder(
        CssPropertyDef borderPropertyDef,
        CssValue style,
        CssValue width,
        CssValue color)
    {
        Debug.assertTrue(style != null);

        CssValueList values = new CssValueList(style);
        if (width != null)
            values.add(width);
        if (color != null)
            values.add(color);
        CssDeclarationCollection declarations = borderPropertyDef.createDeclarations(values, false, false);
        Debug.assertTrue(declarations != null);
        mDeclarationBuilder.addOrReplace(declarations);
    }

    /// <summary>
    /// Gets/sets a property by name.
    /// </summary>
    CssPropertyValue get(String name)
    {
        CssDeclaration declaration = mDeclarationBuilder.get(name);
        return (declaration == null)
                   ? null
                   : declaration.getValue();
    }
    void set(String name, CssPropertyValue value) { mDeclarationBuilder.addOrReplace(new CssSpecifiedDeclaration(name, value)); }

    /// <summary>
    /// Gets the number of attributes contained in the style.
    /// </summary>
    int getCount() { return mDeclarationBuilder.getCount(); }

    /// <summary>
    /// Declaration collection builder, should store only individual CSS properties.
    /// </summary>
    private /*final*/ CssDeclarationCollectionBuilder mDeclarationBuilder;
}

