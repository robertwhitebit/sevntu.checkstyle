package  com.github.  sevntu.checkstyle.checks.whitespace;

import java.util.List;
import  java.util.Vector;

public class SingleSpaceErrors  {

int i =    99  ;
{
i=1;
i  =2;
i=  3;
    i =  4;
}

    private  void foo  (int     i) {
        if (i  > 10)  {
            if  (bar(  )) {
                i  ++;
                foo  (i);
            }
        }
    }

    private boolean  bar(  ) {
        List  <Double  > list  = new Vector<  >();
        int a	= 0;
        return  Math.random() <  0.5;
    }  }