// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents a set of features that are considered supported by HTML import when working with IE conditional expressions.
/// </summary>
class Features
{
    /// <summary>
    /// Creates a new set of features.
    /// </summary>
    /// <remarks>
    /// If there are features with same names, only the last one of them is added to the set.
    /// </remarks>
    Features(/*params*/ Feature... features)
    {
        for (Feature feature : features)
        {
            Debug.assertTrue(feature != null);
            mFeatures.set(feature.getName(), feature);
        }
    }

    /// <summary>
    /// Adds feature to set.
    /// </summary>
    /// <param name="feature">Feature.</param>
    void add(Feature feature)
    {
        mFeatures.set(feature.getName(), feature);
    }

    /// <summary>
    /// Gets a feature from the set by name.
    /// </summary>
    /// <param name="featureName">A feature name. Names are case-insensitive.</param>
    /// <returns>A known feature that has the specified name or <c>null</c> if no feature found.</returns>
    Feature get(String featureName)
    {
        return mFeatures.get(featureName);
    }

    public /*override*/ String toString()
    {
        StringBuilder result = new StringBuilder();
        for (Feature feature : mFeatures.getValues())
        {
            if (result.length() > 0)
            {
                msStringBuilder.append(result, "; ");
            }
            msStringBuilder.append(result, feature);
        }
        return result.toString();
    }

    private /*final*/ StringToObjDictionary<Feature> mFeatures = new StringToObjDictionary<Feature>();
}

