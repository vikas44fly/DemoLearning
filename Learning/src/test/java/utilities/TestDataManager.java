package utilities;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pageMethods.basePage;

public class TestDataManager extends basePage{
	
  File fXmlFile;
  DocumentBuilderFactory dbFactory;
  javax.xml.parsers.DocumentBuilder dBuilder;
  Document doc;
  
  public TestDataManager()
  {
    try
    {  
      String path=userDir+"/InputData/testdata.xml";	
      System.out.println(path);
      fXmlFile=new File(path);
      dbFactory = DocumentBuilderFactory.newInstance();
      dBuilder = dbFactory.newDocumentBuilder();
      doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public String read_property(String tag, String type, String value) {
    String attrvalue = "";
    NodeList nList = doc.getElementsByTagName(tag);
    for (int temp = 0; temp < nList.getLength(); temp++)
    {
      Node nNode = nList.item(temp);
      if (nNode.getNodeName() == tag)
      {
        Element eElement = (Element)nNode;
        if (eElement.getAttribute("type").equalsIgnoreCase(type))
        {
          attrvalue = eElement.getElementsByTagName(value).item(0).getTextContent();
        }
      }
    }
    return attrvalue;
  }
}
