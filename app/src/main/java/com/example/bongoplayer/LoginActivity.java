package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongoplayer.Conexion.Conexion;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojo.Usuario;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextContra;

    Usuario user = new Usuario();

    int ra;
    int ra2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextCorreo= findViewById(R.id.txtEmail);
        editTextContra= findViewById(R.id.txtPass);
        Button entrar = findViewById(R.id.btnLogin);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = editTextCorreo.getText().toString();
                String contraseña = editTextContra.getText().toString();

                if(!correo.equals("") && !contraseña.equals("")) {
                    user.setCorreo(correo);
                    user.setContrasena(contraseña);
                    ra = 1;
                }
                else {
                    Toast.makeText(LoginActivity.this, "Introduce los datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(ra == 1)
                {
                    int aux = -1;
                    try {
                        aux = new hilologin().execute().get();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (aux == 1)
                    {
                        Toast.makeText(LoginActivity.this, "Login correcto :D", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("usuarioID",user.getUsuarioId());
                        startActivity(intent);
                    }
                    else if(aux == 0){
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas!", Toast.LENGTH_SHORT).show();
                        aux = -1;
                    }
                }
            }
        });

        TextView link_to_register = findViewById(R.id.link_to_register);
        link_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public class hilologin extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {

            try {
                Conexion conexion = new Conexion();
                ArrayList<Usuario> usuarios = conexion.leerUsuarios();

                for(int i =0; i<usuarios.size();i++)
                {
                    String contraaux = usuarios.get(i).getContrasena();
                    Log.e("contra",contraaux);
                    if(user.getCorreo().equals(usuarios.get(i).getCorreo()) && user.getContrasena().equals(contraaux)) {
                        ra2 = 1;
                        return ra2;
                    }
                }

            } catch (ExcepcionBP e) {
                throw new RuntimeException(e);
            } catch (ExcepcionBPComunicaciones e) {
                throw new RuntimeException(e);
            }
            ra2 = 0;
            return ra2;
        }
    }




}