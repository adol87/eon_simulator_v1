package Interfaces;
import EON.Utilitarios.*;
import EON.*;
import EON.Metricas.*;
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
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import java.awt.event.*;
import java.awt.*;

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

    

public class VentanaPrincipal_Defrag extends javax.swing.JFrame {

    private Topologias Redes; // topologias disponibles
    private double factorTiempo; // factor de tiempo indicado por el usuario
    private double factorCapacidad; // cantidad de FSs que contendra cada demanda
    private int capacidadPorEnlace; // cantidad de FSs por enlace en la topologia elegida
    private int tiempoTotal; // Iiempo que dura una simualcion
    private double anchoFS; // ancho de un FS en los enlaces
    private int cantidadRedes; //cantidad de redes exitentes en el Simulador
    
    //hilo
    hilobar hilobar1;
    
    
    
    public VentanaPrincipal_Defrag() {
        initComponents();
        /* Inicialmente no se muestran en panel para editar las caracteristicas de los enlaces
        */
        this.panelCambiarDatosdelosEnlaces.setVisible(false);
        this.botonCambiarDatosdelosEnlaces.setEnabled(false);
        this.PaneleEditarEnalce.setVisible(false);
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
        this.PanelMSI.setVisible(false);
        this.etiquetaMSI.setVisible(false);
        this.panelBFR.setVisible(false);
        
        this.setTitle("EON Simulator");
        
    }

