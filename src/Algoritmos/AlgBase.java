/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos;

import static java.time.Clock.system;
import java.util.ArrayList;
import modelo.Particion;
import modelo.Proceso;
import modelo.Recurso;

/**
 *
 * @author Tita
 */
public class AlgBase {
    public Recurso CPU;
    public Recurso ES1;
    public Recurso ES2;
    public Integer cantrafaga;
    public ArrayList<Proceso> colaListo;
  //private ArrayList<Proceso> colaNuevo;
    public ArrayList<Proceso> colaBloqueado1;
    public ArrayList<Proceso> colaBloqueado2;
    public Integer procesosVivos;
    public String tipopart;
    public ArrayList<Particion> memoriaVariable;
    public ArrayList<Particion> particiones;
    public int ts = 0;
    public int Time =0;
    
    public void ejecutar(){
    }

    public Recurso getCPU() {
        return CPU;
    }

    public void setCPU(Recurso CPU) {
        this.CPU = CPU;
    }

    public Recurso getES1() {
        return ES1;
    }

    public void setES1(Recurso ES1) {
        this.ES1 = ES1;
    }

    public Recurso getES2() {
        return ES2;
    }

    public void setES2(Recurso ES2) {
        this.ES2 = ES2;
    }

    public Integer getCantrafaga() {
        return cantrafaga;
    }

    public void setCantrafaga(Integer cantrafaga) {
        this.cantrafaga = cantrafaga;
    }

    public ArrayList<Proceso> getColaListo() {
        return colaListo;
    }

    public void setColaListo(ArrayList<Proceso> colaListo) {
        this.colaListo = colaListo;
    }

    public ArrayList<Proceso> getColaBloqueado1() {
        return colaBloqueado1;
    }

    public void setColaBloqueado1(ArrayList<Proceso> colaBloqueado1) {
        this.colaBloqueado1 = colaBloqueado1;
    }

    public ArrayList<Proceso> getColaBloqueado2() {
        return colaBloqueado2;
    }

    public void setColaBloqueado2(ArrayList<Proceso> colaBloqueado2) {
        this.colaBloqueado2 = colaBloqueado2;
    }

    public Integer getProcesosVivos() {
        return procesosVivos;
    }

    public void setProcesosVivos(Integer procesosVivos) {
        this.procesosVivos = procesosVivos;
    }

    public String getTipopart() {
        return tipopart;
    }

    public void setTipopart(String tipopart) {
        this.tipopart = tipopart;
    }

    public ArrayList<Particion> getMemoriaVariable() {
        return memoriaVariable;
    }

    public void setMemoriaVariable(ArrayList<Particion> memoriaVariable) {
        this.memoriaVariable = memoriaVariable;
    }

    public ArrayList<Particion> getParticiones() {
        return particiones;
    }

    public void setParticiones(ArrayList<Particion> particiones) {
        this.particiones = particiones;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int Time) {
        this.Time = Time;
    }

    public Proceso getProcesoCPU() {
        return procesoCPU;
    }

    public void setProcesoCPU(Proceso procesoCPU) {
        this.procesoCPU = procesoCPU;
    }
    
    public void ordenarCLporCPU(){
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
    }

    public AlgBase(Recurso CPU, Recurso ES1, Recurso ES2, int cantrafaga, ArrayList<Proceso> colaListo, ArrayList<Proceso> colaBloqueado1, ArrayList<Proceso> colaBloqueado2, Integer procesosVivos, String tipopart, ArrayList<Particion> memoriaVariable, ArrayList<Particion> particiones, Proceso procesoCPU) {
        this.CPU = CPU;
        this.ES1 = ES1;
        this.ES2 = ES2;
        this.cantrafaga = cantrafaga;
        this.colaListo = colaListo;
        this.colaBloqueado1 = colaBloqueado1;
        this.colaBloqueado2 = colaBloqueado2;
        this.procesosVivos = procesosVivos;
        this.tipopart = tipopart;
        this.memoriaVariable = memoriaVariable;
        this.particiones = particiones;
        this.procesoCPU = procesoCPU;
    }
    protected Proceso procesoCPU;
    
    
    public void cargarCPUconCL() {
        if ((colaListo.size()>0)&(!CPU.estado())){  //Si hay otro proceso en la cola de listo esperando, lo cargo en CPU
                        CPU.PID = colaListo.get(0).getIdproceso();
                        CPU.Tam = colaListo.get(0).getTamanio();
                        CPU.TA = colaListo.get(0).getTiempoarribo();
                        CPU.CPU1 = colaListo.get(0).getCpu1();
                        CPU.ES1 = colaListo.get(0).getEntsal1();
                        CPU.CPU2 = colaListo.get(0).getCpu2();
                        CPU.ES2 = colaListo.get(0).getEntsal2();
                        CPU.CPU3 = colaListo.get(0).getCpu3();
                        CPU.estado = true;
                        colaListo.remove(0);
                }
    }
    
