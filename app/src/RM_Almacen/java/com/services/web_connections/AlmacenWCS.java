package com.services.web_connections;

import com.services.models.IpvRegistroModel;
import com.services.models.TransaccionModel;
import com.utils.exception.*;
import com.services.models.InsumoAlmacenModel;

import java.util.HashMap;
import java.util.List;

/**
 * Capa: Services.
 * Esta clase es la encargada demanejar las peticiones del almacen con el servidor.
 *
 * @extends SimpleWebConnectionService ya que es un servicio.
 */
public class AlmacenWCS extends SimpleWebConnectionService {

    /**
     * Path local.
     */
    final String P = "almacen/";

    /**
     * Etiquetas a llamar.
     */
    final String ENTRADA = "ENTRADA",
            TICKET_COMPRA = "IMPRIMIR-TICKET-COMPRA",
            ESTADO_ALMACEN = "IMPRIMIR-ESTADO-ALMACEN",
            SALIDA = "SALIDA",
            MERMA = "MERMAR",
            LISTA_IPV = "IPVS-DE-INSUMO",
            FILTRAR = "FILTRAR",
            REGISTRO_IPVS = "REGISTRO-IPVS",
            AGREGAR_INSUMO = "AGREGAR-INSUMO",
            OPERACIONES_REALIZADAS = "OPERACIONES-REALIZADAS";

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
    public AlmacenWCS(String codAlmacen) {
        super();
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
    public List<InsumoAlmacenModel> getPrimerAlmacen() throws Exception {
        String resp = connect(path, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, InsumoAlmacenModel.class));
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
    public String darEntrada(InsumoAlmacenModel i, float cantidad, float monto) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("almacenCod", i.getCodAlmacen());
        hm.put("insumoCod", i.getInsumoAlmacenPKModel().getInsumocodInsumo());
        hm.put("cantidad", cantidad);
        hm.put("monto", monto);
        return connect(path + ENTRADA, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
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
    public String darSalida(InsumoAlmacenModel i, float cantidad, String codPtoElaboracion) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("almacenCod", i.getCodAlmacen());
        hm.put("insumoCod", i.getInsumoAlmacenPKModel().getInsumocodInsumo());
        hm.put("cantidad", cantidad);
        hm.put("destino", codPtoElaboracion);
        return connect(path + SALIDA, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
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
    public String rebajar(InsumoAlmacenModel i, float cantidad, String razon) throws Exception {
        /*HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("almacenCod", i.getCodAlmacen());
        hm.put("insumonCod", i.getInsumoAlmacenPKModel().getInsumocodInsumo());
        hm.put("cantidad", cantidad);
        hm.put("razon", razon);
        return connect(path + SALIDA, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);


        return connect(path
                + MERMA
                + "_" + i.getInsumoAlmacenPKModel().getAlmacencodAlmacen()
                + "_" + i.getInsumoAlmacenPKModel().getInsumocodInsumo()
                + "_" + cantidad
                + "_" + razon);*/
        return "";
    }

    /**
     * Obtiene los nombres de las cocinas por IPV.
     *
     * @param codInsumo Codigo del insumo.
     * @return Nombre de las cocinas.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getCocinasNamesForIPV(String codInsumo) throws Exception {
        String URL = path + LISTA_IPV + "?insumoCod=" + codInsumo;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructArrayType(String.class));
    }

    /**
     * Filtra los insumos por el codigo del punto de elaboracion.
     *
     * @param codPtoElaboracion Codigo del punto de elaboracion.
     * @return Lista con los insumos filtrados.
     */
    public List<InsumoAlmacenModel> filterBy(String codPtoElaboracion) throws Exception {
        String URL = path + FILTRAR + "?ptoElab=" + codPtoElaboracion;
        String resp = connect(URL, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, InsumoAlmacenModel.class));
    }

    /**
     * Manda a imprimir el ticket de compra.
     *
     * @return true si lo imprime, false cualquier otro caso.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean imprimirTicketCompra() throws Exception {
        connect(path + TICKET_COMPRA, null, super.TOKEN, HTTPMethod.GET);
        return true;
    }

    /**
     * Manda a imprimir el estado actual del almacen.
     *
     * @return true si lo imprime, false cualquier otro caso.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean imprimirEstadoActualAlmacen() throws Exception {
        connect(path + ESTADO_ALMACEN, null, super.TOKEN, HTTPMethod.GET);
        return true;
    }

    public List<IpvRegistroModel> getIPVRegistro(String codCocina) throws Exception {
        String resp = connect(path + REGISTRO_IPVS + "?ptoElab=" + codCocina, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, IpvRegistroModel.class));
    }

    public List<TransaccionModel> getOperacionesRealizadas() throws Exception {
        String resp = connect(path + OPERACIONES_REALIZADAS, null, super.TOKEN, HTTPMethod.GET);
        return om.readValue(resp, om.getTypeFactory().constructCollectionType(List.class, TransaccionModel.class));
    }

    public boolean agregarInsumo(String nombre, float est, String um) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("insumoNombre", nombre);
        hm.put("estimacionStock", est);
        hm.put("um", um);
        connect(path + AGREGAR_INSUMO, om.writeValueAsString(hm), super.TOKEN, HTTPMethod.POST);
        return true;
    }
}
