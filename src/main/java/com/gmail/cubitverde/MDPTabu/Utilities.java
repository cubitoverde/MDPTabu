package com.gmail.cubitverde.MDPTabu;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utilities {
    static void OnEnable() {
        MDPTabu.n = 500;
        MDPTabu.m = 25;
        MDPTabu.instanceNames = CreateInstancesList();
        MDPTabu.instanceSheets = CreateInstancesMap();
        MDPTabu.alphaGrasp = 0.80;
        MDPTabu.runningTime = 1 * 60;

    }

    static List<String> CreateInstancesList() {
        List<String> instances = new ArrayList<String>();
        instances.add("Amparo.xlsx");
        instances.add("Borja.xlsx");
        instances.add("Daniel.xlsx");
        instances.add("Emilio.xlsx");
        instances.add("Jose.xlsx");
        instances.add("MariaJesus.xlsx");
        instances.add("Raquel.xlsx");
        instances.add("Virginia.xlsx");
        return instances;
    }

    static List<ObjInstanceSheet> CreateInstancesMap() {
        List<ObjInstanceSheet> instanceSheets = new ArrayList<ObjInstanceSheet>();

        List<String> instances = MDPTabu.instanceNames;
        for (String fileName : instances) {
            ObjInstanceSheet instanceSheet = new ObjInstanceSheet(fileName);
            Integer[][] data = new Integer[MDPTabu.n][MDPTabu.n];

            FileInputStream file;
            Workbook workbook;
            Sheet sheet;
            try {
                file = new FileInputStream(new File("/Users/andres/Desktop/GitStuff/Projects/MDPTabu/src/main/resources/" + fileName));
                workbook = new XSSFWorkbook(file);
                sheet = workbook.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            for (int i = 0; i < MDPTabu.n; i++) {
                for (int j = 0; j < MDPTabu.n; j++) {
                    data[i][j] = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                }
            }
            instanceSheet.setData(data);
            instanceSheets.add(instanceSheet);
        }

        return instanceSheets;
    }

    static ObjInstanceSheet GetInstance(String fileName) {
        ObjInstanceSheet sheet = null;
        for (ObjInstanceSheet instance : MDPTabu.instanceSheets) {
            if (instance.getFileName().equals(fileName)) {
                sheet = new ObjInstanceSheet(instance);
            }
        }
        return sheet;
    }

    static void UpdateContributions(Map<Integer, Integer> contributions, ObjInstanceSheet sheet, int bestI) {
        Integer[][] data = sheet.getData();

        if (contributions.size() < MDPTabu.n) {
            for (int i = 0; i < MDPTabu.n; i++) {
                contributions.put(i, data[i][bestI]);
            }
        } else {
            for (int i = 0; i < MDPTabu.n; i++) {
                int oldContribution = contributions.get(i);
                contributions.put(i, oldContribution + data[i][bestI]);
            }
        }
    }

    static void AddNewElement(Map<Integer, Integer> contributions, ObjSolution solution) {
        List<Integer> elements = solution.getElements();
        int value = solution.getValue();

        int max = 0;
        int min = 999999;

        for (int i = 0; i < MDPTabu.n; i++) {
            int contributionI = contributions.get(i);
            if (contributionI > max) {
                max = contributionI;
            }
            if (contributionI > 0 && contributionI < min) {
                min = contributionI;
            }
        }

        double threshold = min + MDPTabu.alphaGrasp * (max - min);
        List<Integer> goodElements = new ArrayList<Integer>();
        for (int i = 0; i < MDPTabu.n; i++) {
            if (contributions.get(i) > threshold) {
                goodElements.add(i);
            }
        }

        int bestI = goodElements.get((int) (Math.random() * goodElements.size()));
        elements.add(bestI);
        solution.setElements(elements);
        solution.setValue(value + contributions.get(bestI));
    }

    static int GetOutValue(int tryOut, List<Integer> elements, Integer[][] data) {
        int outValue = 0;

        for (int i = 0; i < MDPTabu.m; i++) {
            outValue += data[tryOut][elements.get(i)];
        }

        return outValue;
    }

    static int GetInValue(int tryOut, List<Integer> elements, Integer[][] data, int tryIn) {
        int inValue = 0;

        if (!elements.contains(tryIn)) {
            for (int i = 0; i < MDPTabu.m; i++) {
                int element = elements.get(i);
                if (element != tryOut) {
                    inValue += data[tryIn][element];
                }
            }
        }

        return inValue;
    }
}
