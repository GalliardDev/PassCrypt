package es.exmaster.expass.test;

import es.exmaster.expass.ExPassDAO;
import es.exmaster.expass.common.KeyPairManager;
import es.exmaster.expass.util.RSAUtils;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        PrivateKey pk = null;
        PublicKey pub = null;
        try {
            pk = RSAUtils.privateOfString(ExPassDAO.leerTabla("keys").get(0).split(";")[1]);
            pub = RSAUtils.publicOfString(ExPassDAO.leerTabla("keys").get(0).split(";")[0]);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PrivateKey finalPk = pk;

        System.out.println(ExPassDAO.leerTabla("master").get(0).replace(";1",""));
        try {
            System.out.println(RSAUtils.encrypt("ositovito", pub));
            System.out.println(RSAUtils.decrypt(ExPassDAO.leerTabla("master").get(0).replace(";1",""), pk));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
