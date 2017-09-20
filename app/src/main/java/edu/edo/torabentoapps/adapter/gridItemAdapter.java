package edu.edo.torabentoapps.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.daftarreseller;
import edu.edo.torabentoapps.fragment.ItemFragment;
import edu.edo.torabentoapps.fragment.TransaksiFragment;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 16/08/2017.
 */

public class gridItemAdapter extends RecyclerView.Adapter<gridItemAdapter.ViewHolder>{
    private List<itemModel> mMakanan;

    public gridItemAdapter(List<itemModel> mMakanan){
        this.mMakanan = mMakanan;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewgroup, int i){
        View v = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.itemlist, viewgroup, false);
        ViewHolder viewholder = new ViewHolder(v);

        return viewholder;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i){
        //gambil data
        Locale local = new Locale("in","ID");
        final itemModel iModel = mMakanan.get(i);
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);
        viewHolder.nmMakanan.setText(iModel.getNmMakanan());
        viewHolder.gambar.setImageResource(iModel.getThumbnail());
        viewHolder.status.setText(iModel.getKetersediaan());
        viewHolder.harga.setText(nf.format(Double.valueOf(iModel.getHarga())));
        viewHolder.stok.setText("Stok : "+iModel.getStok());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(v.getContext(),iModel.getNmMakanan(), Toast.LENGTH_LONG).show();
                //masukkan dialog disini
                /*final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("Percobaan");
                TextView teks = (TextView) dialog.findViewById(R.id.judul);
                teks.setText(iModel.getNmMakanan());
                dialog.show();

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_layout,null);
                alertDialog.setView(view);
                alertDialog.setTitle("Title");
                TextView teks = (TextView) view.findViewById(R.id.judul);
                teks.setText(iModel.getNmMakanan());
                alertDialog.setNeutralButton("BELI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();*/
                boolean wrapScroll = true;
                MaterialDialog mDialog = new MaterialDialog.Builder(v.getContext())
                        .customView(R.layout.dialog_layout,wrapScroll)
                        .title("Title")
                        .neutralText("BELI")
                        .buttonsGravity(GravityEnum.CENTER)
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //masuk ke transaksi fragment dengan item yang dipilih
                            }
                        })
                        .build();
                TextView makanan = (TextView) mDialog.findViewById(R.id.makananBeli);
                TextView hargabeli = (TextView) mDialog.findViewById(R.id.hargabeli);
                TextView stoktersedia = (TextView) mDialog.findViewById(R.id.stokterserdia);
                stoktersedia.setText(iModel.getStok());
                hargabeli.setText(iModel.getHarga());
                makanan.setText(iModel.getNmMakanan());
                mDialog.show();

            }
        });
    }

    public int getItemCount(){
        return mMakanan.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //nampilin data
        public ImageView gambar;
        public TextView nmMakanan;
        public TextView status;
        public TextView harga;
        public TextView stok;

        public ViewHolder(View itemView){
            super(itemView);
            gambar =(ImageView)itemView.findViewById(R.id.thumbnails);
            nmMakanan =(TextView)itemView.findViewById(R.id.nmMakanan);
            status =(TextView)itemView.findViewById(R.id.status);
            harga =(TextView)itemView.findViewById(R.id.harga);
            stok = (TextView)itemView.findViewById(R.id.stokmakantv);

        }
    }
}

