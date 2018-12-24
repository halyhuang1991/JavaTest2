package com.test.spring.Aop;

import org.springframework.stereotype.Component;

@Component("Calculator")
public class CalculatorImpl implements Calculator {
    public double plus(int i, int j) {
        double result = i + j;
        return result;
    }

    public double sub(int i, int j) {
        double result = i - j;
        return result;
    }

    public double multi(int i, int j) {
        double result = i * j;
        return result;

    }

    public double div(int i, int j) {
        double result = i / j;
        return result;

    }
}