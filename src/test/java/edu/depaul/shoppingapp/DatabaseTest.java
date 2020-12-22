package edu.depaul.shoppingapp;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseTest {
    PurchaseDBO DBO;
    {
        try {
            DBO = new PurchaseDBO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSavePurchase(){
        Purchase mock= mock(Purchase.class);
        when(mock.getCustomerName()).thenReturn("Andy");
        when(mock.getPurchaseDate()).thenReturn(LocalDate.now());
        when(mock.getCost()).thenReturn(20.0);
        when(mock.getShipping()).thenReturn("Standard");
        when(mock.getState()).thenReturn("IL");
        DBO.savePurchase(mock);
        verify(mock).getState();
        verify(mock).getShipping();
        verify(mock).getCost();
        verify(mock).getPurchaseDate();
        verify(mock).getCustomerName();

    }
    @Test
    void testZeroQuantity(){
        PurchaseItem mock= mock(PurchaseItem.class);
        assertThrows(IllegalArgumentException.class, ()-> mock.setQuantity(0));

    }
    @Test
    void testGetPurchase(){
        Purchase mock= mock(Purchase.class);
        when(mock.getCustomerName()).thenReturn("Andy");
        when(mock.getPurchaseDate()).thenReturn(LocalDate.now());
        when(mock.getCost()).thenReturn(20.0);
        when(mock.getShipping()).thenReturn("Standard");
        when(mock.getState()).thenReturn("IL");
        DBO.savePurchase(mock);
        List<Purchase> actual= DBO.getPurchases();
        assertNotNull(actual);
    }
    @Test
    void testGetShipping(){
        Purchase mock= mock(Purchase.class);
        Mockito.doCallRealMethod().when(mock).setShipping("Standard");
        mock.setShipping("Standard");
        Mockito.doCallRealMethod().when(mock).getShipping();
        String actual= mock.getShipping();
        assertEquals(actual,"Standard");
    }
    @Test
    void testGetState(){
        Purchase mock= mock(Purchase.class);
        Mockito.doCallRealMethod().when(mock).setState("IL");
        mock.setState("IL");
        Mockito.doCallRealMethod().when(mock).getState();
        String actual= mock.getState();
        assertEquals(actual,"IL");
    }
    @Test
    void testGetName(){
        Purchase mock= mock(Purchase.class);
        Mockito.doCallRealMethod().when(mock).setCustomerName("Andy");
        mock.setCustomerName("Andy");
        Mockito.doCallRealMethod().when(mock).getCustomerName();
        String actual= mock.getCustomerName();
        assertEquals(actual,"Andy");
    }
    @Test
    void testGetCost(){
        Purchase mock= mock(Purchase.class);
        Mockito.doCallRealMethod().when(mock).setCost(50.0);
        mock.setCost(50.0);
        Mockito.doCallRealMethod().when(mock).getCost();
        double actual= mock.getCost();
        assertEquals(actual,50.0);
    }
    @Test
    void testGetDate(){
        Purchase mock= mock(Purchase.class);
        Mockito.doCallRealMethod().when(mock).setPurchaseDate(LocalDate.now());
        mock.setPurchaseDate(LocalDate.now());
        Mockito.doCallRealMethod().when(mock).getPurchaseDate();
        LocalDate actual= mock.getPurchaseDate();
        assertEquals(actual,LocalDate.now());
    }





}