    public void finalizarProceso(int PID){
        System.out.println("entre en finalizar proceso");
        ts += Time;
        if (tipopart == "Variable") {
            int pos=0;
            for (int i=0; i < memoriaVariable.size(); i++) {
               if((memoriaVariable.get(i).ProCargado)==(PID)){
                   memoriaVariable.get(i).ProCargado = 0;
                   memoriaVariable.get(i).libre = true;
                   CPU = new Recurso(0,0,0,0,0,0,0,0,true); //libera la CPU 
                   System.out.println(procesosVivos);
                   this.procesosVivos=this.procesosVivos - 1;
                   System.out.println(procesosVivos);
                   pos = i;
               }
            }
            if ((pos>0)&&(memoriaVariable.get(pos-1).isLibre())){
                memoriaVariable.get(pos).setTamPart((memoriaVariable.get(pos).getTamPart())+(memoriaVariable.get(pos-1).getTamPart()));
                memoriaVariable.remove(pos-1);
                pos--;
            }
            if ((pos<(memoriaVariable.size()-1))&&(memoriaVariable.get(pos+1).isLibre())){
                memoriaVariable.get(pos).setTamPart((memoriaVariable.get(pos).getTamPart())+(memoriaVariable.get(pos+1).getTamPart()));
                memoriaVariable.remove(pos+1);
            }
            if(memoriaVariable.get(memoriaVariable.size()-1).getTamPart()==0){
                memoriaVariable.remove(memoriaVariable.size()-1);
            }
            
        }else{
           for (Particion x: particiones) {
               if((x.ProCargado)==(PID)){
                   x.ProCargado = 0;
                   x.libre = true;
                   CPU = new Recurso(0,0,0,0,0,0,0,0,true);
                   procesosVivos--;
               }
           }
        }
    }
    
    public void controlRafaga(){
        if ((CPU.CPU1 == 0)&(CPU.estado())) {   
            if(cantrafaga == 1){ //Pregunta si eligio trabajar con una sola rafaga de CPU, 
                finalizarProceso(CPU.PID);  //en ese caso cuando CPU1 llegue a cero el proceso finaliza
            } else { 
                    if(CPU.ES1>0) {//Si tiene 2 rafagas de CPU y la primera ya está en cero, entra acá
                        procesoCPU = new Proceso(CPU.PID,CPU.TA,CPU.Tam,CPU.CPU1,CPU.ES1,CPU.CPU2,CPU.ES2,CPU.CPU3);
                        CPU = new Recurso(0,0,0,0,0,0,0,0,true);
                        colaBloqueado1.add(procesoCPU);
                    }else{
                        if(CPU.CPU2 ==0){
                                colaBloqueado2.add(procesoCPU);
                                CPU = new Recurso(0,0,0,0,0,0,0,0,true);
                            if(cantrafaga == 2){
                                finalizarProceso(CPU.PID);  //en ese caso cuando CPU1 llegue a cero el proceso finaliza
                            } else{
                                if(CPU.ES2>0){
                                    procesoCPU = new Proceso(CPU.PID,CPU.TA,CPU.Tam,CPU.CPU1,CPU.ES1,CPU.CPU2,CPU.ES2,CPU.CPU3);
                                }else{
                                    if(CPU.CPU3==0){
                                        finalizarProceso(CPU.PID);  //en ese caso cuando CPU1 llegue a cero el proceso finaliza
                                    }
                                }
                            }
                        }
                    }
            } 
        } 
    }
    
    public void consumir(){
        switch (cantrafaga){
                        case 1: CPU.CPU1--;break;
                        case 2: if (CPU.CPU1 == 0){
                                    CPU.CPU2--;
                                }else{
                                   CPU.CPU1--; 
                                }break;
                        case 3: if (CPU.CPU1 > 0){
                                    CPU.CPU1--;
                                }else{
                                    if(CPU.CPU2>0){
                                       CPU.CPU2--;
                                    }else{
                                    CPU.CPU3--;
                                    }
                                }break;
                    }
    }
}
