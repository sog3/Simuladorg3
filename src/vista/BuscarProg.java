/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Algoritmos.SJF;
import Algoritmos.AlgBase;
import Algoritmos.FCFS;
import Algoritmos.RoundRobin;
import Algoritmos.SRTF;
import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import controlador.Operaciones;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import modelo.Memoria;
import modelo.Particion;
import modelo.Proceso;
import modelo.Recurso;

/**
 *
 * @author Tita
 */
public class BuscarProg extends javax.swing.JFrame {
    DefaultTableModel tmemo = new DefaultTableModel();
    DefaultTableModel procesos = new DefaultTableModel();
    Operaciones oper = new Operaciones();
    ArrayList<Memoria> listm = oper.listarMemorias();
    Boolean esvariable = false;
    Boolean esfija = false;
    Boolean esrr = false;
    Proceso procesop;        
    Memoria memoriap;

    int quantum;
    int PID = 0;
    int tamMemFija = 0;
    int tamMemVar = 0;
    int cantPart = 0;
    int tamPart;
    int mayorProceso = 0;
    int procesosVivos, div = 0; 
    float tr, te;
    int ts = 0;
    int ta = 0; 
    int ti = 0;
    Recurso CPU, ES1, ES2;
    Proceso proceso, procesoCPU, procesoES1, procesoES2;
    ArrayList<Particion> particiones;
    ArrayList<Particion> memoriaVariable; //variable global de lista que simula ser la memoria en particion variable
    int Time = 0;
    ArrayList<Proceso> listp = new ArrayList<Proceso>();      //Crea una lista con objetos de la clase Proceso
    ArrayList<Proceso> colaListo = new ArrayList<Proceso>();    //Aquí estan los procesos que esperam por la CPU
    ArrayList<Proceso> colaNuevo = new ArrayList<Proceso>();    //Lista con los procesos que esperan por ingresar a la cola de listo y a memoria
    ArrayList<Proceso> colaBloqueado1 = new ArrayList<Proceso>();    //Lista que contiene los procesos que esperan por ES
    ArrayList<Proceso> colaBloqueado2 = new ArrayList<Proceso>();    //Lista que contiene los procesos que esperan por ES
    VentanaSalida ventanaSalida;
    
    AlgBase algoritmo;
    
    /**
     * Creates new form BuscarProg
     */
    public BuscarProg() {
        initComponents();
        this.setLocationRelativeTo(null);
        vistaModificar();
        setModelTM();
        setModelTP();
        llenarTabla();
        
    }
    
    public void actualizarTablas(){
        limpiarTablaM();
        llenarTabla();
    }
    
