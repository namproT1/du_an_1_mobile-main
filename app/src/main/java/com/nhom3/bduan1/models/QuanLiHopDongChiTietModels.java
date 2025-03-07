package com.nhom3.bduan1.models;

public class QuanLiHopDongChiTietModels {
    private String idHopDongChiTiet;
    private String idHopDong;
    private String idDichVu;

    public QuanLiHopDongChiTietModels(String idHopDong, String idDichVu) {
        this.idHopDong = idHopDong;
        this.idDichVu = idDichVu;
    }
    public QuanLiHopDongChiTietModels(){}
    public String getIdHopDongChiTiet() {
        return idHopDongChiTiet;
    }

    public void setIdHopDongChiTiet(String idHopDongChiTiet) {
        this.idHopDongChiTiet = idHopDongChiTiet;
    }

    public String getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(String idHopDong) {
        this.idHopDong = idHopDong;
    }

    public String getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(String idDichVu) {
        this.idDichVu = idDichVu;
    }
}
