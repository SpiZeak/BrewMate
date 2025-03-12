INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
VALUES
  ('Cappuccino', 5, 3, 2, 0, 'Italian', 'Cappuccino.webp'),  -- Espresso 50%, Milk 30%, Foam 20%
  ('Espresso', 10, 0, 0, 0, 'Italian', 'Espresso.webp'),   -- Espresso 100%
  ('Latte', 3, 6, 1, 0, 'American', 'Latte.webp'),      -- Espresso 30%, Milk 60%, Foam 10%
  ('Flat White', 5, 5, 0, 0, 'Australian', 'Flat White.webp'), -- Espresso 50%, Milk 50%
  ('Macchiato', 8, 0, 2, 0, 'Italian', 'Macchiato.webp'),   -- Espresso 80%, Foam 20%
  ('Mocha', 3, 5, 2, 0, 'American', 'Mocha.webp'),      -- Espresso 30%, Milk 50%, Foam 20%
  ('Ristretto', 10, 0, 0, 0, 'Italian', 'Ristretto.webp'),  -- Stronger than Espresso
  ('Long Black', 5, 0, 0, 5, 'Australian', 'Long Black.webp'), -- Espresso 50%, Water 50%
  ('Americano', 3, 0, 0, 7, 'American', 'Americano.webp'),  -- Espresso 30%, Water 70%
  ('Breve', 3, 6, 1, 0, 'American', 'Breve.webp');      -- Espresso 30%, Milk 60%, Foam 10%
