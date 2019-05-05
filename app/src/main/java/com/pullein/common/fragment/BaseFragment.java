package com.pullein.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pullein.common.utils.Log;

/**
 * Common-Tools<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/5/5
 */
public class BaseFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(getClass().getSimpleName() + "=== onAttach ===");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getSimpleName() + "=== onCreate ===");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName() + "=== onCreateView ===");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(getClass().getSimpleName() + "=== onViewCreated ===");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(getClass().getSimpleName() + "=== onActivityCreated ===");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName() + "=== onStart ===");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName() + "=== onResume ===");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName() + "=== onPause ===");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName() + "=== onStop ===");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(getClass().getSimpleName() + "=== onDestroyView ===");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName() + "=== onDestroy ===");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(getClass().getSimpleName() + "=== onDetach ===");
    }
}
