package com.aspose.ms.java.util;


import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Based on methods of https://github.com/jhalterman/typetools
 */
public class JavaTypeUtil
{

    public static final class Unknown
    {
        private Unknown()
        {
        }
    }

    public static boolean isBoxingConversionPossible(Class source, Class target)
    {
        if (source == null && target == null)
            return true;

        if (source == boolean.class && target.isAssignableFrom(Boolean.class))
            return true;

        if (source == byte.class && target.isAssignableFrom(Byte.class))
            return true;

        if (source == short.class && target.isAssignableFrom(Short.class))
            return true;

        if (source == char.class && target.isAssignableFrom(Character.class))
            return true;

        if (source == int.class && target.isAssignableFrom(Integer.class))
            return true;

        if (source == long.class && target.isAssignableFrom(Long.class))
            return true;

        if (source == float.class && target.isAssignableFrom(Float.class))
            return true;

        if (source == double.class && target.isAssignableFrom(Double.class))
            return true;

        return false;
    }

    public static Type resolveArgumentFromClasses(Class type, Class subType)
    {
        return resolveArgument(resolveGenericType(type, subType), subType);
    }

    public static Type resolveArgument(Type genericType, Class<?> subType)
    {
        Type[] arguments = resolveArguments(genericType, subType);
        if (arguments == null)
            return Unknown.class;

        if (arguments.length != 1)
            throw new IllegalArgumentException("Expected 1 type argument on generic type " + genericType + " but found " + arguments.length);

        return arguments[0];
    }

    public static Type[] resolveArguments(Type genericType, Class<?> subType)
    {
        Type[] result = null;

        if (genericType instanceof ParameterizedType)
        {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] arguments = paramType.getActualTypeArguments();
            result = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++)
                result[i] = resolveRawClass(arguments[i], subType);
        }
        else if (genericType instanceof TypeVariable)
        {
            result = new Class[1];
            result[0] = resolveRawClass(genericType, subType);
        }

        return result;
    }

    public static Type resolveGenericType(Class<?> type, Type subType)
    {
        Class<?> rawType;
        if (subType instanceof ParameterizedType)
            rawType = (Class<?>) ((ParameterizedType) subType).getRawType();
        else
            rawType = (Class<?>) subType;

        if (type.equals(rawType))
            return subType;

        Type result;
        if (type.isInterface())
        {
            for (Type superInterface : rawType.getGenericInterfaces())
                if (superInterface != null && !superInterface.equals(Object.class))
                    if ((result = resolveGenericType(type, superInterface)) != null)
                        return result;
        }

        Type superClass = rawType.getGenericSuperclass();
        if (superClass != null && !superClass.equals(Object.class))
            if ((result = resolveGenericType(type, superClass)) != null)
                return result;

        return null;
    }

    public static Class<?> resolveRawClass(Type genericType, Class<?> subType)
    {
        if (genericType instanceof Class)
        {
            return (Class<?>) genericType;
        }
        else if (genericType instanceof ParameterizedType)
        {
            return resolveRawClass(((ParameterizedType) genericType).getRawType(), subType);
        }
        else if (genericType instanceof GenericArrayType)
        {
            GenericArrayType arrayType = (GenericArrayType) genericType;
            Class<?> compoment = resolveRawClass(arrayType.getGenericComponentType(), subType);
            return Array.newInstance(compoment, 0).getClass();
        }
        else if (genericType instanceof TypeVariable)
        {
            TypeVariable<?> variable = (TypeVariable<?>) genericType;
            genericType = getTypeVariableMap(subType).get(variable);
            genericType = genericType == null ? resolveBound(variable) : resolveRawClass(genericType, subType);
        }

        return genericType instanceof Class ? (Class<?>) genericType : Unknown.class;
    }

    public static Type resolveBound(TypeVariable<?> typeVariable)
    {
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length == 0)
            return Unknown.class;

        Type bound = bounds[0];
        if (bound instanceof TypeVariable)
            bound = resolveBound((TypeVariable<?>) bound);

        return bound == Object.class ? Unknown.class : bound;
    }


    private static Map<TypeVariable<?>, Type> getTypeVariableMap(final Class<?> targetType)
    {
        Map<TypeVariable<?>, Type> map = new HashMap<TypeVariable<?>, Type>();

        // Populate interfaces
        buildTypeVariableMap(targetType.getGenericInterfaces(), map);

        // Populate super classes and interfaces
        Type genericType = targetType.getGenericSuperclass();
        Class<?> type = targetType.getSuperclass();
        while (type != null && !Object.class.equals(type))
        {
            if (genericType instanceof ParameterizedType)
                buildTypeVariableMap((ParameterizedType) genericType, map);
            buildTypeVariableMap(type.getGenericInterfaces(), map);

            genericType = type.getGenericSuperclass();
            type = type.getSuperclass();
        }

        // Populate enclosing classes
        type = targetType;
        while (type.isMemberClass())
        {
            genericType = type.getGenericSuperclass();
            if (genericType instanceof ParameterizedType)
                buildTypeVariableMap((ParameterizedType) genericType, map);

            type = type.getEnclosingClass();
        }

        return map;
    }

    private static void buildTypeVariableMap(final Type[] types, final Map<TypeVariable<?>, Type> map)
    {
        for (Type type : types)
        {
            if (type instanceof ParameterizedType)
            {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                buildTypeVariableMap(parameterizedType, map);
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class)
                    buildTypeVariableMap(((Class<?>) rawType).getGenericInterfaces(), map);
            }
            else if (type instanceof Class)
            {
                buildTypeVariableMap(((Class<?>) type).getGenericInterfaces(), map);
            }
        }
    }

    private static void buildTypeVariableMap(ParameterizedType type, Map<TypeVariable<?>, Type> typeVariableMap)
    {
        if (type.getRawType() instanceof Class)
        {
            TypeVariable<?>[] typeVariables = ((Class<?>) type.getRawType()).getTypeParameters();
            Type[] typeArguments = type.getActualTypeArguments();

            if (type.getOwnerType() != null)
            {
                Type owner = type.getOwnerType();
                if (owner instanceof ParameterizedType)
                    buildTypeVariableMap((ParameterizedType) owner, typeVariableMap);
            }

            for (int i = 0; i < typeArguments.length; i++)
            {
                TypeVariable<?> variable = typeVariables[i];
                Type typeArgument = typeArguments[i];

                if (typeArgument instanceof Class)
                {
                    typeVariableMap.put(variable, typeArgument);
                }
                else if (typeArgument instanceof GenericArrayType)
                {
                    typeVariableMap.put(variable, typeArgument);
                }
                else if (typeArgument instanceof ParameterizedType)
                {
                    typeVariableMap.put(variable, typeArgument);
                }
                else if (typeArgument instanceof TypeVariable)
                {
                    TypeVariable<?> typeVariableArgument = (TypeVariable<?>) typeArgument;
                    Type resolvedType = typeVariableMap.get(typeVariableArgument);
                    if (resolvedType == null)
                        resolvedType = resolveBound(typeVariableArgument);
                    typeVariableMap.put(variable, resolvedType);
                }
            }
        }
    }
}
