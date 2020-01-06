<https://dom4j.github.io>



### Parsing XML

One of the first things you'll probably want to do is to parse an XML document of some kind. This is easy to do in <dom4j>. The following code demonstrates how to this.

```java
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class Foo {

    public Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }
}

Copy
```

### Using Iterators

A document can be navigated using a variety of methods that return standard Java Iterators. For example

```java
public void bar(Document document) throws DocumentException {

    Element root = document.getRootElement();

    // iterate through child elements of root
    for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
        Element element = it.next();
        // do something
    }

    // iterate through child elements of root with element name "foo"
    for (Iterator<Element> it = root.elementIterator("foo"); it.hasNext();) {
        Element foo = it.next();
        // do something
    }

    // iterate through attributes of root
    for (Iterator<Attribute> it = root.attributeIterator(); it.hasNext();) {
        Attribute attribute = it.next();
        // do something
    }
 }

Copy
```

### Powerful Navigation with XPath

In <dom4j> XPath expressions can be evaluated on the `Document` or on any `Node` in the tree (such as `Attribute`, `Element` or`ProcessingInstruction`). This allows complex navigation throughout the document with a single line of code. For example

```java
public void bar(Document document) {
    List<Node> list = document.selectNodes("//foo/bar");

    Node node = document.selectSingleNode("//foo/bar/author");

    String name = node.valueOf("@name");
}

Copy
```

For example if you wish to find all the hypertext links in an XHTML document the following code would do the trick.

```java
public void findLinks(Document document) throws DocumentException {

    List<Node> list = document.selectNodes("//a/@href");

    for (Iterator<Node> iter = list.iterator(); iter.hasNext();) {
        Attribute attribute = (Attribute) iter.next();
        String url = attribute.getValue();
    }
}

Copy
```

If you need any help learning the XPath language we highly recommend the [Zvon tutorial](http://www.zvon.org/xxl/XPathTutorial/General/examples.html) which allows you to learn by example.

### Fast Looping

If you ever have to walk a large XML document tree then for performance we recommend you use the fast looping method which avoids the cost of creating an `Iterator` object for each loop. For example

```java
public void treeWalk(Document document) {
    treeWalk(document.getRootElement());
}

public void treeWalk(Element element) {
    for (int i = 0, size = element.nodeCount(); i < size; i++) {
        Node node = element.node(i);
        if (node instanceof Element) {
            treeWalk((Element) node);
        }
        else {
            // do something…
        }
    }
}

Copy
```

### Creating a new XML document

Often in <dom4j> you will need to create a new document from scratch. Here's an example of doing that.

```java
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Foo {

    public Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        Element author1 = root.addElement("author")
            .addAttribute("name", "James")
            .addAttribute("location", "UK")
            .addText("James Strachan");

        Element author2 = root.addElement("author")
            .addAttribute("name", "Bob")
            .addAttribute("location", "US")
            .addText("Bob McWhirter");

        return document;
    }
}

Copy
```

### Writing a document to a file

A quick and easy way to write a `Document` (or any `Node`) to a `Writer` is via the `write()` method.

```java
FileWriter out = new FileWriter("foo.xml");
document.write(out);
out.close();

Copy
```

If you want to be able to change the format of the output, such as pretty printing or a compact format, or you want to be able to work with `Writer` objects or `OutputStream` objects as the destination, then you can use the `XMLWriter` class.

```java
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Foo {

    public void write(Document document) throws IOException {

        // lets write to a file
        try (FileWriter fileWiter = new FileWriter("output.xml")) {
            XMLWriter writer = new XMLWriter(fileWriter);
            writer.write( document );
            writer.close();
        }


        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        writer.write( document );

        // Compact format to System.out
        format = OutputFormat.createCompactFormat();
        writer = new XMLWriter(System.out, format);
        writer.write(document);
        writer.close();
    }
}

Copy
```

### Converting to and from Strings

If you have a reference to a `Document` or any other `Node` such as an `Attribute` or `Element`, you can turn it into the default XML text via the `asXML()` method.

```java
Document document = …;
String text = document.asXML();

Copy
```

If you have some XML as a `String` you can parse it back into a `Document` again using the helper method `DocumentHelper.parseText()`

```java
String text = "<person> <name>James</name> </person>";
Document document = DocumentHelper.parseText(text);

Copy
```

### Transforming a `Document` with XSLT

Applying XSLT on a `Document` is quite straightforward using the [JAXP](http://docs.oracle.com/javase/8/docs/technotes/guides/xml/jaxp/index.html) API from Oracle. This allows you to work against any XSLT engine such as [Xalan](https://xalan.apache.org/) or [Saxon](http://www.saxonica.com/). Here is an example of using JAXP to create a transformer and then applying it to a `Document`.

```java
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;

public class Foo {

    public Document styleDocument(Document document, String stylesheet) throws Exception {

        // load the transformer using JAXP
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));

        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);

        // return the transformed document
        Document transformedDoc = result.getDocument();
        return transformedDoc;
    }
}
```