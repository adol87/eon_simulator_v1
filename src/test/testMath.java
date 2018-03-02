package test;


import static EON.Utilitarios.Utilitarios.factorial;
import java.text.DecimalFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adolfo
 */
public class testMath {
    
    public static void main (String[] args){
        
        testMath t = new testMath();
        
        int value1 = t.obtenerTiempoDeVida(10);
        
        for (int i=0; i<20; i++){
            Double value = t.poisson3(10, 0, i);
            
            //System.out.println(value + "---> "+value.intValue());
        }   
        
    }
    
    
    public int obtenerTiempoDeVida(int ht) {
        int b;
        double s, a, aux, auxB, auxHT;
        double e = Math.E;
        a = Math.random();
        b = 1;
        auxB = (double) b;
        auxHT = (double) ht;
        aux = (-1) * (auxB / auxHT);
        s = 1 - (Math.pow(e, (aux)));
        //System.out.println("a: "+a);
        
        DecimalFormat df = new DecimalFormat("#.000"); 
        
        //System.out.println(df.format(s));
        while (s < a) {
            b++;
            auxB = (double) b;
            aux = (-1) * (auxB / auxHT);
            s = 1 - (Math.pow(e, (aux)));
            //System.out.println(df.format(s));
        }
        return b;
    }
    
    public  double poisson2(double lambda, int T, int n) {
        double fac1, fac2, fac3;
        double e = Math.E;
        fac1 = (Math.pow((lambda*T), n));
        fac2 = (Math.pow(e, (-lambda*T)));
        fac3 = factorial(n);
        
        double resul = (fac1 * fac2) / fac3;
        return resul;
      
    }
    
    public  double poisson3(double lambda, int T, int n) {
        double fac1, fac2, fac3;
        double e = Math.E;
        fac1 = (Math.pow((lambda), n));
        fac2 = (Math.pow(e, (-lambda)));
        fac3 = factorial(n);
        
        double resul = (fac1 * fac2) / fac3;
        return resul;
      
    }
    
}
