package inferencia;

public class Stadistic {
    /**
     * Returns the maximun or minumun value from a float array setting the option.
     * 
     * @param numbersArray An array of samples.
     * @param option Boolean value.
     * @return {@code true}: Maximum or {@code false}: Minimum.
     */
    public float MinMax(float[] numbersArray, boolean option){
        float x = numbersArray[0];

        if (option) {
            for (int i = 0; i < numbersArray.length; i++) {
                if (x < numbersArray[i]) {
                    x = numbersArray[i];
                }
            }

            return x;
        } else {
            for (int i = 0; i < numbersArray.length; i++) {
                if (x > numbersArray[i]) {
                    x = numbersArray[i];
                }
            }

            return x;
        }
    }

    /**
     * Returns the lowers limits from a sample with that intervales and amplitudes.
     * 
     * @param min Minimum value from a sample
     * @param interval Number of intervals about the sample
     * @param intervalAmplitude Constant increase
     * @return the classes about a stadistic sample
     */
    public float[] ClassRange(float min, int interval, float intervalAmplitude){
        float[] array = new float[interval + 1];
        
        array[0] = min;
        for (int i = 1; i < array.length; i++) {
            array[i] = array[i - 1] + intervalAmplitude;
        }
        if (intervalAmplitude < 1) { // to fix decimal interval amplitudes
            for (int i = 0; i < array.length; i++) {
                array[i] *= 100;
                array[i] = Math.round(array[i]);
                array[i] /= 100;
            }
        }

        return array;
    }
    
    /**
     * Returns the frecuency of a sample acording that target.
     * 
     * @param numbersArray a numbers array
     * @param target specific value
     * @return the frecuency acording its target
     */
    public int Frecuency(float[] numbersArray, double target) {
        int frecuency = 0;
        for (int i = 0; i < numbersArray.length; i++) {
            if (numbersArray[i] == target) {
                frecuency++;
            }
        }

        return frecuency;
    }

    /**
     * Return the frecuency of a sample acording its target limits from a minimun and maximun values
     * 
     * @param numbersArray a numbers array
     * @param target lower limit target
     * @param lastTarget upper limit target
     * @return the frecuency acording its target limits
     */
    public int Frecuency(float[] numbersArray, double target, double lastTarget) {
        int frecuency = 0;
        for (int i = 0; i < numbersArray.length; i++) {
            if (numbersArray[i] >= target && numbersArray[i] <= lastTarget) {
                frecuency++;
            }
        }

        return frecuency;
    }

    /**
     * Returns the statistical Median from a sample.
     * 
     * @param numberClass An array of the Classes from a sample.
     * @param n sample length.
     * @param FCumulative Cumulative Frequency.
     * @param intervalAmp Interval Amplitude.
     * @param avg Average.
     * @return float value.
     */
    public float Median(float[] numberClass, int n, int[] FCumulative, float intervalAmp, float avg) {
        float m = 0;

        for (int i = 0; i < FCumulative.length; i++) {
            if (avg <= numberClass[i]) {
                m = numberClass[i - 1] + ((((n / 2) - FCumulative[i - 2]) / n) * (intervalAmp));
                break;
            }
        }

        return m;
    }

    /**
     * Returns the Statistical Mode from a sample.
     * 
     * @param classes The lower limits from the sample.
     * @param frequencies The frequencies array from the sample.
     * @param amplitude The amplitude from the statistical sample.
     * @return float value.
     */
    public float Mode(float[] classes, int[] frequencies, float amplitude) {
        int x = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > x) {
                x = frequencies[i];
            }
        }

        int index = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (x == frequencies[i]) {
                index = i;
                break;
            }
        }
        
        float d1;
        try {
            d1 = x - frequencies[index - 1];    
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            d1 = x - 0;
        }

        float d2;
        try {
            d2 = x - frequencies[index + 1];    
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            d2 = x - 0;
        }

        float mode = classes[index] + amplitude * (d1 / (d1 + d2));

        return mode;
    }
}