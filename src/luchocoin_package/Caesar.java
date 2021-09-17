package luchocoin_package;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class Caesar {

    public static String sha256(String input) {

        try {
            MessageDigest digest= MessageDigest.getInstance("SHA-256");
            // Applies sha256 to our input,
            byte[] hash= digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString= new StringBuffer(); // This will contain hash as hexidecimal
            for (int i= 0; i < hash.length; i++ ) {
                String hex= Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Signs a message (in this case, our txHash)
     */
    public static byte[] applyRSA(PrivateKey privateKey, String input) {
        Signature rsa;
        byte[] output= new byte[0];
        try {

            rsa= Signature.getInstance("RSA");
            rsa.initSign(privateKey);
            byte[] strByte= input.getBytes();
            rsa.update(strByte);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
        return output;
    }

    /*
     * Verifies whether if our signature is true or not
     */
    public static boolean verifyRSA(PublicKey publicKey, String input, byte[] signature) {

        try {

            Signature rsaVerify= Signature.getInstance("RSA");
            rsaVerify.initVerify(publicKey);
            rsaVerify.update(input.getBytes());
            return rsaVerify.verify(signature);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    public static String getStringFromKey(Key key) {

        return Base64.getEncoder().encodeToString(key.getEncoded());

    }

}