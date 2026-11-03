// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a simple comparison, a part of a conditional expression. For example, 'ie', 'vml 1', 'lt ie 9.2', etc.
/// </summary>
class Comparison extends ConditionalExpression
{
    /// <summary>
    /// Creates a new instance of the class.
    /// </summary>
    /// <param name="operation">A comparison operation.</param>
    /// <param name="feature">A feature name. Cannot be empty or <c>null</c>.</param>
    /// <param name="version">
    /// A version of the feature. Can be <c>null</c> to indicate that the version of the feature is unimportant.
    /// </param>
    Comparison(/*ComparisonOperation*/int operation, String feature, VersionVector version)
    {
        Debug.assertTrue(StringUtil.hasChars(feature));
        Debug.assertTrue((mOperation == ComparisonOperation.EQUAL) || (version != null));

        mOperation = operation;
        mFeature = feature;
        mVersion = version;
    }

    public /*override*/ String toString()
    {
        StringBuilder result = new StringBuilder();

        String operation;
        switch (mOperation)
        {
            case ComparisonOperation.GREATER:
                operation = "gt";
                break;
            case ComparisonOperation.GREATER_OR_EQUAL:
                operation = "gte";
                break;
            case ComparisonOperation.LESS:
                operation = "lt";
                break;
            case ComparisonOperation.LESS_OR_EQUAL:
                operation = "lte";
                break;
            default:
                operation = "";
                break;
        }
        msStringBuilder.append(result, operation);

        if (operation.length() > 0)
        {
            result.append(' ');
        }
        msStringBuilder.append(result, mFeature);

        if (mVersion != null)
        {
            result.append(' ');
            msStringBuilder.append(result, mVersion);
        }

        return result.toString();
    }

    /*override*/ boolean matches(Features features)
    {
        Feature feature = features.get(mFeature);
        if (feature == null)
        {
            // Feature is unavailable.
            return false;
        }
        if (mVersion == null)
        {
            // If no version is specified, we are not interested in a specific version of the feature.
            // We only check if the feature is available at all.
            return true;
        }
        if (feature.getVersion() == null)
        {
            // The feature is available but has no specific version. Since we are interested in a specific version of
            // the feature, the check fails.
            return false;
        }
        // Compare actual and requested versions of the feature.
        return feature.getVersion().compareTo(mVersion, mOperation);
    }

    protected /*override*/ boolean isSubexpression() { return false; }

    private /*final*/ /*ComparisonOperation*/int mOperation;
    private /*final*/ String mFeature;
    private /*final*/ VersionVector mVersion;
}
