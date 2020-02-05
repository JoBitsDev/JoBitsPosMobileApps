package com.controllers;

import android.content.Context;

import com.services.models.IpvRegistroModel;
import com.services.models.TransaccionModel;
import com.services.web_connections.*;
import com.services.models.InsumoAlmacenModel;
import com.utils.adapters.AlmacenInsumoAdapter;
import com.utils.adapters.IPVsAdapter;
import com.utils.adapters.OperacionesAdapter;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.List;

/**
 * Capa: Controllers
 * Clase controladora de LoginActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller plicacion.
 */
public class PantallaPrincipalController extends BaseController {

    /**
     * WCS del almacen.
     */
    AlmacenWCS almacenWCS;

    /**
     * Constructor de la clase.
     */
    public PantallaPrincipalController() {
        almacenWCS = new AlmacenWCS(null);
    }

    /**
     * Manda a imprimir el ticket de compra.
     *
     * @return true si lo imprime, false cualquier otro caso.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean imprimirTicketCompra() throws Exception {
        return almacenWCS.imprimirTicketCompra();
    }

    /**
     * Manda a imprimir el estado actual del almacen.
     *
     * @return true si lo imprime, false cualquier otro caso.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public boolean imprimirEstadoActualAlmacen() throws Exception {
        return almacenWCS.imprimirEstadoActualAlmacen();
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
    public void darEntrada(InsumoAlmacenModel i, float cantidad, float monto) throws Exception {
        almacenWCS.darEntrada(i, cantidad, monto);
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
    public void darSalida(InsumoAlmacenModel i, float cantidad, String codPtoElaboracion) throws Exception {
        almacenWCS.darSalida(i, cantidad, codPtoElaboracion);
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
    public void rebajar(InsumoAlmacenModel i, float cantidad, String razon) throws Exception {
        almacenWCS.rebajar(i, cantidad, razon);
    }

    /**
     * TODO: ni idea.
     *
     * @return Lista de los insumos.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public List<InsumoAlmacenModel> getPrimerAlmacen() throws Exception {
        return almacenWCS.getPrimerAlmacen();
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
        return almacenWCS.getCocinasNamesForIPV(codInsumo);
    }

    /**
     * Obtiene los nombres de todas las cocinas.
     *
     * @return Un arreglo de String con los nombres de todas las cocinas.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public String[] getCocinasNames() throws Exception {
        return new CocinaWCS().getCocinasNames();
    }

    /**
     * Filtra los insumos por el codigo del punto de elaboracion.
     *
     * @param codPtoElaboracion Codigo del punto de elaboracion.
     * @return Lista con los insumos filtrados.
     */
    public List<InsumoAlmacenModel> filterBy(String codPtoElaboracion) throws Exception {
        return almacenWCS.filterBy(codPtoElaboracion);
    }

    /**
     * Obtiene el adapter para los insumos del almacen.
     *
     * @param c            Base activity donde se muestra el adapter
     * @param listaInsumos codigo del R.id de la lista de insumos.
     * @return El adapter para los insumos del almacen.
     * @throws ServerErrorException  si hay error en el servidor.
     * @throws NoConnectionException si no hay coneccion con el servidor.
     */
    public AlmacenInsumoAdapter getAdapter(Context c, int listaInsumos) throws Exception {
        return new AlmacenInsumoAdapter(c, listaInsumos, getPrimerAlmacen());
    }

    public AlmacenInsumoAdapter getAdapter(Context c, int listaInsumos, String filtros) throws Exception {
        return new AlmacenInsumoAdapter(c, listaInsumos, filterBy(filtros));
    }

    public IPVsAdapter getIPVAdapter(Context c, int ipvRegisro) throws Exception {
        return new IPVsAdapter(c, ipvRegisro, almacenWCS.getIPVRegistro(""));
    }

    public IPVsAdapter getIPVAdapter(Context c, int ipvRegisro, String codCocina) throws Exception {
        return new IPVsAdapter(c, ipvRegisro, almacenWCS.getIPVRegistro(codCocina));
    }

    public List<IpvRegistroModel> getIPVRegistro(String codCocina) throws Exception {
        return almacenWCS.getIPVRegistro(codCocina);
    }

    public OperacionesAdapter getOperacionesAdapter(Context c, int listaOperaciones) throws Exception {
        return new OperacionesAdapter(c, listaOperaciones, getOperacionesRealizadas());
    }

    private List<TransaccionModel> getOperacionesRealizadas() throws Exception {
        return almacenWCS.getOperacionesRealizadas();
    }
}
