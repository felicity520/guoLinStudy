package com.ryd.gyy.guolinstudy.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ryd.gyy.guolinstudy.R;

import org.jetbrains.annotations.NotNull;

public class Fragment2 extends Fragment {
    private static String ARG_PARAM = "param_key";
    private String mParam;
    private Activity mActivity;

    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
//        //建议使用onAttach中的context强转成Activity来获取Activity，而不是使用getActivity()
//        mActivity = (Activity) context;
//        assert getArguments() != null;
//        mParam = getArguments().getString(ARG_PARAM);  //获取参数
    }

    /**
     * onSaveInstanceState()在onPause()之后，onStop()之前调用。
     * onRestoreInstanceState()在onStart()之后，onResume()之前。
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 静态加载的方式
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    public static Fragment2 newInstance(String str) {
        Fragment2 frag = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, str);
        //如果在创建Fragment时要传入参数,建议使用setArguments(Bundle bundle)方式添加
        // 而不建议通过为Fragment添加带参数的构造函数
        frag.setArguments(bundle);
        return frag;
    }
}
