package com.example.weatherappgeekbrains.tools;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class Tools {
    public static int[] getImageArray(Resources resources, int idArray){
        @SuppressLint("Recycle")
        TypedArray pictures = resources.obtainTypedArray(idArray);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }
}
