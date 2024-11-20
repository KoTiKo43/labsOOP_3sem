CREATE SCHEMA if not exists mathfunction;

CREATE TABLE mathfunction.t_function (
    id SERIAL PRIMARY KEY,
    c_function_type VARCHAR(255) NOT NULL,
    c_count INTEGER NOT NULL,
    c_x_from DOUBLE PRECISION NOT NULL,
    c_x_to DOUBLE PRECISION NOT NULL
);

CREATE TABLE mathfunction.t_point (
    id SERIAL PRIMARY KEY,
    function_id INT NOT NULL,
    c_x_value DOUBLE PRECISION NOT NULL,
    c_y_value DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (function_id) REFERENCES mathfunction.t_function(id) ON DELETE CASCADE
);
