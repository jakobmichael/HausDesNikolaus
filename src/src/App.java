package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class App {

    static List<Integer[]> permutations;
    static List<Integer[]> validOptions;
    static List<Integer[]> solution;
    static HashSet<List<Integer>> solutionsToCompare;
    static int temp1;
    static int temp2;

    public static void main(String[] args) {

        createCheckList();

        HashSet<List<Integer>> finalOptions = new HashSet<>();

        Integer[] points = new Integer[] { 1, 1, 2, 2, 3, 4, 4, 5, 5 };

        permutations = permutations(points);

        // getIterationPointer();

        finalOptions = checkForValidComb(permutations);

        compareSolutions(finalOptions, solutionsToCompare);

        System.out.println(finalOptions.size());

        // for (List<Integer> strCurrentList : finalOptions) {
        // System.out.println(strCurrentList);
        // }

    }

    static ArrayList<Integer[]> permutations(Integer[] a) {
        ArrayList<Integer[]> ret = new ArrayList<Integer[]>();
        permutation(a, 0, ret);
        return ret;
    }

    public static void permutation(Integer[] arr, int pos, ArrayList<Integer[]> list) {

        // problem: method permutation does not prevent duplicate arrays
        // resolved at the end of program by creating hashset

        if (arr.length - pos == 1)
            list.add(arr.clone());
        else
            for (int i = pos; i < arr.length; i++) {
                swap(arr, pos, i);
                permutation(arr, pos + 1, list);
                swap(arr, pos, i);
            }
    }

    public static void swap(Integer[] arr, int pos1, int pos2) {
        int h = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = h;
    }

    public static HashSet<List<Integer>> checkForValidComb(List<Integer[]> list) {

        validOptions = new ArrayList<Integer[]>();

        boolean cond1 = false;
        boolean cond2 = false;
        boolean cond3 = false;
        boolean cond4 = false;
        boolean cond5 = false;
        boolean cond6 = false;

        // check all Arrays in List:

        for (int i = 0; i < list.size(); i++) {

            List<Boolean> checkList = new ArrayList<>();

            // check condition 1:
            // point 1 | 5 never followed by point 3

            for (int k = 0; k < list.get(i).length - 1; k++) {
                if (list.get(i)[k] == 1 | list.get(i)[k] == 5) {
                    if (list.get(i)[k + 1] == 3) {
                        cond1 = false;
                    } else {
                        cond1 = true;
                    }
                }

                checkList.add(cond1);
            }

            // check condition 2:
            // points cannot be accessed backwards again(e.g. 5-1-5)

            for (int k = 0; k < list.get(i).length - 2; k++) {

                if (list.get(i)[k] == list.get(i)[k + 2]) {
                    cond2 = false;
                } else {
                    cond2 = true;
                }
                checkList.add(cond2);
            }

            // check condition 3:
            // points cannot be accesed backwards (e.g. 5-1 [...] 1-5)

            for (int k = 0; k < list.get(i).length - 1; k++) {
                int temp1 = list.get(i)[k];
                int temp2 = list.get(i)[k + 1];

                for (int j = k + 2; j < list.get(i).length - 1; j++) {

                    if (list.get(i)[j] == temp2) {
                        if (list.get(i)[j + 1] == temp1) {
                            cond3 = false;
                        } else {
                            cond3 = true;
                        }
                    } else {
                        cond3 = true;
                    }

                    checkList.add(cond3);

                }
            }

            // check Condition 4:
            // two matching points cannot be adjacent

            for (int k = 0; k < list.get(i).length - 1; k++) {
                if (list.get(i)[k] == list.get(i)[k + 1]) {
                    cond4 = false;
                } else {
                    cond4 = true;
                }

                checkList.add(cond4);
            }

            // check Condition 5:
            // existing connections cannot be repeated (e.g. 5-1 [...] 5-1)

            for (int k = 0; k < list.get(i).length - 1; k++) {
                int temp1 = list.get(i)[k];
                int temp2 = list.get(i)[k + 1];

                for (int j = k + 2; j < list.get(i).length - 1; j++) {

                    if (list.get(i)[j] == temp1) {
                        if (list.get(i)[j + 1] == temp2) {
                            cond5 = false;
                        } else {
                            cond5 = true;
                        }
                    } else {
                        cond5 = true;
                    }

                    checkList.add(cond5);

                }
            }

            // check condition 6:
            // point 3 is never followed by 5 | 1

            // contained mistake --> "chekcList.add(cond6)" was misplaced, two rows too low 
            // which lead to adding false conditions to the List, when IterationPointer was not pointing at "3"

            for (int k = 0; k < list.get(i).length - 1; k++) {
                if (list.get(i)[k] == 3) {
                    if (list.get(i)[k + 1] == 1 | list.get(i)[k + 1] == 5) {
                        cond6 = false;
                    } else {

                        cond6 = true;
                    }
                    checkList.add(cond6);
                }

            }

            if (!checkList.contains(false)) {

                validOptions.add(list.get(i));

            }

        }

        HashSet<List<Integer>> finalOptions = new HashSet<>();

        // make sure, duplicate valid options get removed from final output

        finalOptions = checkForDuplicates(validOptions);

        return finalOptions;

    }

    public static HashSet<List<Integer>> checkForDuplicates(List<Integer[]> validOptions) {

        List<List<Integer>> temp = new ArrayList<>();

        for (int i = 0; i < validOptions.size(); i++) {

            temp.add(Arrays.asList(validOptions.get(i)));

        }

        HashSet<List<Integer>> finalOptions = new HashSet<>(temp);

        return finalOptions;
    }

    public static void compareSolutions(HashSet<List<Integer>> options, HashSet<List<Integer>> solutions) {

        List<List<Integer>> mySolutions = new ArrayList<List<Integer>>(options);
        List<List<Integer>> allSolutions = new ArrayList<List<Integer>>(solutions);

        for (int i = 0; i < allSolutions.size(); i++) {
            if (!mySolutions.contains(allSolutions.get(i))) {
                System.out.println(allSolutions.get(i));
            }
        }

    }

    public static void createCheckList() {

        solutionsToCompare = new HashSet<>();
        solution = new ArrayList<>();
        solution.add(new Integer[] { 1, 5, 4, 1, 2, 4, 3, 2, 5 });
        solution.add(new Integer[] { 1, 5, 4, 3, 2, 4, 1, 2, 5 });
        solution.add(new Integer[] { 1, 5, 2, 3, 4, 1, 2, 4, 5 });
        solution.add(new Integer[] { 1, 4, 5, 2, 3, 4, 2, 1, 5 });
        solution.add(new Integer[] { 1, 4, 2, 5, 4, 3, 2, 1, 5 });
        solution.add(new Integer[] { 1, 4, 3, 2, 5, 1, 2, 4, 5 });
        solution.add(new Integer[] { 1, 2, 5, 1, 4, 3, 2, 4, 5 });
        solution.add(new Integer[] { 1, 2, 4, 5, 1, 4, 3, 2, 5 });
        solution.add(new Integer[] { 1, 2, 3, 4, 1, 5, 2, 4, 5 });
        solution.add(new Integer[] { 1, 5, 4, 1, 2, 3, 4, 2, 5 });
        solution.add(new Integer[] { 1, 5, 2, 1, 4, 2, 3, 4, 5 });
        solution.add(new Integer[] { 1, 5, 2, 3, 4, 2, 1, 4, 5 });
        solution.add(new Integer[] { 1, 4, 2, 1, 5, 4, 3, 2, 5 });
        solution.add(new Integer[] { 1, 4, 2, 3, 4, 5, 1, 2, 5 });
        solution.add(new Integer[] { 1, 4, 3, 2, 5, 4, 2, 1, 5 });
        solution.add(new Integer[] { 1, 2, 5, 4, 2, 3, 4, 1, 5 });
        solution.add(new Integer[] { 1, 2, 4, 5, 2, 3, 4, 1, 5 });
        solution.add(new Integer[] { 1, 2, 3, 4, 5, 1, 4, 2, 5 });
        solution.add(new Integer[] { 1, 5, 4, 2, 1, 4, 3, 2, 5 });
        solution.add(new Integer[] { 1, 5, 2, 1, 4, 3, 2, 4, 5 });
        solution.add(new Integer[] { 1, 4, 5, 1, 2, 4, 3, 2, 5 });
        solution.add(new Integer[] { 1, 4, 2, 1, 5, 2, 3, 4, 5 });
        solution.add(new Integer[] { 1, 4, 2, 3, 4, 5, 2, 1, 5 });
        solution.add(new Integer[] { 1, 4, 3, 2, 4, 5, 1, 2, 5 });
        solution.add(new Integer[] { 1, 2, 5, 4, 3, 2, 4, 1, 5 });
        solution.add(new Integer[] { 1, 2, 4, 3, 2, 5, 1, 4, 5 });
        solution.add(new Integer[] { 1, 2, 3, 4, 5, 2, 4, 1, 5 });
        solution.add(new Integer[] { 1, 5, 4, 2, 3, 4, 1, 2, 5 });
        solution.add(new Integer[] { 1, 5, 2, 4, 1, 2, 3, 4, 5 });
        solution.add(new Integer[] { 1, 4, 5, 1, 2, 3, 4, 2, 5 });
        solution.add(new Integer[] { 1, 4, 2, 5, 1, 2, 3, 4, 5 });
        solution.add(new Integer[] { 1, 4, 3, 2, 1, 5, 4, 2, 5 });
        solution.add(new Integer[] { 1, 4, 3, 2, 4, 5, 2, 1, 5 });
        solution.add(new Integer[] { 1, 2, 4, 1, 5, 4, 3, 2, 5 });
        solution.add(new Integer[] { 1, 2, 4, 3, 2, 5, 4, 1, 5 });
        solution.add(new Integer[] { 1, 2, 3, 4, 2, 5, 1, 4, 5 });
        solution.add(new Integer[] { 1, 5, 4, 3, 2, 1, 4, 2, 5 });
        solution.add(new Integer[] { 1, 5, 2, 4, 3, 2, 1, 4, 5 });
        solution.add(new Integer[] { 1, 4, 5, 2, 4, 3, 2, 1, 5 });
        solution.add(new Integer[] { 1, 4, 3, 2, 1, 5, 2, 4, 5 });
        solution.add(new Integer[] { 1, 2, 5, 1, 4, 2, 3, 4, 5 });
        solution.add(new Integer[] { 1, 2, 4, 1, 5, 2, 3, 4, 5 });
        solution.add(new Integer[] { 1, 2, 3, 4, 1, 5, 4, 2, 5 });
        solution.add(new Integer[] { 1, 2, 3, 4, 2, 5, 4, 1, 5 });

        List<List<Integer>> temp = new ArrayList<>();

        for (int i = 0; i < solution.size(); i++) {

            temp.add(Arrays.asList(solution.get(i)));

        }

        solutionsToCompare = new HashSet<>(temp);

        // for (List<Integer> strCurrentList : solutionsToCompare) {
        // System.out.println(strCurrentList);
        // }

    }

    // public static void getIterationPointer() {

    //     Integer[] temp = new Integer[] { 1, 2, 4, 5, 1, 4, 3, 2, 5 };
       
    //         for (int i = 0; i < permutations.size(); i++) {
    //             if (Arrays.equals(permutations.get(i), temp)) {
    //                 System.out.println(i);
    //             }
            
    //     }
    // }

}
