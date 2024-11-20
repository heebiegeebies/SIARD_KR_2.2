package com.tmax.tibero.jdbc.data.charset;

import com.tmax.tibero.jdbc.err.TbError;

import java.sql.SQLException;

public class MS1251ByteToCharConverter {
    protected boolean subMode = true;

    protected char[] subChars = new char[]{'?'};

    private static final char[] MS1251_TO_UNICODE_PAGE = new char[]{
            '\u0402', '\u0403', '\u201A', '\u0453', '\u201E', '\u2026', '\u2020', '\u2021', '\u20AC', '\u2030',
            '\u0409', '\u2039', '\u040A', '\u040C', '\u040B', '\u040F', '\u0452', '\u2018', '\u2019', '\u201C',
            '\u201D', '\u2022', '\u2013', '\u2014', '\uFFFD', '\u2122', '\u0459', '\u203A', '\u045A', '\u045C',
            '\u045B', '\u045F', '\u00A0', '\u040E', '\u045E', '\u0408', '\u00A4', '\u0490', '\u00A6', '\u00A7',
            '\u0401', '\u00A9', '\u0404', '\u00AB', '\u00AC', '\u00AD', '\u00AE', '\u0407', '\u00B0', '\u00B1',
            '\u0406', '\u0456', '\u0491', '\u00B5', '\u00B6', '\u00B7', '\u0451', '\u2116', '\u0454', '\u00BB',
            '\u0458', '\u0405', '\u0455', '\u0457', '\u0410', '\u0411', '\u0412', '\u0413', '\u0414', '\u0415',
            '\u0416', '\u0417', '\u0418', '\u0419', '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F',
            '\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426', '\u0427', '\u0428', '\u0429',
            '\u042A', '\u042B', '\u042C', '\u042D', '\u042E', '\u042F', '\u0430', '\u0431', '\u0432', '\u0433',
            '\u0434', '\u0435', '\u0436', '\u0437', '\u0438', '\u0439', '\u043A', '\u043B', '\u043C', '\u043D',
            '\u043E', '\u043F', '\u0440', '\u0441', '\u0442', '\u0443', '\u0444', '\u0445', '\u0446', '\u0447',
            '\u0448', '\u0449', '\u044A', '\u044B', '\u044C', '\u044D', '\u044E', '\u044F'};

    private int decodeFromUcs(byte paramByte1, byte paramByte2) {
        return (paramByte1 << 8) + (paramByte2 & 0xFF);
    }

    private void encodeUCharToUCS2(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
        byte high = 0;
        byte low = (byte) paramInt2;
        paramArrayOfchar[paramInt1] = (char) high;
        paramArrayOfchar[paramInt1++] = (char) low;
    }

    public int convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) throws SQLException {
        int i = paramInt1;
        int j = paramInt3;
        while (i < paramInt2) {
            int k = decodeFromUcs((byte) 0, paramArrayOfbyte[i]);
            if (k < 128) {
                paramArrayOfchar[j++] = (char) k;
                i++;
                continue;
            }
            if (k == 152) {
                paramArrayOfchar[j++] = '\u0098';
                i++;
                continue;
            }
            int m = k - 128;
            k = MS1251_TO_UNICODE_PAGE[m];
            if (k != 65533) {
                paramArrayOfchar[j++] = (char) k;
                i++;
                continue;
            }
            if (this.subMode) {
                encodeUCharToUCS2(paramArrayOfchar, j, this.subChars[0]);
                j++;
                continue;
            }
            throw TbError.newSQLException(-590742, k);
        }
        return j - paramInt3;
    }
}