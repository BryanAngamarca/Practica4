/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.utiles.NodoLista;
import Controlador.tda.lista.exception.PosicionException;
import Controlador.utiles.Utilidades;
import Controlador.utiles.TipoOrdenacion;
import static Controlador.utiles.Utilidades.getMethod;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sebastian
 */
//E   T    K   V
//E = T
@XmlRootElement

public class ListaEnlazada<E> {

    private NodoLista<E> cabecera;

    private Integer size;

    @XmlElement
    public NodoLista<E> getCabecera() {
        return cabecera;
    }

    public void setCabecera(NodoLista<E> cabecera) {
        this.cabecera = cabecera;
    }

    /**
     * Constructor de la clase se inicializa la lista en null y el tamanio en 0
     */
    public ListaEnlazada() {
        cabecera = null;
        size = 0;
    }

    /**
     * Permite ver si la lista esta vacia
     *
     * @return Boolean true si esta vacia, false si esta llena
     */
    public Boolean estaVacia() {
        return cabecera == null;
    }

    private void insertar(E dato) {
        NodoLista<E> nuevo = new NodoLista<>(dato, null);
        if (estaVacia()) {
            cabecera = nuevo;
        } else {
            NodoLista<E> aux = cabecera;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        size++;
    }

    public void insertarCabecera(E dato) {
        if (estaVacia()) {
            insertar(dato);
        } else {
            NodoLista<E> nuevo = new NodoLista<>(dato, null);

            nuevo.setSiguiente(cabecera);
            cabecera = nuevo;
            size++;
        }
    }

    public void insertar(E dato, Integer pos) throws PosicionException {
        //lista size = 1
        if (estaVacia()) {
            insertar(dato);
        } else if (pos >= 0 && pos < size) {
            NodoLista<E> nuevo = new NodoLista<>(dato, null);
            if (pos == (size - 1)) {
                insertar(dato);

            } else {

                NodoLista<E> aux = cabecera;
                for (int i = 0; i < pos - 1; i++) {
                    aux = aux.getSiguiente();
                }
                NodoLista<E> siguiente = aux.getSiguiente();
                aux.setSiguiente(nuevo);
                nuevo.setSiguiente(siguiente);
                size++;
            }

        } else {
            throw new PosicionException("Error en insertar: No existe la posicion dada");
        }
    }

    public void imprimir() {
        System.out.println("**************************");
        NodoLista<E> aux = cabecera;
        for (int i = 0; i < getSize(); i++) {
            System.out.print(aux.getDato().toString() + "\t");
            aux = aux.getSiguiente();
        }
        System.out.println("\n" + "**************************");
    }

    public Integer getSize() {
        return size;
    }

    /**
     * Metodo que permite obtener un dato segun la posicion
     *
     * @param pos posicion en la lista
     * @return Elemento
     */
    public E obtenerDato(Integer pos) throws PosicionException {
        if (!estaVacia()) {
            if (pos >= 0 && pos < size) {
                E dato = null;
                if (pos == 0) {
                    dato = cabecera.getDato();
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getSiguiente();
                    }
                    dato = aux.getDato();
                }

                return dato;
            } else {
                throw new PosicionException("Error en obtener dato: No existe la posicion dada");
            }

        } else {
            throw new PosicionException("Error en obtener dato: La lista esta vacia, por endde no hay esa posicion");
        }
    }

