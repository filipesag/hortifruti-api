-- Criando schemas

CREATE TABLE formato_venda (
    id UUID PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL
);

CREATE TABLE forma_pagamento (
    id UUID PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL,
    valor_taxa_compra DECIMAL(10,2) NOT NULL
);

CREATE TABLE sede (
    id UUID PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    rua VARCHAR(150) NOT NULL,
    numero VARCHAR(6) NOT NULL,
    descricao TEXT
);

CREATE TABLE fornecedor (
    id UUID PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    telefone VARCHAR(15),
    email VARCHAR(100),
    contrato_status BOOLEAN NOT NULL
);

CREATE TABLE produto (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    unidade_medida VARCHAR(20) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    fornecedor_id UUID REFERENCES fornecedor(id)
);

CREATE TABLE estoque_produto (
    id UUID PRIMARY KEY,
    produto_id UUID REFERENCES produto(id),
    sede_id UUID REFERENCES sede(id),
    quantidade DECIMAL(10,2) NOT NULL
);

CREATE TABLE venda (
    id UUID PRIMARY KEY,
    data_venda TIMESTAMP NOT NULL,
    formato_venda_id UUID REFERENCES formato_venda(id),
    status_venda VARCHAR(20) NOT NULL,
    sede_id UUID REFERENCES sede(id),
    total DECIMAL(10,2) NOT NULL
);

CREATE TABLE item_venda (
    id UUID PRIMARY KEY,
    venda_id UUID REFERENCES venda(id),
    produto_id UUID REFERENCES produto(id),
    quantidade DECIMAL(10,2) NOT NULL,
    preco_unit DECIMAL(10,2) NOT NULL
);

CREATE TABLE balancete_operacao (
    id UUID PRIMARY KEY,
    data_receita TIMESTAMP NOT NULL,
    valor_receita DECIMAL(10,2) NOT NULL,
    forma_pagamento_id UUID REFERENCES forma_pagamento(id),
    venda_id UUID REFERENCES venda(id)
);

-- Inserir formatos de venda
INSERT INTO formato_venda (id, tipo) VALUES
('5e996c42-4e70-4e03-a837-1c36506aa580', 'FISICA'),
('9019df90-d99e-4b3e-a441-362d00347d59', 'APP_QUICK_FOOD'),
('31e17215-1579-4399-8f10-6bd457c212b3', 'APP_RANGONTIME'),
('4e3a78b2-5e57-46f7-9c68-14ff65fd7309', 'APP_NATIVO_HORTIFRUTI'),
('28949a91-c174-4fa1-8d5a-f70df055fd01', 'ENCOMENDA_CONTRATO'),
('2b95ff87-b947-4b39-81c5-6ff7c02e15d3', 'RETIRADA');

-- Inserir formas de pagamento
INSERT INTO forma_pagamento (id, descricao, valor_taxa_compra) VALUES
('47b96c6f-e217-4e27-99cc-0f1a574c9bcb', 'DINHEIRO', 0.00),
('3dbf6784-f48d-4f0d-bd57-76ffad7ea5f6', 'CARTAO_DEBITO_VIDACARD', 2.99),
('59c5d9e6-b6e6-4637-8c1a-cbfbcd63cdb3', 'CARTAO_REFEICAO_ALEXO', 1.99),
('c896ef7f-b8c6-4a0a-b408-d2e045c2c3fa', 'PIX', 0.00),
('ef0cc05e-867e-463c-b2a9-93fe22113bfa', 'CARTAO_REFEICAO_SODELO', 3.50);

-- Inserir sedes (lojas físicas)
INSERT INTO sede (id, nome, bairro, cidade, estado, rua, numero, descricao) VALUES
('a3808b7e-f54a-4f7e-87ff-540d0fd65728', 'Hortifruti Savassi', 'Savassi', 'Belo Horizonte', 'MG', 'Rua Pernambuco', '100', 'Loja principal no coração de BH'),
('80bd756a-4b6b-47e7-bc7e-9e05f369aec1', 'Hortifruti Contagem', 'Eldorado', 'Contagem', 'MG', 'Avenida João César de Oliveira', '1275', 'Loja no centro de Contagem'),
('c2d63742-6b5b-4c4c-8f34-c2f24cc96758', 'Hortifruti Betim', 'Ingá', 'Betim', 'MG', 'Rua Edméia Matos Lazzarotti', '300', 'Loja próxima ao BH Shopping Betim'),
('1743b1bb-8bd1-4a76-bda5-47fe1dcaedb6', 'Hortifruti Nova Lima', 'Centro', 'Nova Lima', 'MG', 'Rua São Paulo', '200', 'Loja no centro histórico de Nova Lima'),
('cacc5f2a-b967-4e9a-a406-99a7b76c1b7f', 'Hortifruti Lagoa Santa', 'Centro', 'Lagoa Santa', 'MG', 'Rua Nossa Senhora do Rosário', '150', 'Loja próxima ao aeroporto');

