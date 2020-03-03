package com.example.application;

import android.annotation.SuppressLint;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class BottomNAvigation {
    @SuppressLint("RestrictedApi")
    public static void disableShiftmode(BottomNavigationView view){
        BottomNavigationMenuView navigationView = (BottomNavigationMenuView) view.getChildAt(0);
        try{
            Field shiftmode=navigationView.getClass().getDeclaredField("Shiftmode");
            shiftmode.setAccessible(true);
            shiftmode.setBoolean(navigationView,false);
            shiftmode.setAccessible(false);
            for(int i=0;i<navigationView.getChildCount();i++){
                BottomNavigationItemView item =(BottomNavigationItemView) view.getChildAt(i);
                item.setShifting(false);
                item.setChecked(item.getItemData().isChecked());


            }
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
