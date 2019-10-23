package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Cart;
import com.it18zhang.kanglong.service.CartService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CartControllerTest {

    @Autowired
    private CartService cartService;

    @Test
    public void addCart1() {
Cart cart=new Cart();

String token="";
        //cartService.addCart(cart ,token) ;
    }
}