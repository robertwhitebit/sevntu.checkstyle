package com.github.sevntu.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleSpaceSeparatorCheck extends Check {

    public static final String MSG_KEY = "single.space.separator";

    public static File currentFile;

    private final Pattern multipleWhiteSpaces = Pattern.compile("\\s{2,}");
    private final int singleCommentColumnOffset = 4;
    private final int blockCommentColumnOffset = 2;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.EOF};
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        visitEachToken(rootAST);
    }

    /**
     * TODO: Remove recursive call for each sibling otherwise the method may
     * throw a StackOverflowError on really, really large files.
     */
    private void visitEachToken(DetailAST ast) {
        check(ast);

        DetailAST sibling = ast.getNextSibling();
        if (sibling != null) {
            visitEachToken(sibling);
        }

        if (ast.getFirstChild() != null) {
            visitEachToken(ast.getFirstChild());
        }
    }

    private void check(DetailAST ast) {
        if (ast.getType() == TokenTypes.COMMENT_CONTENT) {
            checkComment(ast);
        } else {
            checkJavaToken(ast);
        }
    }

    private void checkJavaToken(DetailAST ast) {
        String line = getLine(ast.getLineNo() - 1);
        if (ast.getColumnNo() >= 2 && Character.isWhitespace(line.charAt(ast.getColumnNo() - 1))
                && !isFirstTokenInLine(ast)) {
            if (line.charAt(ast.getColumnNo() - 1) != ' ') {
                // The whitespace separator is not a space
                report(ast, ast.getLineNo(), ast.getColumnNo() - 1);
            } else if (Character.isWhitespace(line.charAt(ast.getColumnNo() - 2))) {
                // Multiple whitespaces as separator
                report(ast, ast.getLineNo(), ast.getColumnNo() - 1);
            }
        }
    }

    private boolean isFirstTokenInLine(DetailAST ast) {
        String line = getLine(ast.getLineNo() - 1);
        return line.substring(0, ast.getColumnNo()).trim().length() == 0;
    }

    private void checkComment(DetailAST ast) {
        if (ast.getParent().getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            String line = ast.getText();
            checkCommentLine(ast, line.trim(),
                    0, line.length() - removeLeadingWhitespaces(line).length() + singleCommentColumnOffset);
        } else if (ast.getParent().getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            String[] lines = ast.getText().split("\n");
            for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
                String line = lines[lineIndex];
                checkCommentLine(ast, removeLeadingBlockComment(line.trim()), lineIndex,
                        line.length() - removeLeadingBlockComment(line).length() + blockCommentColumnOffset);
            }
        }
    }

    private void checkCommentLine(DetailAST ast, String line,
            int lineOffset, int columnOffset) {
        Matcher matcher = multipleWhiteSpaces.matcher(line);
        while (matcher.find()) {
            report(ast, ast.getLineNo() + lineOffset, matcher.start() + columnOffset);
        }
    }

    private String removeLeadingBlockComment(String line) {
        return line.replaceFirst("^\\s*\\*\\s*", "");
    }

    private String removeLeadingWhitespaces(String text) {
        return text.replaceFirst("^\\s+", "");
    }

    private String removeTrailingWhitespaces(String text) {
        return text.replaceFirst("\\s+$", "");
    }

    private void report(DetailAST ast, int lineNo, int columnNo) {
        log(lineNo, columnNo, MSG_KEY);
        System.out.println(currentFile + " " + lineNo + ":" + columnNo);
    }

    private String getInfo(DetailAST ast) {
        return ast.getText() + "(" + ast.getType() + "@" + ast.getLineNo() + ":" + ast.getColumnNo() + ")";
    }
}
