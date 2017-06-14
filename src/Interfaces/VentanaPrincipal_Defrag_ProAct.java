package Interfaces;

import EON.Utilitarios.*;
import EON.*;
import EON.Algoritmos.*;
import EON.Metricas.Metricas;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import org.jfree.data.xy.*;
import org.jfree.chart.annotations.XYTextAnnotation;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

/**
 *
 * @author sGaleano - dBaez Frame que se encargad de la interfaz con el usurio y
 * realizar la simulacion de una Red Optica Elastica. Permite realizar una
 * simulacion teniendo: - Una topologia - Un conjunto de algoritmos. - Un tipo
 * de demanda que sera generada y guardada en un archivo.
 */
public class VentanaPrincipal_Defrag_ProAct extends javax.swing.JFrame {

    private Topologias Redes; // topologias disponibles

    private int tiempoTotal; // Iiempo que dura una simualcion
    String redSeleccionada;
    private double anchoFS; // ancho de un FS en los enlaces
    private int capacidadPorEnlace; // cantidad de FSs por enlace en la topologia elegida

    private int Erlang, rutas;
    private int Lambda, contBloqueos;
    private int HoldingTime; // Erlang / Lambda
    private int FsMinimo; // Cantidad mínima de FS por enlace
    private int FsMaximo; // Cantidad máxima de FS por enlace
    private double entropia, msi, bfr, pathConsec, entropiaUso;
    private ArrayList<Integer> rutasEstablecidas; //guarda el tiempo de vida de las rutas ya establecidas por el algoritmo RSA
    private ArrayList<ListaEnlazada> arrayRutas;//Guarda la lista enlazada que representa a la ruta establecida por el algoritmo RSA
    private ArrayList<Resultado> resultadoRuteo;//Guarda los resutados del algoritmo para saber en que FS fue ubicada la demanda
    private ArrayList<ListaEnlazada[]> listaKSP;
    int hora, minutos, segundos, dia, mes, anho;
//    private int cantidadRedes; //cantidad de redes exitentes en el Simulador
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    //private List algoritmosCompletosParaEjecutar;
    private List algoritmosCompletosParaGraficar;
    private int cantidadDeAlgoritmosRuteoSeleccionados;
    private int cantidadDeAlgoritmosTotalSeleccionados;

