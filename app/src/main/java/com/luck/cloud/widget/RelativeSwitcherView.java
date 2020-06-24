package com.luck.cloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.luck.cloud.R;
import com.luck.cloud.function.home.ArticleListBean;

import java.util.ArrayList;
import java.util.List;

public class RelativeSwitcherView extends ViewSwitcher implements ViewSwitcher.ViewFactory, View.OnClickListener {
    private int index;
    private int size;
    private AttributeSet attrs;
    private List<ArticleListBean> arrays;

    public RelativeSwitcherView(Context context) {
        super(context);
        init();
    }

    public RelativeSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init();
    }

    private void init() {
        index = 0;
        arrays = new ArrayList<>();
        setFactory(this);
        setInAnimation(animIn());
        setOutAnimation(animOut());
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            ArticleListBean bean = null;
            if (arrays != null && arrays.size() > 0) {
                bean = arrays.get(index);
            }
            mListener.clickWhich(bean);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (size > 1) {
                index++;
                index = index % size;
                if (arrays != null && arrays.size() > index) {
                    setDataForView(arrays.get(index));
                }
                postDelayed(this, 3000);
            }
        }
    };

    @Override
    public View makeView() {
        View view = View.inflate(getContext(), R.layout.layout_bulletin_park, null);
        view.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        return view;
    }

    private Animation animIn() {
        TranslateAnimation anim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, -0.0f);
        anim.setDuration(1000);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }

    private Animation animOut() {
        TranslateAnimation anim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        anim.setDuration(1000);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            removeCallbacks(runnable);
            super.onDetachedFromWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnMyClickListener(OnMyClickListener listener) {
        this.mListener = listener;
    }

    private OnMyClickListener mListener;

    public interface OnMyClickListener {
        void clickWhich(ArticleListBean bean);
    }

    public void setArticleList(List<ArticleListBean> texts) {
        removeCallbacks(runnable);
        this.index = 0;
        this.size = texts != null ? texts.size() : 0;
        this.arrays = texts;
        if (size > 0) {
            if (arrays != null && arrays.size() > 0) {
                setDataForView(arrays.get(index));
            }
            postDelayed(runnable, 3000);
        } else {
            setDataForView(null);
        }
    }

    private void setDataForView(ArticleListBean indexBase) {
        if (indexBase != null) {
            final RelativeLayout containerView = (RelativeLayout) getNextView();
            TextView tv_title = (TextView) containerView.findViewById(R.id.txt_article_item);
            tv_title.setText(indexBase.getTitle());
            showNext();
        }
    }
}
