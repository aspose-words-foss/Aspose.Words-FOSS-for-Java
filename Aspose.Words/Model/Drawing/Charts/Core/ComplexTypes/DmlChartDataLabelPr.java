// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.Guid;
import com.aspose.RandomUtil;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.IntList;
import com.aspose.StringUtil;


/// <summary>
/// Container of data label properties.
/// </summary>
class DmlChartDataLabelPr implements Cloneable
{
    private DmlChartDataLabelPr()
    {
    	this(null);
	
    }

    DmlChartDataLabelPr(DmlChart chart)
    {
    	this(null, chart);
	
    }

    DmlChartDataLabelPr(DmlChartDataLabelPr parentBag, DmlChart chart)
    {
    	this(parentBag, ((chart == null) ? null : chart.getDocument()),
            ((chart != null) && (chart.getChartType() == DmlChartType.CHART_EX_CHART)));
	
    }

    DmlChartDataLabelPr(DmlChartDataLabelPr parentBag, DocumentBase doc, boolean isChartEx)
    {
        DmlChartDataLabelPr parentPr = (parentBag == null) ? getDefaults(doc, isChartEx) : parentBag;
        mPropertyBag.setParentBagProvider(new DmlChartDataLabelPrParentBagProvider(parentPr));
    }

    DmlChartDataLabelPr deepClone()
    {
        DmlChartDataLabelPr lhs = (DmlChartDataLabelPr)memberwiseClone();
        lhs.mPropertyBag = mPropertyBag.deepClone();

        DmlChartManualLayout layout = (DmlChartManualLayout)getDirectProperty(DmlChartDataLabelAttrs.LAYOUT);
        if (layout != null)
            lhs.setProperty(DmlChartDataLabelAttrs.LAYOUT, layout.deepClone());

        DmlChartNumFormat format = (DmlChartNumFormat)getDirectProperty(DmlChartDataLabelAttrs.NUM_FMT);
        if (format != null)
            lhs.setProperty(DmlChartDataLabelAttrs.NUM_FMT, format.deepClone());

        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartDataLabelAttrs.SP_PR);
        if (spPr != null)
            lhs.setProperty(DmlChartDataLabelAttrs.SP_PR, spPr.deepClone());

        DmlChartTx tx = (DmlChartTx)getDirectProperty(DmlChartDataLabelAttrs.TX);
        if (tx != null)
            lhs.setProperty(DmlChartDataLabelAttrs.TX, tx.deepClone());

        DmlChartTxPr txPr = (DmlChartTxPr)getDirectProperty(DmlChartDataLabelAttrs.TX_PR);
        if (txPr != null)
            lhs.setProperty(DmlChartDataLabelAttrs.TX_PR, txPr.deepClone());

        DmlChartSpPr leaderLines = (DmlChartSpPr)getDirectProperty(DmlChartDataLabelAttrs.LEADER_LINES);
        if (leaderLines != null)
            lhs.setProperty(DmlChartDataLabelAttrs.LEADER_LINES, leaderLines.deepClone());

