package ch.admin.bar.siard2.api.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "candidateKeysType", namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", propOrder = {"candidateKey"})
public class CandidateKeysType
{
  @XmlElement(namespace = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd", required = true)
  protected List<UniqueKeyType> candidateKey;
  
  public List<UniqueKeyType> getCandidateKey() {
    if (this.candidateKey == null) {
      this.candidateKey = new ArrayList<>();
    }
    return this.candidateKey;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\generated\CandidateKeysType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */