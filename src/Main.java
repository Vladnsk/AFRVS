import java.io.FileWriter;
import java.io.IOException;

public class Main {


    static public int Imitation(double T, int count, double step, double lm, double nu){
        double lastT = 0;
        int tFirst = 0;
        boolean stop = false;
        int countStart = count;
        double k = T/step;

        for(double i = 0; i < k; i++ ){
            double rnd = Math.random();
            double rnd2 = Math.random();
            //double dt = i - lastT;

            //Fail
            if(count > 0) {
                if (rnd < (1 - Math.exp(-count * lm * step))) {
                    count--;
                    //lastT = i;
                }
            }
            //add
            if(count < countStart){
                if (rnd2 < (1 - Math.exp(-nu *(countStart - count) * step))) {
                    count++;
                }
            }

        }

        //System.out.println("Количество работоспособных машин: " + count);
        return count;
    }



    public static void main(String[] args) {

        double globalTime;
        double Time = 40.0;
        int count;
        double dCount;
        double lambda = 0.0001;
        double nu = 0.1;
        double dt = 0.1;
        int countElementaryMachine = 10000;
        int countExperiment = 10000;


        for (double j = 0; j < Time; j += dt) {
            globalTime = j;
            count = 0;

            for (int i = 0; i < countExperiment; i++) {
                int goodM = Imitation(globalTime, countElementaryMachine, dt, lambda, nu);
                int countGoodMachine = goodM;
                //System.out.println("Количество работоспособных машин: " + countGoodMachine);
                //System.out.println("---------------------------------------");
                count += countGoodMachine;
            }

            dCount = count / countExperiment;
            String textForWriting = j + " " +dCount;
            try (FileWriter writer = new FileWriter("data_last_recovery6.txt", true)) {
                //writer.write("gTime: " + globalTime + "; lambda: " + lambda + "; mCount: " + countElementaryMachine + "; exCount: " + countExperiment + "; Step: " + dt + "; Nu: " + nu + "\r\n");
                writer.write(textForWriting + "\r\n");
                //writer.write("-----------------------------------" + "\r\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
