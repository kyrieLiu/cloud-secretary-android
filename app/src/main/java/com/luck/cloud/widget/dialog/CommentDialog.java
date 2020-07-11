package com.luck.cloud.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.luck.cloud.R;
import com.luck.picture.lib.tools.ScreenUtils;

/**
 * @author：luck
 * @date：2019-12-12 16:39
 * @describe：PhotoSelectedDialog
 */
public class CommentDialog extends DialogFragment implements View.OnClickListener {
    public static final int IMAGE_CAMERA = 0;
    public static final int VIDEO_CAMERA = 1;
    
    private TextView tvCancel,tvConfirm;
    
    private EditText etContent;

    public static CommentDialog newInstance() {
        CommentDialog selectedDialog = new CommentDialog();
        return selectedDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getDialog() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        }
        return inflater.inflate(R.layout.dialog_comment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etContent = view.findViewById(R.id.comment_content);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvConfirm = view.findViewById(R.id.tv_confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        initDialogStyle();
    }

    /**
     * DialogFragment Style
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initDialogStyle() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ScreenUtils.getScreenWidth(getContext()), RelativeLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.PictureThemeDialogFragmentAnim);
            }
        }
    }

    private ConfirmCommentListener onItemClickListener;

    public void setConfirmCommentListener(ConfirmCommentListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (onItemClickListener != null) {
            if (id == R.id.tv_confirm) {
                onItemClickListener.addComment(etContent.getText().toString());
            }
        }

        dismissAllowingStateLoss();
    }

//    @Override
//    public void show(FragmentManager manager, String tag) {
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.add(this, tag);
//        ft.commitAllowingStateLoss();
//    }
    public interface ConfirmCommentListener{
        void addComment(String content);
    }
}
