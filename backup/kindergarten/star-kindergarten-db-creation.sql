use master;

GO

DROP DATABASE [truong_mam_non_star];

GO

CREATE DATABASE [truong_mam_non_star];

GO

use [truong_mam_non_star];

GO

CREATE TABLE [LOPSINHHOAT] (
    [MaLop] INT PRIMARY KEY IDENTITY,
    [TenLop] NVARCHAR(50) NOT NULL,
    [HocPhiMoiThang] MONEY,
    [TienAnMoiBuoi] MONEY,
    [NgayBD] DATE,
    [NgayKT] DATE,
    [TuoiQuyDinh] TINYINT,
    [MaGV1] INT,
    [MaGV2] INT
);

GO

CREATE TABLE [LOPNANGKHIEU] (
    [MaLopNK] INT PRIMARY KEY IDENTITY,
    [TenLopNK] NVARCHAR(50) NOT NULL,
    [MaGV] INT,
    [NgayBD] DATE,
    [NgayKTDK] DATE,
    [HocPhiMoiThang] MONEY,
    [SoLuongToiDa] TINYINT
);

GO

CREATE TABLE [DANHGIATRE] (
    [MaDanhGia] INT PRIMARY KEY IDENTITY,
    [MaTre] INT NOT NULL,
    [MaLop] INT NOT NULL,
    [Vang] TINYINT NOT NULL,
    [NgayDanhGia] DATE NOT NULL,
    [DanhGia] NVARCHAR(1000) NOT NULL
);

GO

CREATE TABLE [DANGKYLOPNANGKHIEU] (
    [MaLopNK] INT,
    [MaTre] INT,
    PRIMARY KEY (MaLopNK, MaTre)
);

GO

CREATE TABLE [NHANVIEN] (
    [MaNV] INT PRIMARY KEY IDENTITY,
    [HoTenNV] NVARCHAR(50) NOT NULL,
    [ChucVu] NVARCHAR(50),
    [SoNamKN] TINYINT,
    [Luong] MONEY,
    [Sdt] NVARCHAR(10),
    [TaiKhoanNV] VARCHAR(50)
);

GO

CREATE TABLE [THANHTOAN] (
    [MaTT] INT PRIMARY KEY IDENTITY,
    [MaTre] INT NOT NULL,
    [MaLop] INT NOT NULL,
    [NgayThanhToan] DATE,
    [TienTamThoi] MONEY NOT NULL,
    [TienThucTe] MONEY,
    [TrangThai] BIT DEFAULT 0
);

GO

CREATE TABLE [TREEM] (
    [MaTre] INT PRIMARY KEY IDENTITY,
    [MaLop] INT NOT NULL,
    [HoTenTre] NVARCHAR(50) NOT NULL,
    [NgaySinh] DATE NOT NULL,
    [GioiTinh] BIT NOT NULL,
    [HoTenBa] NVARCHAR(50),
    [HoTenMe] NVARCHAR(50),
    [SdtBa] NVARCHAR(10),
    [SdtMe] NVARCHAR(10),
    [DiaChi] NVARCHAR(100),
    [TaiKhoanPH] VARCHAR(50)
);

GO

CREATE TABLE [TAIKHOAN] (
    [TenDN] VARCHAR(50) PRIMARY KEY,
    [MatKhau] VARCHAR(100) NOT NULL,
    [VaiTro] TINYINT NOT NULL
);

GO

ALTER TABLE [LOPSINHHOAT]
ADD CONSTRAINT fk_first_teacher_id FOREIGN KEY (MaGV1) REFERENCES [NHANVIEN] (MaNV);

ALTER TABLE [LOPSINHHOAT]
ADD CONSTRAINT fk_second_teacher_id FOREIGN KEY (MaGV2) REFERENCES [NHANVIEN] (MaNV);

GO

ALTER TABLE [LOPNANGKHIEU]
ADD CONSTRAINT fk_teacher_id FOREIGN KEY (MaGV) REFERENCES [NHANVIEN] (MaNV);

GO

ALTER TABLE [DANHGIATRE]
ADD CONSTRAINT fk_evaluated_student_id FOREIGN KEY (MaTre) REFERENCES [TREEM] (MaTre);

