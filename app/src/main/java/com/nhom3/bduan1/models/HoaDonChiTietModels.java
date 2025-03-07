package com.nhom3.bduan1.models;

public class HoaDonChiTietModels {
    private String idHoaDonChiTiet;
    private String idHoaDon;
    private String idPhong;
    private String idHoaDonDichVu;
    private String soTienThanhToan;

    public HoaDonChiTietModels(String idHoaDon, String idPhong, String idHoaDonDichVu, String soTienThanhToan) {
        this.idHoaDon = idHoaDon;
        this.idPhong = idPhong;
        this.idHoaDonDichVu = idHoaDonDichVu;
        this.soTienThanhToan = soTienThanhToan;
    }
    public HoaDonChiTietModels(){}

    public String getIdHoaDonChiTiet() {
        return idHoaDonChiTiet;
    }

    public void setIdHoaDonChiTiet(String idHoaDonChiTiet) {
        this.idHoaDonChiTiet = idHoaDonChiTiet;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public void setIdHoaDonDichVu(String idHoaDonDichVu) {
        this.idHoaDonDichVu = idHoaDonDichVu;
    }

    public void setSoTienThanhToan(String soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public String getIdHoaDonDichVu() {
        return idHoaDonDichVu;
    }

    public String getSoTienThanhToan() {
        return soTienThanhToan;
    }

}
