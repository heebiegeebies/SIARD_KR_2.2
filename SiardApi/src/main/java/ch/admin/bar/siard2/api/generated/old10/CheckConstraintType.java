package ch.admin.bar.siard2.api.generated.old10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;















































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkConstraintType", namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", propOrder = {"name", "condition", "description"})
public class CheckConstraintType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String name;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd", required = true)
  protected String condition;
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/1.0/metadata.xsd")
  protected String description;
  
  public String getName() {
    return this.name;
  }








  
  public void setName(String value) {
    this.name = value;
  }








  
  public String getCondition() {
    return this.condition;
  }








  
  public void setCondition(String value) {
    this.condition = value;
  }








  
  public String getDescription() {
    return this.description;
  }








  
  public void setDescription(String value) {
    this.description = value;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\old10\CheckConstraintType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */