package com.technest.needfood.driver.akun;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.user.ResponDriver;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.utils.Constanta;
import com.vatsal.imagezoomer.ZoomAnimation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technest.needfood.intro.LoginActivity.my_shared_preferences;

public class AkunDriverFragment extends Fragment {

    View v;

    private String id;
    private String role;
    private String nama;
    private String alamat;
    private String email;
    private String telepon;
    private String foto;
    private String status;
    private String username;

    private boolean load_first = true;

    private TextView tv_nama;
    private TextView tv_username;
    private TextView tv_telpon;
    private TextView tv_alamat;
    private TextView tv_btn_edit;
    private TextView tv_btn_password;

    //sliding
    private SlidingUpPanelLayout sliding_layout_akun;
    //continer 1
    private RelativeLayout continer_edit_profile;
    private TextInputEditText tie_nama_lengkap;
    private TextInputEditText tie_username;
    private TextInputEditText tie_telpon;
    private TextInputEditText tie_alamat;
    private TextView tv_btn_batal_profile;
    private TextView tv_btn_simpan_profile;
    //continer 2
    private RelativeLayout continer_edit_password;
    private TextInputEditText tie_password_lama;
    private TextInputEditText tie_password_baru;
    private TextView tv_btn_batal_password;
    private TextView tv_btn_simpan_password;

