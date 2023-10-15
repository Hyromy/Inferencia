package inferencia;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        Stadistic stadistic = new Stadistic();

        float[] muestra = program.Sample();

        boolean human = program.AskConfirm();
        if (human) {
            program.TreeSheet(muestra);
            program.SortSample(muestra);
        }

        float min = stadistic.MinMax(muestra, false);
        float max = stadistic.MinMax(muestra, true);
        float range = max - min;

        int interval = (int) Math.round((10 * range) / max);
        float intervalAmplitude;
        if ((range / interval) < 1) {
            intervalAmplitude = range / interval;
        } else {
            intervalAmplitude = (float) Math.ceil(range / interval);
        }

        float[] clase = stadistic.ClassRange(min, interval, intervalAmplitude);
        String[] claseString = new String[interval];

        float[] marca = new float[interval];
        String[] marcaString = new String[interval];
        for (int i = 0; i < marca.length; i++) {
            marca[i] = (clase[i] + clase[i + 1]) / 2;
            marca[i] *= 100;
            marca[i] = Math.round(marca[i]);
            marca[i] /= 100;
        }

        int[] F = new int[interval];
        String[] FString = new String[interval];
        for (int i = 0; i < F.length; i++) {
            if (i == 0) {
                F[i] = stadistic.Frecuency(muestra, clase[i], clase[i + 1]);
            } else {
                F[i] = stadistic.Frecuency(muestra, clase[i] + 0.00001, clase[i + 1]);
            }
        }

        float[] FRel = new float[interval];
        String[] FRelString = new String[interval];
        for (int i = 0; i < FRel.length; i++) {
            FRel[i] = (float) F[i] / muestra.length;
            FRel[i] *= 1000;
            FRel[i] = (float) Math.floor(FRel[i]);
            FRel[i] /= 1000;
        }

        int[] FAcu = new int[interval];
        String[] FAcuString = new String[interval];
        FAcu[0] = F[0];
        for (int i = 1; i < FAcu.length; i++) {
            FAcu[i] = FAcu[i - 1] + F[i];
        }

        float[] producto = new float[interval];
        String[] productoString = new String[interval];
        for (int i = 0; i < producto.length; i++) {
            producto[i] = marca[i] * F[i];
        }

        String[][] table = new String[interval + 1][6];
        table[0][0] = "CLASE";
        table[0][1] = "MARCA";
        table[0][2] = "FRECUENCIA";
        table[0][3] = "F. RELATIVA";
        table[0][4] = "F. ACUMULADA";
        table[0][5] = "SUMATORIA";
        for(int i = 0; i < interval; i++) {
            claseString[i] = Float.toString(clase[i]);
            claseString[i] += (" - " + Float.toString(clase[i + 1]));
            table[i + 1][0] = claseString[i]; 
            
            marcaString[i] = Float.toString(marca[i]);
            table[i + 1][1] = marcaString[i];

            FString[i] = Integer.toString(F[i]);
            table[i + 1][2] = FString[i];

            FRelString[i] = Float.toString(FRel[i]);
            table[i + 1][3] = FRelString[i];

            FAcuString[i] = Integer.toString(FAcu[i]);
            table[i + 1][4] = FAcuString[i];

            productoString[i] = Float.toString(producto[i]);
            table[i + 1][5] = productoString[i];
        }

        System.out.println();
        System.out.println("MUESTRAS: " + muestra.length);
        System.out.println("MINIMO: " + min);
        System.out.println("MAXIMO: " + max);
        System.out.println("RANGO: " + range);
        System.out.println("INTERVALOS: " + interval);
        System.out.println("AMPLITUD: " + intervalAmplitude);

        float sumaBruta = 0;
        for (int i = 0; i < producto.length; i ++) {
            sumaBruta +=  producto[i];
        }
        float suma = 0;
        for (int i = 0; i < muestra.length; i++) {
            suma += muestra[i];
        }

        float mediana = stadistic.Median(clase, muestra.length, FAcu, intervalAmplitude, (suma / muestra.length));
        float moda = stadistic.Mode(clase, F, intervalAmplitude);

        System.out.println();
        System.out.println("SUMATORIA: " + suma);
        System.out.println("MEDIA: " + (suma / muestra.length));
        System.out.println("MEDIANA: " + mediana);
        System.out.println("MODA: " + moda);

        System.out.println();
        program.MakeTable(table, true);
        program.DrawTable(table, '-', '|', '+');
        System.out.println();

        float error = (sumaBruta * 100) / suma;
        System.out.println("Precisión " + error + "%\n\n");
    }
}