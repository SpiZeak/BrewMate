INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Cappuccino', 5, 3, 2, 0, 'Italian', 'Cappuccino.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Cappuccino');  -- Espresso 50%, Milk 30%, Foam 20%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Espresso', 10, 0, 0, 0, 'Italian', 'Espresso.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Espresso');  -- Espresso 100%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Latte', 3, 6, 1, 0, 'American', 'Latte.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Latte');  -- Espresso 30%, Milk 60%, Foam 10%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Flat White', 5, 5, 0, 0, 'Australian', 'Flat White.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Flat White');  -- Espresso 50%, Milk 50%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Macchiato', 8, 0, 2, 0, 'Italian', 'Macchiato.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Macchiato');  -- Espresso 80%, Foam 20%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Mocha', 3, 5, 2, 0, 'American', 'Mocha.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Mocha');  -- Espresso 30%, Milk 50%, Foam 20%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Ristretto', 10, 0, 0, 0, 'Italian', 'Ristretto.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Ristretto');  -- Espresso 100% (Shorter, more concentrated than espresso)

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Long Black', 5, 0, 0, 5, 'Australian', 'Long Black.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Long Black');  -- Espresso 50%, Water 50%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Americano', 3, 0, 0, 7, 'American', 'Americano.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Americano');  -- Espresso 30%, Water 70%

INSERT INTO coffees (name, espresso_ratio, milk_ratio, foam_ratio, water_ratio, brewing_style, image_path)
SELECT 'Breve', 3, 6, 1, 0, 'American', 'Breve.webp'
WHERE NOT EXISTS (SELECT 1 FROM coffees WHERE name = 'Breve');  -- Espresso 30%, Milk 60%, Foam 10% (similar to Latte but uses half-and-half or cream)
