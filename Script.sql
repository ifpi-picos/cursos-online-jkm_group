DROP TABLE Matricula;
DROP TABLE Curso;
DROP TABLE Aluno;
DROP TABLE Professor;

CREATE TABLE Aluno (
    id_aluno SERIAL PRIMARY KEY,
    email_aluno VARCHAR(255),
    nome_aluno VARCHAR(255),
    senha_aluno VARCHAR(255)
);

CREATE TABLE Professor (
    id_professor SERIAL PRIMARY KEY,
    email_professor VARCHAR(255),
    nome_professor VARCHAR(255),
    senha_professor VARCHAR(255)
);

CREATE TABLE Curso (
    id_curso SERIAL PRIMARY KEY,
    nome_curso VARCHAR(255),
    status_curso VARCHAR(255),
    carga_horaria INT,
    id_professor INT
);

CREATE TABLE Matricula (
    id_matricula SERIAL PRIMARY KEY,
    id_aluno INT,
    id_curso INT,
    status VARCHAR(255),
    nota DECIMAL
);
 
ALTER TABLE Curso ADD CONSTRAINT Curso_FK_Professor
    FOREIGN KEY (id_professor)
    REFERENCES Professor (id_professor);
 
ALTER TABLE Matricula ADD CONSTRAINT Matricula_FK_Aluno
    FOREIGN KEY (id_aluno)
    REFERENCES Aluno (id_aluno);
 
ALTER TABLE Matricula ADD CONSTRAINT Matricula_FK_Curso
    FOREIGN KEY (id_curso)
    REFERENCES Curso (id_curso);
    
    
-- Inserir registros na tabela Aluno
INSERT INTO Aluno (email_aluno, nome_aluno, senha_aluno)
SELECT 
    'aluno' || generate_series || '@dominio.com',
    'Aluno ' || generate_series,
    'senha' || generate_series
FROM generate_series(1, 100);

-- Inserir registros na tabela Professor
INSERT INTO Professor (email_professor, nome_professor, senha_professor)
SELECT 
    'professor' || generate_series || '@dominio.com',
    'Professor ' || generate_series,
    'senha' || generate_series
FROM generate_series(1, 10);

-- Inserir registros na tabela Curso
INSERT INTO Curso (nome_curso, status_curso, carga_horaria, id_professor)
SELECT 
    'Curso ' || generate_series,
    CASE WHEN random() > 0.2 THEN 'Ativo' ELSE 'Inativo' END,
    random() * 100,
    (random() * 9)::int + 1
FROM generate_series(1, 20);

-- Inserir registros na tabela Matricula
INSERT INTO Matricula (id_aluno, id_curso, status, nota)
SELECT 
    (random() * 99)::int + 1,
    (random() * 19)::int + 1,
    CASE WHEN random() > 0.2 THEN 'Ativa' ELSE 'Cancelada' END,
    random() * 10
FROM generate_series(1, 500);

