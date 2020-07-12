//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.luck.cloud.function.mine.work;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentActivity;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected T[] tArr;
    protected AdapterView.OnItemClickListener OnItemClickListener;
    private FragmentActivity activity;

    public BaseListAdapter() {
    }

    public void setList(List<T> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (this.list == null) {
            this.list = new ArrayList<T>();
        }
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addBean(T bean) {
        if (this.list == null) {
            this.list = new ArrayList<T>();
        }

        this.list.add(bean);
        this.notifyDataSetChanged();
    }

    public ArrayList<T> getList() {
        return (ArrayList<T>) this.list;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.OnItemClickListener = onItemClickListener;
    }

    public FragmentActivity getActivity() {
        return this.activity;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public int getCount() {
        return this.list != null ? this.list.size() : (this.tArr != null ? this.tArr.length : 0);
    }

    public T getItem(int position) {
        T t=this.list != null && position < this.list.size() ? this.list.get(position) : (this.tArr != null && position < this.tArr.length ? this.tArr[position] : null);
        return t;
    }

    public T getItem(long position) {
        return this.getItem((int) position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    @SuppressWarnings("unchecked")
    public View getView(final int position, View convertView, ViewGroup parent) {
        T bean = this.getItem(position);
        BaseViewHolder<T> baseView;
        if (convertView == null) {
            baseView = this.loadView(parent.getContext(), bean, position);
        } else {
            baseView = (BaseViewHolder<T>) convertView.getTag(this.getViewId(bean, position));
            if (baseView == null) {
                baseView = this.loadView(parent.getContext(), bean, position);
            }
        }

        baseView.prepareData(bean, position);
        if (this.OnItemClickListener != null) {
            baseView.getView().setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    BaseListAdapter.this.OnItemClickListener.onItemClick((AdapterView<?>) null, v, position, (long) position);
                }
            });
        }

        return baseView.getView();
    }

    protected abstract BaseViewHolder<T> loadView(Context context, T var2, int var3);

    protected abstract int getViewId(T var1, int var2);


}
