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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * XML Logger for user actions. Logs actions to user_actions.xml in UTF-8 encoding.
 * Uses secure XML features and robust exception handling. Method names preserved for compatibility.
 */
public class XmlLogger {
    private static final Logger logger = LoggerFactory.getLogger(XmlLogger.class);
    private static final String LOG_FILE = "user_actions.xml";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String ACTION_TAG = "Action";
    private static final String TIMESTAMP_TAG = "Timestamp";
    private static final String NAME_TAG = "Name";
    private static final String DESCRIPTION_TAG = "Description";
    private static final String USER_ACTIONS_TAG = "UserActions";

    private XmlLogger() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Log action to XML file.
     * @param action      the action name
     * @param description the action description
     */
    public static void logAction(String action, String description) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            Element root;
            File file = new File(LOG_FILE);
            if (file.exists()) {
                doc = builder.parse(file);
                root = doc.getDocumentElement();
            } else {
                doc = builder.newDocument();
                root = doc.createElement(USER_ACTIONS_TAG);
                doc.appendChild(root);
            }
            Element actionElement = doc.createElement(ACTION_TAG);
            Element timestampElement = doc.createElement(TIMESTAMP_TAG);
            timestampElement.setTextContent(LocalDateTime.now().format(formatter));
            actionElement.appendChild(timestampElement);
            Element nameElement = doc.createElement(NAME_TAG);
            nameElement.setTextContent(action);
            actionElement.appendChild(nameElement);
            Element descElement = doc.createElement(DESCRIPTION_TAG);
            descElement.setTextContent(description);
            actionElement.appendChild(descElement);
            root.appendChild(actionElement);
            writeXmlToFile(doc);
            logger.debug("Action logged to XML: {}", action);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            logger.error("Error logging action to XML", e);
        }
    }

    /**
     * Display logs in the logger output.
     */
    public static void displayLogs() {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                logger.info("No logs found.");
                return;
            }
            Document doc = safeParseXmlFile(file);
            if (doc == null) return;
            NodeList actionNodes = doc.getElementsByTagName(ACTION_TAG);
            logger.info("=== User Action Log ===");
            logger.info("Total actions: {}", actionNodes.getLength());
            for (int i = 0; i < actionNodes.getLength(); i++) {
                Node actionNode = actionNodes.item(i);
                if (actionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element actionElement = (Element) actionNode;
                    String timestamp = getElementText(actionElement, TIMESTAMP_TAG);
                    String name = getElementText(actionElement, NAME_TAG);
                    String description = getElementText(actionElement, DESCRIPTION_TAG);
                    logger.info("[{}] {}: {}", timestamp, name, description);
                }
            }
        } catch (Exception e) {
            logger.error("Error displaying XML logs", e);
        }
    }

    /**
     * Get log entries for UI display as a list of maps.
     * @return list of maps with log fields
     */
    public static List<Map<String, String>> getLogEntries() {
        List<Map<String, String>> entries = new java.util.ArrayList<>();
        File file = new File(LOG_FILE);
        if (!file.exists()) return entries;
        Document doc;
        try {
            doc = parseXmlFile(file);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            logger.error("Error reading XML logs", e);
            return entries;
        }
        NodeList actionNodes = doc.getElementsByTagName(ACTION_TAG);
        for (int i = 0; i < actionNodes.getLength(); i++) {
            Node actionNode = actionNodes.item(i);
            if (actionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element actionElement = (Element) actionNode;
                String timestamp = getElementText(actionElement, TIMESTAMP_TAG);
                String name = getElementText(actionElement, NAME_TAG);
                String description = getElementText(actionElement, DESCRIPTION_TAG);
                Map<String, String> logMap = new java.util.HashMap<>();
                logMap.put("timestamp", timestamp);
                logMap.put("name", name);
                logMap.put("description", description);
                entries.add(logMap);
            }
        }
        return entries;
    }

    /**
     * Delete all logs (removes XML file).
     */
    public static void deleteLogs() {
        File file = new File(LOG_FILE);
        try {
            if (file.exists()) Files.delete(Path.of(LOG_FILE));
        } catch (IOException e) {
            logger.error("Error deleting XML logs", e);
        }
    }

    // Secure XML parsing
    private static Document parseXmlFile(File file) throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    private static Document safeParseXmlFile(File file) {
        try {
            return parseXmlFile(file);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            logger.error("Error reading XML logs", e);
            return null;
        }
    }

    // Get text content of a tag
    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    // Write XML to file with UTF-8 encoding and indentation
    private static void writeXmlToFile(Document doc) {
        try (FileOutputStream fos = new FileOutputStream(LOG_FILE)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            logger.error("Error writing XML to file", e);
        }
    }
}
