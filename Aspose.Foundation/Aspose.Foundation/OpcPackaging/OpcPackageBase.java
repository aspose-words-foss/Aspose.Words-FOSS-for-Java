// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/09/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import java.util.Comparator;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.words.ref.Ref;


/// <summary>
/// Base class for FlatOpcPackage and OpcPackage
/// </summary>
public abstract class OpcPackageBase
{
    protected OpcPackageBase()
    {
        mParts = new OpcPackagePartCollection();
    }

    /// <summary>
    /// Creates the opc package parts using a specific comparator.
    /// </summary>
    protected OpcPackageBase(Comparator<String> comparer)
    {
        mParts = new OpcPackagePartCollection(comparer);
    }

    /// <summary>
    /// Load relationship data from package.
    /// </summary>
    void loadRelationships() throws Exception
    {
        // WORDSNET-13471 Resilience fix to avoid exception upon loading when original part in package
        // is missing while relationship part exists.            
        for (OpcPackagePart part : mParts)
        {
            String oPartName = Path.getFileName(part.getName());

            // Relationship parts will have names like "/word/_rels/document.xml.rels" or "/_rels/.rels"
            if (REL_EXT.equals(part.getExtension()))
            {
                // Remove "/_rels" and ".rels" and we end up with the name of the source part.
                String sourcePartName = part.getName().replace(G_REL_PATH_L, "").replace(G_REL_EXT_F, "");

                if (ROOT_PART_NAME.equals(sourcePartName))
                    readRelationships(part, mRels);
            }
            // Some created parts have only folder path in the name and do not contain data. 
            // As example check test "TestDmlTextBox.TestDmlListsBulletFont".
            else if (StringUtil.hasChars(oPartName))
            {
                // Append "/_rels" and ".rels" and we end up with the name of the relationship part.
                String rPartName = part.getName().replace(oPartName, G_REL_PATH_R + oPartName + G_REL_EXT_F);

                if (mParts.contains(rPartName))
                {
                    OpcPackagePart rPart = fetchPartByName(rPartName);
                    readRelationships(rPart, part.getRels());
                }
            }
        }
    }

