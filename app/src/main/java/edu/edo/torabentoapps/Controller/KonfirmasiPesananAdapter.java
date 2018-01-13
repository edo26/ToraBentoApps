package edu.edo.torabentoapps.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Edo on 1/10/2018.
 */

public class KonfirmasiPesananAdapter extends RecyclerView.Adapter<KonfirmasiPesananAdapter.Holderku> {

    @Override
    public Holderku onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holderku holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Holderku extends RecyclerView.ViewHolder{

        public Holderku(View itemView) {
            super(itemView);
        }
    }
}
