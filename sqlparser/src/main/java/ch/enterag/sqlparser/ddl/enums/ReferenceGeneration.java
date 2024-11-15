package ch.enterag.sqlparser.ddl.enums;

import ch.enterag.sqlparser.K;

public enum ReferenceGeneration {
   SYSTEM_GENERATED(K.SYSTEM.getKeyword() + " " + K.GENERATED.getKeyword()),
   USER_GENERATED(K.USER.getKeyword() + " " + K.GENERATED.getKeyword()),
   DERIVED(K.DERIVED.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private ReferenceGeneration(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}