package com.yskrq.yjs.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.yskrq.common.util.LOG;

import java.io.IOException;
import java.util.List;

import androidx.annotation.Nullable;

public class LocationUtil {
  private static OnResponseListener responseListener;
  private static LocationUtil locationUtil;

  private LocationManager locationManager;
  private Location currentBestLocation;
  private NetworkListener networkListener;
  private GPSLocationListener gpsListener;

  private LocationUtil() {
  }

  /**
   * 定位模式
   */
  public enum Mode {
    NETWORK,    //网络定位
    GPS,        //GPS定位
    AUTO        //自动定位,使用网络或GPS定位
  }


  public static final int NO_PERMISSION = 1;  //没权限
  public static final int GPS_CLOSE = 2;  //GPS是关闭的
  public static final int UNAVAILABLE = 3;  //不可用

  /**
   * 请求定位
   */
  public static void requestLocation(Context context, Mode mode,
                                     OnResponseListener onResponseListener) {
    LocationUtil.responseListener = onResponseListener;
    //    if (PermissionUtil
    //        .checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtil
    //        .checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
    Log.i("LocationUtil", "获取定位权限,开始定位");
    //开始定位
    locationUtil = new LocationUtil();
    locationUtil.startLocation(context, mode);
    //    } else {
    //      Log.i("LocationUtil", "没有定位权限,定位失败");
    //      String provider = mode == Mode.GPS ? LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER;
    //      onResponseListener.onErrorResponse(provider, NO_PERMISSION);
    //    }
  }

  public static void stop(){
    if(locationUtil!=null){
      locationUtil.stopLocation();
    }
  }
  long l;

