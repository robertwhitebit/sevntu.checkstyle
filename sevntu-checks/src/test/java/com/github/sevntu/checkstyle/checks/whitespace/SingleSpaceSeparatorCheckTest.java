package com.github.sevntu.checkstyle.checks.whitespace;

import com.github.sevntu.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SingleSpaceSeparatorCheckTest extends BaseCheckTestSupport {

    private final String message = getCheckMessage(SingleSpaceSeparatorCheck.MSG_KEY);

    public static File currentFileToCheck = null;

    private List<File> getAllJavaFiles(File folder) {
        List<File> files = new ArrayList<>();

        File[] javaFiles = folder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        files.addAll(Arrays.asList(javaFiles));

        File[] folders = folder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File childFolder : folders) {
            files.addAll(getAllJavaFiles(childFolder));
        }

        return files;
    }

    @Test
    public void testSventuCheckstyleProject() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        List<File> javaFiles = getAllJavaFiles(new File("/home/robert/Projects/sevntu.checkstyle/sevntu-checks/src/main/java/com/github/sevntu/checkstyle"));
        for (File javaFile : javaFiles) {
            SingleSpaceSeparatorCheck.currentFile = javaFile;
            System.out.println(javaFile);
            verify(checkConfig, javaFile.getAbsolutePath(), new String[0]);
        }
    }

//    @Test
    public void testNoSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        verify(checkConfig, getPath("SingleNoSpaceErrors.java"), new String[0]);
    }

//    @Test
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
