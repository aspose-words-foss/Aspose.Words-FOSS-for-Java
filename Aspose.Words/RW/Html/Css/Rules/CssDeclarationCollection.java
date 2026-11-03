// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import java.util.Iterator;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import com.aspose.DrColor;
import java.util.Collection;
import com.aspose.ms.System.Collections.SortedList;
import java.util.Map;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents an immutable collection of <see cref="CssDeclaration">CSS declarations</see>.
/// </summary>
/// <remarks>
/// This collection is immutable so declarations stored in it cannot be modified unintentionally.
/// This class does not preserve the order in which declarations are added.
/// </remarks>
class CssDeclarationCollection implements Iterable<CssDeclaration>
{
    /// <summary>
    /// Creates a new empty collection.
    /// </summary>
    CssDeclarationCollection()
    {
        mDeclarations = new CssDeclarationHashtable();
        mDeclarations.makeReadOnly();
    }

    /// <summary>
    /// Creates a new collection containing only one declaration.
    /// </summary>
    /// <param name="declaration">A CSS declaration that should be added to the collection.</param>
    CssDeclarationCollection(CssDeclaration declaration)
    {
        mDeclarations = new CssDeclarationHashtable();
        mDeclarations.add(declaration);
        mDeclarations.makeReadOnly();
    }

    /// <summary>
    /// Creates a new collection containing one or more declarations.
    /// </summary>
    /// <param name="declarations">CSS declaration(s) that should be added to the collection.</param>
    CssDeclarationCollection(/*params*/ CssDeclaration... declarations)
    {
        mDeclarations = new CssDeclarationHashtable();
        mDeclarations.add(declarations);
        mDeclarations.makeReadOnly();
    }

    /// <summary>
    /// Creates a new collection containing the specified declarations.
    /// </summary>
    /// <param name="declarations">
    /// Zero or more instances of <see cref="CssDeclaration"/> that should be added to the collection.
    /// </param>
    CssDeclarationCollection(Iterable<CssDeclaration> declarations)
    {
        mDeclarations = new CssDeclarationHashtable();
        mDeclarations.add(declarations);
        mDeclarations.makeReadOnly();
    }

    /// <summary>
    /// Creates a new collection containing the specified declarations.
    /// This constructor is used for performance and memory optimization in
    /// <see cref="CssDeclarationCollectionBuilder.GetDeclarations"/> method.
    /// You should not call it directly.
    /// </summary>
    /// <param name="declarations">
    /// A hashtable that will be used as a backing field for the new collection. Values are instances of
    /// <see cref="CssDeclaration"/> and keys are corresponding property names (strings). The hashtable contents must not
    /// change after the new collection is created, because the collection must be immutable.
    /// </param>
    CssDeclarationCollection(CssDeclarationHashtable declarations)
    {
        Debug.assertTrue(declarations != null);
        Debug.assertTrue(declarations.isReadOnly());
        mDeclarations = declarations;
    }

    public Iterator<CssDeclaration> iterator()
    {
        return mDeclarations.iterator();
    }

    /// <summary>
    /// Returns an enumerator object that iterates through <see cref="CssDeclaration"/> instances stored in this collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public /*override*/ int hashCode()
    {
        int hashCode = 0;
        for (CssDeclaration declaration : mDeclarations)
        {
            hashCode = (hashCode * 397) ^ declaration.hashCode();
            hashCode = (hashCode * 397) ^ ((int)getFlags(declaration.getProperty()));
        }
        return hashCode;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        return (obj.getClass() == getClass()) && equals((CssDeclarationCollection)obj);
    }

