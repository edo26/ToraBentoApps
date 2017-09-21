package edu.edo.torabentoapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.daftarreseller;
import edu.edo.torabentoapps.fragment.ItemFragment;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 16/08/2017.
 */

public class gridItemAdapter extends RecyclerView.Adapter<gridItemAdapter.ViewHolder>{
    private List<itemModel> mMakanan;
    private Context konteks;

    public gridItemAdapter(List<itemModel> mMakanan, Context konteks) {
        this.mMakanan = mMakanan;
        this.konteks = konteks;
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
        itemModel iModel = mMakanan.get(i);
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);
        viewHolder.nmMakanan.setText(iModel.getNmMakanan());
        Picasso.with(konteks).load(iModel.getGambar()).placeholder(R.drawable.noimage).fit().into(viewHolder.gambar);
        //viewHolder.gambar.setImageResource(iModel.getThumbnail());
        viewHolder.status.setText(iModel.getKetersediaan());
        viewHolder.harga.setText(nf.format(Double.valueOf(iModel.getHarga())));
        viewHolder.stok.setText("Stok : "+iModel.getStok());
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
