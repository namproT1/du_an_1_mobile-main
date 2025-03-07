package com.nhom3.bduan1.models;

public class HoaDonDichVuModels {
    private String idHoaDonDichVu;
    private String idDichVu;
    private String tienDien;
    private String tienNuoc;
    private String tienThangMay;
    private String tienMang;
    private String tienRac;
    private String soDien;
    private String soNuoc;
    private String tongTienDichVu;

    public HoaDonDichVuModels(String idDichVu, String tienDien, String tienNuoc, String tienThangMay, String tienMang, String tienRac, String soDien, String soNuoc, String tongTienDichVu) {
        this.idDichVu = idDichVu;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienThangMay = tienThangMay;
        this.tienMang = tienMang;
        this.tienRac = tienRac;
        this.soDien=soDien;
        this.soNuoc=soNuoc;
        this.tongTienDichVu = tongTienDichVu;
    }
    public HoaDonDichVuModels() {}
    public String getSoDien() {
        return soDien;
    }

    public String getSoNuoc() {
        return soNuoc;
    }

    public String getIdHoaDonDichVu() {
        return idHoaDonDichVu;
    }

    public void setIdHoaDonDichVu(String idHoaDonDichVu) {
        this.idHoaDonDichVu = idHoaDonDichVu;
    }

    public String getTienDien() {
        return tienDien;
    }

    public String getTienNuoc() {
        return tienNuoc;
    }

    public String getTienThangMay() {
        return tienThangMay;
    }

    public String getTienMang() {
        return tienMang;
    }

    public String getTienRac() {
        return tienRac;
    }

}
