package com.lehman.ic9.ui;

public class browserType
{
    public static Object type(String FullyQualifiedClassName) throws ClassNotFoundException
    {
        return Class.forName(FullyQualifiedClassName);
    }
}
