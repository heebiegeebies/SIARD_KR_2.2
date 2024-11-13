package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum Deterministic {
   DETERMINISTIC(K.DETERMINISTIC.getKeyword()),
   NOT_DETERMINISTIC(K.NOT.getKeyword() + " " + K.DETERMINISTIC.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private Deterministic(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
