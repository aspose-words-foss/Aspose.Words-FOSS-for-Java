// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/12/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a run attribute source for a chart collection item that contains text.
/// </summary>
/// <remarks>
/// Run properties hierarchy is processed for a chart collection item in the following way (in terms of
/// IChartItemTextProperties property names):
/// 1. If ItemTx exists and has a non-null <see cref="DmlChartTx.RichText"/>, its first run properties or the
/// default run properties of its first paragraph are used to get the value of the property. If the property
/// is not defined, if ItemTxPr is null or empty, and ItemSpPr is null, CollectionTxPr is used first then
/// <see cref="DmlChartSpace.TxPr"/> and the default value. But if ItemTxPr is not empty or ItemSpPr is not null,
/// CollectionTxPr and <see cref="DmlChartSpace.TxPr"/> are not taken into account, other default values are used
/// only. These defaults are very different from the other defaults: for example, the default font is Arial at
/// 18pt, while in the all other cases it is Calibri at 10pt.
/// When you set a property, it is set in all runs and default run properties of all paragraphs.
/// 2. If ItemTx is null or its <see cref="DmlChartTx.RichText"/> is null, ItemTxPr is checked. If ItemTxPr is
/// not empty or ItemSpPr is not null, the following sequence is used to get the property value: ItemTxPr,
/// <see cref="DmlChartSpace.TxPr"/>, the default value.
/// 3. If ItemTxPr is null or empty and ItemSpPr is null, CollectionTxPr is used to find the value of the property.
/// Then <see cref="DmlChartSpace.TxPr"/> and the defaults are taken into consideration.
/// </remarks>
class ChartItemDmlRunPropertiesSource extends ChartDmlRunPropertiesSourceBase
{
    /// <summary>
    /// Ctor to create an instance of this class.
    /// </summary>
    ChartItemDmlRunPropertiesSource(IChartItemTextProperties chartItemTextProperties, DmlChartSpace chartSpace)
    {
    	super(chartSpace);
	
        mChartItemTextProperties = chartItemTextProperties;
    }

    /// <summary>
    /// Clears all properties of all instances of <see cref="DmlRunProperties"/> related to the current chart
    /// collection item.
    /// </summary>
    protected /*override*/ void clearAllRunProperties()
    {
        // IRunAttrSource.ClearRunAttrs/Font.ClearFormatting must make the run properties have default values,
        // that is, values defined in the parent collection.

        super.clearAllRunProperties();

        // Don't do anything else for a single (non-collection) item.
        if (getCollectionTxPr() == null)
            return;

        // Clear the flag indicating that run properties of the paragraphs are not empty.
        for (DmlParagraph paragraph : getParagraphEnumerable())
            paragraph.getProperties().hasDefaultRunProperties(false);

        // For pre-Word 2016 charts, if ItemTxPr has no non-run properties defined, we can just clear it so that
        // CollectionTxPr will be used. But if ItemTxPr, for example, contains some paragraph properties or ItemSpPr
        // exists, we have to copy run properties from CollectionTxPr to ItemTxPr because MS Word doesn't look into
        // CollectionTxPr at this case.
        if (hasItemTxPrOrSpPr() && !isChartEx())
        {
            ensureGeneralRunPropertiesExist(false);

            for (DmlRunProperties runProperties : getRunPropertiesEnumerable())
                getCollectionTxPr().getRunPr().copyTo(runProperties);

            for (DmlParagraph paragraph : getParagraphEnumerable())
                paragraph.getProperties().hasDefaultRunProperties(true);
        }
    }

