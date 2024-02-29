/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package USUARIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Josue Gavidia
 */
public class ManagerUsuarios {

    private RandomAccessFile RAF = null;
    private File F;
    public static ArrayList<Usuario> users;

    public ManagerUsuarios() throws IOException, ClassNotFoundException {
        F = new File("Usuarios");
        if (!F.exists()) {

            F.mkdir();
            System.out.println("Folder Usuarios se crea ");
        }
        if (F.list().length == 0) {
            createDefaults();
        }

    }

    public void createUserFolder(String name, String user, String pass) throws FileNotFoundException, IOException {
        Usuario us = new Usuario(name, user, pass);

        F = new File("Usuarios/" + us.getUsername());
        if (!F.exists()) {
            F.mkdir();
            System.out.println("Folder de " + us.getUsername() + " creado");
        } else {
            System.out.println("Folder de usuario ya existe");
        }

        if (F.listFiles().length == 0) {
            this.Serializer(us);
        } else {
            System.out.println("Ya tiene un binario");
        }

    }

    private void createDefaults() throws IOException {
        createUserFolder("ERICK", "Player 1", "default");
        createUserFolder("IAN", "Player 2", "default");
        createUserFolder("VALERIE", "Player 3", "default");
        createUserFolder("SANTIAGO", "Player 4", "default");

    }
//SERIALIZAR

    private void Serializer(Usuario user) throws IOException {
        String path = "Usuarios/" + user.getUsername() + "/" + user.getUsername() + ".onion";
        RAF = new RandomAccessFile(path, "rw");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream objeto = new ObjectOutputStream(bytes);
        objeto.writeObject(user);
        byte[] serialized = bytes.toByteArray();
        RAF.writeInt(serialized.length);
        RAF.write(serialized);

    }

    public Usuario SearchUser(String username) throws IOException, ClassNotFoundException {
        File route = new File("Usuarios");
        return DeSerializer(username, 0, route);
    }

    //RECURSIVA
    private Usuario DeSerializer(String username, int index, File route) throws IOException, ClassNotFoundException {

        Usuario tempUser = null;

        String folders[] = route.list();
        if (folders != null && index < folders.length) {

            if (folders[index].equals(username)) {
                System.out.println("encontrado folder de : " + username);

                RAF = new RandomAccessFile(new File(route, username + "/" + username + ".onion"), "r");
                int dataLength = RAF.readInt();
                byte[] serializedData = new byte[dataLength];
                RAF.readFully(serializedData);

                ByteArrayInputStream byteStream = new ByteArrayInputStream(serializedData);
                ObjectInputStream objectStream = new ObjectInputStream(byteStream);

                tempUser = (Usuario) objectStream.readObject();

                System.out.println(tempUser.getNAME());

            } else {
                System.out.println("No se encontro el folder de " + username);
                return DeSerializer(username, index + 1, route);
            }
        }

        return tempUser;
    }
    
    public String[] listUsers(){
        F = new File("Usuarios");
        
        return F.list();
    }

}
