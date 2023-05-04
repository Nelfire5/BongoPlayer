package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class prueba extends AppCompatActivity {

    EditText correo;
    ImageButton consultar;
    TextView nombre;
    TextView apellido;
    TextView contraseña;
    TextView alias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);


        correo = (EditText) findViewById(R.id.txtCorreo);
        nombre = (TextView) findViewById(R.id.lblnombre);
        apellido = (TextView) findViewById(R.id.lblapellido);
        contraseña = (TextView) findViewById(R.id.lblcontraseña);
        alias = (TextView) findViewById(R.id.lblAlias);

        consultar = (ImageButton)  findViewById(R.id.btnconsultar);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarpersonal();
            }
        });

    }

    public Connection conexionBD(){

        String ip="192.168.1.41", port="1433", username="Nelson", password="12345678",database="BongoPlayer";
        Connection con =null;
        try {
            StrictMode.ThreadPolicy politica= new  StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String conUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+";databaseName="+database+";User="+username+";password="+password+";";
            con= DriverManager.getConnection(conUrl);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("excepcion",e.toString());
        }
        return con;
    }


    public void consultarpersonal(){

        try {
            Statement stm= conexionBD().createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM USUARIO WHERE CORREO='" + correo.getText().toString()+"'");
            if(rs.next()){
                nombre.setText(rs.getString(2));
                apellido.setText(rs.getString(3));
                contraseña.setText(rs.getString(4));
                alias.setText(rs.getString(5));
            }
            correo.setText("");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void x(){

    }
}