package rs.ac.bg.etf.sn120348.bp2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PasswordEncoderTest {
    private Md5PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        passwordEncoder = new Md5PasswordEncoder();
    }

    @Test
    public void testPasswordEncryption() {
        String hashInDatabase = "aee712198874c9b20c7cf55200f4208d";
        String password = "92kEwunLW";

        Assert.assertEquals(passwordEncoder.encodePassword(password, null), hashInDatabase);
    }
}
