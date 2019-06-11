/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mariuszgromada.math.mxparser;

import java.util.ArrayList;

/**
 *
 * @author zhuxiaoyu
 */
public class PieceWiseFunction extends Function{
    private String functionName;
    private int pieceWiseNumber;
    private Argument[][] pieceWiseLimits;
    
    public PieceWiseFunction(String functionName, String functionDefinitionString, String ArgumentName){
        
        super(functionName, "x", ArgumentName);
        this.removeAllFunctions();
        this.functionName = functionName;
        String functions[] = functionDefinitionString.split(",");
        pieceWiseNumber = functions.length/3;
        pieceWiseLimits = new Argument [pieceWiseNumber][2];
        
        for(int i=0;i<pieceWiseNumber;i++){
            this.defineFunction(functionName, functions[3*i].trim(),ArgumentName);
            pieceWiseLimits[i][0] = new Argument(ArgumentName+"="+functions[3*i+1].trim());
            pieceWiseLimits[i][1] = new Argument(ArgumentName+"="+functions[3*i+2].trim());
        }
        
    }
    
    public Function getFunctionAt(Argument a){
        for(int i=0;i<pieceWiseNumber;i++){
            
            if (a.getArgumentValue()>=pieceWiseLimits[i][0].getArgumentValue()
                    && a.getArgumentValue()<=pieceWiseLimits[i][1].getArgumentValue()){
                return this.getFunction(i);
            }
        }
        return null;
    }
    
    public Function getFunctionAt(double d){
        for(int i=0;i<pieceWiseNumber;i++){
            
            if (d>=pieceWiseLimits[i][0].getArgumentValue()
                    && d<=pieceWiseLimits[i][1].getArgumentValue()){
                return this.getFunction(i);
            }
        }
        return null;
    }
    
    @Override
    public String getFunctionExpressionString(){
        String FunctionExpressionString="\n{ "+this.getFunction(0).getFunctionExpressionString()
                +", ["+pieceWiseLimits[0][0].getArgumentValue()+","+pieceWiseLimits[0][1].getArgumentValue()+"]";
        
        for(int i=1;i<pieceWiseNumber;i++){
            FunctionExpressionString += "\n  " +
                    this.getFunction(i).getFunctionExpressionString()
                    +", ("+pieceWiseLimits[i][0].getArgumentValue()+","+pieceWiseLimits[i][1].getArgumentValue()+"]";
        }
        FunctionExpressionString += " }";
        return FunctionExpressionString;
    }
    
    public String getFunctionExpressionStringAt(Argument a){
        for(int i=0;i<pieceWiseNumber;i++){
            
            if (a.getArgumentValue()>=pieceWiseLimits[i][0].getArgumentValue()
                    && a.getArgumentValue()<=pieceWiseLimits[i][1].getArgumentValue()){
                return this.getFunction(i).getFunctionExpressionString();
            }
        }
        return null;
    }
    
    public String getFunctionExpressionStringAt(double d){
        for(int i=0;i<pieceWiseNumber;i++){
            
            if (d>=pieceWiseLimits[i][0].getArgumentValue()
                    && d<=pieceWiseLimits[i][1].getArgumentValue()){
                return this.getFunction(i).getFunctionExpressionString();
            }
        }
        return null;
    }
    
    @Override
    public double calculate(Argument... arguments){
        Argument a = arguments[0];
        for(int i=0;i<pieceWiseNumber;i++){
            
            if (a.getArgumentValue()>=pieceWiseLimits[i][0].getArgumentValue()
                    && a.getArgumentValue()<=pieceWiseLimits[i][1].getArgumentValue()){
                return this.getFunction(i).calculate(a);
            }
        }
        return Double.NaN;
    }

    public Argument[][] getPieceWiseLimits() {
        return pieceWiseLimits;
    }
    
}
