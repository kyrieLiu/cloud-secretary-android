package com.luck.cloud.widget.xrecycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luck.cloud.R;
import com.luck.cloud.widget.xrecycler.progressindicator.AVLoadingIndicatorView;


public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    public final static int STATE_ENABLE_FALSE = 3;

    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;
    private int state;

    private AVLoadingIndicatorView progressView;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void destroy() {
        progressCon = null;
        if (progressView != null) {
            progressView.destroy();
            progressView = null;
        }
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    public void initView() {
        setGravity(Gravity.CENTER);

        LayoutParams parentLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parentLayoutParams.setMargins(0, dp2px(10), 0, dp2px(10));

        setLayoutParams(parentLayoutParams);
//        setPadding(0, dp2px(10), 0, dp2px(10));
        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
                dp2px(30), dp2px(30)));

        progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(AVLoadingIndicatorView.LineSpinFadeLoader);
        progressCon.setView(progressView);

        addView(progressCon);
        mText = new TextView(getContext());
        mText.setText(getContext().getString(R.string.listview_loading));

        if (loadingHint == null || loadingHint.equals("")) {
            loadingHint = (String) getContext().getText(R.string.listview_loading);
        }
        if (noMoreHint == null || noMoreHint.equals("")) {
            noMoreHint = (String) getContext().getText(R.string.nomore_loading);
        }
        if (loadingDoneHint == null || loadingDoneHint.equals("")) {
            loadingDoneHint = (String) getContext().getText(R.string.loading_done);
        }

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.dp_10), 0, 0, 0);

        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void setState(int state) {
        this.state = state;
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setVisibility(View.VISIBLE);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setVisibility(View.GONE);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_ENABLE_FALSE:
                mText.setVisibility(View.GONE);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.GONE);
                break;
        }
    }


    public int getState() {
        return state;
    }

    public void hint() {
        //this.setVisibility(View.GONE);
        mText.setText(loadingDoneHint);
        this.setVisibility(View.GONE);
    }

    private int dp2px(int value) {
        return (int) getContext().getResources().getDisplayMetrics().density * value;
    }
}
