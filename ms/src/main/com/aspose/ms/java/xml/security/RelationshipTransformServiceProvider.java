package com.aspose.ms.java.xml.security;

import java.security.Provider;
import java.security.Security;

public class RelationshipTransformServiceProvider extends Provider
{
    protected RelationshipTransformServiceProvider()
    {
        super(RELATIONSHIP_TRANSFORM_SERVICE_PROVIDER, 1.0, RELATIONSHIP_TRANSFORM_SERVICE_PROVIDER);
        put("TransformService.http://schemas.openxmlformats.org/package/2006/RelationshipTransform", RelationshipTransformService.class.getName());
        put("TransformService.http://schemas.openxmlformats.org/package/2006/RelationshipTransform MechanismType", "DOM");
    }

    public static void init()
    {
        Provider provider = Security.getProvider(RELATIONSHIP_TRANSFORM_SERVICE_PROVIDER);
        if (provider == null)
            Security.addProvider(new RelationshipTransformServiceProvider());
    }

    public static final String RELATIONSHIP_TRANSFORM_SERVICE_PROVIDER = "RelationshipTransformServiceProvider";
}
