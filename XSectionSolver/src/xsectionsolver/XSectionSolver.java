
package xsectionsolver;

import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class XSectionSolver {

    public static void main(String[] args) {
        
        System.setProperty("sun.awt.noerasebackground", "true");
        
        Expression eh = new Expression("ln(sin(pi/2))");
        mXparser.consolePrintln(eh.getExpressionString() + " = " + eh.calculate());
        
        
        Function f1 = new Function("f(x) = x^4");
        Function f2 = new Function("f(x) = x");
        Expression e = new Expression("int("+f1.getFunctionExpressionString()+",x,-1,1)");
        mXparser.consolePrintln(f1.getFunctionExpressionString()+","+e.getExpressionString() + " = " + e.calculate());
        
    }
    
}