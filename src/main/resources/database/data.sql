insert into users (u_first_name, u_last_name , u_date_birth, u_number_passport, created_at)
values  ('Ivan', 'Ivanov', '1990-01-15','MC1654724', '2025-01-15 10:00:00'),
        ('Ihar', 'Ivanov', '1992-03-13','MC8744724', '2025-01-16 10:00:00'),
        ('Anna', 'Petrova', '1982-09-11','MP8241722', '2025-01-17 10:00:00'),
        ('Olga', 'Sharova', '1986-05-11','KB5871722', '2025-01-19 10:00:00'),
        ('Lev', 'Petrov', '1978-08-10','MP1111722', '2025-01-19 10:00:00')
ON CONFLICT DO NOTHING;

insert into cards (c_number, c_pin_code, c_currency, expires_at, c_balance, c_user_id)
select '4627100101654724' as c_number, 1234 as c_pincode, 'BYN' as c_currency, '2027-05-15 23:59:59' as с_date_expired, 878 as c_balance, u.u_id as u_id
from users u  where u.u_number_passport='MC1654724' ON CONFLICT DO NOTHING;

insert into cards (c_number, c_pin_code, c_currency, expires_at, c_balance, c_user_id)
select '4486441729154030' as c_number, 5678 as c_pincode, 'BYN' as c_currency, '2026-07-10 23:59:59' as с_date_expired, 1200 as c_balance, u.u_id as u_id
from users u  where u.u_number_passport='MC1654724' ON CONFLICT DO NOTHING;

insert into cards (c_number, c_pin_code, c_currency, expires_at, c_balance, c_user_id)
select '4750657776370372' as c_number, 2468 as c_pincode, 'USD' as c_currency, '2028-05-15 23:59:59' as с_date_expired, 1500 as c_balance,   u.u_id as u_id
from users u  where u.u_number_passport='MC8744724' ON CONFLICT DO NOTHING;

insert into cards (c_number, c_pin_code, c_currency, expires_at, c_balance, c_user_id)
select '4024007123874108' as c_number, 1357 as c_pincode, 'BYN' as c_currency, '2025-01-31 23:59:59' as с_date_expired, 480 as c_balance, u.u_id as u_id
from users u  where u.u_number_passport='MP8241722' ON CONFLICT DO NOTHING;

insert into cards (c_number, c_pin_code, c_currency, expires_at, c_balance, c_user_id)
select '5453010000074617' as c_number, 7044 as c_pincode, 'EUR' as c_currency, '2026-01-31 23:59:59' as с_date_expired, 350 as c_balance, u.u_id as u_id
from users u  where u.u_number_passport='KB5871722' ON CONFLICT DO NOTHING;

INSERT INTO payments (p_name, p_description, p_code, is_active, created_at) VALUES
('Мобильная связь', 'Пополнение баланса мобильного телефона', 3001, true, '2025-01-01 00:00:00'),
('Интернет', 'Оплата услуг интернет-провайдера', 3002, true, '2025-01-01 00:00:00'),
('Коммунальные услуги', 'Оплата ЖКХ', 3003, true, '2025-01-01 00:00:00'),
('Транспорт', 'Пополнение проездного билета', 3006, true, '2025-01-01 00:00:00'),
('Образование', 'Оплата обучения', 3007, true, '2025-01-01 00:00:00')
ON CONFLICT DO NOTHING;

INSERT INTO rates (r_base_currency, r_target_currency, r_rate, updated_at) VALUES
('BYN', 'USD', 0.3100, NOW()),
('BYN', 'EUR', 0.2850, NOW()),
('BYN', 'RUB', 30.1500, NOW()),
('USD', 'BYN', 3.2258, NOW()),
('EUR', 'BYN', 3.5088, NOW()),
('RUB', 'BYN', 0.0332, NOW()),
('USD', 'EUR', 0.9194, NOW()),
('USD', 'RUB', 97.2581, NOW()),
('EUR', 'USD', 1.0877, NOW()),
('EUR', 'RUB', 105.7895, NOW()),
('RUB', 'USD', 0.0103, NOW()),
('RUB', 'EUR', 0.0094, NOW()) ON CONFLICT (r_base_currency, r_target_currency) DO UPDATE
SET r_rate = EXCLUDED.r_rate, updated_at = NOW();

INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'DEPOSIT' as t_type, 1000.00 as t_amount, 'BYN' as t_currency, 1000.00 as t_base_amount,
       'BYN' as t_base_currency, 1.0 as t_rate, 'Пополнение через банкомат' as t_description, NULL as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-20 09:00:00' as created_at
FROM cards c  WHERE c.c_number ='4627100101654724' ON CONFLICT DO NOTHING;

INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'WITHDRAWAL' as t_type, 500.00 as t_amount, 'BYN' as t_currency, 500.00 as t_base_amount,
       'BYN' as t_base_currency, 1.0 as t_rate, 'Снятие наличных' as t_description, NULL as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-23 09:00:00' as created_at
FROM cards c  WHERE c.c_number ='4627100101654724' ON CONFLICT DO NOTHING;

INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'TRANSFER' as t_type, 100.00 as t_amount, 'BYN' as t_currency, 100.00 as t_base_amount,
       'BYN' as t_base_currency, 1.0 as t_rate, 'Перевод другу' as t_description, '4486441729154030' as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-25 13:10:00' as created_at
FROM cards c  WHERE c.c_number ='4627100101654724' ON CONFLICT DO NOTHING;

INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'WITHDRAWAL' as t_type, 100.00 as t_amount, 'BYN' as t_currency, 100.00 as t_base_amount,
       'BYN' as t_base_currency, 1.0 as t_rate, 'Снятие наличных' as t_description, NULL as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-25 16:10:00' as created_at
FROM cards c  WHERE c.c_number ='4486441729154030' ON CONFLICT DO NOTHING;

INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'DEPOSIT' as t_type, 200.00 as t_amount, 'USD' as t_currency, 200.00 as t_base_amount,
       'USD' as t_base_currency, 1.0 as t_rate, 'Валютное пополнение' as t_description, NULL as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-21 10:30:00' as created_at
FROM cards c  WHERE c.c_number ='4750657776370372' ON CONFLICT DO NOTHING;

INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'WITHDRAWAL' as t_type, 200.00 as t_amount, 'USD' as t_currency, 200.00 as t_base_amount,
       'USD' as t_base_currency, 1.0 as t_rate, 'Валютное пополнение' as t_description, NULL as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-21 10:30:00' as created_at
FROM cards c  WHERE c.c_number ='4750657776370372' ON CONFLICT DO NOTHING;


INSERT INTO transactions (t_card_id, t_type, t_amount, t_currency, t_base_amount, t_base_currency, t_rate, t_description,
                          t_target_card_number, t_payment_code, status, created_at)
SELECT c.c_id as t_card_id, 'WITHDRAWAL' as t_type, 500.00 as t_amount, 'USD' as t_currency, 155.00 as t_base_amount,
       'USD' as t_base_currency, 0.3100 as t_rate, 'Снятие BYN → USD' as t_description, NULL as t_target_card_number,
       NULL as t_payment_code, 'COMPLETED' as status,'2024-01-30 16:30:00' as created_at
FROM cards c  WHERE c.c_number ='4486441729154030' ON CONFLICT DO NOTHING;
