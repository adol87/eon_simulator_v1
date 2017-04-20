package EON.Utilitarios;

import EON.*;
import EON.Algoritmos.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oracle.jrockit.jfr.events.Bits;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
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
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setEstado(0);
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setTiempo(d.getTiempo());
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setConexion(conexid);
                util = G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getUtilizacion()[p]++;
                G.acceder(nod.getDato(), nod.getSiguiente().getDato()).setUtilizacionFS(p, util);
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
        int cutAux = 0;
        //int slots=0;
        int m = 1; //controla que los cortes no esten de seguido
        boolean filaLibre = false;
        ArrayList<Integer> indices = new ArrayList<Integer>();

        ArrayList<Integer> indicesL = new ArrayList<Integer>();

        for (int k = 0; k < ksp.size(); k++) {

            cutsSlot = nroCuts(ksp.get(k), G, capacidad);

            if (cutsSlot != null) {

                //atender
                if (cutsFinales.size() < 1) {
                    cutsFinales.add(cutsSlot[0]);
                    caminosFinales.add(k);
                    fSlots.add(cutsSlot[1]);
                } else if (cutsFinales.get(cutsFinales.size() - 1) > cutsSlot[0]) {
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
            caminoFinal = calcularAlineacion(ksp, G, capacidad, caminosFinales, indicesL, fSlots);
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
        ArrayList<Integer> cutsFinales = new ArrayList<Integer>();
        ArrayList<Integer> caminosFinales = new ArrayList<Integer>();
        ArrayList<Integer> fSlots = new ArrayList<Integer>();
        int caminoFinal = 0;
        int cutAux = 0;

        ArrayList<Integer> indices = new ArrayList<Integer>();

        ArrayList<Integer> indicesL = new ArrayList<Integer>();

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
    public static int calcularAlineacion(ArrayList<ListaEnlazada> ksp, GrafoMatriz G, int capacidad, ArrayList<Integer> caminos, ArrayList<Integer> indicesL, ArrayList<Integer> fSlots) {

        Integer[] alineacion = new Integer[indicesL.size()];
        int alineacionAux = 0;
        int alineacionFinal = 999;
        int indiceFinal = -1;
        ArrayList<Integer> desalineado = new ArrayList<Integer>();

        for (int k = 0; k < caminos.size(); k++) {

            alineacionAux = contarDesalineamiento(ksp.get(caminos.get(k)), G, capacidad, indicesL, fSlots.get(k));

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
    public static int contarDesalineamiento(ListaEnlazada ksp, GrafoMatriz G, int capacidad, ArrayList<Integer> indicesL, int fSlots) {

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
}
