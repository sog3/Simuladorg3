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
        while(!this.colaListo.isEmpty()){
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
            this.colaListo.remove(aux);
        }
        this.colaListo = colaOrdenada;
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
        if ((this.colaListo.size()>0)&(!this.CPU.estado())){  //Si hay otro proceso en la cola de listo esperando, lo cargo en CPU
                        this.CPU.PID = this.colaListo.get(0).getIdproceso();
                        this.CPU.Tam = this.colaListo.get(0).getTamanio();
                        this.CPU.TA = this.colaListo.get(0).getTiempoarribo();
                        this.CPU.CPU1 = this.colaListo.get(0).getCpu1();
                        if (this.cantrafaga==2){
                        this.CPU.ES1 = this.colaListo.get(0).getEntsal1();
                        this.CPU.CPU2 = this.colaListo.get(0).getCpu2();}
                        else if (this.cantrafaga==3){
                        this.CPU.ES1 = this.colaListo.get(0).getEntsal1();
                        this.CPU.CPU2 = this.colaListo.get(0).getCpu2();
                        this.CPU.ES2 = this.colaListo.get(0).getEntsal2();
                        this.CPU.CPU3 = this.colaListo.get(0).getCpu3();}
                        this.CPU.estado = true;
                        this.colaListo.remove(0);
                }
    }
    
    public void finalizarProceso(int PID){
        System.out.println("entre en finalizar proceso");
        ts += Time;
        if (this.tipopart == "Variable") {
            int pos=0;
            for (int i=0; i < this.memoriaVariable.size(); i++) {
               if((this.memoriaVariable.get(i).ProCargado)==(PID)){
                   this.memoriaVariable.get(i).ProCargado = 0;
                   this.memoriaVariable.get(i).libre = true;
                   this.CPU = new Recurso(0,0,0,0,0,0,0,0,true); //libera la CPU 
                   System.out.println(procesosVivos);
                   this.procesosVivos=this.procesosVivos - 1;
                   System.out.println("PROCESOS VIVOS"+this.procesosVivos);
                   pos = i;
               }
            }
            if ((pos>0)&&(this.memoriaVariable.get(pos-1).isLibre())){
                this.memoriaVariable.get(pos).setTamPart((this.memoriaVariable.get(pos).getTamPart())+(this.memoriaVariable.get(pos-1).getTamPart()));
                this.memoriaVariable.remove(pos-1);
                pos--;
            }
            if ((pos<(this.memoriaVariable.size()-1))&&(this.memoriaVariable.get(pos+1).isLibre())){
                this.memoriaVariable.get(pos).setTamPart((this.memoriaVariable.get(pos).getTamPart())+(this.memoriaVariable.get(pos+1).getTamPart()));
                this.memoriaVariable.remove(pos+1);
            }
            if(this.memoriaVariable.get(this.memoriaVariable.size()-1).getTamPart()==0){
                this.memoriaVariable.remove(this.memoriaVariable.size()-1);
            }
            
        }else{
           for (Particion x: particiones) {
               if((x.ProCargado)==(PID)){
                   x.ProCargado = 0;
                   x.libre = true;
                   this.CPU = new Recurso(0,0,0,0,0,0,0,0,true);
                   this.procesosVivos--;
               }
           }
        }
    }
    
    public void controlRafaga(){
        if ((this.CPU.CPU1 == 0)&(this.CPU.estado())) {   
            if(this.cantrafaga == 1){ //Pregunta si eligio trabajar con una sola rafaga de CPU, 
                finalizarProceso(this.CPU.PID);  //en ese caso cuando CPU1 llegue a cero el proceso finaliza
            } else { 
                    if(this.CPU.ES1>0) {//Si tiene 2 rafagas de CPU y la primera ya está en cero, entra acá
                        this.procesoCPU = new Proceso(this.CPU.PID,this.CPU.TA,this.CPU.Tam,this.CPU.CPU1,this.CPU.ES1,this.CPU.CPU2,this.CPU.ES2,this.CPU.CPU3);
                        this.CPU = new Recurso(0,0,0,0,0,0,0,0,true);
                        this.colaBloqueado1.add(this.procesoCPU);
                    }else{
                        if(this.CPU.CPU2 ==0){
                                this.colaBloqueado2.add(this.procesoCPU);
                                this.CPU = new Recurso(0,0,0,0,0,0,0,0,true);
                            if(this.cantrafaga == 2){
                                finalizarProceso(this.CPU.PID);  //en ese caso cuando CPU1 llegue a cero el proceso finaliza
                            } else{
                                if(this.CPU.ES2>0){
                                    this.procesoCPU = new Proceso(this.CPU.PID,this.CPU.TA,this.CPU.Tam,this.CPU.CPU1,this.CPU.ES1,this.CPU.CPU2,this.CPU.ES2,this.CPU.CPU3);
                                }else{
                                    if(this.CPU.CPU3==0){
                                        finalizarProceso(this.CPU.PID);  //en ese caso cuando CPU1 llegue a cero el proceso finaliza
                                    }
                                }
                            }
                        }
                    }
            } 
        } 
    }
    
    public void consumir(){
        switch (this.cantrafaga){
                        case 1: this.CPU.CPU1--;break;
                        case 2: if (this.CPU.CPU1 == 0){
                                    this.CPU.CPU2--;
                                }else{
                                   this.CPU.CPU1--; 
                                }break;
                        case 3: if (this.CPU.CPU1 > 0){
                                    this.CPU.CPU1--;
                                }else{
                                    if(this.CPU.CPU2>0){
                                       this.CPU.CPU2--;
                                    }else{
                                    this.CPU.CPU3--;
                                    }
                                }break;
                    }
    }
}
