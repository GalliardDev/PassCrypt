package dev.galliard.passcrypt.common;

import dev.galliard.passcrypt.database.PassCryptDAO;
import dev.galliard.passcrypt.util.ExLogger;
import dev.galliard.passcrypt.util.RSAUtils;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class KeyPairManager {
    public static KeyPair keyPair;

    public KeyPairManager() {
        try {
            keyPair = RSAUtils.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public static void saveKeysBase64() {
        if(!PassCryptDAO.leerTabla("keys").isEmpty()) {
            PassCryptDAO.limpiarTabla("keys");
        }
        PassCryptDAO.agregarDatos("keys", new String[] {
                RSAUtils.publicToBase64(keyPair.getPublic()),
                RSAUtils.privateToBase64(keyPair.getPrivate())
        });
    }

    public static void decryptOldThenEncryptNew() {
        PrivateKey pk = null;
        try {
           pk = RSAUtils.privateOfString(PassCryptDAO.leerTabla("keys").get(0).split(";")[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PrivateKey finalPk = pk;


        // CONTRASEÑA MAESTRA
        List<String> auxMaster = new ArrayList<>();
        List<String> lsMaster = PassCryptDAO.leerTabla("master");
        lsMaster.stream().map(s-> {
            try {
                return RSAUtils.decrypt(s, finalPk);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).map(s -> {
            try {
                return RSAUtils.encrypt(s, keyPair.getPublic());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(auxMaster::add);
        try {
            PassCryptDAO.limpiarTabla("master");
            PassCryptDAO.agregarDatos("master", new String[] {auxMaster.get(0)});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // RESTO DE CONTRASEÑAS
        List<String> aux = new ArrayList<>();
        List<String> ls = PassCryptDAO.leerTabla("passwords").stream().map(s -> s.split(";")[2]).toList();

        ls.stream().map(s-> {
            try {
                return RSAUtils.decrypt(s, finalPk);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).map(s -> {
            try {
                return RSAUtils.encrypt(s, keyPair.getPublic());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(aux::add);

        int regs = 0;
        for(int id = 1; id < ls.size(); id++) {
            PassCryptDAO.update("passwords", id, aux.get(id-1));
            regs = id;
        }
        PassCryptDAO.update("passwords", ls.size(), aux.get(ls.size()-1));
        new ExLogger(KeyPairManager.class).success("Se han actualizado " + Math.addExact(regs, 1) + " registros.");
    }
}
