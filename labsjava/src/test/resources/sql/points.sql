insert into mathfunction.t_function(id, c_function_type, c_count, c_x_from, c_x_to)
values (1, 'test_function', 10, 2, 10);

insert into mathfunction.t_point(id, function_id, c_x_value, c_y_value)
values  (1, 1, 0, 0),
        (2, 1, 1, 1),
        (3, 1, 2, 4),
        (4, 1, 4, 16);