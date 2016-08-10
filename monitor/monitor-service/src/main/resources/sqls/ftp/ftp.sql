/*
get.ftp.monitor.record.duration.by.type
*/
SELECT
	r_duration
FROM
	t_ftp_record_$id
WHERE
	r_check_type = @type
ORDER BY
	id DESC
LIMIT 0,
 150