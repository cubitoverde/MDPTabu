package com.gmail.cubitverde.MDPTabu;

import java.util.*;

public class FunMain {
    static ObjSolution MdpGrasp(ObjInstanceSheet sheet) {
        ObjSolution solution = new ObjSolution(sheet.getFileName());
        LinkedList<Integer> elements = new LinkedList<Integer>();

        int bestI = (int) (Math.random() * MDPTabu.n);
        elements.add(bestI);
        solution.setElements(elements);

        Map<Integer, Integer> contributions = new HashMap<Integer, Integer>();
        Utilities.UpdateContributions(contributions, sheet, bestI);

        for (int j = 1; j < MDPTabu.m; j++) {
            Utilities.AddNewElement(contributions, solution);
            elements = solution.getElements();
            Utilities.UpdateContributions(contributions, sheet, elements.get(j));
            for (int k = 0; k < elements.size(); k++) {
                contributions.put(elements.get(k), 0);
            }
        }

        return solution;
    }

    static ObjSolution ImproveSolution(ObjInstanceSheet sheet, ObjSolution oldSolution) {
        ObjSolution solution = new ObjSolution(oldSolution);
        LinkedList<Integer> elements = solution.getElements();
        int value = solution.getValue();
        Integer[][] data = sheet.getData();

        boolean improved = true;
        while (improved) {
            improved = false;

            for (int i = 0; i < MDPTabu.m; i++) {
                int tryOut = elements.get(i);
                int outValue = Utilities.GetOutValue(tryOut, elements, data);

                for (int tryIn = 0; tryIn < MDPTabu.n; tryIn++) {
                    int inValue = Utilities.GetInValue(tryOut, elements, data, tryIn);

                    if (inValue > outValue) {
                        value += inValue - outValue;
                        elements.remove(Integer.valueOf(tryOut));
                        elements.add(i, tryIn);
                        improved = true;
                        break;
                    }
                }
            }
        }

        solution.setElements(elements);
        solution.setValue(value);
        return solution;
    }

    static ObjSolution TabuMethod(ObjInstanceSheet sheet, ObjSolution oldSolution) {
        ObjSolution solution = new ObjSolution(oldSolution);
        LinkedList<Integer> elements = solution.getElements();
        int value = solution.getValue();
        Integer[][] data = sheet.getData();
        LinkedList<Integer> tabuList = new LinkedList<Integer>();

        for (int i = 0; i < MDPTabu.m; i++) {
            boolean improved = false;
            int tryOut = elements.get(i);
            int outValue = Utilities.GetOutValue(tryOut, elements, data);

            int bestInValue = -1;
            int bestI = -1;
            for (int tryIn = 0; tryIn < MDPTabu.n; tryIn++) {
                if (tabuList.contains(tryIn)) {
                    continue;
                }

                int inValue = Utilities.GetInValue(tryOut, elements, data, tryIn);
                if (inValue > bestInValue) {
                    bestInValue = inValue;
                    bestI = tryIn;
                }

                if (inValue > outValue) {
                    value += inValue - outValue;
                    elements.remove(Integer.valueOf(tryOut));
                    if (tabuList.size() == MDPTabu.tabuTenure) {
                        tabuList.removeFirst();
                    }
                    tabuList.addLast(tryOut);
                    elements.add(i, tryIn);
                    improved = true;
                    break;
                }
            }

            if (!improved) {
                value += bestInValue - outValue;
                elements.remove(Integer.valueOf(tryOut));
                if (tabuList.size() == MDPTabu.tabuTenure) {
                    tabuList.removeFirst();
                }
                tabuList.addLast(tryOut);
                elements.add(i, bestI);
            }
        }

        solution.setElements(elements);
        solution.setValue(value);
        return solution;
    }
}