    public Timer timer;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaRedes = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaRSA = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaDemandas = new javax.swing.JList<>();
        botonEjecutarSimulacion = new javax.swing.JButton();
        etiquetaTopologia = new javax.swing.JLabel();
        etiquetaRSA = new javax.swing.JLabel();
        etiquetaDemanda = new javax.swing.JLabel();
        etiquetaError = new javax.swing.JLabel();
        panelCambiarDatosdelosEnlaces = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaEnlaces = new javax.swing.JList<>();
        PaneleEditarEnalce = new javax.swing.JPanel();
        bGuardar = new javax.swing.JButton();
        campoDeTextoEspectro = new javax.swing.JTextField();
        campoDeTextoAnchoFS = new javax.swing.JTextField();
        etiquetaEspectro = new javax.swing.JLabel();
        etiquetaAnchoFS = new javax.swing.JLabel();
        etiquetaEditarEnlace = new javax.swing.JLabel();
        etiquetaErrorEditarEnlace = new javax.swing.JLabel();
        botonCerrarEditarEnlace = new javax.swing.JButton();
        botonCerrarCerrarDatosEnlaces = new javax.swing.JButton();
        etiquetaTituloDeEnlaces = new javax.swing.JLabel();
        botonCambiarDatosdelosEnlaces = new javax.swing.JButton();
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
        etiquetaMSI = new javax.swing.JLabel();
        etiquetaImagen = new javax.swing.JLabel();
        panelBFR = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        etiqueta1 = new javax.swing.JLabel();
        etiqueta2 = new javax.swing.JLabel();
        etiquetaTextoDemandasTotales = new javax.swing.JLabel();
        etiquetaDemandasTotales = new javax.swing.JLabel();
        etiquetaGHz = new javax.swing.JLabel();
        etiquetaValorGHz = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinnerEarlang = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        spinnerPaso = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        textFieldCapacidadEnlace = new javax.swing.JTextField();
        textFieldAnchoFS = new javax.swing.JTextField();
        PanelMSI = new javax.swing.JPanel();
        etiquetaBFR = new javax.swing.JLabel();
        bar1 = new javax.swing.JProgressBar();
        lbl1 = new javax.swing.JLabel();
        btndefrag = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        listdefrag = new javax.swing.JList<>();
        lbldefrag = new javax.swing.JLabel();
        txtdefrag = new javax.swing.JTextField();
        btninsertar1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        listaRSADF = new javax.swing.JList<>();
        panelResultado = new javax.swing.JPanel();
        PanelEntropia = new javax.swing.JPanel();
        etiquetaResultado = new javax.swing.JLabel();
        etiquetaEntropia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 112, 58));

        listaRSA.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "FAR - FF", "Def P-CF", "Def CBD", "Def MBBR", "Def DWD", "FA CA RSA", "FA RSA", "FAR - FF - SP - DF", "FAR - FF - GREEDY - DF", "FAR - FF - MSGD - DF", "FAR - LF", "FAR - RF", "FAR - EF", "FAR - LU", "FAR - MU" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaRSA.setToolTipText("");
        jScrollPane2.setViewportView(listaRSA);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 112, 110));

        listaDemandas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Tiempo de permanecia y FS Fijos", "Tiempo de permanecia Fijo y FS Variables" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listaDemandas);
        listaDemandas.getAccessibleContext().setAccessibleName("");
        listaDemandas.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, 73));

        botonEjecutarSimulacion.setText("Simular");
        botonEjecutarSimulacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEjecutarSimulacionMouseClicked(evt);
            }
        });
        botonEjecutarSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarSimulacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonEjecutarSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 111, 46));
        botonEjecutarSimulacion.getAccessibleContext().setAccessibleDescription("");
        botonEjecutarSimulacion.getAccessibleContext().setAccessibleParent(botonEjecutarSimulacion);

        etiquetaTopologia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaTopologia.setText("Topologia");
        getContentPane().add(etiquetaTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        etiquetaRSA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA.setText("Algoritmo RSA");
        getContentPane().add(etiquetaRSA, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        etiquetaDemanda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaDemanda.setText("Demanda");
        getContentPane().add(etiquetaDemanda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));
        getContentPane().add(etiquetaError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 394, 23));

        panelCambiarDatosdelosEnlaces.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelCambiarDatosdelosEnlaces.setEnabled(false);

        listaEnlaces.setToolTipText("");
        listaEnlaces.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaEnlacesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listaEnlaces);

        bGuardar.setText("Guardar");
        bGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGuardarActionPerformed(evt);
            }
        });

        botonCerrarEditarEnlace.setText("Cerrar");
        botonCerrarEditarEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarEditarEnlaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PaneleEditarEnalceLayout = new javax.swing.GroupLayout(PaneleEditarEnalce);
        PaneleEditarEnalce.setLayout(PaneleEditarEnalceLayout);
        PaneleEditarEnalceLayout.setHorizontalGroup(
            PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneleEditarEnalceLayout.createSequentialGroup()
                .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaneleEditarEnalceLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(etiquetaEditarEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaneleEditarEnalceLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PaneleEditarEnalceLayout.createSequentialGroup()
                                .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(etiquetaAnchoFS, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addComponent(etiquetaEspectro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoDeTextoEspectro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoDeTextoAnchoFS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PaneleEditarEnalceLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(bGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonCerrarEditarEnlace))))
                    .addComponent(etiquetaErrorEditarEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PaneleEditarEnalceLayout.setVerticalGroup(
            PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneleEditarEnalceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaEditarEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoDeTextoEspectro)
                    .addComponent(etiquetaEspectro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoDeTextoAnchoFS)
                    .addComponent(etiquetaAnchoFS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(PaneleEditarEnalceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bGuardar)
                    .addComponent(botonCerrarEditarEnlace))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(etiquetaErrorEditarEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        botonCerrarCerrarDatosEnlaces.setText("Cerrar");
        botonCerrarCerrarDatosEnlaces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarCerrarDatosEnlacesActionPerformed(evt);
            }
        });

        etiquetaTituloDeEnlaces.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaTituloDeEnlaces.setText("Enlaces");

        javax.swing.GroupLayout panelCambiarDatosdelosEnlacesLayout = new javax.swing.GroupLayout(panelCambiarDatosdelosEnlaces);
        panelCambiarDatosdelosEnlaces.setLayout(panelCambiarDatosdelosEnlacesLayout);
        panelCambiarDatosdelosEnlacesLayout.setHorizontalGroup(
            panelCambiarDatosdelosEnlacesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(botonCerrarCerrarDatosEnlaces)
                .addGap(18, 18, 18)
                .addComponent(PaneleEditarEnalce, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
            .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                .addGroup(panelCambiarDatosdelosEnlacesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(etiquetaTituloDeEnlaces))
                    .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelCambiarDatosdelosEnlacesLayout.setVerticalGroup(
            panelCambiarDatosdelosEnlacesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCambiarDatosdelosEnlacesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                        .addComponent(etiquetaTituloDeEnlaces, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonCerrarCerrarDatosEnlaces))
                    .addGroup(panelCambiarDatosdelosEnlacesLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(PaneleEditarEnalce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        getContentPane().add(panelCambiarDatosdelosEnlaces, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, 250));

        botonCambiarDatosdelosEnlaces.setText("Cambiar Datos de los Enlaces");
        botonCambiarDatosdelosEnlaces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCambiarDatosdelosEnlacesActionPerformed(evt);
            }
        });
        getContentPane().add(botonCambiarDatosdelosEnlaces, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 190, 20));

        etiquetaCapacidadActual.setText("Capacidad");
        getContentPane().add(etiquetaCapacidadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 110, 20));

        etiquetaTiempoActual.setText("Tiempo de Simulacion");
        getContentPane().add(etiquetaTiempoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 180, 20));

        etiquetaAnchoFSActual.setText("Ancho FS");
        getContentPane().add(etiquetaAnchoFSActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 110, 20));

        etiquetaFactorTiempo.setText("Tiempo de Pemancia");
        getContentPane().add(etiquetaFactorTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 110, 20));

        spinnerTiempoSimulacion.setModel(new javax.swing.SpinnerNumberModel(100, 50, 100000, 25));
        getContentPane().add(spinnerTiempoSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, 20));

        jLabel1.setText("FSs por demanda");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 100, 20));

        spinnerFactorCapacidad.setModel(new javax.swing.SpinnerNumberModel(2, 2, 12, 1));
        spinnerFactorCapacidad.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerFactorCapacidadStateChanged(evt);
            }
        });
        getContentPane().add(spinnerFactorCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 70, 20));

        jLabel2.setText("FSs por Enlace");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, 20));

        jLabel3.setText("GHz");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 30, 20));

        spinnerFactorTiempo.setModel(new javax.swing.SpinnerNumberModel(0.1d, 0.001d, 0.9d, 0.01d));
        getContentPane().add(spinnerFactorTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 70, 20));

        etiquetaMSI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaMSI.setText("Promedio de MSI");
        getContentPane().add(etiquetaMSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 610, 180, 20));
        etiquetaMSI.getAccessibleContext().setAccessibleName("Entropia");

        getContentPane().add(etiquetaImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 320, 170));

        panelBFR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelBFRLayout = new javax.swing.GroupLayout(panelBFR);
        panelBFR.setLayout(panelBFRLayout);
        panelBFRLayout.setHorizontalGroup(
            panelBFRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
        panelBFRLayout.setVerticalGroup(
            panelBFRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        getContentPane().add(panelBFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 640, 470, 280));

        jLabel5.setText("Unidades");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 20));

        etiqueta1.setText("Del tiempo de Simulacion");
        getContentPane().add(etiqueta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, -1, 20));

        etiqueta2.setText(" FSs");
        getContentPane().add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, -1, 20));

        etiquetaTextoDemandasTotales.setText("Cantidad total de Demandas");
        getContentPane().add(etiquetaTextoDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 250, 200, 20));
        getContentPane().add(etiquetaDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 270, 90, 30));

        etiquetaGHz.setText("GHz");
        getContentPane().add(etiquetaGHz, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 420, 30, 20));
        getContentPane().add(etiquetaValorGHz, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 40, 20));

        jLabel4.setText("Carga de Trafico Maximo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 120, 20));

        spinnerEarlang.setModel(new javax.swing.SpinnerNumberModel(500, 10, 1500, 100));
        spinnerEarlang.setValue(100);
        getContentPane().add(spinnerEarlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 80, -1));

        jLabel6.setText("Earlang");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 40, 20));

        jLabel7.setText("Paso (u)");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, -1));

        spinnerPaso.setModel(new javax.swing.SpinnerNumberModel(10, 1, 100, 10));
        getContentPane().add(spinnerPaso, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, -1, -1));

        jLabel8.setText("Earlang");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 50, 20));

        textFieldCapacidadEnlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCapacidadEnlace.setText("50");
        getContentPane().add(textFieldCapacidadEnlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 80, -1));

        textFieldAnchoFS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldAnchoFS.setText("2");
        getContentPane().add(textFieldAnchoFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 80, 20));

        PanelMSI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout PanelMSILayout = new javax.swing.GroupLayout(PanelMSI);
        PanelMSI.setLayout(PanelMSILayout);
        PanelMSILayout.setHorizontalGroup(
            PanelMSILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
        PanelMSILayout.setVerticalGroup(
            PanelMSILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        getContentPane().add(PanelMSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 640, 470, 280));
        PanelMSI.getAccessibleContext().setAccessibleName("");

        etiquetaBFR.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaBFR.setText("Promedio de BFR");
        getContentPane().add(etiquetaBFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 610, 180, 20));

        bar1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bar1StateChanged(evt);
            }
        });
        getContentPane().add(bar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 554, 260, 20));
        getContentPane().add(lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 610, -1, -1));

        btndefrag.setText("Desfragmentar");
        btndefrag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndefragMouseClicked(evt);
            }
        });
        btndefrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndefragActionPerformed(evt);
            }
        });
        getContentPane().add(btndefrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 480, 111, 46));

        jScrollPane5.setViewportView(listdefrag);

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 30, 100, 140));

        lbldefrag.setText("Pasos - Desfragmentación");
        getContentPane().add(lbldefrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 10, 140, -1));

        txtdefrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdefragActionPerformed(evt);
            }
        });
        txtdefrag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdefragKeyPressed(evt);
            }
        });
        getContentPane().add(txtdefrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 30, 70, -1));

        btninsertar1.setText("Limpiar");
        btninsertar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninsertar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btninsertar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 60, 70, -1));

        jLabel9.setText("Earlang");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 10, 40, 20));

        listaRSADF.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "FAR - FF - SP - DF", "FAR - FF - GREEDY - DF" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaRSADF.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaRSADF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaRSADFMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(listaRSADF);

        getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1042, 90, 100, 58));

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

        getContentPane().add(panelResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 470, 280));

        PanelEntropia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout PanelEntropiaLayout = new javax.swing.GroupLayout(PanelEntropia);
        PanelEntropia.setLayout(PanelEntropiaLayout);
        PanelEntropiaLayout.setHorizontalGroup(
            PanelEntropiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );
        PanelEntropiaLayout.setVerticalGroup(
            PanelEntropiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        getContentPane().add(PanelEntropia, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 320, 470, 280));

        etiquetaResultado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaResultado.setText("Probabilidad de bloqueo");
        getContentPane().add(etiquetaResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, 180, 20));

        etiquetaEntropia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaEntropia.setText("Promedio Entropía");
        getContentPane().add(etiquetaEntropia, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 290, 150, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /////////////////
    
    
    
    
    private void botonEjecutarSimulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarSimulacionActionPerformed
        
        
        System.out.println("Llamar a Utilitarios");
        Utilitarios.printUtil();
        
        bar1.setValue(0);
        this.etiquetaError.setText("");
        this.etiquetaError.setVisible(true);
        this.etiquetaError.update(this.etiquetaError.getGraphics());
        
        
        
        /* Al inicio de cada Simulacion e+condemos los paneles de Resultado
        */
        this.etiquetaMSI.setVisible(false);
        this.panelBFR.setVisible(false);
        
        
       
        
        Demanda d= new Demanda();  // Demanda a recibir
        Resultado r=new Resultado(); // resultado obtenido en una demanda. Si r==null se cuenta como bloqueo
        String mensajeError = "Seleccione: "; // mensaje de error a mostrar eal usuario si no ha completado los parametros de
                                               // simulacion
        
        List<String> RSA = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        
        
        int E=(int)this.spinnerEarlang.getValue(); // se obtiene el limite de carga (Erlang) de trafico seleccionado por el usuario
        int demandasPorUnidadTiempo=0; //demandas por unidad de tiempo. Considerando el punt decimal
        int earlang=0; //Carga de trafico en cada simulacion
        int k=-1; // contador auxiliar
        int paso=(int)this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD=0; // contador de demandas totales
        int tiempoT=Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        int capacidadE=Integer.parseInt(this.textFieldCapacidadEnlace.getText().toString()); // espectro por enalce
        double anchoFS=Double.parseDouble(this.textFieldAnchoFS.getText().toString()); // ancho de FS
        
        GrafoMatriz Gaux = new GrafoMatriz();
        
        
        //hilos
        
            
            
        
            
            
           
        //E = 720;
        //factor del tiempo de simulacion especificado por el usuario
        
        System.out.println("El ancho del FS es:"+anchoFS);
        System.out.println("Cantidad de FS por enlace: "+capacidadE);
        
        int t=(int)(Double.parseDouble(this.spinnerFactorTiempo.getValue().toString())*tiempoT);
        //cantidad de FS por demanda expresado pro el usuario
        int b=(int)(Double.parseDouble(this.spinnerFactorCapacidad.getValue().toString()));
        
        if(this.listaDemandas.getSelectedIndex()>=0 && listaRSA.getSelectedIndex()>=0 && 
                this.listaRedes.getSelectedIndex()>=0){ // si todos los parametros fueron seleccionados
           this.etiquetaError.setVisible(true); // habilitamos la etiqueta de error
           /////////////////////////////////////
           hilobar1=new hilobar(bar1,1,1,etiquetaError);
            Thread hbar1=new Thread(hilobar1);
            //bar1.setIndeterminate(true);
            
            hbar1.start();
            
           
           
           
            RSA= this.listaRSA.getSelectedValuesList(); // obtenemos los algoritmos RSA seleccionados
            
            GrafoMatriz G [] = new GrafoMatriz[RSA.size()]; // Se tiene una matriz de adyacencia por algoritmo RSA en el Simulador
        
            String redSeleccionada = this.listaRedes.getSelectedValue(); // obtenemos la topologia seleccionada
            String demandaSeleccionada = this.listaDemandas.getSelectedValue(); // obtenemos el tipo de trafico seleccionado
            System.out.println(demandaSeleccionada +"++");
            int [] contB=new int [RSA.size()]; // array encargado de almacenar en cada posicion la cantidad de bloqueo para cada
                                                // algoritmo seleccionado


            List prob []= new LinkedList[RSA.size()]; // probabilidad de bloqueo para cada algoritmo RSA selecciondo 
            List UEnet []= new LinkedList[RSA.size()];// entropia de red para cada algoritmo RSA
            List BFRnet []= new LinkedList[RSA.size()];//Bfr de red
            List MSInet [] = new LinkedList[RSA.size()];//Msi de la red
            
            for(int i=0;i<prob.length;i++){
                prob[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            }
            
            for(int i=0;i<UEnet.length;i++){
                UEnet[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            }
            
            for(int i=0;i<BFRnet.length;i++){
                BFRnet[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            }
            
             for(int i=0;i<MSInet.length;i++){
                MSInet[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            }
            
            
            switch(redSeleccionada){ // cagamos los datos en las matrices de adyacencia segun la topologia seleccionada
                case "Red 0":
                    //de ´rueba no utilizado
                    for(int i=0;i<G.length;i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(0).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(0));
                        
                    }
                    break;
                case "Red 1":
                    for(int i=0;i<G.length;i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(1));
                        Gaux=new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        Gaux.insertarDatos(this.Redes.getTopologia(1));
                    }
                    break;
                case "Red 2":
                    for(int i=0;i<G.length;i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(2));
                        Gaux=new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        Gaux.insertarDatos(this.Redes.getTopologia(2));
                    }
            }
           
            ArrayList<Demanda>  connection1= new ArrayList<Demanda>();
            ArrayList<Demanda>  connection2= new ArrayList<Demanda>();
            
            Number [] earlangprev= new Number[3];
            Number [] earlangprev2 = new Number[3];
            int perc = 4;
            
           
            
            //se especifican los rangos para ejecutar el greedy y el otro de DF
            int cant = E/paso;
            /*for (int i=0;i<3 && i<cant;i++){
                
                Random lim = new Random();
                if (cant>2){
                    int nro = 1 + lim.nextInt(cant-2);
                
                    while(Utilitarios.isInList((int)earlangprev, nro)){
                        nro = 1+ lim.nextInt(cant-1);
                    }
                    earlangprev[i] = nro*paso;
                    earlangprev2[i] = nro*paso;
                    
                    
                }else{
                   earlangprev[i] = cant;
                    earlangprev2[i] = cant; 
                }
                
            }
            Utilitarios.ordenar(earlangprev);
            Utilitarios.ordenar(earlangprev2);*/
            
            earlangprev[0] = E*0.25;
            earlangprev[1] = E*0.50;
            earlangprev[2] = E*0.75;
            
            earlangprev2[0] = E*0.25;
            earlangprev2[1] = E*0.50;
            earlangprev2[2] = E*0.75;
            
            Utilitarios.buscarCercano(earlangprev, E, paso);
            Utilitarios.buscarCercano(earlangprev2, E, paso);
            
            
            
            int contearl=0,itersp=0,itergreedy=0,turnoDF=0,turnoGR=0;
            boolean selectoDF=false, selectoGR=false;
            

            int [] conexid=new int [RSA.size()]; 
            
            for (int i=0; i<conexid.length; i++){
                conexid[i] = 0;
            }
            
            //ArrayList<ListaEnlazadaAsignadas> lea = new ArrayList<ListaEnlazadaAsignadas>();
            ArrayList<ListaEnlazadaAsignadas> [] lea = (ArrayList<ListaEnlazadaAsignadas>[]) new ArrayList[RSA.size()];
            
            
            for (int i=0; i<RSA.size(); i++){
                lea[i] = new ArrayList<ListaEnlazadaAsignadas>();
            }
            
            int expira= 0;
            //int treshold = 30+tiempoT;
            int treshold = 10;
           
            
           while(earlang<=E){ // mientras no se llega a la cargad de trafico maxima
               
                contearl++;
                boolean defrag=false;
                boolean defrag2=false;
                //if (contearl==1) {
                
                if (earlang==50){
                    System.out.println("");
                }
                if (selectoDF && turnoDF<3 && earlangprev[turnoDF].intValue()==earlang) {
                    defrag=true;
                    turnoDF++;
                    Collections.sort(connection1, new Comparator<Demanda>() {
                    public int compare(Demanda one, Demanda other) {
                        if(one.getNroFS()>other.getNroFS())
                            return 1;
                        if(one.getNroFS()<other.getNroFS())
                            return -1;
                        return 0;
                    }}); 
                    //System.out.println("df #: " + ++contearl);
                    //System.in.read();
                }
                if (selectoGR && turnoGR<3 && earlangprev2[turnoGR].intValue()==earlang) {
                    defrag2=true;
                    Collections.sort(connection2, new Comparator<Demanda>() {
                    public int compare(Demanda one, Demanda other) {
                        if(one.getNroFS()>other.getNroFS())
                            return 1;
                        if(one.getNroFS()<other.getNroFS())
                            return -1;
                        return 0;
                    }}); 
                    //System.out.println("df #: " + ++contearl);
                    //System.in.read();
                }
                //calculo de entropia de utilizacion de la red
                /*
                for(int a=0; a<RSA.size();a++){ // para cada algoritmo seleccionado
                    double entropy;
                    String algoritmoSeleccionado = RSA.get(a);
                    switch(algoritmoSeleccionado){ // vemos cual algoritmo ha sido selecciondo, y realizamos la ejecucion de cada uno
                                                   // en su correspondiente Topologia
                        case "MPSC":
                            
                            break;
                        case "MTLSC":
                            
                            break;
                        case "FAR - EF"://g4
                            
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("Entropia-metodo:::::"+ G[4].entropia()+"::::::::::earlang:::::: "+earlang);
                            //System.in.read();
                            
                            break;
                        case "FAR - FF"://g2
                            UEnet[a].add(G[a].entropia());
                            //BFR[a].add(Metricas.BFR(G[a], capacidadE));
                            //System.out.println("inicio debe ser cero:::::"+ G[2].entropia());
                            //System.in.read();
                            
                            break;
                        case "FAR - LF"://g7
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                            break;
                        case "FAR - RF"://g3
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                            
                            break;
                        case "FAR - LU"://g5
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                            
                            break;
                        case "FAR - MU"://g6
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                            
                            break;
                        case "FAR - FF - SP - DF"://g8
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                            
                            break;
                        case "FAR - FF - GREEDY - DF"://g9
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                        case "FAR - FF - MSGD - DF"://g10
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                            
                        case "FA RSA"://g11
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                        case "FA CA RSA"://g12
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                        case "Def DWD"://g13
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                        case "Def MBBR"://g14
                            UEnet[a].add(G[a].entropia());
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                        case "Def CBD"://g15
                            UEnet[a].add(G[a].entropia());
                            //BFR[a].add(Metricas.BFR(G[a], capacidadE));
                            //System.out.println("inicio debe ser cero:::::"+ entropy);
                            //System.in.read();
                        
                            break;
                    }//fin case
                    
                    
                }//fin for
                */
                
                
                /////////////////////////////
                
                  for(int i=0;i<tiempoT;i++){ // para cada unidad de tiempo
                      boolean cbd=false, ff=false;
                   demandasPorUnidadTiempo = Utilitarios.demandasTotalesPorTiempo(earlang, t); // obtener las demandas
                                                                                               // por unidad de tiempo
                     for(int j=0;j<demandasPorUnidadTiempo;j++){ // para cada demanda
                        switch(demandaSeleccionada){ // se ve que modelo de trafico fue seleccionado
                        case "Tiempo de permanecia y FS Fijos":
                             d.obtenerDemandasFijo(b, t,G[0].getCantidadDeVertices()-1);  // obtenemos un origen, fin, catidad de FS y tiempo de permanecia
                             break;
                        case "Tiempo de permanecia Fijo y FS Variables":
                            d.obtenerDemandasFSVariable(b, t,G[0].getCantidadDeVertices()-1);  // obtenemos un origen, fin, catidad de FS y tiempo de permanecia
                             break;
                        } 
                        
                        ListaEnlazada[] ksp=Utilitarios.KSP(G[0], d.getOrigen(), d.getDestino(), 5); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=3
                        
                        for(int a=0; a<RSA.size();a++){ // para cada algoritmo seleccionado
                            String algoritmoSeleccionado = RSA.get(a);
                            switch(algoritmoSeleccionado){ // vemos cual algoritmo ha sido selecciondo, y realizamos la ejecucion de cada uno
                                                           // en su correspondiente Topologia
                                case "MPSC":
                                    r=Algoritmos.MPSC_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                    }
                                    break;
                                case "MTLSC":
                                    r=Algoritmos.MTLSC_Algorithm(G[a], d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                     else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - EF":
                                    r=Algoritmos.FAR_EF_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - FF":
                                    r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                        ff = true;
                                        
                                    }
                                    break;
                                    case "FAR - LF":
                                    r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - RF":
                                    r=Algoritmos.KSP_RF_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - LU":
                                    r=Algoritmos.KSP_LU_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                    }
                                    break;
                                case "FAR - MU":
                                    r=Algoritmos.KSP_MU_Algorithm(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                    }  
                                    else{
                                        contB[a]++;
                                    }
                                    break;
                                //
                                    // El Shortest Path Defragmentation usa el camino mas corto (distancia fisica entre nodos)
                                //
                                case "FAR - FF - SP - DF":
                                    
                                    selectoDF = true;
                                    
                                    r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                    
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                        connection1.add(d);
                                        
                                    }  
                                    else{
                                        contB[a]++;
                                        
                                        
                                    }
                                    if (defrag==true) {
                                        //System.out.println(":::::::::::.defrag numero-sp-df: "+ ++itersp);
                                        
                                        contearl=0;
                                        
                                        G[a].ResetRed();
                                        
                                        
                                        int did=0;
                                        for(int kk=0;kk<connection1.size();kk++){
                                            Demanda df=connection1.get(kk);
                                            ListaEnlazada[] kspdf=Utilitarios.KSP(G[a], df.getOrigen(), df.getDestino(), 3); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=3
                                            r=Algoritmos.KSP_FF_Algorithm(G[a],df,kspdf,capacidadE);
                                            
                                            if(r!=null){
                                                Utilitarios.asignarFS_Defrag(kspdf,r,G[a],df,++did);
                                                
                                            }  
                                            
                                        }
                                        
                                        
                                        defrag=false;
                                    }
                                    
                                    
                                    break;
                                    //
                                        // El Greedy Defragmentation usa el camino con menos saltos (hops)
                                    //
                                case "FAR - FF - GREEDY - DF":
                                     
                                    selectoGR = true;
                                        
                                    
                                    r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                    
                                    if(r!=null){
                                        Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                        connection2.add(d);
                                        
                                    }  
                                    else{
                                        contB[a]++;
                                        
                                        
                                    }
                                    if (defrag2==true) {
                                        //System.out.println(":::::::::::.defrag numero-greedy-df: "+ ++itergreedy);
                                        
                                        //se reinicializa la red
                                        G[a].ResetRed();
                                        
                                        int did=0;
                                        for(int kk=0;kk<connection2.size();kk++){
                                            
                                            Demanda df=connection2.get(kk);
                                            ListaEnlazada[] kspdf=Utilitarios.KSP(G[a], df.getOrigen(), df.getDestino(), 8); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=6
                                            
                                            r=Algoritmos.KSP_FF_Algorithm2_Greedy(G[a],df,kspdf,capacidadE);
                                            
                                            
                                            if(r!=null){
                                                Utilitarios.asignarFS_Defrag(kspdf,r,G[a],df,++did);
                                            
                                            }  
                                            
                                        }
                                        
                                        
                                        defrag2=false;
                                    }
                                    
                                    
                                    break;
                                    
                                    case "FAR - FF - MSGD - DF":
                                    
                                    //r=Algoritmos_Defrag.Def_MSGD(G[8],d,ksp,capacidadE);
                                    r=Algoritmos_Defrag.Def_MSGD(G[a],d,ksp,capacidadE);
                                    if(r!=null){
                                        Utilitarios.asignarFS_saveRoute(ksp,r,G[a],d,lea[a], -1);

                                    }  
                                    else{
                                        contB[a]++;
                                        
                                    }
                                    break;
                                    case "FA RSA":
                                        r=Algoritmos_Defrag.Def_FA(G[a],d,ksp,capacidadE);
                                        if(r!=null){
                                           Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                        }  
                                        else{
                                            contB[a]++;
                                        }
                                    break;
                                    case "FA CA RSA":
                                        r=Algoritmos_Defrag.Def_FACA(G[a],d,ksp,capacidadE);
                                        if(r!=null){
                                           Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                        }  
                                        else{
                                            contB[a]++;
                                        }
                                    break;
                                    case "Def DWD":
                                        r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                        if(r!=null){
                                           Utilitarios.asignarFS_saveRoute(ksp,r,G[a],d,lea[a], -1);
                                           if (contD>2){
                                                Algoritmos_Defrag.DWD(d,G[a],lea[a],capacidadE);
                                           }
                                        }  
                                        else{
                                            contB[a]++;
                                        }
                                    break;
                                    case "Def MBBR":
                                        r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                        if(r!=null){
                                           Utilitarios.asignarFS_saveRoute(ksp,r,G[a],d,lea[a], -1);
                                        }else{
                                            Gaux.restablecerFS();
                                            r = Algoritmos_Defrag.MBBR(d,G[a], Gaux,lea[a],capacidadE, ksp);
                                            if (r!=null){
                                                Utilitarios.asignarFS_saveRoute(ksp,r,G[a],d,lea[a], -1);
                                            }else{
                                                contB[a]++;
                                            }
                                            Gaux.restablecerFS();
                                            
                                        }
                                    break;
                                    case "Def CBD":
                                        r=Algoritmos.KSP_FF_Algorithm(G[a],d,ksp,capacidadE);
                                        if(r!=null){
                                            Utilitarios.asignarFS_saveRoute(ksp,r,G[a],d,lea[a], -1);
                                        }else{
                                            contB[a]++;
                                            cbd = true;
                                        }
                                    break;
                                    case "Def P-CF":
                                        r=Algoritmos_Defrag.PCF(G[a],d,ksp,capacidadE);
                                        if(r!=null){
                                            Utilitarios.asignarFS_Defrag(ksp,r,G[a],d,++conexid[a]);
                                        }else{
                                            contB[a]++;
                                        }
                                    break;
                                    
                                    
                                
                                }//fin case
                            
                            }
                        /*if (cbd==true && ff==false){
                            
                            System.out.println("FF");
                            Utilitarios.imprimirTopologia(G[2], capacidadE);
                            System.out.println("DCB");
                            Utilitarios.imprimirTopologia(G[15], capacidadE);
                            System.out.println("No anda papa");
                        }
                        System.out.println("FF");
                        Utilitarios.imprimirTopologia(G[2], capacidadE);
                        System.out.println("DCB");
                        Utilitarios.imprimirTopologia(G[15], capacidadE);*/
                        contD++; 
                    }
                     // para cada Algoritmo y su correspondiente topologia, disminuimos el tiempo de permanecia
                    for(int a=0;a<RSA.size();a++){
                        if(RSA.get(a)=="MPSC"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="MTLSC"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - FF"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - LF"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - RF"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - EF"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - LU"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - MU"){
                            Utilitarios.Disminuir(G[a]);
                        }
                        else if(RSA.get(a)=="FAR - FF - SP - DF"){
                            Utilitarios.Disminuir(G[a]);
                            //disminuir el tiempo de la demanda(sin utilizar la topologia)
                            for(int kk=0;kk<connection1.size();kk++){
                                if(connection1.get(kk).getTiempo()!=0 && connection1.get(kk).getTiempo()!=1){
                                    Demanda aux=new Demanda(connection1.get(kk).getOrigen(),connection1.get(kk).getDestino(),connection1.get(kk).getNroFS(),connection1.get(kk).getTiempo()-1);
                                    connection1.set(kk, aux);

                                }
                                else{
                                    connection1.remove(kk);
                                }

                            }
                        }
                        else if(RSA.get(a)=="FAR - FF - GREEDY - DF"){
                            Utilitarios.Disminuir(G[a]);
                            //disminuir el tiempo de la demanda(sin utilizar la topologia)
                            for(int kk=0;kk<connection2.size();kk++){
                                if(connection2.get(kk).getTiempo()!=0 && connection2.get(kk).getTiempo()!=1){
                                    Demanda aux=new Demanda(connection2.get(kk).getOrigen(),connection2.get(kk).getDestino(),connection2.get(kk).getNroFS(),connection2.get(kk).getTiempo()-1);
                                    connection2.set(kk, aux);

                                }
                                else{
                                    connection2.remove(kk);
                                }

                            }
                        }
                        else if(RSA.get(a)=="FAR - FF - MSGD - DF"){
                            Utilitarios.Disminuir_MSGD(G[a],lea[a], capacidadE);
                            
                        }
                        else if(RSA.get(a)=="FA RSA"){
                            Utilitarios.Disminuir(G[a]);
                            
                        }
                        else if(RSA.get(a)=="FA CA RSA"){
                            Utilitarios.Disminuir(G[a]);
                            
                        }
                        else if(RSA.get(a)=="Def DWD"){
                            Utilitarios.DisminuirWithRoute(G[a],lea[a]);
                            
                        }
                        else if(RSA.get(a)=="Def MBBR"){
                            Utilitarios.DisminuirWithRoute(G[a],lea[a]);
                            
                        }
                        else if(RSA.get(a)=="Def CBD"){
                            Utilitarios.DisminuirWithRoute(G[a],lea[a]);
                            if (lea[a].size()>0){
                                expira++;
                            }
                            if (contD>100){
                                //System.out.print("");
                            }
                            if (expira>treshold && lea[a].size()>0 && contD>0){
                                //defragmentar
                                //algoritmo de seleccion
                                double R = 1;
                                int [][] selectos = Utilitarios.HUSIF(G[a],lea[a],R,capacidadE);
                                if (selectos!=null){
                                    Gaux.restablecerFS();
                                    Algoritmos_Defrag.CBD(G[a],Gaux,lea[a],selectos,capacidadE);                              
                                    Gaux.restablecerFS();
                                    
                                }
                                expira = 0;
                                //System.out.println("RERUTEO");
                            }
                            
                        }
                        else if(RSA.get(a)=="Def P-CF"){
                            Utilitarios.Disminuir(G[a]);
                            
                        }
                        
                                    
                                
                    }  
                }
            ++k;
            connection1.clear();
            connection2.clear();
            if (earlang>200){
                //System.out.println("");
            }
            // almacenamos la probablidad de bloqueo final para cada algoritmo
            for(int a=0;a<RSA.size();a++){
                prob[a].add(((double)contB[a]/contD));
                BFRnet[a].add(Metricas.BFR(G[a], capacidadE));
                MSInet[a].add(Metricas.MSI(G[a], capacidadE));
                UEnet[a].add(G[a].entropia());
                
               System.out.println("Probabilidad: "+(double)prob[a].get(k)+" Algoritmo: "+a+" Earlang: "+earlang);
            }//System.in.read();
            
            
            
            //hilobar1.barstate=(double)(earlang/500)*100;

            
            
            // avanzamos a la siguiente carga de trafico

            earlang+=paso;
            
            
            
            
            
        }
           


           
           hilobar1.fin=true;
           //bar1.setIndeterminate(false);

        this.etiquetaError.setText("");
        // una vez finalizado, graficamos el resultado.
        Utilitarios.GraficarResultado(prob, this.panelResultado, this.etiquetaResultado,RSA,paso,"Probalididad de bloqueo(%)");
        Utilitarios.GraficarResultado(BFRnet, this.panelBFR, this.etiquetaBFR,RSA,paso,"Bandwidth Fragmentation Ratio(%)");
        Utilitarios.GraficarResultado(UEnet, this.PanelEntropia, this.etiquetaEntropia,RSA,paso,"Entropia(%)");
        Utilitarios.GraficarResultadoEntropia(MSInet, this.PanelMSI, this.etiquetaMSI,RSA,paso,"MSI");
        this.etiquetaMSI.setVisible(true);
        String demandasTotales=""+contD; // mostramos la cantidad de demandas totales recibidas
        this.etiquetaDemandasTotales.setText(demandasTotales);
        }else{ // control de errores posibles realizados al no completar los parametros de simulacion
            if(listaDemandas.getSelectedIndex()<0){
               mensajeError=mensajeError+"Demanda";
                
            }
            if (this.listaRSA.getSelectedIndex()<0){
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
            if(mensajeError!="Seleccione "){
                this.etiquetaError.setText(mensajeError);
            }
        }
    }//GEN-LAST:event_botonEjecutarSimulacionActionPerformed

    private void listaRedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaRedesMouseClicked
        if(this.listaRedes.getSelectedIndex()>=0){
            this.botonCambiarDatosdelosEnlaces.setEnabled(true);
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

    private void listaEnlacesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaEnlacesMouseClicked
        int ban=0;
        int i=0;
        int j=0;
        if(this.listaEnlaces.getSelectedIndex()>=0){
            String enlaceSeleccionado = this.listaEnlaces.getSelectedValue();
            this.PaneleEditarEnalce.setVisible(true);
            this.etiquetaEditarEnlace.setText(enlaceSeleccionado);
            if(enlaceSeleccionado.substring(9,10).equals(" ")){
                i=Character.getNumericValue(enlaceSeleccionado.charAt(8));
                ban=1;
            }else{
                i=Integer.parseInt(enlaceSeleccionado.substring(8, 10));
            }
            
            if(enlaceSeleccionado.length()>12){
                if(ban==1)
                   j=Integer.parseInt(enlaceSeleccionado.substring(12, enlaceSeleccionado.length()));
                else
                   j=Integer.parseInt(enlaceSeleccionado.substring(13, enlaceSeleccionado.length()));
            }
            else{
                j=Character.getNumericValue(enlaceSeleccionado.charAt(12));
            }
            this.etiquetaEspectro.setText("Espectro :");
            this.etiquetaAnchoFS.setText("Ancho del FS :");
            String red = this.listaRedes.getSelectedValue();
            switch(red){
                case "Red 0":
                    this.campoDeTextoEspectro.setText(""+this.Redes.getRed(0).acceder(i, j).getEspectro());
                    this.campoDeTextoAnchoFS.setText(""+this.Redes.getRed(0).acceder(i, j).getAnchoFS());
                    break;
                case "Red 1":
                    this.campoDeTextoEspectro.setText(""+this.Redes.getRed(1).acceder(i,j).getEspectro());
                    this.campoDeTextoAnchoFS.setText(""+this.Redes.getRed(1).acceder(i,j).getAnchoFS());
                    break;
                case "Red 2":
                    this.campoDeTextoEspectro.setText(""+this.Redes.getRed(2).acceder(i,j).getEspectro());
                    this.campoDeTextoAnchoFS.setText(""+this.Redes.getRed(2).acceder(i,j).getAnchoFS());
                    break;    
            }
        }
            
    }//GEN-LAST:event_listaEnlacesMouseClicked
