/*
list.team.by.user.id
*/
SELECT DISTINCT
	t.*
FROM
	t_team t
LEFT JOIN t_team_user tu ON t.id = tu.tu_team_id
WHERE
	tu.tu_user_id = @userId