
package xsectionsolver;

import static java.lang.Math.PI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class Calculator {
    
    final static public int MIN_ACTUAL_LENGTH = 20;
    final static public int MIN_LAYERS_NUM = 20;
    
    final static public int XSECTION_SQUARE = 0;
    final static public int XSECTION_CIRCLE = 1;
    final static public int XSECTION_SEMICIRCLE = 2;
    final static public int XSECTION_EQUILIBRIUM_TRIANGLE = 3;
    final static public int XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE = 4;
    final static public HashMap<Integer,Double> AreaConstants = new HashMap();
    
    final static public int RIGHT_RIEMANNSUM=0;
    final static public int LEFT_RIEMANNSUM=1;
    final static public int MIDDLE_RIEMANNSUM=2;
    
    private String Function1Expression;
    private String Function2Expression;
    private Function Function1;
    private Function Function2;
    private int xSectionType;
    private int layersNum;
    private double upperLimit;
    private double lowerLimit;
    private double actualLength;
    private int riemannSumType;
    private boolean fPieceWise;
    private boolean gPieceWise;
    private double actualToAlgebraRatio;
    private double stepLength;
    
    private ArrayList<Argument> pieceWiseLimits = new ArrayList();
    
    private DecimalFormat df = new DecimalFormat("0.######");
    
    public Calculator(String Function1Expression, String Function2Expression, int xSectionType, int layersNum, 
            int lowerLimit, int upperLimit, int actualLength, int riemannSumType) {
        
        this.Function1Expression = Function1Expression;
        this.Function2Expression = Function2Expression;
        this.xSectionType = xSectionType;
        this.layersNum = layersNum;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.actualLength = actualLength;
        this.riemannSumType = riemannSumType;
        
        actualToAlgebraRatio = actualLength/(Math.abs(upperLimit - lowerLimit));
        stepLength = Math.abs(upperLimit - lowerLimit) / (double)layersNum;
        
        AreaConstants.put(XSECTION_SQUARE, 1.0d);
        AreaConstants.put(XSECTION_CIRCLE, PI/4.);
        AreaConstants.put(XSECTION_SEMICIRCLE, PI/8.);
        AreaConstants.put(XSECTION_EQUILIBRIUM_TRIANGLE, Math.sqrt(3)/4.);
        AreaConstants.put(XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE, 0.25d);
        setFunctions();
        mergePieceWiseLimits();
    }
    
    private void setFunctions(){
        this.fPieceWise = Function1Expression.substring(Function1Expression.indexOf("=")+1).contains(",");
        this.gPieceWise = Function2Expression.substring(Function2Expression.indexOf("=")+1).contains(",");
        
        if(fPieceWise){
            Function1 = new PieceWiseFunction("f",Function1Expression,"x");
        }
        else{
            Function1 = new Function("f",Function1Expression,"x");
        }
        
        if(gPieceWise){
            Function2 = new PieceWiseFunction("f",Function2Expression,"x");
            
        }
        else{
            Function2 = new Function("g",Function2Expression,"x");
        }

    }
    
    public void mergePieceWiseLimits(){
        pieceWiseLimits.add(new Argument("x",lowerLimit));
        Argument[][] fLimits = Function1.getPieceWiseLimits();
        Argument[][] gLimits = Function2.getPieceWiseLimits();
        /*for(Argument[] a:fLimits){
            for(Argument b:a){
                mXparser.consolePrintln("f:"+b.getArgumentValue());
            }
        }
        for(Argument[] a:gLimits){
            for(Argument b:a){
                mXparser.consolePrintln("g:"+b.getArgumentValue());
            }
        }*/
        int i = 0, j = 0;
        int f = fLimits.length*2, g = gLimits.length*2;
        
        while(i<f && j<g){
            if(fLimits[i/2][i%2].getArgumentValue() < gLimits[j/2][j%2].getArgumentValue()){
                
                if(pieceWiseLimits.get(pieceWiseLimits.size()-1).getArgumentValue()<fLimits[i/2][i%2].getArgumentValue()
                        && fLimits[i/2][i%2].getArgumentValue() < upperLimit){

                    pieceWiseLimits.add(fLimits[i/2][i%2]);
                }
                i++;
            }
            else{
                if(pieceWiseLimits.get(pieceWiseLimits.size()-1).getArgumentValue()<gLimits[j/2][j%2].getArgumentValue()
                        && gLimits[j/2][j%2].getArgumentValue() < upperLimit){

                    pieceWiseLimits.add(gLimits[j/2][j%2]);
                }
                j++;
            }
            
        }
        
        while(i<f){
            if(pieceWiseLimits.get(pieceWiseLimits.size()-1).getArgumentValue()<fLimits[i/2][i%2].getArgumentValue()
                    && fLimits[i/2][i%2].getArgumentValue() < upperLimit){

                pieceWiseLimits.add(fLimits[i/2][i%2]);
            }
            i++;
        }
        
        while(j<g){
            if(pieceWiseLimits.get(pieceWiseLimits.size()-1).getArgumentValue()<gLimits[j/2][j%2].getArgumentValue()
                    && gLimits[j/2][j%2].getArgumentValue() < upperLimit){

                pieceWiseLimits.add(gLimits[j/2][j%2]);
            }
            j++;
        }
        
        pieceWiseLimits.add(new Argument("x",upperLimit));
    }
    
    public double calculateTheoraticalVolume(){
        double AreaConstant = AreaConstants.get(xSectionType);
        if(!fPieceWise && !gPieceWise){
        
            Expression volume = new Expression("int((("+Function1.getFunctionExpressionString()
                    +")-("+Function2.getFunctionExpressionString()+"))^2,x,"+lowerLimit+","+upperLimit+")");
            return AreaConstant * volume.calculate();
            
        }
        else{
            
            double volumeSum = 0;
            
            for(int i=0;i<pieceWiseLimits.size()-1;i++){
                double lowL=pieceWiseLimits.get(i).getArgumentValue();
                double upL=pieceWiseLimits.get(i+1).getArgumentValue();
                        
                Expression volume = new Expression("int((("+Function1.getFunctionExpressionStringAt(upL)
                        +")-("+Function2.getFunctionExpressionStringAt(upL)+"))^2,x,"+lowL+","+upL+")");
                volumeSum += volume.calculate();
            }
            
            return AreaConstant * volumeSum;
            
        }
        
    }
    
    public double calculateRSumVolume() {
        double volumeSum = 0;
        for (double i=lowerLimit; RoundToSixDecimal(i)<upperLimit; i+=stepLength){
            double x;
            switch (riemannSumType) {
                case RIGHT_RIEMANNSUM:
                    x=i;
                    break;
                case LEFT_RIEMANNSUM:
                    x=i+stepLength;
                    break;
                default:
                    x=i+stepLength/2;
                    break;
            }
            double y1=Function1.calculate(new Argument("x="+x));
            double y2=Function2.calculate(new Argument("x="+x));
            volumeSum += AreaConstants.get(xSectionType)*Math.pow(y1-y2,2)*stepLength;
            //System.out.println(RoundToSixDecimal(x)+","+RoundToSixDecimal(y1)+","+RoundToSixDecimal(y2)+","+(RoundToSixDecimal(Math.pow(y1-y2,2)*stepLength)));
            
        }
        
        return volumeSum;
    }
    
    public static double RoundToSixDecimal(double d){
        return Math.round(d*1000000.d)/1000000.d;
    }
    
    public double sliceXPos(int i) {return 0;
    }
    
    public double sliceActualPos(int i) {return 0;
    }
    
    public double baseLength(int i) {return 0;
    }
    
    public double getStepLength() {
    
        return stepLength;
    
    }
    
    public double getLayerThickness() {
    
        return actualToAlgebraRatio * stepLength;
    
    }
    
    public double getActualToAlgebraRatio(){
        return actualToAlgebraRatio;
    }
    
    public double surfaceArea(int i) {return 0;
    }
    
    public double layerVolume(int i) {return 0;
    }
    
    public double[] yBoundary(int i) {return null;
    }

    public int getRiemannSumType() {
        return riemannSumType;
    }

    public void setRiemannSumType(int riemannSumType) {
        this.riemannSumType = riemannSumType;
    }

    public int getMIN_ACTUAL_LENGTH() {
        return MIN_ACTUAL_LENGTH;
    }
    
    public Function getFunction1(){
        return Function1;
    }
    
    public Function getFunction2(){
        return Function2;
    }

    public String getFunction1Expression() {
        return Function1Expression;
    }

    public void setFunction1Expression(String Function1Expression) {
        this.Function1Expression = Function1Expression;
    }

    public String getFunction2Expression() {
        return Function2Expression;
    }

    public void setFunction2Expression(String Function2Expression) {
        this.Function2Expression = Function2Expression;
    }

    public int getxSectionType() {
        return xSectionType;
    }

    public void setxSectionType(int xSectionType) {
        this.xSectionType = xSectionType;
    }

    public int getLayersNum() {
        return layersNum;
    }

    public void setLayersNum(int layersNum) {
        this.layersNum = layersNum;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public ArrayList<Argument> getPieceWiseLimits() {
        return pieceWiseLimits;
    }
    
    public boolean getFunction1Type(){
        return fPieceWise;
    }
    
    public boolean getFunction2Type(){
        return gPieceWise;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Calculator other = (Calculator) obj;
        if (this.MIN_ACTUAL_LENGTH != other.MIN_ACTUAL_LENGTH) {
            return false;
        }
        if (this.xSectionType != other.xSectionType) {
            return false;
        }
        if (this.layersNum != other.layersNum) {
            return false;
        }
        if (this.upperLimit != other.upperLimit) {
            return false;
        }
        if (this.lowerLimit != other.lowerLimit) {
            return false;
        }
        if (this.actualLength != other.actualLength) {
            return false;
        }
        if (this.riemannSumType != other.riemannSumType) {
            return false;
        }
        if (!Objects.equals(this.Function1Expression, other.Function1Expression)) {
            return false;
        }
        if (!Objects.equals(this.Function2Expression, other.Function2Expression)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Calculator{" + "MIN_ACTUAL_LENGTH=" + MIN_ACTUAL_LENGTH + ", f=" + Function1Expression + ", g=" + Function2Expression + ", xSectionType=" + xSectionType + ", layersNum=" + layersNum + ", upperLimit=" + upperLimit + ", lowerLimit=" + lowerLimit + ", actualLength=" + actualLength + ", riemannSumType=" + riemannSumType + '}';
    }

    public void setActualToAlgebraRatio(double actualToAlgebraRatio) {
        this.actualToAlgebraRatio = actualToAlgebraRatio;
    }

    public double getActualLength() {
        return actualLength;
    }

    public void setActualLength(double actualLength) {
        this.actualLength = actualLength;
    }
    
}