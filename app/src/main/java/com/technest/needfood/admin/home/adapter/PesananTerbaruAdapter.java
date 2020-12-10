package com.technest.needfood.admin.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.home.PesananTerbaruActivity;
import com.technest.needfood.admin.pesanan.detail.DetailPesananBaruActivity;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.models.pesanan.ResponsePesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananTerbaruAdapter extends RecyclerView.Adapter<PesananTerbaruAdapter.MyHolderView> {

    private Context context;
    private ArrayList<Pesanan> pesananArrayList;

    public PesananTerbaruAdapter(Context context, ArrayList<Pesanan> pesananArrayList) {
        this.context = context;
        this.pesananArrayList = pesananArrayList;
    }

    @NonNull
    @Override
    public PesananTerbaruAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_pesanan_baru, parent, false);
        PesananTerbaruAdapter.MyHolderView myHolderView = new PesananTerbaruAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull PesananTerbaruAdapter.MyHolderView holder, int position) {

        holder.tv_alamat.setText(pesananArrayList.get(position).getDeskripsi_lokasi());
        holder.tv_kode_pesanan.setText(pesananArrayList.get(position).getKd_pemesanan());
        holder.tv_nama_pelanggan.setText(pesananArrayList.get(position).getNama());

        holder.img_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(true);
                pDialog.show();

                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Menyetujui ?")
                        .setContentText("Ingin Menyetujui Pesanan")
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.dismiss();
                            }
                        })
                        .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.show();
                                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                Call<ResponsePesanan> responsePesananCall = apiInterface.updateSatusPesanan(
                                        "Bearer " + BuildConfig.TOKEN,
                                        String.valueOf(pesananArrayList.get(position).getId()), "Acc");
                                responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
                                    @Override
                                    public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                                        pDialog.dismiss();
                                        if (response.isSuccessful()) {
                                            if (response.body().getmSuccess()) {
                                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Success..")
                                                        .setContentText("Penyetujuan Berhasil")
                                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                sweetAlertDialog.dismiss();
                                                                if (context instanceof PesananTerbaruActivity) {
                                                                    ((PesananTerbaruActivity) context).onRefresh();
                                                                }
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Mohon Maaf...")
                                                        .setContentText("Terjadi Kesalahan!")
                                                        .show();
                                            }
                                        } else {
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Oops...")
                                                    .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                                        pDialog.dismiss();
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                .show();
                                    }
                                });
                            }
                        })
                        .show();

            }
        });

        holder.img_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(true);
                pDialog.show();


                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Menolak ?")
                        .setContentText("Ingin Menolak Pesanan")
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.dismiss();
                            }
                        })
                        .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                pDialog.show();
                                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                                Call<ResponsePesanan> responsePesananCall = apiInterface.updateSatusPesanan(
                                        "Bearer " + BuildConfig.TOKEN,
                                        String.valueOf(pesananArrayList.get(position).getId()), "Refuse");
                                responsePesananCall.enqueue(new Callback<ResponsePesanan>() {
                                    @Override
                                    public void onResponse(Call<ResponsePesanan> call, Response<ResponsePesanan> response) {
                                        pDialog.dismiss();
                                        if (response.isSuccessful()) {
                                            if (response.body().getmSuccess()) {
                                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Success..")
                                                        .setContentText("Penolakan Berhasil")
                                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                sweetAlertDialog.dismiss();
                                                                if (context instanceof PesananTerbaruActivity) {
                                                                    ((PesananTerbaruActivity) context).onRefresh();
                                                                }
                                                            }
                                                        })
                                                        .show();
                                            } else {
                                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Mohon Maaf...")
                                                        .setContentText("Terjadi Kesalahan!")
                                                        .show();
                                            }
                                        } else {
                                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Oops...")
                                                    .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsePesanan> call, Throwable t) {
                                        pDialog.dismiss();
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Permintaan Gagal, Periksa Koneksi Internet!")
                                                .show();
                                    }
                                });
                            }
                        })
                        .show();


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPesananBaruActivity.class);
                intent.putExtra(DetailPesananBaruActivity.EXTRA_DATA, pesananArrayList.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return pesananArrayList.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private TextView tv_kode_pesanan;
        private TextView tv_nama_pelanggan;
        private TextView tv_alamat;
        private ImageView img_accept;
        private ImageView img_refuse;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            tv_kode_pesanan = itemView.findViewById(R.id.tv_kode_pesanan);
            tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            img_accept = itemView.findViewById(R.id.img_accept);
            img_refuse = itemView.findViewById(R.id.img_refuse);

        }
    }
}
