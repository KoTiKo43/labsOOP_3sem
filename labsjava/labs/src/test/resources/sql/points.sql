insert into mathfunction.t_function(id, c_function_type, c_count, c_x_from, c_x_to)
values (525252, 'test_function', 10, 2, 10);

insert into mathfunction.t_point(id, function_id, c_x_value, c_y_value)
values  (11111, 525252, 0, 0),
        (11112, 525252, 1, 1),
        (11113, 525252, 2, 4),
        (11114, 525252, 4, 16);