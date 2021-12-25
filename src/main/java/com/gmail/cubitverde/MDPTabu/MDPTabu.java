package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class MDPTabu {
    static int n; // Number of nodes
    static int m; // Number of selected elements
    static List<String> instanceNames; // Names of instance excel sheets
    static List<ObjInstanceSheet> instanceSheets; // Data of instance excel sheets
    static double alphaGrasp; // Alpha used in GRASP method
    static int runningTime; // Running time in seconds for the methods
    static int tabuTenure; // Tenure used in Tabu method
    static int runWhichMethod = 2; // Set to: 1 to run GRASP, 2 to run Tabu, 3 to run ?

    public static void main(String args[]) {
        Utilities.OnEnable();

        int i = 0;
        for (ObjInstanceSheet instanceSheet : instanceSheets) {

            switch (runWhichMethod) {
                case 1:
                    Thread instanceThreadGrasp = new Thread(new RunGrasp(instanceSheet));
                    instanceThreadGrasp.start();
                    break;
                case 2:
                    Thread instanceThreadTabu = new Thread(new RunTabu(instanceSheet));
                    instanceThreadTabu.start();
                    break;
                case 3:

                    break;
            }

            try {
                if (i == 4 - 1) {
                    Thread.sleep(runningTime * 1000 + (i + 2) * 1000);
                } else {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            i++;
        }
    }
}
