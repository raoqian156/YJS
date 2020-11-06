package ecomm.lib_comm.permission;

import android.Manifest;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;

public class PermissionUtil {

    public interface OnPermissionListener {

        void onPermissionOk();
    }

    public static void openIMEI(Activity activity, final OnPermissionListener listener) {
        ArrayList<String> permissions = new ArrayList<>();
        //        android.permission.READ_PRIVILEGED_PHONE_STATE
        Collections.addAll(permissions, Manifest.permission.READ_PHONE_STATE);
        String[] arr = permissions.toArray(new String[0]);
        new UsesPermission(activity, activity, arr) {

            @Override
            protected void onComplete(@NonNull ArrayList<String> resolvePermissions,
                                      @NonNull ArrayList<String> lowerPermissions,
                                      @NonNull ArrayList<String> rejectFinalPermissions,
                                      @NonNull ArrayList<String> rejectPermissions,
                                      @NonNull ArrayList<String> invalidPermissions) {
                if (resolvePermissions.contains(Manifest.permission.READ_PHONE_STATE)) {
                    listener.onPermissionOk();
                }
            }
        };
    }

    /**
     * 常用权限获取 - 拍照
     */
    public static void openCamera(Activity activity, final OnPermissionListener listener) {
        ArrayList<String> permissions = new ArrayList<>();
        Collections
                .addAll(permissions, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        String[] arr = permissions.toArray(new String[0]);
        new UsesPermission(activity, activity, arr) {

            @Override
            protected void onComplete(@NonNull ArrayList<String> resolvePermissions,
                                      @NonNull ArrayList<String> lowerPermissions,
                                      @NonNull ArrayList<String> rejectFinalPermissions,
                                      @NonNull ArrayList<String> rejectPermissions,
                                      @NonNull ArrayList<String> invalidPermissions) {
                if (resolvePermissions.contains(Manifest.permission.CAMERA) && resolvePermissions
                        .contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    listener.onPermissionOk();
                }
            }
        };
    }

    /**
     * 常用权限获取 - 定位
     */
    public static void openLocate(Activity activity, final OnPermissionListener listener) {
        ArrayList<String> permissions = new ArrayList<>();
        Collections
                .addAll(permissions, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE);
        String[] arr = permissions.toArray(new String[0]);
        new UsesPermission(activity, activity, arr) {

            @Override
            protected void onComplete(@NonNull ArrayList<String> resolvePermissions,
                                      @NonNull ArrayList<String> lowerPermissions,
                                      @NonNull ArrayList<String> rejectFinalPermissions,
                                      @NonNull ArrayList<String> rejectPermissions,
                                      @NonNull ArrayList<String> invalidPermissions) {
                if (resolvePermissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    listener.onPermissionOk();
                }
            }
        };
    }


    public static void updateApk(Activity activity, final OnPermissionListener listener) {
        ArrayList<String> permissions = new ArrayList<>();
        Collections
                .addAll(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.READ_EXTERNAL_STORAGE);
        String[] arr = permissions.toArray(new String[0]);
        new UsesPermission(activity, activity, arr) {

            @Override
            protected void onComplete(@NonNull ArrayList<String> resolvePermissions,
                                      @NonNull ArrayList<String> lowerPermissions,
                                      @NonNull ArrayList<String> rejectFinalPermissions,
                                      @NonNull ArrayList<String> rejectPermissions,
                                      @NonNull ArrayList<String> invalidPermissions) {
                if (resolvePermissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    listener.onPermissionOk();
                }
            }
        };
    }

    /**
     * 常用权限获取 - 读写操作
     */
    public static void selectLocalFile(Activity activity, final OnPermissionListener listener) {
        ArrayList<String> permissions = new ArrayList<>();
        Collections
                .addAll(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        String[] arr = permissions.toArray(new String[0]);
        new UsesPermission(activity, activity, arr) {

            @Override
            protected void onComplete(@NonNull ArrayList<String> resolvePermissions,
                                      @NonNull ArrayList<String> lowerPermissions,
                                      @NonNull ArrayList<String> rejectFinalPermissions,
                                      @NonNull ArrayList<String> rejectPermissions,
                                      @NonNull ArrayList<String> invalidPermissions) {
                if (resolvePermissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    listener.onPermissionOk();
                }
            }
        };
    }

}