    public void limpiarTablaM(){
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        int a = jTable1.getRowCount()-1;
        for (int i = a; i >= 0; i--) {          
        tb.removeRow(tb.getRowCount()-1);
        }
        jTable1.setModel(tb);
    }
    public void vistaModificar(){
        jLabel8.setVisible(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jLabel12.setVisible(false);
        algasigmfija.setVisible(false);
        algasigmvar.setVisible(false);
        algplanp.setVisible(false);
        quantuma.setVisible(false);
        guardar.setVisible(false);
        modificar.setVisible(true);
        modificar.setEnabled(false);
    }
    
    public void listarProcesos(){
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            System.out.println("estoy seleccionando");
        }
    });
    }
    
    public void setModelTP(){
        procesos.addColumn("PID");
        procesos.addColumn("Tiempo Arribo");
        procesos.addColumn("Tamaño");
        procesos.addColumn("CPU");
        procesos.addColumn("E/S");
        procesos.addColumn("CPU");
        procesos.addColumn("E/S");
        procesos.addColumn("CPU");
    }
    
    public void setModelTM(){
        tmemo.addColumn("ID");
        tmemo.addColumn("Descripcion");
        tmemo.addColumn("Tipo de Particion");
        tmemo.addColumn("Alg Asig de Memoria");
        tmemo.addColumn("Tamaño de memoria");
        tmemo.addColumn("Nro de particiones");
        tmemo.addColumn("Alg Plan Proceso");
        tmemo.addColumn("Quantum");
    }
    public void llenarTablaProcesos(ArrayList<Proceso> listp){

        Integer campo0, campo1, campo2, campo3, campo4,
                campo5, campo6, campo7;
        for(Proceso proceso : listp)
        {
            campo0 = proceso.getIdproceso();
            campo1 = proceso.getTiempoarribo();
            campo2 = proceso.getTamanio();
            campo3 = proceso.getCpu1();
            campo4 = proceso.getEntsal1();
            campo5 = proceso.getCpu2();
            campo6 = proceso.getEntsal2();
            campo7 = proceso.getCpu3();
        
            Object[] datosp = new Object[procesos.getColumnCount()];
            datosp[0] = campo0;
            datosp[1] = campo1;
            datosp[2] = campo2;
            datosp[3] = campo3;
            datosp[4] = campo4;
            datosp[5] = campo5;
            datosp[6] = campo6;
            datosp[7] = campo7;
            
            procesos.addRow(datosp);
            jTable2.setModel(procesos);
        }
    }

    
    public void llenarTabla(){
        listm = oper.listarMemorias();
        String campo0, campo1, campo2, campo5;
        Integer campo, campo3, campo4, campo6;
       for(Memoria memoria : listm)
        {
        campo = memoria.getIdmemoria();
        campo0 = memoria.getDescripcion();
        campo1 = memoria.getTipoparticion();
        campo2 = memoria.getAlgasigmemo();
        campo3 = memoria.getTammemo();
        campo4 = memoria.getNroparticiones();
        campo5 = memoria.getAlgplanproc();
        campo6 = memoria.getQuantum();
        
        Object[] datos = new Object[tmemo.getColumnCount()];
        datos[0] = campo;
        datos[1] = campo0;
        datos[2] = campo1;
        datos[3] = campo2;
        datos[4] = campo3;
        datos[5] = campo4;
        datos[6] = campo5;
        datos[7] = campo6;
        tmemo.addRow(datos);
        jTable1.setModel(tmemo);
        }
        
    }
    
    public void limpiarTablaP(){
        DefaultTableModel tb = (DefaultTableModel) jTable2.getModel();
        int a = jTable2.getRowCount()-1;
        for (int i = a; i >= 0; i--) {          
        tb.removeRow(tb.getRowCount()-1);
        }
        jTable2.setModel(tb);
    }
    
    public void emplearAlgoritmo(){
        int cantrafaga = procesop.getRafagascpu();
        String tipopart = memoriap.getTipoparticion();
        particiones = new ArrayList<Particion>();
        ArrayList<Proceso>cb1 = listp;
        ArrayList<Proceso>cb2 = listp;
        switch (memoriap.getAlgplanproc()) 
        {
            case "FCFS": algoritmo= new FCFS(CPU, ES1, ES2, cantrafaga, colaListo,  cb1, cb2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);

            break;
            case "Round Robin": algoritmo= new RoundRobin(quantum, CPU, ES1, ES2, cantrafaga, colaListo,  cb1, cb2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);
            
            break;
            case "SJF": algoritmo= new SJF(CPU, ES1, ES2, cantrafaga, colaListo,  cb1, cb2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);
            
            break;
            case "SRTF": algoritmo= new SRTF(CPU, ES1, ES2, cantrafaga, colaListo,  cb1, cb2, procesosVivos, tipopart, memoriaVariable, particiones, procesoCPU);
  
            break;
        }
    }
    
    public void crearMemFija(int cantPart) {
        particiones = new ArrayList<Particion>();
        tamMemFija = 0;
        for (int i=0; i < particiones.size(); i++){
            int nroPart = i+1;
            do{
                String TamPart = (JOptionPane.showInputDialog(null, "Ingrese el tamaño de la partición nro " + nroPart));
                try {
                    tamPart = Integer.parseInt(TamPart);
                    if(tamPart<1){ 
                        JOptionPane.showMessageDialog(this, "Cada partición debe ser mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Ingrese sólo números", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }while(tamPart<1);
            Particion p= new Particion(nroPart, 0, tamPart, true);
            particiones.add(p);
            tamMemFija = tamMemFija + tamPart;
        }
        int x;
        int mayorPart = 0;
        for (Particion lista2 : particiones) {  //Recorre la lista para saber cual es
            x = lista2.getTamPart();     //el tamaño de la particion mayor
            if (x > mayorPart) {
                mayorPart = x;
            }
        }
        if (mayorProceso > mayorPart) {
           JOptionPane.showMessageDialog(null, "Uno o más procesos no podrán ejecutarse por su tamaño, ingrese nuevamente los tamaños de las particiones", "Alerta", JOptionPane.WARNING_MESSAGE);
           crearMemFija(cantPart);
        }
    }
    
    public void crearMemVar(){
        memoriaVariable = new ArrayList<Particion>();
        Particion p = new Particion(0,0,tamMemVar,true);
        memoriaVariable.add(p);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        guardar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        algplanp = new javax.swing.JComboBox<>();
        algasigmfija = new javax.swing.JComboBox<>();
        algasigmvar = new javax.swing.JComboBox<>();
        quantuma = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscar Programa");
        setMinimumSize(new java.awt.Dimension(700, 600));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Descripcion", "Tipo de Particion", "Alg de Asig de Memoria", "Tamaño de Memoria", "Nro de Particiones", "Alg de Plan Proceso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 640, 141));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel1.setText("Buscar Programa");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel2.setText("Configuracion Inicial Memoria");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 83, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondomorado.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 80, 650, 175));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel3.setText("Procesos");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PID", "Tiempo Arribo", "Tamaño", "CPU", "E/S", "CPU", "E/S", "CPU"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(7).setResizable(false);
        }

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 640, 120));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondomorado.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 650, 160));

        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        getContentPane().add(guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, -1, -1));

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        getContentPane().add(modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, -1, -1));

        jLabel8.setText("Algoritmo de ");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        jLabel9.setText("Asignacion de Memoria");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        jLabel10.setText("Algoritmo de");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, -1, -1));

        jLabel11.setText("Planificacion de Proceso");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, -1, -1));

        jLabel12.setText("Quantum");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, -1, -1));

        algplanp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "Round Robin", "SJF", "SRTF" }));
        algplanp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                algplanpItemStateChanged(evt);
            }
        });
        getContentPane().add(algplanp, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 280, -1, -1));

        algasigmfija.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First-Fit", "Best-Fit" }));
        algasigmfija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algasigmfijaActionPerformed(evt);
            }
        });
        getContentPane().add(algasigmfija, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, -1, -1));

        algasigmvar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First-Fit", "Worst-Fit" }));
        getContentPane().add(algasigmvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, -1, -1));

        quantuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantumaActionPerformed(evt);
            }
        });
        getContentPane().add(quantuma, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, 80, -1));

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 537, -1, -1));

        jButton2.setText("Ejecutar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 537, -1, -1));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 264, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoclaro.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        limpiarTablaP();
        vistaModificar();
        int column = 0;
        int row = jTable1.getSelectedRow();
        int idm = Integer.parseInt(jTable1.getModel().getValueAt(row, column).toString());
        listp = oper.buscarProcesos(idm);
        llenarTablaProcesos(listp);
        modificar.setEnabled(true);
        procesop = listp.get(1);
        memoriap = oper.buscarMemoria(idm);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Principal principal = new Principal();
        principal.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Salida salida = new Salida();
        //salida.setVisible(true);
        //dispose();
        int tama;
       div = listp.size();
      
       cantPart = memoriap.getNroparticiones();
       
       for (Proceso lista1 : listp) {  //Recorre la lista para saber cual es
            tama = lista1.getTamanio();     //el tamaño del proceso mayor
            if (tama > mayorProceso) {
                mayorProceso = tama;
            }
       }
       
        //if (jTextFieldTamMV.getText().isEmpty()) {  //Si el usuario no designó el tamaño de la memoria variable, el tamaño por defecto será el triple del tamaño del proceso mayor
        //    tamMemVar = mayorProceso*3;
        //}else{
            tamMemVar = memoriap.getTammemo();
            System.out.println("obtuve el tamaño de la memoria"+tamMemVar);
        //}
        
        
        
        if ("Fija".equals(memoriap.getTipoparticion())){    
          crearMemFija(cantPart); 
        } else {
          if (mayorProceso > tamMemVar) {    //Error si un proceso es mayor que la memoria o particion
            JOptionPane.showMessageDialog(null, "Uno o más procesos no podrán ejecutarse por su tamaño", "Alerta", JOptionPane.WARNING_MESSAGE);
          }else{
              crearMemVar();
              System.out.println("creo la memoria variable");
          }
        }
        
        ejecutar();
    }//GEN-LAST:event_jButton2ActionPerformed

     public void ejecutar() {
        
        CPU = new Recurso(0, 0, 0, 0, 0, 0, 0, 0, false);
        ES1 = new Recurso(0, 0, 0, 0, 0, 0, 0, 0, false);
        ES2 = new Recurso(0, 0, 0, 0, 0, 0, 0, 0, false);
        emplearAlgoritmo();
        //Collections.sort(listp); //Ordena la lista por TA
        colaNuevo = listp;
        
        ventanaSalida = new VentanaSalida(algoritmo.particiones, tamMemFija, Time);
        algoritmo.procesosVivos = listp.size();
        while (algoritmo.procesosVivos > 0) {  //itera hasta que no hayan mas procesos vivos
           // Time++;  
           System.out.println("controlo el tiempo "+Time);
            tratarColaNuevo(); //Recorre la cola de Nuevo y carga los procesos que cumplan las condiciones a la cola de Listo y a la memoria, con el tipo de asignacion que haya elegido el usuario
            algoritmo.ejecutar();
            Time=algoritmo.getTime()+1 ;
            algoritmo.setTime(Time);
            tratarES();  
            
            ventanaSalida.dibujarMemoria(algoritmo.particiones, algoritmo.memoriaVariable, tamMemFija, Time);   //Por cada unidad de tiempo dibuja una memoria en ese instante
            ventanaSalida.dibujarCPU(Time, algoritmo.CPU.getPID());
            ventanaSalida.dibujarES(Time,algoritmo.ES1.getPID());
            ventanaSalida.dibujarES2(Time,algoritmo.ES2.getPID());
        }
        tr = ts - ta;
        te = tr - ti;
        tr = Math.round((tr/div) * 100) / 100f;
        te = Math.round((te/div) * 100) / 100f;
        ventanaSalida.mostrarSalida(Time, tr, te, (listp.get(0).getRafagascpu()+1));
        ventanaSalida.setVisible(true); //Cuando ya finalizaron todos los procesos, muestra la salida
        
    }
    
        public void tratarColaNuevo() {    
        int index = 0;
            System.out.println("cola nuevo"+colaNuevo.get(index).getTiempoarribo());
             System.out.println("esto tiene time "+Time);
        while (index < (colaNuevo.size())) {    //Recorre todos los procesos que estan en la cola de nuevo
            boolean cargo = false;
            if (colaNuevo.get(index).getTiempoarribo() <= Time) {
                switch (memoriap.getTipoparticion()) {
                    case "Variable" : 
                        System.out.println("entre en variable");
                        System.out.println(memoriap.getAlgasigmemo());
                        System.out.println("proceso para first fit "+colaNuevo.get(index));
                        switch (memoriap.getAlgasigmemo()) {
                            case "First-Fit": cargo = cargaFirstFit(colaNuevo.get(index));    //Si el proceso cumple las condiciones, lo carga en memoria
                                    break;
                            case "Worst-Fit": cargo = cargaWorstFit(colaNuevo.get(index));
                                    break;
                        } 
                    break;
                    case "Fija" : 
                        switch (memoriap.getAlgasigmemo()) {
                            case "First-Fit": cargo = cargaFirstFit(colaNuevo.get(index));    //Si el proceso cumple las condiciones, lo carga en memoria
                                    break;
                            case "Best-Fit": cargo = cargaBestFit(colaNuevo.get(index));    //Si el proceso cumple las condiciones, lo carga en  en memoria
                                    break;
                        }
                    break;
                }
            }
            if (cargo){ 
                colaListo.add(colaNuevo.get(index));
                colaNuevo.remove(index);
                index--;    //Si cargo un proceso se resta, porque como se elimino un elemento de la colaNuevo, entonces el siguiente elemente va ocupar ese lugar,
            }
            index++;
        } 
        
    }
    
    public void tratarES(){
        if (algoritmo.ES1.estado()){ //Si esta ocupada la primer E/S descuenta una unidad de tiempo
            algoritmo.ES1.ES1--;
        } 
        if (algoritmo.ES2.estado()){    //Si esta ocupada la segunda E/S descuenta una unidad de tiempo
            algoritmo.ES2.ES2--;
        }
        cargarES1conCB1();  //Si la primer E/S está libre, la carga con un proceso que este en la colaBloqueado1, si lo tuviese
        cargarES2conCB2();  //Si la segunda E/S está libre, la carga con un proceso que este en la colaBloqueado2, si lo tuviese
        if ((algoritmo.ES1.estado())&&(algoritmo.ES1.ES1 == 0)) { //Si la primer E/S está ocupada y se le acabó el tiempo, debe abandonar el recurso y volver a la colaListo
                procesoES1 = new Proceso(ES1.PID,ES1.TA,ES1.Tam,ES1.CPU1,ES1.ES1,ES1.CPU2,ES1.ES2,ES1.CPU3);
                colaListo.add(procesoES1);
                algoritmo.ES1 = new Recurso(0,0,0,0,0,0,0,0,true);
                cargarES1conCB1();
        }
        if ((algoritmo.ES2.estado())&&(algoritmo.ES2.ES2 == 0)) { //Si la segunda E/S está ocupada y se le acabó el tiempo, debe abandonar el recurso y volver a la colaListo
                procesoES2 = new Proceso(ES2.PID,ES2.TA,ES2.Tam,ES2.CPU1,ES2.ES1,ES2.CPU2,ES2.ES2,ES2.CPU3);
                colaListo.add(procesoES2);
                algoritmo.ES2 = new Recurso(0,0,0,0,0,0,0,0,true);
                cargarES2conCB2();
        }
        tratarColaNuevo();    //Siempre hacer esto antes de preguntar por colaListo, porque puede ser haya un proceso que este esperando que finalice otro para ocupar ese espacio de memoria
        if(("SJF".equals(memoriap.getAlgplanproc()))||("STRF".equals(memoriap.getAlgplanproc()))){
            algoritmo.ordenarCLporCPU();
            if(("STRF".equals(memoriap.getAlgplanproc()))&&(!colaListo.isEmpty())){
                Proceso pro_menor = colaListo.get(0);
                int tiempoProceso = menorTiempoCpuProceso(pro_menor); //obtento el tiempo de CPU del Proceso mas quequeño
                //creo una clase proceso auxiliar y almaceno los datos del proceso actual en CPU
                Proceso pro_CPU = new Proceso(CPU.PID,CPU.TA,CPU.Tam,CPU.CPU1,CPU.ES1,CPU.CPU2,CPU.ES2,CPU.CPU3);
                //obtento el tiempo de CPU del proceso en CPU
                int tiempoCPU = menorTiempoCpuProceso(pro_CPU);
                if(tiempoProceso < tiempoCPU){ 
                    //pregunta si el tiempo del proceso que arriba es menor al tiempo del proceso que esta en CPU
                    //el proceso que está primero en la cola de listo se apropia de CPU
                    desalojarProcesoDeCPU();
                }
            }
        }
        if((quantum==0)&&(algoritmo.CPU.estado())&&("Round Robin".equals(memoriap.getAlgplanproc()))){
            desalojarProcesoDeCPU();
        }
        if((!algoritmo.CPU.estado()) && (!algoritmo.colaListo.isEmpty())){
            algoritmo.cargarCPUconCL();   //Si CPU está libre y hay un elemento en la colaListo, lo asigna a la CPU
            //if (Quantum.getText().isEmpty()) {  //Si el usuario no designó ningún quantum, el valor por defecto será 2
            //    quantum = 2;
            //}else{
                quantum = memoriap.getQuantum(); 
            //}
        }
    }
    
    public void desalojarProcesoDeCPU(){
        Proceso pro = new Proceso(algoritmo.CPU.PID,algoritmo.CPU.TA,algoritmo.CPU.Tam,algoritmo.CPU.CPU1,algoritmo.CPU.ES1,algoritmo.CPU.CPU2,algoritmo.CPU.ES2,algoritmo.CPU.CPU3);
        algoritmo.colaListo.add(pro);
        algoritmo.CPU.estado = false;
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
    
    public void cargarES1conCB1(){
        if((!algoritmo.ES1.estado())&(colaBloqueado1.size()>0)) {
           algoritmo.ES1.PID = colaBloqueado1.get(0).getIdproceso();
           algoritmo.ES1.Tam = colaBloqueado1.get(0).getTamanio();
           algoritmo.ES1.TA = colaBloqueado1.get(0).getTiempoarribo();
           algoritmo.ES1.CPU1 = colaBloqueado1.get(0).getCpu1();
           algoritmo.ES1.ES1 = colaBloqueado1.get(0).getEntsal1();
           algoritmo.ES1.CPU2 = colaBloqueado1.get(0).getCpu2();
           algoritmo.ES1.ES2 = colaBloqueado1.get(0).getEntsal2();
           algoritmo.ES1.CPU3 = colaBloqueado1.get(0).getCpu3();
           algoritmo.ES1.estado = true;
           algoritmo.colaBloqueado1.remove(0);
        }
    }
    
    public void cargarES2conCB2(){
        if((!algoritmo.ES2.estado())&(colaBloqueado2.size()>0)) {
           algoritmo.ES2.PID = colaBloqueado2.get(0).getIdproceso();
           algoritmo.ES2.Tam = colaBloqueado2.get(0).getTamanio();
           algoritmo.ES2.TA = colaBloqueado2.get(0).getTiempoarribo();
           algoritmo.ES2.CPU1 = colaBloqueado2.get(0).getCpu1();
           algoritmo.ES2.ES1 = colaBloqueado2.get(0).getEntsal1();
           algoritmo.ES2.CPU2 = colaBloqueado2.get(0).getCpu2();
           algoritmo.ES2.ES2 = colaBloqueado2.get(0).getEntsal2();
           algoritmo.ES2.CPU3 = colaBloqueado2.get(0).getCpu3();
           algoritmo.ES2.estado = true;
           algoritmo.colaBloqueado2.remove(0);
        }
    }
    
        
    public boolean cargaFirstFit(Proceso proceso) {
        boolean cargo = false;
            if ("Variable".equals(memoriap.getTipoparticion())) {
                
                for(int i=0; i<memoriaVariable.size(); i++){
                    if((memoriaVariable.get(i).isLibre())&&(memoriaVariable.get(i).TamPart>=proceso.getTamanio())&&(!cargo)){
                        Particion nuevo = new Particion(0,proceso.getIdproceso(),proceso.getTamanio(),false);
                        memoriaVariable.add(i, nuevo);
                        memoriaVariable.get(i+1).setTamPart((memoriaVariable.get(i+1).getTamPart())-(proceso.getTamanio()));
                        cargo = true; 
                    }
                }
                if(memoriaVariable.get(memoriaVariable.size()-1).getTamPart()==0){
                    memoriaVariable.remove(memoriaVariable.size()-1);
                }
            } else{
                for (Particion x: particiones){
                    if ((x.libre) & (x.TamPart >= proceso.getTamanio()) &(!cargo)) {
                        x.setProCargado(proceso.getIdproceso());
                        x.setLibre(false);
                        cargo = true;
                    }
                }
            }
        return cargo;
    }
    
    public boolean cargaBestFit(Proceso proceso) {
        boolean cargo = false;
        int menor = 0;
        int pos = 0;
        boolean existe = false;
        for (int i = 0; i < particiones.size(); i++) { //Recorro las particiones hasta encontrar la primera que pueda ser asignada
            if ((particiones.get(i).libre) &(particiones.get(i).TamPart >= proceso.getTamanio())){
                menor = particiones.get(i).TamPart;     //La guardo para comparar si hay otras menores
                pos = i;    //Guardo la posicion, porque de no haber otra particion menor, sera asignada a esta
                existe = true;  //Afirma que existe por lo menos una particion disponible para ser asignada
                break;
            }
        }
        for (int i = 0; i < particiones.size(); i++){
            if ((particiones.get(i).libre) & (particiones.get(i).TamPart >= proceso.getTamanio())) {
                if (particiones.get(i).TamPart < menor) {   //Busco si hay otra particion menor disponible que pueda ser asignada
                    menor = particiones.get(i).TamPart;
                    pos = i;
                }
            }
        }
        if (existe) {
            particiones.get(pos).ProCargado = proceso.getIdproceso();
            particiones.get(pos).libre = false;
            cargo = true;
        }
        return cargo;
    }
    
    public boolean cargaWorstFit(Proceso proceso){
        int mayor = 0;
        int index = 0;
        boolean cargo = false;
        boolean existe = false;
        for (int i = 0; i < memoriaVariable.size(); i++) {
            if((memoriaVariable.get(i).isLibre()) && (memoriaVariable.get(i).TamPart >= proceso.getIdproceso()) && (memoriaVariable.get(i).TamPart >= mayor)) {   
                mayor = memoriaVariable.get(i).TamPart;
                index = i;
                existe = true;
            }
        }
        if (existe) {
            Particion nuevo = new Particion(0,proceso.getIdproceso(),proceso.getTamanio(),false);
            memoriaVariable.add(index, nuevo);
            memoriaVariable.get(index+1).setTamPart((memoriaVariable.get(index+1).getTamPart())-(proceso.getTamanio()));
            cargo = true;
            if(memoriaVariable.get(memoriaVariable.size()-1).getTamPart()==0){
                memoriaVariable.remove(memoriaVariable.size()-1);
            }
        }  
        return cargo;
    }
    
     
    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        jLabel8.setVisible(true);        jLabel10.setVisible(true);

        jLabel9.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(false);
        quantuma.setVisible(false);
        algplanp.setVisible(true);
        guardar.setVisible(true);
        modificar.setVisible(false);
        algasigmvar.setVisible(false);
        algasigmfija.setVisible(false);
        int column = 2;
        int row = jTable1.getSelectedRow();
        String tipopar = (jTable1.getModel().getValueAt(row, column).toString());
        int column2 = 6;
        String planp = (jTable1.getModel().getValueAt(row, column2).toString());
        esvariable=false;
        esfija=false;
        esrr=false;
        if (tipopar.equals("Variable")){
            algasigmvar.setVisible(true);
            String aam = (jTable1.getModel().getValueAt(row, 3).toString());
            algasigmvar.setSelectedItem(aam);
            esvariable=true;
        } else {
            algasigmfija.setVisible(true);
            String aam = (jTable1.getModel().getValueAt(row, 3).toString());
            algasigmfija.setSelectedItem(aam);
            esfija=true;
        }
        if (planp.equals("Round Robin")){
            
            jLabel12.setVisible(true);
            quantuma.setVisible(true);
            String q = (jTable1.getModel().getValueAt(row, 7).toString());
            quantuma.setText(q);
            algplanp.setSelectedItem("Round Robin");
        } else {
            String app = (jTable1.getModel().getValueAt(row, 6).toString());
            algplanp.setSelectedItem(app);
        }
        
    }//GEN-LAST:event_modificarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        int column = 0;
        int row = jTable1.getSelectedRow();
        int idm = Integer.parseInt(jTable1.getModel().getValueAt(row, column).toString());
        String alg1, alg2;
        if (esvariable){
            alg1 = (String)algasigmvar.getSelectedItem();
            alg2 = (String)algplanp.getSelectedItem();
            if (alg2 == "Round Robin"){
                int quantum = Integer.parseInt(quantuma.getText());
                oper.actualizarMemoriaRR(idm, alg1, alg2, quantum);
            } else{
            oper.actualizarMemoria(idm, alg1, alg2);}
        } else if (esfija){
            alg1 = (String)algasigmfija.getSelectedItem();
            alg2 = (String)algplanp.getSelectedItem();
            if (alg2 == "Round Robin"){
                int quantum = Integer.parseInt(quantuma.getText());
                oper.actualizarMemoriaRR(idm, alg1, alg2, quantum);
            }else {
            oper.actualizarMemoria(idm, alg1, alg2);}
        }
        
        vistaModificar();
        actualizarTablas();
        
    }//GEN-LAST:event_guardarActionPerformed

    private void quantumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantumaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantumaActionPerformed

    private void algplanpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_algplanpItemStateChanged
        
        if ((String)algplanp.getSelectedItem()=="Round Robin"){
            jLabel12.setVisible(true);
            quantuma.setVisible(true);
            esrr=true;
        } else {
            jLabel12.setVisible(false);
            quantuma.setVisible(false);
            esrr=false;
        }
    }//GEN-LAST:event_algplanpItemStateChanged

    private void algasigmfijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algasigmfijaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_algasigmfijaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarProg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarProg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarProg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarProg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarProg().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> algasigmfija;
    private javax.swing.JComboBox<String> algasigmvar;
    private javax.swing.JComboBox<String> algplanp;
    private javax.swing.JButton guardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField quantuma;
    // End of variables declaration//GEN-END:variables
}
