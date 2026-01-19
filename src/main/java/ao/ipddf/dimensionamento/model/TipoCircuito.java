package ao.ipddf.dimensionamento.model;

public enum TipoCircuito {
    ILUMINACAO { public String toString() { return "Iluminação"; } },
    TOMADA_UG { public String toString() { return "Tomada UG"; } },
    TOMADA_UE { public String toString() { return "Tomada UE"; } },
    CLIMATIZACAO { public String toString() { return "Climatização"; } }
}