package Interfaces;
import EON.Utilitarios.*;
import EON.*;
import EON.Algoritmos.*;
import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.*;



/**
 *
 * @author Team Delvalle
 * Frame que se encargad de la interfaz con el usurio y realizar la simulacion de una Red Optica Elastica.
 * Permite realizar una simulacion teniendo:
 * - Una topologia
 * - Un conjunto de algoritmos(para esta version dos).
 * - Un tipo de demanda que sera generada. 
 * Para esta version las simulaciones se realizan considerando para cada demanda se tienen el mismo valor
 * en todas las demandas para el tiempo de permanencia en la red como para la cantidad de FSs requeridos.
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private Topologias Redes; // topologias disponibles
    private double factorTiempo; // factor de tiempo indicado por el usuario
    private double factorCapacidad; // cantidad de FSs que contendra cada demanda
    private int capacidadPorEnlace; // cantidad de FSs por enlace en la topologia elegida
    private int tiempoTotal; // Iiempo que dura una simualcion
    private double anchoFS; // ancho de un FS en los enlaces
    private int cantidadRedes; //cantidad de redes exitentes en el Simulador
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    private List  algoritmosCompletosParaEjecutar;
    private List  algoritmosCompletosParaGraficar;
    private int cantidadDeAlgoritmosRuteoSeleccionados;
    private int cantidadDeAlgoritmosTotalSeleccionados;
    
    public VentanaPrincipal() {
        initComponents();
       /* Inicialmente no se muestran en panel para editar las caracteristicas de los enlaces
        */
       
        /*
        */
        this.Redes=new Topologias(); // asignamos todas las topologias disponibles
        //obtenemos el factor de tiempo inidicado por el usuario
        this.factorTiempo=Double.parseDouble(this.spinnerFactorTiempo.getValue().toString()); 
        //obtenemos la cantidad de FS que tendra cada demanda indicado por el usuario
        this.factorCapacidad=Double.parseDouble(this.spinnerFactorCapacidad.getValue().toString());
        //obtenemos la cantidad de FS de los enlaces indicados por el usuario
        this.capacidadPorEnlace=Integer.parseInt(this.textFieldCapacidadEnlace.getText());
        //Tiempo de simulacion indicado por el usuario
        this.tiempoTotal=Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString());
        // ancho de los FSs de la toplogia elegida, tambien indicado por el usuario
        this.anchoFS=Double.parseDouble(this.textFieldAnchoFS.getText());
        /*No mostramos inicialmente los paneles que muestran los Resultados
        */
        this.etiquetaResultado.setVisible(true);
        this.panelResultado.setVisible(false);
        /////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////
       // this.algoritmosCompletos = new LinkedList();
        this.panelAsignacionSpectro.setVisible(false);
        this.botonGuardarAS.setEnabled(false);
        this.cantidadDeAlgoritmosRuteoSeleccionados=0;
        this.cantidadDeAlgoritmosTotalSeleccionados=0;
        this.algoritmosCompletosParaEjecutar = new LinkedList();
        this.algoritmosCompletosParaGraficar = new LinkedList();
        this.setTitle("EON Simulator");
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaRedes = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaAlgoritmosRuteo = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaDemandas = new javax.swing.JList<>();
        botonEjecutarSimulacion = new javax.swing.JButton();
        etiquetaTopologia = new javax.swing.JLabel();
        etiquetaDemanda = new javax.swing.JLabel();
        etiquetaError = new javax.swing.JLabel();
        etiquetaCapacidadActual = new javax.swing.JLabel();
        etiquetaTiempoActual = new javax.swing.JLabel();
        etiquetaAnchoFSActual = new javax.swing.JLabel();
        etiquetaFactorTiempo = new javax.swing.JLabel();
        spinnerTiempoSimulacion = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        spinnerFactorCapacidad = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spinnerFactorTiempo = new javax.swing.JSpinner();
        etiquetaResultado = new javax.swing.JLabel();
        etiquetaImagen = new javax.swing.JLabel();
        panelResultado = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        etiqueta1 = new javax.swing.JLabel();
        etiquetaTextoDemandasTotales = new javax.swing.JLabel();
        etiquetaDemandasTotales = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinnerEarlang = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        spinnerPaso = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        textFieldCapacidadEnlace = new javax.swing.JTextField();
        textFieldAnchoFS = new javax.swing.JTextField();
        panelAsignacionSpectro = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaAlgoritmosAS = new javax.swing.JList();
        etiquetaRSA = new javax.swing.JLabel();
        botonGuardarAS = new javax.swing.JButton();
        botonCancelarAS = new javax.swing.JButton();
        checkBotonModulacion = new javax.swing.JCheckBox();
        etiquetaRSA1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaRedes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {"Red 1","Red 2"};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listaRedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaRedesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaRedes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 112, 58));

        listaAlgoritmosRuteo.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "FAR" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaAlgoritmosRuteo.setToolTipText("");
        listaAlgoritmosRuteo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaAlgoritmosRuteoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaAlgoritmosRuteo);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 112, 110));

        listaDemandas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Tiempo de permanecia y FS Fijos", "Tiempo de permanecia Fijo y FS Variables" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listaDemandas);
        listaDemandas.getAccessibleContext().setAccessibleName("");
        listaDemandas.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, 73));

        botonEjecutarSimulacion.setText("Simular");
        botonEjecutarSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarSimulacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonEjecutarSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 111, 46));

        etiquetaTopologia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaTopologia.setText("Topologia");
        getContentPane().add(etiquetaTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        etiquetaDemanda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaDemanda.setText("Demanda");
        getContentPane().add(etiquetaDemanda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));
        getContentPane().add(etiquetaError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 394, 23));

        etiquetaCapacidadActual.setText("Capacidad");
        getContentPane().add(etiquetaCapacidadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 110, 20));

        etiquetaTiempoActual.setText("Tiempo de Simulacion");
        getContentPane().add(etiquetaTiempoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 150, 20));

        etiquetaAnchoFSActual.setText("Ancho FS");
        getContentPane().add(etiquetaAnchoFSActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 110, 20));

        etiquetaFactorTiempo.setText("Tiempo de Pemancia");
        getContentPane().add(etiquetaFactorTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 110, 20));

        spinnerTiempoSimulacion.setModel(new javax.swing.SpinnerNumberModel(100, 50, 100000, 25));
        getContentPane().add(spinnerTiempoSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, 20));

        jLabel1.setText("Ancho de Banda x Demanda (Gbps)");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 230, 40));

        spinnerFactorCapacidad.setModel(new javax.swing.SpinnerNumberModel(2, 2, 1000, 50));
        spinnerFactorCapacidad.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerFactorCapacidadStateChanged(evt);
            }
        });
        getContentPane().add(spinnerFactorCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, 70, 20));

        jLabel2.setText("FSs por Enlace");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, 20));

        jLabel3.setText("GHz");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 30, 20));

        spinnerFactorTiempo.setModel(new javax.swing.SpinnerNumberModel(0.1d, 0.001d, 0.9d, 0.01d));
        getContentPane().add(spinnerFactorTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 70, 20));

        etiquetaResultado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaResultado.setText("Resultado de la Simulación");
        getContentPane().add(etiquetaResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 180, 20));
        getContentPane().add(etiquetaImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 320, 170));

        panelResultado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelResultadoLayout = new javax.swing.GroupLayout(panelResultado);
        panelResultado.setLayout(panelResultadoLayout);
        panelResultadoLayout.setHorizontalGroup(
            panelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
        panelResultadoLayout.setVerticalGroup(
            panelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        getContentPane().add(panelResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 470, 280));

        jLabel5.setText("Unidades");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 20));

        etiqueta1.setText("Del tiempo de Simulacion");
        getContentPane().add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, -1, 20));

        etiquetaTextoDemandasTotales.setText("Cantidad total de Demandas");
        getContentPane().add(etiquetaTextoDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 320, 190, 20));
        getContentPane().add(etiquetaDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 330, 90, 30));

        jLabel4.setText("Carga de Trafico Maximo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 160, 20));

        spinnerEarlang.setModel(new javax.swing.SpinnerNumberModel(100, 10, 1500, 100));
        getContentPane().add(spinnerEarlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 80, -1));

        jLabel6.setText("Earlang");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, 20));

        jLabel7.setText("Paso (u)");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, -1));

        spinnerPaso.setModel(new javax.swing.SpinnerNumberModel(10, 1, 100, 10));
        getContentPane().add(spinnerPaso, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, -1, -1));

        jLabel8.setText("Earlang");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 60, 20));

        textFieldCapacidadEnlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCapacidadEnlace.setText("50");
        getContentPane().add(textFieldCapacidadEnlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 80, -1));

        textFieldAnchoFS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldAnchoFS.setText("2");
        getContentPane().add(textFieldAnchoFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 80, 20));

        listaAlgoritmosAS.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "FF", "EF", "RF" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listaAlgoritmosAS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaAlgoritmosASMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listaAlgoritmosAS);

        etiquetaRSA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA.setText("Asignacion de Spectro");

        botonGuardarAS.setText("Guardar");
        botonGuardarAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarASActionPerformed(evt);
            }
        });

        botonCancelarAS.setText("Cancelar");
        botonCancelarAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarASActionPerformed(evt);
            }
        });

        checkBotonModulacion.setActionCommand("Desea considerar Modulacion? ");
        checkBotonModulacion.setLabel("Desea considerar Modulacion? ");

        javax.swing.GroupLayout panelAsignacionSpectroLayout = new javax.swing.GroupLayout(panelAsignacionSpectro);
        panelAsignacionSpectro.setLayout(panelAsignacionSpectroLayout);
        panelAsignacionSpectroLayout.setHorizontalGroup(
            panelAsignacionSpectroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                .addGroup(panelAsignacionSpectroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelAsignacionSpectroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(botonGuardarAS))
                            .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(checkBotonModulacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(etiquetaRSA))
                    .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonCancelarAS)))
                .addGap(20, 20, 20))
        );
        panelAsignacionSpectroLayout.setVerticalGroup(
            panelAsignacionSpectroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAsignacionSpectroLayout.createSequentialGroup()
                .addComponent(etiquetaRSA)
                .addGroup(panelAsignacionSpectroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(checkBotonModulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonGuardarAS))
                    .addGroup(panelAsignacionSpectroLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(botonCancelarAS)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelAsignacionSpectro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 300, 150));

        etiquetaRSA1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA1.setText("Algoritmo de Ruteo");
        getContentPane().add(etiquetaRSA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void botonEjecutarSimulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarSimulacionActionPerformed
        
         /* Al inicio de cada Simulacion e+condemos los paneles de Resultado
        */
        this.etiquetaResultado.setVisible(true);
        this.panelResultado.setVisible(false);
    
        GrafoMatriz G [] = new GrafoMatriz[this.algoritmosCompletosParaGraficar.size()]; // Se tiene una matriz de adyacencia por algoritmo RSA elegidos para por el usuario
        
        Demanda d= new Demanda();  // Demanda a recibir
        Resultado r=new Resultado(); // resultado obtenido en una demanda. Si r==null se cuenta como bloqueo
        String mensajeError = "Seleccione: "; // mensaje de error a mostrar eal usuario si no ha completado los parametros de
                                               // simulacion
        
        List<String> RSA = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        ResultadoRuteo r1 = new ResultadoRuteo(); // resultado optenido luego de ejecutarse un algoritmo de ruteo
        
        int E=(int)this.spinnerEarlang.getValue(); // se obtiene el limite de carga (Erlang) de trafico seleccionado por el usuario
        int demandasPorUnidadTiempo=0; //demandas por unidad de tiempo. Considerando el punt decimal
        int earlang=0; //Carga de trafico en cada simulacion
        int k=-1; // contador auxiliar
        int paso=(int)this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD=0; // contador de demandas totales
        int tiempoT=Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        int capacidadE=Integer.parseInt(this.textFieldCapacidadEnlace.getText()); // espectro por enalce
        double anchoFS=Double.parseDouble(this.textFieldAnchoFS.getText().toString()); // ancho de FS
        //factor del tiempo de simulacion especificado por el usuario
        
        System.out.println("El ancho del FS es:"+anchoFS);
        System.out.println("Cantidad de FS por enlace:"+capacidadE);
        System.out.println("Cantidad Algoritmos:"+this.cantidadDeAlgoritmosTotalSeleccionados);
        
        
        // tiempo de permanencia fijado por el usuario.
        int t=(int)(Double.parseDouble(this.spinnerFactorTiempo.getValue().toString())*tiempoT);
        //cantidad de FS por demanda expresado pro el usuario (en la version 0.2 es el ancho de banda requerido por el usuario en Gbps)
        int B=(int)(Double.parseDouble(this.spinnerFactorCapacidad.getValue().toString()));
        
        if(this.listaDemandas.getSelectedIndex()>=0 && this.listaAlgoritmosRuteo.getSelectedIndex()>=0 && 
                this.listaRedes.getSelectedIndex()>=0 && this.listaAlgoritmosAS.getSelectedIndex()>=0 && this.cantidadDeAlgoritmosTotalSeleccionados >0){ // si todos los parametros fueron seleccionados
           this.etiquetaError.setVisible(true); // habilitamos la etiqueta de error
           
            RSA= this.algoritmosCompletosParaGraficar; // obtenemos los algoritmos RSA seleccionados
            String redSeleccionada = this.listaRedes.getSelectedValue(); // obtenemos la topologia seleccionada
            String demandaSeleccionada = this.listaDemandas.getSelectedValue(); // obtenemos el tipo de trafico seleccionado
           
            int [] contB=new int [RSA.size()]; // array encargado de almacenar en cada posicion la cantidad de bloqueo para cada
                                                // algoritmo seleccionado
            int contBloqInstant = 0;
            List prob []= new LinkedList[RSA.size()]; // probabilidad de bloqueo para cada algoritmo RSA selecciondo 
            
            
            for(int i=0;i<prob.length;i++){
                prob[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            } 
            
            switch(redSeleccionada){ // cagamos los datos en las matrices de adyacencia segun la topologia seleccionada
                case "Red 0":
                    //de ´rueba no utilizado
                    for(int i=0;i<RSA.size();i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(0).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(0));
                    }
                    break;
                case "Red 1":
                    for(int i=0;i<RSA.size();i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(1));
                    }
                    break;
                case "Red 2":
                    for(int i=0;i<RSA.size();i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(2));
                    }
            }
           while(earlang<=E){ // mientras no se llega a la carga de trafico maxima
                contBloqInstant = 0;
                for(int i=0;i<tiempoT;i++){ // para cada unidad de tiempo
                   demandasPorUnidadTiempo = Utilitarios.demandasTotalesPorTiempo(earlang, i); // obtener las demandas
                    //System.out.println("En el tiempo: "+i+" demandas x unid: "+demandasPorUnidadTiempo);
                        // por unidad de tiempo
                     for(int j=0;j<demandasPorUnidadTiempo;j++){ // para cada demanda
                        switch(demandaSeleccionada){ // se ve que modelo de trafico fue seleccionado
                        case "Tiempo de permanecia y FS Fijos":
                             d.obtenerDemandasFijoConModulacion(B, i,G[0].getCantidadDeVertices()-1);  // obtenemos un origen, fin, catidad de FS y tiempo de permanecia
                             break;
                        case "Tiempo de permanecia Fijo y FS Variables": // aun en duda, no utilizar. Se espera tener listo en la version 0.3
                            d.obtenerDemandasFSVariable(B, t,G[0].getCantidadDeVertices()-1);  // obtenemos un origen, fin, catidad de FS y tiempo de permanecia
                             break;
                        } 
                        ListaEnlazada[] ksp=Utilitarios.KSP(G[0], d.getOrigen(), d.getDestino(), 5); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=5 (pude ser mas, cambiar dependiendo de la necesidad)
                        for(int a=0; a<RSA.size();a++){ 
                            
                            String algoritmoAejecutar = RSA.get(a);
                            
                            switch(algoritmoAejecutar){
                                
                                case "FAR - M - FF":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FAR(G[a], d, ksp, capacidadE,true);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_FF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - M - EF":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FAR(G[a], d, ksp, capacidadE,true);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_EF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - M - RF":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FAR(G[a], d, ksp, capacidadE,true);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_RF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - SM - FF":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FAR(G[a], d, ksp, capacidadE,false);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_FF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contBloqInstant++;
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - SM - EF":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FAR(G[a], d, ksp, capacidadE,false);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_EF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - SM - RF":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FAR(G[a], d, ksp, capacidadE,false);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_RF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contB[a]++;
                                    }
                                    break;  
                                case "FA":
                                    ////////////////////////////Ruteo///////////////////////////
                                    r1=Algoritmos.Ruteo_FA(G[a], d, ksp, capacidadE);
                                    if(r1!=null){
                                        /////////////////////Asignacion de Espectro//////////////////////
                                        r=Algoritmos.asignacionSpectro_EF(G[a], r1, capacidadE, d);
                                        Utilitarios.asignarFS(ksp, r, G[a], d);
                                    }else{
                                        contB[a]++;
                                    }
                                    break;
                            }
                            
                        }
                        contD++; 
                    }
                    
                }
            
            ++k;
            // almacenamos la probablidad de bloqueo final para cada algoritmo
            for(int a=0;a<RSA.size();a++){
                System.out.println("Bloqueo: "+contB[a] + " cant demandas: "+contD);
                System.out.println("Bloqueo Instantaneo: "+contBloqInstant+ " demandas insta: "+0);
                prob[a].add(((double)contB[a]/contD));
               System.out.println("Probabilidad: "+(double)prob[a].get(k)+" Algoritmo: "+a+" Earlang: "+earlang);
            }
            // avanzamos a la siguiente carga de trafico
             System.out.println("Vertices:"+ G[0].getCantidadDeVertices());
        
            System.out.println("Enlaces:"+ G[0].getCantidadEnlaces());
            System.out.println("Capacidad:"+ G[0].getCapacidadTotal());
            
            G[0].cargaRed(capacidadE);
            
            System.out.println("-------------------------------------------------");
            
            for(int j=0;j<RSA.size();j++){
                 Utilitarios.Disminuir(G[j]);
            }    
            
            earlang+=paso;
        }
        this.etiquetaError.setText("Simulacion Terminada...");
        // una vez finalizado, graficamos el resultado.
        Utilitarios.GraficarResultado(prob, this.panelResultado, this.etiquetaResultado,RSA,paso);
        String demandasTotales=""+contD; // mostramos la cantidad de demandas totales recibidas
        this.etiquetaDemandasTotales.setText(demandasTotales);
        System.out.println("Total demandas: "+demandasTotales);
        ////////Vaciar listas para las siguientes simulaciones///////////////
        /////////////////////////////////////////////////////////////////////
        this.algoritmosCompletosParaEjecutar.clear();
        this.algoritmosCompletosParaGraficar.clear();
        this.cantidadDeAlgoritmosRuteoSeleccionados=0;
        this.cantidadDeAlgoritmosTotalSeleccionados=0;
        
        }else{ // control de errores posibles realizados al no completar los parametros de simulacion
            if(listaDemandas.getSelectedIndex()<0){
               mensajeError=mensajeError+"Demanda";
                
            }
            if (this.listaAlgoritmosRuteo.getSelectedIndex()<0){
                if(mensajeError=="Seleccione "){
                    mensajeError=mensajeError+"Algoritmo RSA";
                }else{
                    mensajeError=mensajeError+", Algoritmo RSA";
                }
            }
            if(this.listaRedes.getSelectedIndex()<0){
                if(mensajeError=="Seleccione "){
                    mensajeError=mensajeError+"Topologia";
                }else{
                    mensajeError=mensajeError+", Topologia";
                }   
            }
            if(this.listaAlgoritmosAS.getSelectedIndex()<0){
                if(mensajeError=="Seleccione "){
                    mensajeError=mensajeError+"Algoritmo AS";
                }else{
                    mensajeError=mensajeError+", Algoritmo AS";
                }   
            }
            if(this.listaAlgoritmosAS.getSelectedIndex()<0){
                if(mensajeError=="Seleccione "){
                    mensajeError=mensajeError+"No se Selecciono Ningun Algoritmo de Ruteo y Asignacion de Espectro";
                }else{
                    mensajeError=mensajeError+", No se Selecciono Ningun Algoritmo de Ruteo y Asignacion de Espectro";
                }   
            }
            
            
            if(mensajeError!="Seleccione "){
                this.etiquetaError.setText(mensajeError);
            }
        }
    }//GEN-LAST:event_botonEjecutarSimulacionActionPerformed

    private void listaRedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaRedesMouseClicked
        if(this.listaRedes.getSelectedIndex()>=0){
          
            ImageIcon Img = new ImageIcon(getClass().getResource("Imagenes/"+(listaRedes.getSelectedValue()+".png")));
            etiquetaImagen.setIcon(Img);
            etiquetaImagen.setBounds(210, 100, 320, 170);
            etiquetaImagen.setVisible(true);
            etiquetaImagen.setOpaque(false);
            String redseleccionada=this.listaRedes.getSelectedValue();
            switch(redseleccionada){
                case "Red 0":
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int)(this.Redes.getRed(0).getCapacidadTotal()/this.Redes.getRed(0).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(0).getAnchoFS()));
                    break;
                case "Red 1":
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int)(this.Redes.getRed(1).getCapacidadTotal()/this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(1).getAnchoFS()));
                    break;
                case "Red 2":
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int)(this.Redes.getRed(2).getCapacidadTotal()/this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(2).getAnchoFS()));
                    break;
            }
        }
    }//GEN-LAST:event_listaRedesMouseClicked

