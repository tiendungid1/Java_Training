package rxn.jdbc.core.kindergarten.module.student.model;

import java.time.LocalDate;
import rxn.jdbc.core.kindergarten.enums.Sex;

public class Student {
  private int maTre;
  private int maLop;
  private String hoTenTre;
  private LocalDate ngaySinh;
  private Sex gioiTinh;
  private String hoTenBa;
  private String hoTenMe;
  private String sdtBa;
  private String sdtMe;
  private String diaChi;
  private String taiKhoanPH;

  public int getMaTre() {
    return maTre;
  }

  public void setMaTre(int maTre) {
    this.maTre = maTre;
  }

  public int getMaLop() {
    return maLop;
  }

  public void setMaLop(int maLop) {
    this.maLop = maLop;
  }

  public String getHoTenTre() {
    return hoTenTre;
  }

  public void setHoTenTre(String hoTenTre) {
    this.hoTenTre = hoTenTre;
  }

  public LocalDate getNgaySinh() {
    return ngaySinh;
  }

  public void setNgaySinh(LocalDate ngaySinh) {
    this.ngaySinh = ngaySinh;
  }

  public Sex getGioiTinh() {
    return gioiTinh;
  }

  public void setGioiTinh(Sex gioiTinh) {
    this.gioiTinh = gioiTinh;
  }

  public String getHoTenBa() {
    return hoTenBa;
  }

  public void setHoTenBa(String hoTenBa) {
    this.hoTenBa = hoTenBa;
  }

  public String getHoTenMe() {
    return hoTenMe;
  }

  public void setHoTenMe(String hoTenMe) {
    this.hoTenMe = hoTenMe;
  }

  public String getSdtBa() {
    return sdtBa;
  }

  public void setSdtBa(String sdtBa) {
    this.sdtBa = sdtBa;
  }

  public String getSdtMe() {
    return sdtMe;
  }

  public void setSdtMe(String sdtMe) {
    this.sdtMe = sdtMe;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }

  public String getTaiKhoanPH() {
    return taiKhoanPH;
  }

  public void setTaiKhoanPH(String taiKhoanPH) {
    this.taiKhoanPH = taiKhoanPH;
  }

  @Override
  public String toString() {
    return "Student: {"
           + "\n  \"maTre\":" + maTre
           + ", \n  \"maLop\":" + maLop
           + ", \n  \"hoTenTre\":" + hoTenTre
           + ", \n  \"ngaySinh\":" + ngaySinh
           + ", \n  \"gioiTinh\":" + gioiTinh
           + ", \n  \"hoTenBa\":" + hoTenBa
           + ", \n  \"hoTenMe\":" + hoTenMe
           + ", \n  \"sdtBa\":" + sdtBa
           + ", \n  \"sdtMe\":" + sdtMe
           + ", \n  \"diaChi\":" + diaChi
           + ", \n  \"taiKhoanPH\":" + taiKhoanPH
           + "\n}";
  }
}
