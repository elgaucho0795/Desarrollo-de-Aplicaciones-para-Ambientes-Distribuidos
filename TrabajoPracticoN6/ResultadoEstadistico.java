import java.io.Serializable;

public class ResultadoEstadistico implements Serializable {
    private final double promedio;
    private final double maximo;
    private final double minimo;
    private final double desviacionEstandard;

    public ResultadoEstadistico(double promedio, double maximo, double minimo, double desviacionEstandard) {
        this.promedio = promedio;
        this.maximo = maximo;
        this.minimo = minimo;
        this.desviacionEstandard = desviacionEstandard;
    }

    public double getPromedio() { return promedio; }
    public double getMaximo() { return maximo; }
    public double getMinimo() { return minimo; }
    public double getDesviacionEstandard() { return desviacionEstandard; }

    @Override
    public String toString() {
        return String.format("Promedio: %.2f | Máximo: %.2f | Mínimo: %.2f | Desviación Estándar: %.2f",
                promedio, maximo, minimo, desviacionEstandard);
    }
}