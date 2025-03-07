package com.nhom3.bduan1.models;

public class QuanLiHopDongModels {
    private String idHopDong;
    private String tenKhachHang;
    private String emailKhachHang;
    private String cccd;
    private String soNguoi;
    private String tienCoc;
    private String idPhong;
    private String ngayBatDau;
    private String ngayKetThuc;

    public QuanLiHopDongModels() {
    }

    public QuanLiHopDongModels(String tenKhachHang, String emailKhachHang, String cccd, String soNguoi,
                               String idPhong, String ngayBatDau, String ngayKetThuc, String tienCoc) {
        this.tenKhachHang = tenKhachHang;
        this.emailKhachHang = emailKhachHang;
        this.cccd = cccd;
        this.soNguoi = soNguoi;
        this.idPhong = idPhong;
        this.ngayBatDau = ngayBatDau;
        this.tienCoc=tienCoc;
        this.ngayKetThuc = ngayKetThuc;
    }

    public QuanLiHopDongModels(String tenKhachHang, String emailKhachHang, String cccd, String soNguoi, String ngayBatDau, String ngayKetThuc, String tienCoc) {
        this.tenKhachHang = tenKhachHang;
        this.emailKhachHang = emailKhachHang;
        this.cccd = cccd;
        this.soNguoi = soNguoi;
        this.tienCoc=tienCoc;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayBatDau = ngayBatDau;
    }

    public String getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(String tienCoc) {
        this.tienCoc = tienCoc;
    }

    public String getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(String soNguoi) {
        this.soNguoi = soNguoi;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(String idHopDong) {
        this.idHopDong = idHopDong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getEmailKhachHang() {
        return emailKhachHang;
    }

    public void setEmailKhachHang(String emailKhachHang) {
        this.emailKhachHang = emailKhachHang;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
