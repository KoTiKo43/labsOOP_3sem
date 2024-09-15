package Task4;

import myfirstpackage.*;

class MyFirstClass {
    public static void main(String[] s) {

        MySecondClass o = new MySecondClass(33, 44);

        System.out.println(o.operationSum());
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                o.setNum1(i);
                o.setNum2(j);
                System.out.print(o.operationSum());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}