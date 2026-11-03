// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.Debug;


/// <summary>
/// Resolves CSS declaration order using CSS cascade principle.
/// </summary>
/// <remarks>
/// Implements 2,3 and 4 parts of the cascade principle mechanism:
/// 2. Sort declarations according to their importance (normal or important) and origin (author, user, or user agent). 
///    From highest to lowest precedence:
///    1.  user !important declarations
///    2.  author !important declarations
///    3.  author normal declarations
///    4.  user normal declarations
///    5.  user agent declarations
/// 3. If declarations have the same importance and source, sort them by selector specificity.
/// 4. Finally, if declarations have the same importance, source, and specificity, sort them by the order 
///   they are specified in the CSS. The last declaration wins.
/// http://www.w3.org/TR/CSS21/cascade.html#cascade
/// </remarks>
class CssCascadingOrderResolver
{
    CssCascadingOrderResolver()
    {
        mDeclarationInfo = new StringToObjDictionary<DeclarationInfo>();
    }

    /// <summary>
    /// Adds CSS declarations to the result considering a CSS specificity and declaration origin.
    /// </summary>
    /// <param name="declarations">CSS declarations.</param>
    /// <param name="origin">CSS declarations origin.</param>
    /// <param name="specificity">CSS specificity of the declarations selector.</param>
    void add(CssDeclarationCollection declarations, /*CssDeclarationOrigin*/int origin, CssSelectorSpecificity specificity)
    {
        for (CssDeclaration declaration : declarations)
        {
            DeclarationInfo declarationInfo = mDeclarationInfo.get(declaration.getProperty());

            boolean updateDeclarationInfo;
            if (declarationInfo == null)
            {
                declarationInfo = new DeclarationInfo();
                mDeclarationInfo.add(declaration.getProperty(), declarationInfo);
                updateDeclarationInfo = true;
            }
            else
            {
                int foundDeclarationPriority = getDeclarationPriority(declarationInfo.Origin, declarationInfo.Declaration.getImportant());
                int newDeclarationPriority = getDeclarationPriority(origin, declaration.getImportant());
                if (newDeclarationPriority > foundDeclarationPriority)
                    updateDeclarationInfo = true;
                else if (newDeclarationPriority == foundDeclarationPriority)
                    updateDeclarationInfo = specificity.compareTo(declarationInfo.Specificity) >= 0;
                else
                    updateDeclarationInfo = false;
            }

            if (updateDeclarationInfo)
            {
                declarationInfo.Declaration = declaration;
                declarationInfo.Origin = origin;
                declarationInfo.Specificity = specificity;
                declarationInfo.Flags = declarations.getFlags(declaration.getProperty());
            }
        }
    }

    /// <summary>
    /// Resets the resolver to initial state.
    /// </summary>
    void clear()
    {
        mDeclarationInfo.clear();
    }

    /// <summary>
    /// Adds resolved CSS declarations to the specified collection replacing existing declarations.
    /// </summary>
    void resolve(CssDeclarationCollectionBuilder destinationDeclarations)
    {
        for (DeclarationInfo item : mDeclarationInfo.getValues())
        {
            destinationDeclarations.addOrReplace(item.Declaration);
            String property = item.Declaration.getProperty();
            destinationDeclarations.setFlags(property, item.Flags);
            if (item.Origin == CssDeclarationOrigin.USER_AGENT)
            {
                destinationDeclarations.markUserAgent(property);
            }
        }
    }

    /// <summary>
    /// Get declaration proprity by origin and !important flag. 
    /// </summary>
    /// <param name="origin">CSS declaration origin.</param>
    /// <param name="important">CSS declaration !important flag.</param>
    /// <returns>Integer value that indicates declaration proprity. Highest value corresponds to highest priority.</returns>
    private static int getDeclarationPriority(/*CssDeclarationOrigin*/int origin, boolean important)
    {
        final int IMPORTANT_USER_AGENT_PRIORITY = 60;
        final int IMPORTANT_USER_PRIORITY = 50;
        final int IMPORTANT_AUTHOR_PRIORITY = 40;
        final int NORMAL_AUTHOR_PRIORITY = 30;
        final int NORMAL_USER_PRIORITY = 20;
        final int NORMAL_USER_AGENT_PRIORITY = 10;

        int prority;
        switch (origin)
        {
            case CssDeclarationOrigin.AUTHOR:
                prority = (important) ? IMPORTANT_AUTHOR_PRIORITY : NORMAL_AUTHOR_PRIORITY;
                break;
            case CssDeclarationOrigin.USER:
                prority = (important) ? IMPORTANT_USER_PRIORITY : NORMAL_USER_PRIORITY;
                break;
            case CssDeclarationOrigin.USER_AGENT:
                prority = (important) ? IMPORTANT_USER_AGENT_PRIORITY : NORMAL_USER_AGENT_PRIORITY;
                break;
            default:
                Debug.assertTrue(false);
                return 0;
        }

        return prority;
    }

    private /*final*/ StringToObjDictionary<DeclarationInfo> mDeclarationInfo;
}

/// <summary>
/// Helps to store intermediate results.
/// </summary>
class DeclarationInfo
{
    CssDeclaration Declaration;
    /*CssDeclarationOrigin*/int Origin;
    CssSelectorSpecificity Specificity;
    /*CssDeclarationFlags*/int Flags;
}

