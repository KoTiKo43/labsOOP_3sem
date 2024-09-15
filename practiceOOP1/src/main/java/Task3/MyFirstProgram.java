package Task3;

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

class MySecondClass {
    private int i1;
    private int i2;
    
    MySecondClass() {
        i1 = 0;
        i2 = 0;
    }

    MySecondClass(int i1, int i2) {
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