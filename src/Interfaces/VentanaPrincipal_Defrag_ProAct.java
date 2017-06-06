package Interfaces;

import EON.Utilitarios.*;
import EON.*;
import EON.Algoritmos.*;
import EON.Metricas.Metricas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        etiquetaTextoMax = new javax.swing.JLabel();
        etiquetaDemandasTotales = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinnerErlang = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        textFieldCapacidadEnlace = new javax.swing.JTextField();
        etiquetaRSA1 = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResultadosBloqueosMinMax = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        panelResultados = new javax.swing.JScrollPane();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableResultadosBloqueos = new javax.swing.JTable();
        etiquetaTextoDemandasTotales = new javax.swing.JLabel();
        etiquetaTextoMin = new javax.swing.JLabel();

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 112, 90));

        botonEjecutarSimulacion.setText("Simular");
        botonEjecutarSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarSimulacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonEjecutarSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 100, 40));

        etiquetaTopologia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiquetaTopologia.setText("Topologia");
        getContentPane().add(etiquetaTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 70, 20));

        etiquetaError.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(etiquetaError, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 430, 20));

        etiquetaCapacidadActual.setText("Capacidad");
        getContentPane().add(etiquetaCapacidadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 70, 20));

        etiquetaTiempoActual.setText("Tiempo de Simulacion");
        getContentPane().add(etiquetaTiempoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 140, 20));

        spinnerTiempoSimulacion.setModel(new javax.swing.SpinnerNumberModel(100, 50, 100000, 25));
        spinnerTiempoSimulacion.setToolTipText("");
        spinnerTiempoSimulacion.setRequestFocusEnabled(false);
        spinnerTiempoSimulacion.setValue(1000);
        getContentPane().add(spinnerTiempoSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 60, 20));

        jLabel2.setText("FSs por Enlace");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, -1, 20));

        etiquetaImagenTopologia.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaImagenTopologia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        etiquetaImagenTopologia.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        etiquetaImagenTopologia.setOpaque(true);
        etiquetaImagenTopologia.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(etiquetaImagenTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 150, 110));

        jLabel5.setText("unid.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, 20));

        etiquetaTextoMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax.setText("max");
        etiquetaTextoMax.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        etiquetaTextoMax.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        getContentPane().add(etiquetaTextoMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 640, 30, 20));

        etiquetaDemandasTotales.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 50, 20));

        jLabel4.setText("Trafico Maximo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 20));

        spinnerErlang.setModel(new javax.swing.SpinnerNumberModel(100, 10, 1500, 100));
        getContentPane().add(spinnerErlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 50, -1));

        jLabel6.setText("Erlang");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 50, 20));

        textFieldCapacidadEnlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCapacidadEnlace.setText("50");
        getContentPane().add(textFieldCapacidadEnlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 50, -1));

        etiquetaRSA1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA1.setText("Algoritmo de Ruteo");
        getContentPane().add(etiquetaRSA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Otros");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        listaRedes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NSFNet", "ARPA-2" }));
        listaRedes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaRedesActionPerformed(evt);
            }
        });
        getContentPane().add(listaRedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 80, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Defragmentación ProActiva");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Resultados");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        etiquetaAnchoFSActual1.setText("Lambda");
        getContentPane().add(etiquetaAnchoFSActual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 50, 20));

        textFieldLambda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldLambda.setText("5");
        textFieldLambda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldLambdaActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldLambda, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 40, 20));

        etiquetaAnchoFSActual2.setText("mín");
        getContentPane().add(etiquetaAnchoFSActual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 30, 20));

        textFieldFSminimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSminimo.setText("2");
        getContentPane().add(textFieldFSminimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 30, 20));

        etiquetaAnchoFSActual3.setText("FS Rango");
        getContentPane().add(etiquetaAnchoFSActual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 70, 20));

        etiquetaAnchoFSActual4.setText("máx");
        getContentPane().add(etiquetaAnchoFSActual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 30, 20));

        textFieldFSmaximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSmaximo.setText("8");
        getContentPane().add(textFieldFSmaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 30, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Red");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel3.setText("GHz");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 30, 20));

        textFieldAnchoFS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldAnchoFS.setText("2");
        textFieldAnchoFS.setEnabled(false);
        textFieldAnchoFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldAnchoFSActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldAnchoFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 30, 20));

        etiquetaAnchoFSActual.setText("Ancho FS");
        getContentPane().add(etiquetaAnchoFSActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 60, 20));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, 290));

        etiquetaTextoBloqueosTotales.setText("Total Bloqueos:");
        getContentPane().add(etiquetaTextoBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 90, 20));

        etiquetaBloqueosTotales.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 50, 20));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 290, 63));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 440, -1));

        panelResultados.setViewportView(filler1);

        getContentPane().add(panelResultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 980, 680));

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

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 430, 250));

        etiquetaTextoDemandasTotales.setText("Total Demandas:");
        getContentPane().add(etiquetaTextoDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 100, 20));

        etiquetaTextoMin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMin.setText("min");
        etiquetaTextoMin.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaTextoMin.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        getContentPane().add(etiquetaTextoMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 630, 30, 20));

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
        
        //inicializamos algunas variables
        this.cantidadDeAlgoritmosTotalSeleccionados = 0;
        this.algoritmosCompletosParaGraficar.clear();
        
        //borramos los resultados que están en la tabla de bloqueos
        reiniciarJTable(this.jTableResultadosBloqueos);
        reiniciarJTable(this.jTableResultadosBloqueosMinMax);

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
        int earlang = 0; //Carga de trafico en cada simulacion
        int k = -1; // contador auxiliar
        //int paso = (int) this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD = 0; // contador de demandas totales
        int tiempoT = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        int capacidadE = Integer.parseInt(this.textFieldCapacidadEnlace.getText().toString()); // espectro por enalce
        double anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText().toString()); // ancho de FS
        //factor del tiempo de simulacion especificado por el usuario

        System.out.println("El ancho del FS es:" + anchoFS);
        System.out.println("Cantidad de FS por enlace:" + capacidadE);
        System.out.println("Cantidad Algoritmos:" + this.cantidadDeAlgoritmosTotalSeleccionados);

        //if(this.listaDemandas.getSelectedIndex()>=0 && this.listaAlgoritmosRuteo.getSelectedIndex()>=0 && 
        //        this.listaRedes.getSelectedIndex()>=0 && this.listaAlgoritmosAS.getSelectedIndex()>=0 && this.cantidadDeAlgoritmosTotalSeleccionados >0){ // si todos los parametros fueron seleccionados
        if (this.listaAlgoritmosRuteo.getSelectedIndex() >= 0 && this.listaRedes.getSelectedIndex() >= 0 && this.cantidadDeAlgoritmosTotalSeleccionados > 0) {
            this.etiquetaError.setVisible(true); // habilitamos la etiqueta de error

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
                archivoDemandas = Utilitarios.generarArchivoDemandas(Lambda, tiempoTotal, FsMinimo, FsMaximo, G[0].getCantidadDeVertices(), HoldingTime);
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
            String ruta = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Resultado" + Lambda + "k_" + tiempoTotal + "t-" + RSA.get(0) + "-" + dia + "-" + mes + "-" + anho + "-" + hora + "_" + minutos + "_" + segundos + ".txt";
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File archivoResultados = new File(ruta);
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
                                r = Algoritmos_Defrag_ProAct.Def_FA(G[a], demanda, ksp, capacidadE);
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
                            case "FA-CA":
                                r = Algoritmos_Defrag_ProAct.Def_FACA(G[a], demanda, ksp, capacidadE);
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
                                r = Algoritmos.MTLSC_Algorithm(G[a], demanda, ksp, capacidadE);
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
                }
                for (int a = 0; a < RSA.size(); a++) {
                    //Escribimos el archivo de resultados
                    entropia = msi = bfr = 0.0;
                    entropia = G[a].entropia();
                    msi = Metricas.MSI(G[a], capacidadE);
                    bfr = Metricas.BFR(G[a], capacidadE);
                    pathConsec = Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidadE, G[a]);
                    entropiaUso = Metricas.EntropiaPorUso(caminosDeDosEnlaces, capacidadE, G[a]);
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
                    }
                }
                contBloqueos = 0;
            }
            ++k;
            // almacenamos la probablidad de bloqueo final para cada algoritmo
            for (int a = 0; a < RSA.size(); a++) {
                prob[a].add(((double) contB[a] / contD));
                System.out.println("Probabilidad: " + (double) prob[a].get(k) + " Algoritmo: " + RSA.get(a));
            }
            this.etiquetaError.setText("Simulacion Terminada...");

            // una vez finalizado, graficamos el resultado.
            //leemos el archivo de resultados
            List<XYTextAnnotation> annotation = new LinkedList<>();
            String linea;
            int contLinea = 0;
            XYSeries series[] = new XYSeries[7];
            XYSeriesCollection datos = new XYSeriesCollection();
            
            //tabla
            DefaultTableModel model = (DefaultTableModel) this.jTableResultadosBloqueos.getModel();

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
                    
                    //agrega en annotation todos los bloqueos para después agregarlos a los gráficos
                    if ((double) Double.parseDouble(line[2]) > 0) {
                        annotation.add(new XYTextAnnotation(line[2], (double) Double.parseDouble(line[0]), 0.02));
                        //agrega a la tabla los bloqueos
                        model.addRow(new Object[]{line[0], line[1], line[2], (double) Double.parseDouble(line[3]), (double) Double.parseDouble(line[4]), (double) Double.parseDouble(line[5]), (double) Double.parseDouble(line[6]), (double) Double.parseDouble(line[7]), (double) Double.parseDouble(line[8])});
                    }

                    series[0].add(contLinea, (double) Double.parseDouble(line[2]));
                    series[1].add(contLinea, (double) Double.parseDouble(line[3]));
                    series[2].add(contLinea, (double) Double.parseDouble(line[4]));
                    series[3].add(contLinea, (double) Double.parseDouble(line[5]));
                    series[4].add(contLinea, (double) Double.parseDouble(line[6]));
                    series[5].add(contLinea, (double) Double.parseDouble(line[7]));
                    series[6].add(contLinea, (double) Double.parseDouble(line[8]));
                }
                
                //hallar el max y min de la tabla
                if (contB[0]!=0){
                    getMaxMin();
                }
                
                //graficar
                Utilitarios.GraficarResultado(series, annotation, this.panelResultados);


            } catch (IOException ioe) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ioe);  
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
    public void getMaxMin(){
        DefaultTableModel model2 = (DefaultTableModel) this.jTableResultadosBloqueosMinMax.getModel();
        ArrayList<Double> list0 = new ArrayList<Double>();
        ArrayList<Double> list1 = new ArrayList<Double>();
        ArrayList<Double> list2 = new ArrayList<Double>();
        ArrayList<Double> list3 = new ArrayList<Double>();
        ArrayList<Double> list4 = new ArrayList<Double>();
        ArrayList<Double> list5 = new ArrayList<Double>();
        for(int i = 0; i < jTableResultadosBloqueos.getRowCount(); i++){
            list0.add(Double.parseDouble(jTableResultadosBloqueos.getValueAt(i,3).toString()));
            list1.add(Double.parseDouble(jTableResultadosBloqueos.getValueAt(i,4).toString()));
            list2.add(Double.parseDouble(jTableResultadosBloqueos.getValueAt(i,5).toString()));
            list3.add(Double.parseDouble(jTableResultadosBloqueos.getValueAt(i,6).toString()));
            list4.add(Double.parseDouble(jTableResultadosBloqueos.getValueAt(i,7).toString()));
            list5.add(Double.parseDouble(jTableResultadosBloqueos.getValueAt(i,8).toString()));
        }
        
        
        Double maxEntro = 0.0;
        Double minEntro = 0.0;
        Double maxMSI = 0.0;
        Double minMSI = 0.0;
        Double maxBRF = 0.0;
        Double minBRF = 0.0;
        Double maxLP = 0.0;
        Double minLP = 0.0;
        Double maxPC = 0.0;
        Double minPC = 0.0;
        Double maxEntroUso = 0.0;
        Double minEntroUso = 0.0;
        
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
        model2.addRow(new Object[]{minEntro, minMSI, minBRF, minLP, minPC, minEntroUso});
        model2.addRow(new Object[]{maxEntro, maxMSI, maxBRF, maxLP, maxPC, maxEntroUso});
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
    
    public static void reiniciarJTable(javax.swing.JTable Tabla){
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while(modelo.getRowCount()>0)modelo.removeRow(0);
    }
    
    private void textFieldLambdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldLambdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldLambdaActionPerformed

    private void textFieldAnchoFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldAnchoFSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldAnchoFSActionPerformed

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
    private javax.swing.JLabel etiquetaAnchoFSActual2;
    private javax.swing.JLabel etiquetaAnchoFSActual3;
    private javax.swing.JLabel etiquetaAnchoFSActual4;
    private javax.swing.JLabel etiquetaBloqueosTotales;
    private javax.swing.JLabel etiquetaCapacidadActual;
    private javax.swing.JLabel etiquetaDemandasTotales;
    private javax.swing.JLabel etiquetaError;
    private javax.swing.JLabel etiquetaImagenTopologia;
    private javax.swing.JLabel etiquetaRSA1;
    private javax.swing.JLabel etiquetaTextoBloqueosTotales;
    private javax.swing.JLabel etiquetaTextoDemandasTotales;
    private javax.swing.JLabel etiquetaTextoMax;
    private javax.swing.JLabel etiquetaTextoMin;
    private javax.swing.JLabel etiquetaTiempoActual;
    private javax.swing.JLabel etiquetaTopologia;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTableResultadosBloqueos;
    private javax.swing.JTable jTableResultadosBloqueosMinMax;
    private javax.swing.JList<String> listaAlgoritmosRuteo;
    private javax.swing.JComboBox<String> listaRedes;
    private javax.swing.JScrollPane panelResultados;
    private javax.swing.JSpinner spinnerErlang;
    private javax.swing.JSpinner spinnerTiempoSimulacion;
    private javax.swing.JTextField textFieldAnchoFS;
    private javax.swing.JTextField textFieldCapacidadEnlace;
    private javax.swing.JTextField textFieldFSmaximo;
    private javax.swing.JTextField textFieldFSminimo;
    private javax.swing.JTextField textFieldLambda;
    // End of variables declaration//GEN-END:variables

}
