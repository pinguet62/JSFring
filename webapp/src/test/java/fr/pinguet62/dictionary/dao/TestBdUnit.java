package fr.pinguet62.dictionary.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pinguet62.dictionary.model.Language;

public final class TestBdUnit extends DatabaseTestCase {

	@BeforeClass
	public static void beforeClass() throws Exception {
		Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/dictionary", "root", "AZE123qsd");
		new DatabaseConnection(jdbcConnection);
	}

	/** The DAO of {@link Language}. */
	private final LanguageDao dao = new LanguageDao();

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		String url = null;
		String user = null;
		String password = null;

		// Read persistence.xml file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputStream persistenceXml = getClass().getResourceAsStream(
				"/META-INF/persistence.xml");
		Document doc = dBuilder.parse(persistenceXml);
		doc.getDocumentElement().normalize();

		NodeList persisenceUnitNodeList = doc
				.getElementsByTagName("persistence-unit").item(0)
				.getChildNodes();
		for (int i = 0; i < persisenceUnitNodeList.getLength(); i++) {
			Node propertiesNode = persisenceUnitNodeList.item(i);
			if (!"properties".equals(propertiesNode.getNodeName()))
				continue;

			NodeList propertiesNodeList = propertiesNode.getChildNodes();
			for (int j = 0; j < propertiesNodeList.getLength(); j++) {
				Node propertyNode = propertiesNodeList.item(j);
				if (propertyNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element propertyElement = (Element) propertyNode;

				switch (propertyElement.getAttribute("name")) {
					case "javax.persistence.jdbc.url":
						url = propertyElement.getAttribute("value");
						break;
					case "javax.persistence.jdbc.user":
						user = propertyElement.getAttribute("value");
						break;
					case "javax.persistence.jdbc.password":
						password = propertyElement.getAttribute("value");
						break;
				}
			}
		}

		// bdunit connection
		Connection jdbcConnection = DriverManager.getConnection(url, user,
				password);
		return new DatabaseConnection(jdbcConnection);
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		FlatXmlDataSetBuilder xmlDSBuilder = new FlatXmlDataSetBuilder();
		xmlDSBuilder.setCaseSensitiveTableNames(true); // not case sensitive
		InputStream inputStreamXML = TestBdUnit.class
				.getResourceAsStream("/dataset.xml");
		return xmlDSBuilder.build(inputStreamXML);
	}

	@Test
	public void testCount() {
		assertEquals(3, dao.count());
	}

	@Test
	public void testCreate() {
		assertEquals(3, dao.count());
		dao.create(new Language("es", "Español"));
		assertEquals(4, dao.count());
	}

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

	@Test
	public void testDeleteAll() {
		assertEquals(3, dao.count());
		dao.deleteAll();
		assertEquals(0, dao.count());
	}

	@Test
	public void testGet() {
		assertEquals("Français", dao.get("fr").getName());
		assertNull(dao.get("  "));
	}

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