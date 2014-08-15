package fr.pinguet62.dictionary;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.jpa.AvailableSettings;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pinguet62.dictionary.dao.AbstractDao;
import fr.pinguet62.dictionary.dao.LanguageDao;
import fr.pinguet62.dictionary.model.Language;

public final class TestBdUnit extends DatabaseTestCase {

    /** The DAO of {@link Language}. */
    private final LanguageDao dao = new LanguageDao();

    /**
     * Read the configuration file {@code /META-INF/persistence.xml} to
     * initialize the
     */
    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        // Read persistence.xml file
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputStream persistenceXml = getClass().getResourceAsStream(
                "/META-INF/persistence.xml");
        Document doc = dBuilder.parse(persistenceXml);
        doc.getDocumentElement().normalize();

        NodeList persistenceUnitNodes = doc
                .getElementsByTagName("persistence-unit");
        // <persistence-unit ...>
        NodeList persisenceUnitNodes = IntStream
                .range(0, persistenceUnitNodes.getLength())
                .mapToObj(i -> persistenceUnitNodes.item(i))
                .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                .map(node -> (Element) node)
                .filter(element -> element.getAttribute("name").equals("test"))
                .findAny().get().getChildNodes();
        // <properties>
        NodeList propertiesChildNodes = IntStream
                .range(0, persisenceUnitNodes.getLength())
                .mapToObj(i -> persisenceUnitNodes.item(i))
                .filter(node -> node.getNodeName().equals("properties"))
                .findAny().get().getChildNodes();
        // <property ...>
        Map<String, String> properties = IntStream
                .range(0, propertiesChildNodes.getLength())
                .mapToObj(i -> propertiesChildNodes.item(i))
                .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                .map(node -> (Element) node)
                .collect(
                        Collectors.toMap(
                                property -> property.getAttribute("name"),
                                property -> property.getAttribute("value")));

        // Connection to database
        Connection jdbcConnection = DriverManager.getConnection(
                properties.get(AvailableSettings.JDBC_URL),
                properties.get(AvailableSettings.JDBC_USER),
                properties.get(AvailableSettings.JDBC_PASSWORD));
        return new DatabaseConnection(jdbcConnection);
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        FlatXmlDataSetBuilder xmlDSBuilder = new FlatXmlDataSetBuilder();
        xmlDSBuilder.setCaseSensitiveTableNames(true);
        InputStream inputStreamXml = getClass().getResourceAsStream(
                "/dataset.xml");
        return xmlDSBuilder.build(inputStreamXml);
    }

    /** Test for {@link AbstractDao#count()}. */
    @Test
    public void testCount() {
        assertEquals(3, dao.count());
    }

    /** Test for {@link AbstractDao#create(Object)}. */
    @Test
    public void testCreate() {
        assertEquals(3, dao.count());
        dao.create(new Language("es", "Español"));
        assertEquals(4, dao.count());
    }

    /** Test for {@link AbstractDao#delete(Object)}. */
    @Test
    public void testDelete() {
        assertEquals(3, dao.count());
        dao.delete(dao.get("fr"));
        assertEquals(2, dao.count());
        dao.delete(dao.get("en"));
        assertEquals(1, dao.count());
        dao.delete(dao.get("ar"));
        assertEquals(0, dao.list().size());
    }

    /** Test for {@link AbstractDao#deleteAll())}. */
    @Test
    public void testDeleteAll() {
        assertEquals(3, dao.count());
        dao.deleteAll();
        assertEquals(0, dao.count());
    }

    /** Test for {@link AbstractDao#get(Serializable)}. */
    @Test
    public void testGet() {
        assertEquals("Français", dao.get("fr").getName());
        assertNull(dao.get("  "));
    }

    /** Test for {@link AbstractDao#list()}. */
    @Test
    public void testList() {
        List<Language> languages = dao.list();
        assertEquals(3, languages.size());
        assertEquals(
                "Français",
                languages.stream()
                        .filter(language -> "fr".equals(language.getCode()))
                        .findFirst().get().getName());
        assertEquals(
                "English",
                languages.stream()
                        .filter(language -> "en".equals(language.getCode()))
                        .findFirst().get().getName());
        assertEquals(
                "العربية",
                languages.stream()
                        .filter(language -> "ar".equals(language.getCode()))
                        .findFirst().get().getName());
    }

    /** Test for {@link AbstractDao#update(Object)}. */
    @Test
    public void testUpdate() {
        // action
        Language language = dao.get("fr");
        language.setName("frensé");
        dao.update(language);

        // test
        assertEquals("frensé", dao.get("fr").getName());
    }

}