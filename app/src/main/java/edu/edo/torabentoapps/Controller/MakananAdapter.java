package edu.edo.torabentoapps.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.roughike.bottombar.BottomBar;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.edo.torabentoapps.Model.Keranjang.DataArray1;
import edu.edo.torabentoapps.Model.Makanan.DataArray;
import edu.edo.torabentoapps.Model.Makanan.ModelStokMakanan;
import edu.edo.torabentoapps.Model.Reseller.ModelLogin;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.View.KeranjangFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 16/08/2017.
 */

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder>{
    private List<DataArray> mMakanan;
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

    public MakananAdapter(List<DataArray> mMakanan, Context konteks){
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
        final DataArray iModel = mMakanan.get(i);
        final String[] idreseller = new String[1];
        pd = new ProgressDialog(konteks);
        pd.setIcon(R.drawable.loading);
        pd.setMessage("Silahkan tunggu....");
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);
        viewHolder.nmMakanan.setText(iModel.getNamaMakanan());
        Picasso.with(konteks).load(iModel.getGambar()).placeholder(R.drawable.noimage).fit().into(viewHolder.gambar);
//        dataGambar = ((BitmapDrawable)viewHolder.gambar.getDrawable()).getBitmap();
//        dataGambar = getResizedBitmap(dataGambar, 80, 80,konteks);
        viewHolder.harga.setText(nf.format(Double.valueOf(iModel.getHarga())));

        SampleAPI.Factory.getIstance((FragmentActivity) konteks).viewStok().enqueue(new Callback<ModelStokMakanan>() {
            @Override
            public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                if(response.isSuccessful()) {
                    if(response.body().getNilai().equals(1)){
//                        for(int i = 0; i < mMakanan.size(); i ++){
//
//                        }
                        idreseller[0] = response.body().getDataArray1().get(0).getIdReseller();

                        SampleAPI.Factory.getIstance((FragmentActivity) konteks).getIdReseller(idreseller[0]).enqueue(new Callback<ModelLogin>() {
                            @Override
                            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                                if(response.isSuccessful()){
                                    SharedPreferences.Editor editor = konteks.getSharedPreferences("id_reseller",MODE_PRIVATE).edit();
                                    editor.putString("id_reseller", response.body().getDataArray().get(0).getIdReseller());
                                    editor.apply();
                                }else{
                                    Toast.makeText(konteks, "Unsuccesfully", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelLogin> call, Throwable t) {
                                Toast.makeText(konteks, "onfailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        switch (iModel.getNamaMakanan()) {
                            case "Chichken Katsu":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getChickenkatsuStok());
                                break;
                            case "Ebifurai":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getEbifuraiStok());
                                break;
                            case "Egg Chicken Roll":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getEggchickenrollStok());
                                break;
                            case "Ekado":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getEkkadoStok());
                                break;
                            case "Beef Teriyaki":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getKakinagaStok());
                                break;
                            case "Kani Roll":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getKanirollStok());
                                break;
                            case "Shrimp Roll":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getShrimprollStok());
                                break;
                            case "Spicy Chicken":
                                viewHolder.stok.setText("Stok : " + response.body().getDataArray1().get(0).getSpicychickenStok());
                                break;
                        }
                    }
                }else{
                    Toast.makeText(konteks, "Error euy unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                Toast.makeText(konteks, "ONFAILURE : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean wrapScroll = true;
                    MaterialDialog mDialog = new MaterialDialog.Builder(v.getContext())
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


                                    //Gathering Data
                                    EditText qty = (EditText)dialog.findViewById(R.id.quantitydidialog);
                                    TextView stoktersedia = (TextView) dialog.findViewById(R.id.stokdialog);
                                    if(qty.getText().toString().equals("")){
                                        pd.dismiss();
                                        new AlertDialog.Builder(dialog.getContext())
                                                .setTitle("PESAN")
                                                .setIcon(R.drawable.error)
                                                .setMessage("PERIKSA LAGI APA YANG ANDA MASUKKAN!")
                                                .show();
                                    }else if(Integer.parseInt(qty.getText().toString()) > Integer.parseInt(stoktersedia.getText().toString()) ){
                                        pd.dismiss();
                                        new AlertDialog.Builder(dialog.getContext())
                                                .setTitle("PESAN")
                                                .setIcon(R.drawable.error)
                                                .setMessage("STOK YANG TERSEDIA HANYA TINGGAL "+stoktersedia.getText().toString()+" !")
                                                .show();
                                    }else{
                                        fragmentTransaction.commit();
                                        // List<transaksiModel> lt = new ArrayList<transaksiModel>();

                                        bottomBar = (BottomBar)((Activity) v.getContext()).findViewById(R.id.bottombar);
                                        bottomBar.selectTabAtPosition(1);
                                        pd.show();
                                        String dataNamaMakanan = iModel.getNamaMakanan();
                                        int dataQuantity = Integer.parseInt(qty.getText().toString());
                                        int dataHarga = Integer.parseInt(iModel.getHarga());
                                        DataArray1 data = new DataArray1(dataNamaMakanan,dataQuantity,dataHarga,iModel.getGambar());
                                        new KeranjangActivity().addFavorite(konteks,data);
                                        pd.dismiss();
                                    }


                                    //Inisialiasi Set
//                                    List<ModelReseller> keranjang = new ArrayList<ModelReseller>();

//                                    //keranjang.add(data);
//                                    Set<ModelReseller> setts = new HashSet<ModelReseller>();
//                                    //keranjang.add(data);
//                                    setts.add(data);
//                                    SharedPreferences.Editor editor = konteks.getSharedPreferences("Keranjang",MODE_PRIVATE).edit();
//                                    editor.putStringSet("keranjang",);


                                    //Toast.makeText(konteks, "Nama makanan : "+dataNamaMakanan+", Qty : "+dataQuantity+", Gambar : "+dataGambar+", Harga : "+dataHarga, Toast.LENGTH_LONG).show();

                                }
                            })
                            .build();
                    TextView makanan = (TextView) mDialog.findViewById(R.id.makananBeli);
                    TextView hargabeli = (TextView) mDialog.findViewById(R.id.hargabeli);
                    final TextView stoktersedia = (TextView) mDialog.findViewById(R.id.stokdialog);
                    ImageView gambardialog = (ImageView) mDialog.findViewById(R.id.gambarBeli);
                    Picasso.with(konteks).load(iModel.getGambar()).placeholder(R.drawable.noimage).fit().into(gambardialog);
                    NumberFormat nf = NumberFormat.getCurrencyInstance(local);
                    hargabeli.setText(nf.format(Double.valueOf(iModel.getHarga())));
                    makanan.setText(iModel.getNamaMakanan());
                    //stoktersedia.setText(""+iModel.getStok());

                SampleAPI.Factory.getIstance((FragmentActivity) konteks).viewStok().enqueue(new Callback<ModelStokMakanan>() {
                    @Override
                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getNilai().equals(1)){
//                        for(int i = 0; i < mMakanan.size(); i ++){
//
//                        }
                                idreseller[0] = response.body().getDataArray1().get(0).getIdReseller();
                                SharedPreferences.Editor editor = konteks.getSharedPreferences("id_reseller",MODE_PRIVATE).edit();
                                editor.putString("id_reseller", idreseller[0]);
                                editor.apply();

                                switch (iModel.getNamaMakanan()) {
                                    case "Chichken Katsu":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getChickenkatsuStok());
                                        break;
                                    case "Ebifurai":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getEbifuraiStok());
                                        break;
                                    case "Egg Chicken Roll":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getEggchickenrollStok());
                                        break;
                                    case "Ekado":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getEkkadoStok());
                                        break;
                                    case "Beef Teriyaki":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getKakinagaStok());
                                        break;
                                    case "Kani Roll":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getKanirollStok());
                                        break;
                                    case "Shrimp Roll":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getShrimprollStok());
                                        break;
                                    case "Spicy Chicken":
                                        stoktersedia.setText(response.body().getDataArray1().get(0).getSpicychickenStok());
                                        break;
                                }
                            }
                        }else{
                            Toast.makeText(konteks, "Error euy unsuccessfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                        Toast.makeText(konteks, "ONFAILURE : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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

