package com.luck.cloud.widget.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.luck.cloud.R;
import com.luck.cloud.config.AppConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuyin on 2019/5/14 15:02
 * Description:网络请求数据后的异常情况展示
 */
public class LoadExceptionView extends RelativeLayout {

    @Bind(R.id.iv_error_img)
    ImageView mErrorImg;
    @Bind(R.id.tv_error_msg)
    TextView mErrorMsg;

    public LoadExceptionView(Context context) {
        this(context, null);

    }

    public LoadExceptionView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.load_exception_page, this);
        ButterKnife.bind(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
    }

    public LoadExceptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 加载异常信息
     *
     * @param message
     */
    public void setExceptionMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            message = AppConstants.HTTP_NO_DATA;
        }
        mErrorMsg.setText(message);
        switch (message) {
            case AppConstants.HTTP_NO_DATA:
                mErrorImg.setImageResource(R.mipmap.http_load_empty);
                break;
            case AppConstants.HTTP_SERVER_EXCEPTION:
            case AppConstants.HTTP_CONNECT_ERROR:
                mErrorImg.setImageResource(R.mipmap.http_load_empty);
                break;
            case AppConstants.HTTP_DATA_ERROR:
                mErrorImg.setImageResource(R.mipmap.http_load_empty);
                break;
            default:
                mErrorImg.setImageResource(R.mipmap.http_load_empty);
                break;
        }
    }

}
