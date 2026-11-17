class  Calculator
{
    protected   int add(int a ,int b)
    {
        return a+b+10;
    }
}
    class AdvancedCalculator  extends Calculator {

       public int add(int a, int b) {
            return a + b ;
        }


        float add(float a, int b) {
            return a + b + 10.0f;
        }


        public static void main(String[] args) {

           AdvancedCalculator  advancedCalculatorc = new AdvancedCalculator();
            System.out.println(advancedCalculatorc.add(10, 20));
        }

}
