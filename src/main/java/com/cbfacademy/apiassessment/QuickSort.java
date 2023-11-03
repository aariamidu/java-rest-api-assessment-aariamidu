package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QuickSort {

    public void sort(List<EmissionsData> emissionsDataList) {
        if (emissionsDataList == null || emissionsDataList.isEmpty()) {
            return;
        }
        quickSort(emissionsDataList, 0, emissionsDataList.size() - 1);
    }

    private void quickSort(List<EmissionsData> emissionsDataList, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(emissionsDataList, low, high);

            quickSort(emissionsDataList, low, partitionIndex - 1);
            quickSort(emissionsDataList, partitionIndex + 1, high);
        }
    }

    private int partition(List<EmissionsData> emissionsDataList, int low, int high) {
        double pivot = emissionsDataList.get(high).getCo2e();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (emissionsDataList.get(j).getCo2e() < pivot) {
                i++;
                swap(emissionsDataList, i, j);
            }
        }
        swap(emissionsDataList, i + 1, high);
        return i + 1;
    }

    private void swap(List<EmissionsData> emissionsDataList, int i, int j) {
        EmissionsData temp = emissionsDataList.get(i);
        emissionsDataList.set(i, emissionsDataList.get(j));
        emissionsDataList.set(j, temp);
    }
}
