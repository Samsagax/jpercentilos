/*
 *  Copyright (C) 2011 Joaquín Ignacio Aramendía <samsagax@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jpercentilos.res;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Objeto para el manejo de tablas de percentilos y obtención de resultados.
 *
 * @author Joaquín Ignacio Aramendía <samsagax@gmail.com>
 */
public final class TablaPercentilos extends Table {

    private final String name;
    static final int S_T = 3;   // S(t) index in table
    static final int M_T = 2;   // M(t) index in table
    static final int L_T = 1;   // L(t) index in table
//    static final int SD = 4;    // SD(t) index in table (not necesary?)
    static final int VALUE = 0;  // Unit index in table
    private double lastValue;
    private LMS lastLMS;
    private static TablaNormales TABLA_NORMALES = initializeTablaNormales();

    /**
     * Crea un objeto <code>TablaPercentilos<code> para el paciente y tipo
     * especificado por <code>profile<code> y <code>tipo<code>.
     * @param profile
     * @param tipo
     * @throws IOException
     */
    public TablaPercentilos(PatientProfile profile, Tipo tipo) throws IOException {
        this(profile.getTableFile(tipo));
    }

    public TablaPercentilos(TextFileReaderME.ResourceFile file) throws IOException {
        super(file);
        this.name = file.getPath();
    }

    private static TablaNormales initializeTablaNormales() {
        if (TABLA_NORMALES == null) {
            try {
                TABLA_NORMALES = new TablaNormales();
            } catch (Exception e) {
                return null;
            }
        }
        return TABLA_NORMALES;

    }

    /**
     * Obtiene el percentilo correspondiente al zScore especificado. Básicamente
     * es un cálculo de fractiles.
     * @param zScore
     * @return
     */
    public double getCentile(double zScore) {
        return TABLA_NORMALES.getPz(zScore);
    }

    /**
     * Devuelve la cadena con el nombre de ésta tabla (el nombre del archivo del
     * que fue leída).
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el zScore para el valor observado (<code>observedValue<code>) y
     * valor de entrada a la tabla (<code>forInputValue<code>), éste último es
     * el devuelto por el método <code>getInputValue<code> del objeto
     * <code>PatientPrfile<code>.
     * @param observedValue
     * @param forInputValue
     * @return
     */
    public double getZScore(double observedValue, double forInputValue) {
        LMS lms = getLms(forInputValue);
        System.out.println("LMS: " + lms.getL() + ", " + lms.getM() + ", " + lms.getS());
        double L = lms.getL(),
                M = lms.getM(),
                S = lms.getS();
        double pow = JPMath.pow(observedValue / M, L);
        double zS = (pow - 1) / (L * S);
        if (JPMath.abs(zS) > 3) {
            if (zS < -3) {
                double s23neg = M * (JPMath.pow(1 + L * S * (-2), L) - JPMath.pow(1 + L * S * (-3), L));
                double s3neg = M * JPMath.pow(1 + L * S * (-3), L);
                zS = -3 + (observedValue - s3neg) / (s23neg);
            } else {
                double s23pos = M * (JPMath.pow(1 + L * S * 3, L) - JPMath.pow(1 + L * S * 2, L));
                double s3pos = M * JPMath.pow(1 + L * S * 3, L);
                zS = 3 + (observedValue - s3pos) / (s23pos);
            }
        }
        System.out.println("z-score: " + zS);
        return zS;
    }

    /**
     * Devuelve el percentilo para el valor observado (<code>observedValue<code>)
     * y valor de entrada a la tabla (<code>forInputValue<code>), éste último es
     * el devuelto por el método <code>getInputValue<code> del objeto
     * <code>PatientPrfile<code>.
     * @param observedValue
     * @param forInputValue
     * @return
     */
    public double getCentile(double observedValue, double forInputValue) {
        double zScore = getZScore(observedValue, forInputValue);
        double centile = TABLA_NORMALES.getPz(zScore);
        System.out.println("Percentilo: " + centile);
        return centile;
    }

    private LMS getLms(double value) {
        if (value != lastValue || value == 0) {
            double L, M, S;
            try {
                int index = binarySearch(value, VALUE);
                L = tabla[index][L_T];
                M = tabla[index][M_T];
                S = tabla[index][S_T];
            } catch (NoSuchElementException e) {
                try {
                    int lower = searchFloorValue(value, VALUE);
                    int higher = searchCeilValue(value, VALUE);
                    L = interpolateValue(value, tabla[lower][VALUE], tabla[higher][VALUE], tabla[lower][L_T], tabla[higher][L_T]);
                    M = interpolateValue(value, tabla[lower][VALUE], tabla[higher][VALUE], tabla[lower][M_T], tabla[higher][M_T]);
                    S = interpolateValue(value, tabla[lower][VALUE], tabla[higher][VALUE], tabla[lower][S_T], tabla[higher][S_T]);
                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    throw arrayIndexOutOfBoundsException;
                }
            }
            lastValue = value;
            lastLMS = new LMS(L, M, S);
        }
        return lastLMS;
    }

    private static class LMS {

        private final double L, M, S;

        public LMS(double L, double M, double S) {
            this.L = L;
            this.M = M;
            this.S = S;
        }

        public double getL() {
            return L;
        }

        public double getM() {
            return M;
        }

        public double getS() {
            return S;
        }
    }

    /**
     * Enumeración de tupos de tablas.
     */
    public static final class Tipo {

        private final String name;
        public static final Tipo TALLA_A_EDAD = new Tipo("TALLA-EDAD");    //leido en cm
        public static final Tipo PESO_A_EDAD = new Tipo("PESO-EDAD");      // Leído en Kg
        public static final Tipo IMC_A_EDAD = new Tipo("IMC-EDAD");        //
        public static final Tipo PC_A_EDAD = new Tipo("PC-EDAD");          // Leído en mm
        public static final Tipo PESO_A_TALLA = new Tipo("PESO-TALLA");

        public Tipo(String name) {
            this.name = name;
        }

        public String bitPath() {
            return toString();
        }

        public String toString() {
            return name;
        }

        public static Tipo[] values() {
            Tipo[] t = {
                TALLA_A_EDAD,
                PESO_A_EDAD,
                IMC_A_EDAD,
                PC_A_EDAD,
                PESO_A_TALLA
            };
            return t;
        }
    }
}
