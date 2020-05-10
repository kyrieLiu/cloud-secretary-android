package com.luck.cloud.utils;

import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.luck.cloud.app.AppApplication;


/**
 * Created by liuyin on 2019/2/20 14:45
 * 键盘控制工具
 */
public class KeyboardHelper {

    static KeyboardHelper instance;

    private KeyboardHelper() {
    }

    public static KeyboardHelper getInstance() {
        if (instance == null) {
            instance = new KeyboardHelper();
        }
        return instance;
    }

    /**
     * 隐藏软键盘 * @param editText
     */
    public void hideKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) AppApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void openKeyBoard(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) AppApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
                editText.setSelection(editText.getText().length());
            }
        },200);

    }


}