    /// <summary>
    /// Fetches the specified property from the parent paragraph, collection or chart space. Run defaults are not
    /// included.
    /// </summary>
    protected /*override*/ Object fetchInheritedRunPropertyValue(int key)
    {
        // If ItemTx is not null and has no a property value, if ItemTxPr or ItemSpPr exists, they are not used,
        // and neither CollectionTxPr nor ChartSpaceTxPr is used to resolve property value (it seems this is a
        // bug of MS Word).
        if (getAreItemTxRunPropertiesUsed() && hasItemTxPrOrSpPr())
        {
            DmlRunProperties runProperties = getItemTx().getFirstParagraph().getProperties().getDefaultRunProperties();
            if (runProperties == null)
                return null;

            return (RunPrConverter.isPropertySpecified(runProperties, key))
                ? getPropertyValue(key, runProperties)
                : null;
        }

        DmlRunProperties parentProperties = null;

        // Get value from parent paragraph in Word 2016 chart.
        if (isChartEx() && (getItemTxPr() != null) && RunPrConverter.isPropertySpecified(getItemTxPr().getRunPr(), key))
            parentProperties = getItemTxPr().getRunPr();

        // Get value from the parent collection. In pre-Word 2016 charts, if ItemTxPr is non empty or ItemSpPr
        // exists, CollectionTxPr is not used to get property value (MS Word behaviour).
        if ((parentProperties == null) &&
            (getCollectionTxPr() != null) &&
            (!hasItemTxPrOrSpPr() || isChartEx()))
        {
            // EndParagraphRunProperties has priority over DefaultRunPr in Word 2016 charts.
            DmlRunProperties paragraphEndProperties = getCollectionTxPr().getFirstParagraph().getEndParagraphRunProperties();
            if (isChartEx() && RunPrConverter.isPropertySpecified(paragraphEndProperties, key))
                parentProperties = paragraphEndProperties;
            else if (RunPrConverter.isPropertySpecified(getCollectionTxPr().getRunPr(), key))
                parentProperties = getCollectionTxPr().getRunPr();
        }

        // Get value from the chart space. MS Word doesn't get a value from a chart space in Word 2016 charts.
        if ((parentProperties == null) && !isChartEx() && RunPrConverter.isPropertySpecified(getChartSpaceTxPr().getRunPr(), key))
            parentProperties = getChartSpaceTxPr().getRunPr();

        if (parentProperties != null)
        {
            Object value = getValueFromParentRunProperties(key, parentProperties);
            return mChartItemTextProperties.getRelativePropertyValue(key, value);
        }

        return null;
    }

    /// <summary>
    /// Returns the default chart run property value.
    /// </summary>
    protected /*override*/ Object fetchDefaultRunPropertyValue(int key)
    {
        // If ItemTx is not null, has no property value and has no default run properties ('defRPr' does not exist),
        // if ItemTxPr is non-empty or ItemSpPr exists, they aren't used, and neither CollectionTxPr nor ChartSpaceTxPr
        // is used to resolve property value, but "strange" default values are used.
        if (getAreItemTxRunPropertiesUsed() &&
            ((getItemTx().getRichText().getParagraphs().size() == 0) ||
             !getItemTx().getRichText().getParagraphs().get(0).getProperties().hasDefaultRunProperties()) &&
            hasItemTxPrOrSpPr())
        {
            return fetchDefaultTxRunPropertyValue(key);
        }

        Object value = mChartItemTextProperties.fetchSpecialDefaultRunPropertyValue(key);
        if (value != null)
            return value;

        return super.fetchDefaultRunPropertyValue(key);
    }

    /// <summary>
    /// Gets an enumerable over all instances of <see cref="DmlRunProperties"/> within <see cref="ItemTx"/> or
    /// <see cref="ItemTxPr"/>.
    /// </summary>
    protected /*override*/ Iterable<DmlRunProperties> getRunPropertiesEnumerable()
    {
        return new DmlRunPropertiesEnumerable(getParagraphs(), isChartEx());
    }

    /// <summary>
    /// Gets an enumerable over all instances of <see cref="DmlParagraph"/> within <see cref="ItemTx"/> or
    /// <see cref="ItemTxPr"/>.
    /// </summary>
    protected /*override*/ Iterable<DmlParagraph> getParagraphEnumerable()
    {
        ArrayList<DmlParagraph> paragraphs = getParagraphs();
        if ( (paragraphs != null) )
            return paragraphs;
        else
            return new ArrayList<DmlParagraph>();
    }

    /// <summary>
    /// Generates <see cref="GeneralRunProperties"/> if it is <b>null</b>.
    /// </summary>
    protected /*override*/ void ensureGeneralRunPropertiesExist(boolean toSetProperty)
    {
        if (getAreItemTxRunPropertiesUsed())
            return;

        if (isChartEx())
        {
            if (getItemTxPr() == null)
                setItemTxPr(new DmlChartTxPr());

            getItemTxPr().ensureParagraphExists();

            if (getItemTxPr().getFirstParagraph().getElements().size() > 0)
                return;

            // Let's generate TxPr in the similar way as MS Word does.
            DmlParagraph paragraph = (getCollectionTxPr() != null)
                ? getCollectionTxPr().getFirstParagraph()
                : getItemTxPr().getFirstParagraph();

            DmlRunProperties runProperties = paragraph.getEndParagraphRunProperties().deepClone();
            DmlRun run = new DmlRun(runProperties);
            run.setText(mChartItemTextProperties.generateItemText());

            getItemTxPr().getFirstParagraph().addElement(run);
        }
        else
        {
            if ((getItemTxPr() == null) ||
                (getItemTxPr().isEmpty() && (getCollectionTxPr() != null) && !getCollectionTxPr().isEmpty()))
            {
                // Since ItemTxPr is empty, need to clone the parent properties, which are defaults for items:
                // when ItemTxPr is not empty, CollectionTxPr is not used by MS Word to resolve a property value.
                setItemTxPr((getCollectionTxPr() != null)
                    ? getCollectionTxPr().deepClone()
                    : new DmlChartTxPr());
            }

            if (toSetProperty)
                getItemTxPr().ensureParagraphExists();
        }
    }

