package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public abstract class DOMDeserializer<T> extends FromStringDeserializer<T> {
    private static final DocumentBuilderFactory _parserFactory;
    private static final long serialVersionUID = 1;

    public static class DocumentDeserializer extends DOMDeserializer<Document> {
        private static final long serialVersionUID = 1;

        public DocumentDeserializer() {
            super(Document.class);
        }

        public Document _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return parse(value);
        }
    }

    public static class NodeDeserializer extends DOMDeserializer<Node> {
        private static final long serialVersionUID = 1;

        public NodeDeserializer() {
            super(Node.class);
        }

        public Node _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return parse(value);
        }
    }

    public abstract T _deserialize(String str, DeserializationContext deserializationContext);

    static {
        _parserFactory = DocumentBuilderFactory.newInstance();
        _parserFactory.setNamespaceAware(true);
    }

    protected DOMDeserializer(Class<T> cls) {
        super(cls);
    }

    protected final Document parse(String value) throws IllegalArgumentException {
        try {
            return _parserFactory.newDocumentBuilder().parse(new InputSource(new StringReader(value)));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON String as XML: " + e.getMessage(), e);
        }
    }
}
