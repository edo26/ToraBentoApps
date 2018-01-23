package edu.edo.torabentoapps.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.edo.torabentoapps.Model.Transaksi.DataArray;
import edu.edo.torabentoapps.Model.Transaksi.DataArray1;
import edu.edo.torabentoapps.Model.Transaksi.ModelDetailPesanan;
import edu.edo.torabentoapps.Model.Transaksi.ModelTransaksi;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Edo on 1/10/2018.
 */

public class KonfirmasiPesananAdapter extends RecyclerView.Adapter<KonfirmasiPesananAdapter.Holderku> {

    List<DataArray> inidatanya;
    Context konteks;

    public KonfirmasiPesananAdapter(List<DataArray> inidatanya, Context konteks) {
        this.inidatanya = inidatanya;
        this.konteks = konteks;
    }

    @Override
    public Holderku onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanankonfirmasi,parent,false);
        Holderku h = new Holderku(v);
        return h;
    }

    @Override
    public void onBindViewHolder(Holderku holder, int position) {

        final DataArray item = inidatanya.get(position);
        holder.transaksiID.setText(item.getIdTransaksi());
        //holder.namamakanan.setText(item.getNamamakanan());
        //holder.quantityItem.setText(""+item.getQuantity());

        Picasso.with(konteks).load(item.getResipembayaran()).error(R.drawable.noimage).fit().into(holder.gambarresi);
                //holder.gambarresi.setImageResource(item.getResipembayaran());

        holder.totalharga.setText("Rp. "+item.getTotalharga());
        holder.namapembeli.setText(item.getNamaPembeli());
        holder.alamatkirim.setText(item.getAlamat());
        //holder.hapus.setFontAwesomeIcon("fa_trash");
        holder.status.setText(item.getStatusTransaksi());
        holder.tanggal.setText(item.getTanggalTransaksi());
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

        holder.konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleAPI.Factory.getIstance((FragmentActivity) konteks).updateTransaksi(item.getIdTransaksi(),"Diterima").enqueue(new Callback<ModelTransaksi>() {
                    @Override
                    public void onResponse(Call<ModelTransaksi> call, Response<ModelTransaksi> response) {
                        if(response.isSuccessful()){
                            if (response.body().getNilai().equals(1)){
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(konteks);
                                alertDialog.setTitle("PESAN")
                                        .setIcon(R.drawable.sukses)
                                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                notifyDataSetChanged();
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .setMessage("Konfirmasi Berhasil !")
                                        .create()
                                        .show();
                            }else{
                                Toast.makeText(konteks, "Nilao 0", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(konteks, "Unsuccesfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelTransaksi> call, Throwable t) {
                        Toast.makeText(konteks, "onfailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return inidatanya.size();
    }

    class Holderku extends RecyclerView.ViewHolder{
        TextView transaksiID,namamakanan,quantityItem,tanggal,status,totalharga,alamatkirim,namapembeli;
        ImageView gambarresi;
        CardView cardViewTransaksi;
        BootstrapButton konfirmasi,detail;

        Holderku(View itemView) {
            super(itemView);
            transaksiID = (TextView)itemView.findViewById(R.id.idtransaksikf);
            //namamakanan = (TextView)itemView.findViewById(R.id.namamakanankf);
            //quantityItem = (TextView)itemView.findViewById(R.id.quantityitemkf);
            namapembeli = (TextView)itemView.findViewById(R.id.namapembelikf);
            tanggal = (TextView)itemView.findViewById(R.id.tanggalpesankf);
            status = (TextView)itemView.findViewById(R.id.statuspengirimankf);
            konfirmasi = (BootstrapButton)itemView.findViewById(R.id.konfirmasi);
            detail = (BootstrapButton)itemView.findViewById(R.id.tomboldetailpesanan);
            totalharga = (TextView)itemView.findViewById(R.id.totalhargakf);
            alamatkirim = (TextView)itemView.findViewById(R.id.alamatpengirimankf);
            gambarresi = (ImageView)itemView.findViewById(R.id.resipembayaran);
            cardViewTransaksi = (CardView)itemView.findViewById(R.id.cardViewitemkonfirmasi);
        }
    }
}
