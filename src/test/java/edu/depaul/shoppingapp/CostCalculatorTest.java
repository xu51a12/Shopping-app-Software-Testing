package edu.depaul.shoppingapp;

import static org.junit.jupiter.api.Assertions.*;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CostCalculatorTest {
    private TotalCostCalculator TotalCostCalculator;
    ShoppingCart cart= new ShoppingCart();
    @BeforeEach
    void setup(){
        PurchaseItem d = new PurchaseItem("test", 50.00, 1);
        cart.addItem(d);
    }

    @ParameterizedTest
    @MethodSource("CostProvider")
    void testZeroCost(double initialCost, String state, ShippingType shipping){
        double total= TotalCostCalculator.calculate(initialCost, state, shipping);
        double expected= 0.0;
        assertEquals(expected,total);
    }
    public static Stream<Arguments>CostProvider(){
        return Stream.of(
                Arguments.of(0, "IL", ShippingType.NEXT_DAY),
                Arguments.of(0, "IL", ShippingType.STANDARD),
                Arguments.of(0, "AL", ShippingType.NEXT_DAY),
                Arguments.of(0, "AL", ShippingType.STANDARD)
        );
    }
    @ParameterizedTest
    @MethodSource("CostProvider2")
    void testNegCost(double initialCost, String state, ShippingType shipping){
        assertThrows(IllegalArgumentException.class,() ->TotalCostCalculator.calculate(initialCost, state, shipping));

    }
    public static Stream<Arguments>CostProvider2(){
        return Stream.of(
                Arguments.of(-1, "IL", ShippingType.NEXT_DAY),
                Arguments.of(-1, "IL", ShippingType.STANDARD),
                Arguments.of(-1, "AL", ShippingType.NEXT_DAY),
                Arguments.of(-1, "AL", ShippingType.STANDARD)
        );
    }
    @ParameterizedTest
    @MethodSource("CostProvider3")
    void testUnder50(double initialCost, String state, ShippingType shipping){
        double total= TotalCostCalculator.calculate(initialCost, state, shipping);
        Double expected;
        if ((state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.NEXT_DAY){
            expected= 27.56;

        }
        else if ((state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.STANDARD){
            expected= 11.66;

        }
        else if (!(state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.NEXT_DAY){
            expected= 26.0;

        }
        else if (!(state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.STANDARD){
            expected= 11.0;
        }
        else{
            expected= null;
        }
        assertEquals(total,expected);

    }
    public static Stream<Arguments>CostProvider3(){
        return Stream.of(
                Arguments.of(1.0, "IL", ShippingType.NEXT_DAY),
                Arguments.of(1.0, "IL", ShippingType.STANDARD),
                Arguments.of(1.0, "CA", ShippingType.STANDARD),
                Arguments.of(1.0, "NY", ShippingType.STANDARD),
                Arguments.of(1.0, "AL", ShippingType.NEXT_DAY),
                Arguments.of(1.0, "AL", ShippingType.STANDARD)
        );
    }
    @ParameterizedTest
    @MethodSource("CostProvider4")
    void test50(double initialCost, String state, ShippingType shipping){
        double total= TotalCostCalculator.calculate(initialCost, state, shipping);
        Double expected;
        if ((state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.NEXT_DAY){
            expected= 79.5;

        }
        else if ((state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.STANDARD){
            expected= 63.6;

        }
        else if (!(state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.NEXT_DAY){
            expected= 75.0;

        }
        else if (!(state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.STANDARD){
            expected= 60.0;
        }
        else{
            expected= null;
        }
        assertEquals(total,expected);

    }
    public static Stream<Arguments>CostProvider4(){
        return Stream.of(
                Arguments.of(50.0, "IL", ShippingType.NEXT_DAY),
                Arguments.of(50.0, "IL", ShippingType.STANDARD),
                Arguments.of(50.0, "CA", ShippingType.STANDARD),
                Arguments.of(50.0, "NY", ShippingType.STANDARD),
                Arguments.of(50.0, "AL", ShippingType.NEXT_DAY),
                Arguments.of(50.0, "AL", ShippingType.STANDARD)
        );
    }
    @ParameterizedTest
    @MethodSource("CostProvider5")
    void testOver50(double initialCost, String state, ShippingType shipping){
        double total= TotalCostCalculator.calculate(initialCost, state, shipping);
        Double expected;
        if ((state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.NEXT_DAY){
            expected= 80.56;

        }
        else if ((state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.STANDARD){
            expected= 54.06;

        }
        else if (!(state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.NEXT_DAY){
            expected= 76.0;

        }
        else if (!(state.equals("IL")||state.equals("NY")||state.equals("CA")) && shipping == ShippingType.STANDARD){
            expected= 51.0;
        }
        else{
            expected= null;
        }
        assertEquals(total,expected);

    }
    public static Stream<Arguments>CostProvider5(){
        return Stream.of(
                Arguments.of(51.0, "IL", ShippingType.NEXT_DAY),
                Arguments.of(51.0, "IL", ShippingType.STANDARD),
                Arguments.of(51.0, "AL", ShippingType.NEXT_DAY),
                Arguments.of(51.0, "AL", ShippingType.STANDARD)
        );
    }
    @Test
    void testInvalidState(){
        assertThrows(IllegalArgumentException.class, ()-> TotalCostCalculator.calculate(50.0, "ddd", ShippingType.STANDARD));


    }

    @Test
    void testReturnBillTotal(){
        Bill bill= TotalCostCalculator.calculate(cart, "IL",ShippingType.NEXT_DAY);
        double expected= 79.5;
        assertEquals(expected,bill.getTotal());
    }
    @Test
    void testReturnBillTax(){
        Bill bill= TotalCostCalculator.calculate(cart, "IL",ShippingType.NEXT_DAY);
        double expected=4.5;
        assertEquals(expected,bill.getTax());
    }
    @Test
    void testReturnBillShipping(){
        Bill bill= TotalCostCalculator.calculate(cart, "IL",ShippingType.NEXT_DAY);
        double expected= 25;
        assertEquals(expected,bill.getShipping());
    }
    @Test
    void testReturnBillInitialCost(){
        Bill bill= TotalCostCalculator.calculate(cart, "IL",ShippingType.NEXT_DAY);
        double expected= 50;
        assertEquals(expected,bill.getInitialCost());
    }
    @Test
    void testCartClear(){
        cart.clear();
        assertEquals(cart.itemCount(),0);
    }
    @Test
    void testCartCount(){
        int actual= cart.itemCount();
        assertEquals(actual,1);
    }



}
