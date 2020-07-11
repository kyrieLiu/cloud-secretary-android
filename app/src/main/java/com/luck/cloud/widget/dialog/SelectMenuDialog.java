package com.luck.cloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.luck.cloud.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuyin on 2019/6/11 13:34
 * Description:  确定提示弹框
 */
public class SelectMenuDialog extends Dialog {

    @Bind(R.id.tv_menu_select_title)
    TextView mTvTitle;
    @Bind(R.id.tv_pmenu_select_content)
    TextView mTvContent;
    @Bind(R.id.tv_menu_select_cancel)
    TextView mTvCancel;
    @Bind(R.id.tv_menu_select_confirm)
    TextView mTvConfirm;

    public SelectMenuDialog(@NonNull Context context) {
        super(context, R.style.ThemeCustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu_select);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_menu_select_cancel, R.id.tv_menu_select_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_menu_select_cancel:
                dismiss();
                break;
            case R.id.tv_menu_select_confirm:
                if (listener != null) listener.callback();
                break;
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }

    public interface OnMenuSelectListener {
        void callback();
    }

    private OnMenuSelectListener listener;
    public void setListener(OnMenuSelectListener listener) {
        this.listener = listener;
    }
}
