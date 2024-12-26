CREATE SCHEMA IF NOT EXISTS mathfunction;

CREATE TABLE IF NOT EXISTS mathfunction.t_function (
                                                       id SERIAL PRIMARY KEY,
                                                       c_function_type VARCHAR(255),
                                                       c_count INTEGER,
                                                       c_x_from DOUBLE PRECISION,
                                                       c_x_to DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS mathfunction.t_point (
                                                    id SERIAL PRIMARY KEY,
                                                    function_id INT,
                                                    c_x_value DOUBLE PRECISION,
                                                    c_y_value DOUBLE PRECISION,
                                                    FOREIGN KEY (function_id) REFERENCES mathfunction.t_function(id) ON DELETE CASCADE
);