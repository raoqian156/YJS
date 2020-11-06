package com.yskrq.yjs.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.EasyViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.OnClick;
import com.yskrq.common.util.LOG;
import com.yskrq.net_library.BaseBean;
import com.yskrq.net_library.RequestType;
import com.yskrq.net_library.okhttp.SPUtil;
import com.yskrq.yjs.R;
import com.yskrq.yjs.bean.PhotosBean;
import com.yskrq.yjs.net.HttpManager;
import com.yskrq.yjs.util.ImageLoadUtil;
import com.yskrq.yjs.util.PathUtils;
import com.yskrq.yjs.widget.CustomPopWindow;
import com.yskrq.yjs.widget.PopUtil;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import ecomm.lib_comm.permission.PermissionUtil;

import static com.yskrq.net_library.HttpProxy.TAG_CHANGE_URL;
import static com.yskrq.yjs.net.Constants.TransCode.UploadPic;
import static com.yskrq.yjs.net.Constants.TransCode.deletePic;
import static com.yskrq.yjs.net.Constants.TransCode.getTecCoverPhotos;
import static com.yskrq.yjs.net.Constants.TransCode.getTecPhotos;

public class PhotoActivity extends BaseActivity implements View.OnClickListener,
                                                           PopUtil.OnPhotoFromSelectListener,
                                                           OnItemClickListener {
  public static void start(Context context) {
    Intent intent = new Intent(context, PhotoActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutId() {
    return R.layout.act_photo;
  }

  BaseAdapter mAdapter;

  @Override
  protected void initView() {
    initTitle("云相册");
    mAdapter = new BaseAdapter(this, R.layout.item_photo, EasyViewHolder.class) {
      @Override
      public void onBindEasyHolder(BaseViewHolder holder, int position, Object o) {
        super.onBindEasyHolder(holder, position, o);
        if (o == null) return;
        PhotosBean.ValueBean bean = (PhotosBean.ValueBean) o;
        ImageView iv = (ImageView) holder.getItemView(R.id.iv);
        ImageLoadUtil.display(getFullPath(bean.getImage()), iv);
      }
    };
    new RecyclerUtil(mAdapter).row(4).set2View((RecyclerView) findViewById(R.id.recycler));
    mAdapter.setFootHolder(0, null, EasyViewHolder.class, R.layout.item_photo_add, this);
    mAdapter.addOnItemClickListener(EasyViewHolder.class, this, R.id.btn_delete, R.id.btn_add);
    HttpManager.getTecCoverPhotos(this);
    HttpManager.getTecPhotos(this);
    onAdapterSDK24();
  }

  @Override
  public <T extends BaseBean> void onResponseSucceed(@NonNull RequestType type, @NonNull T data) {
    super.onResponseSucceed(type, data);
    if (type.is(getTecCoverPhotos)) {
      String path = data.getStrInList("ImageId1");
      if (TextUtils.isEmpty(path)) {
        findViewById(R.id.fl_photo).setVisibility(View.GONE);
        setString2View( R.id.tv_num1,"0/1");
      } else {
        setString2View( R.id.tv_num1,"1/1");
        findViewById(R.id.fl_photo).setVisibility(View.VISIBLE);
        String url = getFullPath(path);
        ImageLoadUtil.display(url, (ImageView) findViewById(R.id.iv_top));
        LOG.e("PhotoActivity", "onResponseSucceed.47:" + url);
      }
    } else if (type.is(getTecPhotos)) {
      PhotosBean bean = (PhotosBean) data;
      setString2View( R.id.tv_num2,bean.getValues().size() + "/5");
      mAdapter.setData(bean.getValues());
    } else if (type.is(UploadPic) || type.is(deletePic)) {
      dismissLoading();
      HttpManager.getTecCoverPhotos(this);
      HttpManager.getTecPhotos(this);
    }
  }

  private String getFullPath(String path) {
    return "https://" + SPUtil.getString(this, TAG_CHANGE_URL) + ":" + "9092" + path;
  }

  CustomPopWindow mpop;

  @OnClick({R.id.btn_take, R.id.btn_delete})
  public void onClick(View v) {
    if (v.getId() == R.id.btn_delete) {
      HttpManager.deletePic(this, 0, true);
    } else if (v.getId() == R.id.btn_take) {
      isFen = true;
      if (mpop == null) {
        mpop = PopUtil.getPhotoWindow(this, this);
      } else {
        mpop.show();
      }
    }
  }

  int select;
  boolean isFen = false; //是否封面图

  @Override
  public boolean onSelect(int select) {
    this.select = select;
    openCamera();
    return false;
  }

  private void openCamera() {
    PermissionUtil.selectLocalFile(this, new PermissionUtil.OnPermissionListener() {
      @Override
      public void onPermissionOk() {
        mpop.dissmiss();
        if (PhotoActivity.this.select == SELECT_PHOTO) {//本地相册
          headGallery();
        } else if (PhotoActivity.this.select == SELECT_TAKE) {//拍照
          headCamera();
        }
      }
    });
  }

  Uri imageUri;// = Uri.parse(IMAGE_FILE_LOCATION); ;//The Uri to store the big bitmap

  /**
   * targetSdkVersion指定成24及之上并且在API>=24的设备上运行时。以原来方式运行，会出现FileUriExposedException异常
   * 因为Android不再允许在app中把file://Uri暴露给其他app，包括但不局限于通过Intent或ClipData 等方法。
   * 解决方式如下：
   * 1.AndroidManifest.xml中 添加 provider
   * 2.添加 res/xml/provider_paths.xml，并增加 <external-path name="yjs" path="yjs"/>
   * 3.调用以下方法
   */
  private void onAdapterSDK24() {
    try {
      //storagePath要和provider_paths.xml的路径保持一致
      String storagePath = Environment.getExternalStorageDirectory()
                                      .getPath() + File.separator + "yjs";
      //KLog.e(storagePath);
      File storageDir = new File(storagePath);
      storageDir.mkdirs();
      File imageFile = File.createTempFile(System.currentTimeMillis() + "", ".jpg", storageDir);
      //以下方式替换Uri.parse(IMAGE_FILE_LOCATION);
      imageUri = FileProvider
          .getUriForFile(this, this.getPackageName() + ".fileProvider", imageFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static final int PHOTO_REQUEST_TAKEPHOTO = 1; // 拍照
  public static final int PHOTO_REQUEST_GALLERY = 2; // 从相册中选择

  /**
   * 点从相册选取
   */
  private void headGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK, null);
    // 指定调用相机拍照后照片的储存路径
    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
  }

  /**
   * 点击拍照
   */
  private void headCamera() {
    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    it.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    startActivityForResult(it, PHOTO_REQUEST_TAKEPHOTO);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    LOG.e("SettingController", "onActivityOther.142:");
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case PHOTO_REQUEST_TAKEPHOTO:
          cropImageUri(imageUri);
        case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
          if (data != null) {
            cropImageUri(data.getData());
          }
        default:
          break;
      }
    }
  }

  String filePath;

  /**
   * 调用截图
   */
  private void cropImageUri(Uri uri) {
    try {
      this.filePath = PathUtils.getPath(this, uri);
      uploadChange();
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOG.e("SettingController", "cropImageUri.filePath:" + filePath);
  }

  private void uploadChange() {
    HttpManager.UploadPic(this, this.filePath, isFen);
  }
  //  private void show(String path) {
  //    ImageView img = findViewById(R.id.iv_top);
  //    Bitmap bm = BitmapFactory.decodeFile(path);
  //    img.setImageBitmap(bm);
  //  }

  @Override
  public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
    if (view.getId() == R.id.btn_delete) {
      LOG.e("PhotoActivity", "onItemClick.249:" + position);
      HttpManager.deletePic(this, position, false);
    } else if (view.getId() == R.id.btn_add) {
      isFen = false;
      if (mpop == null) {
        mpop = PopUtil.getPhotoWindow(this, this);
      } else {
        mpop.show();
      }
    }
  }
}
