package com.luck.cloud.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.luck.cloud.R;
import com.luck.cloud.widget.PickerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by liuyin on 2018/7/20.
 * 滑动选择器
 */

public class TypeSelectDialog extends Dialog {
    @Bind(R.id.iv_dialog_repairs_type_colose)
    ImageView ivColose;
    @Bind(R.id.bt_dialog_repairs_confirm)
    Button btConfirm;
    @Bind(R.id.pv_dialog_repairs_type)
    PickerView pickerView;

    private OnTypeSelector typeSelector;
    private String selectType;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.BOTTOM); //显示在底部
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        getWindow().setAttributes(p);

        pickerView.setIsLoop(false);
        initView();

    }


    public TypeSelectDialog(Activity activity) {
        super(activity, R.style.ThemeCustomDialog);
        setContentView(R.layout.dialog_repairs_select_type);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_dialog_repairs_type_colose, R.id.bt_dialog_repairs_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_dialog_repairs_type_colose:
                dismiss();
                break;
            case R.id.bt_dialog_repairs_confirm:
                if (typeSelector!=null){
                    typeSelector.typeSeletor(selectType);
                }
                dismiss();
                break;
        }
    }
    private void initView(){
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectType=text;

            }
        });

    }
    public void setData(ArrayList<String> list){
        this.selectType=list.get(0);
        pickerView.setData(list);
    }
    public interface OnTypeSelector{
        void typeSeletor(String string);
    }
    public void setTypeSelector(OnTypeSelector typeSelector){
        this.typeSelector=typeSelector;
    }
}