    protected boolean equals(CssDeclarationCollection other)
    {
        if (mDeclarations.getCount() != other.mDeclarations.getCount())
        {
            return false;
        }

        for (CssDeclaration thisDeclaration : mDeclarations)
        {
            CssDeclaration otherDeclaration = other.mDeclarations.get(thisDeclaration.getProperty());
            if (!thisDeclaration.equals(otherDeclaration))
            {
                return false;
            }

            /*CssDeclarationFlags*/int thisFlags = getFlags(thisDeclaration.getProperty());
            /*CssDeclarationFlags*/int otherFlags = other.getFlags(thisDeclaration.getProperty());
            if (thisFlags != otherFlags)
            {
                return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Returns a property length value measured in points if the property exists in the collection
    /// and property value is a correct length value. Otherwise returns <c>double.MinValue</c>.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    double getLength(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        return (declaration != null)
            ? CssUtil.lengthToPoint(declaration.getValue())
            : -Double.MAX_VALUE;
    }

    /// <summary>
    /// Returns a property length value measured in points and indicates whether the value comes from default (user agent)
    /// CSS. If the property doesn't exists in the collection or if the property value is not a correct length value,
    /// the method returns <see cref="CssLength.ZeroDefault"/>.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    CssLength getCssLength(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        double value = (declaration != null)
            ? CssUtil.lengthToPoint(declaration.getValue())
            : 0.0;
        boolean isDefault = (declaration == null) || isUserAgent(propertyName);
        return new CssLength(value, !isDefault);
    }

    /// <summary>
    /// Returns a property identifier value if the property exists in the collection
    /// and property value is a correct identifier value. Otherwise returns an empty string.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    String getIdentifier(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        boolean isIdentifier = (declaration != null) && declaration.hasSingleValueOfType(CssValueType.IDENTIFIER);
        return isIdentifier
            ? ((CssIdentifierValue)declaration.getValue().getFirstValue()).getValue()
            : "";
    }

    boolean containsIdentifier(String propertyName, String value)
    {
        return StringUtil.equalsIgnoreCase(getIdentifier(propertyName), value);
    }

