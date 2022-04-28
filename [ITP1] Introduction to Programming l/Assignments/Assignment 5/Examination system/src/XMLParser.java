import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private static final String FILENAME = "input.xml";
    DocumentBuilderFactory dbf;

    public XMLParser() {
        dbf = DocumentBuilderFactory.newInstance();
    }

    public List<Question> readInput() {
        List<Question> questions = new ArrayList<>();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("question");

            for (int index = 0; index < list.getLength(); index++) {

                Node node = list.item(index);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String questiontype = element.getAttribute("type");
                    QuestionFactory factory = new QuestionFactory();
                    questions.add(factory.createQuestion(questiontype, element));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}