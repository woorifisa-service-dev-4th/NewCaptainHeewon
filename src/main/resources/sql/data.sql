use authdb;

INSERT INTO client (id, client_id, client_secret, redirect_uri)
VALUES
    (1, 'client123', 'secret123', 'http://localhost:3000/oauth'),
    (2, 'client456', 'secret456', 'http://localhost:3000/oauth');

-- User 테이블 (사용자 정보)
INSERT INTO user (id, username, password,client_id)
VALUES
    (1, 'admin', 'password123',1),
    (2, 'user', 'password123',1);
