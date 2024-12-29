INSERT INTO person(login, email, name, password, phone_number, account_verified, document_verified, birth_date)
VALUES ('alexsmith', 'alex.smith@example.com', 'Alex Smith', 'password123', '+79876543210', true, false, '1990-10-12'),
       ('johndoe', 'john.doe@example.com', 'John Doe', 'securepass', '+79123456789', true, true, '1990-10-12'),
       ('janedoe', 'jane.doe@example.com', 'Jane Doe', 'mypassword', '+79234567890', false, true, '1990-10-12'),
       ('mikebrown', 'mike.brown@example.com', 'Mike Brown', '1234secure', '+79012345678', true, false, '1990-10-12'),
       ('emilywhite', 'emily.white@example.com', 'Emily White', 'whitepass', '+79812345679', false, false, '1990-10-12');