
package xsectionsolver;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class Calculator {
    
    final static private int MIN_ACTUAL_LENGTH = 20;
    
    final static private int XSECTION_SQUARE=0;
    final static private int XSECTION_CIRCLE=1;
    final static private int XSECTION_SEMICIRCLE=2;
    final static private int XSECTION_EQUILIBRIUM_TRIANGLE=3;
    final static private int XSECTION_RIGHT_ISOSCELES_TRIANGLE=4;
    
    final static private int RIGHT_RIEMANNSUM=0;
    final static private int LEFT_RIEMANNSUM=1;
    final static private int MIDDLE_RIEMANNSUM=2;
    
    private String Function1Expression;
    private String Function2Expression;
    private Function Function1;
    private Function Function2;
    private int xSectionType;
    private int layersNum;
    private int upperLimit;
    private int lowerLimit;
    private int actualLength;
    private int riemannSumType;
    private boolean fPieceWise;
    private boolean gPieceWise;
    private double actualToAlgebraRatio;
    
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
        for(Argument[] a:fLimits){
            for(Argument b:a){
                mXparser.consolePrintln("f:"+b.getArgumentValue());
            }
        }
        for(Argument[] a:gLimits){
            for(Argument b:a){
                mXparser.consolePrintln("g:"+b.getArgumentValue());
            }
        }
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
        if(!fPieceWise && !gPieceWise){
        
            Expression volume = new Expression("int((("+Function1.getFunctionExpressionString()
                    +")-("+Function2.getFunctionExpressionString()+"))^2,x,"+lowerLimit+","+upperLimit+")");
            return volume.calculate();
            
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
            
            return volumeSum;
            
        }
        
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

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getActualLength() {
        return actualLength;
    }

    public void setActualLength(int actualLength) {
        this.actualLength = actualLength;
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
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.MIN_ACTUAL_LENGTH;
        hash = 13 * hash + Objects.hashCode(this.Function1Expression);
        hash = 13 * hash + Objects.hashCode(this.Function2Expression);
        hash = 13 * hash + this.xSectionType;
        hash = 13 * hash + this.layersNum;
        hash = 13 * hash + this.upperLimit;
        hash = 13 * hash + this.lowerLimit;
        hash = 13 * hash + this.actualLength;
        hash = 13 * hash + this.riemannSumType;
        return hash;
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