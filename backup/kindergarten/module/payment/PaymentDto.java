package rxn.jdbc.core.kindergarten.module.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDto {
  private int maTre;
  private int maLop;
  private LocalDate ngayThanhToan;
  private BigDecimal tienTamThoi;

  public PaymentDto(int maTre, int maLop, LocalDate ngayThanhToan, BigDecimal tienTamThoi) {
    this.maTre = maTre;
    this.maLop = maLop;
    this.ngayThanhToan = ngayThanhToan;
    this.tienTamThoi = tienTamThoi;
  }

  public PaymentDto() {
  }

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

  public LocalDate getNgayThanhToan() {
    return ngayThanhToan;
  }

  public void setNgayThanhToan(LocalDate ngayThanhToan) {
    this.ngayThanhToan = ngayThanhToan;
  }

  public BigDecimal getTienTamThoi() {
    return tienTamThoi;
  }

  public void setTienTamThoi(BigDecimal tienTamThoi) {
    this.tienTamThoi = tienTamThoi;
  }
}
