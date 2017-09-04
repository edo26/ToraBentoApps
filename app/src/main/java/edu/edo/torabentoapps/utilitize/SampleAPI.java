package edu.edo.torabentoapps.utilitize;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
//import com.andrei.template.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.edo.torabentoapps.BuildConfig;
import edu.edo.torabentoapps.Model.DataArray;
import edu.edo.torabentoapps.Model.ResellerModel;
import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.adapter.gridItemAdapter;
import edu.edo.torabentoapps.daftarreseller;
import edu.edo.torabentoapps.fragment.ItemFragment;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SampleAPI {

  String BASE_URL = "https://kptoratorabento.000webhostapp.com/"; //Ganti base url nya sesuai kebutuhan

  //Sesuai kebutuhan get data nya
  //@GET("your_endpoint") Call<YOUR_POJO> getWeather(@Query("from") String from);
  //Contoh @GET("view.php") Call<Model> getModel();


  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=insert")
  Call<ResellerModel> insertData(@Field("nama_reseller") String namareseller,
                                 @Field("alamat") String alamat,
                                 @Field("nomorhp") String nomorhp,
                                 @Field("email") String email,
                                 @Field("username") String username,
                                 @Field("password") String password
                             );

  @FormUrlEncoded
  @POST("json_t_reseller.php?operasi=validasi")
  Call<ResellerModel> validasii(@Field("username") String username, @Field("password") String password);

  @GET("json_t_makanan.php?operasi=view")
  Call<itemRespon> viewMakanan();


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
