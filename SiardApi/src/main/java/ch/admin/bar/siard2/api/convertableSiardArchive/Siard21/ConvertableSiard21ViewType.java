package ch.admin.bar.siard2.api.convertableSiardArchive.Siard21;

import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22ViewType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.Siard21ToSiard22Transformer;
import ch.admin.bar.siard2.api.generated.old21.ViewType;

public class ConvertableSiard21ViewType
  extends ViewType
{
  public ConvertableSiard21ViewType(ViewType viewType) {
    this.name = viewType.getName();
    this.description = viewType.getDescription();
    this.rows = viewType.getRows();
    this.query = viewType.getQuery();
    this.queryOriginal = viewType.getQueryOriginal();
    this.columns = viewType.getColumns();
  }
  
  public ConvertableSiard22ViewType accept(Siard21ToSiard22Transformer visitor) {
    return visitor.visit(this);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard21\ConvertableSiard21ViewType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */