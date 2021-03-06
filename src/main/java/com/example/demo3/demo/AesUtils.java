package com.example.demo3.demo;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.io.DefaultSerializer;
import org.apache.shiro.io.Serializer;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtils {

    private static final Logger log = LoggerFactory.getLogger(AbstractRememberMeManager.class);
    private static final byte[] DEFAULT_CIPHER_KEY_BYTES = Base64.decode("kPH+bIxk5D2deZiIxcaaaA==");
    private Serializer<PrincipalCollection> serializer = new DefaultSerializer();
    private CipherService cipherService = new AesCipherService();
    private byte[] encryptionCipherKey;
    private byte[] decryptionCipherKey;

    public AesUtils() {
        this.setCipherKey(DEFAULT_CIPHER_KEY_BYTES);
    }

    public Serializer<PrincipalCollection> getSerializer() {
        return this.serializer;
    }

    public CipherService getCipherService() {
        return this.cipherService;
    }

    public byte[] getEncryptionCipherKey() {
        return this.encryptionCipherKey;
    }

    public void setEncryptionCipherKey(byte[] encryptionCipherKey) {
        this.encryptionCipherKey = encryptionCipherKey;
    }

    public byte[] getDecryptionCipherKey() {
        return this.decryptionCipherKey;
    }

    public void setDecryptionCipherKey(byte[] decryptionCipherKey) {
        this.decryptionCipherKey = decryptionCipherKey;
    }

    public byte[] getCipherKey() {
        return this.getEncryptionCipherKey();
    }

    public void setCipherKey(byte[] cipherKey) {
        this.setEncryptionCipherKey(cipherKey);
        this.setDecryptionCipherKey(cipherKey);
    }

    protected byte[] convertPrincipalsToBytes(PrincipalCollection principals) {
        byte[] bytes = this.serialize(principals);
        if (this.getCipherService() != null) {
            bytes = this.encrypt(bytes);
        }

        return bytes;
    }

    protected PrincipalCollection convertBytesToPrincipals(byte[] bytes, SubjectContext subjectContext) {
        if (this.getCipherService() != null) {
            bytes = this.decrypt(bytes);
        }

        return this.deserialize(bytes);
    }



    protected byte[] encrypt(byte[] serialized) {
        byte[] value = serialized;
        CipherService cipherService = this.getCipherService();
        if (cipherService != null) {
            ByteSource byteSource = cipherService.encrypt(serialized, this.getEncryptionCipherKey());
            value = byteSource.getBytes();
        }

        return value;
    }

    protected byte[] decrypt(byte[] encrypted) {
        byte[] serialized = encrypted;
        CipherService cipherService = this.getCipherService();
        if (cipherService != null) {
            ByteSource byteSource = cipherService.decrypt(encrypted, this.getDecryptionCipherKey());
            serialized = byteSource.getBytes();
        }

        return serialized;
    }

    protected byte[] serialize(PrincipalCollection principals) {
        return this.getSerializer().serialize(principals);
    }

    protected PrincipalCollection deserialize(byte[] serializedIdentity) {
        return (PrincipalCollection)this.getSerializer().deserialize(serializedIdentity);
    }
}
