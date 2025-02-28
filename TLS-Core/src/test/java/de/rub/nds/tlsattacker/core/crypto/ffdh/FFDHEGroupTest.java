/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 * <p>
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 * <p>
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.tlsattacker.core.crypto.ffdh;

import de.rub.nds.tlsattacker.core.constants.NamedGroup;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class FFDHEGroupTest {

    @Before
    public void setUp() {
    }

    @Test
    public void test() {
        int counter = 0;
        int implemented = 5;
        for (NamedGroup name : NamedGroup.values()) {
            try {
                FFDHEGroup group = GroupFactory.getGroup(name);
                BigInteger p = group.getP();
                BigInteger g = group.getG();

                assertTrue(p.isProbablePrime(32));
                BigInteger q = p.subtract(BigInteger.ONE).divide(BigInteger.TWO);
                assertTrue(q.isProbablePrime(32));
                assertEquals(BigInteger.ONE, g.modPow(q, p));

                counter++;
            } catch (UnsupportedOperationException e) {
            }
        }
        assertEquals(implemented, counter);
    }

    @Test
    public void groupFFDHE2048Test() {
        FFDHEGroup group = GroupFactory.getGroup(NamedGroup.FFDHE2048);
        assertEquals(BigInteger.TWO, group.getG());
        BigInteger q = group.getP().subtract(BigInteger.ONE).divide(BigInteger.TWO);
        assertEquals(q, new BigInteger(
            "7FFFFFFFFFFFFFFFD6FC2A2C515DA54D57EE2B10139E9E78EC5CE2C1E7169B4AD4F09B208A3219FDE649CEE7124D9F7CBE97F1B1B1863AEC7B40D901576230BD69EF8F6AEAFEB2B09219FA8FAF83376842B1B2AA9EF68D79DAAB89AF3FABE49ACC278638707345BBF15344ED79F7F4390EF8AC509B56F39A98566527A41D3CBD5E0558C159927DB0E88454A5D96471FDDCB56D5BB06BFA340EA7A151EF1CA6FA572B76F3B1B95D8C8583D3E4770536B84F017E70E6FBF176601A0266941A17B0C8B97F4E74C2C1FFC7278919777940C1E1FF1D8DA637D6B99DDAFE5E17611002E2C778C1BE8B41D96379A51360D977FD4435A11C30942E4BFFFFFFFFFFFFFFFF",
            16));
    }

    @Test
    public void groupFFDHE3072Test() {
        FFDHEGroup group = GroupFactory.getGroup(NamedGroup.FFDHE3072);
        assertEquals(BigInteger.TWO, group.getG());
        BigInteger q = group.getP().subtract(BigInteger.ONE).divide(BigInteger.TWO);
        assertEquals(q, new BigInteger(
            "7FFFFFFFFFFFFFFFD6FC2A2C515DA54D57EE2B10139E9E78EC5CE2C1E7169B4AD4F09B208A3219FDE649CEE7124D9F7CBE97F1B1B1863AEC7B40D901576230BD69EF8F6AEAFEB2B09219FA8FAF83376842B1B2AA9EF68D79DAAB89AF3FABE49ACC278638707345BBF15344ED79F7F4390EF8AC509B56F39A98566527A41D3CBD5E0558C159927DB0E88454A5D96471FDDCB56D5BB06BFA340EA7A151EF1CA6FA572B76F3B1B95D8C8583D3E4770536B84F017E70E6FBF176601A0266941A17B0C8B97F4E74C2C1FFC7278919777940C1E1FF1D8DA637D6B99DDAFE5E17611002E2C778C1BE8B41D96379A51360D977FD4435A11C308FE7EE6F1AAD9DB28C81ADDE1A7A6F7CCE011C30DA37E4EB736483BD6C8E9348FBFBF72CC6587D60C36C8E577F0984C289C9385A098649DE21BCA27A7EA229716BA6E9B279710F38FAA5FFAE574155CE4EFB4F743695E2911B1D06D5E290CBCD86F56D0EDFCD216AE22427055E6835FD29EEF79E0D90771FEACEBE12F20E95B363171BFFFFFFFFFFFFFFFF",
            16));
    }

    @Test
    public void groupFFDHE4096Test() {
        FFDHEGroup group = GroupFactory.getGroup(NamedGroup.FFDHE4096);
        assertEquals(BigInteger.TWO, group.getG());
        BigInteger q = group.getP().subtract(BigInteger.ONE).divide(BigInteger.TWO);
        assertEquals(q, new BigInteger(
            "7FFFFFFFFFFFFFFFD6FC2A2C515DA54D57EE2B10139E9E78EC5CE2C1E7169B4AD4F09B208A3219FDE649CEE7124D9F7CBE97F1B1B1863AEC7B40D901576230BD69EF8F6AEAFEB2B09219FA8FAF83376842B1B2AA9EF68D79DAAB89AF3FABE49ACC278638707345BBF15344ED79F7F4390EF8AC509B56F39A98566527A41D3CBD5E0558C159927DB0E88454A5D96471FDDCB56D5BB06BFA340EA7A151EF1CA6FA572B76F3B1B95D8C8583D3E4770536B84F017E70E6FBF176601A0266941A17B0C8B97F4E74C2C1FFC7278919777940C1E1FF1D8DA637D6B99DDAFE5E17611002E2C778C1BE8B41D96379A51360D977FD4435A11C308FE7EE6F1AAD9DB28C81ADDE1A7A6F7CCE011C30DA37E4EB736483BD6C8E9348FBFBF72CC6587D60C36C8E577F0984C289C9385A098649DE21BCA27A7EA229716BA6E9B279710F38FAA5FFAE574155CE4EFB4F743695E2911B1D06D5E290CBCD86F56D0EDFCD216AE22427055E6835FD29EEF79E0D90771FEACEBE12F20E95B34F0F78B737A9618B26FA7DBC9874F272C42BDB563EAFA16B4FB68C3BB1E78EAA81A00243FAADD2BF18E63D389AE44377DA18C576B50F0096CF34195483B00548C0986236E3BC7CB8D6801C0494CCD199E5C5BD0D0EDC9EB8A0001E15276754FCC68566054148E6E764BEE7C764DAAD3FC45235A6DAD428FA20C170E345003F2F32AFB57FFFFFFFFFFFFFFF",
            16));
    }

    @Test
    public void groupFFDHE6144Test() {
        FFDHEGroup group = GroupFactory.getGroup(NamedGroup.FFDHE6144);
        assertEquals(BigInteger.TWO, group.getG());
        BigInteger q = group.getP().subtract(BigInteger.ONE).divide(BigInteger.TWO);
        assertEquals(q, new BigInteger(
            "7FFFFFFFFFFFFFFFD6FC2A2C515DA54D57EE2B10139E9E78EC5CE2C1E7169B4AD4F09B208A3219FDE649CEE7124D9F7CBE97F1B1B1863AEC7B40D901576230BD69EF8F6AEAFEB2B09219FA8FAF83376842B1B2AA9EF68D79DAAB89AF3FABE49ACC278638707345BBF15344ED79F7F4390EF8AC509B56F39A98566527A41D3CBD5E0558C159927DB0E88454A5D96471FDDCB56D5BB06BFA340EA7A151EF1CA6FA572B76F3B1B95D8C8583D3E4770536B84F017E70E6FBF176601A0266941A17B0C8B97F4E74C2C1FFC7278919777940C1E1FF1D8DA637D6B99DDAFE5E17611002E2C778C1BE8B41D96379A51360D977FD4435A11C308FE7EE6F1AAD9DB28C81ADDE1A7A6F7CCE011C30DA37E4EB736483BD6C8E9348FBFBF72CC6587D60C36C8E577F0984C289C9385A098649DE21BCA27A7EA229716BA6E9B279710F38FAA5FFAE574155CE4EFB4F743695E2911B1D06D5E290CBCD86F56D0EDFCD216AE22427055E6835FD29EEF79E0D90771FEACEBE12F20E95B34F0F78B737A9618B26FA7DBC9874F272C42BDB563EAFA16B4FB68C3BB1E78EAA81A00243FAADD2BF18E63D389AE44377DA18C576B50F0096CF34195483B00548C0986236E3BC7CB8D6801C0494CCD199E5C5BD0D0EDC9EB8A0001E15276754FCC68566054148E6E764BEE7C764DAAD3FC45235A6DAD428FA20C170E345003F2F06EC8105FEB25B2281B63D2733BE961C29951D11DD2221657A9F531DDA2A194DBB126448BDEEB258E07EA659C74619A6380E1D66D6832BFE67F638CD8FAE1F2723020F9C40A3FDA67EDA3BD29238FBD4D4B4885C2A99176DB1A06C500778491A8288F1855F60FFFCF1D1373FD94FC60C1811E1AC3F1C6D003BECDA3B1F2725CA595DE0CA63328F3BE57CC97755601195140DFB59D39CE091308B4105746DAC23D33E5F7CE4848DA316A9C66B9581BA3573BFAF311496188AB15423282EE416DC2A19C5724FA91AE4ADC88BC66796EAE5677A01F64E8C08631395822D9DB8FCEE35C06B1FEEA5474D6D8F34B1534A936A18B0E0D20EAB86BC9C6D6A5207194E68720732FFFFFFFFFFFFFFFF",
            16));
    }

    @Test
    public void groupFFDHE8192Test() {
        FFDHEGroup group = GroupFactory.getGroup(NamedGroup.FFDHE8192);
        assertEquals(BigInteger.TWO, group.getG());
        BigInteger q = group.getP().subtract(BigInteger.ONE).divide(BigInteger.TWO);
        assertEquals(q, new BigInteger(
            "7FFFFFFFFFFFFFFFD6FC2A2C515DA54D57EE2B10139E9E78EC5CE2C1E7169B4AD4F09B208A3219FDE649CEE7124D9F7CBE97F1B1B1863AEC7B40D901576230BD69EF8F6AEAFEB2B09219FA8FAF83376842B1B2AA9EF68D79DAAB89AF3FABE49ACC278638707345BBF15344ED79F7F4390EF8AC509B56F39A98566527A41D3CBD5E0558C159927DB0E88454A5D96471FDDCB56D5BB06BFA340EA7A151EF1CA6FA572B76F3B1B95D8C8583D3E4770536B84F017E70E6FBF176601A0266941A17B0C8B97F4E74C2C1FFC7278919777940C1E1FF1D8DA637D6B99DDAFE5E17611002E2C778C1BE8B41D96379A51360D977FD4435A11C308FE7EE6F1AAD9DB28C81ADDE1A7A6F7CCE011C30DA37E4EB736483BD6C8E9348FBFBF72CC6587D60C36C8E577F0984C289C9385A098649DE21BCA27A7EA229716BA6E9B279710F38FAA5FFAE574155CE4EFB4F743695E2911B1D06D5E290CBCD86F56D0EDFCD216AE22427055E6835FD29EEF79E0D90771FEACEBE12F20E95B34F0F78B737A9618B26FA7DBC9874F272C42BDB563EAFA16B4FB68C3BB1E78EAA81A00243FAADD2BF18E63D389AE44377DA18C576B50F0096CF34195483B00548C0986236E3BC7CB8D6801C0494CCD199E5C5BD0D0EDC9EB8A0001E15276754FCC68566054148E6E764BEE7C764DAAD3FC45235A6DAD428FA20C170E345003F2F06EC8105FEB25B2281B63D2733BE961C29951D11DD2221657A9F531DDA2A194DBB126448BDEEB258E07EA659C74619A6380E1D66D6832BFE67F638CD8FAE1F2723020F9C40A3FDA67EDA3BD29238FBD4D4B4885C2A99176DB1A06C500778491A8288F1855F60FFFCF1D1373FD94FC60C1811E1AC3F1C6D003BECDA3B1F2725CA595DE0CA63328F3BE57CC97755601195140DFB59D39CE091308B4105746DAC23D33E5F7CE4848DA316A9C66B9581BA3573BFAF311496188AB15423282EE416DC2A19C5724FA91AE4ADC88BC66796EAE5677A01F64E8C08631395822D9DB8FCEE35C06B1FEEA5474D6D8F34B1534A936A18B0E0D20EAB86BC9C6D6A5207194E67FA35551B5680267B00641C0F212D18ECA8D7327ED91FE764A84EA1B43FF5B4F6E8E62F05C661DEFB258877C35B18A151D5C414AAAD97BA3E499332E596078E600DEB81149C441CE95782F22A282563C5BAC1411423605D1AE1AFAE2C8B0660237EC128AA0FE3464E4358115DB84CC3B523073A28D4549884B81FF70E10BF361C13729628D5348F07211E7E4CF4F18B286090BDB1240B66D6CD4AFCEADC00CA446CE05050FF183AD2BBF118C1FC0EA51F97D22B8F7E46705D4527F45B42AEFF395853376F697DD5FDF2C5187D7D5F0E2EB8D43F17BA0F7C60FF437F535DFEF29833BF86CBE88EA4FBD4221E8411728354FA30A7008F154A41C7FC466B4645DBE2E321267FFFFFFFFFFFFFFF",
            16));
    }
}