/*
    En proceso, no esta listo y tampoco es muy necesario
*/
    private void bGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGuardarActionPerformed
        String abc="0123456789";
        String abc2="0123456789.";
        int i;
        int j;
        int ban=0;
        String enlaceSeleccionado = this.listaEnlaces.getSelectedValue();
        String error="Inserte :";
        int redS=0;
        int esp=0;
        double anch=0;
        switch(this.listaRedes.getSelectedValue()){
            case "Red 0":
                redS=0;
                break;
            case "Red 1":
                redS=1;
                break;
            case "Red 2":
                redS=2;
                break;
                
        }
        String espectro=this.campoDeTextoEspectro.getText();
        String anchoFS=this.campoDeTextoAnchoFS.getText();
        if(Utilitarios.verificarNumero(abc,espectro)){
            esp=1;
        }else{
            error+="-el espectro";
        }
        if(Utilitarios.verificarNumero(abc2,anchoFS)){
            anch=1;
        }else{
            error+="-el anchoFS";
        }
        if(error!="Inserte :"){
            if(error.equals("Inserte :-el espectro") || error.equals("Inserte :-el anchoFS"))
                error+=" correcto.";
            else
                error+=" correctos.";
            this.etiquetaErrorEditarEnlace.setText(error);
        }else{
            if(enlaceSeleccionado.substring(9,10).equals(" ")){
                i=Character.getNumericValue(enlaceSeleccionado.charAt(8));
                ban=1;
            }else{
                i=Integer.parseInt(enlaceSeleccionado.substring(8, 10));
            }
            
            if(enlaceSeleccionado.length()>12){
                if(ban==1)
                   j=Integer.parseInt(enlaceSeleccionado.substring(12, enlaceSeleccionado.length()));
                else
                   j=Integer.parseInt(enlaceSeleccionado.substring(13, enlaceSeleccionado.length()));
            }
            else{
                j=Character.getNumericValue(enlaceSeleccionado.charAt(12));
            }
       
            esp=Integer.parseInt(espectro);
            anch=Double.parseDouble(anchoFS);
            if(anch>esp){
                this.etiquetaErrorEditarEnlace.setText("Error: Ancho del FS mayor al Espectro.");
            }else{
                this.Redes.getRed(redS).acceder(i,j).setEspectro(esp);
                this.Redes.getRed(redS).acceder(i,j).setAnchoFS(anch);
                this.Redes.getRed(redS).acceder(j,i).setEspectro(esp);
                this.Redes.getRed(redS).acceder(j,i).setAnchoFS(anch);
                this.etiquetaErrorEditarEnlace.setText("Se ha guardado correctamente.");
            } 
        }
    }//GEN-LAST:event_bGuardarActionPerformed
