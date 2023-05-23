package com.example.bongoplayer.Conexion;

import android.util.Log;

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

    public static final int INSERTAR_USUARIO = 1;
    public static final int ELIMINAR_USUARIO = 2;
    public static final int MODIFICAR_USUARIO = 3;
    public static final int LEER_USUARIO = 4;
    public static final int LEER_USUARIOS = 5;

    public static final int INSERTAR_LISTA = 6;
    public static final int ELIMINAR_LISTA = 7;
    public static final int MODIFICAR_LISTA = 8;
    public static final int LEER_LISTA = 9;
    public static final int LEER_LISTAS = 10;

    public static final int INSERTAR_CANCION = 11;
    public static final int ELIMINAR_CANCION = 12;
    public static final int MODIFICAR_CANCION = 13;
    public static final int LEER_CANCION = 14;
    public static final int LEER_CANCIONES = 15;
    //-------------------------------------------


    public Conexion(int accion) {
        Accion = accion;
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
    public void run(){
        super.run();
        Conexion cliente = new Conexion(getAccion());
        try {

            switch (cliente.getAccion()){
                case INSERTAR_USUARIO:
                    Integer usuarios = cliente.insertarUsuario((Usuario) cliente.getObjeto());
                    Log.e("sssssss","Registros Afectados: "+ usuarios);
                    break;
                case ELIMINAR_USUARIO:
                    Log.e("xxxxxx", "HOLAAAAAAAAAAAAA");
                case MODIFICAR_USUARIO:
                    break;
                case LEER_USUARIO:
                    Usuario user = cliente.leerUsuario(1);
                    Log.e("xxxxxx", user.toString());
                    break;
                case LEER_USUARIOS:
                case 6:
                    break;

                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case LEER_CANCION:
                case LEER_CANCIONES:
                    ArrayList<Cancion> canciones = cliente.leerCanciones();
                    Log.e("xxxxxxxx", canciones.toString());
                    break;
            }
        } catch (ExcepcionBP ex) {
            System.out.println(ex);
            Log.e("xxxxx",ex.toString());
        } catch (ExcepcionBPComunicaciones e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
