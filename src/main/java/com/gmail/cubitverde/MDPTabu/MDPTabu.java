package com.gmail.cubitverde.MDPTabu;

import java.util.List;

public class MDPTabu {
    static int n; // Number of nodes
    static int m; // Number of selected elements
    static List<String> instanceNames; // Names of instance excel sheets
    static List<ObjInstanceSheet> instanceSheets; // Data of instance excel sheets
    static double alphaGrasp; // Alpha used in GRASP method
    static int runningTime; // Running time in seconds for the methods

    public static void main(String args[]) {
        Utilities.OnEnable();

        int i = 0;
        for (ObjInstanceSheet instanceSheet : instanceSheets) {
            Thread instanceThread = new Thread(new RunGrasp(instanceSheet));
            instanceThread.start();

            try {
                if (i == 4) {
                    Thread.sleep(runningTime * 1000 + (i + 1) * 1000);
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
