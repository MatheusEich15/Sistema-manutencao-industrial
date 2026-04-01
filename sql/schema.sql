-- Criação do Banco de Dados para Manutenção Industrial
CREATE DATABASE IF NOT EXISTS manutencao_industrial;
USE manutencao_industrial;

-- 1. Tabela de Máquinas [cite: 372, 373]
CREATE TABLE Maquina (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    setor VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OPERACIONAL' -- OPERACIONAL/EM_MANUTENCAO [cite: 378, 425]
);

-- 2. Tabela de Técnicos [cite: 379, 380]
CREATE TABLE Tecnico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(50)
);

-- 3. Tabela de Peças de Reposição [cite: 385, 386]
CREATE TABLE Peca (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    estoque DOUBLE NOT NULL DEFAULT 0 [cite: 389]
);

-- 4. Tabela de Ordens de Manutenção [cite: 391, 392]
CREATE TABLE OrdemManutencao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idMaquina INT NOT NULL,
    idTecnico INT NOT NULL,
    dataSolicitacao DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE', -- PENDENTE / EXECUTADA / CANCELADA [cite: 398, 470]
    CONSTRAINT fk_ordem_maquina FOREIGN KEY (idMaquina) REFERENCES Maquina(id), [cite: 399]
    CONSTRAINT fk_ordem_tecnico FOREIGN KEY (idTecnico) REFERENCES Tecnico(id) [cite: 400]
);

-- 5. Tabela de Peças utilizadas em cada ordem (N:N) [cite: 401, 402]
CREATE TABLE OrdemPeca (
    idOrdem INT NOT NULL,
    idPeca INT NOT NULL,
    quantidade DOUBLE NOT NULL,
    PRIMARY KEY (idOrdem, idPeca), [cite: 407]
    CONSTRAINT fk_item_ordem FOREIGN KEY (idOrdem) REFERENCES OrdemManutencao(id), [cite: 408]
    CONSTRAINT fk_item_peca FOREIGN KEY (idPeca) REFERENCES Peca(id) [cite: 409]
);