    /// <summary>
    /// Gets a list of paragraphs within <see cref="ItemTx"/> or <see cref="ItemTxPr"/>.
    /// </summary>
    private ArrayList<DmlParagraph> getParagraphs()
    {
        if ( getAreItemTxRunPropertiesUsed() )
            return getItemTx().getRichText().getParagraphs();
        else
            return (getItemTxPr() != null) ? getItemTxPr().getParagraphs() : null;
    }

    /// <summary>
    /// Returns the default chart run property value for a case when <see cref="ItemTx"/> exists, and either
    /// non-empty <see cref="ItemTxPr"/> or <see cref="ItemSpPr"/> exists too.
    /// </summary>
    private static Object fetchDefaultTxRunPropertyValue(int key)
    {
        switch (key)
        {
            case FontAttr.NAME_ASCII:
                return ComplexFontName.fromName("Arial");
            case FontAttr.SIZE:
                return ConvertUtilCore.pointToHalfPoint(18.0);
            default:
                return RunPr.fetchDefaultAttr(key);
        }
    }

    /// <summary>
    /// Gets text element data of the collection item.
    /// </summary>
    /// <remarks>
    /// This instance contains a paragraph with text elements: fields and/or runs.
    /// This is always <b>null</b> in Word 2016 charts.
    /// </remarks>
    private DmlChartTx getItemTx() { return mChartItemTextProperties.getItemTx(); }

    /// <summary>
    /// Gets or sets text properties of the collection item.
    /// </summary>
    /// <remarks>
    /// This <see cref="DmlChartTxPr"/> instance contains a paragraph, which is empty in a pre-Word 2016 chart, and
    /// contains a single run in a Word 2016 chart.
    /// </remarks>
    private DmlChartTxPr getItemTxPr() { return mChartItemTextProperties.getItemTxPr(); }
    private void setItemTxPr(DmlChartTxPr value) { mChartItemTextProperties.setItemTxPr(value); }

    /// <summary>
    /// Gets shape properties of the collection item.
    /// </summary>
    private DmlChartSpPr getItemSpPr() { return mChartItemTextProperties.getItemSpPr(); }

    /// <summary>
    /// Gets text properties of the parent collection.
    /// </summary>
    private DmlChartTxPr getCollectionTxPr() { return mChartItemTextProperties.getCollectionTxPr(); }

    /// <summary>
    /// Gets DML run properties that is used to get direct property value.
    /// </summary>
    /*override*/ DmlRunProperties getGeneralRunProperties()
    {
        // In Word 2016 chart, the first (single) run of a ItemTxPr paragraph is used to get the general run
        // properties.
        // In pre-Word 2016 chart, if ItemTx is defined, its first field or run is used as a general run
        // properties source. If ItemTx is null, ItemTxPr is used, which contains a single empty paragraph;
        // the general run properties are the default run properties of that paragraph.

        DmlParagraph firstParagraph;
        if ((getItemTx() == null) || (getItemTx().getRichText() == null))
        {
            if (getItemTxPr() == null)
                return null;

            firstParagraph = getItemTxPr().getFirstParagraph();
        }
        else
        {
            if (getItemTx().getFirstParagraph() == null)
                msArrayList.add(getItemTx().getRichText().getParagraphs(), new DmlParagraph());

            firstParagraph = getItemTx().getFirstParagraph();
        }

        return (firstParagraph.getFirstElement() != null)
            ? firstParagraph.getFirstElement().getRunProperties()
            : firstParagraph.getProperties().getDefaultRunProperties();
    }

    /// <summary>
    /// Gets a flag indicating whether <see cref="ItemTx"/> is used to format text of the item.
    /// </summary>
    private boolean getAreItemTxRunPropertiesUsed() { return (getItemTx() != null) && (getItemTx().getRichText() != null); }

    /// <summary>
    /// Gets a flag indicating whether the item has non-empty <see cref="ItemTxPr"/> or <see cref="ItemSpPr"/>.
    /// </summary>
    private boolean hasItemTxPrOrSpPr()
    {
        return
            ((getItemTxPr() != null) && !getItemTxPr().isEmpty()) ||
            (getItemSpPr() != null);
    }

    private /*final*/ IChartItemTextProperties mChartItemTextProperties;
}

