/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.models;;

import java.io.Serializable;
import java.util.Date;

/**
 * FirstDream
 * @author Jorge
 * 
 */
@Entity
@Table(name = "ipv_registro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IpvRegistro.findAll", query = "SELECT i FROM IpvRegistro i")
    , @NamedQuery(name = "IpvRegistro.findByIpvinsumocodInsumo", query = "SELECT i FROM IpvRegistro i WHERE i.ipvRegistroPK.ipvinsumocodInsumo = :ipvinsumocodInsumo")
    , @NamedQuery(name = "IpvRegistro.findByIpvcocinacodCocina", query = "SELECT i FROM IpvRegistro i WHERE i.ipvRegistroPK.ipvcocinacodCocina = :ipvcocinacodCocina")
    , @NamedQuery(name = "IpvRegistro.findByIpvcocinacodCocinaAndFecha",
            query = "SELECT i FROM IpvRegistro i WHERE i.ipvRegistroPK.ipvcocinacodCocina = :ipvcocinacodCocina AND i.ipvRegistroPK.fecha = :fecha")
    ,@NamedQuery(name = "IpvRegistro.findByIpvcocinacodCocinaAndFechaAndInsumo",
            query = "SELECT i FROM IpvRegistro i WHERE i.ipvRegistroPK.ipvcocinacodCocina = :ipvcocinacodCocina AND "
            + "i.ipvRegistroPK.fecha = :fecha AND i.ipvRegistroPK.ipvinsumocodInsumo = :codinsumo")
    , @NamedQuery(name = "IpvRegistro.findByFecha", query = "SELECT i FROM IpvRegistro i WHERE i.ipvRegistroPK.fecha = :fecha")
    , @NamedQuery(name = "IpvRegistro.findByInicio", query = "SELECT i FROM IpvRegistro i WHERE i.inicio = :inicio")
    , @NamedQuery(name = "IpvRegistro.findByEntrada", query = "SELECT i FROM IpvRegistro i WHERE i.entrada = :entrada")
    , @NamedQuery(name = "IpvRegistro.findByDisponible", query = "SELECT i FROM IpvRegistro i WHERE i.disponible = :disponible")
    , @NamedQuery(name = "IpvRegistro.findByConsumo", query = "SELECT i FROM IpvRegistro i WHERE i.consumo = :consumo")
    , @NamedQuery(name = "IpvRegistro.findByConsumoReal", query = "SELECT i FROM IpvRegistro i WHERE i.consumoReal = :consumoReal")
    , @NamedQuery(name = "IpvRegistro.findByFinal1", query = "SELECT i FROM IpvRegistro i WHERE i.final1 = :final1")
    , @NamedQuery(name = "IpvRegistro.findByFinalReal", query = "SELECT i FROM IpvRegistro i WHERE i.finalReal = :finalReal")})
public class IpvRegistro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IpvRegistroPK ipvRegistroPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "inicio")
    private Float inicio;
    @Column(name = "entrada")
    private Float entrada;
    @Column(name = "disponible")
    private Float disponible;
    @Column(name = "consumo")
    private Float consumo;
    @Column(name = "consumo_real")
    private Float consumoReal;
    @Column(name = "final")
    private Float final1;
    @Column(name = "final_real")
    private Float finalReal;
    @JoinColumns({
        @JoinColumn(name = "ipvinsumocod_insumo", referencedColumnName = "insumocod_insumo", insertable = false, updatable = false)
        , @JoinColumn(name = "ipvcocinacod_cocina", referencedColumnName = "cocinacod_cocina", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Ipv ipv;

    public IpvRegistro() {
    }

    public IpvRegistro(IpvRegistroPK ipvRegistroPK) {
        this.ipvRegistroPK = ipvRegistroPK;
    }

    public IpvRegistro(String ipvinsumocodInsumo, String ipvcocinacodCocina, Date fecha) {
        this.ipvRegistroPK = new IpvRegistroPK(ipvinsumocodInsumo, ipvcocinacodCocina, fecha);
    }

    public IpvRegistroPK getIpvRegistroPK() {
        return ipvRegistroPK;
    }

    public void setIpvRegistroPK(IpvRegistroPK ipvRegistroPK) {
        this.ipvRegistroPK = ipvRegistroPK;
    }

    public Float getInicio() {
        return inicio;
    }

    public void setInicio(Float inicio) {
        this.inicio = inicio;
    }

    public Float getEntrada() {
        return entrada;
    }

    public void setEntrada(Float entrada) {
        this.entrada = entrada;
    }

    public Float getDisponible() {
        return disponible;
    }

    public void setDisponible(Float disponible) {
        this.disponible = disponible;
    }

    public Float getConsumo() {
        return consumo;
    }

    public void setConsumo(Float consumo) {
        this.consumo = consumo;
    }

    public Float getConsumoReal() {
        return consumoReal;
    }

    public void setConsumoReal(Float consumoReal) {
        this.consumoReal = consumoReal;
    }

    public Float getFinal1() {
        return final1;
    }

    public void setFinal1(Float final1) {
        this.final1 = final1;
    }

    public Float getFinalReal() {
        return finalReal;
    }

    public void setFinalReal(Float finalReal) {
        this.finalReal = finalReal;
    }

    public Ipv getIpv() {
        return ipv;
    }

    public void setIpv(Ipv ipv) {
        this.ipv = ipv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipvRegistroPK != null ? ipvRegistroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpvRegistro)) {
            return false;
        }
        IpvRegistro other = (IpvRegistro) object;
        if ((this.ipvRegistroPK == null && other.ipvRegistroPK != null) || (this.ipvRegistroPK != null && !this.ipvRegistroPK.equals(other.ipvRegistroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.restmanager.IpvRegistro[ ipvRegistroPK=" + ipvRegistroPK + " ]";
    }

}
