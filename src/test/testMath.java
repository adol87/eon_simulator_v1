package test;


import static EON.Utilitarios.Utilitarios.factorial;

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
        for (int i=0; i<100; i++){
            Double value = t.poisson2(0.2, 1, i);
            
            System.out.println(value + "---> "+value.intValue());
        }   
        
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
    
}
