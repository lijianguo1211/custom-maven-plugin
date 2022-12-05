package xyz.lglg.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jay.li
 * @Title: HelpMojo
 * @Package xyz.lglg.maven.plugin
 * @Description: 帮助文件
 * @date 2022/12/2
 */
@Mojo(name = "help")
public class HelpMojo extends CustomAbstractMojo {
    private static final String PLUGIN_HELP_PATH = "/META-INF/maven/xyz.lglg.plugin/custom-maven-plugin/plugin-help.xml";

    private Document build() throws MojoExecutionException {
        this.getLog().debug("load plugin-help.xml: " + PLUGIN_HELP_PATH);
        InputStream is = null;

        Document document;

        try {
            is = this.getClass().getResourceAsStream(PLUGIN_HELP_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(is);
        } catch (IOException | ParserConfigurationException | SAXException err) {
            throw new MojoExecutionException(err.getMessage(), err);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioException) {
                    throw new MojoExecutionException(ioException.getMessage(), ioException);
                }
            }

        }

        return document;
    }

    private Node getSingleChild(Node node, String elementName) throws MojoExecutionException {
        List<Node> namedChild = this.findNamedChild(node, elementName);
        if (namedChild.isEmpty()) {
            throw new MojoExecutionException("Could not find " + elementName + " in plugin-help.xml");
        } else if (namedChild.size() > 1) {
            throw new MojoExecutionException("Multiple " + elementName + " in plugin-help.xml");
        } else {
            return (Node)namedChild.get(0);
        }
    }

    private List<Node> findNamedChild(Node node, String elementName) {
        List<Node> result = new ArrayList<>();
        NodeList childNodes = node.getChildNodes();

        for(int i = 0; i < childNodes.getLength(); ++i) {
            Node item = childNodes.item(i);
            if (elementName.equals(item.getNodeName())) {
                result.add(item);
            }
        }

        return result;
    }

    private Node findSingleChild(Node node, String elementName) throws MojoExecutionException {
        List<Node> elementsByTagName = this.findNamedChild(node, elementName);
        if (elementsByTagName.isEmpty()) {
            return null;
        } else if (elementsByTagName.size() > 1) {
            throw new MojoExecutionException("Multiple " + elementName + "in plugin-help.xml");
        } else {
            return (Node)elementsByTagName.get(0);
        }
    }

    private String getValue(Node node, String elementName) throws MojoExecutionException {
        return this.getSingleChild(node, elementName).getTextContent();
    }

    private static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        this.getLog().info("help~");

        Document doc = this.build();
        StringBuilder sb = new StringBuilder();
        Node plugin = this.getSingleChild(doc, "plugin");
        String name = this.getValue(plugin, "name");
        String version = this.getValue(plugin, "version");
        String id = this.getValue(plugin, "groupId") + ":" + this.getValue(plugin, "artifactId") + ":" + version;
        if (isNotEmpty(name) && !name.contains(id)) {
            sb.append(name).append(" ").append(version);
        } else if (isNotEmpty(name)) {
            sb.append(name);
        } else {
            sb.append(id);
        }

        sb.append("\n").append(this.getValue(plugin, "description"));

        String goalPrefix = this.getValue(plugin, "goalPrefix");
        Node mojos = this.getSingleChild(plugin, "mojos");
        List<Node> mojoList = this.findNamedChild(mojos, "mojo");

        for (Node node : mojoList) {
            String mojoGoal = this.getValue((Element)node, "goal");

            sb.append(goalPrefix).append(":").append(mojoGoal).append("\n");

            Node description = this.findSingleChild((Element)node, "description");

            if (description != null) {
                sb.append(description.getTextContent()).append("\n");
            }
        }

        getLog().info(sb);
    }
}
