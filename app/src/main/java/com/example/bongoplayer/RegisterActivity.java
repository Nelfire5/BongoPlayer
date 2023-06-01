package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongoplayer.Conexion.Conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojo.Usuario;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;
import bongoplayerpojocomunicaciones.Peticion;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextApellido1;
    private EditText editTextApellido2;
    private EditText editTextCorreo;
    private EditText editTextContraseña;
    private EditText editTextConfirmarContraseña;
    private ImageView imageViewIcono;
    private EditText editTextAlias;
    private Button buttonRegistro;

    Usuario usuario = new Usuario();
    HiloRegistrar hilo = new HiloRegistrar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido1 = findViewById(R.id.editTextApellido1);
        editTextApellido2 = findViewById(R.id.editTextApellido2);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        editTextConfirmarContraseña = findViewById(R.id.editTextConfirmarContraseña);
        imageViewIcono = findViewById(R.id.imageViewIcono);
        editTextAlias = findViewById(R.id.editTextAlias);
        buttonRegistro = findViewById(R.id.buttonRegistro);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos
                String nombre = editTextNombre.getText().toString();
                String apellido1 = editTextApellido1.getText().toString();
                String apellido2 = editTextApellido2.getText().toString();
                String correo = editTextCorreo.getText().toString();
                String contraseña = editTextContraseña.getText().toString();
                String confirmarContraseña = editTextConfirmarContraseña.getText().toString();
                String alias = editTextAlias.getText().toString();

                // Validar
                if(contraseña.equals("") || confirmarContraseña.equals("") || nombre.equals("") || apellido1.equals("") || correo.equals("") || alias.equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "Los campos marcados con (*) son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!correo.contains("@") || !correo.contains("."))
                {
                    Toast.makeText(RegisterActivity.this, "El correo no es valido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!contraseña.equals(confirmarContraseña) ) {
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                usuario.setNombreUsuario((nombre));
                usuario.setApellido1((apellido1));
                usuario.setApellido2((apellido2));
                usuario.setCorreo((correo));
                usuario.setContrasena((contraseña));
                usuario.setAlias((alias));

                Log.e("user",usuario.toString());
            }
        });
        // Agregar listener para seleccionar una imagen de usuario
        imageViewIcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la lógica para permitir al usuario seleccionar una imagen de su dispositivo
                // En este ejemplo, simplemente estableceremos una imagen de muestra
                Bitmap sampleImage = BitmapFactory.decodeResource(getResources(), R.drawable.user);
                imageViewIcono.setImageBitmap(sampleImage);
            }
        });

        TextView link_to_login = findViewById(R.id.link_to_login);
        link_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //hilo.run();

    }
    public class HiloRegistrar extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Conexion conexion = new Conexion();
                int ra = conexion.insertarUsuario(usuario);
                if(ra == 1 )
                {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            } catch (ExcepcionBP e) {
                throw new RuntimeException(e);
            } catch (ExcepcionBPComunicaciones e) {
                throw new RuntimeException(e);
            }
        }
    }
}