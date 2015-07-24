package com.github.sevntu.checkstyle.checks.whitespace;

import java.util.ArrayList;
import java.util.List;

public class SingleNoSpaceErrors {

    private void foo(int i) {
        if (i > 10) {
            if (bar()) {
                i++;
                foo(i);
            }
        }
    }

    private boolean bar() {
        List<Double> list = new ArrayList<>();
        return Math.random() < 0.5;
    }
}