    private static final String TAG = AkunDriverFragment.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    CircularImageView img_profile;
    CardView cvProgressBar;
    private SharedPreferences mPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_akun_driver, container, false);

        img_profile = v.findViewById(R.id.img_profile);
        ButterKnife.bind(getActivity());
        //sliding
        sliding_layout_akun = v.findViewById(R.id.sliding_layout_akun);
        continer_edit_profile = v.findViewById(R.id.continer_edit_profile);
        tie_nama_lengkap = v.findViewById(R.id.tie_nama_lengkap);
        tie_username = v.findViewById(R.id.tie_username);
        tie_telpon = v.findViewById(R.id.tie_telpon);
        tie_alamat = v.findViewById(R.id.tie_alamat);
        tv_btn_batal_profile = v.findViewById(R.id.tv_btn_batal_profile);
        tv_btn_simpan_profile = v.findViewById(R.id.tv_btn_simpan_profile);
        continer_edit_password = v.findViewById(R.id.continer_edit_password);
        tie_password_lama = v.findViewById(R.id.tie_password_lama);
        tie_password_baru = v.findViewById(R.id.tie_password_baru);
        tv_btn_batal_password = v.findViewById(R.id.tv_btn_batal_password);
        tv_btn_simpan_password = v.findViewById(R.id.tv_btn_simpan_password);

        hiddenContainerSliding();

        cvProgressBar = v.findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);

        tv_btn_password = v.findViewById(R.id.tv_btn_password);
        tv_btn_edit = v.findViewById(R.id.tv_btn_edit);
        tv_nama = v.findViewById(R.id.tv_nama);
        tv_username = v.findViewById(R.id.tv_username);
        tv_telpon = v.findViewById(R.id.tv_telpon);
        tv_alamat = v.findViewById(R.id.tv_alamat);

        mPreferences = getActivity().getSharedPreferences(my_shared_preferences, getActivity().MODE_PRIVATE);
        id = mPreferences.getString("ID", "");
        loadDataDriver(id);

        tv_btn_simpan_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = tie_password_lama.getText().toString();
                String new_password = tie_password_baru.getText().toString();

                if (old_password.isEmpty() || old_password.equals("")) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Password lama tidak boleh kosong!")
                            .show();
                } else if (new_password.isEmpty() || new_password.equals("")) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Password baru tidak boleh kosong!")
                            .show();
                } else if (new_password.length() < 6) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Password baru setidaknya minimal 6 karakter!")
                            .show();
                } else if (old_password.equals(new_password)) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Password baru dan lama tidak boleh sama!")
                            .show();
                } else {
                    startUpdatePassword(old_password, new_password);
                }
            }
        });

        tv_btn_batal_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusDataPasswordEdit();
                showPanel();
            }
        });

        tv_btn_simpan_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdateProfile();
            }
        });

        tv_btn_batal_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataProfileEdit();
                showPanel();
            }
        });

        tv_btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                continer_edit_password.setVisibility(View.VISIBLE);
            }
        });

        tv_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenContainerSliding();
                showPanel();
                continer_edit_profile.setVisibility(View.VISIBLE);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(getActivity())
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(getActivity());

        return v;
    }

    private void startUpdatePhoto(Uri uri) {

        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        File file = new File(uri.getPath());
        RequestBody foto = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part foto_send = MultipartBody.Part.createFormData("foto", file.getName(), foto);
        RequestBody nama_send = RequestBody.create(MediaType.parse("text/plain"), nama);
        RequestBody alamat_send = RequestBody.create(MediaType.parse("text/plain"), alamat);
        RequestBody telepon_send = RequestBody.create(MediaType.parse("text/plain"), telepon);
        RequestBody email_send = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody status_send = RequestBody.create(MediaType.parse("text/plain"), status);
        RequestBody username_send = RequestBody.create(MediaType.parse("text/plain"), username);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponDriver> driverCall = apiInterface.updateFotoDriver("Bearer " + BuildConfig.TOKEN,
                nama_send, alamat_send, telepon_send, email_send, status_send, username_send, foto_send, id);
        driverCall.enqueue(new Callback<ResponDriver>() {
            @Override
            public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        SweetAlertDialog success = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                        success.setTitleText("Success..");
                        success.setCancelable(false);
                        success.setContentText("Edit Foto Berhasil");
                        success.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.dismiss();
                                load_first = true;
                                cvProgressBar.setVisibility(View.VISIBLE);
                                loadDataDriver(id);
                                // loading profile image from local cache
//                                loadProfile(uri.toString());
                            }
                        });
                        success.show();
                    } else {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Mohon Maaf...")
                                .setContentText("Terjadi Kesalahan!")
                                .show();
                    }
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Permintaan Gagal, erjadi Kesalahan Sistem!")
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResponDriver> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Permintaan Gagal, Terjadi Kesalahan Sistem!")
                        .show();

            }
        });

    }

    private void startUpdatePassword(String old_password, String new_password) {
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponDriver> driverCallPasword = apiInterface.updatePasswordDriver("Bearer " + BuildConfig.TOKEN,
                old_password, new_password, id);
        driverCallPasword.enqueue(new Callback<ResponDriver>() {
            @Override
            public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                pDialog.dismiss();
                if (response.body().isSuccess()) {
                    SweetAlertDialog success = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    success.setTitleText("Success..");
                    success.setCancelable(false);
                    success.setContentText("Edit Password Berhasil");
                    success.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            pDialog.dismiss();
                            cvProgressBar.setVisibility(View.VISIBLE);
                            showPanel();
                            loadDataDriver(id);
                        }
                    });
                    success.show();
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText(response.body().getMessage())
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponDriver> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Permintaan Gagal, Terjadi Kesalahan Sistem!")
                        .show();
            }
        });


    }

    private void startUpdateProfile() {

        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        String nama_send = tie_nama_lengkap.getText().toString();
        String alamat_send = tie_alamat.getText().toString();
        String telpon_send = tie_telpon.getText().toString();
        String username_send = tie_username.getText().toString();
        String email_send = email;
        String status_send = status;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponDriver> driverCallUpdate = apiInterface.updateDataDriver("Bearer " + BuildConfig.TOKEN,
                nama_send, alamat_send, telpon_send, email_send, status_send, username_send, id);
        driverCallUpdate.enqueue(new Callback<ResponDriver>() {
            @Override
            public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                pDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        SweetAlertDialog success = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                        success.setTitleText("Success..");
                        success.setCancelable(false);
                        success.setContentText("Edit Profile Berhasil");
                        success.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.dismiss();
                                cvProgressBar.setVisibility(View.VISIBLE);
                                showPanel();
                                loadDataDriver(id);
                            }
                        });
                        success.show();
                    } else {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Mohon Maaf...")
                                .setContentText("Terjadi Kesalahan!")
                                .show();
                    }
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponDriver> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Permintaan Gagal, Terjadi Kesalahan Sistem!")
                        .show();

            }
        });
    }

    private void loadDataDriver(String id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponDriver> responDriverCall = apiInterface.getDriver("Bearer " + BuildConfig.TOKEN, id);
        responDriverCall.enqueue(new Callback<ResponDriver>() {
            @Override
            public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        nama = response.body().getDriver().getNama();
                        alamat = response.body().getDriver().getAlamat();
                        email = response.body().getDriver().getEmail();
                        telepon = response.body().getDriver().getTelepon();
                        foto = response.body().getDriver().getFoto();
                        status = response.body().getDriver().getStatus();
                        username = response.body().getDriver().getUsername();

                        if (load_first) {
                            load_first = false;
                            loadProfileDefault(foto);
                        }
                        tv_nama.setText(nama);
                        tv_username.setText(username);
                        tv_telpon.setText(telepon);
                        tv_alamat.setText(alamat);
                        setDataProfileEdit();
                    } else {
                        gagalLoadData();
                    }
                } else {
                    actionNotConnection();
                }
            }

            @Override
            public void onFailure(Call<ResponDriver> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);
                gagalLoadData();
            }
        });
    }

    private void gagalLoadData() {
        Snackbar.make(v.findViewById(android.R.id.content), "Gagal Proses Data!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    private void actionNotConnection() {
        Snackbar.make(v.findViewById(android.R.id.content), "Koneksi Tidak Ada!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    private void hapusDataPasswordEdit() {
        tie_password_baru.setText("");
        tie_password_lama.setText("");
    }

    private void setDataProfileEdit() {
        tie_nama_lengkap.setText(nama);
        tie_username.setText(username);
        tie_telpon.setText(telepon);
        tie_alamat.setText(alamat);
    }

    private void hiddenContainerSliding() {
        continer_edit_profile.setVisibility(View.GONE);
        continer_edit_password.setVisibility(View.GONE);
    }

    private void showPanel() {

        if (sliding_layout_akun != null &&
                (sliding_layout_akun.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout_akun.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout_akun.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            hiddenContainerSliding();
        } else {
            hapusDataPasswordEdit();
            setDataProfileEdit();
            sliding_layout_akun.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }

    }

    private void loadProfileDefault(String foto) {
        Glide.with(getActivity())
                .load(Constanta.url_foto_foto_driver + foto)
                .into(img_profile);
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        Glide.with(getActivity())
                .load(url)
                .into(img_profile);
        img_profile.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.transparent));
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }

            @Override
            public void onViewImage() {
                launchViewImage();
            }
        });
    }

    private void launchViewImage() {
//        Toast.makeText(getActivity(), "Lihat Gambar!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ViewImageActivity.class);
        intent.putExtra("foto", foto);
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    startUpdatePhoto(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }



}
