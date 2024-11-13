package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum LanguageName {
   ADA(K.ADA.getKeyword()),
   C(K.C.getKeyword()),
   COBOL(K.COBOL.getKeyword()),
   FORTRAN(K.FORTRAN.getKeyword()),
   MUMPS(K.MUMPS.getKeyword()),
   PASCAL(K.PASCAL.getKeyword()),
   PLI(K.PLI.getKeyword()),
   SQL(K.SQL.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private LanguageName(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
