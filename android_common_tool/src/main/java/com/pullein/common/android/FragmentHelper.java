package com.pullein.common.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Common-Tools<br>
 * describe ：Fragment 工具类，快速add,replace
 *
 * @author xugang
 * @date 2019/5/5
 */
public class FragmentHelper {

    public static void add(FragmentManager manager, int id, Fragment fragment) {
        manager.beginTransaction().add(id, fragment).show(fragment).commitAllowingStateLoss();
    }

    public static void addToBackStack(FragmentManager manager, int id, Fragment fragment) {
        manager.beginTransaction().add(id, fragment).show(fragment).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    public static void replace(FragmentManager manager, int id, Fragment fragment) {
        manager.beginTransaction().replace(id, fragment).commitAllowingStateLoss();
    }

    public static void replaceToBackStack(FragmentManager manager, int id, Fragment fragment) {
        manager.beginTransaction().replace(id, fragment).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    public static void remove(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public static void hide(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    public static void show(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

}
