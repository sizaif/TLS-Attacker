/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.util.ArrayConverter;

import java.util.Random;

/**
 * @ClassName CustumArrayConverter
 * @Auther sizaif
 * @Date 2023/5/17 1:14
 **/
public class CustumArrayConverter extends ArrayConverter {
    /**
     * Converts a string with an even number of hexadecimal characters to a byte array.
     *
     * @param  input
     *               hex string
     * @return byte array
     */
    public static byte[] hexStringToByteArray(String input) {
        if ((input == null)) {
            throw new IllegalArgumentException("The input must not be null hexadecimal characters. Found: " + input);
        }
        //
        if (input.length() % 2 != 0) {

            System.out.println("testttsssssssssssssssssssssssss");

            int x = 1;
            if (x == 1) {
                byte[] output = new byte[input.length() / 2 + 1];
                for (int i = 0; i < output.length; i++) {
                    int startIndex = i * 2;

                    // 处理当前字符
                    if (startIndex + 1 < input.length()) {
                        // 处理两个字符并转换为字节
                        output[i] = (byte) ((Character.digit(input.charAt(startIndex), 16) << 4)
                                + Character.digit(input.charAt(startIndex + 1), 16));
                    } else {
                        // 处理最后一个字符，并在末尾添加 0
                        output[i] = (byte) (Character.digit(input.charAt(startIndex), 16) << 4);
                    }
                }
            }

            /**
             * 1/2 概率处理， 1/2概率不处理
             *
             * 处理时 ，50%概率头部添加0， 50%概率尾部添加0
             */
            Random random1 = new Random();
            if (random1.nextBoolean()) {
                Random random2 = new Random();
                if (random2.nextBoolean()) {
                    // 在头部添加零
                    input = "0" + input;
                } else {
                    // 在尾部添加零
                    input = input + "0";
                }
            } else {
                byte[] output = new byte[input.length() / 2 + 1];
                for (int i = 0; i < output.length; i++) {
                    int startIndex = i * 2;

                    // 处理当前字符
                    if (startIndex + 1 < input.length()) {
                        // 处理两个字符并转换为字节
                        output[i] = (byte) ((Character.digit(input.charAt(startIndex), 16) << 4)
                                + Character.digit(input.charAt(startIndex + 1), 16));
                    } else {
                        // 处理最后一个字符，并在末尾添加 0
                        output[i] = (byte) (Character.digit(input.charAt(startIndex), 16) << 4);
                    }
                }
                return output;
            }
        }

        byte[] output = new byte[input.length() / 2];
        for (int i = 0; i < output.length; i++) {
            output[i] =
                    (byte) ((Character.digit(input.charAt(i * 2), 16) << 4) + Character.digit(input.charAt(i * 2 + 1), 16));
        }
        return output;
    }
}
