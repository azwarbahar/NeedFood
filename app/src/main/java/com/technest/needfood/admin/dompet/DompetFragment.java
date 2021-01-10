package com.technest.needfood.admin.dompet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hadiidbouk.charts.OnBarClickedListener;
import com.technest.needfood.BuildConfig;
import com.technest.needfood.R;
import com.technest.needfood.models.keuangan.DataKas;
import com.technest.needfood.models.keuangan.ResponseKeuangan;
import com.technest.needfood.models.keuangan.ResultItem;
import com.technest.needfood.network.ApiClient;
import com.technest.needfood.network.ApiInterface;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DompetFragment extends Fragment {

    View view;

    //    private BarChart barChart;
    private ChartProgressBar mChart;
    private String tahun_now;

    private TextView tv_kas;
    private TextView tv_debit;
    private TextView tv_kredit;

    private CardView cvProgressBar;

    private ArrayList<ResultItem> resultItems;
    private RecyclerView rv_keuangan;
    private ImageView img_search;

    private ArrayList<String> Hari1;
    private ArrayList<String> Hari2;
    private ArrayList<String> Hari3;
    private ArrayList<String> Hari4;
    private ArrayList<String> Hari5;
    private ArrayList<String> Hari6;
    private ArrayList<String> Hari7;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dompet, container, false);

        img_search = view.findViewById(R.id.img_search);
        rv_keuangan = view.findViewById(R.id.rv_keuangan);
        tv_debit = view.findViewById(R.id.tv_debit);
        tv_kas = view.findViewById(R.id.tv_kas);
        tv_kredit = view.findViewById(R.id.tv_kredit);
        cvProgressBar = view.findViewById(R.id.cvProgressBar);
        cvProgressBar.setVisibility(View.VISIBLE);

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        tahun_now = dateFormat.format(date);
        Log.e("GET TAHUN", "RESULT : " + tahun_now);
        gerDataKeuanganAll(tahun_now);

        ArrayList<BarData> dataList = new ArrayList<>();

        BarData data = new BarData("Sen", 3.0f, "30.000");
        dataList.add(data);

        data = new BarData("Sel", 2.0f, "20.000");
        dataList.add(data);

        data = new BarData("Rab", 1.0f, "1.000");
        dataList.add(data);

        data = new BarData("Kam", 7.3f, "4.000");
        dataList.add(data);

        data = new BarData("Jum", 0.2f, "200");
        dataList.add(data);

        data = new BarData("Sab", 8.0f, "8.000");
        dataList.add(data);

        data = new BarData("Min", 3.3f, "3.300");
        dataList.add(data);

        mChart = (ChartProgressBar) view.findViewById(R.id.ChartProgressBar);
        mChart.setDataList(dataList);
        mChart.build();
        mChart.setOnBarClickedListener(new OnBarClickedListener() {
            @Override
            public void onBarClicked(int i) {

                Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), "Posisi : " + i, Snackbar.LENGTH_LONG);
                View view = snack.getView();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.BOTTOM;
                view.setLayoutParams(params);
                snack.show();
            }
        });

        img_search = view.findViewById(R.id.img_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchKeuanganActivity.class));
            }
        });

        return view;
    }

    private void gerDataKeuanganAll(String tahun_now) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseKeuangan> keuanganCall = apiInterface.getKeuangan("Bearer " + BuildConfig.TOKEN,
                "All", tahun_now);
        keuanganCall.enqueue(new Callback<ResponseKeuangan>() {
            @Override
            public void onResponse(Call<ResponseKeuangan> call, Response<ResponseKeuangan> response) {
                cvProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    setDataKas(response.body().getDataKas());
                    resultItems = (ArrayList<ResultItem>) response.body().getResult();
                    setDataResult(resultItems);

                    AdapterKeuangan adapterKeuangan = new AdapterKeuangan(getActivity(), resultItems);
                    rv_keuangan.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_keuangan.setAdapter(adapterKeuangan);

                }
            }

            @Override
            public void onFailure(Call<ResponseKeuangan> call, Throwable t) {
                cvProgressBar.setVisibility(View.GONE);

            }
        });

    }

    private void setDataResult(ArrayList<ResultItem> resultItems) {

        for (int a = 0; a < resultItems.size(); a++) {
            String tgl = resultItems.get(a).getTanggal();


        }

    }

    private void setDataKas(DataKas dataKas) {

        long kas = dataKas.getUang_kas();
        long debit = dataKas.getTotal_debit();
        long kredit = dataKas.getTotal_kredit();

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        tv_kas.setText(kursIndonesia.format(kas));
        tv_debit.setText(kursIndonesia.format(debit));
        tv_kredit.setText(kursIndonesia.format(kredit));

    }
}
