package ch.enterag.sqlparser.expression.enums;

import ch.enterag.sqlparser.K;

public enum InverseDistributionFunction {
   PERCENTILE_CONT(K.PERCENTILE_CONT.getKeyword()),
   PERCENTILE_DISC(K.PERCENTILE_DISC.getKeyword());

   private String _sKeywords = null;

   public String getKeywords() {
      return this._sKeywords;
   }

   private InverseDistributionFunction(String sKeywords) {
      this._sKeywords = sKeywords;
   }
}
