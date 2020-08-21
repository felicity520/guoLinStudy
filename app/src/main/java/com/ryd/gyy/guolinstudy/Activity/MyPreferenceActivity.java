package com.ryd.gyy.guolinstudy.Activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.ryd.gyy.guolinstudy.R;

public class MyPreferenceActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private Preference checkboxPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //静态加载xml布局
        addPreferencesFromResource(R.xml.activity_pre);

//        initView();
//        createPreference();
    }

    private void initView() {
        checkboxPreference = findPreference("key_checkbox_preference");
        checkboxPreference.setOnPreferenceChangeListener(this);
        checkboxPreference.setOnPreferenceClickListener(this);
    }

    /**
     * 动态加载PreferenceScreen布局
     */
    private void createPreference() {
        PreferenceScreen preferenceScreen = this.getPreferenceManager().createPreferenceScreen(this);//先构建PreferenceScreen对象得到一个布局容器
        this.setPreferenceScreen(preferenceScreen);//设置容器
        CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);//构建一个子Preference，待添加到容器中
        checkBoxPreference.setKey("zhehsi GYY KEY");//设置key
        checkBoxPreference.setTitle("GYY The Title Of CheckBoxPreference");//设置title
        checkBoxPreference.setSummary("GYY Some summay for CheckBoxPreference");
        preferenceScreen.addPreference(checkBoxPreference);//添加到容器中
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        //如果值改变了我们可以通过监听这个事件来获取新值
        Toast.makeText(this, String.format("Preference的值为%s", newValue), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        //当接收到Click事件之后触发
        Toast.makeText(this, "Preference Clicked", Toast.LENGTH_LONG).show();
        return true;
    }
}
