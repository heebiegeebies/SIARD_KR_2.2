package ch.admin.bar.siard2.api.generated.old21;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;













































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uniqueKeyType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"name", "description", "column"})
public class UniqueKeyType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd")
  protected String description;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<String> column;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }






















  
  public List<String> getColumn() {
    if (this.column == null) {
      this.column = new ArrayList<>();
    }
    return this.column;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old21\UniqueKeyType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */