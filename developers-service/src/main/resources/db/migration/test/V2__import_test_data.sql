INSERT INTO teams(
	 id, name, description )
	VALUES (1, 'Team A', 'Team A description'),
	       (2, 'Team B', 'Team B description'),
	       (3, 'Team C', 'Team C description'),
	       (4, 'Team Empty', 'Team empty description');

INSERT INTO developers(
	 id, name, team_id )
	VALUES (1, 'Developer 1A', 1),
	       (2, 'Developer 1B', 1),
	       (3, 'Developer 2A', 2),
	       (4, 'Developer 2B', 2),
	       (5, 'Developer 3A', 3),
	       (6, 'Developer 3B', 3);


