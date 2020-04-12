package com.example.tuboslider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private static final String hostname = "192.168.43.130";
    private static final int portaServidor = 90;
    private Toolbar mTopoToolbar;
    private PrintWriter output;
    private Socket socket = null;
    private MainActivity isto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.inc_topo_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();


        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setCheckedItem(R.id.search);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {

                    case R.id.configuracoes:
                    {
                       alertConfigs();
                    }break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final Handler mHandler = new Handler((Looper.getMainLooper())) {
         @Override
            public void handleMessage(Message message)
         {

             Toast.makeText(getBaseContext(), message.obj.toString(), Toast.LENGTH_LONG).show();

         }
        };

        final String ip_salvo;
        final int porta_salvo;
        final ManipularSharedPreferences sp1 = new ManipularSharedPreferences(isto, getBaseContext());


        if (sp1.buscarDados("conexao", 0, "ip") == true) {
            //ha um arquivo chamado conexao com uma chave ip ja criado anteriormente
            ip_salvo = sp1.getDadosString("conexao", 0, "ip");
        } else {
            ip_salvo = "192.168.43.1";
            sp1.setDados("conexao", 0, "ip", ip_salvo);

        }


        if (sp1.buscarDados("conexao", 0, "porta") == true) {
            //ha um arquivo chamado conexao com uma chave porta ja criado anteriormente
            porta_salvo = sp1.getDadosInt("conexao", 0, "porta");
        } else {
            porta_salvo = 8098;
            sp1.setDados("conexao", 0, "porta", porta_salvo);

        }


        new Thread(new Runnable() {
            public void run(){
                Message message ;

                try {
                    socket = new Socket(ip_salvo, porta_salvo);
                    message = mHandler.obtainMessage(1, "Conectado ao Servidor");
                    message.sendToTarget();
                    //Toast.makeText(getBaseContext(), "Conectado ao Servidor",Toast.LENGTH_SHORT).show();

                    try {
                        output = new PrintWriter(socket.getOutputStream());
                        message = mHandler.obtainMessage(1, "Fluxo de dados aberto!");
                        message.sendToTarget();
                        // Toast.makeText(getBaseContext(), "Fluxo de dados aberto",Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        message = mHandler.obtainMessage(1, "Sem conexão com o servidor!");
                        message.sendToTarget();
                        //Toast.makeText(getBaseContext(), "Sem conexão com o servidor!",Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    //Toast.makeText(getBaseContext(), "Sem conexão com o servidor!",Toast.LENGTH_SHORT).show();
                    message = mHandler.obtainMessage(1, "Sem conexão com o servidor!");
                    message.sendToTarget();
                    e.printStackTrace();
                }


            }
        }).start();


        isto = this;

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {


                    case R.id.atualizar: {
                       // Toast.makeText(getBaseContext(), "att cliquado", Toast.LENGTH_SHORT).show();

                      
                    }break;

                    }
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_topo_toolbar, menu);
        return true;

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    public void ligar(View view)
    {
        new Thread(new Runnable() {
            public void run(){
                output.write('L');
                output.flush();

            }
        }).start();

    }



    public void desligar(View view)
    {
        new Thread(new Runnable() {
            public void run(){
                output.write('D');
                output.flush();

            }
        }).start();
    }




    public void alertConfigs()
    {
        //busca por dados de conexao salvos antes


         String ip_salvo;
         int porta_salvo;
         final ManipularSharedPreferences sp1 = new ManipularSharedPreferences(isto, getBaseContext());


        if (sp1.buscarDados("conexao", 0, "ip") == true) {
            //ha um arquivo chamado conexao com uma chave ip ja criado anteriormente
            ip_salvo = sp1.getDadosString("conexao", 0, "ip");
        } else {
            ip_salvo = "192.168.43.1";
            sp1.setDados("conexao", 0, "ip", ip_salvo);

        }


        if (sp1.buscarDados("conexao", 0, "porta") == true) {
            //ha um arquivo chamado conexao com uma chave porta ja criado anteriormente
            porta_salvo = sp1.getDadosInt("conexao", 0, "porta");
        } else {
            porta_salvo = 8098;
            sp1.setDados("conexao", 0, "porta", porta_salvo);

        }


        final AlertDialog alert;
        final Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LayoutInflater li = getLayoutInflater();
        View v = li.inflate(R.layout.alert_configs, null);


        builder.setTitle("Conexão");
        builder.setView(v);
        alert = builder.create();
        alert.show();

        final EditText iTIp = v.findViewById(R.id.eTIP);
        final EditText iTPorta = v.findViewById(R.id.eTPorta);
        Button btnSalvarConfigs = (Button) v.findViewById(R.id.btnSalvarConfigs);

        iTIp.setText(ip_salvo.toString());
        iTPorta.setText(Integer.toString(porta_salvo));



        btnSalvarConfigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = iTIp.getText().toString();
                String porta = iTPorta.getText().toString();

                int int_porta = -1;

                boolean check = false;

                    if(ip.equals("") || ip.equals(" ") || ip == null)
                    {
                        Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();
                        Log.i("IP", "if1");
                        check = false;
                    }
                    else {
                        try {

                            Log.i("IP", ip);

                            String[] octetos = ip.toString().split("\\.");
                            if (octetos.length != 4) {
                                int i = octetos.length;
                                Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();
                                check = false;
                                Log.i("IP", "if2");
                                Log.i("IP", Integer.toString(i));

                                Log.i("IP", octetos[0]);
                                Log.i("IP", octetos[1]);
                                Log.i("IP", octetos[2]);
                                Log.i("IP", octetos[3]);


                            } else {
                                try {
                                    int octo0 = Integer.parseInt(octetos[0]);
                                    int octo1 = Integer.parseInt(octetos[1]);
                                    int octo2 = Integer.parseInt(octetos[2]);
                                    int octo3 = Integer.parseInt(octetos[3]);

                                    if (octo0 >= 0 && octo0 <= 255) {
                                        if (octo1 >= 0 && octo1 <= 255) {
                                            if (octo2 >= 0 && octo2 <= 255) {
                                                if (octo3 >= 0 && octo3 <= 255) {
                                                    check = true;

                                                    try {
                                                        int_porta = Integer.parseInt(porta);
                                                        if (int_porta >= 0 && int_porta <= 65000) {
                                                            check = true;
                                                        } else {
                                                            check = false;
                                                            Toast.makeText(getBaseContext(), "Porta deve ser um valor entre 0 e 65000", Toast.LENGTH_SHORT).show();

                                                        }

                                                    } catch (Exception e) {
                                                        Toast.makeText(getBaseContext(), "Porta deve ser um valor entre 0 e 65000", Toast.LENGTH_SHORT).show();
                                                        check = false;

                                                    }

                                                } else {
                                                    check = false;
                                                    Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();

                                                }
                                            } else {
                                                check = false;
                                                Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            check = false;
                                            Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();

                                        }


                                    } else {
                                        check = false;
                                        Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();

                                    }
                                } catch (Exception e) {
                                    Log.i("IP", "excessao");

                                    Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();
                                    check = false;
                                }


                            }
                        }catch (Exception e)
                        {
                            Log.i("IP", "excessao");

                            Toast.makeText(getBaseContext(), "Ip Inválido", Toast.LENGTH_SHORT).show();
                            check = false;

                        }






                    }







               if(check == true) {
                   //apos serem validados os dados sao salvos
                   sp1.setDados("conexao", 0, "ip", ip);
                   sp1.setDados("conexao", 0, "porta", int_porta);

                   //fecha o alertDialog
                   alert.dismiss();
               }


            }
        });

    }

}
