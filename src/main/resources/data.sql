-- Predefined database items:

-- Predefine Admin UI client app (with password: P@ssW0rd!):

INSERT INTO nhs.client_apps (id, name, password, status)
VALUES (1, 'NHS-ADMIN-UI', '$2a$10$HWH5u3XDwT/KY2uu/Px87.ieezR0NJPBqKxd1WVK/M06kGbKtaW9y', 1)
ON DUPLICATE KEY UPDATE id = id;

-- Predefine client app roles:

INSERT INTO nhs.roles (id, name)
VALUES (1, 'ADMIN'), (2, 'USER')
ON DUPLICATE KEY UPDATE id = id;

-- Pre-associate client app with its role:

INSERT INTO nhs.client_apps_roles (client_app_id, role_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE role_id = role_id;

-- Predefine a master admin:

INSERT INTO nhs.admins (id, email, password, first_name, last_name, phone_ro, status, role)
VALUES (1, 'admin', '$2a$10$sfglwqqMV5N779UPzxwIJueELhVKV9BWwoOviJpoi1ke7Rjv.jqFW', 'Master', 'Admin', '0040700100100', 1, 'ADMIN')
ON DUPLICATE KEY UPDATE id = id;
