package edu.edo.torabentoapps.Controller;

import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import edu.edo.torabentoapps.BuildConfig;
import edu.edo.torabentoapps.Model.Keranjang.ModelKeranjangDB;
import edu.edo.torabentoapps.Model.Makanan.ModelMakanan;
import edu.edo.torabentoapps.Model.Makanan.ModelStokMakanan;
import edu.edo.torabentoapps.Model.Makanan.ModelTotalStok;
import edu.edo.torabentoapps.Model.Pembeli.ModelPembeli;
import edu.edo.torabentoapps.Model.Reseller.ModelLogin;
import edu.edo.torabentoapps.Model.Transaksi.ModelDetailPesanan;
import edu.edo.torabentoapps.Model.Transaksi.ModelTransaksi;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

//import com.andrei.template.BuildConfig;

public interface SampleAPI {

  String BASE_URL = "https://kptoratorabento.000webhostapp.com/"; //Ganti base url nya sesuai kebutuhan

  //Sesuai kebutuhan get data nya
  //@GET("your_endpoint") Call<YOUR_POJO> getWeather(@Query("from") String from);
  //Contoh @GET("view.php") Call<Model> getModel();


  @FormUrlEncoded
  @POST("json_t_pembeli.php?operasi=insert")
  Call<ModelPembeli> insertDataPembeli(
          @Field("nama_pembeli") String nama,
          @Field("nomorhp") String nomorhp,
          @Field("email") String email,
          @Field("alamat") String alamat
  );

  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=insert")
  Call<ModelLogin> insertDataReseller(
                                 @Field("alamat") String alamat,
                                 @Field("email") String email,
                                 @Field("password") String password
                             );

  @FormUrlEncoded
  @POST("json_t_keranjang.php?operasi=insert")
  Call<ModelKeranjangDB> insertDataKeranjang(
          @Field("id_makanan") String id_makanan,
          @Field("id_pembeli") String id_pembeli,
          @Field("qty") String qty
  );

  @FormUrlEncoded
  @POST("json_t_resellerdashboard.php?operasi=insert")
  Call<ModelStokMakanan> insertDataMakanan(
          @Field("id_reseller") String id_reseller,
          @Field("email") String email,
          @Field("ekkado_stok") String s1,
          @Field("chickenkatsu_stok") String s2,
          @Field("ebifurai_stok") String s3,
          @Field("eggchickenroll_stok") String s4,
          @Field("kakinaga_stok") String s5,
          @Field("kaniroll_stok") String s6,
          @Field("shrimproll_stok") String s7,
          @Field("spicychicken_stok") String s8
  );

  @FormUrlEncoded
  @POST("json_t_resellerdashboard.php?operasi=viewStok")
  Call<ModelStokMakanan> viewStokReseller(@Field("email") String email);


  @GET("json_t_resellerdashboard.php?operasi=view")
  Call<ModelStokMakanan> viewStok();

  @GET("json_t_transaksi.php?operasi=view")
  Call<ModelTransaksi> viewTransaksi();

  @FormUrlEncoded
  @POST("json_t_transaksi.php?operasi=viewHistory")
  Call<ModelTransaksi> viewHistory(@Field("id_transaksi") String id_transaksi);

  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=getIdReseller")
  Call<ModelLogin> getIdReseller(@Field("email") String email);

  @FormUrlEncoded
  @POST("json_t_makanan.php?operasi=getIdMakanan")
  Call<ModelMakanan> getIdMakanan(@Field("nama_makanan") String nama_makanan);

  @FormUrlEncoded
  @POST("json_t_pembeli.php?operasi=getIdPembeli")
  Call<ModelPembeli> getIdPembeli(@Field("nomorhp") String nomorhp);

  @FormUrlEncoded
  @POST("json_t_keranjang.php?operasi=getIdKeranjang")
  Call<ModelKeranjangDB> getIdKeranjang(@Field("id_pembeli") String id_pembeli);

  ////////////////////////////////////////////////////////////////////////////////

  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=getResellerById")
  Call<ModelLogin> getDataByIdReseller(@Field("id_reseller") String id_reseller);

  @FormUrlEncoded
  @POST("json_t_makanan.php?operasi=getMakananById")
  Call<ModelMakanan> getDataByIdMakanan(@Field("id_makanan") String id_makanan);

  @FormUrlEncoded
  @POST("json_t_pembeli.php?operasi=getPembeliById")
  Call<ModelPembeli> getDataByIdPembeli(@Field("id_pembeli") String id_pembeli);

