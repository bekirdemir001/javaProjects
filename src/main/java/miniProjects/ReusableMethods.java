package miniProjects;

public class ReusableMethods {
    public static void slowPrint(String text, int delay){
        for (char c : text.toCharArray()){
            System.out.print(c);
            try {
                Thread.sleep(delay);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