    /// <summary>
    /// Reads relationship from the specified part into the specified relationship collection.
    /// </summary>
    /// <param name="part">PackagePart to read relationships from.</param>
    /// <param name="rels">RelationshipCollection to read relationships to.</param>
    private static void readRelationships(OpcPackagePart part, OpcRelationshipCollection rels) throws Exception
    {
        AnyXmlReader partReader = new AnyXmlReader(part.getStream());
        while (partReader.readChild("Relationships"))
        {
            switch (gStringSwitchMap.of(partReader.getLocalName()))
            {
                case /*"Relationship"*/0:
                {
                    // Example:
                    //   <Relationship Id="rId1" Type="http://.../attachedTemplate" Target="file:///C:\...\template1.dot" TargetMode="External"/>
                    String id = null;
                    String type = null;
                    String target = null;
                    boolean isExternal = false;

                    while (partReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(partReader.getLocalName()))
                        {
                            case /*"Id"*/1:
                                id = partReader.getValue();
                                break;
                            case /*"Type"*/2:
                                type = partReader.getValue();
                                break;
                            case /*"Target"*/3:
                                target = partReader.getValue();
                                break;
                            case /*"TargetMode"*/4:
                                isExternal = ("External".equals(partReader.getValue()));
                                break;
                            default:
                                // Ignore.
                                break;
                        }
                    }
                    // WORDSNET-9113 Ignore relationships with an empty target.
                    if (StringUtil.hasChars(target))
                        rels.add(id, type, target, isExternal);
                    break;
                }
                default:
                {
                    partReader.ignoreElement();
                    break;
                }
            }
        }
    }

    /// <summary>
    /// This includes ALL items in the package including Content_Types.xml and rels.
    /// </summary>
    public OpcPackagePartCollection getParts() { return mParts; }

    /// <summary>
    /// Relationships for this package.
    /// </summary>
    public OpcRelationshipCollection getRels() { return mRels; }

    /// <summary>
    /// Gets a part by name or returns null if the part does not exist.
    /// </summary>
    /// <param name="name">Must be the absolute part name.</param>
    public OpcPackagePart getPartByName(String name)
    {
        return mParts.get(name);
    }

    /// <summary>
    /// Gets a part by name or throw an exception if the part does not exist.
    /// </summary>
    /// <param name="name">Must be the absolute part name.</param>
    public OpcPackagePart fetchPartByName(String name)
    {
        OpcPackagePart result = getPartByName(name);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find part '{0}'.", name));
        else
            return result;
    }

    /// <summary>
    /// RK This is the proper way of getting singleton parts. By relationship type.
    /// </summary>
    /// <param name="parentPart">The parent part of the part we are getting. Null if the parent is the package.</param>
    /// <param name="relType">The relationship type from the parent to the child part.</param>
    /// <returns>The child part or null.</returns>
    public OpcPackagePart getPartByRelationshipType(OpcPackagePart parentPart, String relType)
    {
        OpcRelationshipCollection rels;
        String rootName;
        if (parentPart == null)
        {
            rels = mRels;
            rootName = ROOT_PART_NAME;
        }
        else
        {
            rels = parentPart.getRels();
            rootName = parentPart.getName();
        }

        OpcRelationship rel = rels.getFirstByType(relType);
        if (rel != null)
            return getPartByName(makeAbsolute(rootName, rel.getTarget()));
        else
            return null;
    }

    public OpcPackagePart fetchPartByRelationshipType(OpcPackagePart parentPart, String relType)
    {
        OpcPackagePart result = getPartByRelationshipType(parentPart, relType);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find target of relationship '{0}'", relType));
        else
            return result;
    }

    /// <summary>
    /// Check if there is part that matches the specified relationship type.
    /// </summary>
    public boolean exists(String relType)
    {
        return (mRels.getFirstByType(relType) != null);
    }

    /// <summary>
    /// Creates a package part that is a child of the specified part.
    /// Adds the part to the collection adds a relationship from the parent part to the created part.
    /// </summary>
    public OpcPackagePart createChildPart(
        OpcPackagePart parentPart,
        String childPartName,
        String contentType,
        String relType) throws Exception
    {
        String dummyRelId = null;
        Ref<String> referenceToDummyRelId = new Ref<String>(dummyRelId);
        OpcPackagePart part = createChildPart(parentPart, childPartName, contentType, relType, /*out*/ referenceToDummyRelId);
        dummyRelId = referenceToDummyRelId.get();
        // RK Split assignment and return into two lines to allow autoporting to succeed.
        return part;
    }

    /// <summary>
    /// Same as above, but also returns the id of the created relationship.
    /// </summary>
    public OpcPackagePart createChildPart(
        OpcPackagePart parentPart,
        String childPartName,
        String contentType,
        String relType,
        /*out*/Ref<String> relId) throws Exception
    {
        if (parentPart != null)
            childPartName = makeAbsolute(parentPart.getName(), childPartName);

        OpcPackagePart childPart = new OpcPackagePart(childPartName, contentType);
        mParts.add(childPart);

        OpcRelationshipCollection rels = (parentPart != null) ? parentPart.getRels() : mRels;
        relId.set(rels.add(relType, childPart.getName(), false));

        return childPart;
    }

    /// <summary>
    /// When overridden saves package to stream. 
    /// </summary>
    public /*virtual*/ void save(Stream stream) throws Exception
    {
        // No default implementation.
    }

    /// <summary>
    /// Makes the specified target relative to the part name.
    /// Adds the "../" segments as appropriate.
    /// If target is already relative, return as is.
    /// </summary>
    public static String makeRelative(String parentPartName, String targetName)
    {
        if (!msString.startsWith(targetName, "/", StringComparison.ORDINAL))
            return targetName;

        // Find the position where the difference starts.
        int lastSlashIdx = 0;
        int maxIdx = Math.min(parentPartName.length(), targetName.length());
        for (int i = 0; i < maxIdx; i++)
        {
            if (parentPartName.charAt(i) == '/')
                lastSlashIdx = i;
            if (parentPartName.charAt(i) != targetName.charAt(i))
                break;
        }

        // Add enough ../ to cover for the remaning segments in the parent part name.
        int diffStartIdx = lastSlashIdx + 1;
        StringBuilder result = new StringBuilder();
        for (int i = diffStartIdx; i < parentPartName.length(); i++)
        {
            if (parentPartName.charAt(i) == '/')
                msStringBuilder.append(result, STEP_UP_SEGMENT);
        }

        // Add the remainder of the target name.
        result.append((targetName), (diffStartIdx), (diffStartIdx) + (targetName.length() - diffStartIdx));

        return result.toString();
    }

    /// <summary>
    /// Builds a complete target name out of a relative target name.
    /// Resolves the "../" segments in the part name.
    /// If target name is absolute, returns it as is.
    /// If target name is "NULL", returns empty string.
    /// </summary>
    public static String makeAbsolute(String parentPartName, String targetName)
    {
        if (msString.startsWith(targetName, "/", StringComparison.ORDINAL))
            return targetName;

        // WORDSNET-8253 In some cases rels Target can be equals "NULL" or "null"
        // MS Word does not throws when encounters such target as image uri, so we should not throw as well
        // such resource is not reachable anyway, so return an empty string
        if (StringUtil.equalsIgnoreCase(targetName, "NULL"))
            return "";

        String result = removeLastSegment(parentPartName);

        int pos = 0;
        while (true)
        {
            int newPos = msString.indexOf(targetName, STEP_UP_SEGMENT, pos, StringComparison.ORDINAL);
            if (newPos >= pos)
            {
                // This code is for an unlikely case when the target name contains /../ in the middle, 
                // for example "aaa/../bbb". It will copy "aaa/" to the output.
                if (newPos > pos)
                    result = msString.plusEqOperator(result, targetName.substring((pos), (pos) + (newPos - pos)));

                // Execute the step up.
                result = removeLastSegment(result);
                pos = newPos + STEP_UP_SEGMENT.length();
            }
            else
            {
                // Flush the remainder of the target name to the result.
                result = msString.plusEqOperator(result, targetName.substring((pos), (pos) + (targetName.length() - pos)));
                return result;
            }
        }
    }

    /// <summary>
    /// Removes the last segment from a path. Segments must be separated by "/" slashes.
    /// If a slash is at the end of the string, it is not counted as a segment (e.g. ignored).
    /// </summary>
    private static String removeLastSegment(String s)
    {
        if (!StringUtil.hasChars(s))
            return s;

        if (ROOT_PART_NAME.equals(s))
            return s;

        int startIdx = s.length() - 1;
        for (int i = startIdx; i >= 0; i--)
        {
            if ((s.charAt(i) == '/') && (i < startIdx))
                return s.substring((0), (0) + (i + 1));
        }

        return "";
    }

    private /*final*/ OpcRelationshipCollection mRels = new OpcRelationshipCollection(ROOT_PART_NAME);
    private /*final*/ OpcPackagePartCollection mParts;

    private static final String REL_EXT = "rels";
    private static final String ROOT_PART_NAME = "/";
    private static final String STEP_UP_SEGMENT = "../";
    private static /*final*/ String G_REL_EXT_F = msString.format(".{0}", REL_EXT);
    private static /*final*/ String G_REL_PATH_L = msString.format("{0}_{1}", ROOT_PART_NAME, REL_EXT);
    private static /*final*/ String G_REL_PATH_R = msString.format("_{0}{1}", REL_EXT, ROOT_PART_NAME);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Relationship",
		"Id",
		"Type",
		"Target",
		"TargetMode"
	);

}