    public E eliminarDato(Integer pos) throws PosicionException {
        E auxDato = null;
        if (!estaVacia()) {
            if (pos >= 0 && pos < size) {
                if (pos == 0) {
                    auxDato = cabecera.getDato();
                    cabecera = cabecera.getSiguiente();
                    size--;
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos - 1; i++) {
                        aux = aux.getSiguiente();
                    }
                    auxDato = aux.getDato();
                    NodoLista<E> proximo = aux.getSiguiente();
                    aux.setSiguiente(proximo.getSiguiente());
                    size--;
                }
                return auxDato;

            } else {
                throw new PosicionException("Error en eliminar dato: No existe la posicion dada");
            }

        } else {
            throw new PosicionException("Error en eliminar dato: La lista esta vacia, por endde no hay esa posicion");
        }
    }

    public void vaciar() {
        cabecera = null;
        size = 0;
        //en c utilizamos el free
        //malloc
    }

    public void modificarDato(Integer pos, E datoM) throws PosicionException {
        if (!estaVacia()) {
            if (pos >= 0 && pos < size) {
                // E dato = null;
                if (pos == 0) {
                    cabecera.setDato(datoM);
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getSiguiente();
                    }
                    aux.setDato(datoM);
                }

            } else {
                throw new PosicionException("Error en obtener dato: No existe la posicion dada");
            }

        } else {
            throw new PosicionException("Error en obtener dato: La lista esta vacia, por endde no hay esa posicion");
        }
    }

    public E[] toArray() {
        // E[] matriz = (E[]) (new Object[this.size]);
        Class<E> clazz = null;
        E[] matriz = null;
        if (this.size > 0) {
            matriz = (E[]) java.lang.reflect.Array.newInstance(cabecera.getDato().getClass(), this.size);
            NodoLista<E> aux = cabecera;
            for (int i = 0; i < this.size; i++) {
                matriz[i] = aux.getDato();
                //System.out.print(aux.getDato().toString() + "\t");
                aux = aux.getSiguiente();
            }
        }

        return matriz;
    }

    public ListaEnlazada<E> toList(E[] matriz) {
        //E[] matriz = (E[]) (new Object[this.size]);
        this.vaciar();
        for (int i = 0; i < matriz.length; i++) {
            this.insertar(matriz[i]);
        }
        return this;
    }

    public ListaEnlazada<E> ordenar_seleccion(String atributo, TipoOrdenacion tipoOrdenacion) throws Exception {
        Class<E> clazz = null;
        E[] matriz = null;
        if (size > 0) {
            matriz = toArray();
            E t = null;
            clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
            Boolean isObject = Utilidades.isObject(clazz);//si es objeto
            Integer i, j, k = 0;
            Integer n = matriz.length;
            Integer cont = 0;
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = matriz[i];
                for (j = i + 1; j < n; j++) {
                    if (isObject) {
                        Field field = Utilidades.getField(atributo, clazz);
                        Method method = getMethod("get" + Utilidades.capitalizar(atributo), t.getClass());
                        Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[j].getClass());
                        Object[] aux = evaluaCambiarDato(field.getType(), t, matriz[j], method, method1, tipoOrdenacion, j, matriz);
                        if (aux[0] != null) {
                            t = (E) aux[0];
                            k = (Integer) aux[1];
                        }
                    } else {
                        Object[] aux = evaluaCambiarDatoNoObjeto(clazz, t, matriz[j], tipoOrdenacion, j, matriz);
                        if (aux[0] != null) {
                            t = (E) aux[0];
                            k = (Integer) aux[1];
                        }
                    }

                }
                matriz[k] = matriz[i];
                matriz[i] = t;
            }
        }
        if (matriz != null) {
            toList(matriz);
        }
        return this;
    }

    /*
     public ListaEnlazada<E> burbuja(String atributo, TipoOrdenacion tipoOrdenacion) throws PosicionException, Exception {
     Class<E> clazz = null;
     E[] matriz = null;
     if (size > 0) {
     clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
     Boolean isObject = Utilidades.isObject(clazz);//si es objeto
     System.out.println("TRANFORMANDO A MATRIZ");
     matriz = toArray();
     if (isObject) {
     for (int i = matriz.length; i > 1; i--) {
     for (int j = 0; j < i - 1; j++) {
     //E auxJ = this.obtenerDato(j);
     //E auxJ1 = this.obtenerDato(j + 1);//getApellido
     E auxJ = matriz[j];
     E auxJ1 = matriz[j + 1];//getApellido
     Field field = Utilidades.getField(atributo, clazz);
     Method method = getMethod("get" + Utilidades.capitalizar(atributo), auxJ.getClass());
     Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), auxJ1.getClass());
     //llamar a utilidades
     //if (field.getType().getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
     evaluaCambiarDato(field.getType(), auxJ, auxJ1, method, method1, tipoOrdenacion, j, matriz);

     }
     }
     } else {
     System.out.println("METODO BURBUJA");
     for (int i = matriz.length; i > 1; i--) {
     for (int j = 0; j < i - 1; j++) {
     //E auxJ = this.obtenerDato(j);
     //E auxJ1 = this.obtenerDato(j + 1);//getApellido
     E auxJ = matriz[j];
     E auxJ1 = matriz[j + 1];
     evaluaCambiarDatoNoObjeto(clazz, auxJ, auxJ1, tipoOrdenacion, j, matriz);
     }
     }
     }

     }
     System.out.println("TRANFORMANDO A LISTA");
     if (matriz != null) {
     toList(matriz);
     }
     return this;
     }
     */
    /**
     * Sirve para realizar el intercambio del metodo burbuja
     *
     * @param j Posicion
     * @throws Exception Excepciones de tipo PosicionExcepcion
     */
    /* private void cambioBurbuja(Integer j, E[] matriz) throws Exception {
     E aux = matriz[j];
     matriz[j] = matriz[j + 1];
     matriz[j + 1] = aux;
     //E aux = this.obtenerDato(j);
     //this.modificarDato(j, this.obtenerDato(j + 1));
     //this.modificarDato(j + 1, aux);
     //  System.out.println("Cambio");
     }*/
    /**
     * Permite hacer el cambio con datos que no son objetos
     *
     * @param clazz El tipo de clase q permite identificar q tipo de dato es
     * @param auxJ Dato en la posicion J
     * @param auxJ1 Dato en la posicion J + 1
     * @param tipoOrdenacion El tipo de ordenacion si es Ascendente o
     * Descendente
     * @param j Posicion
     * @throws Exception
     */
    private Object[] evaluaCambiarDatoNoObjeto(Class clazz, E auxJ, E auxJ1, TipoOrdenacion tipoOrdenacion, Integer j, E[] matriz) throws Exception {
        Object aux[] = new Object[2];//aux[0];--->null
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            // Number datoJ = (Number) auxJ;
            // Number datoJ1 = (Number) auxJ1;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((((Number) auxJ).doubleValue() > ((Number) auxJ1).doubleValue())) {
                    aux[0] = auxJ1;
                    aux[1] = j;
                    //  cambioBurbuja(j, matriz);
                }
            } else {
                if ((((Number) auxJ).doubleValue() < ((Number) auxJ1).doubleValue())) {
                    // cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) auxJ;
            String datoJ1 = (String) auxJ1;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) auxJ;
            Character datoJ1 = (Character) auxJ1;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if (datoJ < datoJ1) {
                    //cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        }
        return aux;
    }

    /**
     * Permite hacer el cambio con datos que son objetos del modelo
     *
     * @param clazz El tipo de clase del atributo
     * @param auxJ dato en la posicion J
     * @param auxJ1 dato en la posicion J + 1
     * @param method El metodo get de J
     * @param method1 El metodo get de J + 1
     * @param tipoOrdenacion El tipo de ordenacion si es Ascendente o
     * Descendente
     * @param j posicion
     * @throws Exception
     */
    private Object[] evaluaCambiarDato(Class clazz, E auxJ, E auxJ1, Method method, Method method1, TipoOrdenacion tipoOrdenacion, Integer j, E[] matriz) throws Exception {
        Object aux[] = new Object[2];
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) method.invoke(auxJ);
            Number datoJ1 = (Number) method1.invoke(auxJ1);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.doubleValue() > datoJ1.doubleValue())) {
                    // cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if ((datoJ.doubleValue() < datoJ1.doubleValue())) {
                    //    cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) method.invoke(auxJ);
            String datoJ1 = (String) method1.invoke(auxJ1);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    //   cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    //  cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) method.invoke(auxJ);
            Character datoJ1 = (Character) method1.invoke(auxJ1);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    // cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            } else {
                if (datoJ < datoJ1) {
                    //  cambioBurbuja(j, matriz);
                    aux[0] = auxJ1;
                    aux[1] = j;
                }
            }

        }
        return aux;
    }

    /**
     * Metodo para busqueda secuencial
     *
     * @param atributo el atributo donde deseo buscar
     * @param dato El dato a buscar
     * @return ListaEnlazada<E> El listado de datos a buscar
     */
    public ListaEnlazada<E> buscar(String atributo, Object dato) throws Exception {
        Class<E> clazz = null;
        E[] matriz = null;
        ListaEnlazada<E> resultado = new ListaEnlazada<>();
        if (size > 0) {
            matriz = toArray();

            clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
            Boolean isObject = Utilidades.isObject(clazz);//si es objeto
            if (isObject) {
                Field field = Utilidades.getField(atributo, clazz);
//                Method method = getMethod("get" + Utilidades.capitalizar(atributo), field.getClass());

                for (int i = 0; i < matriz.length; i++) {
                    Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[i].getClass());
                    E aux = buscarDatoPosicionObjeto(i, matriz, field.getType(), dato, method1);
                    if (aux != null) {
                        resultado.insertar(aux);
                    }
                }
            } else {
                for (int i = 0; i < matriz.length; i++) {
                    E aux = buscarDatoPosicion(i, matriz, clazz, (E) dato);
                    if (aux != null) {
                        resultado.insertar(aux);
                    }
                }
            }

        }
        return resultado;
    }

    private boolean evaluaCambiarDato(Class clazz, Method method, Method method1, TipoOrdenacion tipoOrdenacion, Integer j, E dato, E[] matriz) throws Exception {
        boolean aux = false;
        if (Utilidades.isNumber(clazz)) {
            Number datoJ = (Number) method.invoke(dato);
            Number datoJ1 = (Number) method1.invoke(matriz[j]);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.doubleValue() > datoJ1.doubleValue())) {
                    // cambioBurbuja(j, matriz);
                    aux = true;
                }
            } else {
                if ((datoJ.doubleValue() < datoJ1.doubleValue())) {
                    //    cambioBurbuja(j, matriz);
                    aux = true;
                }
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) method.invoke(dato);
            String datoJ1 = (String) method1.invoke(matriz[j]);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    //   cambioBurbuja(j, matriz);
                    aux = true;
                }
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    //  cambioBurbuja(j, matriz);
                    aux = true;
                }
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) method.invoke(dato);
            Character datoJ1 = (Character) method1.invoke(matriz[j]);
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ > datoJ1) {
                    // cambioBurbuja(j, matriz);
                    aux = true;
                }
            } else {
                if (datoJ < datoJ1) {
                    //  cambioBurbuja(j, matriz);
                    aux = true;
                }
            }

        }
        return aux;
    }

    private boolean evaluaCambiarDatoNoObjeto(Class clazz, TipoOrdenacion tipoOrdenacion, Integer i, E dato, E[] matriz) throws Exception {
        boolean aux = false;
        if (Utilidades.isNumber(clazz)) {
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((((Number) matriz[i]).doubleValue() < ((Number) dato).doubleValue())) {
                    aux = true;
                    //  cambioBurbuja(j, matriz);
                }
            } else {
                if ((((Number) matriz[i]).doubleValue() > ((Number) dato).doubleValue())) {
                    // cambioBurbuja(j, matriz);
                    aux = true;
                }
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) matriz[i];
            String datoJ1 = (String) dato;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) < 0)) {
                    //cambioBurbuja(j, matriz);
                    aux = true;
                }
            } else {
                if ((datoJ.toLowerCase().compareTo(datoJ1.toLowerCase()) > 0)) {
                    //cambioBurbuja(j, matriz);
                    aux = true;
                }
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) matriz[i];
            Character datoJ1 = (Character) dato;
            if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                if (datoJ < datoJ1) {
                    //cambioBurbuja(j, matriz);
                    aux = true;
                }
            } else {
                if (datoJ > datoJ1) {
                    //cambioBurbuja(j, matriz);
                    aux = true;
                }
            }

        }
        return aux;
    }
    public ListaEnlazada<E> quicksort(String atributo, TipoOrdenacion tipoOrdenacion, int izq, int der) throws Exception {
        Class<E> clazz = (Class<E>) cabecera.getDato().getClass();
        Boolean isObject = Utilidades.isObject(clazz);
        Field field = Utilidades.getField(atributo, clazz);
        E[] matriz = quickSort(atributo, tipoOrdenacion, izq, der, toArray(), clazz, isObject, field);
        return toList(matriz);
    }

    public E[] quickSort(String atributo, TipoOrdenacion tipoOrdenacion, int izq, int der, E[] matriz, Class<E> clazz, boolean isObject, Field field) throws Exception {
        if (size > 0) {
            E pivote = matriz[izq]; // tomamos primer elemento como pivote
            int i = izq;         // i realiza la búsqueda de izquierda a derecha
            int j = der;         // j realiza la búsqueda de derecha a izquierda
            while (i < j) {
                int[] aux = evaluarCambioQsort(atributo, tipoOrdenacion, matriz, izq, der, isObject, clazz, field);
                i = aux[0];
                j = aux[1];
                if (i < j) {
                    E t = matriz[i];
                    matriz[i] = matriz[j];
                    matriz[j] = t;
                }
            }
            matriz[izq] = matriz[j];      // se coloca el pivote en su lugar de forma que tendremos                                    
            matriz[j] = pivote;      // los menores a su izquierda y los mayores a su derecha

            if (izq < j - 1) {
                quickSort(atributo, tipoOrdenacion, izq, j - 1, matriz, clazz, isObject, field);          // ordenamos subarray izquierdo
            }
            if (j + 1 < der) {
                quickSort(atributo, tipoOrdenacion, j + 1, der, matriz, clazz, isObject, field);          // ordenamos subarray derecho
            }
        }
        return matriz;
    }

    public int[] evaluarCambioQsort(String atributo, TipoOrdenacion tipoOrdenacion, E[] matriz, int izq, int der, boolean isObject, Class<E> clazz, Field field) throws Exception {
        int i = izq;
        int j = der;
        E pivote = matriz[izq];
        if (isObject) {
            Method method = getMethod("get" + Utilidades.capitalizar(atributo), matriz[i].getClass());
            Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[j].getClass());
            Method methodp = getMethod("get" + Utilidades.capitalizar(atributo), matriz[izq].getClass());
            if (Utilidades.isNumber(field.getType())) {
                if (tipoOrdenacion.toString().equalsIgnoreCase("ASCENDENTE")) {
                    while ((((Number) method.invoke(matriz[i])).doubleValue() <= ((Number) (methodp.invoke(matriz[izq]))).doubleValue() && i < j)) {
                        i++;
                    }
                    while ((((Number) method1.invoke(matriz[j])).doubleValue() > ((Number) (methodp.invoke(matriz[izq]))).doubleValue())) {
                        j--;
                    }
                } else {
                    while ((((Number) method.invoke(matriz[i])).doubleValue() >= ((Number) (methodp.invoke(matriz[izq]))).doubleValue() && i < j)) {
                        i++;
                    }
                    while ((((Number) method1.invoke(matriz[j])).doubleValue() < ((Number) (methodp.invoke(matriz[izq]))).doubleValue())) {
                        j--;
                    }
                }
            } else if (Utilidades.isString(field.getType())) {
                if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                    while ((((String) method.invoke(matriz[i])).toLowerCase().compareTo(((String) methodp.invoke(matriz[izq])).toLowerCase()) <= 0) && i < j) {
                        i++;
                    }
                    while ((((String) method1.invoke(matriz[j])).toLowerCase().compareTo(((String) methodp.invoke(matriz[izq])).toLowerCase()) > 0)) {
                        j--;
                    }
                } else {
                    while ((((String) method.invoke(matriz[i])).toLowerCase().compareTo(((String) methodp.invoke(matriz[izq])).toLowerCase()) >= 0) && i < j) {
                        i++;
                    }
                    while ((((String) method1.invoke(matriz[j])).toLowerCase().compareTo(((String) methodp.invoke(matriz[izq])).toLowerCase()) < 0)) {
                        j--;
                    }
                }

            } else if (Utilidades.isCharacter(field.getType())) {
                if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                    while ((Character) method.invoke(matriz[i]) <= (Character) methodp.invoke(matriz[izq]) && i < j) {
                        i++;
                    }
                    while ((Character) method1.invoke(matriz[j]) > (Character) methodp.invoke(matriz[izq])) {
                        j--;
                    }
                } else {
                    while ((Character) method.invoke(matriz[i]) >= (Character) methodp.invoke(matriz[izq]) && i < j) {
                        i++;
                    }
                    while ((Character) method1.invoke(matriz[j]) < (Character) methodp.invoke(matriz[izq])) {
                        j--;
                    }
                }
            }
        } else {
            if (Utilidades.isNumber(clazz)) {
                if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                    while ((((Number) matriz[i]).doubleValue() <= ((Number) (pivote)).doubleValue() && i < j)) {
                        i++;
                    }
                    while ((((Number) matriz[j]).doubleValue() > ((Number) (pivote)).doubleValue())) {
                        j--;
                    }
                } else {
                    while ((((Number) matriz[i]).doubleValue() >= ((Number) (pivote)).doubleValue() && i < j)) {
                        i++;
                    }
                    while ((((Number) matriz[j]).doubleValue() < ((Number) (pivote)).doubleValue())) {
                        j--;
                    }
                }
            } else if (Utilidades.isString(clazz)) {
                if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                    while ((((String) matriz[i]).toLowerCase().compareTo(((String) matriz[izq]).toLowerCase()) <= 0) && i < j) {
                        i++;
                    }
                    while ((((String) matriz[j]).toLowerCase().compareTo(((String) matriz[izq]).toLowerCase()) > 0)) {
                        j--;
                    }
                } else {
                    while ((((String) matriz[i]).toLowerCase().compareTo(((String) matriz[izq]).toLowerCase()) >= 0) && i < j) {
                        i++;
                    }
                    while ((((String) matriz[j]).toLowerCase().compareTo(((String) matriz[izq]).toLowerCase()) < 0)) {
                        j--;
                    }
                }
            } else if (Utilidades.isCharacter(clazz)) {
                Character datoJ = (Character) matriz[i];
                Character datoJ1 = (Character) matriz[j];
                if (tipoOrdenacion.toString().equalsIgnoreCase(TipoOrdenacion.ASCENDENTE.toString())) {
                    while ((Character) matriz[i] <= (Character) pivote && i < j) {
                        i++;
                    }
                    while ((Character) matriz[j] > (Character) pivote) {
                        j--;
                    }
                } else {
                    while ((Character) matriz[i] >= (Character) pivote && i < j) {
                        i++;
                    }
                    while ((Character) matriz[j] < (Character) pivote) {
                        j--;
                    }
                }

            }
        }
        return new int[]{i, j};

    }

    public ListaEnlazada<E> ordenar_Shell(String atributo, TipoOrdenacion tipoOrdenacion) throws Exception {
        Class<E> clazz = null;
        E[] matriz = null;
        if (size > 0) {
            matriz = toArray();
            clazz = (Class<E>) cabecera.getDato().getClass();//primitivo, Dato envolvente, Object
            Boolean isObject = Utilidades.isObject(clazz);//si es objeto
            Integer i, salto;
            boolean cambios;
            for (salto = matriz.length / 2; salto != 0; salto /= 2) {
                cambios = true;
                while (cambios) {
                    cambios = false;
                    for (i = salto; i < matriz.length; i++) {
                        if (isObject) {
                            Field field = Utilidades.getField(atributo, clazz);
                            Method method = getMethod("get" + Utilidades.capitalizar(atributo), matriz[i - salto].getClass());
                            Method method1 = getMethod("get" + Utilidades.capitalizar(atributo), matriz[i].getClass());
                            boolean aux = evaluaCambiarDato(field.getType(), method, method1, tipoOrdenacion, i, matriz[i - salto], matriz);
                            if (aux) {
                                E t = matriz[i];
                                matriz[i] = matriz[i - salto];
                                matriz[i - salto] = t;
                                cambios = true;
                            }
                        } else {
                            boolean aux = evaluaCambiarDatoNoObjeto(clazz, tipoOrdenacion, i, matriz[i - salto], matriz);
                            if (aux) {
                                E t = matriz[i];
                                matriz[i] = matriz[i - salto];
                                matriz[i - salto] = t;
                                cambios = true;
                            }
                        }
                    }
                }
            }
        }
        if (matriz != null) {
            return toList(matriz);
        }
        return null;
    }

    private E buscarDatoPosicion(Integer j, E[] matriz, Class<E> clazz, E dato) throws Exception {
        E aux = null;
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) dato;
            Number datoJ1 = (Number) matriz[j];
            if (datoJ.doubleValue() == datoJ1.doubleValue()) {
                aux = (E) datoJ1;
            }
        } else if (Utilidades.isString(clazz)) {
            String datoJ = (String) dato;
            String datoJ1 = (String) matriz[j];

            if (datoJ1.toLowerCase().startsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().endsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().equalsIgnoreCase(datoJ.toLowerCase())) {
                //cambioBurbuja(j, matriz);
                aux = (E) matriz[j];
            }

        } else if (Utilidades.isCharacter(clazz)) {
            Character datoJ = (Character) dato;
            Character datoJ1 = (Character) matriz[j];
            if (datoJ.charValue() == datoJ1.charValue()) {
                aux = (E) matriz[j];
            }

        }
        return aux;
    }

    private E buscarDatoPosicionObjeto(Integer j, E[] matriz, Class clazz, Object dato, Method method1) throws Exception {
        E aux = null;
        if (clazz.getSuperclass().getSimpleName().equalsIgnoreCase("Number")
                && dato.getClass().getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            Number datoJ = (Number) dato;
            Number datoJ1 = (Number) method1.invoke(matriz[j]);
            if (datoJ.doubleValue() == datoJ1.doubleValue()) {
                aux = (E) matriz[j];
            }
        } else if (Utilidades.isString(clazz) && Utilidades.isString(dato.getClass())) {
            String datoJ = (String) dato;
            String datoJ1 = (String) method1.invoke(matriz[j]);

            if (datoJ1.toLowerCase().startsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().endsWith(datoJ.toLowerCase())
                    || datoJ1.toLowerCase().equalsIgnoreCase(datoJ.toLowerCase())) {
                //cambioBurbuja(j, matriz);
                aux = (E) matriz[j];
            }

        } else if (Utilidades.isCharacter(clazz) && Utilidades.isCharacter(dato.getClass())) {
            Character datoJ = (Character) dato;
            Character datoJ1 = (Character) method1.invoke(matriz[j]);
            if (datoJ.charValue() == datoJ1.charValue()) {
                aux = (E) matriz[j];
            }

        }
        return aux;
    }

