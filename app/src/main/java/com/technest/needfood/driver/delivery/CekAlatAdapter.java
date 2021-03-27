package com.technest.needfood.driver.delivery;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.dompet.AdapterKeuangan;
import com.technest.needfood.models.alat.Alat;
import com.technest.needfood.models.alat.CekAlatPesanan;
import com.technest.needfood.models.alat.ResponseAlat;
import com.technest.needfood.models.user.ResponDriver;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;
import com.technest.needfood.utils.Constanta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekAlatAdapter extends RecyclerView.Adapter<CekAlatAdapter.MyHolderView> {

    private Context context;
    private String id_pesanan;
    private ArrayList<CekAlatPesanan> cekAlatPesanans;
    private Alat alat;

    public CekAlatAdapter(Context context, String id_pesanan, ArrayList<CekAlatPesanan> cekAlatPesanans) {
        this.context = context;
        this.id_pesanan = id_pesanan;
        this.cekAlatPesanans = cekAlatPesanans;
    }

    @NonNull
    @Override
    public CekAlatAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_alat_kembali, parent, false);
        CekAlatAdapter.MyHolderView myHolderView = new CekAlatAdapter.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull CekAlatAdapter.MyHolderView holder, int position) {

        String alat_id = String.valueOf(cekAlatPesanans.get(position).getAlatId());
        String status = String.valueOf(cekAlatPesanans.get(position).getStatus());
        String nama_alat = String.valueOf(cekAlatPesanans.get(position).getNamaAlat());
        String jumlah_alat = String.valueOf(cekAlatPesanans.get(position).getJumlah());

        holder.tv_nama_alat.setText(nama_alat);
        holder.tv_jumlah.setText(jumlah_alat);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseAlat> responseAlatCall = apiInterface.getAlat("Bearer " + BuildConfig.TOKEN, alat_id);
        responseAlatCall.enqueue(new Callback<ResponseAlat>() {
            @Override
            public void onResponse(Call<ResponseAlat> call, Response<ResponseAlat> response) {
                alat = response.body().getResult();
                Glide.with(context)
                        .load(Constanta.url_foto_alat + alat.getFoto())
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                        .into(holder.img_alat);
            }

            @Override
            public void onFailure(Call<ResponseAlat> call, Throwable t) {
                Glide.with(context)
                        .load(Constanta.url_foto_alat + alat.getFoto())
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                        .into(holder.img_alat);
            }
        });

        holder.btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nilai_awal = holder.tv_jumlah.getText().toString();
                int hasil = Integer.parseInt(nilai_awal) + 1;
                holder.tv_jumlah.setText(String.valueOf(hasil));
            }
        });

        holder.btn_kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nilai_awal = holder.tv_jumlah.getText().toString();
                int hasil = Integer.parseInt(nilai_awal) - 1;
                holder.tv_jumlah.setText(String.valueOf(hasil));
            }
        });

        if (status.equals("used")){
            holder.rl_check_alat.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_success_alat));
        } else {
            holder.rl_check_alat.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_success_alat_grey));
            holder.rl_check_alat.setEnabled(false);
        }

        holder.rl_check_alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set view progres
                holder.img_check.setVisibility(View.GONE);
                holder.progress_check.setVisibility(View.VISIBLE);

                // call api
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponDriver> driverCall = apiInterface.cekAlatDriver("Bearer " + BuildConfig.TOKEN,
                        alat_id, holder.tv_jumlah.getText().toString(), id_pesanan);
                driverCall.enqueue(new Callback<ResponDriver>() {
                    @Override
                    public void onResponse(Call<ResponDriver> call, Response<ResponDriver> response) {
                        if (response.isSuccessful()){
                            Log.e("SEND DATA KEMBALI", "SUKSES");
                            holder.img_check.setVisibility(View.VISIBLE);
                            holder.progress_check.setVisibility(View.GONE);
                            holder.rl_check_alat.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_success_alat_grey));
                            holder.rl_check_alat.setEnabled(false);
                        } else {
                            Toast.makeText(context, "Jumlah "+nama_alat+" melebihi ketentuan!", Toast.LENGTH_SHORT).show();
                            holder.img_check.setVisibility(View.VISIBLE);
                            holder.progress_check.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponDriver> call, Throwable t) {
                        Log.e("SEND DATA KEMBALI", t.getLocalizedMessage());
                        holder.img_check.setVisibility(View.VISIBLE);
                        holder.progress_check.setVisibility(View.GONE);
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return cekAlatPesanans.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private ImageButton img_alat;
        private TextView tv_nama_alat;
        private TextView tv_jumlah;
        private CardView btn_tambah;
        private CardView btn_kurang;
        private RelativeLayout rl_check_alat;
        private ImageView img_check;
        private ProgressBar progress_check;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            img_alat = itemView.findViewById(R.id.img_alat);
            tv_nama_alat = itemView.findViewById(R.id.tv_nama_alat);
            tv_jumlah = itemView.findViewById(R.id.tv_jumlah);
            btn_tambah = itemView.findViewById(R.id.btn_tambah);
            btn_kurang = itemView.findViewById(R.id.btn_kurang);
            rl_check_alat = itemView.findViewById(R.id.rl_check_alat);
            img_check = itemView.findViewById(R.id.img_check);
            progress_check = itemView.findViewById(R.id.progress_check);

        }
    }
}
