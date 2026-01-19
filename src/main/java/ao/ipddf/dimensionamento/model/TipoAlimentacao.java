package ao.ipddf.dimensionamento.model;

/**
 * Enumerações
 */
public enum TipoAlimentacao {
    MONOFASICA { public String toString() { return "Monofásica"; } },
    BIFASICA { public String toString() { return "Bifásica"; } },
    TRIFASICA { public String toString() { return "Trifásica"; } }
}