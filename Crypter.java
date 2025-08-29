package SchoolManager;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public interface Crypter {
    
    final String KS_PASSWORD = "KSPass475";
    final String KS_ALIAS = "KeyStore1";
    final String KEY_PASSWORD = "KEYEntPass991";
    final String KEY_ALIAS = "KeyAlias1";
    final String iv_bank_file = "ivb.ser";
    final String ks_file = "keystore.ks";
    
    public void storeKeyStore(); //store key and keystore
    public void loadKeyStore(); //load key and keystore
    
    public void setPlainData(String data);
    
    public void setCipherData(String data);
    
    public void setCrypterKey(SecretKey key); //The SecretKey for encryption and decryption
    
    public SecretKey getCrypterKey();
    
    public IvParameterSpec getIV();
    
    public byte[] getIVBytes();
    
    public void setIVBytes(byte[]iv);
    
    public void changeIV();
    
    
    public byte[] encrypt(String data);
    public byte[] encryptAuth(String data);
    public String encryptEncode(String data);
    
    public String encode64(byte[] str);
    public byte[] decode64(String str);
    
    public String decrypt(byte[] cipher);
    public String decryptAuth(byte[] cipher);
    public String decodeDecrypt(String data);
    
    
    public byte[] getCombined(byte[] data);
    
}