package inferencia;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    Scanner keyboard = new Scanner(System.in);

    /**
    * <p>Creates a float array setting manually or automatically configuration.
    *
    * <p>Manual Configuration:
    * You input the length of the array and then enter the data.
    * 
    * <p>Automatic Configuration:
    * You input a single string containing "," to later separate the elements based on that character.
    *
    * @return float array.
    * @throws java.util.InputMismatchExceptions Incompatible data types.
    * @throws java.lang.NumberFormatException Invalid string conversion.
    * @throws Exception The number of entered or recognized samples is less than 1.
    */
    public float[] Sample() {
        String string;
        float[] array;
        int n;
        
        try {
            System.out.print("Establezca un modo de ejecucion\n[M / A] => ");
            string = keyboard.nextLine();
            string = string.toLowerCase();

            if (string.startsWith("a")) { // automatic method
                System.out.println("\nModo automatico establecido");

                System.out.print("Escriba la muestra a procesar separada por comas (Presione INTRO 2 veces para terminar)\n=> ");
                string = keyboard.nextLine();

                if (!string.contains(",")) {
                    throw new Error("ERROR: Muestra no valida (el numero de elementos a evaluar debe ser mayor a 1)");
                }

                String[] fragmments = string.split(",");
                n = fragmments.length;
                array = new float[n];

                for (int i = 0; i < n; i++) {
                    fragmments[i] = fragmments[i].trim();
                    array[i] = Float.parseFloat(fragmments[i]);
                }

                return array;
            } else { // manual method
                System.out.println("Modo manual establecido");

                System.out.print("\nIngrese el tamaño de la muestra\n=> ");
                n = keyboard.nextInt();

                if (n <= 1) {
                    throw new Error("ERROR: Muestra no valida (el numero de elementos a evaluar debe ser mayor a 1)");
                }

                array = new float[n];
                for (int i = 0; i < n; i++) {
                    System.out.printf("(%d/%d) => ", i + 1, n);
                    array[i] = keyboard.nextFloat();
                }

                return array;
            }
        } catch (java.util.InputMismatchException e) {
            throw new Error("Tipo de dato no valido");
        } catch (java.lang.NumberFormatException e) {
            throw new Error("Error al convertir");
        }
    }

    /**
     * Ask to do "Tallo hoja" and a ordered sample.
     * 
     * @return boolean value.
     */
    public boolean AskConfirm() {
        String string;

        keyboard.nextLine();

        System.out.println("\n¿Deseas desarrollar metodos de comprobacion?\n");
        System.out.println("Estos incluyen METODO TALLO HOJA y MATRIZ ORDENADA");
        System.out.print("[ADVERTENCIA] TALLO HOJA Puede generarse de forma erronea\n[N / S] => ");
        string = keyboard.nextLine();
        if (string.equalsIgnoreCase("s")) {            
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>Make "Tallo hoja".
     * 
     * <p>Sets a groups from the sample and sort the numbers acording that group.
     * 
     * @param numbersArray A sample.
     */
    public void TreeSheet(float[] numbersArray) {
        Stadistic stadistic = new Stadistic();
        float min = stadistic.MinMax(numbersArray, false);
        float max = stadistic.MinMax(numbersArray, true);

        int exp = 0;
        while (min / Math.pow(10, exp) >= 10) {
            exp++;
        }

        int target = (int) (min / Math.pow(10, exp));
        int lastTarget = (int) (max / Math.pow(10, exp));
        int[] gruop = new int[lastTarget - target + 1];
        int x = 0;
        for (int i = 0; i < gruop.length; i++) {
            gruop[i] = target + x;
            x++;
        }

        int size = 0;
        int f = 0;
        for (int i = 0; i < gruop.length; i++) {
            f = stadistic.Frecuency(numbersArray, gruop[i] * Math.pow(10, exp), ((gruop[i] + 0.99999) * Math.pow(10, exp)));
            if (f > size) {
                size = f;
            }
        } 

        System.out.println("\nTALLO HOJA");
        String[][] tree = new String[gruop.length][size + 1];
        int sheet = 0;;
        for (int i = 0; i < tree.length; i++) {
            for (int j = 0; j < tree[i].length; j++) {
                if (j == 0) {
                    System.out.print(" ");
                    tree[i][j] = Integer.toString(gruop[i]);
                    System.out.print(tree[i][j] + " | ");
                } else {
                    for (int j2 = 0; j2 < numbersArray.length; j2++) {
                        if ((numbersArray[j2] - (gruop[i] * Math.pow(10, exp)) < 10) && (numbersArray[j2] - (gruop[i] * Math.pow(10, exp)) >= 0)) {
                            sheet = (int) (numbersArray[j2] - (gruop[i] * Math.pow(10, exp)));
                            tree[i][j] = Integer.toString(sheet);

                            numbersArray[j2] /= Math.pow(10, exp + 1);
                            break;
                        }
                    }

                    if (tree[i][j] == null) {
                        tree[i][j] = " ";
                    }

                    if (j != tree[i].length - 1) {
                        System.out.print(tree[i][j] + " ");
                    } else {
                        System.out.print(tree[i][j]);
                    }
                }
            }
            System.out.println();
        }

        for (int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] *= Math.pow(10, exp + 1);
            numbersArray[i] = Math.round(numbersArray[i]);
        }
    }

    /**
     * Make an ordered matrix sample.
     * 
     * @param numbersArray A sample.
     */
    public void SortSample(float[] numbersArray) {
        Arrays.sort(numbersArray);
        
        int n = numbersArray.length;
        int c = (int) Math.floor(Math.sqrt(n));
        int r = 0;
        int x = -1;

        while ((c * r) < n) {
            r++;
        }

        String[][] square = new String[r][c];

        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if (x < n - 1) {
                    x++;
                    square[i][j] = " " + numbersArray[x] + " ";
                } else {
                    square[i][j] = "";
                }
            }
        }

        System.out.println("\nMATRIZ ORDENADA");
        MakeTable(square, false);
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                System.out.print(square[i][j]); 
            }
            System.out.println();
        }
    }

    /**
     * <p>Complete a String with another until it has a specific size.
     * <p>If string length is higher than the size, the strings does not change.
     * 
     * @param string Original String.
     * @param addString Another String.
     * @param size Specific size.
     * @return A Strting.
     */
    public String ToComplete(String string, String addString, int size) {
        if (string.length() >= size) {
            return string;
        } else {
            char[] c = addString.toCharArray();
            
            d: do {
                for (int i = 0; i < c.length; i++) {
                    string += c[i];
                    if (string.length() == size) {
                        break d;
                    }
                }
            } while (string.length() < size);

            return string;
        }
    }

    /**
     * Retruns a Stirng Matrix whose cells have the same length.
     * 
     * @param matrix String matrix.
     * @param centerItems boolean value.
     * @return String Matrix
     */
    public String[][] MakeTable(String[][] matrix, boolean centerItems) {
        int x = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].length() > x) {
                    x = matrix[i][j].length();
                }        
            }
        }

        if (centerItems) {
            x += 2;
            int space;
            String writer;

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                space = x - matrix[i][j].length();
                space = (int) (Math.floor(space / 2));
                writer = ToComplete("", " ", space);
                matrix[i][j] = writer + matrix[i][j];
                matrix[i][j] = ToComplete(matrix[i][j], " ", x);
                }
            }
        } else {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = ToComplete(matrix[i][j], " ", x);
                }
            }
        }

        return matrix;
    }

    /**
     * Draw a table with vertical, horizontal and union characters. 
     * 
     * @param matrix String matrix.
     * @param h horizontal character.
     * @param v vertical character.
     * @param cross union character.
     */
    public void DrawTable(String[][] matrix, char h, char v, char cross) {
        String line = "";
        line = ToComplete(line, Character.toString(h), matrix[0][0].length());;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j != matrix[i].length -1) {
                    System.out.print(matrix[i][j] + v);
                } else {
                    System.out.print(matrix[i][j]);
                }
            }

            System.out.println();
            if (i == 0) {
                System.out.print(line);
                for(int k = 0; k < matrix[i].length; k++) {
                    if (k != matrix[i].length -1) {
                        System.out.print(cross + line);
                    }
                }
                System.out.println();
            }
        }
    }
}