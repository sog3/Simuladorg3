/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos;

import java.util.ArrayList;
import modelo.Particion;
import modelo.Proceso;
import modelo.Recurso;

/**
 *
 * @author Tita
 */
public class RoundRobin extends AlgBase{

    public int quantum ;

    public RoundRobin(int quantum, Recurso CPU, Recurso ES1, Recurso ES2, int cantrafaga, ArrayList<Proceso> colaListo, ArrayList<Proceso> colaBloqueado1, ArrayList<Proceso> colaBloqueado2, Integer procesosVivos, String tipopart, ArrayList<Particion> memoriaVariable, ArrayList<Particion> particiones, Proceso procesoCPU) {
        super(CPU, ES1, ES2, cantrafaga, colaListo, colaBloqueado1, colaBloqueado2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);
        this.quantum = quantum;
    }
    
    @Override
    public void ejecutar(){
         if (CPU.estado()){
                consumir();
                quantum--;
        }else{  
            cargarCPUconCL();
            //falta asignarle alguvalor a quantum
        }
        controlRafaga();
    }
    
    
    
}
