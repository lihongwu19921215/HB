/*
get.ftp.monitor.record.duration.by.type
*/
SELECT
	r.r_duration,
	r.r_monitor_time
FROM
	(
		SELECT
			t.*
		FROM
			t_ftp_record_$id t
		WHERE
			r_check_type = @type
		ORDER BY
			t.id DESC
		LIMIT 0,
		150
	) r
ORDER BY
	r.id ASC