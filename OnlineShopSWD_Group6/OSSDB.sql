-- Tạo database
CREATE DATABASE OSSDB;
USE OSSDB;

-- Bảng Users
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NULL,
    LastName VARCHAR(50) NULL,
    Gender VARCHAR(10) NULL,
    DateOfBirth DATE NULL,
    UserName VARCHAR(50) UNIQUE,
    Password VARCHAR(255) NULL,
    Role VARCHAR(20) NULL,
    Email VARCHAR(100) UNIQUE,
    PhoneNumber VARCHAR(20) NULL,
    Address TEXT NULL,
    Status VARCHAR(12) DEFAULT 'Active'
);

-- Bảng Category
CREATE TABLE Category (
    CategoryID VARCHAR(50) PRIMARY KEY,
    CategoryName VARCHAR(100) NULL
);

-- Bảng Products
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryID VARCHAR(50) NOT NULL,
    ProductName VARCHAR(255) NULL,
    Description TEXT NULL,
    Provider VARCHAR(100) NULL,
    Price DECIMAL(10,2) NULL,
    OldPrice DECIMAL(10,2) NULL,
    WarrantyPeriod VARCHAR(50) NULL,
    Amount INT NULL,
    ImageLink VARCHAR(255) NULL,
    IsPromoted BOOLEAN NULL,
    Status VARCHAR(12) DEFAULT 'Active',
    CreateAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)
);

-- Bảng ProductDetails
CREATE TABLE ProductDetails (
    ProductDetailID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT NULL,
    ProductDetailName VARCHAR(255) NULL,
    Value TEXT NULL,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Bảng Orders (Đã xóa cột GuestID và ràng buộc liên quan)
CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL, -- Bắt buộc phải có CustomerID
    OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    DeliveryAddress TEXT NULL,
    Status VARCHAR(20) NULL,
    TotalAmount DECIMAL(10,2) NULL,
    BillOfLading VARCHAR(100) NULL,
    FOREIGN KEY (CustomerID) REFERENCES Users(UserID)
);

-- Bảng OrderDetails
CREATE TABLE OrderDetails (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NULL,
    ProductID INT NULL,
    Quantity INT NULL,
    Price DECIMAL(10,2) NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Bảng Payments
CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NULL,
    PaymentMethod VARCHAR(100) NULL,
    Price DECIMAL(10,2) NULL,
    PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PaymentStatus VARCHAR(20) NULL,
    TransactionCode VARCHAR(255) NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);

-- Bảng Carts
CREATE TABLE Carts (
    CartID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NULL,
    Status VARCHAR(20) NULL,
    FOREIGN KEY (CustomerID) REFERENCES Users(UserID)
);

