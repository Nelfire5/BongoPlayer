package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bongoplayerpojo.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextContra;
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

                Usuario user = new Usuario();
                user.setCorreo("1");
                user.setContrasena("1234");

/*                if(user.getCorreo().equals(correo) && user.getContrasena().equals(contraseña)) {
                    Toast.makeText(LoginActivity.this, "Login correcto :D", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas!", Toast.LENGTH_SHORT).show();
                }*/
                Toast.makeText(LoginActivity.this, "Login correcto :D", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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


}