    /// <summary>
    /// Returns a property uri value if the property exists in the collection and property value is a correct uri value.
    /// Otherwise returns empty string.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    String getUri(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        boolean isUri = (declaration != null) && declaration.hasSingleValueOfType(CssValueType.URI);
        return isUri
            ? ((CssUriValue)declaration.getValue().getFirstValue()).getValue()
            : "";
    }

    /// <summary>
    /// Returns a property number value if the property exists in the collection
    /// and property value is a correct number value. Otherwise returns <c>double.MinValue</c>.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    double getNumber(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        boolean isNumber = (declaration != null) && declaration.hasSingleValueOfType(CssValueType.NUMBER);
        return isNumber
            ? ((CssNumberValue)declaration.getValue().getFirstValue()).getDoubleValue()
            : -Double.MAX_VALUE;
    }

    /// <summary>
    /// Returns a property percentage value if the property exists in the collection
    /// and property value is a correct percentage value. Otherwise returns <c>double.MinValue</c>.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    double getPercentage(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        boolean isPercentage = (declaration != null) && declaration.hasSingleValueOfType(CssValueType.PERCENTAGE);
        return isPercentage
            ? ((CssPercentageValue)declaration.getValue().getFirstValue()).getDoubleValue()
            : -Double.MAX_VALUE;
    }

    /// <summary>
    /// Returns a property string value if the property exists in the collection
    /// and property value is a correct string value. Otherwise returns an empty string. The returned string has no quotes.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    String getString(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        boolean isString = (declaration != null) && declaration.hasSingleValueOfType(CssValueType.STRING);
        return isString
            ? ((CssStringValue)declaration.getValue().getFirstValue()).getValue()
            : "";
    }

    /// <summary>
    /// Returns the property value as a string if the property exists in the collection and the property value
    /// is a valid identifier or string. Otherwise returns an empty string. The returned string has no enclosing quotes.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    String getIdentifierOrString(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        if ((declaration != null) && (declaration.getValue().getCount() == 1))
        {
            CssValue cssValue = declaration.getValue().getFirstValue();
            if ((cssValue.getValueType() == CssValueType.IDENTIFIER) || (cssValue.getValueType() == CssValueType.STRING))
            {
                return (String)cssValue.getValue();
            }
        }
        return "";
    }

    /// <summary>
    /// Returns a property color value if the property exists in the collection
    /// and property value is a correct color value. Otherwise returns <c>null</c>.
    /// </summary>
    /// <param name="propertyName">Property name.</param>
    DrColor getColor(String propertyName)
    {
        CssDeclaration declaration = this.get(propertyName);
        return (declaration != null)
            ? declaration.getValue().parseAsColor()
            : null;
    }

    /// <summary>
    /// Returns font name accordingly to the value of 'font-family' CSS property using the font provider to check fonts
    /// for availability.
    /// </summary>
    /// <returns>
    /// Returns font name or empty string in case of missing declaration and other inconsistencies.
    /// </returns>
    String getFontName(
        String propertyName,
        CssFontFaceProvider cssFontFaceProvider,
        DocumentFontProvider fontProvider) throws Exception
    {
        CssDeclaration fontFamilyDeclaration = this.get(propertyName);
        if (fontFamilyDeclaration == null)
        {
            return "";
        }

        if (!(fontFamilyDeclaration.getValue() instanceof CssFontFamilyPropertyValue))
        {
            return "";
        }

        // Check fonts from the "font-family" list for availability and return the name of the first font that is available.
        // Return the last font name from the list in case none of the fonts is available.
        String resolvedFontName = "";
        for (int i = 0; i < fontFamilyDeclaration.getValue().getCount(); i++)
        {
            String requestedFontFamily;
            CssValue fontFamilyValue = fontFamilyDeclaration.getValue().get(i);
            switch (fontFamilyValue.getValueType())
            {
                case CssValueType.IDENTIFIER:
                    requestedFontFamily = getGenericFontFamily(((CssIdentifierValue)fontFamilyValue).getValue());
                    break;
                case CssValueType.STRING:
                    requestedFontFamily = ((CssStringValue)fontFamilyValue).getValue();
                    break;
                default:
                    Debug.fail("Unexpected type of a CSS value.");
                    continue;
            }

            // Here, we are trying to import the requested font from the @font-face CSS rule.
            if (cssFontFaceProvider != null)
            {
                cssFontFaceProvider.importFontFaceToDocument(requestedFontFamily);
            }

            // FIX WORDSNET-17383 - Previously, we used FontPal.FindInstalledFontName to resolve font names.
            // Now we use font provider and font settings. It's useful if customer wants to control font substitution
            // while loading document.
            String resolvedFont = fontProvider.getFontNameForHtml(requestedFontFamily);
            if (resolvedFont != null)
            {
                resolvedFontName = resolvedFont;
                break;
            }

            resolvedFontName = requestedFontFamily;
        }
        return resolvedFontName;
    }

    String getFontName(
        CssFontFaceProvider cssFontFaceProvider,
        DocumentFontProvider fontProvider) throws Exception
    {
        return getFontName("font-family", cssFontFaceProvider, fontProvider);
    }

    /// <summary>
    /// Gets a copy of these declarations with all longhand properties substituted with their shorthand counterparts
    /// where possible.
    /// </summary>
    CssDeclarationCollection getShorthandVersion()
    {
        Collection<CssShorthandPropertyDef> shorthandProperties = CssPropertyDefFactory.getShorthandProperties();

        // MEMORY. Normally only a small subset of declarations is reducible to shorthands. We considerably decrease number
        // of memory allocations if we check declarations for reducibility before actually trying to reduce them.
        boolean isReducible = false;
        for (CssShorthandPropertyDef shorthandProp : shorthandProperties)
        {
            if (shorthandProp.canReduce(this))
            {
                isReducible = true;
                break;
            }
        }
        if (!isReducible)
        {
            return this;
        }

        CssDeclarationCollectionBuilder modifiedCollection = new CssDeclarationCollectionBuilder(this);
        for (CssShorthandPropertyDef shorthandProp : shorthandProperties)
        {
            shorthandProp.reduce(modifiedCollection);
        }
        return modifiedCollection.getDeclarations();
    }

    /// <summary>
    /// Removes each of the specified properties from the declarations if the property's value is user-agent default.
    /// </summary>
    CssDeclarationCollection withoutUserAgentDeclarations(String[] userAgentPropertiesToRemove)
    {
        if ((userAgentPropertiesToRemove == null) || (userAgentPropertiesToRemove.length == 0))
        {
            return this;
        }

        // The result builder is initialized lazily, because in the most common scenario there's nothing to remove.
        CssDeclarationCollectionBuilder modifiedCollection = null;
        for (String property : userAgentPropertiesToRemove)
        {
            // Remove the property if its value is user-agent default.
            if ((getFlags(property) & CssDeclarationFlags.USER_AGENT) != 0)
            {
                if (modifiedCollection == null)
                {
                    modifiedCollection = new CssDeclarationCollectionBuilder(this);
                }
                modifiedCollection.remove(property);
            }
        }
        return (modifiedCollection != null)
            ? modifiedCollection.getDeclarations()
            : this;
    }

    /// <summary>
    /// Indicates whether this property comes from our User Agent stylesheet.
    /// </summary>
    boolean isUserAgent(String property)
    {
        return mDeclarations.getFlag(property, CssDeclarationFlags.USER_AGENT);
    }

    /// <summary>
    /// Gets all flags that are set for a property.
    /// </summary>
    /*CssDeclarationFlags*/int getFlags(String property)
    {
        return mDeclarations.getFlags(property);
    }

    /// <summary>
    /// Generates text representation of the declarations.
    /// Declarations are ordered by some preferable order and finished by our -aw-* CSS properties.
    /// </summary>
    String toCss()
    {
        SortedList<String, CssDeclaration> sortedList =
            new SortedList<String, CssDeclaration>(new CssPropertyNameComparer());
        for (CssDeclaration declaration : mDeclarations)
        {
            sortedList.add(declaration.getProperty(), declaration);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CssDeclaration> pair : sortedList)
        {
            if (sb.length() > 0)
                msStringBuilder.append(sb, "; ");
            pair.getValue().toCss(sb);
        }
        return sb.toString();
    }

    CssDeclarationHashtable getHashtable()
    {
        Debug.assertTrue(mDeclarations.isReadOnly());
        return mDeclarations;
    }


    /// <summary>
    /// Returns the number of declarations in the collection.
    /// </summary>
    int getCount() { return mDeclarations.getCount(); }

    /// <summary>
    /// Gets a CSS declaration for the specified property.
    /// </summary>
    /// <param name="property">CSS property name.</param>
    /// <returns>CSS declaration, if found; otherwise, null.</returns>
    CssDeclaration get(String property) { return mDeclarations.get(property); }

    /// <summary>
    /// An empty collection of CSS declarations.
    /// </summary>
    static /*final*/ CssDeclarationCollection EMPTY = new CssDeclarationCollection();

    private static String getGenericFontFamily(String value)
    {
        String result;
        switch (gStringSwitchMap.of(msString.toLowerInvariant(value)))
        {
            case /*"serif"*/0:
                result = "Times New Roman";
                break;
            case /*"sans-serif"*/1:
                result = "Arial";
                break;
            case /*"cursive"*/2:
                // http://www.kayskreations.net/fonts/fonttb.html
                // http://www.ampsoft.net/webdesign-l/WindowsMacFonts.html
                result = "Comic Sans MS";
                break;
            case /*"fantasy"*/3:
                // At the moment lets default to Arial. No browser-safe defaults.
                result = "Arial";
                break;
            case /*"monospace"*/4:
                result = "Courier New";
                break;
            default:
                // If we cannot recognize the value as a generic family name, we just use it as a font name.
                result = value;
                break;
        }
        return result;
    }

    /// <summary>
    /// The backing collection that stores all declarations. The collection is always read-only.
    /// </summary>
    private /*final*/ CssDeclarationHashtable mDeclarations;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"serif",
		"sans-serif",
		"cursive",
		"fantasy",
		"monospace"
	);

}

