/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos;

import Algoritmos.AlgBase;
import java.util.ArrayList;
import modelo.Particion;
import modelo.Proceso;
import modelo.Recurso;

/**
 *
 * @author Tita
 */
public class SJF extends AlgBase {

    public SJF(Recurso CPU, Recurso ES1, Recurso ES2, int cantrafaga, ArrayList<Proceso> colaListo, ArrayList<Proceso> colaBloqueado1, ArrayList<Proceso> colaBloqueado2, Integer procesosVivos, String tipopart, ArrayList<Particion> memoriaVariable, ArrayList<Particion> particiones, Proceso procesoCPU) {
        super(CPU, ES1, ES2, cantrafaga, colaListo, colaBloqueado1, colaBloqueado2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);
    }
    @Override
    public void ejecutar(){
         if (CPU.estado()){
                consumir();
        }else{
            ordenarCLporCPU();
            cargarCPUconCL();
        }
        controlRafaga();
    }
    
    /*public void ordenarCLporCPU(){
        ArrayList<Proceso> colaOrdenada = new ArrayList<Proceso>();
        while(!colaListo.isEmpty()){
            int mayor = 0;        
            Proceso aux = null;
            for(Proceso x: colaListo){
                if(x.getCpu1() == 0){
                    if(x.getCpu2() == 0){
                        if(x.getCpu3() > mayor){
                            aux = x;
                            mayor = x.getCpu3();
                        }
                    }else{
                        if(x.getCpu2() > mayor){
                            aux = x;
                            mayor = x.getCpu2();
                        }
                    }
                }else{
                    if(x.getCpu1() > mayor){
                        aux = x;
                        mayor = x.getCpu1();
                    }
                }
            }
            colaOrdenada.add(0,aux);
            colaListo.remove(aux);
        }
        colaListo = colaOrdenada;
    }*/
    
}