/*
    En proceso, no esta listo y tampoco es muy necesario
*/
    private void spinnerFactorCapacidadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerFactorCapacidadStateChanged
        // TODO add your handling code here:
        double valor=(int)this.spinnerFactorCapacidad.getValue();
        
    }//GEN-LAST:event_spinnerFactorCapacidadStateChanged

    private void listaAlgoritmosRuteoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaAlgoritmosRuteoMouseClicked
        // TODO add your handling code here:
        this.panelAsignacionSpectro.setVisible(true);
        
        
        
    }//GEN-LAST:event_listaAlgoritmosRuteoMouseClicked

    private void listaAlgoritmosASMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaAlgoritmosASMouseClicked
        // TODO add your handling code here:
        this.botonGuardarAS.setEnabled(true);
        
    }//GEN-LAST:event_listaAlgoritmosASMouseClicked

    private void botonGuardarASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarASActionPerformed
        // TODO add your handling code here:
        List algoritmosRuteoSeleccionados= new LinkedList();
        List algoritmosASpectroSeleccionados = new LinkedList();
        algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
        if(algoritmosRuteoSeleccionados.size()>0){
            algoritmosASpectroSeleccionados = this.listaAlgoritmosAS.getSelectedValuesList();
            if(algoritmosASpectroSeleccionados.size()>0){
                boolean modulacion = this.checkBotonModulacion.isSelected();
                List ruteoASmodulacionAejecutar = new LinkedList();
                for(int i =0; i<algoritmosRuteoSeleccionados.size();i++){
                    ruteoASmodulacionAejecutar.add(algoritmosRuteoSeleccionados.get(i)); // en el primer lugar guardamos el algoritmo de ruteo seleccionando
                    ruteoASmodulacionAejecutar.add(modulacion);
                    for(int j=0;j<algoritmosASpectroSeleccionados.size();j++){
                        ruteoASmodulacionAejecutar.add(algoritmosASpectroSeleccionados.get(j));
                    }
                } 
            }
        }
        List listaAuxilar = new LinkedList();
        for(int i=0; i<algoritmosRuteoSeleccionados.size();i++){
            listaAuxilar.add(algoritmosRuteoSeleccionados.get(i));
            listaAuxilar.add(this.checkBotonModulacion.isSelected());
            String ruteo = (String)algoritmosRuteoSeleccionados.get(i);
            for(int j=0; j<algoritmosASpectroSeleccionados.size();j++){
                listaAuxilar.add(algoritmosASpectroSeleccionados.get(j));
                 String nombreCompletoAlgoritmo;
                if(this.checkBotonModulacion.isSelected()){
                    nombreCompletoAlgoritmo = ruteo+" - M"+" - "+(String)algoritmosASpectroSeleccionados.get(j);
                }else{
                    nombreCompletoAlgoritmo = ruteo+" - SM"+" - "+(String)algoritmosASpectroSeleccionados.get(j);
                }
                
                
                boolean nombreIgual=false;
                for(int a =0;a<this.algoritmosCompletosParaGraficar.size();a++){
                    if(this.algoritmosCompletosParaGraficar.get(a).equals(nombreCompletoAlgoritmo)){
                        nombreIgual=true;
                    }
                }
                if(!nombreIgual){
                    this.algoritmosCompletosParaGraficar.add(cantidadDeAlgoritmosTotalSeleccionados, nombreCompletoAlgoritmo);
                    this.cantidadDeAlgoritmosTotalSeleccionados++;
                }
            }
            this.algoritmosCompletosParaEjecutar.add(listaAuxilar);
            
        }
        
        this.cantidadDeAlgoritmosRuteoSeleccionados+=algoritmosRuteoSeleccionados.size();
        this.panelAsignacionSpectro.setVisible(false);
        
                
        
    }//GEN-LAST:event_botonGuardarASActionPerformed

    private void botonCancelarASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarASActionPerformed
        // TODO add your handling code here:
        this.panelAsignacionSpectro.setVisible(false);
    }//GEN-LAST:event_botonCancelarASActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelarAS;
    private javax.swing.JButton botonEjecutarSimulacion;
    private javax.swing.JButton botonGuardarAS;
    private javax.swing.JCheckBox checkBotonModulacion;
    private javax.swing.JLabel etiqueta1;
    private javax.swing.JLabel etiquetaAnchoFSActual;
    private javax.swing.JLabel etiquetaCapacidadActual;
    private javax.swing.JLabel etiquetaDemanda;
    private javax.swing.JLabel etiquetaDemandasTotales;
    private javax.swing.JLabel etiquetaError;
    private javax.swing.JLabel etiquetaFactorTiempo;
    private javax.swing.JLabel etiquetaImagen;
    private javax.swing.JLabel etiquetaRSA;
    private javax.swing.JLabel etiquetaRSA1;
    private javax.swing.JLabel etiquetaResultado;
    private javax.swing.JLabel etiquetaTextoDemandasTotales;
    private javax.swing.JLabel etiquetaTiempoActual;
    private javax.swing.JLabel etiquetaTopologia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList listaAlgoritmosAS;
    private javax.swing.JList<String> listaAlgoritmosRuteo;
    private javax.swing.JList<String> listaDemandas;
    private javax.swing.JList<String> listaRedes;
    private javax.swing.JPanel panelAsignacionSpectro;
    private javax.swing.JPanel panelResultado;
    private javax.swing.JSpinner spinnerEarlang;
    private javax.swing.JSpinner spinnerFactorCapacidad;
    private javax.swing.JSpinner spinnerFactorTiempo;
    private javax.swing.JSpinner spinnerPaso;
    private javax.swing.JSpinner spinnerTiempoSimulacion;
    private javax.swing.JTextField textFieldAnchoFS;
    private javax.swing.JTextField textFieldCapacidadEnlace;
    // End of variables declaration//GEN-END:variables
  
   
}

