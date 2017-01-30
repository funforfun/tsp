package ru.mivar.syntax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mivar.syntax.resource.Resource;
import ru.mivar.syntax.resource.ResourceFactory;
import ru.mivar.syntax.resource.ResourcesMap;
import ru.mivar.syntax.syntaxGraph.builder.Builder;
import ru.mivar.syntax.syntaxGraph.components.Sentence;
import ru.mivar.syntax.syntaxGraph.components.SyntaxGraph;
import ru.mivar.syntax.vfs.VFSImpl;

import java.util.Iterator;
import java.util.Map;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.info("Start app");
        initResources();
        String testText = "Папа и папа пошел в лес. Он там увидел грибы. Папа и папа пошел в лес. Папа и папа пошел в лес.";
        Map<Sentence, SyntaxGraph> map = Builder.buildPlainGraph(testText, DBService.getConnection());
        LOGGER.info("End app");
    }


    private static void initResources() {
        VFSImpl vfs = new VFSImpl("");
        LOGGER.info("Absolute path to here: " + vfs.getAbsolutePath(""));
        Iterator<String> iterator = vfs.getIterator("./config");
        String path;
        while (iterator.hasNext()) {
            path = iterator.next();
            if (!vfs.isDirectory(path)) {
                System.out.println(path);
                if (path.endsWith(".template")) {
                    continue;
                }
                Resource resource = ResourceFactory.getInstance().getResource(path);
                ResourcesMap.put(resource);
            }
        }
    }

}