-- Inserir fornecedores locais
INSERT INTO fornecedor (id, nome, cidade, estado, cnpj, telefone, email, contrato_status) VALUES
('e2621c28-4696-44d1-b823-cc59fd8851bb', 'Fazenda do Sol', 'Sabará', 'MG', '12345678000191', '3133334444', 'contato@fazendadosol.com.br', true),
('f5143408-0985-4a44-a47f-61d65edecf30', 'Sítio das Frutas', 'Brumadinho', 'MG', '98765432000191', '3133335555', 'vendas@sitiodasfrutas.com.br', true),
('9bbf0ef8-e52e-49f6-a3f3-f2f74a7760ae', 'Produtos Orgânicos MG', 'Esmeraldas', 'MG', '45678912000191', '3133336666', 'contato@organicosmg.com.br', true),
('5aef0aef-498e-40bc-a0e2-4e7ab098db67', 'Horta da Serra', 'Ribeirão das Neves', 'MG', '32165498000191', '3133337777', 'vendas@hortadaserra.com.br', true),
('5ffb981c-353e-40e0-a3c5-f0e74831c5c4', 'Frutas Frescas BH', 'Santa Luzia', 'MG', '65412378000191', '3133338888', 'compras@frutasfrescasbh.com.br', true);

-- Inserir produtos
INSERT INTO produto (id, nome, unidade_medida, preco, fornecedor_id) VALUES
('1f2a5d9a-f4c1-4d9b-b5f1-054e83a72eaf', 'Banana Prata', 'KG', 4.50, 'e2621c28-4696-44d1-b823-cc59fd8851bb'),
('b8cd5c40-6df2-402e-bb5d-0f3c3fdc1f2f', 'Maçã Fuji', 'KG', 7.90, 'f5143408-0985-4a44-a47f-61d65edecf30'),
('812e6a2e-d6e1-4db0-a69e-c01d83bbbf4e', 'Alface Crespa', 'UNIDADE', 2.50, '9bbf0ef8-e52e-49f6-a3f3-f2f74a7760ae'),
('a2dbf9e4-9d6e-43ce-bbee-2e2e576f4179', 'Cenoura', 'KG', 3.80, '5aef0aef-498e-40bc-a0e2-4e7ab098db67'),
('cfcb3025-194c-4f34-8266-9a79a8f1b7e5', 'Laranja Pêra', 'KG', 2.90, '5ffb981c-353e-40e0-a3c5-f0e74831c5c4');

-- Inserir estoque por sede
INSERT INTO estoque_produto (id, produto_id, sede_id, quantidade) VALUES
('ddcf623f-d99d-4aa3-8b9b-1c83c4c37ba2', '1f2a5d9a-f4c1-4d9b-b5f1-054e83a72eaf', 'a3808b7e-f54a-4f7e-87ff-540d0fd65728', 150.0),
('1810609a-52fc-44d3-85e3-d94eaf4f4e1b', 'b8cd5c40-6df2-402e-bb5d-0f3c3fdc1f2f', '80bd756a-4b6b-47e7-bc7e-9e05f369aec1', 100.0),
('2ed5fcb3-2b5a-47fc-bfb3-678e5d84d55f', '812e6a2e-d6e1-4db0-a69e-c01d83bbbf4e', 'c2d63742-6b5b-4c4c-8f34-c2f24cc96758', 200.0),
('8a12d786-7dfc-41d5-87d0-b39cc3c537f1', 'a2dbf9e4-9d6e-43ce-bbee-2e2e576f4179', '1743b1bb-8bd1-4a76-bda5-47fe1dcaedb6', 180.0),
('34834b7d-b97e-4061-950c-bb73ec331c2a', 'cfcb3025-194c-4f34-8266-9a79a8f1b7e5', 'cacc5f2a-b967-4e9a-a406-99a7b76c1b7f', 120.0);

-- Inserir venda (com sede física)
INSERT INTO venda (id, data_venda, formato_venda_id, status_venda, sede_id, total) VALUES
('a1b2c3d4-e5f6-4789-abcd-1234567890ab', '2025-05-13 10:00:00', '5e996c42-4e70-4e03-a837-1c36506aa580', 'APROVADA', 'a3808b7e-f54a-4f7e-87ff-540d0fd65728', 20.20);

-- Inserir itens da venda
INSERT INTO item_venda (id, venda_id, produto_id, quantidade, preco_unit) VALUES
('0e5c093c-4497-4dc3-913e-bbb93cfe55c2', 'a1b2c3d4-e5f6-4789-abcd-1234567890ab', '1f2a5d9a-f4c1-4d9b-b5f1-054e83a72eaf', 2.0, 4.50),
('ebc1a59b-59dc-4f23-b180-d68e03dc2a52', 'a1b2c3d4-e5f6-4789-abcd-1234567890ab', 'cfcb3025-194c-4f34-8266-9a79a8f1b7e5', 3.0, 2.90);

-- Inserir balancete
INSERT INTO balancete_operacao (id, data_receita, valor_receita, forma_pagamento_id, venda_id) VALUES
('1bb277c0-526d-4ce0-b40c-538d126f11c2','2025-05-13 10:00:00',20.20,'47b96c6f-e217-4e27-99cc-0f1a574c9bcb','a1b2c3d4-e5f6-4789-abcd-1234567890ab');
-- Fim do script
