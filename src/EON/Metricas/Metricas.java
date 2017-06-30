/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package EON.Metricas;
import EON.*;
import EON.Utilitarios.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author sFernandez
 */
public class Metricas {
    
    /*
    Bandwidth Fragmentation Ratio - Mide la cantidad de bloques de FS por enlace
    @param
    G - red actual
    capacidad - cantdiad de FS por enlace
    
    */
    public static double BFR(GrafoMatriz G, int capacidad){
        ArrayList<Integer> lista = new ArrayList<>();
        double contOcupados = 0;
        double contSeguido = 0;
        double mayorSeguido = 0;
        double sumaEnlaces = 0;
        double [][] maxBlocks = new double[G.getCantidadDeVertices()][G.getCantidadDeVertices()];
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0 ;j<G.getCantidadDeVertices() ; j++){
                if (!Utilitarios.isInList(lista, j)){
                    if (G.acceder(i, j)!=null){
                        for (int k=0; k<capacidad; k++){
                            //1= libre 0 = Ocupado
                            if (G.acceder(i, j).getFS()[k].getEstado()==1){
                                contSeguido++;
                            }else{
                                if (contSeguido>mayorSeguido){
                                    mayorSeguido = contSeguido;
                                }
                                contSeguido = 0;
                                contOcupados++;
                            }
                        }
                        if (contSeguido>mayorSeguido){
                            mayorSeguido = contSeguido;
                        }
                        
                        if (mayorSeguido==0){
                            System.out.println("");
                        }
                        
                        if (contOcupados==capacidad){
                            maxBlocks[i][j] = 0;
                        }else{
                            maxBlocks[i][j] = 1 - (mayorSeguido/(capacidad-contOcupados));
                        }
                        
                        if (maxBlocks[i][j]==1){
                            System.out.println("MaxBlock= "+mayorSeguido + " / (352 - subm(b) : "+ contOcupados);
                        }
                        
                    }else{
                        maxBlocks[i][j] = -1;
                    }
                    contOcupados = 0;
                    mayorSeguido = 0;
                    contSeguido = 0;
                }
            }
            lista.add(i);
        }
        
        lista = new ArrayList<>();
        
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (!Utilitarios.isInList(lista, j) && maxBlocks[i][j]!=-1 ){
                    
                    sumaEnlaces = sumaEnlaces + maxBlocks[i][j];
                    
                }
            }
            lista.add(i);
        }
        if (sumaEnlaces==G.getCantidadEnlaces()){
            System.out.println("");
        }
        
        return (sumaEnlaces/G.getCantidadEnlaces());
        
        
        
    }
    
    /*
    Maximu Used Index-Slot retorna el mayor indice usado en un enlace
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    */
    public static double MSI(GrafoMatriz G, int capacidad){
        ArrayList<Integer> lista = new ArrayList<>();
        double cont = 0;
        double sumaIndices = 0;
        //ArrayList<Integer> indices = new ArrayList<>();
        double [] indices = new double[G.getCantidadEnlaces()];
        int c= 0;
        
        for (int i=0; i<G.getCantidadEnlaces(); i++){
            indices[i] = 0;
        }
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (G.acceder(i, j)!=null){
                    if (!Utilitarios.isInList(lista, j)){
                        for (int k=capacidad-1; k>-1; k--){
                            // 1 = libre 0 = Ocupado
                            if (G.acceder(i, j).getFS()[k].getEstado()==0){
                                //indices.add(k);
                                indices[c] = k;
                                c++;
                                break;
                            }
                        }
                    }
                }
            }
            lista.add(i);
        }
        
        
        
        for (int i=0; i<indices.length; i++){
            sumaIndices = sumaIndices + indices[i];
        }
        
        return (sumaIndices/G.getCantidadEnlaces());
    }
    
    /*
    Path Consecutiveness - Usado en DefragProAct
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    ListaEnlazada[] caminos - todos los caminos de dos enlaces de la red
    */
    public static double PathConsecutiveness (ListaEnlazada[] caminos, int capacidad, GrafoMatriz G){
        int k = 0;
        float cl = 0;
        double suma=0;
        double promedio;
        int OE[] = new int[(capacidad)];
        
        while (caminos.length > k && caminos[k] != null) { //esto no es redundante?
            //inicializa el espectro
            for (int w = 0; w < (capacidad); w++) {
                OE[w] = 1;
            }
            Nodo t = caminos[k].getInicio(); //la asignación no se usa creo
            int Total = (G.acceder(t.getDato(), t.getSiguiente().getDato())).getFS().length; //capacidad?
            //calcula la ocupacion real del espectro
            for (int j = 0; j < Total; j++) {
                for (t = caminos[k].getInicio(); t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                    if (G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[j].getEstado() == 0) {
                        OE[j] = 0;
                        break;
                    }
                }
            }
            int vector[] = new int[2];
            List<int[]> BloquesE = new LinkedList<>(); //no necesito bloques
            int cgb = 0;//contador global de bloques
            for (int c = 0; c < OE.length; c++) {//calcula la cantidad de bloques
                int i = 0, f = 0;
                if (OE[c] == 1) {
                    cgb++;
                    i = f = c;
                    for (; c < OE.length; c++) { //esto modifica el valor de c del for anterior?
                        if (OE[c] == 0 || c == OE.length - 1) { //si encuentra un slot ocupado o si es el final de OE
                            f = c;
                            vector[0] = i;
                            vector[1] = f;
                            BloquesE.add(vector);
                        }
                    }
                }
            }
            
            t = caminos[k].getInicio();
            FrecuencySlots enlace[] = new FrecuencySlots[G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length];
            float sum = 0, cfs = 0;
            cl = 0;
            for (; t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                enlace = G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS();
                //contador  frecuency slots
                for (int j = 0; j < enlace.length - 1; j++) {
                    sum += enlace[j].getEstado() * enlace[j + 1].getEstado(); //suma solo si son FS libres consecutivos
                    if (enlace[j].getEstado() == 1) {
                        cfs++;
                    }
                }
                if (enlace[enlace.length - 1].getEstado() == 1) {
                    cfs++;
                }
                cl = (sum / cgb) * (cfs / OE.length);
            }
            suma = suma + cl;
            k++;
        }
        promedio = suma/caminos.length;
        
        return promedio;
    }
    
    /*
    Entropia por el porcentaje de Uso del enlace - Usado en DefragProAct
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    ListaEnlazada[] caminos - todos los caminos de dos enlaces de la red
    */
    public static double EntropiaPorUso (ListaEnlazada[] caminos, int capacidad, GrafoMatriz G){
        double uelink=0;
        double entropy=0;
        double total=0;
        double promedio=0, uso = 0;
        int countlinks=0;
        int k = 0, suma = 0;
        int OE[] = new int[(capacidad)];
        Nodo t;
        while (k<caminos.length && caminos[k]!=null){
                    for (t = caminos[k].getInicio(); t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                        int UEcont=0;
                        if(G.acceder(t.getDato(), t.getSiguiente().getDato())!=null){
                            for(int kk=0;kk<G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length-1;kk++){
                                if(G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[kk].getEstado()!=G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[kk+1].getEstado()){
                                    UEcont++;
                                }
                            }
                            uelink=uelink+(double)UEcont;//(G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length-1));
                            countlinks++;
                        }
                    }
                    entropy=uelink/countlinks;
                    //inicializa el espectro
                    //Calcular el procentaje de uso
//                    for (int w = 0; w < (capacidad); w++) {
//                        OE[w] = 1;
//                    }
                    Nodo n = caminos[k].getInicio();
                    int Total = (G.acceder(n.getDato(), n.getSiguiente().getDato())).getFS().length;
                    //calcula la ocupacion real del espectro
                    for (int j = 0; j < Total; j++) {
                        for (n = caminos[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                            if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].getEstado() == 0) {
//                                OE[j] = 0;
                                suma++;
                                break;
                            }
                        }
                    }
                    uso = suma / capacidad;
                    total= total + (entropy*uso);
                    k++;
        }
        promedio = total/caminos.length;
        return promedio;
    }
    
    /*
    Porcentaje de uso de los FS en todo el grafo - Usado en DefragProAct
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    */
    public static double PorcUsoGrafo (GrafoMatriz G){
        double contUso = 0;
        double contTotal = 0;
        for(int i=0;i<G.getCantidadDeVertices();i++){
            for(int j=0;j<G.getCantidadDeVertices();j++){
                if(j>i && G.acceder(i, j)!=null){
                    for (FrecuencySlots f : G.acceder(i, j).getFS()) {
                        contTotal++;
                        if (f.getEstado() == 0) {
                            contUso++;
                        }
                    }
                }
            }
        }

        double porcUso = contUso/contTotal;
        return porcUso;
    }
}