        StringToObjDictionary<DmlExtension> extensions =
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartDataLabelAttrs.EXTENSIONS);
        if (extensions != null)
            lhs.setProperty(DmlChartDataLabelAttrs.EXTENSIONS, DmlExtensionListSource.cloneExtensions(extensions));

        return lhs;
    }

    /// <summary>
    /// Clears contents of this property collection.
    /// </summary>
    void clear()
    {
        mPropertyBag.removeAll();
        mPropertyBag.setExtensionProperties(null);
    }

    /// <summary>
    /// Returns a separator.
    /// </summary>
    /// <remarks>
    /// If the separator is not specified, returns the default separator. In case of using a pie chart,
    /// the default separator is a new line.
    /// </remarks>
    String getSeparator(boolean isPieChart)
    {
        // WORDSNET-12467 According to specification there is an exception, when in pie chart 
        // category name and percentage value are displayed, new line is used as default separator,
        // in all other cases coma is used.
        // WORDSNET-16520 If a pie chart is used, and a separator is directly specified, the specified separator
        // must be used. The separator can be specified in the dLbls element or directly in the data label. 
        if (isPieChart &&
            getShowCategoryAndPercentageOnly() &&
            !isPropertySpecified(DmlChartDataLabelAttrs.SEPARATOR) &&
            !isParentPropertySpecified(DmlChartDataLabelAttrs.SEPARATOR))
            return ControlChar.CR_LF;

        return (String)getProperty(DmlChartDataLabelAttrs.SEPARATOR);
    }

    void setProperty(/*DmlChartDataLabelAttrs*/int attr, Object value)
    {
        mPropertyBag.setProperty((int)attr, value);

        // Set extension properties.
        if ((attr == DmlChartDataLabelAttrs.EXTENSIONS) && (value != null))
        {
            DmlExtension extension = ((StringToObjDictionary<DmlExtension>)value).get(DmlExtensionUri.DATA_LABELS);
            if ((extension != null) && (extension.getDataLabelPr() != null))
                mPropertyBag.setExtensionProperties(extension.getDataLabelPr().mPropertyBag);
        }
    }

    /// <summary>
    /// Sets the property to the specified value if the property is not defined, i.e. if parent value is used.
    /// </summary>
    void setPropertyIfNotDefined(/*DmlChartDataLabelAttrs*/int attribute, Object value)
    {
        if (!mPropertyBag.isPropertySpecified((int)attribute))
            setProperty(attribute, value);
    }

    Object getProperty(/*DmlChartDataLabelAttrs*/int attr)
    {
        return mPropertyBag.getProperty((int)attr);
    }

    Object getDirectProperty(/*DmlChartDataLabelAttrs*/int attr)
    {
        return mPropertyBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Returns property from extension properties.
    /// </summary>
    /// <param name="attr">The specified property</param>
    /// <returns>Specified property, if extension is set and extension properties is specified. Otherwise - null</returns>
    Object getExtensionProperty(/*DmlChartDataLabelAttrs*/int attr)
    {
        return (mPropertyBag.getExtensionProperties() != null)
            ? mPropertyBag.getExtensionProperties().getDirectProperty((int)attr)
            : null;
    }

    /// <summary>
    /// Sets value of the specified extension property.
    /// </summary>
    void setExtensionProperty(/*DmlChartDataLabelAttrs*/int attr, Object value)
    {
        createExtensionPropertyBagIfNone();
        mPropertyBag.getExtensionProperties().setProperty((int)attr, value);
    }

    /// <summary>
    /// Removes the specified property.
    /// </summary>
    void removeProperty(/*DmlChartDataLabelAttrs*/int attr)
    {
        mPropertyBag.remove((int)attr);
    }

    /// <summary>
    /// Removes the specified extension property.
    /// </summary>
    void removeExtensionProperty(/*DmlChartDataLabelAttrs*/int attr)
    {
        if (mPropertyBag.getExtensionProperties() != null)
            mPropertyBag.getExtensionProperties().remove((int)attr);
    }

    /// <summary>
    /// Creates uniqueId extension of a label if it does not exist yet.
    /// </summary>
    void createUniqueIdExtensionIfNone()
    {
        StringToObjDictionary<DmlExtension> extensions = getOrCreateExtensions();
        DmlExtension extension = DmlExtension.getOrCreateExtension(extensions, DmlExtensionUri.UNIQUE_ID);

        if (Guid.equals(extension.getDataLabelId(), Guid.Empty))
        {
            Object index = getProperty(DmlChartDataLabelAttrs.IDX);
            if (index == null)
                index = 0;

            extension.setDataLabelId(RandomUtil.newGuid(52731 + (/*int*/Integer)index));
        }
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which is set directly.
    /// </summary>
    /// <param name="attr">The specified property</param>
    /// <returns>"True", if the property is set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlChartDataLabelAttrs*/int attr)
    {
        return mPropertyBag.isPropertySpecified((int)attr);
    }

    /// <summary>
    /// Checks if the specified property is set directly in the parent bag provider.
    /// </summary>
    /// <param name="attr">The specified property</param>
    /// <returns>"True", if the property is set directly in the parent bag provider, "false" otherwise</returns>
    boolean isParentPropertySpecified(/*DmlChartDataLabelAttrs*/int attr)
    {
        if (mPropertyBag.getParentBagProvider() == null)
            return false;

        if (isPropertySpecified(attr))
            return true;

        return mPropertyBag.getParentBagProvider().getParentBag().isPropertySpecified((int)attr);
    }

    /// <summary>
    /// Gets values of the specified attributes from the parent property bag and puts them into the current bag if
    /// the attributes are not defined directly.
    /// </summary>
    void expandParentProperties(/*DmlChartDataLabelAttrs*/int[] attributes)
    {
        for (/*DmlChartDataLabelAttrs*/int attribute : attributes)
        {
            if (getDirectProperty(attribute) == null)
                setProperty(attribute, getProperty(attribute));
        }
    }

    /// <summary>
    /// Gets values of the specified attributes from the parent property bag and puts them into the current extension
    /// bag if the attributes are not defined directly.
    /// </summary>
    void expandParentExtensionProperties(/*DmlChartDataLabelAttrs*/int[] attributes)
    {
        for (/*DmlChartDataLabelAttrs*/int attribute : attributes)
        {
            if (getExtensionProperty(attribute) == null)
            {
                Object parentValue = getProperty(attribute);
                if (parentValue != null)
                    setExtensionProperty(attribute, parentValue);
            }
        }
    }

    /// <summary>
    /// Removes the specified attributes from the property bag if their values are the same as in the parent bag.
    /// </summary>
    void collapseParentProperties(/*DmlChartDataLabelAttrs*/int[] attributes)
    {
        for (/*DmlChartDataLabelAttrs*/int attribute : attributes)
        {
            Object value = getDirectProperty(attribute);
            if (value != null)
            {
                Object parentValue = mPropertyBag.getParentBagProvider().getParentBag().getProperty((int)attribute);
                if ((parentValue != null) && parentValue.equals(value))
                    mPropertyBag.remove((int)attribute);
            }
        }
    }

    /// <summary>
    /// Generates default value for the property <see cref="DmlChartDataLabelAttrs.Tx"/>, if it is not defined yet,
    /// to display information specified by the ShowXXX properties. MS Word needs this property written when the
    /// <see cref="DmlChartDataLabelAttrs.ShowDataLabelsRange"/> extension property is set to On.
    /// </summary>
    void generateTx(boolean isCategoryAbscissa)
    {
        if (getDirectProperty(DmlChartDataLabelAttrs.TX) != null)
            return;

        DmlChartTx tx = new DmlChartTx();
        setProperty(DmlChartDataLabelAttrs.TX, tx);

        DmlTextBody reachText = new DmlTextBody();
        tx.setRichText(reachText);

        DmlChartTxPr txPr = (DmlChartTxPr)getProperty(DmlChartDataLabelAttrs.TX_PR);
        if (txPr != null)
        {
            reachText.setProperties(txPr.getBodyPr().deepClone());
            reachText.setTextListStyles(txPr.getLstStyle().deepClone());
            if (txPr.getParagraphs().size() > 0)
                msArrayList.add(reachText.getParagraphs(), txPr.getParagraphs().get(0).deepClone());
        }

        if (reachText.getParagraphs().size() == 0)
            msArrayList.add(reachText.getParagraphs(), new DmlParagraph());

        DmlParagraph paragraph = reachText.getParagraphs().get(0);

        String separator = (String)getProperty(DmlChartDataLabelAttrs.SEPARATOR) + " ";

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE))
            addDmlField(paragraph, CELL_RANGE_FIELD_TYPE, CELL_RANGE_FIELD_CODE, separator);

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME))
            addDmlField(paragraph, SERIES_FIELD_TYPE, SERIES_FIELD_CODE, separator);

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME))
        {
            if (isCategoryAbscissa)
                addDmlField(paragraph, CATEGORY_FIELD_TYPE, CATEGORY_FIELD_CODE, separator);
            else
                addDmlField(paragraph, X_VALUE_FIELD_TYPE, X_VALUE_FIELD_CODE, separator);
        }

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_VAL))
            addDmlField(paragraph, VALUE_FIELD_TYPE, VALUE_FIELD_CODE, separator);

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE))
            addDmlField(paragraph, BUBBLE_SIZE_FIELD_TYPE, BUBBLE_SIZE_FIELD_CODE, separator);

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_PERCENT))
            addDmlField(paragraph, PERCENTAGE_FIELD_TYPE, PERCENTAGE_FIELD_CODE, separator);
    }

    /// <summary>
    /// Creates a <see cref="DmlTextField"/> with the specified property values and adds it into the paragraph.
    /// A separator run is also created if the paragraph already contains children.
    /// </summary>
    private static void addDmlField(DmlParagraph paragraph, String type, String text, String separator)
    {
        if (paragraph.getElements().size() > 0)
        {
            DmlRun run = new DmlRun();
            run.setParagraph(paragraph);
            paragraph.getElements().add(run);
            run.setText(separator);
        }

        DmlTextField field = new DmlTextField();
        field.setParagraph(paragraph);
        paragraph.getElements().add(field);

        field.setType(type);
        field.setText(text);
        field.setId(RandomUtil.newGuid(paragraph.getElements().size()));
    }

    /// <summary>
    /// Creates a property bag for extension properties if it does not exist yet.
    /// </summary>
    private void createExtensionPropertyBagIfNone()
    {
        if (mPropertyBag.getExtensionProperties() != null)
            return;

        StringToObjDictionary<DmlExtension> extensions = getOrCreateExtensions();

        DmlExtension extension = new DmlExtension(DmlExtensionUri.DATA_LABELS, null);
        extensions.set(DmlExtensionUri.DATA_LABELS, extension);
        extension.setDataLabelPr(new DmlChartDataLabelPr());
        mPropertyBag.setExtensionProperties(extension.getDataLabelPr().mPropertyBag);
    }

    /// <summary>
    /// Gets an extensions dictionary of this label properties collection. Creates the dictionary if it does not
    /// exist yet.
    /// </summary>
    private StringToObjDictionary<DmlExtension> getOrCreateExtensions()
    {
        StringToObjDictionary<DmlExtension> extensions =
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartDataLabelAttrs.EXTENSIONS);

        if (extensions == null)
        {
            extensions = new StringToObjDictionary<DmlExtension>();
            mPropertyBag.setProperty((int)DmlChartDataLabelAttrs.EXTENSIONS, extensions);
        }

        return extensions;
    }

    /// <summary>
    /// If ShowDataLabelsRange is On, MS Word generates and writes Tx object with fields for ShowXXX properties.
    /// This method returns <c>true</c> if the specified object is such default Tx generated by Word.
    /// </summary>
    private boolean isDefaultTx(DmlChartTx tx)
    {
        DmlTextBody reachText = tx.getRichText();
        if ((tx.getTxType() != DmlChartTxType.RICH_TEXT) ||
            (tx.getStrRef() != null) ||
            (tx.getFormula() != null) ||
            !reachText.getProperties().isEmpty() ||
            !reachText.getTextListStyles().isEmpty() ||
            reachText.getParagraphs().size() != 1)
            return false;

        String separator = (String)getProperty(DmlChartDataLabelAttrs.SEPARATOR) + " ";
        DmlParagraph paragraph = reachText.getParagraphs().get(0);
        int runIndex = 0;

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE))
        {
            if (!checkTxRun(paragraph.getElements(), runIndex, CELL_RANGE_FIELD_TYPE, CELL_RANGE_FIELD_CODE, separator))
                return false;
            runIndex += (runIndex == 0) ? 1 : 2;
        }

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME))
        {
            if (!checkTxRun(paragraph.getElements(), runIndex, SERIES_FIELD_TYPE, SERIES_FIELD_CODE, separator))
                return false;
            runIndex += (runIndex == 0) ? 1 : 2;
        }

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME))
        {
            if (!checkTxRun(paragraph.getElements(), runIndex, X_VALUE_FIELD_TYPE, X_VALUE_FIELD_CODE, separator) &&
                !checkTxRun(paragraph.getElements(), runIndex, CATEGORY_FIELD_TYPE, CATEGORY_FIELD_CODE, separator))
                return false;
            runIndex += (runIndex == 0) ? 1 : 2;
        }

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_VAL))
        {
            if (!checkTxRun(paragraph.getElements(), runIndex, VALUE_FIELD_TYPE, VALUE_FIELD_CODE, separator))
                return false;
            runIndex += (runIndex == 0) ? 1 : 2;
        }

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE))
        {
            if (!checkTxRun(paragraph.getElements(), runIndex, BUBBLE_SIZE_FIELD_TYPE, BUBBLE_SIZE_FIELD_CODE, separator))
                return false;
            runIndex += (runIndex == 0) ? 1 : 2;
        }

        if ((/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_PERCENT))
        {
            if (!checkTxRun(paragraph.getElements(), runIndex, PERCENTAGE_FIELD_TYPE, PERCENTAGE_FIELD_CODE, separator))
                return false;
            runIndex += (runIndex == 0) ? 1 : 2;
        }

        return paragraph.getElements().size() == runIndex;
    }

    /// <summary>
    /// Checks that the paragraph element list contains a <see cref="DmlTextField"/> with a separator before if
    /// necessary at the specified position with the specified property values.
    /// </summary>
    private boolean checkTxRun(ArrayList<DmlParagraphTextElementBase> elements, int runIndex,
        String expectedFieldType, String expectedFieldText, String separator)
    {
        if (elements.size() <= runIndex)
            return false;

        // Only the first field has no separator before it. Subsequent ones have.
        if (runIndex > 0)
        {
            DmlRun run = ms.as(elements.get(runIndex), DmlRun.class);
            if ((run == null) || hasRunProperties(run) || (!msString.equals(run.getText(), separator)))
                return false;

            runIndex++;
            if (elements.size() <= runIndex)
                return false;
        }

        DmlTextField field = ms.as(elements.get(runIndex), DmlTextField.class);
        return
            (field != null) &&
            (msString.equals(field.getType(), expectedFieldType)) &&
            (msString.equals(field.getText(), expectedFieldText)) &&
            !hasRunProperties(field);
    }

    /// <summary>
    /// Returns <c>true</c> if the specified DML paragraph element has non-default run formatting defined.
    /// </summary>
    private static boolean hasRunProperties(DmlParagraphTextElementBase element)
    {
        DmlRunProperties properties = element.getRunProperties();
        return
            ((properties.getEffects() != null) && (properties.getEffects().getCount() > 0)) ||
            ((properties.getExtensions() != null) && (properties.getExtensions().getCount() > 0)) ||
            // Word may write these properties into chart XML, let's just ignore them for now.
            properties.hasNonDefaultFormatting(
                new int[] { (int)DmlRunPropertiesIds.BASELINE, (int)DmlRunPropertiesIds.LANGUAGE }) ||
            !MathUtil.areEqual(properties.getBaseline(), 0.0);
    }

    /// <summary>
    /// Gets defaults depending on the document version.
    /// </summary>
    /// <param name="doc">The specified <see cref="DocumentBase"/></param>
    /// <param name="isChartEx">Indicates that the parent chart is of the
    /// http://schemas.microsoft.com/office/drawing/2014/chartex schema [MS-ODRAWXML]. Charts of the schema are
    /// introduced in MS Office 2016.</param>
    /// <returns>The defaults for data label properties</returns>
    private static DmlChartDataLabelPr getDefaults(DocumentBase doc, boolean isChartEx)
    {
        if (isChartEx)
            return G_CHART_EX_DEFAULTS;

        // WORDSNET-20873 The defaults depend on the document version (7.2.2.2 AppVersion (Application Version)). 
        // The version in CompatibilityOptions does not affect the defaults.
        return DmlChartUtil.isMsWord2007OrLower(doc) ? G_DEFAULTS : G_DEFAULTS_AFTER_2007;
    }

    /// <summary>
    /// Returns true if only category name and percentage value are displayed by this data label properties.
    /// Special rules must be applied in this case for Pie charts.
    /// </summary>
    private boolean getShowCategoryAndPercentageOnly()
    {
        return
            (/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME) &&
            (/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_PERCENT) &&
            !(/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE) &&
            !(/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME) &&
            !(/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_VAL);
    }

    int getCount() { return mPropertyBag.getCount(); }

    /// <summary>
    /// Returns <c>true</c> if this property collection redefines any properties of the parent collection.
    /// </summary>
    /// <dev>
    /// Now the property may not work correctly for complex attributes Tx and TxPr since the corresponding classes
    /// do not override the <see cref="object.Equals(object)"/> method.
    /// </dev>
    boolean hasNonDefaultFormatting()
    {
        IntList attributesToIgnore = new IntList();

        attributesToIgnore.add((int)DmlChartDataLabelAttrs.IDX); // ignore since each label has such property defined
        attributesToIgnore.add((int)DmlChartDataLabelAttrs.TX); // checked separately below
        attributesToIgnore.add((int)DmlChartDataLabelAttrs.EXTENSIONS); // checked separately below

        // Ignore empty leader lines properties.
        DmlChartSpPr leaderLines = (DmlChartSpPr)getDirectProperty(DmlChartDataLabelAttrs.LEADER_LINES);
        if ((leaderLines != null) && leaderLines.isEmpty())
            attributesToIgnore.add((int)DmlChartDataLabelAttrs.LEADER_LINES);

        // Ignore empty SpPr properties.
        DmlChartSpPr spPr = (DmlChartSpPr)getDirectProperty(DmlChartDataLabelAttrs.SP_PR);
        if ((spPr != null) && spPr.isEmpty())
            attributesToIgnore.add((int)DmlChartDataLabelAttrs.SP_PR);

        // Ignore empty TxPr properties.
        DmlChartTxPr txPr = (DmlChartTxPr)getDirectProperty(DmlChartDataLabelAttrs.TX_PR);
        if ((txPr != null) && txPr.isEmpty())
            attributesToIgnore.add((int)DmlChartDataLabelAttrs.TX_PR);

        if (mPropertyBag.hasNonDefaultFormatting(attributesToIgnore.toArray(), null))
            return true;

        // If ShowDataLabelsRange is ON, MS Word generates and writes Tx object with fields for each ShowXXX
        // property set to On. Let's recognize such Tx values and treat as default formatting.
        DmlChartTx tx = (DmlChartTx)getDirectProperty(DmlChartDataLabelAttrs.TX);
        if (tx != null)
        {
            // It seems Word generates Tx only if ShowDataLabelsRange is On.
            if (!(/*boolean*/Boolean)getProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE))
                return true;

            // If Tx is defined in parent, this Tx may override it, at this case the collection has non-default
            // formatting.
            Object parentTx =
                mPropertyBag.getParentBagProvider().getParentBag().getDirectProperty((int)DmlChartDataLabelAttrs.TX);
            if ((parentTx != null) && !tx.equals(parentTx)) // Now Equals is not defined, it checks references only.
                return true;

            if (!isDefaultTx(tx))
                return true;
        }

        IDmlHierarchicalPropertyBag extensionProperties = mPropertyBag.getExtensionProperties();
        IDmlHierarchicalPropertyBag parentExtensionProperties =
            mPropertyBag.getParentBagProvider().getParentBag().getExtensionProperties();
        if ((extensionProperties != null) &&
            extensionProperties.hasNonDefaultFormatting(G_EXTENSION_ATTRIBUTES_TO_IGNORE, parentExtensionProperties))
            return true;

        StringToObjDictionary<DmlExtension> extensions =
            (StringToObjDictionary<DmlExtension>)getDirectProperty(DmlChartDataLabelAttrs.EXTENSIONS);

        if (extensions != null)
        {
            for (String uri : extensions.getKeys())
            {
                // Ignore UniqueId since it seems it has no any useful information.
                // Ignore DataLabels since it is mPropertyBag.ExtensionProperties: already compared.
                // If another extension exists, just consider that the extensions are different (no way to
                // compare them now).
                if (!StringUtil.equalsOrdinalIgnoreCase(uri, DmlExtensionUri.DATA_LABELS) &&
                    !StringUtil.equalsOrdinalIgnoreCase(uri, DmlExtensionUri.UNIQUE_ID))
                    return true;
            }
        }

        return false;
    }

    private IDmlHierarchicalPropertyBag mPropertyBag = new DmlHierarchicalPropertyBag();

    /// <summary>
    /// Gets defaults for data labels if the version of the MS Word is higher than Word2007.
    /// </summary>
    /// <remarks>
    /// MS Word renders the series name, category name, value , bubble size (only if chart type is "BubbleChart"), 
    /// percentages (only if chart type is "PieChart") if data labels are specified for the chart or for the series and 
    /// properties are not set directly and the document version is higher than "12.0000". 
    /// </remarks>
    private static DmlChartDataLabelPr getDefaultsAfter2007()
    {
        DmlChartDataLabelPr defaults = getDefaults();

        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, true);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME, true);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, true);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_PERCENT, true);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME, true);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_VAL, true);

        return defaults;
    }

    /// <summary>
    /// Gets defaults for data labels of new chart types of Word 2016.
    /// </summary>
    private static DmlChartDataLabelPr getChartExDefaults()
    {
        DmlChartDataLabelPr defaults = getDefaults();

        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_VAL, true);

        return defaults;
    }

    private static DmlChartDataLabelPr getDefaults()
    {
        DmlChartDataLabelPr defaults = new DmlChartDataLabelPr();

        defaults.setProperty(DmlChartDataLabelAttrs.DELETE, false);
        defaults.setProperty(DmlChartDataLabelAttrs.D_LBL_POS, DEFAULT_POSITION);
        defaults.setProperty(DmlChartDataLabelAttrs.IDX, 0);
        defaults.setProperty(DmlChartDataLabelAttrs.LAYOUT, null);
        defaults.setProperty(DmlChartDataLabelAttrs.NUM_FMT, null);
        defaults.setProperty(DmlChartDataLabelAttrs.SEPARATOR, ",");
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_PERCENT, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_VAL, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE, false);
        defaults.setProperty(DmlChartDataLabelAttrs.SP_PR, null);
        defaults.setProperty(DmlChartDataLabelAttrs.TX, null);
        defaults.setProperty(DmlChartDataLabelAttrs.TX_PR, null);
        defaults.setProperty(DmlChartDataLabelAttrs.LEADER_LINES, null);
        defaults.setProperty(DmlChartDataLabelAttrs.X_FOR_SAVE, false);
        defaults.setProperty(DmlChartDataLabelAttrs.EXTENSIONS, null);

        return defaults;
    }

    private static /*final*/ DmlChartDataLabelPr G_DEFAULTS_AFTER_2007;
    private static /*final*/ DmlChartDataLabelPr G_CHART_EX_DEFAULTS;
    private static /*final*/ DmlChartDataLabelPr G_DEFAULTS;

    private static /*final*/ int[] G_EXTENSION_ATTRIBUTES_TO_IGNORE = { (int)DmlChartDataLabelAttrs.X_FOR_SAVE };

    private static class DmlChartDataLabelPrParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        DmlChartDataLabelPrParentBagProvider(DmlChartDataLabelPr parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPropertyBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlChartDataLabelPrParentBagProvider clone = (DmlChartDataLabelPrParentBagProvider)memberwiseClone();
            if (mParentPr != null)
                clone.mParentPr = mParentPr.deepClone();
            return clone;
        }

        private DmlChartDataLabelPr mParentPr;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
    }

    static final /*ChartDataLabelPosition*/int DEFAULT_POSITION = (/*ChartDataLabelPosition*/int)(-1);

    private static final String CELL_RANGE_FIELD_TYPE = "CELLRANGE";
    private static final String CELL_RANGE_FIELD_CODE = "[CELLRANGE]";
    private static final String SERIES_FIELD_TYPE = "SERIESNAME";
    private static final String SERIES_FIELD_CODE = "[SERIES NAME]";
    private static final String CATEGORY_FIELD_TYPE = "CATEGORYNAME";
    private static final String CATEGORY_FIELD_CODE = "[CATEGORY NAME]";
    private static final String X_VALUE_FIELD_TYPE = "XVALUE";
    private static final String X_VALUE_FIELD_CODE = "[X VALUE]";
    private static final String VALUE_FIELD_TYPE = "VALUE";
    private static final String VALUE_FIELD_CODE = "[VALUE]";
    private static final String BUBBLE_SIZE_FIELD_TYPE = "BUBBLESIZE";
    private static final String BUBBLE_SIZE_FIELD_CODE = "[BUBBLESIZE]";
    private static final String PERCENTAGE_FIELD_TYPE = "PERCENTAGE";
    private static final String PERCENTAGE_FIELD_CODE = "[PERCENTAGE]";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    static/* DmlChartDataLabelPr()*/
    {
        G_DEFAULTS = getDefaults();
        G_DEFAULTS_AFTER_2007 = getDefaultsAfter2007();
        G_CHART_EX_DEFAULTS = getChartExDefaults();
    }
}

