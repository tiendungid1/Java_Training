use star_kindergarten;

-- SELECT *
-- FROM [star_kindergarten].[dbo].[student]
-- WHERE MONTH([date_of_birth]) = 3 AND YEAR([date_of_birth]) = 2023;

-- SELECT [father_name], [father_mobile], [mother_name], [mother_mobile]
-- FROM [star_kindergarten].[dbo].[student]
-- GROUP BY [father_name], [father_mobile], [mother_name], [mother_mobile]
-- HAVING COUNT([father_name]) = 2 AND COUNT([mother_name]) = 2;

-- SELECT 
--     100 * SUM(CASE WHEN [sex] = 1 THEN 1 ELSE 0 END) / count([id]) AS male_percentage,
--     100 * SUM(CASE WHEN [sex] = 0 THEN 1 ELSE 0 END) / count([id]) AS female_percentage
-- FROM [star_kindergarten].[dbo].[student]
-- WHERE [main_class_id] IN (
--     SELECT [id]
--     FROM [star_kindergarten].[dbo].[main_class]
--     WHERE GETDATE() BETWEEN [start_date] AND [end_date]
-- );

-- SELECT [id]
-- FROM [star_kindergarten].[dbo].[main_class]
-- WHERE ? BETWEEN YEAR([start_date]) AND YEAR([end_date]);

-- DECLARE @currentDate DATE;
-- SET @currentDate = GETDATE();

-- SELECT [s].[id], [s].[name], [s].[date_of_birth], [s].[sex], [s].[main_class_id]
-- FROM [star_kindergarten].[dbo].[student] AS s
-- JOIN [star_kindergarten].[dbo].[main_class] AS ms
-- ON [s].[main_class_id] = [ms].[id]
-- WHERE
--     (@currentDate BETWEEN [ms].[start_date] AND [ms].[end_date])
--     AND
--     (@currentDate >= DATEADD(YEAR, ?, [s].[date_of_birth]) AND @currentDate <= DATEADD(YEAR, ?, [s].[date_of_birth]));