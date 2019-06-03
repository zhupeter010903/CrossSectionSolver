
package xsectionsolver;

import java.util.Objects;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.mathcollection.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;
import org.mariuszgromada.math.mxparser.regressiontesting.*;
import org.mariuszgromada.math.mxparser.syntaxchecker.*;

public class Calculator {
    
    private int MIN_ACTUAL_LENGTH = 20;
    
    private String f;
    private String g;
    private int xSectionType;
    private int layersNum;
    private int upperLimit;
    private int lowerLimit;
    private int actualLength;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.MIN_ACTUAL_LENGTH;
        hash = 13 * hash + Objects.hashCode(this.f);
        hash = 13 * hash + Objects.hashCode(this.g);
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
        if (!Objects.equals(this.f, other.f)) {
            return false;
        }
        if (!Objects.equals(this.g, other.g)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Calculator{" + "MIN_ACTUAL_LENGTH=" + MIN_ACTUAL_LENGTH + ", f=" + f + ", g=" + g + ", xSectionType=" + xSectionType + ", layersNum=" + layersNum + ", upperLimit=" + upperLimit + ", lowerLimit=" + lowerLimit + ", actualLength=" + actualLength + ", riemannSumType=" + riemannSumType + '}';
    }
    private int riemannSumType;

    public Calculator(String f, String g, int xSectionType, int layersNum, int upperLimit, int lowerLimit, int actualLength, int riemannSumType) {
        this.f = f;
        this.g = g;
        this.xSectionType = xSectionType;
        this.layersNum = layersNum;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.actualLength = actualLength;
        this.riemannSumType = riemannSumType;
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

    public void setMIN_ACTUAL_LENGTH(int MIN_ACTUAL_LENGTH) {
        this.MIN_ACTUAL_LENGTH = MIN_ACTUAL_LENGTH;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
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
    
}