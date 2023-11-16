package com.cbfacademy.apiassessment.emissions;

import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Component for performing quicksort on a list of EmissionsData.
 */
@Component
public class QuickSort {

    public void sort(List<EmissionsData> emissionsDataList, String sortBy) {
        if (emissionsDataList == null || emissionsDataList.isEmpty()) {
            return;
        }

        switch (sortBy) {
            case "distance":
                quickSortByDistance(emissionsDataList, 0, emissionsDataList.size() - 1);
                break;
            case "co2e":
                quickSortByCo2e(emissionsDataList, 0, emissionsDataList.size() - 1);
                break;
            case "id":
                quickSortById(emissionsDataList, 0, emissionsDataList.size() - 1);
                break;
            default:
                throw new IllegalArgumentException("Unsupported sorting criteria: " + sortBy);
        }
    }

    private void quickSortByDistance(List<EmissionsData> emissionsDataList, int low, int high) {
        if (low < high) {
            int partitionIndex = partitionByDistance(emissionsDataList, low, high);
            quickSortByDistance(emissionsDataList, low, partitionIndex - 1);
            quickSortByDistance(emissionsDataList, partitionIndex + 1, high);
        }
    }

    private int partitionByDistance(List<EmissionsData> emissionsDataList, int low, int high) {
        double pivot = emissionsDataList.get(high).getDistance();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (emissionsDataList.get(j).getDistance() < pivot) {
                i++;
                swap(emissionsDataList, i, j);
            }
        }
        swap(emissionsDataList, i + 1, high);
        return i + 1;
    }

    private void quickSortByCo2e(List<EmissionsData> emissionsDataList, int low, int high) {
        if (low < high) {
            int partitionIndex = partitionByCo2e(emissionsDataList, low, high);
            quickSortByCo2e(emissionsDataList, low, partitionIndex - 1);
            quickSortByCo2e(emissionsDataList, partitionIndex + 1, high);
        }
    }

    private int partitionByCo2e(List<EmissionsData> emissionsDataList, int low, int high) {
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

    private void quickSortById(List<EmissionsData> emissionsDataList, int low, int high) {
        if (low < high) {
            int partitionIndex = partitionById(emissionsDataList, low, high);
            quickSortById(emissionsDataList, low, partitionIndex - 1);
            quickSortById(emissionsDataList, partitionIndex + 1, high);
        }
    }

    private int partitionById(List<EmissionsData> emissionsDataList, int low, int high) {
        long pivot = emissionsDataList.get(high).getId();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (emissionsDataList.get(j).getId() < pivot) {
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
