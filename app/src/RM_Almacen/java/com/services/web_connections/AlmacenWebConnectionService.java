package com.services.web_connections;

import com.utils.exception.*;
import com.utils.EnvironmentVariables;
import com.services.models.InsumoAlmacenModel;
import com.services.parsers.InsumoAlmacenXMLParser;

import java.util.List;

/**
 * Capa: Services.
 * Esta clase es la encargada demanejar las peticiones del almacen con el servidor.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */
public class AlmacenWebConnectionService extends SimpleWebConnectionService {

    /**
     * Path local.
     */
    final String P = "com.restmanager.almacen/";

    /**
     * Etiquetas a llamar.
     */
    final String ENTRADA = "ENTRADA_",
            TICKET_COMPRA = "IMPRIMIR_TICKET_COMPRA",
            ESTADO_ALMACEN = "IMPRIMIR_ESTADO_ALMACEN",
            SALIDA = "SALIDA_",
            MERMA = "MERMAR",
            LISTA_IPV = "IPVS",
            FILTRAR_ = "FILTRAR_";

    /**
     * Usuario que lo esta usando.
     * NO SE USA
     */
    private String user;

    /**
     * Codigo del almacen que se esta trabajando.
     * NO SE USA
     */
    private String codAlmacen;

    /**
     * Constructor del WCS.
     *
     * @param user       Usuario que lo esta usando.
     * @param codAlmacen Codigo del almacen que se esta trabajando.
     */
    public AlmacenWebConnectionService(String user, String codAlmacen) {
        super();
        this.user = user;
        this.codAlmacen = codAlmacen;
        path += P;
    }

    /**
     * TODO: ni idea.
     *
     * @return Lista de los insumos.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public List<InsumoAlmacenModel> getPrimerAlmacen() throws ServerErrorException, NoConnectionException {
        return new InsumoAlmacenXMLParser().fetch(path);
    }

    /**
     * Da entrada a un producto en el almacen.
     *
     * @param i        Modelo del insumo.
     * @param cantidad Cantidad a ingresar.
     * @param monto    Monto de lo que cuesta esa cantidad.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String darEntrada(InsumoAlmacenModel i, float cantidad, float monto) throws ServerErrorException, NoConnectionException {
        return connect(path
                + ENTRADA
                + i.getCodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo() + "_"
                + cantidad + "_" + monto);
    }

    /**
     * Sa salida a un producto del almacen.
     *
     * @param i                 Modelo del insumo.
     * @param cantidad          Cantidad a dar salida.
     * @param codPtoElaboracion punto de elaboracion.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String darSalida(InsumoAlmacenModel i, float cantidad, String codPtoElaboracion) throws ServerErrorException, NoConnectionException {
        return connect(path
                + SALIDA
                + i.getCodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + codPtoElaboracion);
    }

    /**
     * Da rebaja como Merma a un producto del almacen.
     *
     * @param i        Modelo del insumo.
     * @param cantidad Cantidad a dar salida.
     * @param razon    Razon.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String rebajar(InsumoAlmacenModel i, float cantidad, String razon) throws ServerErrorException, NoConnectionException {
        return connect(path
                + MERMA
                + "_" + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + razon);
    }

    /**
     * Obtiene los nombres de las cocinas por IPV.
     *
     * @param codInsumo Codigo del insumo.
     * @return Nombre de las cocinas.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getCocinasNamesForIPV(String codInsumo) throws ServerErrorException, NoConnectionException {
        return connect(path + LISTA_IPV + "_" + codInsumo).split(",");
    }

    /**
     * Filtra los insumos por el codigo del punto de elaboracion.
     *
     * @param codPtoElaboracion Codigo del punto de elaboracion.
     * @return Lista con los insumos filtrados.
     */
    public List<InsumoAlmacenModel> filterBy(String codPtoElaboracion) {
        return new InsumoAlmacenXMLParser().fetch(path + FILTRAR_ + codPtoElaboracion);
    }

    /**
     * Manda a imprimir el ticket de compra.
     *
     * @return true si lo imprime, false cualquier otro caso.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean imprimirTicketCompra() throws ServerErrorException, NoConnectionException {
        return connect(path + TICKET_COMPRA).equals(EnvironmentVariables.PETITION_TRUE);
    }

    /**
     * Manda a imprimir el estado actual del almacen.
     *
     * @return true si lo imprime, false cualquier otro caso.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean imprimirEstadoActualAlmacen() throws ServerErrorException, NoConnectionException {
        return connect(path + ESTADO_ALMACEN).equals(EnvironmentVariables.PETITION_TRUE);
    }

}
