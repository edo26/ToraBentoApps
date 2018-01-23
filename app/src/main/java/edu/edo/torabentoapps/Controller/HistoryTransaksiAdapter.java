package edu.edo.torabentoapps.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.List;

import edu.edo.torabentoapps.Model.Transaksi.DataArray;
import edu.edo.torabentoapps.Model.Transaksi.DataArray1;
import edu.edo.torabentoapps.Model.Transaksi.ModelDetailPesanan;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anggy on 20/08/2017.
 */

public class HistoryTransaksiAdapter extends RecyclerView.Adapter<HistoryTransaksiAdapter.holderTransaksi> {

    List<DataArray> data;
    Context konteks;

    public HistoryTransaksiAdapter(List<DataArray> data, Context konteks) {
        this.data = data;
        this.konteks = konteks;
    }

    @Override
    public holderTransaksi onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historitransaksi, parent, false);

        holderTransaksi yeah = new holderTransaksi(v);

        return yeah;
    }

    @Override
    public void onBindViewHolder(holderTransaksi holder, int position) {

        final DataArray item = data.get(position);
        holder.transaksiID.setText(item.getIdTransaksi());
        //holder.namamakanan.setText(item.);
        holder.namapembeli.setText(item.getNamaPembeli());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleAPI.Factory.getIstance((FragmentActivity) konteks).submitSQLTr("SELECT t_pembeli.nama_pembeli,t_makanan.nama_makanan,t_keranjang.qty FROM t_keranjang JOIN t_makanan ON t_keranjang.id_makanan = t_makanan.id_makanan JOIN t_pembeli ON t_keranjang.id_pembeli = t_pembeli.id_pembeli WHERE t_pembeli.nama_pembeli = '"+item.getNamaPembeli()+"'")
                        .enqueue(new Callback<ModelDetailPesanan>() {
                            @Override
                            public void onResponse(@NonNull Call<ModelDetailPesanan> call, @NonNull Response<ModelDetailPesanan> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getNilai().equals(1)){
                                        Log.d("tes","berhasillewat");
                                        //list = new ArrayList<DataArray1>();
                                        MaterialDialog mDOG = new MaterialDialog.Builder(konteks)
                                                .customView(R.layout.dialog_detailpesan,true)
                                                .title("DETAIL PESANAN")
                                                .positiveText("OK")
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        dialog.dismiss();
                                                    }
                                                }).build();
                                        List<DataArray1> list;
                                        ListView inidetail;
                                        list = response.body().getDataArray1();
                                        String[] values = new String[list.size()];
                                        for (int i = 0; i < list.size(); i++) {
                                            if(list.get(i).getNamaMakanan() != null && list.get(i).getQty() != null){
                                                values[i] = list.get(i).getNamaMakanan() + " x " + list.get(i).getQty();
                                            }
                                        }
                                        inidetail = (ListView) mDOG.findViewById(R.id.dialogpesananlist);
                                        ArrayAdapter adapter = new ArrayAdapter<String>(konteks,
                                                R.layout.support_simple_spinner_dropdown_item, values);
                                        inidetail.setAdapter(adapter);
                                        mDOG.show();
                                    }else{
                                        Toast.makeText(konteks, "Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(konteks, "fak euy unsuccesfully", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelDetailPesanan> call, Throwable t) {
                                Toast.makeText(konteks, "Yeaey failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.totalharga.setText("Rp. "+item.getTotalharga());
        holder.alamatkirim.setText(item.getAlamat());
        //holder.hapus.setFontAwesomeIcon("fa_trash");
        holder.status.setText(item.getStatusTransaksi());
        holder.timestamp.setText(item.getTanggalTransaksi());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class holderTransaksi extends RecyclerView.ViewHolder{
        public TextView transaksiID,namamakanan,quantityItem,timestamp,status,totalharga,alamatkirim,namapembeli;
        public ImageView thumbnailsItem;
        public CardView cardViewTransaksi;
        public BootstrapButton detail;

        public holderTransaksi(View itemView) {
            super(itemView);
            transaksiID = (TextView)itemView.findViewById(R.id.idtransaksi);
            //namamakanan = (TextView)itemView.findViewById(R.id.namamakanan);
            //quantityItem = (TextView)itemView.findViewById(R.id.quantityitem);
            namapembeli = (TextView)itemView.findViewById(R.id.namapembeliht);
            timestamp = (TextView)itemView.findViewById(R.id.tanggalpesan);
            status = (TextView)itemView.findViewById(R.id.statuspengiriman);
            detail = (BootstrapButton)itemView.findViewById(R.id.tomboldetailpesananht);
            totalharga = (TextView)itemView.findViewById(R.id.totalharga);
            alamatkirim = (TextView)itemView.findViewById(R.id.alamatpengiriman);
            //thumbnailsItem = (ImageView)itemView.findViewById(R.id.thumbnailsitem);
            cardViewTransaksi = (CardView)itemView.findViewById(R.id.cardViewitemtransaksi);
        }
    }

}
