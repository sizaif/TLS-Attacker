/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.socket;

import de.rub.nds.tlsattacker.core.connection.InboundConnection;
import de.rub.nds.tlsattacker.transport.TransportHandlerType;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class InboundConnectionTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private StringWriter writer;
    private JAXBContext context;
    private Marshaller m;
    private Unmarshaller um;

    @Before
    public void setUp() throws JAXBException {
        writer = new StringWriter();
        context = JAXBContext.newInstance(TestXmlRoot.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    @Test
    public void marshalingAndUnmarshalingEmptyObjectYieldsEqualObject() throws Exception {

        TestXmlRoot expected = new TestXmlRoot();

        m.marshal(expected, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        TestXmlRoot actual = (TestXmlRoot) um.unmarshal(new StringReader(xmlString));

        assertEquals(expected, actual);
        assertNotSame(expected, actual);
    }

    @Test
    public void marshalingEmptyActionYieldsMinimalOutput() throws Exception {

        TestXmlRoot expected = new TestXmlRoot();

        m.marshal(expected, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        assertEquals("<testXmlRoot/>", xmlString.split("\\n")[1]);

        Unmarshaller um = context.createUnmarshaller();
        TestXmlRoot actual = (TestXmlRoot) um.unmarshal(new StringReader(xmlString));

        assertEquals(expected, actual);
        assertNotSame(expected, actual);
    }

    /**
     * Verify that the ServerConnectionEnd includes manually set values in serialization output.
     *
     * @throws Exception
     */
    @Test
    public void testSerializeNonDefaultFields() throws Exception {

        TestXmlRoot expected = new TestXmlRoot();
        expected.setAlias("TestMe");
        expected.setPort(4444);

        m.marshal(expected, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        sb.append("<testXmlRoot>\n");
        sb.append("    <alias>TestMe</alias>\n");
        sb.append("    <port>4444</port>\n");
        sb.append("</testXmlRoot>\n");
        assertEquals(sb.toString(), xmlString);

        Unmarshaller um = context.createUnmarshaller();
        TestXmlRoot actual = (TestXmlRoot) um.unmarshal(new StringReader(xmlString));

        assertEquals(expected, actual);
        assertNotSame(expected, actual);
    }

    @Test
    public void mixInDefaultsFromReference() throws Exception {

        TestXmlRoot con = new TestXmlRoot();
        InboundConnection defaultCon = new InboundConnection();

        defaultCon.setPort(9772);
        defaultCon.setTimeout(2300);
        defaultCon.setTransportHandlerType(TransportHandlerType.EAP_TLS);
        defaultCon.setHostname("testDefaultHost");
        defaultCon.setAlias("testDefaultAlias");

        assertNull(con.getTimeout());
        assertNull(con.getTransportHandlerType());
        assertNull(con.getHostname());
        assertNull(con.getAlias());
        assertNull(con.getPort());

        con.normalize(defaultCon);
        assertThat(con.getTimeout(), equalTo(2300));
        assertThat(con.getTransportHandlerType(), equalTo(TransportHandlerType.EAP_TLS));
        assertThat(con.getHostname(), equalTo("testDefaultHost"));
        assertThat(con.getAlias(), equalTo("testDefaultAlias"));
        assertThat(con.getPort(), equalTo(9772));
    }

    @Test
    public void mixInDefaultsFromEmptyReference() throws Exception {
        TestXmlRoot con = new TestXmlRoot();
        InboundConnection defaultCon = new InboundConnection();

        assertNull(con.getTimeout());
        assertNull(con.getTransportHandlerType());
        assertNull(con.getHostname());
        assertNull(con.getAlias());
        assertNull(con.getPort());

        con.normalize(null);
        assertThat(con.getTimeout(), equalTo(InboundConnection.DEFAULT_TIMEOUT));
        assertThat(con.getTransportHandlerType(), equalTo(InboundConnection.DEFAULT_TRANSPORT_HANDLER_TYPE));
        assertThat(con.getHostname(), equalTo(InboundConnection.DEFAULT_HOSTNAME));
        assertThat(con.getAlias(), equalTo(InboundConnection.DEFAULT_CONNECTION_ALIAS));
        assertThat(con.getPort(), equalTo(InboundConnection.DEFAULT_PORT));
    }

    @Test
    public void stripDefaultsReversesMixInEmptyDefaults() throws Exception {
        TestXmlRoot con = new TestXmlRoot();
        InboundConnection defaultCon = new InboundConnection();

        assertNull(con.getTimeout());
        assertNull(con.getTransportHandlerType());
        assertNull(con.getHostname());
        assertNull(con.getAlias());
        assertNull(con.getPort());

        con.normalize(null);
        assertThat(con.getTimeout(), equalTo(InboundConnection.DEFAULT_TIMEOUT));
        assertThat(con.getTransportHandlerType(), equalTo(InboundConnection.DEFAULT_TRANSPORT_HANDLER_TYPE));
        assertThat(con.getHostname(), equalTo(InboundConnection.DEFAULT_HOSTNAME));
        assertThat(con.getAlias(), equalTo(InboundConnection.DEFAULT_CONNECTION_ALIAS));
        assertThat(con.getPort(), equalTo(InboundConnection.DEFAULT_PORT));

        con.filter(null);
        assertNull(con.getTimeout());
        assertNull(con.getTransportHandlerType());
        assertNull(con.getHostname());
        assertNull(con.getAlias());
        assertNull(con.getPort());
    }

    @Test
    public void stripDefaultsReversesMixInDefaults() throws Exception {

        TestXmlRoot con = new TestXmlRoot();
        InboundConnection defaultCon = new InboundConnection();

        defaultCon.setPort(9772);
        defaultCon.setTimeout(2300);
        defaultCon.setTransportHandlerType(TransportHandlerType.EAP_TLS);
        defaultCon.setHostname("testDefaultHost");
        defaultCon.setAlias("testDefaultAlias");

        assertNull(con.getTimeout());
        assertNull(con.getTransportHandlerType());
        assertNull(con.getHostname());
        assertNull(con.getAlias());
        assertNull(con.getPort());

        con.normalize(defaultCon);
        assertThat(con.getTimeout(), equalTo(2300));
        assertThat(con.getTransportHandlerType(), equalTo(TransportHandlerType.EAP_TLS));
        assertThat(con.getHostname(), equalTo("testDefaultHost"));
        assertThat(con.getAlias(), equalTo("testDefaultAlias"));
        assertThat(con.getPort(), equalTo(9772));

        con.filter(defaultCon);
        assertNull(con.getTimeout());
        assertNull(con.getTransportHandlerType());
        assertNull(con.getHostname());
        assertNull(con.getAlias());
        assertNull(con.getPort());
    }

    // /**
    // * Verify that ClientConnectionEnd does not include default values in
    // * serialization output.
    // *
    // * @throws Exception
    // */
    // @Test
    // public void testSerializeDefaultFields() throws Exception {
    //
    // InboundConnectionTest.TestXmlRoot expected = new
    // InboundConnectionTest.TestXmlRoot();
    // expected.setDefaultTimeout(2300);
    // expected.setDefaultTransportHandlerType(TransportHandlerType.EAP_TLS);
    //
    // m.marshal(expected, writer);
    // String xmlString = writer.toString();
    // LOGGER.debug(xmlString);
    //
    // assertEquals("<testXmlRoot/>", xmlString.split("\\n")[1]);
    //
    // Unmarshaller um = context.createUnmarshaller();
    // InboundConnectionTest.TestXmlRoot actual =
    // (InboundConnectionTest.TestXmlRoot) um
    // .unmarshal(new StringReader(xmlString));
    //
    // // Default values are not marshaled
    // assertNotEquals(expected, actual);
    //
    // // Default values need to be set again after un-marshaling
    // actual.setDefaultTimeout(2300);
    // actual.setDefaultTransportHandlerType(TransportHandlerType.EAP_TLS);
    // assertEquals(expected, actual);
    // assertNotSame(expected, actual);
    // }
    //
    // /**
    // * Verify that ServerConnectionEnd includes overridden defaults in
    // * serialization output.
    // *
    // * @throws Exception
    // */
    // @Test
    // public void testSerializeOverriddenDefaultFields() throws Exception {
    //
    // TestXmlRoot expected = new TestXmlRoot();
    // expected.setTimeout(40);
    // expected.setTransportHandlerType(TransportHandlerType.UDP);
    // m.marshal(expected, writer);
    //
    // String xmlString = writer.toString();
    // LOGGER.debug(xmlString);
    //
    // StringBuilder sb = new
    // StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
    // sb.append("<testXmlRoot>\n");
    // sb.append(" <transportHandlerType>UDP</transportHandlerType>\n");
    // sb.append(" <timeout>40</timeout>\n");
    // sb.append("</testXmlRoot>\n");
    // assertEquals(sb.toString(), xmlString);
    //
    // Unmarshaller um = context.createUnmarshaller();
    // TestXmlRoot actual = (TestXmlRoot) um.unmarshal(new
    // StringReader(xmlString));
    //
    // assertEquals(expected, actual);
    // assertNotSame(expected, actual);
    // }
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class TestXmlRoot extends InboundConnection {
    }
}
