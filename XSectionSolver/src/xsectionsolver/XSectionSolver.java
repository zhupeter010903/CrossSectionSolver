
package xsectionsolver;

import java.text.DecimalFormat;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class XSectionSolver {

    public static void main(String[] args) {
        
        System.setProperty("sun.awt.noerasebackground", "true");
        
        /*Expression eh = new Expression("ln(sin(pi/2))");
        mXparser.consolePrintln(eh.getExpressionString() + " = " + eh.calculate());
        
        
        Function f1 = new Function("f(x) = x^2");
        Function f2 = new Function("f","x^x","x");
        Expression e = new Expression("int("+f1.getFunctionExpressionString()+"^2,x,-1,1)");
        mXparser.consolePrintln(f1.getFunctionExpressionString()+","+e.getExpressionString() + " = " + e.calculate());
        
        mXparser.consolePrintln(f2.getFunctionExpressionString()+ " = " + f2.calculate(2));
        
        Calculator cal = new Calculator("x","-x",0,20,1,-1,20,0,false,false);
        System.out.println(cal.calculateTheoraticalVolume());*/
        
        
        Calculator cal2 = new Calculator("arcsec(x)-pi/4,-4,-1,arcsin(x)-pi/4,-1,1,arcsec(x)-pi/4,1,4"
                ,"arccsc(x)-pi/4,-4,-1,arccos(x)-pi/4,-1,1,arccsc(x)-pi/4,1,4"
                ,Calculator.XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE,80,"-4","4",20,0);
        //System.out.println(cal2.getDataString());
        /*for(int i=0;i<cal2.getPieceWiseLimits().size();i++){
            System.out.println(i+": "+cal2.getPieceWiseLimits().get(i).getArgumentValue());
        }*/
        //mXparser.consolePrintln(f3.getFunctionExpressionString()+","+e.getExpressionString() + " = " + e.calculate());
        
        //Mark
        Calculator cal3 = new Calculator("(cos((4/pi)*x))^4","((2*x/pi)*e^(0.2*x))-x/(pi^2-x^2)"
                ,Calculator.XSECTION_EQUILIBRIUM_TRIANGLE,40,"-2.9","2.9",20,0);
        //System.out.println(cal3.getDataString());
        
        //Cici
        Calculator cal4 = new Calculator("-3*abs(sin(x/2))","-abs(x)+2*pi"
                ,Calculator.XSECTION_SEMICIRCLE,50,"-2*pi","2*pi",20,0);
        //System.out.println(cal4.getDataString());
        
        
        //Alec
        Calculator cal5 = new Calculator("sqrt((x^2)*(pi-x))","sin(x)"
                ,Calculator.XSECTION_SEMICIRCLE,53,"0","pi",24,0);
        //System.out.println(cal5.getDataString());
        //Expression eh = new Expression("-3*abs(sin(x/2))",new Argument("x=pi"));
        //System.out.println(eh.calculate());
           
        
        
        Window3d threeD= new Window3d(800,600,"3d model",cal2);
        threeD.run();
        //new XSectionGUI().run();
        //new XSectionGUI().setVisible(true);
    }
    /**
     * Mark Chen: 
     * Function 1:(cos((4/pi)*x))^4
     * Function 2:((2*x/pi)*e^(0.2*x))-x/(pi^2-x^2)
     * Calculator.XSECTION_EQUILIBRIUM_TRIANGLE
     * -2.9,2.9
    **/
    
}