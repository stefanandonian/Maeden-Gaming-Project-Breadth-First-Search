// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utilities.java

import java.util.Arrays;

public class Utilities<T>
{

    public Utilities()
    {
    }

    public boolean arrayContains(T[] array, T obj)
    {
        Arrays.sort(array);
        return Arrays.binarySearch(array, obj) >= 0;
    }

    public int arrayIndexOf(T[] array, T obj)
    {
        for(int i = 0; i < array.length; i++)
            if(array[i].equals(obj))
                return i;

        return -1;
    }
}