    public VentanaPrincipal_Defrag_ProAct() {
        initComponents();
        this.Redes = new Topologias(); // asignamos todas las topologias disponibles}

        /*No mostramos inicialmente los paneles que muestran los Resultados
         */
        //this.cantidadDeAlgoritmosRuteoSeleccionados = 0;
        this.cantidadDeAlgoritmosTotalSeleccionados = 0;
        //this.algoritmosCompletosParaEjecutar = new LinkedList();
        this.algoritmosCompletosParaGraficar = new LinkedList();
        this.setTitle("EON Simulator - Defragmentación ProActiva");

        setearRed(); // setea la red que aparece por defecto

        // Al inicio de cada Simulacion e+condemos los paneles de Resultado
        this.etiquetaTextoBloqueosTotales.setVisible(false);
        this.etiquetaDemandasTotales.setVisible(false);
        this.etiquetaTextoDemandasTotales.setVisible(false);
        this.etiquetaBloqueosTotales.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        listaAlgoritmosRuteo = new javax.swing.JList<>();
        botonEjecutarSimulacion = new javax.swing.JButton();
        etiquetaTopologia = new javax.swing.JLabel();
        etiquetaError = new javax.swing.JLabel();
        etiquetaCapacidadActual = new javax.swing.JLabel();
        etiquetaTiempoActual = new javax.swing.JLabel();
        spinnerTiempoSimulacion = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        etiquetaImagenTopologia = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        etiquetaDemandasTotales = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinnerErlang = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        textFieldCapacidadEnlace = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        listaRedes = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        etiquetaAnchoFSActual1 = new javax.swing.JLabel();
        textFieldLambda = new javax.swing.JTextField();
        etiquetaAnchoFSActual2 = new javax.swing.JLabel();
        textFieldFSminimo = new javax.swing.JTextField();
        etiquetaAnchoFSActual3 = new javax.swing.JLabel();
        etiquetaAnchoFSActual4 = new javax.swing.JLabel();
        textFieldFSmaximo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textFieldAnchoFS = new javax.swing.JTextField();
        etiquetaAnchoFSActual = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        etiquetaTextoBloqueosTotales = new javax.swing.JLabel();
        etiquetaBloqueosTotales = new javax.swing.JLabel();
        etiquetaTextoDemandasTotales = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        etiquetaTextoMax = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResultadosBloqueosMinMax = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableResultadosBloqueos = new javax.swing.JTable();
        etiquetaTextoMin = new javax.swing.JLabel();
        etiquetaRSA1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableResultados = new javax.swing.JTable();
        etiquetaRSA3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableResultadosMinMax = new javax.swing.JTable();
        etiquetaTextoMin1 = new javax.swing.JLabel();
        etiquetaTextoMax1 = new javax.swing.JLabel();
        panelResultados = new javax.swing.JScrollPane();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableEstadoEnlaces = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableResultadosDefrag = new javax.swing.JTable();
        etiquetaRSA2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        textFieldEntropíaMin = new javax.swing.JTextField();
        etiquetaAnchoFSActual5 = new javax.swing.JLabel();
        etiquetaAnchoFSActual6 = new javax.swing.JLabel();
        textFieldCantHormigas = new javax.swing.JTextField();
        etiquetaAnchoFSActual7 = new javax.swing.JLabel();
        etiquetaAnchoFSActual8 = new javax.swing.JLabel();
        etiquetaAnchoFSActual9 = new javax.swing.JLabel();
        textFieldBFRMin = new javax.swing.JTextField();
        etiquetaAnchoFSActual10 = new javax.swing.JLabel();
        etiquetaAnchoFSActual11 = new javax.swing.JLabel();
        etiquetaAnchoFSActual12 = new javax.swing.JLabel();
        textFieldLightPathMax = new javax.swing.JTextField();
        etiquetaAnchoFSActual13 = new javax.swing.JLabel();
        etiquetaAnchoFSActual14 = new javax.swing.JLabel();
        textFieldPathConsMin = new javax.swing.JTextField();
        textFieldEntroUsoMin = new javax.swing.JTextField();
        etiquetaAnchoFSActual15 = new javax.swing.JLabel();
        etiquetaAnchoFSActual16 = new javax.swing.JLabel();
        etiquetaAnchoFSActual17 = new javax.swing.JLabel();
        jCheckBoxPasoaPaso = new javax.swing.JCheckBox();
        textFieldMSIMin1 = new javax.swing.JTextField();
        etiquetaAnchoFSActual18 = new javax.swing.JLabel();
        etiquetaAnchoFSActual19 = new javax.swing.JLabel();
        textFieldMejoraACO = new javax.swing.JTextField();
        etiquetaAnchoFSActual20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaAlgoritmosRuteo.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "FA", "FA-CA", "MTLSC" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaAlgoritmosRuteo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaAlgoritmosRuteo.setToolTipText("");
        listaAlgoritmosRuteo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listaAlgoritmosRuteo.setSelectedIndex(0);
        listaAlgoritmosRuteo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaAlgoritmosRuteoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaAlgoritmosRuteo);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, 120, 110));

        botonEjecutarSimulacion.setText("Simular");
        botonEjecutarSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarSimulacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonEjecutarSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 650, 100, 40));

        etiquetaTopologia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiquetaTopologia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTopologia.setText("Topologia");
        getContentPane().add(etiquetaTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 70, 20));

        etiquetaError.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(etiquetaError, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 650, 160, 40));

        etiquetaCapacidadActual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaCapacidadActual.setText("Capacidad");
        getContentPane().add(etiquetaCapacidadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 70, 20));

        etiquetaTiempoActual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTiempoActual.setText("Tiempo de Simulacion");
        getContentPane().add(etiquetaTiempoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 20));

        spinnerTiempoSimulacion.setModel(new javax.swing.SpinnerNumberModel(100, 50, 100000, 25));
        spinnerTiempoSimulacion.setToolTipText("");
        spinnerTiempoSimulacion.setRequestFocusEnabled(false);
        spinnerTiempoSimulacion.setValue(1000);
        getContentPane().add(spinnerTiempoSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 60, 20));

        jLabel2.setText("FSs por Enlace");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, 20));

        etiquetaImagenTopologia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaImagenTopologia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        etiquetaImagenTopologia.setFocusable(false);
        etiquetaImagenTopologia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        etiquetaImagenTopologia.setOpaque(true);
        etiquetaImagenTopologia.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(etiquetaImagenTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 350, 150));

        jLabel5.setText("unid.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, -1, 20));

        etiquetaDemandasTotales.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 50, 20));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Trafico Maximo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 90, 20));

        spinnerErlang.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1500, 50));
        getContentPane().add(spinnerErlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 50, -1));

        jLabel6.setText("Erlang");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 50, 20));

        textFieldCapacidadEnlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCapacidadEnlace.setText("50");
        getContentPane().add(textFieldCapacidadEnlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 50, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Parámetros de Desfragmentación");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, -1, -1));

        listaRedes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NSFNet", "ARPA-2" }));
        listaRedes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaRedesActionPerformed(evt);
            }
        });
        getContentPane().add(listaRedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 80, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Desfragmentación ProActiva");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Resultados");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        etiquetaAnchoFSActual1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual1.setText("Lambda");
        getContentPane().add(etiquetaAnchoFSActual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 50, 20));

        textFieldLambda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldLambda.setText("5");
        textFieldLambda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldLambdaActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldLambda, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 40, 20));

        etiquetaAnchoFSActual2.setText("mín");
        getContentPane().add(etiquetaAnchoFSActual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, 30, 20));

        textFieldFSminimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSminimo.setText("2");
        getContentPane().add(textFieldFSminimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 30, 20));

        etiquetaAnchoFSActual3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual3.setText("FS Rango");
        getContentPane().add(etiquetaAnchoFSActual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 70, 20));

        etiquetaAnchoFSActual4.setText("máx");
        getContentPane().add(etiquetaAnchoFSActual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 470, 30, 20));

        textFieldFSmaximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSmaximo.setText("8");
        getContentPane().add(textFieldFSmaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 470, 30, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Red");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel3.setText("GHz");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 30, 20));

        textFieldAnchoFS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldAnchoFS.setText("2");
        textFieldAnchoFS.setEnabled(false);
        textFieldAnchoFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldAnchoFSActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldAnchoFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 30, 20));

        etiquetaAnchoFSActual.setText("Ancho FS");
        getContentPane().add(etiquetaAnchoFSActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 60, 20));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, 690));

        etiquetaTextoBloqueosTotales.setText("Total Bloqueos:");
        getContentPane().add(etiquetaTextoBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 90, 20));

        etiquetaBloqueosTotales.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 50, 20));

        etiquetaTextoDemandasTotales.setText("Total Demandas:");
        getContentPane().add(etiquetaTextoDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 100, 20));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiquetaTextoMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax.setText("max");
        etiquetaTextoMax.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        etiquetaTextoMax.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 600, 30, 20));

        jTableResultadosBloqueosMinMax.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableResultadosBloqueosMinMax.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTableResultadosBloqueosMinMax);
        jTableResultadosBloqueosMinMax.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 560, 330, 63));

        jTableResultadosBloqueos.setAutoCreateRowSorter(true);
        jTableResultadosBloqueos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tiempo", "Demandas", "Bloqueos", "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableResultadosBloqueos.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(jTableResultadosBloqueos);
        jTableResultadosBloqueos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 480, 520));

        etiquetaTextoMin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMin.setText("min");
        etiquetaTextoMin.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaTextoMin.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 590, 30, 20));

        etiquetaRSA1.setBackground(new java.awt.Color(255, 102, 102));
        etiquetaRSA1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetaRSA1.setText("Bloqueos");
        jPanel1.add(etiquetaRSA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 80, -1));

        jTableResultados.setAutoCreateRowSorter(true);
        jTableResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tiempo", "Demandas", "Bloqueos", "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableResultados);
        jTableResultados.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 490, 520));

        etiquetaRSA3.setBackground(new java.awt.Color(255, 102, 102));
        etiquetaRSA3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetaRSA3.setText("Resultados");
        jPanel1.add(etiquetaRSA3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        jTableResultadosMinMax.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTableResultadosMinMax);
        jTableResultadosMinMax.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 340, 63));

        etiquetaTextoMin1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMin1.setText("min");
        etiquetaTextoMin1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaTextoMin1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 590, 30, 20));

        etiquetaTextoMax1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax1.setText("max");
        etiquetaTextoMax1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        etiquetaTextoMax1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMax1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 600, 30, 20));

        jTabbedPane1.addTab("Datos", jPanel1);

        panelResultados.setViewportView(filler1);

        jTabbedPane1.addTab("Gráficos", panelResultados);

        jTableEstadoEnlaces.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableEstadoEnlaces.setColumnSelectionAllowed(true);
        jScrollPane6.setViewportView(jTableEstadoEnlaces);

        jTabbedPane1.addTab("Estado Final de los Enlaces", jScrollPane6);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableResultadosDefrag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tiempo", "Cant. Rutas", "Mejora %"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jTableResultadosDefrag);

        jPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 240, 610));

        jTabbedPane1.addTab("Desfragmentaciones", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 990, 660));

        etiquetaRSA2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA2.setText("Algoritmo de Ruteo");
        getContentPane().add(etiquetaRSA2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Otros");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        textFieldEntropíaMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldEntropíaMin.setText("0");
        textFieldEntropíaMin.setToolTipText("");
        textFieldEntropíaMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldEntropíaMinActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldEntropíaMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 40, 20));

        etiquetaAnchoFSActual5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual5.setText("Min");
        getContentPane().add(etiquetaAnchoFSActual5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 20, 20));

        etiquetaAnchoFSActual6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual6.setText("%");
        getContentPane().add(etiquetaAnchoFSActual6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 20, 20));

        textFieldCantHormigas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCantHormigas.setText("100");
        textFieldCantHormigas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldCantHormigasActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldCantHormigas, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 620, 40, 20));

        etiquetaAnchoFSActual7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual7.setText("Min");
        getContentPane().add(etiquetaAnchoFSActual7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 580, 20, 20));

        etiquetaAnchoFSActual8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual8.setText("Cant. hormigas:");
        getContentPane().add(etiquetaAnchoFSActual8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 620, 100, 20));

        etiquetaAnchoFSActual9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaAnchoFSActual9.setText("BFR");
        getContentPane().add(etiquetaAnchoFSActual9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 50, 20));

        textFieldBFRMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldBFRMin.setText("0");
        textFieldBFRMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldBFRMinActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldBFRMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 580, 40, 20));

        etiquetaAnchoFSActual10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual10.setText("Min");
        getContentPane().add(etiquetaAnchoFSActual10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 20, 20));

        etiquetaAnchoFSActual11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaAnchoFSActual11.setText("LightPath");
        getContentPane().add(etiquetaAnchoFSActual11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 560, 70, 20));

        etiquetaAnchoFSActual12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual12.setText("Max");
        getContentPane().add(etiquetaAnchoFSActual12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 580, 30, 20));

        textFieldLightPathMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldLightPathMax.setText("0");
        textFieldLightPathMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldLightPathMaxActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldLightPathMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 580, 40, 20));

        etiquetaAnchoFSActual13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaAnchoFSActual13.setText("PathCons");
        getContentPane().add(etiquetaAnchoFSActual13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 560, 60, 20));

        etiquetaAnchoFSActual14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual14.setText("Min");
        getContentPane().add(etiquetaAnchoFSActual14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 580, 20, 20));

        textFieldPathConsMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldPathConsMin.setText("0");
        textFieldPathConsMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPathConsMinActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldPathConsMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 580, 40, 20));

        textFieldEntroUsoMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldEntroUsoMin.setText("0");
        textFieldEntroUsoMin.setToolTipText("");
        textFieldEntroUsoMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldEntroUsoMinActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldEntroUsoMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 580, 40, 20));

        etiquetaAnchoFSActual15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual15.setText("Min");
        getContentPane().add(etiquetaAnchoFSActual15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 20, 20));

        etiquetaAnchoFSActual16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaAnchoFSActual16.setText("Entr/Uso");
        getContentPane().add(etiquetaAnchoFSActual16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 560, 50, 20));

        etiquetaAnchoFSActual17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaAnchoFSActual17.setText("Entropía");
        getContentPane().add(etiquetaAnchoFSActual17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 50, 20));

        jCheckBoxPasoaPaso.setText("Paso a Paso");
        jCheckBoxPasoaPaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPasoaPasoActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBoxPasoaPaso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, -1, -1));

        textFieldMSIMin1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldMSIMin1.setText("0");
        textFieldMSIMin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMSIMin1ActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldMSIMin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 580, 40, 20));

        etiquetaAnchoFSActual18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaAnchoFSActual18.setText("MSI");
        getContentPane().add(etiquetaAnchoFSActual18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, 50, 20));

        etiquetaAnchoFSActual19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual19.setText("0 = No considera");
        getContentPane().add(etiquetaAnchoFSActual19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 100, 20));

        textFieldMejoraACO.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldMejoraACO.setText("1");
        textFieldMejoraACO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMejoraACOActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldMejoraACO, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 620, 40, 20));

        etiquetaAnchoFSActual20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual20.setText("Méjora buscada en ACO:");
        getContentPane().add(etiquetaAnchoFSActual20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 150, 20));

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEjecutarSimulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarSimulacionActionPerformed

        // Al inicio de cada Simulacion e+condemos los paneles de Resultado
        //this.panelResultadosBloqueos.setVisible(false);
