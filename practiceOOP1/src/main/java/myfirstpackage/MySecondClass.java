package myfirstpackage;

public class MySecondClass {
    private int i1;
    private int i2;

    public MySecondClass() {
        i1 = 0;
        i2 = 0;
    }

    public MySecondClass(int i1, int i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    public int getNum1() {
         return i1;
    }

    public void setNum1(int i1) {
        this.i1 = i1;
    }

    public int getNum2() {
        return i2;
    }

    public void setNum2(int i2) {
        this.i2 = i2;
    }

    public int operationSum() {
        return i1 + i2;
    }
}