/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.config.converters;

import com.beust.jcommander.ParameterException;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class ByteArrayConverterTest {
    private ByteArrayConverter converter;

    @Before
    public void setUp() {
        converter = new ByteArrayConverter();
    }

    @Test
    public void testConvert() {
        String testString = "00";
        assertArrayEquals(new byte[] { 0x00 }, converter.convert(testString));
        testString = "FF";
        assertArrayEquals(new byte[] { (byte) 0xff }, converter.convert(testString));
        testString = "FFFF";
        assertArrayEquals(new byte[] { (byte) 0xff, (byte) 0xff }, converter.convert(testString));
    }

    @Test(expected = ParameterException.class)
    public void testConvertError() {
        converter.convert("hello world");
    }
}