/*
    En proceso, no esta listo y tampoco es muy necesario
*/
    private void botonCerrarCerrarDatosEnlacesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarCerrarDatosEnlacesActionPerformed
        this.panelCambiarDatosdelosEnlaces.setVisible(false);
        this.etiquetaErrorEditarEnlace.setText("");
    }//GEN-LAST:event_botonCerrarCerrarDatosEnlacesActionPerformed
/*
    En proceso, no esta listo y tampoco es muy necesario
*/
    private void botonCerrarEditarEnlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarEditarEnlaceActionPerformed
        this.PaneleEditarEnalce.setVisible(false);
        this.etiquetaErrorEditarEnlace.setText("");
    }//GEN-LAST:event_botonCerrarEditarEnlaceActionPerformed

    private void spinnerFactorCapacidadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerFactorCapacidadStateChanged
        // TODO add your handling code here:
        double valor=(int)this.spinnerFactorCapacidad.getValue();
        valor=valor*12.5;
        this.etiquetaValorGHz.setText(" "+valor+" ");
    }//GEN-LAST:event_spinnerFactorCapacidadStateChanged
/*
    En proceso, no esta listo y tampoco es muy necesario
*/
    private void botonCambiarDatosdelosEnlacesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCambiarDatosdelosEnlacesActionPerformed
        DefaultListModel modelo = new DefaultListModel();
        GrafoMatriz G=new GrafoMatriz();
        this.listaEnlaces.setModel(modelo);
        String red = this.listaRedes.getSelectedValue();
        switch(red){
            case "Red 0":
            G=this.Redes.getRed(0);
            break;
            case "Red 1":
            G=this.Redes.getRed(1);
            break;
            case "Red 2":
            G=this.Redes.getRed(2);
            break;
        }
        for(int i=0;i<G.getCantidadDeVertices();i++){
            for(int j=i;j<G.getCantidadDeVertices();j++){
                if(G.acceder(i, j)!=null){
                    modelo.addElement(" Enlace "+i+" - "+j);
                }
            }
        }
        this.listaEnlaces.setModel(modelo);
        this.panelCambiarDatosdelosEnlaces.setVisible(true);

    }//GEN-LAST:event_botonCambiarDatosdelosEnlacesActionPerformed

    private void bar1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bar1StateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_bar1StateChanged

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        

    }//GEN-LAST:event_formWindowOpened

    private void botonEjecutarSimulacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEjecutarSimulacionMouseClicked
        // TODO add your handling code here:
        this.etiquetaError.setVisible(false);
    }//GEN-LAST:event_botonEjecutarSimulacionMouseClicked

    private void btndefragMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndefragMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btndefragMouseClicked

    private void btndefragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndefragActionPerformed
        // TODO add your handling code here:
        bar1.setValue(0);
        this.etiquetaError.setText("");
        this.etiquetaError.setVisible(true);
        this.etiquetaError.update(this.etiquetaError.getGraphics());
        
        
        
        /* Al inicio de cada Simulacion e+condemos los paneles de Resultado
        */
        this.etiquetaMSI.setVisible(false);
        this.panelBFR.setVisible(false);
        
        
        GrafoMatriz G [] = new GrafoMatriz[15]; // Se tiene una matriz de adyacencia por algoritmo RSA en el Simulador
        
        Demanda d= new Demanda();  // Demanda a recibir
        Resultado r=new Resultado(); // resultado obtenido en una demanda. Si r==null se cuenta como bloqueo
        String mensajeError = "Seleccione: "; // mensaje de error a mostrar eal usuario si no ha completado los parametros de
                                               // simulacion
        
        List<String> RSA = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        List<String> RSA2 = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        
        int E=(int)this.spinnerEarlang.getValue(); // se obtiene el limite de carga (Erlang) de trafico seleccionado por el usuario
        int demandasPorUnidadTiempo=0; //demandas por unidad de tiempo. Considerando el punt decimal
        int earlang=0; //Carga de trafico en cada simulacion
        int k=-1; // contador auxiliar
        int paso=(int)this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD=0; // contador de demandas totales
        int tiempoT=Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        int capacidadE=Integer.parseInt(this.textFieldCapacidadEnlace.getText().toString()); // espectro por enalce
        double anchoFS=Double.parseDouble(this.textFieldAnchoFS.getText().toString()); // ancho de FS
        
        
        
        
            
            
        
            
            
            
        //factor del tiempo de simulacion especificado por el usuario
        
        System.out.println("El ancho del FS es:"+anchoFS);
        System.out.println("Cantidad de FS por enlace:"+capacidadE);
        
        int t=(int)(Double.parseDouble(this.spinnerFactorTiempo.getValue().toString())*tiempoT);
        //cantidad de FS por demanda expresado pro el usuario
        int b=(int)(Double.parseDouble(this.spinnerFactorCapacidad.getValue().toString()));
        
        if(this.listaDemandas.getSelectedIndex()>=0 && listaRSADF.getSelectedIndex()>=0 && 
                this.listaRedes.getSelectedIndex()>=0){ // si todos los parametros fueron seleccionados
           this.etiquetaError.setVisible(true); // habilitamos la etiqueta de error
           /////////////////////////////////////
           //hilos
           hilobar1=new hilobar(bar1,1,1,etiquetaError);
            Thread hbar1=new Thread(hilobar1);
            //bar1.setIndeterminate(true);
            
            hbar1.start();
            
           
           
           
            //RSA= this.listaRSA.getSelectedValuesList(); // obtenemos los algoritmos RSA seleccionados
            for (int i = 0; i < listdefrag.getModel().getSize(); i++) {
                RSA.add(listaRSADF.getModel().getElementAt(0)); 
                RSA2.add(listdefrag.getModel().getElementAt(i));
           }
            String redSeleccionada = this.listaRedes.getSelectedValue(); // obtenemos la topologia seleccionada
            String demandaSeleccionada = this.listaDemandas.getSelectedValue(); // obtenemos el tipo de trafico seleccionado
            System.out.println(demandaSeleccionada +"++");
            //int [] contB=new int [RSA.size()]; // array encargado de almacenar en cada posicion la cantidad de bloqueo para cada
                                                // algoritmo seleccionado
            int contB=0;


            List prob []= new LinkedList[RSA.size()]; // probabilidad de bloqueo para cada algoritmo RSA selecciondo 
            List UEnet []= new LinkedList[RSA.size()];// entropia de red para cada algoritmo RSA
            
            
            for(int i=0;i<prob.length;i++){
                prob[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            }
            
            for(int i=0;i<UEnet.length;i++){
                UEnet[i]=new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                                            // obtenidad en cada simulacion
            }
            
            switch(redSeleccionada){ // cagamos los datos en las matrices de adyacencia segun la topologia seleccionada
                case "Red 0":
                    //de ´rueba no utilizado
                    for(int i=0;i<G.length;i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(0).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(0));
                    }
                    break;
                case "Red 1":
                    for(int i=0;i<G.length;i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(1));
                    }
                    break;
                case "Red 2":
                    for(int i=0;i<G.length;i++){
                        G[i]=new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(2));
                    }
            }
            ArrayList<Demanda>  connection1= new ArrayList<Demanda>();
            ArrayList<Demanda>  connection2= new ArrayList<Demanda>();
            int earlangprev=0;
            int earlangprev2=0;
            int contearl=0,itersp=0,itergreedy=0;
            
            earlangprev=earlang;
            earlangprev2=earlang;
            int [] conexid=new int [RSA.size()]; 
            
            
            for (int idf = 0; idf < this.listdefrag.getModel().getSize(); idf++) {
                G[1].ResetRed();
                k=-1;
                contD=0;
                contB=0;
                String algoritmo =RSA.get(idf);
                earlang=0;
                int pasoactual=Integer.parseInt(this.listdefrag.getModel().getElementAt(idf));
                int contpaso=0;
                while(earlang<=E){ // mientras no se llega a la cargad de trafico maxima
                    
                    contearl++;
                     boolean defrag=false;
                     boolean defrag2=false;
                     //if (contearl==1) {
                         if (contpaso==pasoactual) {
                         defrag=true;
                         contpaso=0;
                         Collections.sort(connection1, new Comparator<Demanda>() {
                         public int compare(Demanda one, Demanda other) {
                             if(one.getNroFS()>other.getNroFS())
                                 return 1;
                             if(one.getNroFS()<other.getNroFS())
                                 return -1;
                             return 0;
                         }}); 
                  
                     }
                     if (contpaso==pasoactual) {
                         defrag2=true;
                         contpaso=0;
                         Collections.sort(connection2, new Comparator<Demanda>() {
                         public int compare(Demanda one, Demanda other) {
                             if(one.getNroFS()>other.getNroFS())
                                 return 1;
                             if(one.getNroFS()<other.getNroFS())
                                 return -1;
                             return 0;
                         }}); 
                         
                     }
                     //calculo de entropia de utilizacion de la red
                     UEnet[idf].add(G[1].entropia());
                     


                     /////////////////////////////

                       for(int i=0;i<tiempoT;i++){ // para cada unidad de tiempo
                        demandasPorUnidadTiempo = Utilitarios.demandasTotalesPorTiempo(earlang, t); // obtener las demandas
                                                                                                    // por unidad de tiempo
                          for(int j=0;j<demandasPorUnidadTiempo;j++){ // para cada demanda
                             switch(demandaSeleccionada){ // se ve que modelo de trafico fue seleccionado
                             case "Tiempo de permanecia y FS Fijos":
                                  d.obtenerDemandasFijo(b, t,G[0].getCantidadDeVertices()-1);  // obtenemos un origen, fin, catidad de FS y tiempo de permanecia
                                  break;
                             case "Tiempo de permanecia Fijo y FS Variables":
                                 d.obtenerDemandasFSVariable(b, t,G[0].getCantidadDeVertices()-1);  // obtenemos un origen, fin, catidad de FS y tiempo de permanecia
                                  break;
                             } 
                             ListaEnlazada[] ksp=Utilitarios.KSP(G[0], d.getOrigen(), d.getDestino(), 3); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=3
    
                                 switch(algoritmo){ // vemos cual algoritmo ha sido selecciondo, y realizamos la ejecucion de cada uno
                                                                // en su correspondiente Topologia
                                     //
                                        // El Shortest Path Defragmentation usa el camino mas corto (distancia fisica entre nodos)
                                    //
                                     case "FAR - FF - SP - DF":
                                         
                                         r=Algoritmos.KSP_FF_Algorithm(G[1],d,ksp,capacidadE);
                                         
                                         if(r!=null){
                                             Utilitarios.asignarFS_Defrag(ksp,r,G[1],d,++conexid[idf]);
                                             connection1.add(d);
                                             
                                         }  
                                         else{
                                             contB++;

                                             
                                         }
                                         if (defrag==true) {
                                             //System.out.println(":::::::::::.defrag numero-sp-df: "+ ++itersp);
                                             
                                             contearl=0;
                                             
                                             //se reinicializa la red
                                             G[1].ResetRed();
                                             
                                             int did=0;
                                             for(int kk=0;kk<connection1.size();kk++){
                                                 Demanda df=connection1.get(kk);
                                                 ListaEnlazada[] kspdf=Utilitarios.KSP(G[0], df.getOrigen(), df.getDestino(), 3); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=3
                                                 r=Algoritmos.KSP_FF_Algorithm(G[1],df,kspdf,capacidadE);
                                                 
                                                 if(r!=null){
                                                     Utilitarios.asignarFS_Defrag(kspdf,r,G[1],df,++did);
                                                     
                                                 }  
                                                 
                                             }
                                             

                                             defrag=false;
                                         }


                                         break;
                                         //
                                            // El Greedy Defragmentation usa el camino con menos saltos (hops)
                                        //
                                         case "FAR - FF - GREEDY - DF":
                                         
                                         r=Algoritmos.KSP_FF_Algorithm(G[1],d,ksp,capacidadE);
                                         
                                         if(r!=null){
                                             Utilitarios.asignarFS_Defrag(ksp,r,G[1],d,++conexid[idf]);
                                             connection2.add(d);
                                         
                                         }  
                                         else{
                                             contB++;

                                         }
                                         if (defrag2==true) {
                                             
                                             //se reinicializa la red
                                             G[1].ResetRed();
                                             
                                             int did=0;
                                             for(int kk=0;kk<connection2.size();kk++){

                                                 Demanda df=connection2.get(kk);
                                                 ListaEnlazada[] kspdf=Utilitarios.KSP(G[0], df.getOrigen(), df.getDestino(), 8); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=6
                                                 
                                                 r=Algoritmos.KSP_FF_Algorithm2_Greedy(G[1],df,kspdf,capacidadE);
                                                 

                                                 if(r!=null){
                                                     Utilitarios.asignarFS_Defrag(kspdf,r,G[1],df,++did);
                                                     
                                                 }  
                                                 
                                             }
                                             

                                             defrag2=false;
                                         }


                                         break;


                                     }//fin case


                             contD++; 
                         }
                          // para cada Algoritmo y su correspondiente topologia, disminuimos el tiempo de permanecia

                            if(RSA.get(idf)=="FAR - FF - SP - DF"){
                                 Utilitarios.Disminuir(G[1]);
                                 //disminuir el tiempo de la demanda(sin utilizar la topologia)
                                 for(int kk=0;kk<connection1.size();kk++){
                                     if(connection1.get(kk).getTiempo()!=0 && connection1.get(kk).getTiempo()!=1){
                                         Demanda aux=new Demanda(connection1.get(kk).getOrigen(),connection1.get(kk).getDestino(),connection1.get(kk).getNroFS(),connection1.get(kk).getTiempo()-1);
                                         connection1.set(kk, aux);

                                     }
                                     else{
                                         connection1.remove(kk);
                                     }

                                 }
                             }
                             else if(RSA.get(idf)=="FAR - FF - GREEDY - DF"){
                                 Utilitarios.Disminuir(G[1]);
                                 //disminuir el tiempo de la demanda(sin utilizar la topologia)
                                 for(int kk=0;kk<connection2.size();kk++){
                                     if(connection2.get(kk).getTiempo()!=0 && connection2.get(kk).getTiempo()!=1){
                                         Demanda aux=new Demanda(connection2.get(kk).getOrigen(),connection2.get(kk).getDestino(),connection2.get(kk).getNroFS(),connection2.get(kk).getTiempo()-1);
                                         connection2.set(kk, aux);

                                     }
                                     else{
                                         connection2.remove(kk);
                                     }

                                 }
                             }

                     }
                 ++k;
                 connection1.clear();
                 connection2.clear();
                 // almacenamos la probablidad de bloqueo final para cada algoritmo
                 
                     prob[idf].add(((double)contB/contD));
                    System.out.println("Probabilidad: "+(double)prob[idf].get(k)+" Algoritmo: "+idf+" Earlang: "+earlang+":: "+idf);
                 //System.in.read();



                 //hilobar1.barstate=(double)(earlang/500)*100;



                 // avanzamos a la siguiente carga de trafico

                 earlang+=paso;
                 contpaso=paso;//paso de defragmentacion
            
            
            
            
            
                }
            }
           
           
           hilobar1.fin=true;
           //bar1.setIndeterminate(false);

        this.etiquetaError.setText("");
        // una vez finalizado, graficamos el resultado.
        Utilitarios.GraficarResultado(prob, this.panelResultado, this.etiquetaResultado,RSA,paso,"Probalididad de bloqueo(%)");
        Utilitarios.GraficarResultado(UEnet, this.PanelEntropia, this.etiquetaEntropia,RSA,paso,"Entropia(%)");
        this.etiquetaMSI.setVisible(true);
        String demandasTotales=""+contD; // mostramos la cantidad de demandas totales recibidas
        this.etiquetaDemandasTotales.setText(demandasTotales);
        }else{ // control de errores posibles realizados al no completar los parametros de simulacion
            if(listaDemandas.getSelectedIndex()<0){
               mensajeError=mensajeError+"Demanda";
                
            }
            if (this.listaRSA.getSelectedIndex()<0){
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
            if(mensajeError!="Seleccione "){
                this.etiquetaError.setText(mensajeError);
            }
        }
        
    }//GEN-LAST:event_btndefragActionPerformed

    private void txtdefragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdefragActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdefragActionPerformed

    private void txtdefragKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdefragKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER ){
            DefaultListModel<String> model = new DefaultListModel<>();
            ListModel<String> m=listdefrag.getModel();
            if (m.getSize()<8) {
                for ( int i = 0; i < m.getSize(); i++ ){
                    
                    model.addElement(m.getElementAt(i));
                      //System.out.println("valores en la lista:::::::::"+model.getElementAt(i));
                }
                if(Integer.parseInt(txtdefrag.getText())>(Integer)spinnerPaso.getValue() && Integer.parseInt(txtdefrag.getText())>0 && Integer.parseInt(txtdefrag.getText())%(Integer)spinnerPaso.getValue()==0 )
                    model.addElement(txtdefrag.getText());
                
                  
                  listdefrag.setModel(model);
            }
            txtdefrag.setText("");
            
        }
        
        
    }//GEN-LAST:event_txtdefragKeyPressed

    private void btninsertar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertar1ActionPerformed
        // TODO add your handling code here:
        DefaultListModel<String> model = new DefaultListModel<>();
        listdefrag.setModel(model);

    }//GEN-LAST:event_btninsertar1ActionPerformed

    private void listaRSADFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaRSADFMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listaRSADFMouseClicked

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
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                
                VentanaPrincipal_Defrag  v=new VentanaPrincipal_Defrag();
                v.setVisible(true);
                //v.bar1.setStringPainted(true);
                
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelEntropia;
    private javax.swing.JPanel PanelMSI;
    private javax.swing.JPanel PaneleEditarEnalce;
    private javax.swing.JButton bGuardar;
    private javax.swing.JProgressBar bar1;
    private javax.swing.JButton botonCambiarDatosdelosEnlaces;
    private javax.swing.JButton botonCerrarCerrarDatosEnlaces;
    private javax.swing.JButton botonCerrarEditarEnlace;
    private javax.swing.JButton botonEjecutarSimulacion;
    private javax.swing.JButton btndefrag;
    private javax.swing.JButton btninsertar1;
    private javax.swing.JTextField campoDeTextoAnchoFS;
    private javax.swing.JTextField campoDeTextoEspectro;
    private javax.swing.JLabel etiqueta1;
    private javax.swing.JLabel etiqueta2;
    private javax.swing.JLabel etiquetaAnchoFS;
    private javax.swing.JLabel etiquetaAnchoFSActual;
    private javax.swing.JLabel etiquetaBFR;
    private javax.swing.JLabel etiquetaCapacidadActual;
    private javax.swing.JLabel etiquetaDemanda;
    private javax.swing.JLabel etiquetaDemandasTotales;
    private javax.swing.JLabel etiquetaEditarEnlace;
    private javax.swing.JLabel etiquetaEntropia;
    private javax.swing.JLabel etiquetaError;
    private javax.swing.JLabel etiquetaErrorEditarEnlace;
    private javax.swing.JLabel etiquetaEspectro;
    private javax.swing.JLabel etiquetaFactorTiempo;
    private javax.swing.JLabel etiquetaGHz;
    private javax.swing.JLabel etiquetaImagen;
    private javax.swing.JLabel etiquetaMSI;
    private javax.swing.JLabel etiquetaRSA;
    private javax.swing.JLabel etiquetaResultado;
    private javax.swing.JLabel etiquetaTextoDemandasTotales;
    private javax.swing.JLabel etiquetaTiempoActual;
    private javax.swing.JLabel etiquetaTituloDeEnlaces;
    private javax.swing.JLabel etiquetaTopologia;
    private javax.swing.JLabel etiquetaValorGHz;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbldefrag;
    private javax.swing.JList<String> listaDemandas;
    private javax.swing.JList<String> listaEnlaces;
    private javax.swing.JList<String> listaRSA;
    private javax.swing.JList<String> listaRSADF;
    private javax.swing.JList<String> listaRedes;
    private javax.swing.JList<String> listdefrag;
    private javax.swing.JPanel panelBFR;
    private javax.swing.JPanel panelCambiarDatosdelosEnlaces;
    private javax.swing.JPanel panelResultado;
    private javax.swing.JSpinner spinnerEarlang;
    private javax.swing.JSpinner spinnerFactorCapacidad;
    private javax.swing.JSpinner spinnerFactorTiempo;
    private javax.swing.JSpinner spinnerPaso;
    private javax.swing.JSpinner spinnerTiempoSimulacion;
    private javax.swing.JTextField textFieldAnchoFS;
    private javax.swing.JTextField textFieldCapacidadEnlace;
    private javax.swing.JTextField txtdefrag;
    // End of variables declaration//GEN-END:variables
  
   
}



