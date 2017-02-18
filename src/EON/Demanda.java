
package EON;

import java.util.Random;

/**
 *
 * @author Team Delvalle
 * 
 * La clase se encarga de generar las Demandas.
 * El origen, el destino de forma aleatoria.
 * Y alacenar el numero de FSs requeridos y el tiempo de permanencia en la red.
 */
public class Demanda {
    
    private int origen;
    private int destino;
    private int nroFS;
    private int tiempo;
    
    public Demanda(int o,int d, int n,int t){
        this.origen=o;
        this.destino=d;
        this.nroFS=n;
        this.tiempo=t;
    }
    public Demanda(){
        origen=destino=nroFS=0;
    }
    public int getNroFS(){
        return this.nroFS;
    }
    public int getOrigen(){
        return this.origen;
    }
    public int getDestino(){
        return this.destino;
    }
    public int getTiempo(){
        return this.tiempo;
    }
    
    //****//
    //Agregado por el reruteo
    public void setTiempo(int tiempo){
        this.tiempo= tiempo;
    }
    /*
    Metodo encargado de obtener la demandas para la opcion "Tiempo de permanencia y FS Fijos".
    El origen y desino se toman al azar.
    El tiempo de permanecia y la cantidad de FS por demanda lo define el usuario
    */
    public void obtenerDemandasFijo(int capacidad, int tiempo,int n){
        Random r=new Random();
        this.origen=r.nextInt(n);
        this.destino=r.nextInt(n);
        while(this.destino==this.origen)
            this.destino=r.nextInt(n);
        this.nroFS=capacidad;
        this.tiempo=tiempo;
    }
    /*
    Metodo encargado de obtener la demandas para la opcion "Tiempo de permanencia Fijo y FS Variable".
    El origen y desino se toman al azar.
    El tiempo de permanecia lo introduce el usuario y la cantidad de FS por demanda es tomado al azar, la cantidad
    maxima de FS por demanda es inticada por capacidadMax
    (Aun Falta mejorar, existen muchas dudas sobre la generacion de los FS. No fiable.)
    */
    public void obtenerDemandasFSVariable(int capacidadMax, int tiempo,int n){
        Random r=new Random();
        this.origen=r.nextInt(n);
        this.destino=r.nextInt(n);
        while(this.destino==this.origen)
            this.destino=r.nextInt(n);
        this.nroFS=2+r.nextInt(capacidadMax-1);
        //this.nroFS=2+r.nextInt(12);
        this.tiempo=tiempo;
    }
}
