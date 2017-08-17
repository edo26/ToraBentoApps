package edu.edo.torabentoapps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anggy on 12/08/2017.
 */

public class Adapter {

    public static class HolderInimah extends RecyclerView.ViewHolder{
        ImageView thumbnails;
        TextView labelfood;
        public HolderInimah(View itemView) {
            super(itemView);
            thumbnails = (ImageView)itemView.findViewById(R.id.thumbnails);
            labelfood = (TextView)itemView.findViewById(R.id.nmMakanan);

        }

    }

}
