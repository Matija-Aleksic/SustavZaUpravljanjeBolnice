package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for logging user actions to XML
 */
public class XmlLogger {
    private static final Logger logger = LoggerFactory.getLogger(XmlLogger.class);
    private static final String LOG_FILE = "user_actions.xml";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private XmlLogger() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Log a user action to XML file
     *
     * @param action      the action name
     * @param description the action description
     */
    public static void logAction(String action, String description) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            Element root;

            File file = new File(LOG_FILE);
            if (file.exists()) {
                doc = builder.parse(file);
                root = doc.getDocumentElement();
            } else {
                doc = builder.newDocument();
                root = doc.createElement("UserActions");
                doc.appendChild(root);
            }

            Element actionElement = doc.createElement("Action");

            Element timestampElement = doc.createElement("Timestamp");
            timestampElement.setTextContent(LocalDateTime.now().format(formatter));
            actionElement.appendChild(timestampElement);

            Element nameElement = doc.createElement("Name");
            nameElement.setTextContent(action);
            actionElement.appendChild(nameElement);

            Element descElement = doc.createElement("Description");
            descElement.setTextContent(description);
            actionElement.appendChild(descElement);

            root.appendChild(actionElement);

            writeXmlToFile(doc, LOG_FILE);
            logger.debug("Action logged to XML: {}", action);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            logger.error("Error logging action to XML", e);
        }
    }

    /**
     * Display all logged actions without XML tags
     */
    public static void displayLogs() {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                System.out.println("No logs found.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList actionNodes = doc.getElementsByTagName("Action");
            System.out.println("\n=== User Action Log ===");
            System.out.println("Total actions: " + actionNodes.getLength());
            System.out.println();

            for (int i = 0; i < actionNodes.getLength(); i++) {
                Node actionNode = actionNodes.item(i);
                if (actionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element actionElement = (Element) actionNode;

                    String timestamp = getElementText(actionElement, "Timestamp");
                    String name = getElementText(actionElement, "Name");
                    String description = getElementText(actionElement, "Description");

                    System.out.printf("[%s] %s: %s%n", timestamp, name, description);
                }
            }
        } catch (Exception e) {
            logger.error("Error displaying XML logs", e);
            System.out.println("Error reading logs: " + e.getMessage());
        }
    }

    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    private static void writeXmlToFile(Document doc, String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            logger.error("Error writing XML to file", e);
        }
    }
}

