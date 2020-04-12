package com.example.tuboslider;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class ManipularSharedPreferences {


    private Context tela;
    private Activity activity;


    public ManipularSharedPreferences(Activity activity, Context tela)
    {
        this.tela = tela;
        this.activity = activity;
    }

    public void setDados(String arquivo, int modo, String chave, String valor)
    {
      SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

        SharedPreferences.Editor editor_preferencias = preferences.edit();

        editor_preferencias.putString(chave, valor);
        editor_preferencias.commit();
        preferences.unregisterOnSharedPreferenceChangeListener(listener);


    }

    public void setDados(String arquivo, int modo, String chave, boolean valor)
    {             Toast.makeText(tela.getApplicationContext(), "set dados chamado", Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

        SharedPreferences.Editor editor_preferencias = preferences.edit();

        editor_preferencias.putBoolean(chave, valor);
        editor_preferencias.commit();

        preferences.unregisterOnSharedPreferenceChangeListener(listener);


    }
/*
    public void setDados(String arquivo, int modo, String chave, float valor)
    {
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

        SharedPreferences.Editor editor_preferencias = preferences.edit();

        editor_preferencias.putFloat(chave, valor);
        editor_preferencias.commit();
        preferences.unregisterOnSharedPreferenceChangeListener(listener);



    }

*/
    public void setDados(String arquivo, int modo, String chave, int valor)
    {
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

        SharedPreferences.Editor editor_preferencias = preferences.edit();

        editor_preferencias.putInt(chave, valor);
        editor_preferencias.commit();
        preferences.unregisterOnSharedPreferenceChangeListener(listener);



    }


    public void setDados(String arquivo, int modo, String chave, Set<String> valor)
    {
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);


        SharedPreferences.Editor editor_preferencias = preferences.edit();

        editor_preferencias.putStringSet(chave, valor);
        editor_preferencias.commit();
        preferences.unregisterOnSharedPreferenceChangeListener(listener);



    }


    public boolean buscarDados(String arquivo, int modo, String chave){
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

         return preferences.contains(chave);

    }

    public Map<String, ?> getDados(String arquivo, int modo)
    {
        String retorno = new String();
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

        Map<String, ?> tudo = preferences.getAll();

        return tudo;
    }

    public String getDadosString(String arquivo, int modo, String chave)
    {
        String retorno = new String();
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);

        boolean existe = preferences.contains(chave);

        if(existe) {
            SharedPreferences.Editor editor_preferencias = preferences.edit();

            retorno = preferences.getString(chave, null);

            preferences.unregisterOnSharedPreferenceChangeListener(listener);

            return retorno;
        }
        else
        {
            return null;
        }
    }

    public boolean getDadosBoolean(String arquivo, int modo, String chave)
    {
        boolean retorno = false;
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);


        SharedPreferences.Editor editor_preferencias = preferences.edit();

        retorno = preferences.getBoolean(chave, false);

        preferences.unregisterOnSharedPreferenceChangeListener(listener);

        return retorno;
    }


    public int getDadosInt(String arquivo, int modo, String chave)
    {
        int retorno = 0;
        SharedPreferences preferences = tela.getSharedPreferences(arquivo, modo);
        preferences.registerOnSharedPreferenceChangeListener(listener);


        SharedPreferences.Editor editor_preferencias = preferences.edit();

        retorno = (int) preferences.getInt(chave, 0);

        preferences.unregisterOnSharedPreferenceChangeListener(listener);

        return retorno;
    }






    OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener(){

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
             Toast.makeText(tela, key + "update", Toast.LENGTH_SHORT).show();
        }
    };

}