  @FormUrlEncoded
  @POST("json_t_keranjang.php?operasi=getKeranjangById")
  Call<ModelKeranjangDB> getDataByIdKeranjang(@Field("id_keranjang") String id_keranjang);

  @FormUrlEncoded
  @POST("json_t_resellerdashboard.php?operasi=update")
  Call<ModelStokMakanan> updateStokReseller(@Field("email") String email,
                                            @Field("ebifurai_stok") String torben1,
                                            @Field("chickenkatsu_stok") String torben2,
                                            @Field("kakinaga_stok") String torben3,
                                            @Field("ekkado_stok") String torben4,
                                            @Field("shrimproll_stok") String torben5,
                                            @Field("spicychicken_stok") String torben6,
                                            @Field("kaniroll_stok") String torben7,
                                            @Field("eggchickenroll_stok") String torben8);

  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=validasi")
  Call<ModelLogin> validasii(@Field("username") String username, @Field("password") String password);

  @GET("json_t_makanan.php?operasi=view")
  Call<ModelMakanan> viewMakanan();

  @GET("json_t_reseller.php?operasi=view")
  Call<ModelLogin> viewReseller();

  @GET("json_t_keranjang.php?operasi=view")
  Call<ModelKeranjangDB> viewKeranjang();

  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=tesSQL")
  Call<ModelLogin> submitSQL(@Field("SQL") String QuerySQLnya);

  @FormUrlEncoded
  @POST("json_t_transaksi.php?operasi=tesSQL")
  Call<ModelDetailPesanan> submitSQLTr(@Field("SQL") String QuerySQLnya);

  @FormUrlEncoded
  @POST("json_t_transaksi.php?operasi=tesSQL")
  Call<ModelDetailPesanan> insertTransaksi(@Field("SQL") String QuerySQLnya);

  @FormUrlEncoded
  @POST("json_t_transaksi.php?operasi=insert")
  Call<ModelDetailPesanan> insertManualTrans(@Field("id_reseller") String id_reseller,
                                             @Field("id_pembeli") String id_pembeli,
                                             @Field("status_transaksi") String status_transaksi,
                                             @Field("tanggal_transaksi") String tanggal_transaksi,
                                             @Field("resipembayaran") String resi_pembayaran,
                                             @Field("nama_pembeli") String nama_pembeli
                                             );

  @FormUrlEncoded
  @POST("json_t_resellerdashboard.php?operasi=tesSQL")
  Call<ModelTotalStok> submitSQLRd(@Field("SQL") String QuerySQLnya);

  @FormUrlEncoded
  @POST("json_t_makanan.php?operasi=update")
  Call<ModelMakanan> updateMakanan(@Field("id_reseller") String idreseller,
                                   @Field("id_makanan") String idmakanan,
                                   @Field("nama_makanan") String namamakanan,
                                   @Field("stok") String stok,
                                   @Field("harga") String harga,
                                   @Field("status") String status,
                                   @Field("gambar") String gambar);

  @FormUrlEncoded
  @POST("json_t_transaksi.php?operasi=update")
  Call<ModelTransaksi> updateTransaksi(@Field("id_transaksi") String idtransaksi,
                                   @Field("status_transaksi") String status);

  @FormUrlEncoded
  @POST("json_t_makanan.php?operasi=delete")
  Call<ModelMakanan> deleteMakanan(@Field("id_makanan") String idmakanan);


  class Factory {
    private static SampleAPI service;

    public static SampleAPI getIstance(FragmentActivity context) {
      if (service == null) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        //builder.certificatePinner(new CertificatePinner.Builder().add("*.androidadvance.com", "sha256/RqzElicVPA6LkKm9HblOvNOUqWmD+4zNXcRb+WjcaAE=")
        //    .add("*.xxxxxx.com", "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
        //    .add("*.xxxxxxx.com", "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
        //    .add("*.xxxxxxx.com", "sha256/VjLZe/p3W/PJnd6lL8JVNBCGQBZynFLdZSTIqcO0SJ8=")
        //    .build());

        if (BuildConfig.DEBUG) {
          HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
          interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
          builder.addInterceptor(interceptor);
        }

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        builder.cache(cache);

	Gson gson = new GsonBuilder()
        .setLenient()
        .create();

        Retrofit retrofit = new Retrofit.Builder().client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build();
        service = retrofit.create(SampleAPI.class);
        return service;
      } else {
        return service;
      }
    }
  }
}