//        this.etiquetaTextoMax.setVisible(false);
//        this.etiquetaDemandasTotales.setVisible(false);
//        this.etiquetaTextoBloqueosTotales.setVisible(false);
//        this.etiquetaBloqueosTotales.setVisible(false);
        this.etiquetaError.setText("Simulando...");
        this.etiquetaError.setVisible(true); 
        
        //inicializamos algunas variables
        this.cantidadDeAlgoritmosTotalSeleccionados = 0;
        this.algoritmosCompletosParaGraficar.clear();
        
        //borramos los resultados que están en las tablas
        reiniciarJTableRows(this.jTableResultados);
        reiniciarJTableRows(this.jTableResultadosMinMax);
        reiniciarJTableRows(this.jTableResultadosBloqueos);
        reiniciarJTableRows(this.jTableResultadosBloqueosMinMax);
        reiniciarJTableRows(this.jTableEstadoEnlaces);
        reiniciarJTableColumns(this.jTableEstadoEnlaces);
        reiniciarJTableRows(this.jTableResultadosDefrag);
        
        //parámetros ACO
        double mejoraACO = Double.parseDouble(this.textFieldMejoraACO.getText());
        int cantHormACO = Integer.parseInt(this.textFieldCantHormigas.getText());

        //leemos los valores seteados
        this.tiempoTotal = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); //Tiempo de simulacion indicado por el usuario
        this.redSeleccionada = (String) this.listaRedes.getSelectedItem(); // obtenemos la topologia seleccionada
        this.anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText()); // ancho de los FSs de la toplogia elegida, tambien indicado por el usuario
        this.capacidadPorEnlace = Integer.parseInt(this.textFieldCapacidadEnlace.getText()); //obtenemos la cantidad de FS de los enlaces indicados por el usuario

        this.Erlang = Integer.parseInt(this.spinnerErlang.getValue().toString()); //obtenemos Erlang indicados por el usuario
        this.Lambda = Integer.parseInt(this.textFieldLambda.getText()); //obtenemos Erlang indicados por el usuario
        this.HoldingTime = (Erlang / Lambda); // Erlang / Lambda
        this.FsMinimo = Integer.parseInt(this.textFieldFSminimo.getText()); //obtenemos FSminimo indicados por el usuario
        this.FsMaximo = Integer.parseInt(this.textFieldFSmaximo.getText()); //obtenemos FSmaximo indicados por el usuario

        //Guardar el seleccionado en la lista de algoritmos seleccionados, más adelante ver como agregar más algoritmos a la lista
        List algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
        String algoritmoSeleccionado = (String) algoritmosRuteoSeleccionados.get(0);
        //System.out.println("El algoritmosRuteoSeleccionados22:"+algoritmoSeleccionado);
        this.algoritmosCompletosParaGraficar.add(cantidadDeAlgoritmosTotalSeleccionados, algoritmoSeleccionado);
        this.cantidadDeAlgoritmosTotalSeleccionados++;
        
        //parámetros desfragmentación
        double entropiaMin = Double.parseDouble(this.textFieldEntropíaMin.getText());
        double msiMin = Double.parseDouble(this.textFieldCantHormigas.getText());
        double bfrMin = Double.parseDouble(this.textFieldBFRMin.getText());
        double lightPathMax = Double.parseDouble(this.textFieldLightPathMax.getText());
        double pathConsMin = Double.parseDouble(this.textFieldPathConsMin.getText());
        double entroUsoMin = Double.parseDouble(this.textFieldEntroUsoMin.getText());

        GrafoMatriz G[] = new GrafoMatriz[this.algoritmosCompletosParaGraficar.size()]; // Se tiene una matriz de adyacencia por algoritmo RSA elegidos para por el usuario
        ListaEnlazada[] caminosDeDosEnlaces = null;
        Demanda d = new Demanda();  // Demanda a recibir
        File archivoDemandas = null;
        Resultado r = new Resultado(); // resultado obtenido en una demanda. Si r==null se cuenta como bloqueo
        String mensajeError = "Seleccione: "; // mensaje de error a mostrar eal usuario si no ha completado los parametros de
        // simulacion

        List<String> RSA = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        ResultadoRuteo r1 = new ResultadoRuteo(); // resultado optenido luego de ejecutarse un algoritmo de ruteo

        int E = (int) this.spinnerErlang.getValue(); // se obtiene el limite de carga (Erlang) de trafico seleccionado por el usuario
        ArrayList<Demanda> demandasPorUnidadTiempo = new ArrayList<>(); //ArrayList que contiene las demandas para una unidad de tiempo T
        rutasEstablecidas = new ArrayList();
        arrayRutas = new ArrayList<>();
        resultadoRuteo = new ArrayList<>();
        listaKSP = new ArrayList<>();
        int k = -1; // contador auxiliar
        //int paso = (int) this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD = 0; // contador de demandas totales
        int tiempoT = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        double anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText()); // ancho de FS
        //factor del tiempo de simulacion especificado por el usuario

        System.out.println("El ancho del FS es:" + anchoFS);
        System.out.println("Cantidad de FS por enlace:" + capacidadPorEnlace);
        System.out.println("Cantidad Algoritmos:" + this.cantidadDeAlgoritmosTotalSeleccionados);

        //if(this.listaDemandas.getSelectedIndex()>=0 && this.listaAlgoritmosRuteo.getSelectedIndex()>=0 && 
        //        this.listaRedes.getSelectedIndex()>=0 && this.listaAlgoritmosAS.getSelectedIndex()>=0 && this.cantidadDeAlgoritmosTotalSeleccionados >0){ // si todos los parametros fueron seleccionados
        if (this.listaAlgoritmosRuteo.getSelectedIndex() >= 0 && this.listaRedes.getSelectedIndex() >= 0 && this.cantidadDeAlgoritmosTotalSeleccionados > 0) {

            RSA = this.algoritmosCompletosParaGraficar; // obtenemos los algoritmos RSA seleccionados

            //String demandaSeleccionada = this.listaDemandas.getSelectedValue(); // obtenemos el tipo de trafico seleccionado
            int[] conexid = new int[RSA.size()];

            for (int i = 0; i < conexid.length; i++) {
                conexid[i] = 0;
            }

            int[] contB = new int[RSA.size()]; // array encargado de almacenar en cada posicion la cantidad de bloqueo para cada
            // algoritmo seleccionado
            List prob[] = new LinkedList[RSA.size()]; // probabilidad de bloqueo para cada algoritmo RSA selecciondo 

            for (int i = 0; i < prob.length; i++) {
                prob[i] = new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                // obtenidad en cada simulacion
            }

            switch (redSeleccionada) { // cargamos los datos en las matrices de adyacencia segun la topologia seleccionada
                case "Red 0":
                    //de ´rueba no utilizado
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(0).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(0));
                    }
                    break;
                case "NSFNet":
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(1));
                    }
                    caminosDeDosEnlaces = Utilitarios.hallarCaminosTomadosDeADos(this.Redes.getTopologia(1), 14, 21);
                    break;
                case "ARPA-2":
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        G[i].insertarDatos(this.Redes.getTopologia(2));
                    }
                    caminosDeDosEnlaces = Utilitarios.hallarCaminosTomadosDeADos(this.Redes.getTopologia(2), 21, 26);
            }
            try {
                //while (earlang <= E) { // mientras no se llega a la cargad de trafico maxima
                archivoDemandas = Utilitarios.generarArchivoDemandas(Lambda, tiempoTotal, FsMinimo, FsMaximo, G[0].getCantidadDeVertices(), HoldingTime, Erlang);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Construimos el nombre del archivo con la fecha y hora
            Calendar calendario = new GregorianCalendar();
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            anho = calendario.get(Calendar.YEAR);
            File carpeta = new File(System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\");
            String detallesNombre = ""+Lambda + "k_" + tiempoTotal + "t-" + RSA.get(0) + "-" + dia + "-" + mes + "-" + anho + "-" + hora + "_" + minutos + "_" + segundos + ".txt";
            String ruta = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Resultado"+detallesNombre;
            String rutaDefrag = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Defrag"+detallesNombre;
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File archivoResultados = new File(ruta);
            File archivoDefrag = new File(rutaDefrag);
            for (int i = 1; i <= tiempoT; i++) {
                try {
                    demandasPorUnidadTiempo = Utilitarios.leerDemandasPorTiempo(archivoDemandas, i); //lee las demandas para el tiempo i
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Demanda demanda : demandasPorUnidadTiempo) { // para cada demanda
                    ListaEnlazada[] ksp = Utilitarios.KSP(G[0], demanda.getOrigen(), demanda.getDestino(), 5); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=5 (pude ser mas, cambiar dependiendo de la necesidad)
                    for (int a = 0; a < RSA.size(); a++) {

                        String algoritmoAejecutar = RSA.get(a);

                        switch (algoritmoAejecutar) {
                            case "FA":
                                r = Algoritmos_Defrag_ProAct.Def_FA(G[a], demanda, ksp, capacidadPorEnlace);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    rutasEstablecidas.add(demanda.getTiempo());
                                    arrayRutas.add(ksp[r.getCamino()]);
                                    resultadoRuteo.add(r);
                                    listaKSP.add(ksp);
                                } else {
                                    contB[a]++;
                                    contBloqueos++;
                                }
                                break;
                            case "FA-CA":
                                r = Algoritmos_Defrag_ProAct.Def_FACA(G[a], demanda, ksp, capacidadPorEnlace);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    rutasEstablecidas.add(demanda.getTiempo());
                                    arrayRutas.add(ksp[r.getCamino()]);
                                    resultadoRuteo.add(r);
                                } else {
                                    contB[a]++;
                                    contBloqueos++;
                                }
                                break;
                           case "MTLSC":
                                r = Algoritmos.MTLSC_Algorithm(G[a], demanda, ksp, capacidadPorEnlace);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    rutasEstablecidas.add(demanda.getTiempo());
                                    arrayRutas.add(ksp[r.getCamino()]);
                                    resultadoRuteo.add(r);
                                } else {
                                    contB[a]++;
                                    contBloqueos++;
                                }
                                break;
                        }

                    }
                    contD++;
                    
                    //si la opción paso a paso está activada entonces actualiza la tabla de estados de los enlaces
//                    if(this.jCheckBoxPasoaPaso.isSelected()) {
//                        //o sino ver como hacer con hilos
//                    }
                }
                for (int a = 0; a < RSA.size(); a++) {
                    //Escribimos el archivo de resultados
                    entropia = msi = bfr = 0.0;
                    entropia = G[a].entropia();
                    msi = Metricas.MSI(G[a], capacidadPorEnlace);
                    bfr = Metricas.BFR(G[a], capacidadPorEnlace);
                    pathConsec = Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidadPorEnlace, G[a]);
                    entropiaUso = Metricas.EntropiaPorUso(caminosDeDosEnlaces, capacidadPorEnlace, G[a]);
                    try {
                        Utilitarios.escribirArchivoResultados(archivoResultados, i, contBloqueos, demandasPorUnidadTiempo.size(), entropia, msi, bfr, rutasEstablecidas.size(), pathConsec, entropiaUso);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (int j = 0; j < RSA.size(); j++) {
                    Utilitarios.Disminuir(G[j]);
                }
                //verificar si la ruta sigue activa o no dentro de la red.
                for (int index = 0; index < rutasEstablecidas.size(); index++) {
                    rutasEstablecidas.set(index, rutasEstablecidas.get(index) - 1);
                    if (rutasEstablecidas.get(index) == 0) { //si el tiempo de vida es cero
                        rutasEstablecidas.remove(index); //remover del contador de rutas establecidas
                        arrayRutas.remove(index); //remover la ruta de la lista de rutas vigentes
                        resultadoRuteo.remove(index);//remueve de la lista de resultados de ruteo
                        listaKSP.remove(index);
                    }
                }
                
//                //desfragmentación
//                try {
//                    if (entropiaMin > 0 && entropia >= entropiaMin){
//                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadE, G[0], listaKSP, archivoDefrag, i, cantHormACO);
//                    }
//                    if (msiMin > 0 && msi >= msiMin){
//                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadE, G[0], listaKSP, archivoDefrag, i, cantHormACO);
//                    }
//                    if (bfrMin > 0 && bfr >= bfrMin){
//                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadE, G[0], listaKSP, archivoDefrag, i, cantHormACO);
//                    }
//                    if (lightPathMax > 0 && rutasEstablecidas.size() >= lightPathMax){
//                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadE, G[0], listaKSP, archivoDefrag, i, cantHormACO);
//                    }                
//                    if (pathConsMin > 0 && pathConsec >= pathConsMin){
//                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadE, G[0], listaKSP, archivoDefrag, i, cantHormACO);
//                    }                
//                    if (entroUsoMin > 0 && entropiaUso >= entroUsoMin){
//                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadE, G[0], listaKSP, archivoDefrag, i, cantHormACO);
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
                    if(i==250 || i == 500 || i == 750){
                    try {
                        Utilitarios.seleccionDeRutas(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, 5, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantHormACO, this.jTableEstadoEnlaces);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                
                contBloqueos = 0;
                
                //imprimir estado si marco paso a paso
                if(jCheckBoxPasoaPaso.isSelected()){
                    Utilitarios.actualizarTablaEstadoEnlaces(G[0], jTableResultados, capacidadPorEnlace);
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            ++k;
            // almacenamos la probablidad de bloqueo final para cada algoritmo
            for (int a = 0; a < RSA.size(); a++) {
                prob[a].add(((double) contB[a] / contD));
                System.out.println("Probabilidad: " + (double) prob[a].get(k) + " Algoritmo: " + RSA.get(a));
            }
            this.etiquetaError.setText("Simulacion Terminada...");
            
            //Si no existe escibir 0,0,0
            if(!archivoDefrag.exists()){
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoDefrag));
                            bw.write("" + 0);
                            bw.write(",");
                            bw.write("" + 0);
                            bw.write(",");
                            bw.write("" + 0);
                            bw.write("\r\n");
                            bw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            //RESULTADOS

            // una vez finalizado, graficamos el resultado.
            //leemos el archivo de resultados
            List<XYTextAnnotation> annotation = new LinkedList<>();
            String linea;
            int contLinea = 0;
            XYSeries series[] = new XYSeries[7];
            XYSeriesCollection datos = new XYSeriesCollection();
            
            //tablas
            DefaultTableModel modelBloqueos = (DefaultTableModel) this.jTableResultadosBloqueos.getModel(); //todos
            DefaultTableModel modelResultados = (DefaultTableModel) this.jTableResultados.getModel(); //bloqueos

            FileReader fr;
            try {
                fr = new FileReader(archivoResultados);
                BufferedReader br = new BufferedReader(fr);
                series[0] = new XYSeries("Bloqueos");
                series[1] = new XYSeries("Entropía");
                series[2] = new XYSeries("MSI");
                series[3] = new XYSeries("BFR");
                series[4] = new XYSeries("Cantidad de Light Paths");
                series[5] = new XYSeries("Path Consecutiveness");
                series[6] = new XYSeries("Entropía por su uso");

                while (((linea = br.readLine()) != null)) {
                    contLinea++;
                    String[] line = linea.split(",", 9);
                    
                    //agrega a la tabla los Resultados
                        modelResultados.addRow(new Object[]{line[0], line[1], line[2], (double) Double.parseDouble(line[3]), (double) Double.parseDouble(line[4]), (double) Double.parseDouble(line[5]), (double) Double.parseDouble(line[6]), (double) Double.parseDouble(line[7]), (double) Double.parseDouble(line[8])});
                    
                    //agrega en annotation todos los bloqueos para después agregarlos a los gráficos
                    if ((double) Double.parseDouble(line[2]) > 0) {
                        annotation.add(new XYTextAnnotation(line[2], (double) Double.parseDouble(line[0]), 0.02));
                        //agrega a la tabla los bloqueos
                        modelBloqueos.addRow(new Object[]{line[0], line[1], line[2], (double) Double.parseDouble(line[3]), (double) Double.parseDouble(line[4]), (double) Double.parseDouble(line[5]), (double) Double.parseDouble(line[6]), (double) Double.parseDouble(line[7]), (double) Double.parseDouble(line[8])});
                    }

                    series[0].add(contLinea, (double) Double.parseDouble(line[2]));
                    series[1].add(contLinea, (double) Double.parseDouble(line[3]));
                    series[2].add(contLinea, (double) Double.parseDouble(line[4]));
                    series[3].add(contLinea, (double) Double.parseDouble(line[5]));
                    series[4].add(contLinea, (double) Double.parseDouble(line[6]));
                    series[5].add(contLinea, (double) Double.parseDouble(line[7]));
                    series[6].add(contLinea, (double) Double.parseDouble(line[8]));
                }
                
                //hallar el max y min de los resultados
                guardarMaxMin(this.jTableResultados, this.jTableResultadosMinMax);
                
                //hallar el max y min de los bloqueos
                if (contB[0]!=0){
                    guardarMaxMin(this.jTableResultadosBloqueos, this.jTableResultadosBloqueosMinMax);
                }
                
                //graficar
                Utilitarios.GraficarResultado(series, annotation, this.panelResultados);
                
                //estado final de los enlaces
                Utilitarios.actualizarTablaEstadoEnlaces(G[0], jTableResultados, capacidadPorEnlace);


            } catch (IOException ioe) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ioe);  
            }
            
            //tabla desfragmentaciones
            String rutaResultadosDefrag = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Defrag"+detallesNombre;
            File archivoResultadosDefrag = new File(rutaResultadosDefrag);
            try {
                Utilitarios.cargarTablaResultadosDefrag(archivoResultadosDefrag, this.jTableResultadosDefrag);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
            }


            //Utilitarios.GraficarResultado(prob, this.panelResultado, "Resultado de la Simulación", RSA, paso);
            String demandasTotales = "" + contD; // mostramos la cantidad de demandas totales recibidas
            this.etiquetaDemandasTotales.setText(demandasTotales);
            this.etiquetaBloqueosTotales.setText("" + contB[0]);
            this.etiquetaTextoBloqueosTotales.setVisible(true);
            this.etiquetaDemandasTotales.setVisible(true);
            this.etiquetaTextoDemandasTotales.setVisible(true);
            this.etiquetaBloqueosTotales.setVisible(true);

            ////////Vaciar listas para las siguientes simulaciones///////////////
            /////////////////////////////////////////////////////////////////////
            //this.algoritmosCompletosParaEjecutar.clear();
            //this.algoritmosCompletosParaGraficar.clear();
            //this.cantidadDeAlgoritmosRuteoSeleccionados = 0;
            this.cantidadDeAlgoritmosTotalSeleccionados = 0;

        } else { // control de errores posibles realizados al no completar los parametros de simulacion
            if (this.listaAlgoritmosRuteo.getSelectedIndex() < 0) {
                if (mensajeError == "Seleccione ") {
                    mensajeError = mensajeError + "Algoritmo RSA";
                } else {
                    mensajeError = mensajeError + ", Algoritmo RSA";
                }
            }
            if (this.listaRedes.getSelectedIndex() < 0) {
                if (mensajeError == "Seleccione ") {
                    mensajeError = mensajeError + "Topologia";
                } else {
                    mensajeError = mensajeError + ", Topologia";
                }
            }
            if (mensajeError != "Seleccione ") {
                this.etiquetaError.setText(mensajeError);
            }
        }
    }//GEN-LAST:event_botonEjecutarSimulacionActionPerformed

    // get the maximum and the minimum
    public void guardarMaxMin(JTable Tabla, JTable TablaMaxMin){
        DefaultTableModel model = (DefaultTableModel) TablaMaxMin.getModel();
        ArrayList<Double> list0 = new ArrayList<>();
        ArrayList<Double> list1 = new ArrayList<>();
        ArrayList<Double> list2 = new ArrayList<>();
        ArrayList<Double> list3 = new ArrayList<>();
        ArrayList<Double> list4 = new ArrayList<>();
        ArrayList<Double> list5 = new ArrayList<>();
        for(int i = 0; i < Tabla.getRowCount(); i++){
            list0.add(Double.parseDouble(Tabla.getValueAt(i,3).toString()));
            list1.add(Double.parseDouble(Tabla.getValueAt(i,4).toString()));
            list2.add(Double.parseDouble(Tabla.getValueAt(i,5).toString()));
            list3.add(Double.parseDouble(Tabla.getValueAt(i,6).toString()));
            list4.add(Double.parseDouble(Tabla.getValueAt(i,7).toString()));
            list5.add(Double.parseDouble(Tabla.getValueAt(i,8).toString()));
        }
        
        Double maxEntro;
        Double minEntro;
        Double maxMSI;
        Double minMSI;
        Double maxBRF;
        Double minBRF;
        Double maxLP;
        Double minLP;
        Double maxPC;
        Double minPC;
        Double maxEntroUso;
        Double minEntroUso;
        
        maxEntro = Collections.max(list0);
        minEntro = Collections.min(list0);
        maxMSI = Collections.max(list1);
        minMSI = Collections.min(list1);
        maxBRF = Collections.max(list2);
        minBRF = Collections.min(list2);
        maxLP = Collections.max(list3);
        minLP = Collections.min(list3);
        maxPC = Collections.max(list4);
        minPC = Collections.min(list4);
        maxEntroUso = Collections.max(list5);
        minEntroUso = Collections.min(list5);
        
        
        //agrega a la tabla los bloqueos
        model.addRow(new Object[]{minEntro, minMSI, minBRF, minLP, minPC, minEntroUso});
        model.addRow(new Object[]{maxEntro, maxMSI, maxBRF, maxLP, maxPC, maxEntroUso});
//        Tmax.setText(Integer.toString(max));
//        Tmin.setText(Integer.toString(min));
    }
    

            
    private void listaAlgoritmosRuteoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaAlgoritmosRuteoMouseClicked
        // TODO add your handling code here:
