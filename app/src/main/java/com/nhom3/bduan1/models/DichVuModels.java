package com.nhom3.bduan1.models;

public class DichVuModels {
    private String idDichVu;
    private String tienDien;
    private String tienMang;
    private String tienNuoc;
    private String tienThangMay;
    private String tienRac;

    public DichVuModels(String tienDien, String tienMang, String tienNuoc, String tienThangMay, String tienRac) {
        this.tienDien = tienDien;
        this.tienMang = tienMang;
        this.tienNuoc = tienNuoc;
        this.tienThangMay = tienThangMay;
        this.tienRac = tienRac;
    }
    public DichVuModels(){}
    public String getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(String idDichVu) {
        this.idDichVu = idDichVu;
    }

    public String getTienDien() {
        return tienDien;
    }

    public void setTienDien(String tienDien) {
        this.tienDien = tienDien;
    }

    public String getTienMang() {
        return tienMang;
    }

    public void setTienMang(String tienMang) {
        this.tienMang = tienMang;
    }

    public String getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(String tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public String getTienThangMay() {
        return tienThangMay;
    }

    public void setTienThangMay(String tienThangMay) {
        this.tienThangMay = tienThangMay;
    }

    public String getTienRac() {
        return tienRac;
    }

    public void setTienRac(String tienRac) {
        this.tienRac = tienRac;
    }
}
