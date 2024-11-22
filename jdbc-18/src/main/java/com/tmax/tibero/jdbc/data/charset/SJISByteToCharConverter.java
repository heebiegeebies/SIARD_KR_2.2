package com.tmax.tibero.jdbc.data.charset;

import java.sql.SQLException;

public class SJISByteToCharConverter extends JIS0208ByteToCharConverter {
    private JIS0201ByteToCharConverter JIS0201bc = new JIS0201ByteToCharConverter();

    protected char convSingleByte(int paramInt) {
        return ((paramInt & 0xFF80) == 0) ? (char) paramInt : this.JIS0201bc.getUnicode(paramInt);
    }

    protected char getUnicode(int paramInt1, int paramInt2) throws SQLException {
        int b1 = (paramInt2 >= 159) ? 0 : 1;
        char b2 = (paramInt1 >= 160) ? '\u00B0' : 'p';
        byte b3 = (b1 != 1) ? 126 : (byte) ((paramInt2 <= 127) ? 31 : 32);
        int i = (paramInt1 - b2 << 1) - b1;
        int j = paramInt2 - b3;
        return super.getUnicode(i, j);
    }
}