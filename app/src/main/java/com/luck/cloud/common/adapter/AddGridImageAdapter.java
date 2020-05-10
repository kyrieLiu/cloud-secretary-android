package com.luck.cloud.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.luck.cloud.R;
import com.luck.cloud.utils.view.GlideUtils;

import java.util.ArrayList;

/**
 * Created by liuyin on 2019/3/14 13:50
 * Describe: 图片选择适配器
 */
public class AddGridImageAdapter extends BaseAdapter {
    private ArrayList<String> pathList;//图片资源
    private Context context;
    private int maxImageSize;//最大显示图片数量


    public AddGridImageAdapter(Context context, int maxImageSize, ArrayList<String> pathimgs) {
        this.context = context;
        this.maxImageSize = maxImageSize;
        this.pathList = pathimgs;
    }

    @Override
    public int getCount() {
        int count = pathList.size() + 1;
        if (count > maxImageSize) {
            count = maxImageSize;
        }
        return count;
    }

    public void setPathList(ArrayList<String> pathList) {
        this.pathList = pathList;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_image, null);
        ImageView iv_item_image = (ImageView) view.findViewById(R.id.iv_item_image);
        ImageView iv_item_del = (ImageView) view.findViewById(R.id.iv_item_del);
        final int imageSize = pathList.size();

        if (position == imageSize && imageSize <= maxImageSize) {
            iv_item_image.setImageResource(R.mipmap.add_img);
            iv_item_del.setVisibility(View.GONE);

        } else  {
            iv_item_del.setVisibility(View.VISIBLE);
            String filePath = pathList.get(position);
            GlideUtils.loadImage(context,iv_item_image,filePath);
        }

        iv_item_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (null != processImageListener) {
                    processImageListener.OnDeleteImmage(position);
                }
            }
        });
        iv_item_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (position == imageSize ) {
                    if (null != processImageListener) {
                        processImageListener.OnAddImmage();
                    }
                } else {
                    itemClick(position);
                }
            }
        });
        return view;
    }

    /**
     * 点击已经添加上去的图片,查看大图
     *
     * @param position
     */
    private void itemClick(int position) {


//        Intent intent = new Intent(context, ImagePagerActivity.class);
//        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, pathList);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
//        context.startActivity(intent);
    }

    OnProcessImageListener processImageListener;

    public void setOnProcessImageListener(OnProcessImageListener processImageListener) {
        this.processImageListener = processImageListener;
    }

    public interface OnProcessImageListener {

        void OnAddImmage();

        /**
         * 删除图片
         *
         * @param position
         */
        void OnDeleteImmage(int position);
    }

}
