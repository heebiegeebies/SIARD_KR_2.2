package ch.admin.bar.siard2.api.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "viewsType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"view"})
public class ViewsType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<ViewType> view;
  
  public List<ViewType> getView() {
    if (this.view == null) {
      this.view = new ArrayList<>();
    }
    return this.view;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\ViewsType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */