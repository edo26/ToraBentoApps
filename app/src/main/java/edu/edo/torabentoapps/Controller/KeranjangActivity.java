package edu.edo.torabentoapps.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.edo.torabentoapps.Model.Keranjang.DataArray1;

public class KeranjangActivity {
 
    public static final String PREFS_NAME = "Keranjang";
    public static final String FAVORITES = "Keranjang";
    
    public KeranjangActivity() {
        super();
    }
 
    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<DataArray1> favorites) {
        SharedPreferences settings;
        Editor editor;
 
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
 
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
 
        editor.putString(FAVORITES, jsonFavorites);
 
        editor.commit();
    }
 
    public void addFavorite(Context context, DataArray1 DataArray1) {
        List<DataArray1> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<DataArray1>();
        favorites.add(DataArray1);
        saveFavorites(context, favorites);
    }
 
    public void removeFavorite(Context context, DataArray1 DataArray1) {
        ArrayList<DataArray1> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(DataArray1);
            saveFavorites(context, favorites);
        }
    }

    public void hapusData(Context context, List<DataArray1> data, int indeks) {
        KeranjangAdapter adapter;
        data = getFavorites(context);
        data.remove(indeks);
        saveFavorites(context, data);
        adapter = new KeranjangAdapter(data,context);
        adapter.notifyDataSetChanged();
    }
 
    public ArrayList<DataArray1> getFavorites(Context context) {
        SharedPreferences settings;
        List<DataArray1> favorites;
 
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
 
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            DataArray1[] favoriteItems = gson.fromJson(jsonFavorites,
                    DataArray1[].class);
 
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<DataArray1>(favorites);
        } else
            return null;
 
        return (ArrayList<DataArray1>) favorites;
    }
}