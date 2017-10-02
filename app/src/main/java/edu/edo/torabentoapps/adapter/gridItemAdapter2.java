package edu.edo.torabentoapps.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.edo.torabentoapps.Model.DataArray;
import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.dashboardreseller;
import edu.edo.torabentoapps.loginreseller;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 16/08/2017.
 */

public class gridItemAdapter2 extends RecyclerView.Adapter<gridItemAdapter2.ViewHolder>{
    private List<itemModel> mMakanan;
    private Context konteks;
    private FragmentActivity fa;
    public BottomBar bottomBar;

    public gridItemAdapter2(List<itemModel> mMakanan, Context konteks){
        this.mMakanan = mMakanan;
        this.konteks = konteks;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewgroup, int i){
        View v = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.itemlist, viewgroup, false);
        ViewHolder viewholder = new ViewHolder(v);

        return viewholder;
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i){
        //gambil data
        final Locale local = new Locale("in","ID");
        final itemModel iModel = mMakanan.get(i);
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);
        viewHolder.nmMakanan.setText(iModel.getNmMakanan());
        Picasso.with(konteks).load(iModel.getGambar()).placeholder(R.drawable.noimage).fit().into(viewHolder.gambar);
        //viewHolder.gambar.setImageResource(iModel.getThumbnail());
        if (iModel.getKetersediaan().equals("Tidak Tersedia")){
            viewHolder.status.setTextColor(Color.RED);
        }
        viewHolder.status.setText(iModel.getKetersediaan());
        viewHolder.harga.setText(nf.format(Double.valueOf(iModel.getHarga())));
        viewHolder.stok.setText("Stok : "+iModel.getStok());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SharedPreferences.Editor se = v.getContext().getSharedPreferences("Nama Reseller",Context.MODE_PRIVATE).edit();
                se.putString("idmakanan",iModel.getIdMakanan());
                se.apply();
                Intent i = new Intent(konteks, dashboardreseller.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("namamakanan",viewHolder.nmMakanan.getText().toString());
                i.putExtra("gambar",iModel.getGambar());
                i.putExtra("stok",viewHolder.stok.getText().toString());
                i.putExtra("status",viewHolder.status.getText().toString());
                i.putExtra("harga",viewHolder.harga.getText().toString());
                konteks.startActivity(i);
                //Toast.makeText(konteks, "You was click "+viewHolder.nmMakanan.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                konteks = fa;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("KONFIRMASI")
                        .setMessage("ANDA YAKIN INGIN MENGHAPUS MAKANAN "+iModel.getNmMakanan()+" ?")
                        .setPositiveButton("IYA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hapusData(viewHolder.getAdapterPosition(),view.getContext(),fa);
                            }
                        })
                        .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

                return false;
            }
        });
    }

    private void hapusData(int position, final Context ini, FragmentActivity itu){
        SampleAPI hapus = SampleAPI.Factory.getIstance(itu);
        final itemModel iModel = mMakanan.get(position);
        hapus.deleteMakanan(iModel.getIdMakanan()).enqueue(new Callback<itemRespon>() {
            @Override
            public void onResponse(Call<itemRespon> call, Response<itemRespon> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ini.getApplicationContext(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ini.getApplicationContext(), "Unsuccessfully : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<itemRespon> call, Throwable t) {
                Toast.makeText(konteks, "OnFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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

