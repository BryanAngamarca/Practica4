/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.ListaEnlazadaServices;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import javax.swing.table.AbstractTableModel;
import Modelo.Persona;

/**
 *
 * @author LENOVO LEGION 5
 */
public class TablaPersonas extends AbstractTableModel {

    ListaEnlazadaServices<Persona> lp = new ListaEnlazadaServices<>();

    public TablaPersonas(ListaEnlazadaServices<Persona> lc) {
        this.lp = lc;
    }

    @Override
    public int getRowCount() {
        return lp.getSize();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nombres";
            case 1:
                return "Apellido";
            case 2:
                return "Cedula";
            case 3:
                return "Celular";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona p = lp.obtenerDato(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getNombre();
            case 1:
                return p.getApellido();
            case 2:
                return p.getCedula();
            case 3:
                return p.getNumcelular();
            default:
                return null;
        }
    }

}
