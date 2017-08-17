package edu.edo.torabentoapps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.R;

/**
 * Created by ASUS on 16/08/2017.
 */

public class gridItemAdapter extends RecyclerView.Adapter<gridItemAdapter.ViewHolder>{
    List<itemModel> itemMakanan;

    public gridItemAdapter(){
        super();
        //nanti ngambil di database kecuali gambar
        itemMakanan = new ArrayList<itemModel>();
        itemModel im = new itemModel();
        im.setNmMakanan("Shrimp Roll");
        im.setHarga("Rp. 20.000");
        im.setThumbnail(R.drawable.shrimproll);
        im.setKetersediaan("Tersedia");
        itemMakanan.add(im);

        im = new itemModel();
        im.setNmMakanan("Shrimp Roll");
        im.setHarga("Rp. 20.000");
        im.setThumbnail(R.drawable.shrimproll);
        im.setKetersediaan("Tersedia");
        itemMakanan.add(im);

        im = new itemModel();
        im.setNmMakanan("Shrimp Roll");
        im.setHarga("Rp. 20.000");
        im.setThumbnail(R.drawable.shrimproll);
        im.setKetersediaan("Tersedia");
        itemMakanan.add(im);

        im = new itemModel();
        im.setNmMakanan("Shrimp Roll");
        im.setHarga("Rp. 20.000");
        im.setThumbnail(R.drawable.shrimproll);
        im.setKetersediaan("Tersedia");
        itemMakanan.add(im);

        im = new itemModel();
        im.setNmMakanan("Shrimp Roll");
        im.setHarga("Rp. 20.000");
        im.setThumbnail(R.drawable.shrimproll);
        im.setKetersediaan("Tersedia");
        itemMakanan.add(im);

    }

    public ViewHolder onCreateViewHolder(ViewGroup viewgroup, int i){
        View v = LayoutInflater.from(viewgroup.getContext())
                .inflate(R.layout.itemlist, viewgroup, false);
        ViewHolder viewholder = new ViewHolder(v);

        return viewholder;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i){
        //gambil data
        itemModel im = itemMakanan.get(i);
        viewHolder.nmMakanan.setText(im.getNmMakanan());
        viewHolder.gambar.setImageResource(im.getThumbnail());
        viewHolder.status.setText(im.getKetersediaan());
        viewHolder.harga.setText(im.getHarga());
    }

    public int getItemCount(){
        return itemMakanan.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //nampilin data
        public ImageView gambar;
        public TextView nmMakanan;
        public TextView status;
        public TextView harga;

        public ViewHolder(View itemView){
            super(itemView);
            gambar =(ImageView)itemView.findViewById(R.id.thumbnails);
            nmMakanan =(TextView)itemView.findViewById(R.id.nmMakanan);
            status =(TextView)itemView.findViewById(R.id.status);
            harga =(TextView)itemView.findViewById(R.id.harga);

        }
    }
}
