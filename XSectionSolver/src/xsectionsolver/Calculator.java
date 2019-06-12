
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
    
    final static public int LEFT_RIEMANNSUM=0;
    final static public int RIGHT_RIEMANNSUM=1;
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
    private double riemannSumAdjustment;
    
    private ArrayList<Argument> pieceWiseLimits = new ArrayList();
    
    public static DecimalFormat df = new DecimalFormat("0.######");
    
    public Calculator(String Function1Expression, String Function2Expression, int xSectionType, int layersNum, 
            String lowerLimit, String upperLimit, double actualLength, int riemannSumType) {
        
        this.Function1Expression = Function1Expression;
        this.Function2Expression = Function2Expression;
        this.xSectionType = xSectionType;
        this.layersNum = layersNum;
        this.lowerLimit = new Argument("x="+lowerLimit).getArgumentValue();
        this.upperLimit = new Argument("x="+upperLimit).getArgumentValue();
        this.actualLength = actualLength;
        this.riemannSumType = riemannSumType;
        
        actualToAlgebraRatio = actualLength/(Math.abs(this.upperLimit - this.lowerLimit));
        stepLength = Math.abs(this.upperLimit - this.lowerLimit) / (double)layersNum;
        riemannSumAdjustment = this.getRSumAdjustment();
        
        AreaConstants.put(XSECTION_SQUARE, 1.0d);
        AreaConstants.put(XSECTION_CIRCLE, PI/4.0d);
        AreaConstants.put(XSECTION_SEMICIRCLE, PI/8.0d);
        AreaConstants.put(XSECTION_EQUILIBRIUM_TRIANGLE, Math.sqrt(3)/4.0d);
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
            Function2 = new PieceWiseFunction("g",Function2Expression,"x");
            
        }
        else{
            Function2 = new Function("g",Function2Expression,"x");
        }

    }
    
    private void mergePieceWiseLimits(){
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
    
    public double getTheoraticalVolume(){
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
    
    public double getTheoraticalVolumeActual(){
        return getTheoraticalVolume() * Math.pow(actualToAlgebraRatio, 3);
    }
    
    public double getRSumVolume() {
        double volumeSum = 0;
        for (int i=0; i<layersNum; i++){
            volumeSum += this.layerVolume(i);
        }
        
        return volumeSum;
    }
    
    public double getRSumVolumeActual(){
        return getRSumVolume() * Math.pow(actualToAlgebraRatio, 3);
    }
    
    public double getRSumAdjustment(){
        switch (riemannSumType) {
            case RIGHT_RIEMANNSUM:
                return stepLength;
            case LEFT_RIEMANNSUM:
                return 0;
            default:
                return stepLength/2;
        }
    }
    
    public static double Round(double d, int decimalPlace){
        return Math.round(d*Math.pow(10, decimalPlace))/Math.pow(10, decimalPlace);
    }
    
    public static String RoundToString(double d, int decimalPlace){
        return df.format(Round(d,decimalPlace));
    }
    
    public double getSliceXPos(int i) {
        return lowerLimit+i*stepLength;
    }
    
    public double getSliceActualXPos(int i) {
        return i*stepLength*actualToAlgebraRatio;
    }
    
    public double getBaseLength(int i) {
        
        return getYBoundary(i)[0]-getYBoundary(i)[1];
    }
    
    public double getBaseLengthActual(int i){
        return getBaseLength(i) * actualToAlgebraRatio;
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
    
    public double surfaceArea(int i) {
        double baseLength = getBaseLength(i);
        double area = Math.pow(baseLength, 2)*AreaConstants.get(this.xSectionType);
        return area;
    }
    
    public double surfaceAreaActual(int i) {
        return surfaceArea(i) * Math.pow(actualToAlgebraRatio, 2);
    }
    
    public double layerVolume(int i) {
        return surfaceArea(i) * stepLength;
    }
    
    public double layerVolumeActual (int i){
        return surfaceAreaActual(i) * getLayerThickness();
    }
    
    public double[] getYBoundary(int i) {
        double[] yBound = new double[2];
        
        double x = getSliceXPos(i);
        x += riemannSumAdjustment;
        double y1 = Function1.calculate(new Argument("x="+x));
        double y2 = Function2.calculate(new Argument("x="+x));
        yBound[0] = Math.max(y1, y2);
        yBound[1] = Math.min(y1, y2);
        return yBound;
    }
    
    public double[] getYBoundaryActual(int i){
        double[] yActualBound = new double[2];
        yActualBound[0] = getYBoundary(i)[0] * actualToAlgebraRatio;
        yActualBound[1] = getYBoundary(i)[1] * actualToAlgebraRatio;
        return yActualBound;
    }

    public int getRiemannSumType() {
        return riemannSumType;
    }

    public void setRiemannSumType(int riemannSumType) {
        this.riemannSumType = riemannSumType;
        riemannSumAdjustment = this.getRSumAdjustment();
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
        setFunctions();
        mergePieceWiseLimits();
    }

    public String getFunction2Expression() {
        return Function2Expression;
    }

    public void setFunction2Expression(String Function2Expression) {
        this.Function2Expression = Function2Expression;
        setFunctions();
        mergePieceWiseLimits();
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
        stepLength = Math.abs(upperLimit - lowerLimit) / (double)layersNum;
        riemannSumAdjustment = this.getRSumAdjustment();
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
        actualToAlgebraRatio = actualLength/(Math.abs(upperLimit - lowerLimit));
        stepLength = Math.abs(upperLimit - lowerLimit) / (double)layersNum;
        riemannSumAdjustment = this.getRSumAdjustment();
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
        actualToAlgebraRatio = actualLength/(Math.abs(upperLimit - lowerLimit));
        stepLength = Math.abs(upperLimit - lowerLimit) / (double)layersNum;
        riemannSumAdjustment = this.getRSumAdjustment();
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
    
    public void setActualToAlgebraRatio(double actualToAlgebraRatio) {
        this.actualToAlgebraRatio = actualToAlgebraRatio;
    }

    public double getActualLength() {
        return actualLength;
    }

    public void setActualLength(double actualLength) {
        this.actualLength = actualLength;
        actualToAlgebraRatio = actualLength/(Math.abs(upperLimit - lowerLimit));
    }

    public String getDataString(){
        int d = 6;
        int dcm=2;
        String data = "";
        data += "Function 1: "+Function1.getFunctionName()+"("+Function1.getParameterName(0)+") = "
                +Function1.getFunctionExpressionString()+"\n";
        data += "Function 2: "+Function2.getFunctionName()+"("+Function2.getParameterName(0)+") = "
                +Function2.getFunctionExpressionString()+"\n";
        double volume = getTheoraticalVolume();
        double rVolume = getRSumVolume();
        data += "Volume: "+ RoundToString(volume,d)+"\n"
                + "Volume(in cm³): "+ RoundToString(volume * Math.pow(actualToAlgebraRatio, 3),dcm)+"\n"
                + "Riemann Sum volume: "+ RoundToString(rVolume,d)+"\n"
                + "Riemann Sum volume(in cm³): "+ RoundToString(rVolume * Math.pow(actualToAlgebraRatio, 3),dcm)+"\n"
                + "Riemann Sum type: ";
        switch(riemannSumType){
            case(Calculator.LEFT_RIEMANNSUM):
                data += "Left\n"; break;
            case(Calculator.RIGHT_RIEMANNSUM):
                data += "Right\n"; break;
            case(Calculator.MIDDLE_RIEMANNSUM):
                data += "Middle\n"; break;
            default:
                break;
        }       
                
        data += "Limit: "+ RoundToString(lowerLimit,d) + " to "+ RoundToString(upperLimit,d)+"\n"
                + "Actual length(in cm): "+RoundToString(actualLength,dcm) + "\n"
                + "Number of layers: "+ layersNum+"\n"
                + "Step length: " + RoundToString(stepLength,6)+"\n"
                + "Layer's thickness(in cm): " + RoundToString(getLayerThickness(),dcm)+"\n"
                + "Cross section shape: ";
        switch(xSectionType){
            case(Calculator.XSECTION_SQUARE):
                data += "Square\n";break;
            case(Calculator.XSECTION_CIRCLE):
                data += "Circle\n";break;
            case(Calculator.XSECTION_SEMICIRCLE):
                data += "Semi-circle\n";break;
            case(Calculator.XSECTION_EQUILIBRIUM_TRIANGLE):
                data += "Equilibrium triangle\n";break;
            case(Calculator.XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE):
                data += "Right isosceles triangle\n";break;
            default: 
                break; 
        }
        
        d=3;
        for (int i=0;i<layersNum;i++){
            double[] yBoundary = getYBoundary(i);
            double baseLength = yBoundary[0]-yBoundary[1];
            
            data += "\nLayer #"+(i+1)+ "\n";
            data += "x-position: " + RoundToString(getSliceXPos(i),d)+"\n"
                    + "x-position(in cm): " + RoundToString(getSliceActualXPos(i),dcm)+"\n"
                    + "y boundaries: " + RoundToString(yBoundary[1],d)+" to "+RoundToString(yBoundary[0],d)+"\n"
                    + "y boundaries(in cm): " + RoundToString(yBoundary[1]*actualToAlgebraRatio,dcm)
                    +" to "+RoundToString(yBoundary[0]*actualToAlgebraRatio,d)+"\n"
                    + "Base length: " + RoundToString(baseLength,d)+"\n"
                    + "Base length(in cm): " + RoundToString(baseLength * actualToAlgebraRatio,dcm)+"\n";
            
            switch(xSectionType){
                case(Calculator.XSECTION_CIRCLE):
                case(Calculator.XSECTION_SEMICIRCLE):
                    data += "Radius: " + RoundToString(baseLength/2,d)+"\n";
                    data += "Radius(in cm): " + RoundToString(baseLength*actualToAlgebraRatio/2,dcm)+"\n";
                    break;
                case(Calculator.XSECTION_EQUILIBRIUM_TRIANGLE):
                    data += "Height: " + RoundToString(baseLength*Math.sqrt(3)/2,d)+"\n";
                    data += "Height(in cm): " + RoundToString(baseLength*Math.sqrt(3)*actualToAlgebraRatio/2,dcm)+"\n";
                    break;
                case(Calculator.XSECTION_RIGHTISOSCELES_TRIANGLE_HYPOTENUSE):
                    data += "Height: " + RoundToString(baseLength/2,d)+"\n";
                    data += "Height(in cm): " + RoundToString(baseLength*actualToAlgebraRatio/2,dcm)+"\n";
                    break;
                default: break;
                                
            }
            
            data += "Cross section area: " + RoundToString(surfaceArea(i),d)+"\n"
                    + "Cross section area(in cm²): " + RoundToString(surfaceAreaActual(i),dcm)+"\n"
                    + "Layer volume: " + RoundToString(layerVolume(i),d)+"\n"
                    + "Layer volume(in cm³): " + RoundToString(layerVolumeActual(i),dcm)+"\n";
        }
        return data;
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

}