package com.example.ruins7.casioms_8l;

/**
 * Created by ruins7 on 2016-09-16.
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateType {

    public String calculate(String number1, String number2, String operation) {
        BigDecimal num1 = new BigDecimal(number1);
        BigDecimal num2 = new BigDecimal(number2);
        BigDecimal hundred = new BigDecimal(100.0);
        BigDecimal result = BigDecimal.valueOf(0);

        switch (operation) {
            case "+":
                result = num1.add(num2);
                break;
            case "-":
                result = num1.subtract(num2);
                break;
            case "*":
                result = num1.multiply(num2);
                break;
            case "/":
                if (num2.intValue() != 0) {
                    result = num1.divide(num2, 20, BigDecimal.ROUND_UP);
                } else {
                    result = BigDecimal.ZERO;
                }
                break;
            case "%":
                result = num1.divide(hundred);
                break;
            case "√":
                //precision:30, HALF_DOWN:Rounding mode where values are rounded towards the nearest neighbor.
                MathContext mc = new MathContext(30, RoundingMode.HALF_DOWN);
                result = new BigDecimal(Math.sqrt(num1.doubleValue()), mc);
                break;
        }
        if (result == BigDecimal.ZERO) {
            return "ERROR";
        } else {
            return result.stripTrailingZeros().toPlainString();//equal value but without zeros
        }

    }
}
