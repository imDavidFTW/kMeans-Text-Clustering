package vector;

public class VectorOperations
{
    // the norm of a vector v.
    public static double norm(double[] v) {
        double s = 0;
        for(int i = 0; i < v.length; i++) {
            s = s + v[i] * v[i];
        }
        return Math.sqrt(s);
    }

    // the dot product of two vectors v1 and v2.
    public static double dotProduct(double[] v1, double[] v2) {
        double s = 0;
        for(int i = 0; i < v1.length; i++) {
            s = s + v1[i] * v2[i];
        }
        return s;
    }

    // the cosine similarity between two vectors v1 and v2.
    public static double cosineSimilarity(double[] v1, double[] v2, double normV1, double normV2) {
        return dotProduct(v1, v2) / (normV1 * normV2);
    }
}
