package com.github.sevntu.checkstyle.checks.whitespace;

import com.github.sevntu.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class SingleSpaceSeparatorCheckTest extends BaseCheckTestSupport {

    private final String message = getCheckMessage(SingleSpaceSeparatorCheck.MSG_KEY);

    @Test
    public void testNoSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        verify(checkConfig, getPath("SingleNoSpaceErrors.java"), new String[0]);
    }

    @Test
    public void testSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        String[] expected = {
            "1:9: " + message,
            "1:22: " + message,
            "4:8: " + message,
            "6:32: " + message,
            "8:11: " + message,
            "8:15: " + message,
            "11:3: " + message,
            "12:4: " + message,
            "13:9: " + message,
            "16:13: " + message,
            "16:23: " + message,
            "16:32: " + message,
            "17:15: " + message,
            "17:22: " + message,
            "18:16: " + message,
            "18:23: " + message,
            "19:19: " + message,
            "20:21: " + message,
            "25:21: " + message,
            "25:27: " + message,
            "26:14: " + message,
            "26:23: " + message,
            "26:31: " + message,
            "26:46: " + message,
            "27:14: " + message,
            "28:16: " + message,
            "28:33: " + message,
            "29:7: " + message,};

        verify(checkConfig, getPath("SingleSpaceErrors.java"), expected);
    }
}