ALTER TABLE [DANHGIATRE]
ADD CONSTRAINT fk_student_class_id FOREIGN KEY (MaLop) REFERENCES [LOPSINHHOAT] (MaLop);

GO

ALTER TABLE [DANGKYLOPNANGKHIEU]
ADD CONSTRAINT fk_talent_class_id FOREIGN KEY (MaLopNK) REFERENCES [LOPNANGKHIEU] (MaLopNK);

ALTER TABLE [DANGKYLOPNANGKHIEU]
ADD CONSTRAINT fk_talent_student_id FOREIGN KEY (MaTre) REFERENCES [TREEM] (MaTre);

GO

ALTER TABLE [NHANVIEN]
ADD CONSTRAINT fk_username FOREIGN KEY (TaiKhoanNV) REFERENCES [TAIKHOAN] (TenDN) ON UPDATE CASCADE;

ALTER TABLE [TREEM]
ADD CONSTRAINT fk_parent_account FOREIGN KEY (TaiKhoanPH) REFERENCES [TAIKHOAN] (TenDN) ON UPDATE CASCADE;

GO

ALTER TABLE [THANHTOAN]
ADD CONSTRAINT fk_paid_student_id FOREIGN KEY (MaTre) REFERENCES [TREEM] (MaTre);

ALTER TABLE [THANHTOAN]
ADD CONSTRAINT fk_paid_student_class_id FOREIGN KEY (MaLop) REFERENCES [LOPSINHHOAT] (MaLop);

GO

ALTER TABLE [TREEM]
ADD CONSTRAINT fk_student_main_class_id FOREIGN KEY (MaLop) REFERENCES [LOPSINHHOAT] (MaLop);

-- 
-- INSERT INTO TABLE
-- 

GO

INSERT INTO [TAIKHOAN] ([TenDN], [MatKhau], [VaiTro])
VALUES
    ('admin', '123123123', 1),
    ('NganNK', '123123123', 2),
	('ChuongDV', '123123123', 2),
	('Dong1', '123123123', 3),
	('TungDT', '123123123', 3),
    ('DungNT', '123123123', 3);

GO

INSERT INTO [NHANVIEN] ([HoTenNV], [ChucVu], [SoNamKN], [Luong], [Sdt], [TaiKhoanNV])
VALUES
    (N'Ngo Khanh Ly', N'Ke toan', 2, 5000000, '0123123123', 'admin'),
    (N'Nguyen Kim Ngan', N'Giao vien', 3, 4000000, '0123123124', 'NganNK'),
    (N'Nguyen Ngoc Dong', N'Giao vien', 2, 4500000, '0123123125', NULL),
	(N'Truong Kim Thanh', N'Giao vien', 2, 4500000, '0908756415', NULL),
    (N'Doan Van Chuong', N'Giao vien', 5, 4000000, '0975628156', 'ChuongDV');

GO

INSERT INTO [LOPSINHHOAT] ([TenLop], [HocPhiMoiThang], [TienAnMoiBuoi], [NgayBD], [NgayKT], [TuoiQuyDinh], [MaGV1], [MaGV2])
VALUES
    (N'Mam 01', 3000000, 50000, '2021-09-05', '2022-05-25', 3, 2, 3),
    (N'Choi 01', 3500000, 55000, '2021-09-05', '2022-05-25', 4, 3, NULL),
    (N'La 01', 4000000, 60000, '2021-09-05', '2022-05-25', 5, 4, NULL),
	(N'Mam 02', 3000000, 50000, '2022-09-05', '2023-05-25', 3, 3, 4),
    (N'Choi 02', 3500000, 55000, '2022-09-05', '2023-05-25', 4, 2, NULL),
	(N'La 02', 4000000, 60000, '2022-09-05', '2023-05-25', 5, 2, 3),
    (N'Mam 03', 3000000, 50000, '2023-09-05', '2024-05-25', 3, 4, NULL),
	(N'Choi 03', 3500000, 55000, '2023-09-05', '2024-05-25', 4, 2, 3),
    (N'La 03', 4000000, 60000, '2023-09-05', '2024-05-25', 5, 3, 4);

GO

