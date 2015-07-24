package com.github.sevntu.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SingleSpaceSeparatorCheck extends Check {

    public static final String MSG_KEY = "single.space.separator";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.EOF};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        visitEachToken(rootAST);
    }

    /**
     * TODO: Remove recursive call for each sibling otherwise the method may
     * throw a StackOverflowError.
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
        String line = getLine(ast.getLineNo() - 1);
        if (ast.getColumnNo() >= 2 && Character.isWhitespace(line.charAt(ast.getColumnNo() - 1))
                && !isFirstTokenInLine(ast)) {
            if (line.charAt(ast.getColumnNo() - 1) != ' ') {
                // The whitespace separator is not a space
                log(ast.getLineNo(), ast.getColumnNo() - 1, MSG_KEY);
            } else if (Character.isWhitespace(line.charAt(ast.getColumnNo() - 2))) {
                // Multiple whitespaces as separator
                log(ast.getLineNo(), ast.getColumnNo() - 1, MSG_KEY);
            }
        }
    }

    private boolean isFirstTokenInLine(DetailAST ast) {
        String line = getLine(ast.getLineNo() - 1);
        return line.substring(0, ast.getColumnNo()).trim().length() == 0;
    }

    private String getInfo(DetailAST ast) {
        return ast.getText() + "(" + ast.getType() + "@" + ast.getLineNo() + ":" + ast.getColumnNo() + ")";
    }
}
