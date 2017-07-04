package EON.Utilitarios;

import EON.*;
import EON.Algoritmos.*;
import EON.Metricas.Metricas;
import Interfaces.VentanaPrincipal_Defrag_ProAct;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Team Delvalle
 */
public class Utilitarios {

    /**
     * ************************************************************
     *                                                             *
     * Algoritmos utilizados para realizar los K caminos mas cortos** *
     * *************************************************************
     */
    /*Algoritmo de los k caminos mas cortos*/
    public static ListaEnlazada[] KSP(GrafoMatriz G, int s, int d, int k) {
        ListaEnlazada A[] = new ListaEnlazada[k];
        A[0] = Dijkstra(G, s, d);
        ListaEnlazada B[] = new ListaEnlazada[100];

        Nodo spurNode = new Nodo();
        ListaEnlazada spurPath, rootPath, totalPath;

        int[][] enlacesR = new int[100][2];
        int nodosR[] = new int[100];
        int cont = 0;
        int b = -1;
        int flag;
        for (int j = 1; j < k; j++) {
            for (int i = 0; i < A[j - 1].getTamanho() - 2; i++) {
                spurPath = new ListaEnlazada();
                rootPath = new ListaEnlazada();
                totalPath = new ListaEnlazada();
                flag = 0;
                spurNode = A[j - 1].nodo(i);
                rootPath = A[j - 1].optenerSublista(0, i);
                int h = 0;
                //Elimnar enlaces
                cont = 0;
                while (A[h] != null) {
                    ListaEnlazada p = A[h];
                    if (i < p.getTamanho() - 1) {
                        if (rootPath.comparar(p.optenerSublista(0, i))) {
                            G.acceder(p.nodo(i).getDato(), p.nodo(i + 1).getDato()).setEstado(false);
                            enlacesR[cont][0] = p.nodo(i).getDato();
                            enlacesR[cont][1] = p.nodo(i + 1).getDato();
                            cont++;
                        }
                    }
                    h++;
                }
                //Eliminar Nodos
                h = 0;
                int cont3 = 0;
                while (h < rootPath.getTamanho()) {
                    if (rootPath.nodo(h).getDato() != spurNode.getDato()) {
                        for (int cont2 = 0; cont2 < G.getCantidadDeVertices(); cont2++) {
                            if (G.acceder(rootPath.nodo(h).getDato(), cont2) != null) {
                                G.acceder(rootPath.nodo(h).getDato(), cont2).setEstado(false);
                            }
                        }
                        for (int cont2 = 0; cont2 < G.getCantidadDeVertices(); cont2++) {
                            if (G.acceder(cont2, rootPath.nodo(h).getDato()) != null) {
                                G.acceder(cont2, rootPath.nodo(h).getDato()).setEstado(false);
                            }
                        }
                        nodosR[cont3] = rootPath.nodo(h).getDato();
                        cont3++;
                    }
                    h++;
                }
                //Ver si el spurnode tiene enlaces libres para ir hasta el destino.
                for (int cont2 = 0; cont2 < G.getCantidadDeVertices(); cont2++) {
                    if (G.acceder(spurNode.getDato(), cont2) != null && G.acceder(spurNode.getDato(), cont2).getEstado() == true) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    spurPath = Dijkstra(G, spurNode.getDato(), d);
                    //Camino potencial
                    if (spurPath != null) {
                        totalPath.union(rootPath, spurPath);
                        //Calcular peso del camino potencial
                        if (rootPath.getTamanho() != 1) {
                            int peso = 0;
                            for (int cont2 = 0; cont2 < rootPath.getTamanho() - 1; cont2++) {
                                peso = peso + G.acceder(rootPath.nodo(cont2).getDato(), rootPath.nodo(cont2 + 1).getDato()).getDistancia();
                            }
                            totalPath.nodo(totalPath.getTamanho() - 1).setDato(peso + spurPath.nodo(spurPath.getTamanho() - 1).getDato());
                        }
                        if (verificar(B, b, totalPath)) {
                            b++;
                            B[b] = totalPath;
                        }
                    }
                }
                reestablecerNodosEnlaces(G, enlacesR, cont, nodosR, cont3);
            }
            if (b > -1) {
                ordenar(B, b);
                A[j] = B[0];
                for (int cont2 = 0; cont2 < b; cont2++) {
                    B[cont2] = B[cont2 + 1];
                }
                b--;
            } else {
                // System.out.println("\nSolo existen "+j+" caminos");
                break;
            }
        }
        return A;
    }
    
    public static ListaEnlazada[] KSP_nuevo(GrafoMatriz G, int o, int d, int k) {
        ListaEnlazada ksp[] = new ListaEnlazada[k];
        ListaEnlazada posiblesCaminos[] = new ListaEnlazada[100]; //no creo que hallan m[as de 100 combinaciones antes de que encuentre la cant k sp
        int termino = 0; //bandera para saber si recorrió el grafo y no encontró más caminos a seguir
        int cont = 0; //contado de sh encontrados
        int primera = 0; //bandera para saber si es la primera opci;on del nodo, para saber si agregar o crea un nuevo posibleCamino
        int tamañoPC = 1;
        int hayPCSinTerminar = 0;

        posiblesCaminos[0].insertarAlComienzo(o);
        
        while(ksp[k-1] != null || termino == 1){ //que esté lleno el vector ksp o que ya recorra todo
            for(int i = 0; i < tamañoPC && cont < k; i++){ // de 0 a cant de caminos posibles y si aún no consiguió la cant k que busca
                primera = 0;
                if(posiblesCaminos[i].getFin().getDato() != d){ //solo si este posible camino aún no llegó a destino
                    hayPCSinTerminar = 0;
                    for(int j=0;j<G.getCantidadDeVertices() && cont < k;j++){ // de 0 a cant de caminos nodos y si aún no consiguió la cant k que busca
                        if(G.acceder(posiblesCaminos[i].getFin().getDato(), j)!=null){
                            if (primera == 0){
                                posiblesCaminos[i].insertarAlfinal(j);
                                primera = 1; //las siguientes tiene que crear uno de vuelta
                            }else{ //crea un posible camino nuevo con el mismo antecedente
                                posiblesCaminos[tamañoPC] = posiblesCaminos[i]; //agrega al final
                                posiblesCaminos[tamañoPC].insertarAlfinal(j);
                                tamañoPC++; //tamaño usado del vector
                            }
                            if(j == d){ //si ya es el nodo destino
                                //agrega a la lista de ksp a retornar
                                ksp[cont] = posiblesCaminos[i]; //se puede pio copiar el ksp así? ja
                                cont++;
                            }
                        }
                    }
                }
            }
            if(hayPCSinTerminar == 1){ //ya controló todos
                //no encontró la cant k de SP
                break;
            }
        }
        
        return ksp;
    }

    /*Algoritmo encargado de retornar el resultado del algoritmo de Dijkstra 
      en una lista enlazada con los nodos del camino mas corto.*/
    public static ListaEnlazada Dijkstra(GrafoMatriz G, int o, int d) {
        int aux;
        Tabla t = new Tabla(G.getCantidadDeVertices());
        t.setDistancia(o, 0);
        boolean camino = DM(G, t, o, d);
        if (camino) {
            ListaEnlazada l = new ListaEnlazada();
            aux = d;
            while (aux != o) {
                l.insertarAlComienzo(aux);
                aux = t.getOrigen(aux);
            }
            l.insertarAlComienzo(o);
            l.insertarAlfinal(t.getDistancia(d));
            return l;
        }
        return null;
    }

    /*Algoritmo de Disjkstra)*/
    public static boolean DM(GrafoMatriz G, Tabla t, int v, int d) {
        if (v != -1 && v != d) {
            if (!t.getMarca(v)) {
                t.marcar(v);
                for (int i = 0; i < G.getCantidadDeVertices(); i++) {
                    if (!t.getMarca(i) && G.acceder(v, i) != null && G.acceder(v, i).getEstado() == true) {
                        if (t.getDistancia(i) == -1) {
                            t.setDistancia(i, t.getDistancia(v) + G.acceder(v, i).getDistancia());
                            t.setOrigen(i, v);
                        } else {
                            if ((t.getDistancia(v) + G.acceder(v, i).getDistancia()) < t.getDistancia(i)) {
                                t.setDistancia(i, t.getDistancia(v) + G.acceder(v, i).getDistancia());
                                t.setOrigen(i, v);
                            }
                        }
                    }
                }
            }
        } else if (v != -1 && v == d) {
            t.marcar(v);
            return true;
        } else if (v == -1) {
            return false;
        }
        return DM(G, t, t.menor(), d);
    }

