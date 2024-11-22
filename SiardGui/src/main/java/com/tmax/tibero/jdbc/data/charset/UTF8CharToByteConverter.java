package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class UTF8CharToByteConverter {
    protected boolean subMode;

    protected byte[] subBytes = new byte[]{63};

    public boolean canConvert(char paramChar) {
        return true;
    }

    public int convCharArr(char[] paramArrayOfchar, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int charOff = 0;
        int byteOff = 0;
        char highHalfZoneCode = Character.MIN_VALUE;
        byte[] arrayOfByte = new byte[6];
        charOff = paramInt1;
        byteOff = paramInt3;
        if (highHalfZoneCode != '\000') {
            highHalfZoneCode = Character.MIN_VALUE;
            if (paramArrayOfchar[paramInt1] >= '\uDC00' && paramArrayOfchar[paramInt1] <= '\uDFFF') {
                int k = (highHalfZoneCode - 55296) * 1024 + paramArrayOfchar[paramInt1] - 56320 + 65536;
                paramArrayOfbyte[0] = (byte) (0xF0 | k >> 18 & 0x7);
                paramArrayOfbyte[1] = (byte) (0x80 | k >> 12 & 0x3F);
                paramArrayOfbyte[2] = (byte) (0x80 | k >> 6 & 0x3F);
                paramArrayOfbyte[3] = (byte) (0x80 | k & 0x3F);
                charOff++;
                highHalfZoneCode = Character.MIN_VALUE;
            } else {
                throw TbError.newSQLException(-590742, paramArrayOfchar[paramInt1]);
            }
        }
        while (charOff < paramInt2) {
            byte b1;
            byte b2;
            char c1 = paramArrayOfchar[charOff];
            if (c1 < '\u0080') {
                arrayOfByte[0] = (byte) c1;
                b1 = 1;
                b2 = 1;
            } else if (c1 < '\u0800') {
                arrayOfByte[0] = (byte) (0xC0 | c1 >> 6 & 0x1F);
                arrayOfByte[1] = (byte) (0x80 | c1 & 0x3F);
                b1 = 1;
                b2 = 2;
            } else if (c1 >= '\uD800' && c1 <= '\uDBFF') {
                if (charOff + 1 >= paramInt2) {
                    highHalfZoneCode = c1;
                    break;
                }
                char c2 = paramArrayOfchar[charOff + 1];
                if (c2 < '\uDC00' || c2 > '\uDFFF')
                    throw TbError.newSQLException(-590742, c2);
                int k = (c1 - 55296) * 1024 + c2 - 56320 + 65536;
                arrayOfByte[0] = (byte) (0xF0 | k >> 18 & 0x7);
                arrayOfByte[1] = (byte) (0x80 | k >> 12 & 0x3F);
                arrayOfByte[2] = (byte) (0x80 | k >> 6 & 0x3F);
                arrayOfByte[3] = (byte) (0x80 | k & 0x3F);
                b2 = 4;
                b1 = 2;
            } else {
                arrayOfByte[0] = (byte) (0xE0 | c1 >> 12 & 0xF);
                arrayOfByte[1] = (byte) (0x80 | c1 >> 6 & 0x3F);
                arrayOfByte[2] = (byte) (0x80 | c1 & 0x3F);
                b1 = 1;
                b2 = 3;
            }
            if (byteOff + b2 > paramInt4)
                throw TbError.newSQLException(-590744, byteOff + " + " + b2 + " > " + paramInt4);
            for (byte b3 = 0; b3 < b2; b3++)
                paramArrayOfbyte[byteOff++] = arrayOfByte[b3];
            charOff += b1;
        }
        return byteOff - paramInt3;
    }

    public int convString(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) throws SQLException {
        int charOff = 0;
        int byteOff = 0;
        char highHalfZoneCode = Character.MIN_VALUE;
        byte[] arrayOfByte = new byte[6];
        charOff = paramInt1;
        byteOff = paramInt3;
        if (highHalfZoneCode != '\000') {
            highHalfZoneCode = Character.MIN_VALUE;
            if (paramString.charAt(paramInt1) >= '\uDC00' && paramString.charAt(paramInt1) <= '\uDFFF') {
                int k = (highHalfZoneCode - 55296) * 1024 + paramString.charAt(paramInt1) - 56320 + 65536;
                paramArrayOfbyte[0] = (byte) (0xF0 | k >> 18 & 0x7);
                paramArrayOfbyte[1] = (byte) (0x80 | k >> 12 & 0x3F);
                paramArrayOfbyte[2] = (byte) (0x80 | k >> 6 & 0x3F);
                paramArrayOfbyte[3] = (byte) (0x80 | k & 0x3F);
                charOff++;
                highHalfZoneCode = Character.MIN_VALUE;
            } else {
                throw TbError.newSQLException(-590742, paramString.charAt(paramInt1));
            }
        }
        while (charOff < paramInt2) {
            byte b1;
            byte b2;
            char c1 = paramString.charAt(charOff);
            if (c1 < '\u0080') {
                arrayOfByte[0] = (byte) c1;
                b1 = 1;
                b2 = 1;
            } else if (c1 < '\u0800') {
                arrayOfByte[0] = (byte) (0xC0 | c1 >> 6 & 0x1F);
                arrayOfByte[1] = (byte) (0x80 | c1 & 0x3F);
                b1 = 1;
                b2 = 2;
            } else if (c1 >= '\uD800' && c1 <= '\uDBFF') {
                if (charOff + 1 >= paramInt2) {
                    highHalfZoneCode = c1;
                    break;
                }
                char c2 = paramString.charAt(charOff + 1);
                if (c2 < '\uDC00' || c2 > '\uDFFF')
                    throw TbError.newSQLException(-590742, c2);
                int k = (c1 - 55296) * 1024 + c2 - 56320 + 65536;
                arrayOfByte[0] = (byte) (0xF0 | k >> 18 & 0x7);
                arrayOfByte[1] = (byte) (0x80 | k >> 12 & 0x3F);
                arrayOfByte[2] = (byte) (0x80 | k >> 6 & 0x3F);
                arrayOfByte[3] = (byte) (0x80 | k & 0x3F);
                b2 = 4;
                b1 = 2;
            } else {
                arrayOfByte[0] = (byte) (0xE0 | c1 >> 12 & 0xF);
                arrayOfByte[1] = (byte) (0x80 | c1 >> 6 & 0x3F);
                arrayOfByte[2] = (byte) (0x80 | c1 & 0x3F);
                b1 = 1;
                b2 = 3;
            }
            if (byteOff + b2 > paramInt4)
                throw TbError.newSQLException(-590744, byteOff + " + " + b2 + " > " + paramInt4);
            for (byte b3 = 0; b3 < b2; b3++)
                paramArrayOfbyte[byteOff++] = arrayOfByte[b3];
            charOff += b1;
        }
        return byteOff - paramInt3;
    }

    public int getMaxBytesPerChar() {
        return 3;
    }
}