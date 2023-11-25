INSERT INTO app_config (secret_key) VALUES ('abc123');

INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('UZS', 'EUR', 0, 0.000074);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('UZS', 'USD', 0, 0.000081);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('UZS', 'RUB', 0, 0.007218);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('UZS', 'GBP', 0, 0.000065);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('UZS', 'JPY', 0, 0.012154);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('EUR', 'UZS', 0, 13427);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('USD', 'UZS', 0, 12304);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('RUB', 'UZS', 0, 138.55);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('GBP', 'UZS', 0, 15419.37);
INSERT INTO commissions (currency_from, currency_to, commission, conversion_rate) VALUES ('JPY', 'UZS', 0, 82.27);

INSERT INTO accounts (currency_name, balance) VALUES ('USD', 1020.12);
INSERT INTO accounts (currency_name, balance) VALUES ('EUR', 5000.75);
INSERT INTO accounts (currency_name, balance) VALUES ('RUB', 45230.21);
INSERT INTO accounts (currency_name, balance) VALUES ('GBP', 80.97);
INSERT INTO accounts (currency_name, balance) VALUES ('JPY', 500.33);