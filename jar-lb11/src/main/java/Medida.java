import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Medida {
    private Integer idMedida;
    private Float usoProcessador;
    private Float usoRam;
    private Float usoDisco;
    private Timestamp dataMedida;
    private String unidadeMedida;

    public Medida () {}

    public Medida(Integer idMedida, Float usoProcessador, Float usoRam, Float usoDisco, Timestamp dataMedida, String unidadeMedida) {
        this.idMedida = idMedida;
        this.usoProcessador = usoProcessador;
        this.usoRam = usoRam;
        this.usoDisco = usoDisco;
        this.dataMedida = dataMedida;
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    public Float getUsoProcessador() {
        return usoProcessador;
    }

    public void setUsoProcessador(Float usoProcessador) {
        this.usoProcessador = usoProcessador;
    }

    public Float getUsoRam() {
        return usoRam;
    }

    public void setUsoRam(Float usoRam) {
        this.usoRam = usoRam;
    }

    public Float getUsoDisco() {
        return usoDisco;
    }

    public void setUsoDisco(Float usoDisco) {
        this.usoDisco = usoDisco;
    }

    public Timestamp getDataMedida() {
        return dataMedida;
    }

    public void setDataMedida(Timestamp dataMedida) {
        this.dataMedida = dataMedida;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    @Override
    public String toString() {
        return "Medida" +
                "idMedida=" + idMedida +
                ", usoProcessador=" + usoProcessador +
                ", usoRam=" + usoRam +
                ", usoDisco=" + usoDisco +
                ", dataMedida=" + dataMedida +
                ", unidadeMedida='" + unidadeMedida + '\'' +
                '}';
    }
}