    /*Restablecer los nodos eliminados por el Algoritmo KSP*/
    public static void reestablecerNodosEnlaces(GrafoMatriz G, int[][] e, int n1, int[] nodos, int n2) {
        for (int i = 0; i < n1; i++) {
            G.acceder(e[i][0], e[i][1]).setEstado(true);
        }
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(nodos[i], j) != null) {
                    G.acceder(nodos[i], j).setEstado(true);
                }
            }
            for (int j = 0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(j, nodos[i]) != null) {
                    G.acceder(j, nodos[i]).setEstado(true);
                }
            }
        }
    }

    /*Ordenar una lista enlazada de n elementos*/
    /**
     * **************************************************************
     *
     *****************Algoritmos de Utilizacion general**************
     *
     ***************************************************************
     */
    /*Algoritmo para ordenar una lista enalzada de n elementos*/
    public static void ordenar(ListaEnlazada[] v, int n) {
        ListaEnlazada aux = new ListaEnlazada();
        for (int i = 0; i <= n - 1; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (v[i].nodo(v[i].getTamanho() - 1).getDato() > v[j].nodo(v[j].getTamanho() - 1).getDato()) {
                    aux = v[i];
                    v[i] = v[j];
                    v[j] = aux;
                }
            }
        }
    }

    public static void ordenar(int[] lista) {
        for (int i = lista.length - 1; i > 0; i--) {
            int aux;
            for (int j = i - 1; j > -1; j--) {
                if (lista[i] < lista[j]) {
                    aux = lista[i];
                    lista[i] = lista[j];
                    lista[j] = aux;
                }
            }
        }
    }

    /*Algoritmo para verificar si dos listas son iguales*/
    public static boolean verificar(ListaEnlazada[] b, int n, ListaEnlazada v) {
        Nodo aux = new Nodo();
        boolean ban;
        for (int i = 0; i <= n; i++) {
            aux = b[i].getInicio();
            if (b[i].getTamanho() == v.getTamanho()) {
                ban = true;
                for (Nodo j = v.getInicio(); j != null; j = j.getSiguiente(), aux = aux.getSiguiente()) {
                    if (j.getDato() != aux.getDato()) {
                        ban = false;
                        break;
                    }
                }
                if (ban) {
                    return false;
                }
            }
        }
        return true;
    }

    /*Verificar si el String 'dato' es un numero*/
    public static boolean verificarNumero(String abc, String dato) {
        boolean ban = false;
        for (int i = 0; i < dato.length(); i++) {
            ban = false;
            for (int j = 0; j < abc.length(); j++) {
                if (dato.charAt(i) == abc.charAt(j)) {
                    ban = true;
                    break;
                }
            }
            if (!ban) {
                break;
            }
        }
        if (dato == "0") {
            ban = false;
        }
        return ban;
    }

    /**
     * ***********************************************************************
     *
     * Algortimos Utilizados para el calculo de las Demandas/Unidad de tiempo
     *
     ************************************************************************
     */
    /*Calcular el valor de lambda en funcion al Earlang y el tiempo*/
    public static double demandasporTiempo(int E, int t) {
        return (double) E / t;
    }

    /*Del resultado del Algoritmo demandasporTiempo(), se calcula la cantidad  
    * segura de demandas en una unidad de tiempo
     */
    public static double[] cantidadDemandas(double l) {

        int entero = (int) l;
        double decimal = (double) (l - entero);
        double[] r = new double[2];
        r[0] = entero;
        r[1] = decimal;
        return r;

    }

    /*Toma la parte decimal optenida del Algoritmo demandasporTiempo()
    * y retorna si se envia una demanda mas pàra una unidad de tiempo
     */
    public static boolean probabilidadUnaDemanda(double d) {

        int n = (int) (d * 100);
        Random nro = new Random();
        int r = 1 + nro.nextInt(99);
        if (r > n) {
            return false;
        }
        return true;

    }

    public static void printUtil() {
        System.out.println("Llego a utilitarios");
    }

    /*
    * Demandas totales que caeran segun la carga de trafico: erlang, y el tiempo
    * de duracion de las demandas: t
     */
    public static int demandasTotalesPorTiempo(int erlang, int t) {

        double demandasPorUnidadTiempo;
        double[] demanda;
        int d;
        demandasPorUnidadTiempo = demandasporTiempo(erlang, t);
        demanda = cantidadDemandas(demandasPorUnidadTiempo);
        d = (int) demanda[0];
        if (Utilitarios.probabilidadUnaDemanda(demanda[1])) {
            d++;
        }
        return d;

    }

    /*Ver si se tienen FS disponibles en un vector de Utilziacion de Espectro
    * Util principalmente para los esquemas de Ruteo
    * Parametros:
    * int [] OE: vector de utilizacion del espectro
    * int capacidad: tamaño de OE
    * int N: los N FS requeridos 
     */
    public static boolean buscarFSdisponibles(int[] OE, int capacidad, int N) {
        int inicio = 0;
        int fin = 0;
        int cont = 0;
        boolean fsEcontrados = false;
        for (int i = 0; i < capacidad; i++) {
            if (OE[i] == 1) {
                inicio = i;
                for (int j = inicio; j < capacidad; j++) {
                    if (OE[j] == 1) {
                        cont++;
                    } else {
                        cont = 0;
                        break;
                    }
                    //si se encontro un bloque valido, salimos de todos los bloques
                    if (cont == N) {
                        fin = j;
                        fsEcontrados = true;
                        break;
                    }
                }
            }
            if (fsEcontrados == true) {
                break;
            }
        }
        return fsEcontrados;
    }

    /*Algoritmo que se encarga de asignar a una demanda los FSs requeridos en la red*/
    public static void asignarFS(ListaEnlazada ksp[], Resultado r, GrafoMatriz G, Demanda d) {
        int util = 0;
        int cont = 0;
        for (Nodo nod = ksp[r.getCamino()].getInicio(); nod.getSiguiente().getSiguiente() != null; nod = nod.getSiguiente()) {
            for (int p = r.getInicio(); cont <= d.getNroFS() && p <= r.getFin(); p++) {
                cont++;
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setTiempo(d.getTiempo());
                util = G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).setUtilizacionFS(p, util);
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setTiempo(d.getTiempo());
                util = G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).setUtilizacionFS(p, util);
            }
        }
    }

    /*
    ****************************************************************************************
    ****************************************************************************************
    ************DESDE AQUI, SON LOS UTILITARIOS  PARA LOS ALGORITMOS DE DEFRAGMENTACION*****
    ****************************************************************************************
    ****************************************************************************************
     */

 /*Algoritmo que se encarga de asignar a una demanda los FSs requeridos en la red donde se realiza defragmentaciones- se utiiza
    en la  clase ventanaPricipal_Defrag*/
    public static void asignarFS_Defrag(ListaEnlazada ksp[], Resultado r, GrafoMatriz G, Demanda d, int conexid) {
        int util = 0;
        int cont = 0;
        for (Nodo nod = ksp[r.getCamino()].getInicio(); nod.getSiguiente().getSiguiente() != null; nod = nod.getSiguiente()) {
            cont = 0;
            for (int p = r.getInicio(); cont <= d.getNroFS() && p <= r.getFin(); p++) {
                cont++;
                if (G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].getEstado() == 0){
                    System.out.println("HAY UN CONFLICTO AL GUARDAR EL ESTADO DEL FS IDA");
                }
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setTiempo(d.getTiempo());
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setConexion(conexid);
                util = G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).setUtilizacionFS(p, util);
                if (G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].getEstado() == 0){
                    System.out.println("HAY UN CONFLICTO AL GUARDAR EL ESTADO DEL FS VUELTA");
                }
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setConexion(-conexid);
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setTiempo(d.getTiempo());
                util = G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).setUtilizacionFS(p, util);
            }
        }
    }

    /*Algoritmo que se encarga de asignar a una demanda los FSs requeridos en la red*/
    public static void asignarFS_saveRoute(ListaEnlazada ksp[], Resultado r, GrafoMatriz G, Demanda d, ArrayList<ListaEnlazadaAsignadas> lea, int indexC) {
        int util = 0;
        int cont = 0;
        ListaEnlazadaAsignadas e = new ListaEnlazadaAsignadas(ksp[r.getCamino()], d);
        if (indexC == -1) {
            lea.add(e);
        } else {
            lea.get(indexC).setListaAsignada((ksp[r.getCamino()]));
            lea.get(indexC).setDemanda(d);
            lea.get(indexC).setEstado(1);
        }
        for (Nodo nod = ksp[r.getCamino()].getInicio(); nod.getSiguiente().getSiguiente() != null; nod = nod.getSiguiente()) {
            //agregado
            cont = 0;
            for (int p = r.getInicio(); cont <= d.getNroFS() && p <= r.getFin(); p++) {
                //cual pio es el plan de cont aca? Nunca le esta asignando a los FS
                cont++;
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setTiempo(d.getTiempo());
                util = G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).setUtilizacionFS(p, util);
                //bidireccional?
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setTiempo(d.getTiempo());
                util = G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getSiguiente().getDato(), nod.getDato()).setUtilizacionFS(p, util);

                //se especifica el camino propietario del FS
                if (indexC == -1) {
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setPropietario(lea.size() - 1);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setPropietario(lea.size() - 1);

                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setConexion(lea.size() - 1);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setConexion((lea.size() - 1) * (-1));
                } else {
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setPropietario(indexC);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setPropietario(indexC);

                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setConexion(indexC);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setConexion(indexC);
                }

            }
        }

    }

    /*Algotimo que se encarga de graficar el resultado final de las problidades de bloqueo con respecto al earlang*/
    public static void GraficarResultado(List result[], JPanel panelResultado, JLabel etiquetaResultado, List<String> lista, int paso) {
        double sum = 0;
        XYSplineRenderer renderer = new XYSplineRenderer();
        XYSeries series[] = new XYSeries[result.length];
        XYSeriesCollection datos = new XYSeriesCollection();
        ValueAxis ejex = new NumberAxis();
        ValueAxis ejey = new NumberAxis();
        XYPlot plot;
        panelResultado.removeAll();
        for (int i = 0; i < result.length; i++) {
            series[i] = new XYSeries((String) lista.get(i));
            for (int j = 0; j < result[i].size(); j++) {
                sum += paso;
                series[i].add(sum, (double) result[i].get(j));
            }
            sum = 1;
            datos.addSeries(series[i]);
        }

        ejex.setLabel("Erlang");
        ejey.setLabel("Probalididad de bloqueo(%)");
        plot = new XYPlot(datos, ejex, ejey, renderer);
        JFreeChart grafica = new JFreeChart(plot);
        //grafica.setTitle("Probabilidad de Bloqueo");
        ChartPanel panel = new ChartPanel(grafica);
        panel.setBounds(2, 2, 466, 268);
        panelResultado.add(panel);
        panelResultado.repaint();
        panelResultado.setVisible(true);
    }

    /*Algotimo que se encarga de graficar el resultado final de las problidades de bloqueo con respecto al earlang*/
    public static void GraficarResultado(List result[], JPanel panelResultado, JLabel etiquetaEntropia, List<String> lista, int paso, String label) {
        double sum = 1;
        XYSplineRenderer renderer = new XYSplineRenderer();
        XYSeries series[] = new XYSeries[result.length];
        XYSeriesCollection datos = new XYSeriesCollection();
        ValueAxis ejex = new NumberAxis();
        ValueAxis ejey = new NumberAxis();
        XYPlot plot;
        panelResultado.removeAll();
        for (int i = 0; i < result.length; i++) {
            series[i] = new XYSeries((String) lista.get(i));
            for (int j = 0; j < result[i].size(); j++) {
                sum += paso;
                series[i].add(sum, (double) result[i].get(j) * 100);
            }
            sum = 1;
            datos.addSeries(series[i]);
        }

        ejex.setLabel("Erlang");
        ejey.setLabel(label);
        plot = new XYPlot(datos, ejex, ejey, renderer);
        JFreeChart grafica = new JFreeChart(plot);
        //grafica.setTitle("Probabilidad de Bloqueo");
        ChartPanel panel = new ChartPanel(grafica);
        panel.setBounds(2, 2, 466, 268);
        panelResultado.add(panel);
        panelResultado.repaint();
        panelResultado.setVisible(true);
    }

    /*El algoritmo que se encarga en cada unidad de tiempo de disminuir el tiempo de permanecia en la red de los
    FSs.*/
    public static void Disminuir(GrafoMatriz G) {
        for (int i = 0; i < G.getCantidadDeVertices(); i++) {
            for (int j = 0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(i, j) != null) {
                    for (int k = 0; k < G.acceder(i, j).getFS().length; k++) {
                        if (G.acceder(i, j).getFS()[k].getEstado() == 0) {
                            G.acceder(i, j).getFS()[k].setTiempo(G.acceder(i, j).getFS()[k].getTiempo() - 1);
                            if (G.acceder(i, j).getFS()[k].getTiempo() == 0) {
                                G.acceder(i, j).getFS()[k].setEstado(1);
                                //System.out.print("FF Murio "+G.acceder(i, j).getFS()[k].getConexion());
                                G.acceder(i, j).getFS()[k].setConexion(-1);
                            }
                        }
                    }
                }
            }
        }
    }

    /*
        Disminuir with route
     */
    public static void DisminuirWithRoute(GrafoMatriz G, ArrayList<ListaEnlazadaAsignadas> lea) {
        int prop = -1;
        for (int i = 0; i < G.getCantidadDeVertices(); i++) {
            for (int j = 0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(i, j) != null) {
                    for (int k = 0; k < G.acceder(i, j).getFS().length; k++) {
                        if (G.acceder(i, j).getFS()[k].getEstado() == 0) {
                            G.acceder(i, j).getFS()[k].setTiempo(G.acceder(i, j).getFS()[k].getTiempo() - 1);
                            if (G.acceder(i, j).getFS()[k].getTiempo() == 0) {
                                //indice del propietario
                                prop = G.acceder(i, j).getFS()[k].getPropietario();
                                if (prop != -1 && lea.get(prop).getEstado() != 0) {
                                    eliminarCamino(prop, lea);
                                    //System.out.print("CBD Murio "+ prop);
                                }
                                G.acceder(i, j).getFS()[k].setEstado(1);
                                G.acceder(i, j).getFS()[k].setPropietario(-1);
                            }
                        }
                    }
                }
            }
        }
    }

    /*Disminuir de MSGD
    FSs.*/
    public static void Disminuir_MSGD(GrafoMatriz G, ArrayList<ListaEnlazadaAsignadas> lea, int capacidadE) {
        ArrayList<ListaEnlazadaAsignadas> caminosEliminados = new ArrayList<ListaEnlazadaAsignadas>();
        int prop, PropFinal = -1;
        int PropTornDown = -1;
        for (int i = 0; i < G.getCantidadDeVertices(); i++) {
            for (int j = 0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(i, j) != null) {
                    for (int k = 0; k < G.acceder(i, j).getFS().length; k++) {
                        if (G.acceder(i, j).getFS()[k].getEstado() == 0) {
                            G.acceder(i, j).getFS()[k].setTiempo(G.acceder(i, j).getFS()[k].getTiempo() - 1);
                            if (G.acceder(i, j).getFS()[k].getTiempo() == 0) {
                                //indice del propietario
                                prop = G.acceder(i, j).getFS()[k].getPropietario();
                                if (prop != -1 && lea.get(prop).getEstado() != 0) {
                                    eliminarCamino(prop, lea);
                                    caminosEliminados.add(lea.get(prop));
                                    //Guarda el indice del camino cambiado. El primero de todos
                                    if (PropTornDown == -1) {
                                        PropTornDown = prop;
                                    }
                                }
                                G.acceder(i, j).getFS()[k].setEstado(1);
                            }
                        }
                    }
                }
            }
        }
        ///ME olvide de eliminar el camino viejo
        if (caminosEliminados.size() > 0) {
            //Random rdm =new Random();
            //int cSelected = rdm.nextInt(caminosEliminados.size())-1;
            int cSelected = 0;
            //se toma el primero // cCandidatos guarda el camino optimo para el camino actualmente suboptimo
            ArrayList<ListaEnlazadaAsignadas> cCandidatos = buscarCandidatos(lea, caminosEliminados.get(cSelected), G);
            if (cCandidatos.size() > 0) {

                // System.out.println("Aca Paso la macana");
                //System.out.println("Camino Libre: "+caminosEliminados.get(cSelected).getDemanda().getOrigen() + " - " 
                // + caminosEliminados.get(cSelected).getDemanda().getDestino() );
                //System.out.println(cCandidatos.get(0).getDemanda().getOrigen()+" - "+cCandidatos.get(0).getDemanda().getDestino());
                ListaEnlazada[] caminoOptimo = new ListaEnlazada[1];
                ListaEnlazadaAsignadas cResult = new ListaEnlazadaAsignadas();
                //se debe retornar el camino a rerutear
                cResult = Algoritmos_Defrag.MSGD(cCandidatos, caminosEliminados.get(cSelected), 10);

                if (cResult != null) {
                    PropFinal = cResult.getEnteroAux();//guarda el indice del propietario del camino
                    cResult.getDemanda().setTiempo(capturarTiempo(G, PropFinal, cResult, capacidadE));
                    //eliminar el camino reruteado
                    //caminoOptimo[0] = cResult.getListaAsignada();
                    caminoOptimo[0] = caminosEliminados.get(cSelected).getListaAsignada();
                    //reallocate con el algoritmo FF
                    Resultado r = Algoritmos_Defrag.Def_MSGD(G, cResult.getDemanda(), caminoOptimo, capacidadE);
                    if (r != null) {
                        Utilitarios.asignarFS_saveRoute(caminoOptimo, r, G, cResult.getDemanda(), lea, PropTornDown);

                        limpiarCaminoAnterior(cResult, G, PropFinal, capacidadE, lea);
                        //VentanaPrincipal.imprimirCamino(caminoOptimo[r.getCamino()]);
                        //System.out.println("COLOCADO...");
                    }

                }
            }
        }

    }

    /*
        capturarTiempo() - retorna el tiempo actual del camino que sera reruteado
    @Param  
        G - Topologia de la red
        prop - indice del propietario del camino a ser reruteado
        caminoViejo - el camino anterior del camino reruteado
        capacidadE - capacidad del frecuency slot
     */
    public static int capturarTiempo(GrafoMatriz G, int prop, ListaEnlazadaAsignadas caminoViejo, int capacidadE) {
        int tiempo = -1;
        if (prop != -1) {
            ListaEnlazada camino = caminoViejo.getListaAsignada();
            for (Nodo n = camino.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                int nActual = n.getDato();
                int nSgte = n.getSiguiente().getDato();
                for (int i = 0; i < capacidadE; i++) {
                    if (G.acceder(nActual, nSgte).getFS()[i].getPropietario() == prop) {
                        tiempo = G.acceder(nActual, nSgte).getFS()[i].getTiempo();
                        break;
                    }
                }
                if (tiempo != -1) {
                    break;
                }
            }
        }

        return tiempo;
    }

    /*
       limpiarCaminoAnterior() - Elimina los enlaces y FS del camino reruteado
    @Param
        caminoViejo - ListaEnlazadaAsignadas del camino a eliminar
        G - topologia de la red
        prop - indice del propietario del  camino reruteado
        capacidadE - la capacidad del Espectro
        lea - totalidad de conexiones
     */
    public static void limpiarCaminoAnterior(ListaEnlazadaAsignadas caminoViejo, GrafoMatriz G, int prop, int capacidadE, ArrayList<ListaEnlazadaAsignadas> lea) {
        //no existe propietario
        if (prop != -1) {

            ListaEnlazada camino = caminoViejo.getListaAsignada();
            for (Nodo n = camino.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                int nActual = n.getDato();
                int nSgte = n.getSiguiente().getDato();
                int[] fs;
                int contador = 0;
                for (int i = 0; i < capacidadE; i++) {
                    if (G.acceder(nActual, nSgte).getFS()[i].getPropietario() == prop) {
                        //set estado libre = 1
                        G.acceder(nActual, nSgte).getFS()[i].setEstado(1);
                        G.acceder(nActual, nSgte).getFS()[i].setTiempo(0);
                        G.acceder(nActual, nSgte).getFS()[i].setPropietario(-1);
                        //System.out.println("Contador dice: "+ contador++ + " veces.......................");
                        G.acceder(nSgte, nActual).getFS()[i].setEstado(1);
                        G.acceder(nSgte, nActual).getFS()[i].setTiempo(0);
                        G.acceder(nSgte, nActual).getFS()[i].setPropietario(-1);
                    }
                }
            }

            lea.get(prop).setEstado(0);
        }

    }

    /*
        buscarCandidato() - busca todos los caminos que pueden ser optimos usando el camino que se libero
        @Param 
            lea - todos los caminos vivos
            cLibre - el camino a ser utilizado
            G - topologia de la red
        @return
            ArrayList<ListaEnlazadaAsignadas> list - lista de caminos candidatos
     */
    public static ArrayList<ListaEnlazadaAsignadas> buscarCandidatos(ArrayList<ListaEnlazadaAsignadas> lea, ListaEnlazadaAsignadas cLibre, GrafoMatriz G) {

        ArrayList<ListaEnlazadaAsignadas> cCandidatos = new ArrayList<ListaEnlazadaAsignadas>();

        //se recorren todos los caminos vivos
        for (int i = 0; i < lea.size(); i++) {
            //si esta vivo
            if (lea.get(i).getEstado() == 1) {
                //calcular ksp de cada camino
                ListaEnlazada[] ksp = Utilitarios.KSP(G, lea.get(i).getDemanda().getOrigen(), lea.get(i).getDemanda().getDestino(), 2);

                boolean existe = false;
                for (int j = 0; j < ksp.length; j++) {
                    int cont = 0;
                    Nodo nLibre = cLibre.getListaAsignada().getInicio();
                    boolean cortar = false;
                    //verificar si el camino libre existe en uno de los ksp
                    //cortar - booleano que se encarga de terminar el ciclo
                    for (Nodo nCand = ksp[j].getInicio(); nCand.getSiguiente() != null && !cortar; nCand = nCand.getSiguiente()) {
                        if (nCand.getDato() == nLibre.getDato()) {
                            cont++;
                            if (nLibre.getSiguiente() != null) {
                                nLibre = nLibre.getSiguiente();
                            }
                        } else if (cont != 0) {
                            cortar = true;
                            ///cont=0;
                        }
                    }
                    //si el camino libre esta en el ksp // retorna mas 1 por algo
                    if (cont == ksp[j].getTamanho() - 1 && cLibre.getListaAsignada().getTamanho() == ksp[j].getTamanho()) {
                        //ListaEnlazadaAsignadas cSelected = new ListaEnlazadaAsignadas(ksp[j], lea.get(i).getDemanda());
                        //cCandidatos.add(cSelected);
                        cCandidatos.add(lea.get(i));
                        //se guarda la posicion en lea del reruteado
                        cCandidatos.get(cCandidatos.size() - 1).setEnteroAux(i);
                        break;
                    }
                }
            }
        }
        return cCandidatos;
    }

    /*
        Elimina el camino del grafo
        @Param:
            int prop . El inidice del camino propietario
            ListaEnlazadaAsignadas lea . El array de caminos
           
     */
    public static void eliminarCamino(int prop, ArrayList<ListaEnlazadaAsignadas> lea) {
        lea.get(prop).setEstado(0);
    }

    /*
        calcularSaltos() - calcula todos los saltos en un camino dado
        @Param
            camino - camino a calcular saltos
        @return
            int con nro de saltos
     */
    public static int calcularSaltos(ListaEnlazada camino) {
        int saltos = 0;
        for (Nodo n = camino.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
            saltos++;
        }
        return saltos;
    }

    /*Para FARSA*/
 /*
    contarCuts - Cuenta la cantidad de cortes en un camino
    @Param
        ksp - todos los caminos candidatos
        G - la topologia
        capacidad - tamanho del enlace
    @return
        k - indice del camino con menos cortes
     */
    public static int contarCuts(ArrayList<ListaEnlazada> ksp, GrafoMatriz G, int capacidad) {

        int[] cutsSlot = new int[2];
        ArrayList<Integer> cutsFinales = new ArrayList<Integer>();
        ArrayList<Integer> caminosFinales = new ArrayList<Integer>();
        ArrayList<Integer> fSlots = new ArrayList<Integer>();
        int caminoFinal = 0;
//        int cutAux = 0;
        //int slots=0;
//        int m = 1; //controla que los cortes no esten de seguido
//        boolean filaLibre = false;
//        ArrayList<Integer> indices = new ArrayList<Integer>();

        ArrayList<Integer> indicesL = new ArrayList<Integer>();

        for (int k = 0; k < ksp.size(); k++) {

            cutsSlot = nroCuts(ksp.get(k), G, capacidad); //TIENE QUE TRAER UN ARRAYLIST CON TODOS LOS CORTES MENORES DE ESTE KSP, PUEDE HABER MAS DE UNO

            if (cutsSlot != null) { //POR CADA CORTE MENOR ENCONTRADO EN EL KSP

                //atender
                if (cutsFinales.size() < 1) {
                    cutsFinales.add(cutsSlot[0]);
                    caminosFinales.add(k);
                    fSlots.add(cutsSlot[1]);
                } else if (cutsFinales.get(cutsFinales.size() - 1) > cutsSlot[0]) { //SI YA HABÍAN DOS MENORS IGUALES, ESTE REEMPLAZA SOLO EL ÚLTIMO
                    cutsFinales.set(cutsFinales.size() - 1, cutsSlot[1]);
                    caminosFinales.set(caminosFinales.size() - 1, k);
                    fSlots.set(fSlots.size() - 1, cutsSlot[1]);
                } else if (cutsFinales.get(cutsFinales.size() - 1) == cutsSlot[0]) {
                    cutsFinales.add(cutsSlot[1]);
                    caminosFinales.add(k);
                    fSlots.add(cutsSlot[1]);
                }
            }

        }

        if (cutsFinales.size() > 1) {
            caminoFinal = calcularAlineacion(ksp, G, capacidad, caminosFinales, fSlots); //SI HAY DOS CON LA MISMA CANT DE ALINEACIONES, NO TOMA EN CUENTA PARA HACER MIN KSP Y FIRSFIT
        } else if (cutsFinales.size() == 1) {
            caminoFinal = caminosFinales.get(0);
        } else {
            //printLista(caminosFinales);
            //System.out.println("VAcio he'i");
            //System.out.println("camino"+ksp.get(0).getInicio().getDato()+ " - ");
        }

        return caminoFinal;
    }

    /*
     Guarda los indices de los FS donde existen cortes
     @param
        ksp - todos los caminos candidatos
        G - la topologia
        capacidad - tamanho del enlace
     @return
        ArrayList con los indices de los cortes
     */
    public static ArrayList<Integer> buscarIndices(ListaEnlazada ksp, GrafoMatriz G, int capacidad) {

        ArrayList<Integer> indices = new ArrayList<Integer>();

        ArrayList<Integer> indicesL = new ArrayList<Integer>();
        boolean filaLibre = false;

        for (int i = 0; i < capacidad; i++) {

            for (Nodo n = ksp.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                // 1 = libre 0 = ocupado
                if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 1) {
                    filaLibre = true;
                } else {
                    filaLibre = false;
                    break;
                }
            }
            if (filaLibre) {
                //if (indices.size()<1){
                indices.add(i);
                filaLibre = false;
                //}
            }
        }

        //limpiar indices
        for (int i = indices.size() - 1; i > 0; i--) {
            if ((indices.get(i) - indices.get(i - 1)) != 1) {
                indicesL.add(indices.get(i));
            }
        }
        indicesL.add(indices.get(0));

        return indicesL;

    }

    /*
    * Cuenta el nro de cortes de un camino
    * @param
        ksp - todos los caminos candidatos
        G - la topologia
        capacidad - tamanho del enlace
    * @return
        vector con el nro de cortes, y el indice del slot en el FS para ubicar
     */
    public static int[] nroCuts(ListaEnlazada ksp, GrafoMatriz G, int capacidad) {

        int cuts = 999;
        int slots = -1;

        int[] cortesSlots = new int[2];
//        ArrayList<Integer> cutsFinales = new ArrayList<Integer>();
//        ArrayList<Integer> caminosFinales = new ArrayList<Integer>();
//        ArrayList<Integer> fSlots = new ArrayList<Integer>();
//        int caminoFinal = 0;
        int cutAux = 0;

//        ArrayList<Integer> indices = new ArrayList<Integer>();

        ArrayList<Integer> indicesL;

        indicesL = buscarIndices(ksp, G, capacidad);

        if (indicesL.size() == 1 && indicesL.get(0) == 0) {
            cuts = 0;
            slots = 0;
        } else {
            for (int i = 0; i < indicesL.size(); i++) {
                for (Nodo n = ksp.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {

                    if (indicesL.get(i) != 0 && indicesL.get(i) < capacidad - 1) {
                        // 1=libre 0=Ocupado
                        if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[indicesL.get(i) - 1].getEstado() == 1
                                && G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[indicesL.get(i) + 1].getEstado() == 1) {
                            cutAux = cutAux + 1;

                        }
                    }

                }
                if (cutAux < cuts) {
                    cuts = cutAux;
                    slots = i;
                }
                cutAux = 0;
            }
        }

        if (cuts != 999 && slots != -1) {
            cortesSlots[0] = cuts;
            cortesSlots[1] = slots;

            return cortesSlots;
        }

        return null;

    }

    public static void printLista(ArrayList<Integer> camino) {
        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i) + " - ");
        }
    }


    /*
    Si es que existen dos caminos con los mismos cortes, se calcula la alineacion
    @Param
        ksp - caminos candidatos
        G - topologia
        capacidad - cantidad de slots en enlace
        caminosFinales - indices de los caminos candidatos finales
        indicesL - indice de los cortes en los FS
        fSlots - indices de los FS a utilizar
    @return
        indice del camino mas corto y menor misaligment
     */
    public static int calcularAlineacion(ArrayList<ListaEnlazada> ksp, GrafoMatriz G, int capacidad, ArrayList<Integer> caminos, ArrayList<Integer> fSlots) {

//        Integer[] alineacion = new Integer[indicesL.size()];
        int alineacionAux = 0;
        int alineacionFinal = 999;
        int indiceFinal = -1;
//        ArrayList<Integer> desalineado = new ArrayList<Integer>();

        for (int k = 0; k < caminos.size(); k++) {

            alineacionAux = contarDesalineamiento(ksp.get(caminos.get(k)), G, capacidad, fSlots.get(k));

            if (alineacionAux < alineacionFinal) {
                alineacionFinal = alineacionAux;
                indiceFinal = caminos.get(k);
            }
        }

        return indiceFinal;
    }

    /*
     Cuenta el desalineamiento
     @param
        ksp - caminos candidatos
        G - topologia
        capacidad - cantidad de slots en enlace
        caminosFinales - indices de los caminos candidatos finales
        indicesL - indice de los cortes en los FS
        fSlots - indices de los FS a utilizar
     @return
        int con el nro de desalineamiento
     */
    public static int contarDesalineamiento(ListaEnlazada ksp, GrafoMatriz G, int capacidad, int fSlots) {

        int alineacionAux = 0;
        int nActual, nSgte, nAnterior = -1;
        int nSgteSgte = -1;
        for (Nodo n = ksp.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
            for (int i = 0; i < G.getCantidadDeVertices(); i++) {
                nActual = n.getDato();
                nSgte = n.getSiguiente().getDato();

                try {
                    if (n.getSiguiente().getSiguiente() != null) {
                        nSgteSgte = n.getSiguiente().getSiguiente().getDato();
                    }
                } catch (Exception e) {
                    //exploto
                    System.out.println("No le salio sgte de sgte");
                    e.printStackTrace();
                }

                if (G.acceder(i, nActual) != null && i != nSgte && i != nActual && i != nAnterior) {
                    if (G.acceder(i, nActual).getFS()[fSlots].getEstado() == 1) {
                        alineacionAux = alineacionAux + 1;
                    } else {
                        alineacionAux = alineacionAux - 1;
                    }

                }

                if (G.acceder(nSgte, i) != null && i != nActual && i != nSgte && i != nAnterior && i != nSgteSgte) {
                    if (G.acceder(nSgte, i).getFS()[fSlots].getEstado() == 1) {
                        alineacionAux = alineacionAux + 1;
                    } else {
                        alineacionAux = alineacionAux - 1;
                    }

                }
            }
            nAnterior = n.getDato();

        }

        return alineacionAux;

    }

    /*
     Busca el menor elemento
     @param
        lista - ArrayList de elementos
     @return 
        int con el menor elemento
     */
    public static int buscarMenor(ArrayList<Integer> lista) {
        int menor = 999;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) < menor) {
                menor = lista.get(i);
            }
        }
        return menor;
    }

    /*
     Comprueba si un elemento ya esta en la lista
    @param
        lista - lista a revisar
        elem - elemnto  a comprobar
    @return
        true si esta, false otherwise
     */
    public static boolean isInList(ArrayList<Integer> lista, int elem) {
        boolean repetido = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(elem)) {
                repetido = true;
            }
        }
        return repetido;
    }

    public static boolean isInList(int[] lista, int elem) {
        boolean repetido = false;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i] == elem) {
                repetido = true;
            }
        }
        return repetido;
    }

    /*
     Cuenta la capacidad residual de un camino (los FS libres)
     @param
        ksp - camino seleccionado
        G - topologia
        capacidad - tamanho del enlace en FS
     @return
        int con el nro de FS libres
     */
    public static int contarCapacidadLibre(ListaEnlazada ksp, GrafoMatriz G, int capacidad) {

        int libres = 0;
        for (Nodo n = ksp.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
            for (int i = 0; i < capacidad; i++) {
                // 1 = libre 0 = Ocupado
                if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 1) {
                    libres++;
                }
            }

        }
        return libres;
    }

    /*
     Cuenta los enlaces vecinos de cada nodo en el camino
     @param
        ksp - camino seleccionado
        G - topologia
        capacidad - tamanho del enlace en FS
     @return
        int con el nro de vecinos
     */
    public static int contarVecinos(ListaEnlazada ksp, GrafoMatriz G, int capacidad) {

        int vecinos = 0;
        int nAnterior = -1;
        int nSgte = -1;
        for (Nodo n = ksp.getInicio(); n.getSiguiente() != null; n = n.getSiguiente()) {
            if (n.getSiguiente() != null) {
                nSgte = n.getSiguiente().getDato();
            } else {
                nSgte = -1;
            }
            for (int i = 0; i < G.getCantidadDeVertices(); i++) {
                if (G.acceder(n.getDato(), i) != null && i != nAnterior && i != nSgte) {
                    vecinos++;
                }
            }
            nAnterior = n.getDato();
            //se le resta el link que es parte del camino
            // vecinos = vecinos - 1;
        }

        return vecinos;
    }

    /*
     busca la demanda con el mayor tiempo de vida, que no sea el ultimo
    @param
        lea - lista de conexiones establecidas
        r - ultima demanda cargada
        G - topologia de la red
    @return
        indice de la conexion con el mayor lifetime
     */
    public static int buscarDemandaLifetime(ArrayList<ListaEnlazadaAsignadas> lea, Demanda dR, GrafoMatriz G) {

        //Demanda d = new Demanda();
        int mayorLife = 0;
        int prop = -1;
        int inicio = -1;
        int fin = -1;
        int tamanho = 0;

        for (int i = 0; i < G.getCantidadDeVertices(); i++) {
            for (int j = 0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(i, j) != null) {
                    for (int k = 0; k < G.acceder(i, j).getFS().length; k++) {
                        if (G.acceder(i, j).getFS()[k].getEstado() == 0) {
                            if (G.acceder(i, j).getFS()[k].getTiempo() > mayorLife && G.acceder(i, j).getFS()[k].getTiempo() != dR.getTiempo()) {
                                mayorLife = G.acceder(i, j).getFS()[k].getTiempo();
                                //indice del propietario
                                prop = G.acceder(i, j).getFS()[k].getPropietario();
                                /*inicio = k;
                                fin = k +  lea.get(prop).getDemanda().getNroFS() - 1;
                                tamanho = fin - inicio + 1;*/
                            }
                        }
                    }
                }
            }
        }
        if (prop != -1) {
            lea.get(prop).getDemanda().setTiempo(mayorLife);
            return prop;
            //Demanda d = new Demanda(inicio, fin, tamanho, mayorLife);
        }
        return -1;

    }

    /*
     busca si existe un lugar en la red para ubicar la demanda
    @param
        ksp - vector de caminos posibles
        d - demanda actual
        G - topologia red
        capacidad - 
    @return
        r - Resultado con las posiciones libres
     */
    public static Resultado buscarEspacio(ListaEnlazada[] ksp, Demanda d, GrafoMatriz G, int capacidad) {

        int k = 0;
        int demandaColocada = 0;
        int[] OE = new int[capacidad];
        int inicio = 0, fin = 0, cont;

        while (k < ksp.length && ksp[k] != null && demandaColocada == 0) {
            //busca lugar en la mitad para abajo
            for (int i = 0; i < capacidad; i++) {
                //1 = libre 0 = Ocupado
                if (i < capacidad / 2) {
                    OE[i] = 1;
                } else {
                    OE[i] = 0;
                }
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
             */
            inicio = fin = cont = 0;
            for (int i = 0; i < capacidad; i++) {
                if (OE[i] == 1) {
                    inicio = i;
                    for (int j = inicio; j < capacidad; j++) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if (cont == d.getNroFS()) {
                            fin = j;
                            demandaColocada = 1;
                            break;
                        }
                    }
                }
                if (demandaColocada == 1) {
                    break;
                }
            }
            k++;
        }

        if (demandaColocada == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        r.setCamino(k - 1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }

    /*
     Busca todos los caminos que necesitan moverse para asignar la demanda
    
     */
    public static ArrayList<Integer> obtenerCaminosMovidos(ListaEnlazada ksp, GrafoMatriz G, Demanda d) {

        ArrayList<Integer> caminosMovidos = new ArrayList<Integer>();
        int prop = -1;

        //se guarda todos los caminos que se interponen entre la demanda
        //los slots seran 0 a capacidad
        for (int i = 0; i < d.getNroFS(); i++) {
            for (Nodo n = ksp.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                //1 = libre 0 = Ocupado
                if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                    prop = G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getPropietario();
                    if (!isInList(caminosMovidos, prop)) {
                        caminosMovidos.add(prop);
                    }
                }
            }

        }
        return caminosMovidos;
    }

    /*
     Comprueba si existen caminos alternativos a los caminos movidos
    @param
        caminosMovidos - indice de todos los caminos que se buscar reasignar
        lea - lista de todos los caminos asignados
        G - la red actual
        capacidad - cantidad de FS por enlace
    @return
        true si existen caminos alternativos, false otherwise
     */
    public static Resultado[] buscarCaminosAlternativos(ArrayList<Integer> caminosMovidos, ArrayList<ListaEnlazadaAsignadas> lea,
            GrafoMatriz G, GrafoMatriz Gaux, int capacidad) {

        Resultado[] results = new Resultado[caminosMovidos.size()];
        ListaEnlazada[] ksp;
        Demanda d;

        int saltos = 0;
        int asignado = 0;

        for (int i = 0; i < caminosMovidos.size(); i++) {
            d = lea.get(caminosMovidos.get(i)).getDemanda();
            saltos = calcularSaltos(lea.get(caminosMovidos.get(i)).getListaAsignada());
            ksp = KSP(G, d.getOrigen(), d.getDestino(), 5);

            Resultado r = Algoritmos_Defrag.KSP_FF_Algorithm_MBBR(G, Gaux, d, ksp, capacidad);
            if (r != null) {
                if (calcularSaltos(ksp[r.getCamino()]) <= saltos) {
                    asignarFS_Defrag(ksp, r, Gaux, d, -1);
                    asignado++;
                    results[i] = r;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        if (asignado == caminosMovidos.size()) {
            return results;
        }

        return null;
    }

    /*
     Algoritmo de seleccion de caminos en una red para posterior reruteo
     antes ordena descendentemente el indice de su FS mas grande de cada conexion
    @param
        G - informacion de la red actual
        lea - lista de todos las conexiones establecidas
        R - porcentaje de la red que seran seleccionados
        capacidadE - cantidad de FS por enlace
    @return
        lista de caminos seleccionados
     */
    public static int[][] HUSIF(GrafoMatriz G, ArrayList<ListaEnlazadaAsignadas> lea, double R, int capacidadE) {
        ArrayList<ListaEnlazadaAsignadas> preLista = new ArrayList<>();
        int cantidadConex = 0;
        for (int i = 0; i < lea.size(); i++) {
            if (lea.get(i).getEstado() == 1) {
                ListaEnlazada auxLista = lea.get(i).getListaAsignada();
                for (Nodo n = auxLista.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    for (int j = 0; j < capacidadE; j++) {
                        if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].getPropietario() == i) {
                            int valor = j + lea.get(i).getDemanda().getNroFS() - 1;
                            lea.get(i).setEnteroAux(valor);
                            cantidadConex++;
                            break;
                        }
                    }
                    break;
                }
            }
        }

        if (cantidadConex != 0) {
            int c = 0;
            int[][] listaHusif = new int[cantidadConex][2];
            for (int i = 0; i < lea.size(); i++) {
                if (lea.get(i).getEstado() == 1 && lea.get(i).getEnteroAux() > 0) {
                    if (c < cantidadConex) {
                        //se guarda el indice del FS
                        listaHusif[c][0] = lea.get(i).getEnteroAux();
                        //se guarda el propietario
                        listaHusif[c][1] = i;
                        c++;
                    }
                }
            }

            ordenarMatrizDescendente(listaHusif);
            Number limiteDouble = new Double(cantidadConex * R);

            Integer limite = limiteDouble.intValue();

            int[][] selectos = new int[limite][2];

            for (int i = 0; i < limite; i++) {
                selectos[i][1] = listaHusif[i][1];
                selectos[i][0] = listaHusif[i][0];
            }
            return selectos;
        }
        return null;
    }

    /*
     Ordena una matriz de forma descendente
    @param
        lista - lista a ser ordenada
     */
    public static void ordenarMatrizDescendente(int[][] lista) {
        for (int i = lista.length - 1; i > 0; i--) {
            int aux1, aux2;
            for (int j = i - 1; j > -1; j--) {
                if (lista[i][0] > lista[j][0]) {
                    aux1 = lista[i][0];
                    aux2 = lista[i][1];
                    lista[i][0] = lista[j][0];
                    lista[i][1] = lista[j][1];
                    lista[j][0] = aux1;
                    lista[j][1] = aux2;
                }
            }
        }
    }

    /*
     Carga todo el contenido del grafo origen al grafo Destino
    @param
        Gorigen - Grafo origen
        Gdestino - Grafo destino
        capacidad - cantidad de FS por enlace
     */
    public static void cargarEnGrafo(GrafoMatriz Gorigen, GrafoMatriz Gdestino, int capacidadE) {
        int tiempo;
        int propietario;
        for (int i = 0; i < Gorigen.getCantidadDeVertices(); i++) {
            for (int j = 0; j < Gorigen.getCantidadDeVertices(); j++) {
                for (int k = 0; k < capacidadE; k++) {
                    //1 = libre 0 = Ocupado
                    if (Gorigen.acceder(i, j) != null && Gorigen.acceder(i, j).getFS()[k].getEstado() == 0) {

                        tiempo = Gorigen.acceder(i, j).getFS()[k].getTiempo();
                        propietario = Gorigen.acceder(i, j).getFS()[k].getPropietario();

                        Gdestino.acceder(i, j).getFS()[k].setEstado(0);
                        Gdestino.acceder(i, j).getFS()[k].setTiempo(tiempo);
                        Gdestino.acceder(i, j).getFS()[k].setPropietario(propietario);

                        Gdestino.acceder(j, i).getFS()[k].setEstado(0);
                        Gdestino.acceder(j, i).getFS()[k].setTiempo(tiempo);
                        Gdestino.acceder(j, i).getFS()[k].setPropietario(propietario);

                    }
                }

            }
        }
    }

    /*
     Borra de la red los caminos seleccionados
    @param
        Gaux - estado de la red
        selectos - indices de caminos a ser borrados
        lea - lista de caminos
     */
    public static void borrarCaminos(GrafoMatriz Gaux, int selectos, ArrayList<ListaEnlazadaAsignadas> lea, int capacidadE) {

        for (Nodo n = lea.get(selectos).getListaAsignada().getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
            for (int j = 0; j < capacidadE; j++) {
                //1 = libre 0 = Ocupado
                if (Gaux.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].getPropietario() == selectos) {

                    //cargar tiempo actual
                    lea.get(selectos).getDemanda().setTiempo(Gaux.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].getTiempo());
                    Gaux.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].setEstado(1);
                    Gaux.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].setPropietario(-1);
                    Gaux.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].setTiempo(0);

                    Gaux.acceder(n.getSiguiente().getDato(), n.getDato()).getFS()[j].setEstado(1);
                    Gaux.acceder(n.getSiguiente().getDato(), n.getDato()).getFS()[j].setPropietario(-1);
                    Gaux.acceder(n.getSiguiente().getDato(), n.getDato()).getFS()[j].setTiempo(0);
                }
            }
        }

    }

    /*
     Busca si existe lugar en la red para los caminos
    @param
        ksp - lista de caminos mas cortos posibles
        d - demanda
        mayorIndice - indice del FS en el enlace en el que esta la conexion
        Gaux - topologia auxiliar
        capacidad - cantidad de FS por enlace
    @return
        resultado - un camino posible con el menor indice, o nulo
     */
    public static Resultado buscarEspacioCBD(ListaEnlazada[] ksp, Demanda d, int mayorIndice, GrafoMatriz Gaux, int capacidad) {

        int k = 0;
        int demandaColocada = 0;
        int[] OE = new int[capacidad];
        int inicio = 0, fin = 0, cont;

        int inicioF = 0, finF = 0, caminoF = -1;

        while (k < ksp.length && ksp[k] != null) {
            //busca lugar en la mitad para abajo
            for (int i = 0; i < capacidad; i++) {
                //1 = libre 0 = Ocupado
                OE[i] = 1;
            }

            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (Gaux.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
             */
            inicio = fin = cont = 0;
            for (int i = 0; i < capacidad; i++) {
                if (OE[i] == 1) {
                    inicio = i;
                    for (int j = inicio; j < capacidad; j++) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if (cont == d.getNroFS()) {
                            fin = j;
                            demandaColocada = 1;
                            break;
                        }
                    }
                }
                if (demandaColocada == 1) {
                    if (fin < mayorIndice) {
                        mayorIndice = fin;
                        finF = fin;
                        inicioF = inicio;
                        caminoF = k;
                    }
                    demandaColocada = 0;
                    break;
                }
            }
            k++;
        }

        if (caminoF == -1) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        r.setCamino(caminoF);
        r.setFin(finF);
        r.setInicio(inicioF);
        return r;

    }

    public static void imprimirTopologia(GrafoMatriz G, int capacidad) {

        for (int i = 0; i < G.getCantidadDeVertices(); i++) {
            for (int j = -0; j < G.getCantidadDeVertices(); j++) {
                if (G.acceder(i, j) != null) {
                    System.out.print(" - " + i + " a " + j + " :  ");
                    for (int k = 0; k < capacidad; k++) {
                        //1 = libre 0 = Ocupado
                        if (G.acceder(i, j).getFS()[k].getEstado() == 0) {
                            /*if (G.acceder(i, j).getFS()[k].getPropietario()==-1)
                                System.out.print(G.acceder(i, j).getFS()[k].getConexion() + " ");
                            else
                                System.out.print(G.acceder(i, j).getFS()[k].getPropietario() + " ");*/
                            System.out.print(G.acceder(i, j).getFS()[k].getConexion() + " ");
                        } else {
                            System.out.print(" # ");
                        }
                    }
                    System.out.println();
                }
            }
        }

    }

    public static void buscarCercano(Number[] lista, int E, int paso) {

        int[] vectorPasos = new int[E / paso];
        boolean encontrado = false;
        int buscarArriba, buscarAbajo;

        for (int i = 0; i < vectorPasos.length; i++) {
            vectorPasos[i] = i * paso;
        }

        for (int i = 0; i < lista.length; i++) {
            if (!isInList(vectorPasos, lista[i].intValue())) {
                buscarArriba = lista[i].intValue() + 1;
                buscarAbajo = lista[i].intValue() - 1;
                while (!encontrado) {

                    if (isInList(vectorPasos, buscarArriba)) {
                        lista[i] = buscarArriba;
                        encontrado = true;
                        break;
                    }

                    if (isInList(vectorPasos, buscarAbajo) && !encontrado) {
                        lista[i] = buscarAbajo;
                        encontrado = true;
                        break;
                    }
                    buscarArriba++;
                    buscarAbajo--;
                }
                encontrado = false;
            }
        }
    }

    /*
    *Genera un alista de n numeros enteros aleatrios todos diferentes.
    * Parametros: int n: Cantidad de numeros a generar.
    * Retorna: int [] : lista de numeros generados
    * Rango de numeros aleatorios [ 0 , n ).
     */
    public static int[] listaDeNumeros(int n) {

        /*Definicion de variables.*/
        Random r = new Random(); //Objeto a generar los numeros aleatorios
        int nro; // variable que almacenara cada numero aleatorio a generar.
        int[] lista = new int[n]; // lista que almacenara los n numeros aleatorios.
        int flag1 = 1; // Variable bandera que identifica si se encontro un numero aleatorio diferente
        // a todos los anteriormente generados.

        /*Comprobamos si cada numero gnerado es diferente a todos los demas numeros ya generados.*/
        int i = 0;
        while (i < n) {
            nro = r.nextInt(n);// Generamos un numero
            flag1 = 1;
            for (int j = 0; j < i; j++) {
                if (lista[j] == nro) { // si es igual a un anterior, lo desechamos.
                    flag1 = 0;
                    break;
                }
            }
            if (flag1 == 1) { // si no se cambio de valor la badera, es un nuemro valido y lo almacenamos.
                lista[i] = nro;
                i++;
            }
        }
        return lista;
    }

    /*Algotimo que se encarga de graficar la entropia con respecto al earlang*/
    public static void GraficarResultadoEntropia(List result[], JPanel PanelEntropia, JLabel etiquetaResultado, List<String> lista, int paso, String label) {
        double sum = 1;
        XYSplineRenderer renderer = new XYSplineRenderer();
        XYSeries series[] = new XYSeries[result.length];
        XYSeriesCollection datos = new XYSeriesCollection();
        ValueAxis ejex = new NumberAxis();
        ValueAxis ejey = new NumberAxis();
        XYPlot plot;
        PanelEntropia.removeAll();
        for (int i = 0; i < result.length; i++) {
            series[i] = new XYSeries((String) lista.get(i));
            for (int j = 0; j < result[i].size(); j++) {
                sum += paso;
                series[i].add(sum, ((double) result[i].get(j)));
            }
            sum = 1;
            datos.addSeries(series[i]);
        }

        ejex.setLabel("Erlang");
        ejey.setLabel(label);
        plot = new XYPlot(datos, ejex, ejey, renderer);
        JFreeChart grafica = new JFreeChart(plot);
        //grafica.setTitle("Probabilidad de Bloqueo");
        ChartPanel panel = new ChartPanel(grafica);
        panel.setBounds(2, 2, 466, 268);
        PanelEntropia.add(panel);
        PanelEntropia.repaint();
        PanelEntropia.setVisible(true);
    }

    //ALGORITMOS DEFRAGMENTACIÓN PROACTIVA
    /*Metodo que encuentra el minimo valor dentro de un ArrayList y retorna su posicion*/
    public static ArrayList encontrarMinimo(ArrayList<Integer> list) {
        int i, min;
        ArrayList pos = new ArrayList(); //ArrayList que guarda la posicion de el/los minimo/s
        min = list.get(0); //se toma como minimo el primero
        pos.add(0); //se guarda la posicion del minimo
        for (i = 1; i <= list.size(); i++) {
            if (list.get(i) < min) {
                min = list.get(i);
                pos.clear(); //se borra la posicion del anterior minimo
                pos.add(i); //se agrega la posicion del nuevo minimo
            } else if (list.get(i) == min) {
                pos.add(i);
            }
        }
        return pos;
    }

    /*Metodo que retorna la ruta mas corta, recibe la lista de ksp y la posicion de los que resultaron minimos*/
    public static Integer encontrarRutaMasCorta(ListaEnlazada[] ksp, ArrayList<Integer> posicion) {
        ArrayList<ListaEnlazada> minimos = new ArrayList<ListaEnlazada>();
        ListaEnlazada min = new ListaEnlazada();
        int i, j, pos;
        for (i = 0; i < ksp.length; i++) {
            if (posicion.contains(i)) {
                minimos.add(ksp[i]);
            }
        }
        min = minimos.get(0);
        pos = 0;
        for (j = 1; j < minimos.size(); j++) {
            if (minimos.get(j).getTamanho() < min.getTamanho()) {
                min = minimos.get(j);
                pos = j;
            }
        }
        return pos;
    }

    /*Metodo que genera el archivo de demandas
    Parametros:
    lambda: valor para la distribucion de Poisson
    t: Tiempo de simulacion
    minFS y maxFS: Rango de variacion de cantidad de FS por demanda    */
    public static File generarArchivoDemandas(int lambda, int t, int minFS, int maxFS, int cantNodos, int HT, int erlang) throws IOException {
        int i, cantidadDemandas, j, origen, destino, fs, tVida;
        File carpeta = new File(System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Requerimientos\\");
        String ruta = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Requerimientos\\req_"+erlang+"Erlang_"+ lambda + "k_" + t + "t_" + minFS + "-" + maxFS + "FS.txt";
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(ruta);
        if (archivo.exists()) {
            return archivo;
        } else {
            Random rand = new Random();
            for (i = 1; i < t; i++) {
                cantidadDemandas = poisson(lambda);
                for (j = 0; j < cantidadDemandas; j++) {
                    rand = new Random();
                    origen = rand.nextInt(cantNodos);
                    destino = rand.nextInt(cantNodos);
                    fs = (int) (Math.random() * (maxFS-minFS+1)) + minFS;
                    while (origen == destino) {
                        destino = rand.nextInt(cantNodos);
                    }
                    tVida = obtenerTiempoDeVida(HT);
                    archivo = escribirArchivo(origen, destino, fs, lambda, i, archivo, tVida, cantidadDemandas);
                }
            }
            return archivo;
        }
    }

    public static File escribirArchivo(int o, int d, int fs, int lambda, int t, File archivo, int tVida, int cantidadDemandas) throws IOException {
        BufferedWriter bw;
        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo, true));
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
        }
        bw.write("" + t);
        bw.write(",");
        bw.write("" + cantidadDemandas);
        bw.write(",");
        bw.write("" + o);
        bw.write(",");
        bw.write("" + d);
        bw.write(",");
        bw.write("" + fs);
        bw.write(",");
        bw.write("" + tVida);
        bw.write("\r\n");
        bw.close();

        return archivo;
    }

    public static ArrayList<Demanda> leerDemandasPorTiempo(File archivo, int t) throws FileNotFoundException, IOException {
        String linea;
        ArrayList<Demanda> demandas = new ArrayList<>();

        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        while (((linea = br.readLine()) != null)) {
            String[] line = linea.split(",", 6);
            if (Integer.parseInt(line[0]) > t) {
                break;                          //
            } else if (Integer.parseInt(line[0]) == t) {
                Demanda d = new Demanda();
                d.setOrigen(Integer.parseInt(line[2]));
                d.setDestino(Integer.parseInt(line[3]));
                d.setNroFS(Integer.parseInt(line[4]));
                d.setTiempo(Integer.parseInt(line[5]));
                demandas.add(d);
            }
        }
        return demandas;
    }
    
        public static void cargarTablaResultadosDefrag(File archivo, JTable tabla) throws FileNotFoundException, IOException {
            
        String linea;
        DefaultTableModel model = (DefaultTableModel) tabla.getModel(); //bloqueos
        
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            while (((linea = br.readLine()) != null)) {
                String[] line = linea.split(",", 5);
                model.addRow(new Object[]{(double) Double.parseDouble(line[0]), (double) Double.parseDouble(line[1]), (double) Double.parseDouble(line[2]), (double) Double.parseDouble(line[3]), (double) Double.parseDouble(line[4])});
                
            }
        } catch (IOException ioe) {
            Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ioe);  
        }
    }


    public static int poisson(int lambda) {
        int b, bFact;
        double s, a;
        double e = Math.E;
        a = (Math.random() * 1) + 0;
        b = 0;
        bFact = factorial(b);
        s = (Math.pow(e, (-lambda))) * ((Math.pow(lambda, b)) / (bFact));
        while (a > s) {
            b++;
            bFact = factorial(b);
            s = s + ((Math.pow(e, (-lambda))) * ((Math.pow(lambda, b)) / (bFact)));
        }
        return b;
    }

    public static int obtenerTiempoDeVida(int ht) {
        int b;
        double s, a, aux, auxB, auxHT;
        double e = Math.E;
        a = Math.random();
        b = 1;
        auxB = (double) b;
        auxHT = (double) ht;
        aux = (-1) * (auxB / auxHT);
        s = 1 - (Math.pow(e, (aux)));
        while (s < a) {
            b++;
            auxB = (double) b;
            aux = (-1) * (auxB / auxHT);
            s = 1 - (Math.pow(e, (aux)));
        }
        return b;
    }

    public static int factorial(int n) {
        int resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static void escribirArchivoResultados(File archivo, int tiempo, int cantB, int cantD, double entropia, double MSI, double BFR, int cantRutas, double pathConsec, double entropiaUso, double porcUso, double probBloqueo){
        BufferedWriter bw;
        try {
        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo, true));
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
        }
        bw.write("" + tiempo);
        bw.write(",");
        bw.write("" + cantD);
        bw.write(",");
        bw.write("" + cantB);
        bw.write(",");
        bw.write("" + redondearDecimales(entropia, 3));
        bw.write(",");
        bw.write("" + redondearDecimales(MSI, 3));
        bw.write(",");
        bw.write("" + redondearDecimales(BFR, 3));
        bw.write(",");
        bw.write("" + cantRutas);
        bw.write(",");
        bw.write("" + redondearDecimales(pathConsec, 3));
        bw.write(",");
        bw.write("" + redondearDecimales(entropiaUso, 3));
        bw.write(",");
        bw.write("" + redondearDecimales(porcUso, 3));
        bw.write(",");
        bw.write("" + redondearDecimales(probBloqueo, 3));
        bw.write("\r\n");
        bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return;
    }

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }

    /*Algotimo que se encarga de graficar el resultado final de las problidades de bloqueo con respecto al earlang*/
    public static void GraficarResultado(XYSeries series[], List<XYTextAnnotation> annotation, JScrollPane panelResultados) throws FileNotFoundException, IOException {
        XYSeriesCollection datos = new XYSeriesCollection();
        panelResultados.removeAll();
        
        // create subplot 1...
        datos.addSeries(series[1]);
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis1 = new NumberAxis("Entropía");
        rangeAxis1.setAutoRangeIncludesZero(false);
        final XYPlot subplot1 = new XYPlot(datos, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        datos = new XYSeriesCollection();
        
        // create subplot 2...
        //final XYDataset data1 = createDataset1();
        datos.addSeries(series[2]);
        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis2 = new NumberAxis("MSI");
        final XYPlot subplot2 = new XYPlot(datos, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        datos = new XYSeriesCollection();

        // create subplot 3...
        datos.addSeries(series[3]);
        final XYItemRenderer renderer3 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis3 = new NumberAxis("BFR");
        rangeAxis3.setAutoRangeIncludesZero(false);
        final XYPlot subplot3 = new XYPlot(datos, null, rangeAxis3, renderer3);
        subplot3.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        datos = new XYSeriesCollection();
        
//        // create subplot 3...
//        //final XYDataset data2 = createDataset2();
//        datos.addSeries(series[4]);
//        final XYItemRenderer renderer4 = new StandardXYItemRenderer();
//        final NumberAxis rangeAxis4 = new NumberAxis("Cant. L.P.");
//        rangeAxis4.setAutoRangeIncludesZero(false);
//        final XYPlot subplot4 = new XYPlot(datos, null, rangeAxis4, renderer4);
//        subplot4.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
//        datos = new XYSeriesCollection();

        // create subplot 3... alternativo
        //final XYDataset data2 = createDataset2();
        datos.addSeries(series[8]);
        final XYItemRenderer renderer4 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis4 = new NumberAxis("Prob Bloqueo");
        rangeAxis4.setAutoRangeIncludesZero(false);
        final XYPlot subplot4 = new XYPlot(datos, null, rangeAxis4, renderer4);
        subplot4.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        datos = new XYSeriesCollection();

        // create subplot 5...
        datos.addSeries(series[5]);
        final XYItemRenderer renderer5 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis5 = new NumberAxis("Path Consec.");
        rangeAxis5.setAutoRangeIncludesZero(false);
        final XYPlot subplot5 = new XYPlot(datos, null, rangeAxis5, renderer5);
        subplot5.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        datos = new XYSeriesCollection();
        
        // create subplot 6...
        datos.addSeries(series[6]);
        final XYItemRenderer renderer6 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis6 = new NumberAxis("Entropía/Uso");
        rangeAxis6.setAutoRangeIncludesZero(false);
        final XYPlot subplot6 = new XYPlot(datos, null, rangeAxis6, renderer6);
        subplot6.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        datos = new XYSeriesCollection();
        
        // create subplot 7...
        datos.addSeries(series[7]);
        final XYItemRenderer renderer7 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis7 = new NumberAxis("% Uso");
        rangeAxis7.setAutoRangeIncludesZero(false);
        final XYPlot subplot7 = new XYPlot(datos, null, rangeAxis7, renderer7);
        subplot7.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        //datos = new XYSeriesCollection();

        //agrega los bloqueos
        //for (int a = 0; a < annotation.size(); a++) {
        for (XYTextAnnotation anno : annotation) {
            anno.setFont(new Font("SansSerif", Font.PLAIN, 15));
            //anno.setRotationAngle(Math.PI / 4.0);
            subplot1.addAnnotation(anno);
            subplot2.addAnnotation(anno);
            subplot3.addAnnotation(anno);
            subplot4.addAnnotation(anno);
            subplot5.addAnnotation(anno);
            subplot6.addAnnotation(anno);
            subplot7.addAnnotation(anno);

            ValueMarker marker = new ValueMarker(anno.getX());  // position is the value on the axis
            marker.setPaint(Color.black);
            //marker.setLabel("here"); // see JavaDoc for labels, colors, strokes

            subplot1.addDomainMarker(marker);
            subplot2.addDomainMarker(marker);
            subplot3.addDomainMarker(marker);
            subplot4.addDomainMarker(marker);
            subplot5.addDomainMarker(marker);
            subplot6.addDomainMarker(marker);
            subplot7.addDomainMarker(marker);
        }


        // parent plot...
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Tiempo"));
        plot.setGap(10.0);

        // add the subplots...
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);
        plot.add(subplot3, 1);
        plot.add(subplot4, 1);
        plot.add(subplot5, 1);
        plot.add(subplot6, 1);
        plot.add(subplot7, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        final JFreeChart chart = new JFreeChart(null,JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        //panel.setPreferredSize(new java.awt.Dimension(500, 270));
        //setContentPane(panel);
        panel.setBounds(2, 2, 970, 640);
        panelResultados.add(panel);
        panelResultados.repaint();     
    }
    
    //halla todos los caminos tomados de a dos de una topología
    public static ListaEnlazada[] hallarCaminosTomadosDeADos(double [][][] v, int cantNodos, int cantEnlaces) {
//        int promedioGrados = (cantEnlaces * 2)/cantNodos;
//        int cantCaminosDosEnlaces = (cantEnlaces * promedioGrados) / 2; // = (cantEnlaces * cantEnlaces / cantNodos)
        ListaEnlazada A[] = new ListaEnlazada[100];
        ListaEnlazada P;
        int cont = 0; //cuenta los resultados
        
        for(int i=0;i<cantNodos;i++){
            for(int j=0;j<cantNodos;j++){
                if(v[i][j][1]!=0){ //si es adyacente al primer nodo agrega como primer camino
                    for(int k=0;k<cantNodos;k++){ //por cada nodo
                        if(v[j][k][1]!=0 && i<k){ //si es adyacente al segundo nodo y si i<k entonces no graba caminos inversos
                            P = new ListaEnlazada(); //borra todo antes
                            P.insertarAlComienzo(i);
                            P.insertarAlfinal(j);
                            P.insertarAlfinal(k);
                            A[cont] = P;
                            cont++;
                        }
                    }
                }
            }
        }

       return A;
    }
    
    //Algoritmo ACO para seleccioinar el conjunto de rutas a reconfigurar
    public static void seleccionDeRutas(double [][][]v, String algoritmoAejecutar, ArrayList<Resultado> resultados, ArrayList<ListaEnlazada> rutas, double mejora, int capacidad, GrafoMatriz G, ArrayList<ListaEnlazada[]> listaKSP, File archivo, int tiempo, int cantHormigas, JTable tablaEnlaces) throws IOException {
         int h, cont, cantidadRutasMejor=rutas.size(), mejorHormiga = 0, hormigaActual;
         double entropiaActual, entropiaGrafo;
         double mejoraActual, mejor=0;
         Resultado rparcial;
         GrafoMatriz copiaGrafo =  new GrafoMatriz(G.getCantidadDeVertices());
         copiaGrafo.insertarDatos(v);
         GrafoMatriz grafoMejor =  new GrafoMatriz(G.getCantidadDeVertices());
         grafoMejor.insertarDatos(v);
         float[] feromonas = new float[rutas.size()];
         double[] visibilidad = new double[rutas.size()];
         double[] probabilidad = new double[rutas.size()];
         ArrayList<Integer> indexOrden = new ArrayList<>();
         double sumatoria;
         ArrayList<Resultado> resultadosMejor = new ArrayList<>(); //arrayList que guarda el mejor conjunto de resultados
         ArrayList<ListaEnlazada> rutasMejor = new ArrayList<>(); //arrayList que guarda el mejor conjunto de resultados
         ArrayList<Resultado> resultadosActualElegidas = new ArrayList<>(); //ArrayList que guarda el conjunto de resultados de la hormiga actual
         ArrayList<Integer> indicesMejor = new ArrayList<>(); //arrayList que guarda los indices de las rutas que consiguieron la mejor solucion
        //imprimir estado de los enlaces
        
         ArrayList<ListaEnlazada> rutasElegidas = new ArrayList<>();;  //guarda las rutas elegidas por una hormiga
         ArrayList<Integer> indicesElegidas = new ArrayList<>(); //guarda los indices de las rutas elegidas por la hormiga
         entropiaGrafo = G.entropia();

         //imprimir estado de los enlaces
//        System.out.println("Copia del Grafo: ");
//        actualizarTablaEstadoEnlaces(copiaGrafo,tablaEnlaces,capacidad);
        //vector que sirve de indice entre la probabilidad y los demas vectores
        for (int i =0; i<probabilidad.length ; i++){
            indexOrden.add(i);
        }
//         //imprimir estado de los enlaces
//        System.out.println("Copia del Grafo: ");
//        actualizarTablaEstadoEnlaces(copiaGrafo,tablaEnlaces,capacidad);
        //inicializarFeromonas y visibilidad
        for (int i =0; i<feromonas.length ; i++){
            feromonas[i]=1;
            visibilidad[i]=entropiaDeRuta(rutas.get(i), capacidad, G);
        }
        for(h=0;h<cantHormigas;h++){ //ir comparando con criterio de parada 
            rutasElegidas.clear();
            indicesElegidas.clear();
            mejoraActual=0;
            //calcular la probabilidad
            sumatoria=0.0;
            for(int i=0; i<feromonas.length; i++){
                sumatoria = sumatoria+(feromonas[indexOrden.get(i)]*visibilidad[indexOrden.get(i)]);
            }
            for(int i=0; i<feromonas.length; i++){
                probabilidad[i] = (feromonas[indexOrden.get(i)]*visibilidad[indexOrden.get(i)])/sumatoria;
            }
                        
            //ordenar todos de acuerdo a su probabilidad
            ordenarProbabilidad(probabilidad, indexOrden);
            cont = 0;
            
            while(mejoraActual<mejora && cont<rutas.size()){
                //Crear la copia del grafo original manualmente
                copiarGrafo(copiaGrafo, G, capacidad);
                indicesElegidas.add(indexOrden.get(elegirRuta(probabilidad, indicesElegidas, indexOrden)));
                rutasElegidas.add(rutas.get(indicesElegidas.get(cont)));
                desasignarFS_DefragProAct(rutasElegidas, resultados, copiaGrafo, indicesElegidas); //desasignamos los FS de las rutas a reconfigurar

                //imprimir estado de los enlaces
//                System.out.println("Despues de desasignar del grafo copia, cont: " + cont);
//                imprimirListaEnlazada(rutasElegidas);
//                actualizarTablaEstadoEnlaces(copiaGrafo,tablaEnlaces,capacidad);
                
                //ORDENAR LISTA
                if (rutasElegidas.size()>1){
                    ordenarRutas(resultados, rutasElegidas, indicesElegidas, rutasElegidas.size());
//                    System.out.println("Ordena la lista de rutas a re rutear de acuerdo a la cantidad de FS");
                }
                //volver a rutear con las nuevas condiciones mismo algoritmo
                int contBloqueos =0;
                resultadosActualElegidas.clear();
                for (int i=0; i<rutasElegidas.size(); i++){
                    int fs = resultados.get(indicesElegidas.get(i)).getFin() - resultados.get(indicesElegidas.get(i)).getInicio();
                    fs++;
                    int tVida = G.acceder(rutas.get(indicesElegidas.get(i)).getInicio().getDato(),rutas.get(indicesElegidas.get(i)).getInicio().getSiguiente().getDato()).getFS()[resultados.get(indicesElegidas.get(i)).getInicio()].getTiempo();
                    Demanda demandaActual = new Demanda(rutasElegidas.get(i).getInicio().getDato(), obtenerFin(rutasElegidas.get(i).getInicio()).getDato(), fs, tVida);
                    //ListaEnlazada[] ksp = KSP(G, rutasElegidas.get(i).getInicio().getDato(),rutasElegidas.get(i).getFin().getDato() , 5);
                    ListaEnlazada[] ksp = listaKSP.get(indicesElegidas.get(i));
                    rparcial = realizarRuteo(algoritmoAejecutar,demandaActual,copiaGrafo, ksp,capacidad);
                    if (rparcial != null) {
                        asignarFS_Defrag(ksp, rparcial, copiaGrafo, demandaActual, 0);
                        resultadosActualElegidas.add(rparcial); //guardar el conjunto de resultados para esta solucion parcial
                        //imprimir estado de los enlaces
//                        System.out.println("Despues de RE rutear la/s ruta/s en el grafo copia: ");
//                        imprimirDemanda(demandaActual);
//                        actualizarTablaEstadoEnlaces(copiaGrafo,tablaEnlaces,capacidad);
                
                        //verificar si la nueva asignacion crea una disrupcion
                    } else {
                        contBloqueos++;
//                        System.out.println("El Re Ruteo en el grafo copia fue bloqueo, la demanda fue: ");
//                        imprimirDemanda(demandaActual);
                    }
                }
                
                //si hubo bloqueo no debe contar como una solucion
                if(contBloqueos==0){
                    entropiaActual = copiaGrafo.entropia();
//                    System.out.println("Entropia del Grafo Copia: " + entropiaActual);
                    
                    mejoraActual = 100 - ((redondearDecimales(entropiaActual, 6) * 100)/redondearDecimales(entropiaGrafo, 6));
                } else {
                    mejoraActual = 0;
                    break;
                }
                cont++;
            }
//            System.out.println("Índices de rutas que eligio la hormiga: "+h);
//            for (int i =0; i<indicesElegidas.size(); i++){
//                System.out.println(""+indicesElegidas.get(i));
//            }
//            System.out.println("Rutas que eligio la hormiga: "+h);
//            imprimirListaEnlazada(rutasElegidas);
            
            
            if(mejoraActual>mejor && mejoraActual>mejora){ //si se logro una mejora mas alta
 //               if(cantidadRutasMejor >= resultadosActualElegidas.size()){ //si la nueva mejora mueve menos rutas o la misma cantidad 
                    System.out.println("Mejor actual: " + redondearDecimales(mejoraActual, 2) + "%, con "+rutasElegidas.size() + " rutas re ruteadas, Hormiga: "+h);
                    mejor = mejoraActual;
//                    cantidadRutasMejor = resultadosActualElegidas.size();
                    copiarGrafo(grafoMejor, copiaGrafo, capacidad);
                    //Guarda el mejor conjunto de resultados para posteriormente cambiar en el vector resultados
                    resultadosMejor.clear();
                    rutasMejor.clear();
                    indicesMejor.clear();
                    for (int k=0; k<resultadosActualElegidas.size(); k++){
                        resultadosMejor.add(resultadosActualElegidas.get(k));
                        indicesMejor.add(indicesElegidas.get(k));
                        rutasMejor.add(listaKSP.get(indicesElegidas.get(k))[resultadosActualElegidas.get(k).getCamino()]);
                        mejorHormiga = h;
                    }
//              }
            }
             
            //depositar feromonas de acuerdo al porcentaje de mejora
            for(int i=0;i<indicesElegidas.size();i++){
                feromonas[indicesElegidas.get(i)] = (float) (feromonas[indicesElegidas.get(i)] + (mejor/100)); //TODO agregar feromona de acuerdo a la mejora
            }
            
            //evaporar feromonas
            for(int i=0;i<feromonas.length;i++){
                feromonas[i] = (float) (feromonas[i]*0.9); //TODO agregar feromona de acuerdo a la mejora
            }
            
        }
        if(mejor!=0){
           copiarGrafo(G, grafoMejor, capacidad);
           escribirArchivoDefrag(archivo, rutasElegidas.size(), tiempo, mejora, true ,mejorHormiga, rutas.size());
           //Retirar resultados viejos del vector resultados, colocar los resultados de la mejor solucion           
           for (int k=0; k<indicesMejor.size(); k++){
               resultados.set(indicesMejor.get(k), resultadosMejor.get(k));
               rutas.set(indicesMejor.get(k), rutasMejor.get(k));
           }
           System.out.println("Encontró una solucion mejor entre las hormigas y copio el grafoCopia al Grafo original.");
        }else{
           escribirArchivoDefrag(archivo, rutasElegidas.size(), tiempo, mejora, false, mejorHormiga, rutas.size());
           System.out.println("No encontró un resultado mínimo deseado entre las hormigas, no hace nada con el grafo. :(");
        }

     }
   
     //Algoritmo ACO para seleccioinar el conjunto de rutas a reconfigurar
    public static void seleccionDeRutasPathConsec(double [][][]v, String algoritmoAejecutar, ArrayList<Resultado> resultados, ArrayList<ListaEnlazada> rutas, double mejora, int capacidad, GrafoMatriz G, ArrayList<ListaEnlazada[]> listaKSP, File archivo, int tiempo, int cantHormigas, JTable tablaEnlaces, ListaEnlazada[] caminosDeDosEnlaces) throws IOException {
         int h, cont, mejorHormiga=0;
         double pathConsecActual, pathConsecGrafo;
         double mejoraActual, mejor=0;
         Resultado rparcial;
         GrafoMatriz copiaGrafo =  new GrafoMatriz(G.getCantidadDeVertices());
         copiaGrafo.insertarDatos(v);
         GrafoMatriz grafoMejor =  new GrafoMatriz(G.getCantidadDeVertices());
         grafoMejor.insertarDatos(v);
         float[] feromonas = new float[rutas.size()];
         double[] visibilidad = new double[rutas.size()];
         double[] probabilidad = new double[rutas.size()];
         double sumatoria;
         ArrayList<Integer> indexOrden = new ArrayList<>();
        
         ArrayList<ListaEnlazada> rutasElegidas = new ArrayList<>();;  //guarda las rutas elegidas por una hormiga
         ArrayList<Integer> indicesElegidas = new ArrayList<>(); //guarda los indices de las rutas elegidas por la hormiga
         pathConsecGrafo = Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidad, G);
         
         ArrayList<Resultado> resultadosMejor = new ArrayList<>(); //arrayList que guarda el mejor conjunto de resultados
         ArrayList<ListaEnlazada> rutasMejor = new ArrayList<>(); //arrayList que guarda el mejor conjunto de resultados
         ArrayList<Resultado> resultadosActualElegidas = new ArrayList<>(); //ArrayList que guarda el conjunto de resultados de la hormiga actual
         ArrayList<Integer> indicesMejor = new ArrayList<>(); //arrayList que guarda los indices de las rutas que consiguieron la mejor solucion
        
        //inicializarFeromonas y visibilidad
        for (int i =0; i<feromonas.length ; i++){
            feromonas[i]=1;
            ListaEnlazada[] rutaActual = new ListaEnlazada[1];
            rutaActual[0] = rutas.get(i);
            visibilidad[i]=1/Metricas.PathConsecutiveness(rutaActual, capacidad, G);
        }
        //cargar lista de indices
        for (int i =0; i<probabilidad.length ; i++){
            indexOrden.add(i);
        }
        for(h=0;h<cantHormigas;h++){ //ir comparando con criterio de parada 
            rutasElegidas.clear();
            indicesElegidas.clear();
            mejoraActual=0;
            //calcular la probabilidad
            sumatoria=0.0;
            for(int i=0; i<feromonas.length; i++){
                sumatoria = sumatoria+(feromonas[indexOrden.get(i)]*visibilidad[indexOrden.get(i)]);
            }
            for(int i=0; i<feromonas.length; i++){
                probabilidad[i] = (feromonas[indexOrden.get(i)]*visibilidad[indexOrden.get(i)])/sumatoria;
            }
            //ordenar todos de acuerdo a su probabilidad
            ordenarProbabilidad(probabilidad, indexOrden);

            cont = 0;
            
            while(mejoraActual<mejora && cont<rutas.size()){
                //Crear la copia del grafo original manualmente
                copiarGrafo(copiaGrafo, G, capacidad);
                indicesElegidas.add(indexOrden.get(elegirRuta(probabilidad, indicesElegidas, indexOrden)));
                rutasElegidas.add(rutas.get(indicesElegidas.get(cont)));
                desasignarFS_DefragProAct(rutasElegidas, resultados, copiaGrafo, indicesElegidas); //desasignamos los FS de las rutas a reconfigurar
                
                //ORDENAR LISTA
                if (rutasElegidas.size()>1){
                    ordenarRutas(resultados, rutasElegidas, indicesElegidas, rutasElegidas.size());
                }
                //volver a rutear con las nuevas condiciones mismo algoritmo
                int contBloqueos =0;
                resultadosActualElegidas.clear();
                for (int i=0; i<rutasElegidas.size(); i++){
                    int fs = resultados.get(indicesElegidas.get(i)).getFin() - resultados.get(indicesElegidas.get(i)).getInicio();
                    fs++;
                    int tVida = G.acceder(rutas.get(indicesElegidas.get(i)).getInicio().getDato(),rutas.get(indicesElegidas.get(i)).getInicio().getSiguiente().getDato()).getFS()[resultados.get(indicesElegidas.get(i)).getInicio()].getTiempo();
                    Demanda demandaActual = new Demanda(rutasElegidas.get(i).getInicio().getDato(), obtenerFin(rutasElegidas.get(i).getInicio()).getDato(), fs, tVida);
                    //ListaEnlazada[] ksp = KSP(G, rutasElegidas.get(i).getInicio().getDato(),rutasElegidas.get(i).getFin().getDato() , 5);
                    ListaEnlazada[] ksp = listaKSP.get(indicesElegidas.get(i));
                    rparcial = realizarRuteo(algoritmoAejecutar,demandaActual,copiaGrafo, ksp,capacidad);
                    if (rparcial != null) {
                        asignarFS_Defrag(ksp, rparcial, copiaGrafo, demandaActual, 0); 
                        resultadosActualElegidas.add(rparcial); //guardar el conjunto de resultados para esta solucion parcial
                        //verificar si la nueva asignacion crea una disrupcion
                    } else {
                        contBloqueos++;
                    }
                }
                
                //si hubo bloqueo no debe contar como una solucion
                if(contBloqueos==0){
                    pathConsecActual = Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidad, copiaGrafo);                    
                    mejoraActual = ((redondearDecimales(pathConsecActual, 6) * 100)/redondearDecimales(pathConsecGrafo, 6))-100;
                } else {
                    mejoraActual = 0;
                    break;
                }
                cont++;
            }
            
            //si hay una mejor solucion, reemplazar el grafo guardado por el grafo de la mejor solucion
            if(mejoraActual>mejor && mejoraActual>mejora){
                System.out.println("Mejor actual: " + redondearDecimales(mejoraActual, 2) + "%, con "+rutasElegidas.size() + " rutas re ruteadas");
                mejor = mejoraActual;
                copiarGrafo(grafoMejor, copiaGrafo, capacidad);
                //Guarda el mejor conjunto de resultados para posteriormente cambiar en el vector resultados
                resultadosMejor.clear();
                rutasMejor.clear();
                indicesMejor.clear();
                for (int k=0; k<resultadosActualElegidas.size(); k++){
                    resultadosMejor.add(resultadosActualElegidas.get(k));
                    indicesMejor.add(indicesElegidas.get(k));
                    rutasMejor.add(listaKSP.get(indicesElegidas.get(k))[resultadosActualElegidas.get(k).getCamino()]);
                    mejorHormiga = h;
                }
                
            }
             
            //depositar feromonas de acuerdo al porcentaje de mejora
            for(int i=0;i<indicesElegidas.size();i++){
                feromonas[indicesElegidas.get(i)] = (float) (feromonas[indicesElegidas.get(i)] + (mejor/100)); //TODO agregar feromona de acuerdo a la mejora
            }
            
            //evaporar feromonas
            for(int i=0;i<feromonas.length;i++){
                feromonas[i] = (float) (feromonas[i]*0.9); //TODO agregar feromona de acuerdo a la mejora
            }
            
        }
        if(mejor!=0){
           copiarGrafo(G, grafoMejor, capacidad);
           escribirArchivoDefrag(archivo, rutasElegidas.size(), tiempo, mejora, true ,mejorHormiga, rutas.size());
           for (int k=0; k<indicesMejor.size(); k++){
               resultados.set(indicesMejor.get(k), resultadosMejor.get(k));
               rutas.set(indicesMejor.get(k), rutasMejor.get(k));
           }
           System.out.println("Encontró un mejor entre las hormigas y copio el grafoCopia al Grafo original.");
        }else{
           escribirArchivoDefrag(archivo, rutasElegidas.size(), tiempo, mejora, false ,mejorHormiga, rutas.size());
           System.out.println("No encontró un resultado mínimo deseado entre las hormigas, no hace nada con el grafo. :(");
        }

     }
    //Metodo para realizar la copia de un grafo item por item
    public static void copiarGrafo(GrafoMatriz copia, GrafoMatriz original, int capacidad){
        for(int i=0;i<original.getCantidadDeVertices();i++){
            for(int j=0;j<original.getCantidadDeVertices();j++){
                if(original.acceder(i, j)!=null){
                    for (int k=0; k<capacidad; k++){
                        copia.acceder(i, j).getFS()[k].setEstado(original.acceder(i, j).getFS()[k].getEstado());
                        copia.acceder(i, j).getFS()[k].setTiempo(original.acceder(i, j).getFS()[k].getTiempo());
                        copia.acceder(i, j).getFS()[k].setConexion(original.acceder(i, j).getFS()[k].getConexion());
                        copia.acceder(i, j).getFS()[k].setPropietario(original.acceder(i, j).getFS()[k].getPropietario());
                    } 
                }
            }
        }
    }
    
    public static boolean sonIguales(GrafoMatriz copia, GrafoMatriz original, int capacidad){
        for(int i=0;i<original.getCantidadDeVertices();i++){
            for(int j=0;j<original.getCantidadDeVertices();j++){
                if(original.acceder(i, j)!=null){
                    for (int k=0; k<capacidad; k++){
                        if (original.acceder(i, j).getFS()[k].getEstado() != copia.acceder(i, j).getFS()[k].getEstado() || 
                        original.acceder(i, j).getFS()[k].getTiempo() != copia.acceder(i, j).getFS()[k].getTiempo() ||
                        original.acceder(i, j).getFS()[k].getConexion() != copia.acceder(i, j).getFS()[k].getConexion() ||
                        original.acceder(i, j).getFS()[k].getPropietario() != copia.acceder(i, j).getFS()[k].getPropietario()){
                            return false;
                        }
                    } 
                }
            }
        }
        return true;
    }
    //Metodo que ordena las rutas elegidas por las hormigas para su posterior re-ruteo 
    //por orden decreciente de cantidad de FS requeridos
    public static void ordenarRutas(ArrayList<Resultado> resultados, ArrayList<ListaEnlazada> rutas, ArrayList<Integer> indices,int n){
        Integer aux = 0;
        ListaEnlazada aux2 = new ListaEnlazada();
        for (int i = 0; i <= n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (resultados.get(indices.get(j)).getFin()-resultados.get(indices.get(j)).getInicio() > resultados.get(indices.get(i)).getFin()-resultados.get(indices.get(i)).getInicio()) {
                    //cambia el orden del array de indices
                    aux = indices.get(i);
                    indices.set(i,indices.get(j));
                    indices.set(j, aux);
                    
                    //cambia el orden en el array de rutas
                    aux2 = rutas.get(i);
                    rutas.set(i,rutas.get(j));
                    rutas.set(j, aux2);
                }
            }
        }
    }
    
    //Metodo que ordena el vector de probabilidades de forma creciente
    //reordenando tambien los vectores de feromonas y visibilidad, y el array de rutas.
    public static void ordenarProbabilidad(double[] probabilidad, ArrayList<Integer> orden){
        double auxp;
        int auxi;
        int n = probabilidad.length;
        for (int i = 0; i <= n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (probabilidad[i] > probabilidad[j]) {
                    auxp = probabilidad[i];
                    probabilidad[i] = probabilidad[j];
                    probabilidad[j] = auxp;
                    
                    //cambiar el orden del vector de indices
                    auxi = orden.get(i);
                    orden.set(i,orden.get(j));
                    orden.set(j, auxi);
                }
            }
        }
    }
    
    //Metodo que realiza el re-ruteo de las rutas seleccionadas por las hormigas
    public static Resultado realizarRuteo(String algoritmo, Demanda demanda,  GrafoMatriz grafoCopia, ListaEnlazada ksp[], int capacidadE){
        Resultado r = null;
        switch (algoritmo) {
                            case "FA":
                                r = Algoritmos_Defrag_ProAct.Def_FA(grafoCopia, demanda, ksp, capacidadE);
                                break;
                            case "FA-CA":
                                r = Algoritmos_Defrag_ProAct.Def_FACA(grafoCopia, demanda, ksp, capacidadE);
                                break;
                           case "MTLSC":
                                r = Algoritmos.MTLSC_Algorithm(grafoCopia, demanda, ksp, capacidadE);
                                break;
                        }
        return r;
    }
    
    //Metodo que elige la ruta a seleccionar de acuerdo a su vector de probabilidades
    public static int elegirRuta(double[] p, ArrayList<Integer> indices, ArrayList<Integer> indexOrden){
        int indice;
        ArrayList<Integer> indicesProbab = new ArrayList<>();
        for (int i=0; i<indices.size(); i++){
            indicesProbab.add(indexOrden.indexOf(indices.get(i)));
        }
        //sumar todas las probabilidades que siguen en juego
        double sumaProbParticipan = 0;
        int n = p.length;
        for (int i = 0; i <= n - 1; i++) {
            sumaProbParticipan = sumaProbParticipan + p[i];
        }
        while (true){
            //hallar el valor random entre 0 a el valor máximo de probabilidades en juego
            Random randomGenerator = new Random();
            double randomValue = sumaProbParticipan * randomGenerator.nextDouble();

            double sumaProb = 0;
            indice = -1;
            while(sumaProb < randomValue){
                indice++;
                sumaProb = sumaProb + p[indice];
            }
            if (!isInList(indicesProbab, indice)){
                    break;
            }
        }
        
        if (indice > p.length - 1 || indice < 0){
            System.out.println("oh ooh, mando índice: " + indice + ", máximo: " + p.length);
        }
        return indice;
    }
    
    //Metodo que calcula la entropia de los enlaces por los que pasa una ruta en particular 
    public static double entropiaDeRuta(ListaEnlazada ruta, int capacidad, GrafoMatriz G){
        double uelink=0;
        double entropy=0;
        int countlinks=0;
        Nodo t;
        for (t = ruta.getInicio(); t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
            int UEcont=0;
            if(G.acceder(t.getDato(), t.getSiguiente().getDato())!=null){
                for(int kk=0;kk<G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length-1;kk++){
                    if(G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[kk].getEstado()!=G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[kk+1].getEstado()){
                        UEcont++;
                    }
                }
                uelink=uelink+((double)UEcont/(G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length-1));
                countlinks++;
            }
        }
        entropy=uelink/countlinks;
        return entropy;
    }
    
     /*Metodo que se encarga de desasignar los FS de una ruta marcada para reconfiguracion en el grafo matriz copia*/
    public static void desasignarFS_DefragProAct(ArrayList<ListaEnlazada> rutas, ArrayList<Resultado> r, GrafoMatriz G, ArrayList<Integer> indices) {
        for (int i =0; i<rutas.size(); i++){
            for (Nodo nod = rutas.get(i).getInicio(); nod.getSiguiente().getSiguiente() != null; nod = nod.getSiguiente()) {
                for (int p = r.get(indices.get(i)).getInicio(); p <= r.get(indices.get(i)).getFin(); p++) {
                    if (G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].getEstado() == 1){
                        System.out.println("CONFLICTO AL DESASIGNAR UN SLOT ida. (NO ESTA LUEGO ASIGNADO). Nodo: " + nod.getDato() + ", Posición: " + p );
                    }
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setEstado(1);
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setTiempo(0);
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setConexion(1);
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).setUtilizacionFS(p, 0);
                    if (G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].getEstado() == 1){
                        System.out.println("CONFLICTO AL DESASIGNAR UN SLOT vuelta. (NO ESTA LUEGO ASIGNADO). Nodo: " + nod.getDato() + ", Posición: " + p );
                    }
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setEstado(1);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setConexion(-1);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setTiempo(0);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).setUtilizacionFS(p, 0);
                }
            }
        }
    }
    
    public static void escribirArchivoDefrag (File archivo, int cantRutas, int tiempo, double mejora, boolean solucion, int mejorHormiga, int totalRutas) throws IOException{
        BufferedWriter bw;
        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo, true));
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
        }
        if (solucion){
            bw.write("" + tiempo);
            bw.write(",");
            bw.write("" + cantRutas);
            bw.write(",");
            bw.write("" + mejora);
            bw.write(",");
            bw.write("" + mejorHormiga);
            bw.write(",");
            bw.write("" + totalRutas);
            bw.write("\r\n");
            bw.close();
        }else{
            bw.write("" + tiempo);
            bw.write(",");
            bw.write("" + 0);
            bw.write(",");
            bw.write("" + 0);
            bw.write(",");
            bw.write(""+-1);
            bw.write(",");
            bw.write("" + totalRutas);
            bw.write("\r\n");
            bw.close();
        }
    }
    
    public static void actualizarTablaEstadoEnlaces(GrafoMatriz G, JTable tabla, int capacidadEnlaces){
//        //estado final de los enlaces
//        int cont = 0;
//        DefaultTableModel modelEstadoEnlaces = (DefaultTableModel) tabla.getModel(); //todos
//
//        //agrega una columna por cada enlace
//        for(int i=0;i<G.getCantidadDeVertices();i++){
//            for(int j=0;j<G.getCantidadDeVertices();j++){
//                if(j>i && G.acceder(i, j)!=null){
//                    modelEstadoEnlaces.addColumn(i + " - " + j); //con el nombre de origen-destino
//                }
//            }
//        }
//
//        //agrega todas las lineas por cada FS
//        modelEstadoEnlaces.setRowCount(capacidadEnlaces);
//
//        //crear matriz de estados de los enlaces
//        for(int i=0;i<G.getCantidadDeVertices();i++){
//            for(int j=0;j<G.getCantidadDeVertices();j++){
//                if(j>i && G.acceder(i, j)!=null){
//                    for(int kk=0;kk<G.acceder(i, j).getFS().length;kk++){
//                        modelEstadoEnlaces.setValueAt(G.acceder(i, j).getFS()[kk].getEstado() + "(" + G.acceder(i, j).getFS()[kk].getTiempo() + ")", kk, cont);
//                    }
//                    cont++;
//                }
//            }
//        }
//        
//        
//        //imprimir en consola
//        //imprime encabezado con los enlaces
//        System.out.print("|");
//        System.out.print("\t");
//        for (int y=0; y < 21; y++) {
//            System.out.print (modelEstadoEnlaces.getColumnName(y));
//          System.out.print("\t");
//        }
//        System.out.println("\\");
//        for (int x=0; x < 10; x++) {
//            System.out.print("|");
//            System.out.print("\t");
//            for (int y=0; y < 21; y++) {
//              System.out.print (modelEstadoEnlaces.getValueAt(x, y));
//              System.out.print("\t");
//            }
//            System.out.println("|");
//        }
//        System.out.println("\\");
    }

    private static Nodo obtenerFin(Nodo inicio){
        Nodo nd = inicio;
        while (nd.getSiguiente().getSiguiente() !=null){
            nd = nd.getSiguiente();
        }
        return nd;
    }
    
    private static void imprimirResultadoRuteo(ResultadoRuteo r){
        //imprimir ruta
        Nodo nd = r.getRuta().getInicio();
        System.out.println(nd.getDato());
        while (nd.getSiguiente().getSiguiente() !=null){
            nd = nd.getSiguiente();
            System.out.println(nd.getDato());
        }
        
        //nrocaminoksp
        System.out.println("Nro camino ksp: " + r.getNroCaminoKsp());
        
        //demanda
        System.out.println("Demanda. Origen: " + r.getDemanda().getOrigen()+ " Destino: " + r.getDemanda().getDestino() + " Cant FS: " + r.getDemanda().getNroFS()+ " Tiempo de vida: " + r.getDemanda().getTiempo());
        
    }
    
    public static void imprimirResultado(Resultado r){
        System.out.println("Camino: " + r.getCamino() + " Inicio: " + r.getInicio() + " Fin: " + r.getFin() + " CP: " + r.getCp());
    }
    
    public static void imprimirDemanda(Demanda d){
        System.out.println("Demanda. Origen: " + d.getOrigen()+ " Destino: " + d.getDestino() + " Cant FS: " + d.getNroFS()+ " Tiempo de vida: " + d.getTiempo());
    }
    
    public static void imprimirListaEnlazada(ArrayList<ListaEnlazada> lista) {
        for (int i =0; i<lista.size(); i++){
            System.out.println("Ruta " + i + ": ");
            for (Nodo nod = lista.get(i).getInicio(); nod.getSiguiente().getSiguiente() != null; nod = nod.getSiguiente()) {
                System.out.println(nod.getDato());
            }
        }
    }
    
        public static void escribirArchivoEstados(File archivo, double entropia, double msi, double bfr, double pathConsec, double entropiaUso, boolean esBloqueo, int cantRutas, double porcUso) {
        BufferedWriter bw;
        try {
            if (archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo, true));
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("bloqueo,entropia,msi,bfr,rutas,pathconsec,entropiauso,porcuso");
                bw.write("\r\n");
            }
            if(esBloqueo){
                bw.write("" + 1);
            }else {
                bw.write("" + 0);
            }
            bw.write(",");
            bw.write("" + redondearDecimales(entropia, 3));
            bw.write(",");
            bw.write("" + redondearDecimales(msi, 3));
            bw.write(",");
            bw.write("" + redondearDecimales(bfr, 3));
            bw.write(",");
            bw.write("" + cantRutas);
            bw.write(",");
            bw.write("" + redondearDecimales(pathConsec, 3));
            bw.write(",");
            bw.write("" + redondearDecimales(entropiaUso, 3));
            bw.write(",");
            bw.write("" + redondearDecimales(porcUso, 3));
            bw.write("\r\n");
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return;
    }
        
    public static double calcularProbabilidadDeBloqueo (double entropia, double msi, double bfr, double pathConsec,double entropiaUso, double porcUso, int rutas){
        double probabilidad = 0.0, resultado = 0.0, aux=0.0;
        double e = Math.E;
        //primera formula
        //resultado = (137.3690*entropia)+(-3689.0928*bfr)+(0.4861*rutas)+(5.3776*pathConsec)+(-23.5029*entropiaUso)+(433.2762*porcUso)+(11.5475*msi)-1963.5518;
        resultado = (137.3690*entropia)+(-3689.0928*bfr)+(0.4861*rutas)+(5.3776*pathConsec)+(-23.5029*entropiaUso)+(433.2762*porcUso)+(11.5475*msi)-1963.5518;
        //segunda formula
        //resultado = (-3.7101*entropia)+(288.4515*bfr)+(0.5280*rutas)+(5.0762*pathConsec)+(0.7617*entropiaUso)+(-216.5551*porcUso)+(-1.6310*msi)+304.0107;
        aux = (Math.pow(e, (resultado)));

        probabilidad = aux / (1+aux);
        if(probabilidad*100 == 100){
            System.out.print("");
        }
        return probabilidad;
    }
    
}