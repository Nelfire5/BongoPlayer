package com.example.bongoplayer.Conexion;

import android.content.Intent;
import android.util.Log;

import com.example.bongoplayer.RegisterActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import bongoplayerpojo.*;
import bongoplayerpojocomunicaciones.*;

public class Conexion extends Thread{

    private Object objeto;
    private int pk;
    private int Accion;
    //-------------------------------------------


    public Conexion() {
    }

    public Conexion(Object objeto, int accion) {
        this.objeto = objeto;
        this.Accion = accion;
    }
    public Conexion(int pk, int accion) {
        this.pk = pk;
        this.Accion = accion;
    }
    public Conexion(Object objeto, int pk, int accion) {
        this.objeto = objeto;
        this.pk = pk;
        this.Accion = accion;
    }

    public Conexion(Object objeto) {
        this.objeto = objeto;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getAccion() {
        return Accion;
    }

    public void setAccion(int accion) {
        Accion = accion;
    }

    //-----------------------------------------------------------------


    @Override
    public void run() {
        super.run();

        try {
            int ra = insertarUsuario((Usuario) getObjeto());


        } catch (ExcepcionBP e) {
            throw new RuntimeException(e);
        } catch (ExcepcionBPComunicaciones e) {
            throw new RuntimeException(e);
        }
    }

    public Integer insertarUsuario(Usuario usuario) throws ExcepcionBP, ExcepcionBPComunicaciones {

        try {
            String equipoServidor = "192.168.1.27";
            int puertoServidor = 30500;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            Peticion peticion = new Peticion(Peticion.INSERTAR_USUARIO, usuario);
            oos.writeObject(peticion);

            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            Object respuesta = (Object) ois.readObject();

            ois.close();
            oos.close();
            if(respuesta.equals(1)){

                Integer registrosAfectados = (Integer) respuesta;
                Log.e("respuesta", respuesta.toString());
                return registrosAfectados;
            }
            else if (respuesta.equals(0)){

                ExcepcionBP e = (ExcepcionBP) respuesta;
                throw e;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Log class not found excepcion"+ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("Error general del sistema");
            throw e;

        }
        return null;
    }

    public Integer eliminarUsuario(Integer usuarioId){

        return null;
    }

    public Integer actualizarUsuario(Integer usuarioId,Usuario usuario) {

        return null;
    }

    public Usuario leerUsuario(Integer usuarioId) throws ClassNotFoundException, ExcepcionBP {
        try {
            String equipoServidor = "192.168.1.27";
            int puertoServidor = 30500;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            Peticion peticion = new Peticion(Peticion.LEER_USUARIO,usuarioId);
            oos.writeObject(peticion);

            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            Object respuesta = ois.readObject();
            Log.e("aqui",respuesta.toString());

            ois.close();
            oos.close();

            if(!respuesta.equals(null)){

                Usuario usuario = (Usuario) respuesta;
                return usuario;
            }
            else if (respuesta.equals(null)){

                ExcepcionBP e = (ExcepcionBP) respuesta;
                throw e;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Log class not found excepcion"+ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("Error general del sistema");
            throw ex;

        } catch (ExcepcionBP ex) {
            System.out.println("Log IO exception" + ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("No se ha podido establecer la conexion a la red. Revise su conexion");
            throw ex;
        }
        return null;
    }

    public ArrayList<Usuario> leerUsuarios() throws ExcepcionBP, ExcepcionBPComunicaciones{

        try {
            String equipoServidor = "192.168.1.27";
            int puertoServidor = 30500;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            Peticion peticion = new Peticion(Peticion.LEER_USUARIOS);
            oos.writeObject(peticion);

            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            Object respuesta = ois.readObject();
            //Log.e("aqui",respuesta.toString());

            ois.close();
            oos.close();

            if(!respuesta.equals(null)){

                ArrayList<Usuario> usuarios = (ArrayList<Usuario>) respuesta;
                return usuarios;
            }
            else if (respuesta.equals(null)){

                ExcepcionBP e = (ExcepcionBP) respuesta;
                throw e;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Log class not found excepcion"+ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("Error general del sistema");
            throw e;

        } /*catch (ExcepcionBPComunicaciones ex) {
            System.out.println("Log IO exception" + ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("No se ha podido establecer la conexion a la red. Revise su conexion");
            throw e;
        }*/
        return null;
    }

    public ArrayList<Cancion> leerCanciones() throws ExcepcionBP, ExcepcionBPComunicaciones{

        try {
            String equipoServidor = "192.168.1.27";
            int puertoServidor = 30500;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            Peticion peticion = new Peticion(Peticion.LEER_CANCIONES);
            oos.writeObject(peticion);

            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            Object respuesta = ois.readObject();
            //Log.e("aqui",respuesta.toString());

            ois.close();
            oos.close();

            if(!respuesta.equals(null)){

                ArrayList<Cancion> canciones = (ArrayList<Cancion>) respuesta;
                return canciones;
            }
            else if (respuesta.equals(null)){

                ExcepcionBP e = (ExcepcionBP) respuesta;
                throw e;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Log class not found excepcion"+ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("Error general del sistema");
            throw e;

        } /*catch (ExcepcionBPComunicaciones ex) {
            System.out.println("Log IO exception" + ex.getMessage());
            ExcepcionBPComunicaciones e = new ExcepcionBPComunicaciones();
            e.setMemensajeErrorAdmin(ex.getMessage());
            e.setMemensajeErrorUser("No se ha podido establecer la conexion a la red. Revise su conexion");
            throw e;
        }*/
        return null;
    }
}