INSERT INTO [LOPNANGKHIEU] ([TenLopNK], [NgayBD], [NgayKTDK], [HocPhiMoiThang], [MaGV], [SoLuongToiDa])
VALUES
    (N'Ve 01', '2022-10-01', '2022-09-29', 500000, 2, 5),
	(N'Erobic', '2022-10-01', '2022-09-29', 500000, 3, 10),
	(N'Tieng Anh 01', '2022-12-01', '2022-11-29', 700000, 4, 15),
    (N'Nhac 01', '2022-11-01', '2022-10-29', 700000, 3, 20),
    (N'Nhac 02', '2021-09-06', '2021-10-06', 700000, 3, 20);

GO

INSERT INTO [TREEM] ([HoTenTre], [MaLop], [NgaySinh], [GioiTinh], [HoTenBa], [HoTenMe], [SdtBa], [SdtMe], [DiaChi], [TaiKhoanPH])
VALUES
    (N'Nguyen Kim Chung', 1, '2020-02-28', 0, N'Dong', N'Thuy', '0123123123', '0123123123', N'Quang Nam', 'Dong1'),
    (N'Vo Van Dung', 2, '2019-03-20', 1, N'Hai', N'Tam', '0123123123', '0123123123', N'Quang Tri', NULL),
    (N'Tran Nguyen Khanh', 3, '2018-03-20', 1, N'Hoai', N'Thu', '0123123123', '0123123123', N'Hải Châu Da Nang', NULL),
	(N'Nguyen Bich Tram', 4, '2020-02-28', 0, N'Khoa', N'Hanh', '0123123123', '0123123123', N'Quang Nam', NULL),
    (N'Nguyen Hoang Vi', 5, '2019-03-20', 1, N'Sau', N'Hue', '0123123123', '0123123123', N'Quang Nam', NULL),
    (N'Tran Van Tuan', 6, '2018-03-20', 1, N'Tung', N'Trang', '0123123123', '0123123123', N'hai chau Da Nang', 'TungDT'),
	(N'Ngo Kim Thoa', 7, '2020-02-28', 0, N'Tuan', N'Dung', '0123123123', '0123123123', N'Quang Nam', 'DungNT'),
    (N'Ho Truc Ly', 8, '2019-03-20', 0, N'An', N'Tam', '0123123123', '0123123123', N'Quang Tri', NULL),
    (N'Truong Quoc Cuong', 9, '2018-03-20', 1, N'Phuc', N'Phung', '0123123123', '0123123123', N'Hải Châu Da Nang', NULL),
	(N'Ngo Minh Tuan', 7, '2020-02-28', 1, N'Dang', N'Trinh', '0123123123', '0123123123', N'Da Nang', NULL),
    (N'Nguyen Van Hien', 2, '2019-03-20', 1, N'Bay', N'Cuc', '0123123123', '0123123123', N'Hai Chau Da Nang', NULL),
    (N'Le Khoa Nguyen', 6, '2018-03-20', 1, N'Nhat', N'Nguyet', '0123123123', '0123123123', N'Quang Nam', NULL),
    (N'Le Van A', 6, '2023-03-20', 0, N'Nghia', N'Loan', '0123123123', '0123123123', N'Quang Nam', NULL),
    (N'Le Van B', 6, '2023-04-20', 0, N'Nghia', N'Loan', '0123123123', '0123123123', N'Quang Nam', NULL);

GO

INSERT INTO [DANHGIATRE] ([MaTre], [MaLop], [Vang], [NgayDanhGia], [DanhGia])
VALUES
    (1, 1, 0, '2022-09-06', N'Tre an day du'),
    (3, 3, 1, '2022-09-12', N'Vang hoc khong phep'),
    (3, 3, 2, '2022-09-13', N'Vang hoc khong phep'),
    (3, 3, 0, '2022-09-14', N'Vang hoc khong phep'),
    (3, 3, 3, '2022-09-15', N'Vang hoc khong phep');

GO

INSERT INTO [DANGKYLOPNANGKHIEU] ([MaLopNK], [MaTre])
VALUES
    (1, 1),
    (2, 1),
    (4, 1),
    (2, 2),
	(2, 3),
    (3, 3),
    (3, 4),
    (1, 5),
	(3, 5),
    (2, 6),
    (4, 6),
    (1, 7),
	(3, 8),
    (2, 8),
    (4, 9),
    (3, 9),
    (5, 2);
