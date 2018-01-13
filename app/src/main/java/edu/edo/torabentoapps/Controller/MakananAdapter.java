package edu.edo.torabentoapps.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.roughike.bottombar.BottomBar;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.edo.torabentoapps.Model.Keranjang.DataArray1;
import edu.edo.torabentoapps.Model.Makanan.itemRespon;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.View.KeranjangFragment;

/**
 * Created by ASUS on 16/08/2017.
 */

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder>{
    private List<itemRespon> mMakanan;
    private Context konteks;
    public BottomBar bottomBar;
    Bitmap dataGambar = null;
    ProgressDialog pd;

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth, Context konteks)
    {
        try
        {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            // RECREATE THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
        }
        catch(Exception e) {
            //Toast.makeText(konteks, "", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public MakananAdapter(List<itemRespon> mMakanan, Context konteks){
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
        final itemRespon iModel = mMakanan.get(i);
        pd = new ProgressDialog(konteks);
        pd.setMessage("Silahkan tunggu....");
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);
        viewHolder.nmMakanan.setText(iModel.getNmMakanan());
        Picasso.with(konteks).load(iModel.getGambar()).placeholder(R.drawable.noimage).fit().into(viewHolder.gambar);
//        dataGambar = ((BitmapDrawable)viewHolder.gambar.getDrawable()).getBitmap();
//        dataGambar = getResizedBitmap(dataGambar, 80, 80,konteks);
        viewHolder.harga.setText(nf.format(Double.valueOf(iModel.getHarga())));
        viewHolder.stok.setText("Stok : "+iModel.getStok());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean wrapScroll = true;
                    final MaterialDialog mDialog = new MaterialDialog.Builder(v.getContext())
                            .customView(R.layout.dialog_layout,wrapScroll)
                            .title("PESANAN")
                            .neutralText("BELI")
                            .negativeText("BATAL")
                            .buttonsGravity(GravityEnum.CENTER)
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //masuk ke transaksi fragment dengan item yang dipilih
                                    //tess
                                    KeranjangFragment frag = new KeranjangFragment();
                                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content, frag);
                                    fragmentTransaction.commit();
                                   // List<transaksiModel> lt = new ArrayList<transaksiModel>();

                                    bottomBar = (BottomBar)((Activity) v.getContext()).findViewById(R.id.bottombar);
                                    bottomBar.selectTabAtPosition(1);
                                    pd.show();

                                    //Gathering Data
                                    EditText qty = (EditText)dialog.findViewById(R.id.quantitydidialog);
                                    String dataNamaMakanan = iModel.getNmMakanan();
                                    int dataQuantity = Integer.parseInt(qty.getText().toString());
                                    int dataHarga = Integer.parseInt(iModel.getHarga());

                                    //Inisialiasi Set
//                                    List<ModelReseller> keranjang = new ArrayList<ModelReseller>();
                                    DataArray1 data = new DataArray1(dataNamaMakanan,dataQuantity,dataHarga,iModel.getGambar());
//                                    keranjang.add(data);
                                    new KeranjangActivity().addFavorite(konteks,data);
//                                    //keranjang.add(data);
//                                    Set<ModelReseller> setts = new HashSet<ModelReseller>();
//                                    //keranjang.add(data);
//                                    setts.add(data);
//                                    SharedPreferences.Editor editor = konteks.getSharedPreferences("Keranjang",MODE_PRIVATE).edit();
//                                    editor.putStringSet("keranjang",);


                                    //Toast.makeText(konteks, "Nama makanan : "+dataNamaMakanan+", Qty : "+dataQuantity+", Gambar : "+dataGambar+", Harga : "+dataHarga, Toast.LENGTH_LONG).show();
                                    pd.dismiss();

                                }
                            })
                            .build();
                    TextView makanan = (TextView) mDialog.findViewById(R.id.makananBeli);
                    TextView hargabeli = (TextView) mDialog.findViewById(R.id.hargabeli);
                    TextView stoktersedia = (TextView) mDialog.findViewById(R.id.stokdialog);
                    ImageView gambardialog = (ImageView) mDialog.findViewById(R.id.gambarBeli);
                    Picasso.with(konteks).load(iModel.getGambar()).placeholder(R.drawable.noimage).fit().into(gambardialog);
                    NumberFormat nf = NumberFormat.getCurrencyInstance(local);
                    hargabeli.setText(nf.format(Double.valueOf(iModel.getHarga())));
                    makanan.setText(iModel.getNmMakanan());
                    stoktersedia.setText(""+iModel.getStok());
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
        public TextView harga;
        public TextView stok;

        public ViewHolder(View itemView){
            super(itemView);
            gambar =(ImageView)itemView.findViewById(R.id.thumbnails);
            nmMakanan =(TextView)itemView.findViewById(R.id.nmMakanan);
            harga =(TextView)itemView.findViewById(R.id.harga);
            stok = (TextView)itemView.findViewById(R.id.stokmakantv);

        }
    }
}

