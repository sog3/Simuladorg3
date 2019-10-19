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
public class SRTF extends AlgBase{

    public SRTF(Recurso CPU, Recurso ES1, Recurso ES2, int cantrafaga, ArrayList<Proceso> colaListo, ArrayList<Proceso> colaBloqueado1, ArrayList<Proceso> colaBloqueado2, Integer procesosVivos, String tipopart, ArrayList<Particion> memoriaVariable, ArrayList<Particion> particiones, Proceso procesoCPU) {
        super(CPU, ES1, ES2, cantrafaga, colaListo, colaBloqueado1, colaBloqueado2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);
    }
    
    @Override
    public void ejecutar(){
         if (CPU.estado()){
                consumir();
            ordenarCLporCPU();
        }else{  
            ordenarCLporCPU();
            cargarCPUconCL();
        }
        controlRafaga();
        if((!colaListo.isEmpty())&&(CPU.estado())){
                    ordenarCLporCPU();
                    Proceso pro_menor = colaListo.get(0);
                    int tiempoProceso = menorTiempoCpuProceso(pro_menor); //obtento el tiempo de CPU del Proceso mas quequeño
                    //creo una clase proceso auxiliar y almaceno los datos del proceso actual en CPU
                    Proceso pro_CPU = new Proceso(CPU.PID,CPU.TA,CPU.Tam,CPU.CPU1,CPU.ES1,CPU.CPU2,CPU.ES2,CPU.CPU3);
                    //obtento el tiempo de CPU del proceso en CPU
                    int tiempoCPU = menorTiempoCpuProceso(pro_CPU);
                    if(tiempoProceso < tiempoCPU){ 
                        //pregunta si el tiempo del proceso que arriva es menor al tiempo del proceso que esta en CPU
                        //el proceso que está primero en la cola de listo se apropia de CPU
                        desalojarProcesoDeCPU();
                        cargarCPUconCL();
                    }
        }
    }
    
    public int menorTiempoCpuProceso(Proceso aux){
        //procedimiento para obtener el menor tiempo de cpu del proceso
        int menor;
        if (aux.cpu1 == 0){
            if (aux.cpu2 == 0){
                menor = aux.cpu3;
            }else{
                menor = aux.cpu2;
            }
        }else{
            menor = aux.cpu1;
        }
        return menor;
    }
    
    public void desalojarProcesoDeCPU(){
        Proceso pro = new Proceso(CPU.PID,CPU.TA,CPU.Tam,CPU.CPU1,CPU.ES1,CPU.CPU2,CPU.ES2,CPU.CPU3);
        colaListo.add(pro);
        CPU.estado = true;
    }
    
       /* public void ordenarCLporCPU(){
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
