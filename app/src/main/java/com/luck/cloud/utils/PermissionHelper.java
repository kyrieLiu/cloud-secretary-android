package com.luck.cloud.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuyin on 2018/7/19
 *
 * @usage android >=M 的权限申请统一处理,动态申请权限
 */

public class PermissionHelper {

    private static final int REQUEST_PERMISSION_CODE = 1000;

    private Object mContext;

    private PermissionListener mListener;

    private List<String> mPermissionList;

    private static volatile PermissionHelper instance;

    public static PermissionHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PermissionHelper(context);
        }
        return instance;
    }

    public PermissionHelper(@NonNull Object object) {
        checkCallingObjectSuitability(object);
        this.mContext = object;

    }


    /**
     * 权限授权申请
     *
     * @param hintMessage 要申请的权限的提示
     * @param permissions 要申请的权限
     * @param listener    申请成功之后的callback
     */
    public void requestPermissions(@NonNull CharSequence hintMessage,
                                    PermissionListener listener,
                                   @NonNull final String... permissions) {

        if (listener != null) {
            mListener = listener;
        }

        mPermissionList = Arrays.asList(permissions);

        //没全部权限
        if (!hasPermissions(mContext, permissions)) {
            //对于6.0以下机型被拒绝的权限无法动态申请,只能去设置打开
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                listener.doAfterDenied();
            } else {
                boolean shouldShowRationale = false;
                for (String perm : permissions) {
                    shouldShowRationale =
                            shouldShowRationale || shouldShowRequestPermissionRationale(mContext, perm);
                }

                if (shouldShowRationale) {
                    showMessageOKCancel(hintMessage, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executePermissionsRequest(mContext, permissions,
                                    REQUEST_PERMISSION_CODE);
                        }
                    });
                } else {
                    executePermissionsRequest(mContext, permissions,
                            REQUEST_PERMISSION_CODE);
                }
            }

        } else if (mListener != null) { //有全部权限
            mListener.doAfterGrand(permissions);
        }
    }

    /**
     * 处理onRequestPermissionsResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void handleRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                boolean allGranted = true;
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false;
                        break;
                    }
                }

                allGranted = allGranted && permissions.length != 0;
                if (allGranted && mListener != null) {
                    mListener.doAfterGrand((String[]) mPermissionList.toArray());
                } else if (!allGranted && mListener != null) {
                    mListener.doAfterDenied((String[]) mPermissionList.toArray());
                }
                break;
        }
    }

    /**
     * 判断是否具有某权限
     *
     * @param object
     * @param perms
     * @return
     */
    public static boolean hasPermissions(@NonNull Object object, @NonNull String... perms) {

        for (String perm : perms) {
            //6.0以下有部分机型也需要做权限申请
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (perm.equals("android.permission.CAMERA")) {
                    boolean hasCameraPermission = isHasCameraPermission();
                    if (!hasCameraPermission) return false;
                } else if (perm.equals("android.permission.RECORD_AUDIO")) {
                    boolean hasAudioPermission = isHasAudioPermission();
                    if (!hasAudioPermission) return false;
                }
            } else {
                boolean hasPerm = (ContextCompat.checkSelfPermission(getActivity(object), perm) ==
                        PackageManager.PERMISSION_GRANTED);
                if (!hasPerm) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * 判断6.0以下机型是否有拍照或录像权限
     *
     * @return
     */
    public static boolean isHasCameraPermission() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(); // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 判断6.0以下手机是否有录音权限
     *
     * @return
     */
    public static boolean isHasAudioPermission() {
        // 音频获取源
        int audioSource = MediaRecorder.AudioSource.MIC; // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        int sampleRateInHz = 44100; // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO; // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT; // 缓冲区字节大小
        int bufferSizeInBytes = 0;

        bufferSizeInBytes = 0;
        boolean flag;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes); //开始录制音频
        try { // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } /** * 根据开始录音判断是否有录音权限 */
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            flag = false;
        } else {
            flag = true;
        }
        audioRecord.stop();
        audioRecord.release();
        return flag;
    }


    /**
     * 兼容fragment
     *
     * @param object
     * @param perm
     * @return
     */
    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale(@NonNull Object
                                                                        object, @NonNull String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    /**
     * 执行申请,兼容fragment
     *
     * @param object
     * @param perms
     * @param requestCode
     */
    @TargetApi(23)
    private void executePermissionsRequest(@NonNull Object object, @NonNull String[] perms,
                                           int requestCode) {
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    /**
     * 检查传递Context是否合法
     *
     * @param object
     */
    private void checkCallingObjectSuitability( Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }

        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof Fragment;
        if (!(isSupportFragment || isActivity || (isAppFragment && isNeedRequest()))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }


    @TargetApi(11)
    private static Activity getActivity(@NonNull Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    /**
     * 权限被拒绝后重新申请权限
     *
     * @param hintMessage
     */
    public void againWarnRequestPermission(String hintMessage, final Context context) {
        showAgainMessage(hintMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
                context.startActivity(localIntent);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    public static boolean isNeedRequest() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void showMessageOKCancel(CharSequence message, DialogInterface.OnClickListener
            okListener) {
        new AlertDialog.Builder(getActivity(mContext))
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .create()
                .show();
    }

    public void showAgainMessage(CharSequence message, DialogInterface.OnClickListener
            okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(getActivity(mContext))
                .setMessage(message)
                .setPositiveButton("手动去授权", okListener)
                .setNegativeButton("取消", cancelListener)
                .create()
                .show();
    }

    public interface PermissionListener {

        void doAfterGrand(String... permission);

        void doAfterDenied(String... permission);
    }
}