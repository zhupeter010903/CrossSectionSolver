
package xsectionsolver;

import java.io.*;
import java.util.*;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class XSectionSolver {

    public static void main(String[] args) {
        
        /*if (restartJVM()) {
            return;
        }*/
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
        
        
        Calculator cal2 = new Calculator("arcsec(x)-pi/4,-4,-1,arccos(x)-pi/4,-1,1,arcsec(x)-pi/4,1,4"
                ,"arccsc(x)-pi/4,-4,-1,arcsin(x)-pi/4,-1,1,arccsc(x)-pi/4,1,4"
                ,Calculator.XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE,20,"-4","-1",22.5,Calculator.LEFT_RIEMANNSUM);
        
        System.out.println(cal2.getDataString());
        /*for(int i=0;i<cal2.getPieceWiseLimits().size();i++){
            System.out.println(i+": "+cal2.getPieceWiseLimits().get(i).getArgumentValue());
        }*/
        //mXparser.consolePrintln(f3.getFunctionExpressionString()+","+e.getExpressionString() + " = " + e.calculate());
        
        //Mark
        /*Calculator cal3 = new Calculator("(cos((pi/4)*x))^4","((2*x/pi)*e^(0.2*x))-x/(pi^2-x^2)"
                ,Calculator.XSECTION_EQUILIBRIUM_TRIANGLE,56,"-2.6","2.9",28,0);*/
        //System.out.println(cal3.getDataString());
        
        //Cici
        /*Calculator cal4 = new Calculator("-3*abs(sin(x/2))","-abs(x)+2*pi"
                ,Calculator.XSECTION_SEMICIRCLE,50,"-2*pi","2*pi",20,0);*/
        //System.out.println(cal4.getDataString());
        
        
        //Alec
        //Calculator cal5 = new Calculator("sqrt((x^2)*(pi-x))","sin(x)"
                //,Calculator.XSECTION_SEMICIRCLE,53,"0","pi",24,0);
        //System.out.println(cal5.getDataString());
        
        /*
        Window3d threeD= new Window3d(800,600,"3d model",cal3);
        threeD.run();*/
        //new XSectionGUI().run();
        //new XSectionGUI().setVisible(true);
        /*Calculator cal=new Calculator("arcsec(x)-pi/4,-4,-1,arccos(x)-pi/4,-1,1,arcsec(x)-pi/4,1,4",
        "arcsec(x)-pi/4,-4,-1,arccos(x)-pi/4,-1,1,arcsec(x)-pi/4,1,4",
        0,40,"-4","4",20,0);
        System.out.println(cal.getFunction1().calculate(0));*/
    }
    /**
     * Mark Chen: 
     * Function 1:(cos((4/pi)*x))^4
     * Function 2:((2*x/pi)*e^(0.2*x))-x/(pi^2-x^2)
     * Calculator.XSECTION_EQUILIBRIUM_TRIANGLE
     * -2.9,2.9
    **/
    public static boolean restartJVM() {
      
        String osName = System.getProperty("os.name");

        // if not a mac return false
        if (!osName.startsWith("Mac") && !osName.startsWith("Darwin")) {
           return false;
        }

        // get current jvm process pid
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        // get environment variable on whether XstartOnFirstThread is enabled
        String env = System.getenv("JAVA_STARTED_ON_FIRST_THREAD_" + pid);

        // if environment variable is "1" then XstartOnFirstThread is enabled
        if (env != null && env.equals("1")) {
           return false;
        }

        // restart jvm with -XstartOnFirstThread
        String separator = System.getProperty("file.separator");
        String classpath = System.getProperty("java.class.path");
        String mainClass = System.getenv("JAVA_MAIN_CLASS_" + pid);
        String jvmPath = System.getProperty("java.home") + separator + "bin" + separator + "java";

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();

        ArrayList<String> jvmArgs = new ArrayList<String>();

        jvmArgs.add(jvmPath);
        jvmArgs.add("-XstartOnFirstThread");
        jvmArgs.addAll(inputArguments);
        jvmArgs.add("-cp");
        jvmArgs.add(classpath);
        jvmArgs.add(mainClass);

        // if you don't need console output, just enable these two lines 
        // and delete bits after it. This JVM will then terminate.
        //ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
        //processBuilder.start();

        try {
           ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
           processBuilder.redirectErrorStream(true);
           Process process = processBuilder.start();

           InputStream is = process.getInputStream();
           InputStreamReader isr = new InputStreamReader(is);
           BufferedReader br = new BufferedReader(isr);
           String line;

           while ((line = br.readLine()) != null) {
              System.out.println(line);
           }

           process.waitFor();
        } catch (Exception e) {
           e.printStackTrace();
        }

        return true;
   }
    
}