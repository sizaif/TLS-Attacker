/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.workflow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.StringUtils;

public class WorkflowTraceSchemaGenerator {

    private static final String ROOT_NS = "";

    private static final String NO_NS = "__NO__NS";

    public static void main(String[] args) {
        try {
            new File(args[0]).mkdirs();
            generateSchema(args[0]);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void generateSchema(String path) throws IOException, JAXBException {
        AccumulatingSchemaOutputResolver sor = new AccumulatingSchemaOutputResolver();
        WorkflowTraceSerializer.getJAXBContext().generateSchema(sor);
        for (Entry<String, StringWriter> e : sor.getSchemaWriters().entrySet()) {
            String systemId = sor.getSystemIds().get(e.getKey());
            FileWriter w = null;
            try {
                File f = new File(path, systemId);
                w = new FileWriter(f);
                System.out.println(String.format("Writing %s to %s", e.getKey(), f.getAbsolutePath()));
                w.write(e.getValue().toString());
            } finally {
                if (w != null) {
                    w.close();
                }
            }
        }
    }

    public static class AccumulatingSchemaOutputResolver extends SchemaOutputResolver {

        private final Map<String, StringWriter> schemaWriters = new HashMap<>();
        private final Map<String, String> systemIds = new HashMap<>();

        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            String ns = StringUtils.isBlank(namespaceURI) ? NO_NS : namespaceURI;
            schemaWriters.put(ns, new StringWriter());
            String systemId = mapSystemIds();
            systemIds.put(ns, systemId);
            StreamResult result = new StreamResult(schemaWriters.get(ns));
            result.setSystemId(systemId);
            return result;
        }

        public static String mapSystemIds() {
            return "workflowTrace.xsd";
        }

        public Map<String, StringWriter> getSchemaWriters() {
            return schemaWriters;
        }

        public Map<String, String> getSystemIds() {
            return systemIds;
        }
    }

}
