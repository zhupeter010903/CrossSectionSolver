
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
        
        
        Calculator cal2 = new Calculator("arcsec(x),-4,-1,arcsin(x),-1,1","arccsc(x),-4,-1,arccos(x),-1,1",0,20,-1,1,20,0);
        
        System.out.println(cal2.getFunction1().getFunctionExpressionString());
        System.out.println(cal2.getFunction2().getFunctionExpressionString());
        
        Function f3 = new Function("f","arcsin(x)","x");
        Expression e = new Expression("int((arcsin(x)-arccos(x))^2,x,-1,1)");
        //mXparser.consolePrintln(f3.getFunctionExpressionString()+","+e.getExpressionString() + " = " + e.calculate());
        System.out.println(cal2.calculateTheoraticalVolume());
        
    }
    
}