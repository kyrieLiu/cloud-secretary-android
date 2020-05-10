package com.luck.cloud.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liuyin on 2019/3/14 14:38
 * Describe: 
 */

public class ActivitiesManager {
    private static volatile ActivitiesManager instance;
    private List<Activity> list = new ArrayList();

    public static ActivitiesManager getInstance() {
        if (instance == null) {
            instance = new ActivitiesManager();
        }

        return instance;
    }

    public ActivitiesManager() {
    }

    public void pushActivity(Activity activity) {
        this.list.add(activity);
    }

    public void popCurrentActivity(Activity activity) {
        list.remove(activity);
    }

    public void popActivity(Class<?> cls) {
        ArrayList list2 = new ArrayList();
        Iterator var4 = this.list.iterator();

        while (var4.hasNext()) {
            Activity activity = (Activity) var4.next();
            if (cls.equals(activity.getClass())) {
                activity.finish();
            } else {
                list2.add(activity);
            }
        }

        this.list = list2;
    }

    public synchronized void popOtherActivity(Class c) {
        for (int i = 0; i < list.size(); i++) {
            Activity activity = list.get(i);
            if (!c.getName().equals(activity.getClass().getName())) {
                activity.finish();
                list.remove(activity);
                i--;
            }
        }
    }

    public void popAllActivity() {
        Iterator var2 = this.list.iterator();

        while (var2.hasNext()) {
            Activity activity = (Activity) var2.next();
            activity.finish();
        }

        this.list.clear();
    }
}
