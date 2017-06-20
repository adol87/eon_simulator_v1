/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EON.Algoritmos;

import EON.Demanda;
import EON.GrafoMatriz;
import EON.ListaEnlazada;
import EON.ListaEnlazadaAsignadas;
import EON.Nodo;
import EON.Resultado;
import EON.Utilitarios.Utilitarios;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author sFernandez
 */
public class Algoritmos_Defrag_ProAct {

    
    public static Resultado Def_FACA(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<ListaEnlazada>();
        ArrayList<Integer> inicios = new ArrayList<Integer>();
        ArrayList<Integer> fines = new ArrayList<Integer>();
        ArrayList<Integer> indiceKsp = new ArrayList<Integer>();

        //Probamos para cada camino, si existe espectro para ubicar la damanda
        int k=0;

        while(k<ksp.length && ksp[k]!=null){
            //Inicializadomos el espectro, inicialmente todos los FSs estan libres
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            //Calcular la ocupacion del espectro para cada camino k
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                   //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
           
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            fines.add(fin);
                            inicios.add(inicio);
                            demandaColocada=1;
                            kspUbicados.add(ksp[k]);
                            indiceKsp.add(k);
                            //inicio=fin=cont=0;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                    demandaColocada = 0;
                    break;
                }
            }
            k++;
        }
        
        /*if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }*/
        
        if (kspUbicados.isEmpty()){
            //System.out.println("Desubicado");
            return null;
        }
        
        int [] cortesSlots = new int [2];
        double corte = -1;
        double Fcmt = 9999999;
        double FcmtAux = -1;
        
        int caminoElegido = -1;

        //controla que exista un resultado
        boolean nulo = true;

        ArrayList<Integer> indiceL = new ArrayList<Integer>();
        
        //contar los cortes de cada candidato
        for (int i=0; i<kspUbicados.size(); i++){
            cortesSlots = Utilitarios.nroCuts(kspUbicados.get(i), G, capacidad);
            if (cortesSlots != null){
                corte = (double)cortesSlots[0];
                
                indiceL = Utilitarios.buscarIndices(kspUbicados.get(i), G, capacidad);
                
                
                //contar los desalineamientos
                double desalineamiento = (double)Utilitarios.contarDesalineamiento(kspUbicados.get(i), G, capacidad, indiceL, cortesSlots[1]);
                
                double capacidadLibre = (double)Utilitarios.contarCapacidadLibre(kspUbicados.get(i),G,capacidad);
                
                double saltos = (double)Utilitarios.calcularSaltos(kspUbicados.get(i));
                
                
                double vecinos = (double)Utilitarios.contarVecinos(kspUbicados.get(i),G,capacidad);
                

                
                FcmtAux = corte + (desalineamiento/(demanda.getNroFS()*vecinos)) + (saltos *(demanda.getNroFS()/capacidadLibre)); 
                
                if (FcmtAux<Fcmt){
                    Fcmt = FcmtAux;
                    caminoElegido = i;
                }
                
                nulo = false;
                if (caminoElegido==-1){
                    System.out.println("Camino Elegido = -1 ..................");
                }
                
            }
        }
        
        if (caminoElegido==-1){
            System.out.println("Camino Elegido = -1 ..................");
        }
        //caminoElegido = Utilitarios.contarCuts(kspUbicados, G, capacidad);
    
        if (nulo || caminoElegido==-1){
            return null;
        }
        
        Resultado r= new Resultado();
        /*r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);*/
        
        r.setCamino(indiceKsp.get(caminoElegido));
        r.setFin(fines.get(caminoElegido));
        r.setInicio(inicios.get(caminoElegido));
        return r;
    }
    
     public static Resultado Def_FA(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<ListaEnlazada>();
        ArrayList<Integer> inicios = new ArrayList<Integer>();
        ArrayList<Integer> fines = new ArrayList<Integer>();
        ArrayList<Integer> indiceKsp = new ArrayList<Integer>();

        //Probamos para cada camino, si existe espectro para ubicar la damanda
        int k=0;

        while(k<ksp.length && ksp[k]!=null){
            //Inicializadomos el espectro, inicialmente todos los FSs estan libres
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            //Calcular la ocupacion del espectro para cada camino k
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                   //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
           
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    cont=0; //faltó esta inicialización del contador.
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            fines.add(fin);
                            inicios.add(inicio);
                            demandaColocada=1;
                            kspUbicados.add(ksp[k]);
                            indiceKsp.add(k);
                            //inicio=fin=cont=0;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                    demandaColocada=0;
                    break;
                }
            }
            k++;
        }
        
        /*if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }*/
        
        if (kspUbicados.isEmpty()){
            //System.out.println("Desubicado");
            return null;
        }
        
        
        
        int caminoElegido = Utilitarios.contarCuts(kspUbicados, G, capacidad);
    
        Resultado r= new Resultado();
        /*r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);*/
        
        r.setCamino(indiceKsp.get(caminoElegido));
        r.setFin(fines.get(caminoElegido));
        r.setInicio(inicios.get(caminoElegido));
        return r;
    }
}
