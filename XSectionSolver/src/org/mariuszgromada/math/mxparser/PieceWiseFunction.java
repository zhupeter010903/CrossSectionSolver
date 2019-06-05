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
    private double[][] pieceWiseLimits;

    public PieceWiseFunction(String functionDefinitionString, PrimitiveElement... elements) {
        super(functionDefinitionString, elements);
        //this.
    }
    
    public PieceWiseFunction(String functionName, String functionDefinitionString, String ArgumentName){
        
        super(functionName, "x", ArgumentName);
        this.removeAllFunctions();
        this.functionName = functionName;
        String functions[] = functionDefinitionString.split(",");
        pieceWiseNumber = functions.length/3;
        pieceWiseLimits = new double [pieceWiseNumber][2];
        
        for(int i=0;i<pieceWiseNumber;i++){
            this.defineFunction(functionName, functions[3*i].trim(),ArgumentName);
            pieceWiseLimits[i][0] = Double.parseDouble(functions[3*i+1].trim());
            pieceWiseLimits[i][1] = Double.parseDouble(functions[3*i+2].trim());
        }
        
    }
    
    @Override
    public String getFunctionExpressionString(){
        String FunctionExpressionString=functionName+" = \n{\n";
        FunctionExpressionString += "   " +
                this.getFunction(0).getFunctionExpressionString()
                +", ["+pieceWiseLimits[0][0]+","+pieceWiseLimits[0][1]+"]\n";
        
        for(int i=1;i<pieceWiseNumber;i++){
            FunctionExpressionString += "   " +
                    this.getFunction(i).getFunctionExpressionString()
                    +", ["+pieceWiseLimits[i][0]+","+pieceWiseLimits[i][1]+")\n";
        }
        FunctionExpressionString += "}\n";
        return FunctionExpressionString;
    }
    
    public double calculate(Argument a){
        for(int i=0;i<pieceWiseNumber;i++){
            System.out.println(pieceWiseLimits[i][0]+", "+a.getArgumentExpressionString()+", "+pieceWiseLimits[i][1]);
            if (a.getArgumentValue()>=pieceWiseLimits[i][0]&&a.getArgumentValue()<=pieceWiseLimits[i][1]){
                System.out.println("!");
                return this.getFunction(i).calculate(a);
            }
        }
        return 0.111;
    }

    
    
}
