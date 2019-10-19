/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Tita
 */
public class Grafico {
    
    public static void main(String[] args){
     generarBarras();
    }
    
    public static void generarBarras(){
        try {
            DefaultCategoryDataset ds = new DefaultCategoryDataset();
            ds.addValue(10, "Proceso 01", "01");
            ds.addValue(20, "Proceso 02", "02");
            ds.addValue(15, "Proceso 03", "03");
            ds.addValue(15, "Proceso 04", "04");
            ds.addValue(15, "Proceso 05", "05");
            ds.addValue(15, "Proceso 06", "06");
            ds.addValue(15, "Proceso 07", "07");
            
            JFreeChart jf = ChartFactory.createBarChart("Procesos","PID","CPU",ds, PlotOrientation.VERTICAL, false,false, false);
            ChartFrame f = new ChartFrame("Procesos", jf);
            f.setSize(1000,600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        } catch (Exception e) {
        }
    }
    
}
