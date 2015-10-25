package demo1;

/**
 * Created by shihuacai on 2015/10/24.
 */
public class PrinterI extends demo._PrinterDisp {
    public void
    printString(String s, Ice.Current current)
    {
        System.out.println(s);
    }
}