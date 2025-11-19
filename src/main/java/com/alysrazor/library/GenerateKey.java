package com.alysrazor.library;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class GenerateKey {
    public static void main(String[] args) {
        var key = Jwts.SIG.HS512.key().build();
        System.out.println(Encoders.BASE64.encode(key.getEncoded()));
    }
}
