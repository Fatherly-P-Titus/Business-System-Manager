package SchoolManager;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.security.KeyStore.*;
import java.security.KeyStore.Entry.*;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import javax.security.*;
 
import javax.crypto.spec.IvParameterSpec;

import static java.lang.System.out;
import android.util.Base64;


public class CrypterSymmetricMech implements Crypter {
    
    private String plain_data;
    private byte[] cipher_data;
    
    private SecretKey key;
    
    private IvParameterSpec iv;
    private IvParameterSpec auth_iv;
    
    private byte[ ]iv_bytes ;
    private byte[]auth_iv_bytes;
    
    private final int IV_SIZE = 16;
    
    private IVault vault;
    
    
    private final String db_data_file = "";
    private final String security_config_file = "";
    private final String crypter_ser_file = "";
    private final String keystore_file = "";
    
    private final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private final String PASSWORD = "XZyyAbR52JLpTQYPR5d";
       
     private KeyStore keyStore;
    
    public CrypterSymmetricMech(){
        System.out.println("\nINITIALZING CRYPTER MECH FIELDS....!");

        this.plain_data = "null";
        this.cipher_data = "".getBytes();
        this.iv_bytes = this.auth_iv_bytes = null;
        
        try{
            System.out.println("\nINITIALIZING KEY STORE OBJECT!\n");

            this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            this.keyStore.load(null,KS_PASSWORD.toCharArray());
            
            this.loadKeyStore();
            this.loadIVault();
            
            if((this.vault == null) || (this.vault.getStat() == false)){
                this.vault = new IVault();
                this.generateIV();
                this.generateAuthIV();
            }
            
            this.setIVs();
            this.vault.setStat(true);
            this.storeIVault();
            
            if(this.key == null){
                System.out.println("\nKEYSTORE INSTANCE NOT FOUND!\nGENERATING NEW SECRETKEY!\n");

                this.key = this.generateKey();
                this.storeKeyStore();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public CrypterSymmetricMech(SecretKey key){
        this.plain_data = "null";
        this.cipher_data = "".getBytes();
        this.key = key;
    }
    
    
    // GETTER / SETTER METHODS
    public String getPlainData(){
        return this.plain_data;
    }
    
    public byte[] getCipherData(){
        return this.cipher_data;
    }
    
    public SecretKey getCrypterKey(){
        return this.key;
    }
    
    public IvParameterSpec getIV(){
        return this.iv;
    }
    
    public byte[] getIVBytes(){
        return this.iv_bytes;
    }
    
    public void setPlainData(String data){
        this.plain_data = data;
    }
    
    public void setCipherData(String data){
        this.cipher_data = data.getBytes();
    }
    
    public void setCrypterKey(SecretKey key){
        this.key = key;
    }
    
    public void setIVBytes(byte[] iv_bytes){
        this.iv_bytes = iv_bytes;
        this.iv = new IvParameterSpec(iv_bytes);
    }
    
    public void setAuthIVBytes(byte[] b){
        this.auth_iv = new IvParameterSpec(b);
    }
    
    private void setIVs(){
        this.iv = new IvParameterSpec(this.vault.IVault1());
        this.auth_iv = new IvParameterSpec(this.vault.IVault2());
        System.out.println("IVs Setup !");
    }
    
    ///========================================
    //CRYTPTOGRAPHIC MECH
    private SecretKey generateKey()throws NoSuchAlgorithmException{
        KeyGenerator secretKey = KeyGenerator.getInstance("AES");
        secretKey.init(128);
        return secretKey.generateKey();
    }
    
    private IvParameterSpec generateIV()throws Exception{
        byte[] iv = new byte[this.IV_SIZE];
        
        SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
        this.iv_bytes = iv;
        this.iv = new IvParameterSpec(iv);
        this.vault.setIVault1(iv_bytes);
        return this.iv;
    }
    
    private IvParameterSpec generateAuthIV()throws Exception{
        byte[] iv = new byte[this.IV_SIZE];
        
        SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
        this.auth_iv_bytes = iv;
        this.auth_iv = new IvParameterSpec(iv);
        this.vault.setIVault2(auth_iv_bytes);
        return this.auth_iv;
    }
    
    public void changeIV(){
        try{
            this.generateIV();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public byte[] getCombined(byte[] encrypted_data){
        //JOIN IV WITH ENCRYPYED_DATA
        byte[]iv_bytes = this.getIVBytes();
        byte[] combined = new byte[iv_bytes.length + encrypted_data.length];
        
        System.arraycopy(iv_bytes,0,combined,0,iv_bytes.length);
        System.arraycopy(encrypted_data,0,combined,iv_bytes.length,encrypted_data.length);
        return combined;
    }
    
    public String encode64(byte[] bstr){
        byte[] encrypted_data = bstr;
        String encoded_data = Base64.encodeToString(encrypted_data,Base64.NO_WRAP);
        return encoded_data;
    } 
    
    public byte[] decode64(String str){
        return Base64.decode(str,Base64.NO_WRAP);
    }
     
    
    // ENCRYPTION LOGIC
    public byte[] encrypt(String plaintext) {
        byte[] combined = null;
        
        try{
        Cipher cipher = Cipher.getInstance(this.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, this.key, this.iv);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        
        // Combine IV + encrypted data
         combined = new byte[this.iv_bytes.length 
            + encrypted.length];
        System.arraycopy(this.iv_bytes, 0, combined, 0, this.iv_bytes.length);
        System.arraycopy(encrypted, 0, combined, this.iv_bytes.length, encrypted.length);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Encrypted Array: "+combined.length);
        return combined;
    }
    
    //Encrypt the plaintext and return the encoded cipher 
    public String encryptEncode(String text){
        return this.encode64(this.encrypt(text));
    }
    
    //Decode the encoded ciphertext and return the decryption 
    public String decodeDecrypt(String cipher){
        return this.decrypt(this.decode64(cipher));
    }
    
    
    public byte[] encryptAuth(String plaintext) {
        //byte[] iv = new byte[this.IV_SIZE];
        byte[] combined = null;
        
        try{
        Cipher cipher = Cipher.getInstance(this.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, this.key, this.auth_iv);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        
        // Combine IV + encrypted data
         combined = new byte[this.auth_iv_bytes.length + encrypted.length];
        System.arraycopy(this.auth_iv_bytes, 0, combined, 0, this.auth_iv_bytes.length);
        System.arraycopy(encrypted, 0, combined, this.auth_iv_bytes.length, encrypted.length);
        }catch(Exception e){
            e.printStackTrace();
        }System.out.println("Encrypted Array: "+combined.length);
        return combined;
    }
    

    public String decrypt(byte[] combined) {
        byte[] iv_f = new byte[this.IV_SIZE];
        String decrypted_data = "";
        
        try{
        System.arraycopy(combined, 0, iv_f, 0, this.iv_bytes.length);
        //IvParameterSpec ivSpec = new IvParameterSpec(iv);
        byte[] encrypted = new byte[combined.length - this.IV_SIZE];
        System.arraycopy(combined, this.IV_SIZE, encrypted, 0, encrypted.length);
            
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.key, this.iv);
        byte[] decrypted = cipher.doFinal(encrypted);
        
           decrypted_data = new String(decrypted);
        }catch(Exception e){
          e.printStackTrace();   
        }
        return decrypted_data;
    }
    
    public String decryptAuth(byte[] combined) {
        byte[] iv_f = new byte[this.IV_SIZE];
        
        String decrypted_data = "";
        
        try{
        System.arraycopy(combined, 0, iv_f, 0, this.auth_iv_bytes.length);
        //IvParameterSpec ivSpec = new IvParameterSpec(iv);
        byte[] encrypted = new byte[combined.length - this.IV_SIZE];
        System.arraycopy(combined, this.IV_SIZE, encrypted, 0, encrypted.length);
            
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.key, this.auth_iv);
        byte[] decrypted = cipher.doFinal(encrypted);
            decrypted_data = new String(decrypted);
        }catch(Exception e){
          e.printStackTrace();   
        }
        return decrypted_data;
    }
    
    
    
    
    ///CRYPTER MECH PERSISTER / DEPERSISTER LOGIC
    public void persistCrypterKey(){
        try{
            FileOutputStream fos = new FileOutputStream(crypter_ser_file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.getCrypterKey());
            oos.close();
            System.out.println("CRYPTER SECRET KEY PERSISTED !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void depersistCrypterKey(){
        Object key;
        try{
            FileInputStream fis = new FileInputStream(crypter_ser_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            key = ois.readObject();
            ois.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    private void storeIVault(){
        try{
            FileOutputStream fos = new FileOutputStream(iv_bank_file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.vault);
            out.println("\n\t  [IVAULT SERIALIZED !]\n");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private IVault loadIVault(){
        try{
            FileInputStream fis = new FileInputStream(iv_bank_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            this.vault = (IVault) ois.readObject(); 
            this.iv_bytes = this.vault.IVault1();
            this.auth_iv_bytes = this.vault.IVault2();
            out.println("\n\t  [IVAULT DESERIALIZED !]\n");
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
        return this.vault;
    }
    
    
     // STORING KEY AND KEYSTORE
    public void storeKeyStore(){
        char[] keyPassword = KEY_PASSWORD.toCharArray();
        char[] keyStorePassword = KS_PASSWORD.toCharArray();
        
        System.out.println("\n\tSTORING KEYSTORE INSTANCE !");
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream(ks_file)) {
            //SETTING KEY TO KEYSTORE
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(this.key);
            KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(keyPassword);
            this.keyStore.setEntry(KS_ALIAS, secretKeyEntry, entryPassword);
            
            //STORING KEYSTORE
            //this.keyStore.load(null,keyStorePassword);
            this.keyStore.store(keyStoreOutputStream, keyStorePassword);
            System.out.println("\n\tKEYSTORE INSTANCE STORED!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void loadKeyStore(){
        char[] keyStorePassword = KS_PASSWORD.toCharArray();
        char[] keyPassword = KEY_PASSWORD.toCharArray();
        
        System.out.println("\n\tLOADING KEYSTORE INSTANCE!");
        
        try(InputStream keyStoreData = new FileInputStream(ks_file)){
            //LOADING THE KEYSTORE
            this.keyStore.load(keyStoreData, keyStorePassword);
            
            //FETCHING KEY FROM KEYSTORE
            KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(keyPassword);
            KeyStore.SecretKeyEntry skent = (KeyStore.SecretKeyEntry) this.keyStore.getEntry(KS_ALIAS,entryPassword);
            //KeyStore.Entry keyEntry = keyStore.getEntry(KS_ALIAS, entryPassword);
            
            System.out.println("\nKEYSTORE INSTANCE LOADED!");
            //Creating SecretKey object
            this.key = skent.getSecretKey();
            
            System.out.println("\n\tSECRET KEY ACQUIRED.... !");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}






