//        List algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
//        String algoritmoSeleccionado = (String) algoritmosRuteoSeleccionados.get(0);
//        //System.out.println("El algoritmosRuteoSeleccionados22:"+algoritmoSeleccionado);
//        if (algoritmoSeleccionado.equals("FAR")) {
//            this.panelAsignacionSpectro.setVisible(true);
//        } else {
//            this.panelAsignacionSpectro.setVisible(false);
//        }


    }//GEN-LAST:event_listaAlgoritmosRuteoMouseClicked

    private void listaRedesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaRedesActionPerformed
        setearRed();
    }//GEN-LAST:event_listaRedesActionPerformed

    private void setearRed() {
        if (this.listaRedes.getSelectedIndex() >= 0) {
            ImageIcon Img = new ImageIcon();
            
            String redseleccionada = (String) this.listaRedes.getSelectedItem();
            switch (redseleccionada) {
                case "NSFNet":
                    Img = new ImageIcon(getClass().getResource(("Imagenes/" + ("Red 1.png"))));
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(1).getCapacidadTotal() / this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(1).getAnchoFS()));
                    break;
                case "ARPA-2":
                    Img = new ImageIcon(getClass().getResource(("Imagenes/" + ("Red 2.png"))));
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(2).getCapacidadTotal() / this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(2).getAnchoFS()));
                    break;
            }
            
            etiquetaImagenTopologia.setBounds(150, 110, 150, 110);
            etiquetaImagenTopologia.setIcon(Img);
            etiquetaImagenTopologia.setVisible(true);
            etiquetaImagenTopologia.setOpaque(false);
        }
    }
    
    public static void reiniciarJTableRows(javax.swing.JTable Tabla){
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while(modelo.getRowCount()>0)modelo.removeRow(0);
    }
    
    public static void reiniciarJTableColumns(javax.swing.JTable Tabla){
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        modelo.setColumnCount(0);
    }
    
    private void textFieldLambdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldLambdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldLambdaActionPerformed

    private void textFieldAnchoFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldAnchoFSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldAnchoFSActionPerformed

    private void textFieldEntropíaMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldEntropíaMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldEntropíaMinActionPerformed

    private void textFieldCantHormigasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldCantHormigasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldCantHormigasActionPerformed

    private void textFieldBFRMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldBFRMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldBFRMinActionPerformed

    private void textFieldLightPathMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldLightPathMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldLightPathMaxActionPerformed

    private void textFieldPathConsMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPathConsMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldPathConsMinActionPerformed

    private void textFieldEntroUsoMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldEntroUsoMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldEntroUsoMinActionPerformed

    private void jCheckBoxPasoaPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPasoaPasoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxPasoaPasoActionPerformed

    private void textFieldMSIMin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMSIMin1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldMSIMin1ActionPerformed

    private void textFieldMejoraACOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMejoraACOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldMejoraACOActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new VentanaPrincipal_Defrag_ProAct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEjecutarSimulacion;
    private javax.swing.JLabel etiquetaAnchoFSActual;
    private javax.swing.JLabel etiquetaAnchoFSActual1;
    private javax.swing.JLabel etiquetaAnchoFSActual10;
    private javax.swing.JLabel etiquetaAnchoFSActual11;
    private javax.swing.JLabel etiquetaAnchoFSActual12;
    private javax.swing.JLabel etiquetaAnchoFSActual13;
    private javax.swing.JLabel etiquetaAnchoFSActual14;
    private javax.swing.JLabel etiquetaAnchoFSActual15;
    private javax.swing.JLabel etiquetaAnchoFSActual16;
    private javax.swing.JLabel etiquetaAnchoFSActual17;
    private javax.swing.JLabel etiquetaAnchoFSActual18;
    private javax.swing.JLabel etiquetaAnchoFSActual19;
    private javax.swing.JLabel etiquetaAnchoFSActual2;
    private javax.swing.JLabel etiquetaAnchoFSActual20;
    private javax.swing.JLabel etiquetaAnchoFSActual3;
    private javax.swing.JLabel etiquetaAnchoFSActual4;
    private javax.swing.JLabel etiquetaAnchoFSActual5;
    private javax.swing.JLabel etiquetaAnchoFSActual6;
    private javax.swing.JLabel etiquetaAnchoFSActual7;
    private javax.swing.JLabel etiquetaAnchoFSActual8;
    private javax.swing.JLabel etiquetaAnchoFSActual9;
    private javax.swing.JLabel etiquetaBloqueosTotales;
    private javax.swing.JLabel etiquetaCapacidadActual;
    private javax.swing.JLabel etiquetaDemandasTotales;
    private javax.swing.JLabel etiquetaError;
    private javax.swing.JLabel etiquetaImagenTopologia;
    private javax.swing.JLabel etiquetaRSA1;
    private javax.swing.JLabel etiquetaRSA2;
    private javax.swing.JLabel etiquetaRSA3;
    private javax.swing.JLabel etiquetaTextoBloqueosTotales;
    private javax.swing.JLabel etiquetaTextoDemandasTotales;
    private javax.swing.JLabel etiquetaTextoMax;
    private javax.swing.JLabel etiquetaTextoMax1;
    private javax.swing.JLabel etiquetaTextoMin;
    private javax.swing.JLabel etiquetaTextoMin1;
    private javax.swing.JLabel etiquetaTiempoActual;
    private javax.swing.JLabel etiquetaTopologia;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JCheckBox jCheckBoxPasoaPaso;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableEstadoEnlaces;
    private javax.swing.JTable jTableResultados;
    private javax.swing.JTable jTableResultadosBloqueos;
    private javax.swing.JTable jTableResultadosBloqueosMinMax;
    private javax.swing.JTable jTableResultadosDefrag;
    private javax.swing.JTable jTableResultadosMinMax;
    private javax.swing.JList<String> listaAlgoritmosRuteo;
    private javax.swing.JComboBox<String> listaRedes;
    private javax.swing.JScrollPane panelResultados;
    private javax.swing.JSpinner spinnerErlang;
    private javax.swing.JSpinner spinnerTiempoSimulacion;
    private javax.swing.JTextField textFieldAnchoFS;
    private javax.swing.JTextField textFieldBFRMin;
    private javax.swing.JTextField textFieldCantHormigas;
    private javax.swing.JTextField textFieldCapacidadEnlace;
    private javax.swing.JTextField textFieldEntroUsoMin;
    private javax.swing.JTextField textFieldEntropíaMin;
    private javax.swing.JTextField textFieldFSmaximo;
    private javax.swing.JTextField textFieldFSminimo;
    private javax.swing.JTextField textFieldLambda;
    private javax.swing.JTextField textFieldLightPathMax;
    private javax.swing.JTextField textFieldMSIMin1;
    private javax.swing.JTextField textFieldMejoraACO;
    private javax.swing.JTextField textFieldPathConsMin;
    // End of variables declaration//GEN-END:variables

}
