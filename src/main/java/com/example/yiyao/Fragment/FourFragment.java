package com.example.yiyao.Fragment;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.yiyao.LoginActivity;
import com.example.yiyao.R;
import com.example.yiyao.Utils.ImageTools;
import com.example.yiyao.Utils.ImageUri;
import com.example.yiyao.Utils.LoadDialog;
import com.example.yiyao.Utils.NetService;
import com.example.yiyao.Utils.TmallUtils;
import com.example.yiyao.Utils.UploadService;
import com.example.yiyao.fourFragment.DaiZhiFuActivity;
import com.example.yiyao.fourFragment.DingDanActivity;
import com.example.yiyao.fourFragment.KaQuanActivity;
import com.example.yiyao.fourFragment.QianDaoActivity;
import com.example.yiyao.fourFragment.SheZhiActivity;
import com.example.yiyao.fourFragment.ShouCangActivity;
import com.example.yiyao.fourFragment.YiJianActivity;
import com.example.yiyao.pojo.User;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//个人中心页面
public class FourFragment extends Fragment implements View.OnClickListener {

    //QQ属性
    public static Tencent mTencent;
    // QQ用户信息
    private UserInfo mInfo;
    //QQ名称
    private String qqname;

    private int code;
    //图像名
    private String userImage;
    private String backgroundImage;

    private static final String TAG = "MainActivity";
    //初始化控件
    private TextView tvQianDao, tvUserNiCheng, userpost, userphone;
    private LinearLayout daiZhiFu, daiShouHuo, daiPingLun, dingDan, shouHou, shouCang, kaQuan, yiJian, sheZhi;
    private ImageView ivUserUrl, userBackgroundImage;
    //获取用户选项存储类
    private User user;


