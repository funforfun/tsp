package ru.mivar.syntax.resource;

import ru.mivar.syntax.sax.ReaderXMLFile;

public class ResourceFactory {
    private static ResourceFactory instance;

    private ResourceFactory() {

    }

    public static ResourceFactory getInstance() {
        if (instance == null) {
            instance = new ResourceFactory();
        }
        return instance;
    }

    public Resource getResource(String path) {
        return (Resource) ReaderXMLFile.readXML(path);
    }
}
