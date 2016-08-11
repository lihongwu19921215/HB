/*
get.ftp.monitor.record.duration.by.type
*/
SELECT
	r_duration,r_monitor_time
FROM
	t_ftp_record_$id
WHERE
	r_check_type = @type
ORDER BY
	id ASC
LIMIT 0,
 150