    public FourFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_four, container, false);
        //user = TmallUtils.getUser(getActivity());
        //start QQ
        mTencent = LoginActivity.mTencent;

        initView(view);
        //updateUserInfo();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ivUserUrl.setClickable(true);
        userBackgroundImage.setClickable(true);

        user = TmallUtils.getUser(getActivity());
        //设置用户昵称
        if (user.getU_id() > 0) {
            //已登录
            if (user.getU_nickname() != null && !user.getU_nickname().equals("")) {
                tvUserNiCheng.setText(user.getU_nickname());

            } else {
                tvUserNiCheng.setText("未设置");
            }

        } else {
            //未登录
            tvUserNiCheng.setText("未登录");
        }
        //设置用户性别
        if (user.getU_sex() != null && !user.getU_sex().equals("")) {
            userpost.setText("性别：" + user.getU_sex());
        } else {
            userpost.setText("性别：未填写");
        }
        //设置用户绑定手机
        userphone.setText("绑定手机：" + user.getU_mobile());
        //************************************************
        Log.v("MainActivity", user.getU_imageurl() + "8888");
        if (user.getU_imageurl() != null && !user.getU_imageurl().equals("")) {
            ImageLoader imageLoader = NetService.getInstance(getActivity()).getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(ivUserUrl, R.drawable.signin_local_gallry, R.drawable.signin_local_gallry);
            imageLoader.get(NetService.BASE_URL + user.getU_imageurl(), listener);
        }
        if (user.getU_backgroundimageurl() != null && !user.getU_backgroundimageurl().equals("")) {
            ImageLoader imageLoader = NetService.getInstance(getActivity()).getImageLoader();
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(userBackgroundImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            imageLoader.get(NetService.BASE_URL + user.getU_backgroundimageurl(), listener);
        }
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    private void initView(View view) {
        //初始化控件
        //待支付 待收货  待评价
        daiZhiFu = (LinearLayout) view.findViewById(R.id.linear_daizhifu);
        daiShouHuo = (LinearLayout) view.findViewById(R.id.linear_daishouhuo);
        daiPingLun = (LinearLayout) view.findViewById(R.id.linear_daipinglun);
        //订单
        dingDan = (LinearLayout) view.findViewById(R.id.linear_dingdan);
        //收货
        shouHou = (LinearLayout) view.findViewById(R.id.linear_shouhou);
        //收藏
        shouCang = (LinearLayout) view.findViewById(R.id.linear_shoucang);
        //卡券
        kaQuan = (LinearLayout) view.findViewById(R.id.linear_kaquan);
        //意见
        yiJian = (LinearLayout) view.findViewById(R.id.linear_yijian);
        //设置
        sheZhi = (LinearLayout) view.findViewById(R.id.linear_shezhi);
        //签到
        tvQianDao = (TextView) view.findViewById(R.id.attention_user);
        //用户头像
        ivUserUrl = (ImageView) view.findViewById(R.id.user_avatar);
        //背景头像
        userBackgroundImage = (ImageView) view.findViewById(R.id.user_background_url);
        //用户昵称
        tvUserNiCheng = (TextView) view.findViewById(R.id.user_name);
        //性别
        userpost = (TextView) view.findViewById(R.id.user_post);
        //电话
        userphone = (TextView) view.findViewById(R.id.user_phone);
        //设置点击监听
        //待支付监听事件
        daiZhiFu.setOnClickListener(this);
        //待收货监听事件
        daiShouHuo.setOnClickListener(this);
        //带评论监听事件
        daiPingLun.setOnClickListener(this);
        //订单监听事件
        dingDan.setOnClickListener(this);
        //收货监听事件
        shouHou.setOnClickListener(this);
        //收藏监听事件
        shouCang.setOnClickListener(this);
        //卡卷监听事件
        kaQuan.setOnClickListener(this);
        //意见监听事件
        yiJian.setOnClickListener(this);
        //设计监听事件
        sheZhi.setOnClickListener(this);
        //签到监听事件
        tvQianDao.setOnClickListener(this);
        //用户头像监听事件
        ivUserUrl.setOnClickListener(this);
        //背景图片监听事件
        userBackgroundImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //图像
            case R.id.user_avatar:
                code = 1;
                if (user.getU_id() == 0) {
                    QingDengLu();
                } else {
                    changeUserImage();
                    //ivUserUrl.setClickable(false);
                }
                break;
            case R.id.user_background_url:
                code = 2;
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    changeUserImage();
                }
                break;
            case R.id.attention_user:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    startActivity(new Intent(getActivity(), QianDaoActivity.class));
                }
                break;
            case R.id.linear_shezhi:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    startActivity(new Intent(getActivity(), SheZhiActivity.class));
                }
                break;
            case R.id.linear_shoucang:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    startActivity(new Intent(getActivity(), ShouCangActivity.class));
                }
                break;
            case R.id.linear_kaquan:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    startActivity(new Intent(getActivity(), KaQuanActivity.class));
                }
                break;
            case R.id.linear_yijian:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    startActivity(new Intent(getActivity(), YiJianActivity.class));
                }
                break;
            case R.id.linear_daizhifu:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    Intent intent = new Intent(getActivity(), DaiZhiFuActivity.class);
                    intent.putExtra("o_state", "1");
                    startActivity(intent);
                }
                break;
            case R.id.linear_daipinglun:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    Intent intent = new Intent(getActivity(), DaiZhiFuActivity.class);
                    intent.putExtra("o_state", "3");
                    startActivity(intent);
                }
                break;
            case R.id.linear_daishouhuo:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    Intent intent = new Intent(getActivity(), DaiZhiFuActivity.class);
                    intent.putExtra("o_state", "2");
                    startActivity(intent);
                }
                break;
            case R.id.linear_shouhou:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    Intent intent = new Intent(getActivity(), DaiZhiFuActivity.class);
                    intent.putExtra("o_state", "4");
                    startActivity(intent);
                }
                break;
            case R.id.linear_dingdan:
                if (user.getU_id() == 0) {
                    QingdengLu();
                } else {
                    Intent intent = new Intent(getActivity(), DingDanActivity.class);
                    intent.putExtra("o_state", "0");
                    startActivity(intent);
                }
                break;

        }


    }


    private void QingdengLu() {
        TmallUtils.showToast(getActivity(), "亲，赶快登录哟");
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    /**
     * 修改用户图片的点击事件
     */

    //头象点击选择事件
    private static final int TAKE_PHOTO = 0;
    private static final int CHOOSE_PHOTO = 1;
    private static final int SCALE = 5;
    private String uploadFile;

    private void changeUserImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("图片来源：");
        builder.setNegativeButton(R.string.four_tishi_btn_canl, null);
        builder.setItems(new CharSequence[]{"拍照上传", "相册上传"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case TAKE_PHOTO:
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                        //指定我们照片保存路径（sd）卡，image.jpg为一个临时文件，然后每次拍照图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, TAKE_PHOTO);
                        break;
                    case CHOOSE_PHOTO:
                        //指定一个意图去访问内容提供者
                        Intent chooseCameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        //过滤除去图片之外的内容
                        chooseCameraIntent.setType("image/*");
                        startActivityForResult(chooseCameraIntent, CHOOSE_PHOTO);
                        break;
                }
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    // 将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(
                            Environment.getExternalStorageDirectory() + "/image.jpg");
                    // 缩小相机的图片
                    Bitmap newBitmap = ImageTools.zoomBitmap(
                            bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    ivUserUrl.setImageBitmap(newBitmap);
                    // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();

                    // 同时复制至存储相片的目录
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String filename = sdf.format(new Date());
                    // 文件名
                    filename = "IMG_" + filename;
                    // 图片目录
                    String dir = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .getAbsolutePath()
                            + "/Camera";
                    ImageTools.savePhotoToSDCard(newBitmap, dir, filename);
                    // 上传文件名
                    uploadFile = dir + "/" + filename + ".png";
                    break;
                case CHOOSE_PHOTO:
                    ContentResolver resolver = getActivity().getContentResolver();
                    // 照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        // 通过内容访问者和图片url获得bitmap的图片(相册中的原图)
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(
                                    photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            // 释放原始图片占用的内存，防止out of memory异常发生
                            ivUserUrl.setImageBitmap(smallBitmap);
                            photo.recycle();
                            // 上传文件名
                            uploadFile = ImageUri.getImageAbsolutePath(getActivity(), originalUri);
                            Log.v("MainActivity", originalUri.toString());
                        }
                    } catch (FileNotFoundException e) {
                        TmallUtils.showToast(getActivity(), "文件不存在");
                    } catch (IOException e) {
                        TmallUtils.showToast(getActivity(), "读取文件,出错啦");
                    }
                    break;
            }
            //***上传图片到服务器********************************************
            String requestUrl = NetService.BASE_URL + "UploadServlet";
            File file = new File(uploadFile);
            LoadDialog dialog = new LoadDialog(getActivity());
            dialog.execute(new LoadDialog.Callback() {
                @Override
                public void getResult(Object obj) {
                    System.out.println(obj + "");
                }
            }, UploadService.class, "postUseUrlConnection", requestUrl, file);


            // 将 imageurl 保存至首选项存储
            final User user = TmallUtils.getUser(getActivity());
            //(保存在数据库中的地址)
            final String imageUrl = "images" + uploadFile.substring(uploadFile.lastIndexOf("/"), uploadFile.length());
            //保存在选项存储中的地址
            user.setU_imageurl(NetService.BASE_URL + imageUrl);
            TmallUtils.saveUser(getActivity(), user);
            Log.v("MainActivity", "99" + imageUrl);
            //更新网络上用户的图像
            imageToService();
        }
    }

    private void imageToService() {
        //图片的地址。
        userImage = "images/userimage" + uploadFile.substring(uploadFile.lastIndexOf("/"), uploadFile.length());
        backgroundImage = "images/userimage" + uploadFile.substring(uploadFile.lastIndexOf("/"), uploadFile.length());
        String url = NetService.BASE_URL + "UpdateUserServlet";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.v("MainActivity", s);
                        try {
                            JSONObject jo = new JSONObject(s);
                            String flag = jo.getString("flag");
                            if (flag.equals("success")) {
                                TmallUtils.showToast(getActivity(), "网络更换图片成功");
                                //保存到选项存储中
                                user.setU_imageurl(userImage);
                                user.setU_backgroundimageurl(backgroundImage);
                                TmallUtils.saveUser(getActivity(), user);
                            } else if (flag.equals("error")) {
                                TmallUtils.showToast(getActivity(), "网络更换图片失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TmallUtils.showToast(getActivity(), "网络连接失败！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("u_id", user.getU_id() + "");
                map.put("u_image_url", userImage);
                map.put("u_backgroundimageurl", backgroundImage);
                return map;
            }
        };
        NetService.getInstance(getActivity()).addToRequestQueue(request, "imageRequest");
    }

    /**
     * 请登录的封装事件
     */
    private void QingDengLu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("亲，查看此项须先登录哟");
        builder.setMessage("前去登录...");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {

                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetService.getInstance(getActivity()).cancelRequest("imageRequest");
    }
}
