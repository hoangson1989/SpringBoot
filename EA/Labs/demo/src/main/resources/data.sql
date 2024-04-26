INSERT INTO product (id, name, color) VALUES
    (1, 'Smartphone', 'Black'),
    (2, 'Laptop', 'Silver');

-- Insert dummy data into Review table for Product 1
INSERT INTO review (id, product_id, comment)
VALUES
    (1, 1, 'Great phone, highly recommend!'),
    (2, 1, 'Battery life could be better.'),
    (3, 1, 'Camera quality is impressive.'),
    (4, 1, 'Easy to use interface.'),
    (5, 1, 'Fast delivery and excellent packaging.'),
    (6, 1, 'The screen resolution is amazing.'),
    (7, 1, 'Good value for money.'),
    (8, 1, 'Responsive touchscreen.'),
    (9, 1, 'Sleek design, fits perfectly in the hand.'),
    (10, 1, 'The speaker sound is clear and loud.');

-- Insert dummy data into Review table for Product 2
INSERT INTO review (id, product_id, comment)
VALUES
    (11, 2, 'Excellent laptop, very fast!'),
    (12, 2, 'Sleek design and lightweight.'),
    (13, 2, 'Battery life lasts all day.'),
    (14, 2, 'Highly recommended for work and gaming.'),
    (15, 2, 'The keyboard is comfortable to type on.'),
    (16, 2, 'Great value for the price.'),
    (17, 2, 'Good customer service and support.'),
    (18, 2, 'Impressive graphics performance.'),
    (19, 2, 'The touchpad is responsive and accurate.'),
    (20, 2, 'Fast boot-up time and smooth operation.');
