package edu.edo.torabentoapps.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.edo.torabentoapps.Model.Keranjang.DataArray1;
import edu.edo.torabentoapps.R;

/**
 * Created by anggy on 20/08/2017.
 */

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.holderTransaksi> {

    List<DataArray1> data;
    Context konteks;


    public KeranjangAdapter(List<DataArray1> data, Context konteks) {
        this.data = data;
        this.konteks = konteks;
    }

    @Override
    public holderTransaksi onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.keranjang, parent, false);

        holderTransaksi yeah = new holderTransaksi(v);

        return yeah;
    }

    @Override
    public void onBindViewHolder(holderTransaksi holder, final int position) {
        final ProgressDialog pd = new ProgressDialog(konteks);
        pd.setTitle("Pesan");
        pd.setMessage("Harap tunggu...");
        DataArray1 item = data.get(position);
        //holder.transaksiID.setText(item.getTransaksiID());
        holder.namamakanan.setText(item.getNamaMakanan());
        holder.quantityItem.setText(""+item.getQty());

        Picasso.with(konteks).load(item.getLinkgambar()).placeholder(R.drawable.noimage).fit().into(holder.thumbnailsItem);
        //holder.thumbnailsItem.setImageBitmap(item.getLinkgambar());

        holder.totalharga.setText("Rp. "+item.getHarga());
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(konteks)
                        .title("Konfirmasi Hapus")
                        .content("Apakah anda ingin menghapus makanan ini ?")
                        .positiveText("Ya")
                        .negativeText("Tidak")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                pd.show();
                                new KeranjangActivity().hapusData(konteks,data,position);
                                Toast.makeText(konteks, "Makanan Telah Terhapus!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).build().show();
            }
        });
        //holder.alamatkirim.setText(item.getAlamat());
        //holder.hapus.setFontAwesomeIcon("fa_trash");
        //holder.status.setText(item.getStatuspemesanan());
        //holder.timestamp.setText(item.getTanggal_transaksi());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class holderTransaksi extends RecyclerView.ViewHolder{
        public TextView namamakanan,quantityItem,totalharga;
        public ImageView thumbnailsItem;
        public CardView cardViewTransaksi;
        public BootstrapButton hapus;

        public holderTransaksi(View itemView) {
            super(itemView);
            //transaksiID = (TextView)itemView.findViewById(R.id.transaksiID);
            namamakanan = (TextView)itemView.findViewById(R.id.namamakanan);
            quantityItem = (TextView)itemView.findViewById(R.id.quantityitem);
            //timestamp = (TextView)itemView.findViewById(R.id.timestamp);
            //status = (TextView)itemView.findViewById(R.id.statuspemesanan);
            hapus = (BootstrapButton)itemView.findViewById(R.id.hapustransaksi);
            totalharga = (TextView)itemView.findViewById(R.id.totalharga);
            //alamatkirim = (TextView)itemView.findViewById(R.id.alamatkirim);
            thumbnailsItem = (ImageView)itemView.findViewById(R.id.thumbnailsitem);
            cardViewTransaksi = (CardView)itemView.findViewById(R.id.cardViewitemtransaksi);
        }
    }

}
