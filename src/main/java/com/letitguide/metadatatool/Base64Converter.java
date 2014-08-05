package com.letitguide.metadatatool;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class Base64Converter {
    private static byte[] asByteArray(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private static UUID toUUID(byte[] byteArray) {
        ByteBuffer bb = ByteBuffer.wrap(byteArray);
        Long mostSignificantBits = bb.getLong();
        Long leastSignificantBits = bb.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public static String UUIDToBase64(UUID uuid) throws UnsupportedEncodingException {
        Base64 b64 = new Base64();
        return new String(b64.encode(asByteArray(uuid)), "UTF8").replace('/', '-').replace('+', '_').replace("=", "");
    }

    public static UUID Base64ToUUID(String base64) throws UnsupportedEncodingException {
        base64 = base64.replace('-', '/').replace('_', '+') + "==";
        Base64 b64 = new Base64();
        return toUUID(b64.decode(base64));
    }
}