//    public static void main(String[] args) {
//
//        try {
//
////            ListaEnlazada<String> lista = new ListaEnlazada<>();
////            FileReader fr = new FileReader("datos" + File.separatorChar + "numero.txt");
////            BufferedReader entrada = new BufferedReader(fr);
////            String aux = entrada.readLine();
////            Integer cont = 0;
////            while (aux != null) {
////                //matriz[cont] = Integer.parseInt(aux);
////                //lista.insertar(aux);
////                aux = entrada.readLine();
////                cont++;
////            }
////            fr.close();
////            entrada.close();
//            ListaEnlazada<Factura> lista = new FacturaServicio().getListaArchivo().getLista();
//            lista.imprimir();
//            System.out.println("SELECCION");
//            lista.ordenar_seleccion("total", TipoOrdenacion.DESCENDENTE);
//            lista.imprimir();
//            ListaEnlazada<Factura> busAux = lista.buscar("total", 78.0);
//            System.out.println("--------------------------------------------------");
//            busAux.imprimir();
//            System.out.println("--------------------------------------------------");
////            ListaEnlazada<Double> lista1 = new ListaEnlazada<>();
////            lista1.insertar(9.3);
////            lista1.insertar(4.1);
////            lista1.insertar(1.0);
////            lista1.insertar(9.3);
////            lista1.insertar(4.0);
////            lista1.insertar(4.1);
////            lista1.ordenar_seleccion("", TipoOrdenacion.ASCENDENTE);
////            lista1.imprimir();
////            ListaEnlazada<Double> busqueda = lista1.buscar("", 4.0);
////            busqueda.imprimir();
//        } catch (Exception e) {
//            System.out.println("erro " + e);
//        }
//
//    }
}