-- Bảng CartItems
CREATE TABLE CartItems (
    CartItemID INT AUTO_INCREMENT PRIMARY KEY,
    CartID INT NULL,
    ProductID INT NULL,
    Quantity INT NULL,
    FOREIGN KEY (CartID) REFERENCES Carts(CartID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Bảng Settings
CREATE TABLE Settings (
    SettingID INT AUTO_INCREMENT PRIMARY KEY,
    SettingType VARCHAR(100) NULL,
    SettingValue TEXT NULL,
    Status VARCHAR(20) NULL
);

-- Chèn dữ liệu vào bảng Users
INSERT INTO Users (FirstName, LastName, Gender, DateOfBirth, UserName, Password, Role, Email, PhoneNumber, Address)
VALUES
('Tung', 'Duong', 'Male', '2004-11-24', 'admin', '123', 'Admin', 'admin@example.com', '1234567890', '123 Main St'),
('Tien', 'Dung', 'Female', '1992-07-10', 'customer1', '123', 'Customer', 'customer1@example.com', '1122334455', '789 Pine St'),
('Thanh', 'Trung', 'Male', '1988-11-30', 'customer2', '123', 'Customer', 'customer2@example.com', '2233445566', '321 Elm St'),
('The', 'Vinh', 'Male', '1995-03-25', 'customer3', '123', 'Customer', 'customer3@example.com', '3344556677', '654 Birch St');

-- Chèn dữ liệu vào bảng Category
INSERT INTO Category (CategoryID, CategoryName)
VALUES 
('TBQ', 'Thiết bị quạt'),
('TBCS', 'Thiết bị chiếu sáng'),
('CTD', 'Công tắc điện'),
('TBTM', 'Thiết bị thông minh'),
('TBSCBT', 'Thiết bị sửa chữa & bảo trì'),
('SPM', 'Sản phẩm mới'),
('SPKM', 'Sản phẩm khuyến mãi');

-- Chèn dữ liệu vào bảng Products
INSERT INTO Products (CategoryID, ProductName, Description, Provider, Price, WarrantyPeriod, Amount, ImageLink, IsPromoted, CreateAt) 
VALUES
('TBQ', 'Quạt tích điện', 'Quạt sử dụng pin sạc tiện lợi.', 'ElectroCorp', 195000, '1 năm', 100, 'assets/img/TBQ-1.jpg', 1, NOW()),
('TBQ', 'Quạt hơi nước', 'Giúp làm mát không khí hiệu quả.', 'ElectroCorp', 499000, '1 năm', 50, 'assets/img/TBQ-2.jpg', 1, NOW()),
('TBQ', 'Quạt điều hòa', 'Điều hòa không khí với hơi nước.', 'ElectroCorp', 499000, '2 năm', 30, 'assets/img/TBQ-3.jpg', 1, NOW()),
('TBQ', 'Quạt trần treo tường', 'Quạt treo trần tiết kiệm diện tích.', 'ElectroCorp', 800000, '2 năm', 20, 'assets/img/TBQ-4.jpg', 0, NOW()),
('TBCS', 'Bóng đèn LED MPE LBD-50V 50W', 'Bóng đèn LED MPE LBD-50V 50W.', 'LightingCo', 290000, '2 năm', 100, 'assets/img/TBCS-1.jpg', 1, NOW()),
('TBCS', 'Đèn LED âm trần chống chói 7W', 'Đèn LED âm trần chống chói 7W.', 'LightingCo', 115000, '3 năm', 50, 'assets/img/TBCS-2.jpg', 1, NOW()),
('CTD', 'Công tắc điện điều khiển từ xa qua remote', 'Điều khiển từ xa qua remote tiện lợi.', 'ElectricCo', 550000, '5 năm', 100, 'assets/img/CTD-5.jpg', 1, NOW()),
('TBTM', 'Khoá cửa thông minh tích hợp camera AB-24K', 'Khoá cửa thông minh tích hợp camera AB-24K, an ninh tối ưu.', 'SmartTech', 4160000, '5 năm', 20, 'assets/img/TBTM-4.jpg', 0, NOW()),
('TBTM', 'Cảm biến khói Panasonic', 'Cảm biến khói Panasonic, bảo vệ an toàn cho gia đình.', 'Panasonic', 960000, '2 năm', 50, 'assets/img/TBTM-5.jpg', 1, NOW()),
('TBQ', 'Quạt cây mini', 'Quạt cây nhỏ gọn, 3 tốc độ.', 'ElectroCorp', 150000, '1 năm', 80, 'assets/img/TBQ-5.jpg', 0, NOW()),
('TBQ', 'Quạt phun sương mini', 'Phun sương làm mát hiệu quả.', 'ElectroCorp', 250000, '1 năm', 60, 'assets/img/TBQ-6.jpg', 1, NOW()),
('TBQ', 'Quạt đứng công nghiệp', 'Quạt mạnh mẽ cho không gian lớn.', 'ElectroCorp', 1200000, '2 năm', 15, 'assets/img/TBQ-7.jpg', 0, NOW()),
('TBQ', 'Quạt treo tường 16 inch', 'Quạt treo tường tiết kiệm diện tích.', 'ElectroCorp', 200000, '1 năm', 40, 'assets/img/TBQ-8.jpg', 0, NOW()),
('TBCS', 'Đèn LED bulb 10W', 'Đèn LED tiết kiệm điện, ánh sáng ấm.', 'LightingCo', 50000, '2 năm', 200, 'assets/img/TBCS-3.jpg', 1, NOW()),
('TBCS', 'Đèn LED dây trang trí', 'Đèn LED dây dài, phù hợp trang trí.', 'LightingCo', 150000, '2 năm', 75, 'assets/img/TBCS-4.jpg', 0, NOW()),
('TBCS', 'Đèn pha LED 100W', 'Đèn pha ngoài trời, độ bền cao.', 'LightingCo', 450000, '3 năm', 30, 'assets/img/TBCS-5.jpg', 1, NOW()),
('TBCS', 'Đèn LED panel 40W', 'Đèn panel hiện đại, ánh sáng đồng đều.', 'LightingCo', 300000, '2 năm', 50, 'assets/img/TBCS-6.jpg', 0, NOW()),
('CTD', 'Công tắc bập bênh 1 chiều', 'Công tắc cơ bản, dễ sử dụng.', 'ElectricCo', 10000, '5 năm', 300, 'assets/img/CTD-1.jpg', 0, NOW()),
('CTD', 'Ổ cắm điện đôi âm tường', 'Ổ cắm đôi tiện lợi, an toàn.', 'ElectricCo', 30000, '2 năm', 150, 'assets/img/CTD-2.jpg', 0, NOW()),
('CTD', 'Công tắc hẹn giờ thông minh', 'Tự động bật/tắt theo lịch.', 'ElectricCo', 400000, '3 năm', 25, 'assets/img/CTD-3.jpg', 1, NOW()),
('CTD', 'Ổ cắm đa năng 3 chân', 'Ổ cắm đa năng, hỗ trợ nhiều thiết bị.', 'ElectricCo', 25000, '2 năm', 200, 'assets/img/CTD-4.jpg', 0, NOW()),
('TBTM', 'Cảm biến nhiệt độ thông minh', 'Theo dõi nhiệt độ qua ứng dụng.', 'SmartTech', 800000, '2 năm', 40, 'assets/img/TBTM-1.jpg', 1, NOW()),
('TBTM', 'Rèm tự động thông minh', 'Điều khiển rèm qua điện thoại.', 'SmartTech', 3000000, '3 năm', 10, 'assets/img/TBTM-2.jpg', 0, NOW()),
('TBTM', 'Chuông cửa có hình', 'Chuông cửa tích hợp camera HD.', 'SmartTech', 1500000, '2 năm', 20, 'assets/img/TBTM-3.jpg', 1, NOW()),
('TBTM', 'Bộ điều khiển đèn thông minh', 'Điều khiển đèn từ xa qua app.', 'SmartTech', 700000, '2 năm', 30, 'assets/img/TBTM-6.jpg', 0, NOW()),
('TBQ', 'Quạt sưởi ấm điện', 'Sưởi ấm hiệu quả, an toàn.', 'ElectroCorp', 600000, '1 năm', 25, 'assets/img/TBQ-9.jpg', 1, NOW()),
('TBCS', 'Đèn LED hắt trần', 'Đèn hắt trần hiện đại, sáng đều.', 'LightingCo', 200000, '2 năm', 60, 'assets/img/TBCS-7.jpg', 0, NOW()),
('CTD', 'Công tắc cảm ứng 2 chiều', 'Công tắc cảm ứng cao cấp.', 'ElectricCo', 350000, '3 năm', 50, 'assets/img/CTD-6.jpg', 1, NOW()),
('TBTM', 'Cảm biến ánh sáng tự động', 'Tự động bật/tắt đèn theo ánh sáng.', 'SmartTech', 500000, '2 năm', 45, 'assets/img/TBTM-7.jpg', 0, NOW()),
('TBQ', 'Quạt thông gió công nghiệp', 'Thông gió mạnh mẽ, bền bỉ.', 'ElectroCorp', 900000, '2 năm', 15, 'assets/img/TBQ-10.jpg', 0, NOW());

-- Cập nhật OldPrice cho sản phẩm khuyến mãi
UPDATE Products
SET OldPrice = Price * 1.10
WHERE IsPromoted = 1;

-- Chèn dữ liệu vào bảng Orders (Chỉ sử dụng CustomerID từ 2, 3, 4 - các user có Role là Customer)
INSERT INTO Orders (CustomerID, OrderDate, DeliveryAddress, Status, TotalAmount, BillOfLading)
VALUES
(2, '2025-03-01 10:00:00', '789 Pine St', 'Pending', 1049.98, 'BL12345'),
(3, '2025-03-02 12:30:00', '321 Elm St', 'Shipped', 199.98, 'BL23456'),
(4, '2025-03-03 09:15:00', '654 Birch St', 'Delivered', 29.99, 'BL34567'),
(2, '2025-03-04 14:20:00', '789 Pine St', 'Processing', 149.99, 'BL45678'),
(3, '2025-03-05 16:45:00', '321 Elm St', 'Pending', 2499.50, 'BL56789'),
(4, '2025-03-06 11:10:00', '654 Birch St', 'Cancelled', 799.00, 'BL67890'),
(2, '2025-03-07 08:30:00', '789 Pine St', 'Shipped', 450.00, 'BL78901'),
(3, '2025-03-08 13:00:00', '321 Elm St', 'Delivered', 1500.75, 'BL89012'),
(4, '2025-03-09 15:20:00', '654 Birch St', 'Pending', 300.00, 'BL90123'),
(2, '2025-03-10 10:40:00', '789 Pine St', 'Processing', 950.25, 'BL01234'),
(3, '2025-03-11 09:00:00', '321 Elm St', 'Shipped', 499.99, 'BL12346'),
(4, '2025-03-12 14:15:00', '654 Birch St', 'Delivered', 2000.00, 'BL23457'),
(2, '2025-03-12 16:00:00', '789 Pine St', 'Cancelled', 99.99, 'BL34568'),
(3, '2025-03-12 11:30:00', '321 Elm St', 'Pending', 750.00, 'BL45679'),
(4, '2025-03-12 08:45:00', '654 Birch St', 'Shipped', 1200.50, 'BL56780');

-- Chèn dữ liệu vào bảng OrderDetails
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES
-- Đơn hàng 1 (CustomerID: 2)
(1, 1, 2, 195000.00),  -- Quạt tích điện
(1, 5, 1, 290000.00),  -- Bóng đèn LED MPE LBD-50V 50W
-- Đơn hàng 2 (CustomerID: 3)
(2, 3, 1, 499000.00),  -- Quạt điều hòa
-- Đơn hàng 3 (CustomerID: 4)
(3, 18, 3, 10000.00),  -- Công tắc bập bênh 1 chiều
-- Đơn hàng 4 (CustomerID: 2)
(4, 10, 1, 150000.00), -- Quạt cây mini
-- Đơn hàng 5 (CustomerID: 3)
(5, 8, 1, 4160000.00), -- Khóa cửa thông minh AB-24K
-- Đơn hàng 6 (CustomerID: 4)
(6, 7, 2, 550000.00),  -- Công tắc điện điều khiển từ xa
-- Đơn hàng 7 (CustomerID: 2)
(7, 14, 3, 50000.00),  -- Đèn LED bulb 10W
(7, 19, 1, 30000.00),  -- Ổ cắm điện đôi âm tường
-- Đơn hàng 8 (CustomerID: 3)
(8, 9, 1, 960000.00),  -- Cảm biến khói Panasonic
(8, 23, 1, 1500000.00),-- Chuông cửa có hình
-- Đơn hàng 9 (CustomerID: 4)
(9, 2, 1, 499000.00),  -- Quạt hơi nước
-- Đơn hàng 10 (CustomerID: 2)
(10, 6, 2, 115000.00), -- Đèn LED âm trần chống chói 7W
(10, 15, 1, 150000.00),-- Đèn LED dây trang trí
-- Đơn hàng 11 (CustomerID: 3)
(11, 4, 1, 800000.00), -- Quạt trần treo tường
-- Đơn hàng 12 (CustomerID: 4)
(12, 12, 2, 1200000.00),-- Quạt đứng công nghiệp
-- Đơn hàng 13 (CustomerID: 2)
(13, 20, 1, 400000.00),-- Công tắc hẹn giờ thông minh
-- Đơn hàng 14 (CustomerID: 3)
(14, 16, 1, 450000.00),-- Đèn pha LED 100W
(14, 21, 1, 25000.00), -- Ổ cắm đa năng 3 chân
-- Đơn hàng 15 (CustomerID: 4)
(15, 11, 2, 250000.00);-- Quạt phun sương mini

-- Chèn dữ liệu vào bảng Payments
INSERT INTO Payments (OrderID, PaymentMethod, Price, PaymentDate, PaymentStatus, TransactionCode)
VALUES
(1, 'Credit Card', 1049.98, '2025-03-01 10:05:00', 'Completed', 'TXN12345'),
(2, 'PayPal', 199.98, '2025-03-02 12:35:00', 'Completed', 'TXN23456'),
(3, 'Credit Card', 29.99, '2025-03-03 09:20:00', 'Completed', 'TXN34567'),
(4, 'Bank Transfer', 149.99, '2025-03-04 14:25:00', 'Pending', 'TXN45678'),
(5, 'Credit Card', 2499.50, '2025-03-05 16:50:00', 'Processing', 'TXN56789'),
(6, 'COD', 799.00, '2025-03-06 11:15:00', 'Cancelled', 'TXN67890'),
(7, 'PayPal', 450.00, '2025-03-07 08:35:00', 'Completed', 'TXN78901'),
(8, 'Credit Card', 1500.75, '2025-03-08 13:05:00', 'Completed', 'TXN89012'),
(9, 'Bank Transfer', 300.00, '2025-03-09 15:25:00', 'Pending', 'TXN90123'),
(10, 'COD', 950.25, '2025-03-10 10:45:00', 'Processing', 'TXN01234'),
(11, 'Credit Card', 499.99, '2025-03-11 09:05:00', 'Completed', 'TXN12346'),
(12, 'PayPal', 2000.00, '2025-03-12 14:20:00', 'Completed', 'TXN23457'),
(13, 'COD', 99.99, '2025-03-12 16:05:00', 'Cancelled', 'TXN34568'),
(14, 'Bank Transfer', 750.00, '2025-03-12 11:35:00', 'Pending', 'TXN45679'),
(15, 'Credit Card', 1200.50, '2025-03-12 08:50:00', 'Completed', 'TXN56780');