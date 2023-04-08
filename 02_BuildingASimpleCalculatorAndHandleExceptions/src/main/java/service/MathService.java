package service;

import exceptions.UnsupportedMathOperationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MathService {


    public Double calcular(String operation, String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || (!isNumeric(numberTwo) && !operation.equals("raizQuadrada"))) {
            notANumber();
        }
        Double numberOneConverted = convertToDouble(numberOne);
        Double numberTwoConverted = convertToDouble(numberTwo);
        switch (operation) {
            case "sum":
                return sum(convertToDouble(numberOne), convertToDouble(numberTwo));
            case "sub":
                return substration(numberOneConverted, numberTwoConverted);
            case "div":
                return division(numberOneConverted, numberTwoConverted);
            case "multi":
                return miltiplication(numberOneConverted, numberTwoConverted);
            case "media":
                return media(numberOneConverted, numberTwoConverted);
            default:
                return squareRoot(numberOneConverted, numberTwoConverted);
        }
    }

    private Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    private Double substration(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    private Double division(Double numberOne, Double numberTwo) {
        return numberOne / numberTwo;
    }

    private Double miltiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    private Double media(Double numberOne, Double numberTwo) {
        return sum(numberOne, numberTwo) / 2;
    }

    private Double squareRoot(Double numberOne, Double numberTwo) {
        return Math.sqrt(numberOne);
    }

    private Double convertToDouble(String strNumber) {
        if (Objects.isNull(strNumber)) {
            return 0D;
        }
        String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if (Objects.isNull(strNumber)) {
            return false;
        }
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[+-]?[0-9]*\\.?[0-9]+");
    }

    private void notANumber() {
        throw new UnsupportedMathOperationException("Please, set a numeric value");
    }
}