  /**
   * 开始定位
   */
  @SuppressLint("MissingPermission")
  private void startLocation(Context context, Mode mode) {
    LOG.e("LocationUtil", "startLocation.72:");
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    //    if (PermissionUtil
    //        .checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtil
    //        .checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
    switch (mode) {
      case NETWORK:
        LOG.e("LocationUtil", "startLocation.82:");
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
          Log.i("LocationUtil", "网络定位");
          networkListener = new NetworkListener();
          locationManager
              .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0.1f, networkListener);
        } else {
          if (responseListener != null)
            responseListener.onErrorResponse(LocationManager.NETWORK_PROVIDER, UNAVAILABLE);
        }
        break;
      case GPS:
        LOG.e("LocationUtil", "startLocation.93:");
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
          Log.i("LocationUtil", "GPS定位");
          gpsListener = new GPSLocationListener();
          l = System.currentTimeMillis();
          locationManager
              .requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 2, 0.1f, gpsListener);
        } else {
          if (responseListener != null)
            responseListener.onErrorResponse(LocationManager.GPS_PROVIDER, GPS_CLOSE);
        }

        break;
      case AUTO:
        LOG.e("LocationUtil", "startLocation.106:");
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
          Log.i("LocationUtil", "自动定位选择网络定位");
          networkListener = new NetworkListener();
          locationManager
              .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0.1f, networkListener);
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
          Log.i("LocationUtil", "自动定位选择gps");
          gpsListener = new GPSLocationListener();
          locationManager
              .requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 2, 0.1f, gpsListener);
        } else {
          LOG.e("LocationUtil", "startLocation.118:");
          if (responseListener != null)
            responseListener.onErrorResponse(LocationManager.NETWORK_PROVIDER, UNAVAILABLE);
        }

        break;
      default:
        LOG.e("LocationUtil", "startLocation.default:");
        break;
    }
    //    } else {
    //      Log.e("LocationUtil", "无权限");
    //    }
  }

  /**
   * 停止定位
   */
  public static void stopLocation() {
    if (locationUtil == null) {
      Log.e("LocationUtil", "locationUtil is null");
      return;
    }
    if (locationUtil.networkListener != null) {
      locationUtil.locationManager.removeUpdates(locationUtil.networkListener);
    }
    if (locationUtil.gpsListener != null) {
      locationUtil.locationManager.removeUpdates(locationUtil.gpsListener);
    }
    Log.i("LocationUtil", "停止定位");
  }

  private class GPSLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
      Log.i("LocationUtil", "onLocationChanged");
      if (location != null) {
        Log.i("LocationUtil", "GPS定位成功");
        Log.i("LocationUtil", "GPS定位耗时:" + ((System.currentTimeMillis() - l) / 1000) + "秒");
        boolean isBetter = isBetterLocation(location, currentBestLocation);
        if (isBetter) {
          currentBestLocation = location;
        }
        double latitude = currentBestLocation.getLatitude();    //纬度
        double longitude = currentBestLocation.getLongitude();  //经度
        if (responseListener != null) responseListener.onSuccessResponse(latitude, longitude);
      } else {
        Log.i("LocationUtil", "location is null");
      }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      Log.i("LocationUtil", "onStatusChanged:" + status);
      if (status == LocationProvider.OUT_OF_SERVICE || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
        Log.i("LocationUtil", "GPS定位失败");
        //如果之前没有开启过网络定位,自动切换到网络定位
        if (networkListener == null) {
          //开启网络定位
          networkListener = new NetworkListener();
          locationManager
              .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkListener);
        }
      }
    }

    @Override
    public void onProviderEnabled(String provider) {
      Log.i("LocationUtil", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
      Log.i("LocationUtil", "onProviderDisabled");
    }
  }

  private class NetworkListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
      if (location != null) {
        Log.i("LocationUtil", "网络定位成功");

        boolean isBetter = isBetterLocation(location, currentBestLocation);
        if (isBetter) {
          currentBestLocation = location;
        }
        double latitude = currentBestLocation.getLatitude();    //纬度
        double longitude = currentBestLocation.getLongitude();  //经度
        if (responseListener != null) responseListener.onSuccessResponse(latitude, longitude);

      }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      if (status == LocationProvider.OUT_OF_SERVICE || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
        Log.i("LocationUtil", "网络定位失败");
        if (responseListener != null)
          responseListener.onErrorResponse(LocationManager.NETWORK_PROVIDER, UNAVAILABLE);
      }
    }

    @Override
    public void onProviderEnabled(String provider) {
      Log.e("LocationUtil", "可用");
    }

    @Override
    public void onProviderDisabled(String provider) {
      Log.e("LocationUtil", "不可用");
    }
  }

  private static final int TWO_MINUTES = 1000 * 60 * 2;

  /**
   * 比较最新获取到的位置是否比当前最好的位置更好
   *
   * @param location            最新获得的位置
   * @param currentBestLocation 当前获取到的最好的位置
   *
   * @return 最新获取的位置比当前最好的位置更好则返回true
   */
  private boolean isBetterLocation(Location location, Location currentBestLocation) {
    if (currentBestLocation == null) {
      // A new locationUtil is always better than no locationUtil
      return true;
    }

    //实时性
    // Check whether the new locationUtil fix is newer or older
    long timeDelta = location.getTime() - currentBestLocation.getTime();
    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES; //最新位置比当前位置晚两分钟定位
    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;//最新位置比当前位置早两分钟定位
    boolean isNewer = timeDelta > 0;

    // If it's been more than two minutes since the current locationUtil, use the new locationUtil
    // because the user has likely moved
    if (isSignificantlyNewer) {
      return true;
      // If the new locationUtil is more than two minutes older, it must be worse
    } else if (isSignificantlyOlder) {
      return false;
    }

    //精确性
    // Check whether the new locationUtil fix is more or less accurate
    //locationUtil.getAccuracy()值越小越精确
    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
    boolean isLessAccurate = accuracyDelta > 0; //最新的位置不如当前的精确
    boolean isMoreAccurate = accuracyDelta < 0; //最新的位置比当前的精确
    //最新的位置不如当前的精确，但是相差在一定范围之内
    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

    // Check if the old and new locationUtil are from the same provider
    boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation
        .getProvider());

    // Determine locationUtil quality using a combination of timeliness and accuracy
    if (isMoreAccurate) {
      return true;
    } else if (isNewer && !isLessAccurate) {
      return true;
    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
      return true;
    }
    return false;
  }

  /**
   * Checks whether two providers are the same
   */
  private boolean isSameProvider(String provider1, String provider2) {
    if (provider1 == null) {
      return provider2 == null;
    }
    return provider1.equals(provider2);
  }

  public interface OnResponseListener {
    /**
     * 定位成功
     *
     * @param latitude  纬度
     * @param longitude 经度
     */
    void onSuccessResponse(double latitude, double longitude);

    /**
     * 定位失败
     *
     * @param provider provider
     * @param status   失败码
     */
    void onErrorResponse(String provider, int status);
  }

  /**
   * 获取地址
   *
   * @param context   Context
   * @param latitude  纬度
   * @param longitude 经度
   *
   * @return Address
   */
  public static @Nullable
  Address getAddress(Context context, double latitude, double longitude) {
    Geocoder geocoder = new Geocoder(context);
    try {
      List<Address> list = geocoder.getFromLocation(latitude, longitude, 3);
      if (list.size() > 0) {
        Address address = list.get(0);
        Log.i("LocationUtil", "省:" + address.getAdminArea());
        Log.i("LocationUtil", "市:" + address.getLocality());
        Log.i("LocationUtil", "县/区:" + address.getSubLocality());
        return address;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
