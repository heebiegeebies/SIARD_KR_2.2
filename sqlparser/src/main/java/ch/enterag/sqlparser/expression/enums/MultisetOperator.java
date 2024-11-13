package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum MultisetOperator {
   MULTISET_INTERSECT(K.MULTISET.getKeyword() + " " + K.INTERSECT.getKeyword()),
   MULTISET_UNION(K.MULTISET.getKeyword() + " " + K.UNION.getKeyword()),
   MULTISET_EXCEPT(K.MULTISET.getKeyword() + " " + K.EXCEPT.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private MultisetOperator(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
