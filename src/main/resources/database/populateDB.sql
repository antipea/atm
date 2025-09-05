insert into users (u_id, u_first_name, u_last_name , u_date_birth, u_number_passport)
values  (gen_random_uuid(),'Ivan', 'Ivanov', '1990-01-15','MC1654724'),
        (gen_random_uuid(), 'Ihar', 'Ivanov', '1992-03-13','MC8744724'),
        (gen_random_uuid(), 'Anna', 'Petrova', '1982-09-11','MP8241722'),
        (gen_random_uuid(), 'Lev', 'Petrov', '1978-08-10','MP1111722');

/*insert into cards (c_number, c_pincode , c_currency, с_date_expire, u_id)
values  ('4627100101654724', 1234, 'BYN', '2027-05-15'),
        ('4486441729154030', 5678, 'USD', '2026-07-10'),
        ('4750657776370372', 2468, 'BYN', '2028-05-15'),
        ('4024007123874108', 1357, 'BYN', '2025-01-31');*/