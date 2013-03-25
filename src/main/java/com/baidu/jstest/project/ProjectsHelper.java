package com.baidu.jstest.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.baidu.jstest.Main;
import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.browser.BrowserHolderFactory;
import com.baidu.jstest.browser.DeclaredBrowser;
import com.baidu.jstest.browser.invoker.BrowserInvoker;
import com.baidu.jstest.dependence.TangramDependStrategy;
import com.baidu.jstest.exception.ParseException;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.utils.FileUtil;
import com.baidu.jstest.utils.PathUtil;

/**
 * XML解析的SAX封装
 * 
 * @author dailiqi
 */
public class ProjectsHelper {
//	private Projects projects;
	private Project project;
	private File buildFile;
	private File buildFileParent;
	private XMLReader parser;
	public static final String HELPER_PROPERTY = "com.baidu.jstest.ProjectsHelper";

	public static void configureProjects(File buildFile)
			throws TestException {
		ProjectsHelper helper = ProjectsHelper.getProjectHelper();
		helper.parse(buildFile);
	}

	private void parse(File buildFile) throws TestException {
		FileInputStream inputStream = null;
		InputSource inputSource = null;

//		this.projects = projects;

		this.buildFile = new File(buildFile.getAbsolutePath());
		this.buildFileParent = new File(this.buildFile.getParent());

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser().getXMLReader();
			String uri = "file:"
					+ buildFile.getAbsolutePath().replace('\\', '/');
			for (int index = uri.indexOf('#'); index != -1; index = uri
					.indexOf('#')) {
				uri = uri.substring(0, index) + "%23"
						+ uri.substring(index + 1);
			}

			inputStream = new FileInputStream(buildFile);
			inputSource = new InputSource(inputStream);
			inputSource.setSystemId(uri);
			RootHandler handler = new RootHandler(this);
			parser.setContentHandler(handler);
			parser.setDTDHandler(handler);
			parser.setErrorHandler(handler);
			parser.parse(inputSource);
//			System.out.println("parsed projects size = " + Main.projects.size());
		} catch (SAXParseException exc) {
			throw new TestException(exc);
		} catch (SAXException exc) {
			throw new TestException(exc);
		} catch (FileNotFoundException exc) {
			throw new TestException(exc);
		} catch (ParserConfigurationException e) {
			throw new TestException(e);
		} catch (IOException e) {
			throw new TestException(e);
		}
	}

	private static ProjectsHelper getProjectHelper() throws TestException {
		// Identify the class loader we will be using. Ant may be
		// in a webapp or embeded in a different app
		ProjectsHelper helper = new ProjectsHelper();

		// // First, try the system property
		// String helperClass = HELPER_PROPERTY;
		// try {
		// if (helperClass != null) {
		// helper = newHelper(helperClass);
		// }
		// } catch (SecurityException e) {
		// System.out.println("Unable to load ProjectHelper class \""
		// + helperClass + " specified in system property "
		// + HELPER_PROPERTY);
		// }
		return helper;
	}

	/** Default constructor */
	public ProjectsHelper() {
	}

	// private static ProjectsHelper newHelper(String helperClass)
	// throws TestException {
	// ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	// try {
	// Class clazz = null;
	// if (classLoader != null) {
	// try {
	// clazz = classLoader.loadClass(helperClass);
	// } catch (ClassNotFoundException ex) {
	// // try next method
	// }
	// }
	// if (clazz == null) {
	// clazz = Class.forName(helperClass);
	// }
	// return ((ProjectsHelper) clazz.newInstance());
	// } catch (Exception e) {
	// throw new TestException(e);
	// }
	// }

	private static void handleElement(ProjectsHelper helper,
			ContentHandler parent, String qName, Attributes attributes)
			throws ParseException {
		if (qName.equals("project")) {
			new ProjectHandler(helper, parent).init(qName, attributes);
		} else if (qName.equals("env") || qName.equals("global-env")) {
			new EnvHandler(helper, parent).init(qName, attributes);
		} else if (qName.equals("property")) {
			new PropertyHandler(helper, parent).init(qName, attributes);
		} else if (qName.equals("browser")) {
			new BrowserHandler(helper, parent).init(qName, attributes);
		} else if (qName.equals("invoker")) {
			new InvokerHandler(helper, parent).init(qName, attributes);
		} else if (qName.equals("resources")) {
			new ResourcesHandler(helper, parent).init(qName, attributes);
		} else if (qName.equals("resource")) {
			new ResourceHandler(helper, parent).init(qName, attributes);
		}
	}

	/**
	 * The common superclass for all SAX event handlers used to parse the
	 * configuration file. Each method just throws an exception, so subclasses
	 * should override what they can handle.
	 * 
	 * Each type of XML Kiss (task, target, etc.) in Ant has a specific
	 * subclass.
	 * 
	 * In the constructor, this class takes over the handling of SAX events from
	 * the parent handler and returns control back to the parent in the
	 * endElement method.
	 */
	abstract static class AbstractHandler extends DefaultHandler {
		public abstract void init(String qName, Attributes attributes)
				throws ParseException;

		/**
		 * Previous handler for the document. When the next Kiss is finished,
		 * control returns to this handler.
		 */
		protected ContentHandler parentHandler;

		/**
		 * Helper impl. With non-static internal classes, the compiler will
		 * generate this automatically - but this will fail with some compilers
		 * ( reporting "Expecting to find object/array on stack" ). If we pass
		 * it explicitely it'll work with more compilers.
		 */
		ProjectsHelper helper;

		/**
		 * Creates a handler and sets the parser to use it for the current Kiss.
		 * 
		 * @param helper
		 *            the ProjectHelper instance associated with this handler.
		 * 
		 * @param parentHandler
		 *            The handler which should be restored to the parser at the
		 *            end of the Kiss. Must not be <code>null</code>.
		 */
		public AbstractHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			this.helper = helper;
			this.parentHandler = parentHandler;
			helper.parser.setContentHandler(this);
		}

		/**
		 * Handles text within an Kiss. This base implementation just throws an
		 * exception.
		 * 
		 * @param buf
		 *            A character array of the text within the Kiss. Will not be
		 *            <code>null</code>.
		 * @param start
		 *            The start Kiss in the array.
		 * @param count
		 *            The number of characters to read from the array.
		 * 
		 * @exception SAXParseException
		 *                if this method is not overridden, or in case of error
		 *                in an overridden version
		 */
		@Override
		public void characters(char[] buf, int start, int count)
				throws SAXParseException {
			String s = new String(buf, start, count).trim();
			if (s.length() > 0) {
				throw new SAXParseException("Unexpected text \"" + s + "\"",
						null);
			}
		}

		/**
		 * Handles the end of an Kiss. Any required clean-up is performed by the
		 * finished() method and then the original handler is restored to the
		 * parser.
		 * 
		 * @param name
		 *            The name of the Kiss which is ending. Will not be
		 *            <code>null</code>.
		 * 
		 * @exception SAXException
		 *                in case of error (not thrown in this implementation)
		 * 
		 * @see #finished()
		 */
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			helper.parser.setContentHandler(parentHandler);
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			try {
				handleElement(helper, this, qName, atts);
			} catch (ParseException e) {
				throw new SAXException(e);
			}
		}
	}

	/**
	 * Handler for the top level "projects" Kiss.
	 */
	static class RootHandler extends DefaultHandler {
		ProjectsHelper helper;

		public RootHandler(ProjectsHelper helper) {
			this.helper = helper;
		}

		/**
		 * Resolves file: URIs relative to the build file.
		 * 
		 * @param publicId
		 *            The public identifer, or <code>null</code> if none is
		 *            available. Ignored in this implementation.
		 * @param systemId
		 *            The system identifier provided in the XML document. Will
		 *            not be <code>null</code>.
		 */
		public InputSource resolveEntity(String publicId, String systemId) {

			// helperImpl.project.log("resolving systemId: " + systemId,
			// Project.MSG_VERBOSE);

			if (systemId.startsWith("file:")) {
				String path = systemId.substring(5);
				int index = path.indexOf("file:");

				// we only have to handle these for backward compatibility
				// since they are in the FAQ.
				while (index != -1) {
					path = path.substring(0, index) + path.substring(index + 5);
					index = path.indexOf("file:");
				}

				String entitySystemId = path;
				index = path.indexOf("%23");
				// convert these to #
				while (index != -1) {
					path = path.substring(0, index) + "#"
							+ path.substring(index + 3);
					index = path.indexOf("%23");
				}

				File file = new File(path);
				if (!file.isAbsolute()) {
					file = new File(helper.buildFileParent, path);
				}

				try {
					InputSource inputSource = new InputSource(
							new FileInputStream(file));
					inputSource.setSystemId("file:" + entitySystemId);
					return inputSource;
				} catch (FileNotFoundException fne) {
					// helperImpl.project.log(file.getAbsolutePath() +
					// " could not be found",
					// Project.MSG_WARN);
				}
			}
			// use default if not file or file not found
			return null;
		}

		@Override
		public void startDocument() throws SAXException {
		}

		/**
		 * Handles the start of a project Kiss. A project handler is created and
		 * initialised with the Kiss name and attributes.
		 * 
		 * @param tag
		 *            The name of the Kiss being started. Will not be
		 *            <code>null</code>.
		 * @param attrs
		 *            Attributes of the Kiss being started. Will not be
		 *            <code>null</code>.
		 * 
		 * @exception SAXParseException
		 *                if the tag given is not <code>"project"</code>
		 * @throws TestException
		 */
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) {
			if (qName.equals("projects")) {
				new ProjectsHandler(helper, this).init(qName, attributes);
			}
		}

	}

	/**
	 * 项目组信息不需要解析
	 * 
	 * @author dailiqi
	 */
	static class ProjectsHandler extends AbstractHandler {

		public ProjectsHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);

		}

		@Override
		public void init(String qName, Attributes attributes) {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			try {
				handleElement(helper, this, qName, atts);
			} catch (ParseException e) {
				throw new SAXException(e);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	static class ProjectHandler extends AbstractHandler {

		/**
		 * Constructor which just delegates to the superconstructor.
		 * 
		 * @param parentHandler
		 *            The handler which should be restored to the parser at the
		 *            end of the Element. Must not be <code>null</code>.
		 */
		public ProjectHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		public void init(String qName, Attributes attributes)
				throws ParseException {
			String name = null;
			helper.project = new Project();
			// 可扩展
			helper.project.setDs(TangramDependStrategy.class);
			for (int i = 0; i < attributes.getLength(); i++) {
				String key = attributes.getQName(i);
				String value = attributes.getValue(i);

				if (key.equals("name")) {
					name = value;
				} else if (key.equals("depends")) {
					/**
					 * 追加对多项目的支持，使用,分隔
					 */
					for (String v : value.split(",")) {
						Project dependProj = Main.projects.getEntity(v);
						helper.project.setDependProject(dependProj);
					}
				} else if (key.equals("include")) {
					String path = value;
					if (!path.startsWith("/") && !path.contains(":")) {
						String root = Main.getDefineXML().substring(0,
								Main.getDefineXML().indexOf("define.xml"));
						try {
							path = FileUtil.normalize(root + path);
						} catch (TestException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (FileUtil.existsFile(path)) {
						try {
							helper.parse(new File(path));
						} catch (TestException e) {
							e.printStackTrace();
						}
					} else {
						if (path.contains("*")) {
							String folderPath = path.substring(0,
									path.lastIndexOf("*"));
							File file = new File(folderPath);
							if (file.isDirectory()) {
								File[] files = file.listFiles();
								for (int j = 0; j < files.length; j++) {
									File tmp = files[j];
									try {
										helper.parse(tmp);
									} catch (TestException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			if (null != name && !"".equals(name)) {
				helper.project.setName(name);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			try {
				Main.projects.addEntity(helper.project);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	static class EnvHandler extends AbstractHandler {

		public EnvHandler(ProjectsHelper helper, ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		@Override
		public void init(String qName, Attributes attributes)
				throws ParseException {
			if (qName.equals("env")) {
				// TODO Auto-generated method stub
				Env env = new Env();
				helper.project.setEnv(env);
			} else if (qName.equals("global-env")) {
				Env env = new Env();
				Main.projects.setEnv(env);
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			try {
				handleElement(helper, this, qName, atts);
			} catch (ParseException e) {
				throw new SAXException(e);
			}
		}
	}

	static class BrowserHandler extends AbstractHandler {

		public BrowserHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void init(String qName, Attributes attributes)
				throws ParseException {
			DeclaredBrowser browser = null;
			String invoker = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String key = attributes.getQName(i);
				String value = attributes.getValue(i);

				if (key.equals("name")) {
					browser = new DeclaredBrowser(value);
				} else if (key.equals("path")) {
					browser.setPath(value);
				} else if (key.equals("host")) {
					browser.setHost(value);
				} else if (key.equals("invoker")) {
					invoker = Main.projects.getInvoker(value);
				} else if (key.equals("ref")) {
					browser = Main.projects.getEnv().getBrowserHolder(value)
							.declaredBrowser();
					if (null == browser)
						throw new ParseException("can't found ref for name "
								+ value + ".");
				}
			}
			// 当前是解析porjects，将BrowserHolder加入到总体环境中
			if (null == helper.project) {
				BrowserHolder instance = BrowserHolderFactory
						.getBrowserHolderInstance(browser.getName());
				try {
					instance.setInvoker((Class<BrowserInvoker>) Thread
							.currentThread().getClass().forName(invoker));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
				instance.setDeclaredBrowser(browser);
				Main.projects.getEnv().addBrowserHolder(instance);
			}
			// 对当前项目环境进行注册
			// helper.projects.getEnv().addBrowserHolder(browser);
			else {
				helper.project.getEnv().addBrowserHolder(
						Main.projects.getEnv().getBrowserHolder(
								browser.getName()));
				// helper.project.getEnv().addBrowserHolder(browser);
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			try {
				handleElement(helper, this, qName, atts);
			} catch (ParseException e) {
				throw new SAXException(e);
			}
		}
	}

	static class PropertyHandler extends AbstractHandler {

		public PropertyHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		@Override
		public void init(String qName, Attributes attributes) {
			String name = "";
			String path = "";
			String depends = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String key = attributes.getQName(i);
				String value = attributes.getValue(i);

				if (key.equals("name")) {
					name = value;
				} else if (key.equals("value")) {
					path = value;
				} else if (key.equals("depends")) {
					depends = value;
				}
			}
			if (null != name && !"".equals(name))
				try {
					// depends的是项目，去取得项目内容，然后解析
					if (!"".equals(depends)) {
						path = PathUtil.converse(
								Main.projects.getEntity(depends), path);
					} else {
						path = PathUtil.converse(helper.project, path);
					}

				} catch (ParseException e) {
					System.out.println(e);
				}
			helper.project.setProperties(name, PathUtil.separator(path));
			if (name.equals(XMLConstants.BASE_DIR)) {
				helper.project.setBasedir(PathUtil.separator(path));
			} else if (name.equals(XMLConstants.SRC_DIR)) {
				helper.project.setSrcdir(PathUtil.separator(path));
			} else if (name.equals(XMLConstants.TEST_DIR)) {
				helper.project.setTestdir(PathUtil.separator(path));
			}
		}
	}

	static class ResourcesHandler extends AbstractHandler {

		public ResourcesHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);

		}

		@Override
		public void init(String qName, Attributes attributes) {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			try {
				handleElement(helper, this, qName, atts);
			} catch (ParseException e) {
				throw new SAXException(e);
			}
		}
	}

	static class ResourceHandler extends AbstractHandler {

		public ResourceHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		@Override
		public void init(String qName, Attributes attributes) {
			String path = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String key = attributes.getQName(i);
				String value = attributes.getValue(i);
				if (key.equals("value")) {
					path = value;
				}
			}
			if (!"".equals(path)) {
				try {
					path = PathUtil.converse(helper.project, path);
				} catch (ParseException e) {
					System.out.println(e);
				}
				helper.project.addResource(path);
			}
		}
	}

	static class InvokerHandler extends AbstractHandler {

		public InvokerHandler(ProjectsHelper helper,
				ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		@Override
		public void init(String qName, Attributes attributes) {
			String name = "";
			String clazz = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String key = attributes.getQName(i);
				String value = attributes.getValue(i);

				if (key.equals("name")) {
					name = value;
				} else if (key.equals("class")) {
					clazz = value;
				}
			}
			Main.projects.addInvoker(name, clazz);
		}
	}

	
	
	
	static class SuiteHandler extends AbstractHandler {

		public SuiteHandler(ProjectsHelper helper, ContentHandler parentHandler) {
			super(helper, parentHandler);
		}

		@Override
		public void init(String qName, Attributes attributes) {
			String name = "";
			String src = "";
			String testsrc = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String key = attributes.getQName(i);
				String value = attributes.getValue(i);

				if (key.equals("name")) {
					name = value;
				} else if (key.equals("src")) {
					src = value;
				} else if (key.equals("test")) {
					testsrc = value;
				}
			}
			if (null != name && !"".equals(name)) {
				try {
					src = PathUtil.converse(helper.project, src);
					testsrc = PathUtil.converse(helper.project, testsrc);
				} catch (ParseException e) {
					System.out.println(e);
				}
			}
			Suite suite = SuiteFactory.getSuite(name, src, testsrc);
			try {
				helper.project.addChild(suite);
			} catch (TestException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			// helper.project.setProperties(qName, value)
		}

	}
	
	
	
	
}
