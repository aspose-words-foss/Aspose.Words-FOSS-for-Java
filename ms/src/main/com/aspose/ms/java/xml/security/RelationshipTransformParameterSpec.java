package com.aspose.ms.java.xml.security;

import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.util.ArrayList;
import java.util.List;

/**
 * Relationship transform parameter specifications.
 */
public class RelationshipTransformParameterSpec implements TransformParameterSpec
{
    public List<String> getSourceIds()
    {
        return this.mSourceIds;
    }

    private final List<String> mSourceIds = new ArrayList<String>();
}
