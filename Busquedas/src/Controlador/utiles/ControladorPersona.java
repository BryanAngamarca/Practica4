/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.utiles;

import com.google.gson.Gson;
import Modelo.ListaEnlazadaServices;
import Modelo.Persona;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author User
 */
public class ControladorPersona {

    ListaEnlazadaServices<Persona> lPersonas = new ListaEnlazadaServices<Persona>();

    public ListaEnlazadaServices<Persona> getListaPersona() {
        return lPersonas;
    }

    public void setListaPersona(ListaEnlazadaServices<Persona> listaPer) {
        this.lPersonas = listaPer;
    }

    public int getSize() {
        return lPersonas.getSize();
    }

    public void guardar() throws IOException {
        Gson json = new Gson();
        Persona[] personas = new Persona[lPersonas.getSize()];
        for (int i = 0; i < lPersonas.getSize(); i++) {
            personas[i] = lPersonas.obtenerDato(i);
        }
        String jsons = json.toJson(personas);
        FileWriter fw = new FileWriter("Personas" + ".json");
        fw.write(jsons);
        fw.flush();
    }

    public void cargar() throws FileNotFoundException, IOException {
        Gson json = new Gson();
        FileReader file = new FileReader("Personas.json");
        String jsonString = "";
        int valor = file.read();
        //    System.out.println("dwe");
        StringBuilder sb = new StringBuilder();
        while (valor != - 1) {
            sb.append(String.valueOf((char) valor));
            valor = file.read();
        }
        jsonString = sb.toString();
        //    System.out.println("dwe");
        Persona[] aux = json.fromJson(jsonString, Persona[].class);
        for (int i = aux.length - 1; i >= 0; i--) {
            lPersonas.insertarAlInicio(aux[i]);
        }
        //  System.out.println(listaPersonas.obtenerDato(0).getApellido());

    }

    public void llenar(int n) {
        String[] nombres = {"Pedro", "Juan", "Bryan", "Marco", "Mat", "Hanna", "Lazie", "Ainara", "Nicole", "Viviana", "Megan", "Felipe", "Daniel", "David", "Jordy", "Juan", "Jose", "Carlos", "Gonzalo", "Jean", "Andres", "Cesar", "Christian", "Leandro", "Leonardo", "Stalin", "Jefferson", "Michael", "Elian", "Ariana", "Camila", "Paul", "Ferndando", "Ulvio", "Ufredo", "Mauricio", "Abby", "Bianca", "Clarissa", "Maria", "Ezequiel", "Luis", "Pablo", "Wilson", "Ximena", "Sofia", "Tomas", "Marcos", "Jhonson", "John", "Hector", "Humberto", "Alfredo", "Steven", "Enrique", "Cristopher", "Santiago", "Esteban", "Rostin"};
        String[] apellidos = {"Lorenzo", "Montero", "Hidalgo", "Mora", "Romero", "Valarezo", "Ramirez", "Gonzalez", "Fernandez", "Sanchez", "Largo", "Castillo", "Castro", "Diaz", "Valle", "Largo", "Romero", "Bravo", "Dominguez", "Garcia", "Cruz", "Marquez", "Vargas", "Murillo", "Arroyo", "Salazar", "Ordoñez", "Quishpe", "Lopez", "Gimenez", "Alvarado", "Hernandez", "Alvarez", "Silva", "Muñoz", "Nuñez", "Ortega", "Encarnacion", "Cortez", "Flores", "Tapia", "Molina", "Cueva", "Reyes", "Navarro", "Pachacama", "Lince", "Ochoa", "Atience", "Campoverde", "Gimenez", "Guaman", "Espejo", "Benitez", "Vicente", "Roman", "Cedillo"};
        for (int i = getSize(); i < n; i++) {
            Integer cedula = Integer.valueOf(((int) (Math.random() * 1000000) + 11000000));
            String nombre = "";
            String apellido = "";
            int x1 = (int) (Math.random() * (nombres.length));
            int x2 = (int) (Math.random() * (apellidos.length));
            System.out.println(x1 + " : " + x2);
            nombre += " " + nombres[x1];
            apellido += " " + apellidos[x2];
            int aux = (int) (Math.random() * 4 + 1);
            Integer celular = 0;
            switch (aux) {
                case 1:
                    celular = 30656;

                    break;
                case 2:
                    celular = 5556456;

                    break;
                case 3:
                    celular = 75464565;

                    break;
                case 4:
                    celular = 12546465;

                    break;
            }
            lPersonas.insertarAlFinal(new Persona(nombre, apellido, cedula, celular));
        }
    }

}
