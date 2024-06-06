create database TareaPractica
use TareaPractica


-- Crear tabla 'Cuentas"
CREATE TABLE Cuentas (
CuentaID INT PRIMARY KEY IDENTITY(1,1),
NumeroCuenta VARCHAR(20) NOT NULL UNIQUE,
Nombre VARCHAR(100) NOT NULL,
Tipo VARCHAR(50) NOT NULL,
Nivel INT NOT NULL,
Padre INT,
CONSTRAINT FK_Cuentas_Padre FOREIGN KEY (Padre) REFERENCES Cuentas(CuentaID)
);

-- Crear tabla 'Movimientos'
CREATE TABLE Movimientos (
MovimientoID INT PRIMARY KEY IDENTITY(1,1),
Fecha DATE NOT NULL,
CuentaID INT NOT NULL,
Descripcion VARCHAR(200) NOT NULL,
Debe DECIMAL(10,2) NOT NULL DEFAULT 0,
Haber DECIMAL(10,2) NOT NULL DEFAULT 0,
CONSTRAINT FK_Movimientos_Cuentas FOREIGN KEY (CuentaID) REFERENCES Cuentas(CuentaID)
);