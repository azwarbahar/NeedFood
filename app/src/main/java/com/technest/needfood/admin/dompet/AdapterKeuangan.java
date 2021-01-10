package com.technest.needfood.admin.dompet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.admin.home.adapter.PesananTerbaruAdapter;
import com.technest.needfood.admin.pesanan.detail.DetailPesananActivity;
import com.technest.needfood.admin.pesanan.detail.DetailPesananBaruActivity;
import com.technest.needfood.models.keuangan.ReslutSingle;
import com.technest.needfood.models.keuangan.ResponseKeuanganSingle;
import com.technest.needfood.models.keuangan.ResultItem;
import com.technest.needfood.models.pesanan.Pesanan;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKeuangan extends RecyclerView.Adapter<AdapterKeuangan.MyHolderView> {

    private Context context;
    private ArrayList<ResultItem> resultItems;

    private ReslutSingle reslutSingle;

    public AdapterKeuangan(Context context, ArrayList<ResultItem> resultItems) {
        this.context = context;
        this.resultItems = resultItems;
    }

    @NonNull
    @Override
    public AdapterKeuangan.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_keuangan, parent, false);
        AdapterKeuangan.MyHolderView myHolderView = new AdapterKeuangan.MyHolderView(view);

        return myHolderView;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKeuangan.MyHolderView holder, int position) {

        String uraian = resultItems.get(position).getUraian();
        String jenis = resultItems.get(position).getJenis();
        long nominal = resultItems.get(position).getNominal();
        String tanggal = getDate(resultItems.get(position).getTanggal());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        if (jenis.equals("Debit")){
            holder.img_1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.arrow_up));
            holder.tv_nominal.setTextColor(Color.parseColor("#077307"));
            holder.tv_nominal.setText(kursIndonesia.format(nominal));
        } else {
            holder.img_1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.arrow_down));
            holder.tv_nominal.setTextColor(Color.parseColor("#F10808"));
            holder.tv_nominal.setText("-"+kursIndonesia.format(nominal));
        }
        holder.tv_uraian.setText(uraian);
        holder.tv_tangal.setText(tanggal);

        String id = String.valueOf(resultItems.get(position).getId());
        final String[] pemesanan_id = new String[1];
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseKeuanganSingle> singleCall = apiInterface.getKeuanganSingle("Bearer " + BuildConfig.TOKEN, id);
        singleCall.enqueue(new Callback<ResponseKeuanganSingle>() {
            @Override
            public void onResponse(Call<ResponseKeuanganSingle> call, Response<ResponseKeuanganSingle> response) {
                reslutSingle = response.body().getResult();
                pemesanan_id[0] = reslutSingle.getPemesanan_id();
            }

            @Override
            public void onFailure(Call<ResponseKeuanganSingle> call, Throwable t) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pemesanan_id[0]!=null){

                    Intent intent = new Intent(context, DetailPesananActivity.class);
//                    intent.putExtra(DetailPesananBaruActivity.EXTRA_DATA, pesanans.get(position));
                    intent.putExtra("ID_PESANAN", pemesanan_id[0]);
                    context.startActivity(intent);
                }
            }
        });


    }

    private String getDate(String time) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = dateParser.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        assert date != null;
        return dateFormatter.format(date);
    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }


    public void setFilter(ArrayList<ResultItem> filterList) {
        resultItems = new ArrayList<>();
        resultItems.addAll(filterList);
        notifyDataSetChanged();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {

        private ImageView img_1;
        private TextView tv_uraian;
        private TextView tv_tangal;
        private TextView tv_nominal;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            img_1 = itemView.findViewById(R.id.img_1);
            tv_uraian = itemView.findViewById(R.id.tv_uraian);
            tv_tangal = itemView.findViewById(R.id.tv_tangal);
            tv_nominal = itemView.findViewById(R.id.tv_nominal);

        }
    }
}
