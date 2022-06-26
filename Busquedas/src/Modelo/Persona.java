/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */
public class Persona {

    String nombre;
    String apellido;
    Integer cedula;
    Integer numcelular;

    public Persona(String nombre, String apellido, Integer cedula, Integer numcelular) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.numcelular = numcelular;
    }

    Persona() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correo
     */
    /**
     * @return the cedula
     */
    public Integer getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the numcelular
     */
    public Integer getNumcelular() {
        return numcelular;
    }

    /**
     * @param numcelular the numcelular to set
     */
    public void setNumcelular(Integer numcelular) {
        this.numcelular = numcelular;
    }

}
