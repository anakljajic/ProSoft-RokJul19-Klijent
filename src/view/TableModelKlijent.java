/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.Stado;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author anakl
 */
public class TableModelKlijent extends AbstractTableModel {

    List<Stado> stada;
    String[] columnNames = new String[]{"Zivotinja", "Autohtona", "Subvencija po grlu", "Broj grla", "Iznos subvencije"};

    public TableModelKlijent(List<Stado> stada) {
        this.stada = stada;
    }

    @Override
    public int getRowCount() {
        return stada.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Stado s = stada.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getZivotinja().getNaziv();
            case 1:
                return s.getZivotinja().getAutohtonaVrsta();
            case 2:
                return s.getZivotinja().getSubvencijaPoGrlu();
            case 3:
                return s.getBrojGrla();
            case 4:
                return s.getIznosSubvencije();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void azuriraj(List<Stado> stada) {
        this.stada = stada;
        fireTableDataChanged();
    }

    public BigDecimal vratiUkupnoSubvencija() {
        BigDecimal subv = new BigDecimal(0);
        for (Stado stado : stada) {
            subv = subv.add(stado.getIznosSubvencije());
        }

        return subv;
    }